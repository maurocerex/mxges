package com.metlife.nm.usuarios.action;

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
import com.metlife.nm.usuarios.service.UsuariosService;

@Results( { @Result(name = "input", location = "usuarios", type = "tiles") })
public class UsuariosAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = 7827513005592727263L;
    
    private static final Logger log = Logger.getLogger(UsuariosAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> roles;
    private List<CatalogoVO> lobs;
    
    @Resource(name = BeanNames.UsuariosService)
    protected UsuariosService service;
    
    @Override
    public String getPladActionId() {
        return "usuarios";
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
        // obtenemos los roles existentes
        roles = service.getRoles();
        lobs = service.getLobs();
        return INPUT;
    }
    
    
    public List<CatalogoVO> getRoles() {
        return roles;
    }
    
    public List<CatalogoVO> getLobs() {
        return lobs;
    }  
}
