/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출통지서조회
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061210 {
  /* 자금인출 조회 */
  public static List<CommonEntity> getInchulReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT DECODE(M130_DEPOSITTYPE, 'G1', '정기예금',   \n");
    sb.append("                        'G2', '환매채',              \n");
    sb.append("                        'G3', 'MMDA') DEPOSIT_NAME   \n");
    sb.append("       ,M130_ACCOUNTNO                               \n");
    sb.append("       ,M130_JWASUNO                                 \n");
    sb.append("       ,NVL(M130_JANAMT,0) M130_JANAMT               \n");
    sb.append("       ,NVL(M130_INCHULAMT,0) M130_INCHULAMT         \n");
    sb.append("       ,M130_INTERESTRATE                            \n");
    sb.append("       ,M130_DEPOSITDATE                             \n");
    sb.append("       ,M130_DEPOSITTYPE                             \n");
    sb.append("       ,M130_SINKYUDATE                              \n");
    sb.append("       ,M130_MANGIDATE                               \n");
    sb.append("       ,DECODE(M130_STATECODE, 'S1', '요구등록',     \n");
		sb.append("                       'S2', '승인',                 \n");
		sb.append("                       'S3', '일계등록') STATE_NAME  \n");
		sb.append("       ,M130_STATECODE                               \n");
    sb.append("       ,M130_YEAR                                    \n");
    sb.append("       ,M130_SEQ                                     \n");
    sb.append("       ,M130_DATE                                    \n");
		sb.append("       ,DECODE(M130_MMDATYPE, 'G1', '공금예금',      \n");
    sb.append("                      'G2', '정기예금',              \n");
    sb.append("                      'G3', '환매채',                \n");
    sb.append("                      'G4', 'MMDA',                  \n");
    sb.append("                      'G5', '회계연도이월') MMDA_NAME\n");
    sb.append("       ,M130_DOCUMENTNO                              \n");
        sb.append("       ,M130_MMDA_CANCEL_YN                         \n");
    sb.append("   FROM M130_MONEYINCHUL_T                           \n");
    sb.append("  WHERE M130_YEAR = ?                                \n");
		sb.append("    AND M130_DATE = ?                                \n");
    sb.append("    AND M130_ACCGUBUN = ?                            \n");
    sb.append("    AND M130_ACCCODE = ?                             \n");
    sb.append("    AND M130_PARTCODE = ?                            \n");
    sb.append("    AND M130_DOCUMENTNO IS NOT NULL                  \n");
		sb.append("  ORDER BY M130_MANGIDATE, M130_SINKYUDATE, M130_ACCOUNTNO  \n");

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

	/* 자금인출 총액 */
  public static CommonEntity getInchulTotAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT SUM(M130_INCHULAMT) TOT_AMT                    \n");
		sb.append("       ,NUM_TO_HANGUL(SUM(M130_INCHULAMT)) HANGUL_AMT  \n");
    sb.append("   FROM M130_MONEYINCHUL_T                             \n");
    sb.append("  WHERE M130_DOCUMENTNO = ?                            \n");
		sb.append("    AND M130_DATE = ?                                  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("doc_no"));
    parameters.setString(idx++, paramInfo.getString("reg_date"));

    return template.getData(conn, parameters);
  }


	/* 자금인출 통지서 팝업 */
  public static List<CommonEntity> getInchulReportView(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M130_DEPOSITTYPE                      \n");
    sb.append("       ,'계' DEPOSIT_NAME                                      \n");
    sb.append("       ,SUM(M130_INCHULAMT) M130_INCHULAMT                     \n");
    sb.append("       ,'' M130_INTERESTRATE                                   \n");
    sb.append("       ,TO_NUMBER('') M130_DEPOSITDATE                         \n");
    sb.append("       ,'' M130_SINKYUDATE                                     \n");
    sb.append("       ,SUM(NVL(M130_CANCELINTEREST, 0)) M130_CANCELINTEREST   \n");
    sb.append("   FROM M130_MONEYINCHUL_T                                     \n");
    sb.append("  WHERE M130_DATE = ?                                          \n");
    sb.append("    AND M130_DOCUMENTNO = ?                                    \n");
    sb.append(" UNION ALL                                                     \n");
    sb.append(" SELECT M130_DEPOSITTYPE                                       \n");
    sb.append("       ,DECODE(M130_DEPOSITTYPE, 'G1', '정기예금',             \n");
    sb.append("                        'G2', '환매채',                        \n");
    sb.append("                        'G3', '수시입출식예금') DEPOSIT_NAME   \n");
    sb.append("       ,SUM(M130_INCHULAMT) M130_INCHULAMT                     \n");
    sb.append("       ,MIN(M130_INTERESTRATE) M130_INTERESTRATE               \n");
    sb.append("       ,MIN(M130_DEPOSITDATE) M130_DEPOSITDATE                 \n");
    sb.append("       ,M130_SINKYUDATE                                        \n");
    sb.append("       ,SUM(M130_CANCELINTEREST)                               \n");
    sb.append("   FROM M130_MONEYINCHUL_T                                     \n");
    sb.append("  WHERE M130_DATE = ?                                          \n");
    sb.append("    AND M130_DOCUMENTNO = ?                                    \n");
    sb.append("  GROUP BY M130_SINKYUDATE, M130_DEPOSITTYPE                   \n");
    sb.append("  ORDER BY M130_DEPOSITTYPE                                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getList(conn, parameters);
  }


	/* 자금인출 내역 */
    public static List<CommonEntity> getInchulDetailList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M130_DEPOSITTYPE                                  \n");
        sb.append("       ,'계' DEPOSIT_NAME                                      \n");
        sb.append("       ,SUM(M130_INCHULAMT) M130_INCHULAMT                     \n");
        sb.append("       ,'' M130_INTERESTRATE                                   \n");
        sb.append("       ,TO_NUMBER('') M130_DEPOSITDATE                         \n");
        sb.append("       ,'' M130_SINKYUDATE                                     \n");
        sb.append("       ,SUM(NVL(M130_CANCELINTEREST, 0)) M130_CANCELINTEREST   \n");
        sb.append("       ,'' M130_MMDA_CANCEL_YN                                 \n");
        sb.append("   FROM M130_MONEYINCHUL_T                                     \n");
		sb.append("  WHERE M130_DATE = ?                                          \n");
        sb.append("    AND M130_DOCUMENTNO = ?                                    \n");
        sb.append(" UNION ALL                                                     \n");
        sb.append(" SELECT M130_DEPOSITTYPE                                       \n");
        sb.append("       ,DECODE(M130_DEPOSITTYPE, 'G1', '정기예금',             \n");
        sb.append("                        'G2', '환매채',                        \n");
        sb.append("                        'G3', '수시입출식예금') DEPOSIT_NAME   \n");
        sb.append("       ,M130_INCHULAMT M130_INCHULAMT                          \n");
        sb.append("       ,M130_INTERESTRATE M130_INTERESTRATE                    \n");
        sb.append("       ,M130_DEPOSITDATE M130_DEPOSITDATE                      \n");
        sb.append("       ,M130_SINKYUDATE                                        \n");
        sb.append("       ,M130_CANCELINTEREST                                    \n");
        sb.append("       ,M130_MMDA_CANCEL_YN                                    \n");
        sb.append("   FROM M130_MONEYINCHUL_T                                     \n");
		sb.append("  WHERE M130_DATE = ?                                          \n");
		sb.append("    AND M130_DOCUMENTNO = ?                                    \n");
        sb.append("  ORDER BY M130_DEPOSITTYPE, M130_SINKYUDATE                   \n");

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
