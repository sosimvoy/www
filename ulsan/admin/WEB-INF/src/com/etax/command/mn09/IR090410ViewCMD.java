/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090410ViewCMD.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-25
* 프로그램내용	   : 시스템운영 > 사용자변경신청
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090410;
import com.etax.entity.CommonEntity;


public class IR090410ViewCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090410ViewCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		
		/* 세션 설정 */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
	  String user_name = (String)session.getAttribute("session.user_name");
                                                                                                                                           
		CommonEntity paramInfo = new CommonEntity(); 
     paramInfo.setValue("user_id",           user_id);

  	/* 가상계좌(일별) 조회 */   
		CommonEntity chUserInfo = IR090410.getUpdateUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.chUserInfo", chUserInfo);
	
	}
}