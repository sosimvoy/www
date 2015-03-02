/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR070310InsertCMD.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����      : �ϰ�/���� > ȸ��⵵�̿� > �̿� (ȸ�豸��&�ڵ� �Ǻ� ó��)
* ���α׷����      : 1.ȸ�豸��(A,B,E/D)�� ���� �������ڷ� �ϰ����̺� ������� ���� 
                      2.��������̺� INSERT (ȸ�豸�к� �������̺� ����) - �ܾ��� 0���� ū ��츸 �ش�. ȸ�����ڴ� �������� �Ϳ�����
                      3.ȸ��⵵�̿� ���̺� UPDATE (�̿�����,�Ѱ�)
                      (ȸ�豸��,ȸ���ڵ�,�����ڵ� �� ���)
***********************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.util.*;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.command.common.TransLogInsert;  //�αױ�� ����� ���� import
import com.etax.db.mn07.IR070310;
import com.etax.entity.CommonEntity;

public class IR070310InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR070310InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
	    boolean errChk      = false;
		int     insertCnt   = 0;
		String  retMsg      = "";
        
        String  trans_data  = request.getParameter("trans_data");
        String  next_year   = request.getParameter("next_year");
        String  acc_year    = request.getParameter("acc_year");
        String  acc_type    = request.getParameter("acc_type");
        String  work_log    = request.getParameter("work_log");     // ��������
        String  trans_gubun = request.getParameter("trans_gubun");  // �ŷ�����
        String  acc_code    = trans_data.substring(0,2);            // ȸ���ڵ�
        String  acc_date    = trans_data.substring(2,10);           // ȸ������(������=������)
        
        // ��Ͻ� ȸ������ = �����Ͽ��� ������ �������� �Ϳ����� (���� ���̺� �ش�)
        String reg_acc_date = TextHandler.getBusinessDate(conn,TextHandler.addDays(acc_date,1));

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("next_year",    next_year);  // �̿��⵵
        paramInfo.setValue("acc_year",     acc_year);
        paramInfo.setValue("acc_type",     acc_type);
        paramInfo.setValue("acc_code",     acc_code);
        paramInfo.setValue("acc_date",     acc_date);
		paramInfo.setValue("work_log",     work_log);
		paramInfo.setValue("trans_gubun",  trans_gubun);
		paramInfo.setValue("reg_acc_date", reg_acc_date);   // �̿������� ��Ͻ� ȸ������ 
		
		logger.info("param : " + paramInfo);
        
        // 0.�α׹�ȣ �������� 
        TransLogInsert tli = new TransLogInsert();
        tli.execute(request, response, conn);
        paramInfo.setValue("log_no",		tli.getLogNo());	// �α׹�ȣ

        
        conn.setAutoCommit(false);

        if(acc_type.equals("D")){   // ���Լ��������(D) �̿�

            /* 1.���Լ�������� �������̺� ��� (���ݱ���,���ݱ��п� ���� MAX 12ROW) */
            insertCnt = IR070310.setTaxInCashInsert(conn, paramInfo);
            if (insertCnt > 0){
                
                /* 2. ���Լ�������� ȸ��⵵�̿� ���̺� UPDATE �ڷ� �������� */
                List<CommonEntity> transUpdateList = IR070310.getAccTransCashList(conn, paramInfo);
                
                for (int i=0; transUpdateList != null && i <transUpdateList.size(); i++) {
                    
                    CommonEntity transUpdateData = (CommonEntity)transUpdateList.get(i);

                    /* 3. ���Լ�������� ȸ��⵵�̿� ���̺� UPDATE */
                    if(IR070310.setAccTransUpdate(conn, transUpdateData) < 1 ) {
                        
                        errChk = true;
                        retMsg = "ȸ�迬���̿�- �Ѿ� UPDATE �۾��� ������ �߻��Ͽ����ϴ�.(errCode1)";
                        logger.info("retMsg : " + retMsg);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        throw new ProcessException("E003"); //����� �����޽��� ǥ��
                    }
                }
                
			}else{
                errChk = true;
                retMsg = "ȸ�迬���̿�-���Լ�������� �������̺� ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode2)";
                logger.info("retMsg : " + retMsg);
                conn.rollback();
                conn.setAutoCommit(true);
                throw new ProcessException("E002"); //����� �����޽��� ǥ��
            }

        }else{      // �Ϲ�,Ư��,���(A,B,E) �̿�

            /* 1. ���� �������̺� ��� */
            insertCnt = IR070310.setTaxInInsert(conn, paramInfo);
            if (insertCnt > 0){
                
                /* 2. ���� ȸ��⵵�̿� ���̺� UPDATE �ڷ� �������� */
                List<CommonEntity> transUpdateList = IR070310.getAccTransList(conn, paramInfo);
                
                for (int i=0; transUpdateList != null && i <transUpdateList.size(); i++) {
                    
                    CommonEntity transUpdateData = (CommonEntity)transUpdateList.get(i);

                    /* 3. ���� ȸ��⵵�̿� ���̺� UPDATE */
                    if(IR070310.setAccTransUpdate(conn, transUpdateData) < 1 ) {
                        
                        errChk = true;
                        retMsg = "ȸ�迬���̿�- �Ѿ� UPDATE �۾��� ������ �߻��Ͽ����ϴ�.(errCode3)";
                        logger.info("retMsg : " + retMsg);
                        conn.rollback();
                        conn.setAutoCommit(true);
                        throw new ProcessException("E003"); //����� �����޽��� ǥ��
                    }
                }
                
			}else{
                errChk = true;
                retMsg = "ȸ�迬���̿�-���� �������̺� ��� �۾��� ������ �߻��Ͽ����ϴ�.(errCode4)";
                logger.info("retMsg : " + retMsg);
                conn.rollback();
                conn.setAutoCommit(true);
                throw new ProcessException("E002"); //����� �����޽��� ǥ��
            }
        }

        conn.commit();
        conn.setAutoCommit(true);

        retMsg = "ȸ��⵵�̿� �۾��� �Ϸ�Ǿ����ϴ�.";
        
        
        paramInfo.setValue("end_date",     acc_date);

        if(acc_type.equals("D")){
             /* 2-1.�̿������ȸ ��Ȳ ��ȸ(D) */
            List<CommonEntity> transList = IR070310.getTransCashList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }else{
             /* 2-2.�̿������ȸ ��Ȳ ��ȸ(A,B,E) */
            List<CommonEntity> transList = IR070310.getTransList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }
		request.setAttribute("page.mn07.retMsg", retMsg);
    }
}