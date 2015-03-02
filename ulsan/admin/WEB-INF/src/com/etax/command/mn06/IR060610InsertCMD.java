/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060610InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź(���,Ư��ȸ��) ���
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
import com.etax.db.mn06.IR060610;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;  //�αױ�� Ŭ����
import com.etax.command.mn06.IR060000Register;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR060610InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR060610InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{   

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
        paramInfo.setValue("acc_year",           request.getParameter("fis_year"));
		paramInfo.setValue("acct_kind",          request.getParameter("acct_kind"));
        paramInfo.setValue("acc_type",           request.getParameter("acct_kind"));//�ܾ���� ȸ�豸��
        paramInfo.setValue("acc_code",           request.getParameter("acct_list"));//�ܾ���� ȸ���ڵ�
        paramInfo.setValue("part_code",          request.getParameter("part_code"));//�ܾ���� �μ��ڵ�
        paramInfo.setValue("work_flag",          "1");  //�ű�
		paramInfo.setValue("acct_list",          request.getParameter("acct_list"));
        paramInfo.setValue("dep_list",           request.getParameter("dep_list"));
		paramInfo.setValue("mmda_list",          request.getParameter("mmda_list"));

		paramInfo.setValue("acct_no",            request.getParameter("acct_no"));
		paramInfo.setValue("jwasu_no",           request.getParameter("jwasu_no"));
		paramInfo.setValue("dep_amt",            request.getParameter("dep_amt"));
		paramInfo.setValue("dep_due",            request.getParameter("dep_due"));
		paramInfo.setValue("dep_rate",           request.getParameter("dep_rate"));
		paramInfo.setValue("new_date",           request.getParameter("new_date"));
		paramInfo.setValue("end_date",           request.getParameter("end_date"));
        paramInfo.setValue("acc_date",           request.getParameter("new_date")); //�ܾ���� ȸ������
        paramInfo.setValue("deposit_type",       request.getParameter("dep_list")); //�ܾ���� ��������
        paramInfo.setValue("input_amt",          request.getParameter("dep_amt"));  //�ܾ���� ����
        paramInfo.setValue("due_day",            request.getParameter("dep_due"));  //�ܾ���� ��Ź�Ⱓ

        String reg_date = request.getParameter("new_date");
        String SucMsg = "";
        String ErrMsg = "";

        int len = 0;
        conn.setAutoCommit(false);
		//���ϸ������� üũ
        CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
        CommonEntity closeInfo = IR060000.closingCheck1(conn, reg_date);  //����üũ
        CommonEntity j_cnt = IR060000.JanaekCnt(conn, paramInfo);
        CommonEntity acctCnt =  IR060000.getAccountCnt(conn, paramInfo);
        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. ���ó���� �� �� �����ϴ�.";
        } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
            SucMsg = "�ű����ڰ� �������� �ƴմϴ�.";
        } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
                && !paramInfo.getString("fis_year").equals(reg_date.substring(0,4))) {
            //���Ⱓ�� �ƴѵ� ȸ����⵵ �۾��� �õ��� ��
            SucMsg = "���Ⱓ(1��1�� ~ 3��10��)�� ������ �Ⱓ�� ȸ����⵵ �ڷ� ����� �� �� �����ϴ�.";
        } else if ("0".equals(j_cnt.getString("CNT")) ) {
            SucMsg = "���� �ܾ��� �ڷᰡ �����ϴ�. �ܾ��� �ڷ� �Է� �� �õ��ϼ���.";
        } else {
      
	        logger.info("paramInfo : " + paramInfo);
      
            
            TransLogInsert tli = new TransLogInsert();  //�α׹�ȣ ä�� Ŭ����
		    tli.execute(request, response, conn);
	        paramInfo.setValue("log_no"  , tli.getLogNo());

            CommonEntity m120Seq = IR060610.getYetakSeq(conn);
            paramInfo.setValue("m120_seq",    m120Seq.getString("SEQNO"));

            //MMDA& ���ڱ�
            if ("G3".equals(request.getParameter("dep_list")) || "G4".equals(request.getParameter("dep_list"))) {
		  	    CommonEntity mmdaInfo = IR060610.getMMDAInfo(conn, paramInfo);
		  	    if ("0".equals(mmdaInfo.getString("CNT")) ) {  //mmda������ ���¸�Ī �Ǽ�
		  	        if (IR060610.insertMMDAMaster(conn, paramInfo) < 1 ) {  //mmda���������� ���
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //����� �����޽���
		  	        }
		  	    } else {
		  	        if (IR060610.updateMMDAMaster(conn, paramInfo) < 1 )	{  //mmda������ �ݾ� ����
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //����� �����޽���
		  	        }
		  	    }

                if (IR060610.insertMMDADetail(conn, paramInfo) < 1 )	{  //mmda�� ���
		  	        conn.rollback();
		            conn.setAutoCommit(true);
		  	        throw new ProcessException("E002"); //����� �����޽���
		  	    }
		  	
            } else {
                
                if ("0".equals(acctCnt.getString("CNT")) ) {
                    //���⿹��, ȯ��ü
		  	        if (IR060610.insertHwan(conn, paramInfo) < 1 )	{
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //����� �����޽���
		  	        }
                } else {
                    SucMsg = "�̹� ��ϵ� �����Դϴ�.";
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
		    }
            
            if ("G1".equals(request.getParameter("dep_list")) ||"G2".equals(request.getParameter("dep_list")) ) {
                if ("0".equals(acctCnt.getString("CNT")) ) {
                    ErrMsg = IR060000Register.permission(conn, paramInfo);
         
                    len += ErrMsg.length();

                    if (len == 0)  {
                        //��Ź ���̺� ���
                        if (IR060610.insertYetak(conn, paramInfo) < 1 )	{
		  	                conn.rollback();
		                    conn.setAutoCommit(true);
		  	                throw new ProcessException("E002"); //����� �����޽���
		  	            }

                        SucMsg = "���ó���Ǿ����ϴ�.";
                    } else {
                        SucMsg = "�ܾ��� ó���� ������ �߻��Ͽ����ϴ�.";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                } else {
                    SucMsg = "�̹� ��ϵ� �����Դϴ�.";
                    conn.rollback();
                    conn.setAutoCommit(true);
                }

            } else {
                ErrMsg = IR060000Register.permission(conn, paramInfo);
         
                len += ErrMsg.length();

                if (len == 0)  {
                    //��Ź ���̺� ���
                    if (IR060610.insertYetak(conn, paramInfo) < 1 )	{
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //����� �����޽���
		  	        }

                    SucMsg = "���ó���Ǿ����ϴ�.";
                } else {
                    SucMsg = "�ܾ��� ó���� ������ �߻��Ͽ����ϴ�.";
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            }
        }

        conn.commit();
        conn.setAutoCommit(true);
    
		/* ȸ�� ���� ��ȸ */
		List<CommonEntity> acctList = IR060610.getAcctList(conn);    
		request.setAttribute("page.mn06.acctList", acctList);

    /* �μ� ���� ��ȸ */
		List<CommonEntity> partList = IR060610.getPartList(conn);    
		request.setAttribute("page.mn06.partList", partList);

    request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}