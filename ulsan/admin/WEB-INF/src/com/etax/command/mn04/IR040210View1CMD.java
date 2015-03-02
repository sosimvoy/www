/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR040210ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���ܼ��� > ��ϳ��� ��
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;


public class IR040210View1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210View1CMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

			paramInfo.setValue("seq",       request.getParameter("seq"));
		  paramInfo.setValue("year",      request.getParameter("year"));
      
  	/* ����� ��  */
		CommonEntity chukyngView = IR040210.getChukyngView(conn, paramInfo);
		request.setAttribute("page.mn04.chukyngView", chukyngView);
	}
}