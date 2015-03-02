/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060910DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금인출 요구취소
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
import com.etax.db.mn06.IR060910;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR060910DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060910DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    


    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("acc_date",        request.getParameter("acc_date"));

		String[] chk_list   = request.getParameterValues("allotChk");
		String[] seq_list    = request.getParameterValues("seq_list");
    String[] input_amt    = request.getParameterValues("input_amt");
    String[] acct_no    = request.getParameterValues("acct_no");
    String[] fis_year    = request.getParameterValues("fis_year");
    String[] deposit_type    = request.getParameterValues("deposit_type");
    String[] jwasu_no    = request.getParameterValues("jwasu_no");

		logger.info("paramInfo : " + paramInfo);
    
    String SucMsg = "";
		String acc_date = request.getParameter("acc_date");

    conn.setAutoCommit(false);


	  for (int i=0; i<chk_list.length; i++) {
		  int y = Integer.parseInt(chk_list[i]);

      paramInfo.setValue("seq",       seq_list[y]);  
      paramInfo.setValue("input_amt", input_amt[y]); 
      paramInfo.setValue("acct_no",   acct_no[y]);  
      paramInfo.setValue("fis_year",  fis_year[y]); 
      paramInfo.setValue("jwasu_no",  jwasu_no[y]); 

      if (IR060910.deleteInchul(conn, paramInfo) < 1 )	{  //요구등록 취소
		    conn.rollback();
	      conn.setAutoCommit(true);
  	    throw new ProcessException("E004"); //삭제중 오류메시지
		  } 
      
      if ("G3".equals(deposit_type[y])) {
        if (IR060910.updateMMDAReqAmt(conn, paramInfo) < 1 )	{  //MMDA마스터 요청액 수정
		      conn.rollback();
	        conn.setAutoCommit(true);
  	      throw new ProcessException("E003"); 
		    }

      } else {
        if (IR060910.updateJeongi(conn, paramInfo) < 1) {  //정기환매체 요구등록상태 수정
          conn.rollback();
	        conn.setAutoCommit(true);
          throw new ProcessException("E003");
        }
      }
      
    }

    SucMsg = "취소처리되었습니다.";
	  
    
		conn.commit();
	  conn.setAutoCommit(true);

		List<CommonEntity> inchulReqList = IR060910.getInchulReqList(conn, paramInfo);
		request.setAttribute("page.mn06.inchulReqList", inchulReqList);
		
		request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}