/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : SuperDAO.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-14
* 프로그램내용   : common - 책임자승인 전 책임자여부 체크
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class SuperDAO {

  private static Logger logger = Logger.getLogger(SuperDAO.class);  // log4j 설정
  
	/* 책임자인지 체크 */ 
	public static CommonEntity superCheck(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT *                          \n");
    sb.append("   FROM M260_USERMANAGER_T				  \n");
    sb.append("  WHERE M260_USERID = ?						\n");
    sb.append("    AND M260_CURRENTSIGNTYPE = ? 	\n"); //B2
    sb.append("    AND M260_SERIAL = ?						\n");
    sb.append("    AND M260_SUB_DN = ?						\n");									

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("user_id"));
		parameters.setString(idx++, "B2");
		parameters.setString(idx++, paramInfo.getString("serial"));
		parameters.setString(idx++, paramInfo.getString("subjectDN"));
        
    return template.getData(conn, parameters);
  }
}