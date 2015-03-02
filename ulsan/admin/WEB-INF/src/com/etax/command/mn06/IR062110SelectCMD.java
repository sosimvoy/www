 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR062110SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > ������������
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR062110;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR062110SelectCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR062110SelectCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acc_date",            request.getParameter("acc_date"));
        paramInfo.setValue("acc_year",            request.getParameter("acc_year"));
        paramInfo.setValue("acc_type",            request.getParameter("acc_type"));
        paramInfo.setValue("s_date",              request.getParameter("s_date"));
        paramInfo.setValue("s_year",              request.getParameter("s_year"));

		logger.info("paramInfo : " + paramInfo);

        List<CommonEntity> accList = IR062110.getAccList(conn, paramInfo);
        request.setAttribute("page.mn06.accList",   accList);
        
        if ("S".equals(request.getParameter("flag")) ) {
            List<CommonEntity> segyeList = IR062110.getSegyeList(conn, paramInfo);
            request.setAttribute("page.mn06.segyeList",   segyeList);
        }
        
        /*
		CommonEntity dailyData = IR060000.dailyCheck(conn, acc_date); 
		if (!(request.getParameter("acc_date")).equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
			//������ üũ
		  request.setAttribute("page.mn06.SucMsg",   "�ش����ڴ� �������� �ƴմϴ�.");

		} else if (Integer.parseInt(request.getParameter("acc_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {
			//������� ������ �������� üũ
		  request.setAttribute("page.mn06.SucMsg",   "��ȸ���ڰ� ���������Դϴ�.");

		} else if (!"Y".equals(dailyData.getString("M210_PUBLICDEPOSITSTATE"))) {
			//��������üũ
		  request.setAttribute("page.mn06.SucMsg",   "���ݿ��� �ܾ� ��� �� ��ȸ�� �����մϴ�.");
		} else {
			List<CommonEntity> dailyMoneyList = IR062110.getDailyMoneyList(conn, paramInfo);
            request.setAttribute("page.mn06.dailyMoneyList",   dailyMoneyList);
		}
        */
    }
}
