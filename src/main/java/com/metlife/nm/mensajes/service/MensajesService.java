package com.metlife.nm.mensajes.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.mensajes.vo.VariableVO;

public interface MensajesService {

    List<CatalogoVO> getLobs();

    List<CatalogoVO> getListadoProcesos(String lob);

    void guardar(List<MensajeVO> registros, UserVO user);

    int getCountMensajes();

    List<MensajeVO> getMensajes();

    List<MensajeVO> busqueda(String proceso, String lob, String medio, String status);

	List<VariableVO> getVariables(String proceso);

	List<CatalogoVO> getCatEnvio();

	List<CatalogoVO> getCatStatus();

	boolean existeColumnaFisica(String columna);
	 
	boolean asociado(String idNotificacion, String idMensaje);
}