package com.metlife.nm.notificaciones.action;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.service.NotificacionesService;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.notificaciones.vo.MensajeVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public class NotificacionesAsyncAction extends GenericAsyncAction {

    private static final long serialVersionUID = 3452243275559636736L;
    private static final Logger log = Logger.getLogger(NotificacionesAsyncAction.class);

    @Resource(name = BeanNames.NotificacionesService)
    private NotificacionesService service;

    private static final String MONDAY = "MON";
    private static final String TUESDAY = "TUE";
    private static final String WEDNESDAY = "WED";
    private static final String THURSDAY = "THU";
    private static final String FRIDAY = "FRI";
    private static final String SATURDAY = "SAT";
    private static final String SUNDAY = "SUN";
    private static final String CODE_AUX = "9";

    private static final String PROC_COBRBANC = "PROC_COBRBANC";
    private String lob;
    private String proceso;
    private String medio;
    private String canalEnvio;
    private String reintentable;
    private String idRespBancaria;
    private String status;
    private String idMensaje;
    private String idNotificacion;
    
    private NotificacionVO notificacion;
    private String jsonrecords;

    
        
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;

    private boolean dia;
    private int hora;
    private int min;

    
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

    
    public String buscaMensajes() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("buscaMensajes...");
            log.debug("lob => " + lob);
            log.debug("proceso => " + proceso);
            log.debug("medio => " + medio);
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            List<CatalogoVO> mensajes = service.getMensajes(lob, proceso, medio);

            JSONArray selectconsulta = new JSONArray();

            for (CatalogoVO obj : mensajes) {
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

    public String getMensaje() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("getMensaje...");
            log.debug("idMensaje => " + notificacion.getMensaje().getIdMensaje());
        }
        idMensaje = notificacion.getMensaje().getIdMensaje();
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            MensajeVO mensaje = service.getMensaje(idMensaje);

            messages.put("asunto", mensaje.getAsunto());
            messages.put("mensaje", mensaje.getMensaje());

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

    static final String DIV_ID_NOTIFICACION = "div.notificacion";
    static final String DIV_ID_LOB = "div.lob";
    static final String DIV_ID_PROCESO = "div.proceso";
    static final String DIV_ID_MENSAJE = "div.mensaje";
    static final String DIV_ID_DESCRIPCION = "div.descripcion";
    static final String DIV_ID_DIA = "div.dia";
    static final String DIV_RESPUESTA = "div.respuesta";
    static final String DIV_REINTENTABLE = "div.reintentable";
    static final String DIV_ENVIO = "div.envio";
    static final String DIV_MEDIO = "div.medio";
    static final String DIV_CANAL_ENVIO = "div.canalEnvio";

    public String validar() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("validar... " + dia);
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        String msgError = null;

        try {
            if (notificacion.isNuevo()) {
                /**
                 * Validar el username del usuario cuando sea nuevo
                 */
                boolean errorParti = false;

                if (log.isDebugEnabled()) {
                    log.debug("PROCESO DIV" + notificacion.getIdProceso());
                }

                boolean errorEnvio = false;

                msgError = "";
                boolean envioA = notificacion.isEnvioAgente();
                boolean envioB = notificacion.isEnvioCliente();
                if (!envioB && !envioA) {
                    errorEnvio = true;
                    msgError = this.getText("notificacion.envio.blank");
                }
                if (errorEnvio) {
                    success = false;
                }

                divs.add(getDivYMensaje(DIV_ENVIO, msgError));

                boolean errorIdNotificacion = false;

                msgError = "";
                if (isBlank(notificacion.getIdNotificacion())) {
                    errorIdNotificacion = true;
                    msgError = this.getText("notificacion.idnotificacion.blank");
                } else if (notificacion.getIdNotificacion().trim().length() > 40) {
                    errorIdNotificacion = true;
                    msgError = this.getText("notificacion.idnotificacion.longitud");
                }
                if (!StringUtils.isAlphanumericSpace(notificacion.getIdNotificacion())) {
                    errorIdNotificacion = true;
                    msgError = this.getText("notificacion.idnotificacion.valido");
                }
                if (errorIdNotificacion) {
                    success = false;
                }

                divs.add(getDivYMensaje(DIV_ID_NOTIFICACION, msgError));

                msgError = "";
                boolean errorIdLob = false;
                if (isBlank(notificacion.getIdLob())) {
                    errorIdLob = true;
                    msgError = this.getText("notificacion.idlob.blank");
                } else if (notificacion.getIdLob().trim().length() > 40) {
                    errorIdLob = true;
                    msgError = this.getText("notificacion.idlob.blank");
                }
                if (errorIdLob) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_ID_LOB, msgError));

                msgError = "";
                boolean errorIdProceso = false;
                if (isBlank(notificacion.getIdProceso())) {
                    errorIdProceso = true;
                    msgError = this.getText("notificacion.idproceso.blank");
                }
                if (errorIdProceso) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_ID_PROCESO, msgError));

                if (!errorIdProceso) {
                    msgError = "";
                    if (notificacion.getIdProceso().equals(PROC_COBRBANC)) {
                        if (isBlank(idRespBancaria)) {
                            errorParti = true;
                            msgError = this.getText("notificacion.respuesta.bancaria.blank");
                        }
                        if (errorParti) {
                            success = false;
                        }
                        divs.add(getDivYMensaje(DIV_RESPUESTA, msgError));
                        msgError = "";
                        if (idRespBancaria.equals("R")) {
                            if (isBlank(reintentable) || CODE_AUX.equals(reintentable)) {
                                errorParti = true;
                                msgError = this.getText("notificacion.reintentable.blank");
                            }
                            if (errorParti) {
                                success = false;
                            }
                        }
                        divs.add(getDivYMensaje(DIV_REINTENTABLE, msgError));
                    }
                }
                msgError = "";
                boolean errorIdMensaje = false;
                if (isBlank(notificacion.getMensaje().getIdMensaje())) {
                    errorIdMensaje = true;
                    msgError = this.getText("notificacion.idmensaje.blank");
                }
                if (errorIdMensaje) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_ID_MENSAJE, msgError));
                if(log.isDebugEnabled()){
                    log.debug("medio de envio "+ notificacion.getMedio());
                }
                msgError = "";
                boolean errormedio = false;
                if (isBlank(notificacion.getMedio())) {
                    errormedio = true;
                    msgError = this.getText("busqueda.medio.blank");
                }
                if (errormedio) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_MEDIO, msgError));
                divs.add(getDivYMensaje(DIV_CANAL_ENVIO, msgError));
            }

            msgError = "";
            boolean errorDescripcion = false;
            if (isBlank(notificacion.getNotificacionDesc())) {
                errorDescripcion = true;
                msgError = this.getText("notificacion.descripcion.blank");
            }
            if (!StringUtils.isAlphanumericSpace(notificacion.getNotificacionDesc())) {
                errorDescripcion = true;
                msgError = this.getText("notificacion.descripcion.valido");
            }
            if (errorDescripcion) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_ID_DESCRIPCION, msgError));

            msgError = "";
            boolean errorDia = false;
            if (!dia) {
                errorDia = true;
                msgError = this.getText("notificacion.dia.blank");
            }
            if (errorDia) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_ID_DIA, msgError));

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
        if (log.isInfoEnabled()) {
            log.info("guardarCambios...");
            log.info("Mensajes..." + jsonrecords);
        }

        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        try {
            List<NotificacionVO> registros = parseJson(jsonrecords);
            formatCanalEnvio(registros);
            UserVO user = (UserVO) session.get(ConstantesMxges.USER);
            if (log.isDebugEnabled()) {
                log.debug("que traes " + status);
            }
            service.guardar(registros, user);

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
    
    private void formatCanalEnvio (List<NotificacionVO> registros){
    	if(registros != null && !registros.isEmpty()){
    		
    		List<CatalogoVO> catalogoList = service.getCatCanal();
    		
	    	for(NotificacionVO vo: registros){
	    		log.debug("interacion de proceso: dato:"+ vo.getCanalEnvio());
	    		if(vo.getCanalEnvio() != null){
	    			for(CatalogoVO voF: catalogoList){
	    				if(vo.getCanalEnvio().equals(String.valueOf(voF.getValue()))){
	    					vo.setCanalEnvio(String.valueOf(voF.getKey()));
	    					log.debug("------> Se cambia antes:"+vo.getCanalEnvio()+" despues:" + voF.getKey());
	    				}
	    			}
	    		}
	    	}
    	}
    }

    @SuppressWarnings({ "unused", "rawtypes" })
    private List<NotificacionVO> parseJson(final String jsonString) throws RuntimeException {
        // try {

        List<NotificacionVO> registros = new ArrayList<NotificacionVO>();

        if (jsonString != null) {
            JSONArray jsonArray = JSONArray.fromObject(jsonString);
            if (log.isDebugEnabled()) {
                log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
            }
            boolean nuevo = false;
            boolean modificado = false;

            String lob = null;
            String idLob = null;
            String idProceso = null;
            String proceso = null;
            String medio = null;
            String canalEnvio = null;
            String destinatario = null;
            String status = null;
            String idNotificacion = null;
            String notificacionDesc = null;
            String idMensaje = null;
            String idMensajeAnt = null;
            String mensaje = null;

            String asunto = null;
            String idRespBancaria = null;
            String reintentable = "";
            boolean dxn = false;
            boolean cobraBanca = false;
            boolean pagoDirecto = false;
            boolean regLabBase = false;
            boolean regLabEventual = false;

            String recordId = null;
            boolean monday = false;
            int monHora, monMin;
            boolean tuesday = false;
            int tueHora, tueMin;
            boolean wednesday = false;
            int wedHora, wedMin;
            boolean thursday = false;
            int thuHora, thuMin;
            boolean friday = false;
            int friHora, friMin;
            boolean saturday = false;
            int satHora, satMin;
            boolean sunday = false;
            int sunHora, sunMin;

            NotificacionVO tmp = null;
            Iterator it = jsonArray.iterator();
            while (it.hasNext()) {
                JSONObject jsonObj = (JSONObject) it.next();

                // Es obligatorio que las banderas representen exactamente
                // un valor boolean.
                nuevo = parseJsonBoolean(jsonObj, "nuevo");
                modificado = parseJsonBoolean(jsonObj, "modificado");

                if (nuevo || modificado) {

                    recordId = parseJsonString(jsonObj, "recordId");
                    lob = parseJsonString(jsonObj, "lob");
                    idLob = parseJsonString(jsonObj, "idLob");
                    idProceso = parseJsonString(jsonObj, "idProceso");
                    proceso = parseJsonString(jsonObj, "proceso");
                    medio = parseJsonString(jsonObj, "medio");
                    canalEnvio= parseJsonString(jsonObj, "canalEnvio");
                    destinatario = parseJsonString(jsonObj, "destinatario");
                    status = parseJsonString(jsonObj, "status");
                    idNotificacion = parseJsonString(jsonObj, "idNotificacion");
                    notificacionDesc = parseJsonString(jsonObj, "notificacionDesc");
                    idMensaje = parseJsonString(jsonObj, "idMensaje");
                    idMensajeAnt = parseJsonString(jsonObj, "idMensajeAnt");

                    mensaje = parseJsonString(jsonObj, "mensaje");
                    // envioCliente = parseJsonBoolean(jsonObj,
                    // "envioCliente");
                    asunto = parseJsonString(jsonObj, "asunto");
                    idRespBancaria = parseJsonString(jsonObj, "idRespBancaria");
                    reintentable = parseJsonString(jsonObj, "reintentable");
                    dxn = parseJsonBoolean(jsonObj, "dxn");
                    cobraBanca = parseJsonBoolean(jsonObj, "cobraBanca");
                    pagoDirecto = parseJsonBoolean(jsonObj, "pagoDirecto");
                    regLabBase = parseJsonBoolean(jsonObj, "regLabBase");
                    regLabEventual = parseJsonBoolean(jsonObj, "regLabEventual");

                    ArrayList<FrecuenciaVO> frecs = new ArrayList<FrecuenciaVO>();

                    if (parseJsonBooleanDiaHora(jsonObj, "monday")) {
                        monHora = parseJsonIntDiaHora(jsonObj, "mondayhh");
                        monMin = parseJsonIntDiaHora(jsonObj, "mondaymm");
                        frecs.add(diaFrecuencia(MONDAY, monHora, monMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "tuesday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "tuesdayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "tuesdaymm");
                        frecs.add(diaFrecuencia(TUESDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "wednesday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "wednesdayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "wednesdaymm");
                        frecs.add(diaFrecuencia(WEDNESDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "thursday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "thursdayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "thursdaymm");
                        frecs.add(diaFrecuencia(THURSDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "friday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "fridayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "fridaymm");
                        frecs.add(diaFrecuencia(FRIDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "saturday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "saturdayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "saturdaymm");
                        frecs.add(diaFrecuencia(SATURDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }
                    if (parseJsonBooleanDiaHora(jsonObj, "sunday")) {
                        tueHora = parseJsonIntDiaHora(jsonObj, "sundayhh");
                        tueMin = parseJsonIntDiaHora(jsonObj, "sundaymm");
                        frecs.add(diaFrecuencia(SUNDAY, tueHora, tueMin, idNotificacion, idProceso, idLob, idMensaje));
                    }

                    tmp = new NotificacionVO();
                    MensajeVO mensajeVO = new MensajeVO();

                    tmp.setIdLob(idLob);
                    if (destinatario.equals("AM")) {
                        tmp.setEnvioCliente(true);
                        tmp.setEnvioAgente(true);
                    } else if (destinatario.equals("CL")) {
                        tmp.setEnvioCliente(true);
                        tmp.setEnvioAgente(false);
                    } else if (destinatario.equals("AG")) {
                        tmp.setEnvioCliente(false);
                        tmp.setEnvioAgente(true);
                    }
                    tmp.setIdProceso(idProceso);
                    tmp.setMedio(medio);
                    tmp.setCanalEnvio(canalEnvio);
                    tmp.setStatus(status);
                    tmp.setIdNotificacion(idNotificacion);
                    tmp.setNotificacionDesc(notificacionDesc);
                    tmp.setIdMensajeAnt(idMensajeAnt);
                    mensajeVO.setIdMensaje(idMensaje);
                    tmp.setMensaje(mensajeVO);
                    tmp.setIdRespBancaria(idRespBancaria);
                    tmp.setReintentable(reintentable);
                    tmp.setDxn(dxn);
                    tmp.setCobraBanca(cobraBanca);
                    tmp.setPagoDirecto(pagoDirecto);
                    tmp.setRegLabBase(regLabBase);
                    tmp.setRegLabEventual(regLabEventual);
                    tmp.setNuevo(nuevo);
                    tmp.setModificado(modificado);
                    tmp.setFrecuencia(frecs);
                    registros.add(tmp);
                }

            }
        }

        return registros;
    }

    static final String DIV_PROCESO = "div.proceso.bus";
    static final String DIV_LOB = "div.lob.bus";
    static final String DIV_STATUS = "div.status.bus";

    public String busqueda() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("busqueda...");
            log.debug("proceso -> " + proceso);
            log.debug("lob -> " + lob);
            log.debug("status -> " + status);
        }
        JSONObject messages = new JSONObject();
        JSONArray records = new JSONArray();
        JSONArray divs = new JSONArray();
        boolean success = true;
        @SuppressWarnings("unused")
		String msgError = null;
        try {
            if (success) {
                ArrayList<NotificacionVO> notificaciones = (ArrayList<NotificacionVO>) service.busqueda(proceso, lob, status);

                JSONObject map = new JSONObject();
                ArrayList<FrecuenciaVO> frecu = null;
                for (NotificacionVO tmp : notificaciones) {

                    map = new JSONObject();
                    frecu = (ArrayList<FrecuenciaVO>) tmp.getFrecuencia();

                    map.put("lob", tmp.getLob());
                    map.put("idLob", tmp.getIdLob());
                    map.put("idProceso", tmp.getIdProceso());
                    map.put("proceso", tmp.getProcesoDesc());
                    map.put("medio", tmp.getMedio());
                    map.put("canalEnvio", tmp.getCanalEnvio());

                    if (tmp.isEnvioAgente() && tmp.isEnvioCliente()) {
                        map.put("destinatario", "AM");
                    } else if (tmp.isEnvioAgente()) {
                        map.put("destinatario", "AG");
                    } else if (tmp.isEnvioCliente()) {
                        map.put("destinatario", "CL");
                    }
                    map.put("status", tmp.getStatus());
                    map.put("idNotificacion", tmp.getIdNotificacion());
                    map.put("notificacionDesc", tmp.getNotificacionDesc());
                    map.put("idMensaje", tmp.getMensaje().getIdMensaje());
                    map.put("idMensajeAnt", tmp.getMensaje().getIdMensaje());
                    map.put("asunto", tmp.getMensaje().getAsunto());
                    map.put("mensaje", tmp.getMensaje().getMensaje());
                    map.put("envioCliente", tmp.isEnvioCliente());
                    map.put("idRespBancaria", tmp.getIdRespBancaria());
                    map.put("reintentable", tmp.getReintentable());
                    map.put("dxn", tmp.isDxn());
                    map.put("cobraBanca", tmp.isCobraBanca());
                    map.put("pagoDirecto", tmp.isPagoDirecto());
                    map.put("regLabBase", tmp.isRegLabBase());
                    map.put("regLabEventual", tmp.isRegLabEventual());
                    map.put("envioAgente", tmp.isEnvioAgente());

                    for (FrecuenciaVO frec : frecu) {
                        if (frec.getIdDia().equals(MONDAY)) {
                            map.put("monday", true);
                            map.put("mondayhh", frec.getHora());
                            map.put("mondaymm", frec.getMinuto());
                        }
                        if (frec.getIdDia().equals(TUESDAY)) {
                            map.put("tuesday", true);
                            map.put("tuesdayhh", frec.getHora());
                            map.put("tuesdaymm", frec.getMinuto());

                        }
                        if (frec.getIdDia().equals(WEDNESDAY)) {
                            map.put("wednesday", true);
                            map.put("wednesdayhh", frec.getHora());
                            map.put("wednesdaymm", frec.getMinuto());

                        }
                        if (frec.getIdDia().equals(THURSDAY)) {
                            map.put("thursday", true);
                            map.put("thursdayhh", frec.getHora());
                            map.put("thursdaymm", frec.getMinuto());

                        }
                        if (frec.getIdDia().equals(FRIDAY)) {
                            map.put("friday", true);
                            map.put("fridayhh", frec.getHora());
                            map.put("fridaymm", frec.getMinuto());
                        }
                        if (frec.getIdDia().equals(SATURDAY)) {
                            map.put("saturday", true);
                            map.put("saturdayhh", frec.getHora());
                            map.put("saturdaymm", frec.getMinuto());
                        }
                        if (frec.getIdDia().equals(SUNDAY)) {
                            map.put("sunday", true);
                            map.put("sundayhh", frec.getHora());
                            map.put("sundaymm", frec.getMinuto());
                        }
                    }

                    map.put("nuevo", false);
                    map.put("modificado", false);

                    records.add(map);
                }
                messages.put("rows", records);
            }
        } catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
            messages.put(JSON_DIVS, divs);
            messages.put(JSON_SUCCESS, success);
            streamIt(messages);
        }
        return SUCCESS;
    }

    public String listadoNotificaciones() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");

        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {

            Map<Object, Object> resultSet = new HashMap<Object, Object>();
            List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();
            Map<Object, Object> map = null;
            ArrayList<FrecuenciaVO> frecu = null;
            for (NotificacionVO tmp : service.busqueda(null, null, null)) {

                if (log.isDebugEnabled()) {
                    log.debug("GRU" + tmp.toString());
                }
                map = new HashMap<Object, Object>();
                frecu = (ArrayList<FrecuenciaVO>) tmp.getFrecuencia();
                if (log.isDebugEnabled()) {
                    log.debug(frecu.toString());
                }
                map.put("lob", tmp.getLob());
                map.put("idLob", tmp.getIdLob());
                map.put("idProceso", tmp.getIdProceso());
                map.put("proceso", tmp.getProcesoDesc());
                map.put("medio", tmp.getMedio());
                map.put("canalEnvio", tmp.getCanalEnvio());
                if (tmp.isEnvioAgente() && tmp.isEnvioCliente()) {
                    map.put("destinatario", "AM");
                } else if (tmp.isEnvioAgente()) {
                    map.put("destinatario", "AG");
                } else if (tmp.isEnvioCliente()) {
                    map.put("destinatario", "CL");
                }

                map.put("status", tmp.getStatus());
                map.put("idNotificacion", tmp.getIdNotificacion());
                map.put("notificacionDesc", tmp.getNotificacionDesc());
                map.put("idMensaje", tmp.getMensaje().getIdMensaje());
                map.put("idMensajeAnt", tmp.getMensaje().getIdMensaje());
                map.put("asunto", tmp.getMensaje().getAsunto());
                map.put("mensaje", tmp.getMensaje().getMensaje());
                map.put("envioCliente", tmp.isEnvioCliente());
                map.put("idRespBancaria", tmp.getIdRespBancaria());
                map.put("reintentable", tmp.getReintentable());
                map.put("dxn", tmp.isDxn());
                map.put("cobraBanca", tmp.isCobraBanca());
                map.put("pagoDirecto", tmp.isPagoDirecto());
                map.put("regLabBase", tmp.isRegLabBase());
                map.put("regLabEventual", tmp.isRegLabEventual());
                map.put("envioAgente", tmp.isEnvioAgente());
                map.put("nuevo", false);
                map.put("modificado", false);

                for (FrecuenciaVO frec : frecu) {
                    if (frec.getIdDia().equals(MONDAY)) {
                        map.put("monday", true);
                        map.put("mondayhh", frec.getHora());
                        map.put("mondaymm", frec.getMinuto());

                    }
                    if (frec.getIdDia().equals(TUESDAY)) {
                        map.put("tuesday", true);
                        map.put("tuesdayhh", frec.getHora());
                        map.put("tuesdaymm", frec.getMinuto());

                    }
                    if (frec.getIdDia().equals(WEDNESDAY)) {
                        map.put("wednesday", true);
                        map.put("wednesdayhh", frec.getHora());
                        map.put("wednesdaymm", frec.getMinuto());

                    }
                    if (frec.getIdDia().equals(THURSDAY)) {
                        map.put("thursday", true);
                        map.put("thursdayhh", frec.getHora());
                        map.put("thursdaymm", frec.getMinuto());

                    }
                    if (frec.getIdDia().equals(FRIDAY)) {
                        map.put("friday", true);
                        map.put("fridayhh", frec.getHora());
                        map.put("fridaymm", frec.getMinuto());
                    }
                    if (frec.getIdDia().equals(SATURDAY)) {
                        map.put("saturday", true);
                        map.put("saturdayhh", frec.getHora());
                        map.put("saturdaymm", frec.getMinuto());
                    }
                    if (frec.getIdDia().equals(SUNDAY)) {
                        map.put("sunday", true);
                        map.put("sundayhh", frec.getHora());
                        map.put("sundaymm", frec.getMinuto());
                    }
                }

                mapResult.add(map);

            }
            resultSet.put("Result", mapResult);
            messages.put("ResultSet", resultSet);
            messages.put("ResultSet", resultSet);

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

    public String getReintentable() {
        return reintentable;
    }

    public String getIdRespBancaria() {
        return idRespBancaria;
    }

    public int getHora() {
        return hora;
    }

    public int getMin() {
        return min;
    }

    private FrecuenciaVO diaFrecuencia(String dia, int hora, int min, String idNotificacion, String idProceso, String idLob, String idMensaje) {
        FrecuenciaVO vo = new FrecuenciaVO();
        vo.setIdDia(dia);
        vo.setHora(hora);
        vo.setMinuto(min);
        vo.setIdNotificacion(idNotificacion);
        vo.setIdProceso(idProceso);
        vo.setIdLob(idLob);
        vo.setIdMensaje(idMensaje);

        return vo;
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

    public final String getStatus() {
        return status;
    }

    public final void setStatus(String status) {
        this.status = status;
    }

    public final String getIdMensaje() {
        return idMensaje;
    }

    public final void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public final NotificacionVO getNotificacion() {
        return notificacion;
    }

    public final void setNotificacion(NotificacionVO notificacion) {
        this.notificacion = notificacion;
    }

    public final String getJsonrecords() {
        return jsonrecords;
    }

    public final void setJsonrecords(String jsonrecords) {
        this.jsonrecords = jsonrecords;
    }

    public final NotificacionesService getService() {
        return service;
    }

    public final void setService(NotificacionesService service) {
        this.service = service;
    }

    public final String getMon() {
        return mon;
    }

    public final void setMon(String mon) {
        this.mon = mon;
    }

    public final String getTue() {
        return tue;
    }

    public final void setTue(String tue) {
        this.tue = tue;
    }

    public final String getWed() {
        return wed;
    }

    public final void setWed(String wed) {
        this.wed = wed;
    }

    public final String getThu() {
        return thu;
    }

    public final void setThu(String thu) {
        this.thu = thu;
    }

    public final String getFri() {
        return fri;
    }

    public final void setFri(String fri) {
        this.fri = fri;
    }

    public final String getSat() {
        return sat;
    }

    public final void setSat(String sat) {
        this.sat = sat;
    }

    public final String getSun() {
        return sun;
    }

    public final void setSun(String sun) {
        this.sun = sun;
    }

    public boolean isDia() {
        return dia;
    }

    public void setDia(boolean dia) {
        this.dia = dia;
    }

    public void setReintentable(String reintentable) {
        this.reintentable = reintentable;
    }

    public void setIdRespBancaria(String idRespBancaria) {
        this.idRespBancaria = idRespBancaria;
    }

    public final String getMedio() {
        return medio;
    }

    public final void setMedio(String medio) {
        this.medio = medio;
    }

    public final String getIdNotificacion() {
        return idNotificacion;
    }

    public final void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }


	public String getCanalEnvio() {
		return canalEnvio;
	}


	public void setCanalEnvio(String canalEnvio) {
		this.canalEnvio = canalEnvio;
	}

    
}
