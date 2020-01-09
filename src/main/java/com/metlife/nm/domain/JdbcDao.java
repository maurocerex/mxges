package com.metlife.nm.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class JdbcDao {

    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate jdbcTemplate;
    protected static Calendar aCalendar = Calendar.getInstance();
    protected static final Logger log = Logger.getLogger(JdbcDao.class);

    protected static SimpleDateFormat SDF_DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
    protected static SimpleDateFormat SDF_DDMMYYYY_HHMMSS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    static {
        SDF_DDMMYYYY.setLenient(false);
        SDF_DDMMYYYY_HHMMSS.setLenient(false);
    }

    public final void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    protected void checkAffected(int affected) {
        if (affected > 1) {
            throw new RuntimeException("La operacion afecto mas de 1 registro.");
        }
        if (affected < 1) {
            throw new RuntimeException("La operacion no afecto ningun registro.");
        }
    }

    protected String debugVar(String name, Object var) {
        return new StringBuffer(name).append(" => [ ").append(var).append(" ]").toString();
    }

    protected String parseBoolean(boolean value) {
        if (value == true) {
            return "S";
        } else {
            return "N";
        }
    }

    protected String parseHour(String hh, String mm) {
        if (StringUtils.isNotBlank(hh) && StringUtils.isNotBlank(mm)) {
            return new StringBuffer(hh).append(":").append(mm).toString();
        } else {
            return null;
        }
    }

    protected final String toUpper(String value) {
        if (value == null) {
            return null;
        }
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.toUpperCase();
    }

    protected final String toUpperTrim(String value) {
        if (value == null) {
            return null;
        }
        if (StringUtils.isBlank(value)) {
            return "";
        }
        return value.toUpperCase().trim();
    }

    protected Date parseDate(String ddmmyyyy) {

        if (StringUtils.isBlank(ddmmyyyy)) {
            return null;
        }

        try {
            return SDF_DDMMYYYY.parse(ddmmyyyy);
        } catch (ParseException e) {
            throw new RuntimeException("La fecha " + ddmmyyyy + " esta en formato incorrecto");
        }
    }

}
