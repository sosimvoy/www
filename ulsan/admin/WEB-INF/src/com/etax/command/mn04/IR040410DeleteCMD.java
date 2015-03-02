/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR020510DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-09
* ���α׷�����   : ���ܼ��� > ¡������ ����
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
import com.etax.db.mn04.IR040410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR040410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR040410DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();

    paramInfo.setValue("baluiDate",             request.getParameter("baluiDate"));
    paramInfo.setValue("fis_year",              request.getParameter("fis_year"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] seq_list = request.getParameterValues("seq_list");
    String[] m80_seq = request.getParameterValues("m80_seq");
		String[] year = request.getParameterValues("year");
    String[] acc_date = request.getParameterValues("acc_date");
    String[] totalAmt = request.getParameterValues("totalAmt");
    String[] seq_yn = request.getParameterValues("seq_yn");
    
		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
			if ("Y".equals(seq_yn[chk_val]) ) {
  		  paramInfo.setValue("seq",       seq_list[chk_val]);
        paramInfo.setValue("m80_seq",       m80_seq[chk_val]);
        paramInfo.setValue("year",       year[chk_val]);
        paramInfo.setValue("acc_date",       acc_date[chk_val]);
        paramInfo.setValue("totalAmt",       totalAmt[chk_val]);
        logger.info("IR040410DeleteCMD paramInfo : " + paramInfo);
	  		if (IR040410.jingsuDelete(conn, paramInfo) < 1) {
		      throw new ProcessException("E004"); //������ �����޽��� ǥ��
		    }
      }
    }
    

			/* ���꼭 ��ȸ */
		List<CommonEntity> jingsuList = IR040410.getJingsuList(conn, paramInfo);
		request.setAttribute("page.mn04.jingsuList", jingsuList);

		request.setAttribute("page.mn04.SucMsg", "�����Ǿ����ϴ�.");
  }
}

