package com.metlife.nm.notificaciones.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class FrecuenciaVO extends BaseObjectVO{

    /**
     * 
     */
    private static final long serialVersionUID = 2510454391795796805L;
    private String idNotificacion;
    private String idProceso;
    private String idLob;
    private String idMensaje;
    private int idFrecuencia;
    private String idDia;
    private int hora;
    private int minuto;
    private int dia;
    private String frecuenciaEnvio;
    public final String getIdNotificacion() {
        return idNotificacion;
    }
    public final void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    public final String getIdProceso() {
        return idProceso;
    }
    public final void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }
    public final String getIdLob() {
        return idLob;
    }
    public final void setIdLob(String idLob) {
        this.idLob = idLob;
    }
    public final String getIdMensaje() {
        return idMensaje;
    }
    public final void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }
    public final int getIdFrecuencia() {
        return idFrecuencia;
    }
    public final void setIdFrecuencia(int idFrecuencia) {
        this.idFrecuencia = idFrecuencia;
    }
    public final String getIdDia() {
        return idDia;
    }
    public final void setIdDia(String idDia) {
        this.idDia = idDia;
    }
    public final int getHora() {
        return hora;
    }
    public final void setHora(int hora) {
        this.hora = hora;
    }
    public final int getMinuto() {
        return minuto;
    }
    public final void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    public final int getDia() {
        return dia;
    }
    public final void setDia(int dia) {
        this.dia = dia;
    }
    public final String getFrecuenciaEnvio() {
        return frecuenciaEnvio;
    }
    public final void setFrecuenciaEnvio(String frecuenciaEnvio) {
        this.frecuenciaEnvio = frecuenciaEnvio;
    }
    
    
}
