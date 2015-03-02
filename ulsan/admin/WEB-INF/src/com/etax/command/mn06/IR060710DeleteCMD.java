/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060710DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź(���,Ư��ȸ��) ��� - ���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060710;
import com.etax.db.mn06.IR060610;
import com.etax.db.mn06.IR060000;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060710DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060710DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",        request.getParameter("fis_year"));
		paramInfo.setValue("acct_kind",       request.getParameter("acct_kind"));
    paramInfo.setValue("acc_date",        request.getParameter("new_date"));  //�ܾ��� ȸ������
    paramInfo.setValue("acc_type",        request.getParameter("acct_kind"));  //�ܾ���� ȸ�豸��
    paramInfo.setValue("acct_list",       request.getParameter("acct_list"));
    paramInfo.setValue("part_code",       request.getParameter("part_code"));
		paramInfo.setValue("new_date",        request.getParameter("new_date"));
    paramInfo.setValue("work_flag",       "4");  //�ܾ��� ��������

		String[] chk_list   = request.getParameterValues("allotChk");
		String[] seq_list   = request.getParameterValues("seq_list");
    String[] m120_seq   = request.getParameterValues("m120_seq");
		String[] acct_no    = request.getParameterValues("acct_no");
		String[] jwasu_no   = request.getParameterValues("jwasu_no");
		String[] dep_gubun  = request.getParameterValues("dep_gubun");
		String[] acc_type   = request.getParameterValues("acc_type");
    String[] acc_code   = request.getParameterValues("acc_code");
		String[] due_day    = request.getParameterValues("due_day");
		String[] inamt      = request.getParameterValues("inamt");
		logger.info("paramInfo : " + paramInfo);
    
    String SucMsg = "";
    String ErrMsg = "";
		String reg_date = request.getParameter("new_date");
    String fis_year = request.getParameter("fis_year");
    int len = 0;

    conn.setAutoCommit(false);
    //���ϸ������� üũ
    CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
    if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. ���ó���� �� �� �����ϴ�.";
    } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
      SucMsg = "�ű����ڰ� �������� �ƴմϴ�.";
    } else {

	    for (int i=0; i<chk_list.length; i++) {
			  int y = Integer.parseInt(chk_list[i]);

        paramInfo.setValue("deposit_type",   dep_gubun[y]);
        paramInfo.setValue("input_amt",      inamt[y]);
        paramInfo.setValue("due_day",        due_day[y]);
        paramInfo.setValue("acc_code",       acc_code[y]);
        paramInfo.setValue("m120_seq",       m120_seq[y]);

        ErrMsg = IR060000Register.permission(conn, paramInfo);
          
        len += ErrMsg.length();

        if (len == 0) {

          if (IR060710.deleteYetak(conn, paramInfo) <1) {
            conn.rollback();
			      conn.setAutoCommit(true);
					  throw new ProcessException("E004"); //������ �����޽���
          }

			    //MMDA ����
          if ("G3".equals(dep_gubun[y]) || "G4".equals(dep_gubun[y])) {
				    if (IR060710.deleteMMDA(conn, seq_list[y]) < 1 )	{  //MMDA�� ����
					    conn.rollback();
			        conn.setAutoCommit(true);
					    throw new ProcessException("E004"); //������ �����޽���
				    }

            CommonEntity mmda_cnt = IR060710.getMmdaCnt(conn, fis_year, acct_no[y]);  //MMDA������ �Ǽ�
            if ("1".equals(mmda_cnt.getString("M140_SEQ")) ) {
						    //MMDA Master����
			        if (IR060710.deleteMmdaMaster(conn, fis_year, acct_no[y]) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E004"); //������ �����޽���
			        }
            } else {
						  //MMDA Master����
			        if (IR060710.updateMmdaMaster(conn, fis_year, acct_no[y], inamt[y]) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E003"); //������ �����޽���
			        }
					  }
          } else {  //���⿹��, ȯ��ü ����
				    if (IR060710.deleteHwan(conn, fis_year, acct_no[y], jwasu_no[y]) < 1 )	{
					    conn.rollback();
			        conn.setAutoCommit(true);
					    throw new ProcessException("E004"); //������ �����޽���
				    }
			    }
          SucMsg = "���ó���� �Ϸ�Ǿ����ϴ�.";
        } else {
          SucMsg = "�ܾ��� ó�� �� ������ �߻��Ͽ����ϴ�.";
          conn.rollback();
	        conn.setAutoCommit(true);
        }
      } 
	  }
    
		conn.commit();
	  conn.setAutoCommit(true);

		List<CommonEntity> acctList = new ArrayList<CommonEntity>();
    List<CommonEntity> partList = new ArrayList<CommonEntity>();
	  /* ȸ������ ��ȸ */
		if ("".equals(request.getParameter("fis_year")) || request.getParameter("fis_year") == null) {
      partList = IR060610.getPartList(conn);
			acctList = IR060610.getAcctList(conn);
      
		} else {
      partList = IR060610.getPartList(conn, paramInfo);
			acctList = IR060610.getAcctList(conn, paramInfo);
      
		}
    
		request.setAttribute("page.mn06.acctList", acctList);
    request.setAttribute("page.mn06.partList", partList);

		List<CommonEntity> bankSpRegList = IR060710.getBankSpRegList(conn, paramInfo);
		request.setAttribute("page.mn06.bankSpRegList", bankSpRegList);
		
		request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}