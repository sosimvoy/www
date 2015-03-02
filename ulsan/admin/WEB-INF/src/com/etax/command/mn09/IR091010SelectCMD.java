/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090610.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > ���ϰ���
****************************************************************/
package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.db.common.SelectBox;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;

public class IR091010SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("yyyy",     request.getParameter("yyyy"));
		paramInfo.setValue("hol_name", request.getParameter("hol_name"));
		
		/* ���� ����Ʈ ��ȸ */
		List holList = IR091010.getHolList(conn, paramInfo);
		request.setAttribute("page.mn09.holList", holList);

    /* �μ���ȸ */
		//List deptCdList = SelectBox.getDeptCdList(conn, paramInfo);
		//request.setAttribute("page.mn09.deptCdList", deptCdList);

		 /* �μ���ȸ */
		//List accCdList = SelectBox.getAccCdList(conn, paramInfo);
		//request.setAttribute("page.mn09.accCdList", accCdList);
	}
 }
