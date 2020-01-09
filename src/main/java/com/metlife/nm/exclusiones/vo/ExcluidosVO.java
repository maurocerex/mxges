package com.metlife.nm.exclusiones.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class ExcluidosVO extends BaseObjectVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6142475820459409281L;

	
	private int idRetenedor;
	private int idUnidadPago;
	private String lob;
	private String proceso;
	private String medioEnvio;
	
	
	public int getIdRetenedor() {
		return idRetenedor;
	}
	public void setIdRetenedor(int idRetenedor) {
		this.idRetenedor = idRetenedor;
	}
	public int getIdUnidadPago() {
		return idUnidadPago;
	}
	public void setIdUnidadPago(int idUnidadPago) {
		this.idUnidadPago = idUnidadPago;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getProceso() {
		return proceso;
	}
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}
	public String getMedioEnvio() {
		return medioEnvio;
	}
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
}
