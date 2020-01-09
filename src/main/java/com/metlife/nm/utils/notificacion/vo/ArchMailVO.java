package com.metlife.nm.utils.notificacion.vo;

import java.io.Serializable;

public class ArchMailVO implements Serializable {

	private static final long serialVersionUID = -8135195423438886893L;
	private String nombreArchivo;
	
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
}
