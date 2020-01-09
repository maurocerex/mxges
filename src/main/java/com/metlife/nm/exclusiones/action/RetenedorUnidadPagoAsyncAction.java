package com.metlife.nm.exclusiones.action;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;
import com.metlife.nm.domain.SuperAction;
import com.metlife.nm.exclusiones.dao.RetenedorUnidadPagoDao;
import com.metlife.nm.usuarios.action.UsuariosAsyncAction;

public class RetenedorUnidadPagoAsyncAction extends SuperAction{

	private static final Log log = LogFactory.getLog(UsuariosAsyncAction.class);
	
	@Autowired
	private RetenedorUnidadPagoDao dao;

	private String unidadPago;
	private String retenedor;
	private String term;
	
	public String getRetenedores(){
		boolean ById = false;
		List<LabeValueBeanCascade> result = new ArrayList<LabeValueBeanCascade>();
		
		
		if (log.isInfoEnabled()) {
			log.info("roles...");
		}

		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		boolean success = true;

		JSONArray jsonArray = new JSONArray();
		try {

			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();

			if(StringUtils.isNumeric(term))
				ById = true;
				
			result = dao.getRetenedores(ById, term);
			LabelValueBean uno = new LabelValueBean();
			
			
			
			for(LabeValueBeanCascade rol : result){
				jsonArray.add(rol);
			}
			
			resultSet.put("Result", mapResult);
			messages.put("ResultSet", resultSet);

		} catch (Exception e) {
			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());
		} finally {
			messages.put(JSON_DIVS, divs);
			messages.put(JSON_SUCCESS, success);
			if (log.isDebugEnabled()) {
				log.debug(messages.toString(1));
			}
			try {
				inputStream = new ByteArrayInputStream(jsonArray.toString().getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return STREAM;
		
	}
	
	public String getUnidadPago(){
		

		List<LabelValueBean> result = new ArrayList<LabelValueBean>();
		
		
		if (log.isInfoEnabled()) {
		}

		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		boolean success = true;

		try {

			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();

			result = dao.getUndadesPago(retenedor);
			
			for(LabelValueBean rol : result){
				Map<Object, Object> map = new HashMap<Object, Object>();

				map.put("label", rol.getLabel());
				map.put("value", rol.getValue());

				mapResult.add(map);

			}

			resultSet.put("Result", mapResult);
			messages.put("ResultSet", resultSet);

		} catch (Exception e) {
			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());
		} finally {
			messages.put(JSON_DIVS, divs);
			messages.put(JSON_SUCCESS, success);
			if (log.isDebugEnabled()) {
				log.debug(messages.toString(1));
			}
			streamIt(messages);
		}

		return STREAM;
		
	}

	public void setUnidadPago(String unidadPago) {
		this.unidadPago = unidadPago;
	}

	public void setRetenedor(String retenedor) {
		this.retenedor = retenedor;
	}

	public String getRetenedor() {
		return retenedor;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}
