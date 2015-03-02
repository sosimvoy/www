/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050310SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱ���������γ�����ȸ/�����ó��
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050310;
import com.etax.entity.CommonEntity;


public class IR050310SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050310SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    /* ��ȸ�� ������ ���� ���ϱ� */
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");


    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱ���������γ��� ��ȸ */
    List<CommonEntity> reAllotList = IR050310.getReAllotList(conn, paramInfo);
    request.setAttribute("page.mn05.reAllotList", reAllotList);
		
  }
}