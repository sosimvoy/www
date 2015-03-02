/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050510SelectCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 계좌등록
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050510;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR050510SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050510SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();

		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		} else {
			paramInfo.setValue("fis_year",         TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}		
    
		if (!"".equals(request.getParameter("acc_type")) ) {
			paramInfo.setValue("acc_type",       request.getParameter("acc_type"));
		} else {
			paramInfo.setValue("acc_type",       "A");
		}
		if (!"".equals(request.getParameter("dept_cd")) ) {
			paramInfo.setValue("dept_cd",       request.getParameter("dept_cd"));
		} else {
			paramInfo.setValue("dept_cd",       "00000");
		}
		paramInfo.setValue("acc_cd",          request.getParameter("acc_cd"));
		paramInfo.setValue("acct_gubun",      request.getParameter("acct_gubun"));
		paramInfo.setValue("bank_cd",         request.getParameter("bank_cd"));
		paramInfo.setValue("juhaeng",         request.getParameter("juhaeng"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		if (!"".equals(request.getParameter("fis_year")) ) {
			List<CommonEntity> acctInfoList = IR050510.getAcctInfoList(conn, paramInfo);
		  request.setAttribute("page.mn05.bankAcctInfoList", acctInfoList);
		}

		/* 부서리스트 */
		List<CommonEntity> deptList = IR050510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);

		/* 회계명 조회 */
		List<CommonEntity> accList = IR050510.getAccList(conn, paramInfo);
		request.setAttribute("page.mn05.accList", accList);
  }
}