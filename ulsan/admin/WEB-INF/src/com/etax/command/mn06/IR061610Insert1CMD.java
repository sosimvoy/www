/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR061610Insert1CMD.java
* 프로그램작성자    :
* 프로그램작성일    :
* 프로그램내용      : 자금운용 > 잔액장자료입력(기타예금)
***********************************************************************/

package com.etax.command.mn06;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn06.IR061610;
import com.etax.db.mn06.IR060000;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;


public class IR061610Insert1CMD extends BaseCommand {

    private static Logger logger = Logger.getLogger(IR061610Insert1CMD.class); // log4j 설정

    public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{ 

        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("reg_date",            request.getParameter("reg_date2"));
        paramInfo.setValue("last_year",           Integer.parseInt(request.getParameter("reg_date2").substring(0,4)) -1 + "");

        String fis_year    = (request.getParameter("reg_date2")).substring(0,4);  //회계연도
		String reg_date    = request.getParameter("reg_date2");   //회계일
		String before_date = TextHandler.getBeforeDate(conn, reg_date);  //전영업일
		String after_date  = TextHandler.getBusinessDate(conn, TextHandler.addDays(reg_date, 1));  //익영업일
		String after_year  = after_date.substring(0,4);  //익영업일 회계연도
		String city_byul   = request.getParameter("city_byul");
		String ulsan_byul   = request.getParameter("ulsan_byul");
        String magam_date  = IR060000.ilbanCheck(conn, paramInfo).getString("M320_DATEILBAN");

		String[] seip_content   = request.getParameterValues("seip_content");
		String[] seip_amt       = request.getParameterValues("seip_amt");
		String[] sechul_content = request.getParameterValues("sechul_content");
		String[] sechul_amt     = request.getParameterValues("sechul_amt");
		
    
		CommonEntity dailyData = IR060000.dailyCheck(conn, reg_date);  //일일마감체크

		if (!reg_date.equals(TextHandler.getBusinessDate(conn, reg_date)) ) { //영업일 체크
		    request.setAttribute("page.mn06.SucMsg",   "해당일자는 영업일이 아닙니다.");

		} else if (Integer.parseInt(reg_date) > Integer.parseInt(TextHandler.getCurrentDate())) {  //등록일이 오늘을 지났는지 체크
		    request.setAttribute("page.mn06.SucMsg",   "등록일자가 금일이후입니다.");

		} else if (dailyData.size() == 0){
			request.setAttribute("page.mn06.SucMsg",   "공금예금잔액 등록 후 기타예금 잔액을 등록하세요.");

		} else if ("Y".equals(dailyData.getString("M210_WORKENDSTATE")) )	{
			request.setAttribute("page.mn06.SucMsg",   "일일업무가 마감되어 기타예금 잔액 등록을 할 수 없습니다.");

		} else if ("Y".equals(dailyData.getString("M210_ETCDEPOSITSTATE")) ) {
			request.setAttribute("page.mn06.SucMsg",   "기타예금 잔액 등록이 마감되었습니다. 조회 후 기타예금 잔액을 수정바랍니다.");

		} else {
            // ----------- 폐쇄기 작업 ---------------//
			if (Integer.parseInt(reg_date) <= Integer.parseInt(magam_date)) {  //폐쇄기면 등록일자가 회계마감일자보다 같거나 작음

			    if (IR061610.upateEtcMagam2(conn, reg_date) < 1) {   //기타예금 마감 "Y"로 수정(과년도)
			        throw new ProcessException("E003");
			    }

			}  //폐쇄기 끝


			// --------- 현년도 작업 ----------------//
  
			if (IR061610.updateEtcJanak(conn, reg_date, city_byul, ulsan_byul, seip_content, seip_amt, sechul_content, sechul_amt) < 1) {  //잔액장 현년도 수정
			    throw new ProcessException("E003");
			}

			if (IR061610.upateEtcMagam(conn, reg_date) < 1) {  //기타예금 마감 "Y"로 수정
			    throw new ProcessException("E003");
			}

			request.setAttribute("page.mn06.SucMsg",   "기타예금 잔액이 등록되었습니다.");
			
		}
    }
}