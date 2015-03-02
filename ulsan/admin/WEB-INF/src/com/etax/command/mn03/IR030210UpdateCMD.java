/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR030210UpdateCMD.java
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
import com.etax.db.mn03.IR030000;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn03.IR030110;
import com.etax.entity.CommonEntity;

public class IR030210UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030210UpdateCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
    paramInfo.setValue("m040_seq",         request.getParameter("m040_seq"));
    paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
    paramInfo.setValue("part_code",        request.getParameter("part_code"));
    paramInfo.setValue("acc_code",         request.getParameter("acc_code"));	
    paramInfo.setValue("order_no",         request.getParameter("order_no"));
    paramInfo.setValue("org_no",           request.getParameter("org_no"));
  
    CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ

    if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{ 

      CommonEntity noData = IR030110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn03.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
      
      } else {

        paramInfo.setValue("order_name",    request.getParameter("order_name"));
        paramInfo.setValue("note",          request.getParameter("note"));
        paramInfo.setValue("order_no",      request.getParameter("order_no"));
        paramInfo.setValue("cnt",           request.getParameter("cnt"));

        if(IR030210.cashUpdate1(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //������ �����޽��� ǥ��
        }
         request.setAttribute("page.mn03.SucMsg", "����ó���Ǿ����ϴ�.");
      }

    } else {

      CommonEntity noData = IR030110.getOrderNo(conn, paramInfo);   //���޹�ȣ �ߺ� ����

      if(noData.getLong("NO_CNT") != 0 && !paramInfo.getString("order_no").equals(paramInfo.getString("org_no"))) { 
        request.setAttribute("page.mn03.FailMsg", "���޹�ȣ�� �ߺ��˴ϴ�. �ٽ� ����ϼ���.");	
      
      } else {

        paramInfo.setValue("part_code",     request.getParameter("part_code"));
        paramInfo.setValue("acc_code",      request.getParameter("acc_code"));
        paramInfo.setValue("dwtype",        request.getParameter("dwtype"));	
        paramInfo.setValue("intype",        request.getParameter("intype"));	
        paramInfo.setValue("cash_type",     request.getParameter("cash_type"));
        paramInfo.setValue("deposit_type",  request.getParameter("deposit_type"));	
        paramInfo.setValue("order_name",    request.getParameter("order_name"));
        paramInfo.setValue("note",          request.getParameter("note"));
        paramInfo.setValue("order_no",      request.getParameter("order_no"));
        paramInfo.setValue("cnt",           request.getParameter("cnt"));
        paramInfo.setValue("amt",           request.getParameter("amt"));

        if(IR030210.cashUpdate(conn, paramInfo) < 1 ) {
          throw new ProcessException("E003"); //������ �����޽��� ǥ��
        }
        request.setAttribute("page.mn03.SucMsg", "����ó���Ǿ����ϴ�."); 
      }
    }
      
      logger.info("paramInfo : " + paramInfo);		
      
		
		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		CommonEntity cashView = IR030210.getCashView(conn, paramInfo);
		request.setAttribute("page.mn03.cashView", cashView);

		List<CommonEntity> cashDeptList = IR030210.getCashDeptList(conn, paramInfo);
		request.setAttribute("page.mn03.cashDeptList", cashDeptList);

		List<CommonEntity> accNameList = IR030210.getAccNameList(conn,paramInfo);
    request.setAttribute("page.mn03.accNameList", accNameList); 

    request.setAttribute("page.mn03.endChk", endChk);		
	}
}
