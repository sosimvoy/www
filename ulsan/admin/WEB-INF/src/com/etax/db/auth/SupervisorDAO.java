/*****************************************************
* ������Ʈ��		 : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		 : SupervisorDAO.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-19
* ���α׷�����	 : ���α���
******************************************************/

package com.etax.db.auth;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;

public class SupervisorDAO {

  /* ���� üũ */
	public static CommonEntity getPowCheck(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	*                         \n");
		sb.append("  FROM M260_USERMANAGER_T        \n");
		sb.append(" WHERE M260_USERID = TRIM(?)     \n");
    sb.append("   AND M260_CURRENTORGAN = ?     \n");
    sb.append("   AND M260_CURRENTSIGNTYPE = ?  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("user_id"));
    parameters.setString(idx++, "1");
    parameters.setString(idx++, "B2");

		return template.getData(conn, parameters);
	}


	/* ���̵�üũ */
	public static CommonEntity getInfoById2(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	*                       \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = TRIM(?)   \n");
	  sb.append("   AND M260_USERPW = TRIM(?)   \n");
   	sb.append("   AND M260_CURRENTORGAN = '1' \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));
		parameters.setString(2, paramInfo.getString("userpw"));

		return template.getData(conn, parameters);
	}


	/* ������üũ */
	public static CommonEntity getInfoByAuth(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                            \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '��', '')  = REPLACE(REPLACE(?, ' ', ''), '��', '')  \n"); 
    sb.append("   AND M260_CURRENTORGAN = '1' \n");
    sb.append("   AND M260_USERID = TRIM(?)   \n");
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx =1;
		parameters.setString(idx++, paramInfo.getString("serial"));
		parameters.setString(idx++, paramInfo.getString("subjectDN"));
    parameters.setString(idx++, paramInfo.getString("user_id"));
	
		return template.getData(conn, parameters);
	}
}
 