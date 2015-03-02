/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060210DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź�䱸���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060210;
import com.etax.db.mn06.IR060000;
import com.etax.entity.CommonEntity;


public class IR060210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060210DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));

		String[] chk_list = request.getParameterValues("allotChk");
		String[] seq_list = request.getParameterValues("seq_list");
		String[] chk_val  = request.getParameterValues("chk_val");

    String acc_date = request.getParameter("reg_date");
    String SucMsg = "";

    CommonEntity dailyInfo = IR060000.dailyCheck(conn, acc_date);  //���ϸ�������
    if ("Y".equals(dailyInfo.getString("M210_WORKENDSTATE")) ) {
      //���� ���� �Ϸ�Ǿ��� ��
      SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. �ڱݿ�Ź�䱸����� �� �� �����ϴ�.";
    } else {
  
	    for (int i=0; i<chk_list.length; i++) {
			  int y = Integer.parseInt(chk_list[i]);
			  if ("Y".equals(chk_val[y]))	{
				  String seq = seq_list[y];
			    if (IR060210.deleteDeposit(conn, seq) < 1 )	{
            throw new ProcessException("E004"); //������ �����޽���
			    }
			  }			
	    }
      SucMsg =  "���ó���Ǿ����ϴ�.";
    }

	  logger.info("paramInfo : " + paramInfo);
    
		/* �ڱݿ�Ź�䱸��ȸ */
    List<CommonEntity> bankDepositList = IR060210.bankDepositList(conn, paramInfo);
		request.setAttribute("page.mn06.bankDepositList", bankDepositList);
		

    request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}