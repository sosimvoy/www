package com.etax.framework.controller;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

import com.etax.framework.exception.AuthException;
import com.etax.framework.exception.ProcessException;

@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(BaseServlet.class);	// log4j 설정
	
	protected ServletContext application;

	protected String messageViewPage;

	public void init() throws ServletException {

		this.application = super.getServletContext();
		// 하위 서블릿 구현 메소드 호출
		this.initialize();
		
	}

	/* 상속받는 서블릿이 구현(서브릿 init 에서 처리할것들 구현) */
	public abstract void initialize();

	public void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		try {
			String redirectPage = null;
			String message = null;
			
			String cmd = req.getParameter("cmd");
			logger.info("***********************************************************");
			logger.info("[cmd]="+cmd);
			logger.info("***********************************************************");
			/*
			 * redirectPage 는 기본적으로 command_list.xml 에 설정된 페이지로 정한다.
			 * 별도의 redirectPage 를 사용하기위해서는 파라미터로 넘긴다. 
			 */
			redirectPage = req.getParameter("redirectPage");
			message = req.getParameter("message");
	
			// redirectPage = URLDecoder.decode(redirectPage,"euc-kr");

			// 요청 페이지 구하기
			StringBuffer requestUrl = new StringBuffer(req.getServletPath());
			if (req.getQueryString() != null) {
				requestUrl.append("?" + req.getQueryString());
			}
			
			// 일반적인 처리 로직
			this.commonService(req, res);

			// cmd 에 따른 해당 Command Class 객체 생성 및 execute(), redirectPage 생성
			redirectPage = this.executeCommand(req, res, cmd);



			/* redirect or forward */
			if( (redirectPage != null)&&(!"".equals(redirectPage)) ) {
				// redirectPage 가 있는경우 -> redirect
				// 수정 : 메시지가 있을경우 메시지처리 페이지로 먼저 redirect
				if( !"".equals(message) ) {
					String newMessageViewPage = 
						messageViewPage + "?message=" + message + "&redirectPage=" + redirectPage;
					logger.info("Jsp Page Redirecting ... " + newMessageViewPage);
					res.sendRedirect(newMessageViewPage);
				} else {
					logger.info("Jsp Page Redirecting ... " + redirectPage);
					res.sendRedirect(redirectPage);
				}
			} else {
				// redirectPage 가 없는경우 -> 요청 페이지로 forward
				logger.info("Jsp Page Forwarding ... " + requestUrl.toString());
				// Jsp 페이지 포워딩
				RequestDispatcher dispatcher = req.getRequestDispatcher(requestUrl.toString());
				//RequestDispatcher dispatcher = req.getRequestDispatcher("/holidays/test2.jsp");
				dispatcher.forward(req, res);
			}
		} catch (AuthException ex) {
			throw ex;
		} catch (ProcessException ex) {
			throw ex;
		} catch (ServletException ex) {
			// Forwording Jsp Page Error 를 보여주기 위해 
			throw ex;
		} catch (Exception ex) {
			logger.error("BaseServlet execute() error");
			ex.printStackTrace();
			String msg = ex.getMessage();
			if( msg == null ) {
				msg = ex.toString();
			}
			throw new ProcessException("BaseServlet execute() error : "+msg, ex);
		}

	}

	/* sevice() 메소드 내에서 처리해야할 일반적인 프로세스 */
	public abstract void commonService(HttpServletRequest req, HttpServletResponse res);

	/* 상속받는 서블릿이 구현(command 이름에 맞는 Command 생성 및 실행) */
	public abstract String executeCommand(HttpServletRequest req,
								 HttpServletResponse res,
								 String commandName	) throws SQLException, Exception;
}
