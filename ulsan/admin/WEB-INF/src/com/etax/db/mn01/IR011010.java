/***********************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR011010.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ���Ա� ���� �䱸����ȸ/�������
************************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR011010 {

	/* ���Ա������䱸����ȸ */
	public static List<CommonEntity> getExpLedgerList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("	SELECT  M020_SEQ                      \n"); 	//�Ϸù�ȣ
    sb.append("	       ,M020_YEAR                     \n"); 	//���
		sb.append("	       ,M020_CYEARMONTH               \n"); 	//���
		sb.append("		     ,M020_CSUBJECT                 \n"); 	//����
		sb.append("		     ,M020_CRECEIPTDATE             \n"); 	//��������
		sb.append("		     ,M020_CPAYMENTAMT              \n"); 	//���Աݾ�
		sb.append("		     ,M020_OPAYMENTNAME             \n"); 	//�����ڸ�
		sb.append("		     ,M020_OREASON                  \n"); 	//����
		sb.append("	       ,M020_ODATE                    \n"); 	//��������
		sb.append("        ,M020_STATE                    \n");	  //�����ڵ�
		sb.append("   FROM  M020_TAXINCORRECTION_T        \n");
		sb.append("  WHERE  M020_YEAR = ?	                \n");
	  sb.append("		 AND  M020_ODATE = ?                \n");	
    sb.append("		 AND  M020_STATE <> 'S1'            \n");								
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;
   
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("odate"));

		return template.getList(conn, parameters);

	}


  /* ���Ա� ������� */
  public static int updateTaxinState(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M020_TAXINCORRECTION_T       \n");
    sb.append("    SET M020_STATE = ?               \n");
    sb.append("       ,M020_LOGNO_2 = ?             \n");
    sb.append("  WHERE M020_YEAR = ?                \n");
    sb.append("    AND M020_SEQ = ?                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, "S3");
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("seq"));

    return template.update(conn, parameters);
  }


  /* ���Ա����������� �˾� */
  public static CommonEntity getInformView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT  M020_SEQ, M020_YEAR,																	\n");  //�Ϸù�ȣ, ȸ�迬��     
    sb.append("         M020_OYEARMONTH, M020_CYEARMONTH,  										\n");	 //������� ���, �������� ���
    sb.append("         M020_CACCCLASS, M020_CJINGSUGWAN,											\n");	 //�������� ȸ�躰, �������� �ְ�¡����		
    sb.append("	        M020_OGWAMOK, M020_CSUBJECT,													\n");	 //������� ����, �������� ����
    sb.append("	        M020_ORECEIPTDATE, M020_CRECEIPTDATE, 								\n");	 //������� ��������, �������� ��������
    sb.append("	        M020_CRECEIPTLOCATION, 						                    \n");	 //�������� �������
    sb.append("	        M020_OPAYMENTAMT, M020_CPAYMENTAMT,										\n");	 //������� ���Աݾ�, �������� ���Աݾ�	
    sb.append("	        M020_OPAYMENTNAME, M020_OREASON,     						      \n");	 //������� �����ڸ�, ������� ����		
    sb.append("	        M020_ODATE, M020_DOCUMENTNO  		 											\n");	 //������� ��������, ������ȣ
    sb.append("   FROM  M020_TAXINCORRECTION_T 				                      	\n");  
    sb.append("  WHERE  M020_SEQ = ? 					                                \n");  
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));

		return template.getData(conn, parameters);
  }


  /* ���� �������� */
	public static CommonEntity getStamp(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		   
    sb.append(" SELECT  M340_FNAME                  \n");
    sb.append("   FROM  M340_USERSEAL_T             \n");
    sb.append("  WHERE  M340_CURRENTORGAN = '2'     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		        
		return template.getData(conn);
	}
}
