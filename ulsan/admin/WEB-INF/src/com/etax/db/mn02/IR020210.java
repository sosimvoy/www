/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020210.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����� ��ϳ���
******************************************************/
package com.etax.db.mn02;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020210 {

	/* ȸ��� ��ȸ */ 
	public static List<CommonEntity> getRevAccNmList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
		sb.append("  SELECT  A.M360_ACCCODE, A.M360_ACCNAME			 \n"); 
    sb.append("    FROM  M360_ACCCODE_T A										 \n"); 
    sb.append("         ,M390_USESEMOKCODE_T B							 \n"); 
    sb.append("   WHERE  B.M390_WORKGUBUN = '1'						   \n"); 
    sb.append("     AND  A.M360_YEAR = B.M390_YEAR					 \n"); 
    sb.append("     AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN	 \n"); 
    sb.append("     AND  A.M360_ACCCODE =  B.M390_ACCCODE		 \n");
		sb.append("     AND  A.M360_YEAR = ?				             \n");
    if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M360_ACCGUBUN = ?                   \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                   \n");																									 
		}
	  sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME      \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                      \n");										

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
	  if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}	
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}	
        
    return template.getList(conn, parameters);
  }
	
	/* �⺻ ȸ���ڵ� ����Ʈ */ 
	public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
		sb.append("  SELECT  A.M360_ACCCODE, A.M360_ACCNAME			  \n"); 
    sb.append("    FROM  M360_ACCCODE_T A										  \n"); 
    sb.append("         ,M390_USESEMOKCODE_T B								\n"); 
    sb.append("   WHERE  B.M390_WORKGUBUN = '1'							  \n"); 
    sb.append("     AND  A.M360_YEAR = B.M390_YEAR						\n"); 
    sb.append("     AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN		\n"); 
    sb.append("     AND  A.M360_ACCCODE =  B.M390_ACCCODE		  \n");
		sb.append("     AND  A.M360_YEAR = ?				              \n");
    if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M360_ACCGUBUN = ?                    \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                    \n");																									 
		}
	  sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME       \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                       \n");										

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}	
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}	
        
    return template.getList(conn, parameters);
  }

	/* �μ��� ��ȸ */
	public static List<CommonEntity> getRevDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT  A.M350_PARTCODE, A.M350_PARTNAME  	  \n");  
    sb.append("   FROM  M350_PARTCODE_T A 				   			    \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                 \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE     \n"); 
		sb.append("    AND  A.M350_YEAR = B.M390_YEAR             \n"); 
		sb.append("    AND  B.M390_WORKGUBUN = '1'                \n");
		sb.append("    AND  A.M350_YEAR = ?				                \n");
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND B.M390_ACCGUBUN = ?                    \n");																									 
		}
		sb.append("	GROUP BY A.M350_PARTCODE, A.M350_PARTNAME     \n");
    sb.append(" ORDER BY A.M350_PARTCODE									    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		} 
        
    return template.getList(conn, parameters);
  }

	/* ����� ��ȸ */
	public static List<CommonEntity> getRevSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  A.M370_SEMOKCODE, A.M370_SEMOKNAME      \n");		
    sb.append("   FROM  M370_SEMOKCODE_T A                      \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                   \n");       
    sb.append("  WHERE  A.M370_YEAR = B.M390_YEAR               \n");
		sb.append("    AND  A.M370_ACCGUBUN = B.M390_ACCGUBUN       \n"); 
		sb.append("    AND  A.M370_SEMOKCODE = B.M390_SEMOKCODE     \n"); 
		sb.append("    AND  A.M370_ACCCODE = B.M390_ACCCODE         \n");
		sb.append("    AND  A.M370_WORKGUBUN = B.M390_WORKGUBUN     \n");
		sb.append("    AND  A.M370_WORKGUBUN = '1'                  \n");
		sb.append("    AND  A.M370_YEAR = ?				                  \n");
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M370_ACCGUBUN = ?                      \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                      \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M370_ACCCODE = ?                       \n");																									 
		}
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME     \n"); 
		sb.append(" ORDER BY A.M370_SEMOKCODE                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		} 
		if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		} 
       
    return template.getList(conn, parameters);
  }

	/* ����� ��ȸ */
	public static List<CommonEntity> getRevWriteList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT   B.M350_PARTNAME                              \n");	
		sb.append("         ,C.M360_ACCNAME                               \n");	
		sb.append("         ,D.M370_SEMOKNAME                             \n");
		sb.append("		      ,A.M030_SEQ                                   \n"); 	//�Ϸù�ȣ
		sb.append("         ,A.M030_YEAR                                  \n");	  //ȸ�迬��
		sb.append("	        ,A.M030_DATE                                  \n"); 	//ȸ������
		sb.append("	        ,A.M030_ACCTYPE                               \n"); 	//ȸ�豸��
		sb.append("         ,DECODE(A.M030_ACCTYPE, 'A', '�Ϲ�ȸ��',      \n");   
    sb.append("                                 'B', 'Ư��ȸ��',      \n");
    sb.append("                                 'E', '���'           \n");
		sb.append("                ) M030_ACCTYPE_NM                      \n");
		sb.append("	        ,A.M030_PARTCODE                              \n"); 	//�μ�
		sb.append("		      ,A.M030_ACCCODE                               \n"); 	//ȸ���
		sb.append("         ,A.M030_INTYPE                                \n");   //�Է±���
		sb.append("         ,DECODE(A.M030_INTYPE, 'I1', '����',          \n");   
    sb.append("                                'I2', '�ݳ�',          \n");
    sb.append("                                'I3', '����'           \n");
		sb.append("                ) M030_INTYPE_NM                       \n");
		sb.append("		      ,A.M030_SEMOKCODE                             \n"); 	//�����	
		sb.append("		      ,A.M030_ORDERNAME                             \n"); 	//ä�ּ���
		sb.append("		      ,A.M030_ORDERNO                               \n"); 	//���޹�ȣ 			
		sb.append("		      ,A.M030_AMT                                   \n");   //�ݾ�
		sb.append("   FROM  M030_TAXOTHER_T A                             \n");
		sb.append("        ,M350_PARTCODE_T B                             \n");	  //�μ� ����
		sb.append("        ,M360_ACCCODE_T C                              \n");		//ȸ��� ����
		sb.append("        ,M370_SEMOKCODE_T D                            \n");		//����� ����
		sb.append("  WHERE  A.M030_YEAR = B.M350_YEAR                     \n");
		sb.append("    AND  A.M030_YEAR = C.M360_YEAR                     \n");
		sb.append("    AND  A.M030_YEAR = D.M370_YEAR                     \n");
		sb.append("    AND  A.M030_PARTCODE = B.M350_PARTCODE             \n");
		sb.append("    AND  A.M030_ACCCODE = C.M360_ACCCODE               \n");
		sb.append("    AND  A.M030_ACCTYPE = C.M360_ACCGUBUN              \n");
		sb.append("    AND  A.M030_ACCTYPE = D.M370_ACCGUBUN              \n");
		sb.append("    AND  A.M030_ACCCODE = D.M370_ACCCODE               \n");
		sb.append("    AND  A.M030_SEMOKCODE = D.M370_SEMOKCODE           \n");
    sb.append("    AND  D.M370_WORKGUBUN = '1'                        \n");
		sb.append("    AND  A.M030_YEAR = ?                               \n");
		sb.append("		 AND  A.M030_DATE = ?                               \n");																									 
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M030_ACCTYPE = ?                             \n");																									 
		}
    if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND A.M030_PARTCODE = ?                            \n");																									 
		}
    if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M030_ACCCODE = ?                             \n");																									 
		}
    if (!"".equals(paramInfo.getString("intype"))){
		  sb.append("	 AND A.M030_INTYPE = ?                              \n");																									 
		}	
    sb.append("    ORDER BY A.M030_SEQ                                \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
    if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
    if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
    if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
    if (!"".equals(paramInfo.getString("intype")) )	{
			parameters.setString(idx++, paramInfo.getString("intype"));
		}
  
		return template.getList(conn, parameters);
	}


	/* ����� �� */
	public static CommonEntity getRevWriteView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   																						                      
		sb.append(" SELECT   B.M350_PARTNAME                              \n");	
		sb.append("         ,C.M360_ACCNAME                               \n");	
		sb.append("         ,D.M370_SEMOKNAME                             \n");
		sb.append("		      ,A.M030_SEQ                                   \n"); 	//�Ϸù�ȣ
		sb.append("         ,A.M030_YEAR                                  \n");	  //ȸ�迬��
		sb.append("	        ,A.M030_DATE                                  \n"); 	//ȸ������
		sb.append("	        ,A.M030_ACCTYPE                               \n"); 	//ȸ�豸��
		sb.append("         ,DECODE(A.M030_ACCTYPE, 'A', '�Ϲ�ȸ��',      \n");   
    sb.append("                                 'B', 'Ư��ȸ��',      \n");
    sb.append("                                 'E', '���'           \n");
		sb.append("                ) M030_ACCTYPE_NM                      \n");
		sb.append("	        ,A.M030_PARTCODE                              \n"); 	//�μ�
		sb.append("		      ,A.M030_ACCCODE                               \n"); 	//ȸ���
		sb.append("         ,A.M030_INTYPE                                \n");   //�Է±���
		sb.append("         ,DECODE(A.M030_INTYPE, 'I1', '����',          \n");   
    sb.append("                                'I2', '�ݳ�',          \n");
    sb.append("                                'I3', '����'           \n");
		sb.append("                ) M030_INTYPE_NM                       \n");
		sb.append("		      ,A.M030_SEMOKCODE                             \n"); 	//�����	
		sb.append("		      ,A.M030_ORDERNAME                             \n"); 	//ä�ּ���
		sb.append("		      ,A.M030_ORDERNO                               \n"); 	//���޹�ȣ 			
		sb.append("		      ,A.M030_AMT                                   \n");   //�ݾ�
		sb.append("   FROM  M030_TAXOTHER_T A                             \n");
		sb.append("        ,M350_PARTCODE_T B                             \n");	  //�μ� ����
		sb.append("        ,M360_ACCCODE_T C                              \n");		//ȸ��� ����
		sb.append("        ,M370_SEMOKCODE_T D                            \n");		//����� ����
		sb.append("  WHERE  A.M030_YEAR = B.M350_YEAR                     \n");
		sb.append("    AND  A.M030_YEAR = C.M360_YEAR                     \n");
		sb.append("    AND  A.M030_YEAR = D.M370_YEAR                     \n");
		sb.append("    AND  A.M030_PARTCODE = B.M350_PARTCODE             \n");
		sb.append("    AND  A.M030_ACCCODE = C.M360_ACCCODE               \n");
		sb.append("    AND  A.M030_ACCTYPE = C.M360_ACCGUBUN              \n");
		sb.append("    AND  A.M030_ACCTYPE = D.M370_ACCGUBUN              \n");
		sb.append("    AND  A.M030_ACCCODE = D.M370_ACCCODE               \n");
		sb.append("    AND  A.M030_SEMOKCODE = D.M370_SEMOKCODE           \n");
    sb.append("    AND  D.M370_WORKGUBUN = '1'                        \n");
		sb.append("    AND  A.M030_YEAR = ?	                              \n");
		sb.append("		 AND  A.M030_SEQ = ?                                \n");
    sb.append("    AND  A.M030_ACCTYPE = ?	                          \n");
		sb.append("		 AND  A.M030_PARTCODE = ?                           \n");
    sb.append("    AND  A.M030_ACCCODE = ?	                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;

	  parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("m030_seq"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
		
    return template.getData(conn, parameters);
	}
		

	/* ����� ���� */
	public static int revWriteUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M030_TAXOTHER_T               \n");
		sb.append("   SET M030_ACCTYPE = ?              \n");				//ȸ�豸��
		sb.append("      ,M030_PARTCODE	= ?             \n");			  //�μ�
		sb.append("      ,M030_ACCCODE = ?              \n");				//ȸ���
		sb.append("      ,M030_SEMOKCODE	= ?           \n");			  //�����
		sb.append("      ,M030_INTYPE = ?               \n");				//���ⱸ��
		sb.append("      ,M030_ORDERNAME	= ?           \n");			  //ä�ּ���
		sb.append("      ,M030_ORDERNO = ?              \n");				//���޹�ȣ	
		sb.append("      ,M030_AMT	= ?                 \n");				//�ݾ�
		sb.append(" WHERE M030_YEAR = ?	                \n");
		sb.append("	  AND M030_SEQ = ?                  \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("order_name"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m030_seq"));
		
		return template.update(conn, parameters);
	}

  /* ����� ����  (���ϸ������� 'N') */
	public static int revWriteUpdate1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M030_TAXOTHER_T               \n");
		sb.append("   SET M030_ORDERNAME = ?            \n");				//ä�ּ���
		sb.append("      ,M030_ORDERNO	= ?             \n");			  //���޹�ȣ
		sb.append(" WHERE M030_YEAR = ?	                \n");
		sb.append("	  AND M030_SEQ = ?                  \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("order_name"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m030_seq"));
		
		return template.update(conn, parameters);
	}

	/* ����� ���� (���ϸ������� 'Y') */
	public static int revWriteDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M030_TAXOTHER_T          \n");
		sb.append(" WHERE M030_YEAR = ?                 \n");	//ȸ�迬��
		sb.append("   AND M030_SEQ = ?                  \n");	//�Ϸù�ȣ
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		parameters.setString(1, paramInfo.getString("fis_year"));
		parameters.setString(2, paramInfo.getString("m030_seq"));
		
		return template.delete(conn, parameters);
	}
}