/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090310.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > 
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090310;
import com.etax.entity.CommonEntity;


public class IR090310SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090310SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		
		/* ���� ���� */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
	  String user_name = (String)session.getAttribute("session.user_name");

		CommonEntity paramInfo = new CommonEntity(); 
     paramInfo.setValue("user_id",           user_id);

  	/* �������(�Ϻ�) ��ȸ */
		CommonEntity userInfo = IR090310.getUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.userInfo", userInfo);
	
	}
}