/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR010710SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ��ϳ��� ��ȸ
****************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;																				 
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010710SelectCMD extends BaseCommand {
	
  private static Logger logger = Logger.getLogger(IR010710SelectCMD.class);	// log4j ����
	
  public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
  String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������

		
  request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);
	}
}