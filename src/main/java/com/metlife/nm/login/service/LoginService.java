package com.metlife.nm.login.service;

import java.io.IOException;
import java.util.Map;

import com.metlife.nm.domain.UserVO;


public interface LoginService {

	UserVO getUsuarioPorUsername(String username);
	Map<String, String> getPantallasPorRol(String rolId) throws IOException;
	
}
