/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR051310InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입수기분등록
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
import com.etax.db.mn05.IR051310;
import com.etax.db.mn05.IR050000;
import com.etax.util.TextHandler;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.entity.CommonEntity;

public class IR051310InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR051310InsertCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

    CommonEntity paramInfo = new CommonEntity();
	  paramInfo.setValue("this_year",   request.getParameter("this_year"));		
		paramInfo.setValue("dept_list",   request.getParameter("dept_list"));
		paramInfo.setValue("last_year",   request.getParameter("last_year"));		
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));	
		paramInfo.setValue("acc_gubun",   request.getParameter("acc_gubun"));	
		paramInfo.setValue("acc_list",    request.getParameter("acc_list"));	
		paramInfo.setValue("in_amt",      request.getParameter("in_amt"));	
		paramInfo.setValue("fis_year",    request.getParameter("this_year"));	//일일마감체크 연도
    paramInfo.setValue("work_log",    request.getParameter("work_log"));	 //작업구분
		paramInfo.setValue("trans_gubun", request.getParameter("trans_gubun"));	 //거래구분
    
    logger.info("paramInfo : " + paramInfo);
    
    String acc_date = request.getParameter("acc_date");
    String busi_date = TextHandler.getBusinessDate(conn, acc_date);
    String SucMsg = "";

		CommonEntity endChk = IR050000.getDailyEndWork(conn, paramInfo); //일일마감체크
		CommonEntity semokInfo = IR051310.getSemokInfo(conn, paramInfo); //이월금세목코드 찾기
    CommonEntity closeInfo = IR050000.closingCheck1(conn, acc_date);  //폐쇄기체크
		paramInfo.setValue("semok_cd",    semokInfo.getString("M370_SEMOKCODE"));

		if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      SucMsg = "일일마감이 완료된 회계일자는 잉여금이입수기분등록을 할 수 없습니다.";
		} else if ("".equals(semokInfo.getString("M370_SEMOKCODE")) ) {
			SucMsg = "이월금세목코드가 등록되지 않았습니다. 등록 후 이용바랍니다.";
		} else if ("FALSE".equals(closeInfo.getString("DATE_CHEK")) 
      && !paramInfo.getString("last_year").equals(paramInfo.getString("this_year"))) {
      //폐쇄기간이 아닌데 회계과년도 작업을 시도할 때
      SucMsg = "폐쇄기간(1월1일 ~ 3월10일)을 제외한 기간에 회계과년도 자료 등록을 할 수 없습니다.";
    } else if (!acc_date.equals(busi_date)) {
      SucMsg = "회계일자가 영업일이 아닙니다.";
    }else {

			//로그기록 남기는 클래스및 메소드 호출
      TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no", tli.getLogNo());

			CommonEntity seqInfo = IR051310.getSeqInfo(conn);
			paramInfo.setValue("seq",    seqInfo.getString("M010_SEQ"));

      //잉여금이입명세 등록
			if (IR051310.insertSrpSugi(conn, paramInfo) < 1) {
				throw new ProcessException("E002");
			}
      
			//세입수기분등록
			if (IR051310.insertSeipSugi(conn, paramInfo) < 1) {
				throw new ProcessException("E002");
			}

			SucMsg = "등록처리되었습니다.";
		}
	  
		/* 부서명 조회 */
		List<CommonEntity> deptList = IR051310.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn05.deptList", deptList);

		/* 회계명 조회 */
		List<CommonEntity> accList = IR051310.getAccList(conn, paramInfo);
		request.setAttribute("page.mn05.accList", accList);

    request.setAttribute("page.mn05.SucMsg", SucMsg);

  }
}