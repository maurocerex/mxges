package com.metlife.nm.exclusiones.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.domain.JdbcQueryParameters;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;
import com.metlife.nm.exclusiones.vo.ExcluidosVO;

public class ExclusionesDaoImpl extends JdbcDao implements ExclusionesDao {

    private String qryRetenedores;
    private String qryLobs;
    private String qryProcesos;
    private String qrySeleccionados;
    private String qrySeleccionadosCt;
    private String qryCatSeleccionados;
    private String qryExcluidos;
    private String insExcluido;
    private String delExcluido;
    private String insExcluidoCt;
    private String delExcluidoCt;
    private String qryUnidadesDePago;
    private String qryCatSeleccionadosCT;
    private String qryUnidadesDePagoCt;
    private String qryUnidadesDePagosRet;
    private String qryUnidadesDePagoCtsRet;
    

	

	private JdbcQueryParameters qryDisponibles;

    public static ParameterizedRowMapper<LabeValueBeanCascade> LABEL_VALUE_MAPPER_RET_UP = new ParameterizedRowMapper<LabeValueBeanCascade>() {

        public LabeValueBeanCascade mapRow(ResultSet rs, int arg1) throws SQLException {
            LabeValueBeanCascade result = new LabeValueBeanCascade();

            result.setId(rs.getString(1));
            result.setLabel(rs.getString(1) + "-" + rs.getString(2));

            return result;
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

    public final void setQryUnidadesDePago(String qryUnidadesDePago) {
        this.qryUnidadesDePago = qryUnidadesDePago;
    }

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_LISTAS = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("ID_RETENEDOR") + "-" + rs.getString("ID_UNIDAD_PAGO"));
            obj.setValue(rs.getString("ID_RETENEDOR") + "-" + rs.getString("ID_UNIDAD_PAGO") + " " + rs.getString("NOM_UNIDAD_PAGO"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_SEL_EXCLUIDOS = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("ID_RETENEDOR") + "-" + rs.getString("ID_UNIDAD_PAGO"));
            obj.setValue(rs.getString("ID_RETENEDOR") + "-" + rs.getString("ID_UNIDAD_PAGO") + "-" + rs.getString("NOM_UNIDAD_PAGO"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<ExcluidosVO> MAPPER_EXCLUIDOS = new ParameterizedRowMapper<ExcluidosVO>() {
        public ExcluidosVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ExcluidosVO obj = new ExcluidosVO();

            obj.setIdRetenedor(rs.getInt("ID_RETENEDOR"));
            obj.setIdUnidadPago(rs.getInt("ID_UNIDAD_PAGO"));
            obj.setProceso(rs.getString("CVE_PROCESO"));
            obj.setLob(rs.getString("CVE_LINEA_NEGOCIO"));

            return obj;
        }
    };

    public static ParameterizedRowMapper<LabelValueBean> LABEL_VALUE_MAPPER = new ParameterizedRowMapper<LabelValueBean>() {

        public LabelValueBean mapRow(ResultSet rs, int arg1) throws SQLException {
            LabelValueBean result = new LabelValueBean();

            result.setValue(rs.getString(3) + "-" + rs.getString(1));
            result.setLabel(rs.getString(3) + "-" + rs.getString(1) + "-" + rs.getString(2));

            return result;
        }
    };

    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getUndadesPago(String retenedor,String proceso, String tipoEnvio) {
    	 ArrayList params = new ArrayList();
         params.add(proceso);
         params.add(tipoEnvio);
         params.add(retenedor);
         
         return this.jdbcTemplate.query(qryUnidadesDePago, params.toArray(), LABEL_VALUE_MAPPER);
    }

    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getUndadesPagoCt(String retenedor,String proceso, String tipoEnvio) {
    	ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(tipoEnvio);
        params.add(retenedor);
        
    	return this.jdbcTemplate.query(qryUnidadesDePagoCt, params.toArray(), LABEL_VALUE_MAPPER);
	}
    
    
    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getUndadesPago(String proceso, String tipoEnvio) {
    	 ArrayList params = new ArrayList();
         params.add(proceso);
         params.add(tipoEnvio);
         
         return this.jdbcTemplate.query(qryUnidadesDePagosRet, params.toArray(), LABEL_VALUE_MAPPER);
    }

    @SuppressWarnings("unchecked")
    public List<LabelValueBean> getUndadesPagoCt(String proceso, String tipoEnvio) {
    	ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(tipoEnvio);
        
    	return this.jdbcTemplate.query(qryUnidadesDePagoCtsRet, params.toArray(), LABEL_VALUE_MAPPER);
	}
    
    
    public LabeValueBeanCascade getRetenedores(boolean isNumeric, String filter) {
        LabeValueBeanCascade retedor = new LabeValueBeanCascade();
        String AuxQry = qryRetenedores;
        if (StringUtils.isNumeric(filter)) {
            AuxQry += "WHERE ID_RETENEDOR = ? ";
        } else {
            filter = filter.toUpperCase();
            AuxQry += "WHERE DESC_RETENEDOR = ? ";
        }
        AuxQry += "AND STAT_RET = 'A'";
        try {
            return retedor = (LabeValueBeanCascade) jdbcTemplate.queryForObject(AuxQry, new Object[] { filter }, LABEL_VALUE_MAPPER_RET_UP);
        } catch (DataAccessException e) {
            LabeValueBeanCascade lb = new LabeValueBeanCascade();
            lb.setLabel("No se encontraron retenedores");
            return lb;
        }
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getLobs() {
        if (log.isDebugEnabled()) {
            log.debug("getLobs");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryLobs, MAPPER_LOBS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getListadoProcesos(String lob) {
        if (log.isDebugEnabled()) {
            log.debug("getListadoProcesos DAO...");
        }
        ArrayList params = new ArrayList();
        params.add(lob);
        return (List<CatalogoVO>) jdbcTemplate.query(qryProcesos, params.toArray(), MAPPER_LOBS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getDisponibles(String retenedor) {
        if (log.isDebugEnabled()) {
            log.debug("getDisponibles Dao.... ");
            log.debug("retenedor => [" + retenedor + "]");
        }

        StringBuffer stb = new StringBuffer(qryDisponibles.getSqlBase());

        ArrayList parameters = new ArrayList();

        Properties params = qryDisponibles.getOptionalParameters();

        if (!StringUtils.isBlank(retenedor)) {
            stb.append(" ").append(params.get("retenedor"));
            parameters.add(retenedor);
        }

        Object[] args = parameters.toArray();

        return jdbcTemplate.query(stb.toString(), args, MAPPER_LISTAS);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getCatSeleccionados(String lob, String proceso, String tipoEnvio) {
        if (log.isDebugEnabled()) {
            log.debug("getCatSeleccionados DAO...");
            log.debug("proceso -> " + proceso);
            log.debug("lob -> " + lob);
        }
        ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(lob);
        params.add(tipoEnvio);

        try {
            return (List<CatalogoVO>) jdbcTemplate.query(qryCatSeleccionados, params.toArray(), MAPPER_SEL_EXCLUIDOS);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<CatalogoVO>();
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<CatalogoVO> getCatSeleccionadosCT(String lob, String proceso, String tipoEnvio) {
    	if (log.isDebugEnabled()) {
            log.debug("getCatSeleccionadosCT DAO...");
            log.debug("proceso -> " + proceso);
            log.debug("lob -> " + lob);
        }
        ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(lob);
        params.add(tipoEnvio);

        try {
            return (List<CatalogoVO>) jdbcTemplate.query(qryCatSeleccionadosCT, params.toArray(), MAPPER_SEL_EXCLUIDOS);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<CatalogoVO>();
        }
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ExcluidosVO> getSeleccionados(String lob, String proceso,String medioEnvio) {
        if (log.isDebugEnabled()) {
            log.debug("getSeleccionados DAO...");
            log.debug("proceso => " + proceso);
            log.debug("lob => " + lob);
        }
        ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(lob);
        params.add(medioEnvio);

        return (List<ExcluidosVO>) jdbcTemplate.query(qrySeleccionados, params.toArray(), MAPPER_EXCLUIDOS);
    }
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<ExcluidosVO> getSeleccionadosCt(String lob, String proceso,String medioEnvio) {
        if (log.isDebugEnabled()) {
            log.debug("getSeleccionados DAO...");
            log.debug("proceso => " + proceso);
            log.debug("lob => " + lob);
        }
        ArrayList params = new ArrayList();
        params.add(proceso);
        params.add(lob);
        params.add(medioEnvio);
        
        return (List<ExcluidosVO>) jdbcTemplate.query(qrySeleccionadosCt, params.toArray(), MAPPER_EXCLUIDOS);
    }

    @SuppressWarnings("unchecked")
    public List<ExcluidosVO> getListaExcluidos() {
        if (log.isDebugEnabled()) {
            log.debug("getListadoProcesos DAO...");
        }
        try {
            return (List<ExcluidosVO>) jdbcTemplate.query(qryExcluidos, MAPPER_EXCLUIDOS);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<ExcluidosVO>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void eliminar(ExcluidosVO vo) {
        if (log.isDebugEnabled()) {
            log.debug("eliminar..." + vo.toString());
        }
        ArrayList params = new ArrayList();
        params.add(vo.getIdRetenedor());
        params.add(vo.getIdUnidadPago());
        params.add(vo.getProceso());
        params.add(vo.getLob());
        params.add(vo.getMedioEnvio());

        jdbcTemplate.update(delExcluido, params.toArray());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void eliminarCt(ExcluidosVO vo) {
        if (log.isDebugEnabled()) {
            log.debug("eliminar..." + vo.toString());
        }
        ArrayList params = new ArrayList();
        params.add(vo.getIdRetenedor());
        params.add(vo.getIdUnidadPago());
        params.add(vo.getProceso());
        params.add(vo.getLob());
        params.add(vo.getMedioEnvio());

        jdbcTemplate.update(delExcluidoCt, params.toArray());
    }

    public void insertar(final ExcluidosVO vo) {
        if (log.isDebugEnabled()) {
            log.debug("insertar..." + vo.toString());
        }
        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insExcluido);
                ps.setInt(1, vo.getIdRetenedor());
                ps.setInt(2, vo.getIdUnidadPago());
                ps.setString(3, vo.getProceso());
                ps.setString(4, vo.getLob());
                ps.setString(5, vo.getMedioEnvio());

                return ps;
            }
        });
        
        this.checkAffected(affected);
    }
    
        public void insertarCt(final ExcluidosVO vo) {
            if (log.isDebugEnabled()) {
                log.debug("insertar..." + vo.toString());
            }
            int affected = jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(insExcluidoCt);
                    ps.setInt(1, vo.getIdRetenedor());
                    ps.setInt(2, vo.getIdUnidadPago());
                    ps.setString(3, vo.getProceso());
                    ps.setString(4, vo.getLob());
                    ps.setString(5, vo.getMedioEnvio());

                    return ps;
                }
            });

        this.checkAffected(affected);
    }

    public final void setQryLobs(String qryLobs) {
        this.qryLobs = qryLobs;
    }

    public final void setQryProcesos(String qryProcesos) {
        this.qryProcesos = qryProcesos;
    }

    public final void setQrySeleccionados(String qrySeleccionados) {
        this.qrySeleccionados = qrySeleccionados;
    }

    public final void setQryDisponibles(JdbcQueryParameters qryDisponibles) {
        this.qryDisponibles = qryDisponibles;
    }

    public void setQryExcluidos(String qryExcluidos) {
        this.qryExcluidos = qryExcluidos;
    }

    public void setQryCatSeleccionados(String qryCatSeleccionados) {
        this.qryCatSeleccionados = qryCatSeleccionados;
    }

    public void setInsExcluido(String insExcluido) {
        this.insExcluido = insExcluido;
    }

    public void setDelExcluido(String delExcluido) {
        this.delExcluido = delExcluido;
    }

    public final void setQryRetenedores(String qryRetenedores) {
        this.qryRetenedores = qryRetenedores;
    }

    
    public String getQryCatSeleccionadosCT() {
		return qryCatSeleccionadosCT;
	}

	public void setQryCatSeleccionadosCT(String qryCatSeleccionadosCT) {
		this.qryCatSeleccionadosCT = qryCatSeleccionadosCT;
	}

	
	public String getQryUnidadesDePagoCt() {
		return qryUnidadesDePagoCt;
	}

	public void setQryUnidadesDePagoCt(String qryUnidadesDePagoCt) {
		this.qryUnidadesDePagoCt = qryUnidadesDePagoCt;
	}
	
	public String getInsExcluidoCt() {
		return insExcluidoCt;
	}

	public void setInsExcluidoCt(String insExcluidoCt) {
		this.insExcluidoCt = insExcluidoCt;
	}

	public String getDelExcluidoCt() {
		return delExcluidoCt;
	}

	public void setDelExcluidoCt(String delExcluidoCt) {
		this.delExcluidoCt = delExcluidoCt;
	}
	
	public String getQrySeleccionadosCt() {
		return qrySeleccionadosCt;
	}

	public void setQrySeleccionadosCt(String qrySeleccionadosCt) {
		this.qrySeleccionadosCt = qrySeleccionadosCt;
	}

	public String getQryUnidadesDePagosRet() {
		return qryUnidadesDePagosRet;
	}

	public void setQryUnidadesDePagosRet(String qryUnidadesDePagosRet) {
		this.qryUnidadesDePagosRet = qryUnidadesDePagosRet;
	}
	
	public String getQryUnidadesDePagoCtsRet() {
		return qryUnidadesDePagoCtsRet;
	}

	public void setQryUnidadesDePagoCtsRet(String qryUnidadesDePagoCtsRet) {
		this.qryUnidadesDePagoCtsRet = qryUnidadesDePagoCtsRet;
	}

}
