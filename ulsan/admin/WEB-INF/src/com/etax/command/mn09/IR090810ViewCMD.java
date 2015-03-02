/***************************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR090810ViewCMD.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-20
* ���α׷�����	   : �ý��ۿ > �����ڵ����
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;
import com.etax.db.common.SelectBox;

public class IR090810ViewCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090810ViewCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {

		CommonEntity paramInfo = new CommonEntity();

		String[] chk_list        = request.getParameterValues("userChk");
		String[] year_list       = request.getParameterValues("year_list");
		String[] accGubun_list   = request.getParameterValues("accGubun_list");
		String[] accCode_list    = request.getParameterValues("accCode_list");
   	    String[] workGubun_list  = request.getParameterValues("workGubun_list");
		String[] semokCode_list  = request.getParameterValues("semokCode_list");
		String[] mokGubun_list   = request.getParameterValues("mokGubun_list");


        for (int i=0; i<chk_list.length; i++) {
		    int chk_val = Integer.parseInt(chk_list[i]);
			
		    paramInfo.setValue("year",          year_list[chk_val]);
		    paramInfo.setValue("accGubun",      accGubun_list[chk_val]);
		    paramInfo.setValue("accCode",       accCode_list[chk_val]);
		    paramInfo.setValue("semokCode",     semokCode_list[chk_val]);
		    paramInfo.setValue("workGubun",     workGubun_list[chk_val]);
            paramInfo.setValue("mokGubun",      mokGubun_list[chk_val]);
        }
	 
		/* �����ڵ� ��ȸ */
		CommonEntity semokData = IR090810.getSemokData(conn, paramInfo);
		request.setAttribute("page.mn09.semokData", semokData);

	}
}