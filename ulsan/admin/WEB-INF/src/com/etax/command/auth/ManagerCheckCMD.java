/****************************************************************
* 프로젝트명		  : 대전광역시 세정관리자 시스템
* 프로그램명		  : ManagerCheckCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2009-12-04
* 프로그램내용	  : 회원 가입 여부 확인
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
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;
import com.etax.helper.MessageHelper;


public class ManagerCheckCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerCheckCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* 인증서 처리 */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String message   = auth.getMessage();    //메시지
    String subjectDN = auth.getSubjectDN();  //발급대상
    String serial    = auth.getSerial();     //시리얼
		String userid    = auth.getUserId();     //사용자아이디
		String userpw    = auth.getUserPw();     //사용자패스워드

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",    serial);
    paramInfo.setValue("subjectDN", subjectDN);
    paramInfo.setValue("userid",    userid);
		paramInfo.setValue("userpw",    userpw);
		paramInfo.setValue("message",   message);
    paramInfo.setValue("user_state", "");  //사용자 상태
    paramInfo.setValue("org_id",    "");

    CommonEntity authInfo = ManagerDAO.getInfoByAuth(conn, paramInfo);  //인증서 정보 조회
    CommonEntity idInfo   = ManagerDAO.getInfoById(conn, paramInfo);     //아이디 등록 여부
    
    if (idInfo.size() == 0 && authInfo.size() == 0) {  //가입가능함
      paramInfo.setValue("user_state", "OK");
    } else if (idInfo.size() > 0 && authInfo.size() == 0) {  //인증서 갱신 필요
      paramInfo.setValue("user_state", "RENEWAL");
    } else if (idInfo.size() == 0 && authInfo.size() > 0) {  //등록된 인증서
      paramInfo.setValue("user_state", "ORGAUTH");
      paramInfo.setValue("org_id",   authInfo.getString("M260_USERID"));
    } else if (idInfo.size() > 0 && authInfo.size() > 0) {   //가입되어 있음
      if ("N".equals(authInfo.getString("M260_USERSTATE")) ) {
        paramInfo.setValue("user_state", "PMS_WAIT");
      } else if ("Y".equals(authInfo.getString("M260_USERSTATE")) ) {
        paramInfo.setValue("user_state", "PMS_OK");
      }
    }
		request.setAttribute("auth.info", paramInfo);
	}
}