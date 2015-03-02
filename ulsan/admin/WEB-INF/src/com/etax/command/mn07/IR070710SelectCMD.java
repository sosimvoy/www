/************************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070710SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 국세수납합계표조회
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
import com.etax.db.mn07.IR070710;
import com.etax.entity.CommonEntity;

public class IR070710SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070710SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		String acc_year = request.getParameter("acc_year");		 //회계연도
    String acc_month = request.getParameter("acc_month");  //회계월

		String first_date = acc_year + acc_month + "01";    //월초일		
		String last_date = acc_year + acc_month + TextHandler.getDaysOfMonth(acc_year, acc_month);  //월말일
    String last_day = TextHandler.getDaysOfMonth(acc_year, acc_month);  //마지막일자

		String first_business_date = TextHandler.getBusinessDate(conn, first_date);  //월초영업일
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_date);   //월말영업일

		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}

    if(acc_month.equals("") || acc_month == null){
			Date date = new Date();
			acc_month = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","MM");
		}

		paramInfo.setValue("acc_year",	acc_year);							        	// 회계년도
		paramInfo.setValue("acc_month",	acc_month);	                      // 회계월
		paramInfo.setValue("first_business_date",	first_business_date);		//월초영업일
		paramInfo.setValue("last_business_date",	last_business_date);		//월말영업일   
		paramInfo.setValue("first_date",	first_date);		                //월초
		paramInfo.setValue("last_day",	last_day);		                    //말일


    /* 국세수납 총합계표 */
  	List<CommonEntity> taxTotal = IR070710.getTaxTotal(conn, paramInfo);
		request.setAttribute("page.mn07.taxTotal", taxTotal);

    /* 세입금 이체필 통지서 */
  	List<CommonEntity> taxTransfer  = IR070710.getTaxTransfer(conn, paramInfo);
		request.setAttribute("page.mn07.taxTransfer", taxTransfer);

    request.setAttribute("page.mn07.first_date", first_date);
    request.setAttribute("page.mn07.last_date", last_date);
	}
}