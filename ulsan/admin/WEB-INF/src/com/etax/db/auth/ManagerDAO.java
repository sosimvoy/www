/*****************************************************
* 프로젝트명		 :  울산광역시 세입 및 자금배정관리시스템
* 프로그램명		 : ManagerDAO.java
* 프로그램작성자 : 
* 프로그램작성일 : 2010-07-19
* 프로그램내용	 : 사용자
******************************************************/
package com.etax.db.auth;
import java.sql.*;
import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;
public class ManagerDAO {
	/* 아이디등록 여부 */
	public static CommonEntity getInfoById(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                      \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = TRIM(?)   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));

		return template.getData(conn, parameters);
	}


	/* 아이디체크 */
	public static CommonEntity getInfoById2(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                    \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = ?         \n");
		sb.append("   AND M260_USERPW = ?         \n");
		sb.append("   AND M260_CURRENTORGAN = '1' \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));
		parameters.setString(2, paramInfo.getString("userpw"));

		return template.getData(conn, parameters);
	}


  /* 로그인단계 */
	public static CommonEntity getLogin(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                           \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '　', '')  = REPLACE(REPLACE(?, ' ', ''), '　', '')  \n"); 
    sb.append("   AND M260_USERID = TRIM(?)        \n");
    sb.append("   AND M260_USERPW = TRIM(?)        \n");
   	sb.append("   AND M260_CURRENTORGAN = '1'      \n");
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("serial"));
		parameters.setString(2, paramInfo.getString("subjectDN"));
    parameters.setString(3, paramInfo.getString("userid"));
    parameters.setString(4, paramInfo.getString("userpw"));
	
		return template.getData(conn, parameters);
	}


  /* 인증서체크 */
	public static CommonEntity getInfoByAuth(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                            \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '　', '')  = REPLACE(REPLACE(?, ' ', ''), '　', '')  \n"); 
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("serial"));
		parameters.setString(2, paramInfo.getString("subjectDN"));
	
		return template.getData(conn, parameters);
	}


  /* 사용자 인증서 갱신 */
	public static int AuthUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M260_USERMANAGER_T  \n");
		sb.append("   SET M260_SERIAL = ?     \n");
		sb.append("      ,M260_SUB_DN = ?     \n"); 
		sb.append(" WHERE M260_USERID = ?     \n");
		sb.append("   AND M260_USERPW = ?     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx=1;
    parameters.setString(idx++, paramInfo.getString("serial"));//시리얼
		parameters.setString(idx++, paramInfo.getString("subjectDN"));//발급대상
		parameters.setString(idx++, paramInfo.getString("userid"));//유저아이디
		parameters.setString(idx++, paramInfo.getString("userpw"));//패스워드
	
		return template.insert(conn, parameters);
	}
	 
	
	/* 사용자 등록 신청 */
	public static int managerInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO M260_USERMANAGER_T                \n");
		sb.append("(                                             \n");
		sb.append("  M260_USERID,  M260_USERPW,                  \n"); 
		sb.append("  M260_USERNAME,M260_CURRENTORGAN,            \n"); 
		sb.append("  M260_SERIAL,  M260_SUB_DN,                  \n"); 
    sb.append("  M260_LOGNO ,  M260_REQUESTDATE,             \n");
		sb.append("  M260_CURRENTPART,M260_CURRENTWORK1,         \n");
    sb.append("  M260_CURRENTSIGNTYPE, M260_MANAGERBANKERNO, \n");
		sb.append("  M260_MANAGERNO, M260_TERMINALNO             \n");
		sb.append(" )                                            \n");
		sb.append(" VALUES                                       \n");
		sb.append("(                                             \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append(" TO_CHAR(SYSDATE, 'YYYYMMDD'),                \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?                                           \n");
		sb.append("     )                                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i=1;
		parameters.setString(i++, paramInfo.getString("userid"));//유저아이디
		parameters.setString(i++, paramInfo.getString("userpw"));//패스워드
		parameters.setString(i++, paramInfo.getString("username"));//유저명
		parameters.setString(i++, paramInfo.getString("currentorgan"));//현소속기관
		parameters.setString(i++, paramInfo.getString("serial"));//시리얼
		parameters.setString(i++, paramInfo.getString("subjectDN"));//발급대상
		parameters.setString(i++, paramInfo.getString("logno"));//로그번호(신청)
		parameters.setString(i++, paramInfo.getString("currentdepart"));//현소속부서
		parameters.setString(i++, paramInfo.getString("currentwork1"));//주요업무1
		parameters.setString(i++, paramInfo.getString("currentsign"));//결재권구분
		parameters.setString(i++, paramInfo.getString("managerHangNo"));//현소속부서
		parameters.setString(i++, paramInfo.getString("managerNo"));//주요업무1
		parameters.setString(i++, paramInfo.getString("terminalNo"));//결재권구분
   
		return template.insert(conn, parameters);
	}
}
 