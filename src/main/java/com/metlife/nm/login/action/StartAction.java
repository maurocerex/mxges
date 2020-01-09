package com.metlife.nm.login.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@Results( { @Result(name = "success", location = "start", type = "tiles") })
public class StartAction extends ActionSupport implements SessionAware, RequestAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2793675175711260339L;
	private static final Logger log = Logger.getLogger(StartAction.class);

	protected Map<String, Object> session;

	@SuppressWarnings("unchecked")
	private Map request;

	public String execute() throws Exception {
		if (log.isInfoEnabled()) {
			log.info("execute...");
		}

		if (log.isDebugEnabled()) {
			log.debug("sending start...");
		}

		return SUCCESS;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public void setRequest(Map request) {
		this.request = request;
	}

	@SuppressWarnings("unchecked")
	public final Map getRequest() {
		return request;
	}

	public final Map<String, Object> getSession() {
		return session;
	}

	
	public final boolean showPanel(){
		return true;
	}
	
}
