/*****************************************************
* ������Ʈ��      : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : IR091710SelectCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-08-19
* ���α׷�����		: �ý��ۿ > �μ��ڵ����
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091710;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR091710SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091710SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
	  paramInfo.setValue("queyear",         request.getParameter("queyear"));

    //����˾��� ��������
		/* ���form�� ���μ��ڷ� ��ȸ */
		List usePartList = IR091710.getusePartList(conn, paramInfo);
		request.setAttribute("page.mn09.usePartList", usePartList);

		/* ���⵵ ����μ� �ڷ� ��ȸ */
		List IncomepartList = IR091710.getincomePartList(conn, paramInfo);
		request.setAttribute("page.mn09.IncomepartList", IncomepartList);



    //ǥ�ؼ����ڵ� �ڷ� ��ȸ��
		
		List standardpartList = IR091710.getstandardpartkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardpartList", standardpartList);
	}
}