/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR090510ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : �ý��ۿ > �ڵ� ��
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;


public class IR091010ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091010ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
   paramInfo.setValue("hol_date",        request.getParameter("hol_date"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] hol_list = request.getParameterValues("hol_list");
		
		int chk_val = Integer.parseInt(chk_list[0]);
		paramInfo.setValue("hol_date",           hol_list[chk_val]);

			/* ���� ��  */
		CommonEntity HolView = IR091010.getHolView(conn, paramInfo);
		request.setAttribute("page.mn09.HolView", HolView);
	}
}