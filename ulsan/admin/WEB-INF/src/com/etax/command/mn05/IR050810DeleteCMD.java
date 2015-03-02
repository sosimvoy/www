/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050810DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�������е��
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
import com.etax.db.mn05.IR050810;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;

public class IR050810DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050810DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
    paramInfo.setValue("work_log",    "A05");
		paramInfo.setValue("trans_gubun", "151");

    String SucMsg = "";
    String[] chk = request.getParameterValues("allotChk");
		String[] seq = request.getParameterValues("seq_list");
    String[] log_no = request.getParameterValues("log_no");
    
    logger.info("paramInfo : " + paramInfo);

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      SucMsg = "���ϸ����� �Ϸ�� ȸ�����ڴ� �ڱݹ���������� ����� �� �����ϴ�.";
		} else {
      
			for (int i=0; i<chk.length; i++) {
				int y = Integer.parseInt(chk[i]);

				if (IR050810.deleteSugi(conn, paramInfo, seq[y]) <1 ) {
			    throw new ProcessException("E004");
		    }

        IR050810.deleteSechul(conn, paramInfo, log_no[y]);  //�������� ����
			}

		  SucMsg = "���ó���Ǿ����ϴ�.";
		}

		/* �ڱݹ����������ȸ */
		List<CommonEntity> sugiCancelList = IR050810.getSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.sugiCancelList", sugiCancelList);

    request.setAttribute("page.mn05.SucMsg", SucMsg);
  }
}