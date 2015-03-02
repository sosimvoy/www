/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR010510Select1CMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ��ϳ��� ��ȸ
****************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR010510;
import com.etax.db.mn01.IR010110;			
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010510Select1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR010510Select1CMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year", request.getParameter("fis_year"));
		}	else {
			paramInfo.setValue("fis_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		if ("".equals(request.getParameter("acc_type")) ) {
	    paramInfo.setValue("acc_type", "A");
		}	else {
			paramInfo.setValue("acc_type", request.getParameter("acc_type"));
		}
		if ("".equals(request.getParameter("part_code")) ) {
	    paramInfo.setValue("part_code", "00000");
		}	else {
			paramInfo.setValue("part_code", request.getParameter("part_code"));
		}
		if ("".equals(request.getParameter("acc_code")) ) {
	    paramInfo.setValue("acc_code", "11");
		}	else {
			paramInfo.setValue("acc_code", request.getParameter("acc_code"));
		}

    logger.info("paramInfo : " + paramInfo);		


		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);

    List<CommonEntity> gigwanList = IR010510.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);

	}
}