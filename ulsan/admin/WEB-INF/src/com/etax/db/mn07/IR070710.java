/**********************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070710.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-10
* 프로그램내용   : 일계보고서 > 국세수납합계표조회
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070710 {

  /* 국세수납 총합계표 */
	public static List<CommonEntity> getTaxTotal(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT  TO_CHAR(A.TMP_DATE,'YYYY.MM.DD') TMP_DATE                                                      \n");
	  sb.append("       ,SUM(B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG) SPECIAL_TAX             \n"); //등록농특세,국고합계
    sb.append("  FROM (SELECT TO_DATE( ?, 'YYYYMMDD') + (LEVEL - 1) TMP_DATE                                          \n");	 
		sb.append("          FROM DUAL                                                                                    \n");
		sb.append("        CONNECT BY LEVEL <= ?) A                                                                       \n");		 
    sb.append("       ,M220_DAY_T B                                                                                   \n");	
    sb.append(" WHERE  A.TMP_DATE = B.M220_DATE(+)                                                                    \n");					
    sb.append(" 	AND  B.M220_ACCTYPE(+)   = 'A'                                                                      \n"); //회계구분 A
		sb.append(" 	AND  B.M220_ACCCODE(+)    = '11'                                                                    \n"); //회계코드 11
    sb.append("   AND  B.M220_SEMOKCODE(+)  = '1199900'                                                               \n"); //세목코드 1199900
		sb.append("   AND  B.M220_SUNAPGIGWANCODE(+)  = '310001'                                                          \n"); //수납기관코드 310001
		sb.append(" GROUP BY ROLLUP(A.TMP_DATE)                                                                           \n");
    sb.append(" ORDER BY A.TMP_DATE                                                                                   \n");            		
																																						           																							
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
	
		parameters.setString(idx++, paramInfo.getString("first_date"));
		parameters.setString(idx++, paramInfo.getString("last_day"));
		
		return template.getList(conn, parameters);
	}

  /* 세입금 이체필 통지서 */
	public static List<CommonEntity> getTaxTransfer(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT SUM(B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG) SPECIAL_TAX                                                        \n"); //농특세                                                                
	  sb.append("      ,SUM(DECODE(B.M220_DATE, ?, B.M220_AMTSEIPJEONILTOT + B.M220_AMTSEIP - B.M220_AMTSEIPGWAONAP - B.M220_AMTSEIPJEONGJEONG, 0)) END_DATE_TAX  \n"); //합계
    sb.append("  FROM  M220_DAY_T B                                                                                                                             \n");	 				
    sb.append(" WHERE  B.M220_ACCTYPE(+)   = 'A'                                                                                                                \n"); //회계구분 A
		sb.append(" 	AND  B.M220_ACCCODE(+)    = '11'                                                                                                              \n"); //회계코드 11
    sb.append("   AND  B.M220_SEMOKCODE(+)  = '1199900'                                                                                                         \n"); //세목코드 1199900
		sb.append("   AND  B.M220_SUNAPGIGWANCODE(+)  = '310001'                                                                                                    \n"); //수납기관코드 310001
    sb.append("   AND  B.M220_YEAR = ?                                                                                                                          \n");
    sb.append("   AND  B.M220_DATE LIKE ?                                                                                                                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
	
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_year") + paramInfo.getString("acc_month") +  "%" );
		
		return template.getList(conn, parameters);
	}
}

