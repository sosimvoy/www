/***********************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	   : IR070420.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 일계/보고서 > 세입일계표 조정내역조회
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070420 {
  
	/* 일일비고 조회 */ 
	public static CommonEntity getBigoData(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  A.*                                                     \n");
        sb.append("      ,  (M470_CAR_SUM_GC01 + M470_CAR_SUM_GC02                  \n");
        sb.append("      +  M470_BAL_SUM_GP01 + M470_BAL_SUM_GC01                   \n");
        sb.append("      +  M470_BAL_SUM_GC02) IL_MI_AMT                            \n");
        sb.append("      ,  (M470_CAR_SUM_GC01 + M470_CAR_SUM_GC02                  \n");
        sb.append("      +  M470_NONG_SUM_GC01 + M470_NONG_SUM_GC02 ) CAR_MI_AMT    \n");
		sb.append("   FROM  M470_DAYBIGO_T A	                                    \n");
		sb.append("  WHERE  M470_YEAR	= ?	   	                                    \n");
		sb.append("    AND  M470_DATE = ?                                           \n");	
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        
        return template.getData(conn, parameters);
    }


    /* 일반회계 공금잔액 조회 */ 
	public static CommonEntity getIlgyeData(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT ACCTYPE, ACCTYPENM, SUMGUBUN, ACCCODE, ACCNAME                                                                  \n");              
        sb.append("     ,  AMTSEIPJEONILTOT, AMTSEIP, AMTSEIPGWAONAP, AMTSEIPJEONGJEONG                                                    \n");
        sb.append("     ,  AMTSEIPTOT, AMTBAEJUNG, AMTSECHULJEONILTOT, AMTSECHUL                                                           \n");
        sb.append("     ,  AMTSECHULBANNAP, AMTSECHULJEONGJEONG,  AMTSECHULTOT, AMTJAN                                                     \n");
        sb.append("     ,  AMTJEONGGI, AMTGONGGEUM, AMTSURPLUS                                                                             \n");
        sb.append("     ,  LAST_AMT, M220_AMTJAN, (NVL(AMTJAN,0)-NVL(AMTJEONGGI,0)-ROUND(NVL(SEGYE_AMT,0),0)) GONGGEUMJANAMT               \n");
        sb.append("     ,  ROUND(NVL(SEGYE_AMT, 0), 0) SEGYE_AMT                                                                           \n");
        sb.append("   FROM (                                                                                                               \n");
        sb.append("  SELECT M390_ACCGUBUN  ACCTYPE                                                                                         \n");                               
        sb.append("        ,CASE WHEN M390_ACCGUBUN = 'E'                                                                                  \n");                               
        sb.append("          AND M390_ACCCODE = '40'                                                                                       \n");                                  
        sb.append("         THEN '기타'                                                                                                    \n");                               
        sb.append("         WHEN M390_ACCGUBUN = 'A'                                                                                       \n");                                
        sb.append("         THEN '일반회계'                                                                                                \n");                                                                                                                                                                 
        sb.append("         WHEN M390_ACCGUBUN = 'B'                                                                                       \n");                                                                                                                                                      
        sb.append("         THEN '특별회계'                                                                                                \n");                                                                                                                                                                 
        sb.append("         WHEN M390_ACCGUBUN = 'D'                                                                                       \n");                                
        sb.append("         THEN '세입세출외현금'                                                                                          \n");                                                                                                                                                           
        sb.append("         WHEN M390_ACCGUBUN = 'E'                                                                                       \n");                                                                                                                                                      
        sb.append("         AND M390_ACCCODE <> '40'                                                                                       \n");                                                                                                                                                      
        sb.append("         THEN '기금'                                                                                                    \n");                                   
        sb.append("          END ACCTYPENM                                                                                                 \n");                                 
        sb.append("        ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) AS SUMGUBUN                                                    \n");                                
        sb.append("        ,A.M390_ACCCODE AS ACCCODE                                                                                      \n");                               
        sb.append("        ,DECODE(B.M360_ACCNAME,'일반회계','본청(세입)', B.M360_ACCNAME) AS ACCNAME                                      \n");                                                                                                                                                                                                                                 
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT,0))  AMTSEIPJEONILTOT     -- 세입 전일누계             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIP,0)) AMTSEIP                        -- 세입 금일수입             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPGWAONAP,0)) AMTSEIPGWAONAP          -- 세입 과오납반납           \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPJEONGJEONG    -- 세입 과목정정액등         \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP                                       \n");
        sb.append("                   - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPTOT       -- 세입 총계                 \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT                                                  \n");
        sb.append("                                       + C.M220_AMTBAJEONGSURYUNG),0))  AMTBAEJUNG         -- 자금배정액                \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT  -- 세출 전일누계             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHUL,0)) AMTSECHUL                    -- 세출 당일지급             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULBANNAP,0)) AMTSECHULBANNAP        -- 세출 반납액               \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONGJEONG,0)) AMTSECHULJEONGJEONG  -- 세출 과목정정액등       \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL - C.M220_AMTSECHULBANNAP         \n");
        sb.append("                       - C.M220_AMTSECHULJEONGJEONG),0)) AMTSECHULTOT                        -- 세출 총계               \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT                                                        \n");
        sb.append("                     + C.M220_AMTSEIP - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)                             \n");
        sb.append("         + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                     \n");                               
        sb.append("         - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                                    \n");
        sb.append("                                   - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                            \n");
        sb.append("         - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                                    \n");
        sb.append("         - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0)) AMTJAN                      -- 잔액                        \n");                                                                                                                                                                              
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'3',C.M220_AMTJEONGGI,0)) AMTJEONGGI               -- 자금운용 정기예금등          \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) AMTGONGGEUM               -- 자금운용 공금잔액            \n");                                                                                                                                                                                             
        sb.append("        ,SUM(CASE WHEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0) > 0              \n");
        sb.append("                  THEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0)                  \n");
        sb.append("                  ELSE 0 END) AMTSURPLUS                                                -- 잉여금이입액                 \n");                                                                                                                                                                                     
        sb.append("        ,SUM((DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP                                      \n");
        sb.append("                     - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)                                              \n");
        sb.append("              + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                \n");                                                        
        sb.append("              - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                               \n");
        sb.append("              - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                                                 \n");
        sb.append("              - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                               \n");                                 
        sb.append("              - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0))                                                       \n");                               
        sb.append("            - DECODE(A.M390_WORKGUBUN,'3',M220_AMTJEONGGI,0)                                                            \n");                               
        sb.append("            - DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) LAST_AMT  -- (잔액일치여부: 잔액 - 정기예금 - 공금장액)  \n");
        sb.append("        ,SUM(NVL(C.M220_AMTGONGGEUM,0))  M220_AMTJAN  -- 세입 전일누계                                                  \n");
        sb.append("        ,SUM(CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                        \n");
        sb.append("                 AND TRIM(M390_ACCCODE) IS NOT NULL                                                                     \n");
        sb.append("                THEN CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'A1'                                      \n");
        sb.append("                                    AND M390_ACCCODE = '11'                                                             \n");
        sb.append("                                  THEN NVL(M580_AMT,0)/237                                                              \n");
        sb.append("                          WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'A1'                                     \n");
        sb.append("                                    AND M390_ACCCODE IN ('15', '16')                                                    \n");
        sb.append("                                  THEN NVL(M580_AMT,0)                                                                  \n");
        sb.append("                                 ELSE NVL(M580_AMT,0)/3                                                                 \n");
        sb.append("                           END                                                                                          \n");
        sb.append("              WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                            \n");
        sb.append("                 AND TRIM(M390_ACCCODE) IS NULL                                                                         \n");
        sb.append("              THEN NVL(M580_AMT,0)                                                                                      \n");
        sb.append("               ELSE 0                                                                                                   \n");
        sb.append("       END) SEGYE_AMT    -- 세계현금전용                                                                                \n");
        sb.append("   FROM M390_USESEMOKCODE_T A                                                                                           \n");                              
        sb.append("       ,M360_ACCCODE_T B                                                                                                \n");                                  
        sb.append("       ,M220_DAY_T C                                                                                                    \n");                                
        sb.append("       ,M370_SEMOKCODE_T D                                                                                              \n");
        sb.append("       ,(SELECT SUM(M580_AMT) M580_AMT                                                                                  \n");
        sb.append("              , M580_ACCTYPE, M580_ACCCODE                                                                              \n");
        sb.append("           FROM M580_SEGYE_T                                                                                            \n");
        sb.append("          WHERE M580_YEAR = ?                                                                                           \n");
        sb.append("            AND M580_DATE <= ?                                                                                          \n");
        sb.append("          GROUP BY M580_ACCTYPE, M580_ACCCODE) E                                                                        \n");
        sb.append("  WHERE A.M390_YEAR           = B.M360_YEAR                                                                             \n");                                               
        sb.append("    AND A.M390_ACCGUBUN       = B.M360_ACCGUBUN                                                                         \n");                                           
        sb.append("    AND A.M390_ACCCODE        = B.M360_ACCCODE                                                                          \n");                                
        sb.append("    AND A.M390_YEAR            = C.M220_YEAR(+)                                                                         \n");                                     
        sb.append("    AND A.M390_PARTCODE        = C.M220_PARTCODE(+)                                                                     \n");                                   
        sb.append("    AND A.M390_ACCGUBUN       = C.M220_ACCTYPE(+)                                                                       \n");                              
        sb.append("    AND A.M390_ACCCODE        = C.M220_ACCCODE(+)                                                                       \n");                              
        sb.append("    AND A.M390_SEMOKCODE      = C.M220_SEMOKCODE(+)                                                                     \n");                              
        sb.append("    AND D.M370_SEGUMGUBUN <> '2'         --  국세제외                                                                   \n");                                
        sb.append("    AND A.M390_ACCGUBUN <> 'D'                                                                                          \n");                              
        sb.append("    AND A.M390_YEAR           = D.M370_YEAR                                                                             \n");                                             
        sb.append("    AND A.M390_ACCGUBUN       = D.M370_ACCGUBUN                                                                         \n");                                           
        sb.append("    AND A.M390_ACCCODE        = D.M370_ACCCODE                                                                          \n");                                            
        sb.append("    AND A.M390_WORKGUBUN        = D.M370_WORKGUBUN                                                                      \n");                                              
        sb.append("    AND A.M390_SEMOKCODE        = D.M370_SEMOKCODE                                                                      \n");
        sb.append("    AND A.M390_ACCGUBUN = E.M580_ACCTYPE(+)                                                                             \n");
        sb.append("    AND A.M390_ACCCODE = E.M580_ACCCODE(+)                                                                              \n");
        sb.append("    AND A.M390_ACCGUBUN = 'A'                                                                                           \n");                               
        sb.append("    AND A.M390_YEAR = ?                                                                                                 \n");                                                        
        sb.append("    AND C.M220_DATE(+) = ?                                                                                              \n");
		sb.append("   AND A.M390_ACCCODE = '11'								\n");
        sb.append(" GROUP BY ROLLUP(A.M390_ACCGUBUN                                                                                        \n");
        sb.append("         ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1))                                                               \n");
        sb.append("         ,(A.M390_ACCCODE                                                                                               \n");
        sb.append("         ,B.M360_ACCNAME))                                                                                              \n");
        sb.append(" HAVING ((A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) = 'A1'                                                         \n");
        sb.append("       OR (A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) IS NULL                                                       \n");
        sb.append("       OR A.M390_ACCCODE IS NOT NULL) -- 일반회계-본청, 특별회계-교통사업만 중간소계를 보여준다                         \n");
        sb.append(" ORDER BY ACCTYPE                                                                                                       \n");
        sb.append("         ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1))                                                               \n");
        sb.append("         ,A.M390_ACCCODE                                                                                                \n");
        sb.append("         )                                                                                                              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        
        return template.getData(conn, parameters);
    }


    /* 차량사업소 세입누계 조회 */
	public static CommonEntity getCargyeData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
        
        sb.append("SELECT * FROM (                                                              \n");
		sb.append("SELECT B.M390_SEMOKCODE                                                      \n");
		sb.append("      ,CASE WHEN B.M390_SEMOKCODE IS NULL                                    \n");
        sb.append("            THEN ''                                                          \n");
        sb.append("            ELSE MAX(D.M370_SEMOKNAME)                                       \n");
        sb.append("        END M370_SEMOKNAME                                                   \n");
        sb.append("      ,'' M350_PARTCODE                                                      \n");
        sb.append("      ,CASE WHEN B.M390_SEMOKCODE IS NOT NULL                                \n");
        sb.append("            THEN ''                                                          \n");
        sb.append("            ELSE '합 계'                                                     \n");
        sb.append("        END M350_PARTNAME                                                    \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                         \n");
		sb.append("      ,SUM(A.M220_AMTSEIP) AMTSEIP                                           \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP                             \n");
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP) HWANBUTOT     \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                       \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) TODAYTOT  \n");
		sb.append("  FROM M390_USESEMOKCODE_T B                                                 \n");
		sb.append("      ,M350_PARTCODE_T C                                                     \n");
		sb.append("      ,M370_SEMOKCODE_T D                                                    \n");
		sb.append("      ,M220_DAY_T A                                                          \n");
		sb.append(" WHERE B.M390_YEAR      = C.M350_YEAR                                        \n");
		sb.append("   AND B.M390_PARTCODE  = C.M350_PARTCODE                                    \n");
		sb.append("   AND B.M390_YEAR      = D.M370_YEAR                                        \n");
		sb.append("   AND B.M390_ACCGUBUN  = D.M370_ACCGUBUN                                    \n");
		sb.append("   AND B.M390_ACCCODE   = D.M370_ACCCODE                                     \n");
		sb.append("   AND B.M390_SEMOKCODE = D.M370_SEMOKCODE                                   \n");
		sb.append("   AND B.M390_WORKGUBUN = D.M370_WORKGUBUN                                   \n");
		sb.append("   AND B.M390_YEAR      = A.M220_YEAR(+)                                     \n");
		sb.append("   AND B.M390_PARTCODE  = A.M220_PARTCODE(+)                                 \n");
		sb.append("   AND B.M390_ACCGUBUN  = A.M220_ACCTYPE(+)                                  \n");
		sb.append("   AND B.M390_ACCCODE   = A.M220_ACCCODE(+)                                  \n");
		sb.append("   AND B.M390_SEMOKCODE = A.M220_SEMOKCODE(+)                                \n");
		sb.append("   AND B.M390_ACCGUBUN  = 'A'                                                \n");
		sb.append("   AND B.M390_ACCCODE   = '11'                                               \n");    // 세목(등록세,지방교육세,농어촌특별세)  
		sb.append("   AND B.M390_SEMOKCODE IN ('1110100', '1110200','1120500','1199900')        \n");    // 부서(중구,남구,동구,북구,울주군)    
		sb.append("   AND B.M390_PARTCODE IN ('00110','00140','00170','00200','00710')          \n");    // 수납기관코드 (울산차량등록사업소)     
		sb.append("   AND B.M390_WORKGUBUN = '0'                                                \n");    // 세입                                
		sb.append("   AND A.M220_SUNAPGIGWANCODE(+) = '310001'                                  \n");
        sb.append("   AND B.M390_YEAR = ?										                \n");
		sb.append("   AND A.M220_DATE(+) = ?									                \n");
		sb.append(" GROUP BY ROLLUP(B.M390_SEMOKCODE)                                           \n");
		sb.append("	ORDER BY B.M390_SEMOKCODE )                                                 \n");
        sb.append(" WHERE M350_PARTNAME = '합 계'                                               \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}

}