/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070610SelectCMD.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-25
* 프로그램내용	   : 시스템운영 > 결재권변경승인신청
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090910;
import com.etax.entity.CommonEntity;


public class IR090910Select1CMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090910Select1CMD.class);	// log4j 설정
	
    /* (조회할)파라미터 세팅 */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("year", request.getParameter("year"));
	  paramInfo.setValue("accGubun", request.getParameter("accGubun"));
		paramInfo.setValue("part_code", request.getParameter("part_code"));
		paramInfo.setValue("acc_code", request.getParameter("acc_code"));
    paramInfo.setValue("workGubun", request.getParameter("workGubun"));
		paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));
		paramInfo.setValue("partCode", request.getParameter("partCode"));

    logger.info("paramInfo : " + paramInfo);

  	/* 사용자세목코드 조회 */
		List<CommonEntity> deptList = IR090910.getAccGubunDeptList09(conn, paramInfo);
		request.setAttribute("page.mn09.deptList", deptList);	
			
		List<CommonEntity> accCdList = IR090910.getAccCdList092(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> semokList = IR090910.getSemokList091(conn, paramInfo);
    request.setAttribute("page.mn09.semokList", semokList);
	  
		List<CommonEntity> accCdList1 = IR090910.getAccCdList091(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1); 

		List<CommonEntity> userAccDeptList1 = IR090910.getAccGubunDeptList091(conn, paramInfo);
		request.setAttribute("page.mn09.userAccDeptList1", userAccDeptList1);	
	}
}