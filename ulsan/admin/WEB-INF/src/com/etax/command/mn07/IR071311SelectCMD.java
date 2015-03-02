/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR071311SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 세입금정정필통지서
****************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071311SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR071311SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("queprtty"   , request.getParameter("queprtty"));
    paramInfo.setValue("quepartCode", request.getParameter("quepartCode"));
    paramInfo.setValue("queyear"    , request.getParameter("queyear"));
    paramInfo.setValue("quemm"      , request.getParameter("quemm"));
    paramInfo.setValue("que_date"   , request.getParameter("que_date"));
    paramInfo.setValue("quetaxgbn"   , request.getParameter("quetaxgbn"));

    logger.info("paramInfo : " + paramInfo);		

    if("1".equals(request.getParameter("queprtty"))) {  //수입일계표
		  /* 수입일계표 조회 */		
	  	List<CommonEntity> budgetList = IR071310.budgetdayexcelList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetexcelList", budgetList);
    }else {
		  /* 수입월계표 조회 */		
	  	List<CommonEntity> budgetList = IR071310.budgetdayexcelmonthList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetexcelList", budgetList);
    }
	}
}
