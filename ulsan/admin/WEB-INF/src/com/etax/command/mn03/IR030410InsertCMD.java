/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030410InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-30
* 프로그램내용	   : 세입세출외현금 > 주행세등록
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
import com.etax.db.mn03.IR030410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030410InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030410InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        String SucMsg = ""; //메시지
        CommonEntity paramInfo = new CommonEntity();

	    paramInfo.setValue("year",                   request.getParameter("acc_date").substring(0,4));
	    paramInfo.setValue("acc_date",               request.getParameter("acc_date"));
	    paramInfo.setValue("jingsuType",             request.getParameter("jingsuType"));		
	    paramInfo.setValue("repayType",              request.getParameter("repayType"));
		paramInfo.setValue("cashType",               request.getParameter("cashType"));
		paramInfo.setValue("amt",                    request.getParameter("amt"));
		paramInfo.setValue("napseja",                request.getParameter("napseja"));
		paramInfo.setValue("logno",                  request.getParameter("logno"));

        CommonEntity maxInfo = IR030410.getFisDate(conn);
        paramInfo.setValue("max_date",    maxInfo.getString("M070_DATE"));

        String bigyo_date = TextHandler.addDays(paramInfo.getString("max_date"), -124);
        String first_date = TextHandler.getBusinessDate(conn, TextHandler.getCurrentDate().substring(0,4)+"0101");

        if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt("20101001")) {
            SucMsg = "2010년 10월 1일 자료 이전은 등록할 수 없습니다.";
        } else if (Integer.parseInt(bigyo_date) >  Integer.parseInt(paramInfo.getString("acc_date")) ) {
            SucMsg =  "최종 자료일자에서 124일 이전 날짜로 등록할 수 없습니다.";
        } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
            SucMsg = "등록일이 영업일이 아닙니다.";
        } else if (Integer.parseInt(paramInfo.getString("max_date")) <  Integer.parseInt(paramInfo.getString("acc_date"))) {
            SucMsg = "주행세 일일마감후 주행세 등록을 시도하시기 바랍니다.";
        } else {
		    String jingsuType= request.getParameter("jingsuType");
		    String repayType= request.getParameter("repayType");
		    String cashType= request.getParameter("cashType");

            if (jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A1")){
                paramInfo.setValue("col_name", "M070_DAYSUIPSUMAMT"); 
                paramInfo.setValue("cnt_name", "M070_DAYSUIPSUMCNT");
            } else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A2")){
                paramInfo.setValue("col_name", "M070_DAYGWAONAPSUMAMT"); 
                paramInfo.setValue("cnt_name", "M070_DAYGWAONAPSUMCNT");     	
            } else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_DAYSUIPSUMINTEREST"); 
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_DAYGWAONAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_SDAYSUIPSUMAMT");
                paramInfo.setValue("cnt_name", "M070_SDAYSUIPSUMCNT");  
            } else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_SDAYGWAONAPSUMAMT"); 
                paramInfo.setValue("cnt_name","M070_SDAYGWAONAPSUMCNT");                                 
            } else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A1")){
                paramInfo.setValue("col_name","M070_SDAYSUIPSUMINTEREST"); 
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A2")){
                paramInfo.setValue("col_name","M070_SDAYGWAONAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_DAYJIGUPSUMAMT");
                paramInfo.setValue("cnt_name","M070_DAYJIGUPSUMCNT");                        
			} else if(jingsuType.equals("J1") && repayType.equals("R1") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_DAYBANNAPSUMAMT");
                paramInfo.setValue("cnt_name","M070_DAYBANNAPSUMCNT");                      
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_DAYJIGUPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J1") && repayType.equals("R2") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_DAYBANNAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_SDAYJIGUPSUMAMT");
                paramInfo.setValue("cnt_name","M070_SDAYJIGUPSUMCNT");                                  
			} else if(jingsuType.equals("J2") && repayType.equals("R1") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_SDAYBANNAPSUMAMT");
                paramInfo.setValue("cnt_name","M070_SDAYBANNAPSUMCNT");                                   
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A3")){
                paramInfo.setValue("col_name","M070_SDAYJIGUPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
			} else if(jingsuType.equals("J2") && repayType.equals("R2") && cashType.equals("A4")){
                paramInfo.setValue("col_name","M070_SDAYBANNAPSUMINTEREST");
                paramInfo.setValue("cnt_name", "");
            }
				
	        //로그기록 남기는 클래스및 메소드 호출
            TransLogInsert tli = new TransLogInsert();
	        tli.execute(request, response, conn);
	        paramInfo.setValue("log_no", tli.getLogNo());
            logger.info("paramInfo : " + paramInfo);
    
		    /* 주행세 등록 */
	        if(IR030410.insertjuheang(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //등록중 오류메시지 표시
	        }

            if (Integer.parseInt(paramInfo.getString("acc_date")) == Integer.parseInt(paramInfo.getString("max_date")) ) { 
                //최종일자와 같은 일자
                if(IR030410.updatejuheangDay(conn, paramInfo) < 1 ) {
                    throw new ProcessException("E003");
                }
            } else if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt(paramInfo.getString("max_date")) ) {
                //최종일자보다 작은 일자 등록
                CommonEntity lastNu = IR030410.getLastNu(conn, paramInfo);  //회계일자 직전일의 전일 누계

                paramInfo.setValue("M070_SPREDAYAMT",                  lastNu.getString("M070_SPREDAYAMT")); //특별전일잔액
                paramInfo.setValue("M070_SPREDAYINTERESTAMT",          lastNu.getString("M070_SPREDAYINTERESTAMT")); //특별전일이자
                paramInfo.setValue("M070_PREDAYAMT",                   lastNu.getString("M070_PREDAYAMT")); //주된전일잔액
                paramInfo.setValue("M070_PREDAYINTERESTAMT",           lastNu.getString("M070_PREDAYINTERESTAMT")); //주된전일이자
                paramInfo.setValue("M070_SPREDAYTOTALCNT",             lastNu.getString("M070_SPREDAYTOTALCNT")); //특별전일징수건수누계
                paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastNu.getString("M070_SPREDAYTOTALAMT")); //특별전일징수금액누계
                paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastNu.getString("M070_SPREDAYTOTALINTEREST")); //특별전일징수이자누계
                paramInfo.setValue("M070_PREDAYTOTALCNT",              lastNu.getString("M070_PREDAYTOTALCNT")); //주된전일징수건수누계
                paramInfo.setValue("M070_PREDAYTOTALAMT",              lastNu.getString("M070_PREDAYTOTALAMT")); //주된전일징수금액누계
                paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastNu.getString("M070_PREDAYTOTALINTEREST")); //주된전일징수이자누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       lastNu.getString("M070_SPREDAYDIVIDETOTALCNT")); //특별전일배분건수누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       lastNu.getString("M070_SPREDAYDIVIDETOTALAMT")); //특별전일배분금액누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   lastNu.getString("M070_SPREDAYDIVIDETOTALINTERES")); //특별전일배분이자누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        lastNu.getString("M070_PREDAYDIVIDETOTALCNT")); //주된전일배분건수누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        lastNu.getString("M070_PREDAYDIVIDETOTALAMT")); //주된전일배분금액누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   lastNu.getString("M070_PREDAYDIVIDETOTALINTEREST")); //주된전일배분이자누계

                if (first_date.equals(paramInfo.getString("acc_date"))) {  //연초영업일이면
                    //회계일자와 이전년도 다를 때
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //특별전일징수건수누계
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastNu.getString("M070_SPREDAYAMT")); //특별전일징수금액누계
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastNu.getString("M070_SPREDAYINTERESTAMT")); //특별전일징수이자누계
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //주된전일징수건수누계
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              lastNu.getString("M070_PREDAYAMT")); //주된전일징수금액누계
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastNu.getString("M070_PREDAYINTERESTAMT")); //주된전일징수이자누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //특별전일배분건수누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //특별전일배분금액누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //특별전일배분이자누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //주된전일배분건수누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //주된전일배분금액누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //주된전일배분이자누계
                }

                CommonEntity juhaengCnt = IR030410.juhaengCnt(conn, paramInfo);

                /* 입력받은 회계일자 처리 */
                if ("0".equals(juhaengCnt.getString("CNT"))) {  // 해당일 일계자료 없을 때
                    if(IR030410.insertjuheangDay(conn, paramInfo) < 1 ) {
                        throw new ProcessException("E002"); //등록중 오류메시지 표시
                    }
                } else {
                    //최종일자와 같은 일자
                    if(IR030410.updatejuheangDay(conn, paramInfo) < 1 ) {
                        throw new ProcessException("E003");
                    }
                }

                CommonEntity minInfo = IR030410.getMinDate(conn, paramInfo);
                paramInfo.setValue("min_date",    minInfo.getString("M070_DATE"));
        
                /* 회계 일자 이후의 일자리스트 */
                List<CommonEntity> dayList = IR030410.getDayList(conn, paramInfo);
           
                for (int i = 0; i < dayList.size(); i++) {
                    CommonEntity dayInfo = (CommonEntity)dayList.get(i);
                    paramInfo.setValue("acc_date",    dayInfo.getString("M070_DATE"));

                    CommonEntity roopNu = IR030410.getLastNu(conn, paramInfo);  //작업일 직전일의 전일 누계

                    paramInfo.setValue("M070_SPREDAYAMT",                  roopNu.getString("M070_SPREDAYAMT")); //특별전일잔액
                    paramInfo.setValue("M070_SPREDAYINTERESTAMT",          roopNu.getString("M070_SPREDAYINTERESTAMT")); //특별전일이자
                    paramInfo.setValue("M070_PREDAYAMT",                   roopNu.getString("M070_PREDAYAMT")); //주된전일잔액
                    paramInfo.setValue("M070_PREDAYINTERESTAMT",           roopNu.getString("M070_PREDAYINTERESTAMT")); //주된전일이자
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             roopNu.getString("M070_SPREDAYTOTALCNT")); //특별전일징수건수누계
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             roopNu.getString("M070_SPREDAYTOTALAMT")); //특별전일징수금액누계
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        roopNu.getString("M070_SPREDAYTOTALINTEREST")); //특별전일징수이자누계
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              roopNu.getString("M070_PREDAYTOTALCNT")); //주된전일징수건수누계
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              roopNu.getString("M070_PREDAYTOTALAMT")); //주된전일징수금액누계
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         roopNu.getString("M070_PREDAYTOTALINTEREST")); //주된전일징수이자누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       roopNu.getString("M070_SPREDAYDIVIDETOTALCNT")); //특별전일배분건수누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       roopNu.getString("M070_SPREDAYDIVIDETOTALAMT")); //특별전일배분금액누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   roopNu.getString("M070_SPREDAYDIVIDETOTALINTERES")); //특별전일배분이자누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        roopNu.getString("M070_PREDAYDIVIDETOTALCNT")); //주된전일배분건수누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        roopNu.getString("M070_PREDAYDIVIDETOTALAMT")); //주된전일배분금액누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   roopNu.getString("M070_PREDAYDIVIDETOTALINTEREST")); //주된전일배분이자누계

                    if (paramInfo.getString("acc_date").equals(paramInfo.getString("min_date"))) {
                        paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //특별전일징수건수누계
                        paramInfo.setValue("M070_SPREDAYTOTALAMT",             roopNu.getString("M070_SPREDAYAMT")); //특별전일징수금액누계
                        paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        roopNu.getString("M070_SPREDAYINTERESTAMT")); //특별전일징수이자누계
                        paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //주된전일징수건수누계
                        paramInfo.setValue("M070_PREDAYTOTALAMT",              roopNu.getString("M070_PREDAYAMT")); //주된전일징수금액누계
                        paramInfo.setValue("M070_PREDAYTOTALINTEREST",         roopNu.getString("M070_PREDAYINTERESTAMT")); //주된전일징수이자누계
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //특별전일배분건수누계
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //특별전일배분금액누계
                        paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //특별전일배분이자누계
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //주된전일배분건수누계
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //주된전일배분금액누계
                        paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //주된전일배분이자누계
                    }

                    if (IR030410.updateRoopData(conn, paramInfo) < 1) {  //입력받은 회계일자 이후 자료 수정
                        throw new ProcessException("E003");
                    }

                }

            }
    
        }

        if (!"".equals(SucMsg) ) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "등록처리 되었습니다.");
        }
	}
}