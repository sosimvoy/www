package com.etax.framework.filter;

import javax.servlet.http.*;
import org.apache.log4j.Logger;

public class URLRequestWrapper extends HttpServletRequestWrapper {

	private static Logger logger = Logger.getLogger(URLRequestWrapper.class);	// log4j 설정

	public URLRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	// getServletPath override ( *.etax -> *.jsp 로 바꾸어서 return )
	public String getServletPath() {
		
		StringBuffer servletPath = new StringBuffer(super.getServletPath());
		logger.info("servletPath="+servletPath);
		if( servletPath.indexOf(".jsp") != -1 ) {
			return super.getServletPath();
		}
		int start = servletPath.indexOf(".etax") + 1;
		int end = servletPath.length();
		servletPath = servletPath.replace(start, end, "jsp");
		logger.info("getServletPath() is called => "+ servletPath.toString());
		return servletPath.toString();
	}

	// getParameter override ( null -> "" )
	public String getParameter(String param) {
		if( super.getParameter(param) == null ) {
			return "";
		} else {
			return super.getParameter(param);
		}
	}

}
