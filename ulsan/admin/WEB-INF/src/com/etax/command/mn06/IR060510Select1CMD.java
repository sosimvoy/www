/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060510Select1CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź��������ȸ - �ڱݿ�Ź����
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


public class IR060510Select1CMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060510Select1CMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    paramInfo.setValue("doc_no",           request.getParameter("doc_no"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱݿ�Ź���� ��ȸ */
    List<CommonEntity> bankDepDetailList = IR060510.getBankDepDetailList(conn, paramInfo);
    request.setAttribute("page.mn06.bankDepDetailList", bankDepDetailList);
		
  }
}