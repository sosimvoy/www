/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR070310DeleteCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    : 2010-10-01
* 프로그램내용      : 일계/보고서 > 회계년도이월 > 취소 (회계구분&코드 건별 처리)
* 프로그램비고      : 1.회계구분(A,B,E/D)에 따른 마감일자로 일계테이블 마감대상 선택 
                      2.수기분테이블 DELETE (회계구분별 수기테이블 상이) - 회계일자는 마감일자 익영업일
                        (삭제기준: 회계년도, 이월회계년도, 회계일자, 회계구분, 회계코드, 업무구분, 거래구분)
                      3.회계년도이월 테이블 UPDATE (이월여부,총계)
***********************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.util.*;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070310;
import com.etax.entity.CommonEntity;

public class IR070310DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070310DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
       boolean errChk      = false;
		int     insertCnt   = 0;
		String  retMsg      = "";
        
        String  trans_data  = request.getParameter("trans_data");
        String  next_year   = request.getParameter("next_year");
        String  acc_year    = request.getParameter("acc_year");
        String  acc_type    = request.getParameter("acc_type");
        String  work_log    = request.getParameter("work_log");     // 업무구분
        String  trans_gubun = request.getParameter("trans_gubun");  // 거래구분
        String  acc_code    = trans_data.substring(0,2);            // 회계코드
        String  acc_date    = trans_data.substring(2,10);           // 회계일자(기준일=마감일)
        String  max_seq     = "";                                   // 수기테이블 일련번호MAX
        
        // 등록시 회계일자 = 마김일에서 추출한 기준일의 익영업일 (수기 테이블만 해당)
        String reg_acc_date = TextHandler.getBusinessDate(conn,TextHandler.addDays(acc_date,1));

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("next_year",    next_year);  // 이월년도
        paramInfo.setValue("acc_year",     acc_year);
        paramInfo.setValue("acc_type",     acc_type);
        paramInfo.setValue("acc_code",     acc_code);
        paramInfo.setValue("acc_date",     acc_date);
		paramInfo.setValue("work_log",     work_log);
		paramInfo.setValue("trans_gubun",  trans_gubun);
		paramInfo.setValue("reg_acc_date", reg_acc_date);   // 이월데이터 등록시 회계일자 
		
		logger.info("param : " + paramInfo);
        
        conn.setAutoCommit(false);
        
        if(acc_type.equals("D")){   // 세입세출외현금(D) 이월

            /* 1.세입세출외현금 수기테이블 삭제 */
            insertCnt = IR070310.setTaxInCashDelete(conn, paramInfo);
            if (insertCnt > 0){
                
                /* 2. 세입세출외현금 회계년도이월 테이블 UPDATE (RESET) */
               if(IR070310.setAccTransUpdateReset(conn, paramInfo) < 1 ) {
                   
                   errChk = true;
                   retMsg = "회계연도이월- RESET UPDATE 작업중 오류가 발생하였습니다.(errCode1)";
                   logger.info("retMsg : " + retMsg);
                   conn.rollback();
                   conn.setAutoCommit(true);
                   throw new ProcessException("E003"); //등록중 오류메시지 표시
                }

			}else{
                errChk = true;
                retMsg = "회계연도이월-세입세출외현금 수기테이블 삭제 작업중 오류가 발생하였습니다.(errCode2)";
                logger.info("retMsg : " + retMsg);
                conn.rollback();
                conn.setAutoCommit(true);
                throw new ProcessException("E002"); //등록중 오류메시지 표시
            }

        }else{      // 일반,특별,기금(A,B,E) 이월

            /* 1. 세입 수기테이블 삭제 */
            insertCnt = IR070310.setTaxInDelete(conn, paramInfo);
            if (insertCnt > 0){
                
                
               /* 2. 세입 회계년도이월 테이블 UPDATE (RESET)  */
               if(IR070310.setAccTransUpdateReset(conn, paramInfo) < 1 ) {
                   
                   errChk = true;
                   retMsg = "회계연도이월- RESET UPDATE 작업중 오류가 발생하였습니다.(errCode3)";
                   logger.info("retMsg : " + retMsg);
                   conn.rollback();
                   conn.setAutoCommit(true);
                   throw new ProcessException("E003"); //등록중 오류메시지 표시
               }
                
			}else{
                errChk = true;
                retMsg = "회계연도이월-세입 수기테이블 삭제 작업중 오류가 발생하였습니다.(errCode4)";
                logger.info("retMsg : " + retMsg);
                conn.rollback();
                conn.setAutoCommit(true);
                throw new ProcessException("E002"); //등록중 오류메시지 표시
            }
        }

        conn.commit();
        conn.setAutoCommit(true);

        retMsg = "회계년도이월 취소 작업이 완료되었습니다.";
        
        
        paramInfo.setValue("end_date",     acc_date);
        if(acc_type.equals("D")){
             /* 2-1.이월대상조회 현황 조회(D) */
            List<CommonEntity> transList = IR070310.getTransCashList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }else{
             /* 2-2.이월대상조회 현황 조회(A,B,E) */
            List<CommonEntity> transList = IR070310.getTransList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }
		request.setAttribute("page.mn07.retMsg", retMsg);
    }
}