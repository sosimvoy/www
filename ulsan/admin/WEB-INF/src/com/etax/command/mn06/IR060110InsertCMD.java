/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060110InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź�䱸���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060110;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR060110InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060110InsertCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("deposit_kind",       request.getParameter("deposit_kind"));
		paramInfo.setValue("new_deposit_amt",    TextHandler.replace(request.getParameter("new_deposit_amt"), ",", ""));
    paramInfo.setValue("x_cnt",              TextHandler.replace(request.getParameter("x_cnt"), ",", ""));
		paramInfo.setValue("rate",               request.getParameter("rate"));
		paramInfo.setValue("deposit_due",        request.getParameter("deposit_due"));
		paramInfo.setValue("end_date",           request.getParameter("end_date"));
	  paramInfo.setValue("mmda_kind",          request.getParameter("mmda_kind"));

    String acc_date = TextHandler.getCurrentDate();
    String SucMsg = "";
  
	  logger.info("paramInfo : " + paramInfo);		
    
    CommonEntity dailyInfo = IR060000.dailyCheck(conn, acc_date);  //���ϸ�������
    CommonEntity closeInfo = IR060000.closingCheck1(conn, acc_date);  //����üũ

    if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date))) {
      SucMsg = "������ �������� �ƴմϴ�.";
    } else if ("Y".equals(dailyInfo.getString("M210_WORKENDSTATE")) ) {
      //���� ���� �Ϸ�Ǿ��� ��
      SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. �ڱݿ�Ź�䱸����� �� �� �����ϴ�.";
    } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("fis_year").equals(acc_date.substring(0,4))) {
      //���Ⱓ�� �ƴѵ� ȸ����⵵ �۾��� �õ��� ��
      SucMsg = "���Ⱓ(1��1�� ~ 3��10��)�� ������ �Ⱓ�� ȸ����⵵ �ڷ� ����� �� �� �����ϴ�.";
    } else {
    
		  //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

      for (int i=0; i<Integer.parseInt(paramInfo.getString("x_cnt")); i++) {
        if (IR060110.insertDeposit(conn, paramInfo) < 1)	{
			    throw new ProcessException("E002"); //����� �����޽���
		    }
      }
		  
	  	SucMsg =  "���ó���Ǿ����ϴ�.";
    }
    request.setAttribute("page.mn06.SucMsg",   SucMsg);
  }
}