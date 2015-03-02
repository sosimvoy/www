/*****************************************************
* 프로젝트명      : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : IR091610SelectCMD.java
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
import com.etax.db.mn09.IR091610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR091610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091610SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
    //등록팝업용 쿼리실행
		paramInfo.setValue("queyear",           request.getParameter("year")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("stdAcccode")); 
    if("31".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		/* 등록form의 세목자료 조회 */
		List useSemokList = IR091610.getuseSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.useSemokCdList", useSemokList);

		/* 현년도 예산과목 자료 조회 */
		List nowIncomeList = IR091610.getnowIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.nowincomeSemokCdList", nowIncomeList);

		/* 과년도 예산과목 세목자료 조회 */
		List oldIncomeList = IR091610.getoldIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.oldincomeSemokCdList", oldIncomeList);



    //표준세목코드 자료 조회용
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
    if("31".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		logger.info("paramInfo : " + paramInfo);
		List standardsemokkList = IR091610.getstandardsemokkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardsemokkList", standardsemokkList);
	}
}