/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR051010.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �׿������Կ䱸��ȸ/���
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051010 {

	/* �׿���������ȸ */
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
    sb.append("      ,DECODE(M310_INTOSTATE, 'S1', '�䱸���'   \n");
    sb.append("      ,'S2', '����������', 'S3', '����ó��'      \n");
    sb.append("      ,'S4', 'å���ڽ���', 'S5', '���ԿϷ�'      \n");
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

	/* �׿������Կ䱸��� */
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
