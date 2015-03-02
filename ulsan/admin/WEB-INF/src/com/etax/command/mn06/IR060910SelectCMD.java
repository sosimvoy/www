/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060910SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�����䱸��ȸ/���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060910;
import com.etax.entity.CommonEntity;

public class IR060910SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060910SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("acc_date",   request.getParameter("acc_date"));

    logger.info("paramInfo : " + paramInfo);

		List<CommonEntity> inchulReqList = IR060910.getInchulReqList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulReqList", inchulReqList);
  }
}