/************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR011010UpdateCMD.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-08-16
* ���α׷�����	   : ���� > ���Ա� ���� �������
*************************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR011010;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert;

public class IR011010UpdateCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR011010UpdateCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));	  //ȸ�迬��
		paramInfo.setValue("odate",            request.getParameter("odate"));		  //��������
    paramInfo.setValue("seq",              request.getParameter("seq"));		    //�Ϸù�ȣ
    
    System.out.println("seq�ȹ޳�??:::"+request.getParameter("seq"));
     //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
     TransLogInsert tli = new TransLogInsert();
     tli.execute(request, response, conn);
     paramInfo.setValue("log_no", tli.getLogNo());

     logger.info("paramInfo : " + paramInfo);		

		 if (IR011010.updateTaxinState(conn, paramInfo) < 1)	{
		   throw new ProcessException("E003"); //������ �����޽���
		 }
     request.setAttribute("page.mn01.SucMsg", "��������Ǿ����ϴ�.");

    List<CommonEntity> expLedgerList = IR011010.getExpLedgerList(conn, paramInfo);
    request.setAttribute("page.mn01.expLedgerList", expLedgerList);
  }
}