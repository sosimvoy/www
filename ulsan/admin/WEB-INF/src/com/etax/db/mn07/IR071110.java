/**************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR071110.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 세목별누계조회
***************************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR071110 {
	
	/* 수입일계표 생성전 기존자료 삭제 */
  public static int budgetProcbeforDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" DELETE FROM M500_INCOMEDAY_T                                           \n");
    sb.append(" WHERE M500_DATE = ?                                                    \n");
    sb.append("   AND M500_YEAR BETWEEN TO_CHAR(ADD_MONTHS(SYSDATE,-12),'YYYY') AND    \n");
    sb.append("       TO_CHAR(SYSDATE,'YYYY')                                          \n");
    sb.append("  AND M500_PROCTYPE = '1'                                               \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("que_date"));
		
		return template.insert(conn, parameters); 
	}

	/* 수입일계표 생성전 자료 존재체크 */
  public static CommonEntity budgetProcbeforDatachk(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT 'Y' AS EXIST_YN        \n");
    sb.append("   FROM M500_INCOMEDAY_T       \n");
    sb.append("  WHERE M500_DATE = ?          \n");
    sb.append("    AND M500_PROCTYPE = '1'    \n");
    sb.append("    AND ROWNUM = 1             \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("que_date"));
		
    return template.getData(conn, parameters);
	}
	/* 수입일계표 마스터 자료 존재체크 */
  public static CommonEntity budgetinsertbeforDatachk(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT 'Y' AS EXIST_YN        \n");
    sb.append("      , M500_PROCTYPE          \n");
    sb.append("   FROM M500_INCOMEDAY_T       \n");
    sb.append("  WHERE M500_YEAR = ?          \n");
    sb.append("    AND M500_DATE = ?          \n");
    sb.append("    AND M500_PRTDATE = ?       \n");
    sb.append("    AND M500_TAXGBN = ?        \n");
    sb.append("    AND M500_PARTCODE = ?      \n");
    sb.append("    AND M500_SEMOKCODE  = ?    \n");
    sb.append("    AND M500_PROCTYPE = '2'    \n");
    sb.append("    AND ROWNUM = 1             \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
		
    return template.getData(conn, parameters);
	}

	/* 수입일계표 생성 */
  public static int budgetProc(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO M500_INCOMEDAY_T                                                                     \n");
    sb.append(" (                                                                                                \n");
    sb.append(" M500_YEAR     , M500_DATE     , M500_PRTDATE , M500_GROUPTAX,  M500_TAXGBN,                      \n");
    sb.append(" M500_PARTCODE,  M500_SEMOKCODE, M500_PROCTYPE, M500_SUNABAMT, M500_SUNABCNT,                     \n");
    sb.append(" M500_HWANBUAMT, M500_HWANBUCNT                                                                   \n");
    sb.append(" )                                                                                                \n");
    sb.append(" WITH OCRTMP AS (SELECT A.M010_YEAR AS OCR_YEAR                                                   \n");
    sb.append("       ,A.ETC_ACCDATE AS OCR_DATE                                                                 \n");
    sb.append("      , CASE                                                                                     \n");
    sb.append("       WHEN SUBSTR(A.ETC_ACCDATE,1,4) = A.ETC_YYYY THEN M420_BUDGETSEMOKCODE                     \n");
    sb.append("       WHEN SUBSTR(A.ETC_ACCDATE,1,4) > A.ETC_YYYY                                              \n");
    sb.append("          THEN (CASE                                                                            \n");
    sb.append("                WHEN SUBSTR(A.ETC_ACCDATE,5,8) >= '0301' THEN NVL(B.M420_BUDGETSEMOKBYEAR,'9999999')                         \n");
    sb.append("                ELSE (CASE                                                                                                   \n");
    sb.append("                      WHEN TO_CHAR(SUBSTR(A.ETC_ACCDATE,1,4) - 1) = A.ETC_YYYY THEN M420_BUDGETSEMOKCODE                     \n");
    sb.append("                      WHEN TO_CHAR(SUBSTR(A.ETC_ACCDATE,1,4) - 1) > A.ETC_YYYY THEN NVL(B.M420_BUDGETSEMOKBYEAR,'9999999')   \n");
    sb.append("                      ELSE '0' END)                                                             \n");
    sb.append("               END)                                                                             \n");
    sb.append("       END OCR_SEMOKCODE                                                                          \n");
    sb.append("       ,C.M410_BUDGETPARTCODE  AS OCR_PARTCODE                                                    \n");
    sb.append("       ,A.ETC_AMT2 AS OCR_AMT                                                                     \n");
    sb.append("       ,1        AS OCR_CNT                                                                       \n");
    sb.append("   FROM (                                                                                   \n");
    sb.append("         SELECT CASE                                                                           \n");  
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) = ETC_YYYY  THEN ETC_YYYY                   \n");
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) > ETC_YYYY                                    \n");
    sb.append("                THEN  (CASE                                                                    \n");
    sb.append("                       WHEN SUBSTR(ETC_ACCDATE,5,8) >= '0301' THEN SUBSTR(ETC_ACCDATE,1,4) \n");
    sb.append("                       ELSE  TO_CHAR(SUBSTR(ETC_ACCDATE,1,4) - 1) END)                       \n");
    sb.append("                END AS M010_YEAR                                                               \n");
    sb.append("              , T.*                                                                            \n");
    sb.append("           FROM ETC_T  T  ) A                                                                   \n");
    sb.append("       ,M420_STANDARDSEMOKCODE_T B                                                                \n");
    sb.append("       ,M410_STANDARDPARTCODE_T C                                                                 \n");
    sb.append("  WHERE A.M010_YEAR = B.M420_YEAR                                                                 \n");
    sb.append("    AND A.ETC_ACC = B.M420_STANDARDACCCODE                                                        \n");
    sb.append("    AND A.ETC_TAX = B.M420_STANDARDSEMOKCODE                                                      \n");
    sb.append("    AND A.M010_YEAR = C.M410_YEAR                                                                 \n");
    sb.append("    AND A.ETC_PARTCODE = C.M410_STANDARDPARTCODE                                                  \n");
    sb.append("    AND A.ETC_JOBDATE = ?                                                                         \n");
    sb.append("    AND A.ETC_ACC = '31' --고정코드                                                               \n");
    sb.append(" )                                                                                                \n");
    sb.append(" SELECT OCR_YEAR                                                                                  \n");
    sb.append("      , OCR_DATE                                                                                  \n");
    sb.append("      , OCR_DATE AS PRT_DATE                                                                      \n");
    sb.append("      , DECODE(M550_TAXGBN,'1','1','0','2','2','3','3','3','4','3','9')                           \n");
    sb.append("      , M550_TAXGBN                                                                               \n");
    sb.append("      , OCR_PARTCODE                                                                              \n");
    sb.append("      , OCR_SEMOKCODE                                                                             \n");
    sb.append("      , '1' AS PROCGBN                                                                            \n");
    sb.append("      , OCR_AMT                                                                                   \n");
    sb.append("      , OCR_CNT                                                                                   \n");
    sb.append("      , 0                                                                                         \n");
    sb.append("      , 0                                                                                         \n");
    sb.append("   FROM M550_INCOMESEMOK_T A ,                                                                      \n");
    sb.append("     (                                                                                            \n");
    sb.append("     SELECT OCR_YEAR                                                                              \n");
    sb.append("          , OCR_DATE                                                                              \n");
    sb.append("          , OCR_SEMOKCODE                                                                         \n");
    sb.append("          , OCR_PARTCODE                                                                          \n");
    sb.append("          , SUM(OCR_AMT) AS OCR_AMT                                                               \n");
    sb.append("          , SUM(OCR_CNT) AS OCR_CNT                                                               \n");
    sb.append("       FROM OCRTMP                                                                                \n");
    sb.append("      GROUP BY OCR_YEAR                                                                           \n");
    sb.append("          , OCR_DATE                                                                              \n");
    sb.append("          , OCR_SEMOKCODE                                                                         \n");
    sb.append("          , OCR_PARTCODE                                                                          \n");
    sb.append("      ) B                                                                                         \n");
    sb.append("   WHERE A.M550_YEAR = B.OCR_YEAR                                                                 \n");
    sb.append("     AND A.M550_SEMOKCODE = B.OCR_SEMOKCODE                                                       \n");

											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("que_date"));
		
		return template.insert(conn, parameters); 
	}

	/* 수입일계표 조회 */
  public static List<CommonEntity> budgetList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" with tmptbl as (select '1' as datagbn                                                   \n");
    sb.append("        , M500_YEAR                                                                      \n");
    sb.append("        , M500_DATE                                                                      \n");
    sb.append("        , M500_PRTDATE                                                                   \n");
    sb.append("        , M500_TAXGBN                                                                    \n");
    sb.append("        , M500_PARTCODE                                                                  \n");
    sb.append("        , M500_SEMOKCODE                                                                 \n");
    sb.append("        , 0 AS SEQ                                                                       \n");
    sb.append("        , M500_PROCTYPE                                                                  \n");
    sb.append("        , M500_SUNABAMT                                                                  \n");
    sb.append("        , M500_SUNABCNT                                                                  \n");
    sb.append("        , M500_HWANBUAMT                                                                 \n");
    sb.append("        , M500_HWANBUCNT                                                                 \n");
    sb.append("     from M500_INCOMEDAY_T                                                               \n");
    sb.append("     where M500_YEAR = ?                                                                 \n");
    sb.append("       and M500_DATE = ?                                                                 \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND  M500_TAXGBN = ?                                                           \n");                                  
		}
		if (!"".equals(paramInfo.getString("queprocgbn"))) {                 
	  	sb.append("          AND  M500_PROCTYPE = ?                                                         \n");                                  
		}
    sb.append("     union all                                                                           \n");
    sb.append("     select datagbn                                                                      \n");
    sb.append("       , M501_YEAR                                                                       \n");
    sb.append("       , M501_DATE                                                                       \n");
    sb.append("       , M501_PRTDATE                                                                    \n");
    sb.append("       , M501_TAXGBN                                                                     \n");
    sb.append("       , M501_PARTCODE                                                                   \n");
    sb.append("       , M501_SEMOKCODE                                                                  \n");
    sb.append("       , M501_SEQ                                                                        \n");
    sb.append("       , M501_PROCTYPE                                                                   \n");
    sb.append("       , M501_SUNABAMT                                                                   \n");
    sb.append("       , M501_SUNABCNT                                                                   \n");
    sb.append("       , M501_HWANBUAMT                                                                  \n");
    sb.append("       , M501_HWANBUCNT                                                                  \n");
    sb.append("       from (                                                                            \n");
    sb.append("         select '2' as datagbn                                                           \n");
    sb.append("           , M501_YEAR                                                                   \n");
    sb.append("           , M501_DATE                                                                   \n");
    sb.append("           , M501_PRTDATE                                                                \n");
    sb.append("           , M501_TAXGBN                                                                 \n");
    sb.append("           , M501_PARTCODE                                                               \n");
    sb.append("           , M501_SEMOKCODE                                                              \n");
    sb.append("           , M501_SEQ                                                                    \n");
    sb.append("           , M501_PROCTYPE                                                               \n");
    sb.append("           , M501_SUNABAMT                                                               \n");
    sb.append("           , M501_SUNABCNT                                                               \n");
    sb.append("           , M501_HWANBUAMT                                                              \n");
    sb.append("           , M501_HWANBUCNT                                                              \n");
    sb.append("           , row_number() over(partition by  M501_YEAR, M501_DATE, M501_PRTDATE          \n");
    sb.append("                                           , M501_TAXGBN, M501_PARTCODE    \n");
    sb.append("                                           , M501_SEMOKCODE, M501_PROCTYPE               \n");
    sb.append("                                   order by  M501_SEQ desc ) as posi                     \n");
    sb.append("           from M501_INCOMEDAYHST_T                                                      \n");
    sb.append("          where M501_YEAR = ?                                                            \n");
    sb.append("            and M501_DATE = ?                                                            \n");
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		sb.append("          AND  M501_TAXGBN = ?                                                         \n");                                  
		}
		if (!"".equals(paramInfo.getString("queprocgbn"))) {                 
	  	sb.append("          AND  M501_PROCTYPE = ?                                                       \n");                                  
		}
    sb.append("      )                                                                                  \n");
    sb.append("      where posi = 1                                                                     \n");
    sb.append("  )                                                                                      \n");
    sb.append("   SELECT M500_YEAR                                                                       \n");
    sb.append("        , M500_DATE                                                                       \n");
    sb.append("        , '' AS M500_PRTDATE                                                                    \n");
    sb.append("        , '' AS M500_TAXGBN                                                               \n");
    sb.append("        , '' AS M500_TAXNAME                                                              \n");
    sb.append("        , '' AS M500_PARTCODE                                                             \n");
    sb.append("        , '' AS M551_PARTNAME                                                             \n");
    sb.append("        , '' AS M500_SEMOKCODE                                                            \n");
    sb.append("        , '합계' AS M550_SEMOKNAME                                                        \n");
    sb.append("        , 0 AS SEQ                                                                        \n");
    sb.append("        , '' AS M500_PROCTYPE                                                             \n");
    sb.append("        , '' AS M500_PROCTYPE_NM                                                          \n");
    sb.append("        , SUM(MST_SUNABAMT) AS MST_SUNABAMT                                               \n");
    sb.append("        , SUM(MST_SUNABCNT) AS MST_SUNABCNT                                               \n");
    sb.append("        , SUM(MST_HWANBUAMT) AS MST_HWANBUAMT                                             \n");
    sb.append("        , SUM(MST_HWANBUCNT) AS MST_HWANBUCNT                                             \n");
    sb.append("        , SUM(HST_SUNABAMT) AS HST_SUNABAMT                                               \n");
    sb.append("        , SUM(HST_SUNABCNT) AS HST_SUNABCNT                                               \n");
    sb.append("        , SUM(HST_HWANBUAMT) AS HST_HWANBUAMT                                             \n");
    sb.append("        , SUM(HST_HWANBUCNT) AS HST_HWANBUCNT                                             \n");
    sb.append("     FROM M550_INCOMESEMOK_T B                                                            \n");
    sb.append("        , M551_INCOMEPART_T C                                                             \n");
    sb.append("        , (                                                                               \n");
    sb.append("           SELECT M500_YEAR                                                               \n");
    sb.append("                , M500_DATE                                                               \n");
    sb.append("                , M500_PRTDATE                                                            \n");
    sb.append("                , M500_TAXGBN                                                             \n");
    sb.append("                , DECODE(M500_TAXGBN,'0','지방세','1','국세',                             \n");
    sb.append("                             '2','경상적세외','3','임시적세외',                           \n");
    sb.append("                             '의존수입') AS M500_TAXNAME                                  \n");
    sb.append("                , M500_PARTCODE                                                           \n");
    sb.append("                , M500_SEMOKCODE                                                          \n");
    sb.append("                , max(SEQ) as SEQ                                                         \n");
    sb.append("                , M500_PROCTYPE                                                           \n");
    sb.append("                , DECODE(M500_PROCTYPE,'1','OCR','2','비OCR',                             \n");
    sb.append("                              '3','차량엑셀','농협엑셀') AS M500_PROCTYPE_NM              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_SUNABAMT,0)) AS MST_SUNABAMT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_SUNABCNT,0)) AS MST_SUNABCNT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_HWANBUAMT,0)) AS MST_HWANBUAMT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_HWANBUCNT,0)) AS MST_HWANBUCNT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_SUNABAMT,0)) AS HST_SUNABAMT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_SUNABCNT,0)) AS HST_SUNABCNT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_HWANBUAMT,0)) AS HST_HWANBUAMT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_HWANBUCNT,0)) AS HST_HWANBUCNT              \n");
    sb.append("             FROM TMPTBL A                                                                \n");
    sb.append("            GROUP BY M500_YEAR                                                            \n");
    sb.append("                , M500_DATE                                                               \n");
    sb.append("                , M500_PRTDATE                                                            \n");
    sb.append("                , M500_TAXGBN                                                             \n");
    sb.append("                , M500_PARTCODE                                                           \n");
    sb.append("                , M500_SEMOKCODE                                                          \n");
    sb.append("                , M500_PROCTYPE                                                           \n");
    sb.append("        ) A                                                                               \n");
    sb.append("    WHERE A.M500_YEAR = B.M550_YEAR                                                       \n");
    sb.append("      AND A.M500_YEAR = C.M551_YEAR                                                       \n");
    sb.append("      AND A.M500_SEMOKCODE = B.M550_SEMOKCODE                                             \n");
    sb.append("      AND A.M500_PARTCODE  = C.M551_PARTCODE                                              \n");
    sb.append("    GROUP BY M500_YEAR                                                                    \n");
    sb.append("        , M500_DATE                                                                       \n");
    sb.append("   UNION ALL                                                                              \n");
    sb.append("   SELECT M500_YEAR                                                                       \n");
    sb.append("        , M500_DATE                                                                       \n");
    sb.append("        , M500_PRTDATE                                                                    \n");
    sb.append("        , M500_TAXGBN                                                                     \n");
    sb.append("        , M500_TAXNAME                                                                    \n");
    sb.append("        , M500_PARTCODE                                                                   \n");
    sb.append("        , M551_PARTNAME                                                                   \n");
    sb.append("        , M500_SEMOKCODE                                                                  \n");
    sb.append("        , M550_SEMOKNAME                                                                  \n");
    sb.append("        , SEQ                                                                             \n");
    sb.append("        , M500_PROCTYPE                                                                   \n");
    sb.append("        , M500_PROCTYPE_NM                                                                \n");
    sb.append("        , MST_SUNABAMT                                                                    \n");
    sb.append("        , MST_SUNABCNT                                                                    \n");
    sb.append("        , MST_HWANBUAMT                                                                   \n");
    sb.append("        , MST_HWANBUCNT                                                                   \n");
    sb.append("        , HST_SUNABAMT                                                                    \n");
    sb.append("        , HST_SUNABCNT                                                                    \n");
    sb.append("        , HST_HWANBUAMT                                                                   \n");
    sb.append("        , HST_HWANBUCNT                                                                   \n");
    sb.append("     FROM M550_INCOMESEMOK_T B                                                              \n");
    sb.append("        , M551_INCOMEPART_T C                                                               \n");
    sb.append("        , (                                                                               \n");
    sb.append("           SELECT M500_YEAR                                                               \n");
    sb.append("                , M500_DATE                                                               \n");
    sb.append("                , M500_PRTDATE                                                            \n");
    sb.append("                , M500_TAXGBN                                                             \n");
    sb.append("                , DECODE(M500_TAXGBN,'0','지방세','1','국세',                             \n");
    sb.append("                             '2','경상적세외','3','임시적세외',                           \n");
    sb.append("                             '의존수입') AS M500_TAXNAME                                  \n");
    sb.append("                , M500_PARTCODE                                                           \n");
    sb.append("                , M500_SEMOKCODE                                                          \n");
    sb.append("                , max(SEQ) as SEQ                                                         \n");
    sb.append("                , M500_PROCTYPE                                                           \n");
    sb.append("                , DECODE(M500_PROCTYPE,'1','OCR','2','비OCR',                             \n");
    sb.append("                              '3','차량엑셀','농협엑셀') AS M500_PROCTYPE_NM              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_SUNABAMT,0)) AS MST_SUNABAMT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_SUNABCNT,0)) AS MST_SUNABCNT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_HWANBUAMT,0)) AS MST_HWANBUAMT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'1',M500_HWANBUCNT,0)) AS MST_HWANBUCNT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_SUNABAMT,0)) AS HST_SUNABAMT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_SUNABCNT,0)) AS HST_SUNABCNT                \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_HWANBUAMT,0)) AS HST_HWANBUAMT              \n");
    sb.append("                , SUM(DECODE(DATAGBN,'2',M500_HWANBUCNT,0)) AS HST_HWANBUCNT              \n");
    sb.append("             FROM TMPTBL A                                                                \n");
    sb.append("            GROUP BY M500_YEAR                                                            \n");
    sb.append("                , M500_DATE                                                               \n");
    sb.append("                , M500_PRTDATE                                                            \n");
    sb.append("                , M500_TAXGBN                                                             \n");
    sb.append("                , M500_PARTCODE                                                           \n");
    sb.append("                , M500_SEMOKCODE                                                          \n");
    sb.append("                , M500_PROCTYPE                                                           \n");
    sb.append("        ) A                                                                               \n");
    sb.append("    WHERE A.M500_YEAR = B.M550_YEAR                                                       \n");
    sb.append("      AND A.M500_YEAR = C.M551_YEAR                                                       \n");
    sb.append("      AND A.M500_SEMOKCODE = B.M550_SEMOKCODE                                             \n");
    sb.append("      AND A.M500_PARTCODE  = C.M551_PARTCODE                                              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		if (!"".equals(paramInfo.getString("queprocgbn"))) {                 
	  	parameters.setString(idx++, paramInfo.getString("queprocgbn"));
		}
		parameters.setString(idx++, paramInfo.getString("queyear"));
		parameters.setString(idx++, paramInfo.getString("que_date"));
		if (!"".equals(paramInfo.getString("quetaxgbn"))) {                 
  		parameters.setString(idx++, paramInfo.getString("quetaxgbn"));
		}
		if (!"".equals(paramInfo.getString("queprocgbn"))) {                 
	  	parameters.setString(idx++, paramInfo.getString("queprocgbn"));
		}
		
		
		
		return template.getList(conn, parameters);
	}

	/* 수입일계표 수정 */
  public static int budgetupdateList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M500_INCOMEDAY_T                                                      \n");
    sb.append("     SET M500_SUNABAMT = NVL(M500_SUNABAMT,0) + ?                             \n");
    sb.append("      , M500_SUNABCNT  = NVL(M500_SUNABCNT,0) + ?                             \n");
    sb.append("      , M500_HWANBUAMT = NVL(M500_HWANBUAMT,0) + ?                            \n");
    sb.append("      , M500_HWANBUCNT = NVL(M500_HWANBUCNT,0) + ?                             \n");
    sb.append("  WHERE M500_YEAR = ?                                                          \n");
    sb.append("    AND M500_DATE = ?                                                          \n");
    sb.append("    AND M500_PRTDATE = ?                                                       \n");
    sb.append("    AND M500_TAXGBN = ?                                                        \n");
    sb.append("    AND M500_PARTCODE = ?                                                      \n");
    sb.append("    AND M500_SEMOKCODE  = ?                                                    \n");
    sb.append("    AND M500_PROCTYPE  = '2'                                                    \n");
                         
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("sunapamt"));
		parameters.setString(idx++, paramInfo.getString("sunapcnt"));
		parameters.setString(idx++, paramInfo.getString("hwanbuamt"));
		parameters.setString(idx++, paramInfo.getString("hwanbucnt"));
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));

		return template.insert(conn, parameters); 
	}

	/* 수입일계표 입력 */
  public static int budgethstinsertList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M501_INCOMEDAYHST_T                   \n");
		sb.append(" (M501_YEAR, M501_DATE                             \n");
		sb.append(" ,M501_PRTDATE, M501_GROUPTAX         \n");
		sb.append(" ,M501_TAXGBN, M501_PARTCODE                       \n");
		sb.append("	,M501_SEMOKCODE,M501_SEQ                          \n");
		sb.append("	,M501_PROCTYPE , M501_SUNABAMT                    \n");
		sb.append("	,M501_SUNABCNT, M501_HWANBUAMT, M501_HWANBUCNT)   \n");
		sb.append(" VALUES (?, ?				              						 \n");
	  sb.append(" 			 ,?, DECODE(?,'1','1','0','2','2','3','3','3','4','3','9') \n");
		sb.append(" 			 ,?, ?  				                       \n");
		sb.append(" 			 ,?, M501_SEQ.NEXTVAL                    \n");
		sb.append(" 			 ,'2', ?						                       \n"); 
		sb.append(" 			 ,?,?,?)    						                     \n"); 
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
		parameters.setString(idx++, paramInfo.getString("sunapamt"));
		parameters.setString(idx++, paramInfo.getString("sunapcnt"));
		parameters.setString(idx++, paramInfo.getString("hwanbuamt"));
		parameters.setString(idx++, paramInfo.getString("hwanbucnt"));
		
		return template.insert(conn, parameters); 
	}


	/* 수입일계표 입력 */
  public static int budgetinsertList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M500_INCOMEDAY_T                   \n");
		sb.append(" (M500_YEAR, M500_DATE                          \n");
		sb.append(" ,M500_PRTDATE, M500_GROUPTAX      \n");
		sb.append(" ,M500_TAXGBN, M500_PARTCODE                    \n");
		sb.append("	,M500_SEMOKCODE,M500_PROCTYPE                  \n");
		sb.append("	,M500_SUNABAMT,M500_SUNABCNT                   \n");
		sb.append("	,M500_HWANBUAMT, M500_HWANBUCNT )              \n");
		sb.append(" VALUES (?, ?				              						 \n");
	  sb.append(" 			 ,?, DECODE(?,'1','1','0','2','2','3','3','3','4','3','9')   \n");
		sb.append(" 			 ,?, ?  	  			                       \n");
		sb.append(" 			 ,?, '2'  				                       \n");
		sb.append(" 			 ,?, ?						                       \n"); 
		sb.append(" 			 ,?, ?)  						                     \n"); 
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("input_date"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
		parameters.setString(idx++, paramInfo.getString("sunapamt"));
		parameters.setString(idx++, paramInfo.getString("sunapcnt"));
		parameters.setString(idx++, paramInfo.getString("hwanbuamt"));
		parameters.setString(idx++, paramInfo.getString("hwanbucnt"));
		
		return template.insert(conn, parameters); 
	}
  
	/* 수입일계표 마스터삭제 */
  public static int BudgetdayDeleteCMD(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" DELETE FROM M500_INCOMEDAY_T                    \n");
    sb.append("  WHERE M500_YEAR = ?                            \n");
    sb.append("    AND M500_DATE = ?                            \n");
    sb.append("    AND M500_PRTDATE = ?                         \n");
    sb.append("    AND M500_TAXGBN = ?                          \n");
    sb.append("    AND M500_SEMOKCODE = ?                       \n");
    sb.append("    AND M500_PARTCODE = ?                        \n");
    sb.append("    AND M500_PROCTYPE  = ?                       \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("del_year"));
		parameters.setString(idx++, paramInfo.getString("del_date"));
		parameters.setString(idx++, paramInfo.getString("del_prtdate"));
		parameters.setString(idx++, paramInfo.getString("del_taxgbn"));
		parameters.setString(idx++, paramInfo.getString("del_semokcode"));
		parameters.setString(idx++, paramInfo.getString("del_partcode"));
		parameters.setString(idx++, paramInfo.getString("del_proctype"));
		
		return template.insert(conn, parameters); 
	}
	/* 수입일계표 내역삭제 */
  public static int BudgethstDeleteCMD(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" DELETE FROM M501_INCOMEDAYHST_T                     \n");
    sb.append("  WHERE M501_YEAR = ?                            \n");
    sb.append("    AND M501_DATE = ?                            \n");
    sb.append("    AND M501_PRTDATE = ?                         \n");
    sb.append("    AND M501_TAXGBN = ?                          \n");
    sb.append("    AND M501_SEMOKCODE = ?                       \n");
    sb.append("    AND M501_PARTCODE = ?                        \n");
    sb.append("    AND M501_SEQ = ?                            \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("del_year"));
		parameters.setString(idx++, paramInfo.getString("del_date"));
		parameters.setString(idx++, paramInfo.getString("del_prtdate"));
		parameters.setString(idx++, paramInfo.getString("del_taxgbn"));
		parameters.setString(idx++, paramInfo.getString("del_semokcode"));
		parameters.setString(idx++, paramInfo.getString("del_partcode"));
		parameters.setString(idx++, paramInfo.getString("del_seq"));
		
		return template.insert(conn, parameters); 
	}
	/* 수입일계표 수정 */
  public static int BudgetdayCancelCMD(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M500_INCOMEDAY_T                           \n");
    sb.append("     SET M500_SUNABAMT = NVL(M500_SUNABAMT,0) - ?  \n");
    sb.append("      , M500_SUNABCNT = NVL(M500_SUNABCNT,0) - ?   \n");
    sb.append("      , M500_HWANBUAMT = NVL(M500_HWANBUAMT,0) - ? \n");
    sb.append("      , M500_HWANBUCNT = NVL(M500_HWANBUCNT,0) - ? \n");
    sb.append("  WHERE M500_YEAR = ?                            \n");
    sb.append("    AND M500_DATE = ?                            \n");
    sb.append("    AND M500_PRTDATE = ?                         \n");
    sb.append("    AND M500_TAXGBN = ?                          \n");
    sb.append("    AND M500_SEMOKCODE = ?                       \n");
    sb.append("    AND M500_PARTCODE = ?                        \n");
    sb.append("    AND M500_PROCTYPE  = ?                       \n");

                         
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("del_sunabamt"));
		parameters.setString(idx++, paramInfo.getString("del_sunabcnt"));
		parameters.setString(idx++, paramInfo.getString("del_hwanbuamt"));
		parameters.setString(idx++, paramInfo.getString("del_hwanbucnt"));
		parameters.setString(idx++, paramInfo.getString("del_year"));
		parameters.setString(idx++, paramInfo.getString("del_date"));
		parameters.setString(idx++, paramInfo.getString("del_prtdate"));
		parameters.setString(idx++, paramInfo.getString("del_taxgbn"));
		parameters.setString(idx++, paramInfo.getString("del_semokcode"));
		parameters.setString(idx++, paramInfo.getString("del_partcode"));
		parameters.setString(idx++, paramInfo.getString("del_proctype"));

		return template.insert(conn, parameters); 
	}
	/* 수입일계표 마스터오류자료 삭제처리 */
  public static int BudgetdiscardCMD(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" DELETE                                                \n"); 
    sb.append("   FROM M500_INCOMEDAY_T A                             \n"); 
    sb.append("  WHERE A.M500_DATE = ?                                \n"); 
    sb.append("    AND A.M500_PROCTYPE = ?                            \n"); 
    sb.append("    AND NOT EXISTS (                                   \n"); 
    sb.append("        SELECT 1                                       \n"); 
    sb.append("          FROM M501_INCOMEDAYHST_T B                   \n"); 
    sb.append("         WHERE 1 = 1                                   \n"); 
    sb.append("           AND A.M500_YEAR = B.M501_YEAR               \n"); 
    sb.append("           AND A.M500_DATE = B.M501_DATE               \n"); 
    sb.append("           AND A.M500_PRTDATE = B.M501_PRTDATE         \n"); 
    sb.append("           AND A.M500_GROUPTAX = B.M501_GROUPTAX       \n"); 
    sb.append("           AND A.M500_TAXGBN = B.M501_TAXGBN           \n"); 
    sb.append("           AND A.M500_PARTCODE = B.M501_PARTCODE       \n"); 
    sb.append("           AND A.M500_SEMOKCODE = B.M501_SEMOKCODE     \n"); 
    sb.append("     )                                                 \n"); 
                         
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																																					
		parameters.setString(idx++, paramInfo.getString("del_date"));												
		parameters.setString(idx++, paramInfo.getString("del_proctype"));

		return template.insert(conn, parameters); 
	}


    /* 수입일계표 마스터오류자료 삭제처리(시퀀스 0이면 통으로 삭제) */
    public static int BudgetMasterDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append(" DELETE                                              \n"); 
        sb.append("   FROM M500_INCOMEDAY_T                             \n"); 
        sb.append("  WHERE M500_YEAR = ?                                \n"); 
        sb.append("    AND M500_DATE = ?                                \n");
        sb.append("    AND M500_PRTDATE = ?                             \n");
        sb.append("    AND M500_PROCTYPE = ?                            \n"); 
        sb.append("    AND M500_TAXGBN = ?                              \n");
        sb.append("    AND M500_PARTCODE = ?                            \n");
        sb.append("    AND M500_SEMOKCODE = ?                           \n");
                         
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
        parameters.setString(idx++, paramInfo.getString("del_year"));	
		parameters.setString(idx++, paramInfo.getString("del_date"));												
		parameters.setString(idx++, paramInfo.getString("del_prtdate"));
        parameters.setString(idx++, paramInfo.getString("del_proctype"));	
        parameters.setString(idx++, paramInfo.getString("del_taxgbn"));	
        parameters.setString(idx++, paramInfo.getString("del_partcode"));	
        parameters.setString(idx++, paramInfo.getString("del_semokcode"));	

		return template.insert(conn, parameters); 
	}

	/* 수입일계표 예산과목코드조회 */
  public static List<CommonEntity> getbudgetsemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M550_SEMOKCODE                       \n");
    sb.append("      , M550_SEMOKNAME                       \n");
    sb.append("   FROM M550_INCOMESEMOK_T                     \n");
    sb.append("  WHERE M550_YEAR = TO_CHAR(SYSDATE,'YYYY')  \n");
    sb.append("    AND M550_TAXGBN = ?                      \n");
    sb.append("    AND M550_MOKGUBUN = ?                    \n");
    sb.append("    AND M550_SEMOKNAME NOT LIKE '지난년도%'  \n");
    sb.append(" ORDER BY M550_SEMOKCODE                     \n");
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;																				
		parameters.setString(idx++, paramInfo.getString("taxgbn"));
		parameters.setString(idx++, paramInfo.getString("mokgubun"));
		
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
