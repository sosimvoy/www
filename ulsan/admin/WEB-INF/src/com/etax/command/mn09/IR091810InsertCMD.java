/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR091810InsertCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용	 : 시스템운영 > 부서코드 등록
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091810;
import com.etax.entity.CommonEntity;
import com.etax.util.StringUtil;

public class IR091810InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091810InsertCMD.class);	// log4j 설정
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

    CommonEntity paramInfo = new CommonEntity();

	  paramInfo.setValue("fis_year",      request.getParameter("year"));
		//paramInfo.setValue("account_no",    request.getParameter("acct_no"));
		String CuserPw = "";
		try {
		    CuserPw = request.getParameter("acct_no");
		    if(!"".equals(CuserPw)) {
                CuserPw = StringUtil.encrypt("ETS.TRANS_TEF_EFAM026", "OUT_ACCT_ACCT_NO", CuserPw);
		    }
		}catch (Exception e) {
		    e.printStackTrace();
		}

        paramInfo.setValue("account_no",    CuserPw);
	    paramInfo.setValue("accgubun",      request.getParameter("stdAcccode"));
		paramInfo.setValue("part_code",     request.getParameter("sysPartcode"));
	    paramInfo.setValue("acc_code",      request.getParameter("sysAccount"));
		paramInfo.setValue("sysPartcode",  request.getParameter("sysPartcode")); 
        logger.info("paramInfo : "+paramInfo);
        
        CommonEntity idInfo   = IR091810.getcheckYn(conn, paramInfo);     //계좌등록여부 체크
        if(idInfo.getLong("insertCnt") > 0 ) {
            request.setAttribute("page.mn09.SucMsg", "등록된 계좌번호입니다.");
        } else {
		  if(IR091810.inserttefAccount(conn, paramInfo) < 1 ) {
		    throw new ProcessException("E003"); //수정중 오류메시지 표시
		  }
          request.setAttribute("page.mn09.SucMsg", "등록되었습니다.");
        }
    
    //등록팝업용 쿼리실행
		paramInfo.setValue("queyear",      request.getParameter("year")); 
		paramInfo.setValue("accgbn",       request.getParameter("stdAcccode")); 

		/* 등록form의 부서자료 조회 */
		List usePartList = IR091810.getusePartList(conn, paramInfo);
		request.setAttribute("page.mn09.usePartList", usePartList);

		/* 등록form의 회계자료 조회 */
		List useAcccodeList = IR091810.getuseAcccodeList(conn, paramInfo);
		request.setAttribute("page.mn09.useAcccodeList", useAcccodeList);

    //표준세목코드 자료 조회용
		paramInfo.setValue("queyear",           request.getParameter("queyear")); 
		paramInfo.setValue("accgbn",            request.getParameter("questdAcccode")); 
		logger.info("paramInfo : " + paramInfo);
		List tefAccountList = IR091810.gettefAccountList(conn, paramInfo);
		request.setAttribute("page.mn09.tefAccountList", tefAccountList);
		
	}
}
