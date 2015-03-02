/***********************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR011010.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 세입금 정정 요구서조회/결과통지
************************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR011010 {

	/* 세입금정정요구서조회 */
	public static List<CommonEntity> getExpLedgerList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("	SELECT  M020_SEQ                      \n"); 	//일련번호
    sb.append("	       ,M020_YEAR                     \n"); 	//년월
		sb.append("	       ,M020_CYEARMONTH               \n"); 	//년월
		sb.append("		     ,M020_CSUBJECT                 \n"); 	//과목
		sb.append("		     ,M020_CRECEIPTDATE             \n"); 	//영수일자
		sb.append("		     ,M020_CPAYMENTAMT              \n"); 	//납입금액
		sb.append("		     ,M020_OPAYMENTNAME             \n"); 	//납입자명
		sb.append("		     ,M020_OREASON                  \n"); 	//사유
		sb.append("	       ,M020_ODATE                    \n"); 	//정정일자
		sb.append("        ,M020_STATE                    \n");	  //상태코드
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


  /* 세입금 결과통지 */
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


  /* 세입금정정통지서 팝업 */
  public static CommonEntity getInformView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT  M020_SEQ, M020_YEAR,																	\n");  //일련번호, 회계연도     
    sb.append("         M020_OYEARMONTH, M020_CYEARMONTH,  										\n");	 //기재사항 년월, 정정사항 년월
    sb.append("         M020_CACCCLASS, M020_CJINGSUGWAN,											\n");	 //정정사항 회계별, 정정사항 주관징수관		
    sb.append("	        M020_OGWAMOK, M020_CSUBJECT,													\n");	 //기재사항 과목, 정정사항 과목
    sb.append("	        M020_ORECEIPTDATE, M020_CRECEIPTDATE, 								\n");	 //기재사항 영수일자, 정정사항 영수일자
    sb.append("	        M020_CRECEIPTLOCATION, 						                    \n");	 //정정사항 영수장소
    sb.append("	        M020_OPAYMENTAMT, M020_CPAYMENTAMT,										\n");	 //기재사항 납입금액, 정정사항 납입금액	
    sb.append("	        M020_OPAYMENTNAME, M020_OREASON,     						      \n");	 //기재사항 납입자명, 기재사항 사유		
    sb.append("	        M020_ODATE, M020_DOCUMENTNO  		 											\n");	 //기재사항 정정일자, 문서번호
    sb.append("   FROM  M020_TAXINCORRECTION_T 				                      	\n");  
    sb.append("  WHERE  M020_SEQ = ? 					                                \n");  
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));

		return template.getData(conn, parameters);
  }


  /* 직인 가져오기 */
	public static CommonEntity getStamp(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		   
    sb.append(" SELECT  M340_FNAME                  \n");
    sb.append("   FROM  M340_USERSEAL_T             \n");
    sb.append("  WHERE  M340_CURRENTORGAN = '2'     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		        
		return template.getData(conn);
	}
}
