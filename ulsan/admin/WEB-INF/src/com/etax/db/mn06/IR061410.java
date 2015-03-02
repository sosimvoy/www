/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061410.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출(기금,특별)취소
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061410 {

	/* 자금인출(기금,특별)취소 조회 */
  public static List<CommonEntity> getInchulSpDelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M140_YEAR FIS_YEAR                              \n");
    sb.append("       ,M140_ACCTYPE ACC_TYPE                           \n");
    sb.append("       ,M140_ACCCODE ACC_CODE                           \n");
    sb.append("       ,M140_PARTCODE PART_CODE                         \n");
    sb.append("       ,DECODE(M140_DEPOSITTYPE, 'G3', 'MMDA',          \n");
    sb.append("                      'G4', '융자금') DEPOSIT_NAME      \n");
    sb.append("       ,M140_DEPOSITTYPE DEPOSIT_TYPE                   \n");
    sb.append("       ,M140_ACCOUNTNO ACCT_NO                          \n");
    sb.append("       ,'' JWASU_NO                                     \n");
    sb.append("       ,M140_JANAMT DEP_AMT                             \n");
    sb.append("       ,'' DEP_PERIOD                                   \n");
    sb.append("       ,'' DEP_RATE                                     \n");
    sb.append("       ,'' NEW_DATE                                     \n");
    sb.append("       ,'' END_DATE                                     \n");
    sb.append("       ,M160_TRANSACTIONDATE CANCEL_DATE                \n");
    sb.append("       ,DECODE(M160_MMDATYPE, 'G1', '정기예금',         \n");
    sb.append("                'G2', '환매채', 'G3', 'MMDA'            \n");
    sb.append("              ) MMDA_NAME                               \n");
    sb.append("       ,M160_MMDATYPE MMDA_TYPE                         \n");
    sb.append("       ,NVL(M160_TRANSACTIONAMT,0) CANCEL_AMT           \n");
    sb.append("       ,NVL(M160_INTEREST,0) CANCEL_IJA                 \n");
    sb.append("       ,M160_SEQ SEQ                                    \n");
    sb.append("       ,M160_M130SEQ M130SEQ                            \n");
    sb.append("       ,M140_CANCEL_YN                                  \n");
    sb.append("   FROM M140_MMDAMASTER_T A                             \n");
    sb.append("       ,M160_MMDADETAILS_T B                            \n");
    sb.append("  WHERE A.M140_ACCOUNTNO = B.M160_ACCOUNTNO             \n");
    sb.append("    AND A.M140_ACCTYPE = B.M160_ACCTYPE                 \n");
    sb.append("    AND A.M140_ACCCODE = B.M160_ACCCODE                 \n");
    sb.append("    AND B.M160_TRANSACTIONTYPE = '2'                    \n");
    sb.append("    AND M140_YEAR = ?                                   \n");
    sb.append("    AND M140_ACCTYPE = ?                                \n");
    sb.append("    AND M140_ACCCODE = ?                                \n");
    sb.append("    AND M140_PARTCODE = ?                               \n");
    sb.append("    AND M160_TRANSACTIONDATE = ?                        \n");
    sb.append("   UNION ALL                                            \n");
    sb.append(" SELECT M150_YEAR FIS_YEAR                              \n");
    sb.append("       ,M150_ACCTYPE ACC_TYPE                           \n");
    sb.append("       ,M150_ACCCODE ACC_CODE                           \n");
    sb.append("       ,M150_PARTCODE PART_CODE                         \n");
    sb.append("       ,DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',      \n");
    sb.append("                       'G2', '환매채' ) DEPOSIT_NAME    \n"); 
    sb.append("       ,M150_DEPOSITTYPE DEPOSIT_TYPE                   \n"); 
    sb.append("       ,M150_ACCOUNTNO ACCT_NO                          \n");
    sb.append("       ,M150_JWASUNO JWASU_NO                           \n");
    sb.append("       ,M150_DEPOSITAMT DEP_AMT                         \n");
    sb.append("       ,M150_DEPOSITDATE DEP_PERIOD                     \n");
    sb.append("       ,M150_INTERESTRATE DEP_RATE                      \n");
    sb.append("       ,M150_SINKYUDATE NEW_DATE                        \n");
    sb.append("       ,M150_MANGIDATE END_DATE                         \n");
    sb.append("       ,M150_CANCELDATE CANCEL_DATE                     \n");
    sb.append("       ,'' MMDA_NAME                                    \n");
    sb.append("       ,'' MMDA_TYPE                                    \n");
    sb.append("       ,NVL(M150_DEPOSITAMT,0) CANCEL_AMT               \n");
    sb.append("       ,NVL(M150_INTEREST,0) CANCEL_IJA                 \n");
    sb.append("       ,TO_NUMBER('') SEQ                               \n");
    sb.append("       ,M150_M130SEQ M130SEQ                            \n");
    sb.append("       ,'' M140_CANCEL_YN                               \n");
    sb.append("   FROM M150_MONEYMANAGEMENT_T                          \n");
    sb.append("  WHERE M150_YEAR = ?                                   \n");
    sb.append("    AND M150_ACCTYPE = ?                                \n");
    sb.append("    AND M150_ACCCODE = ?                                \n");
    sb.append("    AND M150_PARTCODE = ?                               \n"); 
    sb.append("    AND M150_CANCELDATE = ?                             \n"); 
    sb.append("  ORDER BY END_DATE, NEW_DATE, ACCT_NO                  \n");
 
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_kind"));
    parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_kind"));
    parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }


    /* MMDA 마스터 수정 */
    public static int updateMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT + "+paramInfo.getLong("input_amt")+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ - 1                \n");
        sb.append("       ,M140_CANCEL_YN = 'N'                   \n");
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
    
    sb.append(" DELETE FROM M160_MMDADETAILS_T    \n");
		sb.append("  WHERE M160_ACCOUNTNO = ?         \n");
		sb.append("    AND M160_SEQ = ?               \n");
		sb.append("    AND M160_ACCTYPE = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("seq"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

    return template.delete(conn, parameters);
  }
    
    
  /* 인출명세 삭제 */
  public static int deleteInchul(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M130_MONEYINCHUL_T    \n");
		sb.append("  WHERE M130_SEQ = ?               \n");
    sb.append("    AND M130_ACCOUNTNO = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("m130_seq"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));

    return template.delete(conn, parameters);
  }


  /* 정기예금, 환매채 수정 - 해지일, 해지이자 제거 */
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
