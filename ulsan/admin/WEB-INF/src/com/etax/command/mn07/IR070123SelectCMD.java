/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070123SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Լ�������� �ϰ�ǥ
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
import com.etax.db.mn07.IR070123;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;

public class IR070123SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070123SelectCMD.class);	// log4j ����
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

        /* ��ȸ�� ������ ���� ���ϱ� */
		int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		int page_cnt = 5;
		int fromRow = (cpage-1) * page_cnt;
		int toRow = cpage * page_cnt;

	  paramInfo.setValue("acc_year",	acc_year);		
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// ȸ������
    paramInfo.setValue("part_code",	request.getParameter("part_code"));		// �μ��ڵ�
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// ȸ���ڵ�
    paramInfo.setValue("fromRow",   fromRow + "");
		paramInfo.setValue("toRow",     toRow + "");
		
        
        // ��������	
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);
		
		// ����
  	    List<CommonEntity> reportList = IR070123.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);
	
		/* �� ī��Ʈ ��ȸ */
		CommonEntity rowData = IR070123.getTotalCount(conn, paramInfo);
		request.setAttribute("page.mn07.totalCount", new Integer(rowData.getInt("CNT")));
	}
}