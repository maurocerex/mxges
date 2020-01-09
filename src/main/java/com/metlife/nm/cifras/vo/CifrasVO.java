package com.metlife.nm.cifras.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class CifrasVO extends BaseObjectVO{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7557723822869543731L;
	
	private String destinatario;
	private String idRegistro;
	private String idEnvio;
	private String idLob;
	private String lobDesc;
	private String idProceso;
	private String procesoDesc;
	private String medioEnvio;
	private String estatus;
	private String noRegistros;
	private String porcentaje;
	private String nombre;
	private String telefono;
	private String mail;
	private String poliza;
	private String refBancaria;
	private int montoRecibido;
	private String recibosPendientes;
	private String idPromotoria;
	private String idAgente;
	private String idRetenedor;
	private String idUnidadPago;
	private String centroTrabajo;
	private String statusEnvio;
	private String fechaIni;
	private String fecFin;
	private String detalleStatus;
	private String detalleNotificacion;
	private String totalEnviados;
	
	public final String getDestinatario() {
        return destinatario;
    }
    public final void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    public final String getIdRegistro() {
        return idRegistro;
    }
    public final void setIdRegistro(String idRegistro) {
        this.idRegistro = idRegistro;
    }
    public final String getIdEnvio() {
        return idEnvio;
    }
    public final void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }
    public final String getFechaIni() {
        return fechaIni;
    }
    public final void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }
    public final String getFecFin() {
        return fecFin;
    }
    public final void setFecFin(String fecFin) {
        this.fecFin = fecFin;
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
	public String getMedioEnvio() {
		return medioEnvio;
	}
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getNoRegistros() {
		return noRegistros;
	}
	public void setNoRegistros(String noRegistros) {
		this.noRegistros = noRegistros;
	}
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	public String getRefBancaria() {
		return refBancaria;
	}
	public void setRefBancaria(String refBancaria) {
		this.refBancaria = refBancaria;
	}
	public int getMontoRecibido() {
		return montoRecibido;
	}
	public void setMontoRecibido(int montoRecibido) {
		this.montoRecibido = montoRecibido;
	}
	public String getRecibosPendientes() {
		return recibosPendientes;
	}
	public void setRecibosPendientes(String recibosPendientes) {
		this.recibosPendientes = recibosPendientes;
	}
	public String getIdPromotoria() {
		return idPromotoria;
	}
	public void setIdPromotoria(String idPromotoria) {
		this.idPromotoria = idPromotoria;
	}
	public String getIdAgente() {
		return idAgente;
	}
	public void setIdAgente(String idAgente) {
		this.idAgente = idAgente;
	}
	public String getIdRetenedor() {
		return idRetenedor;
	}
	public void setIdRetenedor(String idRetenedor) {
		this.idRetenedor = idRetenedor;
	}
	public String getIdUnidadPago() {
		return idUnidadPago;
	}
	public void setIdUnidadPago(String idUnidadPago) {
		this.idUnidadPago = idUnidadPago;
	}
	public String getCentroTrabajo() {
		return centroTrabajo;
	}
	public void setCentroTrabajo(String centroTrabajo) {
		this.centroTrabajo = centroTrabajo;
	}
	public String getStatusEnvio() {
		return statusEnvio;
	}
	public void setStatusEnvio(String statusEnvio) {
		this.statusEnvio = statusEnvio;
	}
	public String getDetalleStatus() {
		return detalleStatus;
	}
	public void setDetalleStatus(String detalleStatus) {
		this.detalleStatus = detalleStatus;
	}
	public String getDetalleNotificacion() {
		return detalleNotificacion;
	}
	public void setDetalleNotificacion(String detalleNotificacion) {
		this.detalleNotificacion = detalleNotificacion;
	}
	public String getTotalEnviados() {
		return totalEnviados;
	}
	public void setTotalEnviados(String totalEnviados) {
		this.totalEnviados = totalEnviados;
	}
	
}

