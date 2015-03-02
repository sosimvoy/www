/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR010510.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����� ��ȸ/����/����
******************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010510 {
  
	/* ȸ��� ��ȸ */ 
	public static List<CommonEntity> getAccCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT A.M360_ACCCODE, A.M360_ACCNAME       \n");		
    sb.append("   FROM M360_ACCCODE_T A						  	      \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                \n"); 
		sb.append("  WHERE A.M360_YEAR = B.M390_YEAR            \n");  
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN    \n");  
	  sb.append("    AND A.M360_ACCCODE = B. M390_ACCCODE     \n");
		sb.append("    AND B.M390_WORKGUBUN = '0'               \n");
		sb.append("    AND A.M360_YEAR = ?                      \n"); 
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M360_ACCGUBUN = ?                  \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                  \n");																									 
		}

		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME     \n"); 																																												
		sb.append(" ORDER BY A.M360_ACCCODE                     \n");

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
	public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT A.M350_PARTCODE, A.M350_PARTNAME 	    \n");  
    sb.append("   FROM M350_PARTCODE_T A				   			      \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                  \n");       
    sb.append("  WHERE A.M350_YEAR =  B.M390_YEAR             \n");   
		sb.append("    AND A.M350_PARTCODE =  B.M390_PARTCODE     \n");   
		sb.append("    AND B.M390_WORKGUBUN = '0'                 \n");   
		sb.append("    AND A.M350_YEAR = ?                        \n"); 
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND B.M390_ACCGUBUN = ?                    \n");																									 
		}
		sb.append("   GROUP BY A.M350_PARTCODE, A.M350_PARTNAME 	\n");
		sb.append("   ORDER BY A.M350_PARTCODE                    \n");
																																																																																																																				
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
	public static List<CommonEntity> getSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																											
		sb.append(" SELECT A.M370_SEMOKCODE, A.M370_SEMOKNAME     \n");		
    sb.append("   FROM M370_SEMOKCODE_T A                     \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                  \n");       
    sb.append("  WHERE A.M370_YEAR = M390_YEAR                \n");
		sb.append("    AND A.M370_ACCGUBUN =  B.M390_ACCGUBUN     \n"); 
		sb.append("    AND A.M370_ACCCODE =  B.M390_ACCCODE       \n"); 
		sb.append("    AND A.M370_WORKGUBUN = B.M390_WORKGUBUN    \n");
		sb.append("    AND A.M370_SEMOKCODE =  B.M390_SEMOKCODE   \n");
		sb.append("    AND A.M370_WORKGUBUN = '0'                 \n");
		sb.append("    AND A.M370_YEAR = ?                        \n");
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M370_ACCGUBUN = ?                    \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                    \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M370_ACCCODE = ?                     \n");																									 
		}
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME   \n"); 
		sb.append(" ORDER BY A.M370_SEMOKCODE                     \n");

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

  /* ������� ��ȸ */ 
	public static List<CommonEntity> getGigwanList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  M430_SUNAPGIGWANCODE       \n");		
    sb.append(" 			 ,M430_SUNAPGIGWANNAME 	     \n"); 
		sb.append("   FROM  M430_SUNAPGIGWANCODE_T     \n");                    
 
		QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getList(conn);
  }

	/* �������ȸ */
	public static List<CommonEntity> getExpWriteList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT  B.M350_PARTNAME                                   \n");
		sb.append("        ,C.M360_ACCNAME                                    \n");	
		sb.append("        ,D.M370_SEMOKNAME                                  \n");	
		sb.append("        ,E.M430_SUNAPGIGWANNAME                            \n");	
		sb.append("		     ,A.M010_SEQ                                        \n");   //�Ϸù�ȣ    
		sb.append("        ,A.M010_YEAR                                       \n");	  //ȸ�迬��
		sb.append("	       ,A.M010_DATE                                       \n");   //ȸ������
		sb.append("	       ,A.M010_INTYPE                                     \n");   //�Է±���
		sb.append("        ,DECODE(A.M010_INTYPE, 'I1', '����',               \n");	
    sb.append("                               'I2', '������',             \n");
    sb.append("                               'I3', '����'                \n");
    sb.append("               ) M010_INTYPE_NM                            \n");
		sb.append("	       ,A.M010_YEARTYPE                                   \n");   //�⵵����
		sb.append("        ,DECODE(A.M010_YEARTYPE, 'Y1', '���⵵',           \n");	
    sb.append("                                 'Y2', '���⵵'            \n");
    sb.append("               ) M010_YEARTYPE_NM                          \n");
    sb.append("        ,A.M010_SUNAPGIGWANCODE                            \n");   //�������
		sb.append("	       ,A.M010_ACCTYPE                                    \n");   //ȸ�豸��
		sb.append("        ,DECODE(A.M010_ACCTYPE, 'A', '�Ϲ�ȸ��',           \n");	 
    sb.append("                                'B', 'Ư��ȸ��',           \n");
    sb.append("                                'E', '���'                \n");
    sb.append("               ) M010_ACCTYPE_NM                           \n");
		sb.append("		     ,A.M010_PARTCODE                                   \n");   //�μ�
		sb.append("		     ,A.M010_ACCCODE                                    \n");   //ȸ���
		sb.append("		     ,A.M010_SEMOKCODE                                  \n");   //�����
		sb.append("		     ,A.M010_AMT                                        \n");   //�ݾ�
		sb.append("		     ,A.M010_CNT                                        \n");   //�Ǽ�
		sb.append("   FROM  M010_TAXIN_T A                                    \n");
		sb.append("        ,M350_PARTCODE_T B                                 \n");	 //�μ� ����
		sb.append("        ,M360_ACCCODE_T C                                  \n");	 //ȸ��� ����	
		sb.append("        ,M370_SEMOKCODE_T D                                \n");	 //����� ����
		sb.append("        ,M430_SUNAPGIGWANCODE_T E                          \n");	 //������� ����
		sb.append("  WHERE  A.M010_YEAR = B.M350_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = C.M360_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = D.M370_YEAR                         \n");
    sb.append("    AND  A.M010_ACCCODE = C.M360_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = C.M360_ACCGUBUN                  \n");
		sb.append("    AND  A.M010_PARTCODE = B.M350_PARTCODE                 \n");
		sb.append("    AND  A.M010_SEMOKCODE = D.M370_SEMOKCODE               \n");
		sb.append("    AND  A.M010_ACCCODE = D.M370_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = D.M370_ACCGUBUN                  \n");
    sb.append("    AND  D.M370_WORKGUBUN = '0'                            \n");
		sb.append("    AND  A.M010_SUNAPGIGWANCODE = E.M430_SUNAPGIGWANCODE   \n");	
    sb.append("    AND  A.M010_YEAR = ?                                   \n");
		sb.append("    AND  A.M010_DATE = ?                                   \n");
    if (!"".equals(paramInfo.getString("intype"))){
		  sb.append("	 AND A.M010_INTYPE = ?                                  \n");																									 
		}
    if (!"".equals(paramInfo.getString("gigwan"))){
		  sb.append("	 AND A.M010_SUNAPGIGWANCODE = ?                         \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M010_ACCTYPE = ?                                 \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND A.M010_PARTCODE = ?                                \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M010_ACCCODE = ?                                 \n");																									 
		}
		if (!"".equals(paramInfo.getString("semok_code"))){
		  sb.append("	 AND A.M010_SEMOKCODE = ?                               \n");																									 
		}
		if (!"".equals(paramInfo.getString("amt"))){
		  sb.append("	 AND A.M010_AMT = ?                                     \n");																									 
		}
    sb.append("    ORDER BY A.M010_SEQ                                    \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_date"));
		if (!"".equals(paramInfo.getString("intype")) )	{
			parameters.setString(idx++, paramInfo.getString("intype"));
		}
    if (!"".equals(paramInfo.getString("gigwan")) )	{
			parameters.setString(idx++, paramInfo.getString("gigwan"));
		}
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		} 
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		} 
    if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		if (!"".equals(paramInfo.getString("semok_code")) )	{
			parameters.setString(idx++, paramInfo.getString("semok_code"));
		} 
		if (!"".equals(paramInfo.getString("amt")) )	{
			parameters.setString(idx++, paramInfo.getString("amt"));
		} 
   
    return template.getList(conn, parameters);
  }


  /* ����� �� */
  public static CommonEntity getExpWriteView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT  B.M350_PARTNAME                                   \n");
		sb.append("        ,C.M360_ACCNAME                                    \n");	
		sb.append("        ,D.M370_SEMOKNAME                                  \n");	
		sb.append("        ,E.M430_SUNAPGIGWANNAME                            \n");	
		sb.append("		     ,A.M010_SEQ                                        \n");   //�Ϸù�ȣ    
		sb.append("        ,A.M010_YEAR                                       \n");	  //ȸ�迬��
		sb.append("	       ,A.M010_DATE                                       \n");   //ȸ������
		sb.append("	       ,A.M010_INTYPE                                     \n");   //�Է±���
		sb.append("        ,DECODE(A.M010_INTYPE, 'I1', '����',               \n");	
    sb.append("                               'I2', '������',             \n");
    sb.append("                               'I3', '����'                \n");
    sb.append("               ) M010_INTYPE_NM                            \n");
		sb.append("	       ,A.M010_YEARTYPE                                   \n");   //�⵵����
		sb.append("        ,DECODE(A.M010_YEARTYPE, 'Y1', '���⵵',           \n");	
    sb.append("                                 'Y2', '���⵵'            \n");
    sb.append("               ) M010_YEARTYPE_NM                          \n");
    sb.append("        ,A.M010_SUNAPGIGWANCODE                            \n");   //�������
		sb.append("	       ,A.M010_ACCTYPE                                    \n");   //ȸ�豸��
		sb.append("        ,DECODE(A.M010_ACCTYPE, 'A', '�Ϲ�ȸ��',           \n");	 
    sb.append("                                'B', 'Ư��ȸ��',           \n");
    sb.append("                                'E', '���'                \n");
    sb.append("               ) M010_ACCTYPE_NM                           \n");
		sb.append("		     ,A.M010_PARTCODE                                   \n");   //�μ�
		sb.append("		     ,A.M010_ACCCODE                                    \n");   //ȸ���
		sb.append("		     ,A.M010_SEMOKCODE                                  \n");   //�����
		sb.append("		     ,A.M010_AMT                                        \n");   //�ݾ�
		sb.append("		     ,A.M010_CNT                                        \n");   //�Ǽ�
		sb.append("   FROM  M010_TAXIN_T A                                    \n");
		sb.append("        ,M350_PARTCODE_T B                                 \n");	 //�μ� ����
		sb.append("        ,M360_ACCCODE_T C                                  \n");	 //ȸ��� ����	
		sb.append("        ,M370_SEMOKCODE_T D                                \n");	 //����� ����
		sb.append("        ,M430_SUNAPGIGWANCODE_T E                          \n");	 //������� ����
		sb.append("  WHERE  A.M010_YEAR = B.M350_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = C.M360_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = D.M370_YEAR                         \n");
    sb.append("    AND  A.M010_ACCCODE = C.M360_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = C.M360_ACCGUBUN                  \n");
		sb.append("    AND  A.M010_PARTCODE = B.M350_PARTCODE                 \n");
		sb.append("    AND  A.M010_SEMOKCODE = D.M370_SEMOKCODE               \n");
		sb.append("    AND  A.M010_ACCCODE = D.M370_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = D.M370_ACCGUBUN                  \n");
    sb.append("    AND  D.M370_WORKGUBUN = '0'                            \n");
		sb.append("    AND  A.M010_SUNAPGIGWANCODE = E.M430_SUNAPGIGWANCODE   \n");	
		sb.append("    AND  A.M010_YEAR = ?	                                  \n");
		sb.append("		 AND  A.M010_SEQ = ?                                    \n");
    sb.append("    AND  A.M010_ACCTYPE = ?	                              \n");
		sb.append("		 AND  A.M010_PARTCODE = ?                               \n");
    sb.append("    AND  A.M010_ACCCODE = ?	                              \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;

	  parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("m010_seq"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
	
		return template.getData(conn, parameters);
	}


	/* ����� ���� (���ϸ������� 'N')*/
	public static int expWriteUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M010_TAXIN_T                \n");
	  sb.append("	  SET M010_INTYPE = ?             \n");  //�Է±���
		sb.append("      ,M010_YEARTYPE = ? 		      \n");	 //�⵵����
    sb.append("      ,M010_SUNAPGIGWANCODE = ? 		\n");	 //�������
		sb.append("      ,M010_ACCTYPE = ? 			      \n");	 //ȸ�豸��
		sb.append("	     ,M010_PARTCODE = ? 		      \n");	 //�μ�
		sb.append("      ,M010_ACCCODE = ? 			      \n");	 //ȸ���
		sb.append("	     ,M010_SEMOKCODE = ? 		      \n");	 //�����
		sb.append("	     ,M010_AMT = ?					      \n");	 //�ݾ�
		sb.append("	     ,M010_CNT = ?					      \n");	 //�Ǽ�
		sb.append(" WHERE M010_YEAR = ? 	            \n");
		sb.append("	  AND M010_SEQ = ?                \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("year_type"));
    parameters.setString(idx++, paramInfo.getString("gigwan"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));

		return template.update(conn, parameters);
	}

  /* ����� ���� (���ϸ������� 'Y') */
	public static int expWriteUpdate1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M010_TAXIN_T                \n");
		sb.append("   SET M010_CNT = ? 		            \n");	 //�Ǽ�
		sb.append(" WHERE M010_YEAR = ? 	            \n");
		sb.append("	  AND M010_SEQ = ?                \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));

		return template.update(conn, parameters);
	}


	/* ����� ���� */
	public static int expWriteDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M010_TAXIN_T             \n");
		sb.append(" WHERE M010_YEAR = ?                 \n");	//ȸ�迬��
		sb.append("   AND M010_SEQ = ?                  \n");	//�Ϸù�ȣ
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));
		
		return template.delete(conn, parameters);
	}
}	
