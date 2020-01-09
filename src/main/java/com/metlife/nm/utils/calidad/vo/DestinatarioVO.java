package com.metlife.nm.utils.calidad.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DestinatarioVO {

    private String agente;
    private String mailAgente;
    private String promotoria;
    private String mailPromotoria;
    
    public final String getAgente() {
        return agente;
    }
    public final void setAgente(String agente) {
        this.agente = agente;
    }
    public final String getPromotoria() {
        return promotoria;
    }
    public final void setPromotoria(String promotoria) {
        this.promotoria = promotoria;
    }
    public final String getMailAgente() {
        return mailAgente;
    }
    public final void setMailAgente(String mailAgente) {
        this.mailAgente = mailAgente;
    }
    public final String getMailPromotoria() {
        return mailPromotoria;
    }
    public final void setMailPromotoria(String mailPromotoria) {
        this.mailPromotoria = mailPromotoria;
    }
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
