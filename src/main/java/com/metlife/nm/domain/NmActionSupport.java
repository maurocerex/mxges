package com.metlife.nm.domain;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionSupport;

@Results( { @Result(name = "forbidden", location = "forbidden.jsp") })
public abstract class NmActionSupport extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = -6589104649941550869L;

    private static final Logger log = Logger.getLogger(NmActionSupport.class);

    @Resource(name = BeanNames.SecurityProperties)
    protected SecurityMap securityMap;

    public abstract String getPladActionId();

    public String verificar(String pladActionId, UserVO user) {

        String menuid = securityMap.getMenuId(pladActionId);

        if (StringUtils.isBlank(menuid)) {
            throw new RuntimeException("El pladActionId: " + pladActionId + " no existe en SecurityProperties (SecurityMap)");
        }

        if (log.isDebugEnabled()) {
            log.debug("pladActionId: " + pladActionId);
            log.debug("menuid: " + menuid);
        }

        /*if (user.hasIdMenu(menuid) == false) {
            return "forbidden";
        } else {
            
        }*/
        return null;
    }

}
