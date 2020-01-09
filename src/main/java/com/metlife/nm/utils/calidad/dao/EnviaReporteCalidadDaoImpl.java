package com.metlife.nm.utils.calidad.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.utils.calidad.vo.DestinatarioVO;
import com.metlife.nm.utils.calidad.vo.RegistrosVO;

public class EnviaReporteCalidadDaoImpl extends JdbcDao implements EnviaReporteCalidadDao {

    private static final Logger log = Logger.getLogger(EnviaReporteCalidadDaoImpl.class);

    private String qryAgentes;
    public final void setQryDirectorio(String qryDirectorio) {
        this.qryDirectorio = qryDirectorio;
    }

    private String qryPromotorias;
    private String qryRegistrosAgente;
    private String qryRegistrosPromotor;
    private String qryDirectorio;

    private static ParameterizedRowMapper<DestinatarioVO> MAPPER_AGENTES = new ParameterizedRowMapper<DestinatarioVO>() {
        public DestinatarioVO mapRow(ResultSet rs, int arg1) throws SQLException {
            DestinatarioVO obj = new DestinatarioVO();

            obj.setAgente(rs.getString("CVE_APODERADO"));
            obj.setPromotoria(rs.getString("CVE_PROMOTORIA"));
            obj.setMailAgente(rs.getString("EMAIL_APODERADO"));

            return obj;
        }
    };
    private static ParameterizedRowMapper<DestinatarioVO> MAPPER_PROMOTORIA = new ParameterizedRowMapper<DestinatarioVO>() {
        public DestinatarioVO mapRow(ResultSet rs, int arg1) throws SQLException {
            DestinatarioVO obj = new DestinatarioVO();

            obj.setPromotoria(rs.getString("CVE_PROMOTORIA"));
            obj.setMailPromotoria(rs.getString("EMAIL_PROMOTORIA"));
            return obj;
        }
    };
    private static ParameterizedRowMapper<RegistrosVO> MAPPER_REGISTROS = new ParameterizedRowMapper<RegistrosVO>() {
        public RegistrosVO mapRow(ResultSet rs, int arg1) throws SQLException {
            RegistrosVO obj = new RegistrosVO();
            obj.setRetenedor(rs.getString("ID_RETENEDOR_CT"));
            obj.setUnidadPago(rs.getString("ID_UNIDAD_PAGO_CT"));
            obj.setPoliza(rs.getString("ID_POLIZA"));
            obj.setNombre(rs.getString("NOMBRE_CLIENTE"));
            obj.setTelefono(rs.getString("TEL_MOVIL_CLIENTE"));
            obj.setMail(rs.getString("EMAIL_CLIENTE"));
            obj.setRefBancaria(rs.getString("REF_BANCARIA"));
            obj.setAgenteVendedor(rs.getString("CVE_APODERADO"));
            obj.setConCobro(rs.getString("CONDUCTO_COBRO"));
            obj.setTipoMensaje(rs.getString("TIPOMENSAJE"));
            obj.setProceso(rs.getString("CVE_PROCESO"));
            obj.setEstatus(rs.getString("CVE_ESTATUS"));
            obj.setDetalle(rs.getString("DETALLE"));
            return obj;
        }
    };
    
    private static ParameterizedRowMapper<HashMap<String, String>> MAPPER_MAP_REGISTROS_AG = new ParameterizedRowMapper<HashMap<String, String>>() {
        public HashMap<String, String> mapRow(ResultSet rs, int arg1) throws SQLException {
            HashMap<String, String> obj = new HashMap<String, String>();
            
            
            if (rs.getString("CVE_PROMOTORIA") != null) {
                obj.put("promotoria", rs.getString("CVE_PROMOTORIA"));
            } else {
                obj.put("promotoria", "");
            }
            if (rs.getString("ID_RETENEDOR_CT") != null) {
                obj.put("retenedor", rs.getString("ID_RETENEDOR_CT"));
            } else {
                obj.put("retenedor", "");
            }
            if (rs.getString("ID_UNIDAD_PAGO_CT") != null) {
                obj.put("unidadPago", rs.getString("ID_UNIDAD_PAGO_CT"));
            } else {
                obj.put("unidadPago", "");
            }
            if (rs.getString("ID_POLIZA") != null) {
                obj.put("poliza", rs.getString("ID_POLIZA"));
            } else {
                obj.put("poliza", "");
            }
            if (rs.getString("NOMBRE_CLIENTE") != null) {
                obj.put("nombre", rs.getString("NOMBRE_CLIENTE"));
            } else {
                obj.put("nombre", "");
            }
            if (rs.getString("TEL_MOVIL_CLIENTE") != null) {
                obj.put("telefono", rs.getString("TEL_MOVIL_CLIENTE"));
            } else {
                obj.put("telefono", "");
            }
            if (rs.getString("EMAIL_CLIENTE") != null) {
                obj.put("mail", rs.getString("EMAIL_CLIENTE"));
            } else {
                obj.put("mail", "");
            }
            if (rs.getString("REF_BANCARIA") != null) {
                obj.put("refBancaria", rs.getString("REF_BANCARIA"));
            } else {
                obj.put("refBancaria", "");
            }
            if (rs.getString("CVE_APODERADO") != null) {
                obj.put("agenteVendedor", rs.getString("CVE_APODERADO"));
            } else {
                obj.put("agenteVendedor", "");
            }
            if (rs.getString("CONDUCTO_COBRO") != null) {
                obj.put("conCobro", rs.getString("CONDUCTO_COBRO"));
            } else {
                obj.put("conCobro", "");
            }
            if (rs.getString("TIPOMENSAJE") != null) {
                obj.put("tipoMensaje", rs.getString("TIPOMENSAJE"));
            } else {
                obj.put("tipoMensaje", "");
            }
            if (rs.getString("CVE_PROCESO") != null) {
                obj.put("proceso", rs.getString("CVE_PROCESO"));
            } else {
                obj.put("proceso", "");
            }
            if (rs.getString("CVE_ESTATUS") != null) {
                obj.put("estatus", rs.getString("CVE_ESTATUS"));
            } else {
                obj.put("estatus", "");
            }
            if (rs.getString("DETALLE") != null) {
                obj.put("detalle", rs.getString("DETALLE"));
            } else {
                obj.put("detalle", "");
            }
            return obj;
        }
    };
    
    
    private static ParameterizedRowMapper<HashMap<String, String>> MAPPER_MAP_REGISTROS_PROM = new ParameterizedRowMapper<HashMap<String, String>>() {
        public HashMap<String, String> mapRow(ResultSet rs, int arg1) throws SQLException {
            HashMap<String, String> obj = new HashMap<String, String>();
            if (rs.getString("CVE_PROMOTORIA") != null) {
                obj.put("promotoria", rs.getString("CVE_PROMOTORIA"));
            } else {
                obj.put("promotoria", "");
            }
            if (rs.getString("ID_RETENEDOR_CT") != null) {
                obj.put("retenedor", rs.getString("ID_RETENEDOR_CT"));
            } else {
                obj.put("retenedor", "");
            }
            if (rs.getString("ID_UNIDAD_PAGO_CT") != null) {
                obj.put("unidadPago", rs.getString("ID_UNIDAD_PAGO_CT"));
            } else {
                obj.put("unidadPago", "");
            }
            if (rs.getString("ID_POLIZA") != null) {
                obj.put("poliza", rs.getString("ID_POLIZA"));
            } else {
                obj.put("poliza", "");
            }
            if (rs.getString("NOMBRE_CLIENTE") != null) {
                obj.put("nombre", rs.getString("NOMBRE_CLIENTE"));
            } else {
                obj.put("nombre", "");
            }
            if (rs.getString("TEL_MOVIL_CLIENTE") != null) {
                obj.put("telefono", rs.getString("TEL_MOVIL_CLIENTE"));
            } else {
                obj.put("telefono", "");
            }
            if (rs.getString("EMAIL_CLIENTE") != null) {
                obj.put("mail", rs.getString("EMAIL_CLIENTE"));
            } else {
                obj.put("mail", "");
            }
            if (rs.getString("REF_BANCARIA") != null) {
                obj.put("refBancaria", rs.getString("REF_BANCARIA"));
            } else {
                obj.put("refBancaria", "");
            }
            if (rs.getString("CVE_APODERADO") != null) {
                obj.put("agenteVendedor", rs.getString("CVE_APODERADO"));
            } else {
                obj.put("agenteVendedor", "");
            }
            if (rs.getString("CONDUCTO_COBRO") != null) {
                obj.put("conCobro", rs.getString("CONDUCTO_COBRO"));
            } else {
                obj.put("conCobro", "");
            }
            if (rs.getString("TIPOMENSAJE") != null) {
                obj.put("tipoMensaje", rs.getString("TIPOMENSAJE"));
            } else {
                obj.put("tipoMensaje", "");
            }
            if (rs.getString("CVE_PROCESO") != null) {
                obj.put("proceso", rs.getString("CVE_PROCESO"));
            } else {
                obj.put("proceso", "");
            }
            if (rs.getString("CVE_ESTATUS") != null) {
                obj.put("estatus", rs.getString("CVE_ESTATUS"));
            } else {
                obj.put("estatus", "");
            }
            if (rs.getString("DETALLE") != null) {
                obj.put("detalle", rs.getString("DETALLE"));
            } else {
                obj.put("detalle", "");
            }
            return obj;
        }
    };

    @SuppressWarnings("unchecked")
    public List<DestinatarioVO> getAgentes() {
        if (log.isDebugEnabled()) {
            log.debug("getAgentes...");
        }
        try {
            return (ArrayList<DestinatarioVO>) jdbcTemplate.query(qryAgentes, MAPPER_AGENTES);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<DestinatarioVO>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<DestinatarioVO> getPromotorias() {
        if (log.isDebugEnabled()) {
            log.debug("getPromotorias...");
        }
        try {
            return (ArrayList<DestinatarioVO>) jdbcTemplate.query(qryPromotorias, MAPPER_PROMOTORIA);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<DestinatarioVO>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<HashMap<String, String>> getRegistrosAgente(String agente, String promotoria) {
        if (log.isDebugEnabled()) {
            log.debug("getRegistrosAgente...");
        }
        ArrayList params = new ArrayList();
        params.add(agente);
        params.add(promotoria);
        
        try {
            return (ArrayList<HashMap<String, String>>) jdbcTemplate.query(qryRegistrosAgente, params.toArray(), MAPPER_MAP_REGISTROS_AG);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<HashMap<String, String>>();
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<HashMap<String, String>> getRegistrosPromotor(String promotor) {
        if (log.isDebugEnabled()) {
            log.debug("getPromotorias...");
        }
        ArrayList params = new ArrayList();
        params.add(promotor);
        try {
            return (ArrayList<HashMap<String, String>>) jdbcTemplate.query(qryRegistrosPromotor, params.toArray(), MAPPER_MAP_REGISTROS_PROM);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<HashMap<String, String>>();
        }
    }

    public String getDirectorio() {
        if (log.isDebugEnabled()) {
            log.debug("getDirectorio...");
        }
        
        try {
            return (String) jdbcTemplate.queryForObject(qryDirectorio, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    
    public final void setQryAgentes(String qryAgentes) {
        this.qryAgentes = qryAgentes;
    }

    public final void setQryPromotorias(String qryPromotorias) {
        this.qryPromotorias = qryPromotorias;
    }

    public final void setQryRegistrosAgente(String qryRegistrosAgente) {
        this.qryRegistrosAgente = qryRegistrosAgente;
    }

    public final void setQryRegistrosPromotor(String qryRegistrosPromotor) {
        this.qryRegistrosPromotor = qryRegistrosPromotor;
    }

    
}
