/**********************************************************************
* 프로젝트명        : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명        : IR071410SelectCMD.java
* 프로그램작성자    : (주)미르이즈
* 프로그램작성일    : 2010-10-01
* 프로그램내용      : 일계/보고서 > 세입세출일계정정(소급처리) 조회
***********************************************************************/
package com.etax.command.mn07;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import com.etax.util.*;
import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR071410;
import com.etax.entity.CommonEntity;

public class IR071410SelectCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR071410SelectCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{
    
    CommonEntity paramInfo = new CommonEntity();

    String SucMsg = "";  //메시지

    paramInfo.setValue("acc_year",      request.getParameter("acc_year"));  //회계연도
    paramInfo.setValue("acc_date",      request.getParameter("acc_date"));  //회계일자
		paramInfo.setValue("tax_type",      request.getParameter("tax_type"));  //세입세출구분
		paramInfo.setValue("input_type",    request.getParameter("input_type"));  //입력구분
		paramInfo.setValue("add_type",      request.getParameter("add_type"));  //증감여부
		paramInfo.setValue("amt",           request.getParameter("amt"));       //금액
		paramInfo.setValue("year_type",     request.getParameter("year_type")); //년도구분
    if ("".equals(paramInfo.getString("tax_type")) ) { 
      paramInfo.setValue("work_gubun",    "0");  //업무구분
    } else if ("T1".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "0");  //업무구분
    } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
      paramInfo.setValue("work_gubun",    "1");  //업무구분
    }
		paramInfo.setValue("rev_cd",        request.getParameter("rev_cd"));    //수납기관
    paramInfo.setValue("acc_type",      request.getParameter("acc_type"));  //회계구분
    paramInfo.setValue("part_code",     request.getParameter("part_code")); //부서코드
    paramInfo.setValue("acc_code",      request.getParameter("acc_code")); //회계코드
    paramInfo.setValue("gubun",         request.getParameter("gubun")); //구분
    
    if ("".equals(paramInfo.getString("acc_code")) && "".equals(paramInfo.getString("acc_year"))) {
      paramInfo.setValue("acc_code",   "11");  //회계코드
    } else if ("".equals(paramInfo.getString("acc_code")) && "A".equals(paramInfo.getString("acc_type")) ) {
      if ("T1".equals(paramInfo.getString("tax_type")) ) {
        paramInfo.setValue("acc_code",   "11");  //회계코드
      } else if ("T2".equals(paramInfo.getString("tax_type")) ) {
        paramInfo.setValue("acc_code",   "16");  //회계코드
      } 
    } else if ("".equals(paramInfo.getString("acc_code")) && "B".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_code",   "04");  //회계코드
    } else if ("".equals(paramInfo.getString("acc_code")) && "E".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_code",   "01");  //회계코드
    }

    if ("C".equals(paramInfo.getString("gubun"))) {
      CommonEntity accCd = IR071410.getAccCd(conn, paramInfo);
      paramInfo.setValue("acc_code",   accCd.getString("ACC_CD"));
    }

    if ("".equals(paramInfo.getString("acc_year")) ) {
      paramInfo.setValue("acc_year",    TextHandler.getCurrentDate().substring(0,4));  //회계연도
    }

    if ("".equals(paramInfo.getString("acc_type")) ) {
      paramInfo.setValue("acc_type",    "A");  //회계구분
    }

    if ("".equals(paramInfo.getString("part_code")) ) {
      paramInfo.setValue("part_code",    "00000");  //부서코드
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