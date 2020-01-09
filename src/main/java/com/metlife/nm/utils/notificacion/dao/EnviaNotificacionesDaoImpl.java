package com.metlife.nm.utils.notificacion.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.utils.notificacion.vo.CabeceraVO;
import com.metlife.nm.utils.notificacion.vo.EnvioNotificacionVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;
import com.metlife.nm.utils.notificacion.vo.MxgesEnvioNotificacionVo;
import com.metlife.nm.utils.notificacion.vo.NotificacionVO;
import com.metlife.nm.utils.notificacion.vo.PolizaCliente;
import com.metlife.nm.utils.notificacion.vo.VarProcVo;

public class EnviaNotificacionesDaoImpl extends JdbcDao implements EnviaNotificacionesDao {

    private static final Logger log = Logger.getLogger(EnviaNotificacionesDaoImpl.class);

    private String qryConfiguraciones;
    private String updCabecera;
    private String qryIdProcesoEnvio;
    private String qryRetroactividad;
    private String qryLimite;
    private String qryCabeceras;
    private String insNuevaCabecera;
    private JdbcQueryParameters qryMensajes;
    private String qryColumnaFisica;
    private String qryValorVariable;
    private String insEnvioSMS;
    private String updRegistro;
    private String insEnvioEmail;
    private String updEnvio;
    private String qryConcurrent;
    private String updConcurrent;
    private String qryAcentos;
    private String spProcesaSinDatos;
    private String qryProcRenew;
    private String qryProcITrans;
    
    

	private String qryNotificacionesActivas;
    private String qryFrecuencias;
    private String qryItVarProc;
    private String qryITNombreFile;
    public String getQryITNombreFile() {
		return qryITNombreFile;
	}

	public void setQryITNombreFile(String qryITNombreFile) {
		this.qryITNombreFile = qryITNombreFile;
	}

	private String qryProcCobrancA;
    private String updEnvioNotificaciones;
    private String qryITupdateRegistros;
    private String insInfoEnvios;
    private String qryProcCobrancR;
    private String qryProcCobrancRNULL;
    private String qryProcCobrancANULL;
    private String insInfoEnviosNull;
    private String updRegistrosRenew;
    private String qryITMensaje;
    
    
	public String getQryITMensaje() {
		return qryITMensaje;
	}

	public void setQryITMensaje(String qryITMensaje) {
		this.qryITMensaje = qryITMensaje;
	}

	public boolean removerAcentos() {
		return (Boolean) jdbcTemplate.queryForObject(qryAcentos, Boolean.class);
	}
    
    public void updateFlagConcurrence(String estatus){
    	int affected = jdbcTemplate.update(updConcurrent, new Object[]{estatus}); 
    	checkAffected(affected);
    }
    

    public String getQryProcITrans() {
		return qryProcITrans;
	}

	public void setQryProcITrans(String qryProcITrans) {
		this.qryProcITrans = qryProcITrans;
	}
    
	public boolean isConcurrrent() {
		boolean concurrent = false;

		try {
			concurrent = (Boolean) jdbcTemplate.queryForObject(qryConcurrent, Boolean.class);
		} catch (EmptyResultDataAccessException e) {
			log.error("No se encontro valor para la propiedad de concurrencia del sistema...");
			e.printStackTrace();
		}

		log.debug("isConcurrent: " + concurrent);
		return concurrent;
	}
	
