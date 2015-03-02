/*****************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	    : IR010710.java
* 프로그램작성자	: (주)미르이즈 
* 프로그램작성일	: 2010-09-08
* 프로그램내용		: 세입 > OCR세입집계/취소 
* 프로그램비고      : 1. 입력한 회계일자 기준 세입수기분&세외수입OCR자료 데이터 유무 확인(회계일 기준 기등록시 재등록 불가)
                      2. 해당 회계일자(회계일자,회계코드,세목코드,부서코드기준) 세입수기분 등록
                      3. 회계년도 및 세목코드 산출
                       1) 회계일자 년도 = 부과년도
                         -> 회계년도 = 부과년도, 세목코드 = 변환표준세목코드
                       2) 회계일자 년도 > 부과년도 
                        2-1) 회계일자 월일 >= '0301'
                         -> 회계년도 = 회계일자 년도, 세목코드 = 과년도수입(2290100)
                        2-2) 회계이자 월일 < '0301'
                         i) (회계일자 년도 - 1) = 부과년도
                          -> 회계년도 = (회계일자 년도 - 1), 세목코드 = 변환표준세목코드
                         ii) (회계일자 년도 - 1) > 부과년도
                          -> 회계년도 = (회계일자 년도 - 1), 세목코드 = 과년도수입(2290100)
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010710 {
  
	/* 수기분등록 */
    public static int ocrInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M010_TAXIN_T													\n");
		sb.append("			(M010_SEQ, M010_YEAR										  		\n"); 		//일련번호, 회계년도
		sb.append("			,M010_DATE, M010_ACCTYPE											\n"); 		//회계일자, 회계구분
		sb.append("			,M010_ACCCODE, M010_SEMOKCODE, M010_YEARTYPE			  	        \n"); 		//회계코드, 세목코드
		sb.append("			,M010_PARTCODE,M010_SUNAPGIGWANCODE						            \n");		//부서코드, 수납기관코드
		sb.append("			,M010_INTYPE, M010_AMT					                            \n"); 		//입력구분, 년도구분, 금액
		sb.append("			,M010_CNT, M010_LOGNO											    \n"); 		//건수,		로그번호
		sb.append("			,M010_WORKTYPE, M010_TRANSGUBUN)							        \n"); 		//업무구분, 거래구분
		sb.append("SELECT M010_SEQ.NEXTVAL AS M010_SEQ                                          \n");
		sb.append("     , X.*                                                                   \n");
		sb.append("   from (                                                                    \n");
		sb.append("SELECT X.M010_YEAR, X.M010_DATE                                              \n");
        sb.append("     , X.M010_ACCTYPE, X.M010_ACCCODE, X.M010_SEMOKCODE                      \n");
        sb.append("     , DECODE(X.M010_SEMOKCODE, '1130100', 'Y2', '2290100', 'Y2', 'Y1') AS M010_YEARTYPE \n");
        sb.append("     , X.M010_PARTCODE, X.M010_SUNAPGIGWANCODE, X.M010_INTYPE                \n");
        sb.append("     , X.M010_AMT, X.M010_CNT                                                \n");
        sb.append("     , X.M010_LOGNO, X.M010_WORKTYPE, X.M010_TRANSGUBUN                      \n");
		sb.append("	 FROM (																	    \n");		//일련번호
		sb.append("		    WITH TMPOCR AS (SELECT A.M010_YEAR                                                                                                                                           	\n");		// 회계일자        
		sb.append("		          ,A.ETC_ACCDATE AS M010_DATE                                                                                                                               	\n");		// 회계구분(h)     
		sb.append("		          ,? AS M010_ACCTYPE                                                                                                                                      	\n");		// 회계코드(변환)  
		sb.append("		          ,B.M420_SYSTEMACCCODE AS M010_ACCCODE                                                                                                                     	\n"); 
		sb.append("		          ,CASE WHEN SUBSTR(A.ETC_ACCDATE,1,4) = A.ETC_YYYY THEN B.M420_SYSTEMSEMOKCODE                                                                             	\n");		 
		sb.append("		                WHEN SUBSTR(A.ETC_ACCDATE,1,4) > A.ETC_YYYY                                                                                                         	\n");		
		sb.append("		                THEN (CASE WHEN SUBSTR(A.ETC_ACCDATE,5,8) >= '0301' THEN '2290100'                                                                                  	\n");		
		sb.append("		                           ELSE (CASE WHEN TO_CHAR(SUBSTR(A.ETC_ACCDATE,1,4) - 1) = A.ETC_YYYY THEN B.M420_SYSTEMSEMOKCODE                                          	\n");		
		sb.append("		                                      ELSE '2290100' END)                                                                                                           	\n");		
		sb.append("		                           END)                                                                                                                                     	\n");		
		sb.append("		            END M010_SEMOKCODE                                                                                                                                      	\n");	    // 세목코드(변환 - 계산)	
		sb.append("		          ,C.M410_SYSTEMPARTCODE AS M010_PARTCODE                                                                                                                   	\n");	// 부서코드(변환)  	
		sb.append("		          ,?    AS M010_SUNAPGIGWANCODE                                                                                                                      	\n");	// 수납기관코드(h) 	
		sb.append("		          ,?    AS M010_INTYPE                                                                                                                                   	\n");	// 입력구분(h)     	
		sb.append("		          ,?    AS M010_YEARTYPE                                                                                                                                 	\n");   // 년도구분(h)      
		sb.append("		          ,A.ETC_AMT2 AS M010_AMT                                                                                                                                   	\n");   // 금액             
		sb.append("		          ,1       AS M010_CNT                                                                                                                                      	\n");   // 건수             
		sb.append("		          ,?    AS M010_LOGNO                                                                                                                                   	\n");   // 로그번호(req)    
		sb.append("		          ,?    AS M010_WORKTYPE                                                                                                                                	\n");   // 업무구분(req)    
		sb.append("		          ,?    AS M010_TRANSGUBUN                                                                                                                              	\n");   // 거래구분(req)    
		sb.append("		      FROM (                                                                                                                                                  	  \n");
        sb.append("              SELECT CASE                                                                                                                                          \n");
        sb.append("                     WHEN SUBSTR(ETC_ACCDATE,1,4) = ETC_YYYY  THEN ETC_YYYY                                                                                  \n");
        sb.append("                     WHEN SUBSTR(ETC_ACCDATE,1,4) > ETC_YYYY                                                                                                   \n");
        sb.append("                     THEN  (CASE                                                                                                                                   \n");
        sb.append("                            WHEN SUBSTR(ETC_ACCDATE,5,8) >= '0301' THEN SUBSTR(ETC_ACCDATE,1,4)                                                                \n");
        sb.append("                            ELSE  TO_CHAR(SUBSTR(ETC_ACCDATE,1,4) - 1) END)                                                                                      \n");
        sb.append("                     END AS M010_YEAR                                                                                                                              \n");
        sb.append("                   , T.*                                                                                                                                    \n");
        sb.append("                FROM ETC_T  T                                                                                                                                       \n");
        sb.append("               WHERE ETC_JOBDATE = ? ) A                                                                                                                           \n");
		sb.append("		          ,M420_STANDARDSEMOKCODE_T B                                                                                                                               	\n");
		sb.append("		          ,M410_STANDARDPARTCODE_T C                                                                                                                                	\n");
		sb.append("		     WHERE A.ETC_ACC = B.M420_STANDARDACCCODE                                                                                                                       	\n");
		sb.append("		       AND A.ETC_TAX = B.M420_STANDARDSEMOKCODE                                                                                                                     	\n");
        sb.append("		       AND A.M010_YEAR = B.M420_YEAR                                                                                                                     	\n");
		sb.append("		       AND A.ETC_PARTCODE = C.M410_STANDARDPARTCODE                                                                                                                 	\n");
		sb.append("		       AND A.M010_YEAR = C.M410_YEAR                                                                                                                     	\n");
		sb.append("		     )                                                                                                                                                              	\n");
        sb.append("		     SELECT M010_YEAR                                                                                                                                               	\n");
		sb.append("		            , M010_DATE                                                                                                                                             	\n");
		sb.append("		            , M010_ACCTYPE                                                                                                                                          	\n");
		sb.append("		            , M010_ACCCODE                                                                                                                                          	\n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , SUM(M010_AMT) AS M010_AMT                                                                                                                               \n");
		sb.append("               , SUM(M010_CNT) AS M010_CNT                                                                                                                               \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("          FROM (                                                                                                                                                         \n");
		sb.append("          SELECT M010_YEAR                                                                                                                                               \n");
		sb.append("               , M010_DATE                                                                                                                                               \n");
		sb.append("               , M010_ACCTYPE                                                                                                                                            \n");
		sb.append("               , M010_ACCCODE                                                                                                                                            \n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , M010_AMT                                                                                                                                                \n");
		sb.append("               , M010_CNT                                                                                                                                                \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("            FROM TMPOCR A                                                                                                                                                \n");
		sb.append("           WHERE EXISTS (                                                                                                                                                \n");
		sb.append("                 SELECT 1                                                                                                                                                \n");
		sb.append("                   FROM M390_USESEMOKCODE_T SUB_T                                                                                                                        \n");
		sb.append("                  WHERE SUB_T.M390_YEAR = A.M010_YEAR                                                                                                                    \n");
		sb.append("                    AND SUB_T.M390_PARTCODE = A.M010_PARTCODE                                                                                                            \n");
		sb.append("                    AND SUB_T.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_ACCCODE  = A.M010_ACCCODE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_WORKGUBUN = '0'                                                                                                                       \n");
		sb.append("                    AND SUB_T.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                          \n");
		sb.append("           )                                                                                                                                                             \n");
		sb.append("           UNION ALL                                                                                                                                                     \n");
		sb.append("           SELECT M010_YEAR                                                                                                                                              \n");
		sb.append("                , M010_DATE                                                                                                                                              \n");
		sb.append("                , M010_ACCTYPE                                                                                                                                           \n");
		sb.append("                , M010_ACCCODE                                                                                                                                           \n");
		sb.append("                , M010_SEMOKCODE                                                                                                                                         \n");
		sb.append("                , '00000' AS M010_PARTCODE                                                                                                                               \n");
		sb.append("                , M010_SUNAPGIGWANCODE                                                                                                                                   \n");
		sb.append("                , M010_INTYPE                                                                                                                                            \n");
		sb.append("                , M010_YEARTYPE                                                                                                                                          \n");
		sb.append("                , M010_AMT                                                                                                                                               \n");
		sb.append("                , M010_CNT                                                                                                                                               \n");
		sb.append("                , M010_LOGNO                                                                                                                                             \n");
		sb.append("                , M010_WORKTYPE                                                                                                                                          \n");
		sb.append("                , M010_TRANSGUBUN                                                                                                                                        \n");
		sb.append("            FROM TMPOCR A                                                                                                                                                \n");
		sb.append("               , M390_USESEMOKCODE_T B                                                                                                                                   \n");
		sb.append("           WHERE B.M390_YEAR = A.M010_YEAR                                                                                                                               \n");
		sb.append("             AND B.M390_PARTCODE = '00000'                                                                                                                               \n");
		sb.append("             AND B.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                                        \n");
		sb.append("             AND B.M390_ACCCODE  = A.M010_ACCCODE                                                                                                                        \n");
		sb.append("             AND B.M390_WORKGUBUN = '0'                                                                                                                                  \n");
		sb.append("             AND B.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                                     \n");
		sb.append("             AND NOT EXISTS (                                                                                                                                            \n");
		sb.append("                 SELECT 1                                                                                                                                                \n");
		sb.append("                   FROM M390_USESEMOKCODE_T SUB_T                                                                                                                        \n");
		sb.append("                  WHERE SUB_T.M390_YEAR = A.M010_YEAR                                                                                                                    \n");
		sb.append("                    AND SUB_T.M390_PARTCODE = A.M010_PARTCODE                                                                                                            \n");
		sb.append("                    AND SUB_T.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_ACCCODE  = A.M010_ACCCODE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_WORKGUBUN = '0'                                                                                                                       \n");
		sb.append("                    AND SUB_T.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                          \n");
		sb.append("           )                                                                                                                                                             \n");
		sb.append("         )                                                                                                                                                               \n");
		sb.append("         GROUP BY M010_YEAR                                                                                                                                              \n");
		sb.append("               , M010_DATE                                                                                                                                               \n");
		sb.append("               , M010_ACCTYPE                                                                                                                                            \n");
		sb.append("               , M010_ACCCODE                                                                                                                                            \n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("		 ) X											                                                                                                                                            \n");											   
        sb.append("         WHERE NOT EXISTS (                                                 \n");
        sb.append("              SELECT 1                                                      \n");
        sb.append("                FROM M010_TAXIN_T TAR                                       \n");
        sb.append("               WHERE TAR.M010_YEAR = X.M010_YEAR                            \n");
        sb.append("                 AND TAR.M010_DATE = X.M010_DATE                            \n");
        sb.append("                 AND TAR.M010_ACCTYPE = X.M010_ACCTYPE                      \n");
        sb.append("                 AND TAR.M010_ACCCODE = X.M010_ACCCODE                      \n");
        sb.append("                 AND TAR.M010_SEMOKCODE = X.M010_SEMOKCODE                  \n");
        sb.append("                 AND TAR.M010_PARTCODE = X.M010_PARTCODE                    \n");
        sb.append("                 AND TAR.M010_SUNAPGIGWANCODE = X.M010_SUNAPGIGWANCODE      \n");
        sb.append("                 AND TAR.M010_INTYPE = X.M010_INTYPE                        \n");
        sb.append("                 AND TAR.M010_AMT = X.M010_AMT                              \n");
        sb.append("                 AND TAR.M010_CNT = X.M010_CNT                              \n");
        sb.append("                 AND TAR.M010_WORKTYPE = X.M010_WORKTYPE                    \n");
        sb.append("                 AND TAR.M010_TRANSGUBUN = X.M010_TRANSGUBUN                \n");
        sb.append("         )                                                                  \n");
        sb.append("         )  X                                                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
		parameters.setString(idx++, "A");			// 회계구분(일반회계)
		parameters.setString(idx++, "110000");		// 수납기관코드
		parameters.setString(idx++, "I1");			// 입력구분(세입)
		parameters.setString(idx++, "Y1");			// 연도구분(현년도)
		parameters.setString(idx++, paramInfo.getString("log_no"));			// 로그번호
		parameters.setString(idx++, paramInfo.getString("work_log"));		// 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));	// 거래구분
		parameters.setString(idx++, paramInfo.getString("input_date"));		// 작업일자
		
		return template.insert(conn, parameters); 
	}

	/* 데이터 여부 확인 */
	public static CommonEntity getOcrCount(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT SUM(A.ETC_CNT) ETC_CNT		\n");
		sb.append("	     ,SUM(A.TAXIN_CNT) TAXIN_CNT	\n");
		sb.append("  FROM (								\n");
		sb.append("SELECT COUNT(1)ETC_CNT				\n");
		sb.append("      ,0 TAXIN_CNT					\n");
		sb.append("  FROM ETC_T							\n");
		sb.append(" WHERE ETC_JOBDATE = ?				\n");	// 회계일자
		sb.append(" UNION								\n");
		sb.append("SELECT 0 ETC_CNT						\n");
		sb.append("      ,COUNT(1)TAXIN_CNT				\n");
		sb.append("  FROM M010_TAXIN_T					\n");
		sb.append(" WHERE M010_DATE = ?					\n");	// 회계일자
		sb.append("   AND M010_WORKTYPE = ?				\n");	// 업무구분(req)
		sb.append("   AND M010_TRANSGUBUN = ?			\n");	// 거래구분(req)
		sb.append("		) A								\n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("input_date"));
		parameters.setString(idx++,  paramInfo.getString("input_date"));
		parameters.setString(idx++,  paramInfo.getString("work_log"));		// 업무구분
		parameters.setString(idx++,  paramInfo.getString("trans_gubun"));	// 거래구분

		return template.getData(conn, parameters);
	}

	/* 작업일자 마감 여부 확인 */
	public static CommonEntity getMagamCount(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) as MAGAM_CNT  		\n");
		sb.append("  FROM M210_WORKEND_T				\n");
		sb.append(" WHERE m210_date = ?			    	\n");	// 작업일자
		sb.append("   AND m210_workendstate = 'Y'    	\n");	// 마감여부

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("input_date"));

		return template.getData(conn, parameters);
	}
	/* 수기분 삭제 */
	public static int ocrDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M010_TAXIN_T     \n");
		sb.append(" WHERE M010_DATE = ?         \n");	// 회계일자
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("input_date"));
		
		return template.delete(conn, parameters);
	}
}