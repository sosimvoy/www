/************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR011010UpdateCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 세입 > 세입금 정정 결과통지
*************************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR011010;
import com.etax.entity.CommonEntity;
import com.etax.command.common.TransLogInsert;

public class IR011010UpdateCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR011010UpdateCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

    CommonEntity paramInfo = new CommonEntity();
    paramInfo.setValue("fis_year",         request.getParameter("fis_year"));	  //회계연도
		paramInfo.setValue("odate",            request.getParameter("odate"));		  //정정일자
    paramInfo.setValue("seq",              request.getParameter("seq"));		    //일련번호
    
    System.out.println("seq안받나??:::"+request.getParameter("seq"));
     //로그기록 남기는 클래스및 메소드 호출
     TransLogInsert tli = new TransLogInsert();
     tli.execute(request, response, conn);
     paramInfo.setValue("log_no", tli.getLogNo());

     logger.info("paramInfo : " + paramInfo);		

		 if (IR011010.updateTaxinState(conn, paramInfo) < 1)	{
		   throw new ProcessException("E003"); //수정중 오류메시지
		 }
     request.setAttribute("page.mn01.SucMsg", "결과통지되었습니다.");

    List<CommonEntity> expLedgerList = IR011010.getExpLedgerList(conn, paramInfo);
    request.setAttribute("page.mn01.expLedgerList", expLedgerList);
  }
}