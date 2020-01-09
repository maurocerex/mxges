package com.metlife.nm.cancelaciones.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;
@Service(value = BeanNames.CancelacionesDao)
public class CancelacionesDaoImpl extends JdbcDao implements CancelacionesDao {

	private String qryRetainer;
	private String qryPayUnit;
	private StringBuffer stb;
	private JdbcQueryParameters qryCancelacionUP;
	
	private static ParameterizedRowMapper<CatalogoVO> MAPPER_RETAINER = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };
    private static ParameterizedRowMapper<CatalogoVO> MAPPER_PAY_UNIT = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CatalogoVO getRetainer(String retenedor) {
		if (log.isDebugEnabled()) {
            log.debug("getRetainer CancelacionesDao...");
            log.debug("Retenedor: " + retenedor);
        }
		
		CatalogoVO retainer = new CatalogoVO();
		ArrayList params = new ArrayList();
		params.add(retenedor);
		try{
			retainer= (CatalogoVO) jdbcTemplate.queryForObject(qryRetainer, new Object[] { retenedor }, MAPPER_RETAINER);
		}catch(DataAccessException e){
			log.error("No se obtuvieron resultados");
			retainer.setValue("No se obtuvieron resultados");
		}catch (Exception e){
			log.error("No se obtuvieron resultados");
			retainer.setValue("No se obtuvieron resultados");
		}
		return retainer;
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CatalogoVO> getPayUnit(String retenedor) {
		if (log.isDebugEnabled()) {
            log.debug("getRetainer CancelacionesDao...");
        }
		ArrayList params = new ArrayList();
		params.add(retenedor);
		try{
			
			return (List<CatalogoVO>) jdbcTemplate.query(qryPayUnit, params.toArray(), MAPPER_PAY_UNIT);
			
		}catch(DataAccessException e){
			log.error("No se obtuvieron resultados");
			return null;
		}catch(Exception e){
			log.error("No se obtuvieron resultados");
			return null;
		}
	}
	
	public String getCancelaNotif(final String retenedor, final String retenedorCT,
			final String unidadPago, final String unidadPagoCT, final String fechaInicio,
			final String fechaFin) {
		if (log.isDebugEnabled()) {
            log.debug("Metodo CancelaNotif en CancelacionDao");
            log.debug("retenedor: " + retenedor);
            log.debug("retenedor CT: " + retenedorCT);
            log.debug("unidad de pago: " + unidadPago);
            log.debug("unidad de pago CT: " + unidadPagoCT);
            log.debug("fecha inicial: " + fechaInicio);
            log.debug("fecha final: " + fechaFin);
        }
		
		String RegAfected = "";
		Properties params = new Properties();
		int afected = 0;
		try{
		
			stb = new StringBuffer(qryCancelacionUP.getSqlBase());
			params = qryCancelacionUP.getOptionalParameters();
			
			if (!StringUtils.isBlank(retenedor)) {
				 stb.append(" ").append(params.get("retenedor"));
				 stb.append(" ").append(retenedor);
			}
			if (!StringUtils.isBlank(unidadPago)) {
				stb.append(" ").append(params.get("unidadPago"));
				stb.append(" ").append(unidadPago);
			}
			if (!StringUtils.isBlank(retenedorCT)) {
				stb.append(" ").append(params.get("retenedorCT"));
				stb.append(" ").append(retenedorCT);
			}
			if (!StringUtils.isBlank(unidadPagoCT)) {
				stb.append(" ").append(params.get("unidadPagoCT"));
				stb.append(" ").append(unidadPagoCT);
			}
			log.debug(stb.toString());
			afected = jdbcTemplate.update(new PreparedStatementCreator() {
	            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                PreparedStatement ps = con.prepareStatement(stb.toString());
	                ps.setString(1, fechaInicio);
	                ps.setString(2, fechaFin);
	                return ps;
	            }
	        });
		}catch(Exception e){
			e.printStackTrace();
			RegAfected = "No se pudo realizar la cancelacion";
			return RegAfected;
		}
		if(afected>0){
			RegAfected = "Se Cancelaron " + afected + " Notificaciones";
		}else{
			RegAfected = "No se encontraron notificaciones para cancelar";
		}
		return RegAfected;
	}

	public void setQryRetainer(String qryRetainer) {
		this.qryRetainer = qryRetainer;
	}
	
	public void setQryPayUnit(String qryPayUnit) {
		this.qryPayUnit = qryPayUnit;
	}

	public void setQryCancelacionUP(JdbcQueryParameters qryCancelacionUP) {
		this.qryCancelacionUP = qryCancelacionUP;
	}

	
	
}
