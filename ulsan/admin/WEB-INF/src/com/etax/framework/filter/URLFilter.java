package com.etax.framework.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import org.apache.log4j.Logger;

public class URLFilter implements Filter {

	private static Logger logger = Logger.getLogger(URLFilter.class);	// log4j ����


	public void init(FilterConfig filterConfig) throws ServletException {
		/* ���� �ʱ�ȭ */
		// ���͸� ȣ���� ������ Context �� ������ �´�.
		//this.filterConfig = filterConfig;
		//this.application = this.filterConfig.getServletContext();
	}

	public void doFilter(ServletRequest request,
		ServletResponse response,
		FilterChain chain) throws IOException, ServletException {

		logger.info("[URLFilter doFilter()]");
		//request ��Ʈ���� ó���� ���ڵ� ����(since servlet spec 2.3)
		((HttpServletRequest)request).setCharacterEncoding("euc-kr");

		// returnUrl ����
		((HttpServletRequest)request).setAttribute("etax.returnURL",
			((HttpServletRequest)request).getContextPath() + 
			((HttpServletRequest)request).getServletPath() + "?" + 
			((HttpServletRequest)request).getQueryString());

		
		// request �� �������ֱ����� Wrapper Ŭ���� ����(*.etax -> *.jsp)
		HttpServletRequestWrapper requestWrapper = 
			new URLRequestWrapper((HttpServletRequest)request);


		chain.doFilter(requestWrapper, response);
	}

	public void destroy() {
		/* ���� �Ҹ� */
		//this.filterConfig = null;
		//this.application = null;
	}
}