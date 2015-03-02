/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091610InsertCMD.java
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

public class IR091610InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091610InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("year",            request.getParameter("year"));
	  paramInfo.setValue("stdAcccode",      request.getParameter("stdAcccode"));
		paramInfo.setValue("stdSemok",        request.getParameter("stdSemok"));
		paramInfo.setValue("stdSemokName",    request.getParameter("stdSemokName"));
	  paramInfo.setValue("sysSemokcode",    request.getParameter("sysSemokcode"));
		paramInfo.setValue("nowincomeSemok",  request.getParameter("nowincomeSemok"));
		paramInfo.setValue("oldincomeSemok",  request.getParameter("oldincomeSemok"));

    if("31".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("51".equals(request.getParameter("stdAcccode"))){
      paramInfo.setValue("accgbn",           "B"); 
      paramInfo.setValue("workgubun",        "0"); 
    }

    logger.info("paramInfo : "+paramInfo);
      
		/* ���� ��� */
		if(IR091610.insertStdsemokCode(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}
    
    //����˾��� ��������
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
    if("11".equals(request.getParameter("questdAcccode"))){
      paramInfo.setValue("accgbn",           "A"); 
      paramInfo.setValue("workgubun",        "0"); 
    }else if("31".equals(request.getParameter("questdAcccode"))){
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

		List standardsemokkList = IR091610.getstandardsemokkList(conn, paramInfo);
		request.setAttribute("page.mn09.standardsemokkList", standardsemokkList);
		request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	}
}
