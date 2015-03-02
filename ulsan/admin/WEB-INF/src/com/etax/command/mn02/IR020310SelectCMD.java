/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR020310SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2012-04-16
* 프로그램내용   : 세출 > 수기분일괄등록(회계일자 전영업일로)
****************************************************************/
 
package com.etax.command.mn02;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.*;


public class IR020310SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR020310SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
        
        String to_day =  TextHandler.getCurrentDate();
        String b_day = TextHandler.getndaybeforeBusinessDate(conn, to_day, 1);  //전영업일
        logger.info("b_day : " + b_day);
        request.setAttribute("page.mn02.b_day", b_day);
	}
}