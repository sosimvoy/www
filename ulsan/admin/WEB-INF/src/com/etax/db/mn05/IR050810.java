/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050810.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 자금배정수기분조회/취소
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050810 {

  /* 자금배정 수기분 조회 */
  public static List<CommonEntity> getSugiCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M330_YEAR                                     \n");
    sb.append("      ,M330_SEQ                                      \n");
    sb.append("      ,M330_DATE                                     \n");
    sb.append("      ,M330_ACCGUBUN                                 \n");
    sb.append("      ,M330_ACCCODE                                  \n");
    sb.append("      ,M330_PARTCODE                                 \n");
    sb.append("      ,M330_ALLOTGUBUN                               \n");
    sb.append("      ,M330_TAMT                                     \n");
    sb.append("      ,DECODE(M330_ACCGUBUN, 'A', '일반회계',        \n");
    sb.append("      'B', '특별회계', 'E', '기금') ACCGUBUN_NAME    \n");
    sb.append("      ,DECODE(M330_ALLOTGUBUN, '1', '배정',          \n");
    sb.append("      '2', '재배정', '3', '배정반납',                \n");
    sb.append("      '4', '재배정반납') ALLOTGUBUN_NAME             \n");
    sb.append("      ,B.M360_ACCNAME                                \n");
    sb.append("      ,C.M350_PARTNAME                               \n");
    sb.append("      ,A.M330_LOGNO                                  \n");
    sb.append("  FROM M330_MONEYALLOTMANUAL_T A                     \n");
    sb.append("      ,M360_ACCCODE_T B                              \n");
    sb.append("      ,M350_PARTCODE_T C                             \n");
    sb.append(" WHERE A.M330_YEAR = B.M360_YEAR                     \n");
    sb.append("   AND A.M330_YEAR = C.M350_YEAR(+)                  \n");
    sb.append("   AND A.M330_ACCGUBUN = B.M360_ACCGUBUN             \n");
    sb.append("   AND A.M330_ACCCODE = B.M360_ACCCODE               \n");
    sb.append("   AND A.M330_PARTCODE = C.M350_PARTCODE(+)          \n");
    sb.append("   AND A.M330_ACCGUBUN IN ('A', 'B', 'E')            \n");
    sb.append("   AND M330_YEAR = ?                                 \n");
    sb.append("   AND M330_DATE = ?                                 \n");
		sb.append(" ORDER BY M330_SEQ                                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }


	/* 자금배정수기분 취소 */
  public static int deleteSugi(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M330_MONEYALLOTMANUAL_T  \n");
    sb.append("  WHERE M330_YEAR = ?                 \n");
    sb.append("    AND M330_DATE = ?	               \n");
    sb.append("    AND M330_SEQ = ?	                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   seq);

    return template.delete(conn, parameters);
  }


  /* 자금배정수기분 취소 */
  public static int deleteSechul(Connection conn, CommonEntity paramInfo, String log_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M030_TAXOTHER_T  \n");
    sb.append("  WHERE M030_YEAR = ?         \n");
    sb.append("    AND M030_DATE = ?	       \n");
    sb.append("    AND M030_LOGNO = ?	       \n");
    sb.append("    AND M030_WORKTYPE = ?	   \n");
    sb.append("    AND M030_TRANSGUBUN = ?	 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   log_no);
    parameters.setString(idx++,   paramInfo.getString("work_log"));
		parameters.setString(idx++,   paramInfo.getString("trans_gubun"));

    return template.delete(conn, parameters);
  }
}