/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR090110DeleteCMD.java
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

public class IR090110DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090110DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("su_sdate",        request.getParameter("su_str_date")); //��ȸ��������
		paramInfo.setValue("su_edate",        request.getParameter("su_end_date")); //��ȸ��������

		String[] chk_list = request.getParameterValues("userChk");
		String[] userid_list = request.getParameterValues("userid_list");
    
		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("userid",       userid_list[chk_val]);

		CommonEntity userState = IR090110.getUserState(conn, paramInfo);
			if ("N".equals(userState.getString("M260_USERSTATE")) ) {  

					if (IR090110.deleteUser(conn, paramInfo) < 1) {
						throw new ProcessException("E004"); //������ �����޽��� ǥ��
					}
			} else { 
					if (IR090110.cancelUser(conn, paramInfo) < 1) {
						throw new ProcessException("E003");
					}
			}
		}
     //paramInfo.setValue("userid",         request.getParameter("userid_list"));

			/* ����� ��� ��ȸ */
		List historyList = IR090110.getUserSinList(conn, paramInfo);
		request.setAttribute("page.mn09.historyList", historyList);

		request.setAttribute("page.mn09.SucMsg", "��ҵǾ����ϴ�.");
  }
}
