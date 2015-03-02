/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060510SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁통지서조회
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060510;
import com.etax.entity.CommonEntity;


public class IR060510SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060510SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* 자금예탁요구내역 조회 */
    List<CommonEntity> bankDepReportList = IR060510.getBankDepReportList(conn, paramInfo);
    request.setAttribute("page.mn06.bankDepReportList", bankDepReportList);
		
  }
}