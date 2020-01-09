package com.metlife.nm.mensajes.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.mensajes.vo.VariableVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public class MensajesDaoImpl extends JdbcDao implements MensajesDao {

    private String qryLobs;
    private String qryProcesos;
    private String insMensaje;
    private String qryMensajesCount;
    private String qryMensajes;
    private String updMensaje;
    private String qryVariables;
    private String qryCatEnvio;
    private String qryCatStatus;
    private String qryColumnaFisica;
    private String qryAsociado;
    private String qryAccent;
    private String qryUpdAccent;

    private JdbcQueryParameters qryBusquedaMensajes;
    private static final Logger log = Logger.getLogger(MensajesDaoImpl.class);
    
	public boolean getAccentProperty() {
		boolean remove = true;

		try {
			remove = (Boolean) jdbcTemplate.queryForObject(qryAccent, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			log.error("No se encontro propiedad para Remover acentos...");
			e.printStackTrace();
		}

		return remove;
	}
	
	public void updateAccentProperty(String estatus) {
		int affected = jdbcTemplate.update(qryUpdAccent, new Object[]{estatus});
		checkAffected(affected);
	}

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_CATALOGO = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<NotificacionVO> MAPPER_ASOCIADO = new ParameterizedRowMapper<NotificacionVO>() {
        public NotificacionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NotificacionVO obj = new NotificacionVO();

            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdMensajeAnt(rs.getString("CVE_MENSAJE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_LOBS = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<VariableVO> MAPPER_VARIABLE = new ParameterizedRowMapper<VariableVO>() {
        public VariableVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            VariableVO obj = new VariableVO();

            obj.setVariable(rs.getString("CVE_VARIABLE"));
            obj.setDescripcion(rs.getString("DES_VARIABLE"));
            obj.setLongitud(rs.getString("LONG_MAX"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<MensajeVO> MAPPER_MENSAJE = new ParameterizedRowMapper<MensajeVO>() {
        public MensajeVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MensajeVO obj = new MensajeVO();

            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setProceso(rs.getString("DES_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setLob(rs.getString("DES_LINEA_NEGOCIO"));
            obj.setDescripcion(rs.getString("DES_MENSAJE"));
            obj.setMedio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setStatus(rs.getString("ESTATUS"));
            if (rs.getString("ASUNTO") != null) {
                obj.setAsunto(rs.getString("ASUNTO"));
            } else {
                obj.setAsunto("");
            }
            if (rs.getString("CUERPO") != null) {
                obj.setMensaje(rs.getString("CUERPO"));
            } else {
                obj.setMensaje("");
            }

            return obj;
        }
    };

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList<NotificacionVO> asociado(String idNotificacion, String idMensaje) {

        ArrayList params = new ArrayList();
        params.add(idMensaje);
        try {
            return (ArrayList<NotificacionVO>) jdbcTemplate.query(qryAsociado, params.toArray(), MAPPER_ASOCIADO);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<CatalogoVO> getCatEnvio() {
        if (log.isDebugEnabled()) {
            log.debug("getCatDestinatario");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatEnvio, MAPPER_CATALOGO);
    }

    public List<CatalogoVO> getCatStatus() {
        if (log.isDebugEnabled()) {
            log.debug("getCatDestinatario");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatStatus, MAPPER_CATALOGO);
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getLobs() {
        log.debug("getLobs");

        return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_LOBS);

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getListadoProcesos(String lob) {
        log.debug("getListadoProcesos");
        ArrayList params = new ArrayList();
        params.add(lob);
        return (List<CatalogoVO>) jdbcTemplate.query(qryProcesos, params.toArray(), MAPPER_LOBS);
    }

    public void insertar(MensajeVO mensaje, UserVO user) {
        if (log.isDebugEnabled()) {
            log.debug("insertar mensajeVO..." + mensaje.toString());
        }

        List<Object> parameters = new ArrayList<Object>();

        parameters.add(mensaje.getIdMensaje());
        parameters.add(mensaje.getDescripcion());
        parameters.add(mensaje.getIdLob());
        parameters.add(mensaje.getIdProceso());
        parameters.add(mensaje.getStatus());
        parameters.add(mensaje.getMedio());
        parameters.add(mensaje.getAsunto());
        parameters.add(mensaje.getMensaje());
        parameters.add(user.getUsername());

        // inserta en la tabla de DM04_SEG_USUARIOS USU
        int affected = jdbcTemplate.update(insMensaje, parameters.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void actualizar(MensajeVO mensaje, UserVO user) {
        if (log.isDebugEnabled()) {
            log.debug("actualizar...");
        }
        ArrayList parameters = new ArrayList();

        parameters.add(mensaje.getDescripcion().toString());
        parameters.add(mensaje.getStatus());
        parameters.add(mensaje.getAsunto());
        parameters.add(mensaje.getMensaje());
        parameters.add(user.getUsername());
        parameters.add(mensaje.getIdProceso());
        parameters.add(mensaje.getIdMensaje());

        int affected = jdbcTemplate.update(updMensaje, parameters.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    public int getCountMensajes() {
        if (log.isDebugEnabled()) {
            log.debug("getCountProcesosBitacora...");
        }
        return jdbcTemplate.queryForInt(qryMensajesCount);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<MensajeVO> getMensajes() {
        if (log.isDebugEnabled()) {
            log.info("getMensajes...");
        }

        return (List<MensajeVO>) jdbcTemplate.query(qryMensajes, MAPPER_MENSAJE);
    }

    @SuppressWarnings("unchecked")
    public List<MensajeVO> busqueda(String proceso, String lob, String medio, String status) {
        if (log.isDebugEnabled()) {
            log.debug("getMensajesDao.... ");
            log.debug("proceso => [" + proceso + "]");
            log.debug("lob => [" + lob + "]");
            log.debug("medio => [" + medio + "]");
            log.debug("status => [" + status + "]");
        }

        StringBuffer stb = new StringBuffer(qryBusquedaMensajes.getSqlBase());

        @SuppressWarnings("rawtypes")
        ArrayList parameters = new ArrayList();

        Properties params = qryBusquedaMensajes.getOptionalParameters();

        if (!StringUtils.isBlank(proceso)) {
            stb.append(" ").append(params.get("proceso"));
            parameters.add(proceso);
        }
        if (!StringUtils.isBlank(lob)) {
            stb.append(" ").append(params.get("lob"));
            parameters.add(lob);
        }
        if (!StringUtils.isBlank(medio)) {
            stb.append(" ").append(params.get("medio"));
            parameters.add(medio);
        }
        if (!StringUtils.isBlank(status)) {
            stb.append(" ").append(params.get("status"));
            parameters.add(status);
        }

        Object[] args = parameters.toArray();

        return jdbcTemplate.query(stb.toString(), args, MAPPER_MENSAJE);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<VariableVO> getVariables(String proceso) {
        if (log.isDebugEnabled()) {
            log.debug("getVariables.... ");
        }
        ArrayList params = new ArrayList();
        params.add(proceso);

        return jdbcTemplate.query(qryVariables, params.toArray(), MAPPER_VARIABLE);
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
    public boolean existeColumnaFisica(String columna) {
        if (log.isDebugEnabled()) {
            log.debug("getRetroactividad...");
            log.debug("columna -> " + columna);
        }
        ArrayList params = new ArrayList();
        params.add(columna);
        try {
            String column = (String) jdbcTemplate.queryForObject(qryColumnaFisica, params.toArray(), String.class);
            return true;
        } catch (DataAccessException e) {
            log.error("No existe la variable");
            return false;
        }
    }

    public final void setQryColumnaFisica(String qryColumnaFisica) {
        this.qryColumnaFisica = qryColumnaFisica;
    }

    public void setQryLobs(String qryLobs) {
        this.qryLobs = qryLobs;
    }

    public void setQryProcesos(String qryProcesos) {
        this.qryProcesos = qryProcesos;
    }

    public void setInsMensaje(String insMensaje) {
        this.insMensaje = insMensaje;
    }

    public void setQryMensajesCount(String qryMensajesCount) {
        this.qryMensajesCount = qryMensajesCount;
    }

    public void setQryMensajes(String qryMensajes) {
        this.qryMensajes = qryMensajes;
    }

    public void setQryBusquedaMensajes(JdbcQueryParameters qryBusquedaMensajes) {
        this.qryBusquedaMensajes = qryBusquedaMensajes;
    }

    public void setQryVariables(String qryVariables) {
        this.qryVariables = qryVariables;
    }

    public void setUpdMensaje(String updMensaje) {
        this.updMensaje = updMensaje;
    }

    public void setQryCatEnvio(String qryCatEnvio) {
        this.qryCatEnvio = qryCatEnvio;
    }

    public void setQryCatStatus(String qryCatStatus) {
        this.qryCatStatus = qryCatStatus;
    }

    public final void setQryAsociado(String qryAsociado) {
        this.qryAsociado = qryAsociado;
    }

	public String getQryAccent() {
		return qryAccent;
	}

	public void setQryAccent(String qryAccent) {
		this.qryAccent = qryAccent;
	}

	public String getQryUpdAccent() {
		return qryUpdAccent;
	}

	public void setQryUpdAccent(String qryUpdAccent) {
		this.qryUpdAccent = qryUpdAccent;
	}

}
