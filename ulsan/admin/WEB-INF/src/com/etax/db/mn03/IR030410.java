/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR030410.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-28
* 프로그램내용	   : 세입세출외현금 > 주행세등록
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030410 {


 /* 수기분등록  */
  public static int insertjuheang(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M060_JUHEANG_T                           \n");
		sb.append("(M060_YEAR, 											\n");	 
		sb.append(" M060_SEQ, 											\n");	
		sb.append(" M060_DATE, 											\n");	 
		sb.append("	M060_JINGSUTYPE,                                    \n");  
		sb.append("	M060_REPAYTYPE, 									\n");	 
		sb.append("	M060_CASHTYPE, 										\n");	 
		sb.append("	M060_AMT, 											\n");	 
		sb.append("	M060_NAPSEJA, 										\n");	 
		sb.append("	M060_LOGNO) 										\n");	 
		sb.append("VALUES	(?,M060_SEQ.NEXTVAL,?,?,?,?,?,?,?)          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
		parameters.setString(i++, paramInfo.getString("jingsuType"));
		parameters.setString(i++, paramInfo.getString("repayType"));
		parameters.setString(i++, paramInfo.getString("cashType"));
		parameters.setString(i++, paramInfo.getString("amt"));
		parameters.setString(i++, paramInfo.getString("napseja"));
		parameters.setString(i++, paramInfo.getString("log_no"));

		return template.insert(conn, parameters);
	}


  /* 회계일자 조회 */
  public static CommonEntity getFisDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(M070_DATE) M070_DATE FROM M070_JUHANGDAY_T  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}


  /* 회계일자(올해 최초일자) 조회 */
  public static CommonEntity getMinDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MIN(M070_DATE) M070_DATE  \n");
        sb.append("  FROM M070_JUHANGDAY_T          \n");
        sb.append(" WHERE M070_YEAR = ?             \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
  
    int i = 1;
		parameters.setString(i++, paramInfo.getString("max_date").substring(0,4));

		return template.getData(conn, parameters);
	}
  
   /* 주행세일계테이블 자료 건수  */
  public static CommonEntity juhaengCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) CNT FROM M070_JUHANGDAY_T  \n");
		sb.append(" WHERE M070_DATE = ?                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
    int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}

	/* 주행세일계테이블 등록  */
    public static int insertjuheangDay(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO M070_JUHANGDAY_T                             \n");
		sb.append("          (  M070_YEAR  ,M070_DATE	                      \n");
		sb.append("             ," + paramInfo.getString("col_name") + "    \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
            sb.append("             ," + paramInfo.getString("cnt_name") + "  \n");
        }
        sb.append("             ,M070_SPREDAYAMT                            \n");
        sb.append("             ,M070_SPREDAYINTERESTAMT                    \n");
        sb.append("             ,M070_PREDAYAMT                             \n");
        sb.append("             ,M070_PREDAYINTERESTAMT                     \n");
		sb.append(" 						,M070_SPREDAYTOTALCNT                       \n");
		sb.append(" 					  ,M070_SPREDAYTOTALAMT                       \n");
		sb.append(" 						,M070_SPREDAYTOTALINTEREST                  \n");
		sb.append(" 						,M070_PREDAYTOTALCNT                        \n");
		sb.append(" 						,M070_PREDAYTOTALAMT                        \n");
		sb.append(" 						,M070_PREDAYTOTALINTEREST                   \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALCNT                 \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALAMT                 \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALINTERES             \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALCNT                  \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALAMT                  \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALINTEREST             \n");
		sb.append("            )                                            \n");
		sb.append("      VALUES	(  ?,?,                                     \n");
	    sb.append("                ?                                        \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
            sb.append("               ,1                                      \n");
        }
        sb.append("                ,?,?,?,?,?                               \n"); 
		sb.append("                ,?,?,?,?,?                               \n"); 
		sb.append("                ,?,?,?,?,?                               \n");
		sb.append("                ,?                                       \n"); 
        sb.append("            )                                            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
        parameters.setString(i++, paramInfo.getString("amt"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYINTERESTAMT"));	
  	    parameters.setString(i++, paramInfo.getString("M070_PREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYINTERESTAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALCNT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALINTERES"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALINTEREST"));

		return template.insert(conn, parameters);
	}


    /* 주행세일계테이블 수정(입력 당일)  */
    public static int updatejuheangDay(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                                                                      \n");
		sb.append("   SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" + ?         \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
		    sb.append("      ," + paramInfo.getString("cnt_name") + " = " + paramInfo.getString("cnt_name") + " + 1 \n");
        }
        sb.append(" WHERE M070_DATE = ?                                                                         \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
        parameters.setString(i++, paramInfo.getString("amt"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
    
		return template.update(conn, parameters);
	}


    /* 주행세일계테이블 수정(입력 당일 이후)  */
    public static int updateRoopData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                              \n");
        sb.append("   SET M070_SPREDAYAMT = ?                           \n");
        sb.append("      ,M070_SPREDAYINTERESTAMT = ?                   \n");
        sb.append("      ,M070_PREDAYAMT = ?                            \n");
        sb.append("      ,M070_PREDAYINTERESTAMT = ?                    \n");
		sb.append(" 		 ,M070_SPREDAYTOTALCNT = ?                      \n");
		sb.append(" 		 ,M070_SPREDAYTOTALAMT = ?                      \n");
		sb.append(" 		 ,M070_SPREDAYTOTALINTEREST = ?                 \n");
		sb.append(" 		 ,M070_PREDAYTOTALCNT = ?                       \n");
		sb.append(" 		 ,M070_PREDAYTOTALAMT = ?                       \n");
		sb.append(" 		 ,M070_PREDAYTOTALINTEREST = ?                  \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALCNT = ?                \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALAMT = ?                \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALINTERES = ?            \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALCNT = ?                 \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALAMT = ?                 \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALINTEREST = ?            \n");
        sb.append(" WHERE M070_DATE = ?                                 \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;

		parameters.setString(i++, paramInfo.getString("M070_SPREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYINTERESTAMT"));	
  	    parameters.setString(i++, paramInfo.getString("M070_PREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYINTERESTAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALCNT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALINTERES"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALINTEREST"));
        parameters.setString(i++, paramInfo.getString("acc_date"));

		return template.update(conn, parameters);
	}

	
	/* 주행세 일계 MAX 일자 조회 */
	public static CommonEntity getMaxDate(Connection conn)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE   \n");	
        sb.append("    FROM M070_JUHANGDAY_T            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
	
		return template.getData(conn);
	}


    /* 회계일자 직전의 마지막 일 */
	public static CommonEntity getLastYear(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("  SELECT MAX(M070_DATE) M070_DATE   \n");	
        sb.append("    FROM M070_JUHANGDAY_T           \n");
        sb.append("   WHERE M070_DATE < ?              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
  
        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}


  /* 전일금액(최종자료) */
	public static CommonEntity getLastAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                    \n");
        sb.append("       - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT                     \n");	//특별전일잔액
        sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");
        sb.append("       - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT   \n");	//특별전일이자
        sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                       \n");
        sb.append("       - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                        \n"); //주된전일잔액
        sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST     \n");
        sb.append("       - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT      \n");	//주된전일이자
        sb.append("       ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT  \n"); //특별전일징수건수누계
        sb.append("       ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT  \n"); //특별전일징수금액누계
        sb.append("       ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST  \n"); //특별전일징수이자누계
        sb.append("       ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT      \n");	//주된전일징수건수누계
        sb.append("       ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT      \n");	//주된전일징수금액누계
        sb.append("       ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST      \n");	//주된전일징수이자누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT          \n");	//특별전일배분건수누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT          \n");  //특별전일배분금액누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES \n");	//특별전일배분이자누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT          \n");	//주된전일배분건수누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT          \n");  //주된전일배분금액누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST \n");	//주된전일배분이자누계
        sb.append("   FROM M070_JUHANGDAY_T            \n");
        sb.append("  WHERE M070_DATE = ?               \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;
		parameters.setString(i++, paramInfo.getString("max_date"));
	
		return template.getData(conn, parameters);
	}


    /* 전일금액(회계일자의 직전 영업일) */
	public static CommonEntity getLastNu(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                    \n");
        sb.append("       - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT                     \n");	//특별전일잔액
        sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");
        sb.append("       - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT   \n");	//특별전일이자
        sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                       \n");
        sb.append("       - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                        \n"); //주된전일잔액
        sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST     \n");
        sb.append("       - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT      \n");	//주된전일이자
        sb.append("       ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT  \n"); //특별전일징수건수누계
        sb.append("       ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT  \n"); //특별전일징수금액누계
        sb.append("       ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST  \n"); //특별전일징수이자누계
        sb.append("       ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT      \n");	//주된전일징수건수누계
        sb.append("       ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT      \n");	//주된전일징수금액누계
        sb.append("       ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST      \n");	//주된전일징수이자누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT          \n");	//특별전일배분건수누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT          \n");  //특별전일배분금액누계
        sb.append("       ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES \n");	//특별전일배분이자누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT          \n");	//주된전일배분건수누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT          \n");  //주된전일배분금액누계
        sb.append("       ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST \n");	//주된전일배분이자누계
        sb.append("   FROM M070_JUHANGDAY_T                                         \n");
        sb.append("  WHERE M070_DATE = GET_AGO_BUSINESSDAY(?)                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}

    /* 주행세일계 일자 리스트 */
	public static List<CommonEntity> getDayList(Connection conn,CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT M070_DATE                   \n");	
        sb.append("   FROM M070_JUHANGDAY_T            \n");
        sb.append("  WHERE M070_DATE > ?               \n");
        sb.append("  ORDER BY M070_DATE                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getList(conn, parameters);
	}
}
