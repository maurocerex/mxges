package com.metlife.nm.notificaciones.vo;

import java.util.ArrayList;
import java.util.List;

import com.metlife.nm.domain.BaseObjectVO;

public class NotificacionVO extends BaseObjectVO{

    /**
     * 
     */
    private static final long serialVersionUID = -3144989733548797306L;
    
    private MensajeVO mensaje;
    private List<FrecuenciaVO> frecuencia;
    private String idNotificacion;
    private String idProceso;
    private String idMensajeAnt;
    private String procesoDesc;
    private String idLob;
    private String lob;
    private String notificacionDesc;
    private String status;
    private String medio;
    private String canalEnvio;
    private String catCanalEnvio;
    private boolean envioAgente;
    private boolean envioCliente;
    private boolean envioPromotoria;
    private String idRespBancaria;
    private String reintentable;
    private boolean dxn;
    private boolean cobraBanca;
    private boolean pagoDirecto;
    private boolean regLabBase;
    private boolean regLabEventual;
    private boolean dia;
    
    
    public boolean isDia() {
		return dia;
	}
	public void setDia(boolean dia) {
		this.dia = dia;
	}
	public NotificacionVO(){
        mensaje = new MensajeVO();
        frecuencia = new ArrayList<FrecuenciaVO>();
    }
    public final MensajeVO getMensaje() {
        return mensaje;
    }
    public final void setMensaje(MensajeVO mensaje) {
        this.mensaje = mensaje;
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
    public final String getProcesoDesc() {
        return procesoDesc;
    }
    public final void setProcesoDesc(String procesoDesc) {
        this.procesoDesc = procesoDesc;
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
    public final String getMedio() {
        return medio;
    }
    public final void setMedio(String medio) {
        this.medio = medio;
    }
    public final boolean isEnvioAgente() {
        return envioAgente;
    }
    public final void setEnvioAgente(boolean envioAgente) {
        this.envioAgente = envioAgente;
    }
    public final boolean isEnvioCliente() {
        return envioCliente;
    }
    public final void setEnvioCliente(boolean envioCliente) {
        this.envioCliente = envioCliente;
    }
    public final boolean isEnvioPromotoria() {
        return envioPromotoria;
    }
    public final void setEnvioPromotoria(boolean envioPromotoria) {
        this.envioPromotoria = envioPromotoria;
    }
    public final String getIdRespBancaria() {
        return idRespBancaria;
    }
    public final void setIdRespBancaria(String idRespBancaria) {
        this.idRespBancaria = idRespBancaria;
    }
    public final boolean isDxn() {
        return dxn;
    }
    public final void setDxn(boolean dxn) {
        this.dxn = dxn;
    }
    public final boolean isCobraBanca() {
        return cobraBanca;
    }
    public final void setCobraBanca(boolean cobraBanca) {
        this.cobraBanca = cobraBanca;
    }
    public final boolean isPagoDirecto() {
        return pagoDirecto;
    }
    public final void setPagoDirecto(boolean pagoDirecto) {
        this.pagoDirecto = pagoDirecto;
    }
    public final boolean isRegLabBase() {
        return regLabBase;
    }
    public final void setRegLabBase(boolean regLabBase) {
        this.regLabBase = regLabBase;
    }
    public final boolean isRegLabEventual() {
        return regLabEventual;
    }
    public final void setRegLabEventual(boolean regLabEventual) {
        this.regLabEventual = regLabEventual;
    }
    public final String getNotificacionDesc() {
        return notificacionDesc;
    }
    public final void setNotificacionDesc(String notificacionDesc) {
        this.notificacionDesc = notificacionDesc;
    }
    public final String getLob() {
        return lob;
    }
    public final void setLob(String lob) {
        this.lob = lob;
    }
    public final List<FrecuenciaVO> getFrecuencia() {
        return frecuencia;
    }
    public final void setFrecuencia(List<FrecuenciaVO> frecuencia) {
        this.frecuencia = frecuencia;
    }
	public String getIdMensajeAnt() {
		return idMensajeAnt;
	}
	public void setIdMensajeAnt(String idMensajeAnt) {
		this.idMensajeAnt = idMensajeAnt;
	}
	public String getReintentable() {
		return reintentable;
	}
	public void setReintentable(String reintentable) {
		this.reintentable = reintentable;
	}
	public String getCanalEnvio() {
		return canalEnvio;
	}
	public void setCanalEnvio(String canalEnvio) {
		this.canalEnvio = canalEnvio;
	}
	public String getCatCanalEnvio() {
		return catCanalEnvio;
	}
	public void setCatCanalEnvio(String catcanalEnvio) {
		this.catCanalEnvio = catcanalEnvio;
	}
	
}
