/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091010InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > ���� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;

public class IR091010InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("hol_date",         request.getParameter("hol_date"));
	  paramInfo.setValue("holName",          request.getParameter("holName"));
		
    logger.info(paramInfo);
      
		/* ���� ��� */
		if(IR091010.getInsertHol(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
