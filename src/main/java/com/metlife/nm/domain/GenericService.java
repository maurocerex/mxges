package com.metlife.nm.domain;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

public class GenericService {

    protected static SimpleDateFormat SDF_DDMMYYYY = new SimpleDateFormat("dd/MM/yyyy");
    protected static SimpleDateFormat SDF_DDMMYYYY_HHMMSS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    protected static SimpleDateFormat SDF_DDMMYYYY_HHMMSS_ARCHIVO = new SimpleDateFormat("ddMMyyyy_HHmmss");

    protected static SimpleDateFormat SDF_CIERRE = new SimpleDateFormat("yyyyMM");

    static {
        SDF_DDMMYYYY.setLenient(false);
        SDF_DDMMYYYY_HHMMSS.setLenient(false);
        SDF_CIERRE.setLenient(false);
    }

    protected String debugVar(String name, Object var) {

        return new StringBuffer(name).append(" => [ ").append(var).append(" ]").toString();

    }

    protected String fix(int val) {
        if (val < 10) {
            return new StringBuffer("0").append(val).toString();
        } else {
            return String.valueOf(val);
        }
    }

    protected String emptyIfIsBlank(String str) {
        if (StringUtils.isBlank(str)) {
            return "";
        } else {
            return str.trim();
        }
    }

}
