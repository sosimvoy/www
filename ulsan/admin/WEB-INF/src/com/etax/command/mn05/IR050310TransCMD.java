/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR050310TransCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݹ��� > �ڱݹ������γ�����ȸ/å���ڽ��ο䱸��ȸ
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn05.IR050310;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;
import com.etax.command.common.TransferNo;
import com.etax.entity.CommonEntity;

public class IR050310TransCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050310TransCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 
    
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");
		String SucMsg = "";  //ó������޽���

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
        paramInfo.setValue("acc_date",           request.getParameter("allot_date"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));

		String[] allotChk  = request.getParameterValues("allotChk");
		String[] seq_list  = request.getParameterValues("seq_list");
		String[] allot_yn  = request.getParameterValues("allot_yn");
		CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
		if ("Y".equals(magamInfo.getString("M210_WORKENDSTATE")) ) {
            SucMsg = "���Ͼ����� �����Ǿ����ϴ�. å���ڽ���ó���� �� �� �����ϴ�.";
		} else {
		    CommonEntity pcNo = TransferNo.transferNo(conn, user_id);  // �ܸ���ȣ ��ȸ
			paramInfo.setValue("man_name",           pcNo.getString("M260_USERNAME"));
		    paramInfo.setValue("man_bankno",         pcNo.getString("M260_MANAGERBANKERNO"));
	  	    paramInfo.setValue("man_no",             pcNo.getString("M260_MANAGERNO"));
			paramInfo.setValue("tml_no",             pcNo.getString("M260_TERMINALNO"));
            paramInfo.setValue("send_no",            pcNo.getString("SEND_NO"));
            paramInfo.setValue("trans_manage_no",    pcNo.getString("TRANS_MANAGE_NO"));

		    //�αױ�� ����� Ŭ������ �޼ҵ� ȣ��
            TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
		    paramInfo.setValue("log_no", tli.getLogNo());
      
		    CommonEntity outAcct = IR050310.getOutAcctInfo(conn, paramInfo); //��ݰ�������
            paramInfo.setValue("out_bank_cd",  outAcct.getString("M300_BANKCODE"));
		    paramInfo.setValue("out_acct_no",  outAcct.getString("M300_ACCOUNTNO"));
      
		    logger.info("paramInfo : " + paramInfo);
		    
		    if ("".equals(outAcct.getString("M300_ACCOUNTNO")) || outAcct.getString("M300_ACCOUNTNO") == null) {
                SucMsg = "��ݰ��°� �������� �ʽ��ϴ�.";
            } else {
                int total = 0;
                int cnt = 0;
                conn.setAutoCommit(false);
                for (int i=0; i<allotChk.length; i++)   {
                    int y = Integer.parseInt(allotChk[i]);
                    if ("Y".equals(allot_yn[y])) {
                        total ++;
                        CommonEntity inAcct = IR050310.getInAcctInfo(conn, paramInfo, seq_list[y]);
                        if (!"".equals(inAcct.getString("M300_ACCOUNTNO"))) { //�Աݰ��� ������ ����
                            paramInfo.setValue("in_bank_cd",  inAcct.getString("M300_BANKCODE"));
                            paramInfo.setValue("in_acct_no",  inAcct.getString("M300_ACCOUNTNO"));
                            IR050310.updateReAllotPms(conn, paramInfo, seq_list[y]);
                            cnt ++;
                        }
                    }
                }
                
                logger.info("üũ�Ǽ� : " + total + ", ó���Ǽ� : " + cnt );
                
                if (allotChk.length > 0 && total == cnt) {
                    SucMsg = "å���� ����ó���Ǿ����ϴ�.";
                    conn.commit();
                } else {
                    SucMsg = "�Աݰ��°� �������� �ʽ��ϴ�.";
                    conn.rollback();
                }
            
                conn.setAutoCommit(true);
            }
		}
		request.setAttribute("page.mn05.SucMsg",    SucMsg);
    }
}
