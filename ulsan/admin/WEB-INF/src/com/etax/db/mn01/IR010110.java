/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR010110.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ȸ�����ڵ��
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010110 {


  /* ȸ��������ȸ */
  public static CommonEntity getFisDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT FIS_DATE                    \n");
    sb.append("   FROM M000_FIS_DATE               \n"); 
   
		QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }
	      
	
	/* ȸ�����ڵ�� */
  public static int updateFisDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("UPDATE M000_FIS_DATE           \n");
    sb.append("   SET FIS_DATE = ?            \n"); //ȸ������
   // sb.append(" WHERE key = '99999999'        \n"); //Ű
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
	  parameters.setString(1, paramInfo.getString("fis_date"));
		
		return template.update(conn, parameters);
  }
}
