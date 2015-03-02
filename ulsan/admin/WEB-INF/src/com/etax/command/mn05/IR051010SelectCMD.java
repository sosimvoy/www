/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051010SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Խ�����ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR051010;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051010SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051010SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* �׿������� ��ȸ */
		List<CommonEntity> srpCancelList = IR051010.getSrpCancelList(conn, paramInfo);
		request.setAttribute("page.mn05.srpCancelList", srpCancelList);
  }
}