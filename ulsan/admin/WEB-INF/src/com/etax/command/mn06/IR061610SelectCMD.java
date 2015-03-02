/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061610SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 잔액장자료입력-기타예금 조회
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061610;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR061610SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061610SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",      request.getParameter("reg_date2"));
		String reg_date = request.getParameter("reg_date2");

    logger.info("paramInfo : " + paramInfo);

		if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //영업일 체크
		  request.setAttribute("page.mn06.SucMsg",   "해당일자는 영업일이 아닙니다.");
		} else if (Integer.parseInt(request.getParameter("reg_date2")) > Integer.parseInt(TextHandler.getCurrentDate())) {  //등록일이 오늘을 지났는지 체크
		  request.setAttribute("page.mn06.SucMsg",   "등록일자가 금일이후입니다.");
		} else {

		  CommonEntity etcJanInfo = IR061610.getEtcJanInfo(conn, paramInfo);
		  request.setAttribute("page.mn06.etcJanInfo", etcJanInfo);
		}
  }
}