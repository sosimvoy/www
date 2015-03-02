/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060810.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출요구등록
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060810 {

	/* 자금인출요구등록조회 */
    public static List<CommonEntity> getBankOutList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append("SELECT LIST.*												                   \n");
		sb.append("  FROM (														                     \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN						             \n");
		sb.append("		       FROM (											                   \n");
        sb.append(" SELECT M140_YEAR FIS_YEAR                              \n");
        sb.append("        ,M140_ACCTYPE ACC_TYPE                          \n");
        sb.append("        ,M140_ACCCODE ACC_CODE                          \n");
        sb.append("        ,M140_PARTCODE PART_CODE                        \n");
        sb.append("        ,DECODE(M140_DEPOSITTYPE, 'G3', 'MMDA',         \n");
        sb.append("                       'G4', '융자금') DEPOSIT_NAME     \n");                                              
        sb.append("        ,M140_DEPOSITTYPE DEPOSIT_TYPE                  \n");            
        sb.append("        ,M140_ACCOUNTNO ACCT_NO                         \n");
        sb.append("        ,'' JWASU_NO                                    \n");
        sb.append("        ,M140_JANAMT DEP_AMT                            \n");
        sb.append("        ,'' DEP_PERIOD                                  \n");
        sb.append("        ,'' DEP_RATE                                    \n");
        sb.append("        ,'' NEW_DATE                                    \n");
        sb.append("        ,'' END_DATE                                    \n");
        sb.append("        ,M140_REQAMT                                    \n");
        sb.append("    FROM M140_MMDAMASTER_T                              \n");
        sb.append("   WHERE M140_YEAR = ?                                  \n");
        sb.append("     AND M140_ACCTYPE = 'A'                             \n");
        sb.append("     AND M140_ACCCODE = '11'                            \n");
        sb.append("     AND M140_PARTCODE = '00000'                        \n");
        sb.append("     AND M140_JANAMT >= 0                               \n");
        sb.append("     AND M140_JANAMT - NVL(M140_REQAMT,0) >= 0          \n");
        sb.append("     AND M140_DEPOSITTYPE = 'G3'                        \n");
        sb.append("     AND M140_CANCEL_YN = 'N'                           \n");
        sb.append("   UNION ALL                                            \n");
        sb.append("  SELECT M150_YEAR FIS_YEAR                             \n");
        sb.append("        ,M150_ACCTYPE ACC_TYPE                          \n");
        sb.append("        ,M150_ACCCODE ACC_CODE                          \n");
        sb.append("        ,M150_PARTCODE PART_CODE                        \n");
        sb.append("        ,DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',     \n");
        sb.append("                        'G2', '환매채' ) DEPOSIT_NAME   \n");
        sb.append("        ,M150_DEPOSITTYPE DEPOSIT_TYPE                  \n"); 
        sb.append("        ,M150_ACCOUNTNO ACCT_NO                         \n");
        sb.append("        ,M150_JWASUNO JWASU_NO                          \n");
        sb.append("        ,M150_DEPOSITAMT DEP_AMT                        \n");
        sb.append("        ,M150_DEPOSITDATE DEP_PERIOD                    \n");
        sb.append("        ,M150_INTERESTRATE DEP_RATE                     \n");
        sb.append("        ,M150_SINKYUDATE NEW_DATE                       \n");
        sb.append("        ,M150_MANGIDATE END_DATE                        \n");
        sb.append("        ,0 M140_REQAMT                                  \n");
        sb.append("    FROM M150_MONEYMANAGEMENT_T                         \n");
        sb.append("   WHERE M150_YEAR = ?                                  \n");
        sb.append("     AND M150_ACCTYPE = 'A'                             \n");
        sb.append("     AND M150_ACCCODE = '11'                            \n");
        sb.append("     AND M150_PARTCODE = '00000'                        \n"); 
        sb.append("     AND M150_CANCELDATE IS NULL                        \n");
        sb.append("     AND M150_REQ_YN = 'N'                              \n");
        sb.append("   ORDER BY ACCT_NO, END_DATE, NEW_DATE                 \n");
        sb.append("		            ) ORG										                 \n");
		sb.append("		      WHERE ROWNUM <= ?									             \n");
		sb.append("       ) LIST												                   \n");
		sb.append(" WHERE LIST.RN > ?											                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

    return template.getList(conn, parameters);
  }


  /* 자금인출요구등록조회 총카운트 */
  public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT COUNT(1) CNT                                    \n");
    sb.append("   FROM (                                               \n");
    sb.append(" SELECT 1                                               \n");
    sb.append("    FROM M160_MMDADETAILS_T A                           \n");
    sb.append("        ,M140_MMDAMASTER_T B                            \n");
    sb.append("   WHERE A.M160_ACCOUNTNO = B.M140_ACCOUNTNO            \n");
    sb.append("     AND B.M140_YEAR = ?                                \n");
    sb.append("     AND M140_ACCTYPE = 'A'                             \n");
    sb.append("     AND M140_ACCCODE = '11'                            \n");
    sb.append("     AND M140_PARTCODE = '00000'                        \n");
    sb.append("     AND M160_SEQ IN ( SELECT MAX(M160_SEQ)             \n");
    sb.append("                         FROM M160_MMDADETAILS_T        \n");
    sb.append("                        WHERE M160_ACCTYPE = 'A'        \n");
    sb.append("                        GROUP BY M160_ACCOUNTNO         \n");
    sb.append("                     )                                  \n");
    sb.append("     AND M140_JANAMT >= 0                               \n");
    sb.append("     AND M140_JANAMT - NVL(M140_REQAMT,0) >= 0          \n");
    sb.append("     AND M140_DEPOSITTYPE = 'G3'                        \n");
    sb.append("     AND M140_CANCEL_YN = 'N'                           \n");
    sb.append("   UNION ALL                                            \n");
    sb.append("  SELECT 1                                              \n");
    sb.append("    FROM M150_MONEYMANAGEMENT_T                         \n");
    sb.append("   WHERE M150_YEAR = ?                                  \n");
    sb.append("     AND M150_ACCTYPE = 'A'                             \n");
    sb.append("     AND M150_ACCCODE = '11'                            \n");
    sb.append("     AND M150_PARTCODE = '00000'                        \n"); 
    sb.append("     AND M150_CANCELDATE IS NULL                        \n");
    sb.append("     AND M150_REQ_YN = 'N'                              \n");
    sb.append("        )                                               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.getData(conn, parameters);
  }


	/* MMDA인출 등록 */
  public static int insertMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M130_MONEYINCHUL_T       \n");
    sb.append("      ( M130_YEAR                     \n");
    sb.append("       ,M130_SEQ                      \n");
    sb.append("       ,M130_DATE                     \n");
    sb.append("       ,M130_ACCGUBUN                 \n");
    sb.append("       ,M130_ACCCODE                  \n");
    sb.append("       ,M130_PARTCODE                 \n");
    sb.append("       ,M130_DEPOSITTYPE              \n");
    sb.append("       ,M130_MMDATYPE                 \n");
    sb.append("       ,M130_ACCOUNTNO                \n");
    sb.append("       ,M130_JANAMT                   \n");
    sb.append("       ,M130_INCHULAMT                \n");
    sb.append("       ,M130_STATECODE                \n");
    sb.append("       ,M130_MMDA_CANCEL_YN           \n");
    sb.append("       ,M130_LOGNO1 )                 \n");
    sb.append(" SELECT M140_YEAR                     \n");
    sb.append("       ,M130_SEQ.NEXTVAL              \n");
    sb.append("       ,TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,M140_DEPOSITTYPE              \n");
    sb.append("       ,?                             \n");
    sb.append("       ,M140_ACCOUNTNO                \n");
    sb.append("       ,M140_JANAMT                   \n");
    sb.append("       ,?                             \n");
    sb.append("       ,'S1'                          \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("   FROM M140_MMDAMASTER_T             \n");
    sb.append("  WHERE M140_YEAR = ?                 \n");
    sb.append("    AND M140_ACCOUNTNO = ?            \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;	
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");
    parameters.setString(idx++, paramInfo.getString("mmda_type"));
    parameters.setString(idx++, paramInfo.getString("input_amt"));
    parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
    parameters.setString(idx++, paramInfo.getString("log_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));	

    return template.insert(conn, parameters);
  }


  /* MMDA인출 요구액 수정 */
  public static int updateMMDAReqAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T             \n");
    sb.append("    SET M140_REQAMT =  M140_REQAMT + NVL(?, 0) \n");
    sb.append("      , M140_CANCEL_YN = ?            \n");
    sb.append("  WHERE M140_YEAR = ?                 \n");
    sb.append("    AND M140_ACCOUNTNO = ?            \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;	

    parameters.setString(idx++, paramInfo.getString("input_amt"));
    parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));	

    return template.update(conn, parameters);
  }


	/* 정기, 환매채 인출 등록 */
  public static int insertJeongHwan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M130_MONEYINCHUL_T       \n");
    sb.append("      ( M130_YEAR                     \n");
    sb.append("       ,M130_SEQ                      \n");
    sb.append("       ,M130_DATE                     \n");
    sb.append("       ,M130_ACCGUBUN                 \n");
    sb.append("       ,M130_ACCCODE                  \n");
    sb.append("       ,M130_PARTCODE                 \n");
    sb.append("       ,M130_DEPOSITTYPE              \n");
    sb.append("       ,M130_MMDATYPE                 \n");
    sb.append("       ,M130_ACCOUNTNO                \n");
    sb.append("       ,M130_JWASUNO                  \n");
    sb.append("       ,M130_JANAMT                   \n");
    sb.append("       ,M130_INCHULAMT                \n");
    sb.append("       ,M130_SINKYUDATE               \n");
    sb.append("       ,M130_MANGIDATE                \n");
    sb.append("       ,M130_DEPOSITDATE              \n");
    sb.append("       ,M130_INTERESTRATE             \n");
    sb.append("       ,M130_STATECODE                \n");
    sb.append("       ,M130_LOGNO1 )                 \n");
    sb.append(" SELECT M150_YEAR                     \n");
    sb.append("       ,M130_SEQ.NEXTVAL              \n");
    sb.append("       ,TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,M150_DEPOSITTYPE              \n");
    sb.append("       ,?                             \n");
    sb.append("       ,M150_ACCOUNTNO                \n");
    sb.append("       ,M150_JWASUNO                  \n");
    sb.append("       ,M150_DEPOSITAMT               \n");
    sb.append("       ,M150_DEPOSITAMT               \n");
    sb.append("       ,M150_SINKYUDATE               \n");
    sb.append("       ,M150_MANGIDATE                \n");
    sb.append("       ,M150_DEPOSITDATE              \n");
    sb.append("       ,M150_INTERESTRATE             \n");
    sb.append("       ,'S1'                          \n");
    sb.append("       ,?                             \n");
    sb.append("   FROM M150_MONEYMANAGEMENT_T        \n");
    sb.append("  WHERE M150_YEAR = ?                 \n");
    sb.append("    AND M150_ACCOUNTNO = ?            \n");
    sb.append("    AND M150_JWASUNO = ?              \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");
    parameters.setString(idx++, paramInfo.getString("mmda_type"));
    parameters.setString(idx++, paramInfo.getString("log_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));	
    parameters.setString(idx++, paramInfo.getString("jwasu_no"));	

    return template.insert(conn, parameters);
  }



  /* 정기, 환매채 인출 등록 */
  public static int updateReqYN(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M150_MONEYMANAGEMENT_T        \n");
    sb.append("    SET M150_REQ_YN = 'Y'             \n");
    sb.append("  WHERE M150_YEAR = ?                 \n");
    sb.append("    AND M150_ACCOUNTNO = ?            \n");
    sb.append("    AND M150_JWASUNO = ?              \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));	
    parameters.setString(idx++, paramInfo.getString("jwasu_no"));	

    return template.update(conn, parameters);
  }

}
