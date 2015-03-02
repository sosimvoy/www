/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061210SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�������������ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR061210;
import com.etax.entity.CommonEntity;


public class IR061210SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061210SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱ�����䱸���� ��ȸ */
    List<CommonEntity> inchulReportList = IR061210.getInchulReportList(conn, paramInfo);
    request.setAttribute("page.mn06.inchulReportList", inchulReportList);
		
  }
}