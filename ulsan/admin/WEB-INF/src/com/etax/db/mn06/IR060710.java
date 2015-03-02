/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060710.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > �ڱݿ�Ź(���,Ư��ȸ��)
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060710 {

	/* ���,Ư�� ��� �ڷ� ��ȸ */
  public static List<CommonEntity> getBankSpRegList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT B.M140_YEAR FIS_YEAR                          \n");
    sb.append("       ,DECODE(A.M160_ACCTYPE, 'B', 'Ư��ȸ��',       \n");
    sb.append("                       'D', '���Լ��������',         \n");
    sb.append("                       'E', '���') TYPE_NAME         \n");
    sb.append("       ,A.M160_ACCTYPE ACC_TYPE                       \n");
    sb.append("       ,C.M360_ACCNAME ACC_NAME                       \n");
    sb.append("       ,B.M140_ACCCODE ACC_CODE                       \n");
    sb.append("       ,DECODE(B.M140_DEPOSITTYPE, 'G3', 'MMDA',      \n");
    sb.append("                      'G4', '���ڱ�') DEPOSIT_NAME    \n");
    sb.append("       ,B.M140_DEPOSITTYPE DEPOSIT_TYPE               \n");
    sb.append("       ,DECODE(A.M160_MMDATYPE, 'G1', '���ݿ���',     \n");
    sb.append("                       'G2', '���⿹��',              \n");
    sb.append("                       'G3', 'ȯ��ä',                \n");
    sb.append("                       'G4', 'MMDA',                  \n");
    sb.append("                    'G5', 'ȸ�迬���̿�') MMDA_NAME   \n");
    sb.append("       ,A.M160_ACCOUNTNO ACCT_NO                      \n");
    sb.append("       ,'' JWASU_NO                                   \n");
    sb.append("       ,A.M160_TRANSACTIONAMT DEP_AMT                 \n");
    sb.append("       ,A.M160_DEPOSITDATE DEP_PERIOD                 \n");
    sb.append("       ,A.M160_INTERESTRATE DEP_RATE                  \n");
    sb.append("       ,A.M160_TRANSACTIONDATE NEW_DATE               \n");
    sb.append("       ,'' END_DATE                                   \n");
    sb.append("       ,A.M160_SEQ SEQ                                \n");
    sb.append("       ,A.M160_M120SEQ M120SEQ                        \n");
    sb.append("   FROM M160_MMDADETAILS_T A                          \n");
    sb.append("       ,M140_MMDAMASTER_T B                           \n");
    sb.append("       ,M360_ACCCODE_T C                              \n");
    sb.append("  WHERE B.M140_ACCOUNTNO = A.M160_ACCOUNTNO           \n");
    sb.append("    AND B.M140_YEAR = C.M360_YEAR                     \n");
    sb.append("    AND B.M140_ACCTYPE = C.M360_ACCGUBUN              \n");
    sb.append("    AND B.M140_ACCCODE = C.M360_ACCCODE               \n");
    sb.append("    AND B.M140_YEAR = ?                               \n");
    sb.append("    AND B.M140_ACCTYPE = ?                            \n");
    if (!"".equals(paramInfo.getString("acct_list")) ) { 
      sb.append("    AND B.M140_ACCCODE = ?                          \n");
    }
    sb.append("    AND B.M140_PARTCODE = ?                           \n");
    sb.append("    AND B.M140_CANCEL_YN = 'N'                        \n");
    sb.append("    AND A.M160_TRANSACTIONDATE = ?                    \n");
    sb.append("    AND A.M160_TRANSACTIONTYPE = '1'                  \n");
    sb.append("  UNION ALL                                           \n");
    sb.append(" SELECT M150_YEAR FIS_YEAR                            \n");
    sb.append("       ,DECODE(A.M150_ACCTYPE, 'B', 'Ư��ȸ��',       \n");
    sb.append("                       'D', '���Լ��������',         \n");
    sb.append("                       'E', '���') TYPE_NAME         \n");
    sb.append("       ,A.M150_ACCTYPE ACC_TYPE                       \n");
    sb.append("       ,B.M360_ACCNAME ACC_NAME                       \n");
    sb.append("       ,A.M150_ACCCODE ACC_CODE                       \n");
    sb.append("       ,DECODE(A.M150_DEPOSITTYPE, 'G1', '���⿹��',  \n");
    sb.append("                       'G2', 'ȯ��ä' ) DEPOSIT_NAME  \n");
    sb.append("       ,A.M150_DEPOSITTYPE DEPOSIT_TYPE               \n");
    sb.append("       ,'' MMDA_NAME                                  \n");
    sb.append("       ,A.M150_ACCOUNTNO ACCT_NO                      \n");
    sb.append("       ,A.M150_JWASUNO JWASU_NO                       \n");
    sb.append("       ,A.M150_DEPOSITAMT DEP_AMT                     \n");
    sb.append("       ,A.M150_DEPOSITDATE DEP_PERIOD                 \n");
    sb.append("       ,A.M150_INTERESTRATE DEP_RATE                  \n");
    sb.append("       ,A.M150_SINKYUDATE NEW_DATE                    \n");
    sb.append("       ,A.M150_MANGIDATE END_DATE                     \n");
    sb.append("       ,TO_NUMBER('') SEQ                             \n");
    sb.append("       ,A.M150_M120SEQ M120SEQ                        \n");
    sb.append("   FROM M150_MONEYMANAGEMENT_T A                      \n");
    sb.append("       ,M360_ACCCODE_T B                              \n");
    sb.append("  WHERE A.M150_YEAR = B.M360_YEAR                     \n");
    sb.append("    AND A.M150_ACCTYPE = B.M360_ACCGUBUN              \n");
    sb.append("    AND A.M150_ACCCODE = B.M360_ACCCODE               \n");
    sb.append("    AND A.M150_YEAR = ?                               \n");
    sb.append("    AND A.M150_ACCTYPE = ?                            \n");
    if (!"".equals(paramInfo.getString("acct_list")) ) { 
      sb.append("    AND A.M150_ACCCODE = ?                          \n");
    }
    sb.append("    AND A.M150_PARTCODE = ?                           \n");
    sb.append("    AND A.M150_SINKYUDATE = ?                         \n");
    sb.append("  ORDER BY DEPOSIT_NAME                               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_kind"));
		if (!"".equals(paramInfo.getString("acct_list")) ) {
		  parameters.setString(idx++, paramInfo.getString("acct_list"));
		}
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("new_date"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acct_kind"));
		if (!"".equals(paramInfo.getString("acct_list")) ) {
		  parameters.setString(idx++, paramInfo.getString("acct_list"));
		}
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("new_date"));

    return template.getList(conn, parameters);
  }


  /* MMDA�� ���� */
  public static int deleteYetak(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M120_MONEYDEPOSIT_T   \n");
    sb.append("  WHERE M120_SEQ = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("m120_seq"));		

    return template.delete(conn, parameters);
  }


	/* MMDA�� ���� */
  public static int deleteMMDA(Connection conn, String seq) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M160_MMDADETAILS_T    \n");
    sb.append("  WHERE M160_SEQ = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, seq);		

    return template.delete(conn, parameters);
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


	/* ���⿹��, ȯ��ä ���� */
  public static int deleteHwan(Connection conn, String fis_year, String acct_no, String jwasu_no) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M150_MONEYMANAGEMENT_T  \n");
    sb.append("  WHERE M150_YEAR = ?                \n");
		sb.append("    AND M150_ACCOUNTNO = ?           \n");
    sb.append("    AND M150_JWASUNO = ?             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, fis_year);
		parameters.setString(idx++, acct_no);
		parameters.setString(idx++, jwasu_no);

    return template.delete(conn, parameters);
  }
}
