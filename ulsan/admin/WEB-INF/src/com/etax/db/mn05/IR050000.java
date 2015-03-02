/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050000.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 공통
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050000 {

	/* 자금배정진행상태 */
    public static CommonEntity getAllotingStat(Connection conn) throws SQLException {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT COUNT(1) TOT_CNT                         \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) ALLOT_CNT                      \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S1'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) REQ_ALLOT_CNT                  \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S2'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) WORK_ALLOT_CNT                 \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S3'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) CITY_ALLOT_CNT                 \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S6'     \n");
        sb.append("                  AND M100_ALLOTCODE IN ('1', '9')\n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) BANK_ALLOTED_CNT               \n");
		sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '1'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S6'     \n");
        sb.append("                  AND M100_ALLOTCODE = '0'       \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) BANK_ALLOTING_CNT              \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) NOALLOT_CNT                    \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S1'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) REQ_NOALLOT_CNT                \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S2'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) WORK_NOALLOT_CNT               \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S3'     \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) CITY_NOALLOT_CNT               \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S6'     \n");
        sb.append("                  AND M100_ALLOTCODE IN ('1', '9')\n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) BANK_NOALLOTED_CNT             \n");
        sb.append("       ,SUM(CASE WHEN M100_ALLOTGUBUN = '2'      \n");
        sb.append("                  AND M100_ALLOTSTATE = 'S6'     \n");
        sb.append("                  AND M100_ALLOTCODE = '0'       \n");
        sb.append("                 THEN 1                          \n");
        sb.append("                 ELSE 0                          \n");
        sb.append("             END) BANK_NOALLOTING_CNT            \n");
        sb.append("   FROM M100_MONEYALLOT_T                        \n");
        sb.append("  WHERE M100_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");

        QueryTemplate template = new QueryTemplate(sb.toString());

        return template.getData(conn);
    }

	/* 일일마감여부 */
    public static CommonEntity getDailyEndWork(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT *                \n");
        sb.append("   FROM M210_WORKEND_T   \n");
        sb.append("  WHERE M210_YEAR = ?    \n");
        sb.append("    AND M210_DATE = ?    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));

        return template.getData(conn, parameters);
    }

   
	/* 지급전송후 취소 눌렀을 때 다시 원상복귀 */
    public static int errAllot(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T        \n");
        sb.append("    SET M100_OUT_REQ_YN = 'N'    \n");
		sb.append("       ,M100_OUT_REQ_TIME = ''   \n");
		sb.append("       ,M100_OUT_PRC_YN = 'N'    \n");
		sb.append("       ,M100_OUT_PRC_TIME = ''   \n");
		sb.append("       ,M100_OUT_BANKCD = ''     \n");
		sb.append("       ,M100_OUT_ACCOUNTNO = ''  \n");
		sb.append("       ,M100_OUT_REC_CD = ?      \n");
		sb.append("       ,M100_OUT_REC_CONT = ?    \n");
		sb.append("       ,M100_ALLOTSTATE = ?      \n");
        sb.append("  WHERE M100_YEAR = ?            \n");
		sb.append("    AND M100_DATE = ?            \n");
        sb.append("    AND M100_TRADE_NO = ?        \n");
		sb.append("    AND M100_ALLOTGUBUN = ?      \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("err_code"));
		parameters.setString(idx++, paramInfo.getString("err_cont"));
		parameters.setString(idx++, "S4");
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("allot_date"));
		parameters.setString(idx++, paramInfo.getString("send_no"));
		parameters.setString(idx++, paramInfo.getString("allot_type"));

        return template.update(conn, parameters);
    }


    /* 폐쇄기 체크(기타예금) */
    public static CommonEntity closingCheck1(Connection conn, String search_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT CASE WHEN SUBSTR(?, 5,8) >= 0101 \n");
        sb.append("              AND SUBSTR(?, 5,8) <= 0310 \n");
        sb.append("             THEN 'TRUE'                 \n");
        sb.append("             ELSE 'FALSE'                \n");
        sb.append("         END DATE_CHEK                   \n");
        sb.append("   FROM DUAL                             \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, search_date);

        return template.getData(conn, parameters);
    }


    /* 폐쇄기 체크(일반회계/특별회계) */
    public static CommonEntity ilbanCheck(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                            \n");
        sb.append("   FROM M320_COLSINGDATECONTROL_T    \n");
        sb.append("  WHERE M320_YEAR = ?                \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("last_year"));

        return template.getData(conn, parameters);
    }


    /* 직인 얻기 */
    public static CommonEntity getSealInfo(Connection conn) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M340_FNAME                \n");
        sb.append("  FROM M340_USERSEAL_T           \n");
		sb.append(" WHERE M340_CURRENTORGAN = '2'   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());

        return template.getData(conn);
    }
}