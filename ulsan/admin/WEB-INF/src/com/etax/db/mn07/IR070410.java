/***********************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR070410.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 일계/보고서 > 일일비고등록
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070410 {

    /* 전영업일 조회 */ 
	public static CommonEntity getAgoDate(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  COUNT(*) CNT        \n");
		sb.append("   FROM  M470_DAYBIGO_T      \n");   
        sb.append("  WHERE  M470_YEAR = ?       \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }
  
	/* 일일비고 조회 */ 
	public static CommonEntity getDateNote(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  *					\n");
		sb.append("   FROM  M470_DAYBIGO_T 		\n");   
		sb.append("  WHERE  M470_DATE = ?		\n");
        sb.append("    AND  M470_YEAR = ?       \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }


    /* 전 영업일 비고 조회 */ 
	public static CommonEntity getAgoNote1(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT ? AS M470_YEAR                                \n");
        sb.append("      , ? AS M470_DATE                                \n");
        sb.append("      , 0 AS M470_CAR_GC01                            \n");
        sb.append("      , 0 AS M470_CAR_ICHE_GC01                       \n");
        sb.append("      , 0 AS M470_CAR_SUM_GC01                        \n");
        sb.append("      , ? AS M470_CAR_AGODATE_GC01                    \n");
        sb.append("      , 0 AS M470_CAR_GC02                            \n");
        sb.append("      , 0 AS M470_CAR_ICHE_GC02                       \n");
        sb.append("      , 0 AS M470_CAR_SUM_GC02                        \n");
        sb.append("      , ? AS M470_CAR_AGODATE_GC02                    \n");
        sb.append("      , 0 AS M470_NONG_GC01                           \n");
        sb.append("      , 0 AS M470_NONG_ICHE_GC01                      \n");
        sb.append("      , 0 AS M470_NONG_SUM_GC01                       \n");
        sb.append("      , ? AS M470_NONG_AGODATE_GC01                   \n");
        sb.append("      , 0 AS M470_NONG_GC02                           \n");
        sb.append("      , 0 AS M470_NONG_ICHE_GC02                      \n");
        sb.append("      , 0 AS M470_NONG_SUM_GC02                       \n");
        sb.append("      , ? AS M470_NONG_AGODATE_GC02                   \n");
        sb.append("      , 0 AS M470_BAL_GP01                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GP01                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GP01                        \n");
        sb.append("      , GET_NEXT_BUSINESSDAY(?) M470_BAL_AGODATE_GP01 \n");
        sb.append("      , 0 AS M470_BAL_GC01                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GC01                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GC01                        \n");
        sb.append("      , ? AS M470_BAL_AGODATE_GC01                    \n");
        sb.append("      , 0 AS M470_BAL_GC02                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GC02                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GC02                        \n");
        sb.append("      , ? AS M470_BAL_AGODATE_GC02                    \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_CD_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_NT_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_SUNAP                 \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_ICHE                  \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_SUM                   \n");
        sb.append("      , ? AS M470_BS_GUM_NOCARD_DATE                  \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_OARS_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_NARS_DATE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_OCARD_SUNAP                  \n"); //세외수입 카드
        sb.append("      , 0 AS M470_SS_GUM_OCARD_ICHE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_OCARD_SUM                    \n");
        sb.append("      , ? AS M470_SS_GUM_OCARD_DATE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_SUNAP                  \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_ICHE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_SUM                    \n");
        sb.append("      , ? AS M470_SS_GUM_NCARD_DATE                   \n");        
        sb.append("      , 0 AS M470_SS_GUM_OARS_SUNAP                   \n"); //세외수입 ARS
        sb.append("      , 0 AS M470_SS_GUM_OARS_ICHE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_OARS_SUM                     \n");
        sb.append("      , ? AS M470_SS_GUM_OARS_DATE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_ICHE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_SUM                     \n");
        sb.append("      , ? AS M470_SS_GUM_NARS_DATE                    \n");
        sb.append("   FROM DUAL                                          \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));//세외수입
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        
        return template.getData(conn, parameters);
    }


    /* 전 영업일 비고 조회 */ 
	public static CommonEntity getAgoNote(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT M470_YEAR                                        \n");
        sb.append("      , M470_DATE                                        \n");
        sb.append("      , M470_CAR_GC01                                    \n");
        sb.append("      , M470_CAR_ICHE_GC01                               \n");
        sb.append("      , M470_CAR_SUM_GC01                                \n");
        sb.append("      , M470_CAR_AGODATE_GC01                            \n");
        sb.append("      , M470_CAR_GC02                                    \n");
        sb.append("      , M470_CAR_ICHE_GC02                               \n");
        sb.append("      , M470_CAR_SUM_GC02                                \n");
        sb.append("      , M470_CAR_AGODATE_GC02                            \n");
        sb.append("      , M470_NONG_GC01                                   \n");
        sb.append("      , M470_NONG_ICHE_GC01                              \n");
        sb.append("      , M470_NONG_SUM_GC01                               \n");
        sb.append("      , M470_NONG_AGODATE_GC01                           \n");
        sb.append("      , M470_NONG_GC02                                   \n");
        sb.append("      , M470_NONG_ICHE_GC02                              \n");
        sb.append("      , M470_NONG_SUM_GC02                               \n");
        sb.append("      , M470_NONG_AGODATE_GC02                           \n");
        sb.append("      , M470_BAL_GP01                                    \n");
        sb.append("      , M470_BAL_ICHE_GP01                               \n");
        sb.append("      , M470_BAL_SUM_GP01                                \n");
        sb.append("      , GET_NEXT_BUSINESSDAY(?) M470_BAL_AGODATE_GP01    \n");
        sb.append("      , M470_BAL_GC01                                    \n");
        sb.append("      , M470_BAL_ICHE_GC01                               \n");
        sb.append("      , M470_BAL_SUM_GC01                                \n");
        sb.append("      , M470_BAL_AGODATE_GC01                            \n");
        sb.append("      , M470_BAL_GC02                                    \n");
        sb.append("      , M470_BAL_ICHE_GC02                               \n");
        sb.append("      , M470_BAL_SUM_GC02                                \n");
        sb.append("      , M470_BAL_AGODATE_GC02                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_CD_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUM                             \n");
        sb.append("      , M470_CD_GUM_CARD_DATE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_NT_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUM                             \n");
        sb.append("      , M470_NT_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUM                             \n");
        sb.append("      , M470_BS_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUNAP                         \n");
        sb.append("      , M470_BS_GUM_NOCARD_ICHE                          \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUM                           \n");
        sb.append("      , M470_BS_GUM_NOCARD_DATE                          \n");
        sb.append("      , M470_BS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_NARS_DATE                            \n");        
        sb.append("      , M470_SS_GUM_OCARD_SUNAP                  		\n"); //세외수입 카드
        sb.append("      , M470_SS_GUM_OCARD_ICHE                   		\n");
        sb.append("      , M470_SS_GUM_OCARD_SUM                    		\n");
        sb.append("      , M470_SS_GUM_OCARD_DATE                   		\n");
        sb.append("      , M470_SS_GUM_NCARD_SUNAP                  		\n");
        sb.append("      , M470_SS_GUM_NCARD_ICHE                   		\n");
        sb.append("      , M470_SS_GUM_NCARD_SUM                    		\n");
        sb.append("      , M470_SS_GUM_NCARD_DATE                   		\n");        
        sb.append("      , M470_SS_GUM_OARS_SUNAP                   		\n"); //세외수입 ARS
        sb.append("      , M470_SS_GUM_OARS_ICHE                    		\n");
        sb.append("      , M470_SS_GUM_OARS_SUM                     		\n");
        sb.append("      , M470_SS_GUM_OARS_DATE                    		\n");
        sb.append("      , M470_SS_GUM_NARS_SUNAP                   		\n");
        sb.append("      , M470_SS_GUM_NARS_ICHE                    		\n");
        sb.append("      , M470_SS_GUM_NARS_SUM                     		\n");
        sb.append("      , M470_SS_GUM_NARS_DATE                    		\n");
        sb.append("   FROM M470_DAYBIGO_T                                   \n");
        sb.append("  WHERE M470_DATE = GET_AGO_BUSINESSDAY(?)               \n");
        sb.append("    AND M470_YEAR = ?                                    \n");
        
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }

	/* 일일비고 등록  */
    public static int insertNote(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M470_DAYBIGO_T                              \n");
        sb.append("      ( M470_YEAR                                        \n");
        sb.append("      , M470_DATE                                        \n");
        sb.append("      , M470_CAR_GC01                                    \n");
        sb.append("      , M470_CAR_ICHE_GC01                               \n");
        sb.append("      , M470_CAR_SUM_GC01                                \n");
        sb.append("      , M470_CAR_AGODATE_GC01                            \n");
        sb.append("      , M470_CAR_GC02                                    \n");
        sb.append("      , M470_CAR_ICHE_GC02                               \n");
        sb.append("      , M470_CAR_SUM_GC02                                \n");
        sb.append("      , M470_CAR_AGODATE_GC02                            \n");
        sb.append("      , M470_NONG_GC01                                   \n");
        sb.append("      , M470_NONG_ICHE_GC01                              \n");
        sb.append("      , M470_NONG_SUM_GC01                               \n");
        sb.append("      , M470_NONG_AGODATE_GC01                           \n");
        sb.append("      , M470_NONG_GC02                                   \n");
        sb.append("      , M470_NONG_ICHE_GC02                              \n");
        sb.append("      , M470_NONG_SUM_GC02                               \n");
        sb.append("      , M470_NONG_AGODATE_GC02                           \n");
        sb.append("      , M470_BAL_GP01                                    \n");
        sb.append("      , M470_BAL_ICHE_GP01                               \n");
        sb.append("      , M470_BAL_SUM_GP01                                \n");
        sb.append("      , M470_BAL_AGODATE_GP01                            \n");
        sb.append("      , M470_BAL_GC01                                    \n");
        sb.append("      , M470_BAL_ICHE_GC01                               \n");
        sb.append("      , M470_BAL_SUM_GC01                                \n");
        sb.append("      , M470_BAL_AGODATE_GC01                            \n");
        sb.append("      , M470_BAL_GC02                                    \n");
        sb.append("      , M470_BAL_ICHE_GC02                               \n");
        sb.append("      , M470_BAL_SUM_GC02                                \n");
        sb.append("      , M470_BAL_AGODATE_GC02                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_CD_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUM                             \n");
        sb.append("      , M470_CD_GUM_CARD_DATE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_NT_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUM                             \n");
        sb.append("      , M470_NT_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUM                             \n");
        sb.append("      , M470_BS_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUNAP                         \n");
        sb.append("      , M470_BS_GUM_NOCARD_ICHE                          \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUM                           \n");
        sb.append("      , M470_BS_GUM_NOCARD_DATE                          \n");
        sb.append("      , M470_BS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_NARS_DATE                            \n");
        sb.append("      , M470_SS_GUM_OCARD_SUNAP                          \n"); //세외수입
        sb.append("      , M470_SS_GUM_OCARD_ICHE                           \n");
        sb.append("      , M470_SS_GUM_OCARD_SUM                            \n");
        sb.append("      , M470_SS_GUM_OCARD_DATE                           \n");
        sb.append("      , M470_SS_GUM_NCARD_SUNAP                          \n");
        sb.append("      , M470_SS_GUM_NCARD_ICHE                           \n");
        sb.append("      , M470_SS_GUM_NCARD_SUM                            \n");
        sb.append("      , M470_SS_GUM_NCARD_DATE                           \n");
        sb.append("      , M470_SS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_SS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_SS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_SS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_SS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_SS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_SS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_SS_GUM_NARS_DATE )                          \n");
        sb.append(" VALUES                                                  \n");
        sb.append("      ( ?                                                \n");  //회계연도
        sb.append("      , ?                                                \n");  //회계일자
        sb.append("      , ?                                                \n");  //차량취득세 카드 당일수납액
        sb.append("      , ?                                                \n");  //차량취득세 카드 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //차량취득세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //차량취득세 카드 이체예정일
        sb.append("      , ?                                                \n");  //차량취득세 카드외 당일수납액
        sb.append("      , ?                                                \n");  //차량취득세 카드외 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //차량취득세 금결원 외 카드 합계
        sb.append("      , ?                                                \n");  //차량취득세 카드외 이체예정일
        sb.append("      , ?                                                \n");  //차량농특세 카드 당일수납액
        sb.append("      , ?                                                \n");  //차량농특세 카드 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //차량농특세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //차량농특세 카드 이체예정일
        sb.append("      , ?                                                \n");  //차량농특세 카드외 당일수납액
        sb.append("      , ?                                                \n");  //차량농특세 카드외 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //차량농특세 금결원 외 카드 합계
        sb.append("      , ?                                                \n");  //차량농특세 카드외 이체예정일
        sb.append("      , ?                                                \n");  //구군청발행시세 일반 당일수납액
        sb.append("      , ?                                                \n");  //구군청발행시세 일반 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //구군청발행시세 금결원 일반 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 일반 이체예정일
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 당일수납액
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //구군청발행시세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 이체예정일
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 당일수납액
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 당일이체액
        sb.append("      , ? + ? - ? -- 전일누계+수납-이체                  \n");  //구군청발행시세 금결원 외 카드 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 이체예정일
        sb.append("      , ?                                                \n");  //차량취득세 카드 당일수납액
        sb.append("      , ?                                                \n");  //차량취득세 카드 당일이체액
        sb.append("      , ? + ? - ?                                        \n");  //차량취득세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //차량취득세 카드 이체예정일
        sb.append("      , ?                                                \n");  //차량농특세 카드 당일수납액 
        sb.append("      , ?                                                \n");  //차량농특세 카드 당일이체액 
        sb.append("      , ? + ? - ?                                        \n");  //차량농특세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //차량농특세 카드 이체예정일
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 당일수납액
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 당일이체액 
        sb.append("      , ? + ? - ?                                        \n");  //구군청발행시세 금결원 카드 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 카드 이체예정일
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 당일수납액  
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 당일이체액  
        sb.append("      , ? + ? - ?                                        \n");  //구군청발행시세 금결원 외 카드 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 카드외 이체예정일  
        sb.append("      , ?                                                \n");  //구군청발행시세 OARS 당일수납액  
        sb.append("      , ?                                                \n");  //구군청발행시세 OARS 당일이체액  
        sb.append("      , ? + ? - ?                                        \n");  //구군청발행시세 OARS 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 OARS 이체예정일  
        sb.append("      , ?                                                \n");  //구군청발행시세 NARS 당일수납액  
        sb.append("      , ?                                                \n");  //구군청발행시세 NARS 당일이체액  
        sb.append("      , ? + ? - ?                                        \n");  //구군청발행시세 NARS 합계
        sb.append("      , ?                                                \n");  //구군청발행시세 NARS 이체예정일        
        sb.append("      , ?                                                \n");  //세외수입 카드 당일수납액
        sb.append("      , ?                                                \n");  //세외수입 카드 당일이체액 
        sb.append("      , ? + ? - ?                                        \n");  //세외수입 금결원 카드 합계
        sb.append("      , ?                                                \n");  //세외수입 카드 이체예정일
        sb.append("      , ?                                                \n");  //세외수입 카드 당일수납액
        sb.append("      , ?                                                \n");  //세외수입 카드 당일이체액 
        sb.append("      , ? + ? - ?                                        \n");  //세외수입 금결원 카드 합계
        sb.append("      , ?                                                \n");  //세외수입 카드 이체예정일
        sb.append("      , ?                                                \n");  //세외수입 OARS 당일수납액  
        sb.append("      , ?                                                \n");  //세외수입 OARS 당일이체액  
        sb.append("      , ? + ? - ?                                        \n");  //세외수입 OARS 합계
        sb.append("      , ?                                                \n");  //세외수입 OARS 이체예정일  
        sb.append("      , ?                                                \n");  //세외수입 NARS 당일수납액  
        sb.append("      , ?                                                \n");  //세외수입 NARS 당일이체액  
        sb.append("      , ? + ? - ?                                        \n");  //세외수입 NARS 합계
        sb.append("      , ? )                                              \n");  //세외수입 NARS 이체예정일  
		


		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		parameters.setString(idx++, paramInfo.getString("M470_CAR_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_CAR_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGODATE_GC01"));

		parameters.setString(idx++, paramInfo.getString("M470_CAR_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_CAR_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGODATE_GC01"));

        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GP01"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GP01"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GC01"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_DATE"));
        
        //세외수입 카드
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_DATE"));
        //세외수입 카드
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_DATE"));
        //세외수입 ars
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_DATE"));
        //세외수입 ars
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_DATE"));
	
		return template.insert(conn, parameters);
	}

	/* 일일비고 삭제 */
	public static int deleteNote(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M470_DAYBIGO_T      \n");
		sb.append(" WHERE M470_DATE = ?            \n");
        sb.append("   AND M470_YEAR = ?            \n");
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		
		return template.delete(conn, parameters);
	}

}