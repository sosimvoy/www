/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091110SelectCMD.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-25
* ���α׷�����	   : �ý��ۿ > �����ϵ��
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR091110;
import com.etax.entity.CommonEntity;


public class IR091110ViewCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR091110ViewCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 
   
		if ("".equals(request.getParameter("year")) ) {
			paramInfo.setValue("year", "2010");
		}	else {
			paramInfo.setValue("year", request.getParameter("year"));
		}
   
  	/* �������(�Ϻ�) ��ȸ */
		CommonEntity endWorkDate = IR091110.getEndWorkDate(conn, paramInfo);
		request.setAttribute("page.mn09.endWorkDate", endWorkDate);
	
	}
}