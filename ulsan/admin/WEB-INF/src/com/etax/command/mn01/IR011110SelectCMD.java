/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR011110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-01
* 프로그램내용	 : 세입 > 세목별일계조회
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011110;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
//import com.etax.util.StringUtil;

public class IR011110SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011110SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
   
	  CommonEntity paramInfo = new CommonEntity();
		
    String fis_year   = request.getParameter("fis_year");		 //회계연도
    String from_date  = request.getParameter("from_date");	 //시작일자
    String to_date    = request.getParameter("to_date");		 //종료일자
    String part_code  = request.getParameter("part_code");	 //부서
    String first_business_date = TextHandler.getBusinessDate(conn, from_date);  //시작영업일
		String last_business_date = TextHandler.getAgoBusinessDate(conn, to_date);   //종료영업일

    String eight_business_from_date = TextHandler.getndaybeforeBusinessDate(conn ,from_date, 7); //구군청만 당일포함 8영업일
    String eight_business_to_date = TextHandler.getndaybeforeBusinessDate(conn ,to_date, 7);//구군청만 당일포함 8영업일

		if (!"".equals(request.getParameter("part_year")) ) {
			paramInfo.setValue("part_year", request.getParameter("part_year"));
		}	else {
			paramInfo.setValue("part_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}

    if(fis_year.equals("") || fis_year == null){
			Date date = new Date();
			fis_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(from_date.equals("") || from_date == null){
			Date date = new Date();
			from_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}
    if(to_date.equals("") || to_date == null){
			Date date = new Date();
			to_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}
    
		if (part_code.equals("99999") || part_code.equals("00110") || part_code.equals("00140") || part_code.equals("00170") || part_code.equals("00200") ||  part_code.equals("00710") )
		{
	    paramInfo.setValue("fis_year",      fis_year);	
			paramInfo.setValue("from_date",     eight_business_from_date);	 
		  paramInfo.setValue("to_date",       eight_business_to_date);	
			paramInfo.setValue("part_code",     part_code);	
		}else{
			paramInfo.setValue("fis_year",      fis_year);		 
			paramInfo.setValue("from_date",     first_business_date);	 
			paramInfo.setValue("to_date",       last_business_date);	
			paramInfo.setValue("part_code",     part_code);		 
		}

    logger.info("paramInfo : " + paramInfo);		


		/* 리스트 조회 */
		List<CommonEntity> semokTotalList = IR011110.getSemokTotalList(conn, paramInfo);
		request.setAttribute("page.mn01.semokTotalList", semokTotalList);

		request.setAttribute("page.mn01.eight_business_from_date", eight_business_from_date);
		request.setAttribute("page.mn01.eight_business_to_date", eight_business_to_date);
	}
}