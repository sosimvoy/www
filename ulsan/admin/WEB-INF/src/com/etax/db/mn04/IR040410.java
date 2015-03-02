/*****************************************************
* 프로젝트명     : 경기도 세출시스템
* 프로그램명     : IR040410.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-08-13
* 프로그램내용   : 세외수입 > 등록내역 조회/수정/삭제
******************************************************/

package com.etax.db.mn04;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR040410 {

  /* 징수결의 조회 */
  public static List<CommonEntity> getJingsuList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("  SELECT  M090_YEAR                 \n");
    sb.append("         ,M090_SEQ                  \n");  
    sb.append("         ,M090_DATE                 \n");
		sb.append("         ,M090_KYULUITYPE           \n");
    sb.append("         ,M090_BUGWANO              \n");
		sb.append("         ,M090_BALUIDATE            \n");
		sb.append("         ,M090_RETURNORDERDATE      \n");
		sb.append("         ,M090_GOJISEOPUBLISH       \n");
		sb.append("         ,M090_NAPIBDATE            \n");
		sb.append("         ,M090_JINGSUBUWRITE        \n");
		sb.append("         ,M090_GWAONAPWRITE         \n");
		sb.append("     	  ,M090_GWAONAPDATE          \n");
		sb.append("     	  ,M090_GWAN                 \n");
		sb.append("     	  ,M090_HANG                 \n");
		sb.append("     	  ,M090_MOK                  \n");
		sb.append("     	  ,M090_SEMOKCODE            \n");
		sb.append("     	  ,M090_BONAMT               \n");
		sb.append("     	  ,M090_KINAPBUAMT           \n");
		sb.append("     	  ,M090_GASANAMT             \n");
		sb.append("     	  ,M090_INTERESTAMT          \n");
		sb.append("     	  ,M090_INTERESTDAY          \n");
		sb.append("     	  ,M090_NAPBUJANAME          \n");
		sb.append("     	  ,M090_JUMINNO              \n");
		sb.append("     	  ,M090_NAPBUJAADDRESS       \n");
		sb.append("     	  ,M090_BUSINESSNAME         \n");
		sb.append("     	  ,M090_GWAONAPREASON        \n");
		sb.append("     	  ,M090_BANKCODE             \n");
		sb.append("     	  ,M090_ACCOUNTNO            \n");
		sb.append("     	  ,M090_ACCOUNTHOLDER        \n");
		sb.append("     	  ,M090_FILE                 \n");
		sb.append("     	  ,M090_REGISTERPART         \n");
		sb.append("     	  ,M090_DOCUMENTTYPECODE     \n");
		sb.append("     	  ,M090_GWAONAPSTATECODE     \n");
		sb.append("     	  ,M090_DOCUMENTNO           \n");
		sb.append("     	  ,M090_USERNAME             \n");
		sb.append("         ,M090_TOTALAMT             \n");
    sb.append("     	  ,M090_M080_YEAR            \n");
		sb.append("         ,M090_M080_SEQ             \n");
    sb.append("         ,M260_USERNAME             \n");
    sb.append("  FROM M090_JINGSUKYULUI_T A        \n");
    sb.append("      ,M260_USERMANAGER_T B         \n");
    sb.append(" WHERE A.M090_USERNAME = B.M260_USERID(+) \n");
    sb.append("   AND M090_BALUIDATE BETWEEN ? AND ?     \n");
	  sb.append("   AND M090_REGISTERPART  = '시금고'  \n");
    sb.append("   AND M090_YEAR  = ?                 \n");
    sb.append(" ORDER BY M090_BALUIDATE, M090_SEMOKCODE, M090_SEQ	 \n");
   
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx =1;
    parameters.setString(idx++, paramInfo.getString("frombaluiDate"));
    parameters.setString(idx++, paramInfo.getString("tobaluiDate"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		
		return template.getList(conn, parameters);
	}
	
	/*징수결의 상세*/
		public static CommonEntity getJingsuView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("  SELECT  M090_YEAR                 \n");
    sb.append("         ,M090_SEQ                  \n");  
    sb.append("         ,M090_DATE                 \n");
		sb.append("         ,M090_KYULUITYPE           \n");
    sb.append("         ,M090_BUGWANO              \n");
		sb.append("         ,M090_BALUIDATE            \n");
		sb.append("         ,M090_RETURNORDERDATE      \n");
		sb.append("         ,M090_GOJISEOPUBLISH       \n");
		sb.append("         ,M090_NAPIBDATE            \n");
		sb.append("         ,M090_JINGSUBUWRITE        \n");
		sb.append("         ,M090_GWAONAPWRITE         \n");
		sb.append("     	  ,M090_GWAONAPDATE          \n");
		sb.append("     	  ,M090_GWAN                 \n");
		sb.append("     	  ,M090_HANG                 \n");
		sb.append("     	  ,M090_MOK                  \n");
		sb.append("     	  ,M090_SEMOKCODE            \n");
		sb.append("     	  ,M090_BONAMT               \n");
		sb.append("     	  ,M090_KINAPBUAMT           \n");
		sb.append("     	  ,M090_GASANAMT             \n");
		sb.append("     	  ,M090_INTERESTAMT          \n");
		sb.append("     	  ,M090_INTERESTDAY          \n");
		sb.append("     	  ,M090_NAPBUJANAME          \n");
		sb.append("     	  ,M090_JUMINNO              \n");
		sb.append("     	  ,M090_NAPBUJAADDRESS       \n");
		sb.append("     	  ,M090_BUSINESSNAME         \n");
		sb.append("     	  ,M090_GWAONAPREASON        \n");
		sb.append("     	  ,M090_BANKCODE             \n");
		sb.append("     	  ,M090_ACCOUNTNO            \n");
		sb.append("     	  ,M090_ACCOUNTHOLDER        \n");
		sb.append("     	  ,M090_FILE                 \n");
		sb.append("     	  ,M090_REGISTERPART         \n");
		sb.append("     	  ,M090_DOCUMENTTYPECODE     \n");
		sb.append("     	  ,M090_GWAONAPSTATECODE     \n");
		sb.append("     	  ,M090_DOCUMENTNO           \n");
		sb.append("     	  ,M090_USERNAME             \n");
		sb.append("         ,M090_TOTALAMT             \n");
    sb.append("     	  ,M090_M080_YEAR             \n");
		sb.append("         ,M090_M080_SEQ             \n");
    sb.append("     FROM M090_JINGSUKYULUI_T       \n");
    sb.append("    WHERE M090_SEQ = ?              \n");
 
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));
	
		return template.getData(conn, parameters);
	}

	/* 징수결의 수정 */ 
  public static int jingsuUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer(); 
   
    sb.append(" UPDATE  M090_JINGSUKYULUI_T              \n");
		sb.append("    SET  M090_BALUIDATE = ?,              \n");
		sb.append("         M090_GOJISEOPUBLISH = ?,         \n");
		sb.append("         M090_NAPIBDATE= ?,               \n");
		sb.append("         M090_JINGSUBUWRITE = ?,          \n");
		sb.append("         M090_GWAN = ?,                   \n");
		sb.append("         M090_HANG = ?,                   \n");
		sb.append("         M090_MOK = ?,                    \n");
		sb.append("         M090_SEMOKCODE = ?,              \n");
		sb.append("         M090_BONAMT = ?,                 \n");
		sb.append("         M090_GASANAMT = ?,               \n");
		sb.append("         M090_INTERESTAMT = ?,            \n");
		sb.append("         M090_TOTALAMT = ?,               \n");
    sb.append("         M090_NAPBUJANAME = ?,            \n");
    sb.append("         M090_JUMINNO = ?,                \n");
		sb.append("					M090_NAPBUJAADDRESS = ?,         \n");
		sb.append("					M090_BUSINESSNAME = ?,           \n");
		sb.append("         M090_USERNAME = ?                \n");
		sb.append("  WHERE  M090_SEQ  = ?                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1; 
		
		parameters.setString(idx++, paramInfo.getString("baluiDate"));
		parameters.setString(idx++, paramInfo.getString("gojiseoPublish"));
		parameters.setString(idx++, paramInfo.getString("napibDate"));
		parameters.setString(idx++, paramInfo.getString("jingsubuWrite"));
		parameters.setString(idx++, paramInfo.getString("gwan"));
		parameters.setString(idx++, paramInfo.getString("hang"));
		parameters.setString(idx++, paramInfo.getString("mok"));
		parameters.setString(idx++, paramInfo.getString("semok"));
	  parameters.setString(idx++, paramInfo.getString("bonAmt"));
    parameters.setString(idx++, paramInfo.getString("gasanAmt"));
		parameters.setString(idx++, paramInfo.getString("interestAmt"));
    parameters.setString(idx++, paramInfo.getString("totalAmt"));
		parameters.setString(idx++, paramInfo.getString("napbujaName"));
		parameters.setString(idx++, paramInfo.getString("juminNo"));
		parameters.setString(idx++, paramInfo.getString("address"));
		parameters.setString(idx++, paramInfo.getString("businessName"));
		parameters.setString(idx++, paramInfo.getString("userName"));
	  parameters.setString(idx++, paramInfo.getString("seq"));

    return template.update(conn, parameters);
  }

	/*징수결의 삭제*/
	 public static int jingsuDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M090_JINGSUKYULUI_T    \n");
    sb.append("  WHERE M090_YEAR = ?               \n");
    sb.append("    AND M090_SEQ = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year")); 
		parameters.setString(idx++, paramInfo.getString("seq")); 

    return template.delete(conn, parameters);
  }


  /*수령액 수정*/
	public static int updateRevAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M080_BUDGETMANAGE_T           \n");
    sb.append("    SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" + ? \n");
    sb.append("  WHERE M080_YEAR = ?                 \n");
    sb.append("    AND M080_SEQ = ?                  \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("hidden_amt"));
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("m80_seq"));
	
		return template.update(conn, parameters);
	}


  /*수령액 수정(삭제시)*/
	public static int minusRevAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M080_BUDGETMANAGE_T           \n");
    sb.append("    SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" - ? \n");
    sb.append("  WHERE M080_YEAR = ?                 \n");
    sb.append("    AND M080_SEQ = ?                  \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("totalAmt"));
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("m80_seq"));
	
		return template.update(conn, parameters);
	}
}

