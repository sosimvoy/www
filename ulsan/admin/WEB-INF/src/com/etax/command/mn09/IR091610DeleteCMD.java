/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR09060DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : �ý��ۿ > �μ��ڵ����
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091610;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR091610DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091610DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("partCode", request.getParameter("partCode"));

		String[] chk_list      = request.getParameterValues("userChk");
		String[] delyear       = request.getParameterValues("delyear");
		String[] delacccode    = request.getParameterValues("delacccode");
		String[] delsemokcode  = request.getParameterValues("delsemokcode");
    
		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
			
	  	paramInfo.setValue("delyear",       delyear[chk_val]);
	  	paramInfo.setValue("delacccode",    delacccode[chk_val]);
	  	paramInfo.setValue("delsemokcode",  delsemokcode[chk_val]);

		  IR091610.deletestandardsemokk(conn, paramInfo);

    }
     //paramInfo.setValue("userid",         request.getParameter("userid_list"));

    //����˾��� ��������
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
		paramInfo.setValue("questdAcccode",     request.getParameter("questdAcccode")); 
		/* ���form�� �����ڷ� ��ȸ */
		List useSemokList = IR091610.getuseSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.useSemokCdList", useSemokList);

		/* ���⵵ ������� �ڷ� ��ȸ */
		List nowIncomeList = IR091610.getnowIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.nowincomeSemokCdList", nowIncomeList);

		/* ���⵵ ������� �����ڷ� ��ȸ */
		List oldIncomeList = IR091610.getoldIncomeList(conn, paramInfo);
		request.setAttribute("page.mn09.oldincomeSemokCdList", oldIncomeList);

		request.setAttribute("page.mn09.SucMsg", "�����Ǿ����ϴ�.");
  }
}
