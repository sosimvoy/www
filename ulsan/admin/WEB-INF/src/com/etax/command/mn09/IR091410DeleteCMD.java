/*****************************************************
* 프로젝트명      : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : IR091410SelectCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2010-07-25
* 프로그램내용		: 시스템운영 > 직인삭제
******************************************************/

package com.etax.command.mn09;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn09.IR091410;
import com.etax.entity.CommonEntity;

public class IR091410DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR091410DeleteCMD.class);	// log4j 설정

	/****************************************************************************
   * DB 연결을 하고 클라이언트의 요청대로 행함
   * @param request         요청값들
	 * @param response        응답값들
	 * @param conn            DB커넥션 설정값
   * @exception             SQLException
   * @since                 1.0
  ***************************************************************************/
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {

		HttpSession session = request.getSession(false);
		String user_id = (String)session.getAttribute("session.user_id");

		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("user_id",    user_id);
    paramInfo.setValue("current_organ",         request.getParameter("current_organ"));
	
		logger.info("paramInfo : " + paramInfo);

    /*직인 삭제 */
		if(IR091410.deleteSignInfo(conn, paramInfo) < 1 ) {			
			throw new ProcessException("E004"); //삭제중 오류메시지 표시
		}

		CommonEntity signInfo = IR091410.getSignInfo(conn, paramInfo);
		request.setAttribute("page.mn09.signInfo", signInfo);

	}
}