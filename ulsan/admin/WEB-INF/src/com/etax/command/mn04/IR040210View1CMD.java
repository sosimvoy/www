/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR040210ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세외수입 > 등록내역 상세
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;


public class IR040210View1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210View1CMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

			paramInfo.setValue("seq",       request.getParameter("seq"));
		  paramInfo.setValue("year",      request.getParameter("year"));
      
  	/* 수기분 상세  */
		CommonEntity chukyngView = IR040210.getChukyngView(conn, paramInfo);
		request.setAttribute("page.mn04.chukyngView", chukyngView);
	}
}