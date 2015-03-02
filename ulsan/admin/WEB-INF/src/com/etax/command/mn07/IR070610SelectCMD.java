/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070610SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-14
* ���α׷�����   : �ϰ躸�� >�����ڷ���ȸ
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
import com.etax.db.mn07.IR070610;
import com.etax.entity.CommonEntity;


public class IR070610SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070610SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");
		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}

		paramInfo.setValue("acc_year",	  acc_year);
		paramInfo.setValue("acc_sdate",	  TextHandler.replace(request.getParameter("acc_sdate"),"-",""));
		paramInfo.setValue("acc_edate",	  TextHandler.replace(request.getParameter("acc_edate"),"-",""));
		
		// �����ڵ�
		List<CommonEntity> dataList = IR070610.getDataList(conn, paramInfo);
		request.setAttribute("page.mn07.dataList", dataList);
	}
}