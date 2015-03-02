/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : ManagerUpdateCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-08-04
* ���α׷�����	  : ������ ����
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

	private static Logger logger = Logger.getLogger(ManagerUpdateCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("userid",            request.getParameter("userid")); //�������̵�
		paramInfo.setValue("userpw",            request.getParameter("userpw")); //�н�����
    paramInfo.setValue("serial",            request.getParameter("serial")); //�ø���
		paramInfo.setValue("subjectDN",         request.getParameter("subjectDN")); //�߱޴��

    if (ManagerDAO.AuthUpdate(conn, paramInfo) <1 ) {
      request.setAttribute("page.auth.SucMsg", "��й�ȣ�� Ȯ�ιٶ��ϴ�.");
    } else {
		  request.setAttribute("page.auth.SucMsg", "�������� ���ŵǾ����ϴ�.");
    }
	}
}