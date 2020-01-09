package com.metlife.nm.notificaciones.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.notificaciones.dao.NotificacionesDao;
import com.metlife.nm.notificaciones.service.NotificacionesService;
import com.metlife.nm.notificaciones.vo.FrecuenciaVO;
import com.metlife.nm.notificaciones.vo.MensajeVO;
import com.metlife.nm.notificaciones.vo.NotificacionVO;
import com.metlife.nm.utils.notificacion.service.SchedulerService;

@Service(value = BeanNames.NotificacionesService)
public class NotificacionesServiceImpl implements NotificacionesService {

    private static final Logger log = Logger.getLogger(NotificacionesServiceImpl.class);

    @Resource(name = BeanNames.NotificacionesDao)
    private NotificacionesDao dao;
    
    @Autowired
    private SchedulerService schedulerService;

    public List<CatalogoVO> getLobs() {
        return dao.getLobs();
    }

    public List<CatalogoVO> getCatStatus() {
        return dao.getCatStatus();
    }
    
    public List<CatalogoVO> getCatCanalEnvio() {
        return dao.getCatCanalEnvio();
    }

    public List<CatalogoVO> getCatMedio() {
        return dao.getCatMedio();
    }
    
    
    public List<CatalogoVO> getCatCanal() {
    	List<CatalogoVO> canalesList= new ArrayList<CatalogoVO>();
    	CatalogoVO vo= new CatalogoVO();
    	vo.setKey(1);
    	vo.setValue("NORMAL");
    	vo.setKeyTxt("NORMAL");
    	canalesList.add(vo);
    	
    	vo= new CatalogoVO();
    	vo.setKey(2);
    	vo.setValue("HUB");
    	vo.setKeyTxt("HUB");
    	canalesList.add(vo);
    	
    	vo= new CatalogoVO();
    	vo.setKey(3);
    	vo.setValue("FILES");
    	vo.setKeyTxt("FILES");
    	canalesList.add(vo);
    	
    	return canalesList;
    	
    	
    }

    public List<CatalogoVO> getListadoProcesos(String lob) {
        return dao.getListadoProcesos(lob);
    }

    public List<CatalogoVO> getMensajes(String lob, String proceso, String medio) {
        return dao.getMensajes(lob, proceso, medio);
    }

    public MensajeVO getMensaje(String idMensaje) {
        return dao.getMensaje(idMensaje);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void guardar(List<NotificacionVO> registros, UserVO userVO) {
    	
        for (NotificacionVO notificacion : registros) {
            if (notificacion.isNuevo()) {
                dao.insertar(notificacion, userVO);
                for (FrecuenciaVO frec : notificacion.getFrecuencia()) {
                    dao.insertarFrecuencia(frec);
                }
            } else {
                dao.actualizar(notificacion, userVO);
                dao.borrarFrecuencias(notificacion);
                for (FrecuenciaVO frec : notificacion.getFrecuencia()) {
                    if (dao.getDiaFrec(notificacion, frec.getIdDia()) != null) {
                        dao.updateFrecuencia(frec);
                    } else{
                        dao.insertarFrecuencia(frec);
                    } 
                }
            }
        }
        dao.updateNotifyPen();
        schedulerService.configurarSchedulers();
        
       
    }

    public List<NotificacionVO> busqueda(String proceso, String lob, String status) {
        ArrayList<NotificacionVO> lista = (ArrayList<NotificacionVO>) dao.busqueda(proceso, lob, status);
        ArrayList<NotificacionVO> notificacion = new ArrayList<NotificacionVO>();
        for (NotificacionVO vo : lista) {
            vo.setFrecuencia(dao.getFrecuenciasByIdNotificacion(vo));
            System.out.println(vo.getIdProceso() + " --  " + vo.getIdNotificacion() + " --  " + vo.getCanalEnvio());
            notificacion.add(vo);
        }
        return notificacion;

    }

    
    
    
}
