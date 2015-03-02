/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : LogDAO.java
* 프로그램작성자 : 
* 프로그램작성일 : 2010-07-13
* 프로그램내용	 : 거래내역로그 등록
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class LogDAO {
  
	/*거래 로그 번호 조회 */
	 public static CommonEntity getLogNo(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT TRANS_SEQ.NEXTVAL LOG_NO FROM DUAL   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}

  /* 거래 로그 입력 */
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