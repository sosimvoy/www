/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090610.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > ���ϰ���
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091010 {

	/* ���� �� */
	public static List getHolList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   
    sb.append("          SELECT M290_DATE,                        \n");       
    sb.append("                 M290_HOLNAME                      \n");   
    sb.append("            FROM M290_HOLIDAY_T                    \n");
    sb.append("           WHERE SUBSTR(M290_DATE,1,4)  = ?        \n");
		sb.append("             AND M290_HOLNAME LIKE ?               \n");    
    sb.append("        ORDER BY M290_DATE    DESC                 \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;

		  parameters.setString(i++, paramInfo.getString("yyyy"));
      parameters.setString(i++, "%"+paramInfo.getString("hol_name")+"%");

  
		return template.getList(conn, parameters);
	}
	 
	 /* ���� ��� */
	 public static int getInsertHol(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M290_HOLIDAY_T               \n");
		sb.append("           ( M290_DATE,                   \n"); 
		sb.append("	            M290_HOLNAME )               \n");
		sb.append("     VALUES(                              \n");
		sb.append("               ?,                         \n");
		sb.append("								?          )               \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("hol_date"));
		parameters.setString(i++, paramInfo.getString("holName"));


		return template.insert(conn, parameters); 
	}
	
	/* ���� ���� */ 
  public static int deleteHol(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M290_HOLIDAY_T    \n");
    sb.append("  WHERE M290_DATE = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("date"));

    return template.delete(conn, parameters);
  }
		/* ���� �� ��ȸ */
	public static CommonEntity getHolView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("   SELECT M290_DATE ,            \n"); 
    sb.append("          M290_HOLNAME           \n");   
    sb.append("     FROM M290_HOLIDAY_T         \n");
    sb.append("    WHERE M290_DATE = ?          \n");
    sb.append("    ORDER BY M290_DATE           \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("hol_date"));
	
		return template.getData(conn, parameters);
	}

	/* ���� ���� */ 
  public static int getUpdateHol(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer(); 
   
    sb.append(" UPDATE M290_HOLIDAY_T         \n");
    sb.append("    SET M290_DATE = ?,         \n");
		sb.append("        M290_HOLNAME = ?       \n");
		sb.append(" WHERE  M290_DATE  = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1; 
    
		parameters.setString(idx++, paramInfo.getString("hol_date"));
		parameters.setString(idx++, paramInfo.getString("holName"));
		parameters.setString(idx++, paramInfo.getString("date"));

    return template.update(conn, parameters);
  }
}