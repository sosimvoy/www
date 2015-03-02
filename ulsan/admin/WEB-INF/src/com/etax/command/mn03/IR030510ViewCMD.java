/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030510ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-28
* ���α׷�����   : ���Լ�������� > ���༼ ��ȸ/����/����
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    		
		paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));
		paramInfo.setValue("search_mon",       request.getParameter("end_date").substring(0,6));
		paramInfo.setValue("M060_JINGSUTYPE",  request.getParameter("M060_JINGSUTYPE"));
		String first_date = paramInfo.getString("start_date").substring(0, 6) + "01";
		paramInfo.setValue("first_date",       first_date); 
    

		//�����̿���
		CommonEntity iwalData = IR030510.getIwalData(conn, paramInfo);
		first_date = TextHandler.getBusinessDate(conn, first_date);
		iwalData.setValue("first_date",     first_date);
		request.setAttribute("page.mn03.iwalData",  iwalData);

		/* Ư�� ���༼ ������ */
		List<CommonEntity> spJuheangList = IR030510.getSpSunapList(conn, paramInfo);
		request.setAttribute("page.mn03.spJuheangList", spJuheangList);

		/* Ư�� �߻����� ������ */
		List<CommonEntity> spIjaList = IR030510.getSpIjaList(conn, paramInfo);
		request.setAttribute("page.mn03.spIjaList", spIjaList);  
	}
}