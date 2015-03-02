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
import com.etax.db.mn09.IR090210;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;
import com.etax.db.common.SelectBox;

public class IR090210DeleteCMD extends BaseCommand {

  private static Logger logger = Logger.getLogger(IR090210DeleteCMD.class); // log4j 설정

  public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException{    
		CommonEntity paramInfo = new CommonEntity();
    
			/* 세션 설정 */
    HttpSession session = request.getSession(false);
		String user_id   = (String)session.getAttribute("session.user_id");

		paramInfo.setValue("userId", request.getParameter("userId"));
    paramInfo.setValue("user_id",           user_id);

		String SucMsg = "";

		String[] chk_list      = request.getParameterValues("userChk");
		String[] userId_list   = request.getParameterValues("userId_list");
    String[] chk_yn     = request.getParameterValues("chk_yn");
    
		CommonEntity delete_Mgryn = IR090210.getMgrYn(conn , paramInfo);  //삭제 권한 체크
      
		if ("N".equals(delete_Mgryn.getString("M260_MGRYN")) )	{
	  SucMsg =  "삭제 권한이 없습니다";
    } else {

		  for (int i=0; i<chk_list.length; i++) {
		    int chk_val = Integer.parseInt(chk_list[i]);
        if ("Y".equals(chk_yn[chk_val])) {
          paramInfo.setValue("userId",       userId_list[chk_val]);

		      if (IR090210.deleteUserID(conn, paramInfo) < 1) {
		        throw new ProcessException("E004"); //삭제중 오류메시지 표시
				  }
        }		
		  }
		}
		
		if (!"".equals(SucMsg)) {
		  request.setAttribute("page.mn09.SucMsg", SucMsg);
	  } else {
		  request.setAttribute("page.mn09.SucMsg", "삭제되었습니다");
		}
		 	/* 사용자 리스트(일별) 조회 */
		List userList = IR090210.getUserList(conn, paramInfo);
		request.setAttribute("page.mn09.userList", userList);
  }
}
