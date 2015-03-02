/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050510.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > ���µ��
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050510 {

  /* �μ�����ȸ */
  public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT A.M350_PARTCODE                      \n");
    sb.append("      ,A.M350_PARTNAME                      \n");
    sb.append("  FROM M350_PARTCODE_T A                    \n");
    sb.append("      ,M390_USESEMOKCODE_T B                \n");
    sb.append(" WHERE A.M350_YEAR = B.M390_YEAR            \n");
    sb.append("   AND A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("   AND A.M350_YEAR = ?                      \n");
    sb.append("   AND B.M390_ACCGUBUN = ?                  \n");
    sb.append(" GROUP BY A.M350_PARTCODE, A.M350_PARTNAME  \n");
		sb.append(" ORDER BY A.M350_PARTCODE                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
    parameters.setString(idx++,  paramInfo.getString("acc_type"));

    return template.getList(conn, parameters);
  }


	/* ȸ�����ȸ */
  public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT A.M360_ACCCODE                     \n");
    sb.append("       ,A.M360_ACCNAME                     \n");
    sb.append("   FROM M360_ACCCODE_T A                   \n");
    sb.append("       ,M390_USESEMOKCODE_T B              \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR          \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN  \n");
    sb.append("    AND A.M360_ACCCODE = B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_YEAR = ?                    \n");
    sb.append("    AND A.M360_ACCGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                \n");
		sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME  \n");
    sb.append("  ORDER BY A.M360_ACCCODE                  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("acc_type"));
		parameters.setString(idx++,  paramInfo.getString("dept_cd"));

    return template.getList(conn, parameters);
  }

    /* �������� ��ȸ */
    public static List<CommonEntity> getAcctInfoList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.M300_YEAR                                                                 \n");
        sb.append("        ,DECODE(A.M300_BANKCODE, '039', '�泲����',                                 \n");
        sb.append("                         '011', '����') M300_BANKCODE                               \n");
        sb.append("        ,B.M350_PARTNAME                                                            \n");
        sb.append("        ,DECODE(M300_ACCGUBUN, 'A', '�Ϲ�ȸ��', 'B', 'Ư��ȸ��', 'C', '�����Ư��'  \n");
        sb.append("                    , 'D', '���Լ��������', 'E', '���') ACC_GUBUN                 \n");
        sb.append("        ,C.M360_ACCNAME                                                             \n");
        sb.append("        ,DECODE(A.M300_ACCOUNTTYPE, '01', '�Ա�'                                    \n");
        sb.append("                                   ,'02', '���'                                    \n");
        sb.append("                                   ,'03', '��ȸ', '04', '�ϻ�'                      \n");
        sb.append("                                   ) TYPE_NAME                                      \n");
        sb.append("        ,A.M300_ACCOUNTTYPE                                                         \n");
        sb.append("        ,A.M300_ACCOUNTNO                                                           \n");
        sb.append("        ,A.M300_ACCNAME                                                             \n");
        sb.append("        ,A.M300_NAME                                                                \n");
        sb.append("        ,A.M300_ACCCODE                                                             \n");
        sb.append("        ,DECODE(A.M300_JUHANGACCYN, 'Y', 'Yes',                                     \n");
        sb.append("                          'N', 'No') M300_JUHANGACCYN                               \n");
        sb.append("    FROM M300_ACCOUNTMANAGE_T A                                                     \n");
        sb.append("        ,M350_PARTCODE_T B                                                          \n");
        sb.append("        ,M360_ACCCODE_T C                                                           \n");
        sb.append("   WHERE A.M300_YEAR = B.M350_YEAR(+)                                               \n");
        sb.append("     AND A.M300_PARTCODE = B.M350_PARTCODE(+)                                       \n");
        sb.append("     AND A.M300_YEAR = C.M360_YEAR(+)                                               \n");
        sb.append("     AND A.M300_ACCGUBUN = C.M360_ACCGUBUN(+)                                       \n");
        sb.append("     AND A.M300_ACCCODE = C.M360_ACCCODE(+)                                         \n");
        if (!"".equals(paramInfo.getString("fis_year")) )	{
            sb.append("  AND A.M300_YEAR = ?                               \n");
		}
        if (!"".equals(paramInfo.getString("acct_gubun")) )	{
			sb.append("  AND A.M300_ACCOUNTTYPE = ?                        \n");
		}
        if (!"03".equals(paramInfo.getString("acct_gubun")) )	{
            if (!"".equals(paramInfo.getString("acc_type")) )	{
                sb.append("     AND A.M300_ACCGUBUN = ?                                                        \n");
            }
        }
        sb.append("   ORDER BY A.M300_PARTCODE                                                         \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		if (!"".equals(paramInfo.getString("fis_year")) )	{
			parameters.setString(idx++, paramInfo.getString("fis_year"));
		}

		if (!"".equals(paramInfo.getString("acct_gubun")) )	{
			parameters.setString(idx++, paramInfo.getString("acct_gubun"));
		}
        if (!"03".equals(paramInfo.getString("acct_gubun")) )	{
            if (!"".equals(paramInfo.getString("acc_type")) )	{
			    parameters.setString(idx++, paramInfo.getString("acc_type"));
		    }
        }

        return template.getList(conn, parameters);
    }


	/* �������� ���� Ȯ�� */
    public static CommonEntity getAcctInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M300_YEAR                                   \n");
        sb.append("       ,M300_BANKCODE                               \n");
        sb.append("       ,DECODE(M300_ACCOUNTTYPE, '01', '�Աݰ���'   \n");
        sb.append("                                 ,'02', '��ݰ���'  \n");
        sb.append("                                 ,'03', '��ȸ����'  \n");
        sb.append("               ,'04', '�ϻ���') TYPE_NAME         \n");
		sb.append("       ,M300_ACCOUNTTYPE                            \n");
		sb.append("       ,M300_ACCOUNTNO                              \n");
        sb.append("   FROM M300_ACCOUNTMANAGE_T                        \n");
        sb.append("  WHERE M300_YEAR = ?                               \n");
		sb.append("    AND M300_BANKCODE = ?                           \n");
        sb.append("    AND M300_ACCOUNTNO = ?                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("bank_cd"));
	    parameters.setString(idx++, paramInfo.getString("acct_no"));

        return template.getData(conn, parameters);
    }


	/* ȸ���ڵ庰 �������翩�� Ȯ�� */
  public static CommonEntity getAcctCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT COUNT(1) CNT            \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T    \n");
    sb.append("  WHERE M300_YEAR = ?           \n");
		sb.append("    AND M300_ACCGUBUN = ?       \n");
    sb.append("    AND M300_ACCCODE = ?        \n");
		sb.append("    AND M300_ACCOUNTTYPE = ?    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("acc_cd"));
		parameters.setString(idx++, paramInfo.getString("acct_gubun"));

    return template.getData(conn, parameters);
  }


	/* ���¸� ���翩�� Ȯ�� */
  public static CommonEntity getAcctNM(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M300_ACCNAME              \n");
		sb.append("       ,M300_ACCOUNTNO            \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T      \n");
		sb.append("  WHERE M300_YEAR = ?             \n");
    sb.append("    AND M300_ACCNAME = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acct_nm"));

    return template.getData(conn, parameters);
  }


	/* �ܸ���ȣ ��ȸ */
  public static CommonEntity getPcNo(Connection conn, String user_id) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M260_TERMINALNO           \n");
		sb.append("       ,SEND_SEQ.NEXTVAL SEND_NO  \n");
    sb.append("   FROM M260_USERMANAGER_T        \n");
		sb.append("  WHERE M260_USERID = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, user_id);

    return template.getData(conn, parameters);
  }
  

	/* �������� ��� */
  public static int insertAcctInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append("INSERT INTO M300_ACCOUNTMANAGE_T     \n");
    sb.append("(M300_YEAR, M300_BANKCODE,           \n");
		sb.append(" M300_ACCOUNTNO, M300_NAME,          \n");
		sb.append(" M300_ACCOUNTTYPE, M300_PARTCODE,    \n");
		sb.append(" M300_LOGNO, M300_ACCGUBUN,          \n");
		sb.append(" M300_ACCNAME, M300_JUHANGACCYN,     \n");
		sb.append(" M300_STATECODE, M300_ACCCODE )      \n");
		sb.append("VALUES(?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ? )                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("bank_cd"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("owner_nm"));
		parameters.setString(idx++, paramInfo.getString("acct_gubun"));
		parameters.setString(idx++, paramInfo.getString("dept_cd"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acct_nm"));
		parameters.setString(idx++, paramInfo.getString("juhaeng"));
		parameters.setString(idx++, paramInfo.getString("state"));
		parameters.setString(idx++, paramInfo.getString("acc_cd"));

    return template.insert(conn, parameters);
  }


	/* �������� ���� */
  public static int deleteAcctNoInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append("DELETE FROM M300_ACCOUNTMANAGE_T  \n");
    sb.append(" WHERE M300_ACCOUNTNO = ?         \n");
    sb.append("   AND M300_YEAR = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.delete(conn, parameters);
  }
}
