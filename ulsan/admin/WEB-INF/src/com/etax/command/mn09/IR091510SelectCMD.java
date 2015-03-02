/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091510.jsp
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
import com.etax.db.mn09.IR091510;
import com.etax.entity.CommonEntity;

public class IR091510SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR091510SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		CommonEntity paramInfo = new CommonEntity(); 

    	paramInfo.setValue("Napbuja",       request.getParameter("Napbuja")); 
			paramInfo.setValue("Address",       request.getParameter("Address")); 

  	/* �������ּ� ��ȸ */
		List NapbujaList = IR091510.getNapbujaList(conn, paramInfo);
		request.setAttribute("page.mn09.NapbujaList", NapbujaList);
	}
}