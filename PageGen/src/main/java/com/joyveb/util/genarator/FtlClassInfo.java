package com.joyveb.util.genarator;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FtlClassInfo {
	String project;
	String domainObject;
	String domainClass;
	String module;
	String targetProject;
	String tableName;
	String packageName;
	String tablecomments="";
	String primaryKey;
	String primaryKeyMethod;
	Integer count;
	List<FieldInfo> fields = new ArrayList<FieldInfo>();

}
