/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010510DeleteCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���� > ����� ��ϳ��� ����
******************************************************/

package com.etax.command.mn01;

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

public class IR010510DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010510DeleteCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
	
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("m010_seq",         request.getParameter("m010_seq"));
    
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));     
	  paramInfo.setValue("intype",           request.getParameter("intype"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));	
	  paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
	  paramInfo.setValue("semok_code",       request.getParameter("semok_code"));
	  paramInfo.setValue("amt",              request.getParameter("amt"));
	
		String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");

		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
      
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m010_seq",       seq_list[chk_val]);
 
		  logger.info("paramInfo : " + paramInfo);		

      CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
	
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{     
            request.setAttribute("page.mn01.delMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ������ �� �����ϴ�.");   
        
        } else {
          
            /* ����� ���� */
            if(IR010510.expWriteDelete(conn, paramInfo) < 1 ) {			
              throw new ProcessException("E004"); //������ �����޽��� ǥ��
            }
            request.setAttribute("page.mn01.delMsg", "����ó���Ǿ����ϴ�.");
        }
		}
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		                                                                             
    /* ����� ����Ʈ */
		List<CommonEntity> expWriteList = IR010510.getExpWriteList(conn, paramInfo);
		request.setAttribute("page.mn01.expWriteList", expWriteList);

		List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);

    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

	}
}