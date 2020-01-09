package com.metlife.nm.utils.notificacion.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MensajeVO {

	private int idProceso;
	private int idRegistro;
	private String cveProceso;
	private String idLob;
	private String idNotificacion; 
	private String medioEnvio;
	private boolean envioCliente;
	private boolean envioAgente;
	private boolean envioPromotor;
	private String idTipoRespCB;
	private boolean reintentable; 
	private boolean dxn;
	private boolean cobBancaria;
	private boolean pagoDirecto;
	private boolean regLabBase;
	private boolean regLabEventual;
	private String idMensaje;
	private String mensajeDesc;
	private String asunto;
	private String cuerpo;
	private String status;
	private String telMovilApoderado;
	private String telMovilCliente;
	private String emailApoderado;
	private String emailCliente;
	private String conductoCobro;
	private String regLaboral;
	private String emailPromotoria;
	
	public final String getEmailPromotoria() {
        return emailPromotoria;
    }
    public final void setEmailPromotoria(String emailPromotoria) {
        this.emailPromotoria = emailPromotoria;
    }
    public final String getConductoCobro() {
        return conductoCobro;
    }
    public final void setConductoCobro(String conductoCobro) {
        this.conductoCobro = conductoCobro;
    }
    public final String getRegLaboral() {
        return regLaboral;
    }
    public final void setRegLaboral(String regLaboral) {
        this.regLaboral = regLaboral;
    }
    public boolean isEnvioAgente() {
		return envioAgente;
	}
	public String getTelMovilApoderado() {
		return telMovilApoderado;
	}
	public void setTelMovilApoderado(String telMovilApoderado) {
		this.telMovilApoderado = telMovilApoderado;
	}
	public String getTelMovilCliente() {
		return telMovilCliente;
	}
	public void setTelMovilCliente(String telMovilCliente) {
		this.telMovilCliente = telMovilCliente;
	}
	public String getEmailApoderado() {
		return emailApoderado;
	}
	public void setEmailApoderado(String emailApoderado) {
		this.emailApoderado = emailApoderado;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	public void setEnvioAgente(boolean envioAgente) {
		this.envioAgente = envioAgente;
	}
	public String getCveProceso() {
		return cveProceso;
	}
	public void setCveProceso(String cveProceso) {
		this.cveProceso = cveProceso;
	}
	public String getIdLob() {
		return idLob;
	}
	public void setIdLob(String idLob) {
		this.idLob = idLob;
	}
	public String getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public String getMedioEnvio() {
		return medioEnvio;
	}
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
	public boolean isEnvioCliente() {
		return envioCliente;
	}
	public void setEnvioCliente(boolean envioCliente) {
		this.envioCliente = envioCliente;
	}
	public boolean isEnvioPromotor() {
		return envioPromotor;
	}
	public void setEnvioPromotor(boolean envioPromotor) {
		this.envioPromotor = envioPromotor;
	}
	public String getIdTipoRespCB() {
		return idTipoRespCB;
	}
	public void setIdTipoRespCB(String idTipoRespCB) {
		this.idTipoRespCB = idTipoRespCB;
	}
	public boolean isReintentable() {
		return reintentable;
	}
	public void setReintentable(boolean reintentable) {
		this.reintentable = reintentable;
	}
	public boolean isDxn() {
		return dxn;
	}
	public void setDxn(boolean dxn) {
		this.dxn = dxn;
	}
	public boolean isCobBancaria() {
		return cobBancaria;
	}
	public void setCobBancaria(boolean cobBancaria) {
		this.cobBancaria = cobBancaria;
	}
	public boolean isPagoDirecto() {
		return pagoDirecto;
	}
	public void setPagoDirecto(boolean pagoDirecto) {
		this.pagoDirecto = pagoDirecto;
	}
	public boolean isRegLabBase() {
		return regLabBase;
	}
	public void setRegLabBase(boolean regLabBase) {
		this.regLabBase = regLabBase;
	}
	public boolean isRegLabEventual() {
		return regLabEventual;
	}
	public void setRegLabEventual(boolean regLabEventual) {
		this.regLabEventual = regLabEventual;
	}
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getMensajeDesc() {
		return mensajeDesc;
	}
	public void setMensajeDesc(String mensajeDesc) {
		this.mensajeDesc = mensajeDesc;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(int idProceso) {
		this.idProceso = idProceso;
	}
	public int getIdRegistro() {
		return idRegistro;
	}
	public void setIdRegistro(int idRegistro) {
		this.idRegistro = idRegistro;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
