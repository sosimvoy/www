/*****************************************************
* ������Ʈ��      : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : IR091610SelectCMD.java
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
import com.etax.db.mn09.IR091610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR091610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR091610SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
    //����˾��� ��������
		paramInfo.setValue("queyear",           request.getParameter("year")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("stdAcccode")); 
    if("31".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		/* ���form�� �����ڷ� ��ȸ */
		List useSemokList = IR091610.getuseSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.useSemokCdList", useSemokList);

		/* ���⵵ ������� �ڷ� ��ȸ */
		List nowIncomeList = IR091610.getnowIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.nowincomeSemokCdList", nowIncomeList);

		/* ���⵵ ������� �����ڷ� ��ȸ */
		List oldIncomeList = IR091610.getoldIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.oldincomeSemokCdList", oldIncomeList);



    //ǥ�ؼ����ڵ� �ڷ� ��ȸ��
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
    if("31".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		logger.info("paramInfo : " + paramInfo);
		List standardsemokkList = IR091610.getstandardsemokkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardsemokkList", standardsemokkList);
	}
}