/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR040210Update2CMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세외수입 > 수령액 상세
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040210Update2CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210Update2CMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("seq",                request.getParameter("m080seq"));
		paramInfo.setValue("year",               request.getParameter("m080year"));
		paramInfo.setValue("m080monthamt1",      TextHandler.replace(request.getParameter("m080monthamt1"),",","") );
		paramInfo.setValue("m080monthamt2",      TextHandler.replace(request.getParameter("m080monthamt2"),",",""));
		paramInfo.setValue("m080monthamt3",      TextHandler.replace(request.getParameter("m080monthamt3"),",",""));
		paramInfo.setValue("m080monthamt4",      TextHandler.replace(request.getParameter("m080monthamt4"),",",""));
		paramInfo.setValue("m080monthamt5",      TextHandler.replace(request.getParameter("m080monthamt5"),",",""));
		paramInfo.setValue("m080monthamt6",      TextHandler.replace(request.getParameter("m080monthamt6"),",",""));
		paramInfo.setValue("m080monthamt7",      TextHandler.replace(request.getParameter("m080monthamt7"),",",""));
		paramInfo.setValue("m080monthamt8",      TextHandler.replace(request.getParameter("m080monthamt8"),",",""));
		paramInfo.setValue("m080monthamt9",      TextHandler.replace(request.getParameter("m080monthamt9"),",",""));
		paramInfo.setValue("m080monthamt10",     TextHandler.replace(request.getParameter("m080monthamt10"),",",""));
		paramInfo.setValue("m080monthamt11",     TextHandler.replace(request.getParameter("m080monthamt11"),",",""));
		paramInfo.setValue("m080monthamt12",     TextHandler.replace(request.getParameter("m080monthamt12"),",",""));
		paramInfo.setValue("m080monthamt13",     TextHandler.replace(request.getParameter("m080monthamt13"),",",""));
		paramInfo.setValue("m080monthamt14",     TextHandler.replace(request.getParameter("m080monthamt14"),",",""));
      
		IR040210.updateRevInfo(conn, paramInfo);
		request.setAttribute("page.mn04.SucMsg", "수정되었습니다.");


  	/* 수령액 상세  */
		CommonEntity revInfo = IR040210.getRevInfo(conn, paramInfo);
		request.setAttribute("page.mn04.revInfo", revInfo);
	}
}