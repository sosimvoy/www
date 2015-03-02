/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030510InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-28
* ���α׷�����   : ���ܼ��� > ������ ����뺸
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040510;
import com.etax.entity.CommonEntity;


public class IR040510InsertCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040510InsertCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("seq",               request.getParameter("seq"));

    if (IR040510.upateRstState(conn, paramInfo) <1) { //����뺸 ���·�
      throw new ProcessException("E003");
    }

  	/* ������ ��  */
		CommonEntity gwaonapView = IR040510.getGwaonapView(conn, paramInfo);
		request.setAttribute("page.mn04.gwaonapView", gwaonapView);

    /* �������� */
    CommonEntity sealInfo = IR040510.getSealInfo(conn);
    request.setAttribute("page.mn04.sealInfo", sealInfo);

    request.setAttribute("page.mn04.SucMsg",    "����뺸 �Ϸ�Ǿ����ϴ�.");
	}
}