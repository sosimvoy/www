/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051410SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입수기분조회
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051410;
import com.etax.entity.CommonEntity;

public class IR051410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051410SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",   request.getParameter("acc_date"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* 잉여금이입수기분 취소 조회 */
		List<CommonEntity> srpSugiCancelList = IR051410.getSrpSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpSugiCancelList", srpSugiCancelList);
  }
}