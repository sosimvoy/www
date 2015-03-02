/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050710.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 자금배정수기분등록
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050710 {
  /* 부서조회 */
  public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M350_PARTCODE                      \n");
    sb.append("       ,MAX(M350_PARTNAME) M350_PARTNAME   \n");
    sb.append("   FROM (                                  \n");
		if ("1".equals(paramInfo.getString("allot_kind")) || "3".equals(paramInfo.getString("allot_kind")) ) {
		
      sb.append(" SELECT '00000' M350_PARTCODE              \n");
      sb.append("       ,'본청' M350_PARTNAME               \n");
      sb.append("   FROM DUAL                               \n");
      sb.append("  UNION ALL                                \n");
	  }
    sb.append(" SELECT M350_PARTCODE                      \n");
    sb.append("       ,M350_PARTNAME                      \n");
    sb.append("   FROM M350_PARTCODE_T A                  \n");
    sb.append("       ,M390_USESEMOKCODE_T B              \n");
    sb.append("  WHERE A.M350_YEAR = B.M390_YEAR          \n");
    sb.append("    AND A.M350_PARTCODE= B.M390_PARTCODE   \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                \n");
    sb.append("    AND B.M390_WORKGUBUN = '2'             \n");
    if ("A".equals(paramInfo.getString("acc_type")) ) {
		  if ("1".equals(paramInfo.getString("allot_kind")) || "3".equals(paramInfo.getString("allot_kind")) ) {

        sb.append("    AND A.M350_REALLOTPARTYN = 'N'         \n");
	    } else if ("2".equals(paramInfo.getString("allot_kind")) || "4".equals(paramInfo.getString("allot_kind")) ) {

        sb.append("    AND A.M350_REALLOTPARTYN = 'Y'         \n");
      }
	  }
    sb.append(" ) GROUP BY M350_PARTCODE                  \n");
    sb.append(" ORDER BY M350_PARTCODE                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("acc_type"));

    return template.getList(conn, parameters);
  }

  /* 회계명 조회 */
  public static List<CommonEntity> getAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT A.M360_ACCGUBUN                      \n");
    sb.append("      ,A.M360_ACCCODE                       \n");
    sb.append("      ,A.M360_ACCNAME                       \n");
    sb.append("      ,C.M350_PARTCODE                      \n");
    sb.append("      ,C.M350_INSERTYN                      \n");
    sb.append("      ,M350_REALLOTPARTYN                   \n");
    sb.append("  FROM M360_ACCCODE_T A                     \n");
    sb.append("      ,M390_USESEMOKCODE_T B                \n");
    sb.append("      ,M350_PARTCODE_T C                    \n");
    sb.append(" WHERE A.M360_ACCCODE = B.M390_ACCCODE      \n");
    sb.append("   AND A.M360_ACCGUBUN = B.M390_ACCGUBUN    \n");
    sb.append("   AND C.M350_PARTCODE = B.M390_PARTCODE    \n");
    sb.append("   AND B.M390_WORKGUBUN = '2'               \n");
    sb.append("   AND A.M360_ACCGUBUN = ?                  \n");
    sb.append("   AND C.M350_PARTCODE = ?                  \n");
    if ("A".equals(paramInfo.getString("acc_type")) ) {
		  if ("1".equals(paramInfo.getString("allot_kind")) || "3".equals(paramInfo.getString("allot_kind")) ) {

        sb.append("    AND C.M350_REALLOTPARTYN = 'N'        \n");
	    } else if ("2".equals(paramInfo.getString("allot_kind")) || "4".equals(paramInfo.getString("allot_kind")) ) {

        sb.append("    AND C.M350_REALLOTPARTYN = 'Y'        \n");
	    }
    }
    sb.append(" GROUP BY M360_ACCGUBUN, M360_ACCCODE, M360_ACCNAME, M350_PARTCODE, M350_INSERTYN, M350_REALLOTPARTYN \n");
		sb.append(" ORDER BY A.M360_ACCCODE                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("acc_type"));
		parameters.setString(idx++,   paramInfo.getString("dept_kind"));

    return template.getList(conn, parameters);
  }


	/* 자금배정수기분 등록 */
  public static int insertSugi(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M330_MONEYALLOTMANUAL_T  \n");
    sb.append("      ( M330_YEAR	                   \n");
    sb.append("       ,M330_SEQ	                     \n");
    sb.append("       ,M330_DATE	                   \n");
    sb.append("       ,M330_ACCGUBUN	               \n");
    sb.append("       ,M330_ACCCODE	                 \n");
    sb.append("       ,M330_PARTCODE	               \n");
    sb.append("       ,M330_ORGACCCODE	             \n");
    sb.append("       ,M330_ORGPARTCODE              \n");
    sb.append("       ,M330_ALLOTGUBUN	             \n");
    sb.append("       ,M330_TAMT	                   \n");
    sb.append("       ,M330_LOGNO	)                  \n");
		sb.append(" VALUES                               \n");
		sb.append("      ( ?                             \n");
    sb.append("       ,M330_SEQ.NEXTVAL              \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,? )                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   paramInfo.getString("acc_type"));
    parameters.setString(idx++,   paramInfo.getString("acc_kind"));
		parameters.setString(idx++,   paramInfo.getString("dept_kind"));
    parameters.setString(idx++,   paramInfo.getString("t_acc_kind"));
		parameters.setString(idx++,   paramInfo.getString("t_dept_kind"));
		parameters.setString(idx++,   paramInfo.getString("allot_kind"));
		parameters.setString   (idx++,   paramInfo.getString("sugi_amt"));
		parameters.setString(idx++,   paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }

  /* 세출수기분으로 등록(재배정 - 특별회계) */
  public static int insertSechul1(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M030_TAXOTHER_T          \n");
    sb.append("      ( M030_YEAR	                   \n");
    sb.append("       ,M030_SEQ	                     \n");
    sb.append("       ,M030_DATE	                   \n");
    sb.append("       ,M030_ACCTYPE	                 \n");
    sb.append("       ,M030_ACCCODE	                 \n");
    sb.append("       ,M030_SEMOKCODE	               \n");
    sb.append("       ,M030_PARTCODE	               \n");
    sb.append("       ,M030_INTYPE                   \n");
    sb.append("       ,M030_AMT	                     \n");
    sb.append("       ,M030_LOGNO	                   \n");
    sb.append("       ,M030_WORKTYPE                 \n");
    sb.append("       ,M030_TRANSGUBUN	)            \n");
		sb.append(" VALUES                               \n");
		sb.append("      ( ?                             \n");
    sb.append("       ,M030_SEQ.NEXTVAL              \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,? )                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   paramInfo.getString("acc_type"));
    parameters.setString(idx++,   paramInfo.getString("acc_kind"));
    parameters.setString(idx++,   "1110100");
		parameters.setString(idx++,   paramInfo.getString("dept_kind"));
    parameters.setString(idx++,   "I1");
    parameters.setString   (idx++,   paramInfo.getString("sugi_amt"));
    parameters.setString(idx++,   paramInfo.getString("log_no"));
		parameters.setString(idx++,   paramInfo.getString("work_log"));
		parameters.setString(idx++,   paramInfo.getString("trans_gubun"));		

    return template.insert(conn, parameters);
  }



  /* 세출수기분으로 등록(재배정반납 ) */
  public static int insertSechul2(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M030_TAXOTHER_T          \n");
    sb.append("      ( M030_YEAR	                   \n");
    sb.append("       ,M030_SEQ	                     \n");
    sb.append("       ,M030_DATE	                   \n");
    sb.append("       ,M030_ACCTYPE	                 \n");
    sb.append("       ,M030_ACCCODE	                 \n");
    sb.append("       ,M030_SEMOKCODE	               \n");
    sb.append("       ,M030_PARTCODE	               \n");
    sb.append("       ,M030_INTYPE                   \n");
    sb.append("       ,M030_AMT	                     \n");
    sb.append("       ,M030_LOGNO	                   \n");
    sb.append("       ,M030_WORKTYPE                 \n");
    sb.append("       ,M030_TRANSGUBUN	)            \n");
		sb.append(" VALUES                               \n");
		sb.append("      ( ?                             \n");
    sb.append("       ,M030_SEQ.NEXTVAL              \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,?                             \n");
    sb.append("       ,? )                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   paramInfo.getString("acc_type"));
    parameters.setString(idx++,   paramInfo.getString("acc_kind"));
    parameters.setString(idx++,   "1110100");
		parameters.setString(idx++,   paramInfo.getString("dept_kind"));
    parameters.setString(idx++,   "I2");
    parameters.setString   (idx++,   paramInfo.getString("sugi_amt"));
    parameters.setString(idx++,   paramInfo.getString("log_no"));
		parameters.setString(idx++,   paramInfo.getString("work_log"));
		parameters.setString(idx++,   paramInfo.getString("trans_gubun"));

    return template.insert(conn, parameters);
  }
                                                 
}
