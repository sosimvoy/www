/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020310SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2012-04-16
* ���α׷�����   : ���� > ������ϰ����(ȸ������ �������Ϸ�)
****************************************************************/
 
package com.etax.command.mn02;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.*;


public class IR020310SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR020310SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
        
        String to_day =  TextHandler.getCurrentDate();
        String b_day = TextHandler.getndaybeforeBusinessDate(conn, to_day, 1);  //��������
        logger.info("b_day : " + b_day);
        request.setAttribute("page.mn02.b_day", b_day);
	}
}