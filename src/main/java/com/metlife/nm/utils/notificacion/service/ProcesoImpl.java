package com.metlife.nm.utils.notificacion.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.notificaciones.service.NotificacionesService;
import com.metlife.nm.utils.mail.service.EnviaMailService;
import com.metlife.nm.utils.notificacion.dao.EnviaNotificacionesDao;
import com.metlife.nm.utils.notificacion.vo.CabeceraVO;
import com.metlife.nm.utils.notificacion.vo.EnvioNotificacionVO;
import com.metlife.nm.utils.notificacion.vo.MailVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;
import com.metlife.nm.utils.notificacion.vo.MxgesEnvioNotificacionVo;
import com.metlife.nm.utils.notificacion.vo.NotificacionVO;
import com.metlife.nm.utils.notificacion.vo.RegistroProcVO;
import com.metlife.nm.utils.notificacion.vo.VarProcVo;
import com.metlife.nm.utils.sms.service.InvokeWebServiceMessage;
import com.metlife.nm.utils.sms.vo.ResponseWebServiceVO;


@Component("proceso")
public class ProcesoImpl implements Proceso{
	
	private static final Logger log = Logger.getLogger(ProcesoImpl.class);
	
	@Resource(name = BeanNames.EnviaNotificacionDao)
    private EnviaNotificacionesDao dao;
	
	@Resource(name = "InvokeWebServiceMessage")
    private InvokeWebServiceMessage smsService;

    @Resource(name = BeanNames.EnviaMailService)
    private EnviaMailService enviaMailService;
   
    @Resource(name= "notifHubService")
    private NotifHubService notifHubService;
    
    @Resource(name = BeanNames.NotificacionesService)
    private NotificacionesService notificacionesService;
    
    @Resource(name= "createFileService")
    private CreateFileService createFileService;
	
	String cadaux;
	
	StringBuffer cabeceroSms;
	StringBuffer qryArchivoSms;
	StringBuffer cabeceroEmail;
	StringBuffer qryArchivoEmail;
	
