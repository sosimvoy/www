/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR050910InsertCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금배정 > 잉여금이입요구등록
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
import com.etax.db.mn05.IR050910;
import com.etax.db.mn05.IR050000;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR050910InsertCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR050910InsertCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    

        CommonEntity paramInfo = new CommonEntity();
		
		paramInfo.setValue("last_year",    request.getParameter("last_year"));
		paramInfo.setValue("this_year",    request.getParameter("this_year"));
		paramInfo.setValue("out_acct",     request.getParameter("out_acct"));
		paramInfo.setValue("in_acct",      request.getParameter("in_acct"));
		paramInfo.setValue("in_amt",       request.getParameter("in_amt"));
    
        logger.info("paramInfo : " + paramInfo);
    
        String SucMsg = "";
		String to_day = TextHandler.getCurrentDate();
		String std_day = TextHandler.formatDate(TextHandler.getCurrentDate(), "yyyyMMdd", "yyyy") + "0310";

        CommonEntity closeInfo = IR050000.ilbanCheck(conn, paramInfo);  //일반회계폐쇄기체크

		if (Integer.parseInt(to_day) > Integer.parseInt(closeInfo.getString("M320_DATEILBAN")) ) {
            SucMsg = "일반회계 마감일 이후에는 잉여금이입요구등록 작업을 할 수 없습니다.";
		} else if (!to_day.equals(TextHandler.getBusinessDate(conn, to_day)) ) {
            SucMsg = "회계일자가 영업일이 아닙니다.";
        } else {
		    //로그기록 남기는 클래스및 메소드 호출
            TransLogInsert tli = new TransLogInsert();
		    tli.execute(request, response, conn);
		    paramInfo.setValue("log_no", tli.getLogNo());

		    if (IR050910.insertSurplus(conn, paramInfo) <1 ) {
			    throw new ProcessException("E002");
		    }

		    SucMsg = "등록처리되었습니다.";
		}

		/* 출금계좌 */
		List<CommonEntity> outAcctList = IR050910.getOutAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.outAcctList", outAcctList);
		/* 입금계좌 */
		List<CommonEntity> inAcctList = IR050910.getInAcctList(conn, paramInfo);
		request.setAttribute("page.mn05.inAcctList", inAcctList);

        request.setAttribute("page.mn05.SucMsg", SucMsg);
    }
}