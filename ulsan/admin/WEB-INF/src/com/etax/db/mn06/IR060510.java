/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060510.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금예탁통지서조회
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060510 {
  /* 자금예탁 조회 */
  public static List<CommonEntity> getBankDepReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT DECODE(M120_DEPOSITTYPE, 'G1', '정기예금',   \n");
    sb.append("                        'G2', '환매채',              \n");
    sb.append("                        'G3', 'MMDA') DEPOSIT_NAME   \n");
    sb.append("       ,NVL(M120_DEPOSITAMT,0) M120_DEPOSITAMT       \n");
    sb.append("       ,M120_INTERESTRATE                            \n");
    sb.append("       ,M120_DEPOSITDATE                             \n");
    sb.append("       ,M120_DEPOSITTYPE                             \n");
    sb.append("       ,M120_MANGIDATE                               \n");
    sb.append("       ,DECODE(M120_DEPOSITSTATE, 'S1', '요구등록',  \n");
		sb.append("                       'S2', '승인',                 \n");
		sb.append("                       'S3', '일계등록') STATE_NAME  \n");
		sb.append("       ,M120_DEPOSITSTATE                            \n");
    sb.append("       ,M120_YEAR                                    \n");
    sb.append("       ,M120_SEQ                                     \n");
    sb.append("       ,M120_DATE                                    \n");
		sb.append("       ,DECODE(M120_MMDATYPE, 'G1', '공금예금', 'G2', '정기예금'  \n");
		sb.append("                ,'G3', '환매채', 'G4', 'MMDA'                     \n");
    sb.append("                        ,'G5', '회계연도이월') MMDA_NAME          \n");
    sb.append("       ,M120_DOCUMENTNO                              \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                          \n");
    sb.append("  WHERE M120_YEAR = ?                                \n");
		sb.append("    AND M120_DATE = ?                                \n");
    sb.append("    AND M120_ACCGUBUN = ?                            \n");
    sb.append("    AND M120_ACCCODE = ?                             \n");
    sb.append("    AND M120_PARTCODE = ?                            \n");
    sb.append("    AND M120_DOCUMENTNO IS NOT NULL                  \n");
		sb.append("  ORDER BY M120_DEPOSITTYPE                          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");

    return template.getList(conn, parameters);
  }

	/* 자금예탁 총액 */
  public static CommonEntity getDepositTotAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT SUM(M120_DEPOSITAMT) TOT_AMT                       \n");
		sb.append("       ,NUM_TO_HANGUL(SUM(M120_DEPOSITAMT)) HANGUL_AMT     \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                \n");
    sb.append("  WHERE M120_DOCUMENTNO = ?                                \n");
		sb.append("    AND M120_DATE = ?                                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("doc_no"));
    parameters.setString(idx++, paramInfo.getString("reg_date"));

    return template.getData(conn, parameters);
  }


	/* 자금예탁 통지서 팝업 */
  public static List<CommonEntity> getBankDepReportView(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M120_DEPOSITTYPE                                  \n");
    sb.append("       ,'계' DEPOSIT_NAME                                      \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT         \n");
    sb.append("       ,'' M120_INTERESTRATE                                   \n");
    sb.append("       ,TO_NUMBER('') M120_DEPOSITDATE                         \n");
    sb.append("       ,'' M120_MANGIDATE                                      \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append(" UNION ALL                                                     \n");
    sb.append(" SELECT M120_DEPOSITTYPE                                       \n");
    sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금',             \n");
    sb.append("                        'G2', '환매채',                        \n");
    sb.append("                        'G3', '수시입출식예금') DEPOSIT_NAME   \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT                   \n");
    sb.append("       ,MIN(M120_INTERESTRATE) M120_INTERESTRATE               \n");
    sb.append("       ,MIN(M120_DEPOSITDATE) M120_DEPOSITDATE                 \n");
    sb.append("       ,M120_MANGIDATE                                         \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
		sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append("  GROUP BY M120_MANGIDATE, M120_DEPOSITTYPE                    \n");
    sb.append("  ORDER BY M120_DEPOSITTYPE                                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getList(conn, parameters);
  }


	/* 자금예탁 내역 */
  public static List<CommonEntity> getBankDepDetailList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M120_DEPOSITTYPE                                  \n");
    sb.append("       ,'계' DEPOSIT_NAME                                      \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT                   \n");
    sb.append("       ,'' M120_INTERESTRATE                                   \n");
    sb.append("       ,TO_NUMBER('') M120_DEPOSITDATE                         \n");
    sb.append("       ,'' M120_MANGIDATE                                      \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append(" UNION ALL                                                     \n");
    sb.append(" SELECT M120_DEPOSITTYPE                                       \n");
    sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금',             \n");
    sb.append("                        'G2', '환매채',                        \n");
    sb.append("                        'G3', '수시입출식예금') DEPOSIT_NAME   \n");
    sb.append("       ,M120_DEPOSITAMT M120_DEPOSITAMT                        \n");
    sb.append("       ,M120_INTERESTRATE                                      \n");
    sb.append("       ,M120_DEPOSITDATE                                       \n");
    sb.append("       ,M120_MANGIDATE                                         \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append("  ORDER BY M120_DEPOSITTYPE, M120_MANGIDATE                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getList(conn, parameters);
  }

  
  /* 자금(재)배정통지서- 담당자명 얻기 */
  public static CommonEntity getManagerName(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M180_TASKID               \n");
		sb.append("      ,M180_TASKNAME             \n");
		sb.append("      ,M180_MIDDLEID             \n");
		sb.append("      ,M180_MIDDLENAME           \n");
		sb.append("      ,M180_UPPERID              \n");
		sb.append("      ,M180_UPPERNAME            \n");
		sb.append("      ,M180_COOPERATIONID        \n");
		sb.append("      ,M180_COOPERATIONNAME      \n");
                sb.append("      ,M180_SIGNFINISHSTATE      \n");
    sb.append("  FROM M180_DOCUMENT_T           \n");
		sb.append(" WHERE M180_YEAR = ?             \n");
    sb.append("   AND M180_DOCUMENTNO = TO_NUMBER(SUBSTR(?, 5, 4)) \n");
		sb.append("   AND M180_DOCUMENTCODE = ?     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("doc_code"));

    return template.getData(conn, parameters);
  }
}
