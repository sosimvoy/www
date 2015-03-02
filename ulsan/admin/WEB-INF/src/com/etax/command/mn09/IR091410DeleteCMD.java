/*****************************************************
* ������Ʈ��      : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : IR091410SelectCMD.java
* ���α׷��ۼ���	: (��)�̸�����
* ���α׷��ۼ���	: 2010-07-25
* ���α׷�����		: �ý��ۿ > ���λ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091410;
import com.etax.entity.CommonEntity;

public class IR091410DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091410DeleteCMD.class);	// log4j ����

	/****************************************************************************
   * DB ������ �ϰ� Ŭ���̾�Ʈ�� ��û��� ����
   * @param request         ��û����
	 * @param response        ���䰪��
	 * @param conn            DBĿ�ؼ� ������
   * @exception             SQLException
   * @since                 1.0
  ***************************************************************************/
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("user_id",    user_id);
    paramInfo.setValue("current_organ",         request.getParameter("current_organ"));
	
		logger.info("paramInfo : " + paramInfo);

    /*���� ���� */
		if(IR091410.deleteSignInfo(conn, paramInfo) < 1 ) {			
			throw new ProcessException("E004"); //������ �����޽��� ǥ��
		}

		CommonEntity signInfo = IR091410.getSignInfo(conn, paramInfo);
		request.setAttribute("page.mn09.signInfo", signInfo);

	}
}