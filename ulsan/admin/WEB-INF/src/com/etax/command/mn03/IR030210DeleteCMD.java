/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR030210DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���Լ�������� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030210;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn03.IR030000;
import com.etax.entity.CommonEntity;

public class IR030210DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030210DeleteCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity();

    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("m040_seq",         request.getParameter("m040_seq"));
    paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
		paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));

    logger.info("paramInfo : " + paramInfo);		

		String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");

		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
      
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m040_seq",       seq_list[chk_val]);
 
		  logger.info(paramInfo);

      CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{     
          request.setAttribute("page.mn03.delMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ������ �� �����ϴ�.");   
        
        } else {

            /* ����� ���� */
            if(IR030210.cashDelete(conn, paramInfo) < 1 ) {			
              throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
            request.setAttribute("page.mn03.delMsg", "����ó���Ǿ����ϴ�.");
        }
		}
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));


		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> cashList = IR030210.getCashList(conn, paramInfo);
		request.setAttribute("page.mn03.cashList", cashList);

		List<CommonEntity> cashDeptList = IR030210.getCashDeptList(conn, paramInfo);
		request.setAttribute("page.mn03.cashDeptList", cashDeptList);

		List<CommonEntity> accNameList = IR030210.getAccNameList(conn,paramInfo);
    request.setAttribute("page.mn03.accNameList", accNameList); 
	}
}