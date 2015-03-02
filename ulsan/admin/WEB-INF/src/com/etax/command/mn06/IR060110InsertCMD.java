/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR060110InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 자금예탁요구등록
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
import com.etax.db.mn06.IR060110;
import com.etax.db.mn06.IR060000;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;


public class IR060110InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR060110InsertCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("fis_year",           request.getParameter("fis_year"));
    paramInfo.setValue("deposit_kind",       request.getParameter("deposit_kind"));
		paramInfo.setValue("new_deposit_amt",    TextHandler.replace(request.getParameter("new_deposit_amt"), ",", ""));
    paramInfo.setValue("x_cnt",              TextHandler.replace(request.getParameter("x_cnt"), ",", ""));
		paramInfo.setValue("rate",               request.getParameter("rate"));
		paramInfo.setValue("deposit_due",        request.getParameter("deposit_due"));
		paramInfo.setValue("end_date",           request.getParameter("end_date"));
	  paramInfo.setValue("mmda_kind",          request.getParameter("mmda_kind"));

    String acc_date = TextHandler.getCurrentDate();
    String SucMsg = "";
  
	  logger.info("paramInfo : " + paramInfo);		
    
    CommonEntity dailyInfo = IR060000.dailyCheck(conn, acc_date);  //일일마감여부
    CommonEntity closeInfo = IR060000.closingCheck1(conn, acc_date);  //폐쇄기체크

    if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date))) {
      SucMsg = "금일은 영업일이 아닙니다.";
    } else if ("Y".equals(dailyInfo.getString("M210_WORKENDSTATE")) ) {
      //일일 마감 완료되었을 때
      SucMsg = "일일마감이 완료되었습니다. 자금예탁요구등록을 할 수 없습니다.";
    } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("fis_year").equals(acc_date.substring(0,4))) {
      //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
      SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
    } else {
    
		  //로그기록 남기는 클래스및 메소드 호출
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

      for (int i=0; i<Integer.parseInt(paramInfo.getString("x_cnt")); i++) {
        if (IR060110.insertDeposit(conn, paramInfo) < 1)	{
			    throw new ProcessException("E002"); //등록중 오류메시지
		    }
      }
		  
	  	SucMsg =  "등록처리되었습니다.";
    }
    request.setAttribute("page.mn06.SucMsg",   SucMsg);
  }
}