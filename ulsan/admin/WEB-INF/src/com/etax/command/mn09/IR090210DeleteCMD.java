/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR09060DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : �ý��ۿ > �μ��ڵ����
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR090210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090210DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
			/* ���� ���� */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");

		paramInfo.setValue("userId", request.getParameter("userId"));
    paramInfo.setValue("user_id",           user_id);

		String SucMsg = "";

		String[] chk_list      = request.getParameterValues("userChk");
		String[] userId_list   = request.getParameterValues("userId_list");
    String[] chk_yn     = request.getParameterValues("chk_yn");
    
		CommonEntity delete_Mgryn = IR090210.getMgrYn(conn , paramInfo);  //���� ���� üũ
      
		if ("N".equals(delete_Mgryn.getString("M260_MGRYN")) )	{
	  SucMsg =  "���� ������ �����ϴ�";
    } else {

		  for (int i=0; i<chk_list.length; i++) {
		    int chk_val = Integer.parseInt(chk_list[i]);
        if ("Y".equals(chk_yn[chk_val])) {
          paramInfo.setValue("userId",       userId_list[chk_val]);

		      if (IR090210.deleteUserID(conn, paramInfo) < 1) {
		        throw new ProcessException("E004"); //������ �����޽��� ǥ��
				  }
        }		
		  }
		}
		
		if (!"".equals(SucMsg)) {
		  request.setAttribute("page.mn09.SucMsg", SucMsg);
	  } else {
		  request.setAttribute("page.mn09.SucMsg", "�����Ǿ����ϴ�");
		}
		 	/* ����� ����Ʈ(�Ϻ�) ��ȸ */
		List userList = IR090210.getUserList(conn, paramInfo);
		request.setAttribute("page.mn09.userList", userList);
  }
}
