/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR030710BatIntCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030710;
import com.etax.db.mn03.IR030000;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert; 

public class IR030710BatIntCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR030710BatIntCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
        paramInfo.setValue("fis_date",         request.getParameter("fis_date"));

        CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
		
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn03.SucMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");
        } else {

            //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
            TransLogInsert tli = new TransLogInsert();
            tli.execute(request, response, conn);
            paramInfo.setValue("log_no", tli.getLogNo());

            int insertcnt = IR030710.batchcashInsert(conn, paramInfo);

            request.setAttribute("page.mn03.SucMsg", insertcnt + "�� ���ó���Ǿ����ϴ�.");
        }
	}
}