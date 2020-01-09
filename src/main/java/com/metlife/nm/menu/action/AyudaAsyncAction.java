/**
 * 
 */
package com.metlife.nm.menu.action;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.GenericAsyncAction;

/**
 * @author
 * 
 */
public class AyudaAsyncAction extends GenericAsyncAction implements SessionAware {

    /**
     * 
     */
    private static final long serialVersionUID = -5736940634386191878L;

    private static final Logger log = Logger.getLogger(AyudaAsyncAction.class);

    protected Map<String, Object> session;

    private String pladActionId;

    @Override
    public String execute() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("execute...");
        }

        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {

            String page = null;
            if (StringUtils.isBlank(pladActionId)) {
                messages.put("flag", false);
            } else {
                messages.put("flag", true);
                page = pladActionId + ".jsp";
            }

            messages.put("page", page);

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            messages.put(JSON_EXCEPTION, e.toString());
        } finally {
            messages.put(JSON_DIVS, divs);
            messages.put(JSON_ALERTS, alerts);
            messages.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(messages.toString(1));
            }
            streamIt(messages);
        }

        return SUCCESS;

    }

    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public final Map<String, Object> getSession() {
        return session;
    }

    public final String getPladActionId() {
        return pladActionId;
    }

    public final void setPladActionId(String pladActionId) {
        this.pladActionId = pladActionId;
    }
}
