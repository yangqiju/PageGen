package com.joyveb.common.gens.bizcom.bean;

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
	String primaryKeyType;
	Integer count;
	List<FieldInfo> fields = new ArrayList<FieldInfo>();

}
