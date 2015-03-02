/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	   : IR040210UpdateCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-05
* ���α׷�����	 : ���ܼ��� > ��ϳ��� ����
******************************************************/

package com.etax.command.mn04;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.util.StringUtil;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn04.IR040210;
import com.etax.entity.CommonEntity;

public class IR040210Update1CMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR040210Update1CMD.class);	// log4j ����
	// Connection �� �ʿ��� ���� BaseCommand �� abstract execute() �� �����ؼ� ���
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();
    
		String chukyngamt1 = request.getParameter("chukyngamt1");
		String chukyngamt2 = request.getParameter("chukyngamt2");
		String chukyngamt3 = request.getParameter("chukyngamt3");
		String chukyngamt4 = request.getParameter("chukyngamt4");
		String chukyngamt5 = request.getParameter("chukyngamt5");
		String chukyngamt6 = request.getParameter("chukyngamt6");
		String chukyngamt7 = request.getParameter("chukyngamt7");
		String chukyngamt8 = request.getParameter("chukyngamt8");
		String chukyngamt9 = request.getParameter("chukyngamt9");

		paramInfo.setValue("seq",                     request.getParameter("seq"));
		paramInfo.setValue("year",                    request.getParameter("year"));
		

		paramInfo.setValue("chukyngamt1",	            StringUtil.replace(chukyngamt1,",",""));	
	  paramInfo.setValue("chukyngamt2",	            StringUtil.replace(chukyngamt2,",",""));	
		paramInfo.setValue("chukyngamt3",	            StringUtil.replace(chukyngamt3,",",""));	
		paramInfo.setValue("chukyngamt4",	            StringUtil.replace(chukyngamt4,",",""));	
		paramInfo.setValue("chukyngamt5",	            StringUtil.replace(chukyngamt5,",",""));	
	  paramInfo.setValue("chukyngamt6",	            StringUtil.replace(chukyngamt6,",",""));	
		paramInfo.setValue("chukyngamt7",	            StringUtil.replace(chukyngamt7,",",""));	
		paramInfo.setValue("chukyngamt8",	            StringUtil.replace(chukyngamt8,",",""));	
    paramInfo.setValue("chukyngamt9",	            StringUtil.replace(chukyngamt9,",",""));	

    logger.info(paramInfo);
      
		/* �߰� ���� */
		if(IR040210.chukyngUpdate(conn, paramInfo) < 1 ) {
		  throw new ProcessException("E003"); //������ �����޽��� ǥ��
		}
    
		request.setAttribute("page.mn04.SucMsg", "�����Ǿ����ϴ�.");

		/* �߰� ��  */
		CommonEntity chuKyngView = IR040210.getChukyngView(conn, paramInfo);
		request.setAttribute("page.mn04.chukyngView", chuKyngView);
	}
}