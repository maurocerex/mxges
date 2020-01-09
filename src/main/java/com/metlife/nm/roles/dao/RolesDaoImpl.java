package com.metlife.nm.roles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.roles.vo.PantallasVO;

public class RolesDaoImpl extends JdbcDao implements RolesDao {

    
    private String qryPantallas;
    private String updPantalla;
    
    private static ParameterizedRowMapper<PantallasVO> MAPPER_PANTALLAS = new ParameterizedRowMapper<PantallasVO>() {

        public PantallasVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PantallasVO pantalla = new PantallasVO();
            
            pantalla.setIdPantalla(rs.getString("ID_PANTALLA"));
            pantalla.setMenu(rs.getString("MENU"));
            pantalla.setPantalla(rs.getString("PANTALLA"));
            if(rs.getString("VISIBLE").equals("0")){
                pantalla.setEdoIni(false);
                pantalla.setAsig(false);
            }else{
                pantalla.setEdoIni(true);
                pantalla.setAsig(true);
            }
            pantalla.setDescripcion(rs.getString("OBSERVACIONES"));
            return pantalla;
        }
    };
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<PantallasVO> getPantallas(String idRol) {
        ArrayList<String> parameters = new ArrayList();

        parameters.add(idRol);
        
        return (List<PantallasVO>) jdbcTemplate.query(qryPantallas, parameters.toArray(), MAPPER_PANTALLAS);
    }

   
   

    public void updatePant(final PantallasVO pant) {
        if (log.isDebugEnabled()) {
            log.debug("reasignar..."+pant.toString());
        }

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(updPantalla);
                if(pant.isAsig()){
                    ps.setInt(1, 1);
                }else{
                    ps.setInt(1, 0);
                }
                ps.setString(2, pant.getRolId());
                ps.setString(3, pant.getIdPantalla());
                
                return ps;
            }
        });

        this.checkAffected(affected);
    }




    public void setQryPantallas(String qryPantallas) {
        this.qryPantallas = qryPantallas;
    }
    public void setUpdPantalla(String updPantalla) {
        this.updPantalla = updPantalla;
    }
}
