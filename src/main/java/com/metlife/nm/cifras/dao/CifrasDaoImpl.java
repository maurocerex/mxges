package com.metlife.nm.cifras.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.metlife.nm.cifras.vo.CifrasVO;
import com.metlife.nm.cifras.vo.ReenvioVO;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;

@Service (value = BeanNames.CifrasDao)
public class CifrasDaoImpl extends JdbcDao implements CifrasDao {
 
    private String qryLobs;
    private String qryProcesos;
    private JdbcQueryParameters qryCifras;
    private JdbcQueryParameters qryCifrasPrevio;
    private String qryDetalle;
    private JdbcQueryParameters qryReporte;
    private String qryReportePrevio;
    private String qryCatMedio;
    private String qryVigencia;
    private String qryRegistrosReenvio;
    private String updEnvio;
    private String qryRetroactividad;
    private String qryConsultaCA;
    private String qryConsultaNR;
    private String qryReporteNR;
    private String qryReporteCA;
    private String updateNofityNull;
    private static boolean band = true;
    
    private static ParameterizedRowMapper<CatalogoVO> MAPPER_LOBS = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<CifrasVO> MAPPER_CIFRAS = new ParameterizedRowMapper<CifrasVO>() {
        public CifrasVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CifrasVO obj = new CifrasVO();

            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setStatusEnvio(rs.getString("CVE_ESTATUS"));
            obj.setLobDesc(rs.getString("DES_LINEA_NEGOCIO"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setProcesoDesc(rs.getString("DES_PROCESO"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setNoRegistros(rs.getString("REG_TOTAL"));
            obj.setPorcentaje(rs.getString("PORCENTAJE"));
            obj.setTotalEnviados(rs.getString("ENVIADOS"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<ReenvioVO> MAPPER_FOR_UPDATE = new ParameterizedRowMapper<ReenvioVO>() {
        public ReenvioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReenvioVO obj = new ReenvioVO();

            obj.setIdProceso(rs.getString("ID_PROCESO"));
            obj.setIdEnvio(rs.getString("ID_ENVIO"));
            obj.setIdRegistro(rs.getString("ID_REGISTRO"));
            obj.setIdRegistro(rs.getString("ID_REGISTRO"));
            obj.setLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setCveProceso(rs.getString("CVE_PROCESO"));
            obj.setMedioEnvio(rs.getString("CVE_MEDIO_ENVIO"));
            obj.setSubject(rs.getString("SUBJECT"));
            obj.setDestinatario(rs.getString("ENVIO_TO"));
            obj.setBody(rs.getString("BODY"));
            
            return obj;
        }
    };
    
    private static ParameterizedRowMapper<CifrasVO> MAPPER_CIFRAS_DETALLE = new ParameterizedRowMapper<CifrasVO>() {
        public CifrasVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CifrasVO obj = new CifrasVO();

            
            obj.setPoliza(rs.getString("ID_POLIZA"));
            obj.setProcesoDesc(rs.getString("DES_PROCESO"));
            obj.setStatusEnvio(rs.getString("ESTATUS_ENVIO"));
            obj.setNombre(rs.getString("NOMBRE_CLIENTE"));
            obj.setLobDesc(rs.getString("DES_LINEA_NEGOCIO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            //RFC
            //HOMOCLAVE
            //CURP
            //ID_NOMINAL
            obj.setTelefono(rs.getString("TEL_CLIENTE"));
            obj.setMail(rs.getString("EMAIL_CLIENTE"));
            obj.setRefBancaria(rs.getString("REF_BANCARIA"));
            //CVE_PROMOTORIA1
            //CVEAPODERADO1
            obj.setIdUnidadPago(rs.getString("ID_UNIDAD_PAGO"));
            obj.setIdRetenedor(rs.getString("ID_RETENEDOR"));
            obj.setCentroTrabajo(rs.getString("CENTRO_TRABAJO"));
            //ID_RET_CET
            obj.setMedioEnvio(rs.getString("TIPO_MENSAJE"));
            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setMontoRecibido(rs.getInt("MONTO_RECIBO"));
            obj.setRecibosPendientes(rs.getString("NUM_RECIBOS_PENDIENTES"));
            
            obj.setIdPromotoria(rs.getString("CVE_PROMOTORIA"));
            obj.setIdAgente(rs.getString("CVE_APODERADO"));
            obj.setDetalleStatus(rs.getString("DES_MENSAJE_ERROR"));
            obj.setDetalleNotificacion(rs.getString("ID_DES_MSJ"));

            return obj;
        }
    };

    public int getVigencia() {
        if (log.isDebugEnabled()) {
            log.debug("getLobs");
        }
        try {
            return jdbcTemplate.queryForInt(qryVigencia);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No se ha configurado el rango de busqueda.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getLobs() {
        if (log.isDebugEnabled()) {
            log.debug("getLobs");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_LOBS);
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getCatMedio() {
        if (log.isDebugEnabled()) {
            log.debug("getCatMedios");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatMedio, MAPPER_LOBS);
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getListadoProcesos() {
        if (log.isDebugEnabled()) {
            log.debug("getListadoProcesos DAO...");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryProcesos, MAPPER_LOBS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CifrasVO> getCifras(String proceso, String medio, String fecInicio, String fecFin, String tipoReporte) {
        if (log.isDebugEnabled()) {
            log.debug("getCifras Dao.... ");
            log.debug("proceso => [" + proceso + "]");
            log.debug("medio => [" + medio + "]");
            log.debug("fecInicio => [" + fecInicio + "]");
            log.debug("fecFin => [" + fecFin + "]");
            log.debug("Tipo Reporte => [" + tipoReporte + "]");
        }

        StringBuffer stb = null;
        ArrayList parameters = new ArrayList();
        Properties params = new Properties();
        
        parameters.add(fecInicio);
        parameters.add(fecFin);
        
        if(tipoReporte.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_PENDIENTES)){
        	stb = new StringBuffer(qryCifrasPrevio.getSqlBase());
        	params = qryCifrasPrevio.getOptionalParameters();
        	if (!StringUtils.isBlank(proceso)) {
                stb.append(" ").append(params.get("proceso"));
                parameters.add(proceso);
            }
            stb.append(" ").append(qryCifrasPrevio.getSqlLast());
        }else if(tipoReporte.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL)){
        	stb = new StringBuffer(qryCifras.getSqlBase());
        	params = qryCifras.getOptionalParameters();
        	if (!StringUtils.isBlank(proceso)) {
                stb.append(" ").append(params.get("proceso"));
                parameters.add(proceso);
            }
            if (!StringUtils.isBlank(medio)) {
                stb.append(" ").append(params.get("medio"));
                parameters.add(medio);
            }
            stb.append(" ").append(qryCifras.getSqlLast());
        }else if(tipoReporte.equals(ConstantesMxges.TIPO_REPORTE_SMS)){
        	if(proceso.equals("PROC_REPORTE_NR")){
        		stb = new StringBuffer(qryConsultaNR);
        	}else{
        		stb = new StringBuffer(qryConsultaCA);
        	}
        }
        
        Object[] args = parameters.toArray();
        return jdbcTemplate.query(stb.toString(), args, MAPPER_CIFRAS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CifrasVO> getDetalle(String proceso, String medio, String fecInicio, String fecFin) {
        if (log.isDebugEnabled()) {
            log.debug("getDetalle DAO...");
            log.debug("proceso -> " + proceso);
            log.debug("medio -> " + medio);
            log.debug("fecInicio -> " + fecInicio);
            log.debug("fecFin -> " + fecFin);
        }
        ArrayList params = new ArrayList();
        params.add(medio);
        params.add(proceso);
        params.add(fecInicio);
        params.add(fecFin);

        return (List<CifrasVO>) jdbcTemplate.query(qryDetalle, params.toArray(), MAPPER_CIFRAS_DETALLE);
    }
        

    private static ParameterizedRowMapper<String[]> LINEAS = new ParameterizedRowMapper<String[]>() {
        public String[] mapRow(ResultSet rs, int rowNum) throws SQLException {
            String[] obj = new String[27];
            for (int i = 0; i < 27; i++) {
            	if(band){
            		log.debug("llega de BD: " + new Date().toString());
            		band = false;
            	}
                if(rs.getString(i + 1)== null){
                    obj[i] = "";
                }else{
                    obj[i] = rs.getString(i + 1);
                }
            }
            return obj;
        }
    };

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<Map<String,Object>> getReporte(String proceso, String medio, String fecIni, String fecFin, String tipoReport) {
        if (log.isDebugEnabled()) {
            log.debug("getDetalle DAO...");
            log.debug("proceso -> " + proceso);
            log.debug("medio -> " + medio);
            log.debug("fecha inicial -> " + fecIni);
            log.debug("fecha final -> " + fecFin);
            log.debug("tipo reporte -> " + tipoReport);
        }
        
        List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
        
        StringBuffer stb = null;
        ArrayList params = new ArrayList();
        Properties paramsProp = new Properties();
                
        log.debug("Inicia: " + new Date().toString());
        if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL)){ 
        	
        	params.add(medio);
        	params.add(fecIni);
        	params.add(fecFin);
            stb = new StringBuffer(qryReporte.getSqlBase());
            paramsProp = qryReporte.getOptionalParameters();
        	if (!StringUtils.isBlank(proceso)) {
                stb.append(" ").append(paramsProp.get("proceso"));
                params.add(proceso);
            }
        	stb.append(" ").append(qryReporte.getSqlLast());
        	
        	result1 = (List<Map<String,Object>>)jdbcTemplate.queryForList(stb.toString(), params.toArray());
        }else if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_PENDIENTES)){
        	params.add(proceso);
            params.add(fecIni);
            params.add(fecFin);
        	result1 = (List<Map<String,Object>>)jdbcTemplate.queryForList(qryReportePrevio, params.toArray());
        }else if(tipoReport.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_SMS)){
        	params.add(fecIni);
            params.add(fecFin);
            if(proceso.equals("PROC_REPORTE_NR")){
            	result1 = (List<Map<String,Object>>)jdbcTemplate.queryForList(qryReporteNR, params.toArray());
            }else if(proceso.equals("PROC_REPORTE_CA")){
            	result1 = (List<Map<String,Object>>)jdbcTemplate.queryForList(qryReporteCA, params.toArray());
            }
        }
        
        return result1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ReenvioVO> getRegistrosReenvio(String idProceso, String medio, String fecInicio, String fecFin) {
        ArrayList params = new ArrayList();

        params.add(medio);
        params.add(getRetroactividad());
        params.add(idProceso);
        params.add(fecInicio);
        params.add(fecFin);
        
        return (List<ReenvioVO>) jdbcTemplate.query(qryRegistrosReenvio, params.toArray(), MAPPER_FOR_UPDATE);
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void cambiaEstatus(String idProceso, String idEnvio) {
        
        ArrayList params = new ArrayList();
        params.add(idProceso);
        params.add(idEnvio);
        
        jdbcTemplate.update(updEnvio, params.toArray());
    }

    public int updateCC(String tipoReporte){
    	int updatenotif = 0;
    	try{
	    	log.debug("Actulizamos antes de todos los valores nulos de las cifras de control");
	    	updatenotif = jdbcTemplate.update(updateNofityNull);
	    	log.debug("Se actualizaron: " + updatenotif + " registros que tenian valores nulos");
    	}catch (Exception e){
    		e.printStackTrace();
    		return updatenotif;
    	}
    	return updatenotif;   	
    }
    
    public int getRetroactividad() {
        if (log.isDebugEnabled()) {
            log.debug("getRetroactividad...");
        }
        return jdbcTemplate.queryForInt(qryRetroactividad);
    }
    
    public void setQryLobs(String qryLobs) {
        this.qryLobs = qryLobs;
    }

    public void setQryProcesos(String qryProcesos) {
        this.qryProcesos = qryProcesos;
    }

    public void setQryCifras(JdbcQueryParameters qryCifras) {
        this.qryCifras = qryCifras;
    }

    public void setQryDetalle(String qryDetalle) {
        this.qryDetalle = qryDetalle;
    }

    public void setQryReporte(JdbcQueryParameters qryReporte) {
        this.qryReporte = qryReporte;
    }

    public void setQryCatMedio(String qryCatMedio) {
        this.qryCatMedio = qryCatMedio;
    }

    public final void setQryVigencia(String qryVigencia) {
        this.qryVigencia = qryVigencia;
    }

    public final void setQryRegistrosReenvio(String qryRegistrosReenvio) {
        this.qryRegistrosReenvio = qryRegistrosReenvio;
    }

    public final void setUpdEnvio(String updEnvio) {
        this.updEnvio = updEnvio;
    }

    public final void setQryRetroactividad(String qryRetroactividad) {
        this.qryRetroactividad = qryRetroactividad;
    }
	/**
	 * M�todo Setter para la consulta del reporte de notificaciones previas
	 * Mejora 2076540 soluci�n Raiz
	 * @param qryCifrasPrevio
	 */
	public void setQryCifrasPrevio(JdbcQueryParameters qryCifrasPrevio) {
		this.qryCifrasPrevio = qryCifrasPrevio;
	}

	public void setQryReportePrevio(String qryReportePrevio) {
		this.qryReportePrevio = qryReportePrevio;
	}

	public void setQryConsultaCA(String qryConsultaCA) {
		this.qryConsultaCA = qryConsultaCA;
	}

	public void setQryConsultaNR(String qryConsultaNR) {
		this.qryConsultaNR = qryConsultaNR;
	}

	public void setQryReporteNR(String qryReporteNR) {
		this.qryReporteNR = qryReporteNR;
	}

	public void setQryReporteCA(String qryReporteCA) {
		this.qryReporteCA = qryReporteCA;
	}

	public void setUpdateNofityNull(String updateNofityNull) {
		this.updateNofityNull = updateNofityNull;
	}

	
}
