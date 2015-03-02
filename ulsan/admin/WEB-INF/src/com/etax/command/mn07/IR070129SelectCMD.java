/************************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070129SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입월계표(회계별)
*************************************************************************/
 
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
import com.etax.db.mn07.IR070129;
import com.etax.db.common.ReportDAO;   
import com.etax.entity.CommonEntity;

public class IR070129SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070129SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		CommonEntity paramInfo = new CommonEntity();

		/* 조회할 페이지 범위 구하기 */
		int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		/*int fromRow = (cpage-1) * 10 ;
		int toRow = cpage * 10;*/
		//int fromRow = (cpage-1) * ETaxConfig.getInt("page_cnt");
		//int toRow = cpage * ETaxConfig.getInt("page_cnt");

		int page_cnt = 5;
		int fromRow = (cpage-1) * page_cnt;
		int toRow = cpage * page_cnt;

		
		String acc_year = request.getParameter("acc_year");		 //회계연도
		String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");		 //회계일자
		String first_date = acc_date.substring(0,6) + "01";    //월초일
		String last_date = acc_date.substring(0,6) + TextHandler.getDaysOfMonth(acc_date.substring(0,4), acc_date.substring(4,6));  //월말일
		String first_business_date = TextHandler.getBusinessDate(conn, first_date);  //월초영업일
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_date);   //월말영업일

		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		if( !"last_business_date".equals(request.getParameter("acc_date")) ) {
		  request.setAttribute("page.mn07.SucMsg", "월말영업일이 아닙니다. 다시 선택하십시오.");
		}
	
		paramInfo.setValue("acc_year",	acc_year);								// 회계년도
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// 회계일자
		paramInfo.setValue("acc_type",	request.getParameter("acc_type"));		// 회계구분
		paramInfo.setValue("part_code",	request.getParameter("part_code"));		// 부서코드
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// 회계코드	 
		paramInfo.setValue("first_business_date",	first_business_date);		//월초영업일
		paramInfo.setValue("last_business_date",	last_business_date);		//월말영업일
		paramInfo.setValue("fromRow",     fromRow + "");
		paramInfo.setValue("toRow",       toRow + "");

    /* 마감여부	*/
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);

		/* 보고서 */
  		List<CommonEntity> reportList = IR070129.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);

		/* 총 카운트 조회 */
		CommonEntity rowData = IR070129.getTotalCount(conn, paramInfo);
		request.setAttribute("page.mn07.totalCount", new Integer(rowData.getInt("CNT")));
	
	}
}