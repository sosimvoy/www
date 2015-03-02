/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR090510ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용   : 시스템운영 > 코드 상세
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;


public class IR091010ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091010ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
   paramInfo.setValue("hol_date",        request.getParameter("hol_date"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] hol_list = request.getParameterValues("hol_list");
		
		int chk_val = Integer.parseInt(chk_list[0]);
		paramInfo.setValue("hol_date",           hol_list[chk_val]);

			/* 휴일 상세  */
		CommonEntity HolView = IR091010.getHolView(conn, paramInfo);
		request.setAttribute("page.mn09.HolView", HolView);
	}
}