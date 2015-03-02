/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : SuperVisorCheckCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-08-04
* ���α׷�����	  : å���ڿ���
*****************************************************************/
package com.etax.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.common.SuperDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;


public class SuperVisorCheckCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(SuperVisorCheckCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* ������ ó�� */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String subjectDN = auth.getSubjectDN();  //�߱޴��
    String serial    = auth.getSerial();     //�ø���

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",     serial);
    paramInfo.setValue("subjectDN",  subjectDN);
    paramInfo.setValue("user_id",    user_id);

    CommonEntity sprInfo = SuperDAO.superCheck(conn, paramInfo); //å���ڿ��� üũ
    
    if (sprInfo.size() == 0 ) {                 //å���� �ƴ�
      request.setAttribute("page.info",  "N");
    } else if (sprInfo.size() > 0 ) {           //å���� Ȯ��
			request.setAttribute("page.info",  "Y");
    }
	}
}