package com.metlife.nm.login.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@Results( { @Result(name = "success", location = "byebye.jsp") })
public class LogoutAction extends ActionSupport implements SessionAware, ServletRequestAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2793675175711260339L;
    private static final Logger log = Logger.getLogger(LogoutAction.class);

    protected Map<String, Object> session;

    @SuppressWarnings("unchecked")
    private Map request;

    private HttpSession httpSession;

    public String execute() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("execute...");
        }

        verificarMetricasMatriz();

        session.put(ConstantesMxges.USER, null);

        if (log.isDebugEnabled()) {
            log.debug("sending bye bye...");
        }

        return SUCCESS;
    }

    private void verificarMetricasMatriz() {
        if (httpSession == null) {
            return;
        }
        /*if (httpSession.getAttribute(ConstantesPlad.METRICA_MATRIZ_RIESGO) == null) {
            return;
        }*/
        if (httpSession.getAttribute(ConstantesMxges.USER) == null) {
            return;
        }

        /*MetricaVO metrica = (MetricaVO) httpSession.getAttribute(ConstantesPlad.METRICA_MATRIZ_RIESGO);*/

        UserVO user = (UserVO) httpSession.getAttribute(ConstantesMxges.USER);

        ApplicationContext factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpSession.getServletContext());

        if (factory == null) {
            return;
        }

        /*MatrizRiesgoService service = (MatrizRiesgoService) factory.getBean(BeanNames.MatrizRiesgoService);*/

        /*if (service == null) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Actualizando tiempo del analista por expiracion de session");
            log.debug("guardar metrica: " + metrica);
        }

        service.persistirMetricas(metrica, user);
        httpSession.removeAttribute(ConstantesPlad.METRICA_MATRIZ_RIESGO);*/

    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    public final Map getRequest() {
        return request;
    }

    public final Map<String, Object> getSession() {
        return session;
    }

    public void setServletRequest(HttpServletRequest request) {
        if (request != null) {
            // Por si las moscar checamos si no es null, WAS no tiene palabra
            httpSession = request.getSession();
        }
    }

}
