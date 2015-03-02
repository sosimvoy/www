/************************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070127SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입월계표(본청광역시세)
*************************************************************************/
 
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
import com.etax.db.mn07.IR070110;
import com.etax.entity.CommonEntity;

public class IR070110AllCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070110AllCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		
		String acc_year = request.getParameter("acc_year");		 //회계연도
		String acc_date = TextHandler.replace(request.getParameter("acc_date"),"-","");		 //회계일자
		String first_date = acc_date.substring(0,6) + "01";    //월초일
		String last_date = acc_date.substring(0,6) + TextHandler.getDaysOfMonth(acc_date.substring(0,4), acc_date.substring(4,6));  //월말일
		String first_business_date = TextHandler.getBusinessDate(conn, first_date);  //월초영업일
		String last_business_date = TextHandler.getAgoBusinessDate(conn, last_date);   //월말영업일
		



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
        last_bungi =  from_year + "0630";
		} else if ((Integer.parseInt(from_year + "0701") <= acc_date_int) && (acc_date_int <= Integer.parseInt(from_year +"0931"))) { 		
				quarter = "3";        
        first_bungi =  from_year + "0701";
        last_bungi =  from_year + "0930";
		}	else if ((Integer.parseInt(from_year + "1001") <= acc_date_int) && (acc_date_int <= Integer.parseInt(to_year +"0310"))) { 	
			  quarter = "4";          
        first_bungi =  from_year + "1001";
        last_bungi =  to_year + "0310";
	  }

		String first_business_bungi_date = TextHandler.getBusinessDate(conn, first_bungi);  //분기초영업일
		String last_business_bungi_date = TextHandler.getAgoBusinessDate(conn, last_bungi);   //분기말영업일  
		
		if(acc_year.equals("") || acc_year == null){
			Date date = new Date();
			acc_year = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy");
		}
		if(acc_date.equals("") || acc_date == null){
			Date date = new Date();
			acc_date = TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyyMMdd");
		}


		if( !last_business_date.equals(acc_date) ) {
		  request.setAttribute("page.mn07.SucMsg", "월말영업일이 아닙니다. 다시 선택하십시오.");
		}
		


		request.setAttribute("page.mn07.first_business_date", first_business_date);
		request.setAttribute("page.mn07.last_business_date", last_business_date);
		
		request.setAttribute("page.mn07.quarter", quarter);
		request.setAttribute("page.mn07.first_business_bungi_date", first_business_bungi_date);
		request.setAttribute("page.mn07.last_business_bungi_date", last_business_bungi_date);

	
	}
}