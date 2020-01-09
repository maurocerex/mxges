package com.metlife.nm.prioridad.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.prioridad.vo.ProcesoVO;

public class PrioridadDaoImpl extends JdbcDao implements PrioridadDao {

	private static final Logger log = Logger.getLogger(PrioridadDaoImpl.class);

	private String qryLobs;
	private String qryProcesos;
	private String updProceso;
	private static ParameterizedRowMapper<CatalogoVO> MAPPER_LOBS = new ParameterizedRowMapper<CatalogoVO>() {
		public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CatalogoVO obj = new CatalogoVO();

			obj.setKeyTxt(rs.getString("KEY"));
			obj.setValue(rs.getString("VALUE"));

			return obj;
		}
	};

	private static ParameterizedRowMapper<ProcesoVO> MAPPER_PROCESO = new ParameterizedRowMapper<ProcesoVO>() {
		public ProcesoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProcesoVO obj = new ProcesoVO();
			obj.setIdProceso(rs.getString("CVE_PROCESO"));
			obj.setProcesoDesc(rs.getString("DES_PROCESO"));
			obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
			obj.setLobDesc(rs.getString("DES_LINEA_NEGOCIO"));
			if (rs.getString("ESTATUS").equals("A")) {
				obj.setStatus(true);
			} else {
				obj.setStatus(false);
			}
			if (rs.getString("SW_NOTIFICA_REPORTE").equals("1")) {
				obj.setNotifica(true);
			} else {
				obj.setNotifica(false);
			}

			obj.setPrioridad(rs.getInt("PRIORIDAD"));

			return obj;
		}
	};

	@SuppressWarnings("unchecked")
	public List<CatalogoVO> getLobs() {
		if (log.isDebugEnabled()) {
			log.debug("getLobs");
		}
		return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_LOBS);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ProcesoVO> getProcesosByIdLob(String idlob) {
		log.debug("getLobs");
		ArrayList params = new ArrayList();
		params.add(idlob);
		return (List<ProcesoVO>) jdbcTemplate.query(qryProcesos, params.toArray(), MAPPER_PROCESO);
	}

	public void updatePant(final ProcesoVO tmp) {
		if (log.isDebugEnabled()) {
			log.debug("updatePant..." + tmp.toString());
		}

		int affected = jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(updProceso);

				ps.setInt(1, tmp.getPrioridad());
				if (tmp.isNotifica()) {
					ps.setString(2, "1");
				} else {
					ps.setString(2, "0");
				}
				ps.setString(3, tmp.getIdProceso());
				ps.setString(4, tmp.getIdLob());

				return ps;
			}
		});

		this.checkAffected(affected);
	}

	public void setQryLobs(String qryLobs) {
		this.qryLobs = qryLobs;
	}

	public void setQryProcesos(String qryProcesos) {
		this.qryProcesos = qryProcesos;
	}

	public final void setUpdProceso(String updProceso) {
		this.updProceso = updProceso;
	}

}
