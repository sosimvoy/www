/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050210SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ������γ�����ȸ/����ó��
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050210;
import com.etax.entity.CommonEntity;


public class IR050210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050210SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱݹ������γ��� ��ȸ */
    List<CommonEntity> allotList = IR050210.getAllotList(conn, paramInfo);
    request.setAttribute("page.mn05.allotList", allotList);
		
  }
}