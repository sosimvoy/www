/*****************************************************
* 프로젝트명      : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : IR090610SelectCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-08-19
* 프로그램내용		: 시스템운영 > 부서코드관리
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090610;
import com.etax.entity.CommonEntity;
import com.etax.util.*;

public class IR090610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR090610SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

        String year = request.getParameter("year");

        if (year == null || "".equals(year) ) {
            year = TextHandler.getCurrentDate().substring(0,4);
        }
		
		paramInfo.setValue("under_year",     year); 

		/* 부서코드 조회 */
		List partCodeList = IR090610.getPartCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.partCodeList", partCodeList);
   
		/* 재배정원부서코드 */
		List allotPartCodeList = IR090610.getAllotPartCode(conn, paramInfo);
		request.setAttribute("page.mn09.allotPartCodeList", allotPartCodeList);
		
	}
}