/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051210ViewCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿��������������˾�
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
import com.etax.db.mn05.IR050000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR051210ViewCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051210ViewCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",    request.getParameter("reg_date"));
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("doc_no",    request.getParameter("doc_no"));
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* �׿������������� �˾� */
		CommonEntity srpRepInfo = IR051210.getSrpRepInfo(conn, paramInfo);
		request.setAttribute("page.mn05.srpRepInfo", srpRepInfo);

		/* �׿������������� ����ڸ� */
		CommonEntity srpRepMan = IR051210.getSrpRepManager(conn, paramInfo);
		request.setAttribute("page.mn05.srpRepMan", srpRepMan);

    CommonEntity sealInfo = IR050000.getSealInfo(conn);
		request.setAttribute("page.mn05.sealInfo", sealInfo);
  }
}