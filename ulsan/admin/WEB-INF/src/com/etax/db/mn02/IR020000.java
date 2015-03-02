/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR020000.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 자금배정진행상태
******************************************************/

package com.etax.db.mn02;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020000 {

	/* 일일마감여부 */
  public static CommonEntity getDailyEndWork(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT *                \n");
    sb.append("   FROM M210_WORKEND_T   \n");
    sb.append("  WHERE M210_YEAR = ?    \n");
    sb.append("    AND M210_DATE = ?    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));
		parameters.setString(idx++,   paramInfo.getString("fis_date"));

    return template.getData(conn, parameters);
  }


  /* 마감일 여부*/
  public static CommonEntity getEndYear(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT *                                 \n");
    sb.append("   FROM M320_COLSINGDATECONTROL_T         \n");
    sb.append("  WHERE M320_YEAR = ?                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("fis_year"));

    return template.getData(conn, parameters);
  }
}
