package com.metlife.nm.login.service;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.GenericService;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.login.dao.LoginDao;

@Service(value = BeanNames.LoginService)
public class LoginServiceImpl extends GenericService implements LoginService {

	
    @Resource(name = BeanNames.LoginDao)
    protected LoginDao loginDao;
    
	public UserVO getUsuarioPorUsername(String username) {
	    return loginDao.getUsuarioPorUsername(username);
	}
	
	public Map<String, String> getPantallasPorRol(String rolId) throws IOException{
	    Map<String, String> mapPantallas = loginDao.getPantallasPorRol(rolId);
        return mapPantallas;
	}

}
