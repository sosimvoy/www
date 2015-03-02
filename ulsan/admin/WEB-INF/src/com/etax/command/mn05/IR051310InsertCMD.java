/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR051310InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �׿������Լ���е��
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
import com.etax.db.mn05.IR051310;
import com.etax.db.mn05.IR050000;
import com.etax.util.TextHandler;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.entity.CommonEntity;

public class IR051310InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051310InsertCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
	  paramInfo.setValue("this_year",   request.getParameter("this_year"));		
		paramInfo.setValue("dept_list",   request.getParameter("dept_list"));
		paramInfo.setValue("last_year",   request.getParameter("last_year"));		
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));	
		paramInfo.setValue("acc_gubun",   request.getParameter("acc_gubun"));	
		paramInfo.setValue("acc_list",    request.getParameter("acc_list"));	
		paramInfo.setValue("in_amt",      request.getParameter("in_amt"));	
		paramInfo.setValue("fis_year",    request.getParameter("this_year"));	//���ϸ���üũ ����
    paramInfo.setValue("work_log",    request.getParameter("work_log"));	 //�۾�����
		paramInfo.setValue("trans_gubun", request.getParameter("trans_gubun"));	 //�ŷ�����
    
    logger.info("paramInfo : " + paramInfo);
    
    String acc_date = request.getParameter("acc_date");
    String busi_date = TextHandler.getBusinessDate(conn, acc_date);
    String SucMsg = "";

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //���ϸ���üũ
		CommonEntity semokInfo = IR051310.getSemokInfo(conn, paramInfo); //�̿��ݼ����ڵ� ã��
    CommonEntity closeInfo = IR050000.closingCheck1(conn, acc_date);  //����üũ
		paramInfo.setValue("semok_cd",    semokInfo.getString("M370_SEMOKCODE"));

		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      SucMsg = "���ϸ����� �Ϸ�� ȸ�����ڴ� �׿������Լ���е���� �� �� �����ϴ�.";
		} else if ("".equals(semokInfo.getString("M370_SEMOKCODE")) ) {
			SucMsg = "�̿��ݼ����ڵ尡 ��ϵ��� �ʾҽ��ϴ�. ��� �� �̿�ٶ��ϴ�.";
		} else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("last_year").equals(paramInfo.getString("this_year"))) {
      //���Ⱓ�� �ƴѵ� ȸ����⵵ �۾��� �õ��� ��
      SucMsg = "���Ⱓ(1��1�� ~ 3��10��)�� ������ �Ⱓ�� ȸ����⵵ �ڷ� ����� �� �� �����ϴ�.";
    } else if (!acc_date.equals(busi_date)) {
      SucMsg = "ȸ�����ڰ� �������� �ƴմϴ�.";
    }else {

			//�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

			CommonEntity seqInfo = IR051310.getSeqInfo(conn);
			paramInfo.setValue("seq",    seqInfo.getString("M010_SEQ"));

      //�׿������Ը� ���
			if (IR051310.insertSrpSugi(conn, paramInfo) < 1) {
				throw new ProcessException("E002");
			}
      
			//���Լ���е��
			if (IR051310.insertSeipSugi(conn, paramInfo) < 1) {
				throw new ProcessException("E002");
			}

			SucMsg = "���ó���Ǿ����ϴ�.";
		}
	  
		/* �μ��� ��ȸ */
		List<CommonEntity> deptList = IR051310.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);

		/* ȸ��� ��ȸ */
		List<CommonEntity> accList = IR051310.getAccList(conn, paramInfo);
		request.setAttribute("page.mn05.accList", accList);

    request.setAttribute("page.mn05.SucMsg", SucMsg);

  }
}