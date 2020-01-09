package com.metlife.nm.domain;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.app.event.MethodExceptionEventHandler;
import org.apache.velocity.app.event.NullSetEventHandler;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;

public class MxgesEventCartridge implements ReferenceInsertionEventHandler, NullSetEventHandler, MethodExceptionEventHandler,
    LogChute {

    private final static Logger log = Logger.getLogger(MxgesEventCartridge.class);

    private ReferenceInsertionEventHandler rieh;

    private NullSetEventHandler nseh;

    private MethodExceptionEventHandler meeh;

    public MxgesEventCartridge() {
        rieh = null;
        nseh = null;
        meeh = null;
    }

    public boolean shouldLogOnNullSet(String lhs, String rhs) {
        if (log.isDebugEnabled()) {
            log.debug("shouldLogOnNullSet lhs:[" + lhs + "] rhs:[" + rhs + "]");
        }

        if (nseh == null)
            return true;
        else
            return nseh.shouldLogOnNullSet(lhs, rhs);
    }

    /**
     * Event handler for when a reference is inserted into the output stream.
     */
    public Object referenceInsert(String reference, Object value) {
        if (value == null) {
            if (log.isDebugEnabled()) {
                log.debug("reference:[" + reference + "] is null");
            }
        } else if (value instanceof String && StringUtils.isBlank((String) value)) {
            if (log.isDebugEnabled()) {
                log.debug("referenceInsert reference:[" + reference + "] is blank string.");
            }
        }

        if (rieh == null)
            return value;
        else
            return rieh.referenceInsert(reference, value);
    }

    public Object methodException(Class claz, String method, Exception e) throws Exception {
        log.error(e);
        if (meeh == null)
            throw e;
        else
            return meeh.methodException(claz, method, e);
    }

    /**
     * Required init() method for LogSystem to get access to RuntimeServices
     */
    public void init(RuntimeServices rs) {
        return;
    }

    /**
     * This is the key method needed to implement a logging interface for Velocity.
     */
    public void logVelocityMessage(int level, String message) {
        if (log.isDebugEnabled()) {
            log.debug("level : " + level + " msg : " + message);
        }
    }

    public boolean isLevelEnabled(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    public void log(int arg0, String arg1) {
        // TODO Auto-generated method stub
        
    }

    public void log(int arg0, String arg1, Throwable arg2) {
        // TODO Auto-generated method stub
        
    }

}