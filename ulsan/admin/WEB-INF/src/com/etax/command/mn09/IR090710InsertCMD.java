/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR090710InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > ȸ���ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090710;
import com.etax.entity.CommonEntity;

public class IR090710InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090710InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

      paramInfo.setValue("under_year",           request.getParameter("under_year")); 
	  paramInfo.setValue("year",             request.getParameter("year"));
		paramInfo.setValue("accGubun1",         request.getParameter("accGubun1"));
	  paramInfo.setValue("accCode",          request.getParameter("accCode"));
		paramInfo.setValue("accName",          request.getParameter("accName"));
		
    logger.info(paramInfo);
      
		/* ȸ���ڵ� ��� */
		if(IR090710.insertAccCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
