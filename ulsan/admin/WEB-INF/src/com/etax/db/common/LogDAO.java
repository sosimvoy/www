/*****************************************************
* ������Ʈ��	   : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	   : LogDAO.java
* ���α׷��ۼ��� : 
* ���α׷��ۼ��� : 2010-07-13
* ���α׷�����	 : �ŷ������α� ���
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class LogDAO {
  
	/*�ŷ� �α� ��ȣ ��ȸ */
	 public static CommonEntity getLogNo(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT TRANS_SEQ.NEXTVAL LOG_NO FROM DUAL   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}

  /* �ŷ� �α� �Է� */
  public static int insertTransLog(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M280_TRANSACTIONLOG_T                  \n");
		sb.append("(M280_TRANSDATE, M280_LOGNO, M280_USERNAME,        \n");
		sb.append(" M280_WORKTYPE, M280_TRANSGUBUN, M280_CONNECTIP)   \n"); 
		sb.append("VALUES	(TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?,\n");
		sb.append("        ?, ?, ? )                                  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int i = 1;
		parameters.setString(i++, paramInfo.getString("log_no"));
		parameters.setString(i++, paramInfo.getString("user_name"));
		parameters.setString(i++, paramInfo.getString("work_type"));
		parameters.setString(i++, paramInfo.getString("trans_gubun"));
		parameters.setString(i++, paramInfo.getString("user_ip"));
		return template.insert(conn, parameters);
	}
}