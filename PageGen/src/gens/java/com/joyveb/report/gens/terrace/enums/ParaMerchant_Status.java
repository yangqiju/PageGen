package com.joyveb.report.gens.terrace.enums;

public enum ParaMerchant_Status {
		S_正常投注(1,"正常投注"),
		S_关闭投注(0,"关闭投注"),
	UKNOWN(999999, "未知");
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

	private ParaMerchant_Status(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

}
