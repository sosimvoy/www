/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060410DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁일계등록 조회/취소 - 취소
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
import com.etax.db.mn06.IR060410;
import com.etax.db.mn06.IR060000;
import com.etax.command.mn06.IR060000Register;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060410DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060410DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));
    paramInfo.setValue("acc_date",           request.getParameter("reg_date"));  //공통잔액장 프로그램용 일자
    paramInfo.setValue("acc_type",           "A");
    paramInfo.setValue("acc_code",           "11");
    paramInfo.setValue("part_code",          "00000");
    paramInfo.setValue("work_flag",          "4");

    String SucMsg = "";
    String ErrMsg = "";

		String[] chk_list = request.getParameterValues("allotChk");
		String[] seq_list = request.getParameterValues("seq_list");
		String[] chk_val  = request.getParameterValues("chk_val");
		String[] acct_no  = request.getParameterValues("acct_no");
		String[] stat_type  = request.getParameterValues("stat_type");
		String[] due_day    = request.getParameterValues("due_day");
		String[] fis_year   = request.getParameterValues("fis_year");
		String[] inamt = request.getParameterValues("inamt");
		logger.info("paramInfo : " + paramInfo);
    
		String reg_date = request.getParameter("reg_date");
		int len = 0;
		conn.setAutoCommit(false);
		//일일마감여부 체크
    CommonEntity d_cnt = IR060000.dailyCheck(conn, reg_date);
    if ("Y".equals(d_cnt.getString("M210_WORKENDSTATE")) ) {
			SucMsg = "일일마감이 완료되었습니다. 취소처리를 할 수 없습니다.";
    } else if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) {
      SucMsg = "등록일자가 영업일이 아닙니다.";
    } else {

	    for (int i=0; i<chk_list.length; i++) {
			  int y = Integer.parseInt(chk_list[i]);
			  if ("Y".equals(chk_val[y]))	{
				  String seq = seq_list[y];

          paramInfo.setValue("deposit_type",   stat_type[y]);
          paramInfo.setValue("input_amt",      inamt[y]);
          paramInfo.setValue("due_day",        due_day[y]);
          paramInfo.setValue("fis_year",       fis_year[y]);

          ErrMsg = IR060000Register.permission(conn, paramInfo);
          
          len += ErrMsg.length();

          if (len == 0) {
        
				    //일계등록 => 승인상태
		        if (IR060410.updateDeposit(conn, seq) < 1 )	{
		  	      conn.rollback();
		          conn.setAutoCommit(true);
              throw new ProcessException("E003"); //수정중 오류메시지
		        }
		
            if ("G3".equals(stat_type[y])) {
			        //MMDA Detail삭제
			        if (IR060410.deleteMMDA(conn, seq) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E004"); //삭제중 오류메시지
			        }

		          CommonEntity mmda_cnt = IR060410.getMmdaCnt(conn, fis_year[y], acct_no[y]);
              if ("1".equals(mmda_cnt.getString("M140_SEQ")) ) {
						    //MMDA Master삭제
			          if (IR060410.deleteMmdaMaster(conn, fis_year[y], acct_no[y]) < 1 )	{
				          conn.rollback();
		              conn.setAutoCommit(true);
				          throw new ProcessException("E004"); //삭제중 오류메시지
			          }
              } else {
						    //MMDA Master수정
			          if (IR060410.updateMmdaMaster(conn, fis_year[y], acct_no[y], inamt[y]) < 1 )	{
				          conn.rollback();
		              conn.setAutoCommit(true);
				          throw new ProcessException("E003"); //수정중 오류메시지
			          }
					    }
            } else {  //정기예금, 환매체 삭제
			        if (IR060410.deleteHwan(conn, seq) < 1 )	{
				        conn.rollback();
		            conn.setAutoCommit(true);
				        throw new ProcessException("E004"); //삭제중 오류메시지
			        }
		        }
            SucMsg = "일계등록 취소처리가 완료되었습니다.";
          } else {
            SucMsg = "잔액장 처리 중 오류가 발생하였습니다.";
            conn.rollback();
	          conn.setAutoCommit(true);
          }
			  }			
	    }
    }
    
		conn.commit();
	  conn.setAutoCommit(true);
		/* 자금예탁요구조회 */
    List<CommonEntity> bankRegisterList = IR060410.getBankRegisterList(conn, paramInfo);
		request.setAttribute("page.mn06.bankRegisterList", bankRegisterList);
		
		request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}