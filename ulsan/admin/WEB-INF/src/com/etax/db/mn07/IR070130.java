/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070130.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입월계표(총괄광역시세)
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070130 {
/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT  A.M390_SEMOKCODE                                                                                         \n");
		sb.append("       ,B.M370_SEMOKNAME                                                                                         \n");
		sb.append("       ,SUM(DECODE(C.M220_DATE, E.MIN_DATE, C.M220_AMTSEIPJEONILTOT,0)) AMTSEIPJEONILTOT     							   	  \n");
    sb.append("       ,SUM(C.M220_AMTSEIP) AMTSEIP    																																		      \n");
    sb.append("       ,SUM(C.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP    																						                \n");
    sb.append("       ,SUM(C.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                                                          \n"); 
    sb.append("       ,SUM(C.M220_AMTSEIP-C.M220_AMTSEIPGWAONAP-C.M220_AMTSEIPJEONGJEONG) DIFFERENCE                            \n");
    sb.append("       ,SUM(DECODE(C.M220_DATE, E.MAX_DATE,                                                                      \n");
    sb.append("                   C.M220_AMTSEIPJEONILTOT+C.M220_AMTSEIP-C.M220_AMTSEIPGWAONAP-C.M220_AMTSEIPJEONGJEONG,0)      \n");
    sb.append("           ) TOT_AMT                                                                                             \n"); 
    sb.append("  FROM  M390_USESEMOKCODE_T A                                                                                    \n");	 
		sb.append("       ,M370_SEMOKCODE_T B                                                                                       \n");
		sb.append("       ,M220_DAY_T C                                                                                             \n");		 
		sb.append("       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE																								\n"); //들어있는 데이터 최초날짜와, 최종날짜 구하는 쿼리
    sb.append("            FROM M220_DAY_T																																											\n");
    sb.append("           WHERE M220_YEAR = ?                                                                                   \n");           
    sb.append("             AND M220_DATE BETWEEN ? AND ? ) E       															                    					\n");
	  sb.append(" WHERE  A.M390_YEAR       =  B.M370_YEAR(+)                                                                      \n");								  
		sb.append(" 	AND  A.M390_ACCGUBUN   =  B.M370_ACCGUBUN(+)                                                                  \n");  
		sb.append(" 	AND  A.M390_ACCCODE    =  B.M370_ACCCODE(+)                                                                   \n");	 
		sb.append(" 	AND  A.M390_WORKGUBUN  =  B.M370_WORKGUBUN(+)                 		                                            \n");
		sb.append("   AND  A.M390_SEMOKCODE  =  B.M370_SEMOKCODE(+)                                                                 \n");	
		sb.append("   AND  A.M390_YEAR       =  C.M220_YEAR(+)                                                                      \n");
		sb.append("   AND  A.M390_ACCGUBUN   =  C.M220_ACCTYPE(+)                                                                   \n");
		sb.append("   AND  A.M390_ACCCODE    =  C.M220_ACCCODE(+)                                                                   \n");
		sb.append("   AND  A.M390_SEMOKCODE  =  C.M220_SEMOKCODE(+)                                                                 \n");  
		sb.append("   AND  A.M390_PARTCODE   =  C.M220_PARTCODE(+)                                                                  \n"); 
		sb.append(" 	AND  A.M390_ACCGUBUN   = 'A'				                                                                          \n");
		sb.append(" 	AND  A.M390_ACCCODE    = '11'				                                                                          \n");  
		sb.append(" 	AND  A.M390_WORKGUBUN  = '0'                                                                                  \n");	 																	 														 
		sb.append("   AND  C.M220_PARTCODE(+) IN ('02130','02140')                                                                  \n");
		sb.append("   AND  A.M390_YEAR = ?                                                                                          \n");
    sb.append("   AND  C.M220_DATE(+) BETWEEN ? AND ?		                                                                        \n");
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("	AND C.M220_ACCTYPE = ?																			                                                  \n");
		}                                                                                                                            
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("	AND C.M220_PARTCODE = ?																				                                                \n");
		}																																																															
		sb.append(" GROUP BY ROLLUP((A.M390_SEMOKCODE,B.M370_SEMOKNAME))                                                            \n");            						                      
																																																																	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
    if(!paramInfo.getString("acc_type").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if(!paramInfo.getString("part_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
	
		return template.getList(conn, parameters);
	}
}