/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR091710InsertCMD.java
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

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091710;
import com.etax.entity.CommonEntity;

public class IR091710InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091710InsertCMD.class);// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("queyear",         request.getParameter("queyear"));
	  paramInfo.setValue("year",            request.getParameter("year"));
	  paramInfo.setValue("stdPart",         request.getParameter("stdPart"));
		paramInfo.setValue("stdPartName",     request.getParameter("stdPartName"));
		paramInfo.setValue("sysPartcode",     request.getParameter("sysPartcode"));
	  paramInfo.setValue("incomePart",      request.getParameter("incomePart"));

    logger.info("paramInfo : "+paramInfo);
      
		/* 휴일 등록 */
		if(IR091710.insertStdpartCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}
    
    //등록팝업용 쿼리실행
		/* 등록form의 사용부서자료 조회 */
		List usePartList = IR091710.getusePartList(conn, paramInfo);
		request.setAttribute("page.mn09.usePartList", usePartList);

		/* 현년도 예산부서 자료 조회 */
		List IncomepartList = IR091710.getincomePartList(conn, paramInfo);
		request.setAttribute("page.mn09.IncomepartList", IncomepartList);

		List standardpartList = IR091710.getstandardpartkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardpartList", standardpartList);

		request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
	}
}
