/************************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070131SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > �������ǥ(ȸ�躰)
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
import com.etax.db.mn07.IR070131;
import com.etax.db.common.ReportDAO;   
import com.etax.entity.CommonEntity;

public class IR070131SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070131SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		/* ��ȸ�� ������ ���� ���ϱ� */
		int cpage = 1;
		if( !"".equals(request.getParameter("cpage")) ) {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		}

		/*int fromRow = (cpage-1) * 10 ;
		int toRow = cpage * 10;*/
		//int fromRow = (cpage-1) * ETaxConfig.getInt("page_cnt");
		//int toRow = cpage * ETaxConfig.getInt("page_cnt");

		int page_cnt = 5;
		int fromRow = (cpage-1) * page_cnt;
		int toRow = cpage * page_cnt;

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
		paramInfo.setValue("acc_type",	request.getParameter("acc_type"));		// ȸ�豸��
		paramInfo.setValue("part_code",	request.getParameter("part_code"));		// �μ��ڵ�
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// ȸ���ڵ�	 
		paramInfo.setValue("first_business_date",	first_business_date);		//���ʿ�����
		paramInfo.setValue("last_business_date",	last_business_date);		//����������
		paramInfo.setValue("fromRow",     fromRow + "");
		paramInfo.setValue("toRow",       toRow + "");

    /* ��������	*/
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);

		/* ���� */
  		List<CommonEntity> reportList = IR070131.getReportList(conn, paramInfo);
		request.setAttribute("page.mn07.reportList", reportList);

		/* �� ī��Ʈ ��ȸ */
		CommonEntity rowData = IR070131.getTotalCount(conn, paramInfo);
		request.setAttribute("page.mn07.totalCount", new Integer(rowData.getInt("CNT")));
	
	}
}