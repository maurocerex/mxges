/*
 *Copyright (c) 2010, Y. All rights reserved
 */

package com.metlife.nm.roles.vo;

import com.metlife.nm.domain.BaseObjectVO;
/**
 * 
 * Objeto del negocio que define propiedades de las pantallas
 * @author Paulino Mota Hernandez
 *
 */
public class PantallasVO extends BaseObjectVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Identificador de la pantalla
	 */
	private String idPantalla;
	/**
	 * Menu en la que se encuentra
	 */
	private String menu;
	/**
	 * nombre de la pantalla
	 */
	private String pantalla;
	/**
	 * identificador del rol
	 */
	private String rolId;
	/**
	 * Descripcion de la pantalla
	 */
	private String descripcion;
	/**
	 * pantalla en seleccion
	 */
	private String selPant;
	/**
	 * estado inicial de las pantallas
	 */
	private boolean edoIni;
	/**
	 * nombre del usuario
	 */
	private String userName;
	
	
	public boolean isEdoIni() {
		return edoIni;
	}
	public void setEdoIni(boolean edoIni) {
		this.edoIni = edoIni;
	}
	public boolean isAsig() {
		return asig;
	}
	public void setAsig(boolean asig) {
		this.asig = asig;
	}
	private boolean asig;
	
	public String getIdPantalla() {
		return idPantalla;
	}
	public void setIdPantalla(String idPantalla) {
		this.idPantalla = idPantalla;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public String getPantalla() {
		return pantalla;
	}
	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setRolId(String rolId) {
		this.rolId = rolId;
	}
	public String getRolId() {
		return rolId;
	}
	public void setSelPant(String selPant) {
		this.selPant = selPant;
	}
	public String getSelPant() {
		return selPant;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	
}
