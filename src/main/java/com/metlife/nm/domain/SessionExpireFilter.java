package com.metlife.nm.domain;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;



public class SessionExpireFilter extends DomainFilter {

	private static Logger log = Logger.getLogger(SessionExpireFilter.class);
	private static String redirect = "/index.jsp";

	/**
	 * false para no permitir la sesion dummy, y envia al index.jsp
	 */
	private boolean PERMITIR_SESSION_DUMMY = true;

	public boolean isThisSessionValid(HttpServletRequest request) {
		HttpSession httpsession = request.getSession(true);
		UserVO user = null;
		if (httpsession != null) {
			user = (UserVO) httpsession.getAttribute(ConstantesMxges.USER);
		}
		if (user == null) {
			return false;
		} else
			return true;
		
	}

	public boolean isFilterException(String send) {

		if (send.indexOf("start.action") > -1) {
			return true;
		} else if (send.indexOf("login") > -1) {
			return true;			
		} else if (send.indexOf("logout") > -1) {
			return true;
		} else if (send.indexOf("start-dummy.action") > -1) {

			return PERMITIR_SESSION_DUMMY;
		} else if (send.indexOf("seccion") > -1) {
			System.out.println("secccion con action de prueba");
			return true;
		}

		return false;
	}

	public void redirect(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Session expired!. Redirecting...");
		}
		String send = httpservletrequest.getRequestURL().toString();
		if (send.indexOf("async") > -1 || send.indexOf("Async") > -1) {
			if (log.isDebugEnabled()) {
				log.debug("Async result (JSON) Redirect");
			}
			sendJsonError(httpservletresponse);
		} else {
			httpservletresponse.sendRedirect(httpservletrequest.getContextPath() + getRedirect());
		}
	}

	public void sendJsonError(HttpServletResponse oResponse) throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", false);
		jsonObject.put("error", "La sesion ha expirado.");
		jsonObject.put("redirect", redirect);
		InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());

		OutputStream oOutput = null;
		int bufferSize = 1024;

		try {

			// Set the content type
			oResponse.setContentType("text/html;charset=ISO-8859-1");
			// Set the content length
			oResponse.setContentLength(jsonObject.toString().getBytes().length);

			// Set the content-disposition
			oResponse.addHeader("Content-disposition", "inline");

			// Get the outputstream
			oOutput = oResponse.getOutputStream();

			// Copy input to output
			if (log.isDebugEnabled()) {
				log.debug("Streaming to output buffer +++ START +++");
			}
			byte[] oBuff = new byte[bufferSize];
			int iSize;
			while (-1 != (iSize = inputStream.read(oBuff))) {
				oOutput.write(oBuff, 0, iSize);
			}
			if (log.isDebugEnabled()) {
				log.debug("Streaming to output buffer +++ END +++");
			}

			// Flush
			oOutput.flush();
		} finally {
			if (inputStream != null)
				inputStream.close();
			if (oOutput != null)
				oOutput.close();
		}
	}

	public String getRedirect() {
		return redirect;
	}

	@Override
	protected String getMessageBeforeRedirect() {
		return "Session Expired. Redirect...";
	}

}
