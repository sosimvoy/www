/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090710.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 시스템운영 > 회계코드신청
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090710;
import com.etax.entity.CommonEntity;

public class IR090710SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090710SelectCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity(); 

    	paramInfo.setValue("under_year",           request.getParameter("under_year")); 
			paramInfo.setValue("accGubun",       request.getParameter("accGubun")); 

  	/* 회계코드 조회 */
		List accCodeList = IR090710.getAccCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.accCodeList", accCodeList);
	}
}