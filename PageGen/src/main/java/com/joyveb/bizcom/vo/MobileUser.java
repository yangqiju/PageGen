package com.joyveb.bizcom.vo;

import java.io.Serializable;

public class MobileUser implements Serializable{

	private String custloginid;
	private String email;

	private String custareas;

	private String clientos;

	private String clientdevice;

	private String appvers;

	private String provider;
	
	private String mobilenumber;//移动号码
	private String deviceid;//设备好
	private String context;//内容
	private String buno;
	
	private int rechargebalance;//充值余额
	
	private int returnbalance;//赠送余额
	
	private String passwd;
	
	private boolean rememberme=true;
	private String sessionid;

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}


	public String getCustloginid() {
		return custloginid;
	}

	public void setCustloginid(String custloginid) {
		this.custloginid = custloginid;
	}

	public String getCustareas() {
		return custareas;
	}

	public void setCustareas(String custareas) {
		this.custareas = custareas;
	}

	public String getClientos() {
		return clientos;
	}

	public void setClientos(String clientos) {
		this.clientos = clientos;
	}

	public String getClientdevice() {
		return clientdevice;
	}

	public void setClientdevice(String clientdevice) {
		this.clientdevice = clientdevice;
	}

	public String getAppvers() {
		return appvers;
	}

	public void setAppvers(String appvers) {
		this.appvers = appvers;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}


	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public boolean isRememberme() {
		return rememberme;
	}

	public void setRememberme(boolean rememberme) {
		this.rememberme = rememberme;
	}

	public int getRechargebalance() {
		return rechargebalance;
	}

	public void setRechargebalance(int rechargebalance) {
		this.rechargebalance = rechargebalance;
	}

	public int getReturnbalance() {
		return returnbalance;
	}

	public void setReturnbalance(int returnbalance) {
		this.returnbalance = returnbalance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	
	boolean isLogined(){
		return this.sessionid!=null;
	}

	
}
