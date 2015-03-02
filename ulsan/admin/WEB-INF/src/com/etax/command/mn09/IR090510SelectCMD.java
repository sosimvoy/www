/*****************************************************
* ������Ʈ��      : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : IR090610SelectCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-07-13
* ���α׷�����		: �ý��ۿ > �αװ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090510;
import com.etax.entity.CommonEntity;

public class IR090510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR090510SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //��ȸ��������
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //��ȸ��������
		paramInfo.setValue("user_name",       request.getParameter("user_name"));   //����ڸ�
	
	
		/* �������(����) ��ȸ */
		List logList = IR090510.getLogList(conn, paramInfo);
		request.setAttribute("page.mn09.logList", logList);
	}
}