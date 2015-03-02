/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061410DeleteCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�����(���,Ư��) ���
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
import com.etax.db.mn06.IR061410;
import com.etax.db.mn06.IR060000;
import com.etax.db.mn06.IR060610;
import com.etax.command.common.TransLogInsert;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR061410DeleteCMD.class); // log4j ����

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("acct_kind",          request.getParameter("acct_kind"));
    paramInfo.setValue("acct_list",          request.getParameter("acct_list"));
    paramInfo.setValue("acc_type",           request.getParameter("acct_kind"));//�ܾ��� �뵵
    paramInfo.setValue("acc_code",           request.getParameter("acct_list"));//�ܾ��� �뵵
    paramInfo.setValue("part_code",          request.getParameter("part_code"));
    paramInfo.setValue("acc_date",           request.getParameter("acc_date"));
    paramInfo.setValue("work_flag",          "2");  //�������

		String[] chk_list       = request.getParameterValues("allotChk");
		String[] deposit_type   = request.getParameterValues("deposit_type");
		String[] due_day        = request.getParameterValues("due_day");
		String[] input_amt      = request.getParameterValues("input_amt");
    String[] acct_no        = request.getParameterValues("acct_no");
		String[] jwasu_no       = request.getParameterValues("jwasu_no");
    String[] seq            = request.getParameterValues("seq");
    String[] m130_seq       = request.getParameterValues("m130_seq");

		logger.info("paramInfo : " + paramInfo);
    
    String SucMsg = "";
    String ErrMsg = "";
		String acc_date = TextHandler.getCurrentDate();
    String fis_year = request.getParameter("fis_year");

    int len = 0;
		conn.setAutoCommit(false);
		//���ϸ������� üũ
    CommonEntity d_cnt = IR060000.dailyCheck(conn, acc_date);
    if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. ���ó���� �� �� �����ϴ�.";
    } else {
			TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
	    paramInfo.setValue("log_no"  , tli.getLogNo());

	    for (int i=0; i<chk_list.length; i++) {
		  	int y = Integer.parseInt(chk_list[i]);
        paramInfo.setValue("deposit_type",   deposit_type[y]);
        paramInfo.setValue("input_amt",      input_amt[y]);
        paramInfo.setValue("due_day",        due_day[y]);
        paramInfo.setValue("acct_no",        acct_no[y]);
        paramInfo.setValue("jwasu_no",       jwasu_no[y]);
        paramInfo.setValue("seq",            seq[y]);
        paramInfo.setValue("m130_seq",       m130_seq[y]);

        ErrMsg = IR060000Register.permission(conn, paramInfo);
        
        len += ErrMsg.length();

        if (len == 0) {
          
          if (IR061410.deleteInchul(conn, paramInfo)<1) {  //����� ����
            conn.rollback();
		  	    conn.setAutoCommit(true);
		  		  throw new ProcessException("E004"); //������ �����޽���
          }

          if ("G3".equals(deposit_type[y]) || "G4".equals(deposit_type[y])) {
            
						if (IR061410.updateMMDA(conn, paramInfo) < 1 )	{
		  			  conn.rollback();
		  	      conn.setAutoCommit(true);
		  			  throw new ProcessException("E003"); //������ �����޽���
		  		  }
					  
            if (IR061410.deleteMMDADetail(conn, paramInfo) < 1 )	{
		  		    conn.rollback();
		  	      conn.setAutoCommit(true);
		  		    throw new ProcessException("E004"); //������ �����޽���
		  		  }
		  		
          } else {  //���⿹��, ȯ��ü
		  		  if (IR061410.updateHwan(conn, paramInfo) < 1 )	{
		  			  conn.rollback();
		  	      conn.setAutoCommit(true);
		  			  throw new ProcessException("E003"); //������ �����޽���
		  		  }
		  	  }

          SucMsg = "�ڱ�����(���,Ư��) ���ó���� �Ϸ�Ǿ����ϴ�.";

        } else {
          SucMsg = "�ܾ��� ó�� �� ������ �߻��Ͽ����ϴ�.";
          conn.rollback();
	        conn.setAutoCommit(true);
        }
        
      }
    }

    conn.commit();
    conn.setAutoCommit(true);
    request.setAttribute("page.mn06.SucMsg", SucMsg);

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

		List<CommonEntity> inchulSpDelList = IR061410.getInchulSpDelList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulSpDelList", inchulSpDelList);
  }
}