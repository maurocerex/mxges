package com.metlife.nm.notificaciones.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.vo.MensajeVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public interface NotificacionesService {

    List<CatalogoVO> getLobs();

    List<CatalogoVO> getListadoProcesos(String lob);

    List<CatalogoVO> getMensajes(String lob, String proceso, String medio);

    MensajeVO getMensaje(String idMensaje);

    void guardar(List<NotificacionVO> registros, UserVO user);

    List<NotificacionVO> busqueda(String proceso, String lob, String status);

	List<CatalogoVO> getCatStatus();
	
	List<CatalogoVO> getCatCanalEnvio();

	List<CatalogoVO> getCatMedio();
	
	List<CatalogoVO> getCatCanal();



    

}
