/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR091110SelectCMD.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-25
* 프로그램내용	   : 시스템운영 > 마감일등록
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091110;
import com.etax.entity.CommonEntity;


public class IR091110ViewCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR091110ViewCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 
   
		if ("".equals(request.getParameter("year")) ) {
			paramInfo.setValue("year", "2010");
		}	else {
			paramInfo.setValue("year", request.getParameter("year"));
		}
   
  	/* 가상계좌(일별) 조회 */
		CommonEntity endWorkDate = IR091110.getEndWorkDate(conn, paramInfo);
		request.setAttribute("page.mn09.endWorkDate", endWorkDate);
	
	}
}