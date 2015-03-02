/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050610.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > ������ȸ
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050610 {
 
	/* ���¸�� ���¹�ȣ ����Ʈ�ڽ� */
	public static List<CommonEntity> getAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 
	  sb.append(" SELECT M300_ACCOUNTNO,       \n");
		sb.append("        M300_ACCNAME          \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T  \n");
		sb.append("  WHERE M300_YEAR = ?         \n");
		sb.append("    AND M300_BANKCODE = ?     \n");
    sb.append("    AND M300_STATECODE = 'S2' \n");
    sb.append("  ORDER BY M300_ACCOUNTNO     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,    paramInfo.getString("fis_year"));
    parameters.setString(idx++,    "039");

    return template.getList(conn, parameters);
  }


	/* ���¸�� ���¹�ȣ ����Ʈ�ڽ� */
	public static List<CommonEntity> getAcctList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
 
	  sb.append(" SELECT M300_ACCOUNTNO,       \n");
		sb.append("        M300_ACCNAME          \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T  \n");
		sb.append("  WHERE M300_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");
		sb.append("    AND M300_BANKCODE = '039' \n");
    sb.append("    AND M300_STATECODE = 'S2' \n");
    sb.append("  ORDER BY M300_ACCOUNTNO     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        
    return template.getList(conn);
  }
}
