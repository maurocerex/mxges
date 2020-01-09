package com.metlife.nm.error;

import org.apache.commons.lang.StringUtils;

/**
 * Excepcion que se lanza el ocurrir una excepcion del servicio.
 * 
 * @author Meltsan Solutions 
 *
 */
public class ServiceInvocationException extends Exception {

	/**
	 * Serial Version ID 
	 */
	private static final long serialVersionUID = -3532627446483713121L;
	
	public ServiceInvocationException(String msg) {
		super(StringUtils.isEmpty(msg) ? "�Error en el llamado al Servicio Web SMS!" : msg);
	}

}
