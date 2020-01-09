package com.metlife.nm.notificaciones.dao;

import java.util.ArrayList;
import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.notificaciones.vo.MensajeVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public interface NotificacionesDao {

    List<CatalogoVO> getLobs();
    List<CatalogoVO> getListadoProcesos(String lob);
    List<CatalogoVO> getMensajes(String lob, String proceso, String medio);
    MensajeVO getMensaje(String idMensaje);
    void insertar(NotificacionVO notificacion, UserVO userVO);
    List<NotificacionVO> busqueda(String proceso, String lob, String status);
    void actualizar(NotificacionVO notificacion, UserVO userVO);
    void insertarFrecuencia(FrecuenciaVO frec);
    void updateFrecuencia(FrecuenciaVO frec);
    List<FrecuenciaVO> getFrecuenciasByIdNotificacion(NotificacionVO notificacion);
	List<CatalogoVO> getCatStatus();
	List<CatalogoVO> getCatMedio();
	FrecuenciaVO getDiaFrec(NotificacionVO notificacion, String idDia);
    void borrarFrecuencias(NotificacionVO notificacion);
    void updateNotifyPen();
	List<CatalogoVO> getCatCanalEnvio();
    

}
