/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR040210UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���ܼ��� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;

public class IR040210UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR040210UpdateCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("mok",               request.getParameter("mok"));
		paramInfo.setValue("semok",             request.getParameter("semok"));
		paramInfo.setValue("sogwanpart",        request.getParameter("sogwanpart"));
		paramInfo.setValue("silgwa",            request.getParameter("silgwa"));
		paramInfo.setValue("username",          request.getParameter("username"));
		paramInfo.setValue("intelno",           request.getParameter("intelno"));
		paramInfo.setValue("gwamok",            request.getParameter("gwamok"));	
		paramInfo.setValue("businessname",      request.getParameter("businessname"));
		paramInfo.setValue("dangchoamt",        request.getParameter("dangchoamt"));	
		paramInfo.setValue("chukyngamt",        request.getParameter("chukyngamt"));
		paramInfo.setValue("seq",               request.getParameter("seq"));
		
    logger.info(paramInfo);
      
		/* ����� ���� */
		if(IR040210.budgetUpdate(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}

		/* ����� ��  */
		CommonEntity budgetView = IR040210.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);

		request.setAttribute("page.mn04.SucMsg", "�����Ǿ����ϴ�.");
	}
}