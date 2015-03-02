/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050710SelectCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�������е�� - ����Ʈ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050710;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050710SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050710SelectCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
			paramInfo.setValue("allot_kind",  request.getParameter("allot_kind"));
			paramInfo.setValue("acc_type",    request.getParameter("acc_type"));
      if (!"".equals(request.getParameter("dept_kind"))) { 
        paramInfo.setValue("dept_kind",   request.getParameter("dept_kind"));
      } else {
        paramInfo.setValue("dept_kind",   "00000");
      }
			
		} else {
			paramInfo.setValue("fis_year",    TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
			paramInfo.setValue("allot_kind",  "1");
			paramInfo.setValue("acc_type",    "B");
			paramInfo.setValue("dept_kind",   "00000");
		}
    
    logger.info("paramInfo : " + paramInfo);
	  
		/* �μ�����Ʈ */
		List<CommonEntity> deptList = IR050710.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);
		/* ȸ�踮��Ʈ */
		List<CommonEntity> acctList = IR050710.getAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.acctList", acctList);
  }
}