	public void performAction(NotificacionVO notificacionVO){
		log.debug(" &&&&&&&&&&&& idLob = " + notificacionVO.getIdLob());
		log.debug(" &&&&&&&&&&&& idProceso = " + notificacionVO.getIdProceso());
		log.debug(" &&&&&&&&&&&& iscobranzaBancaria = " + notificacionVO.isCobBancaria());
		int retroactividad = dao.getRetroactividad();
		int limite = dao.getLimite();
		notificacionVO.setCanalEnvio("1");
		if(!StringUtils.isBlank(notificacionVO.getCanalEnvio())){//Se valida canal de envio
			log.debug("-----> CANAL_ENVIO Verificado para el proceso:  " + notificacionVO.getIdProceso() + "  se inicia procesamiento de notificaciones");	
	
			if(notificacionVO.getCanalEnvio().equals("1")){
				if(notificacionVO.getIdProceso().equals("PROC_COBRBANC")){
					log.debug(" &&&&&&&&& tipo respuesta = " + notificacionVO.getTipoRespuesta());
					String V_COL_01 = "";
					List<Map<String,Object>> lstnotificaciones = new ArrayList<Map<String,Object>>();
					
					
					log.debug(" &&&&&&&&&&&&&&& inicia consulta");
					if(notificacionVO.getTipoRespuesta().equals("A")){ //Cargos Aceptados
						V_COL_01 = "IPCBCE102";
						lstnotificaciones = dao.getNotificacionesCobranzaAMail(retroactividad, limite);
					}else{ //Cargos Rechazados
						V_COL_01 = "IPCBCE103";
						lstnotificaciones = dao.getNotificacionesCobranzaRMail(retroactividad, limite);
					}
					log.debug(" &&&&&&&&&&&&&&& termina consulta");
					log.debug(" &&&&&&&&&&&&&&& lstnotificaciones.size = " + lstnotificaciones.size());
					MailVO mailVO = null;
					String body = "";
					String subject = "";
					String V_COL_04 = "";
					String V_DES_MENSAJE_ERROR = "";
					String CadAuxImp="";
					EnvioNotificacionVO envioNotificacionVO= new EnvioNotificacionVO();
					log.debug(" &&&&&&&&&&&&&&& inicia iteraci�n");
					for(int i = 0; i<lstnotificaciones.size();i++){
						envioNotificacionVO.setRegistro(lstnotificaciones.get(i).toString().split(",")[1].split("=")[1]);
						envioNotificacionVO.setProceso(lstnotificaciones.get(i).toString().split(",")[2].split("=")[1]);
						if (lstnotificaciones.get(i).toString().split(",")[3].split("=").length>1)
							envioNotificacionVO.setEmail(lstnotificaciones.get(i).toString().split(",")[3].split("=")[1]);
						else
							envioNotificacionVO.setEmail("");
						if (lstnotificaciones.get(i).toString().split(",")[4].split("=").length>1)
							envioNotificacionVO.setNomina(lstnotificaciones.get(i).toString().split(",")[4].split("=")[1]);
						else
							envioNotificacionVO.setNomina("");
						if (lstnotificaciones.get(i).toString().split(",")[5].split("=").length>1)
							envioNotificacionVO.setPoliza(lstnotificaciones.get(i).toString().split(",")[5].split("=")[1]);
						else
							envioNotificacionVO.setPoliza("");
						if (lstnotificaciones.get(i).toString().split(",")[6].split("=").length>1){
							CadAuxImp=lstnotificaciones.get(i).toString().split(",")[6].split("=")[1];
							CadAuxImp=CadAuxImp.replace("@", ",");
							envioNotificacionVO.setImporte(CadAuxImp);
						}else
							envioNotificacionVO.setImporte("");
						if (lstnotificaciones.get(i).toString().split(",")[7].split("=").length>1)
							envioNotificacionVO.setRefBancaria(lstnotificaciones.get(i).toString().split(",")[7].split("=")[1]);
						else
							envioNotificacionVO.setRefBancaria("");
						if (lstnotificaciones.get(i).toString().split(",")[8].split("=").length>1)
							envioNotificacionVO.setNombre(lstnotificaciones.get(i).toString().split(",")[8].split("=")[1]);
						else
							envioNotificacionVO.setNombre("");
						if (lstnotificaciones.get(i).toString().split(",")[9].split("=").length>1)
							envioNotificacionVO.setPaterno(lstnotificaciones.get(i).toString().split(",")[9].split("=")[1]);
						else
							envioNotificacionVO.setPaterno("");
						if (lstnotificaciones.get(i).toString().split(",")[10].split("=").length>1)
							envioNotificacionVO.setMaterno(lstnotificaciones.get(i).toString().split(",")[10].split("=")[1]);
						else
							envioNotificacionVO.setMaterno("");
						if (lstnotificaciones.get(i).toString().split(",")[10].split("=").length>1)
							{
								cadaux = lstnotificaciones.get(i).toString().split(",")[11].split("=")[1];
								envioNotificacionVO.setFecCobro(cadaux.substring(0, cadaux.length()-1));
							}
						else
							envioNotificacionVO.setFecCobro("");
						
						
						log.debug(" &&&&&&&&&&&&&&& Id_proceso=" + envioNotificacionVO.getProceso() + " id_registro=" + envioNotificacionVO.getRegistro());
						log.debug(" &&&&&&&&&&&&&&& email=" + envioNotificacionVO.getEmail());
						log.debug(" &&&&&&&&&&&&&&& nomina=" + envioNotificacionVO.getNomina());
						log.debug(" &&&&&&&&&&&&&&& poliza=" + envioNotificacionVO.getPoliza());
						log.debug(" &&&&&&&&&&&&&&& importe=" + envioNotificacionVO.getImporte());
						log.debug(" &&&&&&&&&&&&&&& ref bancaria=" + envioNotificacionVO.getRefBancaria());
						log.debug(" &&&&&&&&&&&&&&& nombre=" + envioNotificacionVO.getNombre());
						log.debug(" &&&&&&&&&&&&&&& paterno=" + envioNotificacionVO.getPaterno());
						log.debug(" &&&&&&&&&&&&&&& materno=" + envioNotificacionVO.getMaterno());
						log.debug(" &&&&&&&&&&&&&&& fec cobro=" + envioNotificacionVO.getFecCobro());
						
						if(notificacionVO.getTipoRespuesta().equals("A")){
							body = getBodyCargoAceptado(envioNotificacionVO);
							subject = "MetLife agradece el pago a tu seguro MET99";
						}else{
							body = getBodyCargoRechazado(envioNotificacionVO);
							subject = "Cargo bancario no exitoso de tu Seguro de Vida";
						}
						mailVO = new MailVO();
						mailVO.setEmailTo(envioNotificacionVO.getEmail());
						mailVO.setEmailSubject(subject);
						mailVO.setEmailContent(body);
						try {
							enviaMailService.enviaMailConEstilo(mailVO);
							V_COL_04 = "EN";
							dao.updEnvioNotificaciones(envioNotificacionVO.getRegistro(), envioNotificacionVO.getProceso(), envioNotificacionVO.getNomina(), envioNotificacionVO.getPoliza());
						} catch (MessagingException e) {
							V_DES_MENSAJE_ERROR = e.getMessage();
						} catch (Exception e) {
							V_DES_MENSAJE_ERROR = e.getMessage();	
						}
						dao.insInfoEnvios(envioNotificacionVO.getEmail(), subject, body, envioNotificacionVO.getRegistro(), envioNotificacionVO.getProceso(), V_COL_01, V_COL_04, V_DES_MENSAJE_ERROR);
						
						
					 }
					
					log.debug(" &&&&&&&&&&&&&&& Termina iteraci�n");
					
					
					dao.procesaSinDatos(notificacionVO.getTipoRespuesta(), retroactividad);
					 //procesamiento de notificaciones sin datos de contacto
					/*
					if(notificacionVO.getTipoRespuesta().equals("A")){ //Cargos Aceptados
						V_COL_01 = "IPCBCE102";
						lstnotificaciones = dao.getNotificacionesCobranzaAMailNULL(retroactividad);
					}else{ //Cargos Rechazados
						V_COL_01 = "IPCBCE103";
						lstnotificaciones = dao.getNotificacionesCobranzaRMailNULL(retroactividad);
					}
					
					for(EnvioNotificacionVO envioNotificacionVO: lstnotificaciones){
						if(notificacionVO.getTipoRespuesta().equals("A")){
							body = getBodyCargoAceptado(envioNotificacionVO);
							subject = "MetLife agradece el pago a tu seguro MET99";
						}else{
							body = getBodyCargoRechazado(envioNotificacionVO);
							subject = "Cargo bancario no exitoso de tu Seguro de Vida";
						}
						V_COL_04 = "EN";
						dao.updEnvioNotificaciones(envioNotificacionVO.getRegistro(), envioNotificacionVO.getProceso(), envioNotificacionVO.getNomina(), envioNotificacionVO.getPoliza());
						dao.insInfoEnviosNull(envioNotificacionVO.getEmail(), subject, body, envioNotificacionVO.getRegistro(), envioNotificacionVO.getProceso(), V_COL_01, V_DES_MENSAJE_ERROR);
					}
		*/			
				}
			}
			else if(notificacionVO.getCanalEnvio().equals("2")){
				if(notificacionVO.getIdProceso().equals("PROC_RENEW")){///HAY que CORREGIR
					if(notificacionVO.getCanalEnvio().equals("2")){ // Verifica que el id del canal se va a enviar via HUB servicio
		                String procesoHUB= "RENEW";
						log.debug("->  Entra a ejecutar proceso: "+procesoHUB);
						boolean result= doRenew(notificacionVO, procesoHUB+"_");
						log.debug("Resultado de Envio de archivo a servicio HUB canal: "+ result);
		
					}else if(notificacionVO.getCanalEnvio().equals("1")){ 
						
						int idProceso=0;
						int idRegistro=0;
						List<RegistroProcVO> procesados= new ArrayList<RegistroProcVO>();
						ArrayList<CabeceraVO> cabeceras = (ArrayList<CabeceraVO>) dao.getCabeceras(notificacionVO.getIdLob(), retroactividad, notificacionVO.getIdProceso());
	
						for (CabeceraVO proceso : cabeceras) {
							dao.updCabecera(proceso.getIdProcesoPadre(), ConstantesMxges.INICIADO);
							int idProcesoNuevo = dao.getIdProcesoEnvioNuevo();
							insNuevaCabecera(proceso, idProcesoNuevo);
	
							ArrayList<MensajeVO> mensajes = (ArrayList<MensajeVO>) dao.getMensajes(proceso.getIdProcesoPadre(), notificacionVO, "PROC_RENEW");
							if (log.isDebugEnabled()) {
								log.debug("-----------------------------------------------------------------------MENSAJES---------------- " + mensajes.size());
							}
							for (MensajeVO det : mensajes) {
								procesaMensaje(det, notificacionVO);
								if (idProceso != det.getIdProceso() || idRegistro != det.getIdRegistro()) {
									idProceso = det.getIdProceso();
									idRegistro = det.getIdRegistro();
								}
								RegistroProcVO obj = new RegistroProcVO();
								obj.setIdProceso(idProceso);
								obj.setIdRegistro(idRegistro);
								procesados.add(obj);
							}
							dao.updCabecera(proceso.getIdProcesoPadre(), ConstantesMxges.FINALIZADO);
						}
						for (RegistroProcVO tmp : procesados) {
							dao.updRegistro(tmp.getIdProceso(), tmp.getIdRegistro(), ConstantesMxges.ENVIADO);
						}
							
					}
					
				}
			}
			else if(notificacionVO.getCanalEnvio().equals("3")){
				
				//csv.crearArchivoDESC(nombreDeArchivo);
				try{
					
					List<MxgesEnvioNotificacionVo> cadenasNotSms = new ArrayList<MxgesEnvioNotificacionVo>();
					List<MxgesEnvioNotificacionVo> cadenasNotEmail = new ArrayList<MxgesEnvioNotificacionVo>();
					String cuerpoIDPlantillaCalixta="";
					String procNombreArchivo="";
					List<VarProcVo> variablesProc = new ArrayList<VarProcVo>();
					variablesProc = dao.getVarProc(notificacionVO);
					/*for (VarProcVo vo : variablesProc) {
						log.debug("Proceso-----: " + vo.getCveProceso() + "  Variable----: " + vo.getCveVariable()  + "  Variable----: " + vo.getColFisica());
					}*/
					cuerpoIDPlantillaCalixta = dao.getMensajePlantilla(notificacionVO.getIdMensaje());
			        log.debug("Mxges ----  notificacionVO.getIdMensaje() ------->>>: "  + notificacionVO.getIdMensaje());
			        log.debug("cuerpoIDPlantillaMSG ---------> : " + cuerpoIDPlantillaCalixta);
			        procNombreArchivo=dao.getProcesoNomFile(notificacionVO.getIdProceso());
			        log.debug(procNombreArchivo);
			        log.debug("MEDIO ENVIO --------> :    " + notificacionVO.getMedioEnvio());
			        synchronized (this) {
						
					
			        if(notificacionVO.getMedioEnvio().equals("SM")){
			        	cadenasSMS(variablesProc);
			        	cadenasNotSms = dao.getMxgesEnvioNotificacion(qryArchivoSms, notificacionVO);
			        	if(cadenasNotSms.size()>0){
			        		dao.updItRegistros(notificacionVO.getIdProceso());
				        	createFileService.crearArchivoSmsCSV(procNombreArchivo,cabeceroSms.toString(),cadenasNotSms);
				        	log.debug("----->  Se ejecuta CREARARCHIVO_DESC  ");
				        	createFileService.crearArchivoSmsDESC(procNombreArchivo,cuerpoIDPlantillaCalixta);
				        	log.debug("----->  Se ejecuta CREARARCHIVO_CSV  ");
				        	//cabeceroSms.delete(0, cabeceroSms.length());
				        }
			        	else{
							log.debug("----->  No hay registros que procesar para el proceso:  " + notificacionVO.getIdProceso() + "     " + notificacionVO.getIdNotificacion());
			        	}
					}else{
						cadenasEMAIL(variablesProc);
						cadenasNotEmail = dao.getMxgesEnvioNotificacion(qryArchivoEmail, notificacionVO);
						if(cadenasNotEmail.size()>0){
							dao.updItRegistros(notificacionVO.getIdProceso());
				        	createFileService.crearArchivoEmailCSV(procNombreArchivo,cabeceroEmail.toString(),cadenasNotEmail);
				        	log.debug("----->  Se ejecuta CREARARCHIVO_DESC  ");
				        	createFileService.crearArchivoEmailDESC(procNombreArchivo,cuerpoIDPlantillaCalixta);
				        	log.debug("----->  Se ejecuta CREARARCHIVO_CSV  ");
				        	//cabeceroEmail.delete(0, cabeceroEmail.length());
						}
			        	else{
							log.debug("----->  No hay registros que procesar para el proceso:  " + notificacionVO.getIdProceso() + "     " + notificacionVO.getIdNotificacion());
			        	}
					}
					}
				}catch(Exception ex){
					ex.printStackTrace();
					throw new RuntimeException("Exception: No es posible generar el archivo!!!!, mensaje: "+ex.getMessage() );
				}
			}
			else{
				log.debug("----->  CANAL_ENVIO INVALIDO verificar BD tabla MXGES_NOTIFICACIONES para el proceso:  " + notificacionVO.getIdProceso());
			}
		}//////////////
		else{//////////////
			log.debug("----->  Canal de envio vacio verificar BD tabla MXGES_NOTIFICACIONES para el proceso:  " + notificacionVO.getIdProceso());	
		}//////////////

	}
	
	private synchronized void cadenasSMS(List<VarProcVo> varP){
		cabeceroSms = new StringBuffer();
		cabeceroSms.append("TELEFONO");
		qryArchivoSms=new StringBuffer();
		qryArchivoSms.append("TEL_MOVIL_CLIENTE");
		log.debug("CABECERO INICIAL -----> : " + cabeceroSms);
		log.debug("QRYARCHIVO INICIAL -----> : " + qryArchivoSms);
		for(VarProcVo v1 : varP){
			if(!(v1.getColFisica().equals("TEL_MOVIL_CLIENTE"))){
				qryArchivoSms.append(" || '|' || ").append(v1.getColFisica());
				cabeceroSms.append("|");
				cabeceroSms.append(v1.getCveVariable());
			}
			log.debug("Proceso-----: " + v1.getCveProceso() + "  Variable----: " + v1.getCveVariable()  + "  Variable----: " + v1.getColFisica());
		}
		//qryArchivo = qryArchivo + " ) CAD_ARCHIVO";
		log.debug("CABECERO FINAL -----> : " + cabeceroSms);
		log.debug("QRYARCHIVO FINAL -----> : " + qryArchivoSms);
	}
	
	private synchronized void cadenasEMAIL(List<VarProcVo> varP){
		cabeceroEmail = new StringBuffer();
		cabeceroEmail.append("EMAIL");
		qryArchivoEmail=new StringBuffer();
		qryArchivoEmail.append("EMAIL_CLIENTE");
		log.debug("CABECERO INICIAL -----> : " + cabeceroEmail);
		log.debug("QRYARCHIVO INICIAL -----> : " + qryArchivoEmail);
		for(VarProcVo v1 : varP){
			if(!(v1.getColFisica().equals("EMAIL_CLIENTE"))){
				//qryArchivo = qryArchivo + " || " + v1.getColFisica();
				qryArchivoEmail.append(" || '|' || ").append(v1.getColFisica());
				cabeceroEmail.append("|");
				cabeceroEmail.append(v1.getCveVariable());
			}
			log.debug("Proceso-----: " + v1.getCveProceso() + "  Variable----: " + v1.getCveVariable()  + "  Variable----: " + v1.getColFisica());
		}
		//qryArchivo = qryArchivo + " ) CAD_ARCHIVO";
		log.debug("CABECERO FINAL -----> : " + cabeceroEmail);
		log.debug("QRYARCHIVO FINAL -----> : " + qryArchivoEmail);
	}
	
	private boolean doRenew(NotificacionVO notificacionVO, String procesoHUB){
		
		try{
			return notifHubService.invokeHubRenew(procesoHUB);
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException("Exception: No es posible invocar el servicio HUB canal para el mensaje !!!!, mensaje: "+ex.getMessage() );
		}
		
		
		
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private void insNuevaCabecera(CabeceraVO cabecera, int idProcesoNuevo) {
	    dao.insNuevaCabecera(cabecera, idProcesoNuevo);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void insEnvioSMS(MensajeVO msj, int idEnvio, String numero, String destinatario, String status, String error) {
	    try {
	        dao.insEnvioSMS(msj, idEnvio, numero, destinatario, status, error);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void insEnvioEmail(MensajeVO msj, int idEnvio, String destino, String destinatario, String status, String error) {
	    try {
	        dao.insEnvioEmail(msj, idEnvio, destino, destinatario, status, error);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void procesaMensaje(MensajeVO mensaje, NotificacionVO conf) {
	
	    String cuerpoMensaje = mensaje.getCuerpo();
	    String asunto = mensaje.getAsunto();
	
	    Integer idProceso = new Integer(mensaje.getIdProceso());
	    Integer idRegistro = new Integer(mensaje.getIdRegistro());
	
	    mensaje.setCuerpo(transformaMensaje(cuerpoMensaje, idRegistro, idProceso, mensaje));
	    mensaje.setAsunto(transformaMensaje(asunto, idRegistro, idProceso, mensaje));
	    mensaje.setCveProceso(conf.getIdProceso());
	    if (mensaje.getMedioEnvio().equals(ConstantesMxges.SMS)) {
	
	        if (conf.isEnvioAgente()) {
	            enviaSMS(mensaje, idProceso, idRegistro, mensaje.getTelMovilApoderado(), ConstantesMxges.AGENTE);
	        }
	        if (conf.isEnvioCliente()) {
	            enviaSMS(mensaje, idProceso, idRegistro, mensaje.getTelMovilCliente(), ConstantesMxges.CLIENTE);
	        }
	
	    }
	    if (mensaje.getMedioEnvio().equals(ConstantesMxges.EMAIL)) {
	        if (conf.isEnvioAgente()) {
	            if (StringUtils.isBlank(mensaje.getEmailApoderado())) {
	                enviaEmail(mensaje, idProceso, idRegistro, mensaje.getEmailPromotoria(), ConstantesMxges.PROMOTOR);
	            } else {
	                enviaEmail(mensaje, idProceso, idRegistro, mensaje.getEmailApoderado(), ConstantesMxges.AGENTE);
	            }
	        }
	        if (conf.isEnvioCliente()) {
	            enviaEmail(mensaje, idProceso, idRegistro, mensaje.getEmailCliente(), ConstantesMxges.CLIENTE);
	        }
	
	    }
	
	}

	public void enviaEmail(MensajeVO mensaje, Integer idProceso, Integer idRegistro, String destinatario, String dest) {
	    int idEnvio = dao.getIdEnvio();
	    idEnvio++;
	    if (log.isDebugEnabled()) {
	        log.debug("----------------------------------envia Email --------------------");
	        log.debug("idEnvio -----------------------------------------------------> " + idEnvio);
	        log.debug("idProceso -----------------------------------------------------> " + idProceso.toString());
	        log.debug("idRegistro -----------------------------------------------------> " + idRegistro.toString());
	        log.debug("MedioEnvio -----------------------------------------------------> " + mensaje.getMedioEnvio());
	    }
	    if (StringUtils.isNotBlank(destinatario)) {
	
	        try {
	            enviaMailService.enviaMails(mensaje, destinatario);
	            insEnvioEmail(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ENVIADO, null);
	        } catch (Exception e) {
	            e.printStackTrace();
	            StringBuffer stb = new StringBuffer();
	            stb.append("Error en envio SMTP - ");
	            stb.append(e.getCause() + " - ");
	            for (int y = 0; y < e.getStackTrace().length; y++) {
	                stb.append(e.getStackTrace()[y].toString() + "\n");
	            }
	            stb.setLength(500);
	            insEnvioEmail(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ERROR, stb.toString());
	        }
	    } else {
	        insEnvioEmail(mensaje, idEnvio, destinatario, dest, ConstantesMxges.FALTA_DATOS_CONTACTO, null);
	    }
	    
	}

	public void enviaSMS(MensajeVO mensaje, Integer idProceso, Integer idRegistro, String destinatario, String dest) {
	    int idEnvio = dao.getIdEnvio();
	    idEnvio++;
	    if (log.isDebugEnabled()) {
	        log.debug("----------------------------------envia SMS --------------------");
	        log.debug("idEnvio -----------------------------------------------------> " + idEnvio);
	        log.debug("idProceso -----------------------------------------------------> " + idProceso.toString());
	        log.debug("idRegistro -----------------------------------------------------> " + idRegistro.toString());
	        log.debug("MedioEnvio -----------------------------------------------------> " + mensaje.getMedioEnvio());
	    }
	    if (StringUtils.isNotBlank(destinatario)) {
			try {
				ResponseWebServiceVO vo = smsService.sendMessageService(destinatario, removeAcentos(mensaje.getCuerpo()));
				if (log.isDebugEnabled()) {
					log.debug("ResponseWebServiceVO: " + vo.toString());
				}
				if (vo.getResultInfoCode().equals(ConstantesMxges.SMS_ENVIADO)) {
					insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ENVIADO, ("[" + vo.getResultInfoCode() + "]  " + vo.getResultInfoDesc()));
				} else {
					insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ERROR, ("[" + vo.getResultInfoCode() + "]  " + decodeAuronixError(vo.getResultInfoCode())));
				}
	
			} catch (IllegalArgumentException e) {
				// TO FIX Cambio de mensaje temporal, se arreglara en el proximo
				// requerimiento.
				if ("Los parametros no cumplen con las validaciones basicas...".equals(e.getMessage())) {
					insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.FALTA_DATOS_CONTACTO, "CEL: [" + destinatario + "] DESC_ERROR: " + e.getMessage());
				} else {
					insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ENVIADO, "");
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringBuffer stb = new StringBuffer();
				stb.append("Error gen�rico en respuesta a invocaci�n del WS - ");
				stb.append(e.getCause() + " - ");
				for (int y = 0; y < e.getStackTrace().length; y++) {
					stb.append(e.getStackTrace()[y].toString() + "\n");
				}
	
	            stb.setLength(500);
	
	            insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.ERROR, stb.toString());
	        }
	    } else {
	        insEnvioSMS(mensaje, idEnvio, destinatario, dest, ConstantesMxges.FALTA_DATOS_CONTACTO, destinatario);
	    }
	    //dao.updRegistro(idProceso, idRegistro, ConstantesMxges.ENVIADO);
	}


	public String transformaMensaje(String cadena, int idRegistro, int idProceso, MensajeVO mensaje) {
	
		final String VAR_TRANSFORMA_1= "Var06";
		

	    String[] variables = StringUtils.substringsBetween(cadena, "${", "}");
	    if (variables != null) {
	        for (int i = 0; i < variables.length; i++) {
	  
	            String columna = dao.getColumnaFisica(variables[i]);	        
	            String valor = dao.getValorVariable(idRegistro, idProceso, columna);
	            
	          	if(columna.equals(VAR_TRANSFORMA_1)){  // Var06
	          		 valor= notifHubService.getDateEs_MX_Formated(valor);
	          	}
	            
	            if (log.isDebugEnabled()) {
	                log.debug("Variable -> " + variables[i]);
	                log.debug("Valor -> " + valor);
	                log.debug("cuerpoMensaje -> " + cadena);
	
	            }
	            if (valor != null) {
	                cadena = cadena.replace("${" + variables[i] + "}", valor);
	            } else {
	                cadena = cadena.replace("${" + variables[i] + "}", "");
	            }
	            if (log.isDebugEnabled()) {
	                log.debug("Mensaje reemplazado " + cadena.toString());
	            }
	
	        }
	    }
	
	    return cadena;
	
	}

	public void updEnvio(String enviado, String idEnvio, String error) {
	    dao.updEnvio(enviado, idEnvio, error);
	}

	private String removeAcentos(String input) {
	    String original = "��������������u�������������������";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    String output = input;
	    
		if (dao.removerAcentos()) {
			for (int i = 0; i < original.length(); i++) {
				output = output.replace(original.charAt(i), ascii.charAt(i));
			}
		}
		
	    return output;
	}

	private String decodeAuronixError(String codeError) {
		String msg = "";
		int code = 0;
	
		if (StringUtils.isNotBlank(codeError)) {
	
			try {
				code = Integer.parseInt(codeError);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
	
			switch (code) {
			case 3:
				msg = "Mensaje Enviado";
				break;
			case 6:
				msg = "Numero no movil";
				break;
			case 10:
				msg = "Numero invalido";
				break;
			case 101:
				msg = "Falta salto / Saldo Congelado";
				break;
			case -101:
				msg = "Credenciales invalidas";
				break;
	
			default:
				msg = "desconocido";
				break;
			}
		}
	
		return msg;
	}

	private String getBodyCargoAceptado(EnvioNotificacionVO envioNotificacionVO){
		StringBuffer body = new StringBuffer();
		body.append("<html>");
		body.append("<head>");
		body.append("	<title></title>");
		body.append("</head>");
		body.append("<body>");
		body.append("<table width=\"100%\" style=\"background:#0071B9\" cellpadding=\"0\" cellspacing=\"0\">");
		body.append("<tr>");
		body.append("<td style=\"background:#0071B9\"><img src=\"metlife.png\" height=\"120px\"/></td>");
		body.append("</tr>");
		body.append("</table>");
		body.append("<hr size=\"4\" color=\"#86BB6D\" style=\"color: #86BB6D;\"/>");
		body.append("<p>");
		body.append("<strong>&nbsp;Asunto: Cargo bancario exitoso de tu Seguro de Vida</strong></p>");
		body.append("	<hr size=\"4\" color=\"#86BB6D\" style=\"color: #86BB6D;\"/>");
		body.append("<p>");
		body.append("		<strong>");
		body.append(envioNotificacionVO.getNombre().toUpperCase() + " " + envioNotificacionVO.getPaterno().toUpperCase() + " " + envioNotificacionVO.getMaterno().toUpperCase());
		body.append("</strong></p>");
		body.append("	<p>");
		body.append("		<strong>POLIZA: ");
		body.append(envioNotificacionVO.getPoliza());
		body.append("</strong></p>");
		body.append("	<p>");
		body.append("		&nbsp;</p>");
		body.append("	<p>");
		body.append("		ESTIMAD@: ");
		body.append(envioNotificacionVO.getNombre().toUpperCase());
		body.append("</p>");
		body.append("	<p>");
		body.append("		&nbsp;</p>");
		body.append("	<p>");
		body.append("<em><strong>Para MetLife tu protecci&oacute;n y la de tus seres queridos es muy importante,</strong> por esta raz&oacute;n peri&oacute;dicamente te estaremos notificando sobre el estatus de tus pagos del Seguro de vida Met99.</em></p>");
		body.append("	<p>");
		body.append("		<em>En esta ocasi&oacute;n, te informamos que fue exitoso el cargo a tu cuenta bancaria con terminaci&oacute;n ");
		body.append(envioNotificacionVO.getNomina()); 
		body.append(" por un importe de $");
		body.append(envioNotificacionVO.getImporte()); 
		body.append(" MN. Correspondiente al pago de la prima de tu seguro, el d&iacute;a ");
		body.append(envioNotificacionVO.getFecCobro());
		body.append(".</em></p>");
		body.append("<p>");
		body.append("		<em>Agradecemos tu pago&nbsp;y recuerda que mantener al corriente tu seguro, te dar&aacute; la tranquilidad de contar con todos los beneficios para que t&uacute; y tu familia est&eacute;n protegidos.</em></p>");
		body.append("<p>");
		body.append("		<em>Contacta a tu agente de seguros o a nuestro Centro de atenci&oacute;n telef&oacute;nica, en el D.F y &aacute;rea metropolitana: 53 28 73 66 o en el interior de la rep&uacute;blica, lada sin costo al 01 800 83 93 631.</em></p>");
		body.append("		<p>");
		body.append("		&nbsp;</p>");
		body.append("<p>");
		body.append("		<em>Atentamente</em></p>");
		body.append("<p>				&nbsp;</p>");
		body.append("	<p>");
		body.append("<font size=\"5\"><strong><em>MetLife M&eacute;xico, S.A.</em></strong></font></p>");
		body.append("	<p>");
		body.append("		<font size=\"1\">Este es un correo generado de manera autom&aacute;tica, por lo que le pedimos no responderlo.</BR>");
		body.append("Los productos y servicios son ofrecidos por MetLife M&eacute;xico, S.A. filial de MetLife Inc. Que operan bajo la marca &quot;MetLife&quot;.</BR>");
		body.append("		Todo lo anterior de acuerdo a las condiciones generales del seguro contratado.</font></p>");
		body.append("	</p>");
		body.append("</body>");
		body.append("</html>");
		return body.toString();
	}
	
	private String getBodyCargoRechazado(EnvioNotificacionVO envioNotificacionVO){
		StringBuffer body = new StringBuffer();
		body.append("<html>");
		body.append("<head>");
		body.append("	<title></title>");
		body.append("</head>");
		body.append("<body>");
		body.append("<table width=\"100%\" style=\"background:#0071B9\" cellpadding=\"0\" cellspacing=\"0\">");
		body.append("<tr>");
		body.append("<td style=\"background:#0071B9\"><img src=\"metlife.png\" height=\"120px\"/></td>");
		body.append("</tr>");
		body.append("</table>");
		body.append("<hr size=\"4\" color=\"#86BB6D\" style=\"color: #86BB6D;\"/>");
		body.append("<p>");
		body.append("<strong>&nbsp;Asunto: Cargo bancario no exitoso de tu Seguro de Vida</strong></p>");
		body.append("	<hr size=\"4\" color=\"#86BB6D\" style=\"color: #86BB6D;\"/>");
		body.append("<p>");
		body.append("		<strong>");
		body.append(envioNotificacionVO.getNombre().toUpperCase() + " " + envioNotificacionVO.getPaterno().toUpperCase() + " " + envioNotificacionVO.getMaterno().toUpperCase());
		body.append("</strong></p>");
		body.append("	<p>");
		body.append("		<strong>POLIZA: ");
		body.append(envioNotificacionVO.getPoliza());
		body.append("</strong></p>");
		body.append("	<p>");
		body.append("		&nbsp;</p>");
		body.append("	<p>");
		body.append("		ESTIMAD@: ");
		body.append(envioNotificacionVO.getNombre().toUpperCase());
		body.append("</p>");
		body.append("	<p>");
		body.append("		&nbsp;</p>");
		body.append("	<p>");
		body.append("<em><strong>Para MetLife tu protecci&oacute;n y la de tus seres queridos es muy importante,</strong> por esta raz&oacute;n peri&oacute;dicamente te estaremos notificando sobre el estatus de tus pagos del Seguro de vida Met99.</em></p>");
		body.append("	<p>");
		body.append("		<em>En esta ocasi&oacute;n, te informamos que el cargo a tu cuenta bancaria con terminaci&oacute;n ");
		body.append(envioNotificacionVO.getNomina()); 
		body.append(" por un importe de $");
		body.append(envioNotificacionVO.getImporte()); 
		body.append(" MN, correspondiente al pago de la prima de tu seguro, el d&iacute;a ");
		body.append(envioNotificacionVO.getFecCobro());
		body.append(" NO fue exitoso, por lo que es importante que nos contactes lo antes posible para actualizar o verificar tus datos.</em></p>");
		body.append("<p>");
		body.append(" <em>Recuerda que mantener al corriente el pago de tu seguro, te dar&aacute; la tranquilidad de contar con todos los beneficios para que t&uacute; y tu familia est&eacute;n protegidos.</em></p>");
		body.append("<p>");
		body.append("		<em>Contacta a tu agente de seguros o a nuestro Centro de atenci&oacute;n telef&oacute;nica, en el D.F y &aacute;rea metropolitana: 53 28 73 66 o en el interior de la rep&uacute;blica, lada sin costo al 01 800 83 93 631.</em></p>");
		body.append("		<p>");
		body.append("		&nbsp;</p>");
		body.append("<p>");
		body.append("		<em>Atentamente</em></p>");
		body.append("<p>				&nbsp;</p>");
		body.append("	<p>");
		body.append("<font size=\"5\"><strong><em>MetLife M&eacute;xico, S.A.</em></strong></font></p>");
		body.append("	<p>");
		body.append("		<font size=\"1\">Este es un correo generado de manera autom&aacute;tica, por lo que le pedimos no responderlo.</BR>");
		body.append("Los productos y servicios son ofrecidos por MetLife M&eacute;xico, S.A. filial de MetLife Inc. Que operan bajo la marca &quot;MetLife&quot;.</BR>");
		body.append("		Todo lo anterior de acuerdo a las condiciones generales del seguro contratado.</font></p>");
		body.append("	</p>");
		body.append("</body>");
		body.append("</html>");
		return body.toString();
	}
}
