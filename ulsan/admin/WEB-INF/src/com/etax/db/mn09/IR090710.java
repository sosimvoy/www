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

public class IR090710 {

	/* 회계 코드 조회 */
	public static List getAccCodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("	 SELECT M360_YEAR,			     \n"); 
		sb.append("  DECODE(M360_ACCGUBUN,'A','일반회계','B','특별회계','C','공기업특별회계','D','세입세출외현금','E','기금') ACCGUBUN,   \n");
		sb.append("			    M360_ACCGUBUN, 			 \n");
		sb.append("			    M360_ACCCODE, 			 \n");
		sb.append("			    M360_ACCNAME 			   \n"); 
		sb.append("	   FROM M360_ACCCODE_T       \n");
		sb.append("   WHERE M360_YEAR = ?	       \n");
		sb.append("     AND M360_ACCGUBUN = ?	   \n");
                                                                

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("under_year"));
		parameters.setString(i++,  paramInfo.getString("accGubun"));
  
		return template.getList(conn, parameters);
	}

	 /* 회계 코드 등록 */
	 public static int insertAccCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M360_ACCCODE_T        \n");
		sb.append("           ( M360_YEAR,            \n"); 
		sb.append("	            M360_ACCGUBUN,        \n"); 
		sb.append("	            M360_ACCCODE,         \n"); 
		sb.append("	            M360_ACCNAME          \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("								?           )       \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("accGubun1"));
    parameters.setString(i++, paramInfo.getString("accCode"));
		parameters.setString(i++, paramInfo.getString("accName"));

		return template.insert(conn, parameters); 
	}
		
		/* 회계 코드 삭제 */ 
  public static int deleteAccCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M360_ACCCODE_T      \n");
    sb.append("       WHERE M360_YEAR     = ?   \n");
		sb.append("         AND M360_ACCGUBUN = ?   \n");
		sb.append("         AND M360_ACCCODE  = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));
		parameters.setString(idx++, paramInfo.getString("accCode"));

    return template.delete(conn, parameters);
  }
}