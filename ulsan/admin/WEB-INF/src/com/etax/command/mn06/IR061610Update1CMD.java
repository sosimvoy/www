 /**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061610Update1CMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 잔액장자료입력수정(기타예금)
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
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610Update1CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610Update1CMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date2"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date2").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date2")).substring(0,4);  //회계연도
		String reg_date    = request.getParameter("reg_date2");   //회계일
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //전영업일
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String city_byul   = request.getParameter("city_byul");
		String ulsan_byul   = request.getParameter("ulsan_byul");

		String[] seip_content   = request.getParameterValues("seip_content");
		String[] seip_amt       = request.getParameterValues("seip_amt");
		String[] sechul_content = request.getParameterValues("sechul_content");
		String[] sechul_amt     = request.getParameterValues("sechul_amt");

		String SucMsg = new String();  //메시지
    
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);
		if (!(reg_date).equals(TextHandler.getBusinessDate(conn, reg_date)) ) { 
			//영업일 체크
		    SucMsg = "해당일자는 영업일이 아닙니다.";
		} else if (Integer.parseInt(reg_date) > Integer.parseInt(TextHandler.getCurrentDate())) {  
			//등록일이 오늘을 지났는지 체크
		    SucMsg = "등록일자가 금일이후입니다.";
		} else if (dailyData.size() == 0) {
			//일일업무 마감 건이 있는지 체크
			SucMsg = "공금예금 등록 후 수정바랍니다.";
		} else if (!"Y".equals(dailyData.getString("M210_ETCDEPOSITSTATE")) ) {
            //기타예금을 등록했는지 여부
            SucMsg = "기타예금 등록 후 수정바랍니다.";
        } else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			//업무 마감 여부 체크
			SucMsg = "일일업무가 마감되었습니다.";
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
					if (IR061610.updateEtcJanak(conn, reg_date, city_byul, ulsan_byul, seip_content, seip_amt, sechul_content, sechul_amt) < 1) {  //잔액장 현년도 수정
			            throw new ProcessException("E003");
			        }
				}
				request.setAttribute("page.mn06.SucMsg",   "잔액장 자료가 수정되었습니다.");
            } else if (!"".equals(SucMsg)) {
				//에러메시지 저장
				request.setAttribute("page.mn06.SucMsg",   SucMsg);
            }
		}
    }
}
