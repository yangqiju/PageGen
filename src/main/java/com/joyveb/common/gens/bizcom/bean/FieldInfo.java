package com.joyveb.common.gens.bizcom.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FieldInfo {

	String name;
	String column;
	String type;
	String comments="";
	boolean hasOption=false;
	String optionString;
	
	boolean hasTooltip=false;
	String tttype;

	List<Options> options=new ArrayList<Options>();

	boolean isRef=false; 
	String refLabelColumn;
	String refKeyColumn;
	String refPath;

	String refLabelField;
	String refKeyField;
	
	String nullAble;//添加是否为空字段

	
}
