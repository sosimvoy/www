/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070117.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-13
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ������Ư��ȸ�輼���ϰ�ǥ
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070117 {

	/* ���� ��ȸ(��û) */
	public static List<CommonEntity> getReportList1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                                   \n");
		sb.append("      ,C.M350_PARTNAME                                                                   \n");
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		\n");   // ���ϴ��� : ���·�                 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		\n");   // ���ϴ��� : ��������               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		\n");   // ���ϴ��� : ���ڼ���               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		\n");   // ���ϴ��� : ��Ÿ����             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		\n");   // ���ϴ��� : ��Ÿ�����             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		\n");   // ���ϴ��� : �õ����               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		\n");   // ���ϴ��� : ü��ó��               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		\n");   // ���ϴ��� : �̿�                   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		\n");   // ���ϴ��� : �������               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		\n");   // ���ϴ��� : �������Ա�             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		\n");   // ���ϴ��� : ��������             
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		\n");   // ���ϴ��� : �հ�                   
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01                 \n");        // ���ϼ��� : ���·�             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02                 \n");        // ���ϼ��� : ��������           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03                 \n");        // ���ϼ��� : ���ڼ���           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIP,0)) TODAY_04                 \n");        // ���ϼ��� : ��Ÿ����         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_05                 \n");        // ���ϼ��� : ��Ÿ�����         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIP,0)) TODAY_06                 \n");        // ���ϼ��� : �õ����           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIP,0)) TODAY_07                 \n");        // ���ϼ��� : ü��ó��           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIP,0)) TODAY_08                 \n");        // ���ϼ��� : �̿�               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIP,0)) TODAY_09                 \n");        // ���ϼ��� : �������           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIP,0)) TODAY_10                 \n");        // ���ϼ��� : �������Ա�         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIP,0)) TODAY_11                 \n");        // ���ϼ��� : ��������         
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99                 \n");        // ���ϼ��� : �հ�               
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01           \n");        // ������ : ���·�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02           \n");        // ������ : ��������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03           \n");        // ������ : ���ڼ���          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04           \n");        // ������ : ��Ÿ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05           \n");        // ������ : ��Ÿ�����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06           \n");        // ������ : �õ����          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07           \n");        // ������ : ü��ó��          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08           \n");        // ������ : �̿�              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09           \n");        // ������ : �������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10           \n");        // ������ : �������Ա�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11           \n");        // ������ : ��������        
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99           \n");        // ������ : �հ�              
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	   \n");   // ���������� : ���·�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	   \n");   // ���������� : ��������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	   \n");   // ���������� : ���ڼ���     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	   \n");   // ���������� : ��Ÿ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05	   \n");   // ���������� : ��Ÿ�����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06	   \n");   // ���������� : �õ����     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07	   \n");   // ���������� : ü��ó��     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08	   \n");   // ���������� : �̿�         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09	   \n");   // ���������� : �������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10	   \n");   // ���������� : �������Ա�   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11	   \n");   // ���������� : ��������   
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	   \n");   // ���������� : �հ�         
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_01                             \n");   // ������ : ���·�          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_02                             \n");   // ������ : ��������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_03                             \n");   // ������ : ���ڼ���        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_04                             \n");   // ������ : ��Ÿ����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_05                             \n");   // ������ : ��Ÿ�����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_06                             \n");   // ������ : �õ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_07                             \n");   // ������ : ü��ó��        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_08                             \n");   // ������ : �̿�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_09                             \n");   // ������ : �������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_10                             \n");   // ������ : �������Ա�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_11                             \n");   // ������ : ��������      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) 		                                GWAOMOK_99		                       \n");   // ������ : �հ�            
		sb.append("                                                                                                                    \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");      // ���ϴ��� : ���·�     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");      // ���ϴ��� : ��������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");      // ���ϴ��� : ���ڼ���   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");      // ���ϴ��� : ��Ÿ���� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");      // ���ϴ��� : ��Ÿ����� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");      // ���ϴ��� : �õ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");      // ���ϴ��� : ü��ó��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");      // ���ϴ��� : �̿�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");      // ���ϴ��� : �������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");      // ���ϴ��� : �������Ա� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");      // ���ϴ��� : �������� 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");      // ���ϴ��� : �հ�       
		
    sb.append("  FROM M220_DAY_T A                                                   \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                         \n");
		sb.append("      ,M350_PARTCODE_T C                                              \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                              \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                               \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                            \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                              \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'				                         \n");	// ��������:����    
		sb.append("   AND A.M220_ACCTYPE = 'B'					                         \n");  // ȸ�豸��             
		sb.append("   AND A.M220_ACCCODE = '31'					                         \n");  // ȸ���ڵ�         
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'   \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                  \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                         \n");  // ���� ��ü
		sb.append("   AND A.M220_PARTCODE IN ('00000')		                           \n");  // �μ� ��ü
    sb.append("   AND A.M220_SUNAPGIGWANCODE <> '310001'                         \n");  // ��������

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

		return template.getList(conn, parameters);
	}

	/* ���� ��ȸ(�������) */
	public static List<CommonEntity> getReportList3(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                                   \n");
		sb.append("      ,'����������' M350_PARTNAME                                                                   \n");
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		\n");   // ���ϴ��� : ���·�                 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		\n");   // ���ϴ��� : ��������               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		\n");   // ���ϴ��� : ���ڼ���               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		\n");   // ���ϴ��� : ��Ÿ����             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		\n");   // ���ϴ��� : ��Ÿ�����             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		\n");   // ���ϴ��� : �õ����               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		\n");   // ���ϴ��� : ü��ó��               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		\n");   // ���ϴ��� : �̿�                   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		\n");   // ���ϴ��� : �������               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		\n");   // ���ϴ��� : �������Ա�             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		\n");   // ���ϴ��� : ��������             
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		\n");   // ���ϴ��� : �հ�                   
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01                 \n");        // ���ϼ��� : ���·�             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02                 \n");        // ���ϼ��� : ��������           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03                 \n");        // ���ϼ��� : ���ڼ���           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIP,0)) TODAY_04                 \n");        // ���ϼ��� : ��Ÿ����         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_05                 \n");        // ���ϼ��� : ��Ÿ�����         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIP,0)) TODAY_06                 \n");        // ���ϼ��� : �õ����           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIP,0)) TODAY_07                 \n");        // ���ϼ��� : ü��ó��           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIP,0)) TODAY_08                 \n");        // ���ϼ��� : �̿�               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIP,0)) TODAY_09                 \n");        // ���ϼ��� : �������           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIP,0)) TODAY_10                 \n");        // ���ϼ��� : �������Ա�         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIP,0)) TODAY_11                 \n");        // ���ϼ��� : ��������         
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99                 \n");        // ���ϼ��� : �հ�               
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01           \n");        // ������ : ���·�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02           \n");        // ������ : ��������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03           \n");        // ������ : ���ڼ���          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04           \n");        // ������ : ��Ÿ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05           \n");        // ������ : ��Ÿ�����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06           \n");        // ������ : �õ����          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07           \n");        // ������ : ü��ó��          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08           \n");        // ������ : �̿�              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09           \n");        // ������ : �������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10           \n");        // ������ : �������Ա�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11           \n");        // ������ : ��������        
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99           \n");        // ������ : �հ�              
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	   \n");   // ���������� : ���·�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	   \n");   // ���������� : ��������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	   \n");   // ���������� : ���ڼ���     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	   \n");   // ���������� : ��Ÿ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05	   \n");   // ���������� : ��Ÿ�����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06	   \n");   // ���������� : �õ����     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07	   \n");   // ���������� : ü��ó��     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08	   \n");   // ���������� : �̿�         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09	   \n");   // ���������� : �������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10	   \n");   // ���������� : �������Ա�   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11	   \n");   // ���������� : ��������   
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	   \n");   // ���������� : �հ�         
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_01                             \n");   // ������ : ���·�          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_02                             \n");   // ������ : ��������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_03                             \n");   // ������ : ���ڼ���        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_04                             \n");   // ������ : ��Ÿ����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_05                             \n");   // ������ : ��Ÿ�����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_06                             \n");   // ������ : �õ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_07                             \n");   // ������ : ü��ó��        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_08                             \n");   // ������ : �̿�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_09                             \n");   // ������ : �������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_10                             \n");   // ������ : �������Ա�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_11                             \n");   // ������ : ��������      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) 		                                GWAOMOK_99		                       \n");   // ������ : �հ�            
		sb.append("                                                                                                                    \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");      // ���ϴ��� : ���·�     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");      // ���ϴ��� : ��������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");      // ���ϴ��� : ���ڼ���   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");      // ���ϴ��� : ��Ÿ���� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");      // ���ϴ��� : ��Ÿ����� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");      // ���ϴ��� : �õ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");      // ���ϴ��� : ü��ó��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");      // ���ϴ��� : �̿�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");      // ���ϴ��� : �������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");      // ���ϴ��� : �������Ա� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");      // ���ϴ��� : �������� 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");      // ���ϴ��� : �հ�       
		
    sb.append("  FROM M220_DAY_T A                                                   \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                         \n");
		sb.append("      ,M350_PARTCODE_T C                                              \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                              \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                               \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                            \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                              \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'				                         \n");	// ��������:����    
		sb.append("   AND A.M220_ACCTYPE = 'B'					                         \n");  // ȸ�豸��             
		sb.append("   AND A.M220_ACCCODE = '31'					                         \n");  // ȸ���ڵ�         
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'   \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                  \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                         \n");  // ���� ��ü
		sb.append("   AND A.M220_SUNAPGIGWANCODE = '310001'		                       \n");  // ����

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

		return template.getList(conn, parameters);
	}

    /* ���� ��ȸ(�� ��û) */
	public static List<CommonEntity> getReportList2(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                               \n");
		sb.append("      ,C.M350_PARTNAME                                                               \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01	\n");   // ���ϴ��� : ���·�                
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02	\n");   // ���ϴ��� : ��������              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03	\n");   // ���ϴ��� : ���ڼ���              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04	\n");   // ���ϴ��� : ��Ÿ����            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05	\n");   // ���ϴ��� : ��Ÿ�����            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06	\n");   // ���ϴ��� : �õ����              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07	\n");   // ���ϴ��� : ü��ó��              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08	\n");   // ���ϴ��� : �̿�                  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09	\n");   // ���ϴ��� : �������              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10	\n");   // ���ϴ��� : �������Ա�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11	\n");   // ���ϴ��� : ��������            
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99	\n");   // ���ϴ��� : �հ�                  
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_01    \n");    // ���⵵���� : ���·�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_02    \n");    // ���⵵���� : ��������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_03    \n");    // ���⵵���� : ���ڼ���    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_04    \n");    // ���⵵���� : ��Ÿ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_05    \n");    // ���⵵���� : ��Ÿ�����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_06    \n");    // ���⵵���� : �õ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_07    \n");    // ���⵵���� : ü��ó��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_08    \n");    // ���⵵���� : �̿�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_09    \n");    // ���⵵���� : �������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_10    \n");    // ���⵵���� : �������Ա�  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_11    \n");    // ���⵵���� : ��������  
		sb.append("      ,SUM((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP)                                         NOWAMT_99    \n");    // ���⵵���� : �հ�        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)) ,0)) NOWTOT_01    \n");    // ���⵵���� : ���·�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_02    \n");    // ���⵵���� : ��������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_03    \n");    // ���⵵���� : ���ڼ���    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_04    \n");    // ���⵵���� : ��Ÿ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_05    \n");    // ���⵵���� : ��Ÿ�����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_06    \n");    // ���⵵���� : �õ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_07    \n");    // ���⵵���� : ü��ó��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_08    \n");    // ���⵵���� : �̿�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_09    \n");    // ���⵵���� : �������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_10    \n");    // ���⵵���� : �������Ա�  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_11    \n");    // ���⵵���� : ��������  
		sb.append("      ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP))                                         NOWTOT_99    \n");    // ���⵵���� : �հ�        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTPASTSEIP,0))  PASTAMT_01            \n"); // ���⵵���� : ���·�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTPASTSEIP,0))  PASTAMT_02            \n"); // ���⵵���� : ��������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTPASTSEIP,0))  PASTAMT_03            \n"); // ���⵵���� : ���ڼ���     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTPASTSEIP,0))  PASTAMT_04            \n"); // ���⵵���� : ��Ÿ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTPASTSEIP,0))  PASTAMT_05            \n"); // ���⵵���� : ��Ÿ�����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTPASTSEIP,0))  PASTAMT_06            \n"); // ���⵵���� : �õ����     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTPASTSEIP,0))  PASTAMT_07            \n"); // ���⵵���� : ü��ó��     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTPASTSEIP,0))  PASTAMT_08            \n"); // ���⵵���� : �̿�         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTPASTSEIP,0))  PASTAMT_09            \n"); // ���⵵���� : �������     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTPASTSEIP,0))  PASTAMT_10            \n"); // ���⵵���� : �������Ա�   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTPASTSEIP,0))  PASTAMT_11            \n"); // ���⵵���� : ��������   
		sb.append("      ,SUM(A.M220_AMTPASTSEIP)                                       PASTAMT_99            \n"); // ���⵵���� : �հ�         
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_01   \n");  // ���⵵���� : ���·�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_02   \n");  // ���⵵���� : ��������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_03   \n");  // ���⵵���� : ���ڼ���    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_04   \n");  // ���⵵���� : ��Ÿ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_05   \n");  // ���⵵���� : ��Ÿ�����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_06   \n");  // ���⵵���� : �õ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_07   \n");  // ���⵵���� : ü��ó��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_08   \n");  // ���⵵���� : �̿�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_09   \n");  // ���⵵���� : �������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_10   \n");  // ���⵵���� : �������Ա�  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_11   \n");  // ���⵵���� : ��������  
		sb.append("      ,SUM(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)                                            PASTTOT_99   \n");  // ���⵵���� : �հ�        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");  // ���ϴ��� : ���·�          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");  // ���ϴ��� : ��������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");  // ���ϴ��� : ���ڼ���        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");  // ���ϴ��� : ��Ÿ����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");  // ���ϴ��� : ��Ÿ�����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");  // ���ϴ��� : �õ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");  // ���ϴ��� : ü��ó��        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");  // ���ϴ��� : �̿�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");  // ���ϴ��� : �������        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");  // ���ϴ��� : �������Ա�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");  // ���ϴ��� : ��������      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");  // ���ϴ��� : �հ�            
		
        sb.append("  FROM M220_DAY_T A                                                      \n");
		sb.append("      ,M390_USESEMOKCODE_T B                                             \n");
		sb.append("      ,M350_PARTCODE_T C                                                 \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                 \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                  \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                   \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                               \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                 \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                            \n");   // ��������:����
		sb.append("   AND A.M220_ACCTYPE = 'B'                                              \n");   // ȸ�豸��
		sb.append("   AND A.M220_ACCCODE = '31'                                             \n");   // ȸ���ڵ�
        sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'      \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                     \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')                             \n");   // ���� ��ü
		sb.append("   AND A.M220_PARTCODE IN ('00110','00140','00170','00200','00710')	    \n");   // �μ� ��ü 


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
		sb.append(" ORDER BY A.M220_PARTCODE  \n");

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

    /* ���� ��ȸ(�հ�) */
	public static CommonEntity getReportData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		
        sb.append("SELECT SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		 \n");  // ���ϴ��� : ���·�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		 \n");  // ���ϴ��� : ��������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		 \n");  // ���ϴ��� : ���ڼ���    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		 \n");  // ���ϴ��� : ��Ÿ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		 \n");  // ���ϴ��� : ��Ÿ�����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		 \n");  // ���ϴ��� : �õ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		 \n");  // ���ϴ��� : ü��ó��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		 \n");  // ���ϴ��� : �̿�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		 \n");  // ���ϴ��� : �������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		 \n");  // ���ϴ��� : �������Ա�  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		 \n");  // ���ϴ��� : ��������  
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		 \n");  // ���ϴ��� : �հ�        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_01    \n");  // ���⵵���� : ���·�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_02    \n");  // ���⵵���� : ��������      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_03    \n");  // ���⵵���� : ���ڼ���      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_04    \n");  // ���⵵���� : ��Ÿ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_05    \n");  // ���⵵���� : ��Ÿ�����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_06    \n");  // ���⵵���� : �õ����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_07    \n");  // ���⵵���� : ü��ó��      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_08    \n");  // ���⵵���� : �̿�          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_09    \n");  // ���⵵���� : �������      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_10    \n");  // ���⵵���� : �������Ա�    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_11    \n");  // ���⵵���� : ��������    
		sb.append("      ,SUM((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP)                                         NOWAMT_99    \n");  // ���⵵���� : �հ�          
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_01 \n");        // ���⵵���� : ���·�    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_02 \n");        // ���⵵���� : ��������  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_03 \n");        // ���⵵���� : ���ڼ���  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_04 \n");        // ���⵵���� : ��Ÿ����
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_05 \n");        // ���⵵���� : ��Ÿ�����
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_06 \n");        // ���⵵���� : �õ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_07 \n");        // ���⵵���� : ü��ó��  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_08 \n");        // ���⵵���� : �̿�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_09 \n");        // ���⵵���� : �������  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_10 \n");        // ���⵵���� : �������Ա�
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_11 \n");        // ���⵵���� : ��������
		sb.append("      ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP))                                         NOWTOT_99 \n");        // ���⵵���� : �հ�      
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTPASTSEIP,0))  PASTAMT_01          \n");    // ���⵵���� : ���·�     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTPASTSEIP,0))  PASTAMT_02          \n");    // ���⵵���� : ��������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTPASTSEIP,0))  PASTAMT_03          \n");    // ���⵵���� : ���ڼ���   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTPASTSEIP,0))  PASTAMT_04          \n");    // ���⵵���� : ��Ÿ���� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTPASTSEIP,0))  PASTAMT_05          \n");    // ���⵵���� : ��Ÿ����� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTPASTSEIP,0))  PASTAMT_06          \n");    // ���⵵���� : �õ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTPASTSEIP,0))  PASTAMT_07          \n");    // ���⵵���� : ü��ó��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTPASTSEIP,0))  PASTAMT_08          \n");    // ���⵵���� : �̿�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTPASTSEIP,0))  PASTAMT_09          \n");    // ���⵵���� : �������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTPASTSEIP,0))  PASTAMT_10          \n");    // ���⵵���� : �������Ա� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTPASTSEIP,0))  PASTAMT_11          \n");    // ���⵵���� : �������� 
		sb.append("      ,SUM(A.M220_AMTPASTSEIP)                                       PASTAMT_99          \n");    // ���⵵���� : �հ�       
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_01  \n");    // ���⵵���� : ���·�            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_02  \n");    // ���⵵���� : ��������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_03  \n");    // ���⵵���� : ���ڼ���          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_04  \n");    // ���⵵���� : ��Ÿ����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_05  \n");    // ���⵵���� : ��Ÿ�����        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_06  \n");    // ���⵵���� : �õ����          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_07  \n");    // ���⵵���� : ü��ó��          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_08  \n");    // ���⵵���� : �̿�              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_09  \n");    // ���⵵���� : �������          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_10  \n");    // ���⵵���� : �������Ա�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_11  \n");    // ���⵵���� : ��������        
		sb.append("      ,SUM(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)                                            PASTTOT_99  \n");    // ���⵵���� : �հ�              
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01		    \n"); // ������ : ���·�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02		    \n"); // ������ : ��������      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03		    \n"); // ������ : ���ڼ���      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04		    \n"); // ������ : ��Ÿ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05		    \n"); // ������ : ��Ÿ�����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06		    \n"); // ������ : �õ����      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07		    \n"); // ������ : ü��ó��      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08		    \n"); // ������ : �̿�          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09		    \n"); // ������ : �������      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10		    \n"); // ������ : �������Ա�    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11		    \n"); // ������ : ��������    
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99		    \n"); // ������ : �հ�          
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01  \n");  	// ���������� : ���·�      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02  \n");  	// ���������� : ��������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03  \n");  	// ���������� : ���ڼ���    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04  \n");  	// ���������� : ��Ÿ����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05  \n");  	// ���������� : ��Ÿ�����  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06  \n");  	// ���������� : �õ����    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07  \n");  	// ���������� : ü��ó��    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08  \n");  	// ���������� : �̿�        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09  \n");  	// ���������� : �������    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10  \n");  	// ���������� : �������Ա�  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11  \n");  	// ���������� : ��������  
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	 \n");      // ���������� : �հ� 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01    \n");  // ���ϴ��� : ���·�     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02    \n");  // ���ϴ��� : ��������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03    \n");  // ���ϴ��� : ���ڼ���   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04    \n");  // ���ϴ��� : ��Ÿ���� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05    \n");  // ���ϴ��� : ��Ÿ����� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06    \n");  // ���ϴ��� : �õ����   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07    \n");  // ���ϴ��� : ü��ó��   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08    \n");  // ���ϴ��� : �̿�       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09    \n");  // ���ϴ��� : �������   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10    \n");  // ���ϴ��� : �������Ա� 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11    \n");  // ���ϴ��� : �������� 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99    \n");  // ���ϴ��� : �հ�       
		
        sb.append("  FROM M220_DAY_T A                              \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                    \n");
		sb.append("      ,M350_PARTCODE_T C                         \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                 \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE         \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN          \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE           \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE       \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                 \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE         \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'					                                   \n");    // ��������:����
		sb.append("   AND A.M220_ACCTYPE = 'B'					                                       \n");    // ȸ�豸��
		sb.append("   AND A.M220_ACCCODE = '31'						                                   \n");    // ȸ���ڵ�
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'                 \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                                \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                                       \n");    // ���� ��ü
		sb.append("   AND A.M220_PARTCODE IN ('00000','02240','00110','00140','00170','00200','00710') \n");    // �μ� ��ü

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