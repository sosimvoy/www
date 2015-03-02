/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050710InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ�������е��
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR050710;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR050710InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050710InsertCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
		paramInfo.setValue("allot_kind",  request.getParameter("allot_kind"));
		paramInfo.setValue("acc_type",    request.getParameter("acc_type"));
		paramInfo.setValue("dept_kind",   request.getParameter("dept_kind"));
		paramInfo.setValue("acc_kind",    request.getParameter("acc_kind"));
    paramInfo.setValue("t_dept_kind",   request.getParameter("t_dept_kind"));
		paramInfo.setValue("t_acc_kind",    request.getParameter("t_acc_kind"));
		paramInfo.setValue("sugi_amt",    request.getParameter("sugi_amt"));
    paramInfo.setValue("work_log",    request.getParameter("work_log"));
		paramInfo.setValue("trans_gubun",    request.getParameter("trans_gubun"));
    
    logger.info("paramInfo : " + paramInfo);

    String SucMsg = "";
    String acc_date = request.getParameter("acc_date");
    String acc_kind = request.getParameter("acc_kind");

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
    CommonEntity closeInfo = IR050000.closingCheck1(conn, acc_date);  //����üũ
		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      SucMsg = "���ϸ����� �Ϸ�� ȸ�����ڴ� �ڱݹ���������� ����� �� �����ϴ�.";
		} else if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
      SucMsg = "ȸ�����ڰ� �������� �ƴմϴ�.";
    } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("fis_year").equals(acc_date.substring(0,4))) {
      //���Ⱓ�� �ƴѵ� ȸ����⵵ �۾��� �õ��� ��
      SucMsg = "���Ⱓ(1��1�� ~ 3��10��)�� ������ �Ⱓ�� ȸ����⵵ �ڷ� ����� �� �� �����ϴ�.";
    } else {
	  
		  //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

		  if (IR050710.insertSugi(conn, paramInfo) <1 ) {
			  throw new ProcessException("E002");
		  }

      if ("2".equals(request.getParameter("allot_kind")) && "B".equals(request.getParameter("acc_type")) && 
        Integer.parseInt(acc_kind) >= 32 && Integer.parseInt(acc_kind) <= 36) {  // �����

        if (IR050710.insertSechul1(conn, paramInfo) <1 ) {  //������������ ���
			    throw new ProcessException("E002");
		    }

      } else if ("4".equals(request.getParameter("allot_kind")) && !"E".equals(request.getParameter("acc_type"))) {  //����� �ݳ�
        if (("B".equals(request.getParameter("acc_type")) && Integer.parseInt(acc_kind) >= 32 && Integer.parseInt(acc_kind) <= 36) || 
          ("A".equals(request.getParameter("acc_type")) && Integer.parseInt(acc_kind) >= 85 && Integer.parseInt(acc_kind) <= 89)) {
          if (IR050710.insertSechul2(conn, paramInfo) <1 ) {  //������������ ���
			      throw new ProcessException("E002");
		      }
        }
      }      

		  SucMsg = "��ϵǾ����ϴ�.";
		}

		/* �μ�����Ʈ */
		List<CommonEntity> deptList = IR050710.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);
		/* ȸ�踮��Ʈ */
		List<CommonEntity> acctList = IR050710.getAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.acctList", acctList);


    request.setAttribute("page.mn05.SucMsg", SucMsg);
  }
}