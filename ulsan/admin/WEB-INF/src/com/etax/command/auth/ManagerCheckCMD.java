/****************************************************************
* ������Ʈ��		  : ���������� ���������� �ý���
* ���α׷���		  : ManagerCheckCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2009-12-04
* ���α׷�����	  : ȸ�� ���� ���� Ȯ��
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
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;
import com.etax.helper.MessageHelper;


public class ManagerCheckCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerCheckCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* ������ ó�� */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String message   = auth.getMessage();    //�޽���
    String subjectDN = auth.getSubjectDN();  //�߱޴��
    String serial    = auth.getSerial();     //�ø���
		String userid    = auth.getUserId();     //����ھ��̵�
		String userpw    = auth.getUserPw();     //������н�����

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",    serial);
    paramInfo.setValue("subjectDN", subjectDN);
    paramInfo.setValue("userid",    userid);
		paramInfo.setValue("userpw",    userpw);
		paramInfo.setValue("message",   message);
    paramInfo.setValue("user_state", "");  //����� ����
    paramInfo.setValue("org_id",    "");

    CommonEntity authInfo = ManagerDAO.getInfoByAuth(conn, paramInfo);  //������ ���� ��ȸ
    CommonEntity idInfo   = ManagerDAO.getInfoById(conn, paramInfo);     //���̵� ��� ����
    
    if (idInfo.size() == 0 && authInfo.size() == 0) {  //���԰�����
      paramInfo.setValue("user_state", "OK");
    } else if (idInfo.size() > 0 && authInfo.size() == 0) {  //������ ���� �ʿ�
      paramInfo.setValue("user_state", "RENEWAL");
    } else if (idInfo.size() == 0 && authInfo.size() > 0) {  //��ϵ� ������
      paramInfo.setValue("user_state", "ORGAUTH");
      paramInfo.setValue("org_id",   authInfo.getString("M260_USERID"));
    } else if (idInfo.size() > 0 && authInfo.size() > 0) {   //���ԵǾ� ����
      if ("N".equals(authInfo.getString("M260_USERSTATE")) ) {
        paramInfo.setValue("user_state", "PMS_WAIT");
      } else if ("Y".equals(authInfo.getString("M260_USERSTATE")) ) {
        paramInfo.setValue("user_state", "PMS_OK");
      }
    }
		request.setAttribute("auth.info", paramInfo);
	}
}