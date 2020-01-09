package com.metlife.nm.utils.notificacion.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CabeceraVO {

	private int idProcesoPadre;
	private String idProceso;
	private String medioEnvio;
	private String status;
	private String regProcesados;
	private String desMensaje;
	private String ejecutadoPor;
	private String regExitosos;
	private String regError;
	private String nombreArchivo;
	private String idNotificacion;
	private String idLob;
	private String idMensaje;
	public int getIdProcesoPadre() {
		return idProcesoPadre;
	}
	public void setIdProcesoPadre(int idProcesoPadre) {
		this.idProcesoPadre = idProcesoPadre;
	}
	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public String getMedioEnvio() {
		return medioEnvio;
	}
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegProcesados() {
		return regProcesados;
	}
	public void setRegProcesados(String regProcesados) {
		this.regProcesados = regProcesados;
	}
	public String getDesMensaje() {
		return desMensaje;
	}
	public void setDesMensaje(String desMensaje) {
		this.desMensaje = desMensaje;
	}
	public String getEjecutadoPor() {
		return ejecutadoPor;
	}
	public void setEjecutadoPor(String ejecutadoPor) {
		this.ejecutadoPor = ejecutadoPor;
	}
	public String getRegExitosos() {
		return regExitosos;
	}
	public void setRegExitosos(String regExitosos) {
		this.regExitosos = regExitosos;
	}
	public String getRegError() {
		return regError;
	}
	public void setRegError(String regError) {
		this.regError = regError;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(String idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public String getIdLob() {
		return idLob;
	}
	public void setIdLob(String idLob) {
		this.idLob = idLob;
	}
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
