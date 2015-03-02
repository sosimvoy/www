/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR011010SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ���Ա����� ��ȸ
****************************************************************/
 
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011010;
import com.etax.entity.CommonEntity;


public class IR011010SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR011010SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));	  //ȸ�迬��
		paramInfo.setValue("odate",            request.getParameter("odate"));		  //��������

    logger.info("paramInfo : " + paramInfo);		



  	/* ���Ա����� ����Ʈ */
		List<CommonEntity> expLedgerList = IR011010.getExpLedgerList(conn, paramInfo);
		request.setAttribute("page.mn01.expLedgerList", expLedgerList);
	}
}