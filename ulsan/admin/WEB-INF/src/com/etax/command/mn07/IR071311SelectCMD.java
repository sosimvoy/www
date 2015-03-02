/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR071311SelectCMD.java
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
import com.etax.db.mn07.IR071310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071311SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR071311SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("queprtty"   , request.getParameter("queprtty"));
    paramInfo.setValue("quepartCode", request.getParameter("quepartCode"));
    paramInfo.setValue("queyear"    , request.getParameter("queyear"));
    paramInfo.setValue("quemm"      , request.getParameter("quemm"));
    paramInfo.setValue("que_date"   , request.getParameter("que_date"));
    paramInfo.setValue("quetaxgbn"   , request.getParameter("quetaxgbn"));

    logger.info("paramInfo : " + paramInfo);		

    if("1".equals(request.getParameter("queprtty"))) {  //�����ϰ�ǥ
		  /* �����ϰ�ǥ ��ȸ */		
	  	List<CommonEntity> budgetList = IR071310.budgetdayexcelList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetexcelList", budgetList);
    }else {
		  /* ���Կ���ǥ ��ȸ */		
	  	List<CommonEntity> budgetList = IR071310.budgetdayexcelmonthList(conn, paramInfo);
		  request.setAttribute("page.mn07.budgetexcelList", budgetList);
    }
	}
}
