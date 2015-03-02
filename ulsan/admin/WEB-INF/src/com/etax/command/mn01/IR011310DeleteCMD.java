/*****************************************************
* ������Ʈ��		: ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���		: IR011310DeleteCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2014-11-19
* ���α׷�����		: ���� > �����ϰ����
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011310;
import com.etax.entity.CommonEntity;

public class IR011310DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011310DeleteCMD.class);// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("ac_year",           request.getParameter("ac_year"));
		paramInfo.setValue("ac_date",           request.getParameter("ac_date").replace("-", ""));
		
		logger.debug("paramInfo : " + paramInfo);
		
		IR011310.deleteCarDailyData(conn, paramInfo);
        List<CommonEntity> carDailyList = IR011310.getCarDailyList(conn, paramInfo);
        request.setAttribute("page.mn01.carDailyList", carDailyList);
        
        request.setAttribute("page.mn01.SucMsg",  "����ó���Ǿ����ϴ�.");
	}
}