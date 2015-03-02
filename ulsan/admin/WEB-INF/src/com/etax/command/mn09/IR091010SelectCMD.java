/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090610.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 휴일관리
****************************************************************/
package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.db.common.SelectBox;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;

public class IR091010SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("yyyy",     request.getParameter("yyyy"));
		paramInfo.setValue("hol_name", request.getParameter("hol_name"));
		
		/* 휴일 리스트 조회 */
		List holList = IR091010.getHolList(conn, paramInfo);
		request.setAttribute("page.mn09.holList", holList);

    /* 부서조회 */
		//List deptCdList = SelectBox.getDeptCdList(conn, paramInfo);
		//request.setAttribute("page.mn09.deptCdList", deptCdList);

		 /* 부서조회 */
		//List accCdList = SelectBox.getAccCdList(conn, paramInfo);
		//request.setAttribute("page.mn09.accCdList", accCdList);
	}
 }
