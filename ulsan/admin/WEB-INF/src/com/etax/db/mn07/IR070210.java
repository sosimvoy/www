/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR070210.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 일계/보고서 > 일일업무마감
******************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070210 {

	/* 일일업무마감 */
	public static List<CommonEntity> getMagamList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();
                
        sb.append(" SELECT *                                                                   \n");
        sb.append("   FROM M210_WORKEND_T                                                      \n");
        sb.append("  WHERE M210_YEAR = ?                                                       \n");
        sb.append("    AND M210_DATE IN (                                                      \n");
        sb.append("     SELECT M210_DATE                                                       \n");
        sb.append("       FROM (                                                               \n");
        sb.append("         SELECT M210_WORKENDSTATE                                           \n");
        sb.append("               , M210_DATE                                                  \n");
        sb.append("           FROM (                                                           \n");
        sb.append("           SELECT   M210_WORKENDSTATE                                       \n");
        sb.append("                 ,  M210_DATE AS M210_DATE                                  \n");
        sb.append("                 ,  ROW_NUMBER() OVER (ORDER BY M210_DATE DESC) AS WPOSI    \n");
        sb.append("            FROM    M210_WORKEND_T                                          \n");
        sb.append("            WHERE M210_WORKENDSTATE = 'Y'                                   \n");
        sb.append("             AND M210_YEAR = ?                                              \n");
        sb.append("          )                                                                 \n");
        sb.append("          WHERE WPOSI <= 2                                                  \n");
        sb.append("          UNION ALL                                                         \n");
        sb.append("          SELECT M210_WORKENDSTATE                                          \n");
        sb.append("               , M210_DATE                                                  \n");
        sb.append("         FROM    M210_WORKEND_T                                             \n");
        sb.append("          WHERE M210_WORKENDSTATE = 'N'                                     \n");
        sb.append("             AND M210_YEAR = ?                                              \n");
        sb.append("    )                                                                       \n");
        sb.append(" )                                                                          \n");
        sb.append(" ORDER BY  M210_DATE DESC, M210_YEAR                                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

                int idx = 1;
                parameters.setString(idx++, paramInfo.getString("acc_year"));
                parameters.setString(idx++, paramInfo.getString("acc_year"));
                parameters.setString(idx++, paramInfo.getString("acc_year"));

		return template.getList(conn, parameters);
	}


    /* 마감/미감일 최대일자 */
	public static CommonEntity getDateData(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();
                
        sb.append(" SELECT MIN(MIMAGAM_DATE) MIMAGAM_DATE  \n");
        sb.append("         ,MAX(MAGAM_DATE) MAGAM_DATE    \n");
        sb.append("    FROM (                              \n");
        sb.append(" SELECT MIN(M210_DATE) MIMAGAM_DATE     \n");
        sb.append("           , '' MAGAM_DATE              \n");
        sb.append("   FROM M210_WORKEND_T                  \n");
        sb.append("  WHERE M210_YEAR = ?                   \n");
        sb.append("      AND M210_WORKENDSTATE = 'N'       \n");
        sb.append(" UNION ALL                              \n");
        sb.append(" SELECT '' MIMAGAM_DATE                 \n");
        sb.append("           , MAX(M210_DATE) MAGAM_DATE  \n");
        sb.append("   FROM M210_WORKEND_T                  \n");
        sb.append("  WHERE M210_YEAR = ?                   \n");
        sb.append("      AND M210_WORKENDSTATE = 'Y'       \n");
        sb.append(" )                                      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));

		return template.getData(conn, parameters);
	}


	/* 마감회계 추출 */
	public static List<CommonEntity> getMagamType(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT M320_YEAR, M320_ACCDATE, M320_ACCTYPE \n");
		sb.append("FROM  (                                      \n");
		sb.append("       SELECT M320_YEAR                      \n");
		sb.append("             ,M320_DATEILBAN  M320_ACCDATE   \n");
		sb.append("             ,'A'             M320_ACCTYPE   \n");
		sb.append("       FROM   M320_COLSINGDATECONTROL_T      \n");
		sb.append("       WHERE  M320_YEAR = ?                  \n");
		sb.append("       UNION ALL                             \n");
		sb.append("       SELECT M320_YEAR                      \n");
		sb.append("             ,M320_DATETEKBEYL  M320_ACCDATE \n");
		sb.append("             ,'B'               M320_ACCTYPE \n");
		sb.append("       FROM   M320_COLSINGDATECONTROL_T      \n");
		sb.append("       WHERE  M320_YEAR = ?                  \n");
		sb.append("       UNION ALL                             \n");
		sb.append("       SELECT M320_YEAR                      \n");
		sb.append("             ,M320_DATEGIGEUM  M320_ACCDATE  \n");
		sb.append("             ,'E'              M320_ACCTYPE  \n");
		sb.append("       FROM   M320_COLSINGDATECONTROL_T      \n");
		sb.append("       WHERE  M320_YEAR = ?                  \n");
		sb.append("       ) X                                   \n");
		sb.append("WHERE  M320_ACCDATE >= ?                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.getList(conn, parameters);
	}

	/* 전일누계액 오늘일자로 INSERT */
	public static int setAgoDataSetting(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

	    sb.append("INSERT INTO M220_DAY_T                                                                                                       \n");
		sb.append("(   M220_YEAR                                                                                                                \n");
		sb.append("	  ,M220_DATE                                                                                                                \n");
		sb.append("	  ,M220_ACCTYPE                                                                                                             \n");
		sb.append("	  ,M220_ACCCODE                                                                                                             \n");
		sb.append("	  ,M220_SEMOKCODE                                                                                                           \n");
		sb.append("	  ,M220_PARTCODE                                                                                                            \n");
		sb.append("	  ,M220_SUNAPGIGWANCODE                                                                                                     \n");
		sb.append("	  ,M220_AMTSEIPJEONILTOT                                                                                                    \n");
		sb.append("	  ,M220_AMTSEIP                                                                                                             \n");
		sb.append("	  ,M220_AMTGWAONAPJEONILTOT                                                                                                 \n");
		sb.append("	  ,M220_AMTSEIPGWAONAP                                                                                                      \n");
		sb.append("	  ,M220_AMTSEIPJEONGJEONG                                                                                                   \n");
		sb.append("	  ,M220_AMTPASTSEIPJEONILTOT                                                                                                \n");
		sb.append("	  ,M220_AMTPASTSEIP                                                                                                         \n");
		sb.append("	  ,M220_AMTSECHULJEONILTOT                                                                                                  \n");
		sb.append("	  ,M220_AMTSECHUL                                                                                                           \n");
		sb.append("	  ,M220_AMTSECHULBANNAP                                                                                                     \n");
		sb.append("	  ,M220_AMTSECHULJEONGJEONG                                                                                                 \n");
		sb.append("	  ,M220_AMTBAJEONGJEONILTOT                                                                                                 \n");
		sb.append("	  ,M220_AMTBAJEONG                                                                                                          \n");
		sb.append("	  ,M220_AMTBAJEONGSUJEONILTOT                                                                                               \n");
		sb.append("	  ,M220_AMTBAJEONGSURYUNG                                                                                                   \n");
		sb.append("	  ,M220_AMTSURPLUSJEONILTOT                                                                                                 \n");
		sb.append("	  ,M220_AMTSURPLUS                                                                                                          \n");
		sb.append("	  ,M220_AMTJEONGGI                                                                                                          \n");
		sb.append("	  ,M220_AMTGONGGEUM                                                                                                         \n");
		sb.append("	  ,M220_AMTLOAN                                                                                                             \n");
		sb.append(")                                                                                                                            \n");
		sb.append("SELECT (?) M220_YEAR                                                                                                         \n");
		sb.append("      ,(?) M220_DATE                                                                                                         \n");
		sb.append("      ,X.M220_ACCTYPE                                                                                                        \n");
		sb.append("      ,X.M220_ACCCODE                                                                                                        \n");
		sb.append("      ,X.M220_SEMOKCODE                                                                                                      \n");
		sb.append("      ,X.M220_PARTCODE                                                                                                       \n");
		sb.append("      ,X.M220_SUNAPGIGWANCODE                                                                                                \n");
		sb.append("      ,(X.M220_AMTSEIPJEONILTOT + X.M220_AMTSEIP - X.M220_AMTSEIPGWAONAP - X.M220_AMTSEIPJEONGJEONG) JEON_TOTAL_AMT          \n");
		sb.append("      ,(0)M220_AMTSEIP                                                                                                       \n");
		sb.append("      ,(X.M220_AMTGWAONAPJEONILTOT + X.M220_AMTSEIPGWAONAP) JEON_GWAO_TOTAL_AMT                                              \n");
		sb.append("      ,(0)M220_AMTSEIPGWAONAP                                                                                                \n");
		sb.append("      ,(0)M220_AMTSEIPJEONGJEONG                                                                                             \n");
		sb.append("      ,(X.M220_AMTPASTSEIPJEONILTOT + X.M220_AMTPASTSEIP) JEON_PASTSEIP_AMT                                                  \n");
		sb.append("      ,(0)M220_AMTPASTSEIP                                                                                                   \n");
		sb.append("      ,(X.M220_AMTSECHULJEONILTOT + X.M220_AMTSECHUL - X.M220_AMTSECHULBANNAP - X.M220_AMTSECHULJEONGJEONG) SEHUL_TOTAL_AMT  \n");
		sb.append("	     ,(0)M220_AMTSECHUL                                                                                                     \n");
		sb.append("	     ,(0)M220_AMTSECHULBANNAP                                                                                               \n");
		sb.append("	     ,(0)M220_AMTSECHULJEONGJEONG                                                                                           \n");
		sb.append("      ,(X.M220_AMTBAJEONGJEONILTOT + X.M220_AMTBAJEONG) BAEJUNG_TOTAL_AMT                                                    \n");
		sb.append("	     ,(0)M220_AMTBAJEONG                                                                                                    \n");
		sb.append("      ,(X.M220_AMTBAJEONGSUJEONILTOT + X.M220_AMTBAJEONGSURYUNG) JAEBAEJUNG_TOTAL_AMT                                        \n");
		sb.append("	     ,(0)M220_AMTBAJEONGSURYUNG                                                                                             \n");
		sb.append("      ,(X.M220_AMTSURPLUSJEONILTOT + X.M220_AMTSURPLUS) SURPLUS_TOTAL_AMT                                                    \n");
		sb.append("      ,(0)M220_AMTSURPLUS                                                                                                    \n");
		sb.append("	     ,(0)M220_AMTJEONGGI                                                                                                    \n");
		sb.append("	     ,(0)M220_AMTGONGGEUM                                                                                                   \n");
		sb.append("	     ,(0)M220_AMTLOAN                                                                                                       \n");
		sb.append("FROM   M220_DAY_T X                                                                                                          \n");
		sb.append("WHERE  X.M220_YEAR = ?                                                                                                       \n");
		sb.append("AND    X.M220_DATE = GET_AGO_BUSINESSDAY(?)                                                                                  \n");
        sb.append("AND    X.M220_ACCTYPE IN ("+paramInfo.getString("acctype")+")									                            \n");
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		
		return template.insert(conn, parameters);
	}



	/* 세입세출외현금 - 전일누계액 오늘일자로 INSERT */
	public static int setSeipSechulAgoDataSetting(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M270_TAXCASHDAY_T       \n"); 
		sb.append("(       M270_YEAR                   \n");
		sb.append("       ,M270_DATE                   \n");
		sb.append("       ,M270_ACCCODE                \n");
		sb.append("       ,M270_PARTCODE               \n");
		sb.append("       ,M270_BOJEUNGJEONGGIIBJEON   \n");
		sb.append("       ,M270_BOJEUNGBYULDANIBJEON   \n");
		sb.append("       ,M270_BOJEUNGGONGGEUMIBJEON  \n");
		sb.append("       ,M270_BOGWANJEONGGIIBJEON    \n");
		sb.append("       ,M270_BOGWANBYULDANIBJEON    \n");
		sb.append("       ,M270_BOGWANGONGGEUMIBJEON   \n");
		sb.append("       ,M270_JABJONGJEONGGIIBJEON   \n");
		sb.append("       ,M270_JABJONGBYULDANIBJEON   \n");
		sb.append("       ,M270_JABJONGGONGGEUMIBJEON  \n");
		sb.append("       ,M270_BUGASEJEONGGIIBJEON    \n");
		sb.append("       ,M270_BUGASEBYULDANIBJEON    \n");
		sb.append("       ,M270_BUGASEGONGGEUMIBJEON   \n");
		sb.append("       ,M270_BOJEUNGJEONGGIJIJEON   \n");
		sb.append("       ,M270_BOJEUNGBYULDANJIJEON   \n");
		sb.append("       ,M270_BOJEUNGGONGGEUMJIJEON  \n");
		sb.append("       ,M270_BOGWANJEONGGIJIJEON    \n");
		sb.append("       ,M270_BOGWANBYULDANJIJEON    \n");
		sb.append("       ,M270_BOGWANGONGGEUMJIJEON   \n");
		sb.append("       ,M270_JABJONGJEONGGIJIJEON   \n");
		sb.append("       ,M270_JABJONGBYULDANJIJEON   \n");
		sb.append("       ,M270_JABJONGGONGGEUMJIJEON  \n");
		sb.append("       ,M270_BUGASEJEONGGIJIJEON    \n");
		sb.append("       ,M270_BUGASEBYULDANJIJEON    \n");
		sb.append("       ,M270_BUGASEGONGGEUMJIJEON   \n");
		sb.append(")                                   \n");
		sb.append("SELECT  (?) M270_YEAR               \n");
		sb.append("       ,(?) M270_DATE               \n");
		sb.append("       ,M270_ACCCODE                \n");
		sb.append("       ,M270_PARTCODE               \n");
		sb.append("       ,(M270_BOJEUNGJEONGGIIBJEON  + M270_BOJEUNGJEONGGIIBGEUM  - M270_BOJEUNGJEONGGIIBJEONG  ) BOJ_IP_JUNG_AMT \n");
		sb.append("       ,(M270_BOJEUNGBYULDANIBJEON  + M270_BOJEUNGBYULDANIBGEUM  - M270_BOJEUNGBYULDANIBJEONG  ) BOJ_IP_BYUL_AMT \n");
		sb.append("       ,(M270_BOJEUNGGONGGEUMIBJEON + M270_BOJEUNGGONGGEUMIBGEUM - M270_BOJEUNGGONGGEUMIBJEONG ) BOJ_IP_GONG_AMT \n");
		sb.append("       ,(M270_BOGWANJEONGGIIBJEON   + M270_BOGWANJEONGGIIBGEUM   - M270_BOGWANJEONGGIIBJEONG   ) BOG_IP_JUNG_AMT \n");
		sb.append("       ,(M270_BOGWANBYULDANIBJEON   + M270_BOGWANBYULDANIBGEUM   - M270_BOGWANBYULDANIBJEONG   ) BOG_IP_BYUL_AMT \n");
		sb.append("       ,(M270_BOGWANGONGGEUMIBJEON  + M270_BOGWANGONGGEUMIBGEUM  - M270_BOGWANGONGGEUMIBJEONG  ) BOG_IP_GONG_AMT \n");
		sb.append("       ,(M270_JABJONGJEONGGIIBJEON  + M270_JABJONGJEONGGIIBGEUM  - M270_JABJONGJEONGGIIBJEONG  ) JAB_IP_JUNG_AMT \n");
		sb.append("       ,(M270_JABJONGBYULDANIBJEON  + M270_JABJONGBYULDANIBGEUM  - M270_JABJONGBYULDANIBJEONG  ) JAB_IP_BYUL_AMT \n");
		sb.append("       ,(M270_JABJONGGONGGEUMIBJEON + M270_JABJONGGONGGEUMIBGEUM - M270_JABJONGGONGGEUMIBJEONG ) JAB_IP_GONG_AMT \n");
		sb.append("       ,(M270_BUGASEJEONGGIIBJEON   + M270_BUGASEJEONGGIIBGEUM   - M270_BUGASEJEONGGIIBJEONG   ) BUG_IP_JUNG_AMT \n");
		sb.append("       ,(M270_BUGASEBYULDANIBJEON   + M270_BUGASEBYULDANIBGEUM   - M270_BUGASEBYULDANIBJEONG   ) BUG_IP_BYUL_AMT \n");
		sb.append("       ,(M270_BUGASEGONGGEUMIBJEON  + M270_BUGASEGONGGEUMIBGEUM  - M270_BUGASEGONGGEUMIBJEONG  ) BUG_IP_GONG_AMT \n");
		sb.append("       ,(M270_BOJEUNGJEONGGIJIJEON  + M270_BOJEUNGJEONGGIJIGEUB  - M270_BOJEUNGJEONGGIJIJEONG  ) BOJ_JI_JUNG_AMT \n");
		sb.append("       ,(M270_BOJEUNGBYULDANJIJEON  + M270_BOJEUNGBYULDANJIGEUB  - M270_BOJEUNGBYULDANJIJEONG  ) BOJ_JI_BYUL_AMT \n");
		sb.append("       ,(M270_BOJEUNGGONGGEUMJIJEON + M270_BOJEUNGGONGGEUMJIGEUB - M270_BOJEUNGGONGGEUMJIJEONG ) BOJ_JI_GONG_AMT \n");
		sb.append("       ,(M270_BOGWANJEONGGIJIJEON   + M270_BOGWANJEONGGIJIGEUB   - M270_BOGWANJEONGGIJIJEONG   ) BOG_JI_JUNG_AMT \n");
		sb.append("       ,(M270_BOGWANBYULDANJIJEON   + M270_BOGWANBYULDANJIGEUB   - M270_BOGWANBYULDANJIJEONG   ) BOG_JI_BYUL_AMT \n");
		sb.append("       ,(M270_BOGWANGONGGEUMJIJEON  + M270_BOGWANGONGGEUMJIGEUB  - M270_BOGWANGONGGEUMJIJEONG  ) BOG_JI_GONG_AMT \n");
		sb.append("       ,(M270_JABJONGJEONGGIJIJEON  + M270_JABJONGJEONGGIJIGEUB  - M270_JABJONGJEONGGIJIJEONG  ) JAB_JI_JUNG_AMT \n");
		sb.append("       ,(M270_JABJONGBYULDANJIJEON  + M270_JABJONGBYULDANJIGEUB  - M270_JABJONGBYULDANJIJEONG  ) JAB_JI_BYUL_AMT \n");
		sb.append("       ,(M270_JABJONGGONGGEUMJIJEON + M270_JABJONGGONGGEUMJIGEUB - M270_JABJONGGONGGEUMJIJEONG ) JAB_JI_GONG_AMT \n");
		sb.append("       ,(M270_BUGASEJEONGGIJIJEON   + M270_BUGASEJEONGGIJIGEUB   - M270_BUGASEJEONGGIJIJEONG   ) BUG_JI_JUNG_AMT \n");
		sb.append("       ,(M270_BUGASEBYULDANJIJEON   + M270_BUGASEBYULDANJIGEUB   - M270_BUGASEBYULDANJIJEONG   ) BUG_JI_BYUL_AMT \n");
		sb.append("       ,(M270_BUGASEGONGGEUMJIJEON  + M270_BUGASEGONGGEUMJIGEUB  - M270_BUGASEGONGGEUMJIJEONG  ) BUG_JI_GONG_AMT \n");
		sb.append("FROM    M270_TAXCASHDAY_T               \n");    
		sb.append("WHERE   M270_YEAR = ?                   \n");
		sb.append("AND     M270_DATE = GET_AGO_BUSINESSDAY(?) \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.insert(conn, parameters);
	}

	/* 전일누계자료가 한건도 없는경우 오늘일자로 ALL INSERT */
	public static int setAgoNoDataAllSetting(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

	    sb.append("INSERT INTO M220_DAY_T													 \n");
		sb.append("(   M220_YEAR															 \n");
		sb.append("	  ,M220_DATE															 \n");
		sb.append("	  ,M220_ACCTYPE															 \n");
		sb.append("	  ,M220_ACCCODE															 \n");
		sb.append("	  ,M220_SEMOKCODE														 \n");
		sb.append("	  ,M220_PARTCODE														 \n");
		sb.append("	  ,M220_SUNAPGIGWANCODE													 \n");
		sb.append("	  ,M220_AMTSEIPJEONILTOT												 \n");
		sb.append("	  ,M220_AMTSEIP															 \n");
		sb.append("	  ,M220_AMTGWAONAPJEONILTOT												 \n");
		sb.append("	  ,M220_AMTSEIPGWAONAP													 \n");
		sb.append("	  ,M220_AMTSEIPJEONGJEONG												 \n");
		sb.append("	  ,M220_AMTPASTSEIPJEONILTOT                                             \n");
		sb.append("	  ,M220_AMTPASTSEIP                                                      \n");
		sb.append("	  ,M220_AMTSECHULJEONILTOT												 \n");
		sb.append("	  ,M220_AMTSECHUL														 \n");
		sb.append("	  ,M220_AMTSECHULBANNAP													 \n");
		sb.append("	  ,M220_AMTSECHULJEONGJEONG												 \n");
		sb.append("	  ,M220_AMTBAJEONGJEONILTOT												 \n");
		sb.append("	  ,M220_AMTBAJEONG														 \n");
		sb.append("	  ,M220_AMTBAJEONGSUJEONILTOT											 \n");
		sb.append("	  ,M220_AMTBAJEONGSURYUNG												 \n");
		sb.append("	  ,M220_AMTSURPLUSJEONILTOT												 \n");
		sb.append("	  ,M220_AMTSURPLUS														 \n");
		sb.append("	  ,M220_AMTJEONGGI														 \n");
		sb.append("	  ,M220_AMTGONGGEUM														 \n");
		sb.append("	  ,M220_AMTLOAN  														 \n");
		sb.append(")																		 \n");
		sb.append("SELECT  DAY_YEAR															 \n");
		sb.append("       ,DAY_DATE															 \n");
		sb.append("       ,DAY_ACCTYPE														 \n");
		sb.append("       ,DAY_ACCCODE														 \n");
		sb.append("       ,DAY_SEMOKCODE													 \n");
		sb.append("       ,DAY_PARTCODE														 \n");
		sb.append("       ,DAY_SUNAPGIGWANCODE												 \n");
		sb.append("       ,(0) M220_AMTSEIPJEONILTOT										 \n"); //세입전일누계
		sb.append("       ,SUM(SEIP_AMT              ) SEIP_AMT                              \n");
		sb.append("	      ,(0) M220_AMTGWAONAPJEONILTOT                                      \n"); //과오납전일누계
		sb.append("       ,SUM(SEIP_GWAO_AMT         ) SEIP_GWAO_AMT                         \n");
		sb.append("       ,SUM(SEIP_JUNGJUNG_AMT     ) SEIP_JUNGJUNG_AMT                     \n");
		sb.append("	      ,(0) M220_AMTPASTSEIPJEONILTOT                                     \n"); //과년도전일누계
		sb.append("	      ,SUM(PASTSEIP_AMT          ) PASTSEIP_AMT                          \n");
		sb.append("       ,(0) M220_AMTSECHULJEONILTOT                                       \n"); //세출전일누계
		sb.append("       ,SUM(SECHUL_AMT            ) SECHUL_AMT                            \n");
		sb.append("       ,SUM(SECHUL_BAN_AMT        ) SECHUL_BAN_AMT                        \n");
		sb.append("       ,SUM(SECHUL_JUNGJUNG_AMT   ) SECHUL_JUNGJUNG_AMT                   \n");
		sb.append("       ,(0) M220_AMTBAJEONGJEONILTOT                                      \n"); //배정전일누계
		sb.append("       ,SUM(BAEJUNG_AMT           ) BAEJUNG_AMT                           \n");
		sb.append("	      ,(0) M220_AMTBAJEONGSUJEONILTOT                                    \n"); //배정수령전일누계
		sb.append("       ,SUM(BAEJUNG_JAE_AMT       ) BAEJUNG_JAE_AMT                       \n");
		sb.append("	      ,(0) M220_AMTSURPLUSJEONILTOT                                      \n"); //잉여금전일누계
		sb.append("       ,SUM(ING_AMT               ) ING_AMT                               \n");
		sb.append("       ,SUM(JAN_JUNG_ETC          ) JAN_JUNG_ETC                          \n");
		sb.append("       ,SUM(JAN_JUNG_AMT          ) JAN_JUNG_AMT                          \n");
		sb.append("       ,SUM(LOAN_AMT              ) LOAD_AMT                              \n"); //융자금
		sb.append("FROM   (                                                                  \n");
		sb.append("        SELECT M010_YEAR               DAY_YEAR                           \n");
		sb.append("              ,M010_DATE               DAY_DATE                           \n");
		sb.append("              ,M010_ACCTYPE            DAY_ACCTYPE                        \n");
		sb.append("              ,M010_ACCCODE            DAY_ACCCODE                        \n");
		sb.append("              ,M010_SEMOKCODE          DAY_SEMOKCODE                      \n");
		sb.append("              ,M010_PARTCODE           DAY_PARTCODE                       \n");
		sb.append("              ,M010_SUNAPGIGWANCODE    DAY_SUNAPGIGWANCODE                \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I1', M010_AMT, 0)) SEIP_AMT       \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I2', M010_AMT, 0)) SEIP_GWAO_AMT  \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I3', M010_AMT, 0)) SEIP_JUNGJUNG_AMT   \n");
		sb.append("              ,SUM(CASE WHEN M010_YEARTYPE = 'Y2' THEN DECODE(M010_INTYPE, 'I1', M010_AMT, 0) - DECODE(M010_INTYPE, 'I2', M010_AMT, 0) - DECODE(M010_INTYPE, 'I3', M010_AMT, 0) ELSE 0 END) PASTSEIP_AMT  \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT											 \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
		sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('1') DATA_GB                                              \n");
		sb.append("        FROM   M010_TAXIN_T                                               \n");  //세입수기분
		sb.append("        WHERE  M010_YEAR = ?                                              \n");
		sb.append("        AND    M010_DATE = ?                                              \n");
		sb.append("        GROUP BY M010_YEAR                                                \n");
		sb.append("              ,M010_DATE                                                  \n");
		sb.append("              ,M010_ACCTYPE                                               \n");
		sb.append("              ,M010_ACCCODE                                               \n");
		sb.append("              ,M010_SEMOKCODE                                             \n");
		sb.append("              ,M010_PARTCODE                                              \n");
		sb.append("              ,M010_SUNAPGIGWANCODE                                       \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT  M030_YEAR         DAY_YEAR                                \n");
		sb.append("               ,M030_DATE         DAY_DATE                                \n");
		sb.append("               ,M030_ACCTYPE      DAY_ACCTYPE                             \n");
		sb.append("               ,M030_ACCCODE      DAY_ACCCODE                             \n");
		sb.append("               ,M030_SEMOKCODE    DAY_SEMOKCODE                           \n");
		sb.append("               ,M030_PARTCODE     DAY_PARTCODE                            \n");
		sb.append("               ,('110000')        DAY_SUNAPGIGWANCODE                     \n");
		sb.append("               ,(0) SEIP_AMT                                              \n");
		sb.append("               ,(0) SEIP_GWAO_AMT                                         \n");
		sb.append("               ,(0) SEIP_JUNGJUNG_AMT                                     \n");
		sb.append("               ,(0) PASTSEIP_AMT                                          \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I1', M030_AMT, 0)) SECHUL_AMT    \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I2', M030_AMT, 0)) SECHUL_BAN_AMT \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I3', M030_AMT, 0)) SECHUL_JUNGJUNG_AMT  \n");
		sb.append("               ,(0) BAEJUNG_AMT											 \n");
		sb.append("               ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("               ,(0) ING_AMT                                               \n");
		sb.append("               ,(0) JAN_JUNG_AMT                                          \n");
		sb.append("               ,(0) JAN_JUNG_ETC                                          \n");
		sb.append("               ,(0) LOAN_AMT                                              \n");
		sb.append("               ,('2') DATA_GB                                             \n");
		sb.append("        FROM    M030_TAXOTHER_T                                           \n");  //세출수기분
		sb.append("        WHERE   M030_YEAR = ?                                             \n");
		sb.append("        AND     M030_DATE = ?                                             \n");
		sb.append("        GROUP BY M030_YEAR                                                \n");
		sb.append("                  ,M030_DATE                                              \n");
		sb.append("                  ,M030_ACCTYPE                                           \n");
		sb.append("                  ,M030_ACCCODE                                           \n");
		sb.append("                  ,M030_SEMOKCODE                                         \n");
		sb.append("                  ,M030_PARTCODE                                          \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M100_YEAR          DAY_YEAR                                \n");
		sb.append("              ,M100_DATE          DAY_DATE                                \n");
		sb.append("              ,('A')              DAY_ACCTYPE                             \n");
		sb.append("              ,M100_ACCCODE       DAY_ACCCODE                             \n");
		sb.append("              ,('1110100')        DAY_SEMOKCODE                           \n");
		sb.append("              ,M100_PARTCODE      DAY_PARTCODE                            \n");
		sb.append("              ,('110000')         DAY_SUNAPGIGWANCODE                     \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,SUM(M100_ALLOTAMT) BAEJUNG_JAE_AMT                         \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
		sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('3') DATA_GB                                              \n");
		sb.append("        FROM   M100_MONEYALLOT_T                                          \n");  //자금배정
		sb.append("        WHERE  M100_YEAR = ?                                              \n");
		sb.append("        AND    M100_DATE = ?                                              \n");
		sb.append("        AND    M100_ALLOTCODE > 0                                         \n");
		sb.append("        GROUP BY M100_YEAR                                                \n");
		sb.append("                ,M100_DATE                                                \n");
		sb.append("                ,M100_ACCCODE                                             \n");
		sb.append("                ,M100_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M100_YEAR      DAY_YEAR                                    \n");
		sb.append("              ,M100_DATE      DAY_DATE                                    \n");
		sb.append("              ,('A')          DAY_ACCTYPE                                 \n");
		sb.append("              ,('11')         DAY_ACCCODE                                 \n");
		sb.append("              ,('1110100')    DAY_SEMOKCODE                               \n");
		sb.append("              ,('00000')      DAY_PARTCODE                                \n");
		sb.append("              ,('110000')     DAY_SUNAPGIGWANCODE                         \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,SUM(M100_ALLOTAMT) BAEJUNG_AMT                             \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
		sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('3') DATA_GB                                              \n");
		sb.append("        FROM   M100_MONEYALLOT_T                                          \n");  //자금배정 TOTAL
		sb.append("        WHERE  M100_YEAR = ?                                              \n");
		sb.append("        AND    M100_DATE = ?                                              \n");
		sb.append("        AND    M100_ALLOTCODE > 0                                         \n");
		sb.append("        GROUP BY M100_YEAR                                                \n");
		sb.append("                ,M100_DATE                                                \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M330_YEAR       DAY_YEAR                                   \n");
		sb.append("              ,M330_DATE       DAY_DATE                                   \n");
		sb.append("              ,M330_ACCGUBUN   DAY_ACCTYPE                                \n");
		sb.append("              ,M330_ACCCODE    DAY_ACCCODE                                \n");
		sb.append("              ,('1110100')     DAY_SEMOKCODE                              \n");
		sb.append("              ,M330_PARTCODE   DAY_PARTCODE                               \n");
		sb.append("              ,('110000')      DAY_SUNAPGIGWANCODE                        \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(SUM(DECODE(M330_ALLOTGUBUN, '1', M330_TAMT, 0)) +		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '2', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '3', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '4', M330_TAMT, 0))) BAEJUNG_JAE_AMT	\n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
		sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('4') DATA_GB                                              \n");
		sb.append("        FROM   M330_MONEYALLOTMANUAL_T                                    \n");  //자금배정수기분(수령액)
		sb.append("        WHERE  M330_YEAR = ?                                              \n");
		sb.append("        AND    M330_DATE = ?                                             \n");
		sb.append("        GROUP BY M330_YEAR                                                \n");
		sb.append("                ,M330_DATE                                                \n");
		sb.append("                ,M330_ACCGUBUN                                            \n");
		sb.append("                ,M330_ACCCODE                                             \n");
		sb.append("                ,M330_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M330_YEAR         DAY_YEAR                                 \n");
		sb.append("              ,M330_DATE         DAY_DATE                                 \n");
		sb.append("              ,M330_ACCGUBUN     DAY_ACCTYPE                              \n");
		sb.append("              ,M330_ORGACCCODE   DAY_ACCCODE                              \n");
		sb.append("              ,('1110100')       DAY_SEMOKCODE                            \n");
		sb.append("              ,M330_ORGPARTCODE  DAY_PARTCODE                             \n");
		sb.append("              ,('110000')        DAY_SUNAPGIGWANCODE                      \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(SUM(DECODE(M330_ALLOTGUBUN, '1', M330_TAMT, 0)) +		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '2', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '3', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '4', M330_TAMT, 0))) BAEJUNG_AMT 	\n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT                                        \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
		sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('4') DATA_GB                                              \n");
		sb.append("        FROM   M330_MONEYALLOTMANUAL_T                                    \n");  //자금배정수기분(배정액)
		sb.append("        WHERE  M330_YEAR = ?                                              \n");
		sb.append("        AND    M330_DATE = ?                                              \n");
		sb.append("        GROUP BY M330_YEAR                                                \n");
		sb.append("                ,M330_DATE                                                \n");
		sb.append("                ,M330_ACCGUBUN                                            \n");
		sb.append("                ,M330_ORGACCCODE                                          \n");
		sb.append("                ,M330_ORGPARTCODE                                         \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M240_YEAROVER      DAY_YEAR                                \n");
		sb.append("              ,M240_DATE          DAY_DATE                                \n");
		sb.append("              ,M240_ACCTYPE       DAY_ACCTYPE                             \n");
		sb.append("              ,M240_ACCCODE       DAY_ACCCODE                             \n");
		sb.append("              ,M240_SEMOKCODE     DAY_SEMOKCODE                           \n");
		sb.append("              ,M240_PARTCODE      DAY_PARTCODE                            \n");
		sb.append("              ,('110000')         DAY_SUNAPGIGWANCODE                     \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,SUM(M240_AMT) ING_AMT                                      \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('5') DATA_GB                                              \n");
		sb.append("        FROM   M240_SURPLUSINTODETAILS_T                                  \n");  //잉여금
		sb.append("        WHERE  M240_YEAROVER = ?                                          \n");
		sb.append("        AND    M240_DATE     = ?                                          \n");
		sb.append("        GROUP BY M240_YEAROVER                                            \n");
		sb.append("                ,M240_DATE                                                \n");
		sb.append("                ,M240_ACCTYPE                                             \n");
		sb.append("                ,M240_ACCCODE                                             \n");
		sb.append("                ,M240_SEMOKCODE                                           \n");
		sb.append("                ,M240_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT  M170_YEAR       DAY_YEAR                                  \n");
		sb.append("               ,M170_DATE       DAY_DATE                                  \n");
		sb.append("               ,M170_ACCTYPE    DAY_ACCTYPE                               \n");
		sb.append("               ,M170_ACCCODE    DAY_ACCCODE                               \n");
		sb.append("               ,('9999900')     DAY_SEMOKCODE                             \n");
		sb.append("               ,M170_PARTCODE   DAY_PARTCODE                              \n");
		sb.append("               ,('110000')      DAY_SUNAPGIGWANCODE                       \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0)) JAN_JUNG_AMT        \n");
		sb.append("              ,(SUM(NVL(M170_MMDABEFOREDAYJANAMT,0)) +                    \n");
		sb.append("                SUM(NVL(M170_JEUNGGAMAMT,0)) +                            \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0)) +               \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0)) +               \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0)) +               \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0)) +               \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_1,0)) +                   \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_2,0))+                    \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_3,0)) +                   \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_4,0)) +                   \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_1,0)) +                       \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_2,0)) +                       \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_3,0)) +                       \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_4,0)) +                       \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_1,0)) +                        \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_2,0)) +                        \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_3,0)) +                        \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_4,0)) +                        \n");
		sb.append("                SUM(NVL(M170_LOANBEFOREDAYJANAMT,0)) +                    \n");
		sb.append("                SUM(NVL(M170_LOANJEUNGGAMAMT,0))) JAN_JUNG_ETC            \n");
		sb.append("              ,(SUM(NVL(M170_LOANBEFOREDAYJANAMT,0))+SUM(NVL(M170_LOANJEUNGGAMAMT,0))) LOAN_AMT \n");
		sb.append("              ,('6') DATA_GB                                              \n");
		sb.append("        FROM    M170_JANECKJANG_T                                         \n");  //잔액장
		sb.append("        WHERE   M170_YEAR = ?                                             \n");
		sb.append("        AND     M170_DATE = ?                                             \n");
		sb.append("        AND     M170_ACCTYPE IN ('A','B','E')                             \n");
		sb.append("        GROUP BY M170_YEAR                                                \n");
		sb.append("                ,M170_DATE                                                \n");
		sb.append("                ,M170_ACCTYPE                                             \n");
		sb.append("                ,M170_ACCCODE                                             \n");
		sb.append("                ,M170_PARTCODE                                            \n");
		sb.append("       ) X                                                                \n");
		sb.append("GROUP BY DAY_YEAR                                                         \n");
		sb.append("        ,DAY_DATE                                                         \n");
		sb.append("        ,DAY_ACCTYPE                                                      \n");
		sb.append("        ,DAY_ACCCODE                                                      \n");
		sb.append("        ,DAY_SEMOKCODE                                                    \n");
		sb.append("        ,DAY_PARTCODE                                                     \n");
		sb.append("        ,DAY_SUNAPGIGWANCODE                                              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.insert(conn, parameters);
	}



	/* 세입세출외현금 - 전일누계자료가 한건도 없는경우 오늘일자로 ALL INSERT */
	public static int setSeipSechulAgoNoDataAllSetting(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M270_TAXCASHDAY_T     \n");
		sb.append("(   M270_YEAR                     \n");
		sb.append("   ,M270_DATE                     \n");
		sb.append("   ,M270_ACCCODE                  \n");
		sb.append("   ,M270_PARTCODE                 \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBJEON     \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBJEON     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBJEON    \n");
		sb.append("   ,M270_BOGWANJEONGGIIBJEON      \n");
		sb.append("   ,M270_BOGWANBYULDANIBJEON      \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBJEON     \n");
		sb.append("   ,M270_JABJONGJEONGGIIBJEON     \n");
		sb.append("   ,M270_JABJONGBYULDANIBJEON     \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBJEON    \n");
		sb.append("   ,M270_BUGASEJEONGGIIBJEON      \n");
		sb.append("   ,M270_BUGASEBYULDANIBJEON      \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBJEON     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBGEUM    \n");
		sb.append("   ,M270_BOGWANJEONGGIIBGEUM      \n");
		sb.append("   ,M270_BOGWANBYULDANIBGEUM      \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBGEUM     \n");
		sb.append("   ,M270_JABJONGJEONGGIIBGEUM     \n");
		sb.append("   ,M270_JABJONGBYULDANIBGEUM     \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBGEUM    \n");
		sb.append("   ,M270_BUGASEJEONGGIIBGEUM      \n");
		sb.append("   ,M270_BUGASEBYULDANIBGEUM      \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBJEONG   \n");
		sb.append("   ,M270_BOGWANJEONGGIIBJEONG     \n");
		sb.append("   ,M270_BOGWANBYULDANIBJEONG     \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBJEONG    \n");
		sb.append("   ,M270_JABJONGJEONGGIIBJEONG    \n");
		sb.append("   ,M270_JABJONGBYULDANIBJEONG    \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBJEONG   \n");
		sb.append("   ,M270_BUGASEJEONGGIIBJEONG     \n");
		sb.append("   ,M270_BUGASEBYULDANIBJEONG     \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIJEON     \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIJEON     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIJEON    \n");
		sb.append("   ,M270_BOGWANJEONGGIJIJEON      \n");
		sb.append("   ,M270_BOGWANBYULDANJIJEON      \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIJEON     \n");
		sb.append("   ,M270_JABJONGJEONGGIJIJEON     \n");
		sb.append("   ,M270_JABJONGBYULDANJIJEON     \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIJEON    \n");
		sb.append("   ,M270_BUGASEJEONGGIJIJEON      \n");
		sb.append("   ,M270_BUGASEBYULDANJIJEON      \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIJEON     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIGEUB    \n");
		sb.append("   ,M270_BOGWANJEONGGIJIGEUB      \n");
		sb.append("   ,M270_BOGWANBYULDANJIGEUB      \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIGEUB     \n");
		sb.append("   ,M270_JABJONGJEONGGIJIGEUB     \n");
		sb.append("   ,M270_JABJONGBYULDANJIGEUB     \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIGEUB    \n");
		sb.append("   ,M270_BUGASEJEONGGIJIGEUB      \n");
		sb.append("   ,M270_BUGASEBYULDANJIGEUB      \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIJEONG    \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIJEONG    \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIJEONG   \n");
		sb.append("   ,M270_BOGWANJEONGGIJIJEONG     \n");
		sb.append("   ,M270_BOGWANBYULDANJIJEONG     \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIJEONG    \n");
		sb.append("   ,M270_JABJONGJEONGGIJIJEONG    \n");
		sb.append("   ,M270_JABJONGBYULDANJIJEONG    \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIJEONG   \n");
		sb.append("   ,M270_BUGASEJEONGGIJIJEONG     \n");
		sb.append("   ,M270_BUGASEBYULDANJIJEONG     \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIJEONG    \n");
		sb.append(")                                 \n");
		sb.append("SELECT M040_YEAR                  \n");
		sb.append("      ,M040_DATE                  \n");
		sb.append("      ,M040_ACCCODE               \n");
		sb.append("      ,M040_PARTCODE              \n");
		sb.append("      ,(0) M270_BOJEUNGJEONGGIIBJEON     \n");
		sb.append("      ,(0) M270_BOJEUNGBYULDANIBJEON     \n");
		sb.append("      ,(0) M270_BOJEUNGGONGGEUMIBJEON    \n");
		sb.append("      ,(0) M270_BOGWANJEONGGIIBJEON      \n");
		sb.append("      ,(0) M270_BOGWANBYULDANIBJEON      \n");
		sb.append("      ,(0) M270_BOGWANGONGGEUMIBJEON     \n");
		sb.append("      ,(0) M270_JABJONGJEONGGIIBJEON     \n");
		sb.append("      ,(0) M270_JABJONGBYULDANIBJEON     \n");
		sb.append("      ,(0) M270_JABJONGGONGGEUMIBJEON    \n");
		sb.append("      ,(0) M270_BUGASEJEONGGIIBJEON      \n");
		sb.append("      ,(0) M270_BUGASEBYULDANIBJEON      \n");
		sb.append("      ,(0) M270_BUGASEGONGGEUMIBJEON     \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BOJJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BOJBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BOJGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BOGJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BOGBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BOGGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_JABJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_JABBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_JABGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BUGJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BUGBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BUGGONG_AMT          \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGGONG_AMT   \n");
		sb.append("      ,(0) M270_BOJEUNGJEONGGIJIJEON     \n");
		sb.append("      ,(0) M270_BOJEUNGBYULDANJIJEON     \n");
		sb.append("      ,(0) M270_BOJEUNGGONGGEUMJIJEON    \n");
		sb.append("      ,(0) M270_BOGWANJEONGGIJIJEON      \n");
		sb.append("      ,(0) M270_BOGWANBYULDANJIJEON      \n");
		sb.append("      ,(0) M270_BOGWANGONGGEUMJIJEON     \n");
		sb.append("      ,(0) M270_JABJONGJEONGGIJIJEON     \n");
		sb.append("      ,(0) M270_JABJONGBYULDANJIJEON     \n");
		sb.append("      ,(0) M270_JABJONGGONGGEUMJIJEON    \n");
		sb.append("      ,(0) M270_BUGASEJEONGGIJIJEON      \n");
		sb.append("      ,(0) M270_BUGASEBYULDANJIJEON      \n");
		sb.append("      ,(0) M270_BUGASEGONGGEUMJIJEON     \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BOJJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BOJBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BOJGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BOGJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BOGBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BOGGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_JABJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_JABBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_JABGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BUGJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BUGBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BUGGONG_AMT         \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGGONG_AMT  \n");
		sb.append("FROM   M040_TAXCASH_T      \n");        
		sb.append("WHERE  M040_YEAR = ?       \n");
		sb.append("AND    M040_DATE = ?       \n");
		sb.append("GROUP BY M040_YEAR         \n");
		sb.append("        ,M040_DATE         \n");
		sb.append("        ,M040_ACCCODE      \n");
		sb.append("        ,M040_PARTCODE     \n");      

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.insert(conn, parameters);
	}



	/* 전일자료가 있는지 체크 */
	public static int getAgoSumDataCnt(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT COUNT(1) CNT                 \n");
		sb.append("FROM   M220_DAY_T                   \n");
		sb.append("WHERE  M220_YEAR      = ?           \n");
		sb.append("AND    M220_DATE      = ?           \n");
		sb.append("AND    M220_ACCTYPE   = ?           \n");
		sb.append("AND    M220_ACCCODE   = ?           \n");
		sb.append("AND    M220_SEMOKCODE = ?           \n");
		sb.append("AND    M220_PARTCODE  = ?           \n");
		sb.append("AND    M220_SUNAPGIGWANCODE  = ?    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("DAY_YEAR"));
		parameters.setString(idx++, paramInfo.getString("DAY_DATE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCTYPE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_SEMOKCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_PARTCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_SUNAPGIGWANCODE"));

		CommonEntity cCnt = template.getData(conn, parameters);
		int agoCnt = cCnt.getInt("CNT");

		return agoCnt; 
	}


	/* 세입세출외현금 - 전일자료가 있는지 체크 */
	public static int getSeipSechulAgoSumDataCnt(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT COUNT(1) CNT         \n");
		sb.append("FROM   M270_TAXCASHDAY_T    \n");
		sb.append("WHERE  M270_YEAR      = ?   \n");
		sb.append("AND    M270_DATE      = ?   \n");
		sb.append("AND    M270_ACCCODE   = ?   \n");
		sb.append("AND    M270_PARTCODE  = ?   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("M040_YEAR"));
		parameters.setString(idx++, paramInfo.getString("M040_DATE"));
		parameters.setString(idx++, paramInfo.getString("M040_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("M040_PARTCODE"));

		CommonEntity cCnt = template.getData(conn, parameters);
		int agoCnt = cCnt.getInt("CNT");

		return agoCnt; 
	}


	/* 오늘일자 마감자료 추출 */
	public static List<CommonEntity> getTodayMagamList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT  DAY_YEAR															 \n");
		sb.append("       ,DAY_DATE															 \n");
		sb.append("       ,DAY_ACCTYPE														 \n");
		sb.append("       ,DAY_ACCCODE														 \n");
		sb.append("       ,DAY_SEMOKCODE													 \n");
		sb.append("       ,DAY_PARTCODE														 \n");
		sb.append("       ,DAY_SUNAPGIGWANCODE												 \n");
		sb.append("       ,SUM(SEIP_AMT              ) SEIP_AMT                              \n");
		sb.append("       ,SUM(SEIP_GWAO_AMT         ) SEIP_GWAO_AMT                         \n");
		sb.append("       ,SUM(SEIP_JUNGJUNG_AMT     ) SEIP_JUNGJUNG_AMT                     \n");
		sb.append("	      ,SUM(PASTSEIP_AMT          ) PASTSEIP_AMT                          \n");
		sb.append("       ,SUM(SECHUL_AMT            ) SECHUL_AMT                            \n");
		sb.append("       ,SUM(SECHUL_BAN_AMT        ) SECHUL_BAN_AMT                        \n");
		sb.append("       ,SUM(SECHUL_JUNGJUNG_AMT   ) SECHUL_JUNGJUNG_AMT                   \n");
		sb.append("       ,SUM(BAEJUNG_AMT           ) BAEJUNG_AMT                           \n");
		sb.append("       ,SUM(BAEJUNG_JAE_AMT       ) BAEJUNG_JAE_AMT                       \n");
		sb.append("       ,SUM(ING_AMT               ) ING_AMT                               \n");
		sb.append("       ,SUM(JAN_JUNG_ETC          ) JAN_JUNG_ETC                          \n");
		sb.append("       ,SUM(JAN_JUNG_AMT          ) JAN_JUNG_AMT                          \n");
        sb.append("       ,SUM(LOAN_AMT              ) LOAN_AMT                              \n");
		sb.append("FROM   (                                                                  \n");
		sb.append("        SELECT M010_YEAR               DAY_YEAR                           \n");
		sb.append("              ,M010_DATE               DAY_DATE                           \n");
		sb.append("              ,M010_ACCTYPE            DAY_ACCTYPE                        \n");
		sb.append("              ,M010_ACCCODE            DAY_ACCCODE                        \n");
		sb.append("              ,M010_SEMOKCODE          DAY_SEMOKCODE                      \n");
		sb.append("              ,M010_PARTCODE           DAY_PARTCODE                       \n");
		sb.append("              ,M010_SUNAPGIGWANCODE    DAY_SUNAPGIGWANCODE                \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I1', M010_AMT, 0)) SEIP_AMT       \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I2', M010_AMT, 0)) SEIP_GWAO_AMT  \n");
		sb.append("              ,SUM(DECODE(M010_INTYPE, 'I3', M010_AMT, 0)) SEIP_JUNGJUNG_AMT   \n");
		sb.append("              ,SUM(CASE WHEN M010_YEARTYPE = 'Y2' THEN DECODE(M010_INTYPE, 'I1', M010_AMT, 0) - DECODE(M010_INTYPE, 'I2', M010_AMT, 0) - DECODE(M010_INTYPE, 'I3', M010_AMT, 0) ELSE 0 END) PASTSEIP_AMT  \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT											 \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('1') DATA_GB                                              \n");
		sb.append("        FROM   M010_TAXIN_T                                               \n");  //세입수기분
		sb.append("        WHERE  M010_YEAR = ?                                              \n");
		sb.append("        AND    M010_DATE = ?                                              \n");
		sb.append("        GROUP BY M010_YEAR                                                \n");
		sb.append("              ,M010_DATE                                                  \n");
		sb.append("              ,M010_ACCTYPE                                               \n");
		sb.append("              ,M010_ACCCODE                                               \n");
		sb.append("              ,M010_SEMOKCODE                                             \n");
		sb.append("              ,M010_PARTCODE                                              \n");
		sb.append("              ,M010_SUNAPGIGWANCODE                                       \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT  M030_YEAR         DAY_YEAR                                \n");
		sb.append("               ,M030_DATE         DAY_DATE                                \n");
		sb.append("               ,M030_ACCTYPE      DAY_ACCTYPE                             \n");
		sb.append("               ,M030_ACCCODE      DAY_ACCCODE                             \n");
		sb.append("               ,M030_SEMOKCODE    DAY_SEMOKCODE                           \n");
		sb.append("               ,M030_PARTCODE     DAY_PARTCODE                            \n");
		sb.append("               ,('110000')        DAY_SUNAPGIGWANCODE                     \n");
		sb.append("               ,(0) SEIP_AMT                                              \n");
		sb.append("               ,(0) SEIP_GWAO_AMT                                         \n");
		sb.append("               ,(0) SEIP_JUNGJUNG_AMT                                     \n");
		sb.append("               ,(0) PASTSEIP_AMT                                          \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I1', M030_AMT, 0)) SECHUL_AMT    \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I2', M030_AMT, 0)) SECHUL_BAN_AMT \n");
		sb.append("               ,SUM(DECODE(M030_INTYPE, 'I3', M030_AMT, 0)) SECHUL_JUNGJUNG_AMT  \n");
		sb.append("               ,(0) BAEJUNG_AMT                                           \n");
		sb.append("               ,(0) BAEJUNG_JAE_AMT                                       \n");
		sb.append("               ,(0) ING_AMT                                               \n");
		sb.append("               ,(0) JAN_JUNG_AMT                                          \n");
		sb.append("               ,(0) JAN_JUNG_ETC                                          \n");
        sb.append("               ,(0) LOAN_AMT                                              \n");
		sb.append("               ,('2') DATA_GB                                             \n");
		sb.append("        FROM    M030_TAXOTHER_T                                           \n");  //세출수기분
		sb.append("        WHERE   M030_YEAR = ?                                             \n");
		sb.append("        AND     M030_DATE = ?                                             \n");
		sb.append("        GROUP BY M030_YEAR                                                \n");
		sb.append("                  ,M030_DATE                                              \n");
		sb.append("                  ,M030_ACCTYPE                                           \n");
		sb.append("                  ,M030_ACCCODE                                           \n");
		sb.append("                  ,M030_SEMOKCODE                                         \n");
		sb.append("                  ,M030_PARTCODE                                          \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M100_YEAR          DAY_YEAR                                \n");
		sb.append("              ,M100_DATE          DAY_DATE                                \n");
		sb.append("              ,('A')              DAY_ACCTYPE                             \n");
		sb.append("              ,M100_ACCCODE       DAY_ACCCODE                             \n");
		sb.append("              ,('1110100')        DAY_SEMOKCODE                           \n");
		sb.append("              ,M100_PARTCODE      DAY_PARTCODE                            \n");
		sb.append("              ,('110000')         DAY_SUNAPGIGWANCODE                     \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,SUM(M100_ALLOTAMT) BAEJUNG_JAE_AMT                         \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('3') DATA_GB                                              \n");
		sb.append("        FROM   M100_MONEYALLOT_T                                          \n");  //자금배정
		sb.append("        WHERE  M100_YEAR = ?                                              \n");
		sb.append("        AND    M100_DATE = ?                                              \n");
		sb.append("        AND    M100_ALLOTCODE > 0                                         \n");
		sb.append("        GROUP BY M100_YEAR                                                \n");
		sb.append("                ,M100_DATE                                                \n");
		sb.append("                ,M100_ACCCODE                                             \n");
		sb.append("                ,M100_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M100_YEAR      DAY_YEAR                                    \n");
		sb.append("              ,M100_DATE      DAY_DATE                                    \n");
		sb.append("              ,('A')          DAY_ACCTYPE                                 \n");
		sb.append("              ,('11')         DAY_ACCCODE                                 \n");
		sb.append("              ,('1110100')    DAY_SEMOKCODE                               \n");
		sb.append("              ,('00000')      DAY_PARTCODE                                \n");
		sb.append("              ,('110000')     DAY_SUNAPGIGWANCODE                         \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,SUM(M100_ALLOTAMT) BAEJUNG_AMT                             \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('3') DATA_GB                                              \n");
		sb.append("        FROM   M100_MONEYALLOT_T                                          \n");  //자금배정 TOTAL
		sb.append("        WHERE  M100_YEAR = ?                                              \n");
		sb.append("        AND    M100_DATE = ?                                              \n");
		sb.append("        AND    M100_ALLOTCODE > 0                                         \n");
		sb.append("        GROUP BY M100_YEAR                                                \n");
		sb.append("                ,M100_DATE                                                \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M330_YEAR       DAY_YEAR                                   \n");
		sb.append("              ,M330_DATE       DAY_DATE                                   \n");
		sb.append("              ,M330_ACCGUBUN   DAY_ACCTYPE                                \n");
		sb.append("              ,M330_ACCCODE    DAY_ACCCODE                                \n");
		sb.append("              ,('1110100')     DAY_SEMOKCODE                              \n");
		sb.append("              ,M330_PARTCODE   DAY_PARTCODE                               \n");
		sb.append("              ,('110000')      DAY_SUNAPGIGWANCODE                        \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(SUM(DECODE(M330_ALLOTGUBUN, '1', M330_TAMT, 0)) +		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '2', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '3', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '4', M330_TAMT, 0))) BAEJUNG_JAE_AMT	\n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('4') DATA_GB                                              \n");
		sb.append("        FROM   M330_MONEYALLOTMANUAL_T                                    \n");  //자금배정수기분(수령액)
		sb.append("        WHERE  M330_YEAR = ?                                              \n");
		sb.append("        AND    M330_DATE = ?                                             \n");
		sb.append("        GROUP BY M330_YEAR                                                \n");
		sb.append("                ,M330_DATE                                                \n");
		sb.append("                ,M330_ACCGUBUN                                            \n");
		sb.append("                ,M330_ACCCODE                                             \n");
		sb.append("                ,M330_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M330_YEAR         DAY_YEAR                                 \n");
		sb.append("              ,M330_DATE         DAY_DATE                                 \n");
		sb.append("              ,M330_ACCGUBUN     DAY_ACCTYPE                              \n");
		sb.append("              ,M330_ORGACCCODE   DAY_ACCCODE                              \n");
		sb.append("              ,('1110100')       DAY_SEMOKCODE                            \n");
		sb.append("              ,M330_ORGPARTCODE  DAY_PARTCODE                             \n");
		sb.append("              ,('110000')        DAY_SUNAPGIGWANCODE                      \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(SUM(DECODE(M330_ALLOTGUBUN, '1', M330_TAMT, 0)) +		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '2', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '3', M330_TAMT, 0)) -		 \n");
		sb.append("                SUM(DECODE(M330_ALLOTGUBUN, '4', M330_TAMT, 0))) BAEJUNG_AMT 	\n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT                                        \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('4') DATA_GB                                              \n");
		sb.append("        FROM   M330_MONEYALLOTMANUAL_T                                    \n");  //자금배정수기분(배정액)
		sb.append("        WHERE  M330_YEAR = ?                                              \n");
		sb.append("        AND    M330_DATE = ?                                              \n");
		sb.append("        GROUP BY M330_YEAR                                                \n");
		sb.append("                ,M330_DATE                                                \n");
		sb.append("                ,M330_ACCGUBUN                                            \n");
		sb.append("                ,M330_ORGACCCODE                                          \n");
		sb.append("                ,M330_ORGPARTCODE                                         \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT M240_YEAROVER      DAY_YEAR                                \n");
		sb.append("              ,M240_DATE          DAY_DATE                                \n");
		sb.append("              ,M240_ACCTYPE       DAY_ACCTYPE                             \n");
		sb.append("              ,M240_ACCCODE       DAY_ACCCODE                             \n");
		sb.append("              ,M240_SEMOKCODE     DAY_SEMOKCODE                           \n");
		sb.append("              ,M240_PARTCODE      DAY_PARTCODE                            \n");
		sb.append("              ,('110000')         DAY_SUNAPGIGWANCODE                     \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,SUM(M240_AMT) ING_AMT                                      \n");
		sb.append("              ,(0) JAN_JUNG_AMT                                           \n");
		sb.append("              ,(0) JAN_JUNG_ETC                                           \n");
        sb.append("              ,(0) LOAN_AMT                                               \n");
		sb.append("              ,('5') DATA_GB                                              \n");
		sb.append("        FROM   M240_SURPLUSINTODETAILS_T                                  \n");  //잉여금
		sb.append("        WHERE  M240_YEAROVER = ?                                          \n");
		sb.append("        AND    M240_DATE     = ?                                          \n");
		sb.append("        GROUP BY M240_YEAROVER                                            \n");
		sb.append("                ,M240_DATE                                                \n");
		sb.append("                ,M240_ACCTYPE                                             \n");
		sb.append("                ,M240_ACCCODE                                             \n");
		sb.append("                ,M240_SEMOKCODE                                           \n");
		sb.append("                ,M240_PARTCODE                                            \n");
		sb.append("        UNION ALL                                                         \n");
		sb.append("        SELECT  M170_YEAR       DAY_YEAR                                  \n");
		sb.append("               ,M170_DATE       DAY_DATE                                  \n");
		sb.append("               ,M170_ACCTYPE    DAY_ACCTYPE                               \n");
		sb.append("               ,M170_ACCCODE    DAY_ACCCODE                               \n");
		sb.append("               ,('9999900')     DAY_SEMOKCODE                             \n");
		sb.append("               ,M170_PARTCODE   DAY_PARTCODE                              \n");
		sb.append("               ,('110000')      DAY_SUNAPGIGWANCODE                       \n");
		sb.append("              ,(0) SEIP_AMT                                               \n");
		sb.append("              ,(0) SEIP_GWAO_AMT                                          \n");
		sb.append("              ,(0) SEIP_JUNGJUNG_AMT                                      \n");
		sb.append("              ,(0) PASTSEIP_AMT                                           \n");
		sb.append("              ,(0) SECHUL_AMT                                             \n");
		sb.append("              ,(0) SECHUL_BAN_AMT                                         \n");
		sb.append("              ,(0) SECHUL_JUNGJUNG_AMT                                    \n");
		sb.append("              ,(0) BAEJUNG_AMT                                            \n");
		sb.append("              ,(0) BAEJUNG_JAE_AMT										 \n");
		sb.append("              ,(0) ING_AMT                                                \n");
		sb.append("              ,SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0)) JAN_JUNG_AMT        \n");
		sb.append("              ,(SUM(NVL(M170_MMDABEFOREDAYJANAMT,0)) +                    \n");
		sb.append("                SUM(NVL(M170_JEUNGGAMAMT,0)) +                            \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_1,0)) +                      \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_2,0)) +                      \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_3,0)) +                      \n");
		sb.append("                SUM(NVL(M170_DEPOSITBEFOREDAYJANAMT_4,0)) +                      \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_1,0)) +                          \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_2,0)) +                          \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_3,0)) +                          \n");
		sb.append("                SUM(NVL(M170_DEPOSITJEUNGGAMAMT_4,0)) +                          \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_1,0)) +                              \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_2,0)) +                              \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_3,0)) +                              \n");
		sb.append("                SUM(NVL(M170_RPBEFOREDAYAMT_4,0)) +                              \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_1,0)) +                               \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_2,0)) +                               \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_3,0)) +                               \n");
		sb.append("                SUM(NVL(M170_RPJEUNGGAMAMT_4,0)) +                               \n");
		sb.append("                SUM(NVL(M170_LOANBEFOREDAYJANAMT,0)) +                           \n");
		sb.append("                SUM(NVL(M170_LOANJEUNGGAMAMT,0))) JAN_JUNG_ETC                   \n");
		sb.append("              ,(SUM(NVL(M170_LOANBEFOREDAYJANAMT,0))+SUM(NVL(M170_LOANJEUNGGAMAMT,0))) LOAN_AMT \n");
		sb.append("              ,('6') DATA_GB                                              \n");
		sb.append("        FROM    M170_JANECKJANG_T                                         \n");  //잔액장
		sb.append("        WHERE   M170_YEAR = ?                                             \n");
		sb.append("        AND     M170_DATE = ?                                             \n");
        sb.append("        AND     M170_ACCTYPE IN ('A','B','E')                             \n");
		sb.append("        GROUP BY M170_YEAR                                                \n");
		sb.append("                ,M170_DATE                                                \n");
		sb.append("                ,M170_ACCTYPE                                             \n");
		sb.append("                ,M170_ACCCODE                                             \n");
		sb.append("                ,M170_PARTCODE                                            \n");
		sb.append("       ) X                                                                \n");
		sb.append("GROUP BY DAY_YEAR                                                         \n");
		sb.append("        ,DAY_DATE                                                         \n");
		sb.append("        ,DAY_ACCTYPE                                                      \n");
		sb.append("        ,DAY_ACCCODE                                                      \n");
		sb.append("        ,DAY_SEMOKCODE                                                    \n");
		sb.append("        ,DAY_PARTCODE                                                     \n");
		sb.append("        ,DAY_SUNAPGIGWANCODE                                              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.getList(conn, parameters);
	}


	/* 세입세출외현금 - 오늘일자 마감자료 추출 */
	public static List<CommonEntity> getSeipSechulTodayMagamList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT M040_YEAR            \n");
		sb.append("      ,M040_DATE            \n");
		sb.append("      ,M040_ACCCODE         \n");
		sb.append("      ,M040_PARTCODE        \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BOJJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BOJBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BOJGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BOGJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BOGBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BOGGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_JABJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_JABBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_JABGONG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) IN_BUGJUNG_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) IN_BUGBYUL_AMT          \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) IN_BUGGONG_AMT          \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOJGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BOGGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_JABGONG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGJUNG_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGBYUL_AMT   \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G1' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) IN_JUNG_BUGGONG_AMT   \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BOJJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BOJBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BOJGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BOGJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BOGBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BOGGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_JABJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_JABBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_JABGONG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) OUT_BUGJUNG_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) OUT_BUGBYUL_AMT         \n");
		sb.append("      ,SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I1' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) OUT_BUGGONG_AMT         \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C1' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOJGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C2' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BOGGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C3' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_JABGONG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D1' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGJUNG_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D2' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGBYUL_AMT  \n");
		sb.append("      ,(SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I2' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END) +                      \n");
		sb.append("        SUM(CASE WHEN M040_DWTYPE='G2' AND M040_INTYPE='I3' AND M040_CASHTYPE='C4' AND M040_DEPOSITTYPE='D3' THEN  M040_AMT ELSE 0 END)) OUT_JUNG_BUGGONG_AMT  \n");
		sb.append("FROM   M040_TAXCASH_T      \n");
		sb.append("WHERE  M040_YEAR = ?       \n");
		sb.append("AND    M040_DATE = ?       \n");
		sb.append("GROUP BY M040_YEAR         \n");
		sb.append("        ,M040_DATE         \n");
		sb.append("        ,M040_ACCCODE      \n");
		sb.append("        ,M040_PARTCODE     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));
		return template.getList(conn, parameters);
	}

	/* 오늘일자 일계 INSERT */
	public static int setTodayMagamInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

	    sb.append("INSERT INTO M220_DAY_T              \n");
		sb.append("(   M220_YEAR                       \n");
		sb.append("	  ,M220_DATE                       \n");
		sb.append("	  ,M220_ACCTYPE                    \n");
		sb.append("	  ,M220_ACCCODE                    \n");
		sb.append("	  ,M220_SEMOKCODE                  \n");
		sb.append("	  ,M220_PARTCODE                   \n");
		sb.append("	  ,M220_SUNAPGIGWANCODE            \n");
		sb.append("	  ,M220_AMTSEIPJEONILTOT           \n");
		sb.append("	  ,M220_AMTSEIP                    \n");
		sb.append("	  ,M220_AMTGWAONAPJEONILTOT        \n");
		sb.append("	  ,M220_AMTSEIPGWAONAP             \n");
		sb.append("	  ,M220_AMTSEIPJEONGJEONG          \n");
		sb.append("	  ,M220_AMTPASTSEIPJEONILTOT       \n");
		sb.append("	  ,M220_AMTPASTSEIP                \n");
		sb.append("	  ,M220_AMTSECHULJEONILTOT         \n");
		sb.append("	  ,M220_AMTSECHUL                  \n");
		sb.append("	  ,M220_AMTSECHULBANNAP            \n");
		sb.append("	  ,M220_AMTSECHULJEONGJEONG        \n");
		sb.append("	  ,M220_AMTBAJEONGJEONILTOT        \n");
		sb.append("	  ,M220_AMTBAJEONG                 \n");
		sb.append("	  ,M220_AMTBAJEONGSUJEONILTOT      \n");
		sb.append("	  ,M220_AMTBAJEONGSURYUNG          \n");
		sb.append("	  ,M220_AMTSURPLUSJEONILTOT        \n");
		sb.append("	  ,M220_AMTSURPLUS                 \n");
		sb.append("	  ,M220_AMTJEONGGI                 \n");
		sb.append("	  ,M220_AMTGONGGEUM                \n");
		sb.append("	  ,M220_AMTLOAN                    \n");
		sb.append(")                                   \n");
		sb.append(" VALUES                             \n");
		sb.append("(   ?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,0                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append("	  ,?                               \n");
		sb.append(")                                   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("DAY_YEAR"));
		parameters.setString(idx++, paramInfo.getString("DAY_DATE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCTYPE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_SEMOKCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_PARTCODE"));
        parameters.setString(idx++, paramInfo.getString("DAY_SUNAPGIGWANCODE"));
		parameters.setString(idx++, paramInfo.getString("SEIP_AMT"));
		parameters.setString(idx++, paramInfo.getString("SEIP_GWAO_AMT"));
		parameters.setString(idx++, paramInfo.getString("SEIP_JUNGJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("PASTSEIP_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_BAN_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_JUNGJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("BAEJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("BAEJUNG_JAE_AMT"));
		parameters.setString(idx++, paramInfo.getString("ING_AMT"));
		parameters.setString(idx++, paramInfo.getString("JAN_JUNG_ETC"));
		parameters.setString(idx++, paramInfo.getString("JAN_JUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("LOAN_AMT"));

		return template.insert(conn, parameters);
	}



	/* 세입세출외현금 - 오늘일자 일계 INSERT */
	public static int setSeipSechulTodayMagamInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M270_TAXCASHDAY_T     \n");
		sb.append("(   M270_YEAR                     \n");
		sb.append("   ,M270_DATE                     \n");
		sb.append("   ,M270_ACCCODE                  \n");
		sb.append("   ,M270_PARTCODE                 \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBJEON     \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBJEON     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBJEON    \n");
		sb.append("   ,M270_BOGWANJEONGGIIBJEON      \n");
		sb.append("   ,M270_BOGWANBYULDANIBJEON      \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBJEON     \n");
		sb.append("   ,M270_JABJONGJEONGGIIBJEON     \n");
		sb.append("   ,M270_JABJONGBYULDANIBJEON     \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBJEON    \n");
		sb.append("   ,M270_BUGASEJEONGGIIBJEON      \n");
		sb.append("   ,M270_BUGASEBYULDANIBJEON      \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBJEON     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBGEUM    \n");
		sb.append("   ,M270_BOGWANJEONGGIIBGEUM      \n");
		sb.append("   ,M270_BOGWANBYULDANIBGEUM      \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBGEUM     \n");
		sb.append("   ,M270_JABJONGJEONGGIIBGEUM     \n");
		sb.append("   ,M270_JABJONGBYULDANIBGEUM     \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBGEUM    \n");
		sb.append("   ,M270_BUGASEJEONGGIIBGEUM      \n");
		sb.append("   ,M270_BUGASEBYULDANIBGEUM      \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBGEUM     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBJEONG   \n");
		sb.append("   ,M270_BOGWANJEONGGIIBJEONG     \n");
		sb.append("   ,M270_BOGWANBYULDANIBJEONG     \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBJEONG    \n");
		sb.append("   ,M270_JABJONGJEONGGIIBJEONG    \n");
		sb.append("   ,M270_JABJONGBYULDANIBJEONG    \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBJEONG   \n");
		sb.append("   ,M270_BUGASEJEONGGIIBJEONG     \n");
		sb.append("   ,M270_BUGASEBYULDANIBJEONG     \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBJEONG    \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIJEON     \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIJEON     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIJEON    \n");
		sb.append("   ,M270_BOGWANJEONGGIJIJEON      \n");
		sb.append("   ,M270_BOGWANBYULDANJIJEON      \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIJEON     \n");
		sb.append("   ,M270_JABJONGJEONGGIJIJEON     \n");
		sb.append("   ,M270_JABJONGBYULDANJIJEON     \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIJEON    \n");
		sb.append("   ,M270_BUGASEJEONGGIJIJEON      \n");
		sb.append("   ,M270_BUGASEBYULDANJIJEON      \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIJEON     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIGEUB    \n");
		sb.append("   ,M270_BOGWANJEONGGIJIGEUB      \n");
		sb.append("   ,M270_BOGWANBYULDANJIGEUB      \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIGEUB     \n");
		sb.append("   ,M270_JABJONGJEONGGIJIGEUB     \n");
		sb.append("   ,M270_JABJONGBYULDANJIGEUB     \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIGEUB    \n");
		sb.append("   ,M270_BUGASEJEONGGIJIGEUB      \n");
		sb.append("   ,M270_BUGASEBYULDANJIGEUB      \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIGEUB     \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIJEONG    \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIJEONG    \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIJEONG   \n");
		sb.append("   ,M270_BOGWANJEONGGIJIJEONG     \n");
		sb.append("   ,M270_BOGWANBYULDANJIJEONG     \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIJEONG    \n");
		sb.append("   ,M270_JABJONGJEONGGIJIJEONG    \n");
		sb.append("   ,M270_JABJONGBYULDANJIJEONG    \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIJEONG   \n");
		sb.append("   ,M270_BUGASEJEONGGIJIJEONG     \n");
		sb.append("   ,M270_BUGASEBYULDANJIJEONG     \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIJEONG    \n");
		sb.append(")  VALUES                         \n");
		sb.append("( 	    ?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,0   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append("  	   ,?   					\n");
		sb.append(")								\n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("M040_YEAR"));
		parameters.setString(idx++, paramInfo.getString("M040_DATE"));
		parameters.setString(idx++, paramInfo.getString("M040_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("M040_PARTCODE"));
        parameters.setString(idx++, paramInfo.getString("IN_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGGONG_AMT"));

		return template.insert(conn, parameters);
	}


	/* 오늘일자 일계 UPDATE */
	public static int setTodayMagamUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("UPDATE M220_DAY_T                          \n");
		sb.append("SET    M220_AMTSEIP                = ?     \n");
		sb.append("	     ,M220_AMTSEIPGWAONAP         = ?     \n");
		sb.append("	     ,M220_AMTSEIPJEONGJEONG      = ?     \n");
		sb.append("	     ,M220_AMTPASTSEIP            = ?     \n");
		sb.append("	     ,M220_AMTSECHUL              = ?     \n");
		sb.append("	     ,M220_AMTSECHULBANNAP        = ?     \n");
		sb.append("	     ,M220_AMTSECHULJEONGJEONG    = ?     \n");
		sb.append("	     ,M220_AMTBAJEONG             = ?     \n");
		sb.append("	     ,M220_AMTBAJEONGSURYUNG      = ?     \n");
		sb.append("	     ,M220_AMTSURPLUS             = ?     \n");
		sb.append("	     ,M220_AMTJEONGGI             = ?     \n");
		sb.append("	     ,M220_AMTGONGGEUM            = ?     \n");
		sb.append("	     ,M220_AMTLOAN                = ?     \n");
	  sb.append("WHERE  M220_YEAR                   = ?     \n");
		sb.append("AND    M220_DATE                   = ?     \n");
		sb.append("AND    M220_ACCTYPE                = ?     \n");
		sb.append("AND    M220_ACCCODE                = ?     \n");
		sb.append("AND    M220_SEMOKCODE              = ?     \n");
		sb.append("AND    M220_PARTCODE               = ?     \n");
		sb.append("AND    M220_SUNAPGIGWANCODE        = ?     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("SEIP_AMT"));
		parameters.setString(idx++, paramInfo.getString("SEIP_GWAO_AMT"));
		parameters.setString(idx++, paramInfo.getString("SEIP_JUNGJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("PASTSEIP_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_BAN_AMT"));
		parameters.setString(idx++, paramInfo.getString("SECHUL_JUNGJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("BAEJUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("BAEJUNG_JAE_AMT"));
		parameters.setString(idx++, paramInfo.getString("ING_AMT"));
		parameters.setString(idx++, paramInfo.getString("JAN_JUNG_ETC"));
		parameters.setString(idx++, paramInfo.getString("JAN_JUNG_AMT"));
		parameters.setString(idx++, paramInfo.getString("LOAN_AMT"));
		parameters.setString(idx++, paramInfo.getString("DAY_YEAR"));
		parameters.setString(idx++, paramInfo.getString("DAY_DATE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCTYPE"));
		parameters.setString(idx++, paramInfo.getString("DAY_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_SEMOKCODE"));
		parameters.setString(idx++, paramInfo.getString("DAY_PARTCODE"));
        parameters.setString(idx++, paramInfo.getString("DAY_SUNAPGIGWANCODE"));

		return template.update(conn, parameters);
	}


	/* 세입세출외현금 - 오늘일자 일계 UPDATE */
	public static int setSeipSechulTodayMagamUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append("UPDATE M270_TAXCASHDAY_T             \n");
		sb.append("SET M270_BOJEUNGJEONGGIIBGEUM   = ?  \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBGEUM   = ?  \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBGEUM  = ?  \n");
		sb.append("   ,M270_BOGWANJEONGGIIBGEUM    = ?  \n");
		sb.append("   ,M270_BOGWANBYULDANIBGEUM    = ?  \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBGEUM   = ?  \n");
		sb.append("   ,M270_JABJONGJEONGGIIBGEUM   = ?  \n");
		sb.append("   ,M270_JABJONGBYULDANIBGEUM   = ?  \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBGEUM  = ?  \n");
		sb.append("   ,M270_BUGASEJEONGGIIBGEUM    = ?  \n");
		sb.append("   ,M270_BUGASEBYULDANIBGEUM    = ?  \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBGEUM   = ?  \n");
		sb.append("   ,M270_BOJEUNGJEONGGIIBJEONG  = ?  \n");
		sb.append("   ,M270_BOJEUNGBYULDANIBJEONG  = ?  \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMIBJEONG = ?  \n");
		sb.append("   ,M270_BOGWANJEONGGIIBJEONG   = ?  \n");
		sb.append("   ,M270_BOGWANBYULDANIBJEONG   = ?  \n");
		sb.append("   ,M270_BOGWANGONGGEUMIBJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGJEONGGIIBJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGBYULDANIBJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGGONGGEUMIBJEONG = ?  \n");
		sb.append("   ,M270_BUGASEJEONGGIIBJEONG   = ?  \n");
		sb.append("   ,M270_BUGASEBYULDANIBJEONG   = ?  \n");
		sb.append("   ,M270_BUGASEGONGGEUMIBJEONG  = ?  \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIGEUB   = ?  \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIGEUB   = ?  \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIGEUB  = ?  \n");
		sb.append("   ,M270_BOGWANJEONGGIJIGEUB    = ?  \n");
		sb.append("   ,M270_BOGWANBYULDANJIGEUB    = ?  \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIGEUB   = ?  \n");
		sb.append("   ,M270_JABJONGJEONGGIJIGEUB   = ?  \n");
		sb.append("   ,M270_JABJONGBYULDANJIGEUB   = ?  \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIGEUB  = ?  \n");
		sb.append("   ,M270_BUGASEJEONGGIJIGEUB    = ?  \n");
		sb.append("   ,M270_BUGASEBYULDANJIGEUB    = ?  \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIGEUB   = ?  \n");
		sb.append("   ,M270_BOJEUNGJEONGGIJIJEONG  = ?  \n");
		sb.append("   ,M270_BOJEUNGBYULDANJIJEONG  = ?  \n");
		sb.append("   ,M270_BOJEUNGGONGGEUMJIJEONG = ?  \n");
		sb.append("   ,M270_BOGWANJEONGGIJIJEONG   = ?  \n");
		sb.append("   ,M270_BOGWANBYULDANJIJEONG   = ?  \n");
		sb.append("   ,M270_BOGWANGONGGEUMJIJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGJEONGGIJIJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGBYULDANJIJEONG  = ?  \n");
		sb.append("   ,M270_JABJONGGONGGEUMJIJEONG = ?  \n");
		sb.append("   ,M270_BUGASEJEONGGIJIJEONG   = ?  \n");
		sb.append("   ,M270_BUGASEBYULDANJIJEONG   = ?  \n");
		sb.append("   ,M270_BUGASEGONGGEUMJIJEONG  = ?  \n");
		sb.append("WHERE  M270_YEAR      = ?            \n");
		sb.append("AND    M270_DATE      = ?            \n");
		sb.append("AND    M270_ACCCODE   = ?            \n");
		sb.append("AND    M270_PARTCODE  = ?            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("IN_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("IN_JUNG_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_BUGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOJGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BOGGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_JABGONG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGJUNG_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGBYUL_AMT"));
        parameters.setString(idx++, paramInfo.getString("OUT_JUNG_BUGGONG_AMT"));
		parameters.setString(idx++, paramInfo.getString("M040_YEAR"));
		parameters.setString(idx++, paramInfo.getString("M040_DATE"));
		parameters.setString(idx++, paramInfo.getString("M040_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("M040_PARTCODE"));

		return template.update(conn, parameters);
	}


	/* --일일마감 TABLE 마감처리 */
	public static int setdailyMagamUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M210_WORKEND_T          \n");
		sb.append("SET    M210_WORKENDSTATE = 'Y' \n");
		sb.append("WHERE  M210_YEAR = ?           \n");
		sb.append("AND    M210_DATE = ?           \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.update(conn, parameters);
	}


	/* --일계 테이블 삭제 */
	public static int M220DayMagamDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE M220_DAY_T          \n");
		sb.append("WHERE  M220_YEAR = ?       \n");
		sb.append("AND    M220_DATE = ?       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.delete(conn, parameters);
	}


	/* --세입세출외현금 테이블 삭제 */
	public static int M270TaxCashDayMagamDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE M270_TAXCASHDAY_T   \n");
		sb.append("WHERE  M270_YEAR = ?       \n");
		sb.append("AND    M270_DATE = ?       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.delete(conn, parameters);
	}


	/* --일일마감 TABLE 해제처리 */
	public static int M210WorkEndMagamUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M210_WORKEND_T          \n");
		sb.append("SET    M210_WORKENDSTATE = 'N' \n");
		sb.append("WHERE  M210_YEAR = ?           \n");
		sb.append("AND    M210_DATE = ?           \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
		parameters.setString(idx++, paramInfo.getString("accyear"));
		parameters.setString(idx++, paramInfo.getString("accdate"));

		return template.update(conn, parameters);
	}
}
