/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR051210.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �׿���������������ȸ
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051210 {

	/* �׿���������������ȸ */
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
    sb.append("      ,DECODE(M310_INTOSTATE, 'S1', '�䱸���'   \n");
    sb.append("      ,'S2', '����������', 'S3', '����ó��'      \n");
    sb.append("      ,'S4', 'å���ڽ���', 'S5', '���ԿϷ�'      \n");
    sb.append("       ) M310_INTOSTATE_NAME                     \n");
		sb.append("      ,DECODE(M310_INTOCODE, '0', '��ó��'       \n");
    sb.append("      ,'1', '����ó��', '9', '���ܿ���'          \n");
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
  


	/* �׿������������� */
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


	/* �׿������������� ����ڸ�*/
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
