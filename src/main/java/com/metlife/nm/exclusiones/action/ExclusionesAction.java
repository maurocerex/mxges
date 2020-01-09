package com.metlife.nm.exclusiones.action;

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
import com.metlife.nm.exclusiones.service.ExclusionesService;

@Results( { @Result(name = "input", location = "exclusiones", type = "tiles") })
public class ExclusionesAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = -3975553763483505434L;

    private static final Logger log = Logger.getLogger(ExclusionesAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> listaLob;
    private List<CatalogoVO> listaProcesos;
    
    
    private List<CatalogoVO> listaTipoEnvio;
    
   
    
    public List<CatalogoVO> getListaTipoEnvio() {
		return listaTipoEnvio;
	}

	public void setListaTipoEnvio(List<CatalogoVO> listaTipoEnvio) {
		this.listaTipoEnvio = listaTipoEnvio;
	}

	@Resource(name = BeanNames.ExclusionesService)
    protected ExclusionesService service;
    
    
    @Override
    public String getPladActionId() {
        return "exclusiones";
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
        
        listaTipoEnvio = new ArrayList<CatalogoVO>();
        
        CatalogoVO tipoEnvio= new CatalogoVO();

        tipoEnvio.setKeyTxt("EM");
        tipoEnvio.setValue("Email");
        listaTipoEnvio.add(tipoEnvio);
        tipoEnvio= new CatalogoVO();
        tipoEnvio.setKeyTxt("SM");
        tipoEnvio.setValue("SMS");
        listaTipoEnvio.add(tipoEnvio);
        
        listaLob = service.getLobs();
        listaProcesos = new ArrayList<CatalogoVO>();
        
        return INPUT;
    }

    public final List<CatalogoVO> getListaLob() {
        return listaLob;
    }

    public final List<CatalogoVO> getListaProcesos() {
        return listaProcesos;
    }

   
    
}
