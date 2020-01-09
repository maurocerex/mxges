package com.metlife.nm.notificaciones.action;

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
import com.metlife.nm.notificaciones.service.NotificacionesService;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

@Results( { @Result(name = "input", location = "notificaciones", type = "tiles") })
public class NotificacionesAction extends NmActionSupport implements SessionAware{

    /**
     * 
     */
    private static final long serialVersionUID = 7920463780486247856L;

    private static final Logger log = Logger.getLogger(NotificacionesAction.class);
    
 protected Map<String, Object> session;
    
    private List<CatalogoVO> listaLob;
    private List<CatalogoVO> listaProcesos;
    private List<CatalogoVO> listaMensajes;
    private List<CatalogoVO> status;
    private List<CatalogoVO> medio;
    private List<CatalogoVO> canalEnvioList;
    private NotificacionVO notificacion;
    
    
    @Resource(name = BeanNames.NotificacionesService)
    protected NotificacionesService service;
    
    
    @Override
    public String getPladActionId() {
        
        return "notificaciones";
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
        status = service.getCatStatus();
        medio = service.getCatMedio();
        canalEnvioList = service.getCatCanal();
        listaMensajes = new ArrayList<CatalogoVO>();
        listaProcesos = new ArrayList<CatalogoVO>();
        notificacion = new NotificacionVO();
        return INPUT;
    }
    
    public List<CatalogoVO> getListaLob() {
        return listaLob;
    }

    public final List<CatalogoVO> getListaProcesos() {
        return listaProcesos;
    }

    public final Map<String, Object> getSession() {
        return session;
    }

    public final List<CatalogoVO> getListaMensajes() {
        return listaMensajes;
    }

    public final NotificacionVO getNotificacion() {
        return notificacion;
    }

	public List<CatalogoVO> getStatus() {
		return status;
	}

	public List<CatalogoVO> getMedio() {
		return medio;
	}
	
	public List<CatalogoVO> getCanalEnvioList() {
		return canalEnvioList;
	}
	
        
}
