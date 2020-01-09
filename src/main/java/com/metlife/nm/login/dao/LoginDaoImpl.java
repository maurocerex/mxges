package com.metlife.nm.login.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.UserVO;

public class LoginDaoImpl extends JdbcDao implements LoginDao {

    
    private String qryUsuarioPorUsername;
    private String qryPantallasPorRol;
    
    private static ParameterizedRowMapper<UserVO> MAPPER_USR = new ParameterizedRowMapper<UserVO>() {
        public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserVO obj = new UserVO();
            
            obj.setUsername(rs.getString("USERNAME"));
            obj.setNombre(rs.getString("NOMBRE"));
            obj.setRol(rs.getString("ROL"));
            obj.setRolId(rs.getString("ROLID"));
            boolean vigente = "A".equals(rs.getString("VIGENCIA"));
            obj.setVigente(vigente);
            return obj;
        }

    };
    
    private static ParameterizedRowMapper<String> MAPPER_MENU = new ParameterizedRowMapper<String>() {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("PANTALLAID");
        }
    };
    
    
    @SuppressWarnings("unchecked")
    public UserVO getUsuarioPorUsername(String username) {
        if (log.isDebugEnabled()) {
            log.debug("getUsuarioDao...");
        }
        ArrayList params = new ArrayList();
        params.add(username);
        try{
        	return (UserVO) jdbcTemplate.queryForObject(qryUsuarioPorUsername, params.toArray(), MAPPER_USR);
        }catch(EmptyResultDataAccessException e){
        	return null;
        }
        
         
    }

    public Map<String, String> getPantallasPorRol(String rolId) {
        if (log.isDebugEnabled()) {
            log.debug("getPantallasPorRol...");
        }
        List<String> listaPantallas =jdbcTemplate.query(qryPantallasPorRol, new Object[]{rolId}, MAPPER_MENU);
        return toMap(listaPantallas);
    }
    
    private Map<String, String> toMap(List<String> stringList) {
        Map<String, String> map = new HashMap<String, String>();
        for (String cadena: stringList) {
            map.put(cadena, null);
        }
        return map;
    }
    
    public void setQryUsuarioPorUsername(String qryUsuarioPorUsername) {
        this.qryUsuarioPorUsername = qryUsuarioPorUsername;
    }

    public void setQryPantallasPorRol(String qryPantallasPorRol) {
        this.qryPantallasPorRol = qryPantallasPorRol;
    }

   

}
