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

	private static Logger logger = Logger.getLogger(ETaxServlet.class);	// log4j 설정

	private CommandFactory cmdFactory;

	/** 
	 * implements 
	 * servlet init() 내에서 처리해야 하는 로직 구현
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
        
        // etax 설정 properties 파일 로드
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

     // save command xml  - CommandMapper 가 mapping 작업처리
        String commandXmlFile = super.getInitParameter("commandXml");
        commandXmlFile = super.application.getRealPath(commandXmlFile);
        CommandMapper mapper = new CommandMapper(commandXmlFile);
        CommandConfiguration cmdConfig = mapper.doMapping();

        // command factory 생성 및 mapping 저장
        this.cmdFactory = CommandFactory.getInstance();
        this.cmdFactory.setConfiguration(cmdConfig);

        // CommandHelper set (jsp 에서 독립적으로 Command 를 실행할수 있도록 하는 Helper 클래스)
        // ServletContext 를 CommandHelper 에 등록
        CommandHelper.setServletContext(super.application);

        // 메시지 properties 파일 로드
        String messageFile = super.getInitParameter("messageFile");
        messageFile = super.application.getRealPath(messageFile);

        MessageHelper msgHelper = new MessageHelper();
        msgHelper.load(messageFile);

        // 메시지 처리 페이지 저장
        super.messageViewPage = super.getInitParameter("messageViewPage");

	}

	/** 
	 * implements 
	 * servlet service() 내에서 처리해야 하는 로직 구현
	 */
	public void commonService(HttpServletRequest req, HttpServletResponse res) {

		/* 인증이 필요한 페이지 구분 */
		
		boolean loginCheck = true;
		
		String requestPageName = req.getServletPath();
		int index = requestPageName.lastIndexOf("/");
		requestPageName = requestPageName.substring(index+1);
		logger.info("[requestPageName]="+requestPageName);

		PageInfoEntity pageInfo = PageInfoManager.getPageInfo(requestPageName);
		if( pageInfo != null && "Y".equals(pageInfo.getLogin()) ) {
			loginCheck = true;
		}

		/* 토큰 저장( jsp 로 직접 들어온경우 토큰이 없다 ) */
		req.setAttribute("etax.controller.token", "Y");

		if( loginCheck ) {
			//인증체크
			//logger.info("[인증이 필요한 페이지]");
			HttpSession session = req.getSession(false);
			if( session == null ) {
				throw new AuthException("L100");
			} else {
			  if ("login.jsp".equals(requestPageName) || "index.jsp".equals(requestPageName) || "register.jsp".equals(requestPageName) 
          || "hidden_login.jsp".equals(requestPageName) || "auth_confirm.jsp".equals(requestPageName) 
          || "auth_step_one.jsp".equals(requestPageName) || "auth_step_two.jsp".equals(requestPageName) || "login1.jsp".equals(requestPageName)) {
          // 페이지 이동
        }	else if(session.getAttribute("session.user_id") != null) {
          // 페이지 이동
        } else {
					throw new AuthException("L100");
				}
			}
		}

		/* file upload 처리 */
		if( !"".equals(req.getParameter("uploadDir")) ) {
			this.fileUploadProc(req, res);
		}

		if (!"".equals(req.getParameter("deleteDir")) )		{
			this.fileDeleteProc(req, res);
		}

	}

	/** 
	 * implements 
	 * servlet service() 내에서 Command 클래스 실행 부분 구현
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
				 * 파라미터로 넘어오지 않았거나, 값이 없을경우만 factory 에서 생성해준다.
				 */
				redirectPage = this.cmdFactory.createRedirectPage(cmd);
		}
		logger.info("[ETaxServlet] redirectPage="+redirectPage);

		return redirectPage;


	}

	/**
	 * 파일 업로드 처리
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

		int sizeLimit = uploadSize * 1024 * 1024 ; // 제한 넘어서면 예외발생

		try{
			MultipartRequest mRequest = new MultipartRequest(req, savePath, sizeLimit, "euc-kr", new DefaultFileRenamePolicy());
			String fileName = mRequest.getFilesystemName("upfile");  // 파일의 이름 얻기

			if(fileName == null) {   // 파일이 업로드 되지 않았을때
				logger.error("file upload error, fileName is null");
				throw new ProcessException("file upload error");
			} else {  // 파일이 업로드 되었을때

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
	 * 파일 삭제 처리
	 */
	private void fileDeleteProc(HttpServletRequest req, HttpServletResponse res) {
		String deleteDir = req.getParameter("deleteDir");
		String savePath = "";
		if ("sign".equals(deleteDir) || "seal".equals(deleteDir))	{
			savePath = ETaxConfig.getString("seal_dir") + "/" + deleteDir; // 저장할 디렉토리 (절대경로)
		} else {
		  savePath = ETaxConfig.getString("upload_dir") + "/" + deleteDir; // 저장할 디렉토리 (절대경로)
		}
		String f_name   = req.getParameter("upfile");

		try{
			File file = new File(savePath+"/"+f_name);

			if (file.exists() )	{
				file.delete();
				logger.info("FTP 파일 삭제 완료");
			}

		} catch(Exception e) {
			e.printStackTrace();
			throw new ProcessException("file delete error");
		} 
	}

}
