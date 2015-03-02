/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : ManagerLoginCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-07-19
* 프로그램내용	  : 사용자 등록신청
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

public class ManagerLoginCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerLoginCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
		
		/* 인증서 처리 */
		Authenticator authenticator = new Authenticator();
		authenticator.certify(request, response);
		String user_id = authenticator.getUserId();
    String user_pw = authenticator.getUserPw();
		String subjectDN = authenticator.getSubjectDN();
    String serial = authenticator.getSerial();

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("subjectDN", subjectDN);
    paramInfo.setValue("serial",    serial);
		paramInfo.setValue("userid",  user_id);
    paramInfo.setValue("userpw",  user_pw);
    logger.info("paramInfo : " + paramInfo);


    CommonEntity authInfo = ManagerDAO.getInfoByAuth(conn, paramInfo);    //인증서 정보 조회
    CommonEntity idInfo = ManagerDAO.getInfoById2(conn, paramInfo);  //아이디 정보 조회

    if (authInfo.size() == 0 && idInfo.size() == 0) {
      throw new AuthException("L112"); //미가입 사용자
    } else if (authInfo.size() == 0 && idInfo.size() > 0) {
      throw new AuthException("L114"); //미등록 인증서
    } else if (authInfo.size() > 0 && idInfo.size() == 0) {
      throw new AuthException("L115"); //미등록 아이디
    } else if (authInfo.size() > 0 && idInfo.size() > 0) {
      CommonEntity logInfo = ManagerDAO.getLogin(conn, paramInfo);
      if (logInfo.size() == 0) {
        throw new AuthException("L115"); // 로그인 실패
      } else if (logInfo.size() > 0) {
        if ("N".equals(logInfo.getString("M260_USERSTATE")) ) {
          throw new AuthException("L111"); //미승인 상태
        } else if ("Y".equals(logInfo.getString("M260_USERSTATE")) ) {
          /* admin 정보가 있는경우 세션 생성 */
		      HttpSession session = request.getSession(true);
              session.setMaxInactiveInterval(14400);
		      session.setAttribute("session.user_id",         logInfo.getString("M260_USERID"));   //회원아이디
		      session.setAttribute("session.user_name",       logInfo.getString("M260_USERNAME")); //회원명
		      session.setAttribute("session.mgr_yn",          logInfo.getString("M260_MGRYN"));    //관리자권한
		      session.setAttribute("session.buseo_cd",        logInfo.getString("M260_CURRENTPART"));   //부서코드
          session.setAttribute("session.current_organ",   logInfo.getString("M260_CURRENTORGAN"));   //기관(시금고/울산시청)
		      session.setAttribute("session.user_ip",         request.getRemoteAddr());
        }        
      }
    }		
	}
}