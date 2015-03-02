/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091710InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > �μ��ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091710;
import com.etax.entity.CommonEntity;

public class IR091710InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091710InsertCMD.class);// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("queyear",         request.getParameter("queyear"));
	  paramInfo.setValue("year",            request.getParameter("year"));
	  paramInfo.setValue("stdPart",         request.getParameter("stdPart"));
		paramInfo.setValue("stdPartName",     request.getParameter("stdPartName"));
		paramInfo.setValue("sysPartcode",     request.getParameter("sysPartcode"));
	  paramInfo.setValue("incomePart",      request.getParameter("incomePart"));

    logger.info("paramInfo : "+paramInfo);
      
		/* ���� ��� */
		if(IR091710.insertStdpartCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}
    
    //����˾��� ��������
		/* ���form�� ���μ��ڷ� ��ȸ */
		List usePartList = IR091710.getusePartList(conn, paramInfo);
		request.setAttribute("page.mn09.usePartList", usePartList);

		/* ���⵵ ����μ� �ڷ� ��ȸ */
		List IncomepartList = IR091710.getincomePartList(conn, paramInfo);
		request.setAttribute("page.mn09.IncomepartList", IncomepartList);

		List standardpartList = IR091710.getstandardpartkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardpartList", standardpartList);

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
