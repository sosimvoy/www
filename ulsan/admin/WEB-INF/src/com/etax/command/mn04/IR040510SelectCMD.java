/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020710SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-09
* ���α׷�����   : ���ܼ��� > ���������� ��ȸ/����
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040510;
import com.etax.entity.CommonEntity;


public class IR040510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040510SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //��ȸ��������
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //��ȸ��������
	
  	/* ¡������ ��ȸ */
		List<CommonEntity> gwaonapList = IR040510.getGwaonapList(conn, paramInfo);
		request.setAttribute("page.mn04.gwaonapList", gwaonapList);
	
	}
}