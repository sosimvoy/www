/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061110IR061110.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ����������ȸ-�ϰ���
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
import com.etax.db.mn06.IR061110;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061110DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061110DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("reg_date",           request.getParameter("acc_date"));
    paramInfo.setValue("acc_type",           "A");
    paramInfo.setValue("acc_code",           "11");
    paramInfo.setValue("part_code",          "00000");
    paramInfo.setValue("work_flag",          "2");

		String[] chk_list       = request.getParameterValues("allotChk");
		String[] seq_list       = request.getParameterValues("seq_list");
		String[] chk_val        = request.getParameterValues("chk_val");
		String[] deposit_type   = request.getParameterValues("deposit_type");
		String[] due_day        = request.getParameterValues("due_day");
		String[] input_amt      = request.getParameterValues("input_amt");
		String[] fis_year       = request.getParameterValues("fis_year");
    String[] inchul_date    = request.getParameterValues("inchul_date");
		//String[] cancel_ija     = request.getParameterValues("cancel_ija");
    String[] acct_no        = request.getParameterValues("acct_no");
		String[] jwasu_no       = request.getParameterValues("jwasu_no");
    String[] m160_seq       = request.getParameterValues("m160_seq");

		logger.info("paramInfo : " + paramInfo);
    
    String SucMsg = "";
    String ErrMsg = "";
		String acc_date = request.getParameter("acc_date");

    int len = 0;

		conn.setAutoCommit(false);
		//���ϸ������� üũ
    CommonEntity d_cnt = IR060000.dailyCheck(conn, acc_date);
    if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. �ϰ��� ���ó���� �� �� �����ϴ�.";
    } else {
			TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
	    paramInfo.setValue("log_no"  , tli.getLogNo());

	    for (int i=0; i<chk_list.length; i++) {
		  	int y = Integer.parseInt(chk_list[i]);
		  	if ("Y".equals(chk_val[y]))	{
		  		String seq = seq_list[y];

          paramInfo.setValue("deposit_type",   deposit_type[y]);
          paramInfo.setValue("input_amt",      input_amt[y]);
          paramInfo.setValue("due_day",        due_day[y]);
          paramInfo.setValue("fis_year",       fis_year[y]);
          paramInfo.setValue("acc_date",       inchul_date[y]);
          //paramInfo.setValue("cancel_ija",     cancel_ija[y]);
          paramInfo.setValue("acct_no",        acct_no[y]);
          paramInfo.setValue("jwasu_no",       jwasu_no[y]);
          paramInfo.setValue("m160_seq",       m160_seq[y]);

          ErrMsg = IR060000Register.permission(conn, paramInfo);
          
          len += ErrMsg.length();

          if (len == 0) {
            //���� ���·� ������Ʈ
		  		  if (IR061110.updateDeposit(conn, seq, paramInfo) < 1 )	{
		  			  conn.rollback();
		  		    conn.setAutoCommit(true);
              throw new ProcessException("E003"); //������ �����޽���
		  	    }

            if ("G3".equals(deposit_type[y])) {
              
							if (IR061110.updateMMDA(conn, paramInfo) < 1 )	{
		  				  conn.rollback();
		  		      conn.setAutoCommit(true);
		  				  throw new ProcessException("E003"); //������ �����޽���
		  			  }
						  
              if (IR061110.deleteMMDADetail(conn, paramInfo) < 1 )	{  //mmda�� ����
		  			    conn.rollback();
		  		      conn.setAutoCommit(true);
		  			    throw new ProcessException("E004"); //������ �����޽���
		  			  }
		  			
            } else {  //���⿹��, ȯ��ü
		  			  if (IR061110.updateHwan(conn, paramInfo) < 1 )	{
		  				  conn.rollback();
		  		      conn.setAutoCommit(true);
		  				  throw new ProcessException("E002"); //����� �����޽���
		  			  }
		  		  }

            SucMsg = "�ϰ��� ��� ó���� �Ϸ�Ǿ����ϴ�.";

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
    request.setAttribute("page.mn06.SucMsg", SucMsg);

		/* �ڱ����� ������ȸ */
    List<CommonEntity> inchulPmsList = IR061110.getInchulPmsList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulPmsList", inchulPmsList);
  }
}