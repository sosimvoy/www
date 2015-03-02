/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-28
* 프로그램내용   : 세입세출외현금 > 영수필통지서
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;


public class IR030511ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030511ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
     paramInfo.setValue("seq",               request.getParameter("seq"));

  	/* 수기분 상세  */
		CommonEntity juheangView = IR030510.getYoungsuView(conn, paramInfo);
		request.setAttribute("page.mn03.juheangView", juheangView);
	}
}