/*****************************************************
* ������Ʈ��		  : ���������� ���������� �ý���
* ���α׷���		  : ManagerLogoutCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2009-12-22
* ���α׷�����	  : �α׾ƿ�
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

	// Connection �� �ʿ���� ���� Command �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		//�α׾ƿ� ó��
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