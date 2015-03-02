/*****************************************************
* ������Ʈ��		 :  ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���		 : ManagerDAO.java
* ���α׷��ۼ��� : 
* ���α׷��ۼ��� : 2010-07-19
* ���α׷�����	 : �����
******************************************************/
package com.etax.db.auth;
import java.sql.*;
import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;
public class ManagerDAO {
	/* ���̵��� ���� */
	public static CommonEntity getInfoById(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                      \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = TRIM(?)   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));

		return template.getData(conn, parameters);
	}


	/* ���̵�üũ */
	public static CommonEntity getInfoById2(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                    \n");
		sb.append("  FROM M260_USERMANAGER_T      \n");
		sb.append(" WHERE M260_USERID = ?         \n");
		sb.append("   AND M260_USERPW = ?         \n");
		sb.append("   AND M260_CURRENTORGAN = '1' \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("userid"));
		parameters.setString(2, paramInfo.getString("userpw"));

		return template.getData(conn, parameters);
	}


  /* �α��δܰ� */
	public static CommonEntity getLogin(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                           \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '��', '')  = REPLACE(REPLACE(?, ' ', ''), '��', '')  \n"); 
    sb.append("   AND M260_USERID = TRIM(?)        \n");
    sb.append("   AND M260_USERPW = TRIM(?)        \n");
   	sb.append("   AND M260_CURRENTORGAN = '1'      \n");
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("serial"));
		parameters.setString(2, paramInfo.getString("subjectDN"));
    parameters.setString(3, paramInfo.getString("userid"));
    parameters.setString(4, paramInfo.getString("userpw"));
	
		return template.getData(conn, parameters);
	}


  /* ������üũ */
	public static CommonEntity getInfoByAuth(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT	 *                            \n");
		sb.append("  FROM M260_USERMANAGER_T           \n");
		sb.append(" WHERE M260_SERIAL = TRIM(?)        \n");
		sb.append("   AND REPLACE(REPLACE(M260_SUB_DN, ' ', ''), '��', '')  = REPLACE(REPLACE(?, ' ', ''), '��', '')  \n"); 
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("serial"));
		parameters.setString(2, paramInfo.getString("subjectDN"));
	
		return template.getData(conn, parameters);
	}


  /* ����� ������ ���� */
	public static int AuthUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M260_USERMANAGER_T  \n");
		sb.append("   SET M260_SERIAL = ?     \n");
		sb.append("      ,M260_SUB_DN = ?     \n"); 
		sb.append(" WHERE M260_USERID = ?     \n");
		sb.append("   AND M260_USERPW = ?     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx=1;
    parameters.setString(idx++, paramInfo.getString("serial"));//�ø���
		parameters.setString(idx++, paramInfo.getString("subjectDN"));//�߱޴��
		parameters.setString(idx++, paramInfo.getString("userid"));//�������̵�
		parameters.setString(idx++, paramInfo.getString("userpw"));//�н�����
	
		return template.insert(conn, parameters);
	}
	 
	
	/* ����� ��� ��û */
	public static int managerInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO M260_USERMANAGER_T                \n");
		sb.append("(                                             \n");
		sb.append("  M260_USERID,  M260_USERPW,                  \n"); 
		sb.append("  M260_USERNAME,M260_CURRENTORGAN,            \n"); 
		sb.append("  M260_SERIAL,  M260_SUB_DN,                  \n"); 
    sb.append("  M260_LOGNO ,  M260_REQUESTDATE,             \n");
		sb.append("  M260_CURRENTPART,M260_CURRENTWORK1,         \n");
    sb.append("  M260_CURRENTSIGNTYPE, M260_MANAGERBANKERNO, \n");
		sb.append("  M260_MANAGERNO, M260_TERMINALNO             \n");
		sb.append(" )                                            \n");
		sb.append(" VALUES                                       \n");
		sb.append("(                                             \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append(" TO_CHAR(SYSDATE, 'YYYYMMDD'),                \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?,                                          \n");
		sb.append("  ?                                           \n");
		sb.append("     )                                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i=1;
		parameters.setString(i++, paramInfo.getString("userid"));//�������̵�
		parameters.setString(i++, paramInfo.getString("userpw"));//�н�����
		parameters.setString(i++, paramInfo.getString("username"));//������
		parameters.setString(i++, paramInfo.getString("currentorgan"));//���Ҽӱ��
		parameters.setString(i++, paramInfo.getString("serial"));//�ø���
		parameters.setString(i++, paramInfo.getString("subjectDN"));//�߱޴��
		parameters.setString(i++, paramInfo.getString("logno"));//�α׹�ȣ(��û)
		parameters.setString(i++, paramInfo.getString("currentdepart"));//���ҼӺμ�
		parameters.setString(i++, paramInfo.getString("currentwork1"));//�ֿ����1
		parameters.setString(i++, paramInfo.getString("currentsign"));//����Ǳ���
		parameters.setString(i++, paramInfo.getString("managerHangNo"));//���ҼӺμ�
		parameters.setString(i++, paramInfo.getString("managerNo"));//�ֿ����1
		parameters.setString(i++, paramInfo.getString("terminalNo"));//����Ǳ���
   
		return template.insert(conn, parameters);
	}
}
 