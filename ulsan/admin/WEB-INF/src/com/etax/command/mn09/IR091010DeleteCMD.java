/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR091010DeleteCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-21
* 프로그램내용   : 시스템운영 > 휴일관리
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
import com.etax.db.mn09.IR091010;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091010DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091010DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		paramInfo.setValue("yyyy",     request.getParameter("yyyy"));
		paramInfo.setValue("hol_name", request.getParameter("hol_name"));

		String[] chk_list = request.getParameterValues("userChk");
		String[] hol_list = request.getParameterValues("hol_list");
    
		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		paramInfo.setValue("date",       hol_list[chk_val]);

			if (IR091010.deleteHol(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
    }

     //paramInfo.setValue("userid",         request.getParameter("userid_list"));

			/* 휴일 리스트 조회 */
		List holList = IR091010.getHolList(conn, paramInfo);
		request.setAttribute("page.mn09.holList", holList);

		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");
  }
}
