/*****************************************************
* 프로젝트명		: 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		: IR010710InsertCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-09-08
* 프로그램내용		: 세입 > OCR세입집계/취소 > 처리(등록)
* 프로그램비고      : 1. 입력한 회계일자 기준 세입수기분&세외수입OCR자료 데이터 유무 확인(회계일 기준 기등록시 재등록 불가)
                      2. 해당 회계일자 세입수기분 등록
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.mn01.IR010710;
import com.etax.command.common.TransLogInsert;  //로그기록 남기기 위한 import
import com.etax.entity.CommonEntity;

public class IR010710InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010710InsertCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
	
	  CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("work_log",      request.getParameter("work_log"));			//업무구분
		paramInfo.setValue("trans_gubun",   request.getParameter("trans_gubun"));		//거래구분
		paramInfo.setValue("input_date",	request.getParameter("input_date"));		//작업일자
		
		
        /*
		CommonEntity orcData = IR010710.getOcrCount(conn, paramInfo);

		if(orcData.getLong("TAXIN_CNT") == 0){	// M010_TAXIN_T(세입수기분) 존재여부 확인
			if(orcData.getLong("ETC_CNT") > 0){	// ETC_T(세외수입OCR자료) 존재여부 확인
				
				//로그기록 남기는 클래스및 메소드 호출
				TransLogInsert tli = new TransLogInsert();
				tli.execute(request, response, conn);
				paramInfo.setValue("log_no",		tli.getLogNo());	// 로그번호
		
				if (IR010710.ocrInsert(conn, paramInfo) < 1) {
					throw new ProcessException("E002"); //등록중 오류메시지
				}
				request.setAttribute("page.mn01.insMsg", "처리 되었습니다.");
			}else{
				request.setAttribute("page.mn01.insMsg", "해당 작업일자의 OCR자료가 없습니다.");
			}
		}else{
			request.setAttribute("page.mn01.insMsg", "해당 작업일자는 이미 처리 되었습니다.");
		}
        */ 
        //2011-12-21 추가OCR처리를 위해 로직 변경 by 강원모
	    //로그기록 남기는 클래스및 메소드 호출
		CommonEntity magamData = IR010710.getMagamCount(conn, paramInfo);
		if(magamData.getLong("MAGAM_CNT") > 0){	// 마감여부 체크
          request.setAttribute("page.mn01.insMsg", "작업일자는 마감된 일자입니다.");
        } else {
		  TransLogInsert tli = new TransLogInsert();
		  tli.execute(request, response, conn);
		  paramInfo.setValue("log_no",		tli.getLogNo());	// 로그번호
		  int intcnt = IR010710.ocrInsert(conn, paramInfo);
		  request.setAttribute("page.mn01.insMsg", intcnt + "건 처리 되었습니다.");
        }
	}
} 