 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR062010SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �����ڱ���Ȳ��ȸ
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
import com.etax.db.mn06.IR062010;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR062010SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR062010SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

		String acc_date    = request.getParameter("acc_date");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, acc_date);  //��������

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("before_date",         before_date);
		paramInfo.setValue("acc_date",            request.getParameter("acc_date"));
    paramInfo.setValue("fis_year",            request.getParameter("fis_year"));

		logger.info("paramInfo : " + paramInfo);
    
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
			List<CommonEntity> dailyMoneyList = IR062010.getDailyMoneyList(conn, paramInfo);
      request.setAttribute("page.mn06.dailyMoneyList",   dailyMoneyList);
		}
  }
}