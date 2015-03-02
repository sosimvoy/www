/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090810SelectCMD.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-20
* 프로그램내용	   : 시스템운영 > 세목코드관리
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR090810SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090810SelectCMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 
    
			paramInfo.setValue("under_year",      request.getParameter("under_year")); 
			paramInfo.setValue("accGubun1",       request.getParameter("accGubun1")); 
			paramInfo.setValue("accCode",         request.getParameter("accCode")); 
   
	 
		/* 세목코드 조회 */
		List semokCodeList = IR090810.getbudgetSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.semokCodeList", semokCodeList);

		/* 사용자세목코드 조회 */
		List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1);
	}
}