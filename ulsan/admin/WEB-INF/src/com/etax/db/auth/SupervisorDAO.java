/*****************************************************
* 프로젝트명		 : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명		 : SupervisorDAO.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-19
* 프로그램내용	 : 승인권한
******************************************************/

package com.etax.db.auth;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;

public class SupervisorDAO {

  /* 권한 체크 */
	public static CommonEntity getPowCheck(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	*                         \n");
		sb.append("  FROM M260_USERMANAGER_T        \n");
		sb.append(" WHERE M260_USERID = TRIM(?)     \n");
    sb.append("   AND M260_CURRENTORGAN = ?     \n");
    sb.append("   AND M260_CURRENTSIGNTYPE = ?  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("user_id"));
    parameters.setString(idx++, "1");
    parameters.setString(idx++, "B2");

		return template.getData(conn, parameters);
	}


	/* 아이디체크 */
	public static CommonEntity getInfoById2(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	*                       \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = TRIM(?)   \n");
	  sb.append("   AND M260_USERPW = TRIM(?)   \n");
   	sb.append("   AND M260_CURRENTORGAN = '1' \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));
		parameters.setString(2, paramInfo.getString("userpw"));

		return template.getData(conn, parameters);
	}


	/* 인증서체크 */
	public static CommonEntity getInfoByAuth(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                            \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '　', '')  = REPLACE(REPLACE(?, ' ', ''), '　', '')  \n"); 
    sb.append("   AND M260_CURRENTORGAN = '1' \n");
    sb.append("   AND M260_USERID = TRIM(?)   \n");
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx =1;
		parameters.setString(idx++, paramInfo.getString("serial"));
		parameters.setString(idx++, paramInfo.getString("subjectDN"));
    parameters.setString(idx++, paramInfo.getString("user_id"));
	
		return template.getData(conn, parameters);
	}
}
 