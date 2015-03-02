package com.etax.framework.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.apache.log4j.Logger;

public class URLFilter implements Filter {

	private static Logger logger = Logger.getLogger(URLFilter.class);	// log4j 설정


	public void init(FilterConfig filterConfig) throws ServletException {
		/* 필터 초기화 */
		// 필터를 호출한 서블릿의 Context 를 가지고 온다.
		//this.filterConfig = filterConfig;
		//this.application = this.filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest request,
		ServletResponse response,
		FilterChain chain) throws IOException, ServletException {

		logger.info("[URLFilter doFilter()]");
		//request 스트림을 처리할 인코딩 설정(since servlet spec 2.3)
		((HttpServletRequest)request).setCharacterEncoding("euc-kr");

		// returnUrl 세팅
		((HttpServletRequest)request).setAttribute("etax.returnURL",
			((HttpServletRequest)request).getContextPath() + 
			((HttpServletRequest)request).getServletPath() + "?" + 
			((HttpServletRequest)request).getQueryString());

		
		// request 를 변경해주기위한 Wrapper 클래스 생성(*.etax -> *.jsp)
		HttpServletRequestWrapper requestWrapper = 
			new URLRequestWrapper((HttpServletRequest)request);


		chain.doFilter(requestWrapper, response);
	}

	public void destroy() {
		/* 필터 소멸 */
		//this.filterConfig = null;
		//this.application = null;
	}
}