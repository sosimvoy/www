/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050210SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정승인내역조회/배정처리
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050210;
import com.etax.entity.CommonEntity;


public class IR050210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050210SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* 자금배정승인내역 조회 */
    List<CommonEntity> allotList = IR050210.getAllotList(conn, paramInfo);
    request.setAttribute("page.mn05.allotList", allotList);
		
  }
}