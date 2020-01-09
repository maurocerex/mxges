package com.metlife.nm.notificaciones.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.notificaciones.vo.MensajeVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public class NotificacionesDaoImpl extends JdbcDao implements NotificacionesDao {

    private static final Logger log = Logger.getLogger(NotificacionesDaoImpl.class);

    private String qryLobs;
    private String qryProcesos;
    private JdbcQueryParameters qryMensajes;
    private String qryMensaje;
    private String insNotificacion;
    private String insFrecuencia;
    private String updNotificacion;
    private String updFrecuencia;
    private String getLast;
    private String qryFrecuencias;
    private JdbcQueryParameters qryBusquedaNotificaciones;
    private String qryCatStatus;
    private String qryCatMedio;
    private String qryFrecuenciaDia;
    private String delFrecuencia;
    private String updatePendientes2dias;
    private String qryCatCanalEnvio;
    

    public String getQryCatCanalEnvio() {
		return qryCatCanalEnvio;
	}

	public void setQryCatCanalEnvio(String qryCatCanalEnvio) {
		this.qryCatCanalEnvio = qryCatCanalEnvio;
	}

	private static ParameterizedRowMapper<FrecuenciaVO> MAPPER_FRECUENCIA = new ParameterizedRowMapper<FrecuenciaVO>() {
        public FrecuenciaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FrecuenciaVO obj = new FrecuenciaVO();

            obj.setIdDia(rs.getString("CVE_DIA"));
            obj.setHora(rs.getInt("HH_NOTIFICACION"));
            obj.setMinuto(rs.getInt("MM_NOTIFICACION"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_CATALOGO = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<MensajeVO> MAPPER_MENSAJE = new ParameterizedRowMapper<MensajeVO>() {
        public MensajeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MensajeVO obj = new MensajeVO();

            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setDescripcion(rs.getString("DES_MENSAJE"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setStatus(rs.getString("ESTATUS"));
            obj.setMedio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setAsunto(rs.getString("ASUNTO"));
            obj.setMensaje(rs.getString("CUERPO"));

            return obj;
        }
    };

    
    
    
    private static ParameterizedRowMapper<NotificacionVO> MAPPER_NOTIFICACION = new ParameterizedRowMapper<NotificacionVO>() {
        public NotificacionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificacionVO obj = new NotificacionVO();
            MensajeVO msj = new MensajeVO();

            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setProcesoDesc(rs.getString("DES_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setLob(rs.getString("DES_LINEA_NEGOCIO"));
            msj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setMensaje(msj);
            obj.setNotificacionDesc(rs.getString("DES_NOTIFICACION"));
            obj.setStatus(rs.getString("ESTATUS"));
            obj.setMedio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setCanalEnvio(rs.getString("SW_CANAL_ENVIO"));
            if (rs.getString("SW_ENVIO_AGENTE").equals("1")) {
                obj.setEnvioAgente(true);
            } else {
                obj.setEnvioAgente(false);
            }
            if (rs.getString("SW_ENVIO_CLIENTE").equals("1")) {
                obj.setEnvioCliente(true);
            } else {
                obj.setEnvioCliente(false);
            }
            if (rs.getString("SW_ENVIO_PROMOT").equals("1")) {
                obj.setEnvioPromotoria(true);
            } else {
                obj.setEnvioPromotoria(false);
            }
            obj.setIdRespBancaria(rs.getString("CVE_TIPO_RESP_CB"));
            
            obj.setReintentable(rs.getString("SW_REINTENTA_CB"));
            
            if (rs.getString("SW_DXN").equals("1")) {
                obj.setDxn(true);
            } else {
                obj.setDxn(false);
            }
            if (rs.getString("SW_COB_BANCARIA").equals("1")) {
                obj.setCobraBanca(true);
            } else {
                obj.setCobraBanca(false);
            }
            if (rs.getString("SW_PAGO_DIRECTO").equals("1")) {
                obj.setPagoDirecto(true);
            } else {
                obj.setPagoDirecto(false);
            }
            if (rs.getString("SW_REGLAB_BASE").equals("1")) {
                obj.setRegLabBase(true);
            } else {
                obj.setRegLabBase(false);
            }
            if (rs.getString("SW_REGLAB_EVENTUAL").equals("1")) {
                obj.setRegLabEventual(true);
            } else {
                obj.setRegLabEventual(false);
            }

            return obj;
        }
    };

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getLobs() {
        if (log.isDebugEnabled()) {
            log.debug("getLobs");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_CATALOGO);
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getCatStatus() {
        if (log.isDebugEnabled()) {
            log.debug("getCatStatus");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatStatus, MAPPER_CATALOGO);
    }
    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getCatCanalEnvio() {
    	if (log.isDebugEnabled()) {
            log.debug("getCatCanalEnvio");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatCanalEnvio, MAPPER_CATALOGO);
	}
    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getCatMedio() {
        if (log.isDebugEnabled()) {
            log.debug("getCatMedio");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatMedio, MAPPER_CATALOGO);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getListadoProcesos(String lob) {
        if (log.isDebugEnabled()) {
            log.debug("getListadoProcesos DAO...");
        }
        ArrayList params = new ArrayList();
        params.add(lob);
        return (List<CatalogoVO>) jdbcTemplate.query(qryProcesos, params.toArray(), MAPPER_CATALOGO);
    }

    

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getMensajes(String lob, String proceso, String medio) {

        if (log.isDebugEnabled()) {
            log.debug("getMensajes Dao.... ");
            log.debug("proceso => [" + proceso + "]");
            log.debug("lob => [" + lob + "]");
            log.debug("medio => [" + medio + "]");
        }

        StringBuffer stb = new StringBuffer(qryMensajes.getSqlBase());

        ArrayList parameters = new ArrayList();

        Properties params = qryMensajes.getOptionalParameters();
        parameters.add(lob);
        if (!StringUtils.isBlank(proceso)) {
            stb.append(" ").append(params.get("proceso"));
            parameters.add(proceso);
        }
        if (!StringUtils.isBlank(medio)) {
            stb.append(" ").append(params.get("medio"));
            parameters.add(medio);
        }

        Object[] args = parameters.toArray();

        return jdbcTemplate.query(stb.toString(), args, MAPPER_CATALOGO);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MensajeVO getMensaje(String idMensaje) {
        if (log.isDebugEnabled()) {
            log.debug("getMensajes DAO...");
        }
        ArrayList params = new ArrayList();
        params.add(idMensaje);

        try {
            return (MensajeVO) jdbcTemplate.queryForObject(qryMensaje, params.toArray(), MAPPER_MENSAJE);
        } catch (EmptyResultDataAccessException e) {
            return new MensajeVO();
        }
    }

    public void insertarFrecuencia(FrecuenciaVO frec) {
        if (log.isDebugEnabled()) {
            log.debug("insertarFrecuencia..." + frec.toString());
        }
        Integer index = (Integer) jdbcTemplate.queryForObject(getLast, Integer.class);

        if (index == null) {
            index = 0;
        } else {
            index++;
        }
        List<Object> params = new ArrayList<Object>();
        params.add(frec.getIdNotificacion().toString());
        params.add(frec.getIdProceso());
        params.add(frec.getIdLob());
        params.add(frec.getIdMensaje());
        params.add(frec.getIdDia());
        params.add(frec.getHora());
        params.add(frec.getMinuto());
        params.add(index);

        int affected = jdbcTemplate.update(insFrecuencia, params.toArray());

        this.checkAffected(affected);
    }

    public void insertar(NotificacionVO notificacion, UserVO user) {

        List<Object> params = new ArrayList<Object>();
        params.add(notificacion.getIdNotificacion().toString());
        params.add(notificacion.getIdProceso());
        params.add(notificacion.getIdLob());
        params.add(notificacion.getMensaje().getIdMensaje());
        params.add(notificacion.getNotificacionDesc());
        params.add(notificacion.getStatus());
        params.add(notificacion.getMedio());
        if (notificacion.isEnvioAgente()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isEnvioCliente()) {
            params.add("1");
        } else {
            params.add("0");
        }
        params.add("0");
        params.add(notificacion.getIdRespBancaria());
                
        if(StringUtils.isBlank(notificacion.getReintentable())){
        	// valor auxiliar 
        	params.add("9");
        }else{
        	params.add(notificacion.getReintentable());
        }
        	
        
        
        if (notificacion.isDxn()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isCobraBanca()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isPagoDirecto()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isRegLabBase()) {
            params.add("1");
        } else {
            params.add("0");
        }

        if (notificacion.isRegLabEventual()) {
            params.add("1");
        } else {
            params.add("0");
        }

        params.add(user.getUsername());
        
        params.add(notificacion.getCanalEnvio());

        // inserta en la tabla de DM04_SEG_USUARIOS USU
        try {
            this.checkAffected(jdbcTemplate.update(insNotificacion, params.toArray()));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Ya existe una Notificacion con el id " + notificacion.getIdNotificacion());
        }

        // Importante verificar que afectamos exactamente un registro.

    }

    public void updateFrecuencia(FrecuenciaVO frec) {
        if (log.isDebugEnabled()) {
            log.debug("actualizarFrecuencia...");
            log.debug(" idProceso  => [ " + frec.getIdProceso() + " ]");
            log.debug(" lob  => [ " + frec.getIdLob() + " ]");
            log.debug(" idMensaje  => [ " + frec.getIdMensaje() + " ]");
            log.debug(" idDia  => [ " + frec.getIdDia() + " ]");
            log.debug(" hora  => [ " + frec.getHora() + " ]");
            log.debug(" minuto  => [ " + frec.getMinuto() + " ]");
            log.debug(" idNotificacion  => [ " + frec.getIdNotificacion() + " ]");

        }
        List<Object> params = new ArrayList<Object>();

        params.add(frec.getIdProceso());
        params.add(frec.getIdLob());
        params.add(frec.getHora());
        params.add(frec.getMinuto());
        params.add(frec.getIdNotificacion());
        params.add(frec.getIdDia());
        params.add(frec.getIdMensaje());
        int affected = jdbcTemplate.update(updFrecuencia, params.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    public void actualizar(NotificacionVO notificacion, UserVO userVO) {
        List<Object> params = new ArrayList<Object>();

        params.add(notificacion.getNotificacionDesc());
        params.add(notificacion.getStatus());
        params.add(userVO.getUsername());

        if (notificacion.isEnvioCliente() && notificacion.isEnvioAgente()) {
            params.add("1");
            params.add("1");
        } else if (notificacion.isEnvioCliente()) {
            params.add("1");
            params.add("0");
        } else if (notificacion.isEnvioAgente()) {
            params.add("0");
            params.add("1");
        }

        if (notificacion.isDxn()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isCobraBanca()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isPagoDirecto()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isRegLabBase()) {
            params.add("1");
        } else {
            params.add("0");
        }
        if (notificacion.isRegLabEventual()) {
            params.add("1");
        } else {
            params.add("0");
        }
        
        params.add(notificacion.getCanalEnvio());

        params.add(notificacion.getIdNotificacion());
        params.add(notificacion.getMensaje().getIdMensaje());
        

        int affected = jdbcTemplate.update(updNotificacion, params.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    @SuppressWarnings("unchecked")
    public List<NotificacionVO> busqueda(String proceso, String lob, String status) {
        if (log.isDebugEnabled()) {
            log.debug("Busqueda Dao.... ");
            log.debug("proceso => [" + proceso + "]");
            log.debug("lob => [" + lob + "]");
            log.debug("status => [" + status + "]");
        }

        StringBuffer stb = new StringBuffer(qryBusquedaNotificaciones.getSqlBase());

        @SuppressWarnings("rawtypes")
        ArrayList parameters = new ArrayList();

        Properties params = qryBusquedaNotificaciones.getOptionalParameters();

        if (!StringUtils.isBlank(proceso)) {
            stb.append(" ").append(params.get("proceso"));
            parameters.add(proceso);
        }
        if (!StringUtils.isBlank(lob)) {
            stb.append(" ").append(params.get("lob"));
            parameters.add(lob);
        }
        if (!StringUtils.isBlank(status)) {
            stb.append(" ").append(params.get("status"));
            parameters.add(status);
        }
        
        stb.append(" ").append(params.get("orderBy"));

        Object[] args = parameters.toArray();

        return jdbcTemplate.query(stb.toString(), args, MAPPER_NOTIFICACION);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<FrecuenciaVO> getFrecuenciasByIdNotificacion(NotificacionVO notificacion) {
        if (log.isDebugEnabled()) {
            log.debug("getFrecuenciasByIdNotificacion Dao.... ");
        }

        ArrayList params = new ArrayList();
        params.add(notificacion.getIdNotificacion());
        params.add(notificacion.getIdProceso());
        params.add(notificacion.getIdLob());
        params.add(notificacion.getMensaje().getIdMensaje());
        try {
            return (List<FrecuenciaVO>) jdbcTemplate.query(qryFrecuencias, params.toArray(), MAPPER_FRECUENCIA);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<FrecuenciaVO>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public FrecuenciaVO getDiaFrec(NotificacionVO notificacion, String idDia) {
        if (log.isDebugEnabled()) {
            log.debug("getDiaFrec Dao.... ");
        }

        ArrayList params = new ArrayList();
        params.add(notificacion.getIdNotificacion());
        params.add(notificacion.getIdProceso());
        params.add(notificacion.getIdLob());
        params.add(notificacion.getMensaje().getIdMensaje());
        params.add(idDia);
        try {
            return (FrecuenciaVO) jdbcTemplate.queryForObject(qryFrecuenciaDia, params.toArray(), MAPPER_FRECUENCIA);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void borrarFrecuencias(NotificacionVO notificacion) {
        if (log.isDebugEnabled()) {
            log.debug("borrarFrecuencias Dao.... ");
        }

        ArrayList params = new ArrayList();
        params.add(notificacion.getIdNotificacion());
        params.add(notificacion.getIdProceso());
        params.add(notificacion.getMensaje().getIdMensaje());
        params.add(notificacion.getIdLob());

        jdbcTemplate.update(delFrecuencia, params.toArray());
    }

    public void updateNotifyPen(){
    	if (log.isDebugEnabled()) {
            log.debug("se actualiza notificaciones pendientes mayores a 2 dias.... ");
        }
    	try{
    		int rows = jdbcTemplate.update(updatePendientes2dias);
    		log.info("Se actualizaron: " + rows + " notificaciones pendientes mayores a 2 dias");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public final void setQryLobs(String qryLobs) {
        this.qryLobs = qryLobs;
    }

    public final void setQryProcesos(String qryProcesos) {
        this.qryProcesos = qryProcesos;
    }

    public final void setQryMensaje(String qryMensaje) {
        this.qryMensaje = qryMensaje;
    }

    public final void setInsNotificacion(String insNotificacion) {
        this.insNotificacion = insNotificacion;
    }

    public final void setQryBusquedaNotificaciones(JdbcQueryParameters qryBusquedaNotificaciones) {
        this.qryBusquedaNotificaciones = qryBusquedaNotificaciones;
    }

    public final void setUpdNotificacion(String updNotificacion) {
        this.updNotificacion = updNotificacion;
    }

    public final void setInsFrecuencia(String insFrecuencia) {
        this.insFrecuencia = insFrecuencia;
    }

    public final void setUpdFrecuencia(String updFrecuencia) {
        this.updFrecuencia = updFrecuencia;
    }

    public final void setGetLast(String getLast) {
        this.getLast = getLast;
    }

    public final void setQryFrecuencias(String qryFrecuencias) {
        this.qryFrecuencias = qryFrecuencias;
    }

    public void setQryCatStatus(String qryCatStatus) {
        this.qryCatStatus = qryCatStatus;
    }

    public void setQryCatMedio(String qryCatMedio) {
        this.qryCatMedio = qryCatMedio;
    }
    
    public final void setQryMensajes(JdbcQueryParameters qryMensajes) {
        this.qryMensajes = qryMensajes;
    }

    public final void setQryFrecuenciaDia(String qryFrecuenciaDia) {
        this.qryFrecuenciaDia = qryFrecuenciaDia;
    }

    public final void setDelFrecuencia(String delFrecuencia) {
        this.delFrecuencia = delFrecuencia;
    }

	public void setUpdatePendientes2dias(String updatePendientes2dias) {
		this.updatePendientes2dias = updatePendientes2dias;
	}

	

    
}
