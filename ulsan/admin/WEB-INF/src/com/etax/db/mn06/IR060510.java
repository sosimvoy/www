/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060510.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > �ڱݿ�Ź��������ȸ
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060510 {
  /* �ڱݿ�Ź ��ȸ */
  public static List<CommonEntity> getBankDepReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��',   \n");
    sb.append("                        'G2', 'ȯ��ä',              \n");
    sb.append("                        'G3', 'MMDA') DEPOSIT_NAME   \n");
    sb.append("       ,NVL(M120_DEPOSITAMT,0) M120_DEPOSITAMT       \n");
    sb.append("       ,M120_INTERESTRATE                            \n");
    sb.append("       ,M120_DEPOSITDATE                             \n");
    sb.append("       ,M120_DEPOSITTYPE                             \n");
    sb.append("       ,M120_MANGIDATE                               \n");
    sb.append("       ,DECODE(M120_DEPOSITSTATE, 'S1', '�䱸���',  \n");
		sb.append("                       'S2', '����',                 \n");
		sb.append("                       'S3', '�ϰ���') STATE_NAME  \n");
		sb.append("       ,M120_DEPOSITSTATE                            \n");
    sb.append("       ,M120_YEAR                                    \n");
    sb.append("       ,M120_SEQ                                     \n");
    sb.append("       ,M120_DATE                                    \n");
		sb.append("       ,DECODE(M120_MMDATYPE, 'G1', '���ݿ���', 'G2', '���⿹��'  \n");
		sb.append("                ,'G3', 'ȯ��ä', 'G4', 'MMDA'                     \n");
    sb.append("                        ,'G5', 'ȸ�迬���̿�') MMDA_NAME          \n");
    sb.append("       ,M120_DOCUMENTNO                              \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                          \n");
    sb.append("  WHERE M120_YEAR = ?                                \n");
		sb.append("    AND M120_DATE = ?                                \n");
    sb.append("    AND M120_ACCGUBUN = ?                            \n");
    sb.append("    AND M120_ACCCODE = ?                             \n");
    sb.append("    AND M120_PARTCODE = ?                            \n");
    sb.append("    AND M120_DOCUMENTNO IS NOT NULL                  \n");
		sb.append("  ORDER BY M120_DEPOSITTYPE                          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");

    return template.getList(conn, parameters);
  }

	/* �ڱݿ�Ź �Ѿ� */
  public static CommonEntity getDepositTotAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT SUM(M120_DEPOSITAMT) TOT_AMT                       \n");
		sb.append("       ,NUM_TO_HANGUL(SUM(M120_DEPOSITAMT)) HANGUL_AMT     \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                \n");
    sb.append("  WHERE M120_DOCUMENTNO = ?                                \n");
		sb.append("    AND M120_DATE = ?                                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("doc_no"));
    parameters.setString(idx++, paramInfo.getString("reg_date"));

    return template.getData(conn, parameters);
  }


	/* �ڱݿ�Ź ������ �˾� */
  public static List<CommonEntity> getBankDepReportView(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M120_DEPOSITTYPE                                  \n");
    sb.append("       ,'��' DEPOSIT_NAME                                      \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT         \n");
    sb.append("       ,'' M120_INTERESTRATE                                   \n");
    sb.append("       ,TO_NUMBER('') M120_DEPOSITDATE                         \n");
    sb.append("       ,'' M120_MANGIDATE                                      \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append(" UNION ALL                                                     \n");
    sb.append(" SELECT M120_DEPOSITTYPE                                       \n");
    sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��',             \n");
    sb.append("                        'G2', 'ȯ��ä',                        \n");
    sb.append("                        'G3', '��������Ŀ���') DEPOSIT_NAME   \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT                   \n");
    sb.append("       ,MIN(M120_INTERESTRATE) M120_INTERESTRATE               \n");
    sb.append("       ,MIN(M120_DEPOSITDATE) M120_DEPOSITDATE                 \n");
    sb.append("       ,M120_MANGIDATE                                         \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
		sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append("  GROUP BY M120_MANGIDATE, M120_DEPOSITTYPE                    \n");
    sb.append("  ORDER BY M120_DEPOSITTYPE                                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getList(conn, parameters);
  }


	/* �ڱݿ�Ź ���� */
  public static List<CommonEntity> getBankDepDetailList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT 'G0' M120_DEPOSITTYPE                                  \n");
    sb.append("       ,'��' DEPOSIT_NAME                                      \n");
    sb.append("       ,SUM(M120_DEPOSITAMT) M120_DEPOSITAMT                   \n");
    sb.append("       ,'' M120_INTERESTRATE                                   \n");
    sb.append("       ,TO_NUMBER('') M120_DEPOSITDATE                         \n");
    sb.append("       ,'' M120_MANGIDATE                                      \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append(" UNION ALL                                                     \n");
    sb.append(" SELECT M120_DEPOSITTYPE                                       \n");
    sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��',             \n");
    sb.append("                        'G2', 'ȯ��ä',                        \n");
    sb.append("                        'G3', '��������Ŀ���') DEPOSIT_NAME   \n");
    sb.append("       ,M120_DEPOSITAMT M120_DEPOSITAMT                        \n");
    sb.append("       ,M120_INTERESTRATE                                      \n");
    sb.append("       ,M120_DEPOSITDATE                                       \n");
    sb.append("       ,M120_MANGIDATE                                         \n");
    sb.append("   FROM M120_MONEYDEPOSIT_T                                    \n");
		sb.append("  WHERE M120_DATE = ?                                          \n");
    sb.append("    AND M120_DOCUMENTNO = ?                                    \n");
    sb.append("  ORDER BY M120_DEPOSITTYPE, M120_MANGIDATE                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("reg_date"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getList(conn, parameters);
  }

  
  /* �ڱ�(��)����������- ����ڸ� ��� */
  public static CommonEntity getManagerName(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M180_TASKID               \n");
		sb.append("      ,M180_TASKNAME             \n");
		sb.append("      ,M180_MIDDLEID             \n");
		sb.append("      ,M180_MIDDLENAME           \n");
		sb.append("      ,M180_UPPERID              \n");
		sb.append("      ,M180_UPPERNAME            \n");
		sb.append("      ,M180_COOPERATIONID        \n");
		sb.append("      ,M180_COOPERATIONNAME      \n");
                sb.append("      ,M180_SIGNFINISHSTATE      \n");
    sb.append("  FROM M180_DOCUMENT_T           \n");
		sb.append(" WHERE M180_YEAR = ?             \n");
    sb.append("   AND M180_DOCUMENTNO = TO_NUMBER(SUBSTR(?, 5, 4)) \n");
		sb.append("   AND M180_DOCUMENTCODE = ?     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("doc_code"));

    return template.getData(conn, parameters);
  }
}
