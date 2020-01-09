package com.metlife.nm.reportes.action;

import java.util.ArrayList;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.reportes.service.ReportesService;
import com.metlife.nm.reportes.vo.ReporteVO;

public class ReportesAsyncAction extends GenericAsyncAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2991775107420472461L;

    private static final Logger log = Logger.getLogger(ReportesAsyncAction.class);

    private String dia_1;
    private String dia_2;
    private String dia_3;
    private String dia_4;
    private String detinatario;

    @Resource(name = BeanNames.ReportesService)
    private ReportesService service;

    static final String DIV_INVALIDO = "div.invalido";
    static final String DIV_CONSECUTIVO = "div.consecutivo";

    public String guardar() throws Exception {
        if (log.isDebugEnabled()) {
            log.info("guardarCambios...");
            log.info("detinatario => " + detinatario);
            log.info("dia_1 => " + dia_1);
            log.info("dia_2 => " + dia_2);
            log.info("dia_3 => " + dia_3);
            log.info("dia_4 => " + dia_4);

        }

        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        String msgError = null;
        try {
            int dia1 = 0, dia2 = 0, dia3 = 0, dia4 = 0;
            try {
                if (StringUtils.isNumeric(dia_1) && StringUtils.isNotBlank(dia_1)) {
                    dia1 = Integer.parseInt(dia_1);
                }
                if (StringUtils.isNumeric(dia_2) && StringUtils.isNotBlank(dia_2)) {
                    dia2 = Integer.parseInt(dia_2);
                }
                if (StringUtils.isNumeric(dia_3) && StringUtils.isNotBlank(dia_3)) {
                    dia3 = Integer.parseInt(dia_3);
                }
                if (StringUtils.isNumeric(dia_4) && StringUtils.isNotBlank(dia_4)) {
                    dia4 = Integer.parseInt(dia_4);
                }
            } catch (NumberFormatException e) {
                success = false;
            }

            boolean errorDia = false;
            msgError = "";
            if (dia1 > 31 | dia2 > 31 | dia3 > 31 | dia4 > 31) {
                errorDia = true;
                msgError = this.getText("reporte.dia.invalido");
            }
            ArrayList<Integer> lisdias = new ArrayList<Integer>();
            lisdias.add(dia1);
            lisdias.add(dia2);
            lisdias.add(dia3);
            lisdias.add(dia4);
            if (dia1 > 0 && dia2 > 0 && dia3 > 0 && dia4 > 0) {
                for (int t = 0; t < lisdias.size(); t++) {
                    for (int i = t + 1; i < lisdias.size(); i++) {
                        if (lisdias.get(t) == lisdias.get(i)) {
                            errorDia = true;
                            msgError = this.getText("No se permiten dias iguales.");
                        }
                    }
                }
            }
            divs.add(getDivYMensaje(DIV_INVALIDO, msgError));
            msgError = "";
            if (dia1 > dia2) {
                if (dia2 > 0) {
                    errorDia = true;
                    msgError = "El segundo d&iacute;a no puede ser menor al primer dia";
                }
            }
            divs.add(getDivYMensaje(DIV_CONSECUTIVO, msgError));
            if (dia2 > dia3) {
                if (dia3 > 0) {
                    log.debug(dia2 + " " + dia3);
                    errorDia = true;
                    msgError = "El segundo d&iacute;a no puede ser mayor al tercer dia";
                }
            }
            divs.add(getDivYMensaje(DIV_CONSECUTIVO, msgError));

            if (dia3 > dia4) {
                if (dia4 > 0) {
                    errorDia = true;
                    msgError = "El tercer d&iacute;a no puede ser mayor al cuarto dia";
                }
            }
            divs.add(getDivYMensaje(DIV_CONSECUTIVO, msgError));
            if (errorDia) {
                success = false;
            }

            if (success) {
                service.guardar(dia_1, dia_2, dia_3, dia_4, detinatario);
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

    public String getReporte() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("getReporte...");
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            ReporteVO reporte = service.getDatosReporte();

            messages.put("idProceso", reporte.getIdProceso());
            messages.put("idLob", reporte.getIdLob());
            messages.put("idMensaje", "MSG_PROCCALIDAD_01");
            messages.put("idNotificacion", "NOT_PROCCALIDAD_01");

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

    public String getDia_1() {
        return dia_1;
    }

    public void setDia_1(String dia_1) {
        this.dia_1 = dia_1;
    }

    public String getDia_2() {
        return dia_2;
    }

    public void setDia_2(String dia_2) {
        this.dia_2 = dia_2;
    }

    public String getDia_3() {
        return dia_3;
    }

    public void setDia_3(String dia_3) {
        this.dia_3 = dia_3;
    }

    public String getDia_4() {
        return dia_4;
    }

    public void setDia_4(String dia_4) {
        this.dia_4 = dia_4;
    }

    public String getDetinatario() {
        return detinatario;
    }

    public void setDetinatario(String detinatario) {
        this.detinatario = detinatario;
    }

}
