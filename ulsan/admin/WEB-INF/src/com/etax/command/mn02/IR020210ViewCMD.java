/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR020210ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ��ϳ��� ��ȸ
****************************************************************/
 
package com.etax.command.mn02;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn02.IR020210;
import com.etax.db.mn02.IR020000;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;


public class IR020210ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR020210ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",       request.getParameter("org_year"));
    paramInfo.setValue("fis_date",       request.getParameter("fis_date"));
		paramInfo.setValue("m030_seq",       request.getParameter("m030_seq"));
    paramInfo.setValue("acc_type",       request.getParameter("pop_acc_type"));
		paramInfo.setValue("part_code",      request.getParameter("pop_part_code"));
	  paramInfo.setValue("acc_code",       request.getParameter("pop_acc_code"));
    
    String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");
    
		int chk_val = Integer.parseInt(chk_list[0]);

		paramInfo.setValue("fis_year",       year_list[chk_val]);
		paramInfo.setValue("m030_seq",       seq_list[chk_val]);
    
    logger.info("paramInfo : " + paramInfo);		


  	/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		CommonEntity revWriteView = IR020210.getRevWriteView(conn, paramInfo);
		request.setAttribute("page.mn02.revWriteView", revWriteView);

		List<CommonEntity> revDeptList = IR020210.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020210.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020210.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList); 
  
    CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
    request.setAttribute("page.mn02.endChk", endChk);
	}
}