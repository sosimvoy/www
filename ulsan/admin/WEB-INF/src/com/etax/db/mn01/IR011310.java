/*****************************************************
* 프로젝트명     : 울산광역시 세입및 자금배정시스템
* 프로그램명     : IR011310.java
* 프로그램작성자 :
* 프로그램작성일 : 2014-11-19
* 프로그램내용   : 세입 > 차량일계등록
******************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR011310 {

    private static Logger logger = Logger.getLogger(IR011310.class);

    // 일일마감체크
    public static CommonEntity getDailyInfo(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT M210_WORKENDSTATE  \n"); 
        sb.append("   FROM M210_WORKEND_T     \n");
        sb.append("  WHERE M210_YEAR = ?      \n");
        sb.append("    AND M210_DATE = ?      \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("ac_year"));
		parameters.setString(idx++, paramInfo.getString("ac_date"));

		return template.getData(conn, parameters);
    }


	// 중복 자료 체크
    public static CommonEntity getCarDailyData(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(1) CNT        \n"); 
        sb.append("   FROM CAR_DAILY_TAB       \n");
        sb.append("  WHERE AC_YEAR = ?         \n");
        sb.append("    AND AC_DATE = ?         \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("ac_year"));
        parameters.setString(idx++, paramInfo.getString("ac_date"));

		return template.getData(conn, parameters);
    }

	

    
    
    
    
    
    // 차량일계 조회
    public static List<CommonEntity> getCarDailyList(Connection conn, CommonEntity paramInfo) throws SQLException {
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT AC_YEAR                                                                                                            \n");
        sb.append("     , MAX(AC_DATE) AS AC_DATE                                                                                            \n");
        sb.append("     , AC_NAME                                                                                                            \n");
        sb.append("     , SUM(BEFORE_NUGYE) AS BEFORE_NUGYE                                                                                  \n");
        sb.append("     , SUM(TODAY_SUIP) AS TODAY_SUIP                                                                                      \n");
        sb.append("     , SUM(HWANBU_AMT) AS HWANBU_AMT                                                                                      \n");
        sb.append("     , SUM(HWANBU_NUGYE) AS HWANBU_NUGYE                                                                                  \n");
        sb.append("     , SUM(CORRECT_AMT) AS CORRECT_AMT                                                                                    \n");
        sb.append("     , SUM(BEFORE_NUGYE + TODAY_SUIP - HWANBU_AMT) AS TODAY_NUGYE                                                         \n");
        sb.append("     , ORDER_NO                                                                                                           \n");
        sb.append("  FROM (                                                                                                                  \n");
        sb.append("       SELECT AC_YEAR                                                                                                     \n");
        sb.append("            , AC_DATE                                                                                                     \n");
        sb.append("            , AC_NAME                                                                                                     \n");
        sb.append("            , CASE WHEN PNUM = 1                                                                                          \n");
        sb.append("                    AND AC_DATE = INPUT_DATE                                                                              \n");
        sb.append("                   THEN BEFORE_NUGYE                                                                                      \n");
        sb.append("                   WHEN PNUM = 1                                                                                          \n");
        sb.append("                    AND AC_DATE <> INPUT_DATE                                                                             \n");
        sb.append("                   THEN TODAY_NUGYE                                                                                       \n");
        sb.append("                   ELSE 0                                                                                                 \n");
        sb.append("               END BEFORE_NUGYE                                                                                           \n");
        sb.append("            , CASE WHEN PNUM = 1                                                                                          \n");
        sb.append("                    AND AC_DATE = INPUT_DATE                                                                              \n");
        sb.append("                   THEN TODAY_SUIP                                                                                        \n");
        sb.append("                   ELSE 0                                                                                                 \n");
        sb.append("               END TODAY_SUIP                                                                                             \n");
        sb.append("            , CASE WHEN PNUM = 1                                                                                          \n");
        sb.append("                    AND AC_DATE = INPUT_DATE                                                                              \n");
        sb.append("                   THEN HWANBU_AMT                                                                                        \n");
        sb.append("                   ELSE 0                                                                                                 \n");
        sb.append("               END HWANBU_AMT                                                                                             \n");
        sb.append("            , CASE WHEN PNUM = 1                                                                                          \n");
        sb.append("                   THEN HWANBU_NUGYE                                                                                      \n");
        sb.append("                   ELSE 0                                                                                                 \n");
        sb.append("               END HWANBU_NUGYE                                                                                           \n");
        sb.append("            , CASE WHEN PNUM = 1                                                                                          \n");
        sb.append("                    AND AC_DATE = INPUT_DATE                                                                              \n");
        sb.append("                   THEN CORRECT_AMT                                                                                       \n");
        sb.append("                   ELSE 0                                                                                                 \n");
        sb.append("               END CORRECT_AMT                                                                                            \n");
        sb.append("            , ORDER_NO                                                                                                    \n");
        sb.append("         FROM (                                                                                                           \n");
        sb.append("              SELECT AC_YEAR                                                                                              \n");
        sb.append("                   , AC_DATE                                                                                              \n");
        sb.append("                   , AC_NAME                                                                                              \n");
        sb.append("                   , BEFORE_NUGYE                                                                                         \n");
        sb.append("                   , TODAY_SUIP                                                                                           \n");
        sb.append("                   , HWANBU_AMT                                                                                           \n");
        sb.append("                   , HWANBU_NUGYE                                                                                         \n");
        sb.append("                   , CORRECT_AMT                                                                                          \n");
        sb.append("                   , TODAY_NUGYE                                                                                          \n");
        sb.append("                   , ORDER_NO                                                                                             \n");
        sb.append("                   , ? AS INPUT_DATE                                                                                      \n");
        sb.append("                   , ROW_NUMBER() OVER (PARTITION BY AC_YEAR, AC_NAME ORDER BY AC_YEAR DESC, AC_DATE DESC, AC_NAME) PNUM  \n");
        sb.append("                FROM CAR_DAILY_TAB A                                                                                      \n");
        sb.append("               WHERE AC_YEAR = ?                                                                                          \n");
        sb.append("                 AND AC_DATE <= ?                                                                                         \n");
        sb.append("               UNION ALL                                                                                                  \n");
        sb.append("              SELECT ? AS AC_YEAR                                                                                         \n");
        sb.append("                   , AC_DATE                                                                                              \n");
        sb.append("                   , AC_NAME                                                                                              \n");
        sb.append("                   , BEFORE_NUGYE                                                                                         \n");
        sb.append("                   , TODAY_SUIP                                                                                           \n");
        sb.append("                   , HWANBU_AMT                                                                                           \n");
        sb.append("                   , HWANBU_NUGYE                                                                                         \n");
        sb.append("                   , CORRECT_AMT                                                                                          \n");
        sb.append("                   , TODAY_NUGYE                                                                                          \n");
        sb.append("                   , ORDER_NO                                                                                             \n");
        sb.append("                   , ? AS INPUT_DATE                                                                                      \n");
        sb.append("                   , 0 AS PNUM                                                                                            \n");
        sb.append("                 FROM CAR_DAILY_TAB A                                                                                     \n");
        sb.append("                WHERE AC_DATE = '00000000'                                                                                \n");
        sb.append("              )                                                                                                           \n");
        sb.append("        WHERE PNUM <= 2                                                                                                   \n");
        sb.append("       )                                                                                                                  \n");
        sb.append(" GROUP BY AC_YEAR, AC_NAME, ORDER_NO                                                                                      \n");
        sb.append(" ORDER BY ORDER_NO                                                                                                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("ac_date"));
        parameters.setString(idx++, paramInfo.getString("ac_year"));
        parameters.setString(idx++, paramInfo.getString("ac_date"));
        parameters.setString(idx++, paramInfo.getString("ac_year"));
        parameters.setString(idx++, paramInfo.getString("ac_date"));

        return template.getList(conn, parameters);
    }
    
    
    
 // 차량일계등록
    public static int insertCarDailyData(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO CAR_DAILY_TAB     \n");
        sb.append(" (  AC_YEAR                    \n");
        sb.append("  , AC_DATE                    \n");
        sb.append("  , AC_NAME                    \n");
        sb.append("  , BEFORE_NUGYE               \n");
        sb.append("  , TODAY_SUIP                 \n");
        sb.append("  , HWANBU_AMT                 \n");
        sb.append("  , HWANBU_NUGYE               \n");
        sb.append("  , CORRECT_AMT                \n");
        sb.append("  , TODAY_NUGYE                \n");
        sb.append("  , ORDER_NO                   \n");
        sb.append(" )                             \n");
        sb.append("  VALUES                       \n");
        sb.append(" (                             \n");
        sb.append("    ? --AC_YEAR                \n");
        sb.append("  , ? --AC_DATE                \n");
        sb.append("  , ? --AC_NAME                \n");
        sb.append("  , NVL(?, 0) --BEFORE_NUGYE   \n");
        sb.append("  , NVL(?, 0) --TODAY_SUIP     \n");
        sb.append("  , NVL(?, 0) --HWANBU_AMT     \n");
        sb.append("  , NVL(?, 0)+NVL(?, 0) --HWANBU_NUGYE   \n"); //환부+환부누계
        sb.append("  , NVL(?, 0) --CORRECT_AMT    \n");
        sb.append("  , NVL(?, 0) --TODAY_NUGYE    \n");
        sb.append("  , ? --ORDER_NO               \n");
        sb.append(" )                             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("ac_year"));
        parameters.setString(idx++, paramInfo.getString("ac_date"));
        parameters.setString(idx++, paramInfo.getString("ac_name"));
        parameters.setString(idx++, paramInfo.getString("before_nugye"));
        parameters.setString(idx++, paramInfo.getString("today_suip"));
        parameters.setString(idx++, paramInfo.getString("hwanbu_amt"));
        parameters.setString(idx++, paramInfo.getString("hwanbu_amt"));
        parameters.setString(idx++, paramInfo.getString("hwanbu_nugye"));
        parameters.setString(idx++, paramInfo.getString("correct_amt"));
        parameters.setString(idx++, paramInfo.getString("today_nugye"));
        parameters.setString(idx++, paramInfo.getString("order_no"));

        return template.insert(conn, parameters);
    }
    


	//차량일계 삭제
    public static int deleteCarDailyData(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM CAR_DAILY_TAB            \n");
		sb.append("  WHERE AC_YEAR = ?                   \n");
		sb.append("    AND AC_DATE = ?                   \n");

    	QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("ac_year"));
        parameters.setString(idx++, paramInfo.getString("ac_date"));

        return template.delete(conn, parameters);
    }
}
