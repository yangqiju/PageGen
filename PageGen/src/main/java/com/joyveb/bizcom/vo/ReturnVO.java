package com.joyveb.bizcom.vo;

import lombok.Data;

@Data
public class ReturnVO {
	private String description;
	private int retcode;
	private Object retObj;
	private boolean success;
	public final static ReturnVO SuccessVO = new ReturnVO("success", 0, null,true);
	public final static ReturnVO FailVO = new ReturnVO("failed", 0, null,false);


	public ReturnVO(String description, int retcode, Object retObj,boolean success) {
		super();
		this.description = description;
		this.retcode = retcode;
		this.retObj = retObj;
		this.success = success;
	}
	
	public ReturnVO(String description,boolean success) {
		super();
		this.description = description;
		this.retcode = 0;
		this.retObj = null;
		this.success = success;
	}

	public ReturnVO() {
		super();
	}

}
