/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070111.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-09
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Ա��ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070111 {

	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������')  GUBUN                  \n");
		sb.append("      ,B.M390_SEMOKCODE                                                                                          \n");
		sb.append("      ,C.M370_SEMOKNAME                                                                                          \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) M220_AMTSEIP                                                                          \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) M220_AMTSEIPGWAONAP                                                            \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP + M220_AMTGWAONAPJEONILTOT) M220_AMTSEIPGWAONAPTOT                              \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) M220_AMTSEIPJEONGJEONG                                                      \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) TODAY_TOT  \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                                                     \n");
		sb.append("      ,M370_SEMOKCODE_T C                                                                                        \n");
		sb.append("      ,M220_DAY_T A                                                                                              \n");
		sb.append(" WHERE B.M390_YEAR = C.M370_YEAR                                                                                 \n");
		sb.append("   AND B.M390_ACCGUBUN      = C.M370_ACCGUBUN                                                                    \n");
		sb.append("   AND B.M390_ACCCODE       = C.M370_ACCCODE                                                                     \n");
		sb.append("   AND B.M390_SEMOKCODE     = C.M370_SEMOKCODE                                                                   \n");
    sb.append("   AND B.M390_WORKGUBUN     = C.M370_WORKGUBUN                                                                   \n");
		sb.append("   AND B.M390_YEAR          = A.M220_YEAR(+)                                                                     \n");
		sb.append("   AND B.M390_ACCGUBUN      = A.M220_ACCTYPE(+)                                                                  \n");
		sb.append("   AND B.M390_ACCCODE       = A.M220_ACCCODE(+)                                                                  \n");
		sb.append("   AND B.M390_SEMOKCODE     = A.M220_SEMOKCODE(+)                                                                \n");
		sb.append("   AND B.M390_PARTCODE      = A.M220_PARTCODE(+)                                                                 \n");
		sb.append("   AND B.M390_ACCGUBUN      = 'A'                                                                                \n");	// ȸ�豸�� (A:�Ϲ�ȸ��)        
		sb.append("   AND B.M390_ACCCODE       = '11'                                                                               \n");	// ȸ���ڵ� (11)                
		sb.append("   AND B.M390_WORKGUBUN     = '0'                                                                                \n");	// �����ڵ� (9000000 ����)  
		sb.append("   AND B.M390_SEMOKCODE < 9000000                                                                                \n");	// 0: ����
		sb.append("   AND SUBSTR(B.M390_SEMOKCODE,1,1) <> '9'                                                                       \n");
        sb.append("   AND C.M370_SEGUMGUBUN <> '2'                                                                                  \n");   //  ��������

        /* WHERE START */
		sb.append("   AND B.M390_YEAR = ?																							\n");
		sb.append("   AND A.M220_DATE(+) = ?																						\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND B.M390_ACCGUBUN = ?																					\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND B.M390_PARTCODE = ?																					\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND B.M390_ACCCODE = ?																					\n");
		}
        /* WHERE END */
		
		sb.append(" GROUP BY ROLLUP(DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������')  \n");
		sb.append("                ,(B.M390_SEMOKCODE ,C.M370_SEMOKNAME))                                              \n");
		//sb.append(" ORDER BY DECODE(SUBSTR(A.M220_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������')  , A.M220_SEMOKCODE	\n");

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