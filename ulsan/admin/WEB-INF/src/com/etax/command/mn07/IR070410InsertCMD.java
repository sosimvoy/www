/********************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR070410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-02
* 프로그램내용   : 일계/보고서 > 일일보고조회
********************************************************/

package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;


import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR070410InsertCMD extends BaseCommand {
    private static Logger logger = Logger.getLogger(IR070410InsertCMD.class);
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
        
        /* (조회할)파라미터 세팅 */
	    CommonEntity paramInfo = new CommonEntity();
        paramInfo.setValue("acc_year",   request.getParameter("acc_year"));
        paramInfo.setValue("acc_date",   request.getParameter("acc_date"));

        paramInfo.setValue("M470_CAR_AGO_GC01",         request.getParameter("M470_CAR_AGO_GC01"));     //차량취득세 금결원 카드 전일누계
        paramInfo.setValue("M470_CAR_GC01",             request.getParameter("M470_CAR_GC01"));         //차량취득세 카드 당일수납액
        paramInfo.setValue("M470_CAR_ICHE_GC01",        request.getParameter("M470_CAR_ICHE_GC01"));    //차량취득세 카드 당일이체액
        paramInfo.setValue("M470_CAR_AGODATE_GC01",     request.getParameter("M470_CAR_AGODATE_GC01")); //차량취득세 카드 이체예정일

        paramInfo.setValue("M470_CAR_AGO_GC02",         request.getParameter("M470_CAR_AGO_GC02"));     //차량취득세 금결원 외 카드 전일누계
        paramInfo.setValue("M470_CAR_GC02",             request.getParameter("M470_CAR_GC02"));         //차량취득세 카드외 당일수납액
        paramInfo.setValue("M470_CAR_ICHE_GC02",        request.getParameter("M470_CAR_ICHE_GC02"));    //차량취득세 카드외 당일이체액
        paramInfo.setValue("M470_CAR_AGODATE_GC02",     request.getParameter("M470_CAR_AGODATE_GC02")); //차량취득세 카드외 이체예정일

        paramInfo.setValue("M470_NONG_AGO_GC01",        request.getParameter("M470_NONG_AGO_GC01"));    //차량농특세 금결원 카드 전일누계
        paramInfo.setValue("M470_NONG_GC01",            request.getParameter("M470_NONG_GC01"));        //차량농특세 카드 당일수납액
        paramInfo.setValue("M470_NONG_ICHE_GC01",       request.getParameter("M470_NONG_ICHE_GC01"));   //차량농특세 카드 당일이체액
        paramInfo.setValue("M470_NONG_AGODATE_GC01",    request.getParameter("M470_NONG_AGODATE_GC01"));//차량농특세 카드 이체예정일

        paramInfo.setValue("M470_NONG_AGO_GC02",        request.getParameter("M470_NONG_AGO_GC02"));    //차량농특세 금결원 외 카드 전일누계
        paramInfo.setValue("M470_NONG_GC02",            request.getParameter("M470_NONG_GC02"));        //차량농특세 카드외 당일수납액
        paramInfo.setValue("M470_NONG_ICHE_GC02",       request.getParameter("M470_NONG_ICHE_GC02"));   //차량농특세 카드외 당일이체액
        paramInfo.setValue("M470_NONG_AGODATE_GC02",    request.getParameter("M470_NONG_AGODATE_GC02"));//차량농특세 카드외 이체예정일

        paramInfo.setValue("M470_BAL_AGO_GP01",         request.getParameter("M470_BAL_AGO_GP01"));     //구군청발행시세 금결원 일반 전일누계
        paramInfo.setValue("M470_BAL_GP01",             request.getParameter("M470_BAL_GP01"));         //구군청발행시세 일반 당일수납액
        paramInfo.setValue("M470_BAL_ICHE_GP01",        request.getParameter("M470_BAL_ICHE_GP01"));    //구군청발행시세 일반 당일이체액
        paramInfo.setValue("M470_BAL_AGODATE_GP01",     request.getParameter("M470_BAL_AGODATE_GP01")); //구군청발행시세 일반 이체예정일

        paramInfo.setValue("M470_BAL_AGO_GC01",         request.getParameter("M470_BAL_AGO_GC01"));     //구군청발행시세 금결원 카드 전일누계
        paramInfo.setValue("M470_BAL_GC01",             request.getParameter("M470_BAL_GC01"));         //구군청발행시세 카드 당일수납액
        paramInfo.setValue("M470_BAL_ICHE_GC01",        request.getParameter("M470_BAL_ICHE_GC01"));    //구군청발행시세 카드 당일이체액
        paramInfo.setValue("M470_BAL_AGODATE_GC01",     request.getParameter("M470_BAL_AGODATE_GC01")); //구군청발행시세 카드 이체예정일

        paramInfo.setValue("M470_BAL_AGO_GC02",         request.getParameter("M470_BAL_AGO_GC02"));     //구군청발행시세 금결원 외 카드 전일누계
        paramInfo.setValue("M470_BAL_GC02",             request.getParameter("M470_BAL_GC02"));         //구군청발행시세 카드외 당일수납액
        paramInfo.setValue("M470_BAL_ICHE_GC02",        request.getParameter("M470_BAL_ICHE_GC02"));    //구군청발행시세 카드외 당일이체액
        paramInfo.setValue("M470_BAL_AGODATE_GC02",     request.getParameter("M470_BAL_AGODATE_GC02")); //구군청발행시세 카드외 이체예정일

        paramInfo.setValue("M470_CD_GUM_CARD_SUM",      request.getParameter("M470_CD_GUM_CARD_SUM"));     //차량취득세 금결원 카드 전일누계
        paramInfo.setValue("M470_CD_GUM_CARD_SUNAP",    request.getParameter("M470_CD_GUM_CARD_SUNAP"));   //차량취득세 카드 당일수납액
        paramInfo.setValue("M470_CD_GUM_CARD_ICHE",     request.getParameter("M470_CD_GUM_CARD_ICHE"));    //차량취득세 카드 당일이체액
        paramInfo.setValue("M470_CD_GUM_CARD_DATE",     request.getParameter("M470_CD_GUM_CARD_DATE"));    //차량취득세 카드 이체예정일

        paramInfo.setValue("M470_NT_GUM_CARD_SUM",      request.getParameter("M470_NT_GUM_CARD_SUM"));     //차량농특세 금결원 카드 전일누계
        paramInfo.setValue("M470_NT_GUM_CARD_SUNAP",    request.getParameter("M470_NT_GUM_CARD_SUNAP"));   //차량농특세 카드 당일수납액
        paramInfo.setValue("M470_NT_GUM_CARD_ICHE",     request.getParameter("M470_NT_GUM_CARD_ICHE"));    //차량농특세 카드 당일이체액
        paramInfo.setValue("M470_NT_GUM_CARD_DATE",     request.getParameter("M470_NT_GUM_CARD_DATE"));    //차량농특세 카드 이체예정일

        paramInfo.setValue("M470_BS_GUM_CARD_SUM",      request.getParameter("M470_BS_GUM_CARD_SUM"));     //구군청발행시세 금결원 카드 전일누계
        paramInfo.setValue("M470_BS_GUM_CARD_SUNAP",    request.getParameter("M470_BS_GUM_CARD_SUNAP"));   //구군청발행시세 카드 당일수납액
        paramInfo.setValue("M470_BS_GUM_CARD_ICHE",     request.getParameter("M470_BS_GUM_CARD_ICHE"));    //구군청발행시세 카드 당일이체액
        paramInfo.setValue("M470_BS_GUM_CARD_DATE",     request.getParameter("M470_BS_GUM_CARD_DATE"));    //구군청발행시세 카드 이체예정일

        paramInfo.setValue("M470_BS_GUM_NOCARD_SUM",    request.getParameter("M470_BS_GUM_NOCARD_SUM"));   //구군청발행시세 금결원 외 카드 전일누계
        paramInfo.setValue("M470_BS_GUM_NOCARD_SUNAP",  request.getParameter("M470_BS_GUM_NOCARD_SUNAP")); //구군청발행시세 카드외 당일수납액
        paramInfo.setValue("M470_BS_GUM_NOCARD_ICHE",   request.getParameter("M470_BS_GUM_NOCARD_ICHE"));  //구군청발행시세 카드외 당일이체액
        paramInfo.setValue("M470_BS_GUM_NOCARD_DATE",   request.getParameter("M470_BS_GUM_NOCARD_DATE"));  //구군청발행시세 카드외 이체예정일

        paramInfo.setValue("M470_BS_GUM_OARS_SUM",      request.getParameter("M470_BS_GUM_OARS_SUM"));   //구군청발행시세 OARS 전일누계
        paramInfo.setValue("M470_BS_GUM_OARS_SUNAP",    request.getParameter("M470_BS_GUM_OARS_SUNAP")); //구군청발행시세 OARS 당일수납액
        paramInfo.setValue("M470_BS_GUM_OARS_ICHE",     request.getParameter("M470_BS_GUM_OARS_ICHE"));  //구군청발행시세 OARS 당일이체액
        paramInfo.setValue("M470_BS_GUM_OARS_DATE",     request.getParameter("M470_BS_GUM_OARS_DATE"));  //구군청발행시세 OARS 이체예정일

        paramInfo.setValue("M470_BS_GUM_NARS_SUM",      request.getParameter("M470_BS_GUM_NARS_SUM"));   //구군청발행시세 NARS 전일누계
        paramInfo.setValue("M470_BS_GUM_NARS_SUNAP",    request.getParameter("M470_BS_GUM_NARS_SUNAP")); //구군청발행시세 NARS 당일수납액
        paramInfo.setValue("M470_BS_GUM_NARS_ICHE",     request.getParameter("M470_BS_GUM_NARS_ICHE"));  //구군청발행시세 NARS 당일이체액
        paramInfo.setValue("M470_BS_GUM_NARS_DATE",     request.getParameter("M470_BS_GUM_NARS_DATE"));  //구군청발행시세 NARS 이체예정일
        
        paramInfo.setValue("M470_SS_GUM_OCARD_SUM",     request.getParameter("M470_SS_GUM_OCARD_SUM_AGO")); //세외수입 금결원 카드 전일누계
        paramInfo.setValue("M470_SS_GUM_OCARD_SUNAP",   request.getParameter("M470_SS_GUM_OCARD_SUNAP"));   //세외수입 카드 당일수납액
        paramInfo.setValue("M470_SS_GUM_OCARD_ICHE",    request.getParameter("M470_SS_GUM_OCARD_ICHE"));    //세외수입 카드 당일이체액
        paramInfo.setValue("M470_SS_GUM_OCARD_DATE",     request.getParameter("M470_SS_GUM_OCARD_DATE"));   //세외수입 카드 이체예정일
        
        paramInfo.setValue("M470_SS_GUM_NCARD_SUM",     request.getParameter("M470_SS_GUM_NCARD_SUM_AGO")); //세외수입 금결원 카드 전일누계
        paramInfo.setValue("M470_SS_GUM_NCARD_SUNAP",   request.getParameter("M470_SS_GUM_NCARD_SUNAP"));   //세외수입 카드 당일수납액
        paramInfo.setValue("M470_SS_GUM_NCARD_ICHE",    request.getParameter("M470_SS_GUM_NCARD_ICHE"));    //세외수입 카드 당일이체액
        paramInfo.setValue("M470_SS_GUM_NCARD_DATE",    request.getParameter("M470_SS_GUM_NCARD_DATE"));    //세외수입 카드 이체예정일
        
        paramInfo.setValue("M470_SS_GUM_OARS_SUM",      request.getParameter("M470_SS_GUM_OARS_SUM_AGO"));   //세외수입 OARS 전일누계
        paramInfo.setValue("M470_SS_GUM_OARS_SUNAP",    request.getParameter("M470_SS_GUM_OARS_SUNAP")); //세외수입 OARS 당일수납액
        paramInfo.setValue("M470_SS_GUM_OARS_ICHE",     request.getParameter("M470_SS_GUM_OARS_ICHE"));  //세외수입 OARS 당일이체액
        paramInfo.setValue("M470_SS_GUM_OARS_DATE",     request.getParameter("M470_SS_GUM_OARS_DATE"));  //세외수입 OARS 이체예정일

        paramInfo.setValue("M470_SS_GUM_NARS_SUM",      request.getParameter("M470_SS_GUM_NARS_SUM_AGO"));   //세외수입 NARS 전일누계
        paramInfo.setValue("M470_SS_GUM_NARS_SUNAP",    request.getParameter("M470_SS_GUM_NARS_SUNAP")); //세외수입 NARS 당일수납액
        paramInfo.setValue("M470_SS_GUM_NARS_ICHE",     request.getParameter("M470_SS_GUM_NARS_ICHE"));  //세외수입 NARS 당일이체액
        paramInfo.setValue("M470_SS_GUM_NARS_DATE",     request.getParameter("M470_SS_GUM_NARS_DATE"));  //세외수입 NARS 이체예정일
        
        String acc_date = request.getParameter("acc_date");
        String acc_year = request.getParameter("acc_year");

        String SucMsg = ""; //메시지

        if (!acc_date.equals(TextHandler.getBusinessDate(conn, acc_date)) ) {
            SucMsg = "회계일자가 공휴일입니다. 회계일자는 반드시 영업일이어야 합니다.";
        } else {
            //해당 회계일자 자료 조회
            CommonEntity tempNote = IR070410.getDateNote(conn, paramInfo);
            if (tempNote.size() > 0) {
                SucMsg = "해당하는 회계일자에 자료가 존재합니다. 삭제 후 다시 등록하시기 바랍니다.";
            } else {
                if (IR070410.insertNote(conn, paramInfo) < 1) {
                    SucMsg = "등록 중 오류가 발생하였습니다. 관리자에게 문의바랍니다.";
                } else {
                    SucMsg = TextHandler.formatDate(acc_date, "yyyyMMdd", "yyyy-MM-dd") + " 자료가 등록되었습니다.";
                }
            }

            //해당 회계일자 자료 조회
            CommonEntity dateNote = IR070410.getDateNote(conn, paramInfo);
            request.setAttribute("page.mn07.dateNote", dateNote);
            
            String f_date = TextHandler.getBusinessDate(conn, acc_year+"0101");
            logger.info("연초영업일 : " + f_date);
            if (f_date.equals(acc_date)) { //연초 영업일일 경우 모든 자료 0으로 가져오기
                //전 영업일 자료 조회
                CommonEntity agoNote = IR070410.getAgoNote1(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);

            } else {
                //전 영업일 자료 조회
                CommonEntity agoNote = IR070410.getAgoNote(conn, paramInfo);
                request.setAttribute("page.mn07.agoNote", agoNote);
            }
        }
        request.setAttribute("page.mn07.SucMsg", SucMsg);
	}
}