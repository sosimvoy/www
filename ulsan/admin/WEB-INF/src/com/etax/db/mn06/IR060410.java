/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060410.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > �ڱݿ�Ź������ȸ/�ϰ���
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060410 {
    /* �ڱݿ�Ź ������ȸ */
    public static List<CommonEntity> getBankRegisterList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT *                                                          \n");
        sb.append("   FROM (                                                          \n");
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                             \n");
        sb.append("        ,M120_DEPOSITTYPE                                          \n");
        sb.append("        ,DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��', 'G2', 'ȯ��ä' \n");
        sb.append("                                 ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
        sb.append("        ,M120_DEPOSITAMT                                           \n");
        sb.append("        ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
        sb.append("        ,' ' MMDA_NAME                                             \n");
        sb.append("        ,M120_DEPOSITSTATE                                         \n");
        sb.append("        ,DECODE(M120_DEPOSITSTATE, 'S1', '�䱸���', 'S2', '����'  \n");
        sb.append("                                 , 'S3', '�ϰ���') STATE_NAME    \n");
        sb.append("        ,M120_ACCOUNTNO                                            \n");
        sb.append("        ,M120_JWASUNO                                              \n");
        sb.append("    FROM M120_MONEYDEPOSIT_T A                                     \n");
        sb.append("        ,M150_MONEYMANAGEMENT_T B                                  \n");
        sb.append("   WHERE A.M120_YEAR = B.M150_YEAR                                 \n");
        sb.append("     AND A.M120_ACCOUNTNO = B.M150_ACCOUNTNO                       \n");
        sb.append("     AND B.M150_CANCELDATE IS NULL                                 \n");
        sb.append("     AND M120_DATE = ?                                             \n");
        sb.append("     AND M120_ACCGUBUN = ?                                         \n");
        sb.append("     AND M120_ACCCODE = ?                                          \n");
        sb.append("     AND M120_PARTCODE = ?                                         \n");
        sb.append("     AND M120_DEPOSITSTATE = 'S3'                                  \n");
        sb.append("     AND M120_MMDATYPE <> 'G5'                                     \n"); //ȸ�迬�� �̿� ���� ����. 2013.03.14 ������ �䱸����.
        sb.append("  UNION ALL                                                        \n");
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                             \n");
        sb.append("        ,M120_DEPOSITTYPE                                          \n");
        sb.append("        ,DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��', 'G2', 'ȯ��ä' \n");
        sb.append("                                 ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
        sb.append("        ,M120_DEPOSITAMT                                           \n");
        sb.append("        ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
        sb.append("        ,DECODE(M120_MMDATYPE, 'G1', '���ݿ���', 'G2', '���⿹��'  \n");
		sb.append("                ,'G3', 'ȯ��ä', 'G4', 'MMDA'                      \n");
        sb.append("                        ,'G5', 'ȸ�迬���̿�') MMDA_NAME           \n");
        sb.append("        ,M120_DEPOSITSTATE                                         \n");
        sb.append("        ,DECODE(M120_DEPOSITSTATE, 'S1', '�䱸���', 'S2', '����'  \n");
        sb.append("                                 , 'S3', '�ϰ���') STATE_NAME    \n");
        sb.append("        ,M120_ACCOUNTNO                                            \n");
        sb.append("        ,M120_JWASUNO                                              \n");
        sb.append("    FROM M120_MONEYDEPOSIT_T A                                     \n");
        sb.append("        ,M140_MMDAMASTER_T B                                       \n");
        sb.append("   WHERE A.M120_YEAR = B.M140_YEAR                                 \n");
        sb.append("     AND A.M120_ACCOUNTNO = B.M140_ACCOUNTNO                       \n");
        sb.append("     AND B.M140_JANAMT - M120_DEPOSITAMT >= 0                      \n");
        sb.append("     AND M120_DATE = ?                                             \n");
        sb.append("     AND M120_ACCGUBUN = ?                                         \n");
        sb.append("     AND M120_ACCCODE = ?                                          \n");
        sb.append("     AND M120_PARTCODE = ?                                         \n");
        sb.append("     AND M120_DEPOSITSTATE = 'S3'                                  \n");
        sb.append("     AND M120_MMDATYPE <> 'G5'                                     \n"); //ȸ�迬�� �̿� ���� ����. 2013.03.14 ������ �䱸����.
        sb.append("   )                                                               \n");
        sb.append("   ORDER BY M120_DEPOSITSTATE, M120_DEPOSITTYPE                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("reg_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00000");
        parameters.setString(idx++, paramInfo.getString("reg_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00000");

        return template.getList(conn, parameters);
    }

  /* MMDA Master ���Ǽ� */
  public static CommonEntity getMmdaCnt(Connection conn, String fis_year, String acct_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M140_SEQ           \n");
    sb.append("   FROM M140_MMDAMASTER_T  \n");
    sb.append("  WHERE M140_YEAR = ?      \n");
    sb.append("    AND M140_ACCOUNTNO = ? \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.getData(conn, parameters);
  }


	/* MMDA Master ���� */
  public static int deleteMmdaMaster(Connection conn, String fis_year, String acct_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M140_MMDAMASTER_T  \n");
    sb.append("  WHERE M140_YEAR = ?           \n");
    sb.append("    AND M140_ACCOUNTNO = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.delete(conn, parameters);
  }

  
	/* ���ϸ��� ���� üũ */
  public static CommonEntity getDailyCnt(Connection conn, String reg_date) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M210_PUBLICDEPOSITSTATE     \n");
		sb.append("       ,M210_ETCDEPOSITSTATE        \n");
    sb.append("   FROM M210_WORKEND_T              \n");
    sb.append("  WHERE M210_DATE = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, reg_date);

    return template.getData(conn, parameters);
  }

	/* �ϰ��� => ���� */
  public static int updateDeposit(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M120_MONEYDEPOSIT_T          \n");
		sb.append("    SET M120_ACCOUNTNO = NULL        \n");
		sb.append("       ,M120_JWASUNO = NULL          \n");
		sb.append("       ,M120_DEPOSITSTATE = 'S2'     \n");
		sb.append("       ,M120_LOGNO2 = NULL           \n");
    sb.append("  WHERE M120_SEQ = ?                 \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S3'     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, seq);

    return template.update(conn, parameters);
  }

	/* MMDA ���� */
  public static int deleteMMDA(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M160_MMDADETAILS_T   \n");
    sb.append("  WHERE M160_M120SEQ = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, seq);		

    return template.delete(conn, parameters);
  }

	/* ���⿹��, ȯ��ä ���� */
  public static int deleteHwan(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M150_MONEYMANAGEMENT_T  \n");
    sb.append("  WHERE M150_M120SEQ = ?             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, seq);

    return template.delete(conn, parameters);
  }


	/* MMDA Master ���� */
  public static int updateMmdaMaster(Connection conn, String fis_year, String acct_no, String inamt) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT - "+inamt+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ - 1                \n");
    sb.append("  WHERE M140_YEAR = ?                          \n");
    sb.append("    AND M140_ACCOUNTNO = ?                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);

    return template.update(conn, parameters);
  }
}
