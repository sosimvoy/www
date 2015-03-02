/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050810.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �ڱݹ����������ȸ/���
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050810 {

  /* �ڱݹ��� ����� ��ȸ */
  public static List<CommonEntity> getSugiCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT M330_YEAR                                     \n");
    sb.append("      ,M330_SEQ                                      \n");
    sb.append("      ,M330_DATE                                     \n");
    sb.append("      ,M330_ACCGUBUN                                 \n");
    sb.append("      ,M330_ACCCODE                                  \n");
    sb.append("      ,M330_PARTCODE                                 \n");
    sb.append("      ,M330_ALLOTGUBUN                               \n");
    sb.append("      ,M330_TAMT                                     \n");
    sb.append("      ,DECODE(M330_ACCGUBUN, 'A', '�Ϲ�ȸ��',        \n");
    sb.append("      'B', 'Ư��ȸ��', 'E', '���') ACCGUBUN_NAME    \n");
    sb.append("      ,DECODE(M330_ALLOTGUBUN, '1', '����',          \n");
    sb.append("      '2', '�����', '3', '�����ݳ�',                \n");
    sb.append("      '4', '������ݳ�') ALLOTGUBUN_NAME             \n");
    sb.append("      ,B.M360_ACCNAME                                \n");
    sb.append("      ,C.M350_PARTNAME                               \n");
    sb.append("      ,A.M330_LOGNO                                  \n");
    sb.append("  FROM M330_MONEYALLOTMANUAL_T A                     \n");
    sb.append("      ,M360_ACCCODE_T B                              \n");
    sb.append("      ,M350_PARTCODE_T C                             \n");
    sb.append(" WHERE A.M330_YEAR = B.M360_YEAR                     \n");
    sb.append("   AND A.M330_YEAR = C.M350_YEAR(+)                  \n");
    sb.append("   AND A.M330_ACCGUBUN = B.M360_ACCGUBUN             \n");
    sb.append("   AND A.M330_ACCCODE = B.M360_ACCCODE               \n");
    sb.append("   AND A.M330_PARTCODE = C.M350_PARTCODE(+)          \n");
    sb.append("   AND A.M330_ACCGUBUN IN ('A', 'B', 'E')            \n");
    sb.append("   AND M330_YEAR = ?                                 \n");
    sb.append("   AND M330_DATE = ?                                 \n");
		sb.append(" ORDER BY M330_SEQ                                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }


	/* �ڱݹ�������� ��� */
  public static int deleteSugi(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M330_MONEYALLOTMANUAL_T  \n");
    sb.append("  WHERE M330_YEAR = ?                 \n");
    sb.append("    AND M330_DATE = ?	               \n");
    sb.append("    AND M330_SEQ = ?	                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   seq);

    return template.delete(conn, parameters);
  }


  /* �ڱݹ�������� ��� */
  public static int deleteSechul(Connection conn, CommonEntity paramInfo, String log_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M030_TAXOTHER_T  \n");
    sb.append("  WHERE M030_YEAR = ?         \n");
    sb.append("    AND M030_DATE = ?	       \n");
    sb.append("    AND M030_LOGNO = ?	       \n");
    sb.append("    AND M030_WORKTYPE = ?	   \n");
    sb.append("    AND M030_TRANSGUBUN = ?	 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("acc_date"));
		parameters.setString(idx++,   log_no);
    parameters.setString(idx++,   paramInfo.getString("work_log"));
		parameters.setString(idx++,   paramInfo.getString("trans_gubun"));

    return template.delete(conn, parameters);
  }
}