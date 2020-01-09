package com.metlife.nm.reportes.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.NmActionSupport;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.service.ReportesService;
import com.metlife.nm.reportes.vo.ReporteVO;

@Results({ @Result(name = "input", location = "reportes", type = "tiles") })
public class ReportesAction extends NmActionSupport implements SessionAware {

	/**
     * 
     */
	private static final long serialVersionUID = -5295044757491050845L;

	private static final Logger log = Logger.getLogger(ReportesAction.class);

	protected Map<String, Object> session;

	private List<CatalogoVO> destinatarios;
	private String destinatario;
	private String dia1;
	private String dia2;
	private String dia3;
	private String dia4;

	@Resource(name = BeanNames.ReportesService)
	private ReportesService service;

	@Override
	public String getPladActionId() {
		return "reportes";
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
		ArrayList<DiaVO> dias = (ArrayList<DiaVO>) service.getDiasReporteCalidad();
		if (log.isDebugEnabled()) {
			log.debug("size " + dias.size());
		}

		if(dias.size()==4){
			if(dias.get(0).getDiaEnvio()==null){
				dia1="";
			}else{
				if(StringUtils.isBlank(dias.get(0).getDiaEnvio()) || dias.get(0).getDiaEnvio().equals("0")){
					dia1="";
				}else{
					dia1 =dias.get(0).getDiaEnvio();
				}
			}
			if(dias.get(1).getDiaEnvio()==null){
				dia2="";
			}else{
				if(StringUtils.isBlank(dias.get(1).getDiaEnvio()) || dias.get(1).getDiaEnvio().equals("0")){
					dia2="";
				}else{
					dia2 =dias.get(1).getDiaEnvio();
				}
			}
			if(dias.get(2).getDiaEnvio()==null){
				dia3="";
			}else{
				if(StringUtils.isBlank(dias.get(2).getDiaEnvio()) || dias.get(2).getDiaEnvio().equals("0")){
					dia3="";
				}else{
					dia3 =dias.get(2).getDiaEnvio();
				}
			}
			if(dias.get(3).getDiaEnvio()==null){
				dia4="";
			}else{
				if(StringUtils.isBlank(dias.get(3).getDiaEnvio()) || dias.get(3).getDiaEnvio().equals("0")){
					dia4="";
				}else{
					dia4 =dias.get(3).getDiaEnvio();
				}
			}
		}
		
		destinatario = service.getDestinatario().getDestinatario();
		destinatarios = service.getCatDestinatario();

		// obtenemos los roles existentes

		return INPUT;
	}

	public String getDia1() {
		return dia1;
	}

	public String getDia2() {
		return dia2;
	}

	public String getDia3() {
		return dia3;
	}

	public String getDia4() {
		return dia4;
	}

    public final List<CatalogoVO> getDestinatarios() {
        return destinatarios;
    }

    public final String getDestinatario() {
        return destinatario;
    }
}
