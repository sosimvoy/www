/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061410SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�����䱸��� - ��ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061410;
import com.etax.db.mn06.IR060610;
import com.etax.entity.CommonEntity;

public class IR061410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061410SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
		paramInfo.setValue("acct_kind",       request.getParameter("acct_kind"));
    paramInfo.setValue("part_code",       request.getParameter("part_code"));
    paramInfo.setValue("acct_list",       request.getParameter("acct_list"));
    paramInfo.setValue("acc_date",        request.getParameter("acc_date"));

    logger.info("paramInfo : " + paramInfo);

    List<CommonEntity> acctList = new ArrayList<CommonEntity>();
    List<CommonEntity> partList = new ArrayList<CommonEntity>();
    /* ȸ������ ��ȸ */
		if ("".equals(request.getParameter("fis_year")) || request.getParameter("fis_year") == null) {
      partList = IR060610.getPartList(conn);
			acctList = IR060610.getAcctList(conn);
      
		} else {
      partList = IR060610.getPartList(conn, paramInfo);
			acctList = IR060610.getAcctList(conn, paramInfo);
      
		}

    request.setAttribute("page.mn06.acctList", acctList);
    request.setAttribute("page.mn06.partList", partList);

		List<CommonEntity> inchulSpDelList = IR061410.getInchulSpDelList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulSpDelList", inchulSpDelList);
  }
}