/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR070610SelectCMD.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-25
* ���α׷�����	   : �ý��ۿ > ����Ǻ�����ν�û
****************************************************************/
 
package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn09.IR090910;
import com.etax.entity.CommonEntity;


public class IR090910SelectCMD extends BaseCommand {
	
	private static Logger logger = Logger.getLogger(IR090910SelectCMD.class);	// log4j ����
	
    /* (��ȸ��)�Ķ���� ���� */ 
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
	
		CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("year", request.getParameter("year"));
    paramInfo.setValue("year1", request.getParameter("year1"));
	  paramInfo.setValue("accGubun", request.getParameter("accGubun"));
		paramInfo.setValue("part_code", request.getParameter("part_code"));
		paramInfo.setValue("acc_code", request.getParameter("acc_code"));
    paramInfo.setValue("workGubun", request.getParameter("workGubun"));
		paramInfo.setValue("accGubun1", request.getParameter("accGubun1"));
		paramInfo.setValue("partCode", request.getParameter("partCode"));
    paramInfo.setValue("accCode", request.getParameter("accCode"));
    
  	/* ����ڼ����ڵ� ��ȸ */
		List <CommonEntity>userCodeList = IR090910.getUserCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.userCodeList", userCodeList);

		List<CommonEntity> deptList = IR090910.getAccGubunDeptList09(conn, paramInfo);
		request.setAttribute("page.mn09.deptList", deptList);	
			
		List<CommonEntity> accCdList = IR090910.getAccCdList092(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList", accCdList); 
		
		List<CommonEntity> semokList = IR090910.getSemokList091(conn, paramInfo);
    request.setAttribute("page.mn09.semokList", semokList);
	  
		List<CommonEntity> accCdList1 = IR090910.getAccCdList091(conn, paramInfo);
    request.setAttribute("page.mn09.accCdList1", accCdList1); 

		List<CommonEntity> userAccDeptList1 = IR090910.getAccGubunDeptList091(conn, paramInfo);
		request.setAttribute("page.mn09.userAccDeptList1", userAccDeptList1);	
	}
}