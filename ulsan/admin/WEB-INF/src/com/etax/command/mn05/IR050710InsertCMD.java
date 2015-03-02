/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050710InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 자금배정수기분등록
***********************************************************************/

package com.etax.command.mn05;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn05.IR050710;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR050710InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR050710InsertCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("fis_year",    request.getParameter("fis_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
		paramInfo.setValue("allot_kind",  request.getParameter("allot_kind"));
		paramInfo.setValue("acc_type",    request.getParameter("acc_type"));
		paramInfo.setValue("dept_kind",   request.getParameter("dept_kind"));
		paramInfo.setValue("acc_kind",    request.getParameter("acc_kind"));
    paramInfo.setValue("t_dept_kind",   request.getParameter("t_dept_kind"));
		paramInfo.setValue("t_acc_kind",    request.getParameter("t_acc_kind"));
		paramInfo.setValue("sugi_amt",    request.getParameter("sugi_amt"));
    paramInfo.setValue("work_log",    request.getParameter("work_log"));
		paramInfo.setValue("trans_gubun",    request.getParameter("trans_gubun"));
    
    logger.info("paramInfo : " + paramInfo);

    String SucMsg = "";
    String acc_date = request.getParameter("acc_date");
    String acc_kind = request.getParameter("acc_kind");

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //일일마감체크
    CommonEntity closeInfo = IR050000.closingCheck1(conn, acc_date);  //폐쇄기체크
		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      SucMsg = "일일마감이 완료된 회계일자는 자금배정수기분을 등록할 수 없습니다.";
		} else if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
      SucMsg = "회계일자가 영업일이 아닙니다.";
    } else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("fis_year").equals(acc_date.substring(0,4))) {
      //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
      SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
    } else {
	  
		  //로그기록 남기는 클래스및 메소드 호출
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

		  if (IR050710.insertSugi(conn, paramInfo) <1 ) {
			  throw new ProcessException("E002");
		  }

      if ("2".equals(request.getParameter("allot_kind")) && "B".equals(request.getParameter("acc_type")) && 
        Integer.parseInt(acc_kind) >= 32 && Integer.parseInt(acc_kind) <= 36) {  // 재배정

        if (IR050710.insertSechul1(conn, paramInfo) <1 ) {  //세출수기분으로 등록
			    throw new ProcessException("E002");
		    }

      } else if ("4".equals(request.getParameter("allot_kind")) && !"E".equals(request.getParameter("acc_type"))) {  //재배정 반납
        if (("B".equals(request.getParameter("acc_type")) && Integer.parseInt(acc_kind) >= 32 && Integer.parseInt(acc_kind) <= 36) || 
          ("A".equals(request.getParameter("acc_type")) && Integer.parseInt(acc_kind) >= 85 && Integer.parseInt(acc_kind) <= 89)) {
          if (IR050710.insertSechul2(conn, paramInfo) <1 ) {  //세출수기분으로 등록
			      throw new ProcessException("E002");
		      }
        }
      }      

		  SucMsg = "등록되었습니다.";
		}

		/* 부서리스트 */
		List<CommonEntity> deptList = IR050710.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);
		/* 회계리스트 */
		List<CommonEntity> acctList = IR050710.getAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.acctList", acctList);


    request.setAttribute("page.mn05.SucMsg", SucMsg);
  }
}