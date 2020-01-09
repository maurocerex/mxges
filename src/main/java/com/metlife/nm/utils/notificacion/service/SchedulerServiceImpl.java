package com.metlife.nm.utils.notificacion.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Component;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.utils.notificacion.dao.EnviaNotificacionesDao;
import com.metlife.nm.utils.notificacion.vo.NotificacionVO;

@Component("SchedulerService")
public class SchedulerServiceImpl implements SchedulerService {
	
	private static final Logger log = Logger.getLogger(SchedulerServiceImpl.class);

	@Resource(name = BeanNames.EnviaNotificacionDao)
	private EnviaNotificacionesDao dao;
	
    @Resource(name = BeanNames.SCHEDULER)
    private Scheduler scheduler;
    
    @Autowired
    Proceso proceso;

	public void configurarSchedulers() {
		List<NotificacionVO> notificaciones = dao.getNotificacionesActivas();
		int i = 0;
		borrarJobs();
		for(NotificacionVO notificacion: notificaciones){  //Uno por notificacion Activa
			List<FrecuenciaVO> frecuencias = dao.getFrecuenciasByIdNotificacion(notificacion);
			for(FrecuenciaVO frecuencia: frecuencias){ //Uno por cada dia
				try{
					String expression = expression(frecuencia.getIdDia(), frecuencia.getHora(), frecuencia.getMinuto());
					MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
			        jobDetail.setTargetObject(proceso);
			        jobDetail.setTargetMethod("performAction");
			        jobDetail.setName(notificacion.getNotificacionDesc() + notificacion.getIdLob() + frecuencia.getIdDia());
//			        Object[] arrayObject = {notificacion.getIdLob(), notificacion.getIdProceso()};
			        Object[] arrayObject = {notificacion};
			        jobDetail.setArguments(arrayObject);
			        jobDetail.setConcurrent(false);
			        jobDetail.afterPropertiesSet();
			        CronTriggerBean cronTrigger = new CronTriggerBean();
			        cronTrigger.setBeanName(notificacion.getNotificacionDesc() + notificacion.getIdLob() + frecuencia.getIdDia());
			        cronTrigger.setCronExpression(expression);
			        cronTrigger.afterPropertiesSet();
			        scheduler.scheduleJob((JobDetail)jobDetail.getObject(), cronTrigger);
			        scheduler.start();
			        i++;
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
			}		      
		}
	}
	
	private void borrarJobs(){
		try{
			if(scheduler.getJobNames("DEFAULT").length > 0 ){
				for(String name : scheduler.getJobNames("DEFAULT")){
					log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + name);
					scheduler.deleteJob(name, "DEFAULT");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String expression(String dia, int hora, int minuto){
		String expression = "";
		expression = "0 " + minuto  + " " + hora + " ? * " + dia.toUpperCase();
		return expression;
	}

}
