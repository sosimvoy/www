/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR090810InsertCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-08-21
* ���α׷�����	 : �ý��ۿ > �����ڵ� ���
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR090810;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR090810InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR090810InsertCMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
            paramInfo.setValue("under_year",      request.getParameter("under_year")); 
			paramInfo.setValue("year",              request.getParameter("year"));
			paramInfo.setValue("accGubun",          request.getParameter("accGubun"));
			paramInfo.setValue("acc_code",          request.getParameter("acc_code"));
			paramInfo.setValue("workGubun",         request.getParameter("workGubun"));
	    
			String[] semokCode = request.getParameterValues("semokCode");
			String[] semokName = request.getParameterValues("semokName");
			String[] segumGubun = request.getParameterValues("segumGubun");
			String[] mokGubun = request.getParameterValues("mokGubun");
      
    	for (int i=0; i < mokGubun.length; i++)	{
  
				if (!"".equals(semokCode[i])) {
					paramInfo.setValue("semokCode",        semokCode[i]);
			    paramInfo.setValue("semokName",        semokName[i]);
			    paramInfo.setValue("segumGubun",       segumGubun[i]);
			    paramInfo.setValue("mokGubun",         mokGubun[i]);
					logger.info("paramInfo : " + paramInfo);

					/* ȸ���ڵ� ��� */
				  if(IR090810.insertSemokCode(conn, paramInfo) < 1 ) {
				  	throw new ProcessException("E003"); //������ �����޽��� ǥ��
			  	}
				}
		
				request.setAttribute("page.mn09.SucMsg", "��ϵǾ����ϴ�.");
	    }
				/* ����ڼ����ڵ� ��ȸ */
				List<CommonEntity> accCdList = SelectBox.getAccCdList3(conn, paramInfo);
				request.setAttribute("page.mn09.accCdList", accCdList); 
				
				List<CommonEntity> accCdList1 = SelectBox.getAccCdList4(conn, paramInfo);
				request.setAttribute("page.mn09.accCdList1", accCdList1); 
			}
   	}

            
