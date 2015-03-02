/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR030510View2CMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-28
* ���α׷�����   : ���Լ�������� > ���༼�ϰ�
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;


public class IR030510View2CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510View2CMD.class);	// log4j ����
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (��ȸ��)�Ķ���� ���� */
		CommonEntity paramInfo = new CommonEntity();
    
	    paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

        CommonEntity endCnt = IR030510.getEndCnt(conn, paramInfo); //�ڷ� ���翩��
        CommonEntity juhangDayView = new CommonEntity();
        if ("0".equals(endCnt.getString("CNT")) ) {  //�ڷ���� �� ������ ��¥�� ��ȸ
            juhangDayView = IR030510.getNothingView(conn, paramInfo);
        } else {  //�ڷ������� ��
            juhangDayView = IR030510.getJuhangDayView(conn, paramInfo);
        }
        juhangDayView.setValue("CNT",  endCnt.getString("CNT"));
		request.setAttribute("page.mn03.juhangDayView", juhangDayView);

		CommonEntity sealState = IR030510.getSealState(conn);
		request.setAttribute("page.mn03.sealState", sealState);
	}
}