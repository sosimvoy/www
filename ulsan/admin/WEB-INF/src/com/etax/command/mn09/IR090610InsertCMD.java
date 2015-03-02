/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR090610InsertCMD.java
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
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR090610InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090610InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("year",             request.getParameter("year"));
	  paramInfo.setValue("partCode",         request.getParameter("partCode"));
		paramInfo.setValue("partName",         request.getParameter("partName"));
		paramInfo.setValue("insertYN",         request.getParameter("insertYN"));
	  paramInfo.setValue("reAllotPartYN",    request.getParameter("reAllotPartYN"));
		paramInfo.setValue("reAllotPartCode",  request.getParameter("reAllotPartCode"));
		paramInfo.setValue("receiveName",      request.getParameter("receiveName"));
		paramInfo.setValue("receiveCode",      request.getParameter("receiveCode"));
        paramInfo.setValue("under_year",        request.getParameter("under_year")); 


    logger.info(paramInfo);
      
		/* ���� ��� */
		if(IR090610.insertPartCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}
    
		/* ��������μ��ڵ� */
		List allotPartCodeList = SelectBox.getAllotPartCode(conn);
		request.setAttribute("page.mn09.allotPartCodeList", allotPartCodeList);

		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
