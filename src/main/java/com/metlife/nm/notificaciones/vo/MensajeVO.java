package com.metlife.nm.notificaciones.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class MensajeVO extends BaseObjectVO{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8955644880051194063L;
    
    private String idMensaje;
    private String descripcion;
    private String lob;
    private String idLob;
    private String proceso;
    private String idProceso;
    private String status;
    private String medio;
    private String asunto;
    private String mensaje;
    
    public String getIdMensaje() {
        return idMensaje;
    }
    public void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getLob() {
        return lob;
    }
    public void setLob(String lob) {
        this.lob = lob;
    }
    public String getIdLob() {
        return idLob;
    }
    public void setIdLob(String idLob) {
        this.idLob = idLob;
    }
    public String getProceso() {
        return proceso;
    }
    public void setProceso(String proceso) {
        this.proceso = proceso;
    }
    public String getIdProceso() {
        return idProceso;
    }
    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMedio() {
        return medio;
    }
    public void setMedio(String medio) {
        this.medio = medio;
    }
    public String getAsunto() {
        return asunto;
    }
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
