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
import com.etax.db.mn09.IR091510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091510DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091510DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		String[] chk_list      = request.getParameterValues("userChk");
		String[] delnapbuja     = request.getParameterValues("delnapbuja");

		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		  paramInfo.setValue("Napbuja",          delnapbuja[chk_val]);

			if (IR091510.deleteNapbuja(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //������ �����޽��� ǥ��
		  }
    }
  	/* �������ּ� ��ȸ */
		List NapbujaList = IR091510.getNapbujaList(conn, paramInfo);
		request.setAttribute("page.mn09.NapbujaList", NapbujaList);

		request.setAttribute("page.mn09.SucMsg", "�����Ǿ����ϴ�.");
  }
}
