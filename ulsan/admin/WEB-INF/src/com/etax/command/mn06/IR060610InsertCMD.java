/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060610InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁(기금,특별회계) 등록
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
import com.etax.command.common.TransLogInsert;  //로그기록 클래스
import com.etax.command.mn06.IR060000Register;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR060610InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR060610InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{   

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
        paramInfo.setValue("acc_year",           request.getParameter("fis_year"));
		paramInfo.setValue("acct_kind",          request.getParameter("acct_kind"));
        paramInfo.setValue("acc_type",           request.getParameter("acct_kind"));//잔액장용 회계구분
        paramInfo.setValue("acc_code",           request.getParameter("acct_list"));//잔액장용 회계코드
        paramInfo.setValue("part_code",          request.getParameter("part_code"));//잔액장용 부서코드
        paramInfo.setValue("work_flag",          "1");  //신규
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
        paramInfo.setValue("acc_date",           request.getParameter("new_date")); //잔액장용 회계일자
        paramInfo.setValue("deposit_type",       request.getParameter("dep_list")); //잔액장용 예금종류
        paramInfo.setValue("input_amt",          request.getParameter("dep_amt"));  //잔액장용 예금
        paramInfo.setValue("due_day",            request.getParameter("dep_due"));  //잔액장용 예탁기간

        String reg_date = request.getParameter("new_date");
        String SucMsg = "";
        String ErrMsg = "";

        int len = 0;
        conn.setAutoCommit(false);
		//일일마감여부 체크
        CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
        CommonEntity closeInfo = IR060000.closingCheck1(conn, reg_date);  //폐쇄기체크
        CommonEntity j_cnt = IR060000.JanaekCnt(conn, paramInfo);
        CommonEntity acctCnt =  IR060000.getAccountCnt(conn, paramInfo);
        if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "일일마감이 완료되었습니다. 등록처리를 할 수 없습니다.";
        } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
            SucMsg = "신규일자가 영업일이 아닙니다.";
        } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
                && !paramInfo.getString("fis_year").equals(reg_date.substring(0,4))) {
            //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
            SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
        } else if ("0".equals(j_cnt.getString("CNT")) ) {
            SucMsg = "금일 잔액장 자료가 없습니다. 잔액장 자료 입력 후 시도하세요.";
        } else {
      
	        logger.info("paramInfo : " + paramInfo);
      
            
            TransLogInsert tli = new TransLogInsert();  //로그번호 채번 클래스
		    tli.execute(request, response, conn);
	        paramInfo.setValue("log_no"  , tli.getLogNo());

            CommonEntity m120Seq = IR060610.getYetakSeq(conn);
            paramInfo.setValue("m120_seq",    m120Seq.getString("SEQNO"));

            //MMDA& 융자금
            if ("G3".equals(request.getParameter("dep_list")) || "G4".equals(request.getParameter("dep_list"))) {
		  	    CommonEntity mmdaInfo = IR060610.getMMDAInfo(conn, paramInfo);
		  	    if ("0".equals(mmdaInfo.getString("CNT")) ) {  //mmda마스터 계좌매칭 건수
		  	        if (IR060610.insertMMDAMaster(conn, paramInfo) < 1 ) {  //mmda마스터정보 등록
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //등록중 오류메시지
		  	        }
		  	    } else {
		  	        if (IR060610.updateMMDAMaster(conn, paramInfo) < 1 )	{  //mmda마스터 금액 수정
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //등록중 오류메시지
		  	        }
		  	    }

                if (IR060610.insertMMDADetail(conn, paramInfo) < 1 )	{  //mmda명세 등록
		  	        conn.rollback();
		            conn.setAutoCommit(true);
		  	        throw new ProcessException("E002"); //등록중 오류메시지
		  	    }
		  	
            } else {
                
                if ("0".equals(acctCnt.getString("CNT")) ) {
                    //정기예금, 환매체
		  	        if (IR060610.insertHwan(conn, paramInfo) < 1 )	{
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //등록중 오류메시지
		  	        }
                } else {
                    SucMsg = "이미 등록된 계좌입니다.";
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
		    }
            
            if ("G1".equals(request.getParameter("dep_list")) ||"G2".equals(request.getParameter("dep_list")) ) {
                if ("0".equals(acctCnt.getString("CNT")) ) {
                    ErrMsg = IR060000Register.permission(conn, paramInfo);
         
                    len += ErrMsg.length();

                    if (len == 0)  {
                        //예탁 테이블 등록
                        if (IR060610.insertYetak(conn, paramInfo) < 1 )	{
		  	                conn.rollback();
		                    conn.setAutoCommit(true);
		  	                throw new ProcessException("E002"); //등록중 오류메시지
		  	            }

                        SucMsg = "등록처리되었습니다.";
                    } else {
                        SucMsg = "잔액장 처리중 오류가 발생하였습니다.";
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

                if (len == 0)  {
                    //예탁 테이블 등록
                    if (IR060610.insertYetak(conn, paramInfo) < 1 )	{
		  	            conn.rollback();
		                conn.setAutoCommit(true);
		  	            throw new ProcessException("E002"); //등록중 오류메시지
		  	        }

                    SucMsg = "등록처리되었습니다.";
                } else {
                    SucMsg = "잔액장 처리중 오류가 발생하였습니다.";
                    conn.rollback();
                    conn.setAutoCommit(true);
                }
            }
        }

        conn.commit();
        conn.setAutoCommit(true);
    
		/* 회계 종류 조회 */
		List<CommonEntity> acctList = IR060610.getAcctList(conn);    
		request.setAttribute("page.mn06.acctList", acctList);

    /* 부서 종류 조회 */
		List<CommonEntity> partList = IR060610.getPartList(conn);    
		request.setAttribute("page.mn06.partList", partList);

    request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}