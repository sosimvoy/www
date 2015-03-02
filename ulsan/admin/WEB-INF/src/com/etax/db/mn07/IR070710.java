/**********************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070710.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���������հ�ǥ��ȸ
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070710 {

  /* �������� ���հ�ǥ */
	public static List<CommonEntity> getTaxTotal(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT  TO_CHAR(A.TMP_DATE,'YYYY.MM.DD') TMP_DATE                                                      \n");
	  sb.append("       ,SUM(B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG) SPECIAL_TAX             \n"); //��ϳ�Ư��,�����հ�
    sb.append("  FROM (SELECT TO_DATE( ?, 'YYYYMMDD') + (LEVEL - 1) TMP_DATE                                          \n");	 
		sb.append("          FROM DUAL                                                                                    \n");
		sb.append("        CONNECT BY LEVEL <= ?) A                                                                       \n");		 
    sb.append("       ,M220_DAY_T B                                                                                   \n");	
    sb.append(" WHERE  A.TMP_DATE = B.M220_DATE(+)                                                                    \n");					
    sb.append(" 	AND  B.M220_ACCTYPE(+)   = 'A'                                                                      \n"); //ȸ�豸�� A
		sb.append(" 	AND  B.M220_ACCCODE(+)    = '11'                                                                    \n"); //ȸ���ڵ� 11
    sb.append("   AND  B.M220_SEMOKCODE(+)  = '1199900'                                                               \n"); //�����ڵ� 1199900
		sb.append("   AND  B.M220_SUNAPGIGWANCODE(+)  = '310001'                                                          \n"); //��������ڵ� 310001
		sb.append(" GROUP BY ROLLUP(A.TMP_DATE)                                                                           \n");
    sb.append(" ORDER BY A.TMP_DATE                                                                                   \n");            		
																																						           																							
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
	
		parameters.setString(idx++, paramInfo.getString("first_date"));
		parameters.setString(idx++, paramInfo.getString("last_day"));
		
		return template.getList(conn, parameters);
	}

  /* ���Ա� ��ü�� ������ */
	public static List<CommonEntity> getTaxTransfer(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM(B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG) SPECIAL_TAX                                                        \n"); //��Ư��                                                                
	  sb.append("      ,SUM(DECODE(B.M220_DATE, ?, B.M220_AMTSEIPJEONILTOT + B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG, 0)) END_DATE_TAX  \n"); //�հ�
    sb.append("  FROM  M220_DAY_T B                                                                                                                             \n");	 				
    sb.append(" WHERE  B.M220_ACCTYPE(+)   = 'A'                                                                                                                \n"); //ȸ�豸�� A
		sb.append(" 	AND  B.M220_ACCCODE(+)    = '11'                                                                                                              \n"); //ȸ���ڵ� 11
    sb.append("   AND  B.M220_SEMOKCODE(+)  = '1199900'                                                                                                         \n"); //�����ڵ� 1199900
		sb.append("   AND  B.M220_SUNAPGIGWANCODE(+)  = '310001'                                                                                                    \n"); //��������ڵ� 310001
    sb.append("   AND  B.M220_YEAR = ?                                                                                                                          \n");
    sb.append("   AND  B.M220_DATE LIKE ?                                                                                                                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
	
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_year") + paramInfo.getString("acc_month") +  "%" );
		
		return template.getList(conn, parameters);
	}
}

