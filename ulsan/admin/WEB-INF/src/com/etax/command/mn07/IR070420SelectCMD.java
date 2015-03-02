/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070110SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-09
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ (�ڵ�)
****************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070420;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;


public class IR070420SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070420SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();      
		paramInfo.setValue("acc_year",	    request.getParameter("acc_year"));
		paramInfo.setValue("acc_date",		request.getParameter("acc_date"));
        paramInfo.setValue("report_gubun",  request.getParameter("report_gubun"));

		// ���Ϻ������
  		CommonEntity bigoData = IR070420.getBigoData(conn, paramInfo);
		request.setAttribute("page.mn07.bigoData", bigoData);

        CommonEntity ilgyeData = new CommonEntity();  //�ϰ赥���� �ʱ�ȭ
        
        if ("1".equals(request.getParameter("report_gubun"))) {
            // �Ϲ�ȸ���ϰ赥����
  		    ilgyeData = IR070420.getIlgyeData(conn, paramInfo);
        } else if ("2".equals(request.getParameter("report_gubun"))) {
            // ����������ϰ赥����
  		    ilgyeData = IR070420.getCargyeData(conn, paramInfo);
        }  
        request.setAttribute("page.mn07.ilgyeData", ilgyeData);

        //���ε�����
        CommonEntity signData = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.signData", signData);
	
	}
}