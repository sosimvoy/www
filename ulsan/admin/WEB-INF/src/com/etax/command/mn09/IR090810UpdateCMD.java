/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR090810UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-21
* 프로그램내용   : 시스템운영 > 세목코드 수정
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;

public class IR090810UpdateCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR090810UpdateCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
	    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("semokName",         request.getParameter("semokName"));
		paramInfo.setValue("segumGubun",        request.getParameter("segumGubun"));	
		paramInfo.setValue("year",              request.getParameter("year"));
		paramInfo.setValue("accGubun",          request.getParameter("accGubun"));
		paramInfo.setValue("accCode",           request.getParameter("accCode"));
		paramInfo.setValue("workGubun",         request.getParameter("workGubun"));
		paramInfo.setValue("semokCode",         request.getParameter("semokCode"));
		paramInfo.setValue("mokGubun",          request.getParameter("mokGubun"));

		if (IR090810.updateSemokCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E003"); //삭제중 오류메시지 표시
		}
		request.setAttribute("page.mn09.SucMsg", "수정되었습니다.");

		/* 세목코드 조회 */
		CommonEntity semokData = IR090810.getSemokData(conn, paramInfo);
		request.setAttribute("page.mn09.semokData", semokData);
    }
}
