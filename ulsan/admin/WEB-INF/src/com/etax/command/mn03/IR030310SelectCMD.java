/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030310SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-02
* ���α׷�����   : ���Լ�������� > �������� ��ȸ/����
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030310;
import com.etax.entity.CommonEntity;


public class IR030310SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030310SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("year",             request.getParameter("date").substring(0,4));
	  paramInfo.setValue("date",             request.getParameter("date"));
	
  	/* �������� ������Ȳ  */
		List<CommonEntity> stampList = IR030310.getStampList(conn, paramInfo);
		request.setAttribute("page.mn03.stampList", stampList);
	}     
}