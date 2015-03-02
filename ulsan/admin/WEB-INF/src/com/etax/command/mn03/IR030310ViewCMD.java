/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입세출외현금 > 주행세 조회/수정/삭제
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030310;
import com.etax.entity.CommonEntity;

public class IR030310ViewCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030310SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

	    paramInfo.setValue("date",       request.getParameter("date"));
	
		List<CommonEntity> stampList = IR030310.getStampView(conn, paramInfo);
		request.setAttribute("page.mn03.stampList", stampList);

        CommonEntity sealState = IR030310.getSealState(conn);
        request.setAttribute("page.mn03.sealState", sealState);
	}
}