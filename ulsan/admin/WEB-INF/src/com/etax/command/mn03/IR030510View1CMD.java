/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030510ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-28
* ���α׷�����   : ���Լ�������� > ���༼ ��ȸ/����/����
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510View1CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510View1CMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    		
		paramInfo.setValue("year",             request.getParameter("year"));
	  paramInfo.setValue("start_date",        request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

		String choil = paramInfo.getString("start_date").substring(0, 6) + "01";

		choil = TextHandler.getBusinessDate(conn, choil);

    /* �ֵ� ���༼ ������ */
		List<CommonEntity> juJuheangList = IR030510.getJuSunapList(conn, paramInfo);
		request.setAttribute("page.mn03.juJuheangList", juJuheangList);
    
		/* �ֵ� �߻����� ������ */
		List<CommonEntity> juIjaList = IR030510.getJuIjaList(conn, paramInfo);
		request.setAttribute("page.mn03.juIjaList", juIjaList);

		CommonEntity maxSunap = IR030510.getJuMaxSunap(conn, paramInfo);
    maxSunap.setValue("choil",   choil);
		request.setAttribute("page.mn03.maxSunap", maxSunap);
  
	}
}