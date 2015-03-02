/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR010410.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��(���,Ư��ȸ��)
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010410 {
  
	/* ȸ��� ��ȸ */ 
	public static List<CommonEntity> getSpecNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME      \n");		
    sb.append("   FROM  M360_ACCCODE_T A					  	      \n");   
		sb.append("        ,M390_USESEMOKCODE_T B 	            \n"); 
		sb.append("  WHERE  A.M360_YEAR = B.M390_YEAR	          \n");  
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");  
	  sb.append("    AND  A.M360_ACCCODE = B.M390_ACCCODE			\n");
		sb.append("    AND  B.M390_ACCGUBUN IN ('B','E')        \n"); 
		sb.append("    AND  B.M390_WORKGUBUN = '0'              \n");
		sb.append("    AND  A.M360_YEAR = ?                     \n"); 
		sb.append("    AND  A.M360_ACCGUBUN = ?                 \n"); 
    sb.append("    AND  B.M390_PARTCODE = ?                 \n"); 
	  sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME     \n"); 																																												
		sb.append(" ORDER BY A.M360_ACCCODE                     \n"); 		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("part_code"));

    return template.getList(conn, parameters);
  }


  /* �⺻ ȸ���ڵ� ����Ʈ */ 
	public static List<CommonEntity> getAccCodeList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME      \n");		
    sb.append("   FROM  M360_ACCCODE_T A					  	      \n");   
		sb.append("        ,M390_USESEMOKCODE_T B 	            \n"); 
		sb.append("  WHERE  A.M360_YEAR = B.M390_YEAR	          \n");  
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");  
	  sb.append("    AND  A.M360_ACCCODE = B.M390_ACCCODE			\n");
		sb.append("    AND  B.M390_ACCGUBUN IN ('B','E')        \n"); 
		sb.append("    AND  B.M390_WORKGUBUN = '0'              \n");
		sb.append("    AND  A.M360_YEAR = ?                     \n"); 
		sb.append("    AND  A.M360_ACCGUBUN = ?                 \n"); 
    sb.append("    AND  B.M390_PARTCODE = ?                 \n"); 
	  sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME     \n");
		sb.append(" ORDER BY A.M360_ACCCODE                     \n"); 		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("part_code"));

    return template.getList(conn, parameters);
  }


	/* �μ��� ��ȸ */
	public static List<CommonEntity> getSpecDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M350_PARTCODE,  A.M350_PARTNAME  	   \n");  
    sb.append("   FROM  M350_PARTCODE_T A 				   			     \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                  \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE      \n");  
		sb.append("    AND  B.M390_ACCGUBUN IN ('B','E')           \n"); 
		sb.append("    AND  B.M390_WORKGUBUN = '0'                 \n");
		sb.append("    AND  A.M350_YEAR = ?                        \n"); 
		sb.append("    AND  B.M390_ACCGUBUN = ?                    \n"); 
		sb.append("   GROUP BY A.M350_PARTCODE,  A.M350_PARTNAME   \n");
		sb.append("   ORDER BY A.M350_PARTCODE                     \n");
																																																																																																											
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
 
    return template.getList(conn, parameters);
  }


	/* ����� ��ȸ */ 
	public static List<CommonEntity> getSpecSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M370_SEMOKCODE, A.M370_SEMOKNAME       \n");		
    sb.append("   FROM  M370_SEMOKCODE_T A                       \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                    \n");       
    sb.append("  WHERE  A.M370_ACCGUBUN IN ('B','E')             \n");
		sb.append("    AND  A.M370_WORKGUBUN = '0'                   \n"); 
    sb.append("    AND  A.M370_YEAR = B.M390_YEAR                \n"); 
    sb.append("    AND  A.M370_ACCGUBUN = B.M390_ACCGUBUN        \n"); 
    sb.append("    AND  A.M370_ACCCODE  = B.M390_ACCCODE         \n"); 
		sb.append("    AND  A.M370_WORKGUBUN = B.M390_WORKGUBUN      \n"); 
		sb.append("    AND  A.M370_SEMOKCODE = B.M390_SEMOKCODE      \n"); 
		sb.append("    AND  A.M370_YEAR = ?                          \n");   
		sb.append("    AND  A.M370_ACCGUBUN = ?                      \n");   
		sb.append("    AND  B.M390_PARTCODE = ?                      \n");
		sb.append("    AND  A.M370_ACCCODE = ?                       \n"); 
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME      \n"); 
		sb.append(" ORDER BY A.M370_SEMOKCODE                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
   
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
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

	/* ����е��(���,Ư��ȸ��) */
  public static int writeSpecial(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();


    sb.append(" INSERT INTO M010_TAXIN_T                       \n");
		sb.append(" (M010_SEQ, M010_YEAR                           \n");  //�Ϸù�ȣ, ȸ�迬��
		sb.append(" ,M010_DATE, M010_INTYPE												 \n");	//ȸ������, �Է±��� 
		sb.append(" ,M010_SUNAPGIGWANCODE, M010_ACCTYPE            \n");  //�������, ȸ�豸��
    sb.append(" ,M010_PARTCODE, M010_ACCCODE                   \n");  //�μ�, ȸ��� 
		sb.append(" ,M010_SEMOKCODE, M010_AMT                      \n");	//�����, �ݾ� 
		sb.append("	,M010_YEARTYPE, M010_LOGNO                     \n");  //�⵵����, �α�
		sb.append("	,M010_WORKTYPE ,M010_TRANSGUBUN)               \n");  
		sb.append(" VALUES (M010_SEQ.NEXTVAL, ?						\n");
	  sb.append(" 			 ,?, ?						            \n");
		sb.append(" 			 ,?, ?						                       \n");
		sb.append(" 			 ,?, ?						                       \n"); 
		sb.append(" 			 ,?, ?						                       \n"); 
		sb.append(" 			 ,?, ?						                       \n");
		sb.append(" 			 ,'A01', '141')					                 \n"); 
																					   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("gigwan"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		

		return template.insert(conn, parameters); 	
   }
}