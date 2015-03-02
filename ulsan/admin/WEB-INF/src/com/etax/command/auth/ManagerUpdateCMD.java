/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : ManagerUpdateCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-08-04
* 프로그램내용	  : 인증서 갱신
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;

public class ManagerUpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerUpdateCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("userid",            request.getParameter("userid")); //유저아이디
		paramInfo.setValue("userpw",            request.getParameter("userpw")); //패스워드
    paramInfo.setValue("serial",            request.getParameter("serial")); //시리얼
		paramInfo.setValue("subjectDN",         request.getParameter("subjectDN")); //발급대상

    if (ManagerDAO.AuthUpdate(conn, paramInfo) <1 ) {
      request.setAttribute("page.auth.SucMsg", "비밀번호를 확인바랍니다.");
    } else {
		  request.setAttribute("page.auth.SucMsg", "인증서가 갱신되었습니다.");
    }
	}
}