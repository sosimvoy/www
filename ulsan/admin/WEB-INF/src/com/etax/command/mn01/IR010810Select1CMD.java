/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010810Select1CMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세입 > 
******************************************************/
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010810;
import com.etax.entity.CommonEntity;


public class IR010810Select1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR010810Select1CMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("acc_code",       request.getParameter("acc_code"));	
		paramInfo.setValue("part_code",      request.getParameter("part_code"));	

		if ("31".equals(request.getParameter("acc_code")) ) {
			paramInfo.setValue("acc_type",          "11");
		}	else if ("51".equals(request.getParameter("acc_code")) ) {
			paramInfo.setValue("acc_type",          "31");
		}
		
		logger.info("paramInfo : " + paramInfo);
		
		/* 세목명 조회 */
		List<CommonEntity> semokList = IR010810.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList); 

		/*부서 조회 */
		List<CommonEntity> partList = IR010810.getPartList(conn, paramInfo);
    request.setAttribute("page.mn01.partList", partList); 
	}
}