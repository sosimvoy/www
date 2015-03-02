/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030310InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-30
* 프로그램내용	   : 세입세출외현금 > 수입증지불출현황 등록
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.command.common.TransLogInsert;
import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030310;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030310InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030310InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();
  
		paramInfo.setValue("year",                   request.getParameter("date").substring(0,4));
		paramInfo.setValue("date",                   request.getParameter("date"));
		
		String date = request.getParameter("date");

        String SucMsg = "";

        int ret = 0;

		CommonEntity stampDateView = IR030310.getStampDateView(conn, paramInfo);  
	  
        conn.setAutoCommit(false);

		if ("JONJAE".equals(stampDateView.getString("TT")))	{
		  SucMsg =  "자료가 존재합니다.";
		} else if("SUNSEO".equals(stampDateView.getString("TT"))){
	    SucMsg =   "일자순서대로 등록하세요";
		} else if("FALSE".equals(stampDateView.getString("TT"))){
	    SucMsg =   "가장 최근자료보다 이전 자료는 등록할 수 없습니다.";
		} else if(!(request.getParameter("date")).equals(TextHandler.getBusinessDate(conn, date)) ) { //영업일 체크
		  SucMsg =   "해당일자는 영업일이 아닙니다.";
		} else {
      
            CommonEntity MaxInfo = IR030310.getMaxDateView(conn);  //가장 최근 등록일, 가장 최근 년도
            paramInfo.setValue("max_year",    MaxInfo.getString("MAX_YEAR"));  //가장 최근년도
            paramInfo.setValue("max_date",    MaxInfo.getString("MAX_DATE"));  //가장 최근 등록일

            TransLogInsert tli = new TransLogInsert();
            tli.execute(request, response, conn);
            paramInfo.setValue("log_no", tli.getLogNo());

            String[] gueonjong_list = request.getParameterValues("gueonjong");
            String[] create_list = request.getParameterValues("create");
            String[] ldisuse_list = request.getParameterValues("ldisuse");
            String[] gumgosale_list = request.getParameterValues("gumgosale");
            String[] citysale_list = request.getParameterValues("citysale");
            String[] citydivide_list = request.getParameterValues("citydivide");

            CommonEntity rowData = new CommonEntity(); //전년이월액

            for (int i=0; i<gueonjong_list.length; i++)	{

                paramInfo.setValue("gueonjong",        gueonjong_list[i]);
                paramInfo.setValue("create",           TextHandler.unformatNumber(create_list[i]));
                paramInfo.setValue("ldisuse",          TextHandler.unformatNumber(ldisuse_list[i]));
                paramInfo.setValue("gumgosale",        TextHandler.unformatNumber(gumgosale_list[i]));
                paramInfo.setValue("citysale",         TextHandler.unformatNumber(citysale_list[i]));
                paramInfo.setValue("citydivide",       TextHandler.unformatNumber(citydivide_list[i]));	
                CommonEntity janryang = IR030310.getJanryang(conn, paramInfo);  //잔량구하기
                paramInfo.setValue("gumgorest",        janryang.getString("SIGUMGO_JAN"));
			    paramInfo.setValue("cityrest",         janryang.getString("CITY_JAN"));

                if ("FALSE".equals(janryang.getString("SIGUMGO_JAN")) ) {
                    SucMsg =  "시금고잔량이 마이너스입니다. 확인 후 다시 등록하시기 바랍니다.";
                    ret = IR030310.deleteData(conn, paramInfo);
                    if (ret < 1) {  //등록 자료 삭제
                        SucMsg = "시금고잔량이 마이너스입니다. 확인 후 다시 등록하시기 바랍니다.";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                    break;
                } else if ("FALSE".equals(janryang.getString("CITY_JAN")) ) {
                    SucMsg =  "시청지점잔량이 마이너스입니다. 확인 후 다시 등록하시기 바랍니다.";
                    ret = IR030310.deleteData(conn, paramInfo);
                    if (ret < 1) {  //등록 자료 삭제
                        SucMsg = "시청지점잔량이 마이너스입니다. 확인 후 다시 등록하시기 바랍니다.";
                        conn.rollback();
                        conn.setAutoCommit(true);
                    }
                    break;
                } else {

				    rowData = IR030310.getLastRestView(conn, paramInfo);  //전년이월 및 전일 누계

				    if (Integer.parseInt(paramInfo.getString("max_year")) < Integer.parseInt(paramInfo.getString("year")) ) {
				    
			            paramInfo.setValue("lastyear_amt",      rowData.getString("LASTREST"));
					    paramInfo.setValue("total_create",      "0");
                        paramInfo.setValue("total_disuse",      "0");
                        paramInfo.setValue("total_gumgosale",   "0");
                        paramInfo.setValue("total_citysale",    "0");
			
				    } else {

				        paramInfo.setValue("lastyear_amt",      rowData.getString("M050_LASTYEAR"));
                        paramInfo.setValue("total_create",      rowData.getString("M050_TOTALCREATE"));
			  	        paramInfo.setValue("total_disuse",      rowData.getString("M050_TOTALDISUSE"));
			  	        paramInfo.setValue("total_gumgosale",   rowData.getString("M050_TOTALGUMGOSALE"));
			  	        paramInfo.setValue("total_citysale",    rowData.getString("M050_TOTALCITYSALE"));
				    }
          logger.info("paramInfo : " + paramInfo);
           /* 수입증지 등록*/ 
	        if(IR030310.insertStamp(conn, paramInfo) < 1 ) {
            conn.rollback();
            conn.setAutoCommit(true);
		        SucMsg =  "수입증지 등록 중 오류가 발생하였습니다.";
            if (IR030310.deleteData(conn, paramInfo) < 1) {  //등록 자료 삭제
              SucMsg = "자료 삭제 중 오류가 발생하였습니다.";
              conn.rollback();
              conn.setAutoCommit(true);
            }
            break;
	        }
        }
			}
		}	

    if (!"".equals(SucMsg)) {
      request.setAttribute("page.mn03.SucMsg", SucMsg);
      conn.rollback();
      conn.setAutoCommit(true);
    } else {
		  request.setAttribute("page.mn03.SucMsg", "등록되었습니다.");
    }

    conn.commit();
    conn.setAutoCommit(true);
 
	   /*수입 증지상세  */
		List<CommonEntity> stampList = IR030310.getStampList(conn, paramInfo);
		request.setAttribute("page.mn03.stampList", stampList);		

	}
}