/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010910SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-01
* ���α׷�����	 : ���� > �����ϰ���ȸ
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR010910;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010910SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010910SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
   
	  CommonEntity paramInfo = new CommonEntity();
		
    String ago_acc_date = TextHandler.getAgoBusinessDate(conn, TextHandler.addDays(TextHandler.getCurrentDate(),-1));  //���ó�¥ ���� ��������
    String fis_date    = request.getParameter("fis_date");		 
		String eight_business_date = TextHandler.getndaybeforeBusinessDate(conn ,fis_date, 7);
	  String part_code  = request.getParameter("part_code");	 //�μ�
	
    if (part_code.equals("99999") || part_code.equals("00110") || part_code.equals("00140") || part_code.equals("00170") || part_code.equals("00200") ||  part_code.equals("00710") )
		{
	   	paramInfo.setValue("fis_year",     request.getParameter("fis_year"));	 //ȸ�迬��
		  paramInfo.setValue("fis_date",     eight_business_date);	 //8������ ȸ������
		}else{
				paramInfo.setValue("fis_year",     request.getParameter("fis_year"));	 //ȸ�迬��
  	    paramInfo.setValue("fis_date",     request.getParameter("fis_date"));	 //ȸ������
		}

		if (!"".equals(request.getParameter("part_year")) ) {
			paramInfo.setValue("part_year", request.getParameter("part_year"));
		}	else {
			paramInfo.setValue("part_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		paramInfo.setValue("part_code", request.getParameter("part_code"));		 //�μ�

    logger.info("paramInfo : " + paramInfo);		


		/* ����Ʈ ��ȸ */
		List<CommonEntity> semokDailyList = IR010910.getSemokDailyList(conn, paramInfo);
		request.setAttribute("page.mn01.semokDailyList", semokDailyList);

    request.setAttribute("page.mn01.ago_acc_date", ago_acc_date);
		 request.setAttribute("page.mn01.eight_business_date", eight_business_date);
	}
}