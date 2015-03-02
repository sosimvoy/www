/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR020210UpdateCMD.java
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
import com.etax.db.mn02.IR020110;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;

public class IR020210UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020210UpdateCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
    
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
    paramInfo.setValue("m030_seq",         request.getParameter("m030_seq"));
    paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
    paramInfo.setValue("part_code",        request.getParameter("part_code"));
    paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
    paramInfo.setValue("order_no",         request.getParameter("order_no"));
    paramInfo.setValue("org_no",           request.getParameter("org_no"));

    CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ

    if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{   
        
      CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����
      
      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn02.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
      
      } else {
 
        paramInfo.setValue("order_name",           request.getParameter("order_name"));
        paramInfo.setValue("order_no",             request.getParameter("order_no"));

        if(IR020210.revWriteUpdate1(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //������ �����޽��� ǥ��
        }
        request.setAttribute("page.mn02.SucMsg", "����ó���Ǿ����ϴ�.");
      }
                                  
    } else {
          
      CommonEntity noData = IR020110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����
          
      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn02.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
      
      } else {

        paramInfo.setValue("acc_type",             request.getParameter("acc_type"));
        paramInfo.setValue("part_code",            request.getParameter("part_code"));
        paramInfo.setValue("acc_code",             request.getParameter("acc_code"));	
        paramInfo.setValue("semok_code",           request.getParameter("semok_code"));
        paramInfo.setValue("intype",               request.getParameter("intype"));	
        paramInfo.setValue("order_name",           request.getParameter("order_name"));
        paramInfo.setValue("order_no",             request.getParameter("order_no"));
        paramInfo.setValue("amt",                  request.getParameter("amt"));

        logger.info("paramInfo : " + paramInfo);		

        if(IR020210.revWriteUpdate(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //������ �����޽��� ǥ��
        }
        request.setAttribute("page.mn02.SucMsg", "����ó���Ǿ����ϴ�.");
      }
    }
			
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
    
    request.setAttribute("page.mn02.endChk", endChk);
	}
}
