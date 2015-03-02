/************************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070710SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���������հ�ǥ��ȸ
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
import com.etax.db.mn07.IR070710;
import com.etax.entity.CommonEntity;

public class IR070710SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070710SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		String acc_year = request.getParameter("acc_year");		 //ȸ�迬��
    String acc_month = request.getParameter("acc_month");  //ȸ���

		String first_date = acc_year + acc_month + "01";    //������		
		String last_date = acc_year + acc_month + TextHandler.getDaysOfMonth(acc_year, acc_month);  //������
    String last_day = TextHandler.getDaysOfMonth(acc_year, acc_month);  //����������

		String first_business_date = TextHandler.getBusinessDate(conn, first_date);  //���ʿ�����
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_date);   //����������

		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}

    if(acc_month.equals("") || acc_month == null){
			Date date = new Date();
			acc_month = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","MM");
		}

		paramInfo.setValue("acc_year",	acc_year);							        	// ȸ��⵵
		paramInfo.setValue("acc_month",	acc_month);	                      // ȸ���
		paramInfo.setValue("first_business_date",	first_business_date);		//���ʿ�����
		paramInfo.setValue("last_business_date",	last_business_date);		//����������   
		paramInfo.setValue("first_date",	first_date);		                //����
		paramInfo.setValue("last_day",	last_day);		                    //����


    /* �������� ���հ�ǥ */
  	List<CommonEntity> taxTotal = IR070710.getTaxTotal(conn, paramInfo);
		request.setAttribute("page.mn07.taxTotal", taxTotal);

    /* ���Ա� ��ü�� ������ */
  	List<CommonEntity> taxTransfer  = IR070710.getTaxTransfer(conn, paramInfo);
		request.setAttribute("page.mn07.taxTransfer", taxTransfer);

    request.setAttribute("page.mn07.first_date", first_date);
    request.setAttribute("page.mn07.last_date", last_date);
	}
}