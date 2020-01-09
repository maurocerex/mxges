package com.metlife.nm.cifras.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.cifras.dao.CifrasDao;
import com.metlife.nm.cifras.vo.CifrasVO;
import com.metlife.nm.cifras.vo.ReenvioVO;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.utils.mail.service.EnviaMailService;
import com.metlife.nm.utils.notificacion.service.EnviaNotificacionServiceImpl;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;
import com.metlife.nm.utils.sms.service.InvokeWebServiceMessage;

@Service(value = BeanNames.CifrasService)
public class CifrasServiceImpl implements CifrasService {

    protected static SimpleDateFormat SDF_DDMMYYYY_HHMMSS_ARCHIVO = new SimpleDateFormat("ddMMyyyy_HHmmss");

    private static final Logger log = Logger.getLogger(CifrasServiceImpl.class);

    @Resource(name = BeanNames.CifrasDao)
    private CifrasDao dao;

    @Resource(name = BeanNames.EnviaMailService)
    private EnviaMailService enviaMailService;

    @Resource(name = BeanNames.EnviaNotificacionService)
    private EnviaNotificacionServiceImpl envio;

    @Resource(name = "InvokeWebServiceMessage")
    private InvokeWebServiceMessage smsService;

    public List<CatalogoVO> getLobs() {
        return dao.getLobs();
    }
    
    public List<CatalogoVO> getCatMedio() {
        return dao.getCatMedio();
    }

    public int getVigencia() {
        return dao.getVigencia();
    }

    public List<CatalogoVO> getListadoProcesos() {
        return dao.getListadoProcesos();
    }

    public List<CifrasVO> getCifras(String proceso, String medio, String fecInicio, String fecFin, String tipoReporte) {
        return dao.getCifras(proceso, medio, fecInicio, fecFin, tipoReporte);
    }

    public List<CifrasVO> getDetalle(String proceso, String medio, String fecInicio, String fecFin) {
        return dao.getDetalle(proceso, medio, fecInicio, fecFin);
    }
    
    public List<Map<String,Object>> getReporte(String proceso, String medio, String fecIni, String fecFin, String tipoReport) {
        return dao.getReporte(proceso, medio, fecIni, fecFin, tipoReport);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void guardar(List<CifrasVO> registros) {
        String idProceso = null;
        String medio = null;
        String fecInicio = null;
        String fecFin = null;

        for (CifrasVO vo : registros) {
            idProceso = vo.getIdProceso();
            medio = vo.getMedioEnvio();
            fecInicio = vo.getFechaIni();
            fecFin = vo.getFecFin();

            if (log.isDebugEnabled()) {
                log.debug("idProceso  -> " + idProceso);
                log.debug("medio  -> " + medio);
                log.debug("fecInicio  -> " + fecInicio);
                log.debug("fecFin  -> " + fecFin);

            }
            final ArrayList<ReenvioVO> listaReenvio = (ArrayList<ReenvioVO>) dao.getRegistrosReenvio(idProceso, medio, fecInicio, fecFin);
            Thread procesoAsincrono = new Thread() {
                public void run() {
                    for (ReenvioVO obj : listaReenvio) {

                        if (obj.getMedioEnvio().equals(ConstantesMxges.EMAIL)) {
                            MensajeVO men = new MensajeVO();

                            men.setAsunto(obj.getSubject());
                            men.setCuerpo(obj.getBody());

                            if (StringUtils.isNotBlank(obj.getDestinatario())) {
                                try {
                                    enviaMailService.enviaMails(men, obj.getDestinatario());
                                    envio.updEnvio(ConstantesMxges.ENVIADO, obj.getIdEnvio(), null);
                                } catch (Exception e) {
                                    StringBuffer stb = new StringBuffer();
                                    stb.append("No se pudo conectar al servidor SMTP");
                                    for(int y = 0; y<e.getStackTrace().length; y++){
                                        stb.append(e.getStackTrace()[y].toString()+"\n");
                                    }
                                    stb.setLength(500);
                                    envio.updEnvio(ConstantesMxges.ERROR, obj.getIdEnvio(), stb.toString());
                                }
                            } else {
                                envio.updEnvio(ConstantesMxges.FALTA_DATOS_CONTACTO, obj.getIdEnvio(), obj.getDestinatario());
                            }

                        }
                        if (obj.getMedioEnvio().equals(ConstantesMxges.SMS)) {
                            if (StringUtils.isNotBlank(obj.getDestinatario())) {
                                try {
                                    smsService.sendMessageService(obj.getDestinatario(), obj.getBody());
                                    envio.updEnvio(ConstantesMxges.ENVIADO, obj.getIdEnvio(), null);
                                } catch (IllegalAccessException e) {
                                    envio.updEnvio(ConstantesMxges.FALTA_DATOS_CONTACTO, obj.getIdEnvio(), obj.getDestinatario());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    StringBuffer stb = new StringBuffer();
                                    stb.append("Error gen�rico en respuesta a invocaci�n del WS");
                                    for(int y = 0; y<e.getStackTrace().length; y++){
                                        stb.append(e.getStackTrace()[y].toString()+"\n");
                                    }
                                    stb.setLength(500);
                                    envio.updEnvio(ConstantesMxges.ERROR, obj.getIdEnvio(), stb.toString());
                                }
                            } else {
                                envio.updEnvio(ConstantesMxges.FALTA_DATOS_CONTACTO, obj.getIdEnvio(), obj.getDestinatario());
                            }
                        }
                    }
                }
            };

            procesoAsincrono.start();

        }

    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateCC(String tipoReporte){
    	int updateNotif  = 0;
    	try{
    		if(tipoReporte.equalsIgnoreCase(ConstantesMxges.TIPO_REPORTE_CIFRAS_CONTROL)){
    			updateNotif = dao.updateCC(tipoReporte);
    		}
    	}catch (Exception e){
    		updateNotif = 0;
    		e.printStackTrace();
    		return updateNotif;
    	}
    	return updateNotif;
    }

}
