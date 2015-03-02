/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR051210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 잉여금이입통지서조회
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051210 {

	/* 잉여금이입통지서조회 */
  public static List<CommonEntity> getSrpRepList(Connection conn, CommonEntity paramInfo) throws SQLException {
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
		sb.append("      ,M310_DOCUMENTNO                           \n");
    sb.append("      ,DECODE(M310_INTOSTATE, 'S1', '요구등록'   \n");
    sb.append("      ,'S2', '세정과승인', 'S3', '이입처리'      \n");
    sb.append("      ,'S4', '책임자승인', 'S5', '이입완료'      \n");
    sb.append("       ) M310_INTOSTATE_NAME                     \n");
		sb.append("      ,DECODE(M310_INTOCODE, '0', '미처리'       \n");
    sb.append("      ,'1', '정상처리', '9', '별단예금'          \n");
    sb.append("       ) M310_INTOCODE_NAME                      \n");
    sb.append("  FROM M310_SURPLUSINTO_T	                      \n");
    sb.append(" WHERE M310_YEARINTO = ?                         \n");
		sb.append("   AND M310_DATE = ?                             \n");
		sb.append("   AND M310_DOCUMENTNO IS NOT NULL               \n");
		sb.append(" ORDER BY M310_DOCUMENTNO                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("reg_date"));

    return template.getList(conn, parameters);
  }
  


	/* 잉여금이입통지서 */
  public static CommonEntity getSrpRepInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT MAX(M310_YEARINTO) M310_YEARINTO          \n");
    sb.append("      ,MAX(M310_DATE) M310_DATE                  \n");
    sb.append("      ,MAX(M310_YEAROVER) M310_YEAROVER          \n");
    sb.append("      ,SUM(M310_INTOTAMT) M310_INTOTAMT          \n");
		sb.append("      ,NUM_TO_HANGUL(SUM(M310_INTOTAMT)) HAN_AMT \n");
		sb.append("      ,M310_DOCUMENTNO                           \n");
    sb.append("  FROM M310_SURPLUSINTO_T	                      \n");
    sb.append(" WHERE M310_YEARINTO = ?                         \n");
		sb.append("   AND M310_DATE = ?                             \n");
		sb.append("   AND M310_DOCUMENTNO = ?                       \n");
		sb.append(" GROUP BY M310_DOCUMENTNO                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("reg_date"));
		parameters.setString(idx++,  paramInfo.getString("doc_no"));

    return template.getData(conn, parameters);
  }


	/* 잉여금이입통지서 담당자명*/
  public static CommonEntity getSrpRepManager(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M180_TASKNAME           \n");
    sb.append("      ,M180_MIDDLENAME         \n");
    sb.append("      ,M180_UPPERNAME          \n");
    sb.append("      ,M180_COOPERATIONNAME    \n");
		sb.append("      ,M180_MAKEDATE           \n");
    sb.append("      ,M180_MIDDLESIGNDATE     \n");
    sb.append("      ,M180_UPPERSIGNDATE      \n");
    sb.append("      ,M180_COOPERATIONDATE    \n");
    sb.append("      ,M180_SIGNFINISHSTATE    \n");
    sb.append("  FROM M180_DOCUMENT_T   	    \n");
    sb.append(" WHERE M180_YEAR = ?           \n");
		sb.append("   AND M180_DOCUMENTNO = TO_NUMBER(SUBSTR(?, 5, 4)) \n");
		sb.append("   AND M180_DOCUMENTCODE = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("doc_no"));
		parameters.setString(idx++,  "ED09");

    return template.getData(conn, parameters);
  }

}
