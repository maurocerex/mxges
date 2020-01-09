package com.metlife.nm.login.service;

import com.metlife.nm.domain.UserVO;
import com.metlife.nm.login.vo.LdapResponseVO;

public interface LdapService {
	LdapResponseVO authenticateUser(UserVO userVO);
}
