/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR020210SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-05
* 프로그램내용   : 세외수입 > 등록내역 조회/수정/삭제
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040410;
import com.etax.entity.CommonEntity;


public class IR040410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040410SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("frombaluiDate",         request.getParameter("frombaluiDate"));
		paramInfo.setValue("tobaluiDate",           request.getParameter("tobaluiDate"));
    paramInfo.setValue("fis_year",              request.getParameter("fis_year"));

  	/* 징수결의 조회 */
		List<CommonEntity> jingsuList = IR040410.getJingsuList(conn, paramInfo);
		request.setAttribute("page.mn04.jingsuList", jingsuList);
	
	}
}