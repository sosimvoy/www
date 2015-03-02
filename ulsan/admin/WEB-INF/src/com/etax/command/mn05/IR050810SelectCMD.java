/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050810SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ����������ȸ/���
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050810;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050810SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050810SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));

    logger.info("paramInfo : " + paramInfo);
	  
		/* �ڱݹ����������ȸ */
		List<CommonEntity> sugiCancelList = IR050810.getSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.sugiCancelList", sugiCancelList);
  }
}