/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061610.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 잔액장자료입력
******************************************************/

package com.etax.db.mn06;

import java.sql.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061610 {

	/* 일일업무마감등록(현년도) */
    public static int insertDailyData(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO M210_WORKEND_T                         \n");
		sb.append(" ( M210_YEAR, M210_DATE, M210_PUBLICDEPOSITSTATE ) \n");
        sb.append("VALUES ( SUBSTR(?, 1, 4), ?, ? )                   \n"); 

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, "Y");

        return template.insert(conn, parameters);
    }


	/* 일일업무마감등록(과년도) */
  public static int insertLastData(Connection conn, String date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("INSERT INTO M210_WORKEND_T                         \n");
	sb.append(" ( M210_YEAR, M210_DATE, M210_PUBLICDEPOSITSTATE ) \n");
    sb.append("VALUES ( (SUBSTR(?, 1, 4) - 1), ?, ? )             \n"); 

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, "Y");

    return template.insert(conn, parameters);
  }


	/* 잔액장 자료입력(평상시) */
  public static int insertJanackData(Connection conn, String date, String after_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO M170_JANECKJANG_T                                  \n");
    sb.append(" ( M170_YEAR, M170_DATE,                                        \n");
    sb.append("   M170_ACCTYPE, M170_ACCCODE,                                  \n");
    sb.append(" 	M170_PARTCODE,                                               \n");
    sb.append(" 	M170_MMDABEFOREDAYJANAMT,                                    \n");
    sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_1,                               \n");
    sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_2,                               \n");
    sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_3,                               \n");
    sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_4,                               \n");
    sb.append(" 	M170_RPBEFOREDAYAMT_1,                                       \n");
    sb.append(" 	M170_RPBEFOREDAYAMT_2,                                       \n");
    sb.append(" 	M170_RPBEFOREDAYAMT_3,                                       \n");
    sb.append(" 	M170_RPBEFOREDAYAMT_4,                                       \n");
    sb.append(" 	M170_LOANBEFOREDAYJANAMT )                                   \n");
    sb.append(" SELECT                                                         \n");
    sb.append("   M170_YEAR, ?,                                                \n");
    sb.append(" 	M170_ACCTYPE, M170_ACCCODE,                                  \n");
    sb.append(" 	M170_PARTCODE,                                               \n");
    sb.append(" 	NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0),   \n");
    sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0), \n");
    sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0), \n");
    sb.append("   NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0), \n");
    sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0), \n");
    sb.append(" 	NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0),              \n");
    sb.append(" 	NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0),              \n");
    sb.append(" 	NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0),              \n");
    sb.append(" 	NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0),              \n");
    sb.append(" 	NVL(M170_LOANBEFOREDAYJANAMT,0) + NVL(M170_LOANJEUNGGAMAMT,0)            \n");
		sb.append("   FROM M170_JANECKJANG_T                                       \n");
    sb.append("  WHERE M170_YEAR = SUBSTR( ?, 1, 4)                            \n");
    sb.append("    AND M170_DATE = ?                                           \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, after_date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);

    return template.insert(conn, parameters);
  }


	/* 잔액장 자료입력(12월 31일) *///기금, 일반, 특별
    public static int insertJanackData2(Connection conn, String date, String after_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M170_JANECKJANG_T                                               \n");
        sb.append(" ( M170_YEAR, M170_DATE,                                                     \n");
        sb.append("   M170_ACCTYPE, M170_ACCCODE,                                               \n");
        sb.append(" 	M170_PARTCODE,                                                          \n");
        sb.append(" 	M170_MMDABEFOREDAYJANAMT,                                               \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_1,                                          \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_2,                                          \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_3,                                          \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_4,                                          \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_1,                                                  \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_2,                                                  \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_3,                                                  \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_4,                                                  \n");
        sb.append(" 	M170_LOANBEFOREDAYJANAMT )                                              \n");
        sb.append(" SELECT                                                                      \n");
        sb.append("   CASE WHEN M170_ACCTYPE IN ('A', 'B')                                      \n");
        sb.append("        THEN M170_YEAR                                                       \n");
        sb.append("        WHEN M170_ACCTYPE IN ('D', 'E')                                      \n");
        sb.append("        THEN TO_CHAR(M170_YEAR +1)                                           \n");
        sb.append("    END M170_YEAR, ?,                                                        \n");
        sb.append(" 	M170_ACCTYPE, M170_ACCCODE,                                             \n");
        sb.append(" 	M170_PARTCODE,                                                          \n");
        sb.append(" 	NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0),              \n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0),\n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0),\n");
        sb.append("   NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0),  \n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0),\n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0),             \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0),             \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0),             \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0),             \n");
        sb.append(" 	NVL(M170_LOANBEFOREDAYJANAMT,0) + NVL(M170_LOANJEUNGGAMAMT,0)           \n");
		sb.append("   FROM M170_JANECKJANG_T                                                    \n");
        sb.append("  WHERE M170_YEAR = SUBSTR( ?, 1, 4)                                         \n");
        sb.append("    AND M170_DATE = ?                                                        \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, after_date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);

        return template.insert(conn, parameters);
    }



    /* 잔액장 자료입력(12월 31일) */ //일반, 특별 다음 회계연도 인서트
    public static int insertIlbanData(Connection conn, String date, String after_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M170_JANECKJANG_T         \n");
        sb.append(" ( M170_YEAR, M170_DATE,               \n");
        sb.append("   M170_ACCTYPE, M170_ACCCODE,         \n");
        sb.append(" 	M170_PARTCODE,                    \n");
        sb.append(" 	M170_MMDABEFOREDAYJANAMT,         \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_1,    \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_2,    \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_3,    \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_4,    \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_1,            \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_2,            \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_3,            \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_4,            \n");
        sb.append(" 	M170_LOANBEFOREDAYJANAMT )        \n");
        sb.append(" SELECT                                \n");
        sb.append("     SUBSTR(?, 1, 4), ?,               \n");
        sb.append(" 	M170_ACCTYPE, M170_ACCCODE,       \n");
        sb.append(" 	M170_PARTCODE,                    \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append("     0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0,                                \n");
        sb.append(" 	0                                 \n");
		sb.append("   FROM M170_JANECKJANG_T              \n");
        sb.append("  WHERE M170_YEAR = SUBSTR( ?, 1, 4)   \n");
        sb.append("    AND M170_DATE = ?                  \n");
        sb.append("    AND M170_ACCTYPE IN ('A', 'B')     \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, after_date);
        parameters.setString(idx++, after_date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);

        return template.insert(conn, parameters);
    }


	/* 잔액장 자료입력(폐쇄기 과년도 자료 넣기) */
    public static int insertJanackData3(Connection conn, String date, String after_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M170_JANECKJANG_T                                  \n");
        sb.append(" ( M170_YEAR, M170_DATE,                                        \n");
        sb.append("   M170_ACCTYPE, M170_ACCCODE,                                  \n");
        sb.append(" 	M170_PARTCODE,                                               \n");
        sb.append(" 	M170_MMDABEFOREDAYJANAMT,                                    \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_1,                               \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_2,                               \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_3,                               \n");
        sb.append(" 	M170_DEPOSITBEFOREDAYJANAMT_4,                               \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_1,                                       \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_2,                                       \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_3,                                       \n");
        sb.append(" 	M170_RPBEFOREDAYAMT_4,                                       \n");
        sb.append(" 	M170_LOANBEFOREDAYJANAMT )                                 \n");
        sb.append(" SELECT                                                         \n");
        sb.append("   M170_YEAR, ?,                                            \n");
        sb.append(" 	M170_ACCTYPE, M170_ACCCODE,                                  \n");
        sb.append(" 	M170_PARTCODE,                                               \n");
        sb.append(" 	NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0),   \n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0), \n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0), \n");
        sb.append("   NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0), \n");
        sb.append(" 	NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0), \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0),              \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0),              \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0),              \n");
        sb.append(" 	NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0),              \n");
        sb.append(" 	NVL(M170_LOANBEFOREDAYJANAMT,0) + NVL(M170_LOANJEUNGGAMAMT,0)            \n");
		sb.append("   FROM M170_JANECKJANG_T                                       \n");
        sb.append("  WHERE M170_YEAR = (SUBSTR( ?, 1, 4) -1)                       \n");
        sb.append("    AND M170_DATE = ?                                           \n");
		sb.append("    AND M170_ACCTYPE IN ('A', 'B')                              \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, after_date);
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);

        return template.insert(conn, parameters);
    }


    /* 잔액장 자료입력(3월 10일 작업처리) */  
    public static int insertJanackData4(Connection conn, String date, String after_date, String fis_year) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" INSERT INTO M170_JANECKJANG_T                                                 \n");
        sb.append("( M170_YEAR, M170_DATE, M170_ACCTYPE, M170_ACCCODE, M170_PARTCODE              \n");
        sb.append(" , M170_MMDABEFOREDAYJANAMT                                                    \n");
        sb.append(" , M170_DEPOSITBEFOREDAYJANAMT_1, M170_DEPOSITBEFOREDAYJANAMT_2                \n");
        sb.append(" , M170_DEPOSITBEFOREDAYJANAMT_3, M170_DEPOSITBEFOREDAYJANAMT_4                \n");
        sb.append(" , M170_RPBEFOREDAYAMT_1, M170_RPBEFOREDAYAMT_2                                \n");
        sb.append(" , M170_RPBEFOREDAYAMT_3, M170_RPBEFOREDAYAMT_4                                \n");
        sb.append(" , M170_LOANBEFOREDAYJANAMT )                                                  \n");
        sb.append(" SELECT                                                                        \n");
        sb.append("     ?, ?,                                                                     \n");
        sb.append("     M170_ACCTYPE, M170_ACCCODE,                                               \n");
        sb.append("     M170_PARTCODE,                                                            \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)   \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)   \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) MMDA_AMT,                                                       \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)   \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)   \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) DEPOSIT_AMT_1,                                                  \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)   \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)   \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) DEPOSIT_AMT_2,                                                  \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)   \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)   \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) DEPOSIT_AMT_3,                                                  \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)   \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)   \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) DEPOSIT_AMT_4,                                                  \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) RP_AMT_1,                                                       \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) RP_AMT_2,                                                       \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) RP_AMT_3,                                                       \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)                \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)                \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) RP_AMT_4,                                                       \n");
        sb.append("     SUM(CASE WHEN M170_ACCTYPE IN ('A', 'B')                                  \n");
        sb.append("              THEN NVL(M170_LOANBEFOREDAYJANAMT,0) + NVL(M170_LOANJEUNGGAMAMT,0)             \n");
        sb.append("              WHEN M170_ACCTYPE IN ('D', 'E')                                  \n");
        sb.append("               AND M170_YEAR = ?                                               \n");
        sb.append("              THEN NVL(M170_LOANBEFOREDAYJANAMT,0) + NVL(M170_LOANJEUNGGAMAMT,0)             \n");
        sb.append("              ELSE 0                                                           \n");
        sb.append("          END) LOAN_AMT                                                        \n");
        sb.append("   FROM M170_JANECKJANG_T                                                      \n");
        sb.append("  WHERE M170_YEAR in ( ?,  ?-1)                                                \n");
        sb.append("    AND M170_DATE = ?                                                          \n");
        sb.append("  GROUP BY M170_ACCTYPE, M170_ACCCODE, M170_PARTCODE                           \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, fis_year);
		parameters.setString(idx++, after_date);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
        parameters.setString(idx++, fis_year);
		parameters.setString(idx++, date);

        return template.insert(conn, parameters);
    }

    /* 데몬 연계테이블 자료 넣기 */
	public static int insertAcctInfo(Connection conn, String date, String tml_no) throws SQLException {

		int retValue = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(" DELETE FROM M480_INQUIRYMANAGE_T \n");

        QueryTemplate template1 = new QueryTemplate(sb.toString());
		QueryParameters parameters1 = new QueryParameters();

		template1.insert(conn, parameters1);
        sb.delete(0,sb.length());
        sb.append(" INSERT INTO M480_INQUIRYMANAGE_T                          \n");
        sb.append("   ( M480_DATE, M480_ACCOUNTNO, M480_YEAR,                 \n");
        sb.append("     M480_ACCGUBUN, M480_ACCCODE, M480_PARTCODE,           \n");
        sb.append("     M480_JUHANGACCYN, M480_TERMINALNO,                    \n");
        sb.append("     M480_ACCOUNTTYPE)                                     \n");
        sb.append(" SELECT ?, M300_ACCOUNTNO, M300_YEAR,                      \n");
        sb.append("        DECODE(M300_ACCGUBUN, '', 'A', M300_ACCGUBUN),     \n");
        sb.append("        DECODE(M300_ACCCODE, '', '11', M300_ACCCODE),      \n");
        sb.append("        DECODE(M300_PARTCODE, '', '00000', M300_PARTCODE), \n");
        sb.append("        M300_JUHANGACCYN, ?,                               \n");
        sb.append("        M300_ACCOUNTTYPE                                   \n");
        sb.append("   FROM M300_ACCOUNTMANAGE_T                               \n");
        sb.append("  WHERE M300_YEAR = SUBSTR( ?, 1, 4)                       \n");
        sb.append("    AND M300_BANKCODE = '039'                              \n");

		QueryTemplate template2 = new QueryTemplate(sb.toString());
		QueryParameters parameters2 = new QueryParameters();
    
        int idx = 1;

		parameters2.setString(idx++, date);
		parameters2.setString(idx++, tml_no);
		parameters2.setString(idx++, date);

        retValue = template2.insert(conn, parameters2);

        sb.delete(0,sb.length());
		sb.append(" INSERT INTO M480_INQUIRYMANAGE_T                   \n");
        sb.append("   ( M480_DATE, M480_ACCOUNTNO, M480_YEAR,          \n");
        sb.append("     M480_ACCGUBUN, M480_ACCCODE, M480_PARTCODE,    \n");
        sb.append("     M480_JUHANGACCYN, M480_TERMINALNO,             \n");
        sb.append("     M480_ACCOUNTTYPE)                              \n");
        sb.append(" SELECT ?, '99999999999999999999', M300_YEAR,       \n");
        sb.append("        M300_ACCGUBUN, M300_ACCCODE, M300_PARTCODE, \n");
        sb.append("        M300_JUHANGACCYN, ?,                        \n");
        sb.append("        '05'                                        \n");  //2012.1.13 일상경비의 공금계좌화로 인해 추가함.
        sb.append("   FROM M300_ACCOUNTMANAGE_T                        \n");
        sb.append("  WHERE M300_YEAR = SUBSTR( ?, 1, 4)                \n");
        sb.append("    AND M300_BANKCODE = '039'                       \n");
		sb.append("    AND ROWNUM = 1                                  \n");


		QueryTemplate template3 = new QueryTemplate(sb.toString());
		QueryParameters parameters3 = new QueryParameters();
    
        int idx1 = 1;

		parameters3.setString(idx1++, date);
		parameters3.setString(idx1++, tml_no);
		parameters3.setString(idx1++, date);

        return retValue + template3.insert(conn, parameters3);

    }

  
	/* 데몬연계테이블 넣기(과년도, 일반 특별회계만)*/
	public static int insertAcctInfo2(Connection conn, String date, String tml_no) throws SQLException {
    
		StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M480_INQUIRYMANAGE_T                   \n");
        sb.append("   ( M480_DATE, M480_ACCOUNTNO, M480_YEAR,          \n");
        sb.append("     M480_ACCGUBUN, M480_ACCCODE, M480_PARTCODE,    \n");
        sb.append("     M480_JUHANGACCYN, M480_TERMINALNO,             \n");
        sb.append("     M480_ACCOUNTTYPE)                              \n");
        sb.append(" SELECT ?, M300_ACCOUNTNO, M300_YEAR,               \n");
        sb.append("        M300_ACCGUBUN, M300_ACCCODE, M300_PARTCODE, \n");
        sb.append("        M300_JUHANGACCYN, ?,                        \n");
        sb.append("        M300_ACCOUNTTYPE                            \n");
        sb.append("   FROM M300_ACCOUNTMANAGE_T                        \n");
        sb.append("  WHERE M300_YEAR = (SUBSTR( ?, 1, 4) -1)           \n");
        sb.append("    AND M300_BANKCODE = '039'                       \n");
		sb.append("    AND M300_ACCGUBUN IN ('A', 'B')                 \n");
        sb.append("    AND M300_ACCOUNTTYPE <> '04'                    \n");   //2012.01.13 일상경비는 제외

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, date);
		parameters.setString(idx++, tml_no);
		parameters.setString(idx++, date);

        return template.insert(conn, parameters);

    }


	/* 기타예금 조회 */
	public static CommonEntity getEtcJanInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    
		StringBuffer sb = new StringBuffer();

        sb.append(" SELECT NVL(M170_CITYSPECIALDEPOSIT,0) M170_CITYSPECIALDEPOSIT   \n");
        sb.append("       ,NVL(M170_ULSANSPECIALDEPOSIT,0) M170_ULSANSPECIALDEPOSIT \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_1                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_2                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_3                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_4                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_5                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_6                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_7                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_8                               \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_9                               \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_1,0) M170_JEUNGGAMSEIPAMT_1     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_2,0) M170_JEUNGGAMSEIPAMT_2     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_3,0) M170_JEUNGGAMSEIPAMT_3     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_4,0) M170_JEUNGGAMSEIPAMT_4     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_5,0) M170_JEUNGGAMSEIPAMT_5     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_6,0) M170_JEUNGGAMSEIPAMT_6     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_7,0) M170_JEUNGGAMSEIPAMT_7     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_8,0) M170_JEUNGGAMSEIPAMT_8     \n");
        sb.append("       ,NVL(M170_JEUNGGAMSEIPAMT_9,0) M170_JEUNGGAMSEIPAMT_9     \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_1                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_2                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_3                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_4                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_5                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_6                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_7                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_8                             \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_9                             \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_1,0) M170_JEUNGGAMSECHULAMT_1 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_2,0) M170_JEUNGGAMSECHULAMT_2 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_3,0) M170_JEUNGGAMSECHULAMT_3 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_4,0) M170_JEUNGGAMSECHULAMT_4 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_5,0) M170_JEUNGGAMSECHULAMT_5 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_6,0) M170_JEUNGGAMSECHULAMT_6 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_7,0) M170_JEUNGGAMSECHULAMT_7 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_8,0) M170_JEUNGGAMSECHULAMT_8 \n");
        sb.append("       ,NVL(M170_JEUNGGAMSECHULAMT_9,0) M170_JEUNGGAMSECHULAMT_9 \n");
        sb.append("   FROM M170_JANECKJANG_T                                        \n");
        sb.append("  WHERE M170_YEAR = SUBSTR(?, 1, 4)                              \n");
        sb.append("    AND M170_DATE = ?                                            \n");
        sb.append("    AND M170_ACCTYPE = 'A'                                       \n");
        sb.append("    AND M170_ACCCODE = '11'                                      \n");
        sb.append("    AND M170_PARTCODE = '00000'                                  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("reg_date"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));

        return template.getData(conn, parameters);

    }



	/* 기타예금 마감으로 수정(현년도) */
    public static int upateEtcMagam(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE M210_WORKEND_T                 \n");
		sb.append("   SET M210_ETCDEPOSITSTATE = ?       \n");
        sb.append(" WHERE M210_YEAR = SUBSTR(?, 1, 4)    \n"); 
		sb.append("   AND M210_DATE = ?                  \n"); 

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, "Y");
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);		

        return template.update(conn, parameters);
    }



	/* 기타예금 마감으로 수정(과년도) */
    public static int upateEtcMagam2(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE M210_WORKEND_T                 \n");
		sb.append("   SET M210_ETCDEPOSITSTATE = ?       \n");
        sb.append(" WHERE M210_YEAR = SUBSTR(?, 1, 4) -1 \n"); 
		sb.append("   AND M210_DATE = ?                  \n"); 

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, "Y");
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);		

        return template.update(conn, parameters);
    }

	/* 기타예금 잔액장 수정(과년도) */
    public static int updateEtcJanak2(Connection conn, String date, String city_byul, String ulsan_byul, 
		String[] seip_content, String[] seip_amt, String[] sechul_content, String[] sechul_amt) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE M170_JANECKJANG_T                      \n");
		sb.append("   SET M170_CITYSPECIALDEPOSIT = NVL(?, 0)    \n");
		sb.append("      ,M170_ULSANSPECIALDEPOSIT = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_1 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_1 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_1 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_1 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_2 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_2 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_2 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_2 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_3 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_3 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_3 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_3 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_4 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_4 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_4 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_4 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_5 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_5 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_5 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_5 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_6 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_6 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_6 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_6 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_7 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_7 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_7 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_7 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_8 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_8 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_8 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_8 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_9 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_9 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_9 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_9 = NVL(?, 0)   \n");
        sb.append(" WHERE M170_YEAR = SUBSTR(?, 1, 4) -1         \n"); 
		sb.append("   AND M170_DATE = ?                          \n");
		sb.append("   AND M170_ACCTYPE = 'A'                     \n");
		sb.append("   AND M170_ACCCODE = '11'                    \n");
		sb.append("   AND M170_PARTCODE = '00000'                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, city_byul);
		parameters.setString(idx++, ulsan_byul);
		for (int i=0; i<9; i++)	{
			parameters.setString(idx++, seip_content[i]);
			parameters.setString(idx++, seip_amt[i]);
			parameters.setString(idx++, sechul_content[i]);
			parameters.setString(idx++, sechul_amt[i]);
		}
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);		

        return template.update(conn, parameters);
    }



	/* 기타예금 잔액장 수정(현년도) */
    public static int updateEtcJanak(Connection conn, String date, String city_byul, String ulsan_byul, 
		String[] seip_content, String[] seip_amt, String[] sechul_content, String[] sechul_amt) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE M170_JANECKJANG_T                      \n");
		sb.append("   SET M170_CITYSPECIALDEPOSIT = NVL(?, 0)    \n");
		sb.append("      ,M170_ULSANSPECIALDEPOSIT = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_1 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_1 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_1 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_1 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_2 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_2 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_2 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_2 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_3 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_3 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_3 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_3 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_4 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_4 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_4 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_4 = NVL(?, 0)   \n");
		sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_5 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_5 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_5 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_5 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_6 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_6 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_6 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_6 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_7 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_7 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_7 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_7 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_8 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_8 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_8 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_8 = NVL(?, 0)   \n");
        sb.append("      ,M170_JEUNGGAMSEIPHANGMOK_9 = ?         \n");
		sb.append("      ,M170_JEUNGGAMSEIPAMT_9 = NVL(?, 0)     \n");
		sb.append("      ,M170_JEUNGGAMSECHULHANGMOK_9 = ?       \n");
		sb.append("      ,M170_JEUNGGAMSECHULAMT_9 = NVL(?, 0)   \n");
        sb.append(" WHERE M170_YEAR = SUBSTR(?, 1, 4)            \n"); 
		sb.append("   AND M170_DATE = ?                          \n");
		sb.append("   AND M170_ACCTYPE = 'A'                     \n");
		sb.append("   AND M170_ACCCODE = '11'                    \n");
		sb.append("   AND M170_PARTCODE = '00000'                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, city_byul);
		parameters.setString(idx++, ulsan_byul);
		for (int i=0; i<9; i++)	{
			parameters.setString(idx++, seip_content[i]);
			parameters.setString(idx++, seip_amt[i]);
			parameters.setString(idx++, sechul_content[i]);
			parameters.setString(idx++, sechul_amt[i]);
		}
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);		

        return template.update(conn, parameters);
    }
}