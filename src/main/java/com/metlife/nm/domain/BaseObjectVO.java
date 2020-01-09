package com.metlife.nm.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseObjectVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4962803828878973046L;

	protected boolean nuevo;
	protected boolean modificado;
	protected boolean eliminado;

	public final boolean isNuevo() {
		return nuevo;
	}

	public final void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public final boolean isModificado() {
		return modificado;
	}

	public final void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	public final boolean isEliminado() {
		return eliminado;
	}

	public final void setEliminado(boolean eliminado) {
		this.eliminado = eliminado;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
