package com.joyveb.common.gens.bizcom.bean;

import lombok.Data;

@Data
public class Options {

	int value;
	String label;
	public Options(int value, String label) {
		super();
		this.value = value;
		this.label = label;
	}
	public Options() {
		super();
	}
	
}
