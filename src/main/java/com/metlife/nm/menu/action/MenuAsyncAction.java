/**
 * 
 */
package com.metlife.nm.menu.action;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;

/**
 * @author Cesar
 * 
 */
public class MenuAsyncAction extends GenericAsyncAction implements SessionAware{

    private static final long serialVersionUID = -4484840910941300018L;

    protected Map<String, Object> session;

	@Override
    public String execute() throws Exception {
        UserVO user =(UserVO)session.get(ConstantesMxges.USER);
        String jsonMenu = user.getJsonMenu();
        inputStream = new ByteArrayInputStream(jsonMenu.toString().getBytes("UTF-8"));
        return SUCCESS;
    }

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public final Map<String, Object> getSession() {
		return session;
	}
}
