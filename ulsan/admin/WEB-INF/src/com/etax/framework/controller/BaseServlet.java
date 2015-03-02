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

	private static Logger logger = Logger.getLogger(BaseServlet.class);	// log4j ����
	
	protected ServletContext application;

	protected String messageViewPage;

	public void init() throws ServletException {

		this.application = super.getServletContext();
		// ���� ���� ���� �޼ҵ� ȣ��
		this.initialize();
		
	}

	/* ��ӹ޴� ������ ����(���긴 init ���� ó���Ұ͵� ����) */
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
			 * redirectPage �� �⺻������ command_list.xml �� ������ �������� ���Ѵ�.
			 * ������ redirectPage �� ����ϱ����ؼ��� �Ķ���ͷ� �ѱ��. 
			 */
			redirectPage = req.getParameter("redirectPage");
			message = req.getParameter("message");
	
			// redirectPage = URLDecoder.decode(redirectPage,"euc-kr");

			// ��û ������ ���ϱ�
			StringBuffer requestUrl = new StringBuffer(req.getServletPath());
			if (req.getQueryString() != null) {
				requestUrl.append("?" + req.getQueryString());
			}
			
			// �Ϲ����� ó�� ����
			this.commonService(req, res);

			// cmd �� ���� �ش� Command Class ��ü ���� �� execute(), redirectPage ����
			redirectPage = this.executeCommand(req, res, cmd);



			/* redirect or forward */
			if( (redirectPage != null)&&(!"".equals(redirectPage)) ) {
				// redirectPage �� �ִ°�� -> redirect
				// ���� : �޽����� ������� �޽���ó�� �������� ���� redirect
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
				// redirectPage �� ���°�� -> ��û �������� forward
				logger.info("Jsp Page Forwarding ... " + requestUrl.toString());
				// Jsp ������ ������
				RequestDispatcher dispatcher = req.getRequestDispatcher(requestUrl.toString());
				//RequestDispatcher dispatcher = req.getRequestDispatcher("/holidays/test2.jsp");
				dispatcher.forward(req, res);
			}
		} catch (AuthException ex) {
			throw ex;
		} catch (ProcessException ex) {
			throw ex;
		} catch (ServletException ex) {
			// Forwording Jsp Page Error �� �����ֱ� ���� 
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

	/* sevice() �޼ҵ� ������ ó���ؾ��� �Ϲ����� ���μ��� */
	public abstract void commonService(HttpServletRequest req, HttpServletResponse res);

	/* ��ӹ޴� ������ ����(command �̸��� �´� Command ���� �� ����) */
	public abstract String executeCommand(HttpServletRequest req,
								 HttpServletResponse res,
								 String commandName	) throws SQLException, Exception;
}
