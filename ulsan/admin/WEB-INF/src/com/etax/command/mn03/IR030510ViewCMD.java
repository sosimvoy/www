/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-28
* 프로그램내용   : 세입세출외현금 > 주행세 조회/수정/삭제
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    		
		paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));
		paramInfo.setValue("search_mon",       request.getParameter("end_date").substring(0,6));
		paramInfo.setValue("M060_JINGSUTYPE",  request.getParameter("M060_JINGSUTYPE"));
		String first_date = paramInfo.getString("start_date").substring(0, 6) + "01";
		paramInfo.setValue("first_date",       first_date); 
    

		//전월이월금
		CommonEntity iwalData = IR030510.getIwalData(conn, paramInfo);
		first_date = TextHandler.getBusinessDate(conn, first_date);
		iwalData.setValue("first_date",     first_date);
		request.setAttribute("page.mn03.iwalData",  iwalData);

		/* 특별 주행세 수납부 */
		List<CommonEntity> spJuheangList = IR030510.getSpSunapList(conn, paramInfo);
		request.setAttribute("page.mn03.spJuheangList", spJuheangList);

		/* 특별 발생이자 수납부 */
		List<CommonEntity> spIjaList = IR030510.getSpIjaList(conn, paramInfo);
		request.setAttribute("page.mn03.spIjaList", spIjaList);  
	}
}