/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR040210DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-21
* ���α׷�����   : ���ܼ��� > ���� ����
****************************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR040210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR040210DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("seq",     request.getParameter("seq"));
    paramInfo.setValue("year",             request.getParameter("year"));
		paramInfo.setValue("date",             request.getParameter("date"));
		paramInfo.setValue("mok",              request.getParameter("mok"));
		paramInfo.setValue("sogwanpart",       request.getParameter("sogwanpart"));
		paramInfo.setValue("silgwa",           request.getParameter("silgwa"));
		paramInfo.setValue("businessname",     TextHandler.replace(request.getParameter("businessname"), " ", "%"));
		paramInfo.setValue("monthamt",         request.getParameter("monthamt"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] seq_list = request.getParameterValues("seq_list");
    
		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("seq",       seq_list[chk_val]);

			if (IR040210.budgetDelete(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
    }

			/* ���꼭 ��ȸ */
		 List<CommonEntity> budgetList = IR040210.getBudgetList(conn, paramInfo);
		 request.setAttribute("page.mn04.budgetList", budgetList);

		request.setAttribute("page.mn04.SucMsg", "�����Ǿ����ϴ�.");
  }
}
