/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070610SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-14
* 프로그램내용   : 일계보고서 >감사자료조회
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
import com.etax.db.mn07.IR070610;
import com.etax.entity.CommonEntity;


public class IR070610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070610SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");
		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}

		paramInfo.setValue("acc_year",	  acc_year);
		paramInfo.setValue("acc_sdate",	  TextHandler.replace(request.getParameter("acc_sdate"),"-",""));
		paramInfo.setValue("acc_edate",	  TextHandler.replace(request.getParameter("acc_edate"),"-",""));
		
		// 보고서코드
		List<CommonEntity> dataList = IR070610.getDataList(conn, paramInfo);
		request.setAttribute("page.mn07.dataList", dataList);
	}
}