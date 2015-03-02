/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050310TransCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정승인내역조회/책임자승인요구조회
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

    private static Logger logger = Logger.getLogger(IR050310TransCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 
    
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");
		String SucMsg = "";  //처리결과메시지

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("allot_date",         request.getParameter("allot_date"));
        paramInfo.setValue("acc_date",           request.getParameter("allot_date"));
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));

		String[] allotChk  = request.getParameterValues("allotChk");
		String[] seq_list  = request.getParameterValues("seq_list");
		String[] allot_yn  = request.getParameterValues("allot_yn");
		CommonEntity magamInfo = IR050000.getDailyEndWork(conn, paramInfo);
		if ("Y".equals(magamInfo.getString("M210_WORKENDSTATE")) ) {
            SucMsg = "일일업무가 마감되었습니다. 책임자승인처리를 할 수 없습니다.";
		} else {
		    CommonEntity pcNo = TransferNo.transferNo(conn, user_id);  // 단말번호 조회
			paramInfo.setValue("man_name",           pcNo.getString("M260_USERNAME"));
		    paramInfo.setValue("man_bankno",         pcNo.getString("M260_MANAGERBANKERNO"));
	  	    paramInfo.setValue("man_no",             pcNo.getString("M260_MANAGERNO"));
			paramInfo.setValue("tml_no",             pcNo.getString("M260_TERMINALNO"));
            paramInfo.setValue("send_no",            pcNo.getString("SEND_NO"));
            paramInfo.setValue("trans_manage_no",    pcNo.getString("TRANS_MANAGE_NO"));

		    //로그기록 남기는 클래스및 메소드 호출
            TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
		    paramInfo.setValue("log_no", tli.getLogNo());
      
		    CommonEntity outAcct = IR050310.getOutAcctInfo(conn, paramInfo); //출금계좌정보
            paramInfo.setValue("out_bank_cd",  outAcct.getString("M300_BANKCODE"));
		    paramInfo.setValue("out_acct_no",  outAcct.getString("M300_ACCOUNTNO"));
      
		    logger.info("paramInfo : " + paramInfo);
		    
		    if ("".equals(outAcct.getString("M300_ACCOUNTNO")) || outAcct.getString("M300_ACCOUNTNO") == null) {
                SucMsg = "출금계좌가 존재하지 않습니다.";
            } else {
                int total = 0;
                int cnt = 0;
                conn.setAutoCommit(false);
                for (int i=0; i<allotChk.length; i++)   {
                    int y = Integer.parseInt(allotChk[i]);
                    if ("Y".equals(allot_yn[y])) {
                        total ++;
                        CommonEntity inAcct = IR050310.getInAcctInfo(conn, paramInfo, seq_list[y]);
                        if (!"".equals(inAcct.getString("M300_ACCOUNTNO"))) { //입금계좌 존재할 때만
                            paramInfo.setValue("in_bank_cd",  inAcct.getString("M300_BANKCODE"));
                            paramInfo.setValue("in_acct_no",  inAcct.getString("M300_ACCOUNTNO"));
                            IR050310.updateReAllotPms(conn, paramInfo, seq_list[y]);
                            cnt ++;
                        }
                    }
                }
                
                logger.info("체크건수 : " + total + ", 처리건수 : " + cnt );
                
                if (allotChk.length > 0 && total == cnt) {
                    SucMsg = "책임자 승인처리되었습니다.";
                    conn.commit();
                } else {
                    SucMsg = "입금계좌가 존재하지 않습니다.";
                    conn.rollback();
                }
            
                conn.setAutoCommit(true);
            }
		}
		request.setAttribute("page.mn05.SucMsg",    SucMsg);
    }
}
