/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : SupervisorCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-10-04
* 프로그램내용	  : 승인권한 체크
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.SupervisorDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;

public class SupervisorCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(SupervisorCMD.class);	// log4j 설정
  
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* 인증서 처리 */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String message   = auth.getMessage();    //메시지
    String subjectDN = auth.getSubjectDN();  //발급대상
    String serial    = auth.getSerial();     //시리얼

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",      serial);
    paramInfo.setValue("subjectDN",   subjectDN);
    paramInfo.setValue("user_id",     user_id);
		paramInfo.setValue("message",     message);
    paramInfo.setValue("user_state",  "");

    CommonEntity authInfo = SupervisorDAO.getInfoByAuth(conn, paramInfo);  //인증서 정보 조회
    CommonEntity powChk = SupervisorDAO.getPowCheck(conn, paramInfo);  //인증서 정보 조회
    
    if (authInfo.size() == 0) {   //승인권한 없음
      paramInfo.setValue("user_state", "FAIL");
    } else {  //인증서 확인 및 권한 체크
      if (powChk.size() > 0) {
        paramInfo.setValue("user_state", "GOOD");
      } else {
        paramInfo.setValue("user_state", "NOPOW");
      }
    } 
		request.setAttribute("auth.info", paramInfo);
	}
}