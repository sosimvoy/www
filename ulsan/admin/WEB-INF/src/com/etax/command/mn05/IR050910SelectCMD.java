/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050910SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Կ䱸���
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050910;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050910SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050910SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		String this_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		if (!"".equals(request.getParameter("last_year")) ) {
			paramInfo.setValue("last_year",    request.getParameter("last_year"));
		} else {
			paramInfo.setValue("last_year",    (Integer.parseInt(this_year) - 1) + "");
		}

		if (!"".equals(request.getParameter("this_year")) ) {
			paramInfo.setValue("this_year",    request.getParameter("this_year"));
		} else {
			paramInfo.setValue("this_year",    this_year);
		}
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* ��ݰ��� */
		List<CommonEntity> outAcctList = IR050910.getOutAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.outAcctList", outAcctList);
		/* �Աݰ��� */
		List<CommonEntity> inAcctList = IR050910.getInAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.inAcctList", inAcctList);
  }
}