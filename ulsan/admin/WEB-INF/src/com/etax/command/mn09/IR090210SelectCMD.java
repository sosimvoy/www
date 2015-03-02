/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > 
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090210;
import com.etax.entity.CommonEntity;


public class IR090210SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090210SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity(); 
    
	
  	/* ����� ����Ʈ(�Ϻ�) ��ȸ */
		List userList = IR090210.getUserList(conn, paramInfo);
 		request.setAttribute("page.mn09.userList", userList);
	
	}
}