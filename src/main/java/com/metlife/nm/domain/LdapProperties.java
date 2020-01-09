package com.metlife.nm.domain;

public class LdapProperties extends BaseObjectVO {

	private static final long serialVersionUID = -2647864999900830850L;

	private String active;
	private String errorConexServer;
	private String errorServerNotExist;
	private String errorServerNotExist1;
	private String errorOnServer;
	private String errorOnUser;
	private String invalidUser;
	private String invalidPassword;
	private String passwordExpired;
	private String userIdIslocked;
	private String userIdLocked;
	private String hierarchy;
	private String url;
	private String forgotPwdUrl;
	private String changePwdUrl;
	private String dataBaseSid;
	
	public boolean isLdapActive(){
		return Boolean.parseBoolean(active);
	}
	
	public  String getActive() {
		return active;
	}
	public  void setActive(String active) {
		this.active = active;
	}
	public  String getErrorConexServer() {
		return errorConexServer;
	}
	public  void setErrorConexServer(String errorConexServer) {
		this.errorConexServer = errorConexServer;
	}
	public  String getErrorServerNotExist() {
		return errorServerNotExist;
	}
	public  void setErrorServerNotExist(String errorServerNotExist) {
		this.errorServerNotExist = errorServerNotExist;
	}
	public  String getErrorServerNotExist1() {
		return errorServerNotExist1;
	}
	public  void setErrorServerNotExist1(String errorServerNotExist1) {
		this.errorServerNotExist1 = errorServerNotExist1;
	}
	public  String getErrorOnServer() {
		return errorOnServer;
	}
	public  void setErrorOnServer(String errorOnServer) {
		this.errorOnServer = errorOnServer;
	}
	public  String getErrorOnUser() {
		return errorOnUser;
	}
	public  void setErrorOnUser(String errorOnUser) {
		this.errorOnUser = errorOnUser;
	}
	public  String getInvalidUser() {
		return invalidUser;
	}
	public  void setInvalidUser(String invalidUser) {
		this.invalidUser = invalidUser;
	}
	public  String getInvalidPassword() {
		return invalidPassword;
	}
	public  void setInvalidPassword(String invalidPassword) {
		this.invalidPassword = invalidPassword;
	}
	public  String getPasswordExpired() {
		return passwordExpired;
	}
	public  void setPasswordExpired(String passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
	public  String getUserIdIslocked() {
		return userIdIslocked;
	}
	public  void setUserIdIslocked(String userIdIslocked) {
		this.userIdIslocked = userIdIslocked;
	}
	public  String getUserIdLocked() {
		return userIdLocked;
	}
	public  void setUserIdLocked(String userIdLocked) {
		this.userIdLocked = userIdLocked;
	}
	public  String getHierarchy() {
		return hierarchy;
	}
	public  void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}
	public  String getUrl() {
		return url;
	}
	public  void setUrl(String url) {
		this.url = url;
	}
	public  String getForgotPwdUrl() {
		return forgotPwdUrl;
	}
	public  void setForgotPwdUrl(String forgotPwdUrl) {
		this.forgotPwdUrl = forgotPwdUrl;
	}
	public  String getChangePwdUrl() {
		return changePwdUrl;
	}
	public  void setChangePwdUrl(String changePwdUrl) {
		this.changePwdUrl = changePwdUrl;
	}

	public String getDataBaseSid() {
		return dataBaseSid;
	}

	public void setDataBaseSid(String dataBaseSid) {
		this.dataBaseSid = dataBaseSid;
	}
	
	

}
