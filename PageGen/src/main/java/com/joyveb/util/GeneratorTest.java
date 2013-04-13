package com.joyveb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.joyveb.util.genarator.FieldInfo;
import com.joyveb.util.genarator.FtlClassInfo;
import com.joyveb.util.genarator.Options;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class GeneratorTest {

	public final static HashSet<String> modtables = new HashSet<String>();
	static {
	};
	static boolean genXML=true;

	public static Connection getConnection(JDBCConnectionConfiguration cc)
			throws Exception {
		String driver = cc.getDriverClass();// "oracle.jdbc.driver.OracleDriver";
		String username = cc.getUserId();// "username";
		String password = cc.getPassword();// "pass";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(cc.getConnectionURL(), username, password);
		return conn;
	}

	public static void getColumnNames(ResultSet rs, FtlClassInfo fci)
			throws SQLException {
		while (rs.next()) {// table_name, column_name, comments
			String table_name = rs.getString("table_name");
			String nullable = rs.getString("NULLABLE");
			String column_name = rs.getString("column_name").toLowerCase();
			String comments = rs.getString("comments");
			String type = rs.getString("data_type");
			String tbcomments = rs.getString("tbcomments");
			//String primaryKey = rs.getString("PK").toLowerCase();
			String primaryKey = "primaryKey";//抛弃了primaryKey方法
			//添加count
			String count = rs.getString("count");

			String name = FieldHelper.column2Field(column_name);
			FieldInfo fi = new FieldInfo();
			if (comments == null) {
				comments = name;
			}

			fi.setComments(comments);
			fi.setName(name);
			fi.setColumn(column_name);
			fi.setType(type);
			fi.setHasOption(false);
			fi.setNullAble(nullable);//添加是否为空属性 N 不允许 Y 允许

			if (comments != null) {
				comments.replace("（", "(");
				comments.replace("）", ")");
				comments.replace("，", ",");
				comments.replace("；", ";");
				comments.replace("：", ":");
				String str_tmp = StringUtils.substringBetween(comments, "(", ")");
				if(str_tmp!=null&&str_tmp.split("[(|)|,|;]").length>1){
				String options[] = comments.split("[(|)|,|;]");
				if (options.length > 1) {
					fi.setComments(options[0]);
					if (options[1].startsWith("tt:")) {
						// is a ref
						fi.setHasTooltip(true);
						fi.setTttype(options[1].split(":")[1]);
						System.out.println("tooltip:"+fi.getTttype());
						
					} 
					else if (options[1].equalsIgnoreCase("ref")) {
						// is a ref
						fi.setRef(true);
						fi.setRefPath(options[2]);
						fi.setRefKeyColumn(options[3]);
						fi.setRefLabelColumn(options[4]);
						fi.setRefKeyField(FieldHelper.column2Field(options[3]));
						fi.setRefLabelField(FieldHelper.column2Field(options[4]));

					} else {
						StringBuffer buff = new StringBuffer("{");
						fi.setHasOption(true);
						for (String op : options) {
							String kv[] = op.split(":");
							if (kv.length == 2) {
								try{
								fi.getOptions().add(
										new Options(Integer.parseInt(kv[0]
												.replaceAll("－", "-")), kv[1]));
								buff.append("\"" + kv[0] + "\":\"" + kv[1]
										+ "\",");
								}catch (Exception e) {
									// TODO: 枚举注释规范
									e.printStackTrace();
								}
							}
						}
						buff.append("}");
						fi.setOptionString(buff.toString());
					}
				}
				}
			}
			if (tbcomments == null) {
				tbcomments = table_name;
			}
			fci.setTablecomments(tbcomments);
			fci.getFields().add(fi);
			fci.setPrimaryKey(primaryKey);
			fci.setPrimaryKeyMethod("set"
					+ primaryKey.substring(0, 1).toUpperCase()
					+ primaryKey.substring(1));
			fci.setCount(NumberUtils.toInt(count));
		}

	}

	public static void genFtl(FtlClassInfo clazzinfo, String subpack,
			String tmplname, String endx) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			// Here I set a file directory for it:
			cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);

			Template temp = cfg.getTemplate(tmplname);

			/* Create a data-model */
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("clazzinfo", clazzinfo);
			root.put("count", clazzinfo.getCount());//TODO
			root.put("primaryKey", clazzinfo.getPrimaryKey());
			root.put("domainClazz", clazzinfo.getDomainClass());
			root.put("domainObject", clazzinfo.getDomainObject());
			root.put("module", clazzinfo.getModule());
			root.put("packageName",
					clazzinfo.getPackageName().replaceAll("\\"+File.separator, "."));

			File dst = new File(clazzinfo.getTargetProject() + File.separator
					+ clazzinfo.getPackageName() + File.separator
					+ subpack.replaceAll("\\.", "\\"+File.separator)
					+ File.separator + clazzinfo.getDomainClass() + endx
					+ ".java");
			dst.getParentFile().mkdirs();
			FileOutputStream fout = new FileOutputStream(dst);
			/* Merge data-model with template */
			Writer out = new OutputStreamWriter(fout);
			temp.process(root, out);
			out.flush();
			fout.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void genPage(FtlClassInfo clazzinfo, String menu) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			// Here I set a file directory for it:
			cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);

			Template temp = cfg.getTemplate("list.ftl");//TODO 选择模版

			/* Create a data-model */
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("project", clazzinfo.getProject());
			root.put("clazzinfo", clazzinfo);
			root.put("domainObject", clazzinfo.getDomainObject());
			root.put("module", clazzinfo.getModule());
			root.put("packageName",
					clazzinfo.getPackageName().replaceAll("\\"+File.separator, "."));
			root.put("menuName", menu);
			File dst1 = new File(clazzinfo.getTargetProject() + File.separator);

			File dst = new File(dst1.getParent() + File.separator + "pages"
					+ File.separator + "rest" + File.separator
					+ clazzinfo.getModule() + File.separator
					+ clazzinfo.getDomainObject() + ".html");
