package com.metlife.nm.usuarios.dao;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.usuarios.vo.UsuarioVO;

public class UsuariosDaoImpl extends JdbcDao implements UsuariosDao {

	private static final Logger log = Logger.getLogger(UsuariosDaoImpl.class);

	private String qryUsuarios;
	private String qryRoles;
	private String qryLobs;
	private String qryExiste;
	private String qryInsertUsuarios;
	private String qryUpdateUsuarios;

	private static ParameterizedRowMapper<UsuarioVO> MAPPER_USUARIOS = new ParameterizedRowMapper<UsuarioVO>() {
		public UsuarioVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			UsuarioVO usuario = new UsuarioVO();
			usuario.setUsername(rs.getString("USERNAME"));
			usuario.setNombre(rs.getString("NOMBRE"));
			String vigencia = rs.getString("VIGENCIA");
			vigencia = isBlank(vigencia) ? " " : vigencia;
			usuario.setVigencia(vigencia);
			usuario.setDescripcionRol(rs.getString("ROL"));
			usuario.setLob(rs.getString("DES_LINEA_NEGOCIO"));
			usuario.setIdlob(rs.getString("CVE_LINEA_NEGOCIO"));
			String rolId = rs.getString("CVE_ROL");
			rolId = isBlank(rolId) ? " " : rolId;
			usuario.setRolId(rolId);
			return usuario;
		}
	};

	@SuppressWarnings("unchecked")
	public List<UsuarioVO> getUsuarios() {
		if (log.isDebugEnabled()) {
			log.debug("getUsuariosDAO...");
		}
		return (List<UsuarioVO>) jdbcTemplate.query(qryUsuarios,
				MAPPER_USUARIOS);
	}

	private static ParameterizedRowMapper<CatalogoVO> MAPPER_CATALOGO = new ParameterizedRowMapper<CatalogoVO>() {
		public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CatalogoVO obj = new CatalogoVO();
			obj.setKeyTxt(rs.getString("KEY"));
			obj.setValue(rs.getString("VALUE"));

			return obj;
		}
	};

	@SuppressWarnings("unchecked")
	public List<CatalogoVO> getRoles() {
		if (log.isDebugEnabled()) {
			log.debug("getRolesDao...");
		}
		return (List<CatalogoVO>) jdbcTemplate.query(qryRoles, MAPPER_CATALOGO);
	}

	@SuppressWarnings("unchecked")
	public List<CatalogoVO> getLobs() {
		if (log.isDebugEnabled()) {
			log.debug("getLobsDao...");
		}
		return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_CATALOGO);
	}

	@SuppressWarnings("unchecked")
	public boolean existeUsername(String username) {
		if (log.isDebugEnabled()) {
			log.debug("existeUsername...");
		}
		ArrayList params = new ArrayList();
		params.add(username);
		
		try{
			jdbcTemplate.queryForObject(qryExiste, params.toArray(), String.class);
			return true;
		}catch(DataAccessException e){
			return false;
		}
	}

	
	
	public void insertar(UsuarioVO usuarioVO, UserVO userVO) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("insertar usuarioVO..." + usuarioVO.toString());
			log.debug("getUsername" + usuarioVO.getUsername());
			log.debug("getNombre" + usuarioVO.getNombre());
			log.debug("getRolId" + usuarioVO.getRolId());
			log.debug("getVigencia" + usuarioVO.getVigencia());
			log.debug("getUsername" + userVO.getUsername());
		}

		List<Object> parameters = new ArrayList<Object>();

		parameters.add(usuarioVO.getUsername().toUpperCase());
		parameters.add(usuarioVO.getNombre());
		parameters.add(usuarioVO.getRolId());
		parameters.add(usuarioVO.getVigencia());
		parameters.add(userVO.getUsername());
		parameters.add(usuarioVO.getIdlob());
		Object[] params = parameters.toArray();

		// inserta en la tabla de DM04_SEG_USUARIOS USU

		try {
			int affected = jdbcTemplate.update(qryInsertUsuarios, params);
			this.checkAffected(affected);
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException("Ya existe el username "+usuarioVO.getUsername().toUpperCase());
		}

		// Importante verificar que afectamos exactamente un registro.

	}

	public void actualizar(UsuarioVO usuarioVO, UserVO userVO) {
		if (log.isDebugEnabled()) {
			log.debug("actualizar...");
		}
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(usuarioVO.getNombre());
		parameters.add(usuarioVO.getRolId());
		parameters.add(usuarioVO.getVigencia());
		parameters.add(usuarioVO.getUsername());
		Object[] params = parameters.toArray();

		int affected = jdbcTemplate.update(qryUpdateUsuarios, params);

		// Importante verificar que afectamos exactamente un registro.
		this.checkAffected(affected);

	}

	public void setQryRoles(String qryRoles) {
		this.qryRoles = qryRoles;
	}

	public void setQryUsuarios(String qryUsuarios) {
		this.qryUsuarios = qryUsuarios;
	}

	public void setQryInsertUsuarios(String qryInsertUsuarios) {
		this.qryInsertUsuarios = qryInsertUsuarios;
	}

	public void setQryUpdateUsuarios(String qryUpdateUsuarios) {
		this.qryUpdateUsuarios = qryUpdateUsuarios;
	}

	public void setQryLobs(String qryLobs) {
		this.qryLobs = qryLobs;
	}

	public void setQryExiste(String qryExiste) {
		this.qryExiste = qryExiste;
	}

}
