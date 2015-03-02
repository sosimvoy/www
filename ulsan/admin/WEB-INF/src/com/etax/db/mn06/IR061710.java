/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061710.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 잔액장자료입력
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061710 {

	/* 예금종류별(기간) - 울산시청 */
    public static List<CommonEntity> getGiganList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" WITH TMPTABLE AS (                                                                                                 \n");
        sb.append("  SELECT DECODE(M360_ACCGUBUN, 'D', 'F', M360_ACCGUBUN) M360_ACCGUBUN                                               \n");
        sb.append("        ,CASE WHEN M360_ACCGUBUN = 'A'                                                                              \n");
        sb.append("              THEN '11'                                                                                             \n");
        sb.append("              WHEN M360_ACCGUBUN = 'D'                                                                              \n");
        sb.append("              THEN '10'                                                                                             \n");
        sb.append("              WHEN M360_ACCGUBUN = 'E'                                                                              \n");
        sb.append("               AND M360_ACCCODE = '40'                                                                              \n");
        sb.append("              THEN '14'                                                                                             \n");
        sb.append("              WHEN M360_ACCGUBUN = 'B'                                                                              \n");
        sb.append("               AND M360_ACCCODE = '06'                                                                              \n");
        sb.append("              THEN '05'                                                                                             \n");
        sb.append("              WHEN M360_ACCGUBUN = 'B'                                                                              \n");
        sb.append("               AND M360_ACCCODE = '69'                                                                              \n");
        sb.append("              THEN '68'                                                                                             \n");
        sb.append("              ELSE M360_ACCCODE                                                                                     \n");
        sb.append("          END M360_ACCCODE                                                                                          \n");
        sb.append("        ,M360_ACCNAME                                                                                               \n");
        sb.append("        ,NVL(M170_OFFICIALDEPOSITJANAMT,0) M170_OFFICIALDEPOSITJANAMT                                               \n");
        sb.append("        ,0 M170_CITYSPECIALDEPOSIT                                                                                  \n");
        sb.append("        ,NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0) M170_MMDABEFOREDAYJANAMT                         \n");
        sb.append("        ,NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0) M170_DEPOSITBEFOREDAYJANAMT_1      \n");
        sb.append("        ,NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0) M170_DEPOSITBEFOREDAYJANAMT_2      \n");
        sb.append("        ,NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0) M170_DEPOSITBEFOREDAYJANAMT_3      \n");
        sb.append("        ,NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0) M170_DEPOSITBEFOREDAYJANAMT_4      \n");
        sb.append("        ,NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0) M170_RPBEFOREDAYAMT_1                           \n");
        sb.append("        ,NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0) M170_RPBEFOREDAYAMT_2                           \n");
        sb.append("        ,NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0) M170_RPBEFOREDAYAMT_3                           \n");
        sb.append("        ,NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0) M170_RPBEFOREDAYAMT_4                           \n");
        sb.append("    FROM M170_JANECKJANG_T A                                                                                        \n");
        sb.append("        ,M360_ACCCODE_T B                                                                                           \n");
        sb.append("   WHERE A.M170_YEAR = B.M360_YEAR                                                                               \n");
        sb.append("     AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                                                        \n");
        sb.append("     AND A.M170_ACCCODE(+) = B.M360_ACCCODE                                                                         \n");
        sb.append("     AND A.M170_DATE(+) = ?                                                                                         \n");
        sb.append("     AND B.M360_ACCGUBUN IN ('A', 'B', 'D', 'E')                                                                    \n");
        sb.append("     AND CASE                                                                                                       \n");
        sb.append("         WHEN B.M360_ACCGUBUN = 'A' THEN '1'                                                                        \n");
        sb.append(" 				WHEN B.M360_ACCGUBUN = 'D' THEN '1'                                                                \n");
        sb.append("         WHEN B.M360_ACCGUBUN = 'E' THEN '1'                                                                        \n");
        sb.append("         WHEN B.M360_ACCGUBUN = 'B' AND                                                                             \n");
        sb.append("              B.M360_ACCCODE NOT IN ('32', '33', '34', '35', '36') THEN '1'                                         \n");
        sb.append("         ELSE '0' END = '1'                                                                                         \n");
        sb.append("   UNION ALL                                                                                                        \n");
        sb.append("  SELECT 'I' M360_ACCGUBUN                                                                                          \n");
        sb.append("        ,'11' M360_ACCCODE                                                                                          \n");
        sb.append("        ,'일상경비' M360_ACCNAME                                                                                    \n");
        sb.append("        ,NVL(M170_ILSANG_AMT,0) M170_OFFICIALDEPOSITJANAMT                                                          \n");
        sb.append("        ,0 M170_CITYSPECIALDEPOSIT                                                                                  \n");
        sb.append("        ,0 M170_MMDABEFOREDAYJANAMT                                                                                 \n");
        sb.append("        ,0 M170_DEPOSITBEFOREDAYJANAMT_1                                                                            \n");
        sb.append("        ,0 M170_DEPOSITBEFOREDAYJANAMT_2                                                                            \n");
        sb.append("        ,0 M170_DEPOSITBEFOREDAYJANAMT_3                                                                            \n");
        sb.append("        ,0 M170_DEPOSITBEFOREDAYJANAMT_4                                                                            \n");
        sb.append("        ,0 M170_RPBEFOREDAYAMT_1                                                                                    \n");
        sb.append("        ,0 M170_RPBEFOREDAYAMT_2                                                                                    \n");
        sb.append("        ,0 M170_RPBEFOREDAYAMT_3                                                                                    \n");
        sb.append("        ,0 M170_RPBEFOREDAYAMT_4                                                                                    \n");
        sb.append("    FROM M170_JANECKJANG_T A                                                                                        \n");
        sb.append("   WHERE A.M170_DATE = ?                                                                                            \n");
        sb.append("     AND A.M170_ACCTYPE IN ('A', 'B', 'D', 'E')                                                                     \n");
        sb.append("   )                                                                                                                \n");
        sb.append("   SELECT CASE WHEN M360_ACCCODE IS NULL                                                                            \n");
        sb.append("              THEN '00'                                                                                             \n");
        sb.append("              ELSE M360_ACCCODE                                                                                     \n");
        sb.append("          END CHART                                                                                                 \n");
        sb.append("         ,M360_ACCGUBUN                                                                                             \n");
        sb.append("         ,M360_ACCCODE                                                                                              \n");
        sb.append(" 				,CASE WHEN M360_ACCGUBUN = 'A'                                                                     \n");
        sb.append("                AND M360_ACCCODE IS NULL                                                                            \n");
        sb.append("               THEN '일반회계'                                                                                      \n");
        sb.append(" 				      WHEN M360_ACCGUBUN = 'B'                                                                     \n");
        sb.append("                AND M360_ACCCODE IS NULL                                                                            \n");
        sb.append("               THEN '특별회계'                                                                                      \n");
        sb.append("               WHEN M360_ACCGUBUN = 'E'                                                                             \n");
        sb.append("                AND M360_ACCCODE IS NULL                                                                            \n");
        sb.append("               THEN '기금'                                                                                          \n");
        sb.append("               WHEN M360_ACCGUBUN = 'F'                                                                             \n");
        sb.append("                AND M360_ACCCODE IS NULL                                                                            \n");
        sb.append("               THEN '세입세출외'                                                                                    \n");
        sb.append("               WHEN M360_ACCGUBUN = 'E'                                                                             \n");
        sb.append("                AND M360_ACCCODE = '16'                                                                             \n");
        sb.append("               THEN '(생활보호)'                                                                                    \n");
        sb.append("               WHEN M360_ACCGUBUN = 'E'                                                                             \n");
        sb.append("                AND M360_ACCCODE = '17'                                                                             \n");
        sb.append("               THEN '(노인복지)'                                                                                    \n");
        sb.append("               WHEN M360_ACCGUBUN = 'E'                                                                             \n");
        sb.append("                AND M360_ACCCODE = '18'                                                                             \n");
        sb.append("               THEN '(저출산)'                                                                                      \n");
        sb.append("               WHEN M360_ACCGUBUN IS NULL                                                                           \n");
        sb.append("                AND M360_ACCCODE IS NULL                                                                            \n");
        sb.append("               THEN '합 계'                                                                                         \n");
        sb.append("               ELSE SUBSTRB(MAX(M360_ACCNAME),1,12)                                                                 \n");
        sb.append("           END M360_ACCNAME                                                                                         \n");
        sb.append(" 				,SUM(M170_OFFICIALDEPOSITJANAMT) M170_OFFICIALDEPOSITJANAMT                                        \n");
        sb.append(" 				,0 M170_CITYSPECIALDEPOSIT                                                                         \n");
        sb.append(" 				,SUM(M170_MMDABEFOREDAYJANAMT) M170_MMDABEFOREDAYJANAMT                                            \n");
        sb.append(" 				,SUM(M170_DEPOSITBEFOREDAYJANAMT_1) M170_DEPOSITBEFOREDAYJANAMT_1                                  \n");
        sb.append(" 				,SUM(M170_DEPOSITBEFOREDAYJANAMT_2) M170_DEPOSITBEFOREDAYJANAMT_2                                  \n");
        sb.append(" 				,SUM(M170_DEPOSITBEFOREDAYJANAMT_3) M170_DEPOSITBEFOREDAYJANAMT_3                                  \n");
        sb.append("         ,SUM(M170_DEPOSITBEFOREDAYJANAMT_4) M170_DEPOSITBEFOREDAYJANAMT_4                                          \n");
        sb.append("         ,SUM(M170_DEPOSITBEFOREDAYJANAMT_1 + M170_DEPOSITBEFOREDAYJANAMT_2                                         \n");
        sb.append("        + M170_DEPOSITBEFOREDAYJANAMT_3 + M170_DEPOSITBEFOREDAYJANAMT_4) M170_DEPOSITBEFOREDAYJANAMT                \n");
        sb.append("         ,SUM(M170_RPBEFOREDAYAMT_1) M170_RPBEFOREDAYAMT_1                                                          \n");
        sb.append("         ,SUM(M170_RPBEFOREDAYAMT_2) M170_RPBEFOREDAYAMT_2                                                          \n");
        sb.append("         ,SUM(M170_RPBEFOREDAYAMT_3) M170_RPBEFOREDAYAMT_3                                                          \n");
        sb.append("         ,SUM(M170_RPBEFOREDAYAMT_4) M170_RPBEFOREDAYAMT_4                                                          \n");
        sb.append("         ,SUM(M170_RPBEFOREDAYAMT_1 + M170_RPBEFOREDAYAMT_2                                                         \n");
        sb.append("        + M170_RPBEFOREDAYAMT_3 + M170_RPBEFOREDAYAMT_4) M170_RPBEFOREDAYAMT                                        \n");
        sb.append("     FROM TMPTABLE                                                                                                  \n");
        sb.append("    GROUP BY ROLLUP(M360_ACCGUBUN, M360_ACCCODE)                                                                    \n");
        sb.append("  ORDER BY M360_ACCGUBUN, CHART                                                                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getList(conn, parameters);
    }


	/* 사회복지 */
    public static CommonEntity getSocial(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("    SELECT M360_ACCGUBUN                                                                                            \n");
        sb.append("        ,DECODE(M360_ACCCODE,'16','1510','17','1510','18','1510') AS M360_ACCCODE                                   \n");
        sb.append("        ,'사회복지' AS M360_ACCNAME                                                                                 \n");
        sb.append("        ,SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0)) M170_OFFICIALDEPOSITJANAMT                                          \n");
        sb.append("        ,0 M170_CITYSPECIALDEPOSIT                                                                                  \n");
        sb.append("        ,SUM(NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)) M170_MMDABEFOREDAYJANAMT                    \n");
        sb.append("        ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)) M170_DEPOSITBEFOREDAYJANAMT_1 \n");
        sb.append("        ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)) M170_DEPOSITBEFOREDAYJANAMT_2 \n");
        sb.append("        ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)) M170_DEPOSITBEFOREDAYJANAMT_3 \n");
        sb.append("        ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)) M170_DEPOSITBEFOREDAYJANAMT_4 \n");
        sb.append("        ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)                                \n");
        sb.append("        +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)                                    \n");
        sb.append("        +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)                                    \n");
        sb.append("        +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)) M170_DEPOSITBEFOREDAYJANAMT       \n");
        sb.append("        ,SUM(NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)) M170_RPBEFOREDAYAMT_1                      \n");
        sb.append("        ,SUM(NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)) M170_RPBEFOREDAYAMT_2                      \n");
        sb.append("        ,SUM(NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)) M170_RPBEFOREDAYAMT_3                      \n");
        sb.append("        ,SUM(NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)) M170_RPBEFOREDAYAMT_4                      \n");
        sb.append("        ,SUM(NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                                             \n");
        sb.append("        +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                                                 \n");
        sb.append("        +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                                                 \n");
        sb.append("        +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)) M170_RPBEFOREDAYAMT                            \n");
        sb.append("    FROM M170_JANECKJANG_T A                                                                                        \n");
        sb.append("        ,M360_ACCCODE_T B                                                                                           \n");
        sb.append("   WHERE A.M170_YEAR(+) = B.M360_YEAR                                                                               \n");
        sb.append("     AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                                                        \n");
        sb.append("     AND A.M170_ACCCODE(+) = B.M360_ACCCODE                                                                         \n");
        sb.append("     AND A.M170_DATE(+) = ?                                                                                         \n");
        sb.append("     AND B.M360_ACCGUBUN = 'E'                                                                                      \n");
        sb.append("     AND B.M360_ACCCODE IN ('16','17','18')                                                                         \n");
        sb.append("   GROUP BY M360_ACCGUBUN, DECODE(M360_ACCCODE,'16','1510','17','1510','18','1510')                                 \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }

  
    /* 증감내역 */
    public static CommonEntity getAddReduce(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M170_JEUNGGAMSEIPHANGMOK_1                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_2                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_3                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_4                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_5                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_6                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_7                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_8                                           \n");
        sb.append("       ,M170_JEUNGGAMSEIPHANGMOK_9                                           \n");
        sb.append("       ,'합 계' M170_JEUNGGAMSEIPHANGMOK_10                                  \n");    
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_1,0)) M170_JEUNGGAMSEIPAMT_1          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_2,0)) M170_JEUNGGAMSEIPAMT_2          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_3,0)) M170_JEUNGGAMSEIPAMT_3          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_4,0)) M170_JEUNGGAMSEIPAMT_4          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_5,0)) M170_JEUNGGAMSEIPAMT_5          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_6,0)) M170_JEUNGGAMSEIPAMT_6          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_7,0)) M170_JEUNGGAMSEIPAMT_7          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_8,0)) M170_JEUNGGAMSEIPAMT_8          \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSEIPAMT_9,0)) M170_JEUNGGAMSEIPAMT_9          \n");
        sb.append("       ,TRUNC((NVL(M170_JEUNGGAMSEIPAMT_1,0)                                 \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_2,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_3,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_4,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_5,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_6,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_7,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_8,0)                                        \n");
        sb.append("       +NVL(M170_JEUNGGAMSEIPAMT_9,0))) M170_JEUNGGAMSEIPAMT_10              \n"); 
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_1                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_2                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_3                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_4                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_5                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_6                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_7                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_8                                         \n");
        sb.append("       ,M170_JEUNGGAMSECHULHANGMOK_9                                         \n");
        sb.append("       ,'합 계' M170_JEUNGGAMSECHULHANGMOK_10                                \n");    
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_1,0)) M170_JEUNGGAMSECHULAMT_1      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_2,0)) M170_JEUNGGAMSECHULAMT_2      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_3,0)) M170_JEUNGGAMSECHULAMT_3      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_4,0)) M170_JEUNGGAMSECHULAMT_4      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_5,0)) M170_JEUNGGAMSECHULAMT_5      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_6,0)) M170_JEUNGGAMSECHULAMT_6      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_7,0)) M170_JEUNGGAMSECHULAMT_7      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_8,0)) M170_JEUNGGAMSECHULAMT_8      \n");
        sb.append("       ,TRUNC(NVL(M170_JEUNGGAMSECHULAMT_9,0)) M170_JEUNGGAMSECHULAMT_9      \n");
        sb.append("       ,TRUNC((NVL(M170_JEUNGGAMSECHULAMT_1,0)                               \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_2,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_3,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_4,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_5,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_6,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_7,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_8,0)                                      \n");
        sb.append("       +NVL(M170_JEUNGGAMSECHULAMT_9,0))) M170_JEUNGGAMSECHULAMT_10          \n");
        sb.append("       ,NVL(M170_JUHEANGORDINARYDEPOSIT,0) M170_JUHEANGORDINARYDEPOSIT       \n");
        sb.append("       ,NVL(M170_ILSANG_AMT, 0) M170_ILSANG_AMT                              \n");
        sb.append("   FROM M170_JANECKJANG_T                                                    \n");
        sb.append("  WHERE M170_ACCTYPE = 'A'                                                   \n");
        sb.append("    AND M170_ACCCODE = '11'                                                  \n");
        sb.append("    AND M170_PARTCODE = '00000'                                              \n");
        sb.append("    AND M170_DATE = ?                                                        \n");
        sb.append("    AND M170_YEAR = SUBSTR(?, 1, 4)                                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }


    /* 합계 */
    public static CommonEntity getTotalAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M170_OFFICIALDEPOSITJANAMT                                                                         \n");
        sb.append("       ,M170_CITYSPECIALDEPOSIT                                                                            \n");
        sb.append("       ,M170_ULSANSPECIALDEPOSIT                                                                           \n");
        sb.append("       ,M170_JEUNGGAMAMT                                                                                   \n");
        sb.append("       ,M170_CITYORDINARYDEPOSIT                                                                           \n");
        sb.append("       ,M170_ULSANORDINARYDEPOSIT                                                                          \n");
        sb.append("       ,M170_JUHEANGORDINARYDEPOSIT                                                                        \n");
        sb.append("       ,M170_DEPOSITJEUNGGAMAMT                                                                            \n");
        sb.append("       ,M170_RPJEUNGGAMAMT                                                                                 \n");
        sb.append("       ,(M170_OFFICIALDEPOSITJANAMT + M170_CITYSPECIALDEPOSIT + M170_JEUNGGAMAMT                           \n");
        sb.append("       + M170_DEPOSITJEUNGGAMAMT + M170_RPJEUNGGAMAMT + M170_CITYORDINARYDEPOSIT) SIGUMGO_TOT              \n");
        sb.append("       ,(M170_ULSANSPECIALDEPOSIT + M170_ULSANORDINARYDEPOSIT) ULSAN_TOT                                   \n");
        sb.append("   FROM (                                                                                                  \n");
        sb.append(" SELECT SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0) + NVL(M170_ILSANG_AMT,0)) M170_OFFICIALDEPOSITJANAMT         \n");
        sb.append("       ,SUM(NVL(M170_CITYSPECIALDEPOSIT,0)) M170_CITYSPECIALDEPOSIT                                        \n");
        sb.append("       ,SUM(NVL(M170_ULSANSPECIALDEPOSIT,0)) M170_ULSANSPECIALDEPOSIT                                      \n");
        sb.append("       ,SUM(NVL(M170_MMDABEFOREDAYJANAMT,0)+NVL(M170_JEUNGGAMAMT,0)) M170_JEUNGGAMAMT                      \n");
        sb.append("       ,SUM(NVL(M170_CITYORDINARYDEPOSIT,0)) M170_CITYORDINARYDEPOSIT                                      \n");
        sb.append("       ,SUM(NVL(M170_ULSANORDINARYDEPOSIT,0)) M170_ULSANORDINARYDEPOSIT                                    \n");
        sb.append("       ,SUM(NVL(M170_JUHEANGORDINARYDEPOSIT,0)) M170_JUHEANGORDINARYDEPOSIT                                \n");
        sb.append("       ,SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)                        \n");
        sb.append("       +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)                            \n");
        sb.append("       +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)                            \n");
        sb.append("       +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)) M170_DEPOSITJEUNGGAMAMT   \n");
        sb.append("       ,SUM(NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                                     \n");
        sb.append("       +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                                         \n");
        sb.append("       +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                                         \n");
        sb.append("       +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)) M170_RPJEUNGGAMAMT                     \n");
        sb.append("   FROM M170_JANECKJANG_T                                                                                  \n");
        sb.append("  WHERE M170_DATE = ?                                                                                      \n");
        sb.append("  )                                                                                                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }


    /* 전일대비 */
     public static List<CommonEntity> getDaebiList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" WITH TMPTABLE AS (                                                                                         \n");
        sb.append(" SELECT M360_ACCGUBUN                                                                                       \n");
        sb.append("       ,M360_ACCCODE                                                                                        \n");
        sb.append("       ,M360_ACCNAME                                                                                        \n");
        sb.append("       ,TODAY_OFFICIAL_AMT                                                                                  \n");
        sb.append("       ,TODAY_BYUL_AMT                                                                                      \n");
        sb.append("       ,TODAY_MMDA_AMT                                                                                      \n");
        sb.append("       ,TODAY_JEONGI_AMT                                                                                    \n");
        sb.append("       ,TODAY_HWAN_AMT                                                                                      \n");
        sb.append("       ,(TODAY_OFFICIAL_AMT + TODAY_MMDA_AMT                                                                \n");
        sb.append("       + TODAY_JEONGI_AMT + TODAY_HWAN_AMT) TODAY_TOTAL_AMT                                                 \n");
        sb.append("       ,YESTER_TOTAL_AMT                                                                                    \n");
        sb.append("       ,YESTER_BYUL_AMT                                                                                     \n");
        sb.append("   FROM (                                                                                                   \n");
        sb.append(" SELECT M170_DATE                                                                                           \n");
        sb.append("       ,DECODE(M360_ACCGUBUN, 'D', 'F', M360_ACCGUBUN) M360_ACCGUBUN                                        \n");
        sb.append("       ,CASE WHEN M360_ACCGUBUN = 'A'                                                                       \n");
        sb.append("             THEN '11'                                                                                      \n");
        sb.append("             WHEN M360_ACCGUBUN = 'D'                                                                       \n");
        sb.append("             THEN '10'                                                                                      \n");
        sb.append("             WHEN M360_ACCGUBUN = 'E'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '40'                                                                       \n");
        sb.append("             THEN '14'                                                                                      \n");
        sb.append("             WHEN M360_ACCGUBUN = 'B'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '06'                                                                       \n");
        sb.append("             THEN '05'                                                                                      \n");
        sb.append("             WHEN M360_ACCGUBUN = 'B'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '69'                                                                       \n");
        sb.append("             THEN '68'                                                                                      \n");
        sb.append("             ELSE M360_ACCCODE                                                                              \n");
        sb.append("         END M360_ACCCODE                                                                                   \n");
        sb.append("       ,CASE WHEN M360_ACCGUBUN = 'E'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '16'                                                                       \n");
        sb.append("             THEN '(생할보호)'                                                                              \n");
        sb.append("             WHEN M360_ACCGUBUN = 'E'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '17'                                                                       \n");
        sb.append("             THEN '(노인복지)'                                                                              \n");
        sb.append("             WHEN M360_ACCGUBUN = 'E'                                                                       \n");
        sb.append("              AND M360_ACCCODE = '18'                                                                       \n");
        sb.append("             THEN '(저출산)'                                                                                \n");
        sb.append("             ELSE M360_ACCNAME                                                                              \n");
        sb.append("         END M360_ACCNAME                                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0)                                                         \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_OFFICIAL_AMT                                                                             \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                                            \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_BYUL_AMT                                                                                 \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN (NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0))                               \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_MMDA_AMT                                                                                 \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN (NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)                  \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)                  \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)                  \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0))                 \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_JEONGI_AMT                                                                               \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN (NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                               \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                               \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                               \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0))                              \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_HWAN_AMT                                                                                 \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                                        \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0)                                                         \n");
        sb.append("                 +NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)                                 \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)                   \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)                   \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)                   \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)                   \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                                \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                                \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                                \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)                                \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END YESTER_TOTAL_AMT                                                                               \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                                        \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                                            \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END YESTER_BYUL_AMT                                                                                \n");
        sb.append("   FROM M170_JANECKJANG_T A                                                                                 \n");
        sb.append("       ,M360_ACCCODE_T B                                                                                    \n");
        sb.append("  WHERE A.M170_YEAR = B.M360_YEAR                                                                        \n");
        sb.append("    AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                                                 \n");
        sb.append("    AND A.M170_ACCCODE(+) = B.M360_ACCCODE                                                                  \n");
        sb.append("    AND A.M170_DATE(+) IN (GET_AGO_BUSINESSDAY(?), ?)                                                       \n");
        sb.append("    AND B.M360_ACCGUBUN IN ('A', 'B', 'D', 'E')                                                             \n");
        sb.append("    AND CASE                                                                                                \n");
        sb.append("        WHEN B.M360_ACCGUBUN = 'A' THEN '1'                                                                 \n");
        sb.append("        WHEN B.M360_ACCGUBUN = 'D' THEN '1'                                                                 \n");
        sb.append("        WHEN B.M360_ACCGUBUN = 'E' THEN '1'                                                                 \n");
        sb.append("        WHEN B.M360_ACCGUBUN = 'B' AND                                                                      \n");
        sb.append("             B.M360_ACCCODE NOT IN ('32', '33', '34', '35', '36') THEN '1'                                  \n");
        sb.append("        ELSE '0' END = '1'                                                                                  \n");
        sb.append("    )                                                                                                       \n");
        sb.append(" UNION ALL                                                                                                  \n");
        sb.append(" SELECT 'I' M360_ACCGUBUN                                                                                   \n");
        sb.append("       ,'11' M360_ACCCODE                                                                                   \n");
        sb.append("       ,'일상경비' M360_ACCNAME                                                                             \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN NVL(M170_ILSANG_AMT,0)                                                                    \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_OFFICIAL_AMT                                                                             \n");
        sb.append("       ,0 TODAY_BYUL_AMT                                                                                    \n");
        sb.append("       ,0 TODAY_MMDA_AMT                                                                                    \n");
        sb.append("       ,0 TODAY_JEONGI_AMT                                                                                  \n");
        sb.append("       ,0 TODAY_HWAN_AMT                                                                                    \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                             \n");
        sb.append("             THEN NVL(M170_ILSANG_AMT,0)                                                                    \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END TODAY_TOTAL_AMT                                                                                \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                                        \n");
        sb.append("             THEN NVL(M170_ILSANG_AMT,0)                                                                    \n");
        sb.append("             ELSE 0                                                                                         \n");
        sb.append("         END YESTER_TOTAL_AMT                                                                               \n");
        sb.append("       ,0 YESTER_BYUL_AMT                                                                                   \n");
        sb.append("   FROM M170_JANECKJANG_T                                                                                   \n");
        sb.append("  WHERE M170_DATE IN (GET_AGO_BUSINESSDAY(?), ?)                                                            \n");
        sb.append("    AND M170_ACCTYPE IN ('A', 'B', 'D', 'E')                                                                \n");
        sb.append("  )                                                                                                         \n");
        sb.append("  SELECT M360_ACCGUBUN                                                                                      \n");
        sb.append("        ,NVL(M360_ACCCODE, '00') M360_ACCCODE                                                               \n");
        sb.append("        ,CASE WHEN M360_ACCGUBUN = 'A'                                                                      \n");
        sb.append("               AND M360_ACCCODE IS NULL                                                                     \n");
        sb.append("              THEN '일반회계'                                                                               \n");
        sb.append("              WHEN M360_ACCGUBUN = 'B'                                                                      \n");
        sb.append("               AND M360_ACCCODE IS NULL                                                                     \n");
        sb.append("              THEN '특별회계'                                                                               \n");
        sb.append("              WHEN M360_ACCGUBUN = 'E'                                                                      \n");
        sb.append("               AND M360_ACCCODE IS NULL                                                                     \n");
        sb.append("              THEN '기금'                                                                                   \n");
        sb.append("              WHEN M360_ACCGUBUN = 'F'                                                                      \n");
        sb.append("               AND M360_ACCCODE IS NULL                                                                     \n");
        sb.append("              THEN '세입세출외'                                                                             \n");
        sb.append("              WHEN M360_ACCGUBUN IS NULL                                                                    \n");
        sb.append("               AND M360_ACCCODE IS NULL                                                                     \n");
        sb.append("              THEN '합 계'                                                                                  \n");
        sb.append("              ELSE MIN(M360_ACCNAME)                                                                        \n");
        sb.append("          END M360_ACCNAME                                                                                  \n");
        sb.append("        ,SUM(TODAY_OFFICIAL_AMT) TODAY_OFFICIAL_AMT                                                         \n");
        sb.append("        ,SUM(TODAY_BYUL_AMT) TODAY_BYUL_AMT                                                                 \n");
        sb.append("        ,SUM(TODAY_MMDA_AMT) TODAY_MMDA_AMT                                                                 \n");
        sb.append("        ,SUM(TODAY_JEONGI_AMT) TODAY_JEONGI_AMT                                                             \n");
        sb.append("        ,SUM(TODAY_HWAN_AMT) TODAY_HWAN_AMT                                                                 \n");
        sb.append("        ,SUM(TODAY_TOTAL_AMT) TODAY_TOTAL_AMT                                                               \n");
        sb.append("        ,SUM(YESTER_TOTAL_AMT) YESTER_TOTAL_AMT                                                             \n");
        sb.append("        ,SUM(YESTER_BYUL_AMT) YESTER_BYUL_AMT                                                               \n");
        sb.append("    FROM TMPTABLE                                                                                           \n");
        sb.append("   GROUP BY ROLLUP(M360_ACCGUBUN, M360_ACCCODE)                                                             \n");
        sb.append("   ORDER BY M360_ACCGUBUN, M360_ACCCODE                                                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getList(conn, parameters);
    }


    /* 사회복지(전일대비) */
    public static CommonEntity getSocialDaebi(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT 'E' M360_ACCGUBUN                                                                    \n");
        sb.append("       ,'1510' M360_ACCCODE                                                                  \n");
        sb.append("       ,'사회복지' M360_ACCNAME                                                              \n");
        sb.append("       ,SUM(TODAY_OFFICIAL_AMT) TODAY_OFFICIAL_AMT                                           \n");
        sb.append("       ,SUM(TODAY_BYUL_AMT) TODAY_BYUL_AMT                                                   \n");
        sb.append("       ,SUM(TODAY_MMDA_AMT) TODAY_MMDA_AMT                                                   \n");
        sb.append("       ,SUM(TODAY_JEONGI_AMT) TODAY_JEONGI_AMT                                               \n");
        sb.append("       ,SUM(TODAY_HWAN_AMT) TODAY_HWAN_AMT                                                   \n");
        sb.append("       ,SUM(TODAY_OFFICIAL_AMT + TODAY_MMDA_AMT                                              \n");
        sb.append("       + TODAY_JEONGI_AMT + TODAY_HWAN_AMT) TODAY_TOTAL_AMT                                  \n");
        sb.append("       ,SUM(YESTER_TOTAL_AMT) YESTER_TOTAL_AMT                                               \n");
        sb.append("       ,SUM(YESTER_BYUL_AMT) YESTER_BYUL_AMT                                                 \n");
        sb.append("   FROM (                                                                                    \n");
        sb.append(" SELECT M170_DATE                                                                            \n");
        sb.append("       ,M360_ACCGUBUN                                                                        \n");
        sb.append("       ,M360_ACCCODE                                                                         \n");
        sb.append("       ,M360_ACCNAME                                                                         \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                              \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0)                                          \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END TODAY_OFFICIAL_AMT                                                              \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                              \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                             \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END TODAY_BYUL_AMT                                                                  \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                              \n");
        sb.append("             THEN (NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0))                \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END TODAY_MMDA_AMT                                                                  \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                              \n");
        sb.append("             THEN (NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)   \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)   \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)   \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0))  \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END TODAY_JEONGI_AMT                                                                \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                              \n");
        sb.append("             THEN (NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0))               \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END TODAY_HWAN_AMT                                                                  \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                         \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0)                                          \n");
        sb.append("                 +NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)                  \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)    \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)    \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)    \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)    \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                 \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                 \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                 \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)                 \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END YESTER_TOTAL_AMT                                                                \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                         \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                             \n");
        sb.append("             ELSE 0                                                                          \n");
        sb.append("         END YESTER_BYUL_AMT                                                                 \n");
        sb.append("   FROM M170_JANECKJANG_T A                                                                  \n");
        sb.append("       ,M360_ACCCODE_T B                                                                     \n");
        sb.append("  WHERE A.M170_YEAR = B.M360_YEAR                                                         \n");
        sb.append("    AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                                  \n");
        sb.append("    AND A.M170_ACCCODE(+) = B.M360_ACCCODE                                                   \n");
        sb.append("    AND A.M170_DATE(+) IN (GET_AGO_BUSINESSDAY(?), ?)                                        \n");
        sb.append("    AND B.M360_ACCGUBUN = 'E'                                                                \n");
        sb.append("    AND B.M360_ACCCODE IN ('16','17','18')                                                   \n");
        sb.append("  )                                                                                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }



    /* 전일대비합계 */
    public static CommonEntity getDaebiTotal(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT SUM(TODAY_OFFICIAL_AMT) TODAY_OFFICIAL_AMT                                              \n");
        sb.append("       ,SUM(TODAY_BYUL_AMT) TODAY_BYUL_AMT                                                      \n");
        sb.append("       ,SUM(TODAY_MMDA_AMT) TODAY_MMDA_AMT                                                      \n");
        sb.append("       ,SUM(TODAY_JEONGI_AMT) TODAY_JEONGI_AMT                                                  \n");
        sb.append("       ,SUM(TODAY_HWAN_AMT) TODAY_HWAN_AMT                                                      \n");
        sb.append("       ,SUM(TODAY_BOTONG_AMT) TODAY_BOTONG_AMT                                                  \n");
        sb.append("       ,SUM(TODAY_OFFICIAL_AMT + TODAY_MMDA_AMT + TODAY_BYUL_AMT                                \n");    
        sb.append("       + TODAY_JEONGI_AMT + TODAY_HWAN_AMT + TODAY_BOTONG_AMT                                   \n");
        sb.append("       + TODAY_RUN_AMT) TODAY_TOTAL_AMT                                                         \n");
        sb.append("       ,SUM(YESTER_TOTAL_AMT) YESTER_TOTAL_AMT                                                  \n");
        sb.append("       ,SUM(YESTER_BYUL_AMT) YESTER_BYUL_AMT                                                    \n");
        sb.append("       ,SUM(TODAY_ULSAN_BYUL) TODAY_ULSAN_BYUL                                                  \n");
        sb.append("       ,SUM(TODAY_ULSAN_BOTONG) TODAY_ULSAN_BOTONG                                              \n");
        sb.append("       ,SUM(TODAY_RUN_AMT) TODAY_RUN_AMT                                                        \n");
        sb.append("       ,SUM(YESTER_RUN_AMT) YESTER_RUN_AMT                                                      \n");
        sb.append("       ,SUM(YESTER_ULSAN_BYUL) YESTER_ULSAN_BYUL                                                \n");
        sb.append("       ,SUM(TODAY_ULSAN_TOT) TODAY_ULSAN_TOT                                                    \n");
        sb.append("       ,SUM(YESTER_ULSAN_TOT) YESTER_ULSAN_TOT                                                  \n");
        sb.append("       ,SUM(TODAY_ILSANG_TOT) TODAY_ILSANG_TOT                                                  \n");
        sb.append("       ,SUM(YESTER_ILSANG_TOT) YESTER_ILSANG_TOT                                                \n");
        sb.append("   FROM (                                                                                       \n");
        sb.append(" SELECT CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0) + NVL(M170_ILSANG_AMT,0)                    \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_OFFICIAL_AMT                                                                 \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                                \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_BYUL_AMT                                                                     \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN (NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0))                   \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_MMDA_AMT                                                                     \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN (NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)      \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)      \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)      \n");
        sb.append("                  +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0))     \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_JEONGI_AMT                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN (NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                   \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                   \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                   \n");
        sb.append("                  +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0))                  \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_HWAN_AMT                                                                     \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_JUHEANGORDINARYDEPOSIT, 0)                                           \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_RUN_AMT                                                                      \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_CITYORDINARYDEPOSIT, 0)                                              \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_BOTONG_AMT                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_ULSANSPECIALDEPOSIT, 0)                                              \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_ULSAN_BYUL                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_ULSANORDINARYDEPOSIT, 0)                                             \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_ULSAN_BOTONG                                                                 \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_ULSANSPECIALDEPOSIT, 0) + NVL(M170_ULSANORDINARYDEPOSIT, 0)          \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_ULSAN_TOT                                                                    \n");
        sb.append("       ,CASE WHEN M170_DATE = ?                                                                 \n");
        sb.append("             THEN NVL(M170_ILSANG_AMT, 0)                                                       \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END TODAY_ILSANG_TOT                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_OFFICIALDEPOSITJANAMT,0) + NVL(M170_CITYSPECIALDEPOSIT,0)            \n");
        sb.append("                 +NVL(M170_MMDABEFOREDAYJANAMT,0) + NVL(M170_JEUNGGAMAMT,0)                     \n");
        sb.append("                 +NVL(M170_JUHEANGORDINARYDEPOSIT, 0) + NVL(M170_CITYORDINARYDEPOSIT,0)         \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0)       \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0)       \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0)       \n");
        sb.append("                 +NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0)       \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0)                    \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0)                    \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0)                    \n");
        sb.append("                 +NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0)                    \n");
        sb.append("                 +NVL(M170_ILSANG_AMT,0)                                                        \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_TOTAL_AMT                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_CITYSPECIALDEPOSIT,0)                                                \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_BYUL_AMT                                                                    \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_JUHEANGORDINARYDEPOSIT, 0)                                           \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_RUN_AMT                                                                     \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_ULSANORDINARYDEPOSIT, 0)                                             \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_BOTONG_AMT                                                                  \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_ULSANSPECIALDEPOSIT, 0)                                              \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_ULSAN_BYUL                                                                  \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_ULSANORDINARYDEPOSIT, 0)                                             \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_ULSAN_BOTONG                                                                \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_ULSANSPECIALDEPOSIT, 0) + NVL(M170_ULSANORDINARYDEPOSIT, 0)          \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_ULSAN_TOT                                                                   \n");
        sb.append("       ,CASE WHEN M170_DATE = GET_AGO_BUSINESSDAY(?)                                            \n");
        sb.append("             THEN NVL(M170_ILSANG_AMT, 0)                                                       \n");
        sb.append("             ELSE 0                                                                             \n");
        sb.append("         END YESTER_ILSANG_TOT                                                                  \n");
        sb.append("   FROM M170_JANECKJANG_T                                                                       \n");
        sb.append("  WHERE M170_DATE IN (GET_AGO_BUSINESSDAY(?), ?)                                                \n");
        sb.append("  )                                                                                             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }

}
