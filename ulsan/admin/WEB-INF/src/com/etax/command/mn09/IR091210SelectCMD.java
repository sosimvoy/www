/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091210SelectCMD.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-25
* ���α׷�����	   : �ý��ۿ > �����ڵ���
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091210;
import com.etax.entity.CommonEntity;


public class IR091210SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR091210SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 

			paramInfo.setValue("year", request.getParameter("year"));

  	/* ���� �ڵ� �Ѱ��� ��ȸ */

		List endYearCode = IR091210.getEndYearCode(conn, paramInfo);
		request.setAttribute("page.mn09.endYearCode", endYearCode);
	}
}