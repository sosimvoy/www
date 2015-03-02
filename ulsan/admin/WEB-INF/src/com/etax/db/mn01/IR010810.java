/**************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR010810.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 영수필통지서 조회
***************************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010810 {
	
	/* 부서 조회 */ 
	public static List<CommonEntity> getPartList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("   SELECT  A.M410_SYSTEMPARTCODE, B.M350_PARTNAME      	 \n"); 
    sb.append("     FROM  M410_STANDARDPARTCODE_T A                      \n");
    sb.append("          ,M350_PARTCODE_T B                              \n");
    sb.append("          ,M390_USESEMOKCODE_T C                          \n");
    sb.append("    WHERE  A.M410_YEAR             = B.M350_YEAR          \n");
    sb.append("      AND  A.M410_SYSTEMPARTCODE   = B.M350_PARTCODE      \n");
    sb.append("      AND  A.M410_YEAR             = C.M390_YEAR          \n");
    sb.append("      AND  A.M410_SYSTEMPARTCODE   = C.M390_PARTCODE      \n");
    sb.append("      AND  B.M350_YEAR             = C.M390_YEAR          \n");
    //2010.11.10각 코드테이블에 년도별로 자료를 관리하므로 나중에 중복의 조회가 우려되어
    //현 시스템의 년도로 중복을 제거함 BY 강원모
    sb.append("      AND  B.M350_YEAR     = TO_CHAR(SYSDATE,'YYYY')      \n");
    sb.append("      AND  B.M350_PARTCODE = C.M390_PARTCODE              \n");
    sb.append("      AND  C.M390_ACCGUBUN  IN ('A','B')                  \n");
    sb.append("      AND  C.M390_ACCCODE   IN ('11','31')                \n");
    sb.append("      AND  C.M390_WORKGUBUN = '0'                         \n");
    sb.append(" GROUP BY  A.M410_SYSTEMPARTCODE, B.M350_PARTNAME	 	     \n");
    sb.append(" ORDER BY  A.M410_SYSTEMPARTCODE , B.M350_PARTNAME        \n");

	  QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
	
		return template.getList(conn, parameters);
	}

	/* 세목명 조회 */ 
	public static List<CommonEntity> getSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																														  
		sb.append(" SELECT  M370_ACCGUBUN                                  \n");		
		sb.append("        ,M370_ACCCODE                                   \n");
		sb.append("        ,M370_WORKGUBUN                                 \n");
		sb.append("	       ,M370_SEMOKCODE                                 \n");
		sb.append("	       ,M370_SEMOKNAME                                 \n");
    sb.append("   FROM (                                               \n");  
		sb.append("         SELECT  *                                      \n");   
		sb.append("           FROM  M370_SEMOKCODE_T A                     \n");  
		sb.append("                ,M390_USESEMOKCODE_T B                  \n");  
		sb.append("          WHERE  A.M370_YEAR = B.M390_YEAR              \n");  
		sb.append("            AND  A.M370_ACCGUBUN = B.M390_ACCGUBUN      \n");  
		sb.append("            AND  A.M370_ACCCODE = B.M390_ACCCODE        \n");  
		sb.append("            AND  A.M370_WORKGUBUN = B.M390_WORKGUBUN    \n");  
		sb.append("            AND  A.M370_SEMOKCODE = B.M390_SEMOKCODE    \n"); 
		sb.append("            AND  A.M370_ACCGUBUN = 'A'                  \n"); 
		sb.append("            AND  A.M370_ACCCODE = '11'                  \n");               
		sb.append("            AND  A.M370_WORKGUBUN = '0'               	 \n");
		sb.append("            AND  SUBSTR (A.M370_SEMOKCODE, 1,1) <> '1'  \n");
		sb.append("          UNION                                       	 \n");
		sb.append("         SELECT  *                                      \n");   
		sb.append("           FROM  M370_SEMOKCODE_T A                     \n");  
		sb.append("                ,M390_USESEMOKCODE_T B                  \n");  
		sb.append("          WHERE  A.M370_YEAR = B.M390_YEAR              \n");  
		sb.append("            AND  A.M370_ACCGUBUN = B.M390_ACCGUBUN      \n");  
		sb.append("            AND  A.M370_ACCCODE = B.M390_ACCCODE        \n");  
		sb.append("            AND  A.M370_WORKGUBUN = B.M390_WORKGUBUN    \n");  
		sb.append("            AND  A.M370_SEMOKCODE = B.M390_SEMOKCODE    \n"); 
		sb.append("            AND  A.M370_ACCGUBUN = 'B'                  \n"); 
		sb.append("            AND  A.M370_ACCCODE = '31'                  \n");               
		sb.append("            AND  A.M370_WORKGUBUN = '0'               	 \n");
		sb.append("         )                                           	 \n");
		if("11".equals(paramInfo.getString("acc_type")) ) {
			sb.append(" WHERE M370_ACCGUBUN = 'A'                            \n");
			sb.append("   AND M370_ACCCODE = '11'                            \n");
			sb.append("   AND M370_WORKGUBUN = '0'                           \n");
			sb.append("   AND SUBSTR (M370_SEMOKCODE, 1,1) <> '1'            \n");
		} else if ("31".equals(paramInfo.getString("acc_type")) ){
			sb.append(" WHERE M370_ACCGUBUN = 'B'                            \n");
			sb.append("   AND M370_ACCCODE = '31'                            \n");
			sb.append("   AND M370_WORKGUBUN = '0'                           \n");
		}
		sb.append("  GROUP BY  M370_ACCGUBUN, M370_ACCCODE, M370_WORKGUBUN, M370_SEMOKCODE, M370_SEMOKNAME  \n");
	  sb.append("  ORDER BY  M370_ACCGUBUN, M370_ACCCODE, M370_WORKGUBUN, M370_SEMOKCODE, M370_SEMOKNAME  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
	
		return template.getList(conn, parameters);
	}

	/* 영수필통지서 조회 */
	public static List getAccDateList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   	
		
	  sb.append(" SELECT  B.M420_SEMOKNAME SEMOKNAME                                \n");	
		sb.append("        ,C.M410_PARTNAME PARTNAME                                  \n");
		sb.append("        ,A.ETC_JOBDATE                                             \n");
		sb.append("        ,A.ETC_FILENO                                              \n");
		sb.append("        ,A.ETC_SERIALNO                                            \n");
		sb.append("        ,A.ETC_ACCDATE                                             \n");
    sb.append("        ,DECODE (A.ETC_ACC, '31', '일반회계',                      \n");
    sb.append("                            '51', '특별회계'                       \n");
    sb.append("                ) ACCTYPE                                          \n");
    sb.append("        ,A.ETC_PARTCODE                                            \n");
    sb.append("        ,A.ETC_ACC                                                 \n");
    sb.append("        ,DECODE (A.ETC_ACC, '31', '일반회계',                      \n");
    sb.append("                            '51', '교통사업'                       \n");
    sb.append("                ) ACCNAME                                          \n");
    sb.append("        ,A.ETC_TAX                                                 \n");
    sb.append("        ,A.ETC_GWASENO                                             \n");
    sb.append("        ,A.ETC_SUNAPDATE                                           \n");
    sb.append("        ,(A.ETC_AMT1 + A.ETC_AMT2 + A.ETC_AMT3 + A.ETC_AMT4) AMT   \n");
		sb.append("        ,ETC_PATH                                                  \n");
    sb.append("   FROM  (                                                                                     \n");
    sb.append("         SELECT CASE                                                                           \n");  
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) = ETC_YYYY  THEN ETC_YYYY                         \n");
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) > ETC_YYYY                                        \n");
    sb.append("                THEN  (CASE                                                                    \n");
    sb.append("                       WHEN SUBSTR(ETC_ACCDATE,5,8) >= '0301' THEN SUBSTR(ETC_ACCDATE,1,4)     \n");
    sb.append("                       ELSE  TO_CHAR(SUBSTR(ETC_ACCDATE,1,4) - 1) END)                         \n");
    sb.append("                END AS M010_YEAR                                                               \n");
    sb.append("              , T.*                                                                             \n");
    sb.append("           FROM ETC_T T   ) A                                                                   \n");
    sb.append("        ,M420_STANDARDSEMOKCODE_T B                                \n");
    sb.append("        ,M410_STANDARDPARTCODE_T C                                 \n");
    sb.append("  WHERE  A.M010_YEAR = B.M420_YEAR(+)                                 \n");
		sb.append("    AND  A.ETC_TAX = B.M420_STANDARDSEMOKCODE(+)                      \n");
		sb.append("    AND  A.ETC_ACC = B.M420_STANDARDACCCODE(+)                        \n");
		sb.append("    AND  A.M010_YEAR = C.M410_YEAR(+)                                 \n");
		sb.append("    AND  A.ETC_PARTCODE = C.M410_STANDARDPARTCODE(+)                  \n");
		sb.append("    AND  A.ETC_ACCDATE BETWEEN ? AND ?                             \n");
		if (!"".equals(paramInfo.getString("acc_code")) )	{
			sb.append("  AND A.ETC_ACC = ?                                              \n");
		}
		if (!"".equals(paramInfo.getString("semok_code")) )	{
		  sb.append("   AND B.M420_SYSTEMSEMOKCODE = ?                               \n");
		}
		if (!"".equals(paramInfo.getString("gwase_no")) )	{
			sb.append("  AND A.ETC_GWASENO = ?                                          \n");
		}
		if (!"".equals(paramInfo.getString("part_code")) )	{
			sb.append("  AND C.M410_SYSTEMPARTCODE = ?                                  \n");
		}
		if (!"".equals(paramInfo.getString("start_sunap")) && !"".equals(paramInfo.getString("end_sunap")))	{
			sb.append("  AND A.ETC_SUNAPDATE BETWEEN ? AND ?                            \n");
		}
		if (!"".equals(paramInfo.getString("amt")) )	{
			sb.append("  AND (A.ETC_AMT1 + A.ETC_AMT2 + A.ETC_AMT3 + A.ETC_AMT4) = ?    \n");
		}
		sb.append("  ORDER BY A.ETC_ACCDATE DESC                      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("start_acc"));
		parameters.setString(idx++, paramInfo.getString("end_acc"));
		if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		if (!"".equals(paramInfo.getString("semok_code")) )	{
			parameters.setString(idx++, paramInfo.getString("semok_code"));
		}
		if (!"".equals(paramInfo.getString("gwase_no")) )	{
			parameters.setString(idx++, paramInfo.getString("gwase_no"));
		}
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("start_sunap")) )	{
			parameters.setString(idx++, paramInfo.getString("start_sunap"));
		}
		if (!"".equals(paramInfo.getString("end_sunap")) )	{
			parameters.setString(idx++, paramInfo.getString("end_sunap"));
		}
		if (!"".equals(paramInfo.getString("amt")) )	{
			parameters.setString(idx++, paramInfo.getString("amt"));
		}

		return template.getList(conn, parameters);
	}

  /* 영수필통지서 팝업 */
	public static CommonEntity getReceivedView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT  B.M420_SEMOKNAME SEMOKNAME                                \n");	
		sb.append("        ,C.M410_PARTNAME PARTNAME                                  \n");
		sb.append("        ,A.ETC_JOBDATE                                             \n");
		sb.append("        ,A.ETC_FILENO                                              \n");
		sb.append("        ,A.ETC_SERIALNO                                            \n");
		sb.append("        ,A.ETC_ACCDATE                                             \n");
    sb.append("        ,DECODE (A.ETC_ACC, '31', '일반회계',                      \n");
    sb.append("                            '51', '특별회계'                       \n");
    sb.append("                ) ACCTYPE                                          \n");
    sb.append("        ,A.ETC_PARTCODE                                            \n");
    sb.append("        ,A.ETC_ACC                                                 \n");
    sb.append("        ,DECODE (A.ETC_ACC, '31', '일반회계',                      \n");
    sb.append("                            '51', '교통사업'                       \n");
    sb.append("                ) ACCNAME                                          \n");
    sb.append("        ,A.ETC_TAX                                                 \n");
    sb.append("        ,A.ETC_GWASENO                                             \n");
    sb.append("        ,A.ETC_SUNAPDATE                                           \n");
    sb.append("        ,(A.ETC_AMT1 + A.ETC_AMT2 + A.ETC_AMT3 + A.ETC_AMT4) AMT   \n");
		sb.append("        ,ETC_PATH                                                  \n");
    sb.append("   FROM  (                                                                                     \n");
    sb.append("         SELECT CASE                                                                           \n");  
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) = ETC_YYYY  THEN ETC_YYYY                         \n");
    sb.append("                WHEN SUBSTR(ETC_ACCDATE,1,4) > ETC_YYYY                                        \n");
    sb.append("                THEN  (CASE                                                                    \n");
    sb.append("                       WHEN SUBSTR(ETC_ACCDATE,5,8) >= '0301' THEN SUBSTR(ETC_ACCDATE,1,4)     \n");
    sb.append("                       ELSE  TO_CHAR(SUBSTR(ETC_ACCDATE,1,4) - 1) END)                         \n");
    sb.append("                END AS M010_YEAR                                                               \n");
    sb.append("              , T.*                                                                             \n");
    sb.append("           FROM ETC_T T   ) A                                                                   \n");
    sb.append("        ,M420_STANDARDSEMOKCODE_T B                                \n");
    sb.append("        ,M410_STANDARDPARTCODE_T C                                 \n");
    sb.append("  WHERE  A.M010_YEAR = B.M420_YEAR                                 \n");
		sb.append("    AND  A.ETC_TAX = B.M420_STANDARDSEMOKCODE                      \n");
		sb.append("    AND  A.ETC_ACC = B.M420_STANDARDACCCODE                        \n");
		sb.append("    AND  A.M010_YEAR = C.M410_YEAR                                 \n");
		sb.append("    AND  A.ETC_PARTCODE = C.M410_STANDARDPARTCODE                  \n");
		sb.append("    AND  A.ETC_PATH = ?                                              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("file"));

		return template.getData(conn, parameters);
	}
}
