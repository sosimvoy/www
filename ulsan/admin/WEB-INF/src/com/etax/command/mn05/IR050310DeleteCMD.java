/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050310DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱ���������γ�����ȸ/å���ڽ���ó��(å���ڽ��� Ȯ��â���� ��� ������ ��)
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
import com.etax.db.mn05.IR050310;
import com.etax.db.mn05.IR050000;
import com.etax.trans.TransHandler;
import com.etax.db.trans.TransDAO;
import com.etax.trans.data.GNBRev;
import com.etax.entity.CommonEntity;

public class IR050310DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050310DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
		paramInfo.setValue("err_code",           "OVRD");
		paramInfo.setValue("err_cont",           "å���ڽ������ʿ��մϴ�.");
		paramInfo.setValue("send_no",             request.getParameter("send_no"));
		paramInfo.setValue("allot_type",         request.getParameter("allot_type"));  //�ڱ� (��)��������
		

    if (IR050000.errAllot(conn, paramInfo) < 1 ) {
			//Ȯ��â���� ��� ���� �� ���󺹱�
			throw new ProcessException("E004");
    }

		/* �ڱݹ������γ��� ��ȸ */
    List<CommonEntity> allotList = IR050310.getReAllotList(conn, paramInfo);
    request.setAttribute("page.mn05.reAllotList", allotList);

  }
}