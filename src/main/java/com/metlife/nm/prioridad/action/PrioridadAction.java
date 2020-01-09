package com.metlife.nm.prioridad.action;



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
import com.metlife.nm.mensajes.action.MensajesAction;
import com.metlife.nm.prioridad.service.PrioridadService;

@Results( { @Result(name = "input", location = "prioridad", type = "tiles") })
public class PrioridadAction extends NmActionSupport implements SessionAware{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3067170904400136855L;

	private static final Logger log = Logger.getLogger(MensajesAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> listaLob;
    
    @Resource(name = BeanNames.PrioridadService)
    protected PrioridadService service;
    
    
    @Override
    public String getPladActionId() {
        
        return "prioridad";
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String input() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("input ...");
        }
        
        String verify = super.verificar(this.getPladActionId(), (UserVO) session.get(ConstantesMxges.USER));
        if (verify != null) {
            return verify;
        }
        // obtenemos los roles existentes
        listaLob = service.getLobs();
        
        return INPUT;
    }
    
    public List<CatalogoVO> getListaLob() {
        return listaLob;
    }
    
}
