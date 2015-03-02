/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : MasterLoginCMD.java
* 프로그램작성자	  : (주)미르이즈
* 프로그램작성일	  : 2014-09-15
* 프로그램내용	  : 관리자로그인
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.AuthException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;

public class MasterLoginCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(MasterLoginCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("userid",  request.getParameter("userid"));
		paramInfo.setValue("userpw",  request.getParameter("userpw"));
		
	    logger.debug("paramInfo : " + paramInfo);

	    CommonEntity idInfo = ManagerDAO.getInfoById2(conn, paramInfo);  //아이디 정보 조회

	    if (idInfo.size() == 0) {
	        throw new AuthException("L115"); // 로그인 실패
	    } else if (idInfo.size() > 0) {
	        if ("N".equals(idInfo.getString("M260_USERSTATE")) ) {
	            throw new AuthException("L111"); //미승인 상태
	        } else if ("Y".equals(idInfo.getString("M260_USERSTATE")) ) {
	            /* admin 정보가 있는경우 세션 생성 */
	            HttpSession session = request.getSession(true);
	            session.setMaxInactiveInterval(14400);
	            session.setAttribute("session.user_id",         idInfo.getString("M260_USERID"));   //회원아이디
	            session.setAttribute("session.user_name",       idInfo.getString("M260_USERNAME")); //회원명
	            session.setAttribute("session.mgr_yn",          idInfo.getString("M260_MGRYN"));    //관리자권한
	            session.setAttribute("session.buseo_cd",        idInfo.getString("M260_CURRENTPART"));   //부서코드
	            session.setAttribute("session.current_organ",   idInfo.getString("M260_CURRENTORGAN"));   //기관(시금고/울산시청)
	            session.setAttribute("session.user_ip",         request.getRemoteAddr());
	        }
	    }
	}
}