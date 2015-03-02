package com.etax.framework.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import java.sql.*;
import javax.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.exception.ProcessException;

public abstract class BaseCommand implements Command {

	private static Logger logger = Logger.getLogger(BaseCommand.class);	// log4j ����

	/* �� Command ���� �����ɶ� �ѹ��� �����ϱ� ���� static ���� */
	protected ServletContext application = null;
	protected DataSource ds;

	

	public void init(ServletContext application) {
		if( this.application == null ) {
			// �ʱ�ȭ ���� ���úκ� 
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
			// Connection �� �ʿ��� �� Command ���� ������ execute() ����
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

	/* �� Command ���� �����ؾ� �ϴ� �޼ҵ� */
	public abstract void execute(HttpServletRequest request,
								 HttpServletResponse response,
								 Connection conn) throws SQLException ;
}
