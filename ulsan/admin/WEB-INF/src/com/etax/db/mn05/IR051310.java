/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR051310.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 잉여금이입수기분등록
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051310 {

	/* 회계명조회 */
  public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT A.M360_ACCCODE                     \n");
    sb.append("       ,A.M360_ACCNAME                     \n");
    sb.append("   FROM M360_ACCCODE_T A                   \n");
    sb.append("       ,M390_USESEMOKCODE_T B              \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR          \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN  \n");
    sb.append("    AND A.M360_ACCCODE = B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_YEAR = ?                    \n");
    sb.append("    AND A.M360_ACCGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                \n");
    sb.append("    AND B.M390_WORKGUBUN = '0'             \n");
		sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME  \n");
    sb.append("  ORDER BY A.M360_ACCCODE                  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("this_year"));
		parameters.setString(idx++,  "B");
		parameters.setString(idx++,  paramInfo.getString("dept_list"));

    return template.getList(conn, parameters);
  }


	/* 세목명조회 */
  public static CommonEntity getSemokInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M370_SEMOKCODE                 \n");
    sb.append("   FROM M370_SEMOKCODE_T               \n");
    sb.append("  WHERE M370_SEMOKNAME LIKE '%이월금%' \n");
    sb.append("    AND M370_ACCGUBUN = 'B'            \n");
    sb.append("    AND M370_WORKGUBUN = '0'           \n");
    sb.append("    AND M370_YEAR = ?                  \n");
    sb.append("    AND M370_ACCCODE = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("this_year"));
    parameters.setString(idx++,  paramInfo.getString("acc_list"));

    return template.getData(conn, parameters);
  }
  
	/* 부서명조회 */
  public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT A.M350_PARTCODE                      \n");
    sb.append("      ,A.M350_PARTNAME                      \n");
    sb.append("  FROM M350_PARTCODE_T A                    \n");
    sb.append("      ,M390_USESEMOKCODE_T B                \n");
    sb.append(" WHERE A.M350_YEAR = B.M390_YEAR            \n");
    sb.append("   AND A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("   AND A.M350_YEAR = ?                      \n");
    sb.append("   AND B.M390_ACCGUBUN = ?                  \n");
    sb.append("   AND B.M390_WORKGUBUN = '0'               \n");
    sb.append(" GROUP BY A.M350_PARTCODE, A.M350_PARTNAME  \n");
		sb.append(" ORDER BY A.M350_PARTCODE                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("this_year"));
    parameters.setString(idx++,  "B");

    return template.getList(conn, parameters);
  }
  
	/* M010_SEQ 얻기 */
  public static CommonEntity getSeqInfo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M010_SEQ.NEXTVAL M010_SEQ          \n");
    sb.append("   FROM DUAL                               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }


	/* 잉여금이입명세수기분등록 */
  public static int insertSrpSugi(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("INSERT INTO M240_SURPLUSINTODETAILS_T            \n");
    sb.append("( M240_YEARINTO, M240_SEQ, M240_DATE,            \n");
    sb.append("  M240_YEAROVER, M240_ACCTYPE, M240_ACCCODE,     \n");
    sb.append("  M240_SEMOKCODE, M240_PARTCODE, M240_AMT,       \n");
		sb.append("  M240_M010SEQ, M240_LOGNO, M240_TRANSGUBUN )    \n");
		sb.append("VALUES                                           \n");
		sb.append("( ?, M240_SEQ.NEXTVAL, ?,                        \n");
    sb.append("  ?, ?, ?,                                       \n");
    sb.append("  ?, ?, ?,                                       \n");
		sb.append("  ?, ?, ? )                                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++,  paramInfo.getString("this_year"));
		parameters.setString(idx++,  paramInfo.getString("acc_date"));
		parameters.setString(idx++,  paramInfo.getString("last_year"));
		parameters.setString(idx++,  paramInfo.getString("acc_gubun"));
		parameters.setString(idx++,  paramInfo.getString("acc_list"));
		parameters.setString(idx++,  paramInfo.getString("semok_cd"));
    parameters.setString(idx++,  paramInfo.getString("dept_list"));
		parameters.setString(idx++,  paramInfo.getString("in_amt"));
		parameters.setString(idx++,  paramInfo.getString("seq"));
		parameters.setString(idx++,  paramInfo.getString("log_no"));
		parameters.setString(idx++,  paramInfo.getString("trans_gubun"));

    return template.insert(conn, parameters);
  }


	/* 세입수기분등록 */
  public static int insertSeipSugi(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("INSERT INTO M010_TAXIN_T                         \n");
    sb.append("( M010_YEAR, M010_SEQ, M010_DATE,                \n");
    sb.append("  M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,    \n");
    sb.append("  M010_PARTCODE, M010_INTYPE, M010_AMT, M010_SUNAPGIGWANCODE, \n");
		sb.append("  M010_CNT, M010_LOGNO, M010_WORKTYPE, M010_TRANSGUBUN ) \n");
		sb.append("VALUES                                           \n");
		sb.append("( ?, ?, ?,                                       \n");
    sb.append("  ?, ?, ?,                                       \n");
    sb.append("  ?, ?, ?, ?,                                    \n");
		sb.append("  ?, ?, ?, ? )                                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++,  paramInfo.getString("this_year"));
		parameters.setString(idx++,  paramInfo.getString("seq"));
		parameters.setString(idx++,  paramInfo.getString("acc_date"));
		parameters.setString(idx++,  paramInfo.getString("acc_gubun"));
		parameters.setString(idx++,  paramInfo.getString("acc_list"));
		parameters.setString(idx++,  paramInfo.getString("semok_cd"));
    parameters.setString(idx++,  paramInfo.getString("dept_list"));
		parameters.setString(idx++,  "I1");
		parameters.setString(idx++,  paramInfo.getString("in_amt"));
    parameters.setString(idx++,  "110000");
		parameters.setString(idx++,  "1");
		parameters.setString(idx++,  paramInfo.getString("log_no"));
    parameters.setString(idx++,  paramInfo.getString("work_log"));
    parameters.setString(idx++,  paramInfo.getString("trans_gubun"));

    return template.insert(conn, parameters);
  }

}
