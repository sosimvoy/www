/*****************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : AccCdListCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-07-14
* 프로그램내용		: 공통 > 회계코드 조회
******************************************************/

package com.etax.command.common;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.command.BaseCommand;
import com.etax.db.common.SelectBox;
import com.etax.entity.CommonEntity;

public class AccCdListCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(AccCdListCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("part_code", request.getParameter("part_code"));

		/* 회계코드 조회 */
		List<CommonEntity> accCdList = SelectBox.getAccNameList(conn,paramInfo);
		request.setAttribute("page.common.acctCdList", accCdList);
	}
}