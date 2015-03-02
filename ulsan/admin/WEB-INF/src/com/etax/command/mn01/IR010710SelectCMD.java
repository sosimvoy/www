/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR010710SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 등록내역 조회
****************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;																				 
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010710SelectCMD extends BaseCommand {
	
  private static Logger logger = Logger.getLogger(IR010710SelectCMD.class);	// log4j 설정
	
  public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
  String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //오늘날짜 기준 전영업일

		
  request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);
	}
}