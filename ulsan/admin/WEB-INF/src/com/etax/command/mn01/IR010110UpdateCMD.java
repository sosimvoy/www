/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010110UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-01
* 프로그램내용	 : 세입 > 회계일자등록
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR010110UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010110UpdateCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_date", request.getParameter("fis_date"));
   
    logger.info("paramInfo : " + paramInfo);		

		if(IR010110.updateFisDate(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002"); //등록중 오류메시지 표시
		}
	}
} 