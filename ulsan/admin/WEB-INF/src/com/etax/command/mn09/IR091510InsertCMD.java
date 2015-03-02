/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091510InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > ȸ���ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091510InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091510InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

    	paramInfo.setValue("Napbuja",       request.getParameter("Napbuja")); 
			paramInfo.setValue("Address",       request.getParameter("Address")); 
		
    logger.info(paramInfo);
      
		/* ȸ���ڵ� ��� */
		if(IR091510.insertNapbuja(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}

  	/* �������ּ� ��ȸ */
		List NapbujaList = IR091510.getNapbujaList(conn, paramInfo);
		request.setAttribute("page.mn09.NapbujaList", NapbujaList);

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
