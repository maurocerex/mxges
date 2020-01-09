package com.metlife.nm.utils.notificacion.vo;

public class NotificacionVO {

    private String idProceso;
    private String idLob;
    private String idNotificacion;
    private String idMensaje;
    private String medioEnvio;
    private String tipoFrecuencia;
    private String idDia;
    private int diaMes;
    private int minutos;
    private int horas;
    private boolean envia;
    private boolean envioCliente;
    private boolean envioAgente;
    private boolean dxn;
    private boolean cobBancaria;
    private boolean pagoDirecto;
    private boolean regLabBase;
    private boolean regLabEventual;
    private String tipoRespuesta;
    private String reintentable;
    private String notificacionDesc;
    private String canalEnvio;
   
    public String getNotificacionDesc() {
		return notificacionDesc;
	}
	public void setNotificacionDesc(String notificacionDesc) {
		this.notificacionDesc = notificacionDesc;
	}
	public final String getTipoRespuesta() {
        return tipoRespuesta;
    }
    public final void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }
    public final String getReintentable() {
        return reintentable;
    }
    public final void setReintentable(String reintentable) {
        this.reintentable = reintentable;
    }
    public final boolean isDxn() {
        return dxn;
    }
    public final void setDxn(boolean dxn) {
        this.dxn = dxn;
    }
    public final boolean isCobBancaria() {
        return cobBancaria;
    }
    public final void setCobBancaria(boolean cobBancaria) {
        this.cobBancaria = cobBancaria;
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
    public final String getIdNotificacion() {
        return idNotificacion;
    }
    public final void setIdNotificacion(String idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    public final String getIdMensaje() {
        return idMensaje;
    }
    public final void setIdMensaje(String idMensaje) {
        this.idMensaje = idMensaje;
    }
    public final String getMedioEnvio() {
        return medioEnvio;
    }
    public final void setMedioEnvio(String medioEnvio) {
        this.medioEnvio = medioEnvio;
    }
    public final String getTipoFrecuencia() {
        return tipoFrecuencia;
    }
    public final void setTipoFrecuencia(String tipoFrecuencia) {
        this.tipoFrecuencia = tipoFrecuencia;
    }
    public final String getIdDia() {
        return idDia;
    }
    public final void setIdDia(String idDia) {
        this.idDia = idDia;
    }
    public final int getDiaMes() {
        return diaMes;
    }
    public final void setDiaMes(int diaMes) {
        this.diaMes = diaMes;
    }
    public final int getMinutos() {
        return minutos;
    }
    public final void setMinutos(int minutos) {
        this.minutos = minutos;
    }
    public final int getHoras() {
        return horas;
    }
    public final void setHoras(int horas) {
        this.horas = horas;
    }
    public final boolean isEnvia() {
        return envia;
    }
    public final void setEnvia(boolean envia) {
        this.envia = envia;
    }
    public final boolean isEnvioCliente() {
        return envioCliente;
    }
    public final void setEnvioCliente(boolean envioCliente) {
        this.envioCliente = envioCliente;
    }
    public final boolean isEnvioAgente() {
        return envioAgente;
    }
    public final void setEnvioAgente(boolean envioAgente) {
        this.envioAgente = envioAgente;
    }
	public String getCanalEnvio() {
		return canalEnvio;
	}
	public void setCanalEnvio(String canalEnvio) {
		this.canalEnvio = canalEnvio;
	}
    
}
