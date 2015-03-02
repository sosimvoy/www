/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060310.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금예탁승인조회/일계등록
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060310 {
  /* 자금예탁 승인조회 */
  public static List<CommonEntity> bankDepositList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                            \n");
		sb.append("       ,M120_DEPOSITTYPE                                          \n");
    sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채' \n");
		sb.append("                                ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
		sb.append("       ,M120_DEPOSITAMT                                           \n");
		sb.append("       ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
		sb.append("       ,DECODE(M120_MMDATYPE, 'G1', '공금예금', 'G2', '정기예금'  \n");
		sb.append("                ,'G3', '환매채', 'G4', 'MMDA'                     \n");
    sb.append("                        ,'G5', '회계연도이월') MMDA_NAME          \n");
		sb.append("       ,M120_DEPOSITSTATE                                         \n");
		sb.append("       ,DECODE(M120_DEPOSITSTATE, 'S1', '요구등록', 'S2', '승인'  \n");
		sb.append("                                , 'S3', '일계등록') STATE_NAME    \n");
		sb.append("       ,M120_ACCOUNTNO                                            \n");
		sb.append("       ,M120_JWASUNO                                              \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                       \n");
    sb.append("  WHERE M120_DATE = ?                                             \n");
    sb.append("    AND M120_ACCGUBUN = ?                                         \n");
    sb.append("    AND M120_ACCCODE = ?                                          \n");
    sb.append("    AND M120_PARTCODE = ?                                         \n");
    sb.append("    AND M120_DEPOSITSTATE = 'S2'                                  \n");
		sb.append("  ORDER BY M120_DEPOSITSTATE, M120_DEPOSITTYPE                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");

    return template.getList(conn, parameters);
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

	/* 자금예탁 일계등록 상태로 수정 */
  public static int updateDeposit(Connection conn, String seq, String acctNo, String jwasuNo, 
		CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M120_MONEYDEPOSIT_T          \n");
		sb.append("    SET M120_ACCOUNTNO = ?           \n");
		sb.append("       ,M120_JWASUNO = ?             \n");
		sb.append("       ,M120_DEPOSITSTATE = 'S3'     \n");
		sb.append("       ,M120_LOGNO2 = ?              \n");
    sb.append("  WHERE M120_SEQ = ?                 \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S2'     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, acctNo);
		parameters.setString(idx++, jwasuNo);
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, seq);

    return template.update(conn, parameters);
  }

	/* MMDA 계좌건수 */
  public static CommonEntity getMmdaCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT COUNT(1) CNT       \n");
    sb.append("   FROM M140_MMDAMASTER_T  \n");
    sb.append("  WHERE M140_ACCOUNTNO = ? \n");
    sb.append("    AND M140_YEAR = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.getData(conn, parameters);
  }

	/* MMDA 마스터 등록 */
  public static int insertMMDA(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M140_MMDAMASTER_T             \n");
		sb.append(" (M140_ACCOUNTNO, M140_SEQ, M140_ACCTYPE   \n");
		sb.append(" ,M140_ACCCODE, M140_YEAR, M140_JANAMT     \n");
    sb.append(" ,M140_PARTCODE, M140_DEPOSITTYPE )        \n");
		sb.append(" SELECT ?, 1, ?                            \n");
		sb.append("       ,?, M120_YEAR, M120_DEPOSITAMT      \n"); 
    sb.append("       ,?, ?                               \n");
		sb.append("   FROM M120_MONEYDEPOSIT_T                \n");
    sb.append("  WHERE M120_SEQ = ?                       \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S2'           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("deposit_type"));
		parameters.setString(idx++, seq);		

    return template.insert(conn, parameters);
  }


	/* MMDA 마스터 수정 */
  public static int updateMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT + "+paramInfo.getLong("input_amt")+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ + 1                \n");
    sb.append("  WHERE M140_ACCOUNTNO = ?                     \n");
    sb.append("    AND M140_YEAR = ?                          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.update(conn, parameters);
  }


	/* MMDA 명세 등록 */
  public static int insertMMDADetail(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M160_MMDADETAILS_T                              \n");
		sb.append(" (M160_ACCOUNTNO, M160_SEQ, M160_ACCTYPE                     \n");
		sb.append(" ,M160_ACCCODE, M160_TRANSACTIONDATE, M160_TRANSACTIONTYPE   \n");
		sb.append(" ,M160_MMDATYPE, M160_TRANSACTIONAMT, M160_DEPOSITDATE       \n");
		sb.append(" ,M160_INTERESTRATE, M160_LOGNO, M160_M120SEQ)               \n");
		sb.append(" SELECT ?, M160_SEQ.NEXTVAL, ?                               \n");
		sb.append("       ,?, M120_DATE, ?                                      \n"); 
		sb.append("       ,M120_MMDATYPE, M120_DEPOSITAMT, M120_DEPOSITDATE     \n");
		sb.append("       ,M120_INTERESTRATE, ?, M120_SEQ                       \n");
		sb.append("   FROM M120_MONEYDEPOSIT_T                                  \n");
    sb.append("  WHERE M120_SEQ = ?                                         \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S2'                             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, "1");
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, seq);		

    return template.insert(conn, parameters);
  }

	/* 정기예금, 환매채 등록 */
  public static int insertHwan(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M150_MONEYMANAGEMENT_T                   \n");
		sb.append(" (M150_ACCOUNTNO, M150_JWASUNO, M150_ACCTYPE          \n");
		sb.append(" ,M150_ACCCODE, M150_DEPOSITTYPE, M150_DEPOSITAMT     \n");
		sb.append(" ,M150_SINKYUDATE, M150_MANGIDATE, M150_DEPOSITDATE   \n");
		sb.append(" ,M150_INTERESTRATE, M150_LOGNO, M150_M120SEQ         \n");
		sb.append(" ,M150_YEAR, M150_PARTCODE)                           \n");
		sb.append(" SELECT ?, ?, ?                                       \n");
		sb.append("       ,'11', M120_DEPOSITTYPE, M120_DEPOSITAMT       \n"); 
		sb.append("       ,M120_DATE, M120_MANGIDATE, M120_DEPOSITDATE   \n");
		sb.append("       ,M120_INTERESTRATE, ?, M120_SEQ                \n");
		sb.append("       ,M120_YEAR, ?                                  \n");
		sb.append("   FROM M120_MONEYDEPOSIT_T                           \n");
    sb.append("  WHERE M120_SEQ = ?                                      \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S2'                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("jwasu_no"));
		parameters.setString(idx++, "A");
    parameters.setString(idx++, paramInfo.getString("log_no"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, seq);

    return template.insert(conn, parameters);
  }


	/* 잔액장 건수 */
  public static CommonEntity getJanakCnt(Connection conn, String reg_date, String fis_year) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT COUNT(1) CNT       \n");
    sb.append("   FROM M170_JANECKJANG_T  \n");
    sb.append("  WHERE M170_DATE = ?      \n");
		sb.append("    AND M170_ACCTYPE = ?   \n");
		sb.append("    AND M170_ACCCODE = ?   \n");
		sb.append("    AND M170_YEAR = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.getData(conn, parameters);
  }

  /* 익일 잔액계산(MMDA) */
  public static CommonEntity getLastAmt3(Connection conn, String reg_date, String fis_year) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT (NVL(M170_MMDABEFOREDAYJANAMT,0)   \n"); 
		sb.append("      + NVL(M170_JEUNGGAMAMT,0)) LAST_AMT  \n");
    sb.append("   FROM M170_JANECKJANG_T                  \n");
    sb.append("  WHERE M170_DATE = ?                      \n");
		sb.append("    AND M170_ACCTYPE = ?                   \n");
		sb.append("    AND M170_ACCCODE = ?                   \n");
		sb.append("    AND M170_YEAR = ?                      \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.getData(conn, parameters);
  }


	/* 익일 잔액계산(환매채) */
  public static CommonEntity getLastAmt2(Connection conn, String reg_date, String due_day, String fis_year) throws SQLException {
		String schStr = "";
		if (Integer.parseInt(due_day) >= 30 && Integer.parseInt(due_day) < 90) {
			schStr = "NVL(M170_RPBEFOREDAYAMT_1,0) + NVL(M170_RPJEUNGGAMAMT_1,0) ";
		} else if (Integer.parseInt(due_day) >= 90 && Integer.parseInt(due_day) < 120 ) {
			schStr = "NVL(M170_RPBEFOREDAYAMT_2,0) + NVL(M170_RPJEUNGGAMAMT_2,0) ";
		} else if (Integer.parseInt(due_day) >= 120 && Integer.parseInt(due_day) < 180 ) {
			schStr = "NVL(M170_RPBEFOREDAYAMT_3,0) + NVL(M170_RPJEUNGGAMAMT_3,0) ";
		} else if (Integer.parseInt(due_day) >= 180 ) {
			schStr = "NVL(M170_RPBEFOREDAYAMT_4,0) + NVL(M170_RPJEUNGGAMAMT_4,0) ";
		}

    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT " +schStr+ " LAST_AMT  \n");
    sb.append("   FROM M170_JANECKJANG_T      \n");
    sb.append("  WHERE M170_DATE = ?          \n");
		sb.append("    AND M170_ACCTYPE = ?       \n");
		sb.append("    AND M170_ACCCODE = ?       \n");
		sb.append("    AND M170_YEAR = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.getData(conn, parameters);
  }


	/* 익일 잔액계산(정기예금) */
  public static CommonEntity getLastAmt1(Connection conn, String reg_date, String due_day, String fis_year) throws SQLException {
		String schStr = "";
		if (Integer.parseInt(due_day) < 3 ) {
			schStr = "NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0) + NVL(M170_DEPOSITJEUNGGAMAMT_1,0) ";
		} else if (Integer.parseInt(due_day) >= 3 && Integer.parseInt(due_day) < 6 ) {
			schStr = "NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0) + NVL(M170_DEPOSITJEUNGGAMAMT_2,0) ";
		} else if (Integer.parseInt(due_day) >= 6 && Integer.parseInt(due_day) < 12 ) {
			schStr = "NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0) + NVL(M170_DEPOSITJEUNGGAMAMT_3,0) ";
		} else if (Integer.parseInt(due_day) >= 12 ) {
			schStr = "NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0) + NVL(M170_DEPOSITJEUNGGAMAMT_4,0) ";
		}

    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT " +schStr+ " LAST_AMT  \n");
    sb.append("   FROM M170_JANECKJANG_T      \n");
    sb.append("  WHERE M170_DATE = ?          \n");
		sb.append("    AND M170_ACCTYPE = ?       \n");
		sb.append("    AND M170_ACCCODE = ?       \n");
		sb.append("    AND M170_YEAR = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.getData(conn, parameters);
  }

	/* MMDA 당일증감액업뎃 */
  public static int updateIndeAmt3(Connection conn, String inamt, String reg_date, String fis_year) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T                               \n");
		sb.append("    SET M170_JEUNGGAMAMT = NVL(M170_JEUNGGAMAMT,0) + ?  \n");
    sb.append("  WHERE M170_DATE = ?                                   \n");
		sb.append("    AND M170_ACCTYPE = ?                                \n");
		sb.append("    AND M170_ACCCODE = ?                                \n");
		sb.append("    AND M170_YEAR = ?                                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, inamt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }


	/* 환매채 당일증감액업뎃 */
  public static int updateIndeAmt2(Connection conn, String inamt, String reg_date, String due_day, String fis_year) throws SQLException {
		String setStr = "";
		if (Integer.parseInt(due_day) >= 30 && Integer.parseInt(due_day) < 90) {
			setStr = "SET M170_RPJEUNGGAMAMT_1 = NVL(M170_RPJEUNGGAMAMT_1,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 90 && Integer.parseInt(due_day) < 120 ) {
			setStr = "SET M170_RPJEUNGGAMAMT_2 = NVL(M170_RPJEUNGGAMAMT_2,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 120 && Integer.parseInt(due_day) < 180 ) {
			setStr = "SET M170_RPJEUNGGAMAMT_3 = NVL(M170_RPJEUNGGAMAMT_3,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 180 ) {
			setStr = "SET M170_RPJEUNGGAMAMT_4 = NVL(M170_RPJEUNGGAMAMT_4,0) + ?  ";
		}

    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T   \n");
		sb.append("    " + setStr + "          \n");
    sb.append("  WHERE M170_DATE = ?       \n");
		sb.append("    AND M170_ACCTYPE = ?    \n");
		sb.append("    AND M170_ACCCODE = ?    \n");
		sb.append("    AND M170_YEAR = ?       \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, inamt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }


	/* 정기예금 당일증감액업뎃 */
  public static int updateIndeAmt1(Connection conn, String inamt, String reg_date, String due_day, String fis_year) throws SQLException {

		String setStr = "";
		if (Integer.parseInt(due_day) < 3 ) {
			setStr = "SET M170_DEPOSITJEUNGGAMAMT_1 = NVL(M170_DEPOSITJEUNGGAMAMT_1,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 3 && Integer.parseInt(due_day) < 6 ) {
			setStr = "SET M170_DEPOSITJEUNGGAMAMT_2 = NVL(M170_DEPOSITJEUNGGAMAMT_2,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 6 && Integer.parseInt(due_day) < 12 ) {
			setStr = "SET M170_DEPOSITJEUNGGAMAMT_3 = NVL(M170_DEPOSITJEUNGGAMAMT_3,0) + ?  ";
		} else if (Integer.parseInt(due_day) >= 12 ) {
			setStr = "SET M170_DEPOSITJEUNGGAMAMT_4 = NVL(M170_DEPOSITJEUNGGAMAMT_4,0) + ?  ";
		}

    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T     \n");
		sb.append("    " + setStr + "            \n");
    sb.append("  WHERE M170_DATE = ?         \n");
		sb.append("    AND M170_ACCTYPE = ?      \n");
		sb.append("    AND M170_ACCCODE = ?      \n");
		sb.append("    AND M170_YEAR = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, inamt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }



	/* MMDA 전일잔액 업뎃 */
  public static int updateLastAmt3(Connection conn, String last_amt, String reg_date, String fis_year) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T                        \n");
		sb.append("    SET M170_MMDABEFOREDAYJANAMT =  ?            \n");
    sb.append("  WHERE M170_DATE = ?                            \n");
		sb.append("    AND M170_ACCTYPE = ?                         \n");
		sb.append("    AND M170_ACCCODE = ?                         \n");
		sb.append("    AND M170_YEAR = ?                            \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, last_amt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }


	/* 환매채 전일잔액 업뎃 */
  public static int updateLastAmt2(Connection conn, String last_amt, String reg_date, String due_day, String fis_year) throws SQLException {
		String setStr = "";
		if (Integer.parseInt(due_day) >= 30 && Integer.parseInt(due_day) < 90) {
			setStr = "SET M170_RPBEFOREDAYAMT_1 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 90 && Integer.parseInt(due_day) < 120 ) {
			setStr = "SET M170_RPBEFOREDAYAMT_2 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 120 && Integer.parseInt(due_day) < 180 ) {
			setStr = "SET M170_RPBEFOREDAYAMT_3 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 180 ) {
			setStr = "SET M170_RPBEFOREDAYAMT_4 =  ?  ";
		}
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T     \n");
		sb.append("    " + setStr + "            \n");
    sb.append("  WHERE M170_DATE = ?         \n");
		sb.append("    AND M170_ACCTYPE = ?      \n");
		sb.append("    AND M170_ACCCODE = ?      \n");
		sb.append("    AND M170_YEAR = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, last_amt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }


	/* 정기예금 전일잔액 업뎃 */
  public static int updateLastAmt1(Connection conn, String last_amt, String reg_date, String due_day, String fis_year) throws SQLException {
		String setStr = "";
		if (Integer.parseInt(due_day) < 3 ) {
			setStr = "SET M170_DEPOSITBEFOREDAYJANAMT_1 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 3 && Integer.parseInt(due_day) < 6 ) {
			setStr = "SET M170_DEPOSITBEFOREDAYJANAMT_2 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 6 && Integer.parseInt(due_day) < 12 ) {
			setStr = "SET M170_DEPOSITBEFOREDAYJANAMT_3 =  ?  ";
		} else if (Integer.parseInt(due_day) >= 12 ) {
			setStr = "SET M170_DEPOSITBEFOREDAYJANAMT_4 =  ?  ";
		}
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M170_JANECKJANG_T     \n");
		sb.append("    " + setStr + "            \n");
    sb.append("  WHERE M170_DATE = ?         \n");
		sb.append("    AND M170_ACCTYPE = ?      \n");
		sb.append("    AND M170_ACCCODE = ?      \n");
		sb.append("    AND M170_YEAR = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, last_amt);
		parameters.setString(idx++, reg_date);
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, fis_year);

    return template.update(conn, parameters);
  }

}
