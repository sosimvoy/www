/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-28
* 프로그램내용   : 세입세출외현금 > 주행세 조회/수정/삭제
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510View1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510View1CMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    		
		paramInfo.setValue("year",             request.getParameter("year"));
	  paramInfo.setValue("start_date",        request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

		String choil = paramInfo.getString("start_date").substring(0, 6) + "01";

		choil = TextHandler.getBusinessDate(conn, choil);

    /* 주된 주행세 수납부 */
		List<CommonEntity> juJuheangList = IR030510.getJuSunapList(conn, paramInfo);
		request.setAttribute("page.mn03.juJuheangList", juJuheangList);
    
		/* 주된 발생이자 수납부 */
		List<CommonEntity> juIjaList = IR030510.getJuIjaList(conn, paramInfo);
		request.setAttribute("page.mn03.juIjaList", juIjaList);

		CommonEntity maxSunap = IR030510.getJuMaxSunap(conn, paramInfo);
    maxSunap.setValue("choil",   choil);
		request.setAttribute("page.mn03.maxSunap", maxSunap);
  
	}
}