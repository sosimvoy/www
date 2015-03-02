/*****************************************************
* 프로젝트명	    : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	    : IR070310.java
* 프로그램작성자    : (주)미르이즈 
* 프로그램작성일    : 2010-10-01
* 프로그램내용      : 일계/보고서 > 회계연도이월
* 프로그램비고      : 2010-10-12 일계 테이블에서 부서 상관없이 등록 처리
                      2010-10-21 잉여금총액 계산 수정 및 국고제외 (일반,특별,기금 해당)
******************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070310 {

	/* 마감일 조회 */
	public static CommonEntity getCloseDate(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M320_YEAR                     \n");
		sb.append("      ,M320_DATEILBAN                \n");   // 일반회계
		sb.append("      ,M320_DATETEKBEYL              \n");   // 특별회계
		sb.append("      ,M320_DATEGIGEUM               \n");   // 기금회계
		sb.append("      ,M320_DATEHYUNGEUM             \n");   // 세입세출외현금
		sb.append("      ,M320_DATEJEUNGJI              \n");   // 수입증지
		sb.append("      ,M320_DATEJUHAENGSE            \n");   // 주행세
		sb.append("  FROM M320_COLSINGDATECONTROL_T     \n");
		sb.append(" WHERE M320_YEAR = ?                 \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("acc_year"));

		return template.getData(conn, parameters);
	}


    /*  이월대상조회 (회계구분:A,B,E)  - 국세 제외 */
	public static List<CommonEntity> getTransList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_YEAR                  ACCYEAR                              \n");
		sb.append("      ,A.M220_DATE                  ACCDATE                              \n");
		sb.append("      ,A.M220_ACCTYPE               ACCTYPE                              \n");
		sb.append("      ,A.M220_ACCCODE               ACCCODE                              \n");
		sb.append("      ,D.M360_ACCNAME               ACCNAME                              \n");
		sb.append("      ,B.M210_WORKENDSTATE          WORKENDSTATE                         \n");   // 마감여부
		sb.append("      ,NVL(C.M250_ACCTRANSFERYN, 'N') ACCTRANSFERYN                      \n");   // 회계년도이월여부
		
        sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                    \n");
		sb.append("           A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -            \n");
		sb.append("           A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -              \n");
        sb.append("           A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                \n");
        sb.append("           A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) ING_AMT  \n");   // 잉여금총액 2014.01.03 자금배정수령액 추가
		
        sb.append("      ,SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) PRE_SUR_AMT   \n");   // 기이입액
		
        sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                    \n");
		sb.append("           A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -            \n");
		sb.append("           A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -              \n");
        sb.append("           A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                \n");
        sb.append("           A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -        \n");
		sb.append("       SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) TODAY_SUR_AMT \n");   // 당일이입액(잉여금총액-기이입액)
		
        sb.append("FROM   M220_DAY_T A                                                      \n");
		sb.append("      ,M210_WORKEND_T B                                                  \n");
		sb.append("      ,M250_ACCTRANSFER_T C                                              \n");
		sb.append("      ,M360_ACCCODE_T D                                                  \n");
		sb.append("      ,M370_SEMOKCODE_T E                                                \n");

		sb.append("WHERE  A.M220_YEAR          = B.M210_YEAR                                \n");
		sb.append("AND    A.M220_DATE          = B.M210_DATE                                \n");
		sb.append("AND    A.M220_YEAR          = C.M250_YEAR(+)                                \n");
		sb.append("AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                             \n");
		sb.append("AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                             \n");
		sb.append("AND    A.M220_YEAR          = D.M360_YEAR                                \n");
		sb.append("AND    A.M220_ACCTYPE       = D.M360_ACCGUBUN                            \n");
		sb.append("AND    A.M220_ACCCODE       = D.M360_ACCCODE                             \n");
        
        sb.append("AND    A.M220_YEAR           = E.M370_YEAR                               \n");
		sb.append("AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                           \n");
		sb.append("AND    A.M220_ACCCODE        = E.M370_ACCCODE                            \n");
		sb.append("AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                          \n");
        sb.append("AND    E.M370_SEGUMGUBUN <> '2'                                          \n");   //  국세제외
                 sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n"); 
		sb.append("AND    A.M220_YEAR          = ?                                          \n");
		sb.append("AND    A.M220_DATE          = ?                                          \n");   // 마감일
		sb.append("AND    A.M220_ACCTYPE       = ?                                          \n");
		sb.append("GROUP BY A.M220_YEAR                                                     \n");
		sb.append("        ,A.M220_DATE                                                     \n");
		sb.append("        ,A.M220_ACCTYPE                                                  \n");
		sb.append("        ,A.M220_ACCCODE                                                  \n");
		sb.append("        ,B.M210_WORKENDSTATE                                             \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                            \n");
		sb.append("        ,D.M360_ACCNAME                                                  \n");
		sb.append("ORDER BY A.M220_ACCTYPE                                                  \n");
		sb.append("        ,A.M220_ACCCODE                                                  \n");
		sb.append("        ,B.M210_WORKENDSTATE                                             \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                            \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
        
        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_year"));
		parameters.setString(i++, paramInfo.getString("end_date"));
		parameters.setString(i++, paramInfo.getString("acc_type"));

		return template.getList(conn, parameters);
	}

    
    /* 이월대상조회 (회계구분:D 세입세출외현금) */
	public static List<CommonEntity> getTransCashList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M270_YEAR                   ACCYEAR                 \n");
		sb.append("      ,A.M270_DATE                   ACCDATE                 \n");
		sb.append("      ,C.M250_ACCTYPE                ACCTYPE                 \n");
		sb.append("      ,A.M270_ACCCODE                ACCCODE                 \n");
		sb.append("      ,D.M360_ACCNAME                ACCNAME                 \n");
		sb.append("      ,B.M210_WORKENDSTATE           WORKENDSTATE            \n");   // 마감여부
		sb.append("      ,NVL(C.M250_ACCTRANSFERYN, 'N')  ACCTRANSFERYN           \n");   // 회계년도이월여부

		sb.append("      ,(SUM(M270_BOJEUNGJEONGGIIBJEON  +                     \n");
		sb.append("            M270_BOJEUNGJEONGGIIBGEUM  -                     \n");
    sb.append("            M270_BOJEUNGJEONGGIIBJEONG -                     \n");
    sb.append("            M270_BOJEUNGJEONGGIJIJEON  -                     \n");
		sb.append("            M270_BOJEUNGJEONGGIJIGEUB  +                     \n");
    sb.append("            M270_BOJEUNGJEONGGIJIJEONG)                      \n");
    sb.append("       +SUM(M270_BOJEUNGBYULDANIBJEON  +                     \n");
		sb.append("            M270_BOJEUNGBYULDANIBGEUM  -                     \n");
		sb.append("            M270_BOJEUNGBYULDANIBJEONG -                     \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEON  -                     \n");
    sb.append("            M270_BOJEUNGBYULDANJIGEUB  +                     \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEONG)                      \n");
		sb.append("       +SUM(M270_BOJEUNGGONGGEUMIBJEON  +                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBGEUM  -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBJEONG -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEON  -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIGEUB  +                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEONG)                     \n");
    sb.append("       +SUM(M270_BOGWANJEONGGIIBJEON  +                      \n");
    sb.append("            M270_BOGWANJEONGGIIBGEUM  -                      \n");
		sb.append("            M270_BOGWANJEONGGIIBJEONG -                      \n");
		sb.append("            M270_BOGWANJEONGGIJIJEON  -                      \n");
		sb.append("            M270_BOGWANJEONGGIJIGEUB  +                      \n");
    sb.append("            M270_BOGWANJEONGGIJIJEONG)                       \n");
		sb.append("       +SUM(M270_BOGWANBYULDANIBJEON  +                      \n");
		sb.append("            M270_BOGWANBYULDANIBGEUM  -                      \n");
		sb.append("            M270_BOGWANBYULDANIBJEONG -                      \n");
		sb.append("            M270_BOGWANBYULDANJIJEON  -                      \n");
		sb.append("            M270_BOGWANBYULDANJIGEUB  +                      \n");
		sb.append("            M270_BOGWANBYULDANJIJEONG)                       \n");
		sb.append("       +SUM(M270_BOGWANGONGGEUMIBJEON  +                     \n");
    sb.append("            M270_BOGWANGONGGEUMIBGEUM  -                     \n");
    sb.append("            M270_BOGWANGONGGEUMIBJEONG -                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEON  -                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIGEUB  +                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEONG)                      \n");
    sb.append("       +SUM(M270_JABJONGJEONGGIIBJEON  +                     \n");
		sb.append("            M270_JABJONGJEONGGIIBGEUM  -                     \n");
		sb.append("            M270_JABJONGJEONGGIIBJEONG -                     \n");
		sb.append("            M270_JABJONGJEONGGIJIJEON  -                     \n");
		sb.append("            M270_JABJONGJEONGGIJIGEUB  +                     \n");
		sb.append("            M270_JABJONGJEONGGIJIJEONG)                      \n");
		sb.append("       +SUM(M270_JABJONGBYULDANIBJEON  +                     \n");
		sb.append("            M270_JABJONGBYULDANIBGEUM  -                     \n");
    sb.append("            M270_JABJONGBYULDANIBJEONG -                     \n");
    sb.append("            M270_JABJONGBYULDANJIJEON  -                     \n");
		sb.append("            M270_JABJONGBYULDANJIGEUB  +                     \n");
		sb.append("            M270_JABJONGBYULDANJIJEONG)                      \n");
		sb.append("       +SUM(M270_JABJONGGONGGEUMIBJEON  +                    \n");
    sb.append("            M270_JABJONGGONGGEUMIBGEUM  -                    \n");
		sb.append("            M270_JABJONGGONGGEUMIBJEONG -                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEON  -                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIGEUB  +                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEONG)                     \n");
		sb.append("       +SUM(M270_BUGASEJEONGGIIBJEON  +                      \n");
		sb.append("            M270_BUGASEJEONGGIIBGEUM  -                      \n");
		sb.append("            M270_BUGASEJEONGGIIBJEONG -                      \n");
    sb.append("            M270_BUGASEJEONGGIJIJEON  -                      \n");
    sb.append("            M270_BUGASEJEONGGIJIGEUB  +                      \n");
		sb.append("            M270_BUGASEJEONGGIJIJEONG)                       \n");
		sb.append("       +SUM(M270_BUGASEBYULDANIBJEON  +                      \n");
		sb.append("            M270_BUGASEBYULDANIBGEUM  -                      \n");
    sb.append("            M270_BUGASEBYULDANIBJEONG -                      \n");
		sb.append("            M270_BUGASEBYULDANJIJEON  -                      \n");
		sb.append("            M270_BUGASEBYULDANJIGEUB  +                      \n");
		sb.append("            M270_BUGASEBYULDANJIJEONG)                       \n");
		sb.append("       +SUM(M270_BUGASEGONGGEUMIBJEON  +                     \n");
		sb.append("            M270_BUGASEGONGGEUMIBGEUM  -                     \n");
		sb.append("            M270_BUGASEGONGGEUMIBJEONG -                     \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEON  -                     \n");
    sb.append("            M270_BUGASEGONGGEUMJIGEUB  +                     \n");
    sb.append("            M270_BUGASEGONGGEUMJIJEONG)) ING_AMT             \n");

		sb.append("FROM   M270_TAXCASHDAY_T A                                   \n");
		sb.append("      ,M210_WORKEND_T B                                      \n");
    sb.append("      ,M250_ACCTRANSFER_T C                                  \n");
		sb.append("      ,M360_ACCCODE_T D                                      \n");
		sb.append("WHERE  A.M270_YEAR          = B.M210_YEAR                    \n");
		sb.append("AND    A.M270_DATE          = B.M210_DATE                    \n");
		sb.append("AND    A.M270_YEAR          = C.M250_YEAR                    \n");
		sb.append("AND    A.M270_ACCCODE       = C.M250_ACCCODE                 \n");
	//sb.append("--AND    A.M270_PARTCODE      = C.M250_PARTCODE              \n"); //일계 부서 적용X (2010-10-12)   
		sb.append("AND    A.M270_YEAR          = D.M360_YEAR                    \n");
    sb.append("AND    A.M270_ACCCODE       = D.M360_ACCCODE                 \n");
    sb.append("AND    C.M250_ACCTYPE       = D.M360_ACCGUBUN                \n");
		sb.append("AND    A.M270_YEAR          = ?                              \n");
		sb.append("AND    A.M270_DATE          = ?                              \n");    // 마감일
		sb.append("AND    C.M250_ACCTYPE       = ?                              \n");
		sb.append("GROUP BY A.M270_YEAR                                         \n");
		sb.append("        ,A.M270_DATE                                         \n");
		sb.append("        ,C.M250_ACCTYPE                                      \n");
		sb.append("        ,A.M270_ACCCODE                                      \n");
		sb.append("        ,B.M210_WORKENDSTATE                                 \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                \n");
		sb.append("        ,D.M360_ACCNAME                                      \n");
		sb.append("ORDER BY C.M250_ACCTYPE                                      \n");
		sb.append("        ,A.M270_ACCCODE                                      \n");
		sb.append("        ,B.M210_WORKENDSTATE                                 \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
        
        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_year"));
		parameters.setString(i++, paramInfo.getString("end_date"));
		parameters.setString(i++, paramInfo.getString("acc_type"));

		return template.getList(conn, parameters);
	}


    /* 세입수기분 테이블 일련번호 조회 (사용안함) */
	public static CommonEntity getTaxInSeq(Connection conn)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M010_SEQ.NEXTVAL AS MAX_SEQ   \n");
		sb.append("  FROM DUAL                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		return template.getData(conn);
	}


    /* 세입세출외현금수기분 테이블 일련번호 조회 (사용안함) */
	public static CommonEntity getTaxCashSeq(Connection conn)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M040_SEQ.NEXTVAL AS MAX_SEQ   \n");
		sb.append("  FROM DUAL                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		return template.getData(conn);
	}


    /* 세입수기분 (회계구분:A,B,E) 년도이월 INSERT  - 국세 제외 */
	public static int setTaxInInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M010_TAXIN_T                                                         \n"); 
		sb.append("(      M010_SEQ                                                                  \n");
		sb.append("      ,M010_YEAR                                                                 \n");
		sb.append("      ,M010_DATE                                                                 \n");
		sb.append("      ,M010_ACCTYPE                                                              \n");
		sb.append("      ,M010_ACCCODE                                                              \n");
		sb.append("      ,M010_SEMOKCODE                                                            \n");
		sb.append("      ,M010_PARTCODE                                                             \n");
		sb.append("      ,M010_SUNAPGIGWANCODE                                                      \n");
		sb.append("      ,M010_INTYPE                                                               \n");
		sb.append("      ,M010_YEARTYPE                                                             \n");
		sb.append("      ,M010_AMT                                                                  \n");
		sb.append("      ,M010_CNT                                                                  \n");
		sb.append("      ,M010_LOGNO                                                                \n");
		sb.append("      ,M010_WORKTYPE                                                             \n");
		sb.append("      ,M010_TRANSGUBUN                                                           \n");
		sb.append(")                                                                                \n");
		sb.append("SELECT M010_SEQ.NEXTVAL, X.*                                                     \n");
		sb.append("FROM  (                                                                          \n");
		sb.append("       SELECT ? YEAR                                                             \n");
		sb.append("             ,? M220_DATE                                                        \n");
		sb.append("             ,A.M220_ACCTYPE                                                     \n");
		sb.append("             ,A.M220_ACCCODE                                                     \n");
		sb.append("             ,CASE WHEN C.M250_SEMOKCODE IS NULL THEN MAX(A.M220_SEMOKCODE) ELSE C.M250_SEMOKCODE END SEMOK_CODE \n");
		//sb.append("             ,A.M220_PARTCODE                                                    \n"); //일계 부서 적용X (2010-10-12)   
		sb.append("             ,CASE WHEN C.M250_PARTCODE IS NULL THEN MAX(A.M220_PARTCODE) ELSE C.M250_PARTCODE END PART_CODE  \n");
		sb.append("             ,? SUNAPGIGWANCODE                                                  \n");
		sb.append("             ,? INTYPE                                                           \n");
		sb.append("             ,? YEARTYPE                                                         \n");

        sb.append("             ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                     \n");
		sb.append("                  A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -             \n");
		sb.append("                  A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -               \n");
        sb.append("                  A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                 \n");
        sb.append("                  A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -         \n");
		sb.append("              SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) TODAY_SUR_AMT  \n");   // 당일이입액(잉여금총액-기이입액)

		sb.append("             ,? CNT                                                              \n");
		sb.append("             ,? LOGNO                                                            \n");
		sb.append("             ,? WORKTYPE                                                         \n");
		sb.append("             ,? TRANSGUBUN                                                       \n");
		sb.append("                                                                                 \n");
		sb.append("       FROM   M220_DAY_T A                                                       \n");
		sb.append("             ,M210_WORKEND_T B                                                   \n");
		sb.append("             ,M250_ACCTRANSFER_T C                                               \n");
		sb.append("             ,M370_SEMOKCODE_T E                                                 \n");
    
		sb.append("       WHERE  A.M220_YEAR          = B.M210_YEAR                                 \n");
		sb.append("       AND    A.M220_DATE          = B.M210_DATE                                 \n");
		sb.append("       AND    A.M220_YEAR          = C.M250_YEAR(+)                                 \n");
		sb.append("       AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                              \n");
		sb.append("       AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                              \n");
		//sb.append("       AND    A.M220_PARTCODE      = C.M250_PARTCODE                             \n");  //일계 부서 적용X (2010-10-12)   
        
        sb.append("       AND    A.M220_YEAR           = E.M370_YEAR                               \n");
		sb.append("       AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                           \n");
		sb.append("       AND    A.M220_ACCCODE        = E.M370_ACCCODE                            \n");
		sb.append("       AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                          \n");
        sb.append("       AND    E.M370_SEGUMGUBUN <> '2'                                          \n");   //  국세제외
                sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n");
		sb.append("       AND    A.M220_YEAR          = ?                                           \n");
		sb.append("       AND    A.M220_DATE          = ?                                           \n");
		sb.append("       AND    A.M220_ACCTYPE       = ?                                           \n");
		sb.append("       AND    A.M220_ACCCODE       = ?                                           \n");
		sb.append("       AND    B.M210_WORKENDSTATE  = ?                                           \n");
		sb.append("       AND    NVL(C.M250_ACCTRANSFERYN, 'N') = ?                                           \n");
		sb.append("       GROUP BY A.M220_YEAR                                                      \n");
		sb.append("               ,A.M220_DATE                                                      \n");
		sb.append("               ,A.M220_ACCTYPE                                                   \n");
		sb.append("               ,A.M220_ACCCODE                                                   \n");
		sb.append("               ,C.M250_SEMOKCODE                                                 \n");
		//sb.append("               ,A.M220_PARTCODE                                                  \n");  //일계 부서 적용X (2010-10-12)  
		sb.append("               ,C.M250_PARTCODE                                                  \n"); 
		sb.append("       ) X                                                                       \n");
        sb.append("WHERE  TODAY_SUR_AMT >= 0                                                         \n");  // 금액이 0 이상인것만 등록가능 


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 기준일의 익영업일 (등록시)
		parameters.setString(idx++, "110000");                              // 수납기관
		parameters.setString(idx++, "I1");                                  // 입력구분
		parameters.setString(idx++, "Y1");                                  // 년도구분
		parameters.setString(idx++, "1");                                   // 건수
		parameters.setString(idx++, paramInfo.getString("log_no"));         // 로그번호
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // 회계년도
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // 마감일자(기준일자)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // 회계구분
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "Y");                                   // 업무마감여부
		parameters.setString(idx++, "N");                                   // 이월여부

		return template.insert(conn, parameters);
	}


    /* 회계년도이월 (회계구분:A,B,E) Table Update 데이터 생성  - 국세 제외 */
	public static List<CommonEntity> getAccTransList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT   D.M010_YEAR         M010_YEAR                                                       \n");   // 이월회계년도
		sb.append("        ,C.M250_YEAR         M250_YEAR                                                       \n");   // 현재회계년도(추가 2010-10-21)
		sb.append("        ,A.M220_ACCTYPE      M250_ACCTYPE                                                    \n");
		sb.append("        ,A.M220_ACCCODE      M250_ACCCODE                                                    \n"); 
		sb.append("        ,C.M250_PARTCODE     M250_PARTCODE                                                   \n");
		sb.append("        ,C.M250_SEMOKCODE    M250_SEMOKCODE                                                  \n");
		sb.append("        ,'Y'                 M250_ACCTRANSFERYN                                              \n");
        sb.append("        ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                                  \n");
		sb.append("                 A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -                          \n");
		sb.append("                 A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -                            \n");
        sb.append("                 A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                              \n");
        sb.append("                 A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT),0) M250_AMTSURPLUSTOT  \n");   // 잉여금총액
		sb.append("        ,NVL(SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS),0) M250_AMTSURPLUSBEFORE    \n");    //기이입액
		sb.append("       ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                                   \n");
		sb.append("                A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -                           \n");
		sb.append("                A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -                             \n");
        sb.append("                A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                               \n");
        sb.append("                A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -                       \n");
		sb.append("            SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS),0)  M250_AMTSURPLUSTODAY     \n");   // 당일이입액(잉여금총액-기이입액)
		sb.append("        ,D.M010_SEQ          M250_M010SEQ                                                    \n");
		sb.append("  FROM   M220_DAY_T A                                                                        \n");
		sb.append("        ,M210_WORKEND_T B                                                                    \n");
		sb.append("        ,M250_ACCTRANSFER_T C                                                                \n");
		sb.append("        ,M010_TAXIN_T D                                                                      \n");
		sb.append("        ,M370_SEMOKCODE_T E                                                                  \n");
		sb.append("  WHERE  A.M220_YEAR          = B.M210_YEAR                                                  \n");
		sb.append("  AND    A.M220_DATE          = B.M210_DATE                                                  \n");
		sb.append("  AND    A.M220_YEAR          = C.M250_YEAR(+)                                                  \n");
		sb.append("  AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                                               \n");
		sb.append("  AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                                               \n");
		sb.append("  AND    A.M220_ACCTYPE       = D.M010_ACCTYPE                                               \n");
		sb.append("  AND    A.M220_ACCCODE       = D.M010_ACCCODE                                               \n");
		sb.append("  AND    C.M250_SEMOKCODE     = D.M010_SEMOKCODE                                             \n");
		sb.append("  AND    C.M250_PARTCODE      = D.M010_PARTCODE                                              \n");
        sb.append("  AND    A.M220_YEAR           = E.M370_YEAR                                                 \n");
		sb.append("  AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                                             \n");
		sb.append("  AND    A.M220_ACCCODE        = E.M370_ACCCODE                                              \n");
		sb.append("  AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                                            \n");
        sb.append("  AND    E.M370_SEGUMGUBUN <> '2'                                                            \n");   //  국세제외
        sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n");
		sb.append("  AND    A.M220_YEAR          = ?                                                            \n");   // 회계년도
		sb.append("  AND    A.M220_DATE          = ?                                                            \n");   // 마감일자
		sb.append("  AND    A.M220_ACCTYPE       = ?                                                            \n");
		sb.append("  AND    A.M220_ACCCODE       = ?                                                            \n");
		sb.append("  AND    B.M210_WORKENDSTATE  = ?                                                            \n");
		sb.append("  AND    C.M250_ACCTRANSFERYN = ?                                                            \n");
		sb.append("  AND    D.M010_YEAR          = ?                                                            \n");   // 이월년도
		sb.append("  AND    D.M010_DATE          = ?                                                            \n");   
		sb.append("  AND    D.M010_SUNAPGIGWANCODE = ?                                                          \n");
		sb.append("  AND    D.M010_INTYPE       = ?                                                             \n");
		sb.append("  AND    D.M010_YEARTYPE     = ?                                                             \n");
		sb.append("  AND    D.M010_WORKTYPE     = ?                                                             \n");
		sb.append("  AND    D.M010_TRANSGUBUN   = ?                                                             \n");
		sb.append("  GROUP BY D.M010_YEAR                                                                       \n");
		sb.append("          ,C.M250_YEAR                                                                       \n");
		sb.append("          ,A.M220_ACCTYPE                                                                    \n");
		sb.append("          ,A.M220_ACCCODE                                                                    \n");
		sb.append("          ,C.M250_SEMOKCODE                                                                  \n");
		sb.append("          ,C.M250_PARTCODE                                                                   \n");
		sb.append("          ,D.M010_SEQ                                                                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // 회계년도
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // 마감일자(기준일자)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // 회계구분
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "Y");                                   // 업무마감여부
		parameters.setString(idx++, "N");                                   // 이월여부
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 이월일자 (기준일의 익영업일 (수기))
		parameters.setString(idx++, "110000");                              // 수납기관
		parameters.setString(idx++, "I1");                                  // 입력구분
		parameters.setString(idx++, "Y1");                                  // 년도구분
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분

		return template.getList(conn, parameters);
	}


    /* 회계년도이월 (회계구분:A,B,D,E) 공통 UPDATE */
	public static int setAccTransUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M250_ACCTRANSFER_T               \n");
		sb.append("SET    M250_SEMOKCODE           = ?     \n");
		sb.append("      ,M250_ACCTRANSFERYN       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTOT       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSBEFORE    = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTODAY     = ?     \n");
		sb.append("      ,M250_M010SEQ             = ?     \n");
		sb.append("WHERE  M250_YEAR                = ?     \n");
		sb.append("AND    M250_ACCTYPE             = ?     \n");
		sb.append("AND    M250_ACCCODE             = ?     \n");
		sb.append("AND    M250_PARTCODE            = ?     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("M250_SEMOKCODE"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCTRANSFERYN"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSTOT"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSBEFORE"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSTODAY"));
		parameters.setString(idx++, paramInfo.getString("M250_M010SEQ"));
		parameters.setString(idx++, paramInfo.getString("M250_YEAR"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCTYPE"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("M250_PARTCODE"));

		return template.update(conn, parameters);
	}


    /* 세입세출외현금수기분(회계구분:D) 년도이월 INSERT */
	public static int setTaxInCashInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO M040_TAXCASH_T                                           \n");                               
        sb.append("(           M040_SEQ                                                 \n");   
        sb.append("           ,M040_YEAR                                                \n");   
        sb.append("           ,M040_DATE                                                \n");   
        sb.append("           ,M040_ACCCODE                                             \n");   
        sb.append("           ,M040_PARTCODE                                            \n");   
        sb.append("           ,M040_DWTYPE                                              \n");   
        sb.append("           ,M040_INTYPE                                              \n");   
        sb.append("           ,M040_CASHTYPE                                            \n");   
        sb.append("           ,M040_DEPOSITTYPE                                         \n");   
        sb.append("           ,M040_CNT                                                 \n");   
        sb.append("           ,M040_AMT                                                 \n");   
        sb.append("           ,M040_LOGNO                                               \n");   
        sb.append("           ,M040_WORKTYPE                                            \n");   
        sb.append("           ,M040_TRANSGUBUN                                          \n");   
        sb.append(")                                                                    \n");   
        sb.append("SELECT M040_SEQ.NEXTVAL, X.*                                         \n");   
        sb.append("FROM  (                                                              \n");   
        sb.append("        SELECT ?                     M040_YEAR                       \n");   
        sb.append("              ,?                     M040_DATE                       \n");   
        sb.append("              ,A.M270_ACCCODE        M040_ACCCODE                    \n");   
        //sb.append("              ,A.M270_PARTCODE       M040_PARTCODE                   \n");        //일계 부서 적용X (2010-10-12)     
        sb.append("              ,C.M250_PARTCODE       M040_PARTCODE                   \n");   
        sb.append("              ,?                     M040_DWTYPE                     \n");   
        sb.append("              ,?                     M040_INTYPE                     \n");   
        sb.append("              ,E.CASHTYPE            M040_CASHTYPE                   \n");   
        sb.append("              ,F.DEPOSITTYPE         M040_DEPOSITTYPE                \n");   
        sb.append("              ,?                     M040_CNT                        \n");   
        sb.append("              ,SUM(CASE WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BOJEUNGJEONGGIIBJEON  +           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIIBGEUM  -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIIBJEONG -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIJEON  -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIGEUB  +           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BOJEUNGBYULDANIBJEON  +           \n");   
        sb.append("                              M270_BOJEUNGBYULDANIBGEUM  -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANIBJEONG -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIJEON  -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIGEUB  +           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BOJEUNGGONGGEUMIBJEON  +          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMIBGEUM  -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMIBJEONG -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIJEON  -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIGEUB  +          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIJEONG)           \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BOGWANJEONGGIIBJEON  +            \n");   
        sb.append("                              M270_BOGWANJEONGGIIBGEUM  -            \n");   
        sb.append("                              M270_BOGWANJEONGGIIBJEONG -            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIJEON  -            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIGEUB  +            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BOGWANBYULDANIBJEON  +            \n");   
        sb.append("                              M270_BOGWANBYULDANIBGEUM  -            \n");   
        sb.append("                              M270_BOGWANBYULDANIBJEONG -            \n");   
        sb.append("                              M270_BOGWANBYULDANJIJEON  -            \n");   
        sb.append("                              M270_BOGWANBYULDANJIGEUB  +            \n");   
        sb.append("                              M270_BOGWANBYULDANJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BOGWANGONGGEUMIBJEON  +           \n");   
        sb.append("                              M270_BOGWANGONGGEUMIBGEUM  -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMIBJEONG -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIJEON  -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIGEUB  +           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_JABJONGJEONGGIIBJEON  +           \n");   
        sb.append("                              M270_JABJONGJEONGGIIBGEUM  -           \n");   
        sb.append("                              M270_JABJONGJEONGGIIBJEONG -           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIJEON  -           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIGEUB  +           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_JABJONGBYULDANIBJEON  +           \n");   
        sb.append("                              M270_JABJONGBYULDANIBGEUM  -           \n");   
        sb.append("                              M270_JABJONGBYULDANIBJEONG -           \n");   
        sb.append("                              M270_JABJONGBYULDANJIJEON  -           \n");   
        sb.append("                              M270_JABJONGBYULDANJIGEUB  +           \n");   
        sb.append("                              M270_JABJONGBYULDANJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_JABJONGGONGGEUMIBJEON  +          \n");   
        sb.append("                              M270_JABJONGGONGGEUMIBGEUM  -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMIBJEONG -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIJEON  -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIGEUB  +          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIJEONG)           \n");   
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BUGASEJEONGGIIBJEON  +            \n");   
        sb.append("                              M270_BUGASEJEONGGIIBGEUM  -            \n");   
        sb.append("                              M270_BUGASEJEONGGIIBJEONG -            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIJEON  -            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIGEUB  +            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BUGASEBYULDANIBJEON  +            \n");   
        sb.append("                              M270_BUGASEBYULDANIBGEUM  -            \n");   
        sb.append("                              M270_BUGASEBYULDANIBJEONG -            \n");   
        sb.append("                              M270_BUGASEBYULDANJIJEON  -            \n");   
        sb.append("                              M270_BUGASEBYULDANJIGEUB  +            \n");   
        sb.append("                              M270_BUGASEBYULDANJIJEONG)             \n");    
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BUGASEGONGGEUMIBJEON  +           \n");   
        sb.append("                              M270_BUGASEGONGGEUMIBGEUM  -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMIBJEONG -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIJEON  -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIGEUB  +           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIJEONG)            \n");   
        sb.append("                   END) M040_AMT                                     \n");   
        sb.append("              ,?   M040_LOGNO                                        \n");   
        sb.append("              ,? M040_WORKTYPE                                       \n");   
        sb.append("              ,? M040_TRANSGUBUN                                     \n");   
        sb.append("        FROM   M270_TAXCASHDAY_T A                                   \n");   
        sb.append("              ,M210_WORKEND_T B                                      \n");   
        sb.append("              ,M250_ACCTRANSFER_T C                                  \n");   
        sb.append("              ,M360_ACCCODE_T D                                      \n");   
        sb.append("              ,(SELECT 'C'||LEVEL CASHTYPE                           \n");   
        sb.append("                  FROM DUAL                                          \n");   
        sb.append("                 CONNECT BY LEVEL <= 4) E                            \n");   
        sb.append("              ,(SELECT 'D'||LEVEL DEPOSITTYPE                        \n");   
        sb.append("                  FROM DUAL                                          \n");   
        sb.append("                 CONNECT BY LEVEL <= 3) F                            \n");   
        sb.append("        WHERE  A.M270_YEAR          = B.M210_YEAR                    \n");   
        sb.append("        AND    A.M270_DATE          = B.M210_DATE                    \n");   
        sb.append("        AND    A.M270_YEAR          = C.M250_YEAR                    \n");   
        sb.append("        AND    A.M270_ACCCODE       = C.M250_ACCCODE                 \n");   
        //sb.append("        AND    A.M270_PARTCODE      = C.M250_PARTCODE                \n");         //일계 부서 적용X (2010-10-12)      
        sb.append("        AND    A.M270_YEAR          = D.M360_YEAR                    \n");   
        sb.append("        AND    A.M270_ACCCODE       = D.M360_ACCCODE                 \n");   
        sb.append("        AND    C.M250_ACCTYPE       = D.M360_ACCGUBUN                \n");   
        sb.append("        AND    A.M270_YEAR          = ?                              \n");   
        sb.append("        AND    A.M270_DATE          = ?                              \n");   
        sb.append("        AND    C.M250_ACCTYPE       = ?                              \n");   
        sb.append("        AND    A.M270_ACCCODE       = ?                              \n");   
		sb.append("        AND    B.M210_WORKENDSTATE  = ?                              \n");
		sb.append("        AND    C.M250_ACCTRANSFERYN = ?                              \n");

        sb.append("        GROUP BY A.M270_YEAR                                         \n");   
        sb.append("                ,A.M270_DATE                                         \n");   
        sb.append("                ,A.M270_ACCCODE                                      \n");   
        //sb.append("                ,A.M270_PARTCODE                                     \n");     //일계 부서 적용X (2010-10-12)      
        sb.append("                ,C.M250_PARTCODE                                     \n");   
        sb.append("                ,E.CASHTYPE                                          \n");   
        sb.append("                ,F.DEPOSITTYPE                                       \n");   
        sb.append("     ) X                                                             \n");   
        sb.append("WHERE  M040_AMT >= 0                                                 \n");  // 금액이 0 이상인것만 등록가능 


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 기준일의 익영업일 (등록시)
		parameters.setString(idx++, "G1");                                  // 입력지급구분
		parameters.setString(idx++, "I1");                                  // 입력구분
		parameters.setString(idx++, "1");                                   // 건수
		parameters.setString(idx++, paramInfo.getString("log_no"));         // 로그번호
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분

		parameters.setString(idx++, paramInfo.getString("acc_year"));       // 회계년도
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // 마감일자(기준일자)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // 회계구분
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "Y");                                   // 업무마감여부
		parameters.setString(idx++, "N");                                   // 이월여부

		return template.insert(conn, parameters);
	}


    /* 세입세출외현금수기분(회계구분:D) 회계년도이월 Table Update 데이터 생성 */
	public static List<CommonEntity> getAccTransCashList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT D.M040_YEAR          M040_YEAR                           \n");    // 이월회계년도 
		sb.append("      ,C.M250_YEAR          M250_YEAR                           \n");    // 현재회계년도(추가 2010-10-21)
		sb.append("      ,C.M250_ACCTYPE       M250_ACCTYPE                        \n");
		sb.append("      ,A.M270_ACCCODE       M250_ACCCODE                        \n");
		//sb.append("      ,A.M270_PARTCODE      M250_PARTCODE                       \n");      //일계 부서 적용X (2010-10-12)      
		sb.append("      ,C.M250_PARTCODE      M250_PARTCODE                       \n");
		sb.append("      ,C.M250_SEMOKCODE     M250_SEMOKCODE                      \n");
		sb.append("      ,'Y'                  M250_ACCTRANSFERYN                  \n");
		sb.append("      ,(SUM(M270_BOJEUNGJEONGGIIBJEON  +                        \n");
		sb.append("            M270_BOJEUNGJEONGGIIBGEUM  -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIIBJEONG -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIJEON  -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIGEUB  +                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIJEONG)                         \n");
		sb.append("       +SUM(M270_BOJEUNGBYULDANIBJEON  +                        \n");
		sb.append("            M270_BOJEUNGBYULDANIBGEUM  -                        \n");
		sb.append("            M270_BOJEUNGBYULDANIBJEONG -                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEON  -                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIGEUB  +                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEONG)                         \n");
		sb.append("       +SUM(M270_BOJEUNGGONGGEUMIBJEON  +                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBGEUM  -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBJEONG -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEON  -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIGEUB  +                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEONG)                        \n");
		sb.append("       +SUM(M270_BOGWANJEONGGIIBJEON  +                         \n");
		sb.append("            M270_BOGWANJEONGGIIBGEUM  -                         \n");
		sb.append("            M270_BOGWANJEONGGIIBJEONG -                         \n");
		sb.append("            M270_BOGWANJEONGGIJIJEON  -                         \n");
		sb.append("            M270_BOGWANJEONGGIJIGEUB  +                         \n");
		sb.append("            M270_BOGWANJEONGGIJIJEONG)                          \n");
		sb.append("       +SUM(M270_BOGWANBYULDANIBJEON  +                         \n");
		sb.append("            M270_BOGWANBYULDANIBGEUM  -                         \n");
		sb.append("            M270_BOGWANBYULDANIBJEONG -                         \n");
		sb.append("            M270_BOGWANBYULDANJIJEON  -                         \n");
		sb.append("            M270_BOGWANBYULDANJIGEUB  +                         \n");
		sb.append("            M270_BOGWANBYULDANJIJEONG)                          \n");
		sb.append("       +SUM(M270_BOGWANGONGGEUMIBJEON  +                        \n");
		sb.append("            M270_BOGWANGONGGEUMIBGEUM  -                        \n");
		sb.append("            M270_BOGWANGONGGEUMIBJEONG -                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEON  -                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIGEUB  +                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGJEONGGIIBJEON  +                        \n");
		sb.append("            M270_JABJONGJEONGGIIBGEUM  -                        \n");
		sb.append("            M270_JABJONGJEONGGIIBJEONG -                        \n");
		sb.append("            M270_JABJONGJEONGGIJIJEON  -                        \n");
		sb.append("            M270_JABJONGJEONGGIJIGEUB  +                        \n");
		sb.append("            M270_JABJONGJEONGGIJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGBYULDANIBJEON  +                        \n");
		sb.append("            M270_JABJONGBYULDANIBGEUM  -                        \n");
		sb.append("            M270_JABJONGBYULDANIBJEONG -                        \n");
		sb.append("            M270_JABJONGBYULDANJIJEON  -                        \n");
		sb.append("            M270_JABJONGBYULDANJIGEUB  +                        \n");
		sb.append("            M270_JABJONGBYULDANJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGGONGGEUMIBJEON  +                       \n");
		sb.append("            M270_JABJONGGONGGEUMIBGEUM  -                       \n");
		sb.append("            M270_JABJONGGONGGEUMIBJEONG -                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEON  -                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIGEUB  +                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEONG)                        \n");
		sb.append("       +SUM(M270_BUGASEJEONGGIIBJEON  +                         \n");
		sb.append("            M270_BUGASEJEONGGIIBGEUM  -                         \n");
		sb.append("            M270_BUGASEJEONGGIIBJEONG -                         \n");
		sb.append("            M270_BUGASEJEONGGIJIJEON  -                         \n");
		sb.append("            M270_BUGASEJEONGGIJIGEUB  +                         \n");
		sb.append("            M270_BUGASEJEONGGIJIJEONG)                          \n");
		sb.append("       +SUM(M270_BUGASEBYULDANIBJEON  +                         \n");
		sb.append("            M270_BUGASEBYULDANIBGEUM  -                         \n");
		sb.append("            M270_BUGASEBYULDANIBJEONG -                         \n");
		sb.append("            M270_BUGASEBYULDANJIJEON  -                         \n");
		sb.append("            M270_BUGASEBYULDANJIGEUB  +                         \n");
		sb.append("            M270_BUGASEBYULDANJIJEONG)                          \n");
		sb.append("       +SUM(M270_BUGASEGONGGEUMIBJEON  +                        \n");
		sb.append("            M270_BUGASEGONGGEUMIBGEUM  -                        \n");
		sb.append("            M270_BUGASEGONGGEUMIBJEONG -                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEON  -                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIGEUB  +                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEONG)) M250_AMTSURPLUSTOT     \n");
		sb.append("      ,0 M250_AMTSURPLUSBEFORE                                  \n");
		sb.append("      ,0 M250_AMTSURPLUSTODAY                                   \n");
		sb.append("      ,MAX(D.M040_SEQ)   M250_M010SEQ                           \n");
		sb.append("FROM   M270_TAXCASHDAY_T A                                      \n");
		sb.append("      ,M210_WORKEND_T B                                         \n");
		sb.append("      ,M250_ACCTRANSFER_T C                                     \n");
		sb.append("      ,M040_TAXCASH_T D                                         \n");
		sb.append("WHERE  A.M270_YEAR           = B.M210_YEAR                      \n");
		sb.append("AND    A.M270_DATE           = B.M210_DATE                      \n");
		sb.append("AND    A.M270_YEAR           = C.M250_YEAR                      \n");
		sb.append("AND    A.M270_ACCCODE        = C.M250_ACCCODE                   \n");
		//sb.append("AND    A.M270_PARTCODE       = C.M250_PARTCODE                  \n");      //일계 부서 적용X (2010-10-12)      
		//sb.append("AND    A.M270_PARTCODE       = D.M040_PARTCODE                  \n");      //일계 부서 적용X (2010-10-12)
		sb.append("AND    C.M250_PARTCODE       = D.M040_PARTCODE                  \n");      
		sb.append("AND    C.M250_ACCCODE        = D.M040_ACCCODE                    \n");
		sb.append("AND    A.M270_YEAR           = ?                                \n");
		sb.append("AND    A.M270_DATE           = ?                                \n");
		sb.append("AND    C.M250_ACCTYPE        = ?                                \n");
		sb.append("AND    A.M270_ACCCODE        = ?                                \n");
		sb.append("AND    B.M210_WORKENDSTATE   = ?                                \n");
		sb.append("AND    C.M250_ACCTRANSFERYN  = ?                                \n");
		sb.append("AND    D.M040_YEAR           = ?                                \n");
		sb.append("AND    D.M040_DATE           = ?                                \n");
		sb.append("AND    D.M040_DWTYPE         = ?                                \n");
		sb.append("AND    D.M040_INTYPE         = ?                                \n");
		sb.append("AND    D.M040_WORKTYPE       = ?                                \n");
		sb.append("AND    D.M040_TRANSGUBUN     = ?                                \n");
		sb.append("GROUP BY D.M040_YEAR                                            \n");
		sb.append("        ,C.M250_YEAR                                            \n");
		sb.append("        ,C.M250_ACCTYPE                                         \n");
		sb.append("        ,A.M270_ACCCODE                                         \n");
		//sb.append("        ,A.M270_PARTCODE                                        \n");      //일계 부서 적용X (2010-10-12)
		sb.append("        ,C.M250_PARTCODE                                        \n");
		sb.append("        ,C.M250_SEMOKCODE                                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // 회계년도
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // 마감일자(기준일자)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // 회계구분
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "Y");                                   // 업무마감여부
		parameters.setString(idx++, "N");                                   // 이월여부
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 이월일자(기준일의 익영업일 (수기))
		parameters.setString(idx++, "G1");                                  // 입력구분
		parameters.setString(idx++, "I1");                                  // 년도구분
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분

		return template.getList(conn, parameters);
	}


    /* 세입수기분 (회계구분:A,B,E)  Delete */
	public static int setTaxInDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE                               \n");
		sb.append("FROM   M010_TAXIN_T                  \n");
		sb.append("WHERE  M010_YEAR             = ?     \n");
		sb.append("AND    M010_DATE             = ?     \n");
		sb.append("AND    M010_ACCTYPE          = ?     \n");
		sb.append("AND    M010_ACCCODE          = ?     \n");
		sb.append("AND    M010_SUNAPGIGWANCODE  = ?     \n");
		sb.append("AND    M010_INTYPE           = ?     \n");
		sb.append("AND    M010_YEARTYPE         = ?     \n");
		sb.append("AND    M010_WORKTYPE         = ?     \n");
		sb.append("AND    M010_TRANSGUBUN       = ?     \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 기준일의 익영업일 (등록시)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // 회계구분
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "110000");                              // 수납기관
		parameters.setString(idx++, "I1");                                  // 입력구분
		parameters.setString(idx++, "Y1");                                  // 년도구분
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분

		return template.delete(conn, parameters);
	}

    
    /* 세입세출외현금수기분(회계구분:D)  Delete */
	public static int setTaxInCashDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE                           \n");
		sb.append("FROM   M040_TAXCASH_T            \n");
		sb.append("WHERE  M040_YEAR         = ?     \n");
		sb.append("AND    M040_DATE         = ?     \n");
		sb.append("AND    M040_ACCCODE      = ?     \n");
		sb.append("AND    M040_DWTYPE       = ?     \n");
		sb.append("AND    M040_INTYPE       = ?     \n");
		sb.append("AND    M040_WORKTYPE     = ?     \n");
		sb.append("AND    M040_TRANSGUBUN   = ?     \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // 이월년도
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // 기준일의 익영업일 (등록시)
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // 회계코드
		parameters.setString(idx++, "G1");                                  // 입금지급구분
		parameters.setString(idx++, "I1");                                  // 입력구분
		parameters.setString(idx++, paramInfo.getString("work_log"));       // 업무구분
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // 거래구분

		return template.delete(conn, parameters);
	}

    /* 회계년도이월 (회계구분:A,B,D,E) 공통 이월 취소시 RESET UPDATE */
	public static int setAccTransUpdateReset(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M250_ACCTRANSFER_T               \n"); 
		sb.append("SET    M250_ACCTRANSFERYN       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTOT       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSBEFORE    = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTODAY     = ?     \n");
		sb.append("      ,M250_M010SEQ             = ?     \n");
		sb.append("WHERE  M250_YEAR                = ?     \n");
		sb.append("AND    M250_ACCTYPE             = ?     \n");
		sb.append("AND    M250_ACCCODE             = ?     \n");
		//sb.append("AND    M250_PARTCODE            = ?     \n");  (기준: 회계년도, 회계구분, 회계코드)

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, "N");        
		parameters.setInt(idx++, 0);      
		parameters.setInt(idx++, 0);   
		parameters.setInt(idx++, 0);      
		parameters.setString(idx++, "");      
		parameters.setString(idx++, paramInfo.getString("acc_year"));      
		parameters.setString(idx++, paramInfo.getString("acc_type"));      
		parameters.setString(idx++, paramInfo.getString("acc_code"));       

		return template.update(conn, parameters);
	}
}


