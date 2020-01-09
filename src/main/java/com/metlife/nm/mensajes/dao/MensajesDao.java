package com.metlife.nm.mensajes.dao;

import java.util.ArrayList;
import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.mensajes.vo.VariableVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

public interface MensajesDao {

    List<CatalogoVO> getLobs();

    List<CatalogoVO> getListadoProcesos(String lob);

    void insertar(MensajeVO mensaje, UserVO user);

    int getCountMensajes();

    List<MensajeVO> getMensajes();

    void actualizar(MensajeVO mensaje, UserVO userVO);

    List<MensajeVO> busqueda(String proceso, String lob, String medio, String status);

	List<VariableVO> getVariables(String proceso);

	List<CatalogoVO> getCatEnvio();

	List<CatalogoVO> getCatStatus();

    boolean existeColumnaFisica(String columna);

    ArrayList<NotificacionVO> asociado(String idNotificacion, String idMensaje);
    
	/**
	 * Obtiene la propiedad REM_ACENTOS de MXGES_V_METATABLAS1 para saber si se
	 * tienen que enviar acentos en las notificaciones via SMS.
	 * 
	 * @return True.- Si se tienen que enviar, de lo contrario False.
	 */
	boolean getAccentProperty();
	
	/**
	 * Actualiza la bandera para remover o no acentos en las notificaciones via
	 * SMS.
	 * 
	 * @param estatus
	 *            S.- Enciende, N.- Apaga.
	 */
	void updateAccentProperty(String estatus);
}
