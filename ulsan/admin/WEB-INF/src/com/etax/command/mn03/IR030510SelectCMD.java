/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030510SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���Լ�������� > ���༼ ��ȸ/����/����
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

    if (!"".equals(request.getParameter("start_date")) ) {
      paramInfo.setValue("start_date",       request.getParameter("start_date"));
    } else {
	    paramInfo.setValue("start_date",       TextHandler.getCurrentDate());
    }
		paramInfo.setValue("end_date",         request.getParameter("end_date"));
		paramInfo.setValue("napseja",          request.getParameter("napseja"));
    String fis_date = TextHandler.getBeforeDate(conn, TextHandler.getCurrentDate());
    request.setAttribute("page.mn03.fis_date",   fis_date);

    logger.info("paramInfo : " + paramInfo);
	  
    if (Integer.parseInt(paramInfo.getString("start_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  
      //������� ������ �������� üũ
		  request.setAttribute("page.mn06.SucMsg",   "������ڰ� ���������Դϴ�.");
    }
     
  	if (!"".equals(request.getParameter("start_date")) ) {
		  List<CommonEntity> juheangList = IR030510.getJuheangList(conn, paramInfo);
		  request.setAttribute("page.mn03.juheangList", juheangList);
    }
	}
}