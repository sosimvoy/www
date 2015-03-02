/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050510InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > ���µ��-���µ��
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR050510;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR050510InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050510InsertCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
    
		HttpSession session = request.getSession(false);

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));	
    paramInfo.setValue("dept_cd",          request.getParameter("dept_cd"));
		paramInfo.setValue("acct_nm",          request.getParameter("acct_nm"));
		paramInfo.setValue("bank_cd",          request.getParameter("bank_cd"));
    paramInfo.setValue("acct_gubun",       request.getParameter("acct_gubun"));
		paramInfo.setValue("acct_no",          request.getParameter("acct_no"));
		paramInfo.setValue("owner_nm",         TextHandler.replace(TextHandler.replace(request.getParameter("owner_nm"), "��", ""), " ", ""));
		paramInfo.setValue("juhaeng",          request.getParameter("juhaeng"));
		paramInfo.setValue("acc_cd",           request.getParameter("acc_cd"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));
    paramInfo.setValue("state",         "S2");

    if ("03".equals(request.getParameter("acct_gubun")) ) {
      if ("".equals(request.getParameter("acc_type")) ) {
        paramInfo.setValue("acc_type",    "");
        paramInfo.setValue("acc_cd",      "");
        paramInfo.setValue("dept_cd",     "");
      }
    } 
		//�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
    TransLogInsert tli = new TransLogInsert();
		tli.execute(request, response, conn);
		paramInfo.setValue("log_no", tli.getLogNo());

		if (IR050510.insertAcctInfo(conn, paramInfo) < 1)	{
			throw new ProcessException("E002"); //����� �����޽���
		}

    logger.info("paramInfo : " + paramInfo);
		
		request.setAttribute("page.mn05.SucMsg", "���ó���Ǿ����ϴ�.");
  }
}