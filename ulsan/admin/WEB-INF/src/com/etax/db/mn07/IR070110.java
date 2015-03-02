/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070110.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-09
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ (�ڵ�)
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070110 {

	/* �μ��ڵ� ��ȸ */
	public static List<CommonEntity> getPartList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
        
        if(paramInfo.getString("acc_type").equals("")){
        sb.append("SELECT '1'       TYPE                        \n");
        sb.append("      ,''        PARTCODE                    \n");
        sb.append("      ,'��ü'    PARTNAME                    \n");
        sb.append("  FROM DUAL                                  \n");
        sb.append(" UNION ALL                                   \n");
        }

        sb.append("SELECT '2'       TYPE                        \n");
        sb.append("      ,A.M350_PARTCODE  PARTCODE             \n");
        sb.append("      ,A.M350_PARTNAME  PARTNAME             \n");
        sb.append("  FROM M350_PARTCODE_T A                     \n");
        sb.append("      ,M390_USESEMOKCODE_T B                 \n");
        sb.append(" WHERE A.M350_YEAR = B.M390_YEAR             \n");
        sb.append("   AND A.M350_PARTCODE = B.M390_PARTCODE     \n");
		sb.append("   AND A.M350_YEAR = ?                       \n");
        sb.append("   AND B.M390_ACCGUBUN = ?                   \n");
        sb.append(" GROUP BY A.M350_PARTCODE, A.M350_PARTNAME   \n");
		sb.append(" ORDER BY TYPE,PARTCODE                      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

		return template.getList(conn, parameters);
	}

	/* ȸ���ڵ� ��ȸ */
	public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
        
        if(paramInfo.getString("acc_type").equals("")){
        sb.append("SELECT '1'       TYPE                    \n");
        sb.append("      ,''        ACCCODE                 \n");
        sb.append("      ,'��ü'    ACCNAME                 \n");
        sb.append("  FROM DUAL                              \n");
        sb.append(" UNION ALL                               \n");
        }
        sb.append("SELECT '2'       TYPE                    \n");
        sb.append("      ,A.M360_ACCCODE   ACCCODE          \n");
        sb.append("      ,A.M360_ACCNAME   ACCNAME          \n");
        sb.append("  FROM M360_ACCCODE_T A                  \n");
        sb.append("      ,M390_USESEMOKCODE_T B             \n");
        sb.append(" WHERE A.M360_YEAR = B.M390_YEAR         \n");
        sb.append("   AND A.M360_ACCGUBUN = B.M390_ACCGUBUN \n");
        sb.append("   AND A.M360_ACCCODE = B.M390_ACCCODE   \n");
        sb.append("   AND A.M360_YEAR = ?                   \n");
        sb.append("   AND A.M360_ACCGUBUN = ?               \n");
        sb.append("   AND B.M390_PARTCODE = ?               \n");
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME \n");
        sb.append(" ORDER BY TYPE,ACCCODE                   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));

		return template.getList(conn, parameters);
	}
}