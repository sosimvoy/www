/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR020210DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn02.IR020210;
import com.etax.db.mn02.IR020000;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR020210DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020210DeleteCMD.class);	// log4j ����
	
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
		
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("m030_seq",         request.getParameter("m030_seq"));
    
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
		paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
		paramInfo.setValue("intype",           request.getParameter("intype"));

    logger.info("paramInfo : " + paramInfo);		

		String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");

		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
      
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m030_seq",       seq_list[chk_val]);
 
		  logger.info(paramInfo);

      CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{     
          request.setAttribute("page.mn02.delMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ������ �� �����ϴ�.");   
        
        } else {

            /* ����� ���� */
            if(IR020210.revWriteDelete(conn, paramInfo) < 1 ) {			
              throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
            request.setAttribute("page.mn02.delMsg", "����ó���Ǿ����ϴ�.");
        }
		}
	  paramInfo.setValue("fis_year",         request.getParameter("fis_year"));    // paramInfo.setValue("fis_year",       year_list[chk_val]);	���� �޾ƿ��Ƿ�
		                                                                             // �������� ��ȸ�Ķ���Ϳ��� �̰� �ѷ��ش�.
    /* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> revWriteList = IR020210.getRevWriteList(conn, paramInfo);
		request.setAttribute("page.mn02.revWriteList", revWriteList);

		List<CommonEntity> revDeptList = IR020210.getRevDeptList(conn, paramInfo);
    request.setAttribute("page.mn02.revDeptList", revDeptList); 

		List<CommonEntity> revAccNmList = IR020210.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020210.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList); 
	}
}