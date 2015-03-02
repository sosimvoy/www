 /**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR062010SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 일일자금현황조회
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR062010;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR062010SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR062010SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

		String acc_date    = request.getParameter("acc_date");   //회계일
		String before_date = TextHandler.getBeforeDate(conn, acc_date);  //전영업일

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("before_date",         before_date);
		paramInfo.setValue("acc_date",            request.getParameter("acc_date"));
    paramInfo.setValue("fis_year",            request.getParameter("fis_year"));

		logger.info("paramInfo : " + paramInfo);
    
		CommonEntity dailyData = IR060000.dailyCheck(conn, acc_date); 
		if (!(request.getParameter("acc_date")).equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
			//영업일 체크
		  request.setAttribute("page.mn06.SucMsg",   "해당일자는 영업일이 아닙니다.");

		} else if (Integer.parseInt(request.getParameter("acc_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {
			//등록일이 오늘을 지났는지 체크
		  request.setAttribute("page.mn06.SucMsg",   "조회일자가 금일이후입니다.");

		} else if (!"Y".equals(dailyData.getString("M210_PUBLICDEPOSITSTATE"))) {
			//업무마감체크
		  request.setAttribute("page.mn06.SucMsg",   "공금예금 잔액 등록 후 조회가 가능합니다.");
		} else {
			List<CommonEntity> dailyMoneyList = IR062010.getDailyMoneyList(conn, paramInfo);
      request.setAttribute("page.mn06.dailyMoneyList",   dailyMoneyList);
		}
  }
}