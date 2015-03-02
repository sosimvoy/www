/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010110UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-20
* 프로그램내용	 : 시스템운영 > 회계일자등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090310;
import com.etax.entity.CommonEntity;

public class IR090310UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090310UpdateCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
			/* 세션 설정 */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
		
		String SucMsg = "";

		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("username",              request.getParameter("username"));
	  paramInfo.setValue("userpw",                request.getParameter("userpw"));
	  paramInfo.setValue("managerBankerNo",       request.getParameter("managerBankerNo"));
    paramInfo.setValue("managerNo",             request.getParameter("managerNo"));
    paramInfo.setValue("terminalNo",            request.getParameter("terminalNo"));
		paramInfo.setValue("chUsername",            request.getParameter("chUsername"));
		paramInfo.setValue("chUserpw",              request.getParameter("chUserpw"));
		paramInfo.setValue("chManagerBankerNo",     request.getParameter("chManagerBankerNo"));
		paramInfo.setValue("chManagerNo",           request.getParameter("chManagerNo"));
		paramInfo.setValue("chTerminalno",          request.getParameter("chTerminalno"));
    paramInfo.setValue("user_id",               user_id);
    
			if(IR090310.updateUserInfo(conn, paramInfo) < 1 ) {
				throw new ProcessException("E002"); //등록중 오류메시지 표시
			}


			if (!"".equals(SucMsg)) {
		  request.setAttribute("page.mn09.SucMsg", SucMsg);
	  } else {
		  request.setAttribute("page.mn09.SucMsg", "사용자 정보가 수정되었습니다.");
		}
  	/* 사용자 등록 상세 */
		CommonEntity userInfo = IR090310.getUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.userInfo", userInfo);
	}
} 