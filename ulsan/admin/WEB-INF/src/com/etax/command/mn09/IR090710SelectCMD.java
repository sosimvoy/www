/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090710.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > ȸ���ڵ��û
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090710;
import com.etax.entity.CommonEntity;

public class IR090710SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090710SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity(); 

    	paramInfo.setValue("under_year",           request.getParameter("under_year")); 
			paramInfo.setValue("accGubun",       request.getParameter("accGubun")); 

  	/* ȸ���ڵ� ��ȸ */
		List accCodeList = IR090710.getAccCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.accCodeList", accCodeList);
	}
}