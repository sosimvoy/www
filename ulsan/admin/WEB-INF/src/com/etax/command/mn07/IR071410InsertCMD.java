/**********************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		  : IR071410InsertCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 일계/보고서 > 세입세출일계정정(소급처리)
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;
import java.util.*;
import jxl.*;
import jxl.write.*;
import com.oreilly.servlet.MultipartRequest;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.config.ETaxConfig;
import com.etax.db.mn07.IR071410;
import com.etax.util.TextHandler;

public class IR071410InsertCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071410InsertCMD.class);

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
   
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("acc_year",    request.getParameter("acc_year"));
		paramInfo.setValue("acc_date",    request.getParameter("acc_date"));
		paramInfo.setValue("tax_type",   request.getParameter("tax_type"));
    if ("T1".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "0");  //업무구분
    } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "1");  //업무구분
    }
		paramInfo.setValue("rev_cd",        request.getParameter("rev_cd"));      //수납기관
    paramInfo.setValue("acc_type",      request.getParameter("acc_type"));    //회계구분
    paramInfo.setValue("part_code",     request.getParameter("part_code"));   //부서코드
    paramInfo.setValue("semok_code",    request.getParameter("semok_code"));  //세목코드
    paramInfo.setValue("acc_code",      request.getParameter("acc_code"));    //회계코드
    paramInfo.setValue("input_type",    request.getParameter("input_type"));  //입력구분
    paramInfo.setValue("year_type",     request.getParameter("year_type"));   //년도구분
    paramInfo.setValue("add_type",      request.getParameter("add_type"));    //금액증감구분
    paramInfo.setValue("amt",           request.getParameter("amt"));         //금액

    String SucMsg = "";  //메시지

    CommonEntity dailyMax = IR071410.getDailyMax(conn, paramInfo);
    
    if (Integer.parseInt(paramInfo.getString("acc_date")) > Integer.parseInt(TextHandler.getCurrentDate()) ) {
      //회계일자는 금일보다 작아야 한다.
      SucMsg = "회계일자는 금일보다 작아야합니다.";
    } else if (Integer.parseInt(paramInfo.getString("acc_year")) > Integer.parseInt(paramInfo.getString("acc_date").substring(0,4)) ) {
      //회계일자의 연도는 회계연도보다 크거나 같아야 한다.
      SucMsg = "회계일자의 연도가 회계연도보다 크거나 같아야 합니다.";
    } else if (Integer.parseInt(paramInfo.getString("acc_date").substring(0,4)) - Integer.parseInt(paramInfo.getString("acc_year")) == 1
      && Integer.parseInt(paramInfo.getString("acc_date").substring(4,8)) > 310) {
      //회계연도는 과년도이고 회계일자의 연도가 현년도이면서 일자가 3월 10일 이후일 때
      SucMsg = "과년도 정정 시에는 회계일자가 3월 10일 이후가 되서는 안됩니다.";
    } else if (!paramInfo.getString("acc_date").equals(TextHandler.getBusinessDate(conn, paramInfo.getString("acc_date"))) ) {
      //회계일자 영업일 체크
      SucMsg = "회계일자가 영업일이 아닙니다.";
    } else if (Integer.parseInt(dailyMax.getString("M210_DATE")) < Integer.parseInt(paramInfo.getString("acc_date")) ) {
      SucMsg = "회계일자가 마감일자보다 큽니다.";
    } else {
      CommonEntity recode = IR071410.getRecord(conn, paramInfo);  //회계일자 자료 존재 여부
      if (recode.size() > 0) {  //일계자료가 존재할 때
        IR071410.dateNoteUpdate(conn, paramInfo);
        IR071410.dateNoteUpdateProc(conn, paramInfo);
        SucMsg = "정상 수정이 되었습니다.";
      } else if (recode.size() == 0) {
        IR071410.dateNoteInsert(conn, paramInfo);
        IR071410.dateNoteInsertProc(conn, paramInfo);
        SucMsg = "정상 입력이 되었습니다.";
      }
    }
    
    //수납기관코드
    List<CommonEntity> revCdList = IR071410.getRevCdList(conn, paramInfo);
    request.setAttribute("page.mn07.revCdList", revCdList);

    //부서코드
    List<CommonEntity> partCdList = IR071410.getPartCdList(conn, paramInfo);
    request.setAttribute("page.mn07.partCdList", partCdList);


    //회계코드
    List<CommonEntity> accCdList = IR071410.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn07.accCdList", accCdList);


     //세목코드
    List<CommonEntity> semokCdList = IR071410.getSemokCdList(conn, paramInfo);
    request.setAttribute("page.mn07.semokCdList", semokCdList);


    request.setAttribute("page.mn07.SucMsg",   SucMsg);
  }
}