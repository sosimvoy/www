/**********************************************************************
* ������Ʈ��        : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���        : IR060310InsertCMD.java
* ���α׷��ۼ���    :
* ���α׷��ۼ���    :
* ���α׷�����      : �ڱݿ�� > �ڱݿ�Ź������ȸ-�ϰ���
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
import com.etax.db.mn06.IR060310;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060310InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR060310InsertCMD.class); // log4j ����

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",           request.getParameter("reg_date"));  //�����ܾ��� ���α׷��� ����
        paramInfo.setValue("acc_year",           paramInfo.getString("acc_date").substring(0,4));
        paramInfo.setValue("acc_type",           "A");
        paramInfo.setValue("acc_code",           "11");
        paramInfo.setValue("part_code",          "00000");
        paramInfo.setValue("work_flag",          "1");

		String[] chk_list   = request.getParameterValues("allotChk");
		String[] seq_list   = request.getParameterValues("seq_list");
		String[] chk_val    = request.getParameterValues("chk_val");
		String[] acct_no    = request.getParameterValues("acct_no");
		String[] jwasu_no   = request.getParameterValues("jwasu_no");
		String[] stat_type  = request.getParameterValues("stat_type");
		String[] due_day    = request.getParameterValues("due_day");
		String[] inamt      = request.getParameterValues("inamt");
		String[] fis_year   = request.getParameterValues("fis_year");

		logger.info("paramInfo : " + paramInfo);
    
        String SucMsg = "";
        String ErrMsg = "";
		String reg_date = request.getParameter("reg_date");

        int len = 0;
		conn.setAutoCommit(false);
		//���ϸ������� üũ
        CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
        //�ܾ��� ��Ͽ��� üũ
        CommonEntity j_cnt = IR060000.JanaekCnt(conn, paramInfo);

        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "���ϸ����� �Ϸ�Ǿ����ϴ�. �ϰ���ó���� �� �� �����ϴ�.";
        } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
            SucMsg = "������ڰ� �������� �ƴմϴ�.";
        } else if ("0".equals(j_cnt.getString("CNT")) ) {
            SucMsg = "���� �ܾ��� �ڷᰡ �����ϴ�. �ܾ��� �ڷ� �Է� �� �õ��ϼ���.";
        } else {
			TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
	        paramInfo.setValue("log_no"  , tli.getLogNo());

	        for (int i=0; i<chk_list.length; i++) {
		  	    int y = Integer.parseInt(chk_list[i]);
		  	    if ("Y".equals(chk_val[y]))	{
		  		    String seq = seq_list[y];
		  		    String acctNo = acct_no[y];
                    String jwasuNo = jwasu_no[y];

                    paramInfo.setValue("deposit_type",   stat_type[y]);
                    paramInfo.setValue("input_amt",      inamt[y]);
                    paramInfo.setValue("due_day",        due_day[y]);
                    paramInfo.setValue("fis_year",       fis_year[y]);
                    paramInfo.setValue("acc_year",       fis_year[y]);
                    paramInfo.setValue("acct_no",        acct_no[y]);
                    paramInfo.setValue("jwasu_no",       jwasu_no[y]);
		  		    
                    CommonEntity accInfo =  IR060000.getAccInfo(conn, paramInfo);

                    if (accInfo.size() == 0 
                        || ("A".equals(accInfo.getString("ACCTYPE")) && "11".equals(accInfo.getString("ACCCODE")))) { //���°� �Ϲ�ȸ���̰ų� ���� ���� �۾�

                        CommonEntity acctCnt =  IR060000.getAccountCnt(conn, paramInfo);

                        if ("G3".equals(stat_type[y])) {
                            //MMDA������ ���°Ǽ� ��ȸ
					        CommonEntity mmda = IR060310.getMmdaCnt(conn, paramInfo);
						    if ("0".equals(mmda.getString("CNT")) )	{
						        if (IR060310.insertMMDA(conn, seq, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //����� �����޽���
		  			            }
						    } else {
							    if (IR060310.updateMMDA(conn, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //����� �����޽���
		  			            }
						    }
                            if (IR060310.insertMMDADetail(conn, seq, paramInfo) < 1 )	{
		  			            conn.rollback();
		  		                conn.setAutoCommit(true);
		  			            throw new ProcessException("E002"); //����� �����޽���
		  			        }
		  			
                        } else {  //���⿹��, ȯ��ü
                        
                            if ("0".equals(acctCnt.getString("CNT")) ) {
                                if (IR060310.insertHwan(conn, seq, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //����� �����޽���
		  			            }
                            }		  			    
		  		        }

                        if ("G1".equals(paramInfo.getString("deposit_type")) ||"G2".equals(paramInfo.getString("deposit_type")) ) {
                            if ("0".equals(acctCnt.getString("CNT")) ) {
                                ErrMsg = IR060000Register.permission(conn, paramInfo);
         
                                len += ErrMsg.length();

                                if (len == 0) {
                                    if (IR060310.updateDeposit(conn, seq, acctNo, jwasuNo, paramInfo) < 1 )	{
		  			                    conn.rollback();
		  		                        conn.setAutoCommit(true);
                                        throw new ProcessException("E003"); //������ �����޽���
		  	                        }
                                    SucMsg = "�ϰ��� ó���� �Ϸ�Ǿ����ϴ�.";
                                } else {
                                    SucMsg = "�ܾ��� ó�� �� ������ �߻��Ͽ����ϴ�.";
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

                            if (len == 0) {
                                if (IR060310.updateDeposit(conn, seq, acctNo, jwasuNo, paramInfo) < 1 )	{
		  			                conn.rollback();
		  		                    conn.setAutoCommit(true);
                                    throw new ProcessException("E003"); //������ �����޽���
		  	                    }
                                SucMsg = "�ϰ��� ó���� �Ϸ�Ǿ����ϴ�.";
                            } else {
                                SucMsg = "�ܾ��� ó�� �� ������ �߻��Ͽ����ϴ�.";
                                conn.rollback();
	                            conn.setAutoCommit(true);
                            }
                        }
                    } else {
                        SucMsg = "�Ϲ�ȸ�谡 �ƴ� ���¸� �Է��Ͽ����ϴ�.";
                    }
                } 
            }
        } 
        conn.commit();
        conn.setAutoCommit(true);
        request.setAttribute("page.mn06.SucMsg", SucMsg);

	    /* �ڱݿ�Ź�䱸��ȸ */
        List<CommonEntity> bankDepositList = IR060310.bankDepositList(conn, paramInfo);
	    request.setAttribute("page.mn06.bankDepositList", bankDepositList);
    }
}