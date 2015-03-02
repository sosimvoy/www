/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입세출외현금 > 리스트 조회
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030110;
import com.etax.db.mn01.IR010110;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR030110SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030110SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		CommonEntity paramInfo = new CommonEntity();

		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year", request.getParameter("fis_year"));
		}	else {
			paramInfo.setValue("fis_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		if ("".equals(request.getParameter("part_code")) ) {
			paramInfo.setValue("part_code", "00000");
		}	else {
			paramInfo.setValue("part_code", request.getParameter("part_code"));
		}
		
    logger.info("paramInfo : " + paramInfo);		


    /* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date);  

		List<CommonEntity> cashDeptList = IR030110.getCashDeptList(conn, paramInfo);
		request.setAttribute("page.mn03.cashDeptList", cashDeptList);

		List<CommonEntity> accNameList = IR030110.getAccNameList(conn,paramInfo);
    request.setAttribute("page.mn03.accNameList", accNameList); 
		
		List<CommonEntity> accNameList1 = IR030110.getAccNameList(conn,paramInfo);
    request.setAttribute("page.common.acctCdList", accNameList1); 
	}
}