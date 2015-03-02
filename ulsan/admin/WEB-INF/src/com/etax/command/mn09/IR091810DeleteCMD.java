/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR091810DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용   : 시스템운영 > 부서코드관리
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
import com.etax.db.mn09.IR091810;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR091810DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091810DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("partCode", request.getParameter("partCode"));

		String[] chk_list      = request.getParameterValues("userChk");
		String[] delyear       = request.getParameterValues("delyear");
		String[] delacccodeno  = request.getParameterValues("delacccodeno");
    
		for (int i=0; i<chk_list.length; i++) {
		  int chk_val = Integer.parseInt(chk_list[i]);
			
	  	paramInfo.setValue("delyear",       delyear[chk_val]);
	  	paramInfo.setValue("delacccode",    delacccodeno[chk_val]);

		  IR091810.deleteaccountno(conn, paramInfo);

    }
    //등록팝업용 쿼리실행
		paramInfo.setValue("queyear",      request.getParameter("year")); 
		paramInfo.setValue("accgbn",       request.getParameter("stdAcccode")); 
		paramInfo.setValue("sysPartcode",  request.getParameter("sysPartcode")); 

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

		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");
  }
}
