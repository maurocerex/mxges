package com.metlife.nm.mensajes.action;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.io.StringWriter;
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
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.event.EventCartridge;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.MxgesEventCartridge;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.service.MensajesService;
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.mensajes.vo.VariableVO;

public class MensajesAsyncAction extends GenericAsyncAction {
 
    /**
     * 
     */
    private static final long serialVersionUID = -7668387610631518350L;
    private static final Logger log = Logger.getLogger(MensajesAsyncAction.class);
    // busqueda
    private String proceso;
    private String lob;
    private String medio;
    private String status;
    private int startIndex;
    private MensajeVO mensaje;
    private String idMensaje;
    private String idNotificacion;
    private String jsonrecords;
    // Longitud maxima permitida por el campo en BD.
    private static int MAX_LENGHT = 250;

    @Resource(name = BeanNames.MensajesService)
    private MensajesService service;

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

    public String asociado() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("asociado...");
            log.debug("idNotificacion => " + idNotificacion);
            log.debug("idMensaje => " + idMensaje);
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            boolean asociado = service.asociado(idNotificacion, idMensaje);

            JSONArray selectconsulta = new JSONArray();

            if (asociado) {
                messages.put("asociado", true);
                success = false;
            } else {
                success = true;
                messages.put("asociado", false);
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

    static final String DIV_PROCESO = "div.proceso.bus";
    static final String DIV_LOB = "div.lob.bus";
    static final String DIV_STATUS = "div.status.bus";
    static final String DIV_MEDIO_BUS = "div.medio.bus";

    public String busqueda() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("busqueda...");
            log.debug("proceso -> " + proceso);
            log.debug("lob -> " + lob);
            log.debug("medio -> " + medio);
            log.debug("status -> " + status);
        }
        JSONObject messages = new JSONObject();
        JSONArray records = new JSONArray();
        JSONArray divs = new JSONArray();
        boolean success = true;
        String msgError = null;
        try {

            /*
             * msgError=""; boolean errorproceso=false; if (isBlank(proceso)) {
             * errorproceso = true; msgError =
             * this.getText("busqueda.proceso.blank"); } if (errorproceso) {
             * success = false; }
             * 
             * divs.add(getDivYMensaje(DIV_PROCESO, msgError));
             * 
             * msgError=""; boolean errorLob=false; if (isBlank(lob)) { errorLob
             * = true; msgError = this.getText("busqueda.lob.blank"); } if
             * (errorLob) { success = false; }
             * 
             * divs.add(getDivYMensaje(DIV_LOB, msgError));
             * 
             * msgError=""; boolean errorStatus=false; if (isBlank(status)) {
             * errorStatus = true; msgError =
             * this.getText("busqueda.status.blank"); } if (errorStatus) {
             * success = false; }
             * 
             * divs.add(getDivYMensaje(DIV_STATUS, msgError));
             * 
             * msgError=""; boolean errorMedio=false; if (isBlank(medio)) {
             * errorMedio = true; msgError =
             * this.getText("busqueda.medio.blank"); } if (errorMedio) { success
             * = false; }
             * 
             * divs.add(getDivYMensaje(DIV_MEDIO_BUS, msgError));
             */
            if (success) {
                ArrayList<MensajeVO> mensajes = (ArrayList<MensajeVO>) service.busqueda(proceso, lob, medio, status);
                JSONObject map = new JSONObject();

                for (MensajeVO tmp : mensajes) {
                    map = new JSONObject();
                    map.put("idMensaje", tmp.getIdMensaje());
                    map.put("descripcion", tmp.getDescripcion());
                    map.put("asunto", tmp.getAsunto());
                    map.put("mensaje", tmp.getMensaje());
                    map.put("idlob", tmp.getIdLob());
                    map.put("lob", tmp.getLob());
                    map.put("medio", tmp.getMedio());
                    map.put("idProceso", tmp.getIdProceso());
                    map.put("proceso", tmp.getProceso());
                    map.put("status", tmp.getStatus());
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
            if (log.isDebugEnabled()) {
                log.debug(messages.toString(1));
            }
            streamIt(messages);
        }
        return SUCCESS;
    }

    static final String DIV_ID_MENSAJE = "div.mensaje";
    static final String DIV_ID_PROCESO = "div.proceso";
    static final String DIV_ID_LOB = "div.lob";
    static final String DIV_ASUNTO = "div.asunto";
    static final String DIV_MENSAJE_CUERPO = "div.mensaje.cuerpo";
    static final String DIV_MEDIO = "div.medio";
    static final String DIV_DESCRIPCION = "div.descripcion";

    // static final String DIV_STATUS = "div.status";

    public String validar() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("validar..." + mensaje.toString());
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        String msgError = null;

        try {
            msgError = "";

            if (mensaje.isNuevo()) {

                boolean errorIdMensaje = false;

                msgError = "";
                if (isBlank(mensaje.getIdMensaje())) {
                    errorIdMensaje = true;
                    msgError = this.getText("mensajes.idmensaje.blank");
                } else if (mensaje.getIdMensaje().trim().length() > 40) {
                    errorIdMensaje = true;
                    msgError = this.getText("mensajes.idmensaje.longitud");
                }
                if (!StringUtils.isAlphanumericSpace(mensaje.getIdMensaje())) {
                    errorIdMensaje = true;
                    msgError = this.getText("mensajes.idmensaje.valido");
                }
                if (errorIdMensaje) {
                    success = false;
                }

                divs.add(getDivYMensaje(DIV_ID_MENSAJE, msgError));

                msgError = "";
                boolean errorIdProceso = false;
                if (isBlank(mensaje.getIdProceso())) {
                    errorIdProceso = true;
                    msgError = this.getText("mensajes.idproceso.blank");
                }
                if (errorIdProceso) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_ID_PROCESO, msgError));
                
                msgError = "";
                boolean errorIdLob = false;
                if (isBlank(mensaje.getIdLob())) {
                    errorIdLob = true;
                    msgError = this.getText("mensajes.idlob.blank");
                } else if (mensaje.getIdLob().trim().length() > 40) {
                    errorIdLob = true;
                    msgError = this.getText("mensajes.idlob.blank");
                }
                if (errorIdLob) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_ID_LOB, msgError));

                msgError = "";
                boolean errorMedio = false;
                if (isBlank(mensaje.getMedio())) {
                    errorMedio = true;
                    msgError = this.getText("mensajes.medio.blank");
                }
                if (errorMedio) {
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_MEDIO, msgError));

            }

            msgError = "";
            boolean errorDesc = false;
            if (isBlank(mensaje.getDescripcion())) {
                errorDesc = true;
                msgError = this.getText("mensajes.descripcion.blank");
            }
            if (!StringUtils.isAlphanumericSpace(mensaje.getDescripcion())) {
                errorDesc = true;
                msgError = this.getText("mensajes.descripcion.valida");
            }
            if (errorDesc) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_DESCRIPCION, msgError));

            msgError = "";
            boolean errorMensaje = false;
            if (isBlank(mensaje.getMensaje())) {
                errorMensaje = true;
                msgError = this.getText("mensajes.mensaje.blank");
            }
            ArrayList<String> noExisten = new ArrayList<String>();
            if (!isBlank(mensaje.getMensaje())) {

                String[] variables = StringUtils.substringsBetween(mensaje.getMensaje(), "${", "}");
                if (variables != null) {
                    for (int i = 0; i < variables.length; i++) {
                        boolean existe = service.existeColumnaFisica(variables[i]);
                        if (log.isDebugEnabled()) {
                            log.debug("La variable " + variables[i] + " existe? " + existe);
                        }
                        if (existe) {
                            // context.put("{"+variables[i]+"}", "lal");
                        } else {
                            noExisten.add(variables[i]);
                            msgError = "Las siguientes variables no existen: ";
                            for (String tmp : noExisten) {
                                msgError += tmp;
                            }
                            errorMensaje = true;
                        }
                    }
                }

            }

            if (errorMensaje) {

                success = false;
            }

            divs.add(getDivYMensaje(DIV_MENSAJE_CUERPO, msgError));

            if (log.isDebugEnabled()) {
                log.debug("que pasa? si es un email ��" + medio);
                log.debug("que pasa? esta vacio?" + isBlank(mensaje.getAsunto()));
            }
            
            msgError = "";
            if(!StringUtils.isBlank(mensaje.getMedio())){
                medio = mensaje.getMedio();
            }
            if (log.isDebugEnabled()) {
                log.debug("MEDIO " + mensaje.getMedio());
                log.debug("MEDIO " + medio);
            }
            if (medio.equals(ConstantesMxges.EMAIL)) {

                boolean errorAsunto = false;
                if (isBlank(mensaje.getAsunto())) {
                    errorAsunto = true;
                    msgError = this.getText("mensajes.asunto.blank");
                }
                if (errorAsunto) {
                    success = false;
                }

            }
            divs.add(getDivYMensaje(DIV_ASUNTO, msgError));
            
			if (StringUtils.isNotBlank(mensaje.getMensaje())) {
				if (mensaje.getMensaje().trim().length() > MAX_LENGHT) {
					success = false;
					divs.add(getDivYMensaje(DIV_MENSAJE_CUERPO, "El cuerpo de mensaje NO debe superar 250 car�cteres."));
				} else {
					divs.add(getDivYMensaje(DIV_MENSAJE_CUERPO, ""));
				}
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

            if (log.isDebugEnabled()) {
                log.debug("jsonrecords: " + jsonrecords);
            }
            List<MensajeVO> registros = parseJson(jsonrecords);
            UserVO user = (UserVO) session.get(ConstantesMxges.USER);
            for (MensajeVO vo : registros) {
                log.debug("mensaje " + vo.toString());
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

    @SuppressWarnings({ "unused", "rawtypes" })
    private List<MensajeVO> parseJson(final String jsonString) throws RuntimeException {
        try {

            List<MensajeVO> registros = new ArrayList<MensajeVO>();

            if (jsonString != null) {
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                if (log.isDebugEnabled()) {
                    log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
                }
                boolean nuevo = false;
                boolean modificado = false;

                String recordId = null;
                String idMensaje = null;
                String descripcion = null;
                String lob = null;
                String idlob = null;
                String medio = null;
                String proceso = null;
                String idproceso = null;
                String status = null;
                String asunto = null;
                String mensaje = null;

                MensajeVO tmp = null;
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject jsonObj = (JSONObject) it.next();

                    // Es obligatorio que las banderas representen exactamente
                    // un valor boolean.
                    nuevo = parseJsonBoolean(jsonObj, "nuevo");
                    modificado = parseJsonBoolean(jsonObj, "modificado");

                    if (nuevo || modificado) {

                        recordId = parseJsonString(jsonObj, "recordId");
                        idMensaje = parseJsonString(jsonObj, "idMensaje");
                        descripcion = parseJsonString(jsonObj, "descripcion");
                        lob = parseJsonString(jsonObj, "lob");
                        idlob = parseJsonString(jsonObj, "idlob");
                        medio = parseJsonString(jsonObj, "medio");
                        proceso = parseJsonString(jsonObj, "proceso");
                        idproceso = parseJsonString(jsonObj, "idProceso");
                        status = parseJsonString(jsonObj, "status");
                        asunto = parseJsonString(jsonObj, "asunto");
                        mensaje = parseJsonString(jsonObj, "mensaje");

                        tmp = new MensajeVO();

                        tmp.setIdMensaje(idMensaje);
                        tmp.setDescripcion(descripcion);
                        tmp.setLob(lob);
                        tmp.setIdLob(idlob);
                        tmp.setMedio(medio);
                        tmp.setProceso(proceso);
                        tmp.setIdProceso(idproceso);
                        tmp.setStatus(status);
                        tmp.setAsunto(asunto);
                        if (medio.equals(ConstantesMxges.SMS)) {
                            mensaje = removeAcentos(mensaje);
                        }
                        tmp.setMensaje(mensaje);

                        tmp.setNuevo(nuevo);
                        tmp.setModificado(modificado);

                        registros.add(tmp);
                    }

                }
            }

            return registros;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String listadoMensajes() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");
            log.debug("execute..." + startIndex);
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {

            Map<Object, Object> resultSet = new HashMap<Object, Object>();
            List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();

            for (MensajeVO tmp : service.getMensajes()) {
                log.debug(tmp.toString());
                Map<Object, Object> map = new HashMap<Object, Object>();
                map.put("idMensaje", tmp.getIdMensaje());
                map.put("descripcion", tmp.getDescripcion());
                map.put("asunto", tmp.getAsunto());
                map.put("mensaje", tmp.getMensaje());
                map.put("idlob", tmp.getIdLob());
                map.put("lob", tmp.getLob());
                map.put("medio", tmp.getMedio());
                map.put("proceso", tmp.getProceso());
                map.put("idProceso", tmp.getIdProceso());
                map.put("status", tmp.getStatus());
                map.put("nuevo", false);
                map.put("modificado", false);
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

    JSONObject messages = new JSONObject();
    JSONArray records = new JSONArray();

    boolean success = true;

    public String getVariables() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("getVariables...");
            log.debug("proceso -> " + proceso);
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {

            JSONObject map = new JSONObject();
            ArrayList<VariableVO> variables = (ArrayList<VariableVO>) service.getVariables(proceso);

            for (VariableVO vo : variables) {
                map = new JSONObject();
                map.put("variable", vo.getVariable());
                map.put("descripcion", vo.getDescripcion());
                map.put("longitud", vo.getLongitud());
                map.put("nuevo", false);
                map.put("modificado", false);
                map.put("eliminado", false);

                records.add(map);
            }
            messages.put("rows", records);

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

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getProceso() {
        return proceso;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MensajeVO getMensaje() {
        return mensaje;
    }

    public void setMensaje(MensajeVO mensaje) {
        this.mensaje = mensaje;
    }

    public String getJsonrecords() {
        return jsonrecords;
    }

    public void setJsonrecords(String jsonrecords) {
        this.jsonrecords = jsonrecords;
    }

    public final void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public final String getIdMensaje() {
        return idMensaje;
    }

    public final void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }

    public final String getIdNotificacion() {
        return idNotificacion;
    }

    public final void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
}
