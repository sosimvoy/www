/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070137SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입분기표(일반회계세목별)
**********************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.util.TextHandler;
import com.etax.util.StringUtil;
import com.etax.db.mn07.IR070137;
import com.etax.db.common.ReportDAO;   
import com.etax.entity.CommonEntity;

public class IR070137SelectCMD extends BaseCommand {
	
  private static Logger logger = Logger.getLogger(IR070137SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");		 //회계연도
		String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");		 //회계일자 

    int acc_date_int = Integer.parseInt(acc_date);  

    String from_year = acc_year;    //회계연도/
    String to_year = "";
	  int last_int = Integer.parseInt(from_year) + 1;                  // 현재년도 + 1 (int -> String)
	  to_year = String.valueOf(last_int);
    
    String quarter = "";
    String first_bungi =  "";
    String last_bungi =  "";

                                              
    /* 분기 */
    if ((Integer.parseInt(from_year + "0101") <= acc_date_int) &&  (acc_date_int <= Integer.parseInt(from_year + "0331"))) {
        quarter = "1";
        first_bungi =  from_year + "0101";
        last_bungi =  from_year + "0331";
		}	else if ( (Integer.parseInt(from_year + "0401") <= acc_date_int)  && (acc_date_int <= Integer.parseInt(from_year + "0631"))) { 
			 	quarter = "2"; 
        first_bungi =  from_year + "0401";
        last_bungi =  from_year + "0631";
		} else if ((Integer.parseInt(from_year + "0701") <= acc_date_int) && (acc_date_int <= Integer.parseInt(from_year +"0931"))) { 		
				quarter = "3";        
        first_bungi =  from_year + "0701";
        last_bungi =  from_year + "0931";
		}	else if ((Integer.parseInt(from_year + "1001") <= acc_date_int) && (acc_date_int <= Integer.parseInt(to_year +"0310"))) { 	
			  quarter = "4";          
        first_bungi =  from_year + "1001";
        last_bungi =  to_year + "0310";
	  }

		String first_business_date = TextHandler.getBusinessDate(conn, first_bungi);  //분기초영업일
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_bungi);   //분기말영업일  

    String part_code = request.getParameter("part_code");

		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}

		if( !"last_business_date".equals(request.getParameter("acc_date")) ) {
		  request.setAttribute("page.mn07.SucMsg", "분기말 최종 영업일이 아닙니다. 다시 선택하십시오.");
		}
	
		paramInfo.setValue("acc_year",	acc_year);		// 회계년도
		paramInfo.setValue("acc_date",	StringUtil.replace(acc_date,"-",""));	// 회계일자
    paramInfo.setValue("acc_type",	request.getParameter("acc_type"));		// 회계구분
		paramInfo.setValue("part_code",	request.getParameter("part_code"));		// 부서코드
		paramInfo.setValue("acc_code",	request.getParameter("acc_code"));		// 회계코드	 
		paramInfo.setValue("first_business_date",	first_business_date);		//분기초영업일
		paramInfo.setValue("last_business_date",	last_business_date);		//분기말영업일

    /* 마감여부	*/
  		CommonEntity endState = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.endState", endState);

		/* 보고서 */ 
      if(part_code.equals("") || part_code == null){
         //2번
		     
         paramInfo.setValue("part_code",	"02130");		// 부서코드
         List<CommonEntity> reportList = IR070137.getReportList(conn, paramInfo);
		     request.setAttribute("page.mn07.reportList", reportList);
         
         paramInfo.setValue("part_code",	"02140");		// 부서코드
         List<CommonEntity> reportList2 = IR070137.getReportList(conn, paramInfo);
		     request.setAttribute("page.mn07.reportList2", reportList2);

      }else{
         // 1번 (해당부서만.)
         List<CommonEntity> reportList = IR070137.getReportList(conn, paramInfo);
		     request.setAttribute("page.mn07.reportList", reportList);

         List<CommonEntity> reportList2 = null;                 
		     request.setAttribute("page.mn07.reportList2", reportList2);
      }

    /* 분기 */ 
	   request.setAttribute("page.mn07.quarter", quarter);
	}
}
