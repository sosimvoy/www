/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR091410.java
* 프로그램작성자 : 
* 프로그램작성일 : 2010-05-06
* 프로그램내용	 : 관리자 > 직인정보관리
******************************************************/
package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091410 {
	private static Logger logger = Logger.getLogger(IR091410.class);	// log4j 설정
	
	/* 사인 정보 */
	public static CommonEntity getSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT  *                            \n");
		sb.append("   FROM M340_USERSEAL_T               \n");
		sb.append("  WHERE M340_CURRENTORGAN = ?         \n"); 
	
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
               
    parameters.setString(idx++, paramInfo.getString("current_organ") );     
		 
	  return template.getData(conn, parameters);
	} 

	/* 사인등록 */
	public static int insertSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("   UPDATE M340_USERSEAL_T           \n");
    sb.append("      SET M340_FNAME = ?            \n");
    sb.append("    WHERE M340_CURRENTORGAN = ?     \n");
	
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("f_name") );
		parameters.setString(idx++, paramInfo.getString("current_organ") );
		
	  return template.update(conn, parameters);
	} 

	/* 사인삭제 */
	public static int deleteSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("  UPDATE M340_USERSEAL_T                    \n");
		sb.append("    SET M340_FNAME = ''                     \n");
		sb.append("  WHERE M340_CURRENTORGAN = ?               \n"); 
		
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("current_organ") );
		 
	  return template.delete(conn, parameters);
	}
}