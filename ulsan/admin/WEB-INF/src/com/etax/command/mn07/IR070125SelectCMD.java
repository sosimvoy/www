/************************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070125SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Կ���ǥ(�Ѱ������ü�)
*************************************************************************/
 
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
import com.etax.db.mn07.IR070125;
import com.etax.db.common.ReportDAO;    
import com.etax.entity.CommonEntity;

public class IR070125SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070125SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");		 //ȸ�迬��
		String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");		 //ȸ������
		String first_date = acc_date.substring(0,6) + "01";    //������
		String last_date = acc_date.substring(0,6) + TextHandler.getDaysOfMonth(acc_date.substring(0,4), acc_date.substring(4,6));  //������
		String first_business_date = TextHandler.getBusinessDate(conn, first_date);  //���ʿ�����
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_date);   //����������

		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		if( !"last_business_date".equals(request.getParameter("acc_date")) ) {
		  request.setAttribute("page.mn07.SucMsg", "������������ �ƴմϴ�. �ٽ� �����Ͻʽÿ�.");
		}
	
		paramInfo.setValue("acc_year",	acc_year);								// ȸ��⵵
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// ȸ������
		paramInfo.setValue("first_business_date",	first_business_date);		//���ʿ�����
		paramInfo.setValue("last_business_date",	last_business_date);		//����������

    /* ��������	*/
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);

		/* ���� */
  		List<CommonEntity> reportList = IR070125.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);
	
	}
}