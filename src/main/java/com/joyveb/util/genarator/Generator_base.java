package com.joyveb.util.genarator;

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
import java.util.List;
import java.util.Map;

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

import com.joyveb.common.gens.bizcom.bean.FieldInfo;
import com.joyveb.common.gens.bizcom.bean.FtlClassInfo;
import com.joyveb.common.gens.bizcom.bean.Options;
import com.joyveb.util.FieldHelper;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * 
 * 项目名称：PageGen 类名称：Generator_base
 * 
 * @Company: 北京畅享互联有限公司
 * @Copyright: Copyright (c) 2012
 * @Author： 杨其桔 创建时间：2013-6-28 下午6:41:19 修改备注：
 * @version
 * 
 */
public abstract class Generator_base {
	static HashMap<String, String> moduleCHName = new HashMap<String, String>();
	static String cfgs[] = null;
	static String contextid = "ulwp_persist_stat";
	static String project = "";

	public static Connection getConnection(JDBCConnectionConfiguration cc)
			throws Exception {
		String driver = cc.getDriverClass();// "oracle.jdbc.driver.OracleDriver";/mysql
		String username = cc.getUserId();// "username";
		String password = cc.getPassword();// "pass";

		Class.forName(driver);
		Connection conn = DriverManager.getConnection(cc.getConnectionURL(),
				username, password);
		return conn;
	}

	public abstract String getSQL(String table);

	/**
	 * 
	 * @Description: 设定mysql和oracle的通用性
	 * @return String 返回类型
	 * @throws
	 */
	private static String getNullableStr(String value) {
		if ("YES".equals(value)) {
			return "Y";
		} else if ("NO".equals(value)) {
			return "N";
		}
		return value;
	}

