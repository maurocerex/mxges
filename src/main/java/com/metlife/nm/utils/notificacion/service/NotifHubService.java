package com.metlife.nm.utils.notificacion.service;

public interface NotifHubService {
	
	public boolean invokeHubRenew(String procesoHUB) throws Exception;
		
	public String getDateEs_MX_Formated(String dateIn);
	
	
}
