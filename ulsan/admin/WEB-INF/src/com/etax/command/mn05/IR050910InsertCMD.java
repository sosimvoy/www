/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050910InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Կ䱸���
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR050910;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050910InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050910InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("last_year",    request.getParameter("last_year"));
		paramInfo.setValue("this_year",    request.getParameter("this_year"));
		paramInfo.setValue("out_acct",     request.getParameter("out_acct"));
		paramInfo.setValue("in_acct",      request.getParameter("in_acct"));
		paramInfo.setValue("in_amt",       request.getParameter("in_amt"));
    
        logger.info("paramInfo : " + paramInfo);
    
        String SucMsg = "";
		String to_day = TextHandler.getCurrentDate();
		String std_day = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy") + "0310";

        CommonEntity closeInfo = IR050000.ilbanCheck(conn, paramInfo);  //�Ϲ�ȸ������üũ

		if (Integer.parseInt(to_day) > Integer.parseInt(closeInfo.getString("M320_DATEILBAN")) ) {
            SucMsg = "�Ϲ�ȸ�� ������ ���Ŀ��� �׿������Կ䱸��� �۾��� �� �� �����ϴ�.";
		} else if (!to_day.equals(TextHandler.getBusinessDate(conn, to_day)) ) {
            SucMsg = "ȸ�����ڰ� �������� �ƴմϴ�.";
        } else {
		    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
            TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
		    paramInfo.setValue("log_no", tli.getLogNo());

		    if (IR050910.insertSurplus(conn, paramInfo) <1 ) {
			    throw new ProcessException("E002");
		    }

		    SucMsg = "���ó���Ǿ����ϴ�.";
		}

		/* ��ݰ��� */
		List<CommonEntity> outAcctList = IR050910.getOutAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.outAcctList", outAcctList);
		/* �Աݰ��� */
		List<CommonEntity> inAcctList = IR050910.getInAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.inAcctList", inAcctList);

        request.setAttribute("page.mn05.SucMsg", SucMsg);
    }
}