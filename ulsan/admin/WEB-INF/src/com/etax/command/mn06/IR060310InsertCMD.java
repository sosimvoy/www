/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060310InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁승인조회-일계등록
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

    private static Logger logger = Logger.getLogger(IR060310InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));
        paramInfo.setValue("acc_date",           request.getParameter("reg_date"));  //공통잔액장 프로그램용 일자
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
		//일일마감여부 체크
        CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
        //잔액장 등록여부 체크
        CommonEntity j_cnt = IR060000.JanaekCnt(conn, paramInfo);

        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "일일마감이 완료되었습니다. 일계등록처리를 할 수 없습니다.";
        } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
            SucMsg = "등록일자가 영업일이 아닙니다.";
        } else if ("0".equals(j_cnt.getString("CNT")) ) {
            SucMsg = "금일 잔액장 자료가 없습니다. 잔액장 자료 입력 후 시도하세요.";
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
                        || ("A".equals(accInfo.getString("ACCTYPE")) && "11".equals(accInfo.getString("ACCCODE")))) { //계좌가 일반회계이거나 없을 때만 작업

                        CommonEntity acctCnt =  IR060000.getAccountCnt(conn, paramInfo);

                        if ("G3".equals(stat_type[y])) {
                            //MMDA마스터 계좌건수 조회
					        CommonEntity mmda = IR060310.getMmdaCnt(conn, paramInfo);
						    if ("0".equals(mmda.getString("CNT")) )	{
						        if (IR060310.insertMMDA(conn, seq, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //등록중 오류메시지
		  			            }
						    } else {
							    if (IR060310.updateMMDA(conn, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //등록중 오류메시지
		  			            }
						    }
                            if (IR060310.insertMMDADetail(conn, seq, paramInfo) < 1 )	{
		  			            conn.rollback();
		  		                conn.setAutoCommit(true);
		  			            throw new ProcessException("E002"); //등록중 오류메시지
		  			        }
		  			
                        } else {  //정기예금, 환매체
                        
                            if ("0".equals(acctCnt.getString("CNT")) ) {
                                if (IR060310.insertHwan(conn, seq, paramInfo) < 1 )	{
		  				            conn.rollback();
		  		                    conn.setAutoCommit(true);
		  				            throw new ProcessException("E002"); //등록중 오류메시지
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
                                        throw new ProcessException("E003"); //수정중 오류메시지
		  	                        }
                                    SucMsg = "일계등록 처리가 완료되었습니다.";
                                } else {
                                    SucMsg = "잔액장 처리 중 오류가 발생하였습니다.";
                                    conn.rollback();
	                                conn.setAutoCommit(true);
                                }
                            
                            } else {
                                SucMsg = "이미 등록된 계좌입니다.";
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
                                    throw new ProcessException("E003"); //수정중 오류메시지
		  	                    }
                                SucMsg = "일계등록 처리가 완료되었습니다.";
                            } else {
                                SucMsg = "잔액장 처리 중 오류가 발생하였습니다.";
                                conn.rollback();
	                            conn.setAutoCommit(true);
                            }
                        }
                    } else {
                        SucMsg = "일반회계가 아닌 계좌를 입력하였습니다.";
                    }
                } 
            }
        } 
        conn.commit();
        conn.setAutoCommit(true);
        request.setAttribute("page.mn06.SucMsg", SucMsg);

	    /* 자금예탁요구조회 */
        List<CommonEntity> bankDepositList = IR060310.bankDepositList(conn, paramInfo);
	    request.setAttribute("page.mn06.bankDepositList", bankDepositList);
    }
}