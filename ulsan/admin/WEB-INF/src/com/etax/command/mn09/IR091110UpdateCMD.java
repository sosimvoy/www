/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR0091110UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-25
* 프로그램내용	 : 시스템운영 > 결재권변경승인신청
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091110UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010UpdateCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
		CommonEntity paramInfo = new CommonEntity();
   
		paramInfo.setValue("chDateIlban",       request.getParameter("chDateIlban"));
		paramInfo.setValue("chDateTekbeyl",     request.getParameter("chDateTekbeyl"));
		paramInfo.setValue("chDateGigeum",      request.getParameter("chDateGigeum"));
		paramInfo.setValue("chDateHyungeum",    request.getParameter("chDateHyungeum"));
		paramInfo.setValue("chDateJeungji",     request.getParameter("chDateJeungji"));
		paramInfo.setValue("chDateJuhaengse",   request.getParameter("chDateJuhaengse"));
		paramInfo.setValue("dateIlban",         TextHandler.replace(request.getParameter("dateIlban"),"-",""));
		paramInfo.setValue("dateTekbeyl",       TextHandler.replace(request.getParameter("dateTekbeyl"),"-",""));
		paramInfo.setValue("dateGigeum",        TextHandler.replace(request.getParameter("dateGigeum"),"-",""));
    paramInfo.setValue("dateHyungeum",      TextHandler.replace(request.getParameter("dateHyungeum"),"-",""));
    paramInfo.setValue("dateJeungji",       TextHandler.replace(request.getParameter("dateJeungji"),"-",""));
    paramInfo.setValue("dateJuhaengse",     TextHandler.replace(request.getParameter("dateJuhaengse"),"-",""));

		if(IR091110.updateEndWorkDate(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002"); //등록중 오류메시지 표시
		}
     
		 if ("".equals(request.getParameter("year")) ) {
			paramInfo.setValue("year", "2010");
		}	else {
			paramInfo.setValue("year", request.getParameter("year"));
		}

   	/* 마감일 상세*/
		CommonEntity endWorkDate = IR091110.getEndWorkDate(conn, paramInfo);
		request.setAttribute("page.mn09.endWorkDate", endWorkDate);

		request.setAttribute("page.mn09.SucMsg",   "수정 되었습니다.");
	}
} 