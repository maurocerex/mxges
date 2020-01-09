package com.metlife.nm.usuarios.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.usuarios.dao.UsuariosDao;
import com.metlife.nm.usuarios.vo.UsuarioVO;

@Service(value=BeanNames.UsuariosService)
public class UsuariosServiceImpl implements UsuariosService{

    
    @Resource(name=BeanNames.UsuariosDao)
    private UsuariosDao dao;
    
    public List<UsuarioVO> getUsuarios() {
        return dao.getUsuarios();
    }

    public List<CatalogoVO> getRoles() {
        return dao.getRoles();
    }
    
    public List<CatalogoVO> getLobs() {
        return dao.getLobs();
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void guardarCambios(List<UsuarioVO> registros, UserVO userVO)throws Exception {
        
        if (userVO == null || userVO.getUsername().trim().length() == 0) {
            throw new IllegalArgumentException(
                    "No se especifico un usuario de sistema");
        }
        for (UsuarioVO usuarioVO : registros) {
            if (usuarioVO.isNuevo()) {
                dao.insertar(usuarioVO, userVO);
            } else if (usuarioVO.isModificado()) {
                dao.actualizar(usuarioVO, userVO);
            } else {
                throw new RuntimeException(
                        "Valor incorrecto para guardar los cambios de un registro de usuarios.");
            }

        }
        
    }

    public boolean isUsernameRepetido(String username) {
        
            if(dao.existeUsername(username)){
            	return true;            	
            }
        return false;
        
    }

    
}
