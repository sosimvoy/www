/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030410SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���Լ�������� > ���༼ ���
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;

public class IR030410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030410SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
     
  	String fis_date = TextHandler.getBeforeDate(conn, TextHandler.getCurrentDate());
    request.setAttribute("page.mn03.fis_date",   fis_date);
	}
}