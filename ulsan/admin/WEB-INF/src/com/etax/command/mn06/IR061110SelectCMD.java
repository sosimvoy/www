/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061110SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ����������ȸ/�ϰ���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061110;
import com.etax.entity.CommonEntity;

public class IR061110SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061110SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",   request.getParameter("acc_date"));

    logger.info("paramInfo : " + paramInfo);

		List<CommonEntity> inchulPmsList = IR061110.getInchulPmsList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulPmsList", inchulPmsList);
  }
}