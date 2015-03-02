/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060510SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź��������ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn06.IR060510;
import com.etax.entity.CommonEntity;


public class IR060510SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060510SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱݿ�Ź�䱸���� ��ȸ */
    List<CommonEntity> bankDepReportList = IR060510.getBankDepReportList(conn, paramInfo);
    request.setAttribute("page.mn06.bankDepReportList", bankDepReportList);
		
  }
}