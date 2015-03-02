/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091610Insert2CMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����	 : �ý��ۿ > �μ��ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091610;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR091610Insert2CMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091610Insert2CMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("queyear",        request.getParameter("queyear"));
	  paramInfo.setValue("moveyear",        request.getParameter("moveyear"));

    logger.info("paramInfo : "+paramInfo);
      
		IR091610.insertStdsemokCpy(conn, paramInfo);

    //����˾��� ��������
		paramInfo.setValue("queyear",           request.getParameter("year")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("stdAcccode")); 
    if("31".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }
		/* ���form�� �����ڷ� ��ȸ */
		List useSemokList = IR091610.getuseSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.useSemokCdList", useSemokList);

		/* ���⵵ ������� �ڷ� ��ȸ */
		List nowIncomeList = IR091610.getnowIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.nowincomeSemokCdList", nowIncomeList);

		/* ���⵵ ������� �����ڷ� ��ȸ */
		List oldIncomeList = IR091610.getoldIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.oldincomeSemokCdList", oldIncomeList);

	}
}
