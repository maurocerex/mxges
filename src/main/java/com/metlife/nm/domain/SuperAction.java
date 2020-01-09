package com.metlife.nm.domain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@Results({ @Result(name = "stream", type = "stream", params = { "contentType", "text/html", "inputName", "inputStream" }) })
public class SuperAction extends ActionSupport implements SessionAware, RequestAware {

	// TODO: Hacer que el error en el jsp, cuando no hay base de datos se quite
	// en la siguiente ejecucion exitosa

	/**
	 * 
	 */
	private static final long serialVersionUID = 2416274655666702545L;

	private static final Log log = LogFactory.getLog(SuperAction.class);

	protected Map<String, Object> session;

	protected InputStream inputStream;

	protected static final String JSON_SUCCESS = "success";
	protected static final String JSON_HASERRORS = "hasErrors";
	protected static final String JSON_ALERTS = "alerts";
	protected static final String JSON_DIVS = "divs";
	protected static final String JSON_MARKS = "marks";
	protected static final String JSON_UNMARKS = "unmarks";
	protected static final String JSON_VALUES = "values";
	protected static final String JSON_TABS = "tabs";
	protected static final String JSON_EXCEPTION = "exception";

	protected final String DIV = "div";
	protected final String NAME = "name";
	protected final String VALUE = "value";
	protected final String MSG = "msg";
	protected final String TAB = "tab";
	protected final String LEVEL = "lev";

	protected static final String STREAM = "stream";

	@SuppressWarnings("unchecked")
	protected Map request;

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public final InputStream getInputStream() {
		return inputStream;
	}

	public final void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	protected void streamIt(JSONObject messages) {
		try {
			inputStream = new ByteArrayInputStream(messages.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("streamIt", e);
			inputStream = new ByteArrayInputStream(messages.toString().getBytes());
		}
	}

	protected JSONObject getDivYMensaje(String div, String msg) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(DIV, div);
		map.put(MSG, msg);
		return JSONObject.fromObject(map);
	}

	protected String parseJsonString(JSONObject jsonObject, String fieldname) {
		if (jsonObject.get(fieldname) == null) {
			return null;
		} else {
			if (StringUtils.isBlank(jsonObject.get(fieldname).toString())) {
				return "";
			} else {
				return jsonObject.get(fieldname).toString();
			}
		}
	}

	protected String emptyIfIsBlank(String str) {
		if (StringUtils.isBlank(str)) {
			return "";
		} else {
			return str.trim();
		}
	}



	protected boolean parseJsonBoolean(JSONObject jsonObject, String fieldname) {
		if (jsonObject.get(fieldname) == null) {
			throw new RuntimeException(fieldname + " is null, expected boolean.");
		} else {
			try {
				return Boolean.parseBoolean(jsonObject.get(fieldname).toString());
			} catch (Exception e) {
				throw new RuntimeException(fieldname + " is " + jsonObject.get(fieldname).toString() + " expected boolean.");
			}
		}
	}

	protected int parseJsonInt(JSONObject jsonObject, String fieldname) {
		if (jsonObject.get(fieldname) == null) {
			throw new RuntimeException(fieldname + " is null, expected integer.");
		} else {
			try {
				return Integer.parseInt(jsonObject.get(fieldname).toString());
			} catch (NumberFormatException e) {
				throw new RuntimeException(fieldname + " is " + jsonObject.get(fieldname).toString() + " expected integer.");
			}
		}
	}

	protected int parseJsonInt(JSONObject jsonObject, String fieldname, int defaultValue) {
		if (jsonObject.get(fieldname) == null) {
			if (log.isDebugEnabled()) {
				log.debug(fieldname + " is null, expected integer. return " + defaultValue);
			}
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(jsonObject.get(fieldname).toString());
			} catch (NumberFormatException e) {
				if (log.isDebugEnabled()) {
					log.debug(fieldname + " is " + jsonObject.get(fieldname).toString() + " expected integer. return " + defaultValue);
				}
				return defaultValue;

			}
		}
	}

	protected long parseJsonLong(JSONObject jsonObject, String fieldname) {
		if (jsonObject.get(fieldname) == null) {
			throw new RuntimeException(fieldname + " is null, expected long.");
		} else {
			try {
				return Long.parseLong(jsonObject.get(fieldname).toString());
			} catch (NumberFormatException e) {
				throw new RuntimeException(fieldname + " is " + jsonObject.get(fieldname).toString() + " expected long.");
			}
		}
	}

	protected double parseJsonDouble(JSONObject jsonObject, String fieldname) {
		if (jsonObject.get(fieldname) == null) {
			throw new RuntimeException(fieldname + " is null, expected double.");
		} else {
			try {
				return Double.parseDouble(jsonObject.get(fieldname).toString());
			} catch (NumberFormatException e) {
				throw new RuntimeException(fieldname + " is " + jsonObject.get(fieldname).toString() + " expected double.");
			}
		}
	}

	protected boolean isValidTxt(String div, String key, String message, JSONArray divs) {
		if (StringUtils.isBlank(key)) {
			divs.add(getDivYMensaje(div, message));
			return false;
		} else {
			divs.add(getDivYMensaje(div, ""));
		}
		return true;
	}

	protected boolean isValidInteger(String div, String value, JSONArray divs, int minimo) {
		try {
			if (log.isDebugEnabled()) {
				log.debug("isValidInteger value => [ " + value + " ]");
			}
			if (Integer.parseInt(value) < minimo) {
				divs.add(getDivYMensaje(div, "* Indique un valor correcto"));
				return false;
			} else {
				divs.add(getDivYMensaje(div, ""));
				return true;
			}
		} catch (NumberFormatException e) {
			divs.add(getDivYMensaje(div, "* Indique un valor numerico"));
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public void setRequest(Map request) {
		this.request = request;
	}

	@SuppressWarnings("unchecked")
	public final Map getRequest() {
		return request;
	}

}
