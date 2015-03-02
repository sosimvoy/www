/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR071310SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ���Ա�������������
****************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071310SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR071310SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("queprtty"   , request.getParameter("queprtty"));
    paramInfo.setValue("quepartCode", request.getParameter("quepartCode"));
    paramInfo.setValue("queyear"    , request.getParameter("queyear"));
    paramInfo.setValue("quemm"      , request.getParameter("quemm"));
    paramInfo.setValue("que_date"   , request.getParameter("que_date"));
    paramInfo.setValue("end_date"   , request.getParameter("end_date"));
    paramInfo.setValue("quetaxgbn"   , request.getParameter("quetaxgbn"));

    logger.info("paramInfo : " + paramInfo);

    if("1".equals(request.getParameter("queprtty"))) {  //�����ϰ�ǥ
		  /* �����ϰ�ǥ ��ȸ */		
	  	List<CommonEntity> budgetList = IR071310.budgetdayprtList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetprtList", budgetList);
    } else {
		  /* ���Կ���ǥ ��ȸ */		
	  	List<CommonEntity> budgetList = IR071310.budgetdaymonthList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetprtList", budgetList);
    }

		/* ����μ� ��ȸ */		
		List<CommonEntity> budgetpartList = IR071310.getbudgetpartList(conn, paramInfo);
		request.setAttribute("page.mn07.budgetpartList", budgetpartList);

    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������
    request.setAttribute("page.mn07.ago_acc_date", ago_acc_date);

	}
}
