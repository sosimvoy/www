/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051410DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Լ�������
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR051410;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;

public class IR051410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051410DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",   request.getParameter("acc_date"));
    
    logger.info("paramInfo : " + paramInfo);

		String[] chk = request.getParameterValues("allotChk");
		String[] m240_seq = request.getParameterValues("m240_seq");
		String[] m010_seq = request.getParameterValues("m010_seq");

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      request.setAttribute("page.mn05.SucMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� �׿������Լ������ ����� �� �����ϴ�..");
		} else {

			for (int i=0; i<chk.length; i++) {
				int y = Integer.parseInt(chk[i]);
        //�׿������Լ���� ���
				if (IR051410.deleteSrpSugi(conn, paramInfo, m240_seq[y]) <1 ) {
			    throw new ProcessException("E004");
		    }
        
				//���Լ���� ���
				if (IR051410.deleteSeipSugi(conn, paramInfo, m010_seq[y]) <1 ) {
			    throw new ProcessException("E004");
		    }
			}

		  request.setAttribute("page.mn05.SucMsg", "���ó���Ǿ����ϴ�.");
		}
	  
		/* �׿������Լ���� ��� ��ȸ */
		List<CommonEntity> srpSugiCancelList = IR051410.getSrpSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpSugiCancelList", srpSugiCancelList);
  }
}