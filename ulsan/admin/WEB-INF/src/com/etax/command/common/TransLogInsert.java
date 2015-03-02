/*****************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : TransLogInsert.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-07-14
* 프로그램내용		: 공통 > 거래로그입력
******************************************************/

package com.etax.command.common;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.common.LogDAO;
import com.etax.entity.CommonEntity;

public class TransLogInsert extends BaseCommand {
	private static Logger logger = Logger.getLogger(TransLogInsert.class);	// log4j 설정
  public static String p_log = "";
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    HttpSession session = request.getSession(false);
		String user_name = (String)session.getAttribute("session.user_name");
		String user_ip = (String)session.getAttribute("session.user_ip");

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("user_name",          user_name);
		paramInfo.setValue("work_type",          request.getParameter("work_log"));
		paramInfo.setValue("trans_gubun",        request.getParameter("trans_gubun"));
		paramInfo.setValue("user_ip",            user_ip);

		if ("".equals(request.getParameter("work_log")) ) {
			paramInfo.setValue("work_type",  request.getAttribute("work_log"));
		}

		if ("".equals(request.getParameter("trans_gubun")) ) {
			paramInfo.setValue("trans_gubun",  request.getAttribute("trans_gubun"));
		}

		/* 거래로그 번호 조회 */
		CommonEntity log_no = LogDAO.getLogNo(conn);

    paramInfo.setValue("log_no",             log_no.getString("LOG_NO"));
		p_log = log_no.getString("LOG_NO");
    
		conn.setAutoCommit(false);
		if (LogDAO.insertTransLog(conn, paramInfo) < 1) {
			conn.rollback();
			conn.setAutoCommit(true);
			throw new ProcessException("E002"); //등록중 오류메시지
    }
    
		conn.commit();
	  conn.setAutoCommit(true);
	}

	public String getLogNo() {
		return p_log;
	}
}