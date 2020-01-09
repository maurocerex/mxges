package com.metlife.nm.exclusiones.action;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;
import com.metlife.nm.exclusiones.dao.RetenedorUnidadPagoDao;
import com.metlife.nm.exclusiones.service.ExclusionesService;

public class ExclusionesAsyncAction extends GenericAsyncAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8775789600151205473L;

    private static final Logger log = Logger.getLogger(ExclusionesAsyncAction.class);

    static final String DIV_PROCESO = "div.proceso";
    static final String DIV_LOB = "div.lob";
    static final String DIV_RETENEDOR = "div.retenedor";

    @Resource(name = BeanNames.ExclusionesService)
    private ExclusionesService service;

    private String lob;
    private String proceso;
    private String retenedor;
    private String tipoEnvio;
    private String medioEnvio;

	

	private String[] seleccionados;
    private String[] seleccionadosct;

   

	public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            List<CatalogoVO> usuarios = service.getListadoProcesos(lob);

            JSONArray selectconsulta = new JSONArray();

            for (CatalogoVO obj : usuarios) {
                if (log.isDebugEnabled()) {
                    log.debug("obj => " + obj.toString());
                }
                Map<Object, Object> map = new HashMap<Object, Object>();
                map.put("clave", obj.getKeyTxt());
                map.put("valor", obj.getValue());
                selectconsulta.add(map);
            }
            messages.put("select", selectconsulta);

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
            messages.put(JSON_DIVS, divs);
            messages.put(JSON_ALERTS, alerts);
            messages.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(messages.toString(1));
            }
            streamIt(messages);
        }
        return SUCCESS;
    }

    public String guardar() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("guardar...");
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            service.guardar(seleccionados, lob, proceso, medioEnvio);
            service.guardarCt(seleccionadosct, lob, proceso,medioEnvio);

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
            messages.put(JSON_DIVS, divs);
            messages.put(JSON_ALERTS, alerts);
            messages.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(messages.toString(1));
            }
            streamIt(messages);
        }
        return SUCCESS;
    }

    public String busqueda() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("busqueda...");
        }
        boolean ById = false;
        LabeValueBeanCascade result = new LabeValueBeanCascade();
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        String msgError = null;
        try {
            boolean errorProceso = false;

            msgError = "";
            if (isBlank(proceso)) {
                errorProceso = true;
                msgError = this.getText("exclusiones.proceso.blank");
            }
            if (errorProceso) {
                success = false;
            }

            divs.add(getDivYMensaje(DIV_PROCESO, msgError));

            boolean errorlob = false;

            msgError = "";
            if (isBlank(lob)) {
                errorlob = true;
                msgError = this.getText("exclusiones.lob.blank");
            }
            if (errorlob) {
                success = false;
            }

            divs.add(getDivYMensaje(DIV_LOB, msgError));

            if (success) {
                List<CatalogoVO> seleccionados = service.getSeleccionados(lob, proceso, tipoEnvio);
                List<CatalogoVO> seleccionadosCt = service.getSeleccionadosCT(lob, proceso, tipoEnvio);
                
                List<LabelValueBean> unidadesPago = new ArrayList<LabelValueBean>();
                List<LabelValueBean> unidadesPagoCt = new ArrayList<LabelValueBean>();

                if (StringUtils.isNumeric(retenedor)) {
                    ById = true;
                    unidadesPago = (ArrayList<LabelValueBean>) service.getUndadesPago(retenedor,proceso, tipoEnvio);
                    unidadesPagoCt=(ArrayList<LabelValueBean>) service.getUndadesPagoCt(retenedor,proceso, tipoEnvio);
                }
                
                
                result = service.getRetenedores(ById, retenedor);
                
                if ((retenedor.equals(null))||(retenedor.equals(""))){
                	result.setLabel("");
                	unidadesPago = (ArrayList<LabelValueBean>) service.getUndadesPago(proceso, tipoEnvio);
                    unidadesPagoCt=(ArrayList<LabelValueBean>) service.getUndadesPagoCt(proceso, tipoEnvio);
                }


                JSONArray selectconsulta = new JSONArray();
                JSONArray selectconsultaCt = new JSONArray();
                JSONArray selectRetUn = new JSONArray();
                JSONArray selectRetUnCt = new JSONArray();
                selectconsulta = new JSONArray();
                selectconsultaCt= new JSONArray();
                

                for (CatalogoVO obj : seleccionados) {
                    if (log.isDebugEnabled()) {
                        log.debug("obj => " + obj.toString());
                    }
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    map.put("clave", obj.getKeyTxt());
                    map.put("valor", obj.getValue());
                    selectconsulta.add(map);
                }

                for (LabelValueBean obj : unidadesPago) {
                    Map<Object, Object> map = new HashMap<Object, Object>();

                    map.put("clave", obj.getValue());
                    map.put("valor", obj.getLabel());

                    selectRetUn.add(map);
                }
                
                
                
                for (CatalogoVO obj : seleccionadosCt) {
                    if (log.isDebugEnabled()) {
                        log.debug("obj => " + obj.toString());
                    }
                    Map<Object, Object> map = new HashMap<Object, Object>();
                    map.put("clave", obj.getKeyTxt());
                    map.put("valor", obj.getValue());
                    selectconsultaCt.add(map);
                }

                for (LabelValueBean obj : unidadesPagoCt) {
                    Map<Object, Object> map = new HashMap<Object, Object>();

                    map.put("clave", obj.getValue());
                    map.put("valor", obj.getLabel());

                    selectRetUnCt.add(map);
                }
                
                
                

                messages.put("selectSeleccionados", selectconsulta);
                messages.put("selectDisponibles", selectRetUn);
                messages.put("selectSeleccionadosCt", selectconsultaCt);
                messages.put("selectDisponiblesCt", selectRetUnCt);
                messages.put("descripcion", result.getLabel());
            }

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
            messages.put(JSON_DIVS, divs);
            messages.put(JSON_ALERTS, alerts);
            messages.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(messages.toString(1));
            }
            streamIt(messages);
        }
        return SUCCESS;
    }

    public final String getLob() {
        return lob;
    }

    public final void setLob(String lob) {
        this.lob = lob;
    }

    public final String getProceso() {
        return proceso;
    }

    public final void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public final String getRetenedor() {
        return retenedor;
    }

    public final void setRetenedor(String retenedor) {
        this.retenedor = retenedor;
    }

    public String[] getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(String[] seleccionados) {
        this.seleccionados = seleccionados;
    }
    
    public String[] getSeleccionadosct() {
		return seleccionadosct;
	}

	public void setSeleccionadosct(String[] seleccionadosct) {
		this.seleccionadosct = seleccionadosct;
	}
	
	public String getTipoEnvio() {
		return tipoEnvio;
	}

	public void setTipoEnvio(String tipoEnvio) {
		this.tipoEnvio = tipoEnvio;
	}
	
	public String getMedioEnvio() {
		return medioEnvio;
	}

	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
}
