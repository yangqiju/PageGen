package com.joyveb.mongo.genarator;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.joyveb.common.gens.bizcom.bean.FieldInfo;
import com.joyveb.common.gens.bizcom.bean.FtlClassInfo;

public final class MongoDbConfigReader {

	private final static MongoDbConfigReader instance = new MongoDbConfigReader();

	private MongoDbConfigReader() {
	};

	public static MongoDbConfigReader getInstance() {
		return instance;
	}

	public MongoDbContext read(String xmlLocation) throws DocumentException, SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		Document doc = this.getDoc("src\\mongo\\resources\\"+xmlLocation);
		return parseDoc(doc);
	}

	private MongoDbContext parseDoc(Document doc) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String projectName = doc.selectSingleNode(
				"/generatorConfiguration/context/@id").getText();
		String targetSource = doc.selectSingleNode(
				"/generatorConfiguration/context/targetSource").getText();
		String targetPackage = doc.selectSingleNode(
				"/generatorConfiguration/context/targetPackage").getText();
		XPath path = doc
				.createXPath("/generatorConfiguration/context/Tables/Table");
		@SuppressWarnings("unchecked")
		List<Element> games = path.selectNodes(doc);
		MongoDbContext context = new MongoDbContext();
		List<FtlClassInfo> tables = new ArrayList<FtlClassInfo>();
		for (Element el : games) {
			String tablename = el.element("name").getText();
			String domainObjectName = el.element("domainObjectName")
					.getText();
			List<FieldInfo> fields = new ArrayList<FieldInfo>();// 字段
			for (@SuppressWarnings("unchecked")
			Iterator<Element> iter = el.element("Fields").elementIterator(); iter
					.hasNext();) {
				Element element = (Element) iter.next();
				if (element == null)
					continue;
				// 获取属性和它的值
				FieldInfo field = new FieldInfo();
				for (@SuppressWarnings("rawtypes")
				Iterator attrs = element.attributeIterator(); attrs
						.hasNext();) {
					Attribute attr = (Attribute) attrs.next();
					if (attr == null)
						continue;
					String attrName = attr.getName();
					String attrValue = attr.getValue();
					
					String methodStr = "set"
							+ attrName.substring(0, 1).toUpperCase()
							+ attrName.substring(1);
					Method method = FieldInfo.class.getMethod(methodStr, String.class);
					method.invoke(field, attrValue);
				}
				fields.add(field);
			}
			FtlClassInfo table = new FtlClassInfo();
			table.setTableName(tablename);
			table.setDomainObject(domainObjectName);
			table.setFields(fields);
			tables.add(table);// 添加到tables
		}
		context.setProjectName(projectName);
		context.setTargetPackage(targetPackage);
		context.setTargetSource(targetSource);
		context.setTables(tables);
		return context;
	}

	private Document getDoc(String xmlLocation) throws DocumentException {
		SAXReader reader = new SAXReader();
		File file = new File(xmlLocation);
		System.out.println(file.getAbsolutePath());
		Document doc = reader.read(file);
		doc.normalize();
		return doc;
	}

//	public static void main(String[] args) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
//		MongoDbConfigReader reader = new MongoDbConfigReader();
//		try {
//			MongoDbContext context = reader.read("mongo-table.xml");
//			System.out.println(JsonUtil.bean2Json(context.getTables().get(0)).toString());
//			System.out.println(JsonUtil.bean2Json(context.getTables().get(1)).toString());
//		} catch (DocumentException e) {
//			e.printStackTrace();
//		}
//	}

}
