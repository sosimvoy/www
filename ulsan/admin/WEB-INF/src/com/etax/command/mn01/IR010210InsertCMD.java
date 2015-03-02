/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010210InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-01
* 프로그램내용	 : 세입 > 수기분등록
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR010210;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn01.IR010000;
import com.etax.command.common.TransLogInsert;  
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR010210InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010210InsertCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
	  CommonEntity paramInfo = new CommonEntity();

    paramInfo.setValue("fis_year",          request.getParameter("fis_year"));		 //회계연도
    paramInfo.setValue("fis_date",          request.getParameter("fis_date"));		 //회계일자
    paramInfo.setValue("intype",            request.getParameter("intype"));			 //입력구분
    paramInfo.setValue("gigwan",            request.getParameter("gigwan"));			 //수납기관
    paramInfo.setValue("part_code",         request.getParameter("part_code"));		 //부서
    paramInfo.setValue("acc_code",          request.getParameter("acc_code"));		 //회계코드(명)

    String[] semok_list = request.getParameterValues("semok_code");
    String[] amt_list = request.getParameterValues("amt");
    String[] cnt_list = request.getParameterValues("cnt");
    
    CommonEntity endChk = IR010000.getDailyEndWork(conn, paramInfo); //일일마감체크

    if ("Y".equals(endChk.getString("M210_WORKENDSTATE")))	{
      request.setAttribute("page.mn01.FailMsg", "일일마감이 완료된 회계일자는 등록할 수 없습니다.");
    
    } else {
     
      CommonEntity dayChk = IR010000.getEndYear(conn, paramInfo); //마감일체크 
        
      if ( Integer.parseInt(request.getParameter("fis_date")) > Integer.parseInt(dayChk.getString("M320_DATEILBAN")) )	{
        request.setAttribute("page.mn01.FailMsg", "유효하지 않은 날짜입니다. 확인 후 다시 등록하세요.");
    
      } else {

        //로그기록 남기는 클래스및 메소드 호출
        TransLogInsert tli = new TransLogInsert();
        tli.execute(request, response, conn);
        paramInfo.setValue("log_no", tli.getLogNo());

		    for (int i=0; i<semok_list.length; i++)	{
	        if ( !"".equals(amt_list[i]) || !"".equals(cnt_list[i])) {
			      paramInfo.setValue("semok_code",        semok_list[i]);	      
		        paramInfo.setValue("amt",               TextHandler.unformatNumber(amt_list[i]));					 
		        paramInfo.setValue("cnt",               cnt_list[i]);	
                if ("1130100".equals(semok_list[i])) {
                    paramInfo.setValue("yeartype",     "Y2");
                } else {
                    paramInfo.setValue("yeartype",     "Y1");
                }

				    logger.info("paramInfo : " + paramInfo);		
              	
            if (IR010210.writeInsert(conn, paramInfo) < 1) {
              throw new ProcessException("E002"); //등록중 오류메시지
            }
          }
          
        }
        request.setAttribute("page.mn01.SucMsg", "등록처리되었습니다.");	
      }
    }
    
    /* 리스트 조회 */
		CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date);  
         
		List<CommonEntity> accDeptList = IR010210.getAccDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.accDeptList", accDeptList);
	 
	  List<CommonEntity> accNmList = IR010210.getAccNmList(conn, paramInfo);
    request.setAttribute("page.mn01.accNmList", accNmList); 

		List<CommonEntity> accSemokList = IR010210.getAccSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.accSemokList", accSemokList); 

		List<CommonEntity> gigwanList = IR010210.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);
	}
} 