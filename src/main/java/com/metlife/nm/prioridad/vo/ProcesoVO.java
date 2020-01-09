package com.metlife.nm.prioridad.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class ProcesoVO extends BaseObjectVO {

    /**
     * 
     */
    private static final long serialVersionUID = -7886048158439710906L;
    private String idProceso;
    private String procesoDesc;
    private String idLob;
    private String lobDesc;
    private boolean status;
    private boolean notifica;
    private int prioridad;
    private boolean edoIni;
    
    public String getIdProceso() {
        return idProceso;
    }
    public void setIdProceso(String idProceso) {
        this.idProceso = idProceso;
    }
    public String getProcesoDesc() {
        return procesoDesc;
    }
    public void setProcesoDesc(String procesoDesc) {
        this.procesoDesc = procesoDesc;
    }
    public String getIdLob() {
        return idLob;
    }
    public void setIdLob(String idLob) {
        this.idLob = idLob;
    }
    public String getLobDesc() {
        return lobDesc;
    }
    public void setLobDesc(String lobDesc) {
        this.lobDesc = lobDesc;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public boolean isNotifica() {
        return notifica;
    }
    public void setNotifica(boolean notifica) {
        this.notifica = notifica;
    }
    public int getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }
    public final boolean isEdoIni() {
        return edoIni;
    }
    public final void setEdoIni(boolean edoIni) {
        this.edoIni = edoIni;
    }
}
