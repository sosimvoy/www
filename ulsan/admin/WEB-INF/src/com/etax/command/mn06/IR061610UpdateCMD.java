 /**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061610UpdateCMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 잔액장자료입력수정(공금예금)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.db.trans.TransDAO;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610UpdateCMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610UpdateCMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
		
		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

        CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("reg_date",            request.getParameter("reg_date"));
		paramInfo.setValue("user_id",             request.getParameter("user_id"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date")).substring(0,4);  //회계연도
		String reg_date    = request.getParameter("reg_date");   //회계일
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //전영업일
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String SucMsg = "";  //메시지
   
		CommonEntity daemonData = IR060000.getDaemonData(conn, reg_date); 
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
		if (!(request.getParameter("reg_date")).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { 
			//영업일 체크
		    SucMsg = "해당일자는 영업일이 아닙니다.";

		} else if (Integer.parseInt(request.getParameter("reg_date")) > Integer.parseInt(TextHandler.getCurrentDate())) {  
			//등록일이 오늘을 지났는지 체크
		    SucMsg = "등록일자가 금일이후입니다.";

		} else if (dailyData.size() == 0) {
			//일일업무 마감 건이 있는지 체크
			SucMsg = "공금예금 등록 후 수정바랍니다.";

		} else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			//업무 마감 여부 체크
			SucMsg = "일일업무가 마감되었습니다.";

		} else if (daemonData.size() > 0 && "N".equals(daemonData.getString("M480_INQUIRYYN")) ) { 
			//데몬 연동 테이블 조회 "N"이면 에러처리
		    SucMsg = "기등록건 처리 중입니다. 잠시 후 시도하세요.";
			
		} else {
            // ----------- 폐쇄기 작업 ---------------//
			if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //폐쇄기면 등록일자가 회계마감일자보다 같거나 작음 
				CommonEntity gwaJanakData = IR060000.getLastJanakData(conn, reg_date);  //과년도 잔액장 자료 존재여부(일반,특별회계만)
			    if (gwaJanakData.size() == 0) {
					//과년도 잔액장 자료 없을 때
				    SucMsg = "과년도 잔액장 자료가 없습니다. 관리자에게 문의바랍니다.";

			    } 
			}  //폐쇄기 끝

            if ("".equals(SucMsg)) {
				// --------- 현년도 작업 ----------------//
				CommonEntity janakData = IR060000.getJanakData(conn, reg_date);  //잔액장 자료 존재여부
				if (janakData.size () == 0)	{
					SucMsg = "현년도 잔액장 자료가 없습니다. 관리자에게 문의바랍니다.";

				} else {

					CommonEntity pcNo = TransDAO.getTmlNo(conn);
					String tml_no = pcNo.getString("M260_TERMINALNO");

					if (IR061610.insertAcctInfo(conn, reg_date, tml_no) < 1) {  
						//데몬 연계테이블에 계좌 넣기
						throw new ProcessException("E002");
					}
              
					if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) { 
                        //폐쇄기면 등록일자가 회계마감일자보다 같거나 작음 
					    if (IR061610.insertAcctInfo2(conn, reg_date, tml_no) < 1) {   
							//데몬 연계테이블에 계좌 넣기(일반,특별회계만)
					        throw new ProcessException("E002");
					    }
					}
				}
        
				SucMsg =  "잔액장 자료가 수정되었습니다.";
            }
		}

		request.setAttribute("page.mn06.SucMsg",   SucMsg);
    } 
}