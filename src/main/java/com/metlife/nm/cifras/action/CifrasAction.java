package com.metlife.nm.cifras.action;

import java.util.ArrayList;
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

@Results( { @Result(name = "input", location = "cifras", type = "tiles") })
public class CifrasAction extends NmActionSupport implements SessionAware{

    
    /**
     * 
     */
    private static final long serialVersionUID = -3475184029263706432L;

    private static final Logger log = Logger.getLogger(CifrasAction.class);
    
    protected Map<String, Object> session;
    
    private List<CatalogoVO> listaProcesos;
    private List<CatalogoVO> medio;
    private List<CatalogoVO> listaTipoReporte;
    
    @Resource(name = BeanNames.CifrasService)
    protected CifrasService service;
    
    @Override
    public String getPladActionId() {
        return "cifras";
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
        //listaLob = service.getLobs();
        listaProcesos = service.getListadoProcesos();
        medio = service.getCatMedio();
        
        // cargamos tipo de reporte:
        listaTipoReporte = new ArrayList<CatalogoVO>();
        CatalogoVO reportPend = new CatalogoVO();
        reportPend.setKeyTxt(ConstantesMxges.TIPO_REPORTE_PENDIENTES);
        reportPend.setValue(ConstantesMxges.TIPO_REPORTE_PENDIENTES_DESC);
        listaTipoReporte.add(reportPend);
        
        CatalogoVO reportCC = new CatalogoVO();
        reportCC.setKeyTxt(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL);
        reportCC.setValue(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL_DESC);
        listaTipoReporte.add(reportCC);
        
        CatalogoVO reportSMS = new CatalogoVO();
        reportSMS.setKeyTxt(ConstantesMxges.TIPO_REPORTE_SMS);
        reportSMS.setValue(ConstantesMxges.TIPO_REPORTE_SMS_DESC);
        listaTipoReporte.add(reportSMS);
        
        if(log.isDebugEnabled()){
        	log.debug("Lista Procesos "+listaProcesos.size());
        }
    
        
        return INPUT;
    }

	public List<CatalogoVO> getListaProcesos() {
		return listaProcesos;
	}

	public List<CatalogoVO> getMedio() {
		return medio;
	}

	public List<CatalogoVO> getListaTipoReporte() {
		return listaTipoReporte;
	}
	
	
        
}
