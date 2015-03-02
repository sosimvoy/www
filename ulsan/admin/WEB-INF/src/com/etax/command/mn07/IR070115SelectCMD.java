/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070115SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세외수입일계표
****************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;
import com.etax.util.StringUtil;
import com.etax.db.mn07.IR070115;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;

public class IR070115SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070115SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");
		String acc_date = request.getParameter("acc_date");
		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		paramInfo.setValue("acc_year",	acc_year);								// 회계년도
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// 회계일자
		paramInfo.setValue("acc_type",	request.getParameter("acc_type"));		// 회계구분
		paramInfo.setValue("part_code",	request.getParameter("part_code"));		// 부서코드
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// 회계코드
		
        // 마감여부	
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);
		// 보고서
  		List<CommonEntity> reportList = IR070115.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);
	
	}
}