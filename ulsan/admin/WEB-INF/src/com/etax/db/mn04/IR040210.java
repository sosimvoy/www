/*****************************************************
* 프로젝트명     : 경기도 세출시스템
* 프로그램명     : IR040210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 일상경비 > 지급명령 오류조회
******************************************************/

package com.etax.db.mn04;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR040210 {

  /* 예산서 조회 */
  public static List<CommonEntity> getBudgetList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("  SELECT  M080_YEAR            \n");
    sb.append("         ,M080_SEQ             \n");  
    sb.append("         ,M080_MOK             \n");
		sb.append("         ,M080_SEMOKCODE       \n");
    sb.append("         ,M080_SOGWANPART      \n");
		sb.append("         ,M080_SILGWA          \n");
		sb.append("         ,M080_USERNAME        \n");
		sb.append("         ,M080_INTELNO         \n");
		sb.append("         ,M080_GWAMOK          \n");
		sb.append("         ,M080_BUSINESSNAME    \n");
		sb.append("         ,M080_DANGCHOAMT      \n");
		sb.append("         ,(M080_CHUKYNGAMT1           \n");
		sb.append("         +M080_CHUKYNGAMT2           \n");
		sb.append("         +M080_CHUKYNGAMT3           \n");
		sb.append("         +M080_CHUKYNGAMT4           \n");
		sb.append("         +M080_CHUKYNGAMT5           \n");
		sb.append("         +M080_CHUKYNGAMT6           \n");
		sb.append("         +M080_CHUKYNGAMT7           \n");
		sb.append("         +M080_CHUKYNGAMT8           \n");
		sb.append("         +M080_CHUKYNGAMT9) CHUKYUNGAMT  \n");
		sb.append("         ,(M080_MONTHAMT_1             \n");
    sb.append("         +M080_MONTHAMT_2              \n");
    sb.append("         +M080_MONTHAMT_3              \n");
    sb.append("         +M080_MONTHAMT_4              \n");
    sb.append("         +M080_MONTHAMT_5              \n");
    sb.append("         +M080_MONTHAMT_6              \n");
    sb.append("         +M080_MONTHAMT_7              \n");
    sb.append("         +M080_MONTHAMT_8              \n");
    sb.append("         +M080_MONTHAMT_9              \n");
    sb.append("         +M080_MONTHAMT_10             \n");
    sb.append("         +M080_MONTHAMT_11             \n");
    sb.append("         +M080_MONTHAMT_12             \n");
    sb.append("         +M080_MONTHAMT_13             \n");
    sb.append("         +M080_MONTHAMT_14) MONTHAMT  \n");
		sb.append("     	  ,M080_WRITEDATE       \n");
    sb.append("  FROM M080_BUDGETMANAGE_T     \n");
    sb.append(" WHERE M080_YEAR = ?           \n");
		if (!"".equals(paramInfo.getString("date"))){
		sb.append("   AND M080_WRITEDATE = ?      \n");
		 }
    if (!"".equals(paramInfo.getString("mok"))){
	  sb.append("		AND M080_MOK = ?            \n");
		}  
		if (!"".equals(paramInfo.getString("sogwanpart"))){
	  sb.append("		AND M080_SOGWANPART = ?     \n");
		}  
		if (!"".equals(paramInfo.getString("silgwa"))){
	  sb.append("		AND M080_SILGWA = ?         \n");
		}  
    if (!"".equals(paramInfo.getString("businessname"))){
	  sb.append("		AND M080_BUSINESSNAME LIKE ?   \n");
		}  
		if (!"".equals(paramInfo.getString("monthamt"))){
	  sb.append("		AND M080_MONTHAMT = ?       \n");
		}  
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, paramInfo.getString("year"));
		if (!"".equals(paramInfo.getString("date")) ) {
		parameters.setString(idx++, paramInfo.getString("date"));
		}
		if (!"".equals(paramInfo.getString("mok")) ) {
		parameters.setString(idx++, paramInfo.getString("mok"));
	  }
		if (!"".equals(paramInfo.getString("sogwanpart")) ) {
    parameters.setString(idx++, paramInfo.getString("sogwanpart"));
		}
		if (!"".equals(paramInfo.getString("silgwa")) ) {
		parameters.setString(idx++, paramInfo.getString("silgwa"));
		}
		if (!"".equals(paramInfo.getString("username")) ) {
		parameters.setString(idx++, paramInfo.getString("username"));
		}
		if (!"".equals(paramInfo.getString("intelno")) ) {
		parameters.setString(idx++, paramInfo.getString("intelno"));
		}
		if (!"".equals(paramInfo.getString("businessname")) ) {
      parameters.setString(idx++,"%"+paramInfo.getString("businessname")+"%");
    }
		if (!"".equals(paramInfo.getString("monthamt")) ) {
		parameters.setString(idx++, paramInfo.getString("monthamt"));
    }
		return template.getList(conn, parameters);
	}
  /* 예산서 조회 */
  public static List<CommonEntity> getExcelBudgetList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" WITH M080_TABLE AS (SELECT                                                         \n");
    sb.append("        M080_SEQ AS SEQ                                                             \n");
    sb.append("      , M080_SOGWANPART AS SOGWANPART                                               \n");
    sb.append("      , M080_SILGWA AS SILGWA                                                       \n");
    sb.append("      , M080_USERNAME AS USERNAME                                                   \n");
    sb.append("      , M080_INTELNO AS INTELNO                                                     \n");
    sb.append("      , M080_GWAMOK AS GWAMOK                                                       \n");
    sb.append("      , M080_BUSINESSNAME AS BUSINESSNAME                                           \n");
    sb.append("      , ( NVL(M080_DANGCHOAMT,0)                                                    \n");
    sb.append("      + NVL(M080_CHUKYNGAMT1,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT2,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT3,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT4,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT5,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT6,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT7,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT8,0)                                                     \n");
    sb.append("      + NVL(M080_CHUKYNGAMT9,0) )                                                   \n");
    sb.append("      - (   NVL(M080_MONTHAMT_1 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_2 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_3 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_4 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_5 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_6 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_7 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_8 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_9 ,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_10,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_11,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_12,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_13,0)                                                 \n");
    sb.append("          + NVL(M080_MONTHAMT_14,0) ) AS TOT_NOTAMT                                 \n");
    sb.append("      , NVL(M080_DANGCHOAMT,0) / 1000                                               \n");
    sb.append("      + NVL(M080_CHUKYNGAMT1,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT2,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT3,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT4,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT5,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT6,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT7,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT8,0) / 1000                                              \n");
    sb.append("      + NVL(M080_CHUKYNGAMT9,0) / 1000 AS TOT_CHUKYNG                               \n");
    sb.append("      , NVL(M080_DANGCHOAMT,0) / 1000 AS DANGCHOAMT                                 \n");
    sb.append("      , M080_CHUKYNGAMT1 / 1000 AS CHUKYNGAMT1                                      \n");
    sb.append("      , M080_CHUKYNGAMT2 / 1000 AS CHUKYNGAMT2                                      \n");
    sb.append("      , M080_CHUKYNGAMT3 / 1000 AS CHUKYNGAMT3                                      \n");
    sb.append("      , M080_CHUKYNGAMT4 / 1000 AS CHUKYNGAMT4                                      \n");
    sb.append("      , M080_CHUKYNGAMT5 / 1000 AS CHUKYNGAMT5                                      \n");
    sb.append("      , M080_CHUKYNGAMT6 / 1000 AS CHUKYNGAMT6                                      \n");
    sb.append("      , M080_CHUKYNGAMT7 / 1000 AS CHUKYNGAMT7                                      \n");
    sb.append("      , M080_CHUKYNGAMT8 / 1000 AS CHUKYNGAMT8                                      \n");
    sb.append("      , M080_CHUKYNGAMT9 / 1000 AS CHUKYNGAMT9                                      \n");
    sb.append("      , NVL(M080_MONTHAMT_1 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_2 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_3 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_4 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_5 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_6 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_7 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_8 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_9 ,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_10,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_11,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_12,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_13,0)                                                     \n");
    sb.append("      + NVL(M080_MONTHAMT_14,0) AS TOT_MONTH                                        \n");
    sb.append("      , M080_MONTHAMT_1 AS MONTHAMT_1                                               \n");
    sb.append("      , M080_MONTHAMT_2 AS MONTHAMT_2                                               \n");
    sb.append("      , M080_MONTHAMT_3 AS MONTHAMT_3                                               \n");
    sb.append("      , M080_MONTHAMT_4 AS MONTHAMT_4                                               \n");
    sb.append("      , M080_MONTHAMT_5 AS MONTHAMT_5                                               \n");
    sb.append("      , M080_MONTHAMT_6 AS MONTHAMT_6                                               \n");
    sb.append("      , M080_MONTHAMT_7 AS MONTHAMT_7                                               \n");
    sb.append("      , M080_MONTHAMT_8 AS MONTHAMT_8                                               \n");
    sb.append("      , M080_MONTHAMT_9 AS MONTHAMT_9                                               \n");
    sb.append("      , M080_MONTHAMT_10 AS MONTHAMT_10                                             \n");
    sb.append("      , M080_MONTHAMT_11 AS MONTHAMT_11                                             \n");
    sb.append("      , M080_MONTHAMT_12 AS MONTHAMT_12                                             \n");
    sb.append("      , M080_MONTHAMT_13 AS MONTHAMT_13                                             \n");
    sb.append("      , M080_MONTHAMT_14 AS MONTHAMT_14                                             \n");
    sb.append("      , M080_YEAR AS YEAR                                                           \n");
    sb.append("      , M080_MOK AS MOK                                                             \n");
    sb.append("      , M080_SEMOKCODE AS SEMOKCODE                                                 \n");
    sb.append("      , M080_WRITEDATE AS WRITEDATE                                                 \n");
    sb.append("    FROM M080_BUDGETMANAGE_T                                                        \n"); 
    sb.append("   WHERE 1 = 1                                                                      \n"); 
    sb.append("    AND M080_YEAR = ?                                                               \n");
		if (!"".equals(paramInfo.getString("date"))){
    sb.append("    AND M080_WRITEDATE = ?                                                          \n");
 	  }
    if (!"".equals(paramInfo.getString("mok"))){
    sb.append("    AND M080_MOK = ?                                                                \n");
 	  }
		if (!"".equals(paramInfo.getString("sogwanpart"))){
    sb.append("    AND M080_SOGWANPART = ?                                                         \n");
 	  }
		if (!"".equals(paramInfo.getString("silgwa"))){
    sb.append("    AND M080_SILGWA = ?                                                             \n");
 	  }
    if (!"".equals(paramInfo.getString("businessname"))){
    sb.append("    AND M080_BUSINESSNAME LIKE ?                                                    \n");
 	  }
		if (!"".equals(paramInfo.getString("monthamt"))){
    sb.append("    AND M080_MONTHAMT = ?                                                           \n");
 	  }
    sb.append(" )                                                                                  \n");
    sb.append(" SELECT CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN 0                                                        \n");
    sb.append("        WHEN POSI = 2 THEN 0                                                        \n");
    sb.append("        ELSE SEQ END AS SEQ                                                         \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SOGWANPART END AS SOGWANPART                                           \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SILGWA END AS SILGWA                                                   \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE USERNAME END AS USERNAME                                               \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE INTELNO END AS INTELNO                                                 \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN '총합계'                                                 \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-01' THEN '국고보조금 소계'                  \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-02' THEN '광특회계보조금 소계'              \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-03' THEN '기금 소계'                        \n");
    sb.append("        ELSE GWAMOK END AS GWAMOK                                                   \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE BUSINESSNAME END AS BUSINESSNAME                                       \n");
    sb.append("      , SUM(TOT_NOTAMT) AS TOT_NOTAMT                                               \n");
    sb.append("      , SUM(TOT_CHUKYNG) AS TOT_CHUKYNG                                             \n");
    sb.append("      , SUM(DANGCHOAMT) AS DANGCHOAMT                                               \n");
    sb.append("      , SUM(CHUKYNGAMT1) AS CHUKYNGAMT1                                             \n");
    sb.append("      , SUM(CHUKYNGAMT2) AS CHUKYNGAMT2                                             \n");
    sb.append("      , SUM(CHUKYNGAMT3) AS CHUKYNGAMT3                                             \n");
    sb.append("      , SUM(CHUKYNGAMT4) AS CHUKYNGAMT4                                             \n");
    sb.append("      , SUM(CHUKYNGAMT5) AS CHUKYNGAMT5                                             \n");
    sb.append("      , SUM(CHUKYNGAMT6) AS CHUKYNGAMT6                                             \n");
    sb.append("      , SUM(CHUKYNGAMT7) AS CHUKYNGAMT7                                             \n");
    sb.append("      , SUM(CHUKYNGAMT8) AS CHUKYNGAMT8                                             \n");
    sb.append("      , SUM(CHUKYNGAMT9) AS CHUKYNGAMT9                                             \n");
    sb.append("      , SUM(TOT_MONTH) AS TOT_MONTH                                                 \n");
    sb.append("      , SUM(MONTHAMT_1) AS MONTHAMT_1                                               \n");
    sb.append("      , SUM(MONTHAMT_2) AS MONTHAMT_2                                               \n");
    sb.append("      , SUM(MONTHAMT_3) AS MONTHAMT_3                                               \n");
    sb.append("      , SUM(MONTHAMT_4) AS MONTHAMT_4                                               \n");
    sb.append("      , SUM(MONTHAMT_5) AS MONTHAMT_5                                               \n");
    sb.append("      , SUM(MONTHAMT_6) AS MONTHAMT_6                                               \n");
    sb.append("      , SUM(MONTHAMT_7) AS MONTHAMT_7                                               \n");
    sb.append("      , SUM(MONTHAMT_8) AS MONTHAMT_8                                               \n");
    sb.append("      , SUM(MONTHAMT_9) AS MONTHAMT_9                                               \n");
    sb.append("      , SUM(MONTHAMT_10) AS MONTHAMT_10                                             \n");
    sb.append("      , SUM(MONTHAMT_11) AS MONTHAMT_11                                             \n");
    sb.append("      , SUM(MONTHAMT_12) AS MONTHAMT_12                                             \n");
    sb.append("      , SUM(MONTHAMT_13) AS MONTHAMT_13                                             \n");
    sb.append("      , SUM(MONTHAMT_14) AS MONTHAMT_14                                             \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE YEAR END AS YEAR                                                       \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE MOK END AS MOK                                                         \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SEMOKCODE END AS SEMOKCODE                                             \n");  
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE WRITEDATE END AS WRITEDATE                                             \n");
    sb.append("   FROM M080_TABLE                                                                  \n");
    sb.append("      , (SELECT LEVEL AS POSI                                                       \n");
    sb.append("           FROM DUAL                                                                \n");
    sb.append("          CONNECT BY LEVEL < 4 )                                                    \n");
    sb.append("   GROUP BY CASE                                                                      \n");
    sb.append("        WHEN POSI = 1 THEN 0                                                        \n");
    sb.append("        WHEN POSI = 2 THEN 0                                                        \n");
    sb.append("        ELSE SEQ END                                                                \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SOGWANPART END                                                         \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SILGWA END                                                             \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE USERNAME END                                                           \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE INTELNO END                                                            \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN '총합계'                                                 \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-01' THEN '국고보조금 소계'                  \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-02' THEN '광특회계보조금 소계'              \n");
    sb.append("        WHEN POSI = 2 AND GWAMOK = '511-03' THEN '기금 소계'                        \n");
    sb.append("        ELSE GWAMOK END                                                             \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE BUSINESSNAME END                                                       \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE YEAR END                                                               \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE MOK END                                                                \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE SEMOKCODE END                                                          \n");
    sb.append("      , CASE                                                                        \n");
    sb.append("        WHEN POSI = 1 THEN ' '                                                      \n");
    sb.append("        WHEN POSI = 2 THEN ' '                                                      \n");
    sb.append("        ELSE WRITEDATE END                                                          \n");
    sb.append("  ORDER BY CASE                                                                     \n");
    sb.append("        WHEN GWAMOK = '총합계' THEN '000-0000'                                      \n");
    sb.append("        WHEN GWAMOK = '국고보조금 소계' THEN '511-0100'                             \n");
    sb.append("        WHEN GWAMOK = '광특회계보조금 소계' THEN '511-0200'                         \n");
    sb.append("        WHEN GWAMOK = '기금 소계' THEN '511-0300'                                   \n");
    sb.append("        ELSE GWAMOK || '99'  END                                                    \n");
    sb.append("      , SEQ                                                                         \n");
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, paramInfo.getString("year"));
		if (!"".equals(paramInfo.getString("date")) ) {
		parameters.setString(idx++, paramInfo.getString("date"));
		}
		if (!"".equals(paramInfo.getString("mok")) ) {
		parameters.setString(idx++, paramInfo.getString("mok"));
	  }
		if (!"".equals(paramInfo.getString("sogwanpart")) ) {
    parameters.setString(idx++, paramInfo.getString("sogwanpart"));
		}
		if (!"".equals(paramInfo.getString("silgwa")) ) {
		parameters.setString(idx++, paramInfo.getString("silgwa"));
		}
		if (!"".equals(paramInfo.getString("username")) ) {
		parameters.setString(idx++, paramInfo.getString("username"));
		}
		if (!"".equals(paramInfo.getString("intelno")) ) {
		parameters.setString(idx++, paramInfo.getString("intelno"));
		}
		if (!"".equals(paramInfo.getString("businessname")) ) {
      parameters.setString(idx++,"%"+paramInfo.getString("businessname")+"%");
    }
		if (!"".equals(paramInfo.getString("monthamt")) ) {
		parameters.setString(idx++, paramInfo.getString("monthamt"));
    }
		return template.getList(conn, parameters);
	}
	/*예산서 상세*/
		public static CommonEntity getBudgetView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("   SELECT M080_YEAR                  \n"); 
    sb.append("         ,M080_SEQ                   \n");  
    sb.append("         ,M080_MOK                   \n");
		sb.append("         ,M080_SEMOKCODE             \n");
    sb.append("         ,M080_SOGWANPART            \n");
		sb.append("         ,M080_SILGWA                \n");
		sb.append("         ,M080_USERNAME              \n");
		sb.append("         ,M080_INTELNO               \n");
		sb.append("         ,M080_GWAMOK                \n");
		sb.append("         ,M080_BUSINESSNAME          \n");
		sb.append("         ,M080_DANGCHOAMT            \n");
		sb.append("         ,(M080_CHUKYNGAMT1           \n");
		sb.append("         +M080_CHUKYNGAMT2           \n");
		sb.append("         +M080_CHUKYNGAMT3           \n");
		sb.append("         +M080_CHUKYNGAMT4           \n");
		sb.append("         +M080_CHUKYNGAMT5           \n");
		sb.append("         +M080_CHUKYNGAMT6           \n");
		sb.append("         +M080_CHUKYNGAMT7           \n");
		sb.append("         +M080_CHUKYNGAMT8           \n");
		sb.append("         +M080_CHUKYNGAMT9) CHUKYUNGAMT  \n");
		sb.append("         ,(M080_MONTHAMT_1             \n");
    sb.append("         +M080_MONTHAMT_2              \n");
    sb.append("         +M080_MONTHAMT_3              \n");
    sb.append("         +M080_MONTHAMT_4              \n");
    sb.append("         +M080_MONTHAMT_5              \n");
    sb.append("         +M080_MONTHAMT_6              \n");
    sb.append("         +M080_MONTHAMT_7              \n");
    sb.append("         +M080_MONTHAMT_8              \n");
    sb.append("         +M080_MONTHAMT_9              \n");
    sb.append("         +M080_MONTHAMT_10             \n");
    sb.append("         +M080_MONTHAMT_11             \n");
    sb.append("         +M080_MONTHAMT_12             \n");
    sb.append("         +M080_MONTHAMT_13             \n");
    sb.append("         +M080_MONTHAMT_14) MONTHAMT  \n");
		sb.append("     	  ,M080_WRITEDATE             \n");
    sb.append("     FROM M080_BUDGETMANAGE_T        \n");
    sb.append("    WHERE M080_SEQ = ?               \n");
		sb.append("      AND M080_YEAR = ?               \n");
 
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));
		parameters.setString(2, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}

	/* 예산서 수정 */ 
  public static int budgetUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer(); 
   
    sb.append(" UPDATE M080_BUDGETMANAGE_T         \n");
    sb.append("    SET M080_MOK = ?                \n");
		sb.append("       ,M080_SEMOKCODE = ?          \n");
		sb.append("       ,M080_SOGWANPART = ?         \n");   
		sb.append("       ,M080_SILGWA = ?             \n");  
		sb.append("       ,M080_USERNAME = ?           \n");  
		sb.append("       ,M080_INTELNO = ?            \n");  
		sb.append("   	  ,M080_GWAMOK = ?             \n");
		sb.append("	      ,M080_BUSINESSNAME = ?       \n");
		sb.append(" 	    ,M080_DANGCHOAMT = NVL(?, 0)         \n");
		sb.append("	      ,M080_CHUKYNGAMT1 = NVL(?, 0)         \n");
		sb.append(" WHERE  M080_SEQ  = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1; 
    
		parameters.setString(idx++, paramInfo.getString("mok"));
		parameters.setString(idx++, paramInfo.getString("semok"));
		parameters.setString(idx++, paramInfo.getString("sogwanpart"));
		parameters.setString(idx++, paramInfo.getString("silgwa"));
		parameters.setString(idx++, paramInfo.getString("username"));
		parameters.setString(idx++, paramInfo.getString("intelno"));
		parameters.setString(idx++, paramInfo.getString("gwamok"));
		parameters.setString(idx++, paramInfo.getString("businessname"));
		parameters.setString(idx++, paramInfo.getString("dangchoamt"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt"));
	  parameters.setString(idx++, paramInfo.getString("seq"));

    return template.update(conn, parameters);
  }

	/*예산서 삭제*/
	 public static int budgetDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M080_BUDGETMANAGE_T    \n");
    sb.append("  WHERE M080_SEQ = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("seq")); 

    return template.delete(conn, parameters);
  }
		/*예산서 상세*/
		public static CommonEntity getChukyngView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("   SELECT M080_YEAR                  \n");
		sb.append("         ,M080_SEQ                   \n");
		sb.append("         ,M080_CHUKYNGAMT1            \n");
		sb.append("         ,M080_CHUKYNGAMT2           \n");
		sb.append("         ,M080_CHUKYNGAMT3           \n");
		sb.append("         ,M080_CHUKYNGAMT4           \n");
		sb.append("         ,M080_CHUKYNGAMT5           \n");
		sb.append("         ,M080_CHUKYNGAMT6           \n");
		sb.append("         ,M080_CHUKYNGAMT7           \n");
		sb.append("         ,M080_CHUKYNGAMT8           \n");
		sb.append("         ,M080_CHUKYNGAMT9           \n");
    sb.append("         ,(M080_CHUKYNGAMT1           \n");
		sb.append("         +M080_CHUKYNGAMT2           \n");
		sb.append("         +M080_CHUKYNGAMT3           \n");
		sb.append("         +M080_CHUKYNGAMT4           \n");
		sb.append("         +M080_CHUKYNGAMT5           \n");
		sb.append("         +M080_CHUKYNGAMT6           \n");
		sb.append("         +M080_CHUKYNGAMT7           \n");
		sb.append("         +M080_CHUKYNGAMT8           \n");
		sb.append("         +M080_CHUKYNGAMT9) TOT_AMT  \n");
    sb.append("         ,M080_BUSINESSNAME          \n");
    sb.append("     FROM M080_BUDGETMANAGE_T        \n");
    sb.append("    WHERE M080_SEQ = ?               \n");
		sb.append("      AND M080_YEAR = ?              \n");
 
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));
		parameters.setString(2, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}

	/* 예산서 수정 */ 
  public static int chukyngUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer(); 
   
    sb.append(" UPDATE M080_BUDGETMANAGE_T        \n");
    sb.append("    SET M080_CHUKYNGAMT1 = NVL(?, 0)        \n");
    sb.append("       ,M080_CHUKYNGAMT2 = NVL(?, 0)       \n");
		sb.append("				,M080_CHUKYNGAMT3 = NVL(?, 0)       \n");
	  sb.append("				,M080_CHUKYNGAMT4 = NVL(?, 0)       \n");
		sb.append("				,M080_CHUKYNGAMT5 = NVL(?, 0)       \n");
		sb.append("				,M080_CHUKYNGAMT6 = NVL(?, 0)       \n");
	  sb.append("				,M080_CHUKYNGAMT7 = NVL(?, 0)       \n");
		sb.append("				,M080_CHUKYNGAMT8 = NVL(?, 0)       \n");
		sb.append("	      ,M080_CHUKYNGAMT9 = NVL(?, 0)       \n");
		sb.append(" WHERE  M080_SEQ  = ?              \n");
    sb.append("   AND  M080_YEAR  = ?              \n");
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1; 
    
		parameters.setString(idx++, paramInfo.getString("chukyngamt1"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt2"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt3"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt4"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt5"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt6"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt7"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt8"));
		parameters.setString(idx++, paramInfo.getString("chukyngamt9"));
	  parameters.setString(idx++, paramInfo.getString("seq"));
		parameters.setString(idx++, paramInfo.getString("year"));


    return template.update(conn, parameters);
  }


    /*수령액 상세*/
		public static CommonEntity getRevInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M080_YEAR                    \n");
    sb.append("       ,M080_SEQ                     \n");
    sb.append("       ,M080_MONTHAMT_1              \n");
    sb.append("       ,M080_MONTHAMT_2              \n");
    sb.append("       ,M080_MONTHAMT_3              \n");
    sb.append("       ,M080_MONTHAMT_4              \n");
    sb.append("       ,M080_MONTHAMT_5              \n");
    sb.append("       ,M080_MONTHAMT_6              \n");
    sb.append("       ,M080_MONTHAMT_7              \n");
    sb.append("       ,M080_MONTHAMT_8              \n");
    sb.append("       ,M080_MONTHAMT_9              \n");
    sb.append("       ,M080_MONTHAMT_10             \n");
    sb.append("       ,M080_MONTHAMT_11             \n");
    sb.append("       ,M080_MONTHAMT_12             \n");
    sb.append("       ,M080_MONTHAMT_13             \n");
    sb.append("       ,M080_MONTHAMT_14             \n");
    sb.append("       ,(M080_MONTHAMT_1             \n");
    sb.append("       +M080_MONTHAMT_2              \n");
    sb.append("       +M080_MONTHAMT_3              \n");
    sb.append("       +M080_MONTHAMT_4              \n");
    sb.append("       +M080_MONTHAMT_5              \n");
    sb.append("       +M080_MONTHAMT_6              \n");
    sb.append("       +M080_MONTHAMT_7              \n");
    sb.append("       +M080_MONTHAMT_8              \n");
    sb.append("       +M080_MONTHAMT_9              \n");
    sb.append("       +M080_MONTHAMT_10             \n");
    sb.append("       +M080_MONTHAMT_11             \n");
    sb.append("       +M080_MONTHAMT_12             \n");
    sb.append("       +M080_MONTHAMT_13             \n");
    sb.append("       +M080_MONTHAMT_14) TOT_AMT    \n");
    sb.append("       ,M080_BUSINESSNAME            \n");
    sb.append("   FROM M080_BUDGETMANAGE_T          \n");
    sb.append("  WHERE M080_SEQ = ?                 \n");
    sb.append("    AND M080_YEAR = ?                \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));
		parameters.setString(2, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}
    /*수령액 상세*/
		public static int updateRevInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M080_BUDGETMANAGE_T                    \n");
    sb.append("    SET M080_MONTHAMT_1 = ?                    \n");
    sb.append("      , M080_MONTHAMT_2 = ?                    \n");
    sb.append("      , M080_MONTHAMT_3 = ?                    \n");
    sb.append("      , M080_MONTHAMT_4 = ?                    \n");
    sb.append("      , M080_MONTHAMT_5 = ?                    \n");
    sb.append("      , M080_MONTHAMT_6 = ?                    \n");
    sb.append("      , M080_MONTHAMT_7 = ?                    \n");
    sb.append("      , M080_MONTHAMT_8 = ?                    \n");
    sb.append("      , M080_MONTHAMT_9 = ?                    \n");
    sb.append("      , M080_MONTHAMT_10 = ?                   \n");
    sb.append("      , M080_MONTHAMT_11 = ?                   \n");
    sb.append("      , M080_MONTHAMT_12 = ?                   \n");
    sb.append("      , M080_MONTHAMT_13 = ?                   \n");
    sb.append("      , M080_MONTHAMT_14 = ?                   \n");
    sb.append("  WHERE M080_SEQ = ?                           \n");
    sb.append("    AND M080_YEAR = ?                          \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1; 

		parameters.setString(idx++, paramInfo.getString("m080monthamt1"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt2"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt3"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt4"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt5"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt6"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt7"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt8"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt9"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt10"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt11"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt12"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt13"));
		parameters.setString(idx++, paramInfo.getString("m080monthamt14"));
		parameters.setString(idx++, paramInfo.getString("seq"));
		parameters.setString(idx++, paramInfo.getString("year"));
	
    return template.update(conn, parameters);
	}
}
