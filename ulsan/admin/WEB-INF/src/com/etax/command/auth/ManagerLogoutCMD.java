/*****************************************************
* 프로젝트명		  : 대전광역시 세정관리자 시스템
* 프로그램명		  : ManagerLogoutCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2009-12-22
* 프로그램내용	  : 로그아웃
******************************************************/

package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;

public class ManagerLogoutCMD extends BaseCommand {

	// Connection 이 필요없는 경우는 Command 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		//로그아웃 처리
		HttpSession session = request.getSession(false);
    
		if( session != null ) {
			session.removeAttribute("session.user_id");
			session.removeAttribute("session.user_name");
			session.removeAttribute("session.user_pw");
			session.removeAttribute("session.user_ip");
			session.invalidate();
    }
	}

}