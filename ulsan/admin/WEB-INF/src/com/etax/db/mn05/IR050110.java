/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050110.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �ڱݹ�����ó���ܾ���ȸ
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050110 {
  /* �ڱݹ�����ó���ܾ���ȸ */
  public static CommonEntity getAcctData(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT SUM(CASE WHEN M100_ALLOTCODE = '0'                   \n");
    sb.append("                  AND M100_ALLOTGUBUN = '1'                  \n");
    sb.append("                 THEN M100_ALLOTAMT/1000                     \n");
    sb.append("                 ELSE 0                                      \n");
    sb.append("            END) M100_ALLOTAMT                               \n");
    sb.append("       ,SUM(CASE WHEN M100_ALLOTCODE = '0'                   \n");
    sb.append("                  AND M100_ALLOTGUBUN = '2'                  \n");
    sb.append("                 THEN M100_ALLOTAMT/1000                     \n");
    sb.append("                 ELSE 0                                      \n");
    sb.append("            END) M100_ALLOTAMT_N                             \n");
    sb.append("   FROM M100_MONEYALLOT_T                                    \n");
		sb.append("  WHERE M100_YEAR = ?                                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));

    return template.getData(conn, parameters);
  }


}
