package com.joyveb.mongo.genarator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joyveb.common.gens.bizcom.bean.FieldInfo;
import com.joyveb.common.gens.bizcom.bean.FtlClassInfo;
import com.joyveb.util.FieldHelper;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Generator {
	public static void main(String[] args) {
		List<FtlClassInfo> classinfos = new ArrayList<FtlClassInfo>();
		try {
			MongoDbConfigReader mongoReader = MongoDbConfigReader.getInstance();
			MongoDbContext context = mongoReader.read("mongo-table.xml");
			String packageName = context.getTargetPackage().replace(".", "\\");
			String targetProject = context.getTargetSource();
			for (int i = 0; i < context.getTables().size(); i++) {
				FtlClassInfo table = context.getTables().get(i);
				FtlClassInfo fci = new FtlClassInfo();
				fci.setDomainClass(table.getDomainObject());
				fci.setDomainObject(table.getDomainObject());
				fci.setTableName(table.getTableName());
				fci.setFields(table.getFields());
				getColumnNames2(table.getTableName(), fci);
				fci.setTargetProject(targetProject);
				fci.setPackageName(packageName);
				classinfos.add(fci);
				System.out.println(packageName + "---------" + targetProject);
				genFtl(fci, "dao.impl", "MongoDaoImpl.ftl", "DAOImpl");
				genFtl(fci, "dao", "MongoDao.ftl", "DAO");
				genFtl(fci, "domain", "MongoExample.ftl", "Example");
				genBean(fci, "domain", packageName, table.getTableName());
			}
			System.out.println("DONE");
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
		}
	}

	public static void genFtl(FtlClassInfo clazzinfo, String subpack,
			String tmplname, String endx) {
		try {
			freemarker.template.Configuration cfg = new freemarker.template.Configuration();
			cfg.setDirectoryForTemplateLoading(new File("src/mongo/resources"));
			DefaultObjectWrapper ow = new DefaultObjectWrapper();
			cfg.setObjectWrapper(ow);
			Template temp = cfg.getTemplate(tmplname);
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("clazzinfo", clazzinfo);
			root.put("domainClazz", clazzinfo.getDomainClass());
			root.put("domainObject", clazzinfo.getDomainObject());
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

	public static void getColumnNames2(String tablename, FtlClassInfo fci)
			throws SQLException {
		List<FieldInfo> fieldInfos = fci.getFields();
		for (int i = 0; i < fieldInfos.size(); i++) {
			String table_name = tablename;
			String column_name = fieldInfos.get(i).getName().toLowerCase();
			String comments = null;
			String tbcomments = null;
			String name = FieldHelper.column2Field(column_name);
			FieldInfo fi = fieldInfos.get(i);
			if (comments == null) {
				comments = name;
			}
			fi.setColumn(fieldInfos.get(i).getName());
			fi.setType(fieldInfos.get(i).getType());
			fi.setComments(fristCharacterToUpcase(fieldInfos.get(i).getName()
					.toLowerCase()));
			fi.setName(name);
			fi.setColumn(column_name);
			fi.setHasOption(false);
			if (tbcomments == null) {
				tbcomments = table_name;
			}
			fci.setTablecomments(tbcomments);
			// fci.getFields().add(fi);
		}

	}

	public static void genBean(FtlClassInfo clazzinfo, String subpack,
			String packageName, String tablename) {
		List<FieldInfo> fields = clazzinfo.getFields();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("package " + packageName.replace("\\", ".") + "."
					+ subpack + ";\n\n");
			sb.append("import org.springframework.data.mongodb.core.mapping.Document;\n");
			sb.append("import org.springframework.data.annotation.Id;\n");
			sb.append("import lombok.Data;\n");
			sb.append("import java.util.Date;\n");
			sb.append("\n");
			sb.append("@Document(collection = \"" + tablename
					+ "\")\npublic @Data class " + clazzinfo.getDomainClass()
					+ "{\n\n");
			sb.append("@Id private String id;\n");
			for (int i = 0; i < fields.size(); i++) {
				sb.append("	private").append(" " + fields.get(i).getType())
						.append(" " + fields.get(i).getName()).append(";")
						.append("\n");
			}
			sb.append("	@Deprecated\n");
			sb.append("	public void setId(String id) {\n");
			sb.append("	this.id = id;\n");
			sb.append("	}\n");
			sb.append("}");
			File dst = new File(clazzinfo.getTargetProject() + File.separator
					+ clazzinfo.getPackageName() + File.separator
					+ subpack.replaceAll("\\.", "\\" + File.separator)
					+ File.separator + clazzinfo.getDomainClass() + ".java");
			dst.getParentFile().mkdirs();
			FileOutputStream fout = new FileOutputStream(dst);
			Writer out = new OutputStreamWriter(fout);
			out.write(sb.toString());
			out.flush();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String fristCharacterToUpcase(String str) {
		return str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
	}
}
