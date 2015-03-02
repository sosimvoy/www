/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070111.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-09
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입금일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070111 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','지방세','2','세외수입','','','의존수입')  GUBUN                  \n");
		sb.append("      ,B.M390_SEMOKCODE                                                                                          \n");
		sb.append("      ,C.M370_SEMOKNAME                                                                                          \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) M220_AMTSEIP                                                                          \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) M220_AMTSEIPGWAONAP                                                            \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP + M220_AMTGWAONAPJEONILTOT) M220_AMTSEIPGWAONAPTOT                              \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) M220_AMTSEIPJEONGJEONG                                                      \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) TODAY_TOT  \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                                                     \n");
		sb.append("      ,M370_SEMOKCODE_T C                                                                                        \n");
		sb.append("      ,M220_DAY_T A                                                                                              \n");
		sb.append(" WHERE B.M390_YEAR = C.M370_YEAR                                                                                 \n");
		sb.append("   AND B.M390_ACCGUBUN      = C.M370_ACCGUBUN                                                                    \n");
		sb.append("   AND B.M390_ACCCODE       = C.M370_ACCCODE                                                                     \n");
		sb.append("   AND B.M390_SEMOKCODE     = C.M370_SEMOKCODE                                                                   \n");
    sb.append("   AND B.M390_WORKGUBUN     = C.M370_WORKGUBUN                                                                   \n");
		sb.append("   AND B.M390_YEAR          = A.M220_YEAR(+)                                                                     \n");
		sb.append("   AND B.M390_ACCGUBUN      = A.M220_ACCTYPE(+)                                                                  \n");
		sb.append("   AND B.M390_ACCCODE       = A.M220_ACCCODE(+)                                                                  \n");
		sb.append("   AND B.M390_SEMOKCODE     = A.M220_SEMOKCODE(+)                                                                \n");
		sb.append("   AND B.M390_PARTCODE      = A.M220_PARTCODE(+)                                                                 \n");
		sb.append("   AND B.M390_ACCGUBUN      = 'A'                                                                                \n");	// 회계구분 (A:일반회계)        
		sb.append("   AND B.M390_ACCCODE       = '11'                                                                               \n");	// 회계코드 (11)                
		sb.append("   AND B.M390_WORKGUBUN     = '0'                                                                                \n");	// 세목코드 (9000000 작은)  
		sb.append("   AND B.M390_SEMOKCODE < 9000000                                                                                \n");	// 0: 세입
		sb.append("   AND SUBSTR(B.M390_SEMOKCODE,1,1) <> '9'                                                                       \n");
        sb.append("   AND C.M370_SEGUMGUBUN <> '2'                                                                                  \n");   //  국세제외

        /* WHERE START */
		sb.append("   AND B.M390_YEAR = ?																							\n");
		sb.append("   AND A.M220_DATE(+) = ?																						\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND B.M390_ACCGUBUN = ?																					\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND B.M390_PARTCODE = ?																					\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND B.M390_ACCCODE = ?																					\n");
		}
        /* WHERE END */
		
		sb.append(" GROUP BY ROLLUP(DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','지방세','2','세외수입','','','의존수입')  \n");
		sb.append("                ,(B.M390_SEMOKCODE ,C.M370_SEMOKNAME))                                              \n");
		//sb.append(" ORDER BY DECODE(SUBSTR(A.M220_SEMOKCODE,1,1),'1','지방세','2','세외수입','','','의존수입')  , A.M220_SEMOKCODE	\n");

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