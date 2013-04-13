package com.joyveb.util;

public enum ColumnType {
	UKNOWN(9999, "未知");
	int value;
	String desc;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private ColumnType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

}
