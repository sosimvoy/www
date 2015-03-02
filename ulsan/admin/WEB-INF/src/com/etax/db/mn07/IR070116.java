/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070116.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ������ϻ�����ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070116 {

	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT B.M390_PARTCODE                                                       \n");
		sb.append("      ,C.M350_PARTNAME                                                       \n");
		sb.append("      ,B.M390_SEMOKCODE                                                      \n");
		sb.append("      ,D.M370_SEMOKNAME                                                      \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                         \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) AMTSEIP                                           \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP                             \n");
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP) HWANBUTOT     \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                       \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYTOT  \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                 \n");
		sb.append("      ,M350_PARTCODE_T C                                                     \n");
		sb.append("      ,M370_SEMOKCODE_T D                                                    \n");
		sb.append("      ,M220_DAY_T A                                                          \n");
		sb.append(" WHERE B.M390_YEAR      = C.M350_YEAR                                        \n");
		sb.append("   AND B.M390_PARTCODE  = C.M350_PARTCODE                                    \n");
		sb.append("   AND B.M390_YEAR      = D.M370_YEAR                                        \n");
		sb.append("   AND B.M390_ACCGUBUN  = D.M370_ACCGUBUN                                    \n");
		sb.append("   AND B.M390_ACCCODE   = D.M370_ACCCODE                                     \n");
		sb.append("   AND B.M390_SEMOKCODE = D.M370_SEMOKCODE                                   \n");
		sb.append("   AND B.M390_WORKGUBUN = D.M370_WORKGUBUN                                   \n");
		sb.append("   AND B.M390_YEAR      = A.M220_YEAR(+)                                     \n");
		sb.append("   AND B.M390_PARTCODE  = A.M220_PARTCODE(+)                                 \n");
		sb.append("   AND B.M390_ACCGUBUN  = A.M220_ACCTYPE(+)                                  \n");
		sb.append("   AND B.M390_ACCCODE   = A.M220_ACCCODE(+)                                  \n");
		sb.append("   AND B.M390_SEMOKCODE = A.M220_SEMOKCODE(+)                                \n");
		sb.append("   AND B.M390_ACCGUBUN  = 'A'                                                \n");
		sb.append("   AND B.M390_ACCCODE   = '11'                                               \n");    // ����(��ϼ�,���汳����,�����Ư����)  
		sb.append("   AND B.M390_SEMOKCODE IN ('1110200','1120500','1199900')                   \n");    // �μ�(�߱�,����,����,�ϱ�,���ֱ�)    
		sb.append("   AND B.M390_PARTCODE IN ('00110','00140','00170','00200','00710')          \n");    // ��������ڵ� (���������ϻ����)     
		sb.append("   AND B.M390_WORKGUBUN = '0'                                                \n");    // ����                                
		sb.append("   AND A.M220_SUNAPGIGWANCODE(+) = '310001'                                  \n");


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

		sb.append(" GROUP BY ROLLUP(B.M390_SEMOKCODE,(D.M370_SEMOKNAME,B.M390_PARTCODE ,C.M350_PARTNAME))   \n");
		sb.append("	ORDER BY B.M390_PARTCODE,B.M390_SEMOKCODE                                               \n");

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



    /* ���� ��ȸ */
	public static List<CommonEntity> getReportList1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT B.M390_SEMOKCODE                                                      \n");
		sb.append("      ,CASE WHEN B.M390_SEMOKCODE IS NULL                                    \n");
        sb.append("            THEN ''                                                          \n");
        sb.append("            ELSE MAX(D.M370_SEMOKNAME)                                       \n");
        sb.append("        END M370_SEMOKNAME                                                   \n");
        sb.append("      ,'' M350_PARTCODE                                                      \n");
        sb.append("      ,CASE WHEN B.M390_SEMOKCODE IS NOT NULL                                \n");
        sb.append("            THEN ''                                                          \n");
        sb.append("            ELSE '�� ��'                                                     \n");
        sb.append("        END M350_PARTNAME                                                    \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                         \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) AMTSEIP                                           \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP                             \n");
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP) HWANBUTOT     \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                       \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYTOT  \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                 \n");
		sb.append("      ,M350_PARTCODE_T C                                                     \n");
		sb.append("      ,M370_SEMOKCODE_T D                                                    \n");
		sb.append("      ,M220_DAY_T A                                                          \n");
		sb.append(" WHERE B.M390_YEAR      = C.M350_YEAR                                        \n");
		sb.append("   AND B.M390_PARTCODE  = C.M350_PARTCODE                                    \n");
		sb.append("   AND B.M390_YEAR      = D.M370_YEAR                                        \n");
		sb.append("   AND B.M390_ACCGUBUN  = D.M370_ACCGUBUN                                    \n");
		sb.append("   AND B.M390_ACCCODE   = D.M370_ACCCODE                                     \n");
		sb.append("   AND B.M390_SEMOKCODE = D.M370_SEMOKCODE                                   \n");
		sb.append("   AND B.M390_WORKGUBUN = D.M370_WORKGUBUN                                   \n");
		sb.append("   AND B.M390_YEAR      = A.M220_YEAR(+)                                     \n");
		sb.append("   AND B.M390_PARTCODE  = A.M220_PARTCODE(+)                                 \n");
		sb.append("   AND B.M390_ACCGUBUN  = A.M220_ACCTYPE(+)                                  \n");
		sb.append("   AND B.M390_ACCCODE   = A.M220_ACCCODE(+)                                  \n");
		sb.append("   AND B.M390_SEMOKCODE = A.M220_SEMOKCODE(+)                                \n");
		sb.append("   AND B.M390_ACCGUBUN  = 'A'                                                \n");
		sb.append("   AND B.M390_ACCCODE   = '11'                                               \n");    // ����(��ϼ�,���汳����,�����Ư����)  
		sb.append("   AND B.M390_SEMOKCODE IN ('1110100', '1110200','1120500','1199900')        \n");    // �μ�(�߱�,����,����,�ϱ�,���ֱ�)    
		sb.append("   AND B.M390_PARTCODE IN ('00110','00140','00170','00200','00710')          \n");    // ��������ڵ� (���������ϻ����)     
		sb.append("   AND B.M390_WORKGUBUN = '0'                                                \n");    // ����                                
		sb.append("   AND A.M220_SUNAPGIGWANCODE(+) = '310001'                                  \n");


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

		sb.append(" GROUP BY ROLLUP(B.M390_SEMOKCODE)   \n");
		sb.append("	ORDER BY B.M390_SEMOKCODE                                   \n");

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

