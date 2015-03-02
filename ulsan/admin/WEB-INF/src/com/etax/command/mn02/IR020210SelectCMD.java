/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020210SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ��ϳ��� ��ȸ
****************************************************************/
 
package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn02.IR020210;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR020210SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR020210SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
		paramInfo.setValue("part_code",        request.getParameter("part_code"));
	  paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
		paramInfo.setValue("intype",           request.getParameter("intype"));

    logger.info("paramInfo : " + paramInfo);		


		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

	  List<CommonEntity> revWriteList = IR020210.getRevWriteList(conn, paramInfo);
		request.setAttribute("page.mn02.revWriteList", revWriteList);

		List<CommonEntity> revDeptList = IR020210.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020210.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 
	}
}