	private static ParameterizedRowMapper<EnvioNotificacionVO> MAPPER_PROCECOBRANC_MAIL = new ParameterizedRowMapper<EnvioNotificacionVO>() {
		public EnvioNotificacionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			EnvioNotificacionVO obj = new EnvioNotificacionVO();
			obj.setRowId(rs.getString("ROW_ID"));
			obj.setRegistro(rs.getString("REGISTRO"));
			obj.setProceso(rs.getString("PROCESO"));
			obj.setEmail(rs.getString("EMAIL"));
			obj.setNomina(rs.getString("NOMINA"));
			obj.setPoliza(rs.getString("POLIZA"));
			obj.setImporte(rs.getString("IMPORTE"));
			obj.setRefBancaria(rs.getString("REF_BANCARIA"));
			obj.setNombre(rs.getString("NOMBRE"));
			obj.setPaterno(rs.getString("PATERNO"));
			obj.setMaterno(rs.getString("MATERNO"));
			obj.setFecCobro(rs.getString("FEC_COBRO"));
			return obj;
		}
	};
	
	private static ParameterizedRowMapper<FrecuenciaVO> MAPPER_FRECUENCIA = new ParameterizedRowMapper<FrecuenciaVO>() {
        public FrecuenciaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FrecuenciaVO obj = new FrecuenciaVO();

            obj.setIdDia(rs.getString("CVE_DIA"));
            obj.setHora(rs.getInt("HH_NOTIFICACION"));
            obj.setMinuto(rs.getInt("MM_NOTIFICACION"));

            return obj;
        }
    };
    
    private static ParameterizedRowMapper<VarProcVo> MAPPER_VARPROC = new ParameterizedRowMapper<VarProcVo>() {
        public VarProcVo mapRow(ResultSet rs, int rowNum) throws SQLException {
        	VarProcVo obj = new VarProcVo();

        	obj.setCveProceso(rs.getString("CVE_PROCESO"));
        	obj.setCveVariable(rs.getString("CVE_VARIABLE"));
        	obj.setColFisica(rs.getString("COL_FISICA"));
        	
            return obj;
        }
    };
	
	private static ParameterizedRowMapper<NotificacionVO> MAPPER_NOTIFICACION_ACTIVA  = new  ParameterizedRowMapper<NotificacionVO>() {
		public NotificacionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			NotificacionVO obj = new NotificacionVO();
			obj.setIdProceso(rs.getString("CVE_PROCESO"));
			obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
			obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
			obj.setNotificacionDesc(rs.getString("DES_NOTIFICACION"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));
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
            if (rs.getString("SW_DXN").equals("1")) {
                obj.setDxn(true);
            } else {
                obj.setDxn(false);
            }

            if (rs.getString("SW_COB_BANCARIA").equals("1")) {
                obj.setCobBancaria(true);
            } else {
                obj.setCobBancaria(false);
            }

            if (rs.getString("SW_PAGO_DIRECTO").equals("1")) {
                obj.setPagoDirecto(true);
            } else {
                obj.setCobBancaria(false);
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

            obj.setTipoRespuesta(rs.getString("CVE_TIPO_RESP_CB"));
            obj.setReintentable(rs.getString("SW_REINTENTA_CB"));
			
			
			
			
			
			
			
			
			return obj;
		}
	};

    private static ParameterizedRowMapper<NotificacionVO> MAPPER_NOTIFICACION = new ParameterizedRowMapper<NotificacionVO>() {
        public NotificacionVO mapRow(ResultSet rs, int arg1) throws SQLException {
            NotificacionVO obj = new NotificacionVO();

            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setTipoFrecuencia(rs.getString("TIPOFRECUENCIA"));
            obj.setIdDia(rs.getString("CVE_DIA"));
            obj.setDiaMes(rs.getInt("DIA_SEMANA"));
            obj.setMinutos(rs.getInt("MINUTOS_NOTIFICACION"));
            obj.setHoras(rs.getInt("HORA_NOTIFICACION"));
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
            if (rs.getString("SW_DXN").equals("1")) {
                obj.setDxn(true);
            } else {
                obj.setDxn(false);
            }

            if (rs.getString("SW_COB_BANCARIA").equals("1")) {
                obj.setCobBancaria(true);
            } else {
                obj.setCobBancaria(false);
            }

            if (rs.getString("SW_PAGO_DIRECTO").equals("1")) {
                obj.setPagoDirecto(true);
            } else {
                obj.setCobBancaria(false);
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

            obj.setTipoRespuesta(rs.getString("CVE_TIPO_RESP_CB"));
            obj.setReintentable(rs.getString("SW_REINTENTA_CB"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<MensajeVO> MAPPER_MENSAJE = new ParameterizedRowMapper<MensajeVO>() {
        public MensajeVO mapRow(ResultSet rs, int arg1) throws SQLException {
            MensajeVO obj = new MensajeVO();

            obj.setIdRegistro(rs.getInt("ID_REGISTRO"));
            obj.setIdProceso(rs.getInt("ID_PROCESO"));
            obj.setCveProceso(rs.getString("CVE_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));

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
                obj.setEnvioPromotor(true);
            } else {
                obj.setEnvioPromotor(false);
            }

            obj.setIdTipoRespCB(rs.getString("CVE_TIPO_RESP_CB"));

            if (rs.getString("SW_REINTENTA_CB").equals("1")) {
                obj.setReintentable(true);
            } else {
                obj.setReintentable(false);
            }

            if (rs.getString("SW_DXN").equals("1")) {
                obj.setDxn(true);
            } else {
                obj.setDxn(false);
            }

            if (rs.getString("SW_COB_BANCARIA").equals("1")) {
                obj.setCobBancaria(true);
            } else {
                obj.setCobBancaria(false);
            }

            if (rs.getString("SW_PAGO_DIRECTO").equals("1")) {
                obj.setPagoDirecto(true);
            } else {
                obj.setCobBancaria(false);
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

            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));
            obj.setMensajeDesc(rs.getString("DES_MENSAJE"));
            obj.setAsunto(rs.getString("ASUNTO"));
            obj.setCuerpo(rs.getString("CUERPO"));
            obj.setStatus(rs.getString("CVE_ESTATUS"));
            obj.setTelMovilApoderado(rs.getString("TEL_MOVIL_APODERADO"));
            obj.setTelMovilCliente(rs.getString("TEL_MOVIL_CLIENTE"));
            obj.setEmailApoderado(rs.getString("EMAIL_APODERADO"));
            obj.setEmailCliente(rs.getString("EMAIL_CLIENTE"));
            obj.setConductoCobro(rs.getString("CONDUCTO_COBRO"));
            obj.setRegLaboral(rs.getString("ID_REGIMEN_LABORAL"));
            obj.setEmailPromotoria(rs.getString("EMAIL_PROMOTORIA"));
            return obj;
        }
    };

    private static ParameterizedRowMapper<CabeceraVO> MAPPER_CABECERA = new ParameterizedRowMapper<CabeceraVO>() {
        public CabeceraVO mapRow(ResultSet rs, int arg1) throws SQLException {
            CabeceraVO obj = new CabeceraVO();

            obj.setIdProcesoPadre(rs.getInt("ID_PROCESO_PADRE"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setStatus(rs.getString("CVE_ESTATUS"));
            obj.setRegProcesados(rs.getString("REG_PROCESADOS"));
            obj.setDesMensaje(rs.getString("DES_MENSAJE"));
            obj.setEjecutadoPor(rs.getString("EJECUTADO_POR"));
            obj.setRegExitosos(rs.getString("REG_EXITOSOS"));
            obj.setRegError(rs.getString("REG_ERROR"));
            obj.setNombreArchivo(rs.getString("NOM_ARCHIVO_FISICO"));
            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));

            return obj;
        }
    };

    @SuppressWarnings("unchecked")
    public List<NotificacionVO> getConfiguraciones(String diaDeHoy, int hora, int minuto) {
        if (log.isDebugEnabled()) {
            log.debug("getConfiguraciones...");
        }

        @SuppressWarnings("rawtypes")
		ArrayList parameters = new ArrayList();

        parameters.add(diaDeHoy);
        //parameters.add(hora);
        //parameters.add(minuto);

        try {
            return (ArrayList<NotificacionVO>) jdbcTemplate.query(qryConfiguraciones, parameters.toArray(), MAPPER_NOTIFICACION);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<NotificacionVO>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void updCabecera(int idProceso, String status) {
        if (log.isDebugEnabled()) {
            log.debug("getNotificacionesEmail...");
        }
        ArrayList params = new ArrayList();
        params.add(status);
        params.add(idProceso);

        int affected = jdbcTemplate.update(updCabecera, params.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }
    
    public void updItRegistros(String proceso) {
		// TODO Auto-generated method stub
    	if (log.isDebugEnabled()) {
            log.debug("updItRegistros...");
        }
        ArrayList params = new ArrayList();
        params.add(proceso);
        log.debug("MXGES    ------ >>>>"   + qryITupdateRegistros);
        int affected = jdbcTemplate.update(qryITupdateRegistros, params.toArray());
        log.debug("Registros modificados" + affected);
        // Importante verificar que afectamos exactamente un registro.
        
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CabeceraVO> getCabeceras(String lob, int retroactividad, String proceso) {
        if (log.isDebugEnabled()) {
            log.debug("getCabeceras...");
        }
        ArrayList params = new ArrayList();

        params.add(proceso);
        params.add(lob);
        params.add(retroactividad);

        try {
            return (ArrayList<CabeceraVO>) jdbcTemplate.query(qryCabeceras, params.toArray(), MAPPER_CABECERA);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<CabeceraVO>();
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<MxgesEnvioNotificacionVo> getMxgesEnvioNotificacion(StringBuffer qry, NotificacionVO notf) {
        if (log.isDebugEnabled()) {
            log.debug("getMxgesEnvioNotificacion...");
        }
        ArrayList params = new ArrayList();
        StringBuffer ax = new StringBuffer("SELECT ( ");
        ax.append(qry).append(" ) CAD_ARCHIVO "+
					"FROM MXGES_ENVIO_NOTIFICACIONES"+
					" WHERE CVE_PROCESO_ORIGEN = (SELECT VALOR_01 FROM MXGES_CAT_VALORES"+ 
					" WHERE CVE_TABLA = 'CAT_HOMOLOGAPROC' "+
					" AND CLAVE_01 = ? )"+
					" AND CVE_ESTATUS = 'PE'"+
					" AND (TEL_MOVIL_CLIENTE IS NOT NULL OR EMAIL_CLIENTE IS NOT NULL)"+
					" AND FEC_CREACION >= TRUNC(SYSDATE - 1)");
        ///params.add(qry.toString());
        params.add(notf.getIdProceso());
        log.debug("MXGES  qry------->: " + ax.toString());
        log.debug("MXGES  notf.getIdProceso()------->: " + notf.getIdProceso());
        for(int i = 0;i<params.size();i++){
        	log.debug("MXGES------>>>>   params ["+i+"]  ----->>>   :   " + params.get(i));
        }
        setQryProcITrans(ax.toString());
        try {
            return (ArrayList<MxgesEnvioNotificacionVo>) jdbcTemplate.query(qryProcITrans, params.toArray(), MAPPER_MXGESENVIO);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<MxgesEnvioNotificacionVo>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<MensajeVO> getMensajes(int idProcesoPadre, NotificacionVO vo, String proceso) {
        ArrayList parameters = new ArrayList();
        StringBuffer stb = new StringBuffer(qryMensajes.getSqlBase());

        Properties params = qryMensajes.getOptionalParameters();

        int primeroCob = 0;

        // COBRANZA BANCARIA
        if (vo.getIdProceso().equals(ConstantesMxges.PROC_COBRBANC)) {
        	// Polizas Aceptadas
            if (vo.getTipoRespuesta().equals("A")) {
                if (primeroCob == 0) {
                    primeroCob++;
                    stb.append(" AND ( ");
                    stb.append(" ").append(params.get("aceptada"));
                } else if (primeroCob > 0) {

                    stb.append(" OR ").append(params.get("aceptada"));
                }

            }
            
        	// Polizas Rechazadas
            if (vo.getTipoRespuesta().equals("R")) {
            	
            	// Polizas con rechazo re-intentable
                if (vo.getReintentable().equals("1")) {
                    if (primeroCob == 0) {
                        primeroCob++;
                        stb.append(" AND ( ");
                        stb.append(" ").append(params.get("reintentable"));
                    } else if (primeroCob > 0) {
                        stb.append(" OR ").append(params.get("reintentable"));
                    }

                }
                
            	// Polizas con rechazo definitivo
                if (vo.getReintentable().equals("0")) {
                    if (primeroCob == 0) {
                        primeroCob++;
                        stb.append(" AND ( ");
                        stb.append(" ").append(params.get("rechazada"));
                    } else if (primeroCob > 0) {
                        stb.append(" OR ").append(params.get("rechazada"));
                    }

                }
            }
        }
        if (primeroCob > 0) {
            stb.append(" )");
        }

        int primeroCond = 0;

        if (vo.isDxn()) {
            if (primeroCond == 0) {
                primeroCond++;
                stb.append(" AND ( ");
                stb.append(" ").append(params.get("dxn"));
            } else if (primeroCond > 0) {
                stb.append(" OR ").append(params.get("dxn"));
            }
        }
        if (vo.isPagoDirecto()) {
            if (primeroCond == 0) {
                primeroCond++;
                stb.append(" AND ( ");
                stb.append(" ").append(params.get("pagodirecto"));
            } else if (primeroCond > 0) {
                stb.append(" OR ").append(params.get("pagodirecto"));
            }
        }
        if (vo.isCobBancaria()) {
            if (primeroCond == 0) {
                primeroCond++;
                stb.append(" AND ( ");
                stb.append(" ").append(params.get("cobrbanc"));
            } else if (primeroCond > 0) {
                stb.append(" OR ").append(params.get("cobrbanc"));
            }
        }

        if (primeroCond > 0) {
            stb.append(" )");
        }

        int conducto = 0;

        if (vo.isRegLabEventual()) {
            if (conducto == 0) {
                conducto++;
                stb.append(" AND ( ");
                stb.append(" ").append(params.get("eventual"));
            } else if (conducto > 0) {
                stb.append(" OR ").append(params.get("eventual"));
            }
        }

        if (vo.isRegLabBase()) {
            if (conducto == 0) {
                conducto++;
                stb.append(" AND ( ");
                stb.append(" ").append(params.get("base"));
            } else if (conducto > 0) {
                stb.append(" OR ").append(params.get("base"));
            }
        }

        if (conducto > 0) {
            stb.append(" )");
        }
        
        stb.append(" ORDER BY NOTIF.ID_REGISTRO, NOTIF.ID_PROCESO ");

        parameters.add(idProcesoPadre);
        //parameters.add(proceso);
        parameters.add(vo.getIdNotificacion());
        parameters.add(vo.getIdLob());
        parameters.add(vo.getIdMensaje());

        try {
            return (ArrayList<MensajeVO>) jdbcTemplate.query(stb.toString(), parameters.toArray(), MAPPER_MENSAJE);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<MensajeVO>();
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<MensajeVO>();
            
        }
    }

    public int getIdProcesoEnvioNuevo() {
        if (log.isDebugEnabled()) {
            log.debug("getIdProcesoEnvioNuevo...");
        }
        return jdbcTemplate.queryForInt(qryIdProcesoEnvio);
    }

    public int getRetroactividad() {
        if (log.isDebugEnabled()) {
            log.debug("getRetroactividad...");
        }
        return jdbcTemplate.queryForInt(qryRetroactividad);
    }
    
    public int getLimite(){
    	if (log.isDebugEnabled()) {
            log.debug("getLimite...");
        }
    	return jdbcTemplate.queryForInt(qryLimite);
    }

    public String getColumnaFisica(String variable) {
        if (log.isDebugEnabled()) {
            log.debug("getRetroactividad...");
            log.debug("variable -> " + variable);
        }
        ArrayList params = new ArrayList();
        params.add(variable);
        try {
            return (String) jdbcTemplate.queryForObject(qryColumnaFisica, params.toArray(), String.class);
        } catch (DataAccessException e) {
            log.error("No existe la variable");
            return null;
        }
    }

    public int getIdEnvio() {
        if (log.isDebugEnabled()) {
            log.debug("getIdEnvio...");
        }
        StringBuffer stb = new StringBuffer();
        stb.append("SELECT MAX(ID_ENVIO) ID_ENVIO FROM MXGES_INFO_ENVIOS");
        try {
            return (int) jdbcTemplate.queryForInt(stb.toString());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String getValorVariable(int idRegistro, int idProceso, String columna) {
        if (log.isDebugEnabled()) {
            log.debug("getValorVariable...");
            log.debug("idRegistro -> " + idRegistro);
            log.debug("idProceso -> " + idProceso);
            log.debug("columna -> " + columna);
        }
        StringBuffer stb = new StringBuffer();

        stb.append("SELECT ");
        stb.append(columna + " ");
        stb.append(qryValorVariable);

        ArrayList params = new ArrayList();
        params.add(idRegistro);
        params.add(idProceso);

        try {
            return (String) jdbcTemplate.queryForObject(stb.toString(), params.toArray(), String.class);
        } catch (DataAccessException e) {
            log.error("No existe la columna");
            return null;
        }
    }
    
    public String getMensajePlantilla(String variable){
    	if (log.isDebugEnabled()) {
            log.debug("getMensajePlantilla...");
            log.debug("qryITMensaje -> " + qryITMensaje);
            log.debug("idMensaje -> " + variable);
        }
        ArrayList params = new ArrayList();
        params.add(variable);
        try {
            return (String) jdbcTemplate.queryForObject(qryITMensaje, params.toArray(), String.class);
        } catch (DataAccessException e) {
            log.error("No existe el mensaje");
            return null;
        }
    }
    
    public String getProcesoNomFile(String variable){
    	if (log.isDebugEnabled()) {
            log.debug("getProcesoNomFile...");
            log.debug("qryITNombreFile -> " + qryITNombreFile);
            log.debug("idPROCESO -> " + variable);
        }
        ArrayList params = new ArrayList();
        params.add(variable);
        try {
            return (String) jdbcTemplate.queryForObject(qryITNombreFile, params.toArray(), String.class);
        } catch (DataAccessException e) {
            log.error("No existe el mensaje proceso");
            return null;
        }
    }

    public void insNuevaCabecera(final CabeceraVO vo, final int idProcesoNuevo) {
        if (log.isDebugEnabled()) {
            log.debug("insNuevaCabecera..." + vo.toString());
            log.debug("idProcesoNuevo..." + idProcesoNuevo);
        }
        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insNuevaCabecera);

                ps.setInt(1, idProcesoNuevo);
                ps.setInt(2, vo.getIdProcesoPadre());
                ps.setString(3, vo.getMedioEnvio());
                ps.setString(4, vo.getNombreArchivo());

                return ps;
            }
        });

        this.checkAffected(affected);
    }

    public void insEnvioSMS(final MensajeVO mensaje, final int idEnvio, final String destino, final String destinatario,  final String status, final String error) throws SQLException {
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insEnvioSMS);

                ps.setInt(1, idEnvio);
                ps.setString(2, destino);
                ps.setString(3, mensaje.getCuerpo());
                ps.setInt(4, mensaje.getIdRegistro());
                ps.setInt(5, mensaje.getIdProceso());
                ps.setString(6, mensaje.getIdNotificacion());
                ps.setString(7, mensaje.getIdMensaje());
                ps.setString(8, destinatario);
                ps.setString(9, status);
                ps.setString(10, mensaje.getCveProceso());
                ps.setString(11, error);
                
                return ps;
            }
        });

    }

    public void insEnvioEmail(final MensajeVO mensaje, final int idEnvio, final String destino, final String destinatario,  final String status, final String error) {

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insEnvioEmail);

                ps.setInt(1, idEnvio);
                ps.setString(2, destino);
                ps.setString(3, mensaje.getAsunto());
                ps.setString(4, mensaje.getCuerpo());
                ps.setInt(5, mensaje.getIdRegistro());
                ps.setInt(6, mensaje.getIdProceso());
                ps.setString(7, mensaje.getIdNotificacion());
                ps.setString(8, mensaje.getIdMensaje());
                ps.setString(9, destinatario);
                ps.setString(10, status);
                ps.setString(11, mensaje.getCveProceso());
                ps.setString(12, error);
                return ps;
            }
        });

        this.checkAffected(affected);
    }

    public void updRegistro(final int idProceso, final int idRegistro, final String status) {
        if (log.isDebugEnabled()) {
            log.debug("updRegistro...");
        }

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(updRegistro);
                ps.setString(1, status);
                ps.setInt(2, idRegistro);
                ps.setInt(3, idProceso);

                return ps;
            }
        });

        // Importante verificar que afectamos exactamente un registro.
    }
    
    public void updRegistrosRenew(final int idProceso, final int idRegistro, final String cveProcesoOrigen) {
        if (log.isDebugEnabled()) {
            log.debug("updRegistro...");
        }

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(updRegistrosRenew);
                
                ps.setInt(1, idProceso);
                ps.setInt(2, idRegistro);
                ps.setString(3, cveProcesoOrigen);

                return ps;
            }
        });

        // Importante verificar que afectamos exactamente un registro.
    }
    
    public void updEnvio(final String status, final String idEnvio, final String error) {
        if (log.isDebugEnabled()) {
            log.debug("updRegistro...");
        }

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(updEnvio);
                ps.setString(1, status);
                ps.setString(2, error);
                ps.setString(3, idEnvio);

                return ps;
            }
        });

        // Importante verificar que afectamos exactamente un registro.
    }   
    
    public List<NotificacionVO> getNotificacionesActivas() {
		// TODO Auto-generated method stub
		return (List<NotificacionVO>)jdbcTemplate.query(qryNotificacionesActivas, MAPPER_NOTIFICACION_ACTIVA);
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
        params.add(notificacion.getIdMensaje());
        
        log.debug("EN DAO LE PASO __________________ " + notificacion.getIdNotificacion());
        log.debug("EN DAO LE PASO __________________ " + notificacion.getIdProceso() );
        log.debug("EN DAO LE PASO __________________ " + notificacion.getIdLob());
        log.debug("EN DAO LE PASO __________________ " + notificacion.getIdMensaje());
        
        try {
            return (List<FrecuenciaVO>) jdbcTemplate.query(qryFrecuencias, params.toArray(), MAPPER_FRECUENCIA);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<FrecuenciaVO>();
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<VarProcVo> getVarProc(NotificacionVO notificacion) {
        if (log.isDebugEnabled()) {
            log.debug("getVarProc Dao.... ");
        }

        ArrayList params = new ArrayList();
        
        params.add(notificacion.getIdProceso());
        
        
        log.debug("Se PASA al DAO VAR PROC __________________ " + notificacion.getIdProceso() );
        log.debug("QUERY ........... " + qryItVarProc);
        
        try {
            return (List<VarProcVo>) jdbcTemplate.query(qryItVarProc, params.toArray(), MAPPER_VARPROC);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<VarProcVo>();
        }
    }
    
    public List<Map<String,Object>> getNotificacionesCobranzaAMail(int retroactividad, int limite){
    
        List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
        log.debug("Inicia: " + new Date().toString());
    	ArrayList params = new ArrayList();
    	params.add(retroactividad);
    	params.add(limite);
    	try{
    		return (List<Map<String,Object>>)jdbcTemplate.queryForList(qryProcCobrancA, params.toArray());
    	}catch(EmptyResultDataAccessException e){
    		return result1;
    	}
    }
    
    
    public List<Map<String,Object>> getNotificacionesCobranzaRMail(int retroactividad, int limite){
        
        List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
        log.debug("Inicia: " + new Date().toString());
    	ArrayList params = new ArrayList();
    	params.add(retroactividad);
    	params.add(limite);
    	try{
    		return (List<Map<String,Object>>)jdbcTemplate.queryForList(qryProcCobrancR, params.toArray());
    	}catch(EmptyResultDataAccessException e){
    		return result1;
    	}
    }
    

    
    public List<EnvioNotificacionVO> getNotificacionesCobranzaRMailNULL(int retroactividad){
    	ArrayList params = new ArrayList();
    	params.add(retroactividad);
    	try{
    		return (List<EnvioNotificacionVO>) jdbcTemplate.query(qryProcCobrancRNULL, params.toArray(), MAPPER_PROCECOBRANC_MAIL);
    	}catch(EmptyResultDataAccessException e){
    		return new ArrayList<EnvioNotificacionVO>();
    	}
    }
    
    public List<EnvioNotificacionVO> getNotificacionesCobranzaAMailNULL(int retroactividad){
    	ArrayList params = new ArrayList();
    	params.add(retroactividad);
    	try{
    		return (List<EnvioNotificacionVO>) jdbcTemplate.query(qryProcCobrancANULL, params.toArray(), MAPPER_PROCECOBRANC_MAIL);
    	}catch(EmptyResultDataAccessException e){
    		return new ArrayList<EnvioNotificacionVO>();
    	}
    }
    
    public void updEnvioNotificaciones(final String registro, final String proceso, final String nomina, final String poliza) {
         jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(updEnvioNotificaciones);
                ps.setString(1, registro);
                ps.setString(2, proceso);
                ps.setString(3, nomina);
                ps.setString(4, poliza);
                return ps;
            }
        });
    }
    
    public void insInfoEnvios(final String email, final String subject, final String body, final String registro, final String proceso, final String V_COL_01, final String V_COL_04, final String V_DES_MENSAJE_ERROR) {
        jdbcTemplate.update(new PreparedStatementCreator() {
           public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
               PreparedStatement ps = con.prepareStatement(insInfoEnvios);
               ps.setString(1, email);
               ps.setString(2, subject);
               ps.setString(3, body);
               ps.setString(4, registro);
               ps.setString(5, proceso);
               ps.setString(6, V_COL_01);
               ps.setString(7, V_COL_01);
               ps.setString(8, V_COL_04);
               ps.setString(9, V_DES_MENSAJE_ERROR);
               return ps;
           }
       });
   }
    
    public void insInfoEnviosNull(final String email, final String subject, final String body, final String registro, final String proceso, final String V_COL_01, final String V_DES_MENSAJE_ERROR) {
        jdbcTemplate.update(new PreparedStatementCreator() {
           public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
               PreparedStatement ps = con.prepareStatement(insInfoEnviosNull);
               ps.setString(1, email);
               ps.setString(2, subject);
               ps.setString(3, body);
               ps.setString(4, registro);
               ps.setString(5, proceso);
               ps.setString(6, V_COL_01);
               ps.setString(7, V_COL_01);
               ps.setString(8, V_DES_MENSAJE_ERROR);
               return ps;
           }
       });
   }

    public final void setQryConfiguraciones(String qryConfiguraciones) {
        this.qryConfiguraciones = qryConfiguraciones;
    }

    public final void setUpdCabecera(String updCabecera) {
        this.updCabecera = updCabecera;
    }

    public void setQryIdProcesoEnvio(String qryIdProcesoEnvio) {
        this.qryIdProcesoEnvio = qryIdProcesoEnvio;
    }

    public void setQryRetroactividad(String qryRetroactividad) {
        this.qryRetroactividad = qryRetroactividad;
    }

    public void setQryCabeceras(String qryCabeceras) {
        this.qryCabeceras = qryCabeceras;
    }

    public void setInsNuevaCabecera(String insNuevaCabecera) {
        this.insNuevaCabecera = insNuevaCabecera;
    }

    public final void setQryMensajes(JdbcQueryParameters qryMensajes) {
        this.qryMensajes = qryMensajes;
    }

    public void setQryColumnaFisica(String qryColumnaFisica) {
        this.qryColumnaFisica = qryColumnaFisica;
    }

    public void setQryValorVariable(String qryValorVariable) {
        this.qryValorVariable = qryValorVariable;
    }

    public void setInsEnvioSMS(String insEnvioSMS) {
        this.insEnvioSMS = insEnvioSMS;
    }

    public void setUpdRegistro(String updRegistro) {
        this.updRegistro = updRegistro;
    }

    public final void setInsEnvioEmail(String insEnvioEmail) {
        this.insEnvioEmail = insEnvioEmail;
    }

    public final void setUpdEnvio(String updEnvio) {
        this.updEnvio = updEnvio;
    }

	public void setQryConcurrent(String qryConcurrent) {
		this.qryConcurrent = qryConcurrent;
	}

	public void setUpdConcurrent(String updConcurrent) {
		this.updConcurrent = updConcurrent;
	}

	public void setQryAcentos(String qryAcentos) {
		this.qryAcentos = qryAcentos;
	}

	public void setQryNotificacionesActivas(String qryNotificacionesActivas) {
		this.qryNotificacionesActivas = qryNotificacionesActivas;
	}

	public void setQryFrecuencias(String qryFrecuencias) {
		this.qryFrecuencias = qryFrecuencias;
	}

	public String getQryItVarProc() {
		return qryItVarProc;
	}

	public void setQryItVarProc(String qryItVarProc) {
		this.qryItVarProc = qryItVarProc;
	}

	public void setQryProcCobrancA(String qryProcCobrancA) {
		this.qryProcCobrancA = qryProcCobrancA;
	}

	public void setUpdEnvioNotificaciones(String updEnvioNotificaciones) {
		this.updEnvioNotificaciones = updEnvioNotificaciones;
	}

	public void setInsInfoEnvios(String insInfoEnvios) {
		this.insInfoEnvios = insInfoEnvios;
	}

	public void setQryProcCobrancR(String qryProcCobrancR) {
		this.qryProcCobrancR = qryProcCobrancR;
	}

	public void setQryProcCobrancRNULL(String qryProcCobrancRNULL) {
		this.qryProcCobrancRNULL = qryProcCobrancRNULL;
	}

	public void setQryProcCobrancANULL(String qryProcCobrancANULL) {
		this.qryProcCobrancANULL = qryProcCobrancANULL;
	}

	public void setInsInfoEnviosNull(String insInfoEnviosNull) {
		this.insInfoEnviosNull = insInfoEnviosNull;
	}


	public void setQryLimite(String qryLimite) {
		this.qryLimite = qryLimite;
	}
	
	

	public String getQryProcRenew() {
		return qryProcRenew;
	}

	public void setQryProcRenew(String qryProcRenew) {
		this.qryProcRenew = qryProcRenew;
	}

	public void procesaSinDatos(String tipoEnvio, int retroactividad) {
		// TODO Auto-generated method stub
		Connection connection= null;
		try {
			connection= jdbcTemplate.getDataSource().getConnection();
			CallableStatement callableStatement =connection.prepareCall(spProcesaSinDatos);
			callableStatement.setString(1,tipoEnvio);
			callableStatement.setInt(2, retroactividad);
			callableStatement.registerOutParameter(3, Types.VARCHAR);
			
			callableStatement.executeUpdate();
			
			log.debug("Salida de sp: " +callableStatement.getString(3));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(connection!=null){
				try{
					connection.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		
	}

	public void setSpProcesaSinDatos(String spProcesaSinDatos) {
		this.spProcesaSinDatos = spProcesaSinDatos;
	}	
	
	
	
	public List<PolizaCliente> getPolizasClientesHUB(String date){

		/*ArrayList params = new ArrayList();
    	params.add(date);*/
    	
    	try{
    		return (List<PolizaCliente>) jdbcTemplate.query(qryProcRenew, MAPPER_PROCE_RENEW );
    	}catch(EmptyResultDataAccessException e){
    		return null;
    	}
    }
	
	
	public String getUpdRegistrosRenew() {
		return updRegistrosRenew;
	}

	public void setUpdRegistrosRenew(String updRegistrosRenew) {
		this.updRegistrosRenew = updRegistrosRenew;
	}

	public String getQryITupdateRegistros() {
		return qryITupdateRegistros;
	}

	public void setQryITupdateRegistros(String qryITupdateRegistros) {
		this.qryITupdateRegistros = qryITupdateRegistros;
	}

	private static ParameterizedRowMapper<PolizaCliente> MAPPER_PROCE_RENEW = new ParameterizedRowMapper<PolizaCliente>() {
		public PolizaCliente mapRow(ResultSet rs, int rowNum) throws SQLException {
			PolizaCliente obj = new PolizaCliente();
			
			obj.setProceso(Integer.parseInt(rs.getString("ID_PROCESO")));
			obj.setRegistro(Integer.parseInt(rs.getString("ID_REGISTRO")));
			obj.setCveProcesoOrigen(rs.getString("CVE_PROCESO_ORIGEN"));
			obj.setNombre(rs.getString("NOMBRE"));
			obj.setApellidoPaterno(rs.getString("PATERNO"));
			obj.setApellidoMaterno(rs.getString("MATERNO"));
			obj.setEmail(rs.getString("EMAIL"));
			obj.setFechaVigencia_column6(rs.getString("COLUMN_6_CHAR"));
			obj.setCobertura_column5(rs.getString("COLUMN_5_CHAR"));
			obj.setPoliza(rs.getString("POLIZA"));
			return obj;
		}
	};
	
	private static ParameterizedRowMapper<MxgesEnvioNotificacionVo> MAPPER_MXGESENVIO = new ParameterizedRowMapper<MxgesEnvioNotificacionVo>() {
		public MxgesEnvioNotificacionVo mapRow(ResultSet rs, int rowNum) throws SQLException {
			MxgesEnvioNotificacionVo obj = new MxgesEnvioNotificacionVo();
			
			obj.setRegistroArchivo(rs.getString("CAD_ARCHIVO"));
			log.debug("MAURO --- >>>   rs.getString('CAD_ARCHIVO') ----->>>   :   " + rs.getString("CAD_ARCHIVO"));
			log.debug("MAURO --- >>>   obj.getRegistroArchivo() ----->>>   :   " + obj.getRegistroArchivo());
			log.debug("MAURO --- >>>   obj.toString() ----->>>   :   " + obj.toString());
			return obj;
		}
	};
	
	
	
}
