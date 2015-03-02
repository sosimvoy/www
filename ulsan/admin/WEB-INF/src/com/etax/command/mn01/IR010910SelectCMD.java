/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010910SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-01
* 프로그램내용	 : 세입 > 세목별일계조회
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR010910;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010910SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010910SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
   
	  CommonEntity paramInfo = new CommonEntity();
		
    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일
    String fis_date    = request.getParameter("fis_date");		 
		String eight_business_date = TextHandler.getndaybeforeBusinessDate(conn ,fis_date, 7);
	  String part_code  = request.getParameter("part_code");	 //부서
	
    if (part_code.equals("99999") || part_code.equals("00110") || part_code.equals("00140") || part_code.equals("00170") || part_code.equals("00200") ||  part_code.equals("00710") )
		{
	   	paramInfo.setValue("fis_year",     request.getParameter("fis_year"));	 //회계연도
		  paramInfo.setValue("fis_date",     eight_business_date);	 //8영업일 회계일자
		}else{
				paramInfo.setValue("fis_year",     request.getParameter("fis_year"));	 //회계연도
  	    paramInfo.setValue("fis_date",     request.getParameter("fis_date"));	 //회계일자
		}

		if (!"".equals(request.getParameter("part_year")) ) {
			paramInfo.setValue("part_year", request.getParameter("part_year"));
		}	else {
			paramInfo.setValue("part_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		paramInfo.setValue("part_code", request.getParameter("part_code"));		 //부서

    logger.info("paramInfo : " + paramInfo);		


		/* 리스트 조회 */
		List<CommonEntity> semokDailyList = IR010910.getSemokDailyList(conn, paramInfo);
		request.setAttribute("page.mn01.semokDailyList", semokDailyList);

    request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);
		 request.setAttribute("page.mn01.eight_business_date", eight_business_date);
	}
}