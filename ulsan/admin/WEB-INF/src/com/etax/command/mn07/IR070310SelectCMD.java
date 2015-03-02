/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR070310SelectCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    : 2010-10-01
* 프로그램내용      : 일계/보고서 > 회계년도이월
* 프로그램비고      : 회계구분(A,B,E/D)에 따른 마감일자로 일계테이블 마감대상 조회 
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import com.etax.util.*;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070310;
import com.etax.entity.CommonEntity;
import com.etax.trans.GNBDaemonCall;

public class IR070310SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070310SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
        CommonEntity paramInfo = new CommonEntity();
        
        String acc_year     = request.getParameter("acc_year");
        String next_year    = request.getParameter("next_year");
        String acc_type     = request.getParameter("acc_type");
        String end_date     = "";   // 기준일자(회계구분별 마감일자)

		paramInfo.setValue("acc_year",  acc_year);
		paramInfo.setValue("next_year", next_year);
		paramInfo.setValue("acc_type",  acc_type);
        
         /* 1.마감일(기준일) 조회 */
        CommonEntity closeDate = IR070310.getCloseDate(conn, paramInfo);
        
        if(acc_type.equals("A")){   // 일반회계(A)
            end_date = closeDate.getString("M320_DATEILBAN");
        }else if(acc_type.equals("B")){   // 특별회계(B)
            end_date = closeDate.getString("M320_DATETEKBEYL");
        }else if(acc_type.equals("D")){   // 세입세출외현금(D)
            end_date = closeDate.getString("M320_DATEHYUNGEUM");
        }else if(acc_type.equals("E")){   // 기금(E)
            end_date = closeDate.getString("M320_DATEGIGEUM");
        }
        
        logger.info("end_date::"+end_date);

		paramInfo.setValue("end_date",  end_date);


        if(acc_type.equals("D")){
             /* 2-1.이월대상조회 현황 조회(D) */
            List<CommonEntity> transList = IR070310.getTransCashList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }else{
             /* 2-2.이월대상조회 현황 조회(A,B,E) */
            List<CommonEntity> transList = IR070310.getTransList(conn, paramInfo);
            request.setAttribute("page.mn07.transList", transList);
        }
    }
}