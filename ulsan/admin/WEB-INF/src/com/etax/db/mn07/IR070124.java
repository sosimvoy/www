/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070124.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입세출외현금지급증명서
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070124 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
     
		sb.append("SELECT LIST.*                                                                                       \n");                                                                      
		sb.append("   FROM (                                                                                           \n");
		sb.append("   SELECT *                                                                                         \n");
		sb.append("     FROM (                                                                                         \n");
		sb.append("            SELECT M040_ACCCODE                                                                     \n");
		sb.append("                  ,M040_ORDERNO                                                                     \n");
		sb.append("                  ,M040_ORDERNAME                                                                   \n");
		sb.append("                  ,M040_NOTE                                                                        \n"); 
		sb.append("                  ,TOT_CNT                                                                          \n");
		sb.append("                  ,TOT_AMT                                                                          \n");
		sb.append("                  ,M360_ACCNAME                                                                     \n");
		sb.append("                  ,DENSE_RANK() OVER(ORDER BY M040_ACCCODE  ) AS GROW                               \n");
		sb.append("              FROM (                                                                                \n");
		sb.append("                    SELECT  A.M040_ACCCODE ,A.M040_ORDERNO, A.M040_ORDERNAME, A.M040_NOTE, B.M360_ACCNAME   \n");	         
		sb.append("                            ,COUNT(1) TOT_CNT                                                       \n");
		sb.append("                            ,SUM(M040_AMT) TOT_AMT                                                  \n");
		sb.append("                       FROM  M040_TAXCASH_T A                                                       \n");
		sb.append("                            ,M360_ACCCODE_T B                                                       \n");
		sb.append("                      WHERE  M040_DWTYPE = 'G2'                                                     \n");
        sb.append("                        AND  B.M360_ACCGUBUN = 'D'                                                  \n");
		sb.append("                        AND  A.M040_YEAR = B.M360_YEAR                                              \n");
		sb.append("                        AND  A.M040_ACCCODE(+) = B.M360_ACCCODE                                     \n");
		sb.append("                        AND  A.M040_YEAR = ?                                                        \n");
		sb.append("                        AND  A.M040_DATE = ?                                                        \n");
	    if(!paramInfo.getString("acc_code").equals("")){                                               
		    sb.append("                        AND  A.M040_ACCCODE = ?                                                     \n"); 
		}
		sb.append("                      GROUP BY ROLLUP(A.M040_ACCCODE, B.M360_ACCNAME,(A.M040_ORDERNO, A.M040_ORDERNAME, A.M040_NOTE))      \n");
		sb.append("                     HAVING B.M360_ACCNAME IS NOT NULL                                              \n");      
		sb.append("                    )                                                                               \n");
		sb.append("          )                                                                                         \n");
		sb.append("    WHERE GROW <= ?                                                                                 \n");
		sb.append("     ORDER BY M040_ACCCODE , M040_ORDERNO                                                           \n");
		sb.append("  ) LIST                                                                                            \n");
		sb.append("     WHERE LIST.GROW > ?                                                                            \n");

  		QueryTemplate template = new QueryTemplate(sb.toString());
	  	QueryParameters parameters = new QueryParameters();
    
		  int idx = 1;
		  parameters.setString(idx++, paramInfo.getString("acc_year"));
		  parameters.setString(idx++, paramInfo.getString("acc_date"));
		  
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
       
		 sb.append("  SELECT  SUM(COUNT(DISTINCT B.M360_ACCNAME)) CNT   \n");                                                  
     sb.append("    FROM  M040_TAXCASH_T A                          \n");
		 sb.append("         ,M360_ACCCODE_T B                          \n");
     sb.append("   WHERE  M040_DWTYPE = 'G2'                        \n");
		 sb.append("     AND  B.M360_ACCGUBUN = 'D'                     \n");
     sb.append("     AND  A.M040_YEAR = B.M360_YEAR                 \n");
     sb.append("     AND  A.M040_ACCCODE(+) = B.M360_ACCCODE        \n");
     sb.append("     AND  A.M040_YEAR = ?                           \n");                                              
     sb.append("     AND  A.M040_DATE = ?                           \n");                                                                                                              
      if(!paramInfo.getString("acc_code").equals("")){
		 sb.append("     AND  A.M040_ACCCODE = ?                        \n");
	    }
		 sb.append("   GROUP BY B.M360_ACCNAME                          \n");   
     
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("acc_code").equals("")){
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		return template.getData(conn, parameters);
	}
}