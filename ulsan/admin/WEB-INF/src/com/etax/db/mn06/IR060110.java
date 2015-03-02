/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060110.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > �ڱݿ�Ź�䱸���
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060110 {
  /* �ڱݿ�Ź �䱸��� */
  public static int insertDeposit(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M120_MONEYDEPOSIT_T                             \n");
    sb.append(" (M120_YEAR, M120_SEQ, M120_DATE                             \n");
    sb.append(" ,M120_ACCGUBUN, M120_ACCCODE, M120_PARTCODE                 \n");
		sb.append(" ,M120_DEPOSITTYPE, M120_DEPOSITAMT, M120_MANGIDATE          \n");
		sb.append(" ,M120_DEPOSITDATE, M120_INTERESTRATE, M120_DEPOSITSTATE     \n");
		sb.append(" ,M120_MMDATYPE, M120_LOGNO)                                 \n");
    sb.append(" VALUES( ?, M120_SEQ.NEXTVAL, TO_CHAR(SYSDATE, 'YYYYMMDD')   \n");
    sb.append("        ,?, ?, ?                                             \n");
    sb.append("        ,?, ?, ?                                             \n");
    sb.append("        ,?, ?, ?                                             \n");
		sb.append("        ,?, ? )                                              \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, "A");
    parameters.setString(idx++, "11");
    parameters.setString(idx++, "00000");
    parameters.setString(idx++, paramInfo.getString("deposit_kind"));
    parameters.setString(idx++, paramInfo.getString("new_deposit_amt"));
		parameters.setString(idx++, paramInfo.getString("end_date"));
    parameters.setString(idx++, paramInfo.getString("deposit_due"));
		parameters.setString(idx++, paramInfo.getString("rate"));
    parameters.setString(idx++, "S1");
		parameters.setString(idx++, paramInfo.getString("mmda_kind"));
		parameters.setString(idx++, paramInfo.getString("log_no"));

    return template.insert(conn, parameters);
  }
}
