/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR071110InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 세입금정정필통지서
****************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071110InsertCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR071110InsertCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("year",       request.getParameter("year"));
    paramInfo.setValue("input_date", request.getParameter("input_date"));
    paramInfo.setValue("taxgbn",     request.getParameter("taxgbn"));
    paramInfo.setValue("semokCode",  request.getParameter("semokCode"));
    paramInfo.setValue("partCode",   request.getParameter("partCode"));
    paramInfo.setValue("sunapamt",   request.getParameter("sunapamt"));
    paramInfo.setValue("sunapcnt",   request.getParameter("sunapcnt"));
    paramInfo.setValue("hwanbuamt",   request.getParameter("hwanbuamt"));
    paramInfo.setValue("hwanbucnt",   request.getParameter("hwanbucnt"));
    paramInfo.setValue("queyear", request.getParameter("queyear"));
    paramInfo.setValue("que_date", request.getParameter("que_date"));
    paramInfo.setValue("quetaxgbn", request.getParameter("quetaxgbn"));
    paramInfo.setValue("queprocgbn", request.getParameter("queprocgbn"));
    paramInfo.setValue("taxgbn"  , request.getParameter("taxgbn"));
    paramInfo.setValue("mokgubun", request.getParameter("mokgubun"));

    logger.info("paramInfo : " + paramInfo);		

		/* 수입일계표 이력테이블에 자료등록 */		
		IR071110.budgethstinsertList(conn, paramInfo);

		/* 수입일계표 생성전 자료체크 */		
		CommonEntity passChk = IR071110.budgetinsertbeforDatachk(conn, paramInfo);
    
    if ("Y".equals(passChk.getString("EXIST_YN")) ) {
		  IR071110.budgetupdateList(conn, paramInfo);
    }else {
  		/* 수입일계표 등록 */		
	  	IR071110.budgetinsertList(conn, paramInfo);
    }

		request.setAttribute("page.mn07.SucMsg", "등록되었습니다.");

		/* 수입일계표 조회 */		
		List<CommonEntity> budgetList = IR071110.budgetList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetList", budgetList);


		/* 예산과목 조회 */		
		List<CommonEntity> budgetsemokList = IR071110.getbudgetsemokList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetsemokList", budgetsemokList);

		/* 예산부서 조회 */		
		List<CommonEntity> budgetpartList = IR071110.getbudgetpartList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetpartList", budgetpartList);

    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일
    request.setAttribute("page.mn07.ago_acc_date", ago_acc_date);

	}
}
