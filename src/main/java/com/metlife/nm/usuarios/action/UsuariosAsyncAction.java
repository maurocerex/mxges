package com.metlife.nm.usuarios.action;

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
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.usuarios.service.UsuariosService;
import com.metlife.nm.usuarios.vo.UsuarioVO;

public class UsuariosAsyncAction extends GenericAsyncAction {

    /**
     * 
     */
    private static final long serialVersionUID = -6536964772985551843L;

    private static final Logger log = Logger.getLogger(UsuariosAsyncAction.class);

    @Resource(name = BeanNames.UsuariosService)
    protected UsuariosService service;

    private String jsonrecords;

    public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            ArrayList<UsuarioVO> list = (ArrayList<UsuarioVO>) service.getUsuarios();

            Map<Object, Object> resultSet = new HashMap<Object, Object>();
            List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();
            Map<Object, Object> map = new HashMap<Object, Object>();

            for (UsuarioVO vo : list) {
                map = new HashMap<Object, Object>();
                map.put("idusuario", vo.getUsername());
                map.put("username", vo.getUsername());
                map.put("nombre", vo.getNombre());
                map.put("sinovigente", vo.getVigencia());
                map.put("idrol", vo.getRolId());
                map.put("rol", vo.getDescripcionRol());
                map.put("idlob", vo.getIdlob());
                map.put("lob", vo.getLob());

                map.put("nuevo", false);
                map.put("modificado", false);
                map.put("eliminado", false);

                mapResult.add(map);
            }
            resultSet.put("Result", mapResult);
            messages.put("ResultSet", resultSet);

        } catch (Exception e) {

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

    static final String DIV_USERNAME = "div.username";
    static final String DIV_NOMBRE = "div.nombre";
    static final String DIV_VIGENCIA = "div.vigencia";
    static final String DIV_ROL = "div.rolId";
    static final String DIV_LOB = "div.idlob";

    private UsuarioVO parametro;

    public String validar() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("validar...");
        }
        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        String msgError = null;

        try {
            if (parametro.isNuevo()) {
                /**
                 * Validar el username del usuario cuando sea nuevo
                 */
                boolean errorUserName = false;
                String username = parametro.getUsername();
                msgError = "";
                if (isBlank(username)) {
                    errorUserName = true;
                    success = false;
                    msgError = this.getText("segusuarios.username.blank");
                }
                if (!StringUtils.isAlphanumeric(username)) {
                    errorUserName = true;
                    success = false;
                    msgError = this.getText("segusuarios.username.incorrecto");
                }
                if (service.isUsernameRepetido(username.toUpperCase())) {
                    errorUserName = true;
                    msgError = "Ya existe un usuario con el username " + username;
                    success = false;
                }
                divs.add(getDivYMensaje(DIV_USERNAME, msgError));
            }
            /**
             * Validar el nombre del usuario
             */
            boolean errorNombre = false;
            String nombre = parametro.getNombre();
            msgError = "";
            if (isBlank(nombre)) {
                errorNombre = true;
                msgError = this.getText("segusuarios.nombre.blank");
            } else if (nombre.trim().length() > 100) {
                errorNombre = true;
                msgError = this.getText("segusuarios.nombre.longitud");
            }
            if (!StringUtils.isAlphanumericSpace(nombre)) {
                errorNombre = true;
                msgError = this.getText("segusuarios.username.incorrecto");
            }
            if (errorNombre) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_NOMBRE, msgError));

            /**
             * Valida la vigencia
             */
            boolean errorVigencia = false;
            String vigencia = parametro.getVigencia();
            msgError = "";
            if (isBlank(vigencia)) {
                errorVigencia = true;
                msgError = this.getText("segusuarios.vigencia.blank");
            }
            if (errorVigencia) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_VIGENCIA, msgError));

            boolean errorLob = false;
            String lob = parametro.getIdlob();

            msgError = "";
            if (isBlank(lob)) {
                errorLob = true;
                msgError = this.getText("segusuarios.lob.blank");
            }
            if (errorLob) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_LOB, msgError));

            boolean errorRol = false;
            String rol = parametro.getRolId();
            msgError = "";
            if (isBlank(rol)) {
                errorRol = true;
                msgError = this.getText("segusuarios.rol.blank");
            }
            if (errorRol) {
                success = false;
            }
            divs.add(getDivYMensaje(DIV_ROL, msgError));
            /**
             * Valida el rol
             */
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

    public String guardarCambios() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("guardarCambios...");
        }

        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        try {
            if (log.isDebugEnabled()) {
                log.debug("jsonrecords: " + jsonrecords);
            }
            List<UsuarioVO> registros = parseJson(jsonrecords);
            UserVO user = (UserVO) session.get(ConstantesMxges.USER);
            for (UsuarioVO vo : registros) {
                log.debug("nombre " + vo.getNombre());
                log.debug("rol " + vo.getDescripcionRol());
                log.debug("rolid " + vo.getRolId());
            }

            /*
             * if(service.isUsernameRepetido(registros)){ success = false;
             * messages.put("username",
             * "Username repetido por favor verifiquelo."); }
             */
            try {
                service.guardarCambios(registros, user);
            } catch (RuntimeException e) {
                messages.put("username", "Ya existe un usuario con ese Username por favor verifiquelo.");
                success = false;
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

    @SuppressWarnings({ "unused", "rawtypes" })
    private List<UsuarioVO> parseJson(final String jsonString) throws RuntimeException {
        try {

            List<UsuarioVO> registros = new ArrayList<UsuarioVO>();

            if (jsonString != null) {
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                if (log.isDebugEnabled()) {
                    log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
                }
                boolean nuevo = false;
                boolean modificado = false;

                String usuarioId = null;
                String username = null;
                String nombre = null;
                String vigencia = null;
                String rolId = null;
                String idlob = null;
                UsuarioVO tmp = null;
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject jsonObj = (JSONObject) it.next();

                    // Es obligatorio que las banderas representen exactamente
                    // un valor boolean.
                    nuevo = super.parseJsonBoolean(jsonObj, "nuevo");
                    modificado = super.parseJsonBoolean(jsonObj, "modificado");

                    if (nuevo || modificado) {
                        usuarioId = parseJsonString(jsonObj, "username");
                        username = parseJsonString(jsonObj, "username");
                        nombre = parseJsonString(jsonObj, "nombre");
                        vigencia = parseJsonString(jsonObj, "sinovigente");
                        rolId = parseJsonString(jsonObj, "idrol");
                        idlob = parseJsonString(jsonObj, "idlob");

                        tmp = new UsuarioVO();

                        tmp.setUsername(username);
                        tmp.setNombre(nombre);
                        tmp.setVigencia(vigencia);
                        tmp.setRolId(rolId);
                        tmp.setIdlob(idlob);
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

    public UsuarioVO getParametro() {
        return parametro;
    }

    public void setParametro(UsuarioVO parametro) {
        this.parametro = parametro;
    }

    public String getJsonrecords() {
        return jsonrecords;
    }

    public void setJsonrecords(String jsonrecords) {
        this.jsonrecords = jsonrecords;
    }
}
