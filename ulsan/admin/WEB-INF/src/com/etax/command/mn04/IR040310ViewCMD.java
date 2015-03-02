/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020210ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���ܼ��� > ��ϳ��� ��
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR040310ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040310ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
      
			paramInfo.setValue("seq",         request.getParameter("seq"));		 //������� �������
		  paramInfo.setValue("year",        request.getParameter("year"));
                   paramInfo.setValue("fis_year",     request.getParameter("fis_year"));
        if ("".equals(paramInfo.getString("fis_year")) ) {
            paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy"));
        }

  	/* ����� ��  */
		CommonEntity budgetView = IR040310.getBudgetView(conn, paramInfo);
		request.setAttribute("page.mn04.budgetView", budgetView);

    /* ����� ����Ʈ */
    List<CommonEntity> managerList = IR040310.getManagerList(conn);
    request.setAttribute("page.mn04.managerList", managerList);


    /* ���ڵ� select boxó������ */
    List<CommonEntity> semokcodeList = IR040310.getsemokcodeList(conn, paramInfo);
    request.setAttribute("page.mn04.semokcodeList", semokcodeList);
    /* �����ڵ� selectboxó������ */
    List<CommonEntity> sesemokcode = IR040310.getsesemokcodeList(conn, paramInfo);
    request.setAttribute("page.mn04.sesemokcode", sesemokcode);
    /* ���ڵ� ���ý� �����ڵ����� ��ȸ���� */
    CommonEntity sangweesemokList = IR040310.getsangweesemokList(conn, paramInfo);
    request.setAttribute("page.mn04.sangweesemokList", sangweesemokList);

    /* ����� ����Ʈ */
    List<CommonEntity> napbuList = IR040310.getnapbuList(conn, paramInfo);
    request.setAttribute("page.mn04.napbuList", napbuList);
    /* ����� ����Ʈ */
    CommonEntity napbuaddressList = IR040310.getnapbuaddressList(conn, paramInfo);
    request.setAttribute("page.mn04.napbuaddressList", napbuaddressList);
	}
}
