/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090610.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 시스템운영 > 회계코드관리
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091510 {

	/* 회계 코드 조회 */
	public static List getNapbujaList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("	 SELECT M560_NAPBUJA,			     \n"); 
		sb.append("			    M560_ADDRESS 			     \n"); 
		sb.append("	   FROM M560_NAPBUADDRESS_T    \n");
                                                                

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
  
		return template.getList(conn, parameters);
	}

	 /* 회계 코드 등록 */
	 public static int insertNapbuja(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M560_NAPBUADDRESS_T        \n");
		sb.append("           ( M560_NAPBUJA,            \n"); 
		sb.append("	            M560_ADDRESS          \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("								?           )       \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("Napbuja"));
		parameters.setString(i++, paramInfo.getString("Address"));

		return template.insert(conn, parameters); 
	}
		
		/* 회계 코드 삭제 */ 
  public static int deleteNapbuja(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M560_NAPBUADDRESS_T      \n");
    sb.append("       WHERE M560_NAPBUJA     = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int i = 1;
    
		parameters.setString(i++, paramInfo.getString("Napbuja"));

    return template.delete(conn, parameters);
  }
}