/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-09
* 프로그램내용   : 일계보고서 > 보고서 조회 (코드)
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
import com.etax.db.mn07.IR070110;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;


public class IR070110SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070110SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year     = request.getParameter("acc_year");
        String report_gubun = request.getParameter("report_gubun");
        String acc_type     = request.getParameter("acc_type");
        String part_code    = request.getParameter("part_code");
        String acc_code     = request.getParameter("acc_code");
		
		if(acc_year.equals("") || acc_year == null){
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
        if(report_gubun.equals("") || report_gubun == null){
			report_gubun = "D";
		}
        if(part_code.equals("") || part_code == null){
			part_code = "00000";
		}
        if(acc_code.equals("") || acc_code == null){
			acc_code = "11";
		}


		paramInfo.setValue("acc_year",	    acc_year);
		paramInfo.setValue("report_gubun",	report_gubun);
		paramInfo.setValue("acc_type",	    acc_type);
		paramInfo.setValue("part_code",	    part_code);
		paramInfo.setValue("acc_code",	    acc_code);


		// 보고서코드 리스트
  		List<CommonEntity> reportCodeList = ReportDAO.getReportCodeList(conn, paramInfo);
		request.setAttribute("page.mn07.reportCodeList", reportCodeList);
		
		// 부서코드
  		List<CommonEntity> partList = IR070110.getPartList(conn, paramInfo);
		request.setAttribute("page.mn07.partList", partList);
		
		// 회계코드
  		List<CommonEntity> accList = IR070110.getAccList(conn, paramInfo);
		request.setAttribute("page.mn07.accList", accList);
	
	}
}