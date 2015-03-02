/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030110.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-19
* ���α׷�����	   : ���Լ�������� > ����е��
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030110 {

 /* ȸ��� ��ȸ */ 
	public static List<CommonEntity> getAccNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME     \n");		
    sb.append("   FROM  M360_ACCCODE_T A 						  	   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B              \n");       
		sb.append("  WHERE  B.M390_WORKGUBUN = '4'		  			 \n");
		sb.append("    AND  A.M360_ACCGUBUN ='D'               \n");
    sb.append("    AND  A.M360_YEAR = B.M390_YEAR					 \n"); 
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN	 \n"); 
    sb.append("    AND  A.M360_ACCCODE =  B.M390_ACCCODE	 \n");
		sb.append("    AND  A.M360_YEAR = ?				             \n");
    sb.append("    AND  B.M390_PARTCODE = ?                \n"); 
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME    \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                    \n");		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getList(conn, parameters);
  }

	/* �μ��� ��ȸ */
	public static List<CommonEntity> getCashDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M350_PARTCODE, M350_PARTNAME       \n");  
    sb.append("   FROM  M350_PARTCODE_T A  				   			   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("    AND  A.M350_YEAR = B.M390_YEAR            \n"); 
		sb.append("    AND  B.M390_ACCGUBUN ='D'                 \n");
		sb.append("    AND  B.M390_WORKGUBUN = '4'               \n");
		sb.append("    AND  A.M350_YEAR = ?				               \n");
		sb.append("  GROUP BY A.M350_PARTCODE, M350_PARTNAME     \n");
		sb.append(" ORDER BY A.M350_PARTCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        
    return template.getList(conn, parameters);
  }


 /* ����е��  */
  public static int cashInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M040_TAXCASH_T              \n");
		sb.append("       ( M040_SEQ   					            \n");	 
		sb.append("        ,M040_YEAR 					            \n");	 
		sb.append("        ,M040_DATE 					            \n");	 
		sb.append("        ,M040_PARTCODE 			            \n");	 
		sb.append("        ,M040_ACCCODE 				            \n");	 
		sb.append("        ,M040_DWTYPE 				            \n");	 
		sb.append("        ,M040_INTYPE 				            \n");
		sb.append("        ,M040_CASHTYPE 			            \n");	 
		sb.append("        ,M040_DEPOSITTYPE 		            \n");
		sb.append("        ,M040_ORDERNAME                  \n");
		sb.append("        ,M040_NOTE                       \n");
		sb.append("        ,M040_ORDERNO                    \n");		 
		sb.append("        ,M040_CNT 					           	  \n");	 
		sb.append("        ,M040_AMT 						            \n");	 
		sb.append("        ,M040_LOGNO 					            \n");	 
		sb.append("        ,M040_WORKTYPE 			            \n");	 
		sb.append("        ,M040_TRANSGUBUN  )	            \n");	 
		sb.append(" VALUES( M040_SEQ.NEXTVAL		            \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");	
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?                              \n");
	  sb.append("         ,?                              \n");
		sb.append("         ,?                              \n");
		sb.append("         ,?					                    \n"); 
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,'A03'					                \n");
		sb.append("         ,'111' )					              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("dwtype"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("cash_type"));
		parameters.setString(idx++, paramInfo.getString("deposit_type"));
	  parameters.setString(idx++, paramInfo.getString("order_name"));
	  parameters.setString(idx++, paramInfo.getString("note"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		return template.insert(conn, parameters);
	}

  /* ���޹�ȣ �ߺ� ���� Ȯ�� */
	public static CommonEntity getOrderNo(Connection conn, CommonEntity paramInfo) throws SQLException {
	  StringBuffer sb = new StringBuffer();
		
    sb.append("	SELECT  COUNT (M040_ORDERNO) NO_CNT             \n");
    sb.append("   FROM  M040_TAXCASH_T                          \n");
    sb.append("  WHERE  M040_YEAR	= ?                           \n");   //ȸ��⵵
    sb.append("    AND  M040_DATE	= ?                           \n");   //ȸ������
    sb.append("    AND  M040_PARTCODE	= ?                       \n");   //�μ�
    sb.append("    AND  M040_ACCCODE = ?                        \n");   //ȸ���
    sb.append("    AND  M040_ORDERNO = ?                        \n");   //���޹�ȣ

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;

		parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("fis_date"));	
		parameters.setString(idx++,  paramInfo.getString("part_code"));	
    parameters.setString(idx++,  paramInfo.getString("acc_code"));	
    parameters.setString(idx++,  paramInfo.getString("order_no"));	

		return template.getData(conn, parameters);
	}

}
