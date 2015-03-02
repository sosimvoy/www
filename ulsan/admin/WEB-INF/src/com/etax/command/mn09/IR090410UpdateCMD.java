/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR090410UpdateCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-19
* 프로그램내용	 : 시스템운영 > 사용자정보변경신청
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090410;
import com.etax.entity.CommonEntity;

public class IR090410UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090410UpdateCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
			/* 세션 설정 */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
	  
		String SucMsg = "";

		CommonEntity paramInfo = new CommonEntity();
  
		paramInfo.setValue("changePart",        request.getParameter("changePart"));
		paramInfo.setValue("changeWork1",       request.getParameter("changeWork1"));
		paramInfo.setValue("changeSignType",    request.getParameter("changeSignType"));

		if (request.getParameter("part_name").equals(request.getParameter("changePart")) || "".equals(request.getParameter("changePart")) ) {
			paramInfo.setValue("part_name",     "N");
		} else {
			paramInfo.setValue("part_name",     "Y");
		}
		if (request.getParameter("work_name").equals(request.getParameter("changeWork1")) || "".equals(request.getParameter("changeWork1")) ) {
			paramInfo.setValue("work_name",     "N");
		} else {
			paramInfo.setValue("work_name",     "Y");
		}
		if (request.getParameter("sign_name").equals(request.getParameter("changeSignType")) || "".equals(request.getParameter("changeSignType")) ) {
			paramInfo.setValue("sign_name",     "N");
		} else {
			paramInfo.setValue("sign_name",     "Y");
		}
    paramInfo.setValue("user_id",           user_id);

		logger.info("paramInfo : " + paramInfo);
    
			if(IR090410.appUserInfo(conn, paramInfo) < 1 ) {
				throw new ProcessException("E002"); //등록중 오류메시지 표시
			}

			if (!"".equals(SucMsg)) {
		  request.setAttribute("page.mn09.SucMsg", SucMsg);
	  } else {
		  request.setAttribute("page.mn09.SucMsg", "사용자 정보변경 신청이 되었습니다.");
		}
   	/* 사용자 상세*/
		CommonEntity chUserInfo = IR090410.getUpdateUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.chUserInfo", chUserInfo);
	}
} 