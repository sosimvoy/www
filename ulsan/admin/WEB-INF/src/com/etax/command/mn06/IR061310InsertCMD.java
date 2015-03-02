/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR061310InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱ�����(���,Ư��) ���
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061310;
import com.etax.db.mn06.IR060000;
import com.etax.db.mn06.IR060610;
import com.etax.command.common.TransLogInsert;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061310InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061310InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
        paramInfo.setValue("acct_kind",          request.getParameter("acct_kind"));
        paramInfo.setValue("acct_list",          request.getParameter("acct_list"));
        paramInfo.setValue("acc_type",           request.getParameter("acct_kind"));//�ܾ��� �뵵
        paramInfo.setValue("acc_code",           request.getParameter("acct_list"));//�ܾ��� �뵵
        paramInfo.setValue("part_code",          request.getParameter("part_code"));
        paramInfo.setValue("work_flag",          "3");

		String[] chk_list       = request.getParameterValues("allotChk");
		String[] deposit_type   = request.getParameterValues("deposit_type");
		String[] due_day        = request.getParameterValues("due_day");
		String[] input_amt      = request.getParameterValues("input_amt");
        String[] cancel_date    = request.getParameterValues("cancel_date");
		String[] cancel_ija     = request.getParameterValues("cancel_ija");
        String[] acct_no        = request.getParameterValues("acct_no");
		String[] jwasu_no       = request.getParameterValues("jwasu_no");
        String[] mmda_type      = request.getParameterValues("mmda_type");
        String[] dep_amt        = request.getParameterValues("dep_amt");
        String[] mmda_cancel    = request.getParameterValues("mmda_cancel");
    
        String SucMsg = "";
        String ErrMsg = "";
		String acc_date = TextHandler.getCurrentDate();
        String fis_year = request.getParameter("fis_year");

        int len = 0;
        String holy = "";
        for (int i=0; i<chk_list.length; i++) {
		    int x = Integer.parseInt(chk_list[i]);
            if (!cancel_date[x].equals(TextHandler.getBusinessDate(conn, cancel_date[x])) ) {
                holy = "�������ڰ� �������Դϴ�.";
            }  
        }
		conn.setAutoCommit(false);
		//���ϸ������� üũ
        CommonEntity d_cnt = IR060000.dailyCheck(conn, acc_date);
        CommonEntity closeInfo = IR060000.closingCheck1(conn, acc_date);  //����üũ
        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
	    	SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. �ϰ���ó���� �� �� �����ϴ�.";
        } else if (!"".equals(holy) ) {
            SucMsg = holy;
        } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
            && !fis_year.equals(acc_date.substring(0,4))) {
            //���Ⱓ�� �ƴѵ� ȸ����⵵ �۾��� �õ��� ��
            SucMsg = "���Ⱓ(1��1�� ~ 3��10��)�� ������ �Ⱓ�� ȸ����⵵ �ڷ� ����� �� �� �����ϴ�.";
        } else {
			TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
	        paramInfo.setValue("log_no"  , tli.getLogNo());

	        for (int i=0; i<chk_list.length; i++) {
		      	int y = Integer.parseInt(chk_list[i]);
                paramInfo.setValue("deposit_type",   deposit_type[y]);
                paramInfo.setValue("input_amt",      input_amt[y]);
                paramInfo.setValue("due_day",        due_day[y]);
              	paramInfo.setValue("cancel_date",    cancel_date[y]);
                paramInfo.setValue("cancel_ija",     cancel_ija[y]);
                paramInfo.setValue("acct_no",        acct_no[y]);
                paramInfo.setValue("jwasu_no",       jwasu_no[y]);
              	paramInfo.setValue("acc_date",       cancel_date[y]);
                paramInfo.setValue("mmda_type",      mmda_type[y]);
                paramInfo.setValue("dep_amt",        dep_amt[y]);
                paramInfo.setValue("mmda_cancel",    mmda_cancel[y]);
                logger.info("paramInfo : " + paramInfo);

              	ErrMsg = IR060000Register.permission(conn, paramInfo);
                
                len += ErrMsg.length();
                
              	if (len == 0) {
                
                    CommonEntity m130Seq = IR061310.getInchulSeq(conn);
                    paramInfo.setValue("m130_seq",    m130Seq.getString("SEQNO"));

                    if (IR061310.insertInchul(conn, paramInfo)<1) {
                        conn.rollback();
		  	            conn.setAutoCommit(true);
                        SucMsg = "�������̺� ��� �߿� ������ �߻��Ͽ����ϴ�.";
                    }

                  	if ("G3".equals(deposit_type[y]) || "G4".equals(deposit_type[y])) {
                    
				    	if (IR061310.updateMMDA(conn, paramInfo) < 1 )	{
		  		            conn.rollback();
		  	                conn.setAutoCommit(true);
		  			        throw new ProcessException("E003"); //������ �����޽���
		  		        }
					  
                        if (IR061310.insertMMDADetail(conn, paramInfo) < 1 )	{
		  		            conn.rollback();
		  	                conn.setAutoCommit(true);
		  		            throw new ProcessException("E002"); //����� �����޽���
		  		        }
		  		
                    } else {  //���⿹��, ȯ��ü
		  		        if (IR061310.updateHwan(conn, paramInfo) < 1 )	{
		  			        conn.rollback();
		  	                conn.setAutoCommit(true);
		  			        throw new ProcessException("E002"); //����� �����޽���
		  		        }
		  	        }
            
                    if ("".equals(SucMsg)) {
                        SucMsg = "�ϰ��� ó���� �Ϸ�Ǿ����ϴ�.";
                    } 

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

		/* �ڱ����� ������ȸ */
        List<CommonEntity> inchulSpList = IR061310.getInchulSpList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulSpList", inchulSpList);
    }
}