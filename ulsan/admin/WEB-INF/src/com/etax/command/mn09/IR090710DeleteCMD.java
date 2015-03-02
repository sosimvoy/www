/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR09060DeleteCMD.java
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
import com.etax.db.mn09.IR090710;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR090710DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090710DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
        
        paramInfo.setValue("under_year",           request.getParameter("under_year")); 
		String[] chk_list      = request.getParameterValues("userChk");
		String[] year_list     = request.getParameterValues("year_list");
    String[] accGubun_list = request.getParameterValues("accGubun_list");
		String[] accCode_list  = request.getParameterValues("accCode_list");

		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("year",          year_list[chk_val]);
		paramInfo.setValue("accGubun",      accGubun_list[chk_val]);
		paramInfo.setValue("accCode",       accCode_list[chk_val]);

			if (IR090710.deleteAccCode(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
    }
      	/* 회계코드 조회 */
		List accCodeList = IR090710.getAccCodeList(conn, paramInfo);
		request.setAttribute("page.mn09.accCodeList", accCodeList);

		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");
  }
}
