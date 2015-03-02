/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060210DeleteCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁요구취소
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR060210;
import com.etax.db.mn06.IR060000;
import com.etax.entity.CommonEntity;


public class IR060210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060210DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",           request.getParameter("reg_date"));

		String[] chk_list = request.getParameterValues("allotChk");
		String[] seq_list = request.getParameterValues("seq_list");
		String[] chk_val  = request.getParameterValues("chk_val");

    String acc_date = request.getParameter("reg_date");
    String SucMsg = "";

    CommonEntity dailyInfo = IR060000.dailyCheck(conn, acc_date);  //일일마감여부
    if ("Y".equals(dailyInfo.getString("M210_WORKENDSTATE")) ) {
      //일일 마감 완료되었을 때
      SucMsg = "일일마감이 완료되었습니다. 자금예탁요구등록을 할 수 없습니다.";
    } else {
  
	    for (int i=0; i<chk_list.length; i++) {
			  int y = Integer.parseInt(chk_list[i]);
			  if ("Y".equals(chk_val[y]))	{
				  String seq = seq_list[y];
			    if (IR060210.deleteDeposit(conn, seq) < 1 )	{
            throw new ProcessException("E004"); //삭제중 오류메시지
			    }
			  }			
	    }
      SucMsg =  "취소처리되었습니다.";
    }

	  logger.info("paramInfo : " + paramInfo);
    
		/* 자금예탁요구조회 */
    List<CommonEntity> bankDepositList = IR060210.bankDepositList(conn, paramInfo);
		request.setAttribute("page.mn06.bankDepositList", bankDepositList);
		

    request.setAttribute("page.mn06.SucMsg", SucMsg);
  }
}