	public static void getColumnNames(ResultSet rs, FtlClassInfo fci)
			throws SQLException {
		while (rs.next()) {// table_name, column_name, comments
			String table_name = rs.getString("table_name").toUpperCase();
			String nullable = getNullableStr(rs.getString("NULLABLE"));
			String column_name = rs.getString("column_name").toLowerCase();
			String comments = rs.getString("comments");
			String type = rs.getString("data_type").toUpperCase();
			String tbcomments = rs.getString("tbcomments");
			// String primaryKey = rs.getString("PK").toLowerCase();
			String primaryKey = "primaryKey";// 抛弃了primaryKey方法
			// 添加count
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
			fi.setNullAble(nullable);// 添加是否为空属性 N 不允许 Y 允许

			if (comments != null) {
				comments.replace("（", "(");
				comments.replace("）", ")");
				comments.replace("，", ",");
				comments.replace("；", ";");
				comments.replace("：", ":");
				String str_tmp = StringUtils.substringBetween(comments, "(",
						")");
				if (str_tmp != null && str_tmp.split("[(|)|,|;]").length > 1) {
					String options[] = comments.split("[(|)|,|;]");
					if (options.length > 1) {
						fi.setComments(options[0]);
						if (options[1].startsWith("tt:")) {
							// is a ref
							fi.setHasTooltip(true);
							fi.setTttype(options[1].split(":")[1]);
							System.out.println("tooltip:" + fi.getTttype());

						} else if (options[1].equalsIgnoreCase("ref")) {
							// is a ref
							fi.setRef(true);
							fi.setRefPath(options[2]);
							fi.setRefKeyColumn(options[3]);
							fi.setRefLabelColumn(options[4]);
							fi.setRefKeyField(FieldHelper
									.column2Field(options[3]));
							fi.setRefLabelField(FieldHelper
									.column2Field(options[4]));

						} else {
							StringBuffer buff = new StringBuffer("{");
							fi.setHasOption(true);
							for (String op : options) {
								String kv[] = op.split(":");
								if (kv.length == 2) {
									try {
										fi.getOptions().add(
												new Options(Integer
														.parseInt(kv[0]
																.replaceAll(
																		"－",
																		"-")),
														kv[1]));
										buff.append("\"" + kv[0] + "\":\""
												+ kv[1] + "\",");
									} catch (Exception e) {
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
			root.put("count", clazzinfo.getCount());// TODO
			root.put("primaryKey", clazzinfo.getPrimaryKey());
			root.put("domainClazz", clazzinfo.getDomainClass());
			root.put("domainObject", clazzinfo.getDomainObject());
			root.put("module", clazzinfo.getModule());
			root.put(
					"packageName",
					clazzinfo.getPackageName().replaceAll(
							"\\" + File.separator, "."));
			File dst = new File(clazzinfo.getTargetProject() + File.separator
					+ clazzinfo.getPackageName() + File.separator
					+ subpack.replaceAll("\\.", "\\" + File.separator)
					+ File.separator + clazzinfo.getDomainClass() + endx
					+ ".java");
			dst.getParentFile().mkdirs();
			String[] path = { "com.joyveb.common.gens.bizcom.bean",
					"com.joyveb.common.gens.bizcom.vo",
					"com.joyveb.common.gens.spring.bean",
					"com.joyveb.common.gens.spring.util" };
			for (int i = 0; i < 4; i++) {
				File tmp = new File(clazzinfo.getTargetProject()
						+ File.separator
						+ path[i].replaceAll("\\.", "\\" + File.separator));
				tmp.mkdirs();
			}
			FileOutputStream fout = new FileOutputStream(dst);
			/* Merge data-model with template */
			Writer out = new OutputStreamWriter(fout);
			temp.process(root, out);
			out.flush();
			fout.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void genPage(FtlClassInfo clazzinfo, String menu) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);
			Template temp = cfg.getTemplate("list.ftl");// TODO 选择模版
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("project", clazzinfo.getProject());
			root.put("clazzinfo", clazzinfo);
			root.put("domainObject", clazzinfo.getDomainObject());
			root.put("module", clazzinfo.getModule());
			root.put(
					"packageName",
					clazzinfo.getPackageName().replaceAll(
							"\\" + File.separator, "."));
			root.put("menuName", menu);
			File dst1 = new File(clazzinfo.getTargetProject() + File.separator);
			File dst = new File(dst1.getParent() + File.separator + "pages"
					+ File.separator + "rest" + File.separator
					+ clazzinfo.getModule() + File.separator
					+ clazzinfo.getDomainObject() + ".html");
			dst.getParentFile().mkdirs();
			FileOutputStream fout = new FileOutputStream(dst);
			Writer out = new OutputStreamWriter(fout);
			temp.process(root, out);
			out.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void genEnums(FtlClassInfo clazzinfo, String subpack) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			cfg.setDirectoryForTemplateLoading(new File("src/main/resource"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);
			Template temp = cfg.getTemplate("ColumnType.ftl");
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
							.replaceAll("\\" + File.separator, ".")
							+ "."
							+ subpack);
					File dst = new File(clazzinfo.getTargetProject()
							+ File.separator
							+ clazzinfo.getPackageName().replaceAll("\\.",
									File.separator) + File.separator
							+ subpack.replaceAll("\\.", File.separator)
							+ File.separator + enumClass + ".java");
					dst.getParentFile().mkdirs();
					FileOutputStream fout = new FileOutputStream(dst);
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

	public void generator() {
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
				Configuration config = cp
						.parseConfiguration(Generator_base.class
								.getResourceAsStream(cfg));
				DefaultShellCallback callback = new DefaultShellCallback(
						overwrite);
				MyBatisGenerator myBatisGenerator = new MyBatisGenerator(
						config, callback, warnings);
				Context ctx = config.getContext(contextid);//
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
				if (sqlmapFile.exists())
					for (String file : sqlmapFile.list(new FilenameFilter() {
						@Override
						public boolean accept(File arg0, String arg1) {
							return arg1.endsWith("SqlMap.xml");
						}
					})) {
						File ff = new File(sqlmapFile.getAbsolutePath()
								+ File.separator + file);
						ff.delete();
					}
				String packageName = ctx.getJavaModelGeneratorConfiguration()
						.getTargetPackage().replaceAll(".domain", "")
						.replace(".", File.separator);
				String packageBase = StringUtils.substringBeforeLast(
						packageName, File.separator);
				packageBase = packageBase + File.separator + "common";
				for (TableConfiguration tc : ctx.getTableConfigurations()) {
					String query = getSQL(tc.getTableName());

					// System.out.println("query::" + query);
					// create a statement
					stmt = conn.createStatement();
					// execute query and return result as a ResultSet
					rs = stmt.executeQuery(query);
					FtlClassInfo fci = new FtlClassInfo();
					fci.setProject(project);// TODO set PorjectName
					fci.setDomainClass(tc.getDomainObjectName());
					fci.setDomainObject(tc.getDomainObjectName().toLowerCase());
					fci.setTableName(tc.getTableName());
					fci.setModule(tc.getTableName().split("_")[1].toLowerCase());
					// get the column names from the ResultSet
					getColumnNames(rs, fci);
					fci.setTargetProject(targetProject);
					fci.setPackageName(packageName);
					classinfos.add(fci);
					// System.out.println(JsonUtil.bean2Json(fci));
					genFtl(fci, "ctrl", "Controller.ftl", "Ctrl");
					genFtl(fci, "service.iface", "ServiceIface.ftl", "Service");
					genFtl(fci, "service.impl", "ServiceImpl.ftl",
							"ServiceImpl");
					genEnums(fci, "enums");
					genPage(fci, moduleCHName.get(fci.getModule()));// TODO
					/*---------------------显示参数---------------------------------------*/
					boolean flag = true;
					if (flag) {
						// System.out.println(JSONObject.fromObject(fci));//显示参数
						flag = false;
					}
				}
				myBatisGenerator.generate(null);
				for (String warning : warnings) {
					// System.out.println(warning);
				}
				System.out.println("DONE");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (XMLParserException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			} catch (Throwable e) {
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
	}
}
