/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR070810Select1CMD.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-25
* ���α׷�����	   : �ý��ۿ > �����ڵ����
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090810;
import com.etax.db.common.SelectBox;
import com.etax.entity.CommonEntity;


public class IR090810Select1CMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090810Select1CMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		CommonEntity paramInfo = new CommonEntity();
    
	  if (!"".equals(request.getParameter("accGubun")) ) {
			paramInfo.setValue("accGubun", request.getParameter("accGubun"));
		}

		 if (!"".equals(request.getParameter("accGubun1")) ) {
			paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));
		}
    if ("".equals(request.getParameter("accGubun1")) ) {
			paramInfo.setValue("accGubun1", "A");
		}	
  	/* ����ڼ����ڵ� ��ȸ */
		List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1); 
	}
}