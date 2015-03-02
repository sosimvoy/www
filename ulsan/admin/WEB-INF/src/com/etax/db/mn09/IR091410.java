/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : IR091410.java
* ���α׷��ۼ��� : 
* ���α׷��ۼ��� : 2010-05-06
* ���α׷�����	 : ������ > ������������
******************************************************/
package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091410 {
	private static Logger logger = Logger.getLogger(IR091410.class);	// log4j ����
	
	/* ���� ���� */
	public static CommonEntity getSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append(" SELECT  *                            \n");
		sb.append("   FROM M340_USERSEAL_T               \n");
		sb.append("  WHERE M340_CURRENTORGAN = ?         \n"); 
	
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
               
    parameters.setString(idx++, paramInfo.getString("current_organ") );     
		 
	  return template.getData(conn, parameters);
	} 

	/* ���ε�� */
	public static int insertSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("   UPDATE M340_USERSEAL_T           \n");
    sb.append("      SET M340_FNAME = ?            \n");
    sb.append("    WHERE M340_CURRENTORGAN = ?     \n");
	
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("f_name") );
		parameters.setString(idx++, paramInfo.getString("current_organ") );
		
	  return template.update(conn, parameters);
	} 

	/* ���λ��� */
	public static int deleteSignInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("  UPDATE M340_USERSEAL_T                    \n");
		sb.append("    SET M340_FNAME = ''                     \n");
		sb.append("  WHERE M340_CURRENTORGAN = ?               \n"); 
		
    QueryTemplate template = new QueryTemplate(sb.toString());
	  QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("current_organ") );
		 
	  return template.delete(conn, parameters);
	}
}