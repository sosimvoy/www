/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060410SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź������ȸ
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060410;
import com.etax.entity.CommonEntity;


public class IR060410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060410SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));
  
	  logger.info("paramInfo : " + paramInfo);

    /* �ڱݿ�Ź�ϰ�����ȸ */
    List<CommonEntity> bankRegisterList = IR060410.getBankRegisterList(conn, paramInfo);
		request.setAttribute("page.mn06.bankRegisterList", bankRegisterList);
  }
}