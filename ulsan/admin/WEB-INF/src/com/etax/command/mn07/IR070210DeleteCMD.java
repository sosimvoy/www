/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR070210DeleteCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    :  2010-08-27
* ���α׷�����      : �ϰ�/���� > ���Ͼ������� > ����
***********************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070210;
import com.etax.entity.CommonEntity;

public class IR070210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070210DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
	    boolean errChk = false;
		String magamdata = "";
		String accyear   = "";
		String accdate   = "";
		String retMsg    = "";
		int agoCnt = 0;

		magamdata = request.getParameter("magamdata");
		accyear   = magamdata.substring(0,4);
		accdate   = magamdata.substring(4,12);

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("accyear", accyear);
        paramInfo.setValue("accdate", accdate);
        paramInfo.setValue("acc_year",    request.getParameter("acc_year"));
		
		logger.info("param : " + paramInfo);

        //�ϰ� ���̺� ����
		if (IR070210.M220DayMagamDelete(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "���ϸ��� ���� �۾��� ������ �߻��Ͽ����ϴ�.(errCode1)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //����� �����޽��� ǥ��
		} 
                if (IR070210.M270TaxCashDayMagamDelete(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "���ϸ��� ���� �۾��� ������ �߻��Ͽ����ϴ�.(errCode2)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //����� �����޽��� ǥ��
		} 
                if (IR070210.M210WorkEndMagamUpdate(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "���ϸ��� ���� �۾��� ������ �߻��Ͽ����ϴ�.(errCode3)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //����� �����޽��� ǥ��
		}

		retMsg = "���ϸ��� ���� �۾��� �Ϸ�Ǿ����ϴ�.";

		 /* ���Ͼ������� ��Ȳ ��ȸ */
		List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
		request.setAttribute("page.mn07.magamList", magamList);
		request.setAttribute("page.mn07.retMsg", retMsg);

        CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);
  }
}
