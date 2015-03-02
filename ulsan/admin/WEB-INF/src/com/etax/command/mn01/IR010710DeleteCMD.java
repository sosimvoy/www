/*****************************************************
* 프로젝트명		: 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		: IR010710DeleteCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-09-08
* 프로그램내용		: 세입 > OCR세입집계/취소 > 삭제
* 프로그램비고      : 1. 입력한 회계일자 기준 세입수기분 데이터 유무 확인
                      2. 해당 회계일자 세입수기분 삭제
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010710;
import com.etax.entity.CommonEntity;

public class IR010710DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010710DeleteCMD.class);	// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
	
		paramInfo.setValue("input_date",         request.getParameter("input_date"));
		logger.info(paramInfo);

		CommonEntity orcData = IR010710.getOcrCount(conn, paramInfo);

		if(orcData.getLong("TAXIN_CNT") > 0){	// M010_TAXIN_T(세입수기분) 존재여부 확인
			/* 삭제 */
			if(IR010710.ocrDelete(conn, paramInfo) < 1 ) {			
				throw new ProcessException("E004"); //삭제중 오류메시지 표시
			}
			request.setAttribute("page.mn01.delMsg", "삭제처리되었습니다.");
		}else{
			request.setAttribute("page.mn01.delMsg", "해당 작업일자에 삭제할 데이터가 없습니다.");
		}
	}
}