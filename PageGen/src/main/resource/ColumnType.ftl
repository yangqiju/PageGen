package ${packageName};

public enum ${enumClass} {
	<#list field.options as option>
		S_${option.label}(${option.value},"${option.label}"),
	</#list>
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

	private ${enumClass}(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

}
