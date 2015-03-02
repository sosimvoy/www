/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : SupervisorCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-10-04
* ���α׷�����	  : ���α��� üũ
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.SupervisorDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;

public class SupervisorCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(SupervisorCMD.class);	// log4j ����
  
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* ������ ó�� */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String message   = auth.getMessage();    //�޽���
    String subjectDN = auth.getSubjectDN();  //�߱޴��
    String serial    = auth.getSerial();     //�ø���

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",      serial);
    paramInfo.setValue("subjectDN",   subjectDN);
    paramInfo.setValue("user_id",     user_id);
		paramInfo.setValue("message",     message);
    paramInfo.setValue("user_state",  "");

    CommonEntity authInfo = SupervisorDAO.getInfoByAuth(conn, paramInfo);  //������ ���� ��ȸ
    CommonEntity powChk = SupervisorDAO.getPowCheck(conn, paramInfo);  //������ ���� ��ȸ
    
    if (authInfo.size() == 0) {   //���α��� ����
      paramInfo.setValue("user_state", "FAIL");
    } else {  //������ Ȯ�� �� ���� üũ
      if (powChk.size() > 0) {
        paramInfo.setValue("user_state", "GOOD");
      } else {
        paramInfo.setValue("user_state", "NOPOW");
      }
    } 
		request.setAttribute("auth.info", paramInfo);
	}
}