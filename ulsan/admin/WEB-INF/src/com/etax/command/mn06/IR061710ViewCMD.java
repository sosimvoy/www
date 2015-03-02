 /**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061710ViewCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ܾ�����ȸ
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
import com.etax.db.mn06.IR061710;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061710ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061710ViewCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

		String acc_date    = request.getParameter("acc_date");   //ȸ����
		String before_date = TextHandler.getBeforeDate(conn, acc_date);  //��������

    CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("before_date",         before_date);
		paramInfo.setValue("acc_date",            request.getParameter("acc_date"));
    paramInfo.setValue("fis_year",            request.getParameter("fis_year"));
    paramInfo.setValue("janak_type",          request.getParameter("janak_type"));

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
      
      if ("A".equals(paramInfo.getString("janak_type")) ) {
        List<CommonEntity> giganList = IR061710.getGiganList(conn, paramInfo);
        request.setAttribute("page.mn06.giganList",   giganList);

        CommonEntity social = IR061710.getSocial(conn, paramInfo);   
        request.setAttribute("page.mn06.social",   social);

        CommonEntity addReduce = IR061710.getAddReduce(conn, paramInfo);
        request.setAttribute("page.mn06.addReduce",   addReduce);

        CommonEntity totAmt = IR061710.getTotalAmt(conn, paramInfo);      
        request.setAttribute("page.mn06.totAmt",   totAmt);
      } else if ("B".equals(paramInfo.getString("janak_type")) ) {
        List<CommonEntity> daebiList = IR061710.getDaebiList(conn, paramInfo);
        request.setAttribute("page.mn06.daebiList",   daebiList);

        CommonEntity sdInfo = IR061710.getSocialDaebi(conn, paramInfo);   
        request.setAttribute("page.mn06.sdInfo",   sdInfo);

        CommonEntity totInfo = IR061710.getDaebiTotal(conn, paramInfo);   
        request.setAttribute("page.mn06.totInfo",   totInfo);
      }
		}
  }
}