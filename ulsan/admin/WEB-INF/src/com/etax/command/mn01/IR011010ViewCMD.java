/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR011010ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ���Ա�������������
****************************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011010;
import com.etax.entity.CommonEntity;

public class IR011010ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR011010ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
    paramInfo.setValue("seq", request.getParameter("seq"));

    logger.info("paramInfo : " + paramInfo);		


		/* ���Ա����� ��  */
		CommonEntity informView = IR011010.getInformView(conn, paramInfo);
		request.setAttribute("page.mn01.informView", informView);

    /* ���� */
    CommonEntity stamp = IR011010.getStamp(conn);
    request.setAttribute("page.mn01.stamp", stamp);   
	}
}
