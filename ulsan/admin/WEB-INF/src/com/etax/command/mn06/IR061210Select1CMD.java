/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061210Select1CMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출통지서조회 - 자금인출내역
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061210;
import com.etax.entity.CommonEntity;


public class IR061210Select1CMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061210Select1CMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    paramInfo.setValue("doc_no",           request.getParameter("doc_no"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* 자금인출내역 조회 */
    List<CommonEntity> inchulDetailList = IR061210.getInchulDetailList(conn, paramInfo);
    request.setAttribute("page.mn06.inchulDetailList", inchulDetailList);
		
  }
}