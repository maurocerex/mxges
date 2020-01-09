package com.metlife.nm.domain;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class UserVO extends BaseObjectVO {

    private static final long serialVersionUID = 4305764171398414600L;

    private int userId;
    private String username;
    private String rol;
    private String nombre;
    private String rolId;
    private String email;
    private String password;
    private boolean vigente;
    private Map<String, String> idMenuMap;
    private String jsonMenu;

    public boolean hasIdMenu(String idMenu) {
        return idMenuMap.containsKey(idMenu);
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    

    public String getRolId() {
        return rolId;
    }

    public void setRolId(String rolId) {
        this.rolId = rolId;
    }

    public final int getUserId() {
        return userId;
    }

    public final void setUserId(int userId) {
        this.userId = userId;
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(String username) {
        this.username = username;
    }

    public final String getRol() {
        return rol;
    }

    public final void setRol(String rol) {
        this.rol = rol;
    }

    public final String getNombre() {
        return nombre;
    }

    public final void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public final String getNombreCompleto() {
        if (StringUtils.isBlank(nombre)) {
            nombre = "";
        }
        return new StringBuffer(nombre).append(" (").append(rol).append(" )").toString();
    }

    public final String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getIdMenuMap() {
        return idMenuMap;
    }

    public void setIdMenuMap(Map<String, String> idMenuMap) {
        this.idMenuMap = idMenuMap;
    }

    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }

    public boolean isVigente() {
        return vigente;
    }

    public void setJsonMenu(String jsonMenu) {
        this.jsonMenu = jsonMenu;
    }

    public String getJsonMenu() {
        return jsonMenu;
    }

}
