/*****************************************************
* 프로젝트명      : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : IR090610SelectCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-07-13
* 프로그램내용		: 시스템운영 > 로그관리
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090510;
import com.etax.entity.CommonEntity;

public class IR090510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR090510SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //조회시작일자
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //조회종료일자
		paramInfo.setValue("user_name",       request.getParameter("user_name"));   //사용자명
	
	
		/* 가상계좌(개별) 조회 */
		List logList = IR090510.getLogList(conn, paramInfo);
		request.setAttribute("page.mn09.logList", logList);
	}
}