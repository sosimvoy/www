/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR010510ViewCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����   : ���� > ��ϳ��� ��
****************************************************************/ 

package com.etax.command.mn01;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010510;
import com.etax.db.mn01.IR010000;
import com.etax.db.mn01.IR010110;			
import com.etax.entity.CommonEntity;


public class IR010510ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR010510ViewCMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("fis_year",               request.getParameter("org_year"));
    paramInfo.setValue("fis_date",               request.getParameter("fis_date"));
		paramInfo.setValue("m010_seq",               request.getParameter("m010_seq"));
		paramInfo.setValue("acc_type",               request.getParameter("pop_acc_type"));
		paramInfo.setValue("part_code",              request.getParameter("pop_part_code"));
	  paramInfo.setValue("acc_code",               request.getParameter("pop_acc_code"));
    
    String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");
    
			int chk_val = Integer.parseInt(chk_list[0]);
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m010_seq",       seq_list[chk_val]);


    logger.info("paramInfo : " + paramInfo);		

  	/* ����� ��  */
		CommonEntity expWriteView = IR010510.getExpWriteView(conn, paramInfo);
		request.setAttribute("page.mn01.expWriteView", expWriteView);

		List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);	

    List<CommonEntity> gigwanList = IR010510.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);

    CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
    request.setAttribute("page.mn01.endChk", endChk);

    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 
	}
}