/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061210Select1CMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�������������ȸ - �ڱ����⳻��
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


public class IR061210Select1CMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061210Select1CMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",         request.getParameter("reg_date"));
    paramInfo.setValue("doc_no",           request.getParameter("doc_no"));
    
    logger.info("paramInfo : " + paramInfo);
    
	  /* �ڱ����⳻�� ��ȸ */
    List<CommonEntity> inchulDetailList = IR061210.getInchulDetailList(conn, paramInfo);
    request.setAttribute("page.mn06.inchulDetailList", inchulDetailList);
		
  }
}