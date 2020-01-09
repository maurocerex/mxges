package com.metlife.nm.utils.notificacion.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.utils.mail.service.EnviaMailService;
import com.metlife.nm.utils.notificacion.dao.EnviaNotificacionesDao;
import com.metlife.nm.utils.notificacion.vo.CabeceraVO;
import com.metlife.nm.utils.notificacion.vo.MensajeVO;
import com.metlife.nm.utils.notificacion.vo.NotificacionVO;
import com.metlife.nm.utils.notificacion.vo.RegistroProcVO;
import com.metlife.nm.utils.sms.service.InvokeWebServiceMessage;
import com.metlife.nm.utils.sms.vo.ResponseWebServiceVO;


@Service(value = BeanNames.EnviaNotificacionService)
public class EnviaNotificacionServiceImpl {

    private static final Logger log = Logger.getLogger(EnviaNotificacionServiceImpl.class);

    protected static SimpleDateFormat SDF_DDMMYYYY_HHMMSS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    @Resource(name = BeanNames.EnviaNotificacionDao)
    private EnviaNotificacionesDao dao;

    @Resource(name = "InvokeWebServiceMessage")
    private InvokeWebServiceMessage smsService;

    @Resource(name = BeanNames.EnviaMailService)
    private EnviaMailService enviaMailService;

    
	public void enviaNotificaciones() {

		if (log.isDebugEnabled()) {
			log.debug("------------------------------------------INICIA PROCESO DE ENVIO DE NOTIFICACIONES--------------------------------------");
		}
		
try{
	
	if (!dao.isConcurrrent()) {

		// Encendemos bandera de concurrencia.
		dao.updateFlagConcurrence(ConstantesMxges.TURN_ON);
		
			Calendar currentDate = Calendar.getInstance();
			if (log.isDebugEnabled()) {
				log.debug("LA FECHA " + currentDate.toString());
				log.debug("El dia de hoy -> " + currentDate.get(Calendar.DAY_OF_WEEK));
				log.debug("la Hora es -> " + currentDate.get(Calendar.HOUR_OF_DAY));
				log.debug("El minuto es -> " + currentDate.get(Calendar.MINUTE));
			}
			String diaDeHoy = getClaveDia(currentDate.get(Calendar.DAY_OF_WEEK));
			int hora = currentDate.get(Calendar.HOUR_OF_DAY);
			int minuto = currentDate.get(Calendar.MINUTE);

			if (log.isDebugEnabled()) {
				log.debug("Dia transformado " + diaDeHoy);
			}

			ArrayList<NotificacionVO> confgs = (ArrayList<NotificacionVO>) dao.getConfiguraciones(diaDeHoy, hora, minuto);
			int retroactividad = dao.getRetroactividad();
			if (log.isDebugEnabled()) {
				log.debug("retroactividad -> " + retroactividad);
			}
			int idProceso = 0;
			int idRegistro = 0;
			TreeSet<RegistroProcVO> procesados = new TreeSet<RegistroProcVO>();
			for (NotificacionVO notif : confgs) {
				ArrayList<CabeceraVO> cabeceras = (ArrayList<CabeceraVO>) dao.getCabeceras(notif.getIdLob(), retroactividad, notif.getIdProceso());
				
				for (CabeceraVO proceso : cabeceras) {
					dao.updCabecera(proceso.getIdProcesoPadre(), ConstantesMxges.INICIADO);
					int idProcesoNuevo = dao.getIdProcesoEnvioNuevo();
					insNuevaCabecera(proceso, idProcesoNuevo);

					ArrayList<MensajeVO> mensajes = (ArrayList<MensajeVO>) dao.getMensajes(proceso.getIdProcesoPadre(), notif, "PROC_COBRANZA");
					if (log.isDebugEnabled()) {
						log.debug("-----------------------------------------------------------------------MENSAJES---------------- " + mensajes.size());
					}
					for (MensajeVO det : mensajes) {
						procesaMensaje(det, notif);
						if (idProceso != det.getIdProceso() || idRegistro != det.getIdRegistro()) {
							idProceso = det.getIdProceso();
							idRegistro = det.getIdRegistro();
							// dao.updRegistro(idProceso, idRegistro,
							// ConstantesMxges.ENVIADO);
						}
						RegistroProcVO obj = new RegistroProcVO();
						obj.setIdProceso(idProceso);
						obj.setIdRegistro(idRegistro);
						procesados.add(obj);
					}
					dao.updCabecera(proceso.getIdProcesoPadre(), ConstantesMxges.FINALIZADO);
				}
			}
			for (RegistroProcVO tmp : procesados) {
				dao.updRegistro(tmp.getIdProceso(), tmp.getIdRegistro(), ConstantesMxges.ENVIADO);
			}
			// Apagamos bandera de concurrencia.
			dao.updateFlagConcurrence(ConstantesMxges.TURN_OFF);
	}else {
		log.debug("Se omite envio de notificaciones por concurrencia...");
	}
	}catch(Exception e){
		if (log.isDebugEnabled()) {
			log.debug("Error en el env�o de notificaciones: ".concat(e.getMessage()));
		}
	}
		if (log.isDebugEnabled()) {
			log.debug("------------------------------------------FIN PROCESO DE ENVIO DE NOTIFICACIONES--------------------------------------");
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

    private String getClaveDia(int diaSemana) {
        if (log.isDebugEnabled()) {
            log.debug("Dia de la Semana -> " + diaSemana);
        }
        if (diaSemana == ConstantesMxges.SUN) {
            return ConstantesMxges.SUNDAY;
        } else if (diaSemana == ConstantesMxges.MON) {
            return ConstantesMxges.MONDAY;
        } else if (diaSemana == ConstantesMxges.TUE) {
            return ConstantesMxges.TUESDAY;
        } else if (diaSemana == ConstantesMxges.WED) {
            return ConstantesMxges.WEDNESDAY;
        } else if (diaSemana == ConstantesMxges.THU) {
            return ConstantesMxges.THURSDAY;
        } else if (diaSemana == ConstantesMxges.FRI) {
            return ConstantesMxges.FRIDAY;
        } else if (diaSemana == ConstantesMxges.SAT) {
            return ConstantesMxges.SATURDAY;
        }
        return null;
    }

    public String transformaMensaje(String cadena, int idRegistro, int idProceso, MensajeVO mensaje) {

        String[] variables = StringUtils.substringsBetween(cadena, "${", "}");
        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                String columna = dao.getColumnaFisica(variables[i]);
                String valor = dao.getValorVariable(idRegistro, idProceso, columna);
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

}
