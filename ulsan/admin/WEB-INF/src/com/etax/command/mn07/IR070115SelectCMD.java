/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070115SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���ܼ����ϰ�ǥ
****************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;
import com.etax.util.StringUtil;
import com.etax.db.mn07.IR070115;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;

public class IR070115SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070115SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");
		String acc_date = request.getParameter("acc_date");
		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		paramInfo.setValue("acc_year",	acc_year);								// ȸ��⵵
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// ȸ������
		paramInfo.setValue("acc_type",	request.getParameter("acc_type"));		// ȸ�豸��
		paramInfo.setValue("part_code",	request.getParameter("part_code"));		// �μ��ڵ�
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// ȸ���ڵ�
		
        // ��������	
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);
		// ����
  		List<CommonEntity> reportList = IR070115.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);
	
	}
}