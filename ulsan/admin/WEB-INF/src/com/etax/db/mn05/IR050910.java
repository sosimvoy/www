/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050910.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 잉여금이입요구등록
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050910 {

  /* 출금계좌 조회 */
  public static List<CommonEntity> getOutAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M300_BANKCODE            \n");
    sb.append("      ,M300_ACCOUNTNO           \n");
    sb.append("      ,M300_ACCNAME             \n");
    sb.append("  FROM M300_ACCOUNTMANAGE_T     \n");
    sb.append(" WHERE M300_ACCOUNTTYPE = '02'  \n");
    sb.append("   AND M300_STATECODE = 'S2'    \n");
    sb.append("   AND M300_PARTCODE = '00000'  \n");
		sb.append("   AND M300_ACCGUBUN = 'A'      \n");
    sb.append("   AND M300_ACCCODE = '11'      \n");
    sb.append("   AND M300_YEAR = ?            \n");
		sb.append(" ORDER BY M300_ACCNAME          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("last_year"));

    return template.getList(conn, parameters);
  }


	/* 입금계좌 조회 */
  public static List<CommonEntity> getInAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M300_BANKCODE            \n");
    sb.append("      ,M300_ACCOUNTNO           \n");
    sb.append("      ,M300_ACCNAME             \n");
    sb.append("  FROM M300_ACCOUNTMANAGE_T     \n");
    sb.append(" WHERE M300_ACCOUNTTYPE = '02'  \n");
    sb.append("   AND M300_STATECODE = 'S2'    \n");
    sb.append("   AND M300_PARTCODE = '00000'  \n");
		sb.append("   AND M300_ACCGUBUN = 'A'      \n");
    sb.append("   AND M300_ACCCODE = '11'      \n");
    sb.append("   AND M300_YEAR = ?            \n");
		sb.append(" ORDER BY M300_ACCNAME          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("this_year"));

    return template.getList(conn, parameters);
  }


	/* 잉여금이입요구등록 */
  public static int insertSurplus(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M310_SURPLUSINTO_T                              \n");
    sb.append(" ( M310_YEARINTO, M310_SEQ, M310_DATE                        \n");
		sb.append("  ,M310_YEAROVER, M310_ACCOUNTNOOVER, M310_ACCOUNTNOINTO     \n");
		sb.append("  ,M310_INTOTAMT, M310_INTOSTATE, M310_INTOCODE              \n");
		sb.append("  ,M310_LOGNO1 )                                             \n");
		sb.append(" VALUES( ?, M310_SEQ.NEXTVAL, TO_CHAR(SYSDATE, 'YYYYMMDD')   \n");
		sb.append("        ,?, ?, ?                                             \n");
		sb.append("        ,?, ?, ?                                             \n");
		sb.append("        ,? )                                                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("this_year"));
		parameters.setString(idx++,   paramInfo.getString("last_year"));
		parameters.setString(idx++,   paramInfo.getString("out_acct"));
		parameters.setString(idx++,   paramInfo.getString("in_acct"));
		parameters.setString(idx++,   paramInfo.getString("in_amt"));
		parameters.setString(idx++,   "S1");
		parameters.setString(idx++,   "0");
		parameters.setString(idx++,   paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }
}