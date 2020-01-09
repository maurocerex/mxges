package com.metlife.nm.usuarios.dao;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.usuarios.vo.UsuarioVO;

public interface UsuariosDao {

    
    public List<UsuarioVO> getUsuarios();

    public List<CatalogoVO> getRoles();

    public void insertar(UsuarioVO usuarioVO, UserVO userVO) throws Exception;

    public void actualizar(UsuarioVO usuarioVO, UserVO userVO);

    public List<CatalogoVO> getLobs();

	public boolean existeUsername(String username);
    
}
