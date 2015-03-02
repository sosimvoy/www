/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR090110UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-20
* ���α׷�����   : �ý��ۿ > ����ڵ��/�����û������ȸ/����
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
import com.etax.db.mn09.IR090110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.command.common.TransLogInsert; 

public class IR090110UpdateCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090110UpdateCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //��ȸ��������
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //��ȸ��������
    
		String[] chk_list = request.getParameterValues("userChk");
		String[] userid_list = request.getParameterValues("userid_list");
		String[] change_part_list = request.getParameterValues("change_part_list");
		String[] change_work1_list = request.getParameterValues("change_work1_list");
		String[] change_signtype_list = request.getParameterValues("change_signtype_list");
		String[] current_part_list = request.getParameterValues("current_part_list");
		String[] current_work1_list = request.getParameterValues("current_work1_list");
		String[] current_signtype_list = request.getParameterValues("current_signtype_list");
    
		   //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
      TransLogInsert tli = new TransLogInsert();
      tli.execute(request, response, conn);
      paramInfo.setValue("log_no", tli.getLogNo());

		if (userid_list.length > 1)	{
			for (int i=0; i<chk_list.length; i++) {
		    int chk_val = Integer.parseInt(chk_list[i]);
			
		    paramInfo.setValue("userid",               userid_list[chk_val]);
				paramInfo.setValue("change_part",          change_part_list[chk_val]);
			  paramInfo.setValue("change_work1",         change_work1_list[chk_val]);
			  paramInfo.setValue("change_signtype",      change_signtype_list[chk_val]);
				paramInfo.setValue("current_part",         current_part_list[chk_val]);
			  paramInfo.setValue("current_work1",        current_work1_list[chk_val]);
			  paramInfo.setValue("current_signtype",     current_signtype_list[chk_val]);
      
				/* ���� ���� */
			  if (IR090110.updateUser(conn, paramInfo) < 1) {
		      throw new ProcessException("E003"); //������ �����޽��� ǥ��
		    }
      }
		} else {
				paramInfo.setValue("userid",               request.getParameter("userid_list"));
				paramInfo.setValue("change_part",          request.getParameter("change_part_list"));
				paramInfo.setValue("change_work1",         request.getParameter("change_work1_list"));
				paramInfo.setValue("change_signtype",      request.getParameter("change_signtype_list"));
				paramInfo.setValue("current_part",         request.getParameter("current_part_list"));
				paramInfo.setValue("current_work1",        request.getParameter("current_work1_list"));
				paramInfo.setValue("current_signtype",     request.getParameter("current_signtype_list"));
      
			
			
			if (IR090110.updateUser(conn, paramInfo) < 1) {
		    throw new ProcessException("E003"); //������ �����޽��� ǥ��
		  }
		}	

			/* ����� ��� ��ȸ */
		List historyList = IR090110.getUserSinList(conn, paramInfo);
		request.setAttribute("page.mn09.historyList", historyList);

		request.setAttribute("page.mn09.SucMsg", "���εǾ����ϴ�.");
  }
}
