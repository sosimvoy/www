/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR051410.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �׿������Լ������ȸ/���
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051410 {

	/* ȸ�����ȸ */
  public static List<CommonEntity> getSrpSugiCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT A.M240_YEARINTO                    \n");
    sb.append("       ,A.M240_DATE                        \n");
		sb.append("       ,A.M240_YEAROVER                    \n");
		sb.append("       ,A.M240_ACCTYPE                     \n");
		sb.append("       ,A.M240_ACCCODE                     \n");
		sb.append("       ,A.M240_SEMOKCODE                   \n");
		sb.append("       ,A.M240_PARTCODE                    \n");
		sb.append("       ,A.M240_AMT                         \n");
		sb.append("       ,A.M240_M010SEQ                     \n");
		sb.append("       ,A.M240_SEQ                         \n");
		sb.append("       ,B.M360_ACCNAME                     \n");
		sb.append("       ,C.M350_PARTNAME                    \n");
		sb.append("       ,DECODE(A.M240_ACCTYPE              \n");
		sb.append("       ,'A', '�Ϲ�ȸ��', 'B', 'Ư��ȸ��'   \n");
		sb.append("       ,'C', '�����Ư��ȸ��'              \n");
		sb.append("       ,'D', '���Լ��������', 'E', '���' \n");
		sb.append("        ) ACCTYPE_NAME                     \n");
    sb.append("   FROM M240_SURPLUSINTODETAILS_T A        \n");
    sb.append("       ,M360_ACCCODE_T B                   \n");
		sb.append("       ,M350_PARTCODE_T C                  \n");
    sb.append("  WHERE A.M240_YEARINTO = B.M360_YEAR      \n");
    sb.append("    AND A.M240_ACCTYPE = B.M360_ACCGUBUN   \n");
    sb.append("    AND A.M240_ACCCODE = B.M360_ACCCODE    \n");
		sb.append("    AND A.M240_YEARINTO = C.M350_YEAR      \n");
    sb.append("    AND A.M240_PARTCODE = C.M350_PARTCODE  \n");
    sb.append("    AND A.M240_YEARINTO = ?                \n");
    sb.append("    AND A.M240_DATE = ?                    \n");
    sb.append("    AND A.M240_ACCTYPE <> 'A'              \n");
    sb.append("  ORDER BY A.M240_SEQ                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }


	/* �׿������Լ���� ��� */
  public static int deleteSrpSugi(Connection conn, CommonEntity paramInfo, String m240_seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE                            \n");
    sb.append("   FROM M240_SURPLUSINTODETAILS_T  \n");
    sb.append("  WHERE M240_YEARINTO = ?          \n");
    sb.append("    AND M240_SEQ = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
    parameters.setString(idx++,  m240_seq);

    return template.delete(conn, parameters);
  }
  
	/* ���Լ���� ��� */
  public static int deleteSeipSugi(Connection conn, CommonEntity paramInfo, String m010_seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE                     \n");
    sb.append("   FROM M010_TAXIN_T        \n");
    sb.append("  WHERE M010_YEAR = ?       \n");
    sb.append("    AND M010_SEQ = ?        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
    parameters.setString(idx++,  m010_seq);

    return template.delete(conn, parameters);
  }
  
}
