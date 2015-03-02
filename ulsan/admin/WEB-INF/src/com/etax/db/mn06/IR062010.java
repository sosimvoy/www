/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR062010.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 일일자금현황조회
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR062010 {

	/* 일일자금운용현황 조회 */
  public static List<CommonEntity> getDailyMoneyList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT CASE WHEN M360_ACCCODE > '61'                           \n");
    sb.append("             THEN '재배정'                                      \n");
    sb.append("             ELSE '일반회계'                                    \n");
    sb.append("         END GUBUN                                              \n");
    sb.append("       ,M360_ACCCODE                                            \n");
    sb.append("       ,M360_ACCNAME                                            \n");
    sb.append("       ,SUM(NVL(YESTERDAY_AMT, 0)) YESTERDAY_AMT                \n");
    sb.append("       ,SUM(NVL(TODAY_AMT, 0)) TODAY_AMT                        \n");
    sb.append("       ,SUM(NVL(TODAY_AMT, 0) - NVL(YESTERDAY_AMT,0)) YT_AMT    \n");
    sb.append("   FROM M360_ACCCODE_T A,                                       \n");
    sb.append("       (SELECT M170_ACCCODE, M170_YEAR                          \n");
    sb.append("             ,DECODE(M170_DATE, ?,                              \n");
    sb.append("                  M170_OFFICIALDEPOSITJANAMT, 0) YESTERDAY_AMT  \n");
    sb.append("             ,DECODE(M170_DATE, ?,                              \n");
    sb.append("                  M170_OFFICIALDEPOSITJANAMT, 0) TODAY_AMT      \n");
    sb.append("         FROM M170_JANECKJANG_T                                 \n");
    sb.append("        WHERE M170_YEAR = ?                                     \n");
    sb.append("          AND (M170_DATE = ?                                    \n");
    sb.append("              OR M170_DATE = ?)                                 \n");
    sb.append("          AND M170_ACCTYPE = 'A'                                \n");
    sb.append("        ) B                                                     \n");
    sb.append("  WHERE A.M360_ACCCODE = B.M170_ACCCODE(+)                      \n");
    sb.append("    AND A.M360_YEAR = B.M170_YEAR                               \n");
    sb.append("    AND A.M360_ACCGUBUN = 'A'                                   \n");
    sb.append("  GROUP BY ROLLUP ((CASE WHEN M360_ACCCODE > '61'               \n");
    sb.append("             THEN '재배정'                                      \n");
    sb.append("             ELSE '일반회계'                                    \n");
    sb.append("         END), (M360_ACCCODE, M360_ACCNAME))                    \n");
    sb.append("  ORDER BY GUBUN, M360_ACCCODE                                  \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("before_date"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("before_date"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }
}
