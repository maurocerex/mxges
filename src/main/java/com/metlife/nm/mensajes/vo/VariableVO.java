package com.metlife.nm.mensajes.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class VariableVO extends BaseObjectVO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 647501228842981632L;
	private String variable;
	private String descripcion;
	private String longitud;
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getLongitud() {
		return longitud;
	}
	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}
	
	
}
