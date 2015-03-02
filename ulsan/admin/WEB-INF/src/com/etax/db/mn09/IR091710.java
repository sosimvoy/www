/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091710.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �μ��ڵ����
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091710 {

	/* �μ��ڵ� ��ȸ */
	public static List getusePartList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M350_PARTCODE                              \n");
   sb.append("      , M350_PARTNAME                              \n");
   sb.append("   FROM M350_PARTCODE_T                            \n");
   sb.append("  WHERE M350_YEAR = TO_CHAR(SYSDATE,'YYYY')        \n");
   sb.append("  ORDER BY M350_PARTCODE                           \n");

    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
  
		return template.getList(conn, parameters);
	}

	/* �μ��ڵ� ��ȸ */
	public static List getincomePartList(Connection conn, CommonEntity paramInfo)throws SQLException {
   StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M551_PARTCODE                               \n");
   sb.append("      , M551_PARTNAME                               \n");
   sb.append("   FROM M551_INCOMEPART_T                           \n");
   sb.append("  WHERE M551_YEAR = TO_CHAR(SYSDATE,'YYYY')         \n");
   sb.append("  ORDER BY M551_PARTCODE                            \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
  
		return template.getList(conn, parameters);
	}
	/* �μ��ڵ� ��ȸ */
	public static List getstandardpartkList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M410_YEAR                                                            \n");
   sb.append("      , M410_STANDARDPARTCODE                                                \n");
   sb.append("      , M410_PARTNAME                                                        \n");
   sb.append("      , M350_PARTNAME AS M410_SYSTEMPARTCODE                                 \n");
   sb.append("      , M551_PARTNAME AS M410_BUDGETPARTCODE                                 \n");
   sb.append("   FROM M410_STANDARDPARTCODE_T  A                                           \n");
   sb.append("      , M350_PARTCODE_T  B                                                   \n");
   sb.append("      , M551_INCOMEPART_T  C                                                 \n");
   sb.append("  WHERE M410_YEAR             = M350_YEAR(+)                                 \n");
   sb.append("    AND M410_SYSTEMPARTCODE = M350_PARTCODE(+)                               \n");
   sb.append("    AND M410_YEAR             = C.M551_YEAR(+)                               \n");
   sb.append("    AND M410_BUDGETPARTCODE   = C.M551_PARTCODE(+)                           \n");
   sb.append("    AND M410_YEAR             = ?                                            \n");
   sb.append(" ORDER BY M410_YEAR                                                          \n");
   sb.append("      , M410_STANDARDPARTCODE                                                 \n");
   sb.append("      , M410_PARTNAME                                                         \n");

    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
  
		return template.getList(conn, parameters);
	}
	 /* �μ� �ڵ� ��� */
	 public static int insertStdpartCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M410_STANDARDPARTCODE_T    \n");
		sb.append("           ( M410_YEAR,                 \n"); 
		sb.append("	            M410_STANDARDPARTCODE,     \n"); 
		sb.append("	            M410_PARTNAME,             \n"); 
		sb.append("	            M410_SYSTEMPARTCODE,       \n"); 
		sb.append("	            M410_BUDGETPARTCODE)       \n");
		sb.append("     VALUES(                            \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("								?           )            \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("stdPart"));
    parameters.setString(i++, paramInfo.getString("stdPartName"));
		parameters.setString(i++, paramInfo.getString("sysPartcode"));
		parameters.setString(i++, paramInfo.getString("incomePart"));

		return template.insert(conn, parameters); 
	}

		/* �μ� �ڵ� ���� */ 
  public static int deletestandardsemokk(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M410_STANDARDPARTCODE_T    \n");
    sb.append("  WHERE  M410_YEAR = ?                   \n");
    sb.append("    AND  M410_STANDARDPARTCODE = ?       \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("delyear"));
		parameters.setString(idx++, paramInfo.getString("delPart"));

    return template.delete(conn, parameters);
  }
}