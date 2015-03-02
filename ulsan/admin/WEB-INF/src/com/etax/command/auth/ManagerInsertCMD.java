/****************************************************************
* 프로젝트명		  : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		  : ManagerInsertCMD.java
* 프로그램작성자	: 
* 프로그램작성일	: 2010-07-19
* 프로그램내용	  : 사용자 등록신청
*****************************************************************/
package com.etax.command.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.ProcessException;
import com.etax.db.auth.ManagerDAO;
import com.etax.entity.CommonEntity;


public class ManagerInsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(ManagerInsertCMD.class);	// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {

    /* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("userid",            request.getParameter("userid")); //유저아이디
		paramInfo.setValue("userpw",            request.getParameter("userpw")); //패스워드
		paramInfo.setValue("username",          request.getParameter("username")); //유저명
		paramInfo.setValue("currentorgan",      request.getParameter("currentorgan")); //현소속기관
		paramInfo.setValue("currentdepart",     request.getParameter("currentdepart")); //소속부서
		paramInfo.setValue("currentwork1",      request.getParameter("currentwork1")); //주요업무1
		paramInfo.setValue("currentsign",       request.getParameter("currentsign")); //결재권구분
		paramInfo.setValue("serial",            request.getParameter("serial")); //시리얼
		paramInfo.setValue("subjectDN",         request.getParameter("subjectDN")); //발급대상
	  paramInfo.setValue("managerHangNo",     request.getParameter("managerHangNo")); 
		paramInfo.setValue("managerNo",         request.getParameter("managerNo")); 
		paramInfo.setValue("terminalNo",        request.getParameter("terminalNo")); 
	  		
		//회원등록
		if( ManagerDAO.managerInsert(conn, paramInfo) < 1 ) {
			throw new ProcessException("E002");
		} 
    
    request.setAttribute("page.auth.SucMsg",    "가입완료되었습니다. 승인대기중입니다.");
	}
}