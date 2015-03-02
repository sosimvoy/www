/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR091120InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용	 : 시스템운영 > 연말코드등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.oreilly.servlet.MultipartRequest;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091210;
import com.etax.entity.CommonEntity;

public class IR091210InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091210InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

        CommonEntity paramInfo = new CommonEntity();

	    paramInfo.setValue("year",         request.getParameter("year"));
	 
   	    CommonEntity codeCnt = IR091210.getYearCode(conn, paramInfo);
        logger.info("paramInfo : " + paramInfo);
     
		if ("0".equals(codeCnt.getString("CNT_1")) )	{
			if(IR091210.insertEndYearCode(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
		    }
		}
		if ("0".equals(codeCnt.getString("CNT_2")) )	{
			if(IR091210.insertEndYearCode2(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
			}
		}
		if ("0".equals(codeCnt.getString("CNT_3")) )	{
			if(IR091210.insertEndYearCode3(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
			}
		}
		if ("0".equals(codeCnt.getString("CNT_4")) )	{
			if(IR091210.insertEndYearCode4(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
			}
		}
		if ("0".equals(codeCnt.getString("CNT_5")) )	{
			if(IR091210.insertEndYearCode5(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
			}
		}
		if ("0".equals(codeCnt.getString("CNT_6")) )	{
			if(IR091210.insertEndYearCode6(conn, paramInfo) < 1 ) {
		        throw new ProcessException("E002"); //수정중 오류메시지 표시
			}
		}
		if ("0".equals(codeCnt.getString("CNT_7")) )	{
			if(IR091210.insertEndYearCode7(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        //표준코드 항목 추가에 의해 수정됨
        //한영수 2012.01.04
		if ("0".equals(codeCnt.getString("CNT_11")) )	{  //세목
			if (IR091210.insertEndYearCode11(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
		if ("0".equals(codeCnt.getString("CNT_12")) )	{  //부서
			if(IR091210.insertEndYearCode12(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        //예산코드 항목 추가에 의해 수정됨
        //강원모 2010.11.10
		if ("0".equals(codeCnt.getString("CNT_8")) )	{
			if (IR091210.insertEndYearCode8(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
		if ("0".equals(codeCnt.getString("CNT_9")) )	{
			if(IR091210.insertEndYearCode9(conn, paramInfo) < 1) {
                throw new ProcessException("E002");
            }
		}
        
        //농협코드테이블 회계연도 추가에 의해 수정됨
        //한영수 2010.12.30
        if ("0".equals(codeCnt.getString("CNT_10")) )	{
			if(IR091210.insertEndYearCode10(conn, paramInfo) < 1 ) {
                throw new ProcessException("E002");
            }
		}

	    List<CommonEntity> endYearCode = IR091210.getEndYearCode(conn, paramInfo);
		request.setAttribute("page.mn09.endYearCode", endYearCode);

		request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
	}
}
