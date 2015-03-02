/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030610InsertCMD.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-30
* 프로그램내용	   : 세입세출외현금 > 주행세 일일마감
****************************************************************/

package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn03.IR030610;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR030610InsertCMD extends BaseCommand {
 
	private static Logger logger = Logger.getLogger(IR030610InsertCMD.class);
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        String SucMsg = ""; //메시지
        CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("acc_date",               request.getParameter("acc_date"));

        CommonEntity maxInfo = IR030610.getFisDate(conn);
        paramInfo.setValue("max_date",    maxInfo.getString("M070_DATE"));

        CommonEntity businessInfo = IR030610.getNextBusinessDate(conn, paramInfo);  //익영업일
        paramInfo.setValue("next_date",    businessInfo.getString("NEXT_DAY"));
        paramInfo.setValue("year",         paramInfo.getString("next_date").substring(0,4));

        String bigyo_date = TextHandler.addDays(paramInfo.getString("max_date"), -124);
        String first_date = TextHandler.getBusinessDate(conn, paramInfo.getString("year")+"0101");  //연초영업일

        if (Integer.parseInt(paramInfo.getString("acc_date")) < Integer.parseInt("20100930")) {
            SucMsg = "2010년 10월 1일 이전 자료는 마감작업을 할 수 없습니다.";
        } else if (Integer.parseInt(bigyo_date) >  Integer.parseInt(paramInfo.getString("acc_date")) ) {
            SucMsg =  "최종 자료일자에서 124일 이전 날짜로 마감작업을 할 수 없습니다.";
        } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
            SucMsg = "마감일이 영업일이 아닙니다.";
        } else if (paramInfo.getString("max_date").equals(paramInfo.getString("next_date")) ) {
            SucMsg = "이미 마감이 끝났습니다.";
        } else if (!paramInfo.getString("acc_date").equals(paramInfo.getString("max_date")) ) {
            SucMsg = "마지막 작업일은 " + TextHandler.formatDate(paramInfo.getString("max_date"), "yyyyMMdd", "yyyy년 MM월 dd일") + "입니다.";
        } else {
            logger.info("paramInfo : " + paramInfo);

            if (Integer.parseInt(paramInfo.getString("acc_date")) == Integer.parseInt(paramInfo.getString("max_date")) ) {
                //최종일자와 같은 일자
                CommonEntity lastAmt = IR030610.getLastAmt(conn, paramInfo);

                paramInfo.setValue("M070_SPREDAYAMT",                  lastAmt.getString("M070_SPREDAYAMT")); //특별전일잔액
                paramInfo.setValue("M070_SPREDAYINTERESTAMT",          lastAmt.getString("M070_SPREDAYINTERESTAMT")); //특별전일이자
                paramInfo.setValue("M070_PREDAYAMT",                   lastAmt.getString("M070_PREDAYAMT")); //주된전일잔액
                paramInfo.setValue("M070_PREDAYINTERESTAMT",           lastAmt.getString("M070_PREDAYINTERESTAMT")); //주된전일이자
                paramInfo.setValue("M070_SPREDAYTOTALCNT",             lastAmt.getString("M070_SPREDAYTOTALCNT")); //특별전일징수건수누계
                paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastAmt.getString("M070_SPREDAYTOTALAMT")); //특별전일징수금액누계
                paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastAmt.getString("M070_SPREDAYTOTALINTEREST")); //특별전일징수이자누계
                paramInfo.setValue("M070_PREDAYTOTALCNT",              lastAmt.getString("M070_PREDAYTOTALCNT")); //주된전일징수건수누계
                paramInfo.setValue("M070_PREDAYTOTALAMT",              lastAmt.getString("M070_PREDAYTOTALAMT")); //주된전일징수금액누계
                paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastAmt.getString("M070_PREDAYTOTALINTEREST")); //주된전일징수이자누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       lastAmt.getString("M070_SPREDAYDIVIDETOTALCNT")); //특별전일배분건수누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       lastAmt.getString("M070_SPREDAYDIVIDETOTALAMT")); //특별전일배분금액누계
                paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   lastAmt.getString("M070_SPREDAYDIVIDETOTALINTERES")); //특별전일배분이자누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        lastAmt.getString("M070_PREDAYDIVIDETOTALCNT")); //주된전일배분건수누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        lastAmt.getString("M070_PREDAYDIVIDETOTALAMT")); //주된전일배분금액누계
                paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   lastAmt.getString("M070_PREDAYDIVIDETOTALINTEREST")); //주된전일배분이자누계

                if (first_date.equals(paramInfo.getString("next_date"))) {  //익영업일이 연초영업일이면
                    paramInfo.setValue("M070_SPREDAYTOTALCNT",             "0"); //특별전일징수건수누계
                    paramInfo.setValue("M070_SPREDAYTOTALAMT",             lastAmt.getString("M070_SPREDAYAMT")); //특별전일징수금액누계
                    paramInfo.setValue("M070_SPREDAYTOTALINTEREST",        lastAmt.getString("M070_SPREDAYINTERESTAMT")); //특별전일징수이자누계
                    paramInfo.setValue("M070_PREDAYTOTALCNT",              "0"); //주된전일징수건수누계
                    paramInfo.setValue("M070_PREDAYTOTALAMT",              lastAmt.getString("M070_PREDAYAMT")); //주된전일징수금액누계
                    paramInfo.setValue("M070_PREDAYTOTALINTEREST",         lastAmt.getString("M070_PREDAYINTERESTAMT")); //주된전일징수이자누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALCNT",       "0"); //특별전일배분건수누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALAMT",       "0"); //특별전일배분금액누계
                    paramInfo.setValue("M070_SPREDAYDIVIDETOTALINTERES",   "0"); //특별전일배분이자누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALCNT",        "0"); //주된전일배분건수누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALAMT",        "0"); //주된전일배분금액누계
                    paramInfo.setValue("M070_PREDAYDIVIDETOTALINTEREST",   "0"); //주된전일배분이자누계

                }

                if(IR030610.insertjuheangDay(conn, paramInfo) < 1 ) {
                    throw new ProcessException("E002"); //등록중 오류메시지 표시
                }
            }
        }

        if (!"".equals(SucMsg) ) {
            request.setAttribute("page.mn03.SucMsg", SucMsg);
        } else {
            request.setAttribute("page.mn03.SucMsg", "마감처리 되었습니다.");
        }
	}
}
