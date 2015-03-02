/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060610.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금예탁(기금,특별회계)
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060610 {

	/* 회계종류 조회 */
  public static List<CommonEntity> getAcctList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT B.M360_ACCCODE, B.M360_ACCNAME     \n");
    sb.append("   FROM M390_USESEMOKCODE_T A              \n");
    sb.append("       ,M360_ACCCODE_T B                   \n");
    sb.append("  WHERE B.M360_YEAR = A.M390_YEAR          \n");
    sb.append("    AND B.M360_ACCCODE = A.M390_ACCCODE    \n");
    sb.append("    AND B.M360_ACCGUBUN = A.M390_ACCGUBUN  \n");
    sb.append("    AND A.M390_WORKGUBUN = '3'             \n");
    sb.append("    AND B.M360_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");
    sb.append("    AND A.M390_ACCGUBUN = 'B'              \n");
    sb.append("    AND A.M390_PARTCODE = '00000'          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getList(conn);
  }


  /* 부서코드 조회 */
  public static List<CommonEntity> getPartList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT B.M350_PARTCODE, B.M350_PARTNAME       \n");
    sb.append("   FROM M390_USESEMOKCODE_T A                  \n");
    sb.append("       ,M350_PARTCODE_T B                      \n");
    sb.append("  WHERE A.M390_YEAR = B.M350_YEAR              \n");
    sb.append("    AND A.M390_PARTCODE = B.M350_PARTCODE      \n");     
    sb.append("    AND A.M390_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");     
    sb.append("    AND A.M390_ACCGUBUN = 'B'                  \n");     
		sb.append("    AND A.M390_WORKGUBUN = '3'                 \n"); 
    sb.append("  GROUP BY B.M350_PARTCODE, B.M350_PARTNAME    \n");
		sb.append("  ORDER BY B.M350_PARTCODE                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getList(conn);
  }

  /* 회계종류 조회 */
  public static List<CommonEntity> getAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT B.M360_ACCCODE, B.M360_ACCNAME     \n");
    sb.append("   FROM M390_USESEMOKCODE_T A              \n");
    sb.append("       ,M360_ACCCODE_T B                   \n");
    sb.append("  WHERE B.M360_YEAR = A.M390_YEAR          \n");
    sb.append("    AND B.M360_ACCCODE = A.M390_ACCCODE    \n");
    sb.append("    AND B.M360_ACCGUBUN = A.M390_ACCGUBUN  \n");
    sb.append("    AND A.M390_WORKGUBUN = '3'             \n");
    sb.append("    AND B.M360_YEAR = ?                    \n");
    sb.append("    AND A.M390_ACCGUBUN = ?                \n");
    sb.append("    AND A.M390_PARTCODE = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_kind"));
    if ("".equals(paramInfo.getString("part_code")) ) {
      parameters.setString(idx++, "00000");
    } else {
      parameters.setString(idx++, paramInfo.getString("part_code"));
    }

    return template.getList(conn, parameters);
  }

   /* 부서코드 조회 */
  public static List<CommonEntity> getPartList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT B.M350_PARTCODE, B.M350_PARTNAME       \n");
    sb.append("   FROM M390_USESEMOKCODE_T A                  \n");
    sb.append("       ,M350_PARTCODE_T B                      \n");
    sb.append("  WHERE A.M390_YEAR = B.M350_YEAR              \n");
    sb.append("    AND A.M390_PARTCODE = B.M350_PARTCODE      \n");     
    sb.append("    AND A.M390_YEAR = ?                        \n");     
    sb.append("    AND A.M390_ACCGUBUN = ?                    \n");     
		sb.append("    AND A.M390_WORKGUBUN = '3'                 \n"); 
    sb.append("  GROUP BY B.M350_PARTCODE, B.M350_PARTNAME    \n");
		sb.append("  ORDER BY B.M350_PARTCODE                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_kind"));

    return template.getList(conn, parameters);
  }


	/* MMDA마스터 계좌 존재 여부 */
  public static CommonEntity getMMDAInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT COUNT(1) CNT         \n");
    sb.append("   FROM M140_MMDAMASTER_T    \n");
    sb.append("  WHERE M140_YEAR = ?        \n");
		sb.append("    AND M140_ACCOUNTNO = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));

    return template.getData(conn, parameters);
  }


	/* MMDA마스터 등록 */
  public static int insertMMDAMaster(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M140_MMDAMASTER_T                      \n");
		sb.append(" (M140_YEAR, M140_ACCOUNTNO, M140_ACCTYPE           \n");
		sb.append(" ,M140_ACCCODE, M140_PARTCODE, M140_DEPOSITTYPE     \n");
    sb.append(" ,M140_SEQ, M140_JANAMT )                           \n");
		sb.append(" VALUES                                             \n");
		sb.append("( ?, ?, ?                                           \n");
		sb.append(" ,?, ?, ?                                           \n");
    sb.append(" ,1, ?  )                                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("acct_kind"));
		parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("dep_list"));
		parameters.setString(idx++,    paramInfo.getString("dep_amt"));

    return template.insert(conn, parameters);
  }



	/* MMDA마스터 금액 및 명세 수정 */
  public static int updateMMDAMaster(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T             \n");
		sb.append("    SET M140_SEQ = M140_SEQ + 1       \n");
		sb.append("       ,M140_JANAMT = M140_JANAMT + ? \n");
		sb.append("  WHERE M140_YEAR = ?                 \n");
		sb.append("    AND M140_ACCOUNTNO = ?            \n");
	

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("dep_amt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));		

    return template.update(conn, parameters);
  }


  /* mmda명세 등록 */
  public static int insertMMDADetail(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M160_MMDADETAILS_T                             \n");
		sb.append(" (M160_ACCOUNTNO, M160_SEQ, M160_ACCTYPE                    \n");
		sb.append(" ,M160_ACCCODE, M160_TRANSACTIONDATE, M160_TRANSACTIONTYPE  \n");
    sb.append(" ,M160_MMDATYPE, M160_TRANSACTIONAMT, M160_DEPOSITDATE      \n");
    sb.append(" ,M160_INTERESTRATE, M160_M120SEQ, M160_LOGNO )             \n");
		sb.append(" VALUES                                                     \n");
		sb.append("( ?, M160_SEQ.NEXTVAL, ?                                    \n");
		sb.append(" ,?, ?, ?                                                   \n");
    sb.append(" ,?, ?, ?                                                   \n");
    sb.append(" ,?, ?, ? )                                                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("acct_kind"));
		parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("new_date"));
    parameters.setString(idx++, "1");  //예탁
    parameters.setString(idx++, paramInfo.getString("mmda_list"));
    parameters.setString(idx++, paramInfo.getString("dep_amt"));
    parameters.setString(idx++, paramInfo.getString("dep_due"));
    parameters.setString(idx++, paramInfo.getString("dep_rate"));
    parameters.setString(idx++, paramInfo.getString("m120_seq"));
		parameters.setString(idx++, paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }



	/* 정기예금, 환매채 등록 */
  public static int insertHwan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M150_MONEYMANAGEMENT_T                    \n");
		sb.append(" (M150_YEAR, M150_ACCOUNTNO, M150_JWASUNO,             \n");
		sb.append("  M150_ACCTYPE, M150_ACCCODE, M150_PARTCODE,           \n");
		sb.append("  M150_DEPOSITTYPE, M150_DEPOSITAMT, M150_SINKYUDATE,  \n");
		sb.append("  M150_MANGIDATE, M150_DEPOSITDATE, M150_INTERESTRATE, \n");
    sb.append("  M150_M120SEQ, M150_LOGNO )                           \n");
		sb.append(" VALUES                                                \n");
		sb.append(" (?, ?, ?                                              \n");
		sb.append(" ,?, ?, ?                                              \n"); 
		sb.append(" ,?, ?, ?                                              \n");
    sb.append(" ,?, ?, ?                                              \n");
		sb.append(" ,?, ? )                                               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("jwasu_no"));
		parameters.setString(idx++, paramInfo.getString("acct_kind"));
		parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("dep_list"));
		parameters.setString(idx++, paramInfo.getString("dep_amt"));
		parameters.setString(idx++, paramInfo.getString("new_date"));
		parameters.setString(idx++, paramInfo.getString("end_date"));
		parameters.setString(idx++, paramInfo.getString("dep_due"));
		parameters.setString(idx++, paramInfo.getString("dep_rate"));
    parameters.setString(idx++, paramInfo.getString("m120_seq"));
		parameters.setString(idx++, paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }


  /* 자금예탁 일련번호 */
  public static CommonEntity getYetakSeq(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M120_SEQ.NEXTVAL SEQNO FROM DUAL  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }


  /* 자금예탁 등록 */
  public static int insertYetak(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M120_MONEYDEPOSIT_T                          \n");
		sb.append(" (M120_YEAR, M120_SEQ, M120_DATE,                         \n");
		sb.append("  M120_ACCGUBUN, M120_ACCCODE, M120_PARTCODE,             \n");
		sb.append("  M120_DEPOSITTYPE, M120_MMDATYPE, M120_ACCOUNTNO,        \n");
		sb.append("  M120_JWASUNO, M120_DEPOSITAMT, M120_MANGIDATE,          \n");
    sb.append("  M120_DEPOSITDATE, M120_INTERESTRATE, M120_DEPOSITSTATE, \n");
    sb.append("  M120_LOGNO2 )                                           \n");
		sb.append(" VALUES                                                   \n");
		sb.append(" (?, ?, ?                                                 \n");
		sb.append(" ,?, ?, ?                                                 \n"); 
		sb.append(" ,?, ?, ?                                                 \n");
    sb.append(" ,?, ?, ?                                                 \n");
    sb.append(" ,?, ?, ?                                                 \n");
		sb.append(" ,? )                                                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("m120_seq"));
    parameters.setString(idx++, paramInfo.getString("new_date"));
		parameters.setString(idx++, paramInfo.getString("acct_kind"));
		parameters.setString(idx++, paramInfo.getString("acct_list"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("dep_list"));
    parameters.setString(idx++, paramInfo.getString("mmda_list"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("jwasu_no"));
		parameters.setString(idx++, paramInfo.getString("dep_amt"));
		parameters.setString(idx++, paramInfo.getString("end_date"));
		parameters.setString(idx++, paramInfo.getString("dep_due"));
		parameters.setString(idx++, paramInfo.getString("dep_rate"));
    parameters.setString(idx++, "S3");
		parameters.setString(idx++, paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }
}
