package com.metlife.nm.mensajes.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.NmActionSupport;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.service.MensajesService;

@Results( { @Result(name = "input", location = "mensajes", type = "tiles") })
public class MensajesAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = -3975553763483505434L;

    private static final Logger log = Logger.getLogger(MensajesAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> listaLob;
    private List<CatalogoVO> listaProcesos;
    private List<CatalogoVO> medio;
    private List<CatalogoVO> status;
    
    @Resource(name = BeanNames.MensajesService)
    protected MensajesService service;
    
    
    @Override
    public String getPladActionId() {
        return "mensajes";
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String input() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("input MensajesAction...");
        }
        
        String verify = super.verificar(this.getPladActionId(), (UserVO) session.get(ConstantesMxges.USER));
        if (verify != null) {
            return verify;
        }
        // obtenemos los roles existentes
        listaLob = service.getLobs();
        medio = service.getCatEnvio();
        status = service.getCatStatus();
        listaProcesos = new ArrayList<CatalogoVO>();
        return INPUT;
    }

    public List<CatalogoVO> getListaLob() {
        return listaLob;
    }

    public List<CatalogoVO> getListaProcesos() {
        return listaProcesos;
    }

	public List<CatalogoVO> getMedio() {
		return medio;
	}

	public List<CatalogoVO> getStatus() {
		return status;
	}
    
}
