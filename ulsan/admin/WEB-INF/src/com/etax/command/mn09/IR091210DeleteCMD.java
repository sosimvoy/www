/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR091010DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용   : 시스템운영 > 회기초코드 등록
****************************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091210DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("year",     request.getParameter("year"));
	  
		CommonEntity codeCnt = IR091210.getYearCode(conn, paramInfo);
     logger.info("paramInfo : " + paramInfo);
		 
		 if (!"0".equals(codeCnt.getString("CNT_1")) )	{
			if (IR091210.deleteEndYearCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_2")) )	{
    	if (IR091210.deleteEndYearCode2(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_3")) )	{
			if (IR091210.deleteEndYearCode3(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_4")) )	{
			if (IR091210.deleteEndYearCode4(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_5")) )	{
			if (IR091210.deleteEndYearCode5(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_6")) )	{
			if (IR091210.deleteEndYearCode6(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
		 }
		 if (!"0".equals(codeCnt.getString("CNT_7")) )	{
			 IR091210.deleteEndYearCode7(conn, paramInfo);
		 }
         //표준코드 항목 추가에 의해 수정됨
        //한영수 2012.01.04
        if (!"0".equals(codeCnt.getString("CNT_11")) )	{ //세목
		    if (IR091210.deleteEndYearCode11(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //삭제중 오류메시지 표시
            }
		}
		if (!"0".equals(codeCnt.getString("CNT_12")) )	{  //부서
			if (IR091210.deleteEndYearCode12(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //삭제중 오류메시지 표시
            }
		}
    //예산코드 항목 추가에 의해 수정됨
    //강원모 2010.11.10
    if (!"0".equals(codeCnt.getString("CNT_8")) )	{
			   if (IR091210.deleteEndYearCode8(conn, paramInfo) < 1) {
           throw new ProcessException("E004"); //삭제중 오류메시지 표시
         }
		}
		if (!"0".equals(codeCnt.getString("CNT_9")) )	{
			   if (IR091210.deleteEndYearCode9(conn, paramInfo) < 1) {
           throw new ProcessException("E004"); //삭제중 오류메시지 표시
         }
		}
        //농협코드테이블 회계연도 추가에 의해 수정됨
        //한영수 2010.12.30
        if (!"0".equals(codeCnt.getString("CNT_10")) )	{
		    if (IR091210.deleteEndYearCode10(conn, paramInfo) < 1) {
                throw new ProcessException("E004"); //삭제중 오류메시지 표시
            }
		}

		List endYearCode = IR091210.getEndYearCode(conn, paramInfo);
		request.setAttribute("page.mn09.endYearCode", endYearCode);

		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");
  }
}
