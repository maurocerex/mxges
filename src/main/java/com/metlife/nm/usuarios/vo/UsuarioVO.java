package com.metlife.nm.usuarios.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class UsuarioVO extends BaseObjectVO {
    /**
     * serial version.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Idenficador del usuario.
     */
    private int userId;
    /**
     * Nombre del usuario.
     */
    private String username;
    /**
     * Nombre completo del usario.
     */
    private String nombre;

    /**
     * Indica si el producto esta vigente en la configuracion.
     */
    private String vigencia;
    /**
     * Idenficador del rol.
     */
    private String rolId;
    /**
     * Descripcion del rol.
     */
    private String descripcionRol;

    private String lob;
    
    private String idlob;
    
    public String getIdlob() {
        return idlob;
    }

    public void setIdlob(String idlob) {
        this.idlob = idlob;
    }

    public String getLob() {
        return lob;
    }

    public void setLob(String lob) {
        this.lob = lob;
    }

    /**
     * Obtiene el identificador del usuario.
     * 
     * @return Identificador del usario.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Obtiene el identificador del usuario.
     * 
     * @param userId
     *            Identificador del usuario.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define el nombre del usuario.
     * 
     * @param username
     *            Nombre del usuario.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre del usuario.
     * 
     * @param nombre
     *            Nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador del rol.
     * 
     * @return identificador del rol.
     */
    public String getRolId() {
        return rolId;
    }

    /**
     * Define el identificador del rol.
     * 
     * @param rolId
     *            Identificador del rol.
     */
    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    /**
     * Obtiene la descripcion del rol.
     * 
     * @return Descripcion del rol.
     */
    public String getDescripcionRol() {
        return descripcionRol;
    }

    /**
     * Define la descripcion del rol.
     * 
     * @param descripcionRol
     *            Descripcion del rol.
     */
    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    /**
     * Indica si el producto esta vigente en la configuracion.
     * 
     * @return S y N.
     */
    public String getVigencia() {
        return vigencia;
    }

    /**
     * Define si el producto esta vigente en la configuracion.
     * 
     * @param vigencia
     *            S y N.
     */
    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return username;
    }

}
