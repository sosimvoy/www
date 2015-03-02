/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR011010SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 세입금정정 조회
****************************************************************/
 
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011010;
import com.etax.entity.CommonEntity;


public class IR011010SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR011010SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));	  //회계연도
		paramInfo.setValue("odate",            request.getParameter("odate"));		  //정정일자

    logger.info("paramInfo : " + paramInfo);		



  	/* 세입금정정 리스트 */
		List<CommonEntity> expLedgerList = IR011010.getExpLedgerList(conn, paramInfo);
		request.setAttribute("page.mn01.expLedgerList", expLedgerList);
	}
}