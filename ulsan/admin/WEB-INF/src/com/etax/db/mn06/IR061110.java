/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061110.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출일계등록취소
******************************************************/
package com.etax.db.mn06;
import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;
public class IR061110 {
  
  /* 자금인출승인조회 */
  public static List<CommonEntity> getInchulPmsList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("  SELECT M130_YEAR                                    \n");
    sb.append("        ,M130_SEQ                                     \n");
    sb.append("        ,M130_DATE                                    \n");
    sb.append("        ,DECODE(M130_DEPOSITTYPE, 'G1', '정기예금',   \n");
    sb.append("                       'G2', '환매채',                \n");
    sb.append("                       'G3', 'MMDA') DEPOSIT_NAME     \n");
    sb.append("        ,M130_DEPOSITTYPE                             \n");
    sb.append("        ,DECODE(M130_MMDATYPE, 'G1', '공금예금',      \n");
    sb.append("                       'G2', '정기예금',              \n");
    sb.append("                       'G3', '환매채',                \n");
    sb.append("                       'G4', 'MMDA',                  \n");
    sb.append("                       'G5', '회계연도이월') MMDA_TYPE\n"); 
    sb.append("        ,M130_MMDATYPE                                \n");
    sb.append("        ,M130_ACCOUNTNO                               \n");
    sb.append("        ,M130_JWASUNO                                 \n");
    sb.append("        ,NVL(M130_JANAMT,0) M130_JANAMT               \n");
    sb.append("        ,NVL(M130_INCHULAMT,0) M130_INCHULAMT         \n");
    sb.append("        ,M130_INCHULDATE                              \n");
    sb.append("        ,M130_SINKYUDATE                              \n");
    sb.append("        ,M130_MANGIDATE                               \n");
    sb.append("        ,M130_DEPOSITDATE                             \n");
    sb.append("        ,NVL(M130_INTERESTRATE,0) M130_INTERESTRATE   \n");
    sb.append("        ,NVL(M130_CANCELINTEREST,0) M130_CANCELINTEREST \n");
    sb.append("        ,DECODE(M130_STATECODE, 'S1', '요구등록',     \n");
    sb.append("            'S2', '승인',                             \n");
    sb.append("            'S3', '일계등록') STATE_NAME              \n");
    sb.append("        ,M130_STATECODE                               \n");
    sb.append("        ,M130_M160SEQ                                 \n");
    sb.append("        ,M130_MMDA_CANCEL_YN                          \n");
    sb.append("    FROM M130_MONEYINCHUL_T                           \n");
    sb.append("   WHERE M130_DATE = ?                                \n");
    sb.append("     AND M130_ACCGUBUN = ?                            \n");
    sb.append("     AND M130_ACCCODE = ?                             \n");
    sb.append("     AND M130_PARTCODE = ?                            \n");
    sb.append("     AND M130_STATECODE = 'S3'                        \n");
    sb.append("   ORDER BY M130_ACCOUNTNO, M130_MANGIDATE, M130_SINKYUDATE   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");

    return template.getList(conn, parameters);
  }
	
  /* 자금인출 세정과승인 상태로 수정 */
  public static int updateDeposit(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M130_MONEYINCHUL_T           \n");
		sb.append("    SET M130_CANCELINTEREST = ''     \n");
		sb.append("       ,M130_INCHULDATE = ''         \n");
		sb.append("       ,M130_STATECODE = 'S2'        \n");
    sb.append("       ,M130_M160SEQ = ''            \n");
		sb.append("       ,M130_LOGNO3 = ?              \n");
    sb.append("  WHERE M130_SEQ = ?                 \n");
		sb.append("    AND M130_STATECODE = 'S3'        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, seq);

    return template.update(conn, parameters);
  }


  /* MMDA 마스터 수정 */
  public static int updateMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT + "+paramInfo.getLong("input_amt")+"  \n");   
    sb.append("       ,M140_REQAMT = M140_REQAMT + "+paramInfo.getLong("input_amt")+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ - 1                \n");
    sb.append("  WHERE M140_ACCOUNTNO = ?                     \n");
    sb.append("    AND M140_YEAR = ?                          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.update(conn, parameters);
  }

  /* MMDA 명세 삭제 */
  public static int deleteMMDADetail(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M160_MMDADETAILS_T  \n");
    sb.append("  WHERE M160_SEQ = ?             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("m160_seq"));
      
    return template.insert(conn, parameters);
  }


  /* 정기예금, 환매채 수정 - 해지일 제거 */
  public static int updateHwan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M150_MONEYMANAGEMENT_T  \n");
		sb.append("    SET M150_CANCELDATE = ''    \n");
    sb.append("       ,M150_INTEREST = ''      \n");
    sb.append("       ,M150_M130SEQ = ''       \n");
    sb.append("  WHERE M150_YEAR = ?           \n");
		sb.append("    AND M150_ACCOUNTNO = ?      \n");
    sb.append("    AND M150_JWASUNO = ?        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;   
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("jwasu_no"));

    return template.update(conn, parameters);
  }
}
