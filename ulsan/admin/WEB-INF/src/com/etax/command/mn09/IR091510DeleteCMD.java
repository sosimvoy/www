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
import com.etax.db.mn09.IR091510;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR091510DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR091510DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
		String[] chk_list      = request.getParameterValues("userChk");
		String[] delnapbuja     = request.getParameterValues("delnapbuja");

		for (int i=0; i<chk_list.length; i++) {
		int chk_val = Integer.parseInt(chk_list[i]);
			
		  paramInfo.setValue("Napbuja",          delnapbuja[chk_val]);

			if (IR091510.deleteNapbuja(conn, paramInfo) < 1) {
		    throw new ProcessException("E004"); //삭제중 오류메시지 표시
		  }
    }
  	/* 납부자주소 조회 */
		List NapbujaList = IR091510.getNapbujaList(conn, paramInfo);
		request.setAttribute("page.mn09.NapbujaList", NapbujaList);

		request.setAttribute("page.mn09.SucMsg", "삭제되었습니다.");
  }
}
