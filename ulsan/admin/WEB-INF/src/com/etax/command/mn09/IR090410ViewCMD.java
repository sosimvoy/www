/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090410ViewCMD.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-25
* ���α׷�����	   : �ý��ۿ > ����ں����û
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090410;
import com.etax.entity.CommonEntity;


public class IR090410ViewCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090410ViewCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		
		/* ���� ���� */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
	  String user_name = (String)session.getAttribute("session.user_name");
                                                                                                                                           
		CommonEntity paramInfo = new CommonEntity(); 
     paramInfo.setValue("user_id",           user_id);

  	/* �������(�Ϻ�) ��ȸ */   
		CommonEntity chUserInfo = IR090410.getUpdateUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.chUserInfo", chUserInfo);
	
	}
}