/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR040410ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세외수입 > 징수결의 상세
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040410;
import com.etax.entity.CommonEntity;


public class IR040410ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040410ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
    String[] chk_list = request.getParameterValues("userChk");
		String[] seq_list = request.getParameterValues("seq_list");
    
		int chk_val = Integer.parseInt(chk_list[0]);
		paramInfo.setValue("seq",       seq_list[chk_val]);

  	/* 수기분 상세  */
		CommonEntity jingsuView = IR040410.getJingsuView(conn, paramInfo);
		request.setAttribute("page.mn04.jingsuView", jingsuView);
	}
}