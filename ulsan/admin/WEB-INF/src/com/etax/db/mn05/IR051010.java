/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR051010.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 잉여금이입요구조회/취소
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051010 {

	/* 잉여금이입조회 */
  public static List<CommonEntity> getSrpCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M310_YEARINTO	                            \n");
    sb.append("      ,M310_SEQ	                                \n");
    sb.append("      ,M310_DATE	                                \n");
    sb.append("      ,M310_YEAROVER	                            \n");
    sb.append("      ,M310_ACCOUNTNOOVER                        \n");
    sb.append("      ,M310_ACCOUNTNOINTO                        \n");
    sb.append("      ,M310_INTOTAMT	                            \n");
    sb.append("      ,M310_INTOSTATE                            \n");
    sb.append("      ,M310_INTOCODE                             \n");
    sb.append("      ,DECODE(M310_INTOSTATE, 'S1', '요구등록'   \n");
    sb.append("      ,'S2', '세정과승인', 'S3', '이입처리'      \n");
    sb.append("      ,'S4', '책임자승인', 'S5', '이입완료'      \n");
    sb.append("       ) M310_INTOSTATE_NAME                     \n");
    sb.append("  FROM M310_SURPLUSINTO_T	                      \n");
    sb.append(" WHERE M310_DATE = ?                             \n");
		sb.append(" ORDER BY M310_SEQ                               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++,  paramInfo.getString("reg_date"));

    return template.getList(conn, parameters);
  }

	/* 잉여금이입요구취소 */
  public static int deleteSurplus(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE                      \n");
    sb.append("   FROM M310_SURPLUSINTO_T   \n");
    sb.append("  WHERE M310_DATE = ?        \n");
    sb.append("    AND M310_SEQ = ?         \n");
		sb.append("    AND M310_INTOSTATE = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);
		parameters.setString(idx++,   "S1");

    return template.delete(conn, parameters);
  }
}
