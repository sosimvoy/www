/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR070210DeleteCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    :  2010-08-27
* 프로그램내용      : 일계/보고서 > 일일업무마감 > 해제
***********************************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn07.IR070210;
import com.etax.entity.CommonEntity;

public class IR070210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR070210DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
	    boolean errChk = false;
		String magamdata = "";
		String accyear   = "";
		String accdate   = "";
		String retMsg    = "";
		int agoCnt = 0;

		magamdata = request.getParameter("magamdata");
		accyear   = magamdata.substring(0,4);
		accdate   = magamdata.substring(4,12);

        CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("accyear", accyear);
        paramInfo.setValue("accdate", accdate);
        paramInfo.setValue("acc_year",    request.getParameter("acc_year"));
		
		logger.info("param : " + paramInfo);

        //일계 테이블 삭제
		if (IR070210.M220DayMagamDelete(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "일일마감 해제 작업중 오류가 발생하였습니다.(errCode1)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //등록중 오류메시지 표시
		} 
                if (IR070210.M270TaxCashDayMagamDelete(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "일일마감 해제 작업중 오류가 발생하였습니다.(errCode2)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //등록중 오류메시지 표시
		} 
                if (IR070210.M210WorkEndMagamUpdate(conn, paramInfo) < 1){
			errChk = true;
			retMsg = "일일마감 해제 작업중 오류가 발생하였습니다.(errCode3)";
			logger.info("retMsg : " + retMsg);
	//		throw new ProcessException("E002"); //등록중 오류메시지 표시
		}

		retMsg = "일일마감 해제 작업이 완료되었습니다.";

		 /* 일일업무마감 현황 조회 */
		List<CommonEntity> magamList = IR070210.getMagamList(conn, paramInfo);
		request.setAttribute("page.mn07.magamList", magamList);
		request.setAttribute("page.mn07.retMsg", retMsg);

        CommonEntity dateData = IR070210.getDateData(conn, paramInfo);
        request.setAttribute("page.mn07.dateData", dateData);
  }
}
