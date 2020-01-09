package com.metlife.nm.mensajes.action;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.domain.BeanNames;
import static com.metlife.nm.domain.ConstantesMxges.TURN_ON;
import static com.metlife.nm.domain.ConstantesMxges.TURN_OFF;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.mensajes.dao.MensajesDao;

public class SmsConfigAsyncAction extends GenericAsyncAction {

	/**
     * 
     */
	private static final long serialVersionUID = -7668387610631518350L;
	private static final Logger log = Logger.getLogger(SmsConfigAsyncAction.class);

	private boolean acento;

	@Resource(name = BeanNames.MensajesDao)
	protected MensajesDao msjDao;

	public String guardar() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("execute...");
		}
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;
		try {
			log.debug("Actualizando propiedad REM_ACENTO a: [" + acento + "]");
			
			if (acento) {
				msjDao.updateAccentProperty(TURN_ON);
			} else {
				msjDao.updateAccentProperty(TURN_OFF);
			}
			
		} catch (Exception e) {
			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());
		} finally {
			messages.put("estatus", success);
			messages.put(JSON_DIVS, divs);
			messages.put(JSON_ALERTS, alerts);
			messages.put(JSON_SUCCESS, success);
			if (log.isDebugEnabled()) {
				log.debug(messages.toString(1));
			}
			streamIt(messages);
		}
		return SUCCESS;
	}

	public boolean isAcento() {
		return acento;
	}

	public void setAcento(boolean acento) {
		this.acento = acento;
	}

}
