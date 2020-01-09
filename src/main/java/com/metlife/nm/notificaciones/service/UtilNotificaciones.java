package com.metlife.nm.notificaciones.service;


import java.text.ParseException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;

import com.metlife.nm.notificaciones.vo.ExpresionNotificacionVO;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;



public class UtilNotificaciones {

private static final Logger log = Logger.getLogger(UtilNotificaciones.class);
SchedulerFactory SchedFact=null;
Scheduler sched=null;	
	
public ArrayList <ExpresionNotificacionVO> CreaExpresion(NotificacionVO notificacion){
	
	if (log.isDebugEnabled()) {
        log.debug("CreaExpresion.... ");
    }
	ArrayList <ExpresionNotificacionVO> listaux= new ArrayList <ExpresionNotificacionVO>();
	String cadaux=null, cadaux2=null;
	ArrayList <FrecuenciaVO> iguales = new ArrayList<FrecuenciaVO>();
	boolean igual =false;
	FrecuenciaVO frecuenciaaux=null;
	for (FrecuenciaVO frecuencia:notificacion.getFrecuencia()){
		igual =false;
		if (iguales.size()>0){
			for (FrecuenciaVO frecuencia2:iguales){
				if (frecuencia2.getHora()==frecuencia.getHora() && frecuencia2.getMinuto()==frecuencia.getMinuto()){
					frecuencia2.setIdDia(frecuencia2.getIdDia().concat(",".concat(frecuencia.getIdDia())));
					igual=true;
				}
			}
			if(!igual){
				frecuenciaaux= new FrecuenciaVO();
				frecuenciaaux.setIdDia(frecuencia.getIdDia());
				frecuenciaaux.setHora(frecuencia.getHora());
				frecuenciaaux.setMinuto(frecuencia.getMinuto());
				iguales.add(frecuenciaaux);
			}
			
		}else{
			frecuenciaaux= new FrecuenciaVO();
			frecuenciaaux.setIdDia(frecuencia.getIdDia());
			frecuenciaaux.setHora(frecuencia.getHora());
			frecuenciaaux.setMinuto(frecuencia.getMinuto());
			iguales.add(frecuenciaaux);
		}
		
	}
	Integer cont=0;
	for (FrecuenciaVO frecuenciaFinal:iguales){
		cadaux = "0 " + String.valueOf(frecuenciaFinal.getMinuto()) + " " + String.valueOf(frecuenciaFinal.getHora()) + " ? * " + frecuenciaFinal.getIdDia();
		ExpresionNotificacionVO expaux = new ExpresionNotificacionVO();
		expaux.setExpresion(cadaux);
		cadaux2=notificacion.getIdNotificacion() + "_" + notificacion.getIdProceso() + "_" + notificacion.getMensaje().getIdMensaje() + "_" + notificacion.getIdLob() + "_" + Integer.valueOf(cont);
		if (log.isDebugEnabled()) {
            log.debug("expresion " + Integer.valueOf(cont) + ":"  + cadaux);
            log.debug("id " + Integer.valueOf(cont) + ":"  + cadaux2);
        }
		expaux.setIdExpresion(cadaux2);
		listaux.add(expaux);
		cont++;
	}
		
	return listaux;
	
};

public void CreaJobs(ArrayList <ExpresionNotificacionVO> expresiones) throws SchedulerException, ParseException{
	
	
	
	if (log.isDebugEnabled()) {
        log.debug("CreaJobs.... ");
    }
	
	if (SchedFact == null){
		SchedFact= new org.quartz.impl.StdSchedulerFactory();
		
	}
	
	if ( sched ==null){
		sched =SchedFact.getScheduler();
	}
	
	sched.start();
	for (ExpresionNotificacionVO expresion:expresiones){
		if (log.isDebugEnabled()) {
	        log.debug("cveNotificacion : " + expresion.getCveNotificacion());
	        log.debug("cveProceso : " + expresion.getCveProceso());
	        log.debug("cveMensaje : " + expresion.getCveMensaje());
	        log.debug("cveLineaNegocio : " + expresion.getCveLineaNegocio());
	        log.debug("idExpresion : " + expresion.getIdExpresion());
	        log.debug("Expresion : " + expresion.getExpresion());
	    }
		JobDetail jobDetail = new JobDetail(expresion.getIdExpresion(), null,JobEjecucion.class);
		jobDetail.getJobDataMap().put("cveNotificacion",expresion.getCveNotificacion());
		jobDetail.getJobDataMap().put("cveProceso", expresion.getCveProceso());
		jobDetail.getJobDataMap().put("cveMensaje", expresion.getCveMensaje());
		jobDetail.getJobDataMap().put("cveLineaNegocio", expresion.getCveLineaNegocio());
		CronTrigger trigger = new CronTrigger();
		trigger.setName(expresion.getIdExpresion());
		trigger.setCronExpression(expresion.getExpresion());
		sched.scheduleJob(jobDetail, trigger);
	}	
	
}

public void EliminaJobs(ArrayList <ExpresionNotificacionVO> expresiones) throws SchedulerException{
	if (log.isDebugEnabled()) {
        log.debug("EliminaJobs.... ");
    }
	if (sched!=null){
		sched.shutdown(true);
		sched= null;
		if (log.isDebugEnabled()) {
	        log.debug("Se eliminan jobs.... ");
	    }
	}
	
}

	
}