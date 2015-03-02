/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051410SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Լ������ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051410;
import com.etax.entity.CommonEntity;

public class IR051410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051410SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",   request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",   request.getParameter("acc_date"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* �׿������Լ���� ��� ��ȸ */
		List<CommonEntity> srpSugiCancelList = IR051410.getSrpSugiCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpSugiCancelList", srpSugiCancelList);
  }
}