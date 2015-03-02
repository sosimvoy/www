/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR040210Update2CMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���ܼ��� > ���ɾ� ��
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040210Update2CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040210Update2CMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("seq",                request.getParameter("m080seq"));
		paramInfo.setValue("year",               request.getParameter("m080year"));
		paramInfo.setValue("m080monthamt1",      TextHandler.replace(request.getParameter("m080monthamt1"),",","") );
		paramInfo.setValue("m080monthamt2",      TextHandler.replace(request.getParameter("m080monthamt2"),",",""));
		paramInfo.setValue("m080monthamt3",      TextHandler.replace(request.getParameter("m080monthamt3"),",",""));
		paramInfo.setValue("m080monthamt4",      TextHandler.replace(request.getParameter("m080monthamt4"),",",""));
		paramInfo.setValue("m080monthamt5",      TextHandler.replace(request.getParameter("m080monthamt5"),",",""));
		paramInfo.setValue("m080monthamt6",      TextHandler.replace(request.getParameter("m080monthamt6"),",",""));
		paramInfo.setValue("m080monthamt7",      TextHandler.replace(request.getParameter("m080monthamt7"),",",""));
		paramInfo.setValue("m080monthamt8",      TextHandler.replace(request.getParameter("m080monthamt8"),",",""));
		paramInfo.setValue("m080monthamt9",      TextHandler.replace(request.getParameter("m080monthamt9"),",",""));
		paramInfo.setValue("m080monthamt10",     TextHandler.replace(request.getParameter("m080monthamt10"),",",""));
		paramInfo.setValue("m080monthamt11",     TextHandler.replace(request.getParameter("m080monthamt11"),",",""));
		paramInfo.setValue("m080monthamt12",     TextHandler.replace(request.getParameter("m080monthamt12"),",",""));
		paramInfo.setValue("m080monthamt13",     TextHandler.replace(request.getParameter("m080monthamt13"),",",""));
		paramInfo.setValue("m080monthamt14",     TextHandler.replace(request.getParameter("m080monthamt14"),",",""));
      
		IR040210.updateRevInfo(conn, paramInfo);
		request.setAttribute("page.mn04.SucMsg", "�����Ǿ����ϴ�.");


  	/* ���ɾ� ��  */
		CommonEntity revInfo = IR040210.getRevInfo(conn, paramInfo);
		request.setAttribute("page.mn04.revInfo", revInfo);
	}
}