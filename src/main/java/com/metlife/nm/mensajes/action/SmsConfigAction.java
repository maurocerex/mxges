package com.metlife.nm.mensajes.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.NmActionSupport;
import com.metlife.nm.mensajes.dao.MensajesDao;

@Results({ @Result(name = "input", location = "smsConfig", type = "tiles") })
public class SmsConfigAction extends NmActionSupport implements SessionAware {

	/**
     * 
     */
	private static final long serialVersionUID = -3975553763483505434L;

	private static final Logger log = Logger.getLogger(SmsConfigAction.class);

	protected Map<String, Object> session;

	private boolean acento;

	@Resource(name = BeanNames.MensajesDao)
	protected MensajesDao msjDao;

	@Override
	public String getPladActionId() {
		return "smsConfig";
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public String input() throws Exception {
		if (log.isInfoEnabled()) {
			log.info("input SmsConfigAction...");
		}

		acento = msjDao.getAccentProperty();
		log.debug("Remover acentos: [" + acento + "]");
		return INPUT;
	}

	public boolean isAcento() {
		return acento;
	}

	public void setAcento(boolean acento) {
		this.acento = acento;
	}

}
