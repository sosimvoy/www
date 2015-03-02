/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070136.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입분기표(회계별)
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070136 {
/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT LIST.*                                                                                                                 \n");
		sb.append("  FROM (                                                                                                                      \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN                                                                                    			     \n");
		sb.append("		       FROM (                                                                                                              \n");
		sb.append("									SELECT  A.M390_ACCCODE                                                                                       \n");
		sb.append("									       ,B.M360_ACCNAME                                                                                       \n");
    sb.append("									       ,'' M390_SEMOKCODE                                                                                    \n");
    sb.append("									       ,'' M370_SEMOKNAME                                                                                    \n");
		sb.append("									       ,A.M390_ACCGUBUN                                                                                      \n");
    sb.append("									       ,SUM(DECODE(D.M220_DATE, E.MIN_DATE, D.M220_AMTSEIPJEONILTOT,0)) AMTSEIPJEONILTOT     								 \n");
    sb.append("									       ,SUM(D.M220_AMTSEIP) AMTSEIP    																																		   \n");
    sb.append("									       ,SUM(D.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP    																						             \n");
    sb.append("									       ,SUM(D.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                                                      \n"); 
		sb.append("									       ,SUM(D.M220_AMTSEIP-D.M220_AMTSEIPGWAONAP-D.M220_AMTSEIPJEONGJEONG) DIFFERENCE                        \n"); 
		sb.append("									       ,SUM(DECODE(D.M220_DATE, E.MAX_DATE,                                                                  \n");
    sb.append("                                    D.M220_AMTSEIPJEONILTOT+D.M220_AMTSEIP-D.M220_AMTSEIPGWAONAP-D.M220_AMTSEIPJEONGJEONG,0)  \n");
    sb.append("                            ) TOT_AMT                                                                                         \n");
    sb.append("									  FROM  M390_USESEMOKCODE_T A                                                                                \n");	 
		sb.append("									       ,M360_ACCCODE_T B                                                                                     \n");
		sb.append("									       ,M370_SEMOKCODE_T C                                                                                   \n");		 
		sb.append("									       ,M220_DAY_T D                                                                                         \n");	
		sb.append("									       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE																						 \n"); //들어있는 데이터 최초날짜와, 최종날짜 구하는 쿼리
    sb.append("									            FROM M220_DAY_T																																									 \n");
    sb.append("									           WHERE M220_YEAR = ?                                                                               \n");           
    sb.append("									             AND M220_DATE BETWEEN ? AND ? ) E       															                    			 \n");  
		sb.append("									  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                                                               \n");								  
		sb.append("									    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                                                            \n");  
		sb.append("									    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                                                            \n");	 
		sb.append("									    AND  A.M390_YEAR       =  C.M370_YEAR(+)                   		                                           \n");
		sb.append("									    AND  A.M390_ACCGUBUN   =  C.M370_ACCGUBUN(+)                                                             \n");	
		sb.append("									    AND  A.M390_ACCCODE    =  C.M370_ACCCODE(+)                                                              \n");
		sb.append("									    AND  A.M390_WORKGUBUN  =  C.M370_WORKGUBUN(+)                                                            \n");
		sb.append("									    AND  A.M390_SEMOKCODE  =  C.M370_SEMOKCODE(+)                                                            \n");
		sb.append("									    AND  A.M390_YEAR       =  D.M220_YEAR(+)                                                                 \n");  
		sb.append("									    AND  A.M390_ACCGUBUN   =  D.M220_ACCTYPE(+)                                                              \n"); 
		sb.append("									    AND  A.M390_ACCCODE    =  D.M220_ACCCODE(+)                                                              \n");								  
		sb.append("									    AND  A.M390_SEMOKCODE  =  D.M220_SEMOKCODE(+)                                                            \n");  
		sb.append("									    AND  A.M390_PARTCODE   =  D.M220_PARTCODE(+)                                                             \n");	 
		sb.append("									    AND  A.M390_WORKGUBUN  = '0'                    		                                                     \n");
		sb.append("									    AND  C.M370_SEGUMGUBUN <> '2'	                                                                           \n");	
    sb.append("									    AND  A.M390_YEAR = ?                                                                                     \n");
		sb.append("									    AND  D.M220_DATE(+) BETWEEN ? AND ?	                                                                     \n");
    if(!paramInfo.getString("acc_type").equals("")){
			sb.append("										AND D.M220_ACCTYPE = ?																			                                             \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("										AND D.M220_PARTCODE = ?																				                                           \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("										AND D.M220_ACCCODE = ?																			                                             \n");
		}																																																														 				   
		sb.append("                     GROUP BY A.M390_ACCGUBUN, M390_ACCCODE,B.M360_ACCNAME                                                    \n");
    sb.append("									UNION ALL                                                                                                    \n");
    sb.append("									SELECT  A.M390_ACCCODE                                                                                       \n");
		sb.append("									       ,B.M360_ACCNAME                                                                                       \n");
    sb.append("									       ,A.M390_SEMOKCODE                                                                                     \n");
    sb.append("									       ,C.M370_SEMOKNAME                                                                                     \n");
		sb.append("									       ,A.M390_ACCGUBUN                                                                                      \n");
    sb.append("									       ,SUM(DECODE(D.M220_DATE, E.MIN_DATE, D.M220_AMTSEIPJEONILTOT,0)) AMTSEIPJEONILTOT     								 \n");
    sb.append("									       ,SUM(D.M220_AMTSEIP) AMTSEIP    																																		   \n");
    sb.append("									       ,SUM(D.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP    																						             \n");
    sb.append("									       ,SUM(D.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                                                      \n"); 
		sb.append("									       ,SUM(D.M220_AMTSEIP-D.M220_AMTSEIPGWAONAP-D.M220_AMTSEIPJEONGJEONG) DIFFERENCE                        \n"); 
		sb.append("									       ,SUM(DECODE(D.M220_DATE, E.MAX_DATE,                                                                  \n");
    sb.append("                                    D.M220_AMTSEIPJEONILTOT+D.M220_AMTSEIP-D.M220_AMTSEIPGWAONAP-D.M220_AMTSEIPJEONGJEONG,0)  \n");
    sb.append("                            ) TOT_AMT                                                                                         \n");
    sb.append("									  FROM  M390_USESEMOKCODE_T A                                                                                \n");	 
		sb.append("									       ,M360_ACCCODE_T B                                                                                     \n");
		sb.append("									       ,M370_SEMOKCODE_T C                                                                                   \n");		 
		sb.append("									       ,M220_DAY_T D                                                                                         \n");	
		sb.append("									       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE																						 \n"); //들어있는 데이터 최초날짜와, 최종날짜 구하는 쿼리
    sb.append("									            FROM M220_DAY_T																																									 \n");
    sb.append("									           WHERE M220_YEAR = ?                                                                               \n");           
    sb.append("									             AND M220_DATE BETWEEN ? AND ? ) E       															                    			 \n");  
		sb.append("									  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                                                               \n");								  
		sb.append("									    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                                                            \n");  
		sb.append("									    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                                                            \n");	 
		sb.append("									    AND  A.M390_YEAR       =  C.M370_YEAR(+)                   		                                           \n");
		sb.append("									    AND  A.M390_ACCGUBUN   =  C.M370_ACCGUBUN(+)                                                             \n");	
		sb.append("									    AND  A.M390_ACCCODE    =  C.M370_ACCCODE(+)                                                              \n");
		sb.append("									    AND  A.M390_WORKGUBUN  =  C.M370_WORKGUBUN(+)                                                            \n");
		sb.append("									    AND  A.M390_SEMOKCODE  =  C.M370_SEMOKCODE(+)                                                            \n");
		sb.append("									    AND  A.M390_YEAR       =  D.M220_YEAR(+)                                                                 \n");  
		sb.append("									    AND  A.M390_ACCGUBUN   =  D.M220_ACCTYPE(+)                                                              \n"); 
		sb.append("									    AND  A.M390_ACCCODE    =  D.M220_ACCCODE(+)                                                              \n");								  
		sb.append("									    AND  A.M390_SEMOKCODE  =  D.M220_SEMOKCODE(+)                                                            \n");  
		sb.append("									    AND  A.M390_PARTCODE   =  D.M220_PARTCODE(+)                                                             \n");	 
		sb.append("									    AND  A.M390_WORKGUBUN  = '0'                    		                                                     \n");
		sb.append("									    AND  C.M370_SEGUMGUBUN <> '2'	                                                                           \n");	
    sb.append("									    AND  A.M390_ACCGUBUN = 'A'                    		                                                       \n");
		sb.append("									    AND  A.M390_ACCCODE = '11'                                                                               \n");	
    sb.append("									    AND  D.M220_SUNAPGIGWANCODE= '310001'                  		                                               \n");  
		sb.append("									    AND  A.M390_SEMOKCODE IN ('1110100','1110200','1120500')                                                           \n");	
		sb.append("									    AND  A.M390_YEAR = ?                                                                                     \n");
		sb.append("									    AND  D.M220_DATE(+) BETWEEN ? AND ?	                                                                     \n");
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("										AND D.M220_ACCTYPE = ?																			                                             \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("										AND D.M220_PARTCODE = ?																				                                           \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("										AND D.M220_ACCCODE = ?																			                                             \n");
		}																																																																		   
		sb.append("                     GROUP BY A.M390_ACCGUBUN, A.M390_ACCCODE,B.M360_ACCNAME, A.M390_SEMOKCODE, C.M370_SEMOKNAME              \n");
    sb.append("									    ORDER BY M390_ACCGUBUN, M390_ACCCODE                                                                     \n");		
		sb.append("		            ) ORG                                                                                     						         \n");
		sb.append("		      WHERE ROWNUM <= ?                                                                               								     \n");
		sb.append("       ) LIST                                                                                            					           \n");
		sb.append(" WHERE LIST.RN > ?                                                                                       						         \n");
																																																																			   
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
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}	
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
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}				
		parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

		return template.getList(conn, parameters);
	}

    /* 총카운트 */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo)throws SQLException{
		StringBuffer sb = new StringBuffer();

    sb.append("	SELECT  SUM(COUNT(DISTINCT A.M390_ACCCODE)) CNT                               \n");
    sb.append("	  FROM  M390_USESEMOKCODE_T A                                                 \n");	 
		sb.append("	       ,M360_ACCCODE_T B                                                      \n");
		sb.append("	       ,M370_SEMOKCODE_T C                                                    \n");		 
		sb.append("	       ,M220_DAY_T D                                                          \n");	
		sb.append("	       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE            	\n"); //들어있는 데이터 최초날짜와, 최종날짜 구하는 쿼리
    sb.append("	            FROM M220_DAY_T                                                  	\n");
    sb.append("	           WHERE M220_YEAR = ?                                                \n");         
    sb.append("	             AND M220_DATE BETWEEN ? AND ? ) E                               	\n");  
		sb.append("	  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                                \n");								  
		sb.append("	    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                             \n");  
		sb.append("	    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                             \n");	 
		sb.append("	    AND  A.M390_YEAR       =  C.M370_YEAR(+)                                  \n");
		sb.append("	    AND  A.M390_ACCGUBUN   =  C.M370_ACCGUBUN(+)                              \n");	
		sb.append("	    AND  A.M390_ACCCODE    =  C.M370_ACCCODE(+)                               \n");
		sb.append("	    AND  A.M390_WORKGUBUN  =  C.M370_WORKGUBUN(+)                             \n");
		sb.append("	    AND  A.M390_SEMOKCODE  =  C.M370_SEMOKCODE(+)                             \n");
		sb.append("	    AND  A.M390_YEAR       =  D.M220_YEAR(+)                                  \n");  
		sb.append("	    AND  A.M390_ACCGUBUN   =  D.M220_ACCTYPE(+)                               \n"); 
		sb.append("	    AND  A.M390_ACCCODE    =  D.M220_ACCCODE(+)                               \n");								  
		sb.append("	    AND  A.M390_SEMOKCODE  =  D.M220_SEMOKCODE(+)                             \n");  
		sb.append("	    AND  A.M390_PARTCODE   =  D.M220_PARTCODE(+)                              \n");	 
		sb.append("	    AND  A.M390_WORKGUBUN  = '0'                                              \n");
		sb.append("	    AND  C.M370_SEGUMGUBUN <> '2'                                             \n");	
    sb.append("	    AND  A.M390_YEAR = ?                                                      \n");
		sb.append("	    AND  D.M220_DATE(+) BETWEEN ? AND ?                                       \n");
    if(!paramInfo.getString("acc_type").equals("")){                                         
			sb.append("		AND D.M220_ACCTYPE = ?                                                 	  \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("		AND D.M220_PARTCODE = ?                                                	  \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("		AND D.M220_ACCCODE = ?                                                  	\n");
		}                                                                                      	
		sb.append("  GROUP BY A.M390_ACCCODE,B.M360_ACCNAME                                       \n"); 
    sb.append("	UNION ALL                                                                     \n");
    sb.append("	SELECT  SUM(COUNT(DISTINCT A.M390_ACCCODE)) CNT                               \n");
    sb.append("	  FROM  M390_USESEMOKCODE_T A                                                 \n");	 
		sb.append("	       ,M360_ACCCODE_T B                                                      \n");
		sb.append("	       ,M370_SEMOKCODE_T C                                                    \n");		 
		sb.append("	       ,M220_DAY_T D                                                          \n");	
		sb.append("	       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE		          \n"); //들어있는 데이터 최초날짜와, 최종날짜 구하는 쿼리
    sb.append("	            FROM M220_DAY_T                                                   \n");
    sb.append("	           WHERE M220_YEAR = ?                                                \n");           
    sb.append("	             AND M220_DATE BETWEEN ? AND ? ) E                                \n");  
		sb.append("	  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                                \n");								  
		sb.append("	    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                             \n");  
		sb.append("	    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                             \n");	 
		sb.append("	    AND  A.M390_YEAR       =  C.M370_YEAR(+)                                  \n");
		sb.append("	    AND  A.M390_ACCGUBUN   =  C.M370_ACCGUBUN(+)                              \n");	
		sb.append("	    AND  A.M390_ACCCODE    =  C.M370_ACCCODE(+)                               \n");
		sb.append("	    AND  A.M390_WORKGUBUN  =  C.M370_WORKGUBUN(+)                             \n");
		sb.append("	    AND  A.M390_SEMOKCODE  =  C.M370_SEMOKCODE(+)                             \n");
		sb.append("	    AND  A.M390_YEAR       =  D.M220_YEAR(+)                                  \n");  
		sb.append("	    AND  A.M390_ACCGUBUN   =  D.M220_ACCTYPE(+)                               \n"); 
		sb.append("	    AND  A.M390_ACCCODE    =  D.M220_ACCCODE(+)                               \n");								  
		sb.append("	    AND  A.M390_SEMOKCODE  =  D.M220_SEMOKCODE(+)                             \n");  
		sb.append("	    AND  A.M390_PARTCODE   =  D.M220_PARTCODE(+)                              \n");	 
		sb.append("	    AND  A.M390_WORKGUBUN  = '0'                                              \n");
		sb.append("	    AND  C.M370_SEGUMGUBUN <> '2'                                             \n");	
    sb.append("	    AND  A.M390_ACCGUBUN = 'A'                                                \n");
		sb.append("	    AND  A.M390_ACCCODE = '11'                                                \n");	
    sb.append("	    AND  D.M220_SUNAPGIGWANCODE= '310001'                                     \n");  
		sb.append("	    AND  A.M390_SEMOKCODE IN ('1110100','1110200','1120500')                            \n");	
		sb.append("	    AND  A.M390_YEAR = ?                                                      \n");
		sb.append("	    AND  D.M220_DATE(+) BETWEEN ? AND ?                                       \n");
		if(!paramInfo.getString("acc_type").equals("")){                                          
			sb.append("		AND D.M220_ACCTYPE = ?                                                    \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("		AND D.M220_PARTCODE = ?                                                   \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("		AND D.M220_ACCCODE = ?                                                    \n");
		}																																								
		sb.append(" GROUP BY A.M390_ACCCODE,B.M360_ACCNAME, A.M390_SEMOKCODE, C.M370_SEMOKNAME    \n");                                 
    
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
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}				
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
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}			

		return template.getData(conn, parameters);
	}
}