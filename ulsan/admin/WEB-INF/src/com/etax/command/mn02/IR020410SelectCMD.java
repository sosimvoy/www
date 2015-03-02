/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR020410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2015-02-04
* 프로그램내용   : 세출 > 일괄등록미처리분
****************************************************************/
 
package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.db.mn02.IR020410;
import com.etax.entity.CommonEntity;
import com.etax.framework.command.BaseCommand;

public class IR020410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR020410SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
        
	    CommonEntity paramInfo = new CommonEntity();
	    paramInfo.setValue("acc_date", request.getParameter("acc_date"));
	    paramInfo.setValue("acc_year", request.getParameter("acc_year"));
	    
	    logger.debug("paramInfo : " + paramInfo);
	    
	    List<CommonEntity> misechulList = IR020410.getMiSechulList(conn, paramInfo);
	    request.setAttribute("page.mn02.misechulList", misechulList);
	}
}