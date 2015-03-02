/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070132.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입월계표(총괄광역시세)
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070132 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT  A.M390_SEMOKCODE                                                                                                            \n");
		sb.append("       ,B.M370_SEMOKNAME                                                                                                            \n");
		sb.append("       ,SUM(DECODE(C.M220_DATE, E.MIN_DATE, C.M220_AMTSEIPJEONILTOT,0)) AMTSEIPJEONILTOT  											                	   \n");
    sb.append("       ,SUM(C.M220_AMTSEIP) AMTSEIP    																																		                 				 \n");
    sb.append("       ,SUM(C.M220_AMTSEIPGWAONAP + M220_AMTSEIPJEONGJEONG) GWAONAP    																						                 \n");
    sb.append("       ,SUM(DECODE(C.M220_DATE, E.MAX_DATE, M220_AMTSEIPJEONILTOT+M220_AMTSEIP-M220_AMTSEIPGWAONAP-M220_AMTSEIPJEONGJEONG)) TOT_AMT \n"); 
	  sb.append("  FROM  M390_USESEMOKCODE_T A                                                                                                       \n");	 
		sb.append("       ,M370_SEMOKCODE_T B                                                                                                          \n");
		sb.append("       ,M220_DAY_T C                                                                                                                \n");		 
		sb.append("       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE																																	 \n"); 
    sb.append("            FROM M220_DAY_T																																																				 \n");
    sb.append("           WHERE M220_YEAR = ?                                                                                                      \n");           
    sb.append("             AND M220_DATE BETWEEN ? AND ? ) E       															                    														 \n");
	  sb.append(" WHERE  A.M390_YEAR       =  B.M370_YEAR(+)                                                                                         \n");								  
		sb.append(" 	AND  A.M390_ACCGUBUN   =  B.M370_ACCGUBUN(+)                                                                                     \n");  
		sb.append(" 	AND  A.M390_ACCCODE    =  B.M370_ACCCODE(+)                                                                                      \n");	 
		sb.append(" 	AND  A.M390_WORKGUBUN  =  B.M370_WORKGUBUN(+)                 		                                                               \n");
		sb.append("   AND  A.M390_SEMOKCODE  =  B.M370_SEMOKCODE(+)                                                                                    \n");	
		sb.append("   AND  A.M390_YEAR       =  C.M220_YEAR(+)                                                                                         \n");
		sb.append("   AND  A.M390_ACCGUBUN   =  C.M220_ACCTYPE(+)                                                                                      \n");
		sb.append("   AND  A.M390_ACCCODE    =  C.M220_ACCCODE(+)                                                                                      \n");
		sb.append("   AND  A.M390_SEMOKCODE  =  C.M220_SEMOKCODE(+)                                                                                    \n");  
		sb.append("   AND  A.M390_PARTCODE   =  C.M220_PARTCODE(+)                                                                                     \n");  
		sb.append(" 	AND  A.M390_ACCGUBUN   = 'A'				                                                                                             \n");
		sb.append(" 	AND  A.M390_ACCCODE    = '11'				                                                                                             \n");  
		sb.append(" 	AND  A.M390_WORKGUBUN  = '0'                                                                                                     \n");	 																	 
		sb.append("   AND  SUBSTR(A.M390_SEMOKCODE,1,1) = '1'						                                                                               \n");
		sb.append("   AND  B.M370_SEGUMGUBUN <> '2'	                                                                                                   \n");
		sb.append("   AND  A.M390_YEAR = ?                                                                                                             \n");
    sb.append("   AND  C.M220_DATE(+) BETWEEN ? AND ?		                                                                                           \n");
																																																																			   
		sb.append(" GROUP BY ROLLUP((A.M390_SEMOKCODE,B.M370_SEMOKNAME))                                                                               \n");            						                      
																																																																			   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		
		return template.getList(conn, parameters);
	}
}