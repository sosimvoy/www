/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051110DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿�������ó��/å���ڽ���ó��(å���ڽ��� Ȯ��â���� ��� ������ ��)
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR051110;
import com.etax.db.mn05.IR050000;
import com.etax.entity.CommonEntity;

public class IR051110DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051110DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
		paramInfo.setValue("err_code",           "OVRD");
		paramInfo.setValue("err_cont",           "å���ڽ������ʿ��մϴ�.");
		paramInfo.setValue("send_no",             request.getParameter("send_no"));
		

    if (IR051110.resetSrp(conn, paramInfo) < 1 ) {
			//Ȯ��â���� ��� ���� �� ���󺹱�
			throw new ProcessException("E004");
    }

		/* �׿������� ��ȸ */
		List<CommonEntity> srpPmsList = IR051110.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpPmsList", srpPmsList);

  }
}