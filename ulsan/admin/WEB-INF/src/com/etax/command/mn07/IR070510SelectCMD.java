/*********************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR070410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-02
* 프로그램내용   : 일계/보고서 > 지급증명서 내역조회
**********************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070510;
import com.etax.entity.CommonEntity;

public class IR070510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070510SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
   
		paramInfo.setValue("year",           request.getParameter("year")); //조회시작일자                                 
		paramInfo.setValue("su_sdate",       request.getParameter("su_str_date")); //조회시작일자
		paramInfo.setValue("su_edate",       request.getParameter("su_end_date"));
		paramInfo.setValue("accGubun",       request.getParameter("accGubun"));
		paramInfo.setValue("accCode",        request.getParameter("accCode"));
	  paramInfo.setValue("partCode",       request.getParameter("partCode"));
		
	  /* 리스트 조회 */
		List<CommonEntity> jigupReportList = IR070510.getJigupReportList(conn, paramInfo);
		request.setAttribute("page.mn07.jigupReportList", jigupReportList);	 /* 리스트 조회 */

		/* 사용자세목코드 조회 */
		List<CommonEntity> accCdList = IR070510.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList", accCdList); 

		/* 회계구분에 따른 회계명 */
		List<CommonEntity> accCdList1 = IR070510.getSemokList1(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList1", accCdList1); 
    
		CommonEntity semokName = IR070510.getSemokName(conn, paramInfo);
    request.setAttribute("page.mn07.semokName", semokName); 		
	}     
}