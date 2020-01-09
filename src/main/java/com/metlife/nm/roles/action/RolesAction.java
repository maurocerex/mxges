package com.metlife.nm.roles.action;

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

@Results( { @Result(name = "input", location = "roles", type = "tiles") })
public class RolesAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = -1567915141291333309L;

    private static final Logger log = Logger.getLogger(RolesAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> roles;
    
    
    @Resource(name = BeanNames.UsuariosService)
    protected UsuariosService service;
    
    
    @Override
    public String getPladActionId() {
        return "roles";
    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @Override
    public String input() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("input Roles...");
        }
        
        String verify = super.verificar(this.getPladActionId(), (UserVO) session.get(ConstantesMxges.USER));
        if (verify != null) {
            return verify;
        }
        // obtenemos los roles existentes
        roles = service.getRoles();
        
        return INPUT;
    }

    public List<CatalogoVO> getRoles() {
        return roles;
    }
        
}
