/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050510ViewCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > ���µ��-��������ȸ�� �˾�
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050510;
import com.etax.entity.CommonEntity;


public class IR050510ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050510ViewCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("acct_no",            request.getParameter("acct_no"));
		paramInfo.setValue("acct_nm",            request.getParameter("acct_nm"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
		paramInfo.setValue("bank_cd",            request.getParameter("bank_cd"));
		paramInfo.setValue("acc_cd",             request.getParameter("acc_cd"));
		paramInfo.setValue("acc_type",           request.getParameter("acc_type"));
		paramInfo.setValue("acct_gubun",         request.getParameter("acct_gubun"));

		String acct_gubun = request.getParameter("acct_gubun");
    
    logger.info("paramInfo : " + paramInfo);
	  
		CommonEntity acctInfo = IR050510.getAcctInfo(conn, paramInfo);

		if (acctInfo.size() > 0) {
			request.setAttribute("page.mn05.ErrMsg",  "�̹� ��ϵ� ���¹�ȣ�Դϴ�. �ٸ� ���¹�ȣ�� �Է��Ͻñ� �ٶ��ϴ�.");
		} else {
			CommonEntity acctNm = IR050510.getAcctNM (conn, paramInfo);
			
			if (!"03".equals(acct_gubun) && !"04".equals(acct_gubun) ) { //��ȸ���� �� �ϻ��� �ƴϸ�..
				CommonEntity acctCnt  = IR050510.getAcctCnt(conn, paramInfo);
				if (!"0".equals(acctCnt.getString("CNT")) ) {
			  request.setAttribute("page.mn05.ErrMsg",  "ȸ�迬��, ȸ���ڵ庰�� ���, �Աݰ��´� �ϳ��� �����մϴ�. ���� ������ �ٽ� ����Ͻʽÿ�.");
				}
			}
		}
  }
}