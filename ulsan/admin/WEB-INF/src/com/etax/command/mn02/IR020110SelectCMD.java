/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR020110SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-01
* ���α׷�����	 : ���� > ����Ʈ��ȸ
******************************************************/

package com.etax.command.mn02;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn02.IR020110;
import com.etax.db.mn01.IR010110;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR020110SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR020110SelectCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
  	
		CommonEntity paramInfo = new CommonEntity();

    if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year", request.getParameter("fis_year"));
		}	else {
			paramInfo.setValue("fis_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		if (!"".equals(request.getParameter("acc_type")) ) {
			paramInfo.setValue("acc_type", request.getParameter("acc_type"));
		}	else {
			paramInfo.setValue("acc_type", "A");
		}
		if (!"".equals(request.getParameter("part_code")) ) {
			paramInfo.setValue("part_code", request.getParameter("part_code"));
		} else {
			paramInfo.setValue("part_code", "00000");
		}
    if (!"".equals(request.getParameter("acc_code")) ) {
			paramInfo.setValue("acc_code", request.getParameter("acc_code"));
		} else {
			List<CommonEntity> accList = IR020110.getAccList(conn, paramInfo);
			CommonEntity accInfo = (CommonEntity)accList.get(0);
			String acc_code = accInfo.getString("M360_ACCCODE");
			paramInfo.setValue("acc_code", acc_code);
		}

    logger.info("paramInfo : " + paramInfo);		

		/* ����Ʈ ��ȸ */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		List<CommonEntity> revDeptList = IR020110.getRevDeptList(conn, paramInfo);
		request.setAttribute("page.mn02.revDeptList", revDeptList);

		List<CommonEntity> revAccNmList = IR020110.getRevAccNmList(conn, paramInfo);
    request.setAttribute("page.mn02.revAccNmList", revAccNmList); 

		List<CommonEntity> revSemokList = IR020110.getRevSemokList(conn, paramInfo);
    request.setAttribute("page.mn02.revSemokList", revSemokList); 
	}
}