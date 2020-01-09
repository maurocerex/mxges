package com.metlife.nm.utils.notificacion.vo;

public class PolizaCliente {
	
	private int proceso;
	private int registro;
	private String cveProcesoOrigen;
	private String email;
	private String poliza;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String cobertura_column5;
	private String fechaVigencia_column6;
	
	public PolizaCliente() {	
	}
	
	public int getProceso() {
		return proceso;
	}

	public void setProceso(int proceso) {
		this.proceso = proceso;
	}
	
	public int getRegistro() {
		return registro;
	}

	public void setRegistro(int registro) {
		this.registro = registro;
	}

	public String getCveProcesoOrigen() {
		return cveProcesoOrigen;
	}

	public void setCveProcesoOrigen(String cveProcesoOrigen) {
		this.cveProcesoOrigen = cveProcesoOrigen;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPoliza() {
		return poliza;
	}

	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getCobertura_column5() {
		return cobertura_column5;
	}

	public void setCobertura_column5(String cobertura_column5) {
		this.cobertura_column5 = cobertura_column5;
	}

	public String getFechaVigencia_column6() {
		return fechaVigencia_column6;
	}

	public void setFechaVigencia_column6(String fechaVigencia_column6) {
		this.fechaVigencia_column6 = fechaVigencia_column6;
	}
}
