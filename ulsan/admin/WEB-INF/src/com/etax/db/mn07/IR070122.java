/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070122.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 중소기업육성기금수입,지출일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070122 {

	/* 보고서 조회 */
    public static CommonEntity getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

      sb.append("	          SELECT SUM(M220_AMTJEONGGI) AMTJEONGGI,SUM(M220_AMTGONGGEUM) AMTGONGGEUM,SUM(M220_AMTLOAN) AMTLOAN                                      \n");
		  sb.append("	                ,SUM(M220_AMTJEONGGI) - SUM(M220_AMTLOAN)  AMTJEONGGIETC                                                                      \n");
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1110100                     \n");                                                                
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1110200                     \n");                                                   
			sb.append("				        	,SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1120100                     \n");                                                   
			sb.append("					        ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1120200                     \n");                                                   
			sb.append("					        ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1120300                     \n");                                                   
			sb.append("					        ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1120400                     \n");                                                   
			sb.append("					        ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1210100                     \n");                                                   
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1210200                     \n");                                                   
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1220100                     \n");                                                   
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1220200                     \n");
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1230100                     \n");                                                  
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1230200 									  \n");
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1240100 									  \n");
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1240200                     \n");                                                  
			sb.append("	                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) AMTSEIPJEONILTOT_1250100 									  \n");
																																																																																														
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) 																							\n"); 
			sb.append("							   +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                               \n");                                                 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)     																					\n"); 
			sb.append("							   +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)     																				  \n");
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END))TOTJEONSEIP                                   \n");                                                
																																																																																
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)+                                               \n");                        
			sb.append("                SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)) AMTSEIPJEONILTOT_B  													 \n");
																																																																																
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)+                                               \n");                        
			sb.append("                SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)) AMTSEIPJEONILTOT_C 														 \n");
																																																																																
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)+                                               \n");                        
			sb.append("                SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)) AMTSEIPJEONILTOT_D                                         \n");  
																																																																																						
			sb.append("                ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)+                                                          \n");              
			sb.append("                SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)) AMTSEIPJEONILTOT_E																				 \n");  
																																																																																						
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN A.M220_AMTSEIPJEONILTOT  ELSE 0 END) +                                                         \n");                                    
			sb.append("                 SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) +                                                          \n");                                     
			sb.append("                 SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) +																													 \n");  
			sb.append("                 SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) +   																											 \n");  
			sb.append("                 SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END) +                                                          \n");                                        
			sb.append("                 SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END))  AMTSEIPJEONILTOT_A                                        \n");                                      

			sb.append("                ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");                                    
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");                                    
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");                                    
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");                                    
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");                                    
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END)                                                            \n");
			sb.append("                 +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN A.M220_AMTSEIPJEONILTOT ELSE 0 END))JUNGSOAMTSEIPJEONILTOT                                     \n");                                    
																																																																																									
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP1        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP2        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP3        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP4        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP5        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP6        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP7        \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP8       \n");                                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP9  		  \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP10 		  \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP11  		  \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP12  		  \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP13 		 \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP14 		  \n");
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END) DAYSUIP15        \n");                                   
																																																																																							
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                  \n");                                  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                 \n");                                   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                 \n");                                   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                  \n");                                   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                 \n");                                   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)  							  \n");
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                  \n");                                   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");                                  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");         
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");         
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");         
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");         
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)   																				 \n");  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                            \n");         
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)) TOTDAYSUIP                                \n");         
																																																																																																						
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                              \n");       
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                             \n");        
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)  																					  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)  																					  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                             \n");        
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))KYUNGYOUNGDAYSUIP                           \n");        
																																																																																																						 
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                              \n");      
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                             \n");        
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)																						  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)																						  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)																						  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)																						  \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                             \n");   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))JUNGSODAYSUIP 														  \n"); 
																																																																																																							
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                                \n");                                                    
			sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))CHANGUPDAYSUIP 														    \n"); 
																																																																																																							
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                                \n");                                                    
			sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))MARKETDAYSUIP 															    \n"); 
																																																																																																						
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                                \n");                                                   
			sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))UTONGDAYSUIP 															    \n");
																																																																																																							 
			sb.append("              ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END)                                                \n");                                                   
			sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) ELSE 0 END))DISASTERDAYSUIP 														    \n");
																																																																																																																																																																																				
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP1           \n");       
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP2           \n");       
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP3           \n");       
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP4           \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP5           \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP6           \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP7           \n");                    
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP8           \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP9           \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP10  				\n");  
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP11          \n");       
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP12          \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP13          \n");      
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP14					 \n"); 
			sb.append("                ,SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) TOTSUIP15   				 \n"); 
																																																																																																									
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) 										 \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                     \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                        \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                        \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                        \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                        \n");     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) 										    \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)										    \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                        \n"); 
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))TOTSUIP                \n");     
																																																																																																								 
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)    								     \n");
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)    								     \n");
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))KYUNGYOUNGTOTSUIP       \n");    
																																																																																																								 
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");   
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END) 										     \n");
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)												 \n");  
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)) JUNGSOTOTSUIP          \n");  
																																																																																																											
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");                     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))CHANGUPTOTSUIP 				 \n");  
																																																																																																										 
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");                     
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))MARKETTOTSUIP 					 \n"); 
																																																																																																											
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");                    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))UTONGTOTSUIP 					 \n"); 
																																																																																																											
			sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");                    
			sb.append("                +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))DISASTERTOTSUIP 				 \n"); 
																																																																																																											
			sb.append("               ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN A.M220_AMTSECHULJEONILTOT ELSE 0 END) AMTSECHULJEONILTOT_1110100                                                                 	 \n"); 																																																																																																																																																																																																																																																																																																																																			 
			sb.append("               ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)ELSE 0 END)  DAYSECHUL1                                   	 \n");																																																																																																																																																																																																																	 																																																																																																						 
			sb.append("               ,SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSECHULJEONILTOT + (A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG))ELSE 0 END) TOTSECHUL1        \n"); 
			
		  sb.append("               ,(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                         \n");   
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1110200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1120100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1120200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1120300' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1120400' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1210100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1210200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1220100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1220200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1230100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1230200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1240100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1240200' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END)                          \n");
      sb.append("               +SUM(CASE WHEN A.M220_SEMOKCODE = '1250100' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))ELSE 0 END))                         \n");
      sb.append("               -(SUM(CASE WHEN A.M220_SEMOKCODE = '1110100' THEN (A.M220_AMTSECHULJEONILTOT + (A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG))ELSE 0 END))TOTJANAMT        \n");
		   																																																																																																																													 																																																																																																																																					
			sb.append("       ,SUM(CASE WHEN A.M220_SEMOKCODE = '9999900' THEN (((A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))-                                																															 \n"); 
			sb.append("            (A.M220_AMTSECHULJEONILTOT + (A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)))- A.M220_AMTJEONGGI -  A.M220_AMTGONGGEUM)ELSE 0 END) SURPLUS1          																														 \n"); 
			
			sb.append("        FROM   M220_DAY_T A                                                                                                                                                                    																												 \n");
			sb.append("                    ,(SELECT M370_SEMOKCODE                                                                                                                                                   																													 \n");
			sb.append("                         ,M370_YEAR                                                                                                                                                          																													 \n");
			sb.append("                         ,M370_ACCGUBUN                                                                                                                                                     																														 \n");
			sb.append("                         ,M370_ACCCODE                                                                                                                                                      																														 \n");
			sb.append("                     FROM M370_SEMOKCODE_T                                                                                                                                                  																														 \n");
			sb.append("                    WHERE M370_ACCGUBUN = 'E'                                                                                                                                               																														 \n");
			sb.append("                      AND M370_ACCCODE = '01'                                                                                                                                             																															 \n");
			sb.append("                    GROUP BY M370_SEMOKCODE                                                                                                                                                																														 \n");
			sb.append("                         ,M370_YEAR                                                                                                                                                         																														 \n");
			sb.append("                         ,M370_ACCGUBUN                                                                                                                                                     																														 \n");
			sb.append("                         ,M370_ACCCODE                                                                                                                                                       																													 \n");
			sb.append("                         ,M370_SEMOKCODE) B                                                                                                                                                   																													 \n");
			sb.append("     WHERE  A.M220_YEAR =B.M370_YEAR                                                                                                                                                          																													 \n");
			sb.append("         AND  A.M220_ACCCODE = B.M370_ACCCODE                                                                                                                                                  																												 \n");
			sb.append("         AND  A.M220_ACCTYPE = B.M370_ACCGUBUN                                                                                                                                                 																												 \n");
			sb.append("         AND  A.M220_SEMOKCODE =B.M370_SEMOKCODE                                                                                                                                               																												 \n");
			sb.append("         AND  A.M220_ACCTYPE = 'E'                                                                                                                                                             																												 \n");
			sb.append("         AND  A.M220_ACCCODE ='01'                                                                                                                                                             																												 \n");
			sb.append("         AND  A.M220_YEAR = ?                                                                                                                                                                 																											      \n");
      sb.append("         AND  A.M220_DATE = ?    																																																																																																							          \n");                                                                                                                                                                                                                   

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}
}