/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030210SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���Լ�������� > ��ϳ��� ��ȸ
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030210;
import com.etax.db.mn01.IR010110;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;


public class IR030210SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030210SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
		paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));

    logger.info("paramInfo : " + paramInfo);		


  	/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> cashList = IR030210.getCashList(conn, paramInfo);
		request.setAttribute("page.mn03.cashList", cashList);

		List<CommonEntity> cashDeptList = IR030210.getCashDeptList(conn, paramInfo);
		request.setAttribute("page.mn03.cashDeptList", cashDeptList);

		List<CommonEntity> accNameList = IR030210.getAccNameList(conn,paramInfo);
    request.setAttribute("page.mn03.accNameList", accNameList); 
	}
}