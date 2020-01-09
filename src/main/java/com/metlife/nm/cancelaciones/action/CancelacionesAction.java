package com.metlife.nm.cancelaciones.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.cancelaciones.service.CancelacionesService;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.NmActionSupport;
import com.metlife.nm.domain.UserVO;

@Results( { @Result(name = "input", location = "cancelaciones", type = "tiles") })
public class CancelacionesAction extends NmActionSupport implements
		SessionAware {


	/**
	 * Propiedad de serializaci�n de la clase
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Propiedad que define el la escritura del log
	 */
	private static final Logger log = Logger.getLogger(CancelacionesAction.class); 

	/**
	 * Propiedad para la session
	 */
	protected Map<String, Object> session;
	
	/**
	 * Atributo para retornar a la vista los retenedores
	 */
	private List<CatalogoVO> listaRetainer;
	
	@Resource(name = BeanNames.CancelacionesService)
    protected CancelacionesService service;
	
	/**
	 * M�todo setter de la session
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;

	}

	@Override
	public String getPladActionId() {
		return "cancelaciones";
	}
	
	@Override
    public String input() throws Exception {
		if (log.isInfoEnabled()) {
            log.info("input CancelacionesAction...");
        }
		String verify = super.verificar(this.getPladActionId(), (UserVO) session.get(ConstantesMxges.USER));
        if (verify != null) {
            return verify;
        }
		return INPUT;
	}

	public List<CatalogoVO> getListaRetainer() {
		return listaRetainer;
	}

	public void setListaRetainer(List<CatalogoVO> listaRetainer) {
		this.listaRetainer = listaRetainer;
	}
	

}
