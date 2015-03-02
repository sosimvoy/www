/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR090110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-19
* 프로그램내용   : 시스템운영 > 사용자등록/변경신청내역조회/승인
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090110;
import com.etax.entity.CommonEntity;


public class IR090110SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090110SelectCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();

  	/* 사용자 등록 조회 */
		List historyList = IR090110.getUserSinList(conn, paramInfo);
		request.setAttribute("page.mn09.historyList", historyList);
	}
}