//			System.out.println("dst::" + dst.getAbsolutePath());
			dst.getParentFile().mkdirs();
			FileOutputStream fout = new FileOutputStream(dst);
			/* Merge data-model with template */
			Writer out = new OutputStreamWriter(fout);
			temp.process(root, out);
			out.flush();
			fout.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void genEnums(FtlClassInfo clazzinfo, String subpack) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			// Here I set a file directory for it:
			cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);

			Template temp = cfg.getTemplate("ColumnType.ftl");

			/* Create a data-model */
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("domainClass", clazzinfo.getDomainClass());
			for (FieldInfo field : clazzinfo.getFields()) {
				if (field.isHasOption()) {
					String enumClass = clazzinfo.getDomainClass() + "_"
							+ field.getName().substring(0, 1).toUpperCase()
							+ field.getName().substring(1);
					root.put("enumClass", enumClass);
					root.put("field", field);
					root.put("packageName", clazzinfo.getPackageName()
							.replaceAll("\\"+File.separator, ".") + "." + subpack);
					File dst = new File(clazzinfo.getTargetProject()
							+ File.separator
							+ clazzinfo.getPackageName().replaceAll("\\.",
									File.separator) + File.separator
							+ subpack.replaceAll("\\.", File.separator)
							+ File.separator + enumClass + ".java");

					//System.out.println("dst::" + dst.getAbsolutePath());
					dst.getParentFile().mkdirs();
					FileOutputStream fout = new FileOutputStream(dst);
					/* Merge data-model with template */
					Writer out = new OutputStreamWriter(fout);
					temp.process(root, out);
					out.flush();
					fout.close();

				}
			}

		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	public static void main(String[] args) {
		HashMap<String, String> moduleCHName = new HashMap<String, String>();
		moduleCHName.put("sys", "系统管理");
		moduleCHName.put("gold", "资金管理");
		moduleCHName.put("stat", "统计");
		moduleCHName.put("cm", "内容管理");
		moduleCHName.put("para", "参数管理");
		moduleCHName.put("day", "日统计");
		moduleCHName.put("week", "周统计");
		moduleCHName.put("month", "月统计");
		moduleCHName.put("year", "年统计");
		moduleCHName.put("core", "VO表");
		moduleCHName.put("admin", "管理员");
		String cfgs[] = new String[] { 
				"/generatorConfig-terrace.xml"
				};
//		String cfgs[] = new String[] { 
//				"/generatorConfig-bj3.xml","/generatorConfig-saleReport.xml","/generatorConfig-terrace.xml"
//				};
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		List<FtlClassInfo> classinfos = new ArrayList<FtlClassInfo>();
		for (String cfg : cfgs) {
			try {
				// System.out.println("parsingFile;" + cfg);
				List<String> warnings = new ArrayList<String>();
				boolean overwrite = true;
				ConfigurationParser cp = new ConfigurationParser(warnings);
				Configuration config = cp.parseConfiguration(GeneratorTest.class
						.getResourceAsStream(cfg));
				DefaultShellCallback callback = new DefaultShellCallback(
						overwrite);
				MyBatisGenerator myBatisGenerator = new MyBatisGenerator(
						config, callback, warnings);
				Context ctx = config.getContext("ulwp_persist_stat");//TODO
				conn = getConnection(ctx.getJdbcConnectionConfiguration());
				String targetProject = ctx
						.getJavaClientGeneratorConfiguration()
						.getTargetProject();
				File sqlmapFile = new File(ctx
						.getSqlMapGeneratorConfiguration().getTargetProject()
						+ File.separator
						+ ctx.getSqlMapGeneratorConfiguration()
								.getTargetPackage()
								.replaceAll("\\.", "\\" + File.separator));
				if (sqlmapFile.exists()&&genXML)
					for (String file : sqlmapFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File arg0, String arg1) {
							return arg1.endsWith("SqlMap.xml");
						}
					})) {
						File ff=new File(sqlmapFile.getAbsolutePath()+File.separator+file);
						ff.delete();
						// 
					}
				String packageName = ctx.getJavaModelGeneratorConfiguration()
						.getTargetPackage().replaceAll(".domain", "")
						.replace(".", File.separator);

				for (TableConfiguration tc : ctx.getTableConfigurations()) {
					if(modtables.size()>0&&!modtables.contains(tc.getTableName()))
					{
						continue;
					}
					System.out.println(tc.getTableName() + ":"
							+ tc.getDomainObjectName());
					// prepare query
					String query = "select t.table_name,t.NULLABLE,t.column_name, c.comments,t.data_type,u.comments as tbcomments,a.count from user_tab_columns t "
							+ "LEFT JOIN user_col_comments c "
							+ "ON c.table_name=t.table_name and c.column_name=t.column_name "
							+ "LEFT JOIN user_tab_comments u "
							+ "ON u.table_name=t.table_name "
							+ "left join (select count(col.column_name)as count,con.TABLE_NAME "
							+"from user_constraints con inner join  user_cons_columns col "
							+"on con.constraint_name = col.constraint_name "
							+"where con.constraint_type='P' group by con.table_name) a "
							+"on a.table_name = t.table_name "
							+"where t.TABLE_NAME = '" 
							+ tc.getTableName() 
							+ "'";
							
					// System.out.println("query::" + query);
					// create a statement
					stmt = conn.createStatement();
					// execute query and return result as a ResultSet
					rs = stmt.executeQuery(query);

					FtlClassInfo fci = new FtlClassInfo();
					fci.setProject("SpringMVCTest");//TODO set PorjectName
					fci.setDomainClass(tc.getDomainObjectName());
					fci.setDomainObject(tc.getDomainObjectName().toLowerCase());
					fci.setTableName(tc.getTableName());
					fci.setModule(tc.getTableName().split("_")[1].toLowerCase());
					// get the column names from the ResultSet
					getColumnNames(rs, fci);
					fci.setTargetProject(targetProject);
					fci.setPackageName(packageName);
					classinfos.add(fci);
					System.out.println(JsonUtil.bean2Json(fci));
					 genFtl(fci, "ctrl", "Controller.ftl", "Ctrl");
					 genFtl(fci, "service.iface", "ServiceIface.ftl",
					 "Service");
					 genFtl(fci, "service.impl",
					 "ServiceImpl.ftl","ServiceImpl");
					 genEnums(fci, "enums");
					genPage(fci, moduleCHName.get(fci.getModule()));//TODO
					/*---------------------显示参数---------------------------------------*/
					boolean flag = true;
					if(flag){
						System.out.println(JSONObject.fromObject(fci));//显示参数
						flag=false;
					}
					/*-------------------------------------------------------------*/
//					genPrivilegeInsertSQL(fci);
				}
				if(genXML)
				{
					myBatisGenerator.generate(null);
				}
				for (String warning : warnings) {
					System.out.println(warning);
				}
				System.out.println("DONE");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
//		try {
//			FileOutputStream fout=new FileOutputStream("sql"+File.separator+"insert.sql");
//			
//
//			for(String insertSQL:insertSQLs)
//			{
//				System.out.println(insertSQL);
//				fout.write(insertSQL.getBytes());
//			}
//			fout.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	static List<String> insertSQLs=new ArrayList<String>();
	static{
		//modules
		StringBuffer sb=new StringBuffer();

		sb.append("delete from T_SYS_RESOURCE where resid='menu.cm';\n");
		sb.append("delete from T_SYS_RESOURCE where resid='menu.sys';\n");
		sb.append("delete from T_SYS_RESOURCE where resid='menu.gold';\n");
		sb.append("delete from T_SYS_RESOURCE where resid='menu.stat';\n");

		sb.append("insert into T_SYS_RESOURCE(resid,resname) values('menu.cm','内容管理');\n");
		sb.append("insert into T_SYS_RESOURCE(resid,resname) values('menu.sys','系统管理');\n");
		sb.append("insert into T_SYS_RESOURCE(resid,resname) values('menu.gold','资金管理');\n");
		sb.append("insert into T_SYS_RESOURCE(resid,resname) values('menu.stat','统计管理');\n");
		sb.append("\n\n");
		insertSQLs.add(sb.toString());
	}
	public static void genPrivilegeInsertSQL(FtlClassInfo fci)
	{
		StringBuffer sb=new StringBuffer();
		String resid=fci.getModule()+"."+fci.getDomainClass().toLowerCase();
		
		sb.append("delete from T_SYS_RESOURCE where resid='"+resid+"';\n");
		sb.append("insert into T_SYS_RESOURCE(resid,resname,respath) values(");
//		 ('id','name','path','desc');
		sb.append("'").append(resid).append("'");
		sb.append(",'").append(fci.getTablecomments()).append("'");
		sb.append(",'").append("/rest/"+fci.getModule()+"/"+fci.getDomainClass().toLowerCase()+".html").append("'");

		sb.append(");\n\n");
		insertSQLs.add(sb.toString());
	}
	
	
	
	
}
