package com.metlife.nm.notificaciones.vo;

import com.metlife.nm.domain.BaseObjectVO;

public class DiaVO extends BaseObjectVO{

    /**
     * 
     */
    private static final long serialVersionUID = -7589755111028485340L;
    
    private int idFrecuencia;
    private String diaEnvio;
    private String dia;
    private int hora;
    private int min;
    
    public final String getDia() {
        return dia;
    }
    public final void setDia(String dia) {
        this.dia = dia;
    }
    public final int getHora() {
        return hora;
    }
    public final void setHora(int hora) {
        this.hora = hora;
    }
    public final int getMin() {
        return min;
    }
    public final void setMin(int min) {
        this.min = min;
    }
	public String getDiaEnvio() {
		return diaEnvio;
	}
	public void setDiaEnvio(String diaEnvio) {
		this.diaEnvio = diaEnvio;
	}
	public int getIdFrecuencia() {
		return idFrecuencia;
	}
	public void setIdFrecuencia(int idFrecuencia) {
		this.idFrecuencia = idFrecuencia;
	}
}
