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


public class IR040210ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
 
    String[] chk_list = request.getParameterValues("userChk");
		String[] seq_list = request.getParameterValues("seq_list");
		String[] year_list = request.getParameterValues("year_list");
    
			int chk_val = Integer.parseInt(chk_list[0]);
			paramInfo.setValue("seq",       seq_list[chk_val]);
		  paramInfo.setValue("year",      year_list[chk_val]);
      
  	/* ����� ��  */
		CommonEntity budgetView = IR040210.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);
	}
}