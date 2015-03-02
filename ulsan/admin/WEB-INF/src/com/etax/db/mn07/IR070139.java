/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070139.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 보고서 조회 > 지급명령서
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070139 {
  /* 보고서 조회 */
	/*
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
        
				sb.append("SELECT LIST.*                                                                             \n");
				sb.append("  FROM (                                                                                  \n");
				sb.append("		     SELECT ORG.*, ROWNUM RN                                                           \n");
				sb.append("		       FROM (                                                                          \n");
        sb.append("									SELECT  A.M030_ACCTYPE, B.M360_ACCCODE, B.M360_ACCNAME                   \n");
        sb.append("									       ,A.M030_ORDERNO, A.M030_ORDERNAME                                 \n");
        sb.append("									       ,COUNT(1) TOT_CNT                                                 \n");
        sb.append("									       ,SUM(A.M030_AMT) TOT_AMT                                          \n");
 	      sb.append("									  FROM  M030_TAXOTHER_T A                                                \n"); 
        sb.append("									       ,M360_ACCCODE_T B                                                 \n"); 
 	      sb.append("									 WHERE  A.M030_YEAR = B.M360_YEAR                                        \n");
			  sb.append("									 AND  A.M030_INTYPE = 'I1'                                               \n");
		    sb.append("									   AND  A.M030_ACCTYPE = B.M360_ACCGUBUN                                 \n");
        sb.append("									   AND  A.M030_ACCCODE = B.M360_ACCCODE                                  \n");
        sb.append("									   AND  A.M030_YEAR = ?                                                  \n");
        sb.append("									   AND  A.M030_DATE = ?                                                  \n");
        if(!paramInfo.getString("acc_type").equals("")){                                
        sb.append("                    AND  A.M030_ACCTYPE = ?                                               \n");
        }
        if(!paramInfo.getString("part_code").equals("")){
		    sb.append("                    AND  A.M030_PARTCODE = ?                                              \n");
	    	}
		    if(!paramInfo.getString("acc_code").equals("")){
		    sb.append("                    AND  A.M030_ACCCODE = ?                                               \n");
	    	}
        sb.append(" GROUP BY ROLLUP(A.M030_ACCTYPE, B.M360_ACCCODE, B.M360_ACCNAME ,(A.M030_ORDERNO, A.M030_ORDERNAME))     \n");
        sb.append(" HAVING B.M360_ACCNAME IS NOT NULL                                                        \n");
        sb.append(" ORDER BY A.M030_ACCTYPE, B.M360_ACCCODE, A.M030_ORDERNO                                  \n");
			  sb.append("		            ) ORG                                                                      \n");
		    sb.append("		      WHERE ROWNUM <= ?                                                                \n");
		    sb.append("       ) LIST                                                                             \n");
		    sb.append(" WHERE LIST.RN > ?                                                                        \n");										   
				
				QueryTemplate template = new QueryTemplate(sb.toString());
				QueryParameters parameters = new QueryParameters();
				
				int idx = 1;
				
				parameters.setString(idx++, paramInfo.getString("acc_year"));
				parameters.setString(idx++, paramInfo.getString("acc_date"));
				if(!paramInfo.getString("acc_type").equals("")){
					parameters.setString(idx++, paramInfo.getString("acc_type"));
				}
				if(!paramInfo.getString("part_code").equals("")){
					parameters.setString(idx++, paramInfo.getString("part_code"));
				}
				if(!paramInfo.getString("acc_code").equals("")){
					parameters.setString(idx++, paramInfo.getString("acc_code"));
				}	

				return template.getList(conn, parameters);
			}
		}
		*/
		public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		    sb.append("SELECT LIST.*                                                                           \n");                                                                           
        sb.append("   FROM (                                                                               \n");
        sb.append("		SELECT *                                                                             \n");
				sb.append("    FROM (																																							 \n");
				sb.append("					SELECT M030_ACCTYPE																														 \n");
				sb.append("					     , M360_ACCCODE																														 \n");
				sb.append("					     , M030_ORDERNO																														 \n");
				sb.append("					     , M030_ORDERNAME																													 \n");
				sb.append("					     , M360_ACCNAME																														 \n");
				sb.append("					     , TOT_CNT																																 \n");
				sb.append("					     , TOT_AMT																																 \n");
				sb.append("					     , DENSE_RANK() OVER(ORDER BY M030_ACCTYPE, M360_ACCCODE  ) AS GROW        \n");
				sb.append("					  FROM (																																			 \n");
				sb.append("					          SELECT  A.M030_ACCTYPE, B.M360_ACCCODE                			         \n");
				sb.append("					                  ,A.M030_ORDERNO, A.M030_ORDERNAME ,B.M360_ACCNAME            \n");                    
				sb.append("					                  ,COUNT(1) TOT_CNT                                            \n");       
				sb.append("					                  ,SUM(A.M030_AMT) TOT_AMT                                     \n");       
				sb.append("					            FROM  M030_TAXOTHER_T A                                            \n");       
				sb.append("					                  ,M360_ACCCODE_T B                                            \n");       
				sb.append("					           WHERE  A.M030_YEAR = B.M360_YEAR                                    \n");       
				sb.append("					             AND  A.M030_INTYPE = 'I1'                                         \n");          
				sb.append("					             AND  A.M030_ACCTYPE = B.M360_ACCGUBUN                             \n");        
				sb.append("					             AND  A.M030_ACCCODE = B.M360_ACCCODE                              \n");        
				sb.append("					             AND  A.M030_YEAR = ?                                              \n");          
				sb.append("					             AND  A.M030_DATE = ?                                              \n");
				if(!paramInfo.getString("acc_type").equals("")){                                
				sb.append("                      AND  A.M030_ACCTYPE = ?                                           \n");
				}
				if(!paramInfo.getString("part_code").equals("")){
				sb.append("                      AND  A.M030_PARTCODE = ?                                          \n");
				}
				if(!paramInfo.getString("acc_code").equals("")){
				sb.append("                      AND  A.M030_ACCCODE = ?                                           \n");
				}
				sb.append("	GROUP BY ROLLUP(A.M030_ACCTYPE, B.M360_ACCCODE,B.M360_ACCNAME,(A.M030_ORDERNO, A.M030_ORDERNAME))  \n");
				sb.append("	HAVING B.M360_ACCNAME IS NOT NULL                                                                  \n");
				sb.append("					)                                                                               \n");
				sb.append("			    )                                                                               \n");
				sb.append("					WHERE GROW <= ?                                                                 \n");
				sb.append("					 ORDER BY M030_ACCTYPE, M360_ACCCODE, TO_NUMBER(M030_ORDERNO)                              \n");
				sb.append("  ) LIST                                                                                 \n");
        sb.append("     WHERE LIST.GROW > ?                                                                 \n");
								
				QueryTemplate template = new QueryTemplate(sb.toString());
				QueryParameters parameters = new QueryParameters();
				
				int idx = 1;
				
				parameters.setString(idx++, paramInfo.getString("acc_year"));
				parameters.setString(idx++, paramInfo.getString("acc_date"));
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
		
		/* 보고서 총 건수 조회 */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();	
			
			sb.append("SELECT  SUM(COUNT(DISTINCT B.M360_ACCNAME)) CNT                                                     \n");                                          
			sb.append("						  FROM  M030_TAXOTHER_T A                                                                \n");
			sb.append("						       ,M360_ACCCODE_T B                                                                 \n");
			sb.append("						 WHERE  A.M030_YEAR = B.M360_YEAR                                                        \n");
			sb.append("						   AND  A.M030_INTYPE = 'I1'                                                             \n");
			sb.append("						   AND  A.M030_ACCTYPE = B.M360_ACCGUBUN                                                 \n");
			sb.append("						   AND  A.M030_ACCCODE = B.M360_ACCCODE                                                  \n");
			sb.append("						   AND  A.M030_YEAR = ?                                                                  \n");
			sb.append("						   AND  A.M030_DATE = ?                                                                  \n");
			if(!paramInfo.getString("acc_type").equals("")){                                
      sb.append("              AND  A.M030_ACCTYPE = ?                                                               \n");
        }
      if(!paramInfo.getString("part_code").equals("")){
		  sb.append("              AND  A.M030_PARTCODE = ?                                                              \n");
	    	}
		  if(!paramInfo.getString("acc_code").equals("")){
		  sb.append("              AND  A.M030_ACCCODE = ?                                                               \n");
	    	}
      sb.append("         GROUP BY B.M360_ACCNAME                                                                    \n");                                                       
  	
			  QueryTemplate template = new QueryTemplate(sb.toString());
				QueryParameters parameters = new QueryParameters();
				
				int idx = 1;
				
				parameters.setString(idx++, paramInfo.getString("acc_year"));
				parameters.setString(idx++, paramInfo.getString("acc_date"));
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



