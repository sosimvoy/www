/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR010310.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��(���ܼ���)
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010310 {
	
	/* ȸ��� ��ȸ */
	public static List<CommonEntity> getImpNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME        \n");
    sb.append("   FROM  M360_ACCCODE_T A						          \n");
    sb.append("        ,M390_USESEMOKCODE_T B                 \n");   
    sb.append("  WHERE  A.M360_ACCGUBUN = 'A'									\n");
    sb.append("    AND  A.M360_YEAR = B.M390_YEAR							\n");
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN			\n");
    sb.append("    AND  A.M360_ACCCODE = B.M390_ACCCODE				\n");
    sb.append("    AND  B.M390_WORKGUBUN = '0'								\n");
    sb.append("    AND  SUBSTR(B.M390_SEMOKCODE,1,1) = 2 			\n");																	
		sb.append("    AND  A.M360_YEAR = ?                       \n"); 
		sb.append("    AND  B.M390_PARTCODE = ?                   \n"); 
	  sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME       \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                       \n");										

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getList(conn, parameters);
  }

	
	/* �μ��� ��ȸ */
	public static List<CommonEntity> getImpDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M350_PARTCODE, M350_PARTNAME     \n");  
    sb.append("   FROM  M350_PARTCODE_T A 			   			   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B 	           \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE  \n");  
		sb.append("    AND  B.M390_ACCGUBUN = 'A'              \n"); 
		sb.append("    AND  B.M390_WORKGUBUN = '0'             \n");
		sb.append("    AND  SUBSTR(B.M390_SEMOKCODE,1,1) = '2' \n");
		sb.append("    AND  A.M350_YEAR = ?                    \n"); 
		sb.append("   GROUP BY A.M350_PARTCODE, M350_PARTNAME  \n");
		sb.append("   ORDER BY A.M350_PARTCODE                 \n");
																																	  																																																																													
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
   
	  parameters.setString(idx++, paramInfo.getString("fis_year"));
		
    return template.getList(conn, parameters);
  }


	/* ����� ��ȸ */ 
	public static List<CommonEntity> getImpSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M370_SEMOKCODE, A.M370_SEMOKNAME    \n");		
    sb.append("   FROM  M370_SEMOKCODE_T A                    \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                 \n");       
    sb.append("  WHERE  A.M370_YEAR = B.M390_YEAR             \n");
		sb.append("    AND  A.M370_ACCGUBUN = B.M390_ACCGUBUN     \n"); 
		sb.append("    AND  A.M370_SEMOKCODE = B.M390_SEMOKCODE   \n"); 
		sb.append("    AND  A.M370_ACCCODE = B.M390_ACCCODE       \n");
		sb.append("    AND  A.M370_WORKGUBUN = B.M390_WORKGUBUN   \n");
		sb.append("    AND  A.M370_ACCGUBUN = 'A'                 \n"); 
		sb.append("    AND  A.M370_WORKGUBUN = '0'                \n"); 
		sb.append("    AND  SUBSTR(A.M370_SEMOKCODE,1,1) = '2'    \n"); 
		sb.append("    AND  A.M370_YEAR = ?                       \n"); 
		sb.append("    AND  B.M390_PARTCODE = ?                   \n"); 
    sb.append("    AND  A.M370_ACCCODE = ?                    \n"); 
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME   \n"); 
		sb.append(" ORDER BY A.M370_SEMOKCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		
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


  /* ����е��(���ܼ���) */
  public static int writeImport(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M010_TAXIN_T              \n");
		sb.append(" (M010_SEQ, M010_YEAR                  \n"); 	//�Ϸù�ȣ, ȸ�迬��
		sb.append(" ,M010_DATE, M010_INTYPE               \n"); 	//ȸ������, �Է±���
		sb.append(" ,M010_SUNAPGIGWANCODE, M010_PARTCODE  \n");   //�������, �μ�
		sb.append(" ,M010_ACCCODE, M010_SEMOKCODE					\n");		//ȸ���, �����
		sb.append(" ,M010_AMT, M010_CNT                   \n");		//�ݾ�, �Ǽ�
    sb.append("	,M010_LOGNO, M010_ACCTYPE             \n");		//�α�, ȸ�豸��
		sb.append("	,M010_WORKTYPE,M010_TRANSGUBUN        \n");		//�ŷ�����
        sb.append("	,M010_YEARTYPE)                       \n");     //��������
		sb.append(" VALUES (M010_SEQ.NEXTVAL, ?						\n");
	  sb.append(" 			 ,?, ?						              \n");
		sb.append(" 			 ,?, ?						              \n");
		sb.append(" 			 ,?, ?					                \n");     
		sb.append(" 			 ,?, ? 													\n");
		sb.append("        ,?, 'A'						            \n");  
		sb.append("        ,'A01','131' 						      \n");  
        sb.append("        ,?)		            				      \n");  
																		 			   
											   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("gigwan"));
		parameters.setString(idx++, paramInfo.getString("part_code"));	
		parameters.setString(idx++, paramInfo.getString("acc_code"));	
		parameters.setString(idx++, paramInfo.getString("semok_code"));	
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
        parameters.setString(idx++, paramInfo.getString("yeartype"));

		return template.insert(conn, parameters); 	
   }
}