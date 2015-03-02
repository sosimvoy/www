package com.etax.servlet;


import java.sql.*;

import javax.servlet.http.*;

import java.io.*;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.etax.framework.command.*;
import com.etax.framework.exception.ProcessException;
import com.etax.framework.exception.AuthException;
import com.etax.framework.controller.BaseServlet;
import com.etax.helper.CommandHelper;
import com.etax.helper.MessageHelper;
import com.etax.config.ETaxConfig;
import com.etax.page.PageInfoManager;
import com.etax.page.PageInfoEntity;

@SuppressWarnings("serial")
public class ETaxServlet extends BaseServlet {

	private static Logger logger = Logger.getLogger(ETaxServlet.class);	// log4j ����

	private CommandFactory cmdFactory;

	/** 
	 * implements 
	 * servlet init() ������ ó���ؾ� �ϴ� ���� ����
	 */
	public void initialize() {

	    // lookup and save datasource
        String driver = null;
        String url = null;
        String username = null;
        String password = null;
        int maxActive = 10;
        int maxIdle = 30;
        int maxWait = 10000;
        boolean defaultAutoCommit = false;
        boolean defaultReadOnly = false;
        
        // etax ���� properties ���� �ε�
        String etaxConfigFileName = super.getInitParameter("etaxConfigFileName");
        etaxConfigFileName = super.application.getRealPath(etaxConfigFileName);

        ETaxConfig etaxConfig = new ETaxConfig();
        etaxConfig.load(etaxConfigFileName);

        try {
            
            driver = ETaxConfig.getString("db.driver");
            url = ETaxConfig.getString("db.url");
            username = ETaxConfig.getString("db.username");
            password = ETaxConfig.getString("db.password");
            maxActive = Integer.parseInt(ETaxConfig.getString("db.maxActive"));
            maxIdle = Integer.parseInt(ETaxConfig.getString("db.maxIdle"));
            maxWait = Integer.parseInt(ETaxConfig.getString("db.maxWait"));
            defaultAutoCommit = Boolean.parseBoolean(ETaxConfig.getString("db.defaultAutoCommit"));
            defaultReadOnly = Boolean.parseBoolean(ETaxConfig.getString("db.defaultReadOnly"));
            
            logger.info("Setting up data source.");
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(driver);
            ds.setUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setMaxActive(maxActive);
            ds.setMaxIdle(maxIdle);
            ds.setMaxWait(maxWait);
            ds.setDefaultAutoCommit(defaultAutoCommit);
            ds.setDefaultReadOnly(defaultReadOnly);
            
            super.application.setAttribute("etax.datasource", ds);
            logger.info("Done.");
        } catch(Exception ex) {
            logger.error("DataSource lookup Error["+ex.getMessage()+"]");
        }

     // save command xml  - CommandMapper �� mapping �۾�ó��
        String commandXmlFile = super.getInitParameter("commandXml");
        commandXmlFile = super.application.getRealPath(commandXmlFile);
        CommandMapper mapper = new CommandMapper(commandXmlFile);
        CommandConfiguration cmdConfig = mapper.doMapping();

        // command factory ���� �� mapping ����
        this.cmdFactory = CommandFactory.getInstance();
        this.cmdFactory.setConfiguration(cmdConfig);

        // CommandHelper set (jsp ���� ���������� Command �� �����Ҽ� �ֵ��� �ϴ� Helper Ŭ����)
        // ServletContext �� CommandHelper �� ���
        CommandHelper.setServletContext(super.application);

        // �޽��� properties ���� �ε�
        String messageFile = super.getInitParameter("messageFile");
        messageFile = super.application.getRealPath(messageFile);

        MessageHelper msgHelper = new MessageHelper();
        msgHelper.load(messageFile);

        // �޽��� ó�� ������ ����
        super.messageViewPage = super.getInitParameter("messageViewPage");

	}

	/** 
	 * implements 
	 * servlet service() ������ ó���ؾ� �ϴ� ���� ����
	 */
	public void commonService(HttpServletRequest req, HttpServletResponse res) {

		/* ������ �ʿ��� ������ ���� */
		
		boolean loginCheck = true;
		
		String requestPageName = req.getServletPath();
		int index = requestPageName.lastIndexOf("/");
		requestPageName = requestPageName.substring(index+1);
		logger.info("[requestPageName]="+requestPageName);

		PageInfoEntity pageInfo = PageInfoManager.getPageInfo(requestPageName);
		if( pageInfo != null && "Y".equals(pageInfo.getLogin()) ) {
			loginCheck = true;
		}

		/* ��ū ����( jsp �� ���� ���°�� ��ū�� ���� ) */
		req.setAttribute("etax.controller.token", "Y");

		if( loginCheck ) {
			//����üũ
			//logger.info("[������ �ʿ��� ������]");
			HttpSession session = req.getSession(false);
			if( session == null ) {
				throw new AuthException("L100");
			} else {
			  if ("login.jsp".equals(requestPageName) || "index.jsp".equals(requestPageName) || "register.jsp".equals(requestPageName) 
          || "hidden_login.jsp".equals(requestPageName) || "auth_confirm.jsp".equals(requestPageName) 
          || "auth_step_one.jsp".equals(requestPageName) || "auth_step_two.jsp".equals(requestPageName) || "login1.jsp".equals(requestPageName)) {
          // ������ �̵�
        }	else if(session.getAttribute("session.user_id") != null) {
          // ������ �̵�
        } else {
					throw new AuthException("L100");
				}
			}
		}

		/* file upload ó�� */
		if( !"".equals(req.getParameter("uploadDir")) ) {
			this.fileUploadProc(req, res);
		}

		if (!"".equals(req.getParameter("deleteDir")) )		{
			this.fileDeleteProc(req, res);
		}

	}

	/** 
	 * implements 
	 * servlet service() ������ Command Ŭ���� ���� �κ� ����
	 */
	public String executeCommand(HttpServletRequest req,
								 HttpServletResponse res,
								 String cmd ) throws SQLException, Exception {
		
		String redirectPage = req.getParameter("redirectPage");
		if( !"none".equals(cmd) && !"".equals(cmd) ) {
			logger.info("Command Object Create ...");
			Command command = this.cmdFactory.createCommand(cmd);
			command.init(super.application);
			command.execute(req, res);
			if( "".equals(redirectPage) )
				/* 
				 * �Ķ���ͷ� �Ѿ���� �ʾҰų�, ���� ������츸 factory ���� �������ش�.
				 */
				redirectPage = this.cmdFactory.createRedirectPage(cmd);
		}
		logger.info("[ETaxServlet] redirectPage="+redirectPage);

		return redirectPage;


	}

	/**
	 * ���� ���ε� ó��
	 */
	private void fileUploadProc(HttpServletRequest req, HttpServletResponse res) {
		String uploadDir = req.getParameter("uploadDir");
		String savePath = "";
		if ("sign".equals(uploadDir) || "seal".equals(uploadDir))	{
			savePath = ETaxConfig.getString("seal_dir") + "/" + uploadDir;
		} else {
		    savePath = ETaxConfig.getString("upload_dir") + "/" + uploadDir; 
		}

		//String prefix = "etax_";
	
		int uploadSize = ETaxConfig.getInt("upload_size");

		int sizeLimit = uploadSize * 1024 * 1024 ; // ���� �Ѿ�� ���ܹ߻�

		try{
			MultipartRequest mRequest = new MultipartRequest(req, savePath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
			String fileName = mRequest.getFilesystemName("upfile");  // ������ �̸� ���

			if(fileName == null) {   // ������ ���ε� ���� �ʾ�����
				logger.error("file upload error, fileName is null");
				throw new ProcessException("file upload error");
			} else {  // ������ ���ε� �Ǿ�����

				logger.info("file upload success");
				logger.info("[uploaded fileName] = "+ fileName);
				req.setAttribute("page.upload.fileName", fileName);
				req.setAttribute("page.upload.multipartRequest", mRequest);
			} // end if
		} catch(Exception e) {
			logger.error("file upload error");
			e.printStackTrace();
			throw new ProcessException("file upload error");
		} 
	}

	/**
	 * ���� ���� ó��
	 */
	private void fileDeleteProc(HttpServletRequest req, HttpServletResponse res) {
		String deleteDir = req.getParameter("deleteDir");
		String savePath = "";
		if ("sign".equals(deleteDir) || "seal".equals(deleteDir))	{
			savePath = ETaxConfig.getString("seal_dir") + "/" + deleteDir; // ������ ���丮 (������)
		} else {
		  savePath = ETaxConfig.getString("upload_dir") + "/" + deleteDir; // ������ ���丮 (������)
		}
		String f_name   = req.getParameter("upfile");

		try{
			File file = new File(savePath+"/"+f_name);

			if (file.exists() )	{
				file.delete();
				logger.info("FTP ���� ���� �Ϸ�");
			}

		} catch(Exception e) {
			e.printStackTrace();
			throw new ProcessException("file delete error");
		} 
	}

}
