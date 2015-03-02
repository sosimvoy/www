/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR090110SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-19
* ���α׷�����   : �ý��ۿ > ����ڵ��/�����û������ȸ/����
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090110;
import com.etax.entity.CommonEntity;


public class IR090110SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090110SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity();

  	/* ����� ��� ��ȸ */
		List historyList = IR090110.getUserSinList(conn, paramInfo);
		request.setAttribute("page.mn09.historyList", historyList);
	}
}