package com.metlife.nm.utils.sms.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.xmlbeans.XmlException;
import org.springframework.stereotype.Service;
import org.xmlsoap.schemas.soap.envelope.EnvelopeDocument;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.error.ServiceInvocationException;
import com.metlife.nm.utils.sms.vo.DefaultParametersVO;
import com.metlife.nm.utils.sms.vo.DynamicParametersVO;
import com.metlife.nm.utils.sms.vo.ResponseWebServiceVO;

@Service(value = "InvokeWebServiceMessage")
public class InvokeWebServiceMessage {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7850074845144296078L;
	private static final Logger log = Logger.getLogger(InvokeWebServiceMessage.class);
	private static final String PATH = "/soap/default";

	@Resource(name = BeanNames.MxgesProperties)
	private MxgesProperties mxgesProperties;

	/**
	 * Metodo que realiza el envio de mensajes de texto a un telefono celular
	 * 
	 * @param phone
	 *            Numero telefonico a 10 digitos
	 * @param messageText
	 *            Detalle del mensaje no mayor a 160 caracteres.
	 * @return ResponseWebServiceVO Codigo de Respuesta y Descripcion del
	 *         codigo.
	 * @throws Exception
	 */
	public ResponseWebServiceVO sendMessageService(String phone, String messageText) throws Exception {
		String xml = null;
		String response = null;
		EnvelopeDocument respXml = null;
		ResponseWebServiceVO vo = null;

		if (validateInputParams(phone, messageText)) {

			try {
				// Se crea el XML Request
				xml = createRequest(phone, messageText);
				// Se consume el Web Service
				response = invokeWebService(xml);
				// Se parsea la respuesta
				respXml = EnvelopeDocument.Factory.parse(response);

				// Se asignan valores de respuesta al objeto
				vo = new ResponseWebServiceVO();
				vo.setMessageCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultCode().getStringValue());
				vo.setCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultCode().getTc().toString());
				vo.setResultInfoCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultInfo().getResultInfoCode());
				vo.setResultInfoDesc(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultInfo().getResultInfoDesc());
				vo.setResultInfoSysMessageCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultInfo().getResultInfoSysMessageCode());
				vo.setResultInfoSeverity(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultInfo().getResultInfoSeverity().toString());
				// vo.setDeliveryStatus(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().get);
				vo.setResultInfoErrorType(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getOLifEExtension().getResultInfoErrorType());

				if (log.isDebugEnabled()) {
					log.debug("MSG: " + vo.getMessageCode());
					log.debug("CODE: " + vo.getCode());
					log.debug("ResultInfoCode: " + vo.getResultInfoCode());
					log.debug("ResultInfoDesc: " + vo.getResultInfoDesc());
					log.debug("ResultInfoSysMessageCode: " + vo.getResultInfoSysMessageCode());
					log.debug("ResultInfoSeverity: " + vo.getResultInfoSeverity());
					log.debug("DeliveryStatus: " + vo.getDeliveryStatus());
					log.debug("ResultInfoErrorType: " + vo.getResultInfoErrorType());
				}

			} catch (ServiceInvocationException ex) {
				log.error("Ocurrio un error en la invocacion al servicio debido a: " + ex.getMessage());
				throw new Exception(ex.getMessage());
			} catch (XmlException ex) {
				log.error("Ocurrio un error al parsear el documento debido a: " + ex.getMessage());
				throw new Exception(ex.getMessage());
			} catch (Exception e) {
				log.error("Ocurrio un error debido a: " + e.getMessage());
				throw e;
			}
		} else {
			throw new IllegalArgumentException("Los parametros no cumplen con las validaciones basicas...");
		}

		return vo;
	}

	/**
	 * Realiza la conexion con el servico web y lo consume.
	 * 
	 * @param xml
	 *            Xml request
	 * @return Respuesta Xml del servicio de tipo String
	 * @throws ServiceInvocationException
	 */
	private String invokeWebService(String xml) throws ServiceInvocationException {

		InetAddress addr;
		Socket sock = null;
		StringBuilder sb = new StringBuilder();
		BufferedWriter wr = null;
		BufferedReader rd = null;
		String line = null;
		String ip = null;
		String port = null;
		int col = 0;

		try {

			if (StringUtils.isNotEmpty(xml)) {

				// Obtiene los valores por defecto del properties

				ip = mxgesProperties.getIpWs();
				port = mxgesProperties.getPortWs();

				// Obtiene la direccion y crea el socket
				addr = InetAddress.getByName(ip);
				sock = new Socket(addr, Integer.parseInt(port));

				wr = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream(), "UTF-8"));
				wr.write("POST " + PATH + " HTTP/1.0\r\n");
				wr.write("Host: " + ip + ":" + port + "\r\n");
				wr.write("Content-Length: " + xml.length() + "\r\n");
				wr.write("Content-Type: text/xml; charset=\"utf-8\"\r\n");
				wr.write("\r\n");
				wr.write(xml);
				wr.flush();

				rd = new BufferedReader(new InputStreamReader(sock.getInputStream()));

				log.debug("La respuesta para el servicio es:");

				while ((line = rd.readLine()) != null) {
					log.info(line);
					if (col > 6) {
						sb.append(line);
					}
					col++;
				}

				log.info("Fin de la Respuesta...");
			} else {
				throw new IllegalAccessException("Se esperaba un XML valido...");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ServiceInvocationException(ex.getMessage());
		} finally {
			clearResource(sock);
		}

		return sb.toString();
	}

	/**
	 * Genera el xml request para el servicio de mensajeria SMS.
	 * 
	 * @param phone
	 *            Telefono Celular
	 * @param messageText
	 *            Mensaje de Texto
	 * @return XmlRequest con los valores default y dinamicos.
	 * @throws Exception
	 *             Exception - Si no se puede iniciar la velocity.
	 *             IllegalArgumentException - Si los parametros no cumplen
	 *             validaciones basicas.
	 */
	@SuppressWarnings("deprecation")
	private String createRequest(String phone, String messageText) throws Exception {

		Date date = new Date();
		VelocityEngine ve = new VelocityEngine();
		ve.init();

		DefaultParametersVO defParam = new DefaultParametersVO();
		DynamicParametersVO dynParam = new DynamicParametersVO();
		InputStream is = this.getClass().getResourceAsStream("SendSMS.vm");
		VelocityContext context = new VelocityContext();
		VelocityContext velocityContext = new VelocityContext(context);
		Writer out = new StringWriter();

		String transMessageId = null;

		// Se setean valores por Default al request

		defParam.setTransType(mxgesProperties.getTransType());
		defParam.setFechaExeDate(ConstantesMxges.FORMAT_EXE_DATE.format(date));
		defParam.setFechaExeTime(ConstantesMxges.FORMAT_EXE_TIME.format(date));
		defParam.setMessageType(mxgesProperties.getEnviaMensajesOl());
		defParam.setIdivrValue(mxgesProperties.getIdivrValue());
		defParam.setSourceCountry(mxgesProperties.getSourceCountry());
		defParam.setSourceCompany(mxgesProperties.getSourceCompany());
		defParam.setSourceSystem(mxgesProperties.getSourceSystem());
		defParam.setIdLob(mxgesProperties.getIdLob());

		transMessageId = String.valueOf(randomNumber());

		dynParam.setTransactionId(transMessageId);
		dynParam.setMessageId(transMessageId);
		dynParam.setPhone(phone);
		dynParam.setMessageText(messageText);

		context.put("defParam", defParam);
		context.put("dynParam", dynParam);

		try {
			Velocity.evaluate(velocityContext, out, "", is);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception en metodo evaluate [" + e.getMessage() + "]", e.getCause());

		}
		log.debug("Se Enviara el siguiente XML");
		log.debug(out.toString());

		return out.toString();
	}

	/**
	 * Valida que los parametros no sean nulos/vacios, que el numero de celular
	 * sea de tipo entero asi como su longitud igual a 10 digitos.
	 * 
	 * @param phone
	 *            Telefono celular
	 * @param messageText
	 *            Mensaje de texto.
	 * @return TRUE: Si cumplen con las validaciones basicas, de lo contrario
	 *         FALSE.
	 */
	@SuppressWarnings("unused")
	private boolean validateInputParams(String phone, String messageText) {
		boolean success = false;

		long phoneNumber = 0;

		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(messageText)) {
			log.debug("El telefono o el Mensaje de Texto es nulo o vacio...");
		} else {
			try {
				if (phone.length() == 10 && messageText.length() <= 160) {
					phoneNumber = Long.parseLong(phone);
					success = true;
				}
			} catch (NumberFormatException ex) {
				log.debug("Numero de telefono invalido: " + phone + " longitud: " + phone.length());
				ex.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * Metodo para liberar un socket previamente utilizado.
	 * 
	 * @param socket
	 * 
	 */
	private void clearResource(Socket socket) {

		try {

			if (null != socket && !socket.isClosed()) {
				socket.close();
			} else {
				log.info("El socket recibido es nulo o ya se encuentra liberado...");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Obtiene un numero aleatorio que se encuentra desde un intervalo menor
	 * hasta un numero maximo.
	 * 
	 * @return Numero aleatorio.
	 */

	private int randomNumber() {
		
		return Math.abs(new Random().nextInt());
	}

}
