/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050510DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > ���µ��-���»���
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn05.IR050510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR050510DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050510DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
   
   CommonEntity paramInfo = new CommonEntity();
   if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		} else {
			paramInfo.setValue("fis_year",         TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}		
    
		if (!"".equals(request.getParameter("acc_type")) ) {
			paramInfo.setValue("acc_type",       request.getParameter("acc_type"));
		} else {
			paramInfo.setValue("acc_type",       "A");
		}
		if (!"".equals(request.getParameter("dept_cd")) ) {
			paramInfo.setValue("dept_cd",       request.getParameter("dept_cd"));
		} else {
			paramInfo.setValue("dept_cd",       "00000");
		}
		paramInfo.setValue("acc_cd",          request.getParameter("acc_cd"));
		paramInfo.setValue("acct_gubun",      request.getParameter("acct_gubun"));
		paramInfo.setValue("bank_cd",         request.getParameter("bank_cd"));
		paramInfo.setValue("juhaeng",         request.getParameter("juhaeng"));
    
    logger.info("paramInfo : " + paramInfo);

    String[] chk_p       = request.getParameterValues("chk_p");
		String[] acctNo_list = request.getParameterValues("acctNo_list");
    String[] acc_code = request.getParameterValues("acc_code");

		for (int i=0; i<chk_p.length; i++)	{
			int y = Integer.parseInt(chk_p[i]);
			paramInfo.setValue("acct_no",  acctNo_list[y]);
      paramInfo.setValue("acc_code",  acc_code[y]);

			if (IR050510.deleteAcctNoInfo(conn, paramInfo) < 1)	{
				throw new ProcessException("E004"); //������ �����޽���
			}
		}
    
    logger.info("paramInfo : " + paramInfo);
    
		List<CommonEntity> acctInfoList = IR050510.getAcctInfoList(conn, paramInfo);
		request.setAttribute("page.mn05.bankAcctInfoList", acctInfoList);

		/* �μ�����Ʈ */
		List<CommonEntity> deptList = IR050510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);

		/* ȸ��� ��ȸ */
		List<CommonEntity> accList = IR050510.getAccList(conn, paramInfo);
		request.setAttribute("page.mn05.accList", accList);
		
		request.setAttribute("page.mn05.DelMsg", "����ó���Ǿ����ϴ�.");
  }
}