package com.metlife.nm.login.dao;

import java.util.Map;

import com.metlife.nm.domain.UserVO;

public interface LoginDao {

    UserVO getUsuarioPorUsername(String username);

    Map<String, String> getPantallasPorRol(String rolId);

}
