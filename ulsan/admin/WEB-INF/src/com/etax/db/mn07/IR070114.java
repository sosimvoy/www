/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070114.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 광역시세일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;                                            
import com.etax.entity.CommonEntity;

public class IR070114 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') TYPE 	\n");
		sb.append("      ,DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','지방세','2','세외수입','','','의존수입') GUBUN 	\n");
		sb.append("      ,(CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'				\n");	// 지방세 
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'				\n");	// 세외수입 (경상적)
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'				\n");	// 세외수입 (임시적)
		sb.append("				WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''				\n");	
		sb.append("				ELSE '30' END) GUBUN_CODE										\n");	// 의존수입
		sb.append("      ,B.M390_SEMOKCODE                                                      \n");
		sb.append("      ,C.M370_SEMOKNAME                                                      \n");
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
    sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0				                                                     \n");
		sb.append("                ELSE A.M220_AMTSEIPJEONILTOT END) AMTSEIPJEONILTOT_00000																			               \n");	// 본청 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
		sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0                                                            \n");
		sb.append("			       ELSE (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) END) TODAYAMT_00000										 \n");	// 본청 금일수입(세입액 - 과오납 - 세입정정액)  
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE IN  ('02240','02130','02140','02220','02190','00110','00140','00170','00200','00710')  \n");
		sb.append("                     OR A.M220_SUNAPGIGWANCODE = '310001' THEN 0					                                                   \n");
		sb.append("                ELSE (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)) END) TODAYAMTTOT_00000			\n");	// 본청 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN A.M220_AMTSEIPJEONILTOT																			\n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02240																									\n");	// 차량등록소 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)								\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02240																											\n");	// 차량등록소 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN A.M220_SUNAPGIGWANCODE = '310001' THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02240                                                                                                        \n");	// 차량등록소 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                                                                                                 \n");	// 합계 전일까지누계(세입전일누계)
		sb.append("      ,SUM(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYAMT                                                               \n");	// 합계 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)) TODAYAMTTOT                                \n");	// 합계 금일까지 누계 (전일누계 + 금일수입) 
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001'   \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			           \n");
    sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02130																									\n");	// 중부소방서 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001' 							\n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)					                                                     \n");
		sb.append("			       ELSE 0 END) TODAYAMT_02130																											\n");	// 중부소방서 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02130' AND A.M220_SUNAPGIGWANCODE <> '310001' 	\n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))  \n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02130                                                                                                        \n");	// 중부소방서 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT                                            \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02140																									\n");	// 남부소방서 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001' 					\n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)           \n");
		sb.append("			       ELSE 0 END) TODAYAMT_02140																											\n");	// 남부소방서 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02140                                                                            \n");	// 남부소방서 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02220																					\n");	// 농수산물도매시장 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02220																											\n");	// 농수산물도매시장 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02220' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02220                                                                                                        \n");	// 농수산물도매시장 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_02190																		  		\n");	// 문화예술회관 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_02190																											\n");	// 문화예술회관 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '02190' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_02190                                                                                                        \n");	// 문화예술회관 금일까지 누계 (전일누계 + 금일수입)
		
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n"); 
    sb.append("                    THEN A.M220_AMTSEIPJEONILTOT																			      \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00110																				  \n");	// 중구청 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00110																											\n");	// 중구청 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00110' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00110                                                                                                        \n");	// 중구청 금일까지 누계 (전일누계 + 금일수입)
	    
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00140																					\n");	// 남구청 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00140																											\n");	// 남구청 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00140' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00140                                                                                                        \n");	// 남구청 금일까지 누계 (전일누계 + 금일수입)
	
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00170																									\n");	// 동구청 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00170																											\n");	// 동구청 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00170' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00170                                                                                                        \n");	// 동구청 금일까지 누계 (전일누계 + 금일수입)
  
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00200																					\n");	// 북구청 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00200																											\n");	// 북구청 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00200' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00200                                                                                                        \n");	// 북구청 금일까지 누계 (전일누계 + 금일수입)
	
    sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN A.M220_AMTSEIPJEONILTOT																			          \n");
		sb.append("                ELSE 0 END) AMTSEIPJEONILTOT_00710																					\n");	// 울주군 전일까지누계(세입전일누계)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		\n");
		sb.append("			       ELSE 0 END) TODAYAMT_00710																											\n");	// 울주군 금일수입(세입액 - 과오납 - 세입정정액)
		sb.append("      ,SUM(CASE WHEN B.M390_PARTCODE = '00710' AND A.M220_SUNAPGIGWANCODE <> '310001'  \n");
    sb.append("                THEN (A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");
		sb.append("			       ELSE 0 END) TODAYAMTTOT_00710                                                                                                        \n");	// 울주군 금일까지 누계 (전일누계 + 금일수입)
	
    sb.append("  FROM M390_USESEMOKCODE_T B                     \n");
		sb.append("      ,M370_SEMOKCODE_T C                        \n");
		sb.append("      ,M220_DAY_T A                              \n");
		sb.append(" WHERE B.M390_YEAR      = C.M370_YEAR            \n");
		sb.append("   AND B.M390_ACCGUBUN  = C.M370_ACCGUBUN        \n");
		sb.append("   AND B.M390_ACCCODE   = C.M370_ACCCODE         \n");
		sb.append("   AND B.M390_SEMOKCODE = C.M370_SEMOKCODE       \n");
		sb.append("   AND B.M390_WORKGUBUN = C.M370_WORKGUBUN       \n");
		sb.append("   AND B.M390_YEAR      = A.M220_YEAR(+)         \n");
		sb.append("   AND B.M390_PARTCODE  = A.M220_PARTCODE(+)     \n");
		sb.append("   AND B.M390_ACCGUBUN  = A.M220_ACCTYPE(+)      \n");
		sb.append("   AND B.M390_ACCCODE   = A.M220_ACCCODE(+)      \n");
		sb.append("   AND B.M390_SEMOKCODE = A.M220_SEMOKCODE(+)    \n");
		sb.append("   AND B.M390_ACCGUBUN  = 'A'					\n");	// 회계구분 (A:일반회계)        
		sb.append("   AND B.M390_ACCCODE   = '11'					\n");	// 회계코드 (11)                
		sb.append("   AND B.M390_SEMOKCODE < 9000000				\n");	// 세목코드 (9000000 작은)  
        //sb.append("   AND B.M390_SEMOKCODE <> '6110300'             \n");   // 지방채 미포함
		sb.append("   AND B.M390_WORKGUBUN = '0'					\n");	// 업무구분 (0:세입)     
		sb.append("   AND C.M370_SEGUMGUBUN <> '2'                  \n");   //  국세제외   
		

		// WHERE START
		sb.append("   AND B.M390_YEAR = ?							\n");
		sb.append("   AND A.M220_DATE(+) = ?						\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND B.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND B.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND B.M390_ACCCODE = ?								\n");
		}
		// WHERE END


		sb.append("GROUP BY ROLLUP(DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3')           \n");
		sb.append("               ,(CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'                  \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'                 \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'                 \n");
		sb.append("                      WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''                  \n");
		sb.append("                      ELSE '30' END)                                                     \n");
		sb.append("              ,(B.M390_SEMOKCODE,C.M370_SEMOKNAME))                                      \n");
		sb.append(" HAVING ((CASE WHEN SUBSTR(B.M390_SEMOKCODE,1,1) = '1' THEN '10'                         \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '21' THEN '21'                        \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) = '22' THEN '22'                        \n");
		sb.append("               WHEN SUBSTR(B.M390_SEMOKCODE,1,2) IS NULL THEN ''                         \n");
		sb.append("               ELSE '30' END) IS NOT NULL                                                \n");
		sb.append("       OR DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') IS NULL         \n");
		sb.append("       OR DECODE(SUBSTR(B.M390_SEMOKCODE,1,1),'1','1','2','2','','','3') = '2')          \n");

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

    /* 보고서 조회 */
	public static CommonEntity getReportRemark(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT M470_REMARK1                                  \n");
		sb.append("      ,M470_REMARK2                                  \n");
		sb.append("      ,M470_REMARK3                                  \n");
    sb.append("      ,DECODE(M470_AMT1, 0, '', M470_AMT1) M470_AMT1 \n");
    sb.append("      ,DECODE(M470_AMT2, 0, '', M470_AMT2) M470_AMT2 \n");
    sb.append("      ,DECODE(M470_AMT3, 0, '', M470_AMT3) M470_AMT3 \n");
    sb.append("  FROM M470_DAYREMARK_T  \n");
		sb.append(" WHERE M470_YEAR = ?		\n");
		sb.append("   AND M470_DATE = ?		\n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		return template.getData(conn, parameters);
	}
}