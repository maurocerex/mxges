package com.metlife.nm.notificaciones.service;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

import com.metlife.nm.utils.notificacion.service.EnviaNotificacionServiceImpl;


public class JobEjecucion implements Job{

private static final Logger log = Logger.getLogger(JobEjecucion.class);
	

	

public JobEjecucion(){}

	

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
	//SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);	
		
		JobDataMap dataMap = arg0.getJobDetail().getJobDataMap();

	if (log.isDebugEnabled()) {
		log.debug("execute JobEjecucion...");
	}
    
	String cveNotificacion=dataMap.getString("cveNotificacion");
	String cveProceso=dataMap.getString("cveProceso");
	String cveMensaje=dataMap.getString("cveMensaje");
	String cveLineaNegocio=dataMap.getString("cveLineaNegocio");


	if (log.isDebugEnabled()) {
		log.debug("cveNotificacion :" + cveNotificacion);
		log.debug("cveProceso :" + cveProceso);
		log.debug("cveMensaje :" + cveMensaje);
		log.debug("cveLineaNegocio :" + cveLineaNegocio);
	}
   
	EnviaNotificacionServiceImpl enviaNotificacionService = new EnviaNotificacionServiceImpl();
	
	enviaNotificacionService.enviaNotificaciones();	
	}
}
