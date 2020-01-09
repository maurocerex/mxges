package com.metlife.nm.mensajes.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.mensajes.dao.MensajesDao;
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.mensajes.vo.VariableVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;

@Service(value=BeanNames.MensajesService)
public class MensajesServiceImpl implements MensajesService{

    @Resource(name=BeanNames.MensajesDao)
    private MensajesDao dao; 
    
    public List<CatalogoVO> getLobs() {
        return dao.getLobs();
    }

    public List<CatalogoVO> getListadoProcesos(String lob) {
        return dao.getListadoProcesos(lob);
    }

    public List<CatalogoVO> getCatEnvio() {
        return dao.getCatEnvio();
    }
    public List<CatalogoVO> getCatStatus() {
        return dao.getCatStatus();
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void guardar(List<MensajeVO> registros, UserVO userVO) {
        //dao.guardar(registros, user);
        if (userVO == null || userVO.getUsername().trim().length() == 0) {
            throw new IllegalArgumentException(
                    "No se especifico un usuario de sistema");
        }
        for (MensajeVO mensaje : registros) {
            if (mensaje.isNuevo()) {
                dao.insertar(mensaje, userVO);
            } else if (mensaje.isModificado()) {
                dao.actualizar(mensaje, userVO);
            } else {
                throw new RuntimeException(
                        "Valor incorrecto para guardar los cambios de un registro de usuarios.");
            }

        }
    }

    public int getCountMensajes() {
        return dao.getCountMensajes();
    }

    public List<MensajeVO> getMensajes() {
        return dao.getMensajes();
    }

    public List<MensajeVO> busqueda(String proceso, String lob, String medio, String status) {
        return dao.busqueda(proceso, lob, medio, status);
    }

	public List<VariableVO> getVariables(String proceso) {
		return dao.getVariables(proceso);		
	}
	
	public boolean existeColumnaFisica(String columna){
	    return dao.existeColumnaFisica(columna);  
	}

	public boolean asociado(String idNotificacion, String idMensaje){
	    ArrayList<NotificacionVO> tmp = dao.asociado(idNotificacion, idMensaje);
        
        if(tmp.size()==0){
            //no esta asociado
            return false;
        }else{
            //esta asociado
            return true;
        }
        
    }


}
