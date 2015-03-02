/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : SuperVisorCheckCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-08-04
* 프로그램내용	  : 책임자여부
*****************************************************************/
package com.etax.command.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.common.SuperDAO;
import com.etax.entity.CommonEntity;
import com.etax.command.auth.Authenticator;


public class SuperVisorCheckCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(SuperVisorCheckCMD.class);	// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* 인증서 처리 */
	  Authenticator auth = new Authenticator();
    auth.certify(request, response);
    String subjectDN = auth.getSubjectDN();  //발급대상
    String serial    = auth.getSerial();     //시리얼

    CommonEntity paramInfo= new CommonEntity();
    paramInfo.setValue("serial",     serial);
    paramInfo.setValue("subjectDN",  subjectDN);
    paramInfo.setValue("user_id",    user_id);

    CommonEntity sprInfo = SuperDAO.superCheck(conn, paramInfo); //책임자여부 체크
    
    if (sprInfo.size() == 0 ) {                 //책임자 아님
      request.setAttribute("page.info",  "N");
    } else if (sprInfo.size() > 0 ) {           //책임자 확인
			request.setAttribute("page.info",  "Y");
    }
	}
}