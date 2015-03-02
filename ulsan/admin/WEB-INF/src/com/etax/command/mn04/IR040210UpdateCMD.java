/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR040210UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용	 : 세외수입 > 등록내역 수정
******************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;

public class IR040210UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR040210UpdateCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("mok",               request.getParameter("mok"));
		paramInfo.setValue("semok",             request.getParameter("semok"));
		paramInfo.setValue("sogwanpart",        request.getParameter("sogwanpart"));
		paramInfo.setValue("silgwa",            request.getParameter("silgwa"));
		paramInfo.setValue("username",          request.getParameter("username"));
		paramInfo.setValue("intelno",           request.getParameter("intelno"));
		paramInfo.setValue("gwamok",            request.getParameter("gwamok"));	
		paramInfo.setValue("businessname",      request.getParameter("businessname"));
		paramInfo.setValue("dangchoamt",        request.getParameter("dangchoamt"));	
		paramInfo.setValue("chukyngamt",        request.getParameter("chukyngamt"));
		paramInfo.setValue("seq",               request.getParameter("seq"));
		
    logger.info(paramInfo);
      
		/* 수기분 수정 */
		if(IR040210.budgetUpdate(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //수정중 오류메시지 표시
		}

		/* 수기분 상세  */
		CommonEntity budgetView = IR040210.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);

		request.setAttribute("page.mn04.SucMsg", "수정되었습니다.");
	}
}