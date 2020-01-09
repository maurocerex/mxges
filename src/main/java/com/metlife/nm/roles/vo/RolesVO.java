/*
 *Copyright (c) 2010, Y. All rights reserved
 */
package com.metlife.nm.roles.vo;

import com.metlife.nm.domain.BaseObjectVO;
/**
 * Objeto del negocio que define las propiedades de los roles
 * @author Paulino Mota Hernandez
 *
 */
public class RolesVO  extends BaseObjectVO {

	private static final long serialVersionUID = 707751258308193132L;
	
	/**
	 * identificador del Rol 
	 */
	private int idRol;
	/**
	 * descripcion del rol
	 */
	private String descripcion;
	/**
	 * indica la vigencia del rol
	 */
	private String vigencia;
	/**
	 * Obtiene el identificador del rol
	 * @return idRol
	 */
	public int getIdRol() {
		return idRol;
	}
	/**
	 * Define el id del rol 
	 * @param idRol
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
	/**
	 * Obtiene la descripcion del rol 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * Define la descripcion del rol
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * Obtiene la vigencia del rol
	 * @return vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}
	/**
	 * Define la vigencia de un rol
	 * @param vigencia
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	
}
