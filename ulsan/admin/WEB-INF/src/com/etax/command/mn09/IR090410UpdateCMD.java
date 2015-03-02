/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR090410UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-19
* ���α׷�����	 : �ý��ۿ > ��������������û
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090410;
import com.etax.entity.CommonEntity;

public class IR090410UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090410UpdateCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
			/* ���� ���� */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");
	  
		String SucMsg = "";

		CommonEntity paramInfo = new CommonEntity();
  
		paramInfo.setValue("changePart",        request.getParameter("changePart"));
		paramInfo.setValue("changeWork1",       request.getParameter("changeWork1"));
		paramInfo.setValue("changeSignType",    request.getParameter("changeSignType"));

		if (request.getParameter("part_name").equals(request.getParameter("changePart")) || "".equals(request.getParameter("changePart")) ) {
			paramInfo.setValue("part_name",     "N");
		} else {
			paramInfo.setValue("part_name",     "Y");
		}
		if (request.getParameter("work_name").equals(request.getParameter("changeWork1")) || "".equals(request.getParameter("changeWork1")) ) {
			paramInfo.setValue("work_name",     "N");
		} else {
			paramInfo.setValue("work_name",     "Y");
		}
		if (request.getParameter("sign_name").equals(request.getParameter("changeSignType")) || "".equals(request.getParameter("changeSignType")) ) {
			paramInfo.setValue("sign_name",     "N");
		} else {
			paramInfo.setValue("sign_name",     "Y");
		}
    paramInfo.setValue("user_id",           user_id);

		logger.info("paramInfo : " + paramInfo);
    
			if(IR090410.appUserInfo(conn, paramInfo) < 1 ) {
				throw new ProcessException("E002"); //����� �����޽��� ǥ��
			}

			if (!"".equals(SucMsg)) {
		  request.setAttribute("page.mn09.SucMsg", SucMsg);
	  } else {
		  request.setAttribute("page.mn09.SucMsg", "����� �������� ��û�� �Ǿ����ϴ�.");
		}
   	/* ����� ��*/
		CommonEntity chUserInfo = IR090410.getUpdateUserInfo(conn, paramInfo);
		request.setAttribute("page.mn09.chUserInfo", chUserInfo);
	}
} 