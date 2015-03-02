/*********************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR070410SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-02
* ���α׷�����   : �ϰ�/���� > �������� ������ȸ
**********************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070510;
import com.etax.entity.CommonEntity;

public class IR070510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070510SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
   
		paramInfo.setValue("year",           request.getParameter("year")); //��ȸ��������                                 
		paramInfo.setValue("su_sdate",       request.getParameter("su_str_date")); //��ȸ��������
		paramInfo.setValue("su_edate",       request.getParameter("su_end_date"));
		paramInfo.setValue("accGubun",       request.getParameter("accGubun"));
		paramInfo.setValue("accCode",        request.getParameter("accCode"));
	  paramInfo.setValue("partCode",       request.getParameter("partCode"));
		
	  /* ����Ʈ ��ȸ */
		List<CommonEntity> jigupReportList = IR070510.getJigupReportList(conn, paramInfo);
		request.setAttribute("page.mn07.jigupReportList", jigupReportList);	 /* ����Ʈ ��ȸ */

		/* ����ڼ����ڵ� ��ȸ */
		List<CommonEntity> accCdList = IR070510.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList", accCdList); 

		/* ȸ�豸�п� ���� ȸ��� */
		List<CommonEntity> accCdList1 = IR070510.getSemokList1(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList1", accCdList1); 
    
		CommonEntity semokName = IR070510.getSemokName(conn, paramInfo);
    request.setAttribute("page.mn07.semokName", semokName); 		
	}     
}