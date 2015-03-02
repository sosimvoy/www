/*****************************************************
* ������Ʈ��		: ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		: IR011210DeleteCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2014-10-23
* ���α׷�����		: ���� > GIRO��� ����
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011210;
import com.etax.entity.CommonEntity;

public class IR011210DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011210DeleteCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("job_dt",           request.getParameter("job_dt"));
		
		logger.debug("paramInfo : " + paramInfo);

		IR011210.deleteGiroDaesa(conn, paramInfo);
		request.setAttribute("page.mn01.SucMsg",   "GIRO��簡 �����Ǿ����ϴ�.");
	}
}