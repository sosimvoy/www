/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR0091110UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-25
* ���α׷�����	 : �ý��ۿ > ����Ǻ�����ν�û
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091110;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091110UpdateCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091010UpdateCMD.class);	// log4j ����
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
		CommonEntity paramInfo = new CommonEntity();
   
		paramInfo.setValue("chDateIlban",       request.getParameter("chDateIlban"));
		paramInfo.setValue("chDateTekbeyl",     request.getParameter("chDateTekbeyl"));
		paramInfo.setValue("chDateGigeum",      request.getParameter("chDateGigeum"));
		paramInfo.setValue("chDateHyungeum",    request.getParameter("chDateHyungeum"));
		paramInfo.setValue("chDateJeungji",     request.getParameter("chDateJeungji"));
		paramInfo.setValue("chDateJuhaengse",   request.getParameter("chDateJuhaengse"));
		paramInfo.setValue("dateIlban",         TextHandler.replace(request.getParameter("dateIlban"),"-",""));
		paramInfo.setValue("dateTekbeyl",       TextHandler.replace(request.getParameter("dateTekbeyl"),"-",""));
		paramInfo.setValue("dateGigeum",        TextHandler.replace(request.getParameter("dateGigeum"),"-",""));
    paramInfo.setValue("dateHyungeum",      TextHandler.replace(request.getParameter("dateHyungeum"),"-",""));
    paramInfo.setValue("dateJeungji",       TextHandler.replace(request.getParameter("dateJeungji"),"-",""));
    paramInfo.setValue("dateJuhaengse",     TextHandler.replace(request.getParameter("dateJuhaengse"),"-",""));

		if(IR091110.updateEndWorkDate(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002"); //����� �����޽��� ǥ��
		}
     
		 if ("".equals(request.getParameter("year")) ) {
			paramInfo.setValue("year", "2010");
		}	else {
			paramInfo.setValue("year", request.getParameter("year"));
		}

   	/* ������ ��*/
		CommonEntity endWorkDate = IR091110.getEndWorkDate(conn, paramInfo);
		request.setAttribute("page.mn09.endWorkDate", endWorkDate);

		request.setAttribute("page.mn09.SucMsg",   "���� �Ǿ����ϴ�.");
	}
} 