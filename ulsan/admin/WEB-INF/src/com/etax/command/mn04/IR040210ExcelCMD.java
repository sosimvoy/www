/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR040210ExcelCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-05
* ���α׷�����   : ���ܼ��� > ���꼭 ��ȸ/����/����(����)
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040210ExcelCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210ExcelCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("year",             request.getParameter("year"));
		paramInfo.setValue("date",             request.getParameter("date"));
		paramInfo.setValue("mok",              request.getParameter("mok"));
		paramInfo.setValue("sogwanpart",       request.getParameter("sogwanpart"));
		paramInfo.setValue("silgwa",           request.getParameter("silgwa"));
		paramInfo.setValue("businessname",     TextHandler.replace(request.getParameter("businessname"), " ", "%"));
		paramInfo.setValue("monthamt",         request.getParameter("monthamt"));
	
  	/*  */
		List<CommonEntity> budgetList = IR040210.getExcelBudgetList(conn, paramInfo);
		request.setAttribute("page.mn04.m080ExcelList", budgetList);
	
	}
}