/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR010110.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 회계일자등록
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010110 {


  /* 회계일자조회 */
  public static CommonEntity getFisDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT FIS_DATE                    \n");
    sb.append("   FROM M000_FIS_DATE               \n"); 
   
		QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }
	      
	
	/* 회계일자등록 */
  public static int updateFisDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("UPDATE M000_FIS_DATE           \n");
    sb.append("   SET FIS_DATE = ?            \n"); //회계일자
   // sb.append(" WHERE key = '99999999'        \n"); //키
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
	  parameters.setString(1, paramInfo.getString("fis_date"));
		
		return template.update(conn, parameters);
  }
}
