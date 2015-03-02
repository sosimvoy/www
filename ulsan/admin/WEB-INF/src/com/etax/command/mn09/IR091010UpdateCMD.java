/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR090610UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용	 : 시스템운영 > 휴일 수정
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;

public class IR091010UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010UpdateCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("hol_date",             request.getParameter("hol_date"));
		paramInfo.setValue("holName",              request.getParameter("holName"));
		paramInfo.setValue("date",                 request.getParameter("date"));

    logger.info(paramInfo);
      
		/* 휴일 수정 */
		if(IR091010.getUpdateHol(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}

		/* 휴일 상세  */
		CommonEntity HolView = IR091010.getHolView(conn, paramInfo);
		request.setAttribute("page.mn09.HolView", HolView);

		request.setAttribute("page.mn09.SucMsg", "수정되었습니다.");
	}
}
