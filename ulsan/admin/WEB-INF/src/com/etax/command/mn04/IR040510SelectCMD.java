/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR020710SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-09
* 프로그램내용   : 세외수입 > 과오납결의 조회/삭제
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040510;
import com.etax.entity.CommonEntity;


public class IR040510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040510SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //조회시작일자
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //조회종료일자
	
  	/* 징수결의 조회 */
		List<CommonEntity> gwaonapList = IR040510.getGwaonapList(conn, paramInfo);
		request.setAttribute("page.mn04.gwaonapList", gwaonapList);
	
	}
}