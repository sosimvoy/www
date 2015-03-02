package com.etax.framework.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.exception.ProcessException;

public abstract class BaseCommand implements Command {

	private static Logger logger = Logger.getLogger(BaseCommand.class);	// log4j 설정

	/* 각 Command 들이 생성될때 한번만 저장하기 위한 static 변수 */
	protected ServletContext application = null;
	protected DataSource ds;

	

	public void init(ServletContext application) {
		if( this.application == null ) {
			// 초기화 관련 세팅부분 
			logger.info("Command Init start");
			this.application = application;
			this.ds = (DataSource)this.application.getAttribute("etax.datasource");
			logger.info("datasource :::: " + application.getAttribute("etax.datasource"));
			if( this.ds == null ) {
				logger.error("DataSource is null");
			}
		}
	}

	public void execute(HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		try {
			if( this.ds == null ) {
				logger.error("DataSource is null");
				throw new ProcessException("E001");
			}
			conn = this.ds.getConnection();
			// Connection 이 필요한 각 Command 들이 구현한 execute() 실행
			this.execute(request, response, conn);
		} catch( NullPointerException e ) {
			logger.error("DataSource getConnection error");
			e.printStackTrace();
			throw new ProcessException(e);
		} catch( SQLException e ) {
			e.printStackTrace();
			throw new ProcessException(e);
		} finally {
			if( conn != null ) {
				try {
					conn.close();
				} catch(Exception ex) {
					logger.error("Connection Close Error");
					ex.printStackTrace();
					throw new ProcessException(ex);
					
				}
			}
		}
	}

	/* 각 Command 들이 구현해야 하는 메소드 */
	public abstract void execute(HttpServletRequest request,
								 HttpServletResponse response,
								 Connection conn) throws SQLException ;
}
