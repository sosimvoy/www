/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR071110SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ���Ա�������������
****************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071110SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR071110SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("queyear", request.getParameter("queyear"));
    paramInfo.setValue("que_date", request.getParameter("que_date"));
    paramInfo.setValue("quetaxgbn", request.getParameter("quetaxgbn"));
    paramInfo.setValue("queprocgbn", request.getParameter("queprocgbn"));
    paramInfo.setValue("taxgbn"  , request.getParameter("taxgbn"));
    paramInfo.setValue("mokgubun", request.getParameter("mokgubun"));

    logger.info("paramInfo : " + paramInfo);		

		/* �����ϰ�ǥ ��ȸ */		
		List<CommonEntity> budgetList = IR071110.budgetList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetList", budgetList);

		/* ������� ��ȸ */		
		List<CommonEntity> budgetsemokList = IR071110.getbudgetsemokList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetsemokList", budgetsemokList);

		/* ����μ� ��ȸ */		
		List<CommonEntity> budgetpartList = IR071110.getbudgetpartList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetpartList", budgetpartList);

    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������
    request.setAttribute("page.mn07.ago_acc_date", ago_acc_date);

	}
}
