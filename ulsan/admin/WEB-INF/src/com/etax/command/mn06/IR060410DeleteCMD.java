/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060410DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź�ϰ��� ��ȸ/��� - ���
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
import com.etax.db.mn06.IR060410;
import com.etax.db.mn06.IR060000;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060410DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));
    paramInfo.setValue("acc_date",           request.getParameter("reg_date"));  //�����ܾ��� ���α׷��� ����
    paramInfo.setValue("acc_type",           "A");
    paramInfo.setValue("acc_code",           "11");
    paramInfo.setValue("part_code",          "00000");
    paramInfo.setValue("work_flag",          "4");

    String SucMsg = "";
    String ErrMsg = "";

		String[] chk_list = request.getParameterValues("allotChk");
		String[] seq_list = request.getParameterValues("seq_list");
		String[] chk_val  = request.getParameterValues("chk_val");
		String[] acct_no  = request.getParameterValues("acct_no");
		String[] stat_type  = request.getParameterValues("stat_type");
		String[] due_day    = request.getParameterValues("due_day");
		String[] fis_year   = request.getParameterValues("fis_year");
		String[] inamt = request.getParameterValues("inamt");
		logger.info("paramInfo : " + paramInfo);
    
		String reg_date = request.getParameter("reg_date");
		int len = 0;
		conn.setAutoCommit(false);
		//���ϸ������� üũ
    CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
    if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. ���ó���� �� �� �����ϴ�.";
    } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
      SucMsg = "������ڰ� �������� �ƴմϴ�.";
    } else {

	    for (int i=0; i<chk_list.length; i++) {
			  int y = Integer.parseInt(chk_list[i]);
			  if ("Y".equals(chk_val[y]))	{
				  String seq = seq_list[y];

          paramInfo.setValue("deposit_type",   stat_type[y]);
          paramInfo.setValue("input_amt",      inamt[y]);
          paramInfo.setValue("due_day",        due_day[y]);
          paramInfo.setValue("fis_year",       fis_year[y]);

          ErrMsg = IR060000Register.permission(conn, paramInfo);
          
          len += ErrMsg.length();

          if (len == 0) {
        
				    //�ϰ��� => ���λ���
		        if (IR060410.updateDeposit(conn, seq) < 1 )	{
		  	      conn.rollback();
		          conn.setAutoCommit(true);
              throw new ProcessException("E003"); //������ �����޽���
		        }
		
            if ("G3".equals(stat_type[y])) {
			        //MMDA Detail����
			        if (IR060410.deleteMMDA(conn, seq) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E004"); //������ �����޽���
			        }

		          CommonEntity mmda_cnt = IR060410.getMmdaCnt(conn, fis_year[y], acct_no[y]);
              if ("1".equals(mmda_cnt.getString("M140_SEQ")) ) {
						    //MMDA Master����
			          if (IR060410.deleteMmdaMaster(conn, fis_year[y], acct_no[y]) < 1 )	{
				          conn.rollback();
		              conn.setAutoCommit(true);
				          throw new ProcessException("E004"); //������ �����޽���
			          }
              } else {
						    //MMDA Master����
			          if (IR060410.updateMmdaMaster(conn, fis_year[y], acct_no[y], inamt[y]) < 1 )	{
				          conn.rollback();
		              conn.setAutoCommit(true);
				          throw new ProcessException("E003"); //������ �����޽���
			          }
					    }
            } else {  //���⿹��, ȯ��ü ����
			        if (IR060410.deleteHwan(conn, seq) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E004"); //������ �����޽���
			        }
		        }
            SucMsg = "�ϰ��� ���ó���� �Ϸ�Ǿ����ϴ�.";
          } else {
            SucMsg = "�ܾ��� ó�� �� ������ �߻��Ͽ����ϴ�.";
            conn.rollback();
	          conn.setAutoCommit(true);
          }
			  }			
	    }
    }
    
		conn.commit();
	  conn.setAutoCommit(true);
		/* �ڱݿ�Ź�䱸��ȸ */
    List<CommonEntity> bankRegisterList = IR060410.getBankRegisterList(conn, paramInfo);
		request.setAttribute("page.mn06.bankRegisterList", bankRegisterList);
		
		request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}