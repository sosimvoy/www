/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030110.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-19
* 프로그램내용	   : 세입세출외현금 > 수기분등록
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030110 {

 /* 회계명 조회 */ 
	public static List<CommonEntity> getAccNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME     \n");		
    sb.append("   FROM  M360_ACCCODE_T A 						  	   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B              \n");       
		sb.append("  WHERE  B.M390_WORKGUBUN = '4'		  			 \n");
		sb.append("    AND  A.M360_ACCGUBUN ='D'               \n");
    sb.append("    AND  A.M360_YEAR = B.M390_YEAR					 \n"); 
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN	 \n"); 
    sb.append("    AND  A.M360_ACCCODE =  B.M390_ACCCODE	 \n");
		sb.append("    AND  A.M360_YEAR = ?				             \n");
    sb.append("    AND  B.M390_PARTCODE = ?                \n"); 
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME    \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                    \n");		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getList(conn, parameters);
  }

	/* 부서명 조회 */
	public static List<CommonEntity> getCashDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M350_PARTCODE, M350_PARTNAME       \n");  
    sb.append("   FROM  M350_PARTCODE_T A  				   			   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("    AND  A.M350_YEAR = B.M390_YEAR            \n"); 
		sb.append("    AND  B.M390_ACCGUBUN ='D'                 \n");
		sb.append("    AND  B.M390_WORKGUBUN = '4'               \n");
		sb.append("    AND  A.M350_YEAR = ?				               \n");
		sb.append("  GROUP BY A.M350_PARTCODE, M350_PARTNAME     \n");
		sb.append(" ORDER BY A.M350_PARTCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        
    return template.getList(conn, parameters);
  }


 /* 수기분등록  */
  public static int cashInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M040_TAXCASH_T              \n");
		sb.append("       ( M040_SEQ   					            \n");	 
		sb.append("        ,M040_YEAR 					            \n");	 
		sb.append("        ,M040_DATE 					            \n");	 
		sb.append("        ,M040_PARTCODE 			            \n");	 
		sb.append("        ,M040_ACCCODE 				            \n");	 
		sb.append("        ,M040_DWTYPE 				            \n");	 
		sb.append("        ,M040_INTYPE 				            \n");
		sb.append("        ,M040_CASHTYPE 			            \n");	 
		sb.append("        ,M040_DEPOSITTYPE 		            \n");
		sb.append("        ,M040_ORDERNAME                  \n");
		sb.append("        ,M040_NOTE                       \n");
		sb.append("        ,M040_ORDERNO                    \n");		 
		sb.append("        ,M040_CNT 					           	  \n");	 
		sb.append("        ,M040_AMT 						            \n");	 
		sb.append("        ,M040_LOGNO 					            \n");	 
		sb.append("        ,M040_WORKTYPE 			            \n");	 
		sb.append("        ,M040_TRANSGUBUN  )	            \n");	 
		sb.append(" VALUES( M040_SEQ.NEXTVAL		            \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");	
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,?                              \n");
	  sb.append("         ,?                              \n");
		sb.append("         ,?                              \n");
		sb.append("         ,?					                    \n"); 
		sb.append("         ,?					                    \n");
		sb.append("         ,?					                    \n");
		sb.append("         ,'A03'					                \n");
		sb.append("         ,'111' )					              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("dwtype"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("cash_type"));
		parameters.setString(idx++, paramInfo.getString("deposit_type"));
	  parameters.setString(idx++, paramInfo.getString("order_name"));
	  parameters.setString(idx++, paramInfo.getString("note"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		return template.insert(conn, parameters);
	}

  /* 지급번호 중복 여부 확인 */
	public static CommonEntity getOrderNo(Connection conn, CommonEntity paramInfo) throws SQLException {
	  StringBuffer sb = new StringBuffer();
		
    sb.append("	SELECT  COUNT (M040_ORDERNO) NO_CNT             \n");
    sb.append("   FROM  M040_TAXCASH_T                          \n");
    sb.append("  WHERE  M040_YEAR	= ?                           \n");   //회계년도
    sb.append("    AND  M040_DATE	= ?                           \n");   //회계일자
    sb.append("    AND  M040_PARTCODE	= ?                       \n");   //부서
    sb.append("    AND  M040_ACCCODE = ?                        \n");   //회계명
    sb.append("    AND  M040_ORDERNO = ?                        \n");   //지급번호

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;

		parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("fis_date"));	
		parameters.setString(idx++,  paramInfo.getString("part_code"));	
    parameters.setString(idx++,  paramInfo.getString("acc_code"));	
    parameters.setString(idx++,  paramInfo.getString("order_no"));	

		return template.getData(conn, parameters);
	}

}
