/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070118.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ��������ü��δ�ݼ����ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070118 {

	/* ���� ��ȸ */
	public static CommonEntity getReportData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                               \n");
		sb.append("      ,C.M350_PARTNAME                                                               \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01	\n");   // ���ϴ��� : ��������ü��δ��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02	\n");   // ���ϴ��� : ���ڼ���              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03	\n");   // ���ϴ��� : �̿���                
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04	\n");   // ���ϴ��� : �������Ա�            
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99	\n");   // ���ϴ��� : �հ�                  
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01             \n");  // ���ϼ��� : ��������ü��δ��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02             \n");  // ���ϼ��� : ���ڼ���             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03             \n");  // ���ϼ��� : �̿���               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_04             \n");  // ���ϼ��� : �������Ա�           
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99             \n");  // ���ϼ��� : �հ�                 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01		\n");  // ������ : ��������ü��δ�� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02		\n");  // ������ : ���ڼ���           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03		\n");  // ������ : �̿���             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_04		\n");  // ������ : �������Ա�         
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99		\n");  // ������ : �հ�               
		    
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	 \n");   // ���������� : ��������ü��δ��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	 \n");   // ���������� : ���ڼ���             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	 \n");   // ���������� : �̿���               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	 \n");   // ���������� : �������Ա�           
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	 \n");   // ���������� : �հ�                 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_01 \n");     // ������ : ��������ü��δ��
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_02 \n");     // ������ : ���ڼ���          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_03 \n");     // ������ : �̿���            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_04 \n");     // ������ : �������Ա�        
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG)		                                 GWAOMOK_99	\n");     // ������ : �հ�              
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");   // ���ϴ��� : ��������ü��δ��
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");   // ���ϴ��� : ���ڼ���          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");   // ���ϴ��� : �̿���            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");   // ���ϴ��� : �������Ա�        
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");   // ���ϴ��� : �հ�              
		
        sb.append("  FROM M220_DAY_T A                                                      \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                            \n");
		sb.append("      ,M350_PARTCODE_T C                                                 \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                 \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                  \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                   \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                               \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                 \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                            \n");    // ��������:����
		sb.append("   AND A.M220_ACCTYPE = 'B'                                              \n");    // ȸ�豸��     
		sb.append("   AND A.M220_ACCCODE = '21'                                             \n");    // ȸ���ڵ�     
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110400')     \n");    // ���� ��ü    
		sb.append("   AND A.M220_PARTCODE  = '00000'                                        \n");    // ��û�� �ش�  

		// WHERE START
		sb.append("   AND A.M220_YEAR = ?										\n");
		sb.append("   AND A.M220_DATE = ?										\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND A.M220_ACCTYPE = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND A.M220_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND A.M220_ACCCODE = ?								\n");
		}
		// WHERE END

		sb.append(" GROUP BY A.M220_PARTCODE,C.M350_PARTNAME  \n");

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

		return template.getData(conn, parameters);
	}
}