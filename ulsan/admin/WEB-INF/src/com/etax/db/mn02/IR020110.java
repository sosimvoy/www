/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020110.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��
******************************************************/
package com.etax.db.mn02;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020110 {

 /* ȸ��� ��ȸ */ 
	public static List<CommonEntity> getRevAccNmList(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
 		
		sb.append("  SELECT  A.M360_ACCCODE, A.M360_ACCNAME       \n"); 
		sb.append("    FROM  M360_ACCCODE_T A                     \n"); 
		sb.append("         ,M390_USESEMOKCODE_T B                \n"); 
		sb.append("   WHERE  B.M390_WORKGUBUN = '1'               \n"); 
		sb.append("     AND  A.M360_YEAR = B.M390_YEAR            \n"); 
		sb.append("     AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN    \n"); 
		sb.append("     AND  A.M360_ACCCODE =  B.M390_ACCCODE     \n");
		sb.append("     AND  A.M360_YEAR = ?                      \n");
		sb.append("     AND  A.M360_ACCGUBUN = ?                  \n");
		sb.append("     AND  B.M390_PARTCODE = ?                  \n");
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME       \n");
		sb.append(" ORDER BY A.M360_ACCCODE                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
        
		return template.getList(conn, parameters);
	}

	/* �⺻ ȸ���ڵ� ����Ʈ */ 
	public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
 		
		sb.append("  SELECT  A.M360_ACCCODE, A.M360_ACCNAME			  \n"); 
		sb.append("    FROM  M360_ACCCODE_T A						  \n"); 
		sb.append("         ,M390_USESEMOKCODE_T B					  \n"); 
		sb.append("   WHERE  B.M390_WORKGUBUN = '1'					  \n"); 
		sb.append("     AND  A.M360_YEAR = B.M390_YEAR				  \n"); 
		sb.append("     AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN		  \n"); 
		sb.append("     AND  A.M360_ACCCODE =  B.M390_ACCCODE		  \n");
		sb.append("     AND  A.M360_YEAR = ?			              \n");
		sb.append("     AND  A.M360_ACCGUBUN = ?					  \n");
		sb.append("     AND  B.M390_PARTCODE = ?					  \n");
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME           \n");
		if ("A".equals(paramInfo.getString("acc_type")) && "00000".equals(paramInfo.getString("part_code")) ){
		    sb.append("	 ORDER BY A.M360_ACCCODE DESC               \n");
		} else {
		    sb.append(" ORDER BY A.M360_ACCCODE                     \n");
		}

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
        
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
		sb.append("    AND  B.M390_ACCGUBUN = ?                   \n"); 
		sb.append("	 GROUP BY A.M350_PARTCODE, A.M350_PARTNAME    \n");
    sb.append("  ORDER BY A.M350_PARTCODE									    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
       
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
		sb.append("    AND  A.M370_YEAR = ?				            \n");
		sb.append("    AND  A.M370_ACCGUBUN = ?                     \n");
		sb.append("    AND  B.M390_PARTCODE = ?                     \n");
		sb.append("    AND  A.M370_ACCCODE = ?                      \n");
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME     \n");
		sb.append(" ORDER BY A.M370_SEMOKCODE                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		
    return template.getList(conn, parameters);
  }

  /* ����е��  */
  public static int revWriteInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M030_TAXOTHER_T                     \n");
		sb.append(" (M030_SEQ, M030_YEAR 							\n");	 //�Ϸù�ȣ, ȸ�迬��
		sb.append(" ,M030_DATE, M030_ACCTYPE						\n");	 //ȸ������, ȸ�豸��
		sb.append(" ,M030_PARTCODE, M030_ACCCODE					\n");	 //�μ�, ȸ���		
		sb.append(" ,M030_SEMOKCODE, M030_INTYPE					\n");	 //�����, �Է±���
		sb.append(" ,M030_ORDERNAME, M030_ORDERNO 					\n");	 //ä�ּ���, ���޹�ȣ
		sb.append("	,M030_AMT, M030_LOGNO                           \n");  //�ݾ� ,�α�	
		sb.append("	,M030_WORKTYPE, M030_TRANSGUBUN)                \n");  //��������, �ŷ�����
		sb.append(" VALUES ( M030_SEQ.NEXTVAL, ?					\n");
		sb.append("       ,?, ?                                     \n");
		sb.append("       ,?, ?                                     \n");
		sb.append("       ,?, ?                                     \n");
		sb.append("       ,?, ?                                     \n");
		sb.append("       ,?, ?                                     \n");
		sb.append("       ,'A02', '111')                            \n");
		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("order_name"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		
		return template.insert(conn, parameters);
	}


  /* ���޹�ȣ �ߺ� ���� Ȯ�� */
	public static CommonEntity getOrderNo(Connection conn, CommonEntity paramInfo) throws SQLException {
	  StringBuffer sb = new StringBuffer();
		
    sb.append("	SELECT  COUNT (M030_ORDERNO) NO_CNT             \n");
    sb.append("   FROM  M030_TAXOTHER_T                         \n");
    sb.append("  WHERE  M030_YEAR	= ?                         \n");   //ȸ��⵵
    sb.append("    AND  M030_DATE	= ?                         \n");   //ȸ������
    sb.append("    AND  M030_ACCTYPE = ?                        \n");   //ȸ�豸��
    sb.append("    AND  M030_PARTCODE	= ?                     \n");   //�μ�
    sb.append("    AND  M030_ACCCODE = ?                        \n");   //ȸ���
    sb.append("    AND  M030_ORDERNO = ?                        \n");   //���޹�ȣ

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;

		parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("fis_date"));
		parameters.setString(idx++,  paramInfo.getString("acc_type"));	
		parameters.setString(idx++,  paramInfo.getString("part_code"));	
    parameters.setString(idx++,  paramInfo.getString("acc_code"));	
    parameters.setString(idx++,  paramInfo.getString("order_no"));	

		return template.getData(conn, parameters);
	}
}