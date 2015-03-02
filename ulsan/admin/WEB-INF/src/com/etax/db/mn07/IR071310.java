/**************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR071310.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 세목별누계조회
***************************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR071310 {
	
	/* 수입일계표 조회 */
  public static List<CommonEntity> budgetdayprtList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" WITH PRTTMP AS (SELECT M550_YEAR                                                              \n");
    sb.append("       ,M550_TAXGBN                                                                            \n");
    sb.append("      , M550_MOKGUBUN                                                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,1) || '00000000' AS GROUP1                                         \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,2) || '0000000' AS GROUP2                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,3) || '00000' AS GROUP3                                           \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,5) || '0000' AS GROUP4                                            \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,7) || '00' AS GROUP5                                            \n");
    sb.append("      , M550_SEMOKCODE                                                                         \n");
    sb.append("      , M550_SEMOKNAME                                                                         \n");
    sb.append("      , M500_SUNABAMT                                                                          \n");
    sb.append("      , M500_SUNABCNT                                                                          \n");
    sb.append("      , M500_HWANBUAMT                                                                         \n");
    sb.append("      , M500_HWANBUCNT                                                                         \n");
    sb.append("      , CHAGAM_AMT                                                                             \n");
    sb.append("      , CHAGAM_CNT                                                                             \n");
    sb.append("   FROM M550_INCOMESEMOK_T A                                                                     \n");
    sb.append("      , ( SELECT M500_YEAR                                                                     \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("               , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                           \n");
    sb.append("               , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                           \n");
    sb.append("               , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                         \n");
    sb.append("               , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                         \n");
    sb.append("               , SUM(M500_SUNABAMT) - SUM(M500_HWANBUAMT) AS CHAGAM_AMT                        \n");
    sb.append("               , SUM(M500_SUNABCNT) - SUM(M500_HWANBUCNT) AS CHAGAM_CNT                        \n");
    sb.append("            FROM M500_INCOMEDAY_T                                                              \n");
    sb.append("           WHERE M500_YEAR = ?                                                                 \n");
    sb.append("             AND M500_DATE BETWEEN ?  AND ?                                                    \n");
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = 'SUM' AND M500_PARTCODE IN                                           \n");                                  
  		sb.append("                               ('00000','02130','02140','02190','02220','02240') THEN '1'    \n");                                  
  		sb.append("              WHEN  ? != 'SUM' AND M500_PARTCODE = ? THEN '1'                                \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M500_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M500_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M500_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("           GROUP BY M500_YEAR                                                                  \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("        ) B                                                                                    \n");
    sb.append("  WHERE M550_YEAR = ?                                                                          \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M550_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M550_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M550_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("    AND A.M550_YEAR = B.M500_YEAR(+)                                                           \n");
    sb.append("    AND A.M550_SEMOKCODE = B.M500_SEMOKCODE(+)                                                 \n");
    sb.append("  )                                                                                            \n");
    sb.append("  SELECT *                                                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("        , '000000000' AS  M550_SEMOKCODE                                                       \n");
    sb.append("        , '합계' AS M550_SEMOKNAME                                                             \n");
    sb.append("        , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                                  \n");
    sb.append("        , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                                  \n");
    sb.append("        , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                                \n");
    sb.append("        , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                                \n");
    sb.append("        , SUM(CHAGAM_AMT) AS CHAGAM_AMT                                                        \n");
    sb.append("        , SUM(CHAGAM_CNT) AS CHAGAM_CNT                                                        \n");
    sb.append("    FROM PRTTMP                                                                                \n");
    sb.append("   GROUP BY M550_YEAR                                                                          \n");
    sb.append("  UNION ALL                                                                                    \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("       --, M550_TAXGBN                                                                         \n");
    sb.append("       --, M550_MOKGUBUN                                                                       \n");
    sb.append("       --, GROUP1                                                                              \n");
    sb.append("       --, GROUP2                                                                              \n");
    sb.append("       --, GROUP3                                                                              \n");
    sb.append("       --, GROUP4                                                                              \n");
    sb.append("       , M550_SEMOKCODE                                                                      \n");
    sb.append("       , M550_SEMOKNAME                                                                        \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABAMT                                          \n");
    sb.append("         ELSE M500_SUNABAMT END AS M500_SUNABAMT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABCNT                                          \n");
    sb.append("         ELSE M500_SUNABCNT END AS M500_SUNABCNT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUAMT                                         \n");
    sb.append("         ELSE M500_HWANBUAMT END AS M500_HWANBUAMT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUCNT                                         \n");
    sb.append("         ELSE M500_HWANBUCNT END AS M500_HWANBUCNT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_AMT                                        \n");
    sb.append("         ELSE CHAGAM_AMT END AS CHAGAM_AMT                                                     \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_CNT                                        \n");
    sb.append("         ELSE CHAGAM_CNT END AS CHAGAM_CNT                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("       SELECT M550_YEAR                                                                        \n");
    sb.append("            , M550_TAXGBN                                                                      \n");
    sb.append("            , M550_MOKGUBUN                                                                    \n");
    sb.append("            , GROUP1                                                                           \n");
    sb.append("            , GROUP2                                                                           \n");
    sb.append("            , GROUP3                                                                           \n");
    sb.append("            , GROUP4                                                                           \n");
    sb.append("            , GROUP5                                                                          \n");
    sb.append("            , M550_SEMOKCODE                                                                   \n");
    sb.append("            , M550_SEMOKNAME                                                                   \n");
    sb.append("            , M500_SUNABAMT                                                                    \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABAMT        \n");
    sb.append("            , M500_SUNABCNT                                                                    \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABCNT        \n");
    sb.append("            , M500_HWANBUAMT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUAMT      \n");
    sb.append("            , M500_HWANBUCNT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUCNT      \n");
    sb.append("            , CHAGAM_AMT                                                                       \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_AMT         \n");
    sb.append("            , CHAGAM_CNT                                                                       \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_CNT         \n");
    sb.append("         FROM PRTTMP                                                                           \n");
    sb.append("       ))                                                                                      \n");
    sb.append("       ORDER BY M550_SEMOKCODE                                                                 \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
                parameters.setString(idx++, paramInfo.getString("end_date"));
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		parameters.setString(idx++, paramInfo.getString("queyear"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}

		return template.getList(conn, parameters);
	}

	/* 수입월계표 조회 */
  public static List<CommonEntity> budgetdaymonthList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" WITH PRTTMP AS (SELECT M550_YEAR                                                              \n");
    sb.append("       ,M550_TAXGBN                                                                            \n");
    sb.append("      , M550_MOKGUBUN                                                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,1) || '00000000' AS GROUP1                                         \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,2) || '0000000' AS GROUP2                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,3) || '00000' AS GROUP3                                           \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,5) || '0000' AS GROUP4                                            \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,7) || '00' AS GROUP5                                            \n");
    sb.append("      , M550_SEMOKCODE                                                                         \n");
    sb.append("      , M550_SEMOKNAME                                                                         \n");
    sb.append("      , M500_SUNABAMT                                                                          \n");
    sb.append("      , M500_SUNABCNT                                                                          \n");
    sb.append("      , M500_HWANBUAMT                                                                         \n");
    sb.append("      , M500_HWANBUCNT                                                                         \n");
    sb.append("      , CHAGAM_AMT                                                                             \n");
    sb.append("      , CHAGAM_CNT                                                                             \n");
    sb.append("   FROM M550_INCOMESEMOK_T A                                                                     \n");
    sb.append("      , ( SELECT M500_YEAR                                                                     \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("               , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                           \n");
    sb.append("               , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                           \n");
    sb.append("               , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                         \n");
    sb.append("               , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                         \n");
    sb.append("               , SUM(M500_SUNABAMT) - SUM(M500_HWANBUAMT) AS CHAGAM_AMT                        \n");
    sb.append("               , SUM(M500_SUNABCNT) - SUM(M500_HWANBUCNT) AS CHAGAM_CNT                        \n");
    sb.append("            FROM M500_INCOMEDAY_T                                                              \n");
    sb.append("           WHERE M500_YEAR = ?                                                                 \n");
    sb.append("             AND M500_PRTDATE LIKE substr(?,1,6)||'%'                                          \n");
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = 'SUM' AND M500_PARTCODE IN                                           \n");                                  
  		sb.append("                               ('00000','02130','02140','02190','02220','02240') THEN '1'    \n");                                  
  		sb.append("              WHEN  ? != 'SUM' AND M500_PARTCODE = ? THEN '1'                                \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M500_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M500_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M500_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("           GROUP BY M500_YEAR                                                                  \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("        ) B                                                                                    \n");
    sb.append("  WHERE M550_YEAR = ?                                                                          \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M550_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M550_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M550_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("    AND A.M550_YEAR = B.M500_YEAR(+)                                                           \n");
    sb.append("    AND A.M550_SEMOKCODE = B.M500_SEMOKCODE(+)                                                 \n");
    sb.append("  )                                                                                            \n");
    sb.append("  SELECT *                                                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("        , '000000000' AS  M550_SEMOKCODE                                                       \n");
    sb.append("        , '합계' AS M550_SEMOKNAME                                                             \n");
    sb.append("        , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                                  \n");
    sb.append("        , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                                  \n");
    sb.append("        , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                                \n");
    sb.append("        , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                                \n");
    sb.append("        , SUM(CHAGAM_AMT) AS CHAGAM_AMT                                                        \n");
    sb.append("        , SUM(CHAGAM_CNT) AS CHAGAM_CNT                                                        \n");
    sb.append("    FROM PRTTMP                                                                                \n");
    sb.append("   GROUP BY M550_YEAR                                                                          \n");
    sb.append("  UNION ALL                                                                                    \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("       --, M550_TAXGBN                                                                         \n");
    sb.append("       --, M550_MOKGUBUN                                                                       \n");
    sb.append("       --, GROUP1                                                                              \n");
    sb.append("       --, GROUP2                                                                              \n");
    sb.append("       --, GROUP3                                                                              \n");
    sb.append("       --, GROUP4                                                                              \n");
    sb.append("       , M550_SEMOKCODE                                                                      \n");
    sb.append("       , M550_SEMOKNAME                                                                        \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABAMT                                          \n");
    sb.append("         ELSE M500_SUNABAMT END AS M500_SUNABAMT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABCNT                                          \n");
    sb.append("         ELSE M500_SUNABCNT END AS M500_SUNABCNT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUAMT                                         \n");
    sb.append("         ELSE M500_HWANBUAMT END AS M500_HWANBUAMT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUCNT                                         \n");
    sb.append("         ELSE M500_HWANBUCNT END AS M500_HWANBUCNT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_AMT                                        \n");
    sb.append("         ELSE CHAGAM_AMT END AS CHAGAM_AMT                                                     \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_CNT                                        \n");
    sb.append("         ELSE CHAGAM_CNT END AS CHAGAM_CNT                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("       SELECT M550_YEAR                                                                        \n");
    sb.append("            , M550_TAXGBN                                                                      \n");
    sb.append("            , M550_MOKGUBUN                                                                    \n");
    sb.append("            , GROUP1                                                                           \n");
    sb.append("            , GROUP2                                                                           \n");
    sb.append("            , GROUP3                                                                           \n");
    sb.append("            , GROUP4                                                                           \n");
    sb.append("            , GROUP5                                                                           \n");
    sb.append("            , M550_SEMOKCODE                                                                   \n");
    sb.append("            , M550_SEMOKNAME                                                                   \n");
    sb.append("            , M500_SUNABAMT                                                                    \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABAMT        \n");
    sb.append("            , M500_SUNABCNT                                                                    \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABCNT        \n");
    sb.append("            , M500_HWANBUAMT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUAMT      \n");
    sb.append("            , M500_HWANBUCNT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUCNT      \n");
    sb.append("            , CHAGAM_AMT                                                                       \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_AMT         \n");
    sb.append("            , CHAGAM_CNT                                                                       \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_CNT         \n");
    sb.append("         FROM PRTTMP                                                                           \n");
    sb.append("       ) )                                                                                      \n");
    sb.append("       ORDER BY M550_SEMOKCODE                                                                 \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		parameters.setString(idx++, paramInfo.getString("queyear"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}

		return template.getList(conn, parameters);
	}
	/* 수입일계표 엑셀조회 */
  public static List<CommonEntity> budgetdayexcelList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" WITH PRTTMP AS (SELECT M550_YEAR                                                              \n");
    sb.append("       ,M550_TAXGBN                                                                            \n");
    sb.append("      , M550_MOKGUBUN                                                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,1) || '00000000' AS GROUP1                                         \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,2) || '0000000' AS GROUP2                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,3) || '00000' AS GROUP3                                           \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,5) || '0000' AS GROUP4                                            \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,7) || '00' AS GROUP5                                            \n");
    sb.append("      , M550_SEMOKCODE                                                                         \n");
    sb.append("      , M550_SEMOKNAME                                                                         \n");
    sb.append("      , M500_SUNABAMT                                                                          \n");
    sb.append("      , M500_SUNABCNT                                                                          \n");
    sb.append("      , M500_HWANBUAMT                                                                         \n");
    sb.append("      , M500_HWANBUCNT                                                                         \n");
    sb.append("      , CHAGAM_AMT                                                                             \n");
    sb.append("      , CHAGAM_CNT                                                                             \n");
    sb.append("   FROM M550_INCOMESEMOK_T A                                                                     \n");
    sb.append("      , ( SELECT M500_YEAR                                                                     \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("               , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                           \n");
    sb.append("               , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                           \n");
    sb.append("               , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                         \n");
    sb.append("               , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                         \n");
    sb.append("               , SUM(M500_SUNABAMT) - SUM(M500_HWANBUAMT) AS CHAGAM_AMT                        \n");
    sb.append("               , SUM(M500_SUNABCNT) - SUM(M500_HWANBUCNT) AS CHAGAM_CNT                        \n");
    sb.append("            FROM M500_INCOMEDAY_T                                                              \n");
    sb.append("           WHERE M500_YEAR = ?                                                                 \n");
    sb.append("             AND M500_DATE = ?                                                                 \n");
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = 'SUM' AND M500_PARTCODE IN                                           \n");                                  
  		sb.append("                               ('00000','02130','02140','02190','02220','02240') THEN '1'    \n");                                  
  		sb.append("              WHEN  ? != 'SUM' AND M500_PARTCODE = ? THEN '1'                                \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M500_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M500_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M500_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("           GROUP BY M500_YEAR                                                                  \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("        ) B                                                                                    \n");
    sb.append("  WHERE M550_YEAR = ?                                                                          \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M550_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M550_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M550_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("    AND A.M550_YEAR = B.M500_YEAR(+)                                                           \n");
    sb.append("    AND A.M550_SEMOKCODE = B.M500_SEMOKCODE(+)                                                 \n");
    sb.append("  )                                                                                            \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("       --, M550_TAXGBN                                                                         \n");
    sb.append("       , M550_MOKGUBUN                                                                         \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN ' ' = NVL(?,' ') THEN '총괄 수입일계표('||SUBSTR(?,1,4)||'.'||SUBSTR(?,5,2)||'.'||SUBSTR(?,7,2)||')'       \n");
    sb.append("         ELSE ( SELECT M551_PARTNAME                                                          \n");
    sb.append("                  FROM M551_INCOMEPART_T                                                      \n");
    sb.append("                 WHERE M551_YEAR = ?                                                          \n");
    sb.append("                   AND M551_PARTCODE = ?                                                      \n");
    sb.append("                   AND ROWNUM = 1) || '수입일계표('||SUBSTR(?,1,4)||'.'||SUBSTR(?,5,2)||'.'||SUBSTR(?,7,2)||')'  \n");
    sb.append("         END AS TITLENAME                                                                      \n");
    sb.append("       --, GROUP1                                                                              \n");
    sb.append("       --, GROUP2                                                                              \n");
    sb.append("       --, GROUP3                                                                              \n");
    sb.append("       --, GROUP4                                                                              \n");
    sb.append("       --, M550_SEMOKCODE                                                                      \n");
    sb.append("       , M550_SEMOKNAME                                                                        \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABAMT                                          \n");
    sb.append("         ELSE M500_SUNABAMT END AS M500_SUNABAMT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABCNT                                          \n");
    sb.append("         ELSE M500_SUNABCNT END AS M500_SUNABCNT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUAMT                                         \n");
    sb.append("         ELSE M500_HWANBUAMT END AS M500_HWANBUAMT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUCNT                                         \n");
    sb.append("         ELSE M500_HWANBUCNT END AS M500_HWANBUCNT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_AMT                                        \n");
    sb.append("         ELSE CHAGAM_AMT END AS CHAGAM_AMT                                                     \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_CNT                                        \n");
    sb.append("         ELSE CHAGAM_CNT END AS CHAGAM_CNT                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("       SELECT M550_YEAR                                                                        \n");
    sb.append("            , M550_TAXGBN                                                                      \n");
    sb.append("            , M550_MOKGUBUN                                                                    \n");
    sb.append("            , GROUP1                                                                           \n");
    sb.append("            , GROUP2                                                                           \n");
    sb.append("            , GROUP3                                                                           \n");
    sb.append("            , GROUP4                                                                           \n");
    sb.append("            , GROUP5                                                                           \n");
    sb.append("            , M550_SEMOKCODE                                                                   \n");
    sb.append("            , M550_SEMOKNAME                                                                   \n");
    sb.append("            , M500_SUNABAMT                                                                    \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABAMT        \n");
    sb.append("            , M500_SUNABCNT                                                                    \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABCNT        \n");
    sb.append("            , M500_HWANBUAMT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUAMT      \n");
    sb.append("            , M500_HWANBUCNT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUCNT      \n");
    sb.append("            , CHAGAM_AMT                                                                       \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_AMT         \n");
    sb.append("            , CHAGAM_CNT                                                                       \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_CNT         \n");
    sb.append("         FROM PRTTMP                                                                           \n");
    sb.append("       )                                                                                       \n");
    sb.append("       ORDER BY M550_TAXGBN, M550_YEAR DESC, M550_TAXGBN, M550_SEMOKCODE                       \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		parameters.setString(idx++, paramInfo.getString("queyear"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}

    parameters.setString(idx++, paramInfo.getString("quepartCode"));
    parameters.setString(idx++, paramInfo.getString("que_date"));
    parameters.setString(idx++, paramInfo.getString("que_date"));
    parameters.setString(idx++, paramInfo.getString("que_date"));
    parameters.setString(idx++, paramInfo.getString("queyear"));
    parameters.setString(idx++, paramInfo.getString("quepartCode"));
    parameters.setString(idx++, paramInfo.getString("que_date"));
    parameters.setString(idx++, paramInfo.getString("que_date"));
    parameters.setString(idx++, paramInfo.getString("que_date"));

		return template.getList(conn, parameters);
	}
	/* 수입월계표 엑셀 */
  public static List<CommonEntity> budgetdayexcelmonthList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" WITH PRTTMP AS (SELECT M550_YEAR                                                              \n");
    sb.append("       ,M550_TAXGBN                                                                            \n");
    sb.append("      , M550_MOKGUBUN                                                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,1) || '00000000' AS GROUP1                                         \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,2) || '0000000' AS GROUP2                                          \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,3) || '00000' AS GROUP3                                           \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,5) || '0000' AS GROUP4                                            \n");
    sb.append("      , SUBSTR(M550_SEMOKCODE,1,7) || '00' AS GROUP5                                            \n");
    sb.append("      , M550_SEMOKCODE                                                                         \n");
    sb.append("      , M550_SEMOKNAME                                                                         \n");
    sb.append("      , M500_SUNABAMT                                                                          \n");
    sb.append("      , M500_SUNABCNT                                                                          \n");
    sb.append("      , M500_HWANBUAMT                                                                         \n");
    sb.append("      , M500_HWANBUCNT                                                                         \n");
    sb.append("      , CHAGAM_AMT                                                                             \n");
    sb.append("      , CHAGAM_CNT                                                                             \n");
    sb.append("   FROM M550_INCOMESEMOK_T A                                                                     \n");
    sb.append("      , ( SELECT M500_YEAR                                                                     \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("               , SUM(M500_SUNABAMT) AS M500_SUNABAMT                                           \n");
    sb.append("               , SUM(M500_SUNABCNT) AS M500_SUNABCNT                                           \n");
    sb.append("               , SUM(M500_HWANBUAMT) AS M500_HWANBUAMT                                         \n");
    sb.append("               , SUM(M500_HWANBUCNT) AS M500_HWANBUCNT                                         \n");
    sb.append("               , SUM(M500_SUNABAMT) - SUM(M500_HWANBUAMT) AS CHAGAM_AMT                        \n");
    sb.append("               , SUM(M500_SUNABCNT) - SUM(M500_HWANBUCNT) AS CHAGAM_CNT                        \n");
    sb.append("            FROM M500_INCOMEDAY_T                                                              \n");
    sb.append("           WHERE M500_YEAR = ?                                                                 \n");
    sb.append("             AND M500_PRTDATE LIKE substr(?,1,6)||'%'                                          \n");
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = 'SUM' AND M500_PARTCODE IN                                           \n");                                  
  		sb.append("                               ('00000','02130','02140','02190','02220','02240') THEN '1'    \n");                                  
  		sb.append("              WHEN  ? != 'SUM' AND M500_PARTCODE = ? THEN '1'                                \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M500_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M500_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M500_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("           GROUP BY M500_YEAR                                                                  \n");
    sb.append("               , M500_SEMOKCODE                                                                \n");
    sb.append("        ) B                                                                                    \n");
    sb.append("  WHERE M550_YEAR = ?                                                                          \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND CASE                                                                           \n");                                  
  		sb.append("              WHEN  ? = '2' AND M550_TAXGBN IN ('2','3') THEN '1'                            \n");                                  
  		sb.append("              WHEN  ? != '2' AND M550_TAXGBN = ? THEN '1'                                    \n");                                  
  		sb.append("              ELSE '0' END = '1'                                                             \n");                                  
		}else {
  		sb.append("          AND M550_TAXGBN <> '1'                                                             \n");                                  
    }
    sb.append("    AND A.M550_YEAR = B.M500_YEAR(+)                                                           \n");
    sb.append("    AND A.M550_SEMOKCODE = B.M500_SEMOKCODE(+)                                                 \n");
    sb.append("  )                                                                                            \n");
    sb.append("  SELECT M550_YEAR                                                                             \n");
    sb.append("       --, M550_TAXGBN                                                                         \n");
    sb.append("       , M550_MOKGUBUN                                                                       \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN ' ' = NVL(?,' ') THEN '총괄 수입월계표('||?||'.'||?||')'       \n");
    sb.append("         ELSE ( SELECT M551_PARTNAME                                                          \n");
    sb.append("                  FROM M551_INCOMEPART_T                                                      \n");
    sb.append("                 WHERE M551_YEAR = ?                                                          \n");
    sb.append("                   AND M551_PARTCODE = ?                                                      \n");
    sb.append("                   AND ROWNUM = 1) || '수입월계표('||?||'.'||?||')'  \n");
    sb.append("         END AS TITLENAME                                                                      \n");
    sb.append("       --, GROUP1                                                                              \n");
    sb.append("       --, GROUP2                                                                              \n");
    sb.append("       --, GROUP3                                                                              \n");
    sb.append("       --, GROUP4                                                                              \n");
    sb.append("       --, M550_SEMOKCODE                                                                      \n");
    sb.append("       , M550_SEMOKNAME                                                                        \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABAMT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABAMT                                          \n");
    sb.append("         ELSE M500_SUNABAMT END AS M500_SUNABAMT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_SUNABCNT                                          \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_SUNABCNT                                          \n");
    sb.append("         ELSE M500_SUNABCNT END AS M500_SUNABCNT                                               \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUAMT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUAMT                                         \n");
    sb.append("         ELSE M500_HWANBUAMT END AS M500_HWANBUAMT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_HWANBUCNT                                         \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_HWANBUCNT                                         \n");
    sb.append("         ELSE M500_HWANBUCNT END AS M500_HWANBUCNT                                             \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_AMT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_AMT                                        \n");
    sb.append("         ELSE CHAGAM_AMT END AS CHAGAM_AMT                                                     \n");
    sb.append("       , CASE                                                                                  \n");
    sb.append("         WHEN M550_MOKGUBUN = '0' THEN TOTG1_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '1' THEN TOTG2_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '2' THEN TOTG3_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '3' THEN TOTG4_CHAGAM_CNT                                        \n");
    sb.append("         WHEN M550_MOKGUBUN = '4' THEN TOTG5_CHAGAM_CNT                                        \n");
    sb.append("         ELSE CHAGAM_CNT END AS CHAGAM_CNT                                                     \n");
    sb.append("    FROM (                                                                                     \n");
    sb.append("       SELECT M550_YEAR                                                                        \n");
    sb.append("            , M550_TAXGBN                                                                      \n");
    sb.append("            , M550_MOKGUBUN                                                                    \n");
    sb.append("            , GROUP1                                                                           \n");
    sb.append("            , GROUP2                                                                           \n");
    sb.append("            , GROUP3                                                                           \n");
    sb.append("            , GROUP4                                                                           \n");
    sb.append("            , GROUP5                                                                           \n");
    sb.append("            , M550_SEMOKCODE                                                                   \n");
    sb.append("            , M550_SEMOKNAME                                                                   \n");
    sb.append("            , M500_SUNABAMT                                                                    \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABAMT        \n");
    sb.append("            , SUM(M500_SUNABAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABAMT        \n");
    sb.append("            , M500_SUNABCNT                                                                    \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_SUNABCNT        \n");
    sb.append("            , SUM(M500_SUNABCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_SUNABCNT        \n");
    sb.append("            , M500_HWANBUAMT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUAMT      \n");
    sb.append("            , SUM(M500_HWANBUAMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUAMT      \n");
    sb.append("            , M500_HWANBUCNT                                                                   \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_HWANBUCNT      \n");
    sb.append("            , SUM(M500_HWANBUCNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_HWANBUCNT      \n");
    sb.append("            , CHAGAM_AMT                                                                       \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_AMT         \n");
    sb.append("            , SUM(CHAGAM_AMT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_AMT         \n");
    sb.append("            , CHAGAM_CNT                                                                       \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP1) AS TOTG1_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP2) AS TOTG2_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP3) AS TOTG3_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP4) AS TOTG4_CHAGAM_CNT         \n");
    sb.append("            , SUM(CHAGAM_CNT) OVER(PARTITION BY M550_YEAR, GROUP5) AS TOTG5_CHAGAM_CNT         \n");
    sb.append("         FROM PRTTMP                                                                           \n");
    sb.append("       )                                                                                       \n");
    sb.append("       ORDER BY M550_TAXGBN, M550_YEAR DESC, M550_TAXGBN, M550_SEMOKCODE                       \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
		if (!"".equals(paramInfo.getString("quepartCode"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
  		parameters.setString(idx++, paramInfo.getString("quepartCode"));
		}
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		parameters.setString(idx++, paramInfo.getString("queyear"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}

    parameters.setString(idx++, paramInfo.getString("quepartCode"));
    parameters.setString(idx++, paramInfo.getString("queyear"));
    parameters.setString(idx++, paramInfo.getString("quemm"));
    parameters.setString(idx++, paramInfo.getString("queyear"));
    parameters.setString(idx++, paramInfo.getString("quepartCode"));
    parameters.setString(idx++, paramInfo.getString("queyear"));
    parameters.setString(idx++, paramInfo.getString("quemm"));

		return template.getList(conn, parameters);
	}
	/* 수입일계표 표준부서코드조회 */
  public static List<CommonEntity> getbudgetpartList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M551_PARTCODE                        \n");
    sb.append("      , M551_PARTNAME                        \n");
    sb.append("   FROM M551_INCOMEPART_T                      \n");
    sb.append("  WHERE M551_YEAR = TO_CHAR(SYSDATE,'YYYY')  \n");
    sb.append(" ORDER BY M551_PARTCODE                      \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																				
		
		return template.getList(conn, parameters);
	}
}
