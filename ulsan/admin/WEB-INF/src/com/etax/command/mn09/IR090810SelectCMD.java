/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090810SelectCMD.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-20
* ���α׷�����	   : �ý��ۿ > �����ڵ����
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR090810SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090810SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 
    
			paramInfo.setValue("under_year",      request.getParameter("under_year")); 
			paramInfo.setValue("accGubun1",       request.getParameter("accGubun1")); 
			paramInfo.setValue("accCode",         request.getParameter("accCode")); 
   
	 
		/* �����ڵ� ��ȸ */
		List semokCodeList = IR090810.getbudgetSemokList(conn, paramInfo);
		request.setAttribute("page.mn09.semokCodeList", semokCodeList);

		/* ����ڼ����ڵ� ��ȸ */
		List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1);
	}
}