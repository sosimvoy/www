/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050710SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정수기분등록 - 리스트
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050710;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050710SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050710SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
			paramInfo.setValue("allot_kind",  request.getParameter("allot_kind"));
			paramInfo.setValue("acc_type",    request.getParameter("acc_type"));
      if (!"".equals(request.getParameter("dept_kind"))) { 
        paramInfo.setValue("dept_kind",   request.getParameter("dept_kind"));
      } else {
        paramInfo.setValue("dept_kind",   "00000");
      }
			
		} else {
			paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
			paramInfo.setValue("allot_kind",  "1");
			paramInfo.setValue("acc_type",    "B");
			paramInfo.setValue("dept_kind",   "00000");
		}
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* 부서리스트 */
		List<CommonEntity> deptList = IR050710.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);
		/* 회계리스트 */
		List<CommonEntity> acctList = IR050710.getAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.acctList", acctList);
  }
}