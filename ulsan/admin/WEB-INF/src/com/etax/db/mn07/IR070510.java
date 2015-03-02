/***********************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070510.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-09-13
* 프로그램내용	   : 일계/보고서 > 
 내역조회
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070510 {
  
	/* 지급증명 조회 */ 
	public static List<CommonEntity> getJigupReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 	
        sb.append("   SELECT CASE                                                                                      \n");
        sb.append("          WHEN GBN = '소계' THEN GBN                                                                \n");
        sb.append("          ELSE ROW_NUMBER() OVER(PARTITION BY GBN ORDER BY TO_NUMBER(M030_ORDERNO))||'' END AS GBN  \n");
        sb.append("        , CASE                                                                                      \n");
        sb.append("          WHEN GBN = '소계' THEN M030_DATE                                                          \n");
        sb.append("          ELSE M030_ORDERNO||'' END AS M030_ORDERNO                                                 \n");
        sb.append("        , CASE                                                                                      \n");
        sb.append("          WHEN GBN = '소계' THEN '건수 :' || G_CNT                                                  \n");                                                      
        sb.append("          ELSE M030_ORDERNAME END AS M030_ORDERNAME                                                 \n");
        sb.append("        , M030_AMT                                                                                  \n");
        sb.append("        , M030_DATE                                                                                 \n");
        sb.append("    FROM (                                                                                          \n");
        sb.append("           SELECT CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN '소계'                                                         \n");
        sb.append("                  ELSE M030_DATE END AS GBN                                                         \n");
        sb.append("                , M030_DATE                                                                         \n");
        sb.append("                , M030_ACCTYPE                                                                      \n");
        sb.append("                , M030_ACCCODE                                                                      \n");
        sb.append("                , M030_YEAR                                                                         \n");
        sb.append("                , CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN '0'                                                            \n");
        sb.append("                  ELSE M030_ORDERNO END AS M030_ORDERNO                                             \n");
        sb.append("                , CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN ' '                                                            \n");
        sb.append("                  ELSE M030_ORDERNAME END AS M030_ORDERNAME                                         \n");
        sb.append("                , SUM(NVL(M030_AMT,0)) AS M030_AMT                                                  \n");
        sb.append("                , COUNT(*) AS G_CNT                                                                 \n");
        sb.append("             FROM M030_TAXOTHER_T                                                                   \n");
        sb.append("                , (SELECT LEVEL AS POSI                                                             \n");
        sb.append("                     FROM DUAL                                                                      \n");
        sb.append("                  CONNECT BY LEVEL < 3 )                                                            \n");
        sb.append("            WHERE M030_DATE BETWEEN ? AND ?                                                         \n");
		sb.append("              AND M030_YEAR = ?                                                                     \n");
        sb.append("              AND M030_ACCTYPE = ?                                                                  \n");
        sb.append("              AND M030_ACCCODE = ?                                                                  \n");
        sb.append("         GROUP BY CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN '소계'                                                         \n");
        sb.append("                  ELSE M030_DATE END                                                                \n");
        sb.append("                , M030_DATE                                                                         \n");
        sb.append("                , M030_ACCTYPE                                                                      \n");
        sb.append("                , M030_ACCCODE                                                                      \n");
        sb.append("                , M030_YEAR                                                                         \n");
        sb.append("                , CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN '0'                                                            \n");
        sb.append("                  ELSE M030_ORDERNO END                                                             \n");
        sb.append("                , CASE                                                                              \n");
        sb.append("                  WHEN POSI = 1 THEN ' '                                                            \n");
        sb.append("                  ELSE M030_ORDERNAME END                                                           \n");
        sb.append("                 )                                                                                  \n");
        sb.append("         ORDER BY M030_DATE                                                                         \n");
        sb.append("                , CASE                                                                              \n");
        sb.append("                  WHEN GBN='소계' THEN 0                                                            \n");
        sb.append("                  ELSE TO_NUMBER(M030_ORDERNO) END                                                  \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++,  paramInfo.getString("su_sdate"));
		parameters.setString(idx++,  paramInfo.getString("su_edate"));
		parameters.setString(idx++,  paramInfo.getString("year"));
		parameters.setString(idx++,  paramInfo.getString("accGubun"));
		parameters.setString(idx++,  paramInfo.getString("accCode"));
	
        
    return template.getList(conn, parameters);
  }
		
	/* 회계구분에 따른 회계명*/ 
	public static List<CommonEntity> getAccCdList3(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, B.M360_ACCCODE, B.M360_ACCNAME       \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	                \n");   
		sql.append("        ,M360_ACCCODE_T B                                     \n"); 
		sql.append("  WHERE  A.M390_YEAR(+)= B.M360_YEAR	                        \n");  
    sql.append("    AND  A.M390_ACCGUBUN(+) = B.M360_ACCGUBUN                 \n");  
	  sql.append("    AND  A.M390_ACCCODE(+) = B.M360_ACCCODE										\n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("  AND  B.M360_ACCGUBUN = ?                                  \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, B.M360_ACCNAME, B.M360_ACCCODE      \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                      \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("accGubun")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun"));
		}    
    return template.getList(conn, parameters);
  }
	 /* 회계구분에 따른 세목명*/ 
	public static List<CommonEntity> getSemokList1(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																											
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("    AND A.M390_ACCGUBUN = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("accCode")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}	
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  parameters.setString(idx++, paramInfo.getString("accGubun"));
		}
		if (!"".equals(paramInfo.getString("accCode")) ) {
		  parameters.setString(idx++, paramInfo.getString("accCode"));
		}
        
    return template.getList(conn, parameters);
	 }
                                       
	 /* 회계코드에 따른 세목 부서명*/ 
	public static CommonEntity getSemokName(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																											
		sql.append(" SELECT  MIN(A.M390_SEMOKCODE) SEMOKCODE, MIN(B.M370_SEMOKNAME) SEMOKNAME, C.M350_PARTNAME PARTNAME  \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                         \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                            \n");
		sql.append("        ,M350_PARTCODE_T C                                             \n");
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                     \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                             \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                           \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                               \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                           \n");    
		sql.append("    AND  A.M390_YEAR = C.M350_YEAR                                     \n");
		sql.append("    AND  A.M390_PARTCODE = C.M350_PARTCODE                             \n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("    AND A.M390_ACCGUBUN = ?                                          \n"); 
		}	
		if (!"".equals(paramInfo.getString("accCode")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                           \n"); 
		}	
		sql.append(" GROUP BY  C.M350_PARTNAME                                             \n"); 
	

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  parameters.setString(idx++, paramInfo.getString("accGubun"));
		}
		if (!"".equals(paramInfo.getString("accCode")) ) {
		  parameters.setString(idx++, paramInfo.getString("accCode"));
		}
        
    return template.getData(conn, parameters);
	 }
}