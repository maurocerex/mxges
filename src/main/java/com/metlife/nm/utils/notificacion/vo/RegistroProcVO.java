package com.metlife.nm.utils.notificacion.vo;


public class RegistroProcVO implements Comparable<RegistroProcVO> {

    private int idProceso;
    private int idRegistro;
    public final int getIdProceso() {
        return idProceso;
    }
    public final void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }
    public final int getIdRegistro() {
        return idRegistro;
    }
    public final void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }
    public int compareTo(RegistroProcVO o) {
        if(idProceso == o.getIdProceso() && idRegistro == o.getIdRegistro()){
            return 0;
        }else{
            return 1;
        }
    }
    
}
