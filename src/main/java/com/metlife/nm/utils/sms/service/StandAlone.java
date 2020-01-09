package com.metlife.nm.utils.sms.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.xmlsoap.schemas.soap.envelope.EnvelopeDocument;

import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.utils.sms.vo.DefaultParametersVO;
import com.metlife.nm.utils.sms.vo.DynamicParametersVO;
import com.metlife.nm.utils.sms.vo.ResponseWebServiceVO;

public class StandAlone {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 7850074845144296078L;
	private static final Logger log = Logger.getLogger(InvokeWebServiceMessage.class);
	private Properties props = null;
	private static final String PATH = "/soap/default";
	
	
	public static void main(String[] args) throws Exception {
		
		InvokeWebServiceMessage exe = new InvokeWebServiceMessage();
		exe.sendMessageService("7341139101", "Tirando Codigo...");
		
	}

	public ResponseWebServiceVO sendMessageService(String phone, String messageText) throws Exception {
		String xml = null;
		String response = null;
		EnvelopeDocument respXml = null;
		ResponseWebServiceVO vo = null;

		if (validateInputParams(phone, messageText)) {
			xml = createRequest(phone, messageText);
			response = invokeWebService(xml);
			
			respXml =  EnvelopeDocument.Factory.parse(response);
			vo = new ResponseWebServiceVO();
			vo.setMessageCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultCode().getStringValue());
			vo.setCode(respXml.getEnvelope().getBody().getMsgSendResponse().getTXLife().getTXLifeResponse().getTransResult().getResultCode().getTc().toString());
			log.info("MSG: " + vo.getMessageCode());
			log.info("CODE: " + vo.getCode());
			
		} else {
			throw new IllegalAccessException("Los parametros no cumplen con las validaciones basicas...");
		}

		return vo;
	}

	private String invokeWebService(String xml) throws FileNotFoundException, IOException {

		InetAddress addr;
		Socket sock = null;
		StringBuilder sb = new StringBuilder();
		BufferedWriter wr = null;
		BufferedReader rd = null;
		String line = null;
		String ip = null;
		String port = null;
		int col = 0;		
		props = new Properties();	
		props.load(new FileInputStream("d://wsIndigo/MXGES/src/main/resources/mxgesad.properties"));
		
		try {

			if (StringUtils.isNotEmpty(xml)) {

				// Obtiene los valores por defecto del properties

				ip = props.getProperty("ip.ws");
				port = props.getProperty("port.ws");

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

				log.info("La respuesta para el servicio es:");

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

		// Se setean valores por Default al request

		props = new Properties();	
		props.load(new FileInputStream("d://wsIndigo/MXGES/src/main/resources/mxgesad.properties"));
						
		defParam.setTransType(props.getProperty("trans.type"));
		defParam.setFechaExeDate(ConstantesMxges.FORMAT_EXE_DATE.format(date));
		defParam.setFechaExeTime(ConstantesMxges.FORMAT_EXE_TIME.format(date));
		defParam.setMessageType(props.getProperty("envia.mensajes.ol"));
		defParam.setIdivrValue(props.getProperty("idivr.value"));
		defParam.setSourceCountry(props.getProperty("source.country"));
		defParam.setSourceCompany(props.getProperty("source.company"));
		defParam.setSourceSystem(props.getProperty("source.system"));
		defParam.setIdLob(props.getProperty("id.lob"));

		dynParam.setTransactionId("0000003");
		dynParam.setMessageId("0000003");
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
		log.info("Se Enviara el siguiente XML");
		log.info(out.toString());

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
	private boolean validateInputParams(String phone, String messageText) {
		boolean success = false;
		@SuppressWarnings("unused")
		long phoneNumber = 0;

		if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(messageText)) {
			log.info("El telefono o el Mensaje de Texto es nulo o vacio...");
		} else {
			try {
				if (phone.length() == 10 && messageText.length() <= 160) {
					phoneNumber = Long.parseLong(phone);
					success = true;
				}
			} catch (NumberFormatException ex) {
				log.info("Numero de telefono invalido: " + phone + " longitud: " +phone.length());
				ex.printStackTrace();
			}
		}

		return success;
	}
	
	

}
