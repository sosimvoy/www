/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090610.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-19
* 프로그램내용	   : 시스템운영 > 부서코드관리
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090610 {

	/* 부서코드 조회 */
	public static List getPartCodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
		  
	    sb.append("	 SELECT A.M350_YEAR YEAR, A.M350_PARTCODE PARTCODE, A.M350_PARTNAME M350_PARTNAME,A.M350_INSERTYN INSERTYN,           \n");
	    sb.append("     	    A.M350_REALLOTPARTYN REALLOTPARTYN, B.M350_REALLOTPARTCODE REALLOTPARTCODE ,B.M350_PARTNAME PARTNAME,         \n");
	    sb.append("          B.M350_PARTCODE M350_PARTCODE, B.M350_INSERTYN M350_INSERTYN,B.M350_REALLOTPARTYN M350_REALLOTPARTYN,         \n");
	    sb.append("          B.M350_RECEIVENAME M350_RECEIVENAME,B.M350_RECEIVECODE M350_RECEIVECODE                                       \n");
        sb.append("	   FROM M350_PARTCODE_T A, M350_PARTCODE_T B                                                                          \n");
        sb.append("	  WHERE B.M350_REALLOTPARTCODE = A.M350_PARTCODE                                                                      \n");
        sb.append("	    AND B.M350_YEAR = A.M350_YEAR                                                                                     \n");
	    sb.append("	    AND A.M350_YEAR     =  ?                                                                                          \n");
        sb.append(" ORDER BY B.M350_PARTCODE                                                                                               \n"); 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("under_year"));
  
		return template.getList(conn, parameters);
	}


    /* 부서코드 부서명 */
    public static List<CommonEntity> getAllotPartCode(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sql = new StringBuffer();
 											
		sql.append("  SELECT M350_PARTCODE,M350_PARTNAME  	\n"); 
        sql.append("    FROM M350_PARTCODE_T                \n"); 
        sql.append("   WHERE M350_REALLOTPARTYN = 'N'       \n"); 
        sql.append("     AND M350_YEAR = ?                  \n");
    
                             
  	    QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

        int i = 1;
		parameters.setString(i++,  paramInfo.getString("under_year"));
        
        return template.getList(conn, parameters);
    }

	 /* 부서 코드 등록 */
	 public static int insertPartCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M350_PARTCODE_T            \n");
		sb.append("           ( M350_YEAR,                 \n"); 
		sb.append("	            M350_PARTCODE,             \n"); 
		sb.append("	            M350_PARTNAME,             \n");
		sb.append("	            M350_INSERTYN,             \n");
		sb.append("	            M350_REALLOTPARTYN,        \n");
		sb.append("	            M350_REALLOTPARTCODE,      \n");
		sb.append("	            M350_RECEIVENAME,          \n");
		sb.append("	            M350_RECEIVECODE)     \n");
		sb.append("     VALUES(                            \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("								?           )            \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("partCode"));
    parameters.setString(i++, paramInfo.getString("partName"));
		parameters.setString(i++, paramInfo.getString("insertYN"));
		parameters.setString(i++, paramInfo.getString("reAllotPartYN"));
    parameters.setString(i++, paramInfo.getString("reAllotPartCode"));
		parameters.setString(i++, paramInfo.getString("receiveName"));
    parameters.setString(i++, paramInfo.getString("receiveCode"));

		return template.insert(conn, parameters); 
	}
		/* 부서 코드 삭제 */ 
  public static int deletePartCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M350_PARTCODE_T    \n");
    sb.append("  WHERE  M350_PARTCODE = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("partCode"));

    return template.delete(conn, parameters);
  }
}