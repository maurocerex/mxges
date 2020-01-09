package com.metlife.nm.domain;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public abstract class DomainFilter implements Filter {
	private static Logger log = Logger.getLogger(DomainFilter.class);

	private static boolean noInit = true;

	private FilterConfig config;

	public void init(FilterConfig filterconfig) throws ServletException {

		config = filterconfig;
		noInit = false;
	}

	public void destroy() {

		config = null;
	}

	public FilterConfig getFilterConfig() {

		return config;
	}

	public void setFilterConfig(FilterConfig filterconfig) {

		if (noInit) {
			noInit = false;
			config = filterconfig;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain) throws IOException, ServletException {

		HttpServletRequest httpservletrequest = (HttpServletRequest) request;

		String send = httpservletrequest.getRequestURL().toString();
		if (log.isDebugEnabled()) {
			log.debug("RequestURL:[" + send + "]");
		}

		if (isFilterException(send)) {
			filterchain.doFilter(request, response);
			return;
		} else {
			if (isThisSessionValid(httpservletrequest)) {
				filterchain.doFilter(request, response);
			} else {
				if (log.isDebugEnabled()) {
					log.debug(getMessageBeforeRedirect());
				}				
				redirect(httpservletrequest, (HttpServletResponse) response);
			}

		}
	}


	protected abstract boolean isThisSessionValid(HttpServletRequest request);

	protected abstract boolean isFilterException(String send);

	protected abstract void redirect(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws IOException;
	
	protected abstract String getMessageBeforeRedirect();
}
