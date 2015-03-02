/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050510ViewCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 계좌등록-수취인조회및 팝업
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
import com.etax.entity.CommonEntity;


public class IR050510ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050510ViewCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("acct_no",            request.getParameter("acct_no"));
		paramInfo.setValue("acct_nm",            request.getParameter("acct_nm"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
		paramInfo.setValue("bank_cd",            request.getParameter("bank_cd"));
		paramInfo.setValue("acc_cd",             request.getParameter("acc_cd"));
		paramInfo.setValue("acc_type",           request.getParameter("acc_type"));
		paramInfo.setValue("acct_gubun",         request.getParameter("acct_gubun"));

		String acct_gubun = request.getParameter("acct_gubun");
    
    logger.info("paramInfo : " + paramInfo);
	  
		CommonEntity acctInfo = IR050510.getAcctInfo(conn, paramInfo);

		if (acctInfo.size() > 0) {
			request.setAttribute("page.mn05.ErrMsg",  "이미 등록된 계좌번호입니다. 다른 계좌번호를 입력하시기 바랍니다.");
		} else {
			CommonEntity acctNm = IR050510.getAcctNM (conn, paramInfo);
			
			if (!"03".equals(acct_gubun) && !"04".equals(acct_gubun) ) { //조회계좌 및 일상경비가 아니면..
				CommonEntity acctCnt  = IR050510.getAcctCnt(conn, paramInfo);
				if (!"0".equals(acctCnt.getString("CNT")) ) {
			  request.setAttribute("page.mn05.ErrMsg",  "회계연도, 회계코드별로 출금, 입금계좌는 하나만 존재합니다. 계좌 삭제후 다시 등록하십시오.");
				}
			}
		}
  }
}