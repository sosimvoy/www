/**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR010610DeleteCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 농협자료취소
***********************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn01.IR010610;
import com.etax.util.TextHandler;

public class IR010610DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR010610DeleteCMD.class);

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
   
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
		paramInfo.setValue("data_type",   request.getParameter("data_type"));
		paramInfo.setValue("work_log",    request.getParameter("work_log"));
    
    if ("G1".equals(paramInfo.getString("data_type")) ) {
      paramInfo.setValue("trans_gubun", "161");
    } else if ("G2".equals(paramInfo.getString("data_type")) ) {
      paramInfo.setValue("trans_gubun", "162");
    } else if ("G3".equals(paramInfo.getString("data_type")) ) {
      paramInfo.setValue("trans_gubun", "163");
    } else if ("G4".equals(paramInfo.getString("data_type")) ) {
      paramInfo.setValue("trans_gubun", "164");
    }

    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일
    request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);

		if ("G1".equals(request.getParameter("data_type")) || "G3".equals(request.getParameter("data_type")) 
      || "G4".equals(request.getParameter("data_type"))) {
			paramInfo.setValue("in_type",     "I1");
		} else if ("G2".equals(request.getParameter("data_type"))) {
			paramInfo.setValue("in_type",     "I2");
		}

    if ("G4".equals(paramInfo.getString("data_type")) ) {
      paramInfo.setValue("sunap_gigwan",  "310001");
    } else {
      paramInfo.setValue("sunap_gigwan",  "110000");
    }
		
		CommonEntity dailyChk = IR010610.getDailyInfo(conn, paramInfo);  //일일마감체크

		if ("Y".equals(dailyChk.getString("M210_WORKENDSTATE")))	{
			request.setAttribute("page.mn01.SucMsg",   "일일마감이 끝났습니다. 농협자료를 삭제할 수 없습니다.");
		} else {

			if (IR010610.deleteNongHyupData(conn, paramInfo) < 1)	{
				logger.info("삭제할 농협자료 없음!! ");
			}	
			request.setAttribute("page.mn01.SucMsg",   "농협자료를 삭제하였습니다.");
    }
  }
}