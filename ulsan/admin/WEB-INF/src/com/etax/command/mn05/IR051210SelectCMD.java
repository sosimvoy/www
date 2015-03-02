/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051210SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿���������������ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051210;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051210SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* �׿������������� ��ȸ */
		List<CommonEntity> srpRepList = IR051210.getSrpRepList(conn, paramInfo);
		request.setAttribute("page.mn05.srpRepList", srpRepList);
  }
}