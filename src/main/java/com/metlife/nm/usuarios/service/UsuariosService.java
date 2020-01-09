package com.metlife.nm.usuarios.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.usuarios.vo.UsuarioVO;

public interface UsuariosService {

    
    public List<UsuarioVO> getUsuarios();

    public List<CatalogoVO> getRoles();

    public void guardarCambios(List<UsuarioVO> registros, UserVO user) throws Exception;

    public List<CatalogoVO> getLobs();

	public boolean isUsernameRepetido(String username);
}
