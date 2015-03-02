/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070115.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���ܼ����ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070115 {

	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT B.M390_PARTCODE                                                                                           \n");
		sb.append("      ,C.M350_PARTNAME                                                                                           \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                                                             \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) AMTSEIP                                                                               \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP                                                                 \n");
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP) HWANBUTOT                                         \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                                                           \n");   // ȯ�δ��� (���������ϴ��� + ������)
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYTOT \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                                                     \n");   // ���ϱ������� (���ϴ��� + ���ϼ��� - ȯ�� - ����������)
		sb.append("      ,M350_PARTCODE_T C                                                                                         \n");
		sb.append("      ,M220_DAY_T A                                                                                              \n");
		sb.append(" WHERE B.M390_YEAR = C.M350_YEAR                                                                                 \n");
		sb.append("   AND B.M390_PARTCODE = C.M350_PARTCODE                                                                         \n");
		sb.append("   AND B.M390_YEAR       = A.M220_YEAR(+)                                                                        \n");
		sb.append("   AND B.M390_PARTCODE   = A.M220_PARTCODE(+)                                                                    \n");
		sb.append("   AND B.M390_ACCGUBUN   = A.M220_ACCTYPE(+)                                                                     \n");
		sb.append("   AND B.M390_ACCCODE    = A.M220_ACCCODE(+)                                                                     \n");
		sb.append("   AND B.M390_SEMOKCODE  = A.M220_SEMOKCODE(+)                                                                   \n");
		sb.append("   AND B.M390_ACCGUBUN = 'A'                                                                                     \n");
		sb.append("   AND B.M390_ACCCODE = '11'                                                                                     \n");
		sb.append("   AND SUBSTR(B.M390_SEMOKCODE,1,1) = '2'                                                                        \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                                                                    \n");   // ��������(����)

		// WHERE START
		sb.append("   AND B.M390_YEAR = ?										\n");
		sb.append("   AND A.M220_DATE(+) = ?									\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND B.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND B.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND B.M390_ACCCODE = ?								\n");
		}
		// WHERE END

		sb.append(" GROUP BY ROLLUP((B.M390_PARTCODE ,C.M350_PARTNAME))         \n");
		sb.append(" ORDER BY B.M390_PARTCODE ,C.M350_PARTNAME                   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("acc_type").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if(!paramInfo.getString("part_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}

		return template.getList(conn, parameters);
	}
}