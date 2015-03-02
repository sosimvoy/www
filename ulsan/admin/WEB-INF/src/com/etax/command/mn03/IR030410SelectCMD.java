/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입세출외현금 > 주행세 등록
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;

public class IR030410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030410SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
     
  	String fis_date = TextHandler.getBeforeDate(conn, TextHandler.getCurrentDate());
    request.setAttribute("page.mn03.fis_date",   fis_date);
	}
}