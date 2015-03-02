/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : MasterLoginCMD.java
* ���α׷��ۼ���	  : (��)�̸�����
* ���α׷��ۼ���	  : 2014-09-15
* ���α׷�����	  : �����ڷα���
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;

public class MasterLoginCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(MasterLoginCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("userid",  request.getParameter("userid"));
		paramInfo.setValue("userpw",  request.getParameter("userpw"));
		
	    logger.debug("paramInfo : " + paramInfo);

	    CommonEntity idInfo = ManagerDAO.getInfoById2(conn, paramInfo);  //���̵� ���� ��ȸ

	    if (idInfo.size() == 0) {
	        throw new AuthException("L115"); // �α��� ����
	    } else if (idInfo.size() > 0) {
	        if ("N".equals(idInfo.getString("M260_USERSTATE")) ) {
	            throw new AuthException("L111"); //�̽��� ����
	        } else if ("Y".equals(idInfo.getString("M260_USERSTATE")) ) {
	            /* admin ������ �ִ°�� ���� ���� */
	            HttpSession session = request.getSession(true);
	            session.setMaxInactiveInterval(14400);
	            session.setAttribute("session.user_id",         idInfo.getString("M260_USERID"));   //ȸ�����̵�
	            session.setAttribute("session.user_name",       idInfo.getString("M260_USERNAME")); //ȸ����
	            session.setAttribute("session.mgr_yn",          idInfo.getString("M260_MGRYN"));    //�����ڱ���
	            session.setAttribute("session.buseo_cd",        idInfo.getString("M260_CURRENTPART"));   //�μ��ڵ�
	            session.setAttribute("session.current_organ",   idInfo.getString("M260_CURRENTORGAN"));   //���(�ñݰ�/����û)
	            session.setAttribute("session.user_ip",         request.getRemoteAddr());
	        }
	    }
	}
}