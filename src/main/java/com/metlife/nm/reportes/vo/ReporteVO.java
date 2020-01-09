package com.metlife.nm.reportes.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class ReporteVO extends BaseObjectVO {

    
    /**
     * 
     */
    private static final long serialVersionUID = 2926313687045651129L;
    
    private String idProceso;
    private String idLob;
    private String idMensaje;
    private String idNotificacion;
    private String status;
    private String prioridad;
    private String destinatario;
    private String dia;
    
    public final String getIdMensaje() {
        return idMensaje;
    }
    public final void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }
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
    public final String getStatus() {
        return status;
    }
    public final void setStatus(String status) {
        this.status = status;
    }
    public final String getPrioridad() {
        return prioridad;
    }
    public final void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public final String getDia() {
        return dia;
    }
    public final void setDia(String dia) {
        this.dia = dia;
    }
    public final String getDestinatario() {
        return destinatario;
    }
    public final void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
