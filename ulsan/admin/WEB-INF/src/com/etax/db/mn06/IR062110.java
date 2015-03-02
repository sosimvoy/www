/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR062110.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > ������������
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR062110 {

    /* ȸ���ڵ� ��ȸ */
    public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                                             \n");
        sb.append("   FROM M360_ACCCODE_T                                \n");
        sb.append("  WHERE M360_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY'))  \n");
        sb.append("    AND M360_ACCGUBUN = NVL(?, 'A')                   \n");
        sb.append("  ORDER BY M360_ACCCODE                               \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
	    QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.getList(conn, parameters);
    }

	/* ������������ ��ȸ */
    public static List<CommonEntity> getSegyeList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M580_NO                                                 \n");
        sb.append("      , M580_YEAR                                               \n");
        sb.append("      , M580_DATE                                               \n");
        sb.append("      , DECODE(M580_ACCTYPE, 'A', '�Ϲ�ȸ��', 'B', 'Ư��ȸ��',  \n");
        sb.append("               'E', '���', '��Ÿ') M580_ACCTYPE                \n");
        sb.append("       ,M360_ACCNAME                                            \n");
        sb.append("       ,DECODE(M580_JEONTYPE, '1', '�뿩', '2', '�뿩ȸ��',     \n");
        sb.append("               '3', '����', '4', '���Ի�ȯ','��Ÿ') M580_JEONTYPE \n");
        sb.append("       ,ABS(M580_AMT) M580_AMT                                  \n");
        sb.append("   FROM M580_SEGYE_T A                                          \n");
        sb.append("      , M360_ACCCODE_T B                                        \n");
        sb.append("  WHERE A.M580_YEAR = B.M360_YEAR                               \n");
        sb.append("    AND A.M580_ACCTYPE = B.M360_ACCGUBUN                        \n");
        sb.append("    AND A.M580_ACCCODE = B.M360_ACCCODE                         \n");
        sb.append("    AND A.M580_YEAR = ?                                         \n");
        sb.append("    AND A.M580_DATE = ?                                         \n");
        sb.append("  ORDER BY M580_YEAR, M580_ACCTYPE, M580_ACCCODE                \n");
        
        
        QueryTemplate template = new QueryTemplate(sb.toString());
	    QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    parameters.setString(idx++, paramInfo.getString("s_year"));
        parameters.setString(idx++, paramInfo.getString("s_date"));

        return template.getList(conn, parameters);
    }


    /* ������������ ��� */
    public static int segyeInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M580_SEGYE_T                                       \n");
        sb.append(" (M580_NO, M580_YEAR, M580_DATE, M580_ACCTYPE, M580_ACCCODE     \n");
        sb.append("  , M580_JEONTYPE, M580_AMT)                                    \n");
        sb.append(" VALUES                                                         \n");
        sb.append(" (M580_SEQ.NEXTVAL, ?, ?, ?, ?                                  \n");
        sb.append("  , ?, DECODE(?, '1', ?, '2', -1*?, '3', -1*?, '4', ?))         \n");
        
        
        QueryTemplate template = new QueryTemplate(sb.toString());
	    QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_code"));
        parameters.setString(idx++, paramInfo.getString("jeon_type"));
        parameters.setString(idx++, paramInfo.getString("jeon_type"));
        parameters.setString(idx++, paramInfo.getString("amt"));
        parameters.setString(idx++, paramInfo.getString("amt"));
        parameters.setString(idx++, paramInfo.getString("amt"));
        parameters.setString(idx++, paramInfo.getString("amt"));

        return template.insert(conn, parameters);
    }


    /* ������������ ���� */
    public static int segyeDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" DELETE FROM M580_SEGYE_T      \n");
        sb.append("  WHERE M580_NO = ?            \n");
        
        
        QueryTemplate template = new QueryTemplate(sb.toString());
	    QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    parameters.setString(idx++, paramInfo.getString("seq"));

        return template.insert(conn, parameters);
    }
}

