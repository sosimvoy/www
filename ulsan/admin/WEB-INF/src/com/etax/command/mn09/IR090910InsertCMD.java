/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR090910InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용	 : 시스템운영 > 세목코드 사용 등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090910;
import com.etax.entity.CommonEntity;

public class IR090910InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090910InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
    
	  paramInfo.setValue("year", request.getParameter("year"));
	  paramInfo.setValue("accGubun", request.getParameter("accGubun"));
		paramInfo.setValue("part_code", request.getParameter("part_code"));
		paramInfo.setValue("acc_code", request.getParameter("acc_code"));
    paramInfo.setValue("workGubun", request.getParameter("workGubun"));
		paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));
		paramInfo.setValue("partCode", request.getParameter("partCode"));
    paramInfo.setValue("semok_code", request.getParameter("semok_code"));

    logger.info(paramInfo);
      
		/* 회계코드 등록 */
		if(IR090910.insertUserCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}

    request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
    
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
