package com.metlife.nm.login.service;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.naming.directory.DirContext;

import mx.com.iw.ldap.LDAPManager;
import mx.com.iw.ldap.Person;
import mx.com.iw.ldap.SUIDException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.LdapProperties;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.login.vo.LdapResponseVO;

@Service(value = BeanNames.LdapService)
public class LdapServiceImpl implements LdapService{
	
	private static Logger log = Logger.getLogger(LdapServiceImpl.class);
	
	@Resource(name = BeanNames.LdapProperties)
	protected LdapProperties ldapProperties;
	
	@SuppressWarnings("unchecked")
	public LdapResponseVO authenticateUser(UserVO userVO){
		LdapResponseVO ldapResponseVO = new LdapResponseVO();
		try {
			LDAPManager ldapmgr = new LDAPManager();
			DirContext ctx = null;
			
			Person p = new Person();
			p.setUserID(userVO.getUsername());
			p.setPassword(userVO.getPassword());
			p.setHierarchy(ldapProperties.getHierarchy());

			ctx = ldapmgr.authenticate(ldapProperties.getUrl(), p);

			ldapmgr.setCtx(ctx);

			ArrayList<Person> personList = ldapmgr.searchUserInLDAP(p);
			if (personList != null) {
				for(Person person: personList) {
					userVO.setNombre(person.getCommonName());
					userVO.setEmail(person.getEmail());
					ldapResponseVO.setPasswordRetryCount(person.getPasswordRetryCount());
					ldapResponseVO.setAuthenticated(true);
				}
			} else {
				log.warn("empty list");
			}

		} catch (SUIDException se){
			log.error(se.getFaultString(), se);
			ldapResponseVO.setCodeError(se.getFaultCode());
			ldapResponseVO.setDescError(se.getFaultName());
		} 
		
		return ldapResponseVO;
	}
}
