/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR010210SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-01
* ���α׷�����	 : ���� > �ڵ���ȸ
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn01.IR010210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010210SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010210SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
   
	  CommonEntity paramInfo = new CommonEntity();

		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year", request.getParameter("fis_year"));
		}	else {
			paramInfo.setValue("fis_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		if ("".equals(request.getParameter("part_code")) ) {
			paramInfo.setValue("part_code", "00000");
		}	else {
			paramInfo.setValue("part_code", request.getParameter("part_code"));
		}
		if ("".equals(request.getParameter("acc_code")) ) {
		  paramInfo.setValue("acc_code", "11");
		} else {
			paramInfo.setValue("acc_code", request.getParameter("acc_code"));
		}

    logger.info("paramInfo : " + paramInfo);		


	  /* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> accDeptList = IR010210.getAccDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.accDeptList", accDeptList);
	 
	  List<CommonEntity> accNmList = IR010210.getAccNmList(conn, paramInfo);
    request.setAttribute("page.mn01.accNmList", accNmList); 

		List<CommonEntity> accSemokList = IR010210.getAccSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.accSemokList", accSemokList);

		List<CommonEntity> gigwanList = IR010210.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);
	}
}