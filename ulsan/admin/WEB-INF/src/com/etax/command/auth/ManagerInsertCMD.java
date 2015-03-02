/****************************************************************
* ������Ʈ��		  : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		  : ManagerInsertCMD.java
* ���α׷��ۼ���	: 
* ���α׷��ۼ���	: 2010-07-19
* ���α׷�����	  : ����� ��Ͻ�û
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;


public class ManagerInsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerInsertCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    /* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("userid",            request.getParameter("userid")); //�������̵�
		paramInfo.setValue("userpw",            request.getParameter("userpw")); //�н�����
		paramInfo.setValue("username",          request.getParameter("username")); //������
		paramInfo.setValue("currentorgan",      request.getParameter("currentorgan")); //���Ҽӱ��
		paramInfo.setValue("currentdepart",     request.getParameter("currentdepart")); //�ҼӺμ�
		paramInfo.setValue("currentwork1",      request.getParameter("currentwork1")); //�ֿ����1
		paramInfo.setValue("currentsign",       request.getParameter("currentsign")); //����Ǳ���
		paramInfo.setValue("serial",            request.getParameter("serial")); //�ø���
		paramInfo.setValue("subjectDN",         request.getParameter("subjectDN")); //�߱޴��
	  paramInfo.setValue("managerHangNo",     request.getParameter("managerHangNo")); 
		paramInfo.setValue("managerNo",         request.getParameter("managerNo")); 
		paramInfo.setValue("terminalNo",        request.getParameter("terminalNo")); 
	  		
		//ȸ�����
		if( ManagerDAO.managerInsert(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002");
		} 
    
    request.setAttribute("page.auth.SucMsg",    "���ԿϷ�Ǿ����ϴ�. ���δ�����Դϴ�.");
	}
}