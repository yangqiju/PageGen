package com.joyveb.common.gens.bizcom.vo;

import lombok.Data;

@Data
public class PushVO {
	Aps aps;
	String activeid;
	String productid;
	int ftype;
	@Data
	public static class Aps{
		String alert;
		int badge;
		String sound;
		public Aps(String alert) {
			super();
			this.alert = alert;
			badge=1;
			sound="bingbong.aiff";
		}
		
	}
}
