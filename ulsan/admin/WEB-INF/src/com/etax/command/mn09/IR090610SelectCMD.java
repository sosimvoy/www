/*****************************************************
* ������Ʈ��      : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : IR090610SelectCMD.java
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
import com.etax.db.mn09.IR090610;
import com.etax.entity.CommonEntity;
import com.etax.util.*;

public class IR090610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR090610SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

        String year = request.getParameter("year");

        if (year == null || "".equals(year) ) {
            year = TextHandler.getCurrentDate().substring(0,4);
        }
		
		paramInfo.setValue("under_year",     year); 

		/* �μ��ڵ� ��ȸ */
		List partCodeList = IR090610.getPartCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.partCodeList", partCodeList);
   
		/* ��������μ��ڵ� */
		List allotPartCodeList = IR090610.getAllotPartCode(conn, paramInfo);
		request.setAttribute("page.mn09.allotPartCodeList", allotPartCodeList);
		
	}
}