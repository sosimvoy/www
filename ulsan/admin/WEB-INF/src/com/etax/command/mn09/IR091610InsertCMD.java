/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR091610InsertCMD.java
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
import com.etax.db.mn09.IR091610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR091610InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091610InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("year",            request.getParameter("year"));
	  paramInfo.setValue("stdAcccode",      request.getParameter("stdAcccode"));
		paramInfo.setValue("stdSemok",        request.getParameter("stdSemok"));
		paramInfo.setValue("stdSemokName",    request.getParameter("stdSemokName"));
	  paramInfo.setValue("sysSemokcode",    request.getParameter("sysSemokcode"));
		paramInfo.setValue("nowincomeSemok",  request.getParameter("nowincomeSemok"));
		paramInfo.setValue("oldincomeSemok",  request.getParameter("oldincomeSemok"));

    if("31".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }

    logger.info("paramInfo : "+paramInfo);
      
		/* 휴일 등록 */
		if(IR091610.insertStdsemokCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}
    
    //등록팝업용 쿼리실행
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
    if("11".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("31".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		/* 등록form의 세목자료 조회 */
		List useSemokList = IR091610.getuseSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.useSemokCdList", useSemokList);

		/* 현년도 예산과목 자료 조회 */
		List nowIncomeList = IR091610.getnowIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.nowincomeSemokCdList", nowIncomeList);

		/* 과년도 예산과목 세목자료 조회 */
		List oldIncomeList = IR091610.getoldIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.oldincomeSemokCdList", oldIncomeList);

		List standardsemokkList = IR091610.getstandardsemokkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardsemokkList", standardsemokkList);
		request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
	}
}
