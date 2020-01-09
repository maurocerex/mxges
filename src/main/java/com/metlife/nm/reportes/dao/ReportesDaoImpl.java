package com.metlife.nm.reportes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.JdbcDao;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.vo.ReporteVO;

public class ReportesDaoImpl extends JdbcDao implements ReportesDao {

    private String qryReporte;
    private String qryConf;
    private String qryDiasReporte;
    private String insReporteCalidad;
    private String updReporteCalidad;
    private String getLast;
    private String updDestinatario;
    private String qryCatDestinatario;
    private String qryDestinatario;

    private static ParameterizedRowMapper<ReporteVO> MAPPER_REPORTE = new ParameterizedRowMapper<ReporteVO>() {
        public ReporteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteVO obj = new ReporteVO();

            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setStatus(rs.getString("ESTATUS"));
            obj.setPrioridad(rs.getString("PRIORIDAD"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<ReporteVO> MAPPER_CONF = new ParameterizedRowMapper<ReporteVO>() {
        public ReporteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteVO obj = new ReporteVO();

            obj.setIdProceso(rs.getString("CVE_PROCESO"));
            obj.setIdLob(rs.getString("CVE_LINEA_NEGOCIO"));
            obj.setIdNotificacion(rs.getString("CVE_NOTIFICACION"));
            obj.setIdMensaje(rs.getString("CVE_MENSAJE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<DiaVO> MAPPER_DIAS = new ParameterizedRowMapper<DiaVO>() {
        public DiaVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            DiaVO obj = new DiaVO();

            obj.setDiaEnvio(rs.getString("DIA_ENVIO"));
            obj.setIdFrecuencia(rs.getInt("ID_FRECUENCIA"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<CatalogoVO> MAPPER_CATALOGO = new ParameterizedRowMapper<CatalogoVO>() {
        public CatalogoVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            CatalogoVO obj = new CatalogoVO();

            obj.setKeyTxt(rs.getString("KEY"));
            obj.setValue(rs.getString("VALUE"));

            return obj;
        }
    };

    private static ParameterizedRowMapper<ReporteVO> MAPPER_DESTINATARIO = new ParameterizedRowMapper<ReporteVO>() {
        public ReporteVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReporteVO obj = new ReporteVO();
            // /SW_ENVIO_AGENTE , SW_ENVIO_PROMOT
            if (rs.getString("SW_ENVIO_AGENTE").equals("S") && rs.getString("SW_ENVIO_PROMOT").equals("S")) {
                obj.setDestinatario("AM");
            } else if (rs.getString("SW_ENVIO_AGENTE").equals("S")) {
                obj.setDestinatario("AG");
            } else if (rs.getString("SW_ENVIO_PROMOT").equals("S")) {
                obj.setDestinatario("PR");
            }

            return obj;
        }
    };

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ReporteVO getDestinatario(ReporteVO vo) {
        if (log.isDebugEnabled()) {
            log.debug("getConfiguracion...");
            log.debug("idNotificacion -> "+vo.getIdNotificacion());
            log.debug("proceso -> "+vo.getIdProceso());
            log.debug("lob -> "+vo.getIdLob());
            log.debug("idMensaje -> "+vo.getIdMensaje());
        }
        try {
            ArrayList params = new ArrayList();
            params.add(vo.getIdNotificacion());
            params.add(vo.getIdProceso());
            params.add(vo.getIdLob());
            params.add(vo.getIdMensaje());

            return (ReporteVO) jdbcTemplate.queryForObject(qryDestinatario, params.toArray(), MAPPER_DESTINATARIO);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
//            throw new RuntimeException("No se ha configurado el reporte de calidad de datos.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<CatalogoVO> getCatDestinatario() {
        if (log.isDebugEnabled()) {
            log.debug("getCatDestinatario");
        }
        return (List<CatalogoVO>) jdbcTemplate.query(qryCatDestinatario, MAPPER_CATALOGO);
    }

    public ReporteVO getDatosReporte() {
        if (log.isDebugEnabled()) {
            log.debug("getDatosReporte");
        }
        return (ReporteVO) jdbcTemplate.queryForObject(qryReporte, MAPPER_REPORTE);
    }

    public ReporteVO getConfiguracion() {
        if (log.isDebugEnabled()) {
            log.debug("getConfiguracion");
        }
        try {
            return (ReporteVO) jdbcTemplate.queryForObject(qryConf, MAPPER_CONF);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("No se ha configurado el reporte de calidad de datos.");
        }
    }

    @SuppressWarnings("unchecked")
    public List<DiaVO> getDiasReporteCalidad() {
        if (log.isDebugEnabled()) {
            log.debug("getDiasReporteCalidad");
        }
        try {
            return (List<DiaVO>) jdbcTemplate.query(qryDiasReporte, MAPPER_DIAS);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int getLastIdFrecuencia() {
        Integer index = (Integer) jdbcTemplate.queryForObject(getLast, Integer.class);
        if (index == null) {
            index = 0;
        } else {
            index++;
        }

        return index.intValue();
    }

    public void insDiaReporteCalidad(final ReporteVO vo, final int idFrecuencia) {
        if (log.isDebugEnabled()) {
            log.info("insDiaReporteCalidad...");
        }

        int affected = jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insReporteCalidad);

                ps.setString(1, vo.getIdNotificacion());
                ps.setString(2, vo.getIdProceso());
                ps.setString(3, vo.getIdLob());
                ps.setString(4, vo.getIdMensaje());
                ps.setInt(5, idFrecuencia);
                ps.setInt(6, StringUtils.isBlank(vo.getDia()) ? 0 : new Integer(vo.getDia()));

                return ps;
            }
        });

        this.checkAffected(affected);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void updateReporteCalidad(String dia, int idFrecuencia) {
        if (log.isDebugEnabled()) {
            log.debug("actualizarFrecuencia...");

        }
        ArrayList params = new ArrayList();
        params.add(dia);
        params.add(idFrecuencia);

        int affected = jdbcTemplate.update(updReporteCalidad, params.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void updDestinatario(final ReporteVO vo, String destinatario) {
        if (log.isDebugEnabled()) {
            log.debug("updDestinatario..." + destinatario);
            log.debug("getIdProceso..." + vo.getIdProceso());
            log.debug("getIdLob..." + vo.getIdLob());
            log.debug("getIdMensaje..." + vo.getIdMensaje());

        }
        ArrayList params = new ArrayList();
        if (destinatario.equals("AG")) {
            params.add("S");
            params.add("N");
        } else if (destinatario.equals("PR")) {
            params.add("N");
            params.add("S");
        } else if (destinatario.equals("AM")) {
            params.add("S");
            params.add("S");
        }
        params.add(vo.getIdNotificacion());
        params.add(vo.getIdProceso());
        params.add(vo.getIdLob());
        params.add(vo.getIdMensaje());

        int affected = jdbcTemplate.update(updDestinatario, params.toArray());

        // Importante verificar que afectamos exactamente un registro.
        this.checkAffected(affected);
    }

    public final void setQryReporte(String qryReporte) {
        this.qryReporte = qryReporte;
    }

    public final void setQryConf(String qryConf) {
        this.qryConf = qryConf;
    }

    public void setQryDiasReporte(String qryDiasReporte) {
        this.qryDiasReporte = qryDiasReporte;
    }

    public void setInsReporteCalidad(String insReporteCalidad) {
        this.insReporteCalidad = insReporteCalidad;
    }

    public void setGetLast(String getLast) {
        this.getLast = getLast;
    }

    public void setUpdReporteCalidad(String updReporteCalidad) {
        this.updReporteCalidad = updReporteCalidad;
    }

    public void setUpdDestinatario(String updDestinatario) {
        this.updDestinatario = updDestinatario;
    }

    public void setQryCatDestinatario(String qryCatDestinatario) {
        this.qryCatDestinatario = qryCatDestinatario;
    }

    public final void setQryDestinatario(String qryDestinatario) {
        this.qryDestinatario = qryDestinatario;
    }

}
