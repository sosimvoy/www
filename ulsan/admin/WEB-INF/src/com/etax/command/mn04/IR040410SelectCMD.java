/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020210SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-05
* ���α׷�����   : ���ܼ��� > ��ϳ��� ��ȸ/����/����
****************************************************************/
 
package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.db.common.SelectBox;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn04.IR040410;
import com.etax.entity.CommonEntity;


public class IR040410SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR040410SelectCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("frombaluiDate",         request.getParameter("frombaluiDate"));
		paramInfo.setValue("tobaluiDate",           request.getParameter("tobaluiDate"));
    paramInfo.setValue("fis_year",              request.getParameter("fis_year"));

  	/* ¡������ ��ȸ */
		List<CommonEntity> jingsuList = IR040410.getJingsuList(conn, paramInfo);
		request.setAttribute("page.mn04.jingsuList", jingsuList);
	
	}
}