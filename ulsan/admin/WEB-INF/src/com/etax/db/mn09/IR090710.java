/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090610.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > ȸ���ڵ����
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090710 {

	/* ȸ�� �ڵ� ��ȸ */
	public static List getAccCodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("	 SELECT M360_YEAR,			     \n"); 
		sb.append("  DECODE(M360_ACCGUBUN,'A','�Ϲ�ȸ��','B','Ư��ȸ��','C','�����Ư��ȸ��','D','���Լ��������','E','���') ACCGUBUN,   \n");
		sb.append("			    M360_ACCGUBUN, 			 \n");
		sb.append("			    M360_ACCCODE, 			 \n");
		sb.append("			    M360_ACCNAME 			   \n"); 
		sb.append("	   FROM M360_ACCCODE_T       \n");
		sb.append("   WHERE M360_YEAR = ?	       \n");
		sb.append("     AND M360_ACCGUBUN = ?	   \n");
                                                                

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("under_year"));
		parameters.setString(i++,  paramInfo.getString("accGubun"));
  
		return template.getList(conn, parameters);
	}

	 /* ȸ�� �ڵ� ��� */
	 public static int insertAccCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M360_ACCCODE_T        \n");
		sb.append("           ( M360_YEAR,            \n"); 
		sb.append("	            M360_ACCGUBUN,        \n"); 
		sb.append("	            M360_ACCCODE,         \n"); 
		sb.append("	            M360_ACCNAME          \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("								?           )       \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("accGubun1"));
    parameters.setString(i++, paramInfo.getString("accCode"));
		parameters.setString(i++, paramInfo.getString("accName"));

		return template.insert(conn, parameters); 
	}
		
		/* ȸ�� �ڵ� ���� */ 
  public static int deleteAccCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M360_ACCCODE_T      \n");
    sb.append("       WHERE M360_YEAR     = ?   \n");
		sb.append("         AND M360_ACCGUBUN = ?   \n");
		sb.append("         AND M360_ACCCODE  = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));
		parameters.setString(idx++, paramInfo.getString("accCode"));

    return template.delete(conn, parameters);
  }
}