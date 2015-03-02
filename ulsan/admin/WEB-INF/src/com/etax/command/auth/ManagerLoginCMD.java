/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : ManagerLoginCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-07-19
* ���α׷�����	  : ����� ��Ͻ�û
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

public class ManagerLoginCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerLoginCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
		/* ������ ó�� */
		Authenticator authenticator = new Authenticator();
		authenticator.certify(request, response);
		String user_id = authenticator.getUserId();
    String user_pw = authenticator.getUserPw();
		String subjectDN = authenticator.getSubjectDN();
    String serial = authenticator.getSerial();

		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("subjectDN", subjectDN);
    paramInfo.setValue("serial",    serial);
		paramInfo.setValue("userid",  user_id);
    paramInfo.setValue("userpw",  user_pw);
    logger.info("paramInfo : " + paramInfo);


    CommonEntity authInfo = ManagerDAO.getInfoByAuth(conn, paramInfo);    //������ ���� ��ȸ
    CommonEntity idInfo = ManagerDAO.getInfoById2(conn, paramInfo);  //���̵� ���� ��ȸ

    if (authInfo.size() == 0 && idInfo.size() == 0) {
      throw new AuthException("L112"); //�̰��� �����
    } else if (authInfo.size() == 0 && idInfo.size() > 0) {
      throw new AuthException("L114"); //�̵�� ������
    } else if (authInfo.size() > 0 && idInfo.size() == 0) {
      throw new AuthException("L115"); //�̵�� ���̵�
    } else if (authInfo.size() > 0 && idInfo.size() > 0) {
      CommonEntity logInfo = ManagerDAO.getLogin(conn, paramInfo);
      if (logInfo.size() == 0) {
        throw new AuthException("L115"); // �α��� ����
      } else if (logInfo.size() > 0) {
        if ("N".equals(logInfo.getString("M260_USERSTATE")) ) {
          throw new AuthException("L111"); //�̽��� ����
        } else if ("Y".equals(logInfo.getString("M260_USERSTATE")) ) {
          /* admin ������ �ִ°�� ���� ���� */
		      HttpSession session = request.getSession(true);
              session.setMaxInactiveInterval(14400);
		      session.setAttribute("session.user_id",         logInfo.getString("M260_USERID"));   //ȸ�����̵�
		      session.setAttribute("session.user_name",       logInfo.getString("M260_USERNAME")); //ȸ����
		      session.setAttribute("session.mgr_yn",          logInfo.getString("M260_MGRYN"));    //�����ڱ���
		      session.setAttribute("session.buseo_cd",        logInfo.getString("M260_CURRENTPART"));   //�μ��ڵ�
          session.setAttribute("session.current_organ",   logInfo.getString("M260_CURRENTORGAN"));   //���(�ñݰ�/����û)
		      session.setAttribute("session.user_ip",         request.getRemoteAddr());
        }        
      }
    }		
	}
}