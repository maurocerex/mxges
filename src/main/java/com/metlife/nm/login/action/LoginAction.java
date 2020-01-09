package com.metlife.nm.login.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.LdapProperties;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.login.service.LdapService;
import com.metlife.nm.login.service.LoginService;
import com.metlife.nm.login.vo.LdapResponseVO;
import com.metlife.nm.menu.action.MenuBuilder;
import com.opensymphony.xwork2.ActionSupport;

@Results( { @Result(name = "input", location = "login.jsp"), @Result(name = "success", location = "welcome.jsp"),
		@Result(name = "error", location = "error.jsp") })
public class LoginAction extends ActionSupport implements SessionAware, RequestAware {

	private static final long serialVersionUID = -8672292551232357038L;

	private static final Logger log = Logger.getLogger(LoginAction.class);


	@Resource(name = BeanNames.LoginService)
    protected LoginService service;
	
	@Resource(name = BeanNames.LdapProperties)
	protected LdapProperties ldapProperties;

	protected Map<String, Object> session;

	@Resource(name = BeanNames.LdapService)
    protected LdapService ldapService;
	
	@SuppressWarnings("unchecked")
	private Map request;

	private String username;
	private String password;
	private String forgotPwdUrl;
	private String changePwdUrl;

	public String input() throws Exception {
		if (log.isInfoEnabled()) {
			log.info("input...");
		}
		forgotPwdUrl = ldapProperties.getForgotPwdUrl();
		changePwdUrl = ldapProperties.getChangePwdUrl();
		return INPUT;
	}

	private boolean validaRequeridos(){
		boolean valido = true;
		if(StringUtils.isBlank(username)){
			addFieldError("username", getText("login.usuario.requerido"));
			valido = false;
		}
		if(StringUtils.isBlank(password)){
			addFieldError("password", getText("login.password.requerido"));
			valido = false;
		}

		return valido;
	}

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		if (log.isInfoEnabled()) {
			log.info("execute...");
		}
		forgotPwdUrl = ldapProperties.getForgotPwdUrl();
		changePwdUrl = ldapProperties.getChangePwdUrl();
		
		boolean ldapValid = false;
		String result = INPUT;
		if(!validaRequeridos()){
			return result;
		}
		username = username.toUpperCase();

		UserVO userLdapVO = new UserVO();
		try{
			if(ldapProperties.isLdapActive()){
				userLdapVO.setUsername(username);
				userLdapVO.setPassword(password);
				LdapResponseVO ldapResponseVO = ldapService.authenticateUser(userLdapVO);
				if(ldapResponseVO.isAuthenticated()){
					ldapValid = true;
				}else{
					//mostrar descripcion del error de LDAP en pantalla
					addFieldError("msg", getText("login.msg.ldap") + ldapResponseVO.getDescError());
					if("3003".equals(ldapResponseVO.getCodeError())){//3003 = Invalid Password
						addFieldError("retryCount", getText("login.retry.count"));
					}
				}
			}else{
				//simulamos que ldap autentico correctamente
			    if(log.isDebugEnabled()){
			        log.debug("se emula la conexion del usuario LDAP");
			    }
				ldapValid = true;
			}


			if(ldapValid){
				UserVO user = service.getUsuarioPorUsername(username);
				//user.setVigente(true);
				//validamos que el usuario exista en BD
				if(user!=null && user.isVigente()){
					user.setIdMenuMap(service.getPantallasPorRol(user.getRolId()));
					new MenuBuilder().buildMenuForUser(user);
					if(ldapProperties.isLdapActive()){
						//Las propiedades de LDAP sobreescriben las de BD
						user.setNombre(userLdapVO.getNombre());
						user.setEmail(userLdapVO.getEmail());
						session.put(ConstantesMxges.BASESID, ldapProperties.getDataBaseSid());
					}else{
						//Colocamos un mail de pruebas, esto no debe pasar nunca en metlife, 
						//solo en ambiente de desarrollo donde no haya LDAP
						//user.setEmail("soporte01_PLAD@meltsan.com");
						
					}
					session.put(ConstantesMxges.USER, user);
					
					request.put("show", "true");
					result = SUCCESS;
				}else if(user!=null){
					addFieldError("msg", getText("login.usuario.noVigente"));
				}else{
					addFieldError("msg", getText("login.usuario.noExiste"));
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
			addFieldError("msg", getText("login.error"));
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public String dummy() throws Exception {
		if (log.isInfoEnabled()) {
			log.info("dummy...");
		}

		UserVO user = new UserVO();
		user.setUserId(0);
		user.setUsername("cliff");
		user.setNombre("Cliff Burton");
		user.setRol("Test");
		user.setRolId("a");
		user.setEmail("cliff@meltsan.com");

		session.put(ConstantesMxges.USER, user);
		request.put("show", "true");

		if (log.isDebugEnabled()) {
			log.debug("sending 2 welcome...");
		}

		return SUCCESS;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@SuppressWarnings("rawtypes")
    public void setRequest(Map request) {
		this.request = request;
	}

	@SuppressWarnings("rawtypes")
    public final Map getRequest() {
		return request;
	}

	public final Map<String, Object> getSession() {
		return session;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public String getForgotPwdUrl() {
		return forgotPwdUrl;
	}

	public String getChangePwdUrl() {
		return changePwdUrl;
	}
	
	

}
