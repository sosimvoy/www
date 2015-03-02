/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR020310BatIntCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn02.IR020310;
import com.etax.db.mn02.IR020000;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert; 

public class IR020310BatIntCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020310BatIntCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();
    
        paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
        paramInfo.setValue("fis_date",         request.getParameter("fis_date"));

        CommonEntity endChk = IR020000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
      
        if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
            request.setAttribute("page.mn02.SucMsg", "���ϸ����� �Ϸ�� ȸ�����ڴ� ����� �� �����ϴ�.");
        } else {
            //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
            TransLogInsert tli = new TransLogInsert();
            tli.execute(request, response, conn);
            paramInfo.setValue("log_no", tli.getLogNo());

            int insertcnt = IR020310.batchInsert(conn, paramInfo);

            request.setAttribute("page.mn02.SucMsg", insertcnt + "�� ���ó���Ǿ����ϴ�.");
	    }
    }
}
