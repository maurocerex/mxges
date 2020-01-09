package com.metlife.nm.login.vo;

import java.io.Serializable;

public class LdapResponseVO implements Serializable{

	private static final long serialVersionUID = -6040351221805538009L;

	private boolean authenticated;
	private int passwordRetryCount;
	private String codeError;
	private String descError;
	
	public  String getCodeError() {
		return codeError;
	}
	public  void setCodeError(String codeError) {
		this.codeError = codeError;
	}
	public  String getDescError() {
		return descError;
	}
	public  void setDescError(String descError) {
		this.descError = descError;
	}
	public  boolean isAuthenticated() {
		return authenticated;
	}
	public  void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	public int getPasswordRetryCount() {
		return passwordRetryCount;
	}
	public void setPasswordRetryCount(int passwordRetryCount) {
		this.passwordRetryCount = passwordRetryCount;
	}
	
}
