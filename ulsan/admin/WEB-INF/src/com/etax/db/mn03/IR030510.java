/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030510.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 주행세 조회/수정/삭제
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030510 {

	/* 수기분 조회 */
	public static List<CommonEntity> getJuheangList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT  M060_YEAR,                                                      \n");	 
		sb.append("	        M060_DATE,                                                      \n"); 
		sb.append("	        M060_SEQ,                                                       \n");
		sb.append("	        M060_CASHTYPE,                                                  \n"); 
		sb.append("        DECODE(M060_JINGSUTYPE, 'J1','주된특별징수',                     \n");
		sb.append("                                'J2','특별징수') M060_JINGSUNAME,        \n");
		sb.append("        DECODE(M060_REPAYTYPE,  'R1','원금',                             \n");
		sb.append("                                'R2','이자') M060_REPAYNAME,             \n");
        sb.append("        DECODE(M060_CASHTYPE,  'A1','수입액',                            \n");
		sb.append("                               'A2','과오납',                            \n");
        sb.append("                               'A3','지급액',                            \n");
        sb.append("                               'A4','반납액') M060_CASHNAME,             \n");
		sb.append("				 CASE WHEN M060_CASHTYPE = 'A1'                             \n");
		sb.append("							THEN M060_AMT                                   \n");
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END SUIP_AMT                                               \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A2'                             \n");
		sb.append("				      THEN M060_AMT                                         \n");
		sb.append("				      ELSE 0                                                \n");
		sb.append("				 END GWAO_AMT                                               \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A3'                             \n");
		sb.append("							THEN M060_AMT                                   \n");
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END JIGUP_AMT                                              \n"); 
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A4'                             \n");
		sb.append("							THEN M060_AMT                                   \n"); 
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END BANNAP_AMT,                                            \n");
		sb.append("         M060_AMT, 								                        \n");	 
		sb.append("       	M060_NAPSEJA, 						                            \n");	
        sb.append("         M060_JINGSUTYPE, 								                \n");	 
		sb.append("       	M060_REPAYTYPE   						                        \n");	 	 
		sb.append("   FROM  M060_JUHEANG_T                                                  \n");
		sb.append("  WHERE  M060_DATE BETWEEN ? AND ?                                       \n");																							 
		if (!"".equals(paramInfo.getString("napseja"))){
		    sb.append("	 AND  M060_NAPSEJA LIKE ?                                           \n");																									 
		}
		sb.append("  ORDER BY M060_DATE, M060_SEQ                                           \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	    int i = 1;

        parameters.setString(i++, paramInfo.getString("start_date"));
	    parameters.setString(i++, paramInfo.getString("end_date"));
		if(!"".equals(paramInfo.getString("napseja")) ) {
            parameters.setString(i++, "%"+paramInfo.getString("napseja")+"%");
        }
	
		return template.getList(conn, parameters);
	}

	/* 주행세 삭제 */ 
    public static int deleteJuheang(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append(" DELETE FROM M060_JUHEANG_T   \n");
        sb.append("  WHERE M060_SEQ = ?          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("seq"));

        return template.delete(conn, parameters);
    }
	
	/* 영수필통지서 상세 */
	public static CommonEntity getYoungsuView(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append(" SELECT  M060_YEAR,                                    \n");	 
		sb.append("	        M060_DATE,                                    \n"); 
		sb.append("	        M060_SEQ,                                     \n"); 
		sb.append("	        M060_CASHTYPE,                                \n");
		sb.append("	        M060_JINGSUTYPE,                              \n");
		sb.append("				 CASE WHEN M060_CASHTYPE = 'A1'                 \n");
		sb.append("							THEN M060_AMT                             \n");
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END SUIP_AMT                                   \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A2'                 \n");
		sb.append("				      THEN M060_AMT                             \n");
		sb.append("				      ELSE 0                                    \n");
		sb.append("				 END GWAO_AMT                                   \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A3'                 \n");
		sb.append("							THEN M060_AMT                             \n");
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END JIGUP_AMT                                  \n"); 
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A4'                 \n");
		sb.append("							THEN M060_AMT                             \n"); 
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END BANNAP_AMT,                                \n");
		sb.append("         M060_AMT, 								                    \n");	 
		sb.append("       	M060_NAPSEJA  						                    \n");	 
		sb.append("   FROM  M060_JUHEANG_T                                \n");
		sb.append("  WHERE  M060_SEQ = ?	                                \n");
		 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	    int i = 1;

	    parameters.setString(i++, paramInfo.getString("seq"));
	
		return template.getData(conn, parameters);
	}
	
	/* 주행세 수정 */
	public static int juheangUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M060_JUHEANG_T                    \n");
		sb.append("   SET M060_YEAR = ?,                    \n");	
		sb.append("	      M060_DATE = ?,                    \n"); 	 
	    sb.append("	      M060_JINGSUTYPE = ?,              \n");   
		sb.append("	      M060_REPAYTYPE = ?, 				\n");	 
		sb.append("       M060_CASHTYPE = ?, 				\n");	 
		sb.append("       M060_AMT = ?, 					\n");	 
		sb.append("	      M060_NAPSEJA = ?  				\n");	
		sb.append(" WHERE M060_SEQ = ? 	                    \n");
	

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
		parameters.setString(i++, paramInfo.getString("seq"));

		return template.update(conn, parameters);
	}


  /* 최대일자 구하기 */
	public static CommonEntity getMaxDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT MAX(M070_DATE) M070_DATE  \n");
		sb.append("  FROM M070_JUHANGDAY_T          \n");		

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
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
    sb.append("  WHERE M070_DATE = (SELECT MAX(M070_DATE) M070_DATE             \n");
    sb.append("                       FROM M070_JUHANGDAY_T                     \n");
    sb.append("                      WHERE M070_DATE < ?  )                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}


    /* 주행세일계테이블 수정(입력 당일 이후)  */
    public static int updateRoopData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                                  \n");
        sb.append("   SET M070_SPREDAYAMT = ?                               \n");
        sb.append("      ,M070_SPREDAYINTERESTAMT = ?                       \n");
        sb.append("      ,M070_PREDAYAMT = ?                                \n");
        sb.append("      ,M070_PREDAYINTERESTAMT = ?                        \n");
		sb.append("      ,M070_SPREDAYTOTALCNT = ?                          \n");
		sb.append(" 	 ,M070_SPREDAYTOTALAMT = ?                          \n");
		sb.append(" 	 ,M070_SPREDAYTOTALINTEREST = ?                     \n");
		sb.append(" 	 ,M070_PREDAYTOTALCNT = ?                           \n");
		sb.append(" 	 ,M070_PREDAYTOTALAMT = ?                           \n");
		sb.append(" 	 ,M070_PREDAYTOTALINTEREST = ?                      \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALCNT = ?                    \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALAMT = ?                    \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALINTERES = ?                \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALCNT = ?                     \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALAMT = ?                     \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALINTEREST = ?                \n");
        sb.append(" WHERE M070_DATE = ?                                     \n"); 

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


  /* 당일 건수 필드의 합 */
	public static CommonEntity getTotCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT (M070_DAYSUIPSUMCNT +           \n");
    sb.append("       M070_DAYGWAONAPSUMCNT +         \n");
    sb.append("       M070_SDAYSUIPSUMCNT +           \n");
    sb.append("       M070_SDAYGWAONAPSUMCNT +        \n");
    sb.append("       M070_DAYJIGUPSUMCNT +           \n");
    sb.append("       M070_DAYBANNAPSUMCNT +          \n");
    sb.append("       M070_SDAYJIGUPSUMCNT +          \n");
    sb.append("       M070_SDAYBANNAPSUMCNT) TOT_CNT  \n");
		sb.append("  FROM M070_JUHANGDAY_T                \n");	
		sb.append(" WHERE M070_DATE = ? 	                \n");
	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

    parameters.setString(i++, paramInfo.getString("max_date"));

		return template.getData(conn, parameters);
	}


    /* 주행세 일계 수정 */ 
    public static int updateData(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append("UPDATE M070_JUHANGDAY_T                                                                        \n");
		sb.append("   SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" - ?           \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
		    sb.append("    ," + paramInfo.getString("cnt_name") + " = " + paramInfo.getString("cnt_name") + " - 1 \n");
        }
        sb.append(" WHERE M070_DATE = ?                                                                           \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("input_amt"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.update(conn, parameters);
    }
  /* 특별징수 주행세 수납부 조회 */
	public static List<CommonEntity> getSpSunapList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

   sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','주된특별징수 수납부',           \n");
   sb.append("                            'J2','특별징수 수납부') M060_JINGSUNAME, \n");
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','원금',                          \n");
   sb.append("                           'R2','이자') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT,                                                \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE                                                \n");
   sb.append("  FROM M060_JUHEANG_T                                                \n");
   sb.append(" WHERE SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = ?                                           \n");
   sb.append("   AND M060_REPAYTYPE ='R1'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
    parameters.setString(i++, paramInfo.getString("M060_JINGSUTYPE"));
	
		return template.getList(conn, parameters);
	}
  
	 /* 특별징수 발생이자 수납부 조회 */
	public static List<CommonEntity> getSpIjaList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

   sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','주된특별징수 수납부',           \n");
   sb.append("                            'J2','특별징수 수납부') M060_JINGSUNAME, \n");
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','원금',                          \n");
   sb.append("                           'R2','이자') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT,                                                \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE                                                \n");
   sb.append("  FROM M060_JUHEANG_T                                                \n");
   sb.append(" WHERE SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = ?                                           \n");
   sb.append("   AND M060_REPAYTYPE ='R2'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
	      
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
    parameters.setString(i++, paramInfo.getString("M060_JINGSUTYPE"));
	
		return template.getList(conn, parameters);
	}

/* 주된특별징수 수납부 조회 */
	public static List<CommonEntity> getJuSunapList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
     
	 sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','주된특별징수 수납부',           \n");
   sb.append("                            'J2','특별징수 수납부') M060_JINGSUNAME, \n"); 
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','원금',                          \n");
   sb.append("                           'R2','이자') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");      
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT                                                 \n");            
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");      
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE,                                               \n");
   sb.append("  FROM M060_JUHEANG_T A                                              \n");
   sb.append(" WHERE M060_YEAR = ?                                                 \n"); 
   sb.append("   AND SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = 'J1'                                        \n");
   sb.append("   AND M060_REPAYTYPE ='R1'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
	
		return template.getList(conn, parameters);
	}

/* 주된특별징수 발생이자 조회 */
	public static List<CommonEntity> getJuIjaList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
     
	 sb.append("SELECT A.M060_YEAR,                                                    \n");
   sb.append("        A.M060_DATE,                                                   \n");
   sb.append("        A.M060_SEQ,                                                    \n");
   sb.append("        A.M060_CASHTYPE,                                               \n");
   sb.append(" DECODE(A.M060_JINGSUTYPE, 'J1','주된특별징수 수납부',                 \n");
   sb.append("                            'J2','특별징수 수납부') M060_JINGSUNAME,   \n"); 
   sb.append(" DECODE(A.M060_REPAYTYPE,  'R1','원금',                                \n");
   sb.append("                           'R2','이자') M060_REPAYNAME,                \n");
   sb.append(" CASE  WHEN A.M060_CASHTYPE = 'A1'                                     \n");       
   sb.append("       THEN A.M060_AMT                                                 \n");
   sb.append("       WHEN A.M060_CASHTYPE = 'A2'                                     \n");
   sb.append("       THEN -A.M060_AMT                                                \n");
   sb.append("  END R2SUIP_AMT                                                       \n");
   sb.append("  ,CASE WHEN A.M060_CASHTYPE IN ('A3', 'A4')                           \n");
   sb.append("      THEN A.M060_AMT                                                  \n");
   sb.append("      ELSE 0                                                           \n");
   sb.append("  END R2JIGUP_AMT,                                                     \n");
   sb.append("        A.M060_AMT,                                                    \n");
   sb.append("        A.M060_NAPSEJA,                                                \n");
   sb.append("        A.M060_JINGSUTYPE,                                             \n");
   sb.append("        A.M060_REPAYTYPE,                                              \n");
   sb.append("        B.M070_SPREMONTHAMT,                                           \n");
   sb.append("        B.M070_SPREMONTHINTERESTAMT                                    \n");
   sb.append("  FROM  M060_JUHEANG_T A                                               \n");
   sb.append("       ,M070_JUHANGDAY_T B                                             \n");
   sb.append(" WHERE  M060_YEAR = ?                                                  \n"); 
   sb.append("   AND  M060_YEAR = M070_YEAR                                          \n");
   sb.append("   AND  M060_DATE = M070_DATE                                          \n");
   sb.append("   AND SUBSTR(M060_DATE, 1, 6) = ?                                     \n");
   sb.append("   AND M060_DATE <= ?                                                  \n");
   sb.append("   AND  M060_JINGSUTYPE = 'J1'                                         \n");
   sb.append("   AND  A.M060_REPAYTYPE ='R2'                                         \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                          \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
	
		return template.getList(conn, parameters);
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


  /* 일계 자료 존재 여부 */
  public static CommonEntity getEndCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) CNT              \n");
    sb.append("  FROM M070_JUHANGDAY_T          \n");
    sb.append(" WHERE M070_DATE = ?             \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
  
    int i = 1;
		parameters.setString(i++, paramInfo.getString("end_date"));

		return template.getData(conn, parameters);
	}


    /* 일계표 */
    public static CommonEntity getJuhangDayView(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT M070_YEAR, M070_DATE                                                      \n");  //회계년도, 일자
        sb.append("     , M070_SPREDAYAMT                                                           \n");  //특별전일금액
        sb.append("     , M070_SPREDAYINTERESTAMT                                                   \n");  //특별전일이자
        sb.append("     , (M070_SPREDAYAMT + M070_SPREDAYINTERESTAMT) SP_JAN                        \n");  //특별전일잔액
        sb.append("     , M070_PREDAYAMT                                                            \n");  //주된전일금액
        sb.append("     , M070_PREDAYINTERESTAMT                                                    \n");  //주된전일이자
        sb.append("     , (M070_PREDAYAMT + M070_PREDAYINTERESTAMT) BS_JAN                          \n");  //주된전일잔액
        sb.append("     , (M070_SPREDAYTOTALCNT + M070_SPREDAYDIVIDETOTALCNT) SP_CNT                \n");  //특별전일건수
        sb.append("     , (M070_PREDAYTOTALCNT + M070_PREDAYDIVIDETOTALCNT) BS_CNT                  \n");  //주된전일건수
        sb.append("     , M070_SPREDAYTOTALCNT                                                      \n");  //특별전일징수건수
        sb.append("     , M070_SPREDAYTOTALAMT                                                      \n");  //특별전일징수금액
        sb.append("     , M070_SPREDAYTOTALINTEREST                                                 \n");  //특별전일징수이자
        sb.append("     , (M070_SPREDAYTOTALAMT + M070_SPREDAYTOTALINTEREST) SP_JEON_NU             \n");  //특별전일징수소계
        sb.append("     , M070_PREDAYTOTALCNT                                                       \n");  //주된전일징수건수
        sb.append("     , M070_PREDAYTOTALAMT                                                       \n");  //주된전일징수금액
        sb.append("     , M070_PREDAYTOTALINTEREST                                                  \n");  //주된전일징수이자
        sb.append("     , (M070_PREDAYTOTALAMT + M070_PREDAYTOTALINTEREST) BS_JEON_NU               \n");  //주된전일징수소계
        sb.append("     , M070_SDAYSUIPSUMCNT                                                       \n");  //특별당일징수건수
        sb.append("     , M070_SDAYSUIPSUMAMT                                                       \n");  //특별당일징수금액
        sb.append("     , M070_SDAYSUIPSUMINTEREST                                                  \n");  //특별당일징수이자
        sb.append("     , (M070_SDAYSUIPSUMAMT + M070_SDAYSUIPSUMINTEREST) SP_DANG_NU               \n");  //특별당일소계
        sb.append("     , M070_DAYSUIPSUMCNT                                                        \n");  //주된당일징수건수
        sb.append("     , M070_DAYSUIPSUMAMT                                                        \n");  //주된당일징수금액
        sb.append("     , M070_DAYSUIPSUMINTEREST                                                   \n");  //주된당일징수이자
        sb.append("     , (M070_DAYSUIPSUMAMT + M070_DAYSUIPSUMINTEREST) BS_DANG_NU                 \n");  //주된당일소계
        sb.append("     , M070_SDAYGWAONAPSUMCNT                                                    \n");  //특별과오납건수
        sb.append("     , M070_SDAYGWAONAPSUMAMT                                                    \n");  //특별과오납금액
        sb.append("     , M070_SDAYGWAONAPSUMINTEREST                                               \n");  //특별과오납이자
        sb.append("     , (M070_SDAYGWAONAPSUMAMT + M070_SDAYGWAONAPSUMINTEREST) SP_GWAO_NU         \n");  //특별과오납소계
        sb.append("     , M070_DAYGWAONAPSUMCNT                                                     \n");  //주된과오납건수
        sb.append("     , M070_DAYGWAONAPSUMAMT                                                     \n");  //주된과오납금액
        sb.append("     , M070_DAYGWAONAPSUMINTEREST                                                \n");  //주된과오납이자
        sb.append("     , (M070_DAYGWAONAPSUMAMT + M070_DAYGWAONAPSUMINTEREST) BS_GWAO_NU           \n");  //주된과오납소계
        sb.append("     , M070_SPREDAYDIVIDETOTALCNT                                                \n");  //특별전일배분건수
        sb.append("     , M070_SPREDAYDIVIDETOTALAMT                                                \n");  //특별전일배분금액
        sb.append("     , M070_SPREDAYDIVIDETOTALINTERES                                            \n");  //특별전일배분이자
        sb.append("     , (M070_SPREDAYDIVIDETOTALAMT + M070_SPREDAYDIVIDETOTALINTERES) SP_JEON_BAE \n");  //특별전일배분소계
        sb.append("     , M070_PREDAYDIVIDETOTALCNT                                                 \n");  //주된전일배분건수
        sb.append("     , M070_PREDAYDIVIDETOTALAMT                                                 \n");  //주된전일배분금액
        sb.append("     , M070_PREDAYDIVIDETOTALINTEREST                                            \n");  //주된전일배분이자
        sb.append("     , (M070_PREDAYDIVIDETOTALAMT + M070_PREDAYDIVIDETOTALINTEREST) BS_JEON_BAE  \n");  //주된전일배분소계
        sb.append("     , M070_SDAYJIGUPSUMCNT                                                      \n");  //특별당일배분건수
        sb.append("     , M070_SDAYJIGUPSUMAMT                                                      \n");  //특별당일배분금액
        sb.append("     , M070_SDAYJIGUPSUMINTEREST                                                 \n");  //특별당일배분이자
        sb.append("     , (M070_SDAYJIGUPSUMAMT + M070_SDAYJIGUPSUMINTEREST) SP_DANG_BAE            \n");  //특별당일배분소계
        sb.append("     , M070_DAYJIGUPSUMCNT                                                       \n");  //주된당일배분건수
        sb.append("     , M070_DAYJIGUPSUMAMT                                                       \n");  //주된당일배분금액
        sb.append("     , M070_DAYJIGUPSUMINTEREST                                                  \n");  //주된당일배분이자
        sb.append("     , (M070_DAYJIGUPSUMAMT + M070_DAYJIGUPSUMINTEREST) BS_DANG_BAE              \n");  //주된당일배분소계
        sb.append("     , M070_SDAYBANNAPSUMCNT                                                     \n");  //특별반납배분건수
        sb.append("     , M070_SDAYBANNAPSUMAMT                                                     \n");  //특별반납배분금액
        sb.append("     , M070_SDAYBANNAPSUMINTEREST                                                \n");  //특별반납배분이자
        sb.append("     , (M070_SDAYBANNAPSUMAMT + M070_SDAYBANNAPSUMINTEREST) SP_BAN_BAE           \n");  //특별반납배분소계
        sb.append("     , M070_DAYBANNAPSUMCNT                                                      \n");  //주된반납배분건수
        sb.append("     , M070_DAYBANNAPSUMAMT                                                      \n");  //주된반납배분금액
        sb.append("     , M070_DAYBANNAPSUMINTEREST                                                 \n");  //주된반납배분이자
        sb.append("     , (M070_DAYBANNAPSUMAMT + M070_DAYBANNAPSUMINTEREST) BS_BAN_BAE             \n");  //주된반납배분소계
        sb.append("  FROM M070_JUHANGDAY_T                                                          \n");
        sb.append(" WHERE M070_DATE = ?                                                             \n");
		
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int i = 1;

        parameters.setString(i++, paramInfo.getString("end_date"));
		
        return template.getData(conn, parameters);
    }


  /* 일계표 */
	public static CommonEntity getNothingView(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT M070_YEAR, M070_DATE                                                      \n");  //회계년도, 일자
    sb.append("      ,(M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT           \n");  
    sb.append("     - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT             \n");  //특별전일잔액(금액)
    sb.append("      ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");  
    sb.append("     - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT    \n");  //특별전일잔액(이자)
    sb.append("      ,((M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT          \n");  
    sb.append("     - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT)                             \n"); 
    sb.append("     + (M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");  
    sb.append("     - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST)) SP_JAN           \n");  //특별전일잔액(소계)
    sb.append("      ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT              \n");  
    sb.append("     - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                \n");  //주된전일잔액(금액)
    sb.append("      ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST \n");  
    sb.append("     - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT   \n");  //주된전일이자
    sb.append("      ,((M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT             \n");  
    sb.append("     - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT)                               \n");  
    sb.append("     + (M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST \n");  
    sb.append("     - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST)) BS_JAN             \n");  //주된전일잔액(소계)
    sb.append("			 ,((M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT                              \n");
    sb.append("     + M070_SDAYGWAONAPSUMCNT)                                                   \n");   
    sb.append("			+ (M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT                        \n");
    sb.append("     + M070_SDAYBANNAPSUMCNT)) SP_CNT                                            \n");  //특별전일잔액건수
    sb.append("			 ,((M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT                                \n");
    sb.append("     + M070_DAYGWAONAPSUMCNT)                                                    \n");
    sb.append("			+ (M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT                          \n");
    sb.append("     + M070_DAYBANNAPSUMCNT)) BS_CNT                                             \n");  //주된전일잔액건수
    sb.append("      ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT                               \n");
    sb.append("     + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT                              \n");  //특별전일징수건수
    sb.append("      ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT                               \n");
    sb.append("     - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT                              \n");  //특별전일징수금액
    sb.append("		   ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST                     \n");
    sb.append("     - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST                    \n");  //특별전일징수이자
    sb.append("      ,((M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT                              \n");
    sb.append("     - M070_SDAYGWAONAPSUMAMT)                                                   \n");  
    sb.append("		  + (M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST                     \n");
    sb.append("     - M070_SDAYGWAONAPSUMINTEREST)) SP_JEON_NU                                  \n");  //특별전일징수소계
    sb.append("      ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT                                 \n");
    sb.append("     + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT                                \n");  //주된전일징수건수
    sb.append("      ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT                                 \n");
    sb.append("     - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT                                \n");  //주된전일징수금액
    sb.append("		   ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST                       \n");
    sb.append("     - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST                      \n");  //주된전일징수이자
    sb.append("      ,((M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT                                \n");
    sb.append("     - M070_DAYGWAONAPSUMAMT)                                                    \n");  
    sb.append("		  + (M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST                       \n");
    sb.append("     - M070_DAYGWAONAPSUMINTEREST)) BS_JEON_NU                                   \n");  //주된전일징수소계
    sb.append("			 ,0 M070_SDAYSUIPSUMCNT                                                     \n");  //특별당일징수건수
    sb.append("			 ,0 M070_SDAYSUIPSUMAMT                                                     \n");  //특별당일징수금액
    sb.append("			 ,0 M070_SDAYSUIPSUMINTEREST                                                \n");  //특별당일징수이자
    sb.append("			 ,0 SP_DANG_NU                                                              \n");  //특별당일소계
    sb.append("			 ,0 M070_DAYSUIPSUMCNT                                                      \n");  //주된당일징수건수
    sb.append("			 ,0 M070_DAYSUIPSUMAMT                                                      \n");  //주된당일징수금액
    sb.append("			 ,0 M070_DAYSUIPSUMINTEREST                                                 \n");  //주된당일징수이자
    sb.append("			 ,0 BS_DANG_NU                                                              \n");  //주된당일소계
    sb.append("			 ,0 M070_SDAYGWAONAPSUMCNT                                                  \n");  //특별과오납건수
    sb.append("      ,0 M070_SDAYGWAONAPSUMAMT                                                  \n");  //특별과오납금액
    sb.append("      ,0 M070_SDAYGWAONAPSUMINTEREST                                             \n");  //특별과오납이자
    sb.append("      ,0 SP_GWAO_NU                                                              \n");  //특별과오납소계
    sb.append("			 ,0 M070_DAYGWAONAPSUMCNT                                                   \n");  //주된과오납건수
    sb.append("      ,0 M070_DAYGWAONAPSUMAMT                                                   \n");  //주된과오납금액
    sb.append("      ,0 M070_DAYGWAONAPSUMINTEREST                                              \n");  //주된과오납이자
    sb.append("			 ,0 BS_GWAO_NU                                                              \n");  //주된과오납소계
    sb.append("      ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT                        \n");
    sb.append("     + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT                         \n");  //특별전일배분건수
    sb.append("      ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT                        \n");
    sb.append("     - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT                         \n");  //특별전일배분금액
    sb.append("      ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST               \n");
    sb.append("     - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES                \n");  //특별전일배분이자
    sb.append("      ,((M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT                       \n");
    sb.append("     - M070_SDAYBANNAPSUMAMT)                                                    \n");
    sb.append("     + (M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST               \n");
    sb.append("     - M070_SDAYBANNAPSUMINTEREST)) SP_JEON_BAE                                  \n");  //특별전일배분소계
    sb.append("      ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT                          \n");
    sb.append("     + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT                           \n");  //주된전일배분건수
    sb.append("      ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT                          \n");
    sb.append("     - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT                           \n");  //주된전일배분금액
    sb.append("      ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST                \n");
    sb.append("     - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST                 \n");  //주된전일배분이자
    sb.append("      ,((M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT                         \n");
    sb.append("     - M070_DAYBANNAPSUMAMT)                                                     \n");  
    sb.append("     + (M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST                \n");
    sb.append("     - M070_DAYBANNAPSUMINTEREST)) BS_JEON_BAE                                   \n");  //주된전일배분소계
    sb.append("			 ,0 M070_SDAYJIGUPSUMCNT                                                    \n");  //특별당일배분건수
    sb.append("      ,0 M070_SDAYJIGUPSUMAMT                                                    \n");  //특별당일배분금액
    sb.append("      ,0 M070_SDAYJIGUPSUMINTEREST                                               \n");  //특별당일배분이자
    sb.append("      ,0 SP_DANG_BAE                                                             \n");  //특별당일배분소계
    sb.append("			 ,0 M070_DAYJIGUPSUMCNT                                                     \n");  //주된당일배분건수
    sb.append("      ,0 M070_DAYJIGUPSUMAMT                                                     \n");  //주된당일배분금액
    sb.append("      ,0 M070_DAYJIGUPSUMINTEREST                                                \n");  //주된당일배분이자
    sb.append("			 ,0 BS_DANG_BAE                                                             \n");  //주된당일배분소계
    sb.append("			 ,0 M070_SDAYBANNAPSUMCNT                                                   \n");  //특별반납배분건수
    sb.append("      ,0 M070_SDAYBANNAPSUMAMT                                                   \n");  //특별반납배분금액
    sb.append("      ,0 M070_SDAYBANNAPSUMINTEREST                                              \n");  //특별반납배분이자
    sb.append("      ,0 SP_BAN_BAE                                                              \n");  //특별반납배분소계
    sb.append("      ,0 M070_DAYBANNAPSUMCNT                                                    \n");  //주된반납배분건수
    sb.append("      ,0 M070_DAYBANNAPSUMAMT                                                    \n");  //주된반납배분금액
    sb.append("      ,0 M070_DAYBANNAPSUMINTEREST                                               \n");  //주된반납배분이자
    sb.append("      ,0 BS_BAN_BAE                                                              \n");  //주된반납배분소계
    sb.append("  FROM M070_JUHANGDAY_T                                                          \n");                          
    sb.append(" WHERE M070_DATE = (SELECT MAX(M070_DATE) FROM M070_JUHANGDAY_T                  \n");
    sb.append("                     WHERE M070_DATE < ?  )                                      \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
	  int i = 1;

	  parameters.setString(i++, paramInfo.getString("end_date"));
		
		return template.getData(conn, parameters);
	}


	/* 전월이월금 */
	public static CommonEntity getIwalData(Connection conn, CommonEntity paramInfo)throws SQLException {
	    StringBuffer sb = new StringBuffer();

        sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                    \n");
        sb.append("      - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) LAST_MONTH_AMT                       \n");
        sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");
        sb.append("      - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) LAST_MONTH_IJA             \n");
        sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                       \n");
        sb.append("      - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) BASE_MONTH_AMT                         \n");
        sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST     \n");
        sb.append("      - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) BASE_MONTH_IJA               \n");
        sb.append("   FROM M070_JUHANGDAY_T                                                                   \n");
        sb.append("  WHERE M070_DATE = (SELECT MAX(M070_DATE) M070_DATE                                       \n");
        sb.append("                       FROM M070_JUHANGDAY_T                                               \n");
        sb.append("                      WHERE M070_DATE < ? )                                                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;

		parameters.setString(i++, paramInfo.getString("first_date"));
	
		return template.getData(conn, parameters);
	}


	/* 특별주행세 MAX 일자 조회 */
	public static CommonEntity getMaxSunap(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE                                        \n");	
    sb.append("         ,SUM(M070_PREMONTHAMT) M070_PREMONTHAMT                          \n");	                     	
    sb.append("         ,SUM(M070_PREMONTHINTERESTAMT) M070_PREMONTHINTERESTAMT          \n");	                    
    sb.append("         ,SUM(M070_SPREMONTHAMT) M070_SPREMONTHAMT                        \n");	               	
    sb.append("         ,SUM(M070_SPREMONTHINTERESTAMT) M070_SPREMONTHINTERESTAMT        \n");	                   	                                
    sb.append("    FROM M070_JUHANGDAY_T                                                 \n");	
    sb.append("   WHERE M070_YEAR = ?   	                                               \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}
		/* 주된 주행세 MAX 일자 조회 */
	public static CommonEntity getJuMaxSunap(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE                                        \n");	
    sb.append("         ,SUM(M070_PREMONTHAMT) M070_PREMONTHAMT                          \n");	                     	
    sb.append("         ,SUM(M070_PREMONTHINTERESTAMT) M070_PREMONTHINTERESTAMT          \n");	                    
    sb.append("         ,SUM(M070_SPREMONTHAMT) M070_SPREMONTHAMT                        \n");	               	
    sb.append("         ,SUM(M070_SPREMONTHINTERESTAMT) M070_SPREMONTHINTERESTAMT        \n");	                   	                                
    sb.append("    FROM M070_JUHANGDAY_T                                                 \n");	
    sb.append("   WHERE M070_YEAR = ?   	                                               \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}


  /* 직인 가져오기 */
	public static CommonEntity getSealState(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
	
    sb.append(" SELECT M340_FNAME FROM M340_USERSEAL_T WHERE M340_CURRENTORGAN = '1'     \n");
      
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		return template.getData(conn);
	}
}