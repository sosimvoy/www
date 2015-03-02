/***************************************************************
* 프로젝트명     : 경기도 세출시스템
* 프로그램명     : IR091410SelectCMD.java
* 프로그램작성자 : 
* 프로그램작성일 : 2010-05-06
* 프로그램내용   : 관리자 > 직인정보관리
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091410;
import com.etax.entity.CommonEntity;

public class IR091410SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR091410SelectCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
	  /* 세션 설정 */
    
    HttpSession session = request.getSession(false);
		String current_organ = (String)session.getAttribute("session.current_organ");

		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("current_organ",            current_organ);
	
	  logger.info("paramInfo : " + paramInfo);
		
		CommonEntity signInfo = IR091410.getSignInfo(conn, paramInfo);
		request.setAttribute("page.mn09.signInfo", signInfo);

		}
	}