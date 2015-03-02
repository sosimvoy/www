/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR090610InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용	 : 시스템운영 > 부서코드 등록
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
import com.etax.db.mn09.IR090610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR090610InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090610InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("year",             request.getParameter("year"));
	  paramInfo.setValue("partCode",         request.getParameter("partCode"));
		paramInfo.setValue("partName",         request.getParameter("partName"));
		paramInfo.setValue("insertYN",         request.getParameter("insertYN"));
	  paramInfo.setValue("reAllotPartYN",    request.getParameter("reAllotPartYN"));
		paramInfo.setValue("reAllotPartCode",  request.getParameter("reAllotPartCode"));
		paramInfo.setValue("receiveName",      request.getParameter("receiveName"));
		paramInfo.setValue("receiveCode",      request.getParameter("receiveCode"));
        paramInfo.setValue("under_year",        request.getParameter("under_year")); 


    logger.info(paramInfo);
      
		/* 휴일 등록 */
		if(IR090610.insertPartCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}
    
		/* 재배정원부서코드 */
		List allotPartCodeList = SelectBox.getAllotPartCode(conn);
		request.setAttribute("page.mn09.allotPartCodeList", allotPartCodeList);

		request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
	}
}
