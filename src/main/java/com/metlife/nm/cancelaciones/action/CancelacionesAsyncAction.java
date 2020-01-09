package com.metlife.nm.cancelaciones.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.cancelaciones.service.CancelacionesService;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.LabeValueBeanCascade;

public class CancelacionesAsyncAction extends GenericAsyncAction {

	/**
	 * Serializacion de la clase
	 */
	private static final long serialVersionUID = 7702656241761550686L;
	
	private static final Logger log = Logger.getLogger(CancelacionesAsyncAction.class);
	
	@Resource(name = BeanNames.CancelacionesService)
    protected CancelacionesService service;
	
	private String retenedor;
	private String retenedorCT;
	private String unidadPago;
	private String unidadPagoCT;
	private String fechaInicio;
	private String fechaFin;
	
	
	
	
	public String busqueda() throws Exception {
		if (log.isDebugEnabled()) {
            log.debug("M�todo busqueda");
        }
		
		JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        
        List<CatalogoVO> resultPayUnit = new ArrayList<CatalogoVO>();
        CatalogoVO resultDescriptRetainer = new CatalogoVO();
        try{
        	JSONArray selectPayUnit = new JSONArray();
        	resultDescriptRetainer = service.getRetainers(retenedor);
        	resultPayUnit = service.getPayUnit(retenedor);
        	
        	if(!resultPayUnit.isEmpty() && resultPayUnit != null){
        		
	        	for(CatalogoVO catVo:resultPayUnit){
	        		Map<Object, Object> map = new HashMap<Object, Object>();
	        		map.put("clave", catVo.getKeyTxt());
	        		map.put("valor", catVo.getValue());
	        		
	        		selectPayUnit.add(map);
	        	}
	        	
        	}else{
        		Map<Object, Object> map = new HashMap<Object, Object>();
        		map.put("clave", ConstantesMxges.PARAM_KEY_GENERIC);
        		map.put("valor", ConstantesMxges.PARAM_VALUE_GENERIC);
        		
        		selectPayUnit.add(map);
        	}
        	messages.put("selectUnidadesPago", selectPayUnit);
        	messages.put("retainerDescripcion", resultDescriptRetainer.getValue());
        	messages.put("retainerkey", resultDescriptRetainer.getKeyTxt());
        }catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
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
	
	public String cancelaNot() throws Exception {
		if (log.isDebugEnabled()) {
            log.debug("M�todo cancelaNotif");
            log.debug("retenedor: " + retenedor);
            log.debug("retenedor CT: " + retenedorCT);
            log.debug("unidad de pago: " + unidadPago);
            log.debug("unidad de pago CT: " + unidadPagoCT);
            log.debug("fecha inicial: " + fechaInicio);
            log.debug("fecha final: " + fechaFin);
        }
		
		JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        
        String afectados = "";
        try{
        	afectados = service.getCancelaNotif(retenedor, retenedorCT, unidadPago, unidadPagoCT, fechaInicio, fechaFin);        	
        	messages.put("canceladas", afectados);
        }catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
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
	
	public String getRetenedor() {
		return retenedor;
	}

	public void setRetenedor(String retenedor) {
		this.retenedor = retenedor;
	}

	public String getRetenedorCT() {
		return retenedorCT;
	}

	public void setRetenedorCT(String retenedorCT) {
		this.retenedorCT = retenedorCT;
	}

	public String getUnidadPago() {
		return unidadPago;
	}

	public void setUnidadPago(String unidadPago) {
		this.unidadPago = unidadPago;
	}

	public String getUnidadPagoCT() {
		return unidadPagoCT;
	}

	public void setUnidadPagoCT(String unidadPagoCT) {
		this.unidadPagoCT = unidadPagoCT;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
}
