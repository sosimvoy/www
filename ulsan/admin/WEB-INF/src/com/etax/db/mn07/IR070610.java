/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR070610.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-09-14
* 프로그램내용	   : 일계/보고서 > 감사자료조회
******************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070610 {

	/* 일일업무마감 */
	public static List<CommonEntity> getDataList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_YEAR                                           \n");
		sb.append("      ,A.M220_DATE                                           \n");
		
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)  AMTSEIPJEONILTOT		\n");	// 세입 전일누계    
		sb.append("      ,SUM(A.M220_AMTSEIP) AMTSEIP							\n");	// 세입 금일수입    
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP				\n");	// 세입 과오납반납  
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG		\n");	// 세입 과목정정액등
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) AMTSEIPTOT			\n");	// 세입 총계
		
		sb.append("      ,SUM(M220_AMTBAJEONGSUJEONILTOT + M220_AMTBAJEONGSURYUNG)  AMTBAEJUNG													\n");	// 자금배정액
		
		sb.append("      ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)						\n");
		sb.append("       +(M220_AMTBAJEONGSUJEONILTOT + M220_AMTBAJEONGSURYUNG)																\n");
		sb.append("       -(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)					\n");
		sb.append("       - A.M220_AMTBAJEONGJEONILTOT																							\n");
		sb.append("       - A.M220_AMTBAJEONG) AMTJAN							\n");	// 잔액                
																					                       
		sb.append("      ,SUM(A.M220_AMTJEONGGI) AMTJEONGGI						\n");	// 자금운용 정기예금등  

		sb.append("      ,B.M470_AMT1 REMARK_AMT1                               \n");	
		sb.append("      ,B.M470_AMT2 REMARK_AMT2                               \n");	
		sb.append("      ,B.M470_AMT3 REMARK_AMT3                               \n");
		sb.append("      ,B.M470_REMARK1 REMARK1                                \n");	
		sb.append("      ,B.M470_REMARK2 REMARK2                                \n");	
		sb.append("      ,B.M470_REMARK3 REMARK3                                \n");	
		
		sb.append("  FROM M220_DAY_T A											\n");
		sb.append("      ,M470_DAYREMARK_T B									\n");
		sb.append(" WHERE A.M220_YEAR = B.M470_YEAR(+)							\n");
		sb.append("   AND A.M220_DATE = B.M470_DATE(+)							\n");
		sb.append("   AND A.M220_ACCTYPE = 'A'									\n");   // 일반회계
		sb.append("   AND A.M220_YEAR = ?										\n");
		sb.append("   AND A.M220_DATE BETWEEN ? AND ?							\n");
		sb.append(" GROUP BY A.M220_YEAR,A.M220_DATE					        \n");
		sb.append("         ,B.M470_AMT1                                        \n");	
		sb.append("         ,B.M470_AMT2                                        \n");	
		sb.append("         ,B.M470_AMT3                                        \n");
		sb.append("         ,B.M470_REMARK1                                     \n");	
		sb.append("         ,B.M470_REMARK2                                     \n");	
		sb.append("         ,B.M470_REMARK3                                     \n");
		sb.append(" ORDER BY A.M220_YEAR,A.M220_DATE					        \n");
		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_sdate"));
		parameters.setString(idx++, paramInfo.getString("acc_edate"));
		

		return template.getList(conn, parameters);
    }
}