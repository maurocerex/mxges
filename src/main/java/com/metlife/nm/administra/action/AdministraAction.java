package com.metlife.nm.administra.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.cifras.service.CifrasService;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.NmActionSupport;
import com.metlife.nm.domain.UserVO;

@Results( { @Result(name = "input", location = "administra", type = "tiles") })
public class AdministraAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = -3475184029263706432L;

    private static final Logger log = Logger.getLogger(AdministraAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> listaProcesos;
    private List<CatalogoVO> medio;
        
    @Override
    public String getPladActionId() {
        return "administra";
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String input() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("input Usuarios...");
        }
        
        String verify = super.verificar(this.getPladActionId(), (UserVO) session.get(ConstantesMxges.USER));
        if (verify != null) {
            return verify;
        }
        return INPUT;
    }

	public List<CatalogoVO> getListaProcesos() {
		return listaProcesos;
	}

	public List<CatalogoVO> getMedio() {
		return medio;
	}
        
}
