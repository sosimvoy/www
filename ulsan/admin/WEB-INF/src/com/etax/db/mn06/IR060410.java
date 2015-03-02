/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060410.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금예탁승인조회/일계등록
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060410 {
    /* 자금예탁 승인조회 */
    public static List<CommonEntity> getBankRegisterList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT *                                                          \n");
        sb.append("   FROM (                                                          \n");
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                             \n");
        sb.append("        ,M120_DEPOSITTYPE                                          \n");
        sb.append("        ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채' \n");
        sb.append("                                 ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
        sb.append("        ,M120_DEPOSITAMT                                           \n");
        sb.append("        ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
        sb.append("        ,' ' MMDA_NAME                                             \n");
        sb.append("        ,M120_DEPOSITSTATE                                         \n");
        sb.append("        ,DECODE(M120_DEPOSITSTATE, 'S1', '요구등록', 'S2', '승인'  \n");
        sb.append("                                 , 'S3', '일계등록') STATE_NAME    \n");
        sb.append("        ,M120_ACCOUNTNO                                            \n");
        sb.append("        ,M120_JWASUNO                                              \n");
        sb.append("    FROM M120_MONEYDEPOSIT_T A                                     \n");
        sb.append("        ,M150_MONEYMANAGEMENT_T B                                  \n");
        sb.append("   WHERE A.M120_YEAR = B.M150_YEAR                                 \n");
        sb.append("     AND A.M120_ACCOUNTNO = B.M150_ACCOUNTNO                       \n");
        sb.append("     AND B.M150_CANCELDATE IS NULL                                 \n");
        sb.append("     AND M120_DATE = ?                                             \n");
        sb.append("     AND M120_ACCGUBUN = ?                                         \n");
        sb.append("     AND M120_ACCCODE = ?                                          \n");
        sb.append("     AND M120_PARTCODE = ?                                         \n");
        sb.append("     AND M120_DEPOSITSTATE = 'S3'                                  \n");
        sb.append("     AND M120_MMDATYPE <> 'G5'                                     \n"); //회계연도 이월 건은 제외. 2013.03.14 세정과 요구사항.
        sb.append("  UNION ALL                                                        \n");
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                             \n");
        sb.append("        ,M120_DEPOSITTYPE                                          \n");
        sb.append("        ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채' \n");
        sb.append("                                 ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
        sb.append("        ,M120_DEPOSITAMT                                           \n");
        sb.append("        ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
        sb.append("        ,DECODE(M120_MMDATYPE, 'G1', '공금예금', 'G2', '정기예금'  \n");
		sb.append("                ,'G3', '환매채', 'G4', 'MMDA'                      \n");
        sb.append("                        ,'G5', '회계연도이월') MMDA_NAME           \n");
        sb.append("        ,M120_DEPOSITSTATE                                         \n");
        sb.append("        ,DECODE(M120_DEPOSITSTATE, 'S1', '요구등록', 'S2', '승인'  \n");
        sb.append("                                 , 'S3', '일계등록') STATE_NAME    \n");
        sb.append("        ,M120_ACCOUNTNO                                            \n");
        sb.append("        ,M120_JWASUNO                                              \n");
        sb.append("    FROM M120_MONEYDEPOSIT_T A                                     \n");
        sb.append("        ,M140_MMDAMASTER_T B                                       \n");
        sb.append("   WHERE A.M120_YEAR = B.M140_YEAR                                 \n");
        sb.append("     AND A.M120_ACCOUNTNO = B.M140_ACCOUNTNO                       \n");
        sb.append("     AND B.M140_JANAMT - M120_DEPOSITAMT >= 0                      \n");
        sb.append("     AND M120_DATE = ?                                             \n");
        sb.append("     AND M120_ACCGUBUN = ?                                         \n");
        sb.append("     AND M120_ACCCODE = ?                                          \n");
        sb.append("     AND M120_PARTCODE = ?                                         \n");
        sb.append("     AND M120_DEPOSITSTATE = 'S3'                                  \n");
        sb.append("     AND M120_MMDATYPE <> 'G5'                                     \n"); //회계연도 이월 건은 제외. 2013.03.14 세정과 요구사항.
        sb.append("   )                                                               \n");
        sb.append("   ORDER BY M120_DEPOSITSTATE, M120_DEPOSITTYPE                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("reg_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00000");
        parameters.setString(idx++, paramInfo.getString("reg_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00000");

        return template.getList(conn, parameters);
    }

  /* MMDA Master 명세건수 */
  public static CommonEntity getMmdaCnt(Connection conn, String fis_year, String acct_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M140_SEQ           \n");
    sb.append("   FROM M140_MMDAMASTER_T  \n");
    sb.append("  WHERE M140_YEAR = ?      \n");
    sb.append("    AND M140_ACCOUNTNO = ? \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.getData(conn, parameters);
  }


	/* MMDA Master 삭제 */
  public static int deleteMmdaMaster(Connection conn, String fis_year, String acct_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M140_MMDAMASTER_T  \n");
    sb.append("  WHERE M140_YEAR = ?           \n");
    sb.append("    AND M140_ACCOUNTNO = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.delete(conn, parameters);
  }

  
	/* 일일마감 여부 체크 */
  public static CommonEntity getDailyCnt(Connection conn, String reg_date) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M210_PUBLICDEPOSITSTATE     \n");
		sb.append("       ,M210_ETCDEPOSITSTATE        \n");
    sb.append("   FROM M210_WORKEND_T              \n");
    sb.append("  WHERE M210_DATE = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);

    return template.getData(conn, parameters);
  }

	/* 일계등록 => 승인 */
  public static int updateDeposit(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M120_MONEYDEPOSIT_T          \n");
		sb.append("    SET M120_ACCOUNTNO = NULL        \n");
		sb.append("       ,M120_JWASUNO = NULL          \n");
		sb.append("       ,M120_DEPOSITSTATE = 'S2'     \n");
		sb.append("       ,M120_LOGNO2 = NULL           \n");
    sb.append("  WHERE M120_SEQ = ?                 \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S3'     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, seq);

    return template.update(conn, parameters);
  }

	/* MMDA 삭제 */
  public static int deleteMMDA(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M160_MMDADETAILS_T   \n");
    sb.append("  WHERE M160_M120SEQ = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, seq);		

    return template.delete(conn, parameters);
  }

	/* 정기예금, 환매채 삭제 */
  public static int deleteHwan(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M150_MONEYMANAGEMENT_T  \n");
    sb.append("  WHERE M150_M120SEQ = ?             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, seq);

    return template.delete(conn, parameters);
  }


	/* MMDA Master 수정 */
  public static int updateMmdaMaster(Connection conn, String fis_year, String acct_no, String inamt) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT - "+inamt+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ - 1                \n");
    sb.append("  WHERE M140_YEAR = ?                          \n");
    sb.append("    AND M140_ACCOUNTNO = ?                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.update(conn, parameters);
  }
}
