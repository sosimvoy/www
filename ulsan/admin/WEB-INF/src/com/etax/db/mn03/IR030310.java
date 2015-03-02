/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030310.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-02
* 프로그램내용	   : 세입세출외현금 > 수입증지 불출현황
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030310 {
   
	 	/* 수입증지 조회 */
	public static List<CommonEntity> getStampList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT M050_YEAR          \n");
	    sb.append("       ,M050_DATE          \n");
	    sb.append("       ,M050_GUEONJONG     \n");
        sb.append("       ,M050_CREATE        \n");
	    sb.append("       ,M050_LDISUSE       \n");
	    sb.append("       ,M050_GUMGOSALE     \n");
	    sb.append("       ,M050_CITYSALE      \n");
	    sb.append("       ,M050_CITYDIVIDE    \n");
        sb.append("   FROM M050_TAXINSTAMP_T  \n");
        sb.append("  WHERE M050_YEAR = ?      \n");
        sb.append("    AND M050_DATE = ?      \n");
        sb.append("  ORDER BY M050_GUEONJONG  \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	    int i = 1;

        parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("date"));
	
		return template.getList(conn, parameters);
	}

	/* 상세 조회 */
	public static List<CommonEntity> getStampView(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M050_YEAR                                                                                                   \n");
        sb.append("       ,M050_DATE                                                                                                   \n");
        sb.append("       ,M050_GUEONJONG                                                                                              \n");
        sb.append("       ,M050_LASTYEAR                                                                                               \n");
        sb.append(" 			,(M050_CREATE + M050_TOTALCREATE) M050_TOTALCREATE                                                     \n");
        sb.append(" 			,(M050_LASTYEAR + M050_CREATE + M050_TOTALCREATE) SO_GYE                                               \n");
        sb.append("       ,(M050_LDISUSE + M050_TOTALDISUSE) M050_TOTALDISUSE                                                          \n");
        sb.append("       ,M050_GUMGOSALE                                                                                              \n");
        sb.append("       ,(M050_GUMGOSALE + M050_TOTALGUMGOSALE) M050_TOTALGUMGOSALE                                                  \n");
        sb.append("       ,M050_CITYSALE                                                                                               \n");
        sb.append("       ,(M050_CITYSALE + M050_TOTALCITYSALE) M050_TOTALCITYSALE                                                     \n");
        sb.append(" 			,(M050_GUMGOSALE + M050_CITYSALE) DANG_SO_GYE                                                          \n");
        sb.append("       ,(M050_GUMGOSALE + M050_TOTALGUMGOSALE + M050_CITYSALE + M050_TOTALCITYSALE) DANG_NU_GYE                     \n");
        sb.append("       ,M050_CITYDIVIDE                                                                                             \n");
        sb.append(" 			,M050_GUMGOREST                                                                                        \n");
        sb.append(" 			,M050_CITYREST                                                                                         \n");
        sb.append(" 			,(M050_GUMGOREST + M050_CITYREST) REST_AMT                                                             \n");
        sb.append(" 			,(M050_GUEONJONG * M050_LASTYEAR) TOT_LASTYEAR                                                         \n");
        sb.append(" 			,(M050_GUEONJONG * (M050_CREATE + M050_TOTALCREATE)) TOT_CREATE                                        \n");
        sb.append(" 			,(M050_GUEONJONG * (M050_LASTYEAR + M050_CREATE + M050_TOTALCREATE)) TOT_SO_GYE                        \n");
        sb.append(" 			,(M050_GUEONJONG *(M050_LDISUSE + M050_TOTALDISUSE)) TOT_LDISUSE                                       \n");
        sb.append(" 			,(M050_GUEONJONG * M050_GUMGOSALE) TOT_GUMGOSALE                                                       \n");
        sb.append(" 			,(M050_GUEONJONG * (M050_GUMGOSALE + M050_TOTALGUMGOSALE)) TOT_GUMGO_SUM                               \n");
        sb.append("       ,(M050_GUEONJONG * M050_CITYSALE) TOT_CITYSALE                                                               \n");
        sb.append("       ,(M050_GUEONJONG * (M050_CITYSALE + M050_TOTALCITYSALE)) TOT_CITY_SUM                                        \n");
        sb.append("       ,(M050_GUEONJONG * (M050_GUMGOSALE + M050_CITYSALE)) TOT_DANG_GYE                                            \n");
        sb.append("       ,(M050_GUEONJONG * (M050_GUMGOSALE + M050_TOTALGUMGOSALE + M050_CITYSALE + M050_TOTALCITYSALE)) TOT_DANG_NU  \n");
        sb.append("       ,(M050_GUEONJONG * M050_CITYDIVIDE) TOT_CITYDIVIDE                                                           \n");
        sb.append("       ,(M050_GUEONJONG * M050_GUMGOREST) TOT_GUMGOREST                                                             \n");
        sb.append("       ,(M050_GUEONJONG * M050_CITYREST) TOT_CITYREST                                                               \n");
        sb.append("       ,(M050_GUEONJONG * (M050_GUMGOREST + M050_CITYREST)) TOT_REST_AMT                                            \n");
        sb.append("   FROM M050_TAXINSTAMP_T                                                                                           \n");
        sb.append("  WHERE M050_DATE = ?                                                                                               \n");
        sb.append("  ORDER BY M050_GUEONJONG                                                                                           \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
    
		parameters.setString(i++, paramInfo.getString("date"));
	
		return template.getList(conn, parameters);
	}

	/* 수입증지 등록  */
  public static int insertStamp(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M050_TAXINSTAMP_T            \n");
  	sb.append("(           M050_YEAR, 									\n");	 
		sb.append("            M050_DATE, 									\n");	
		sb.append("            M050_GUEONJONG, 							\n");	 
		sb.append("	           M050_CREATE, 								\n");	 
		sb.append("	           M050_LDISUSE, 								\n");	 
		sb.append("            M050_GUMGOSALE, 							\n");	 
		sb.append("	           M050_CITYSALE, 							\n");	 
		sb.append("            M050_CITYDIVIDE,							\n");	 
		sb.append("	           M050_LOGNO, 							  	\n");	
		sb.append("            M050_TOTALCREATE,            \n");     	
 		sb.append("	           M050_TOTALDISUSE,            \n");    
    sb.append("            M050_TOTALGUMGOSALE,         \n");      
    sb.append("            M050_TOTALCITYSALE,          \n");     
    sb.append("            M050_GUMGOREST,              \n");     
    sb.append("            M050_CITYREST,               \n");
    sb.append("            M050_LASTYEAR                \n");    
		sb.append(") 														            \n");	 
		sb.append("VALUES	(    ?,                           \n");
		sb.append("            ?,                           \n");
		sb.append("						 ?,                           \n");
		sb.append("						 NVL(?,0),                    \n");
		sb.append("						 NVL(?,0),                    \n");
		sb.append("						 NVL(?,0),                    \n");
		sb.append("						 NVL(?,0),                    \n");
		sb.append("						 NVL(?,0),                    \n");
    sb.append("						 ?,                           \n");
    sb.append("						 NVL(?,0),                    \n");
    sb.append("						 NVL(?,0),                    \n");
    sb.append("						 NVL(?,0),                    \n");
    sb.append("						 NVL(?,0),                    \n");
    sb.append("						 NVL(?,0),                    \n");
    sb.append("						 NVL(?,0),                    \n");
		 sb.append("					 NVL(?,0)                     \n");
		sb.append("	       )                                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
    int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("date"));
		parameters.setString(i++, paramInfo.getString("gueonjong"));
		parameters.setString(i++, paramInfo.getString("create"));
		parameters.setString(i++, paramInfo.getString("ldisuse"));
		parameters.setString(i++, paramInfo.getString("gumgosale"));
		parameters.setString(i++, paramInfo.getString("citysale"));
		parameters.setString(i++, paramInfo.getString("citydivide"));
		parameters.setString(i++, paramInfo.getString("log_no"));

		parameters.setString(i++, paramInfo.getString("total_create"));
		parameters.setString(i++, paramInfo.getString("total_disuse"));
		parameters.setString(i++, paramInfo.getString("total_gumgosale"));
		parameters.setString(i++, paramInfo.getString("total_citysale"));
		parameters.setString(i++, paramInfo.getString("gumgorest"));
		parameters.setString(i++, paramInfo.getString("cityrest"));
		parameters.setString(i++, paramInfo.getString("lastyear_amt"));

		return template.insert(conn, parameters);
	}


  /* 수입증지 삭제(등록 오류 발생시) */
	public static int deleteData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M050_TAXINSTAMP_T      \n");
		sb.append(" WHERE M050_YEAR = ? 	            \n");
		sb.append("   AND M050_DATE = ? 	            \n");
	 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("date"));
	

		return template.delete(conn, parameters);
	}


    /*권종 구하기 및 해당일 잔량 */
	public static CommonEntity getGwonValue(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT CASE WHEN M050_CREATE = NVL(?, 0)      \n");
        sb.append("              AND M050_LDISUSE = NVL(?, 0)     \n");
        sb.append("              AND M050_GUMGOSALE = NVL(?, 0)   \n");
        sb.append("              AND M050_CITYSALE = NVL(?, 0)    \n");
        sb.append("              AND M050_CITYDIVIDE = NVL(?, 0)  \n");
        sb.append("        THEN 'EVEN'                            \n");
        sb.append("        ELSE M050_GUEONJONG || ''              \n");
        sb.append("        END GWON_VAL                           \n");
        sb.append("       ,(M050_GUMGOREST - M050_CREATE + "+paramInfo.getLong("create")+"   \n");
        sb.append("      + M050_LDISUSE - "+paramInfo.getLong("ldisuse")+" + M050_GUMGOSALE  \n");
        sb.append("      - "+paramInfo.getLong("gumgosale")+" + M050_CITYDIVIDE              \n");
        sb.append("      - "+paramInfo.getLong("citydivide")+" ) SIGUMGO_JAN                 \n");
        sb.append("       ,(M050_CITYREST - M050_CITYDIVIDE + "+paramInfo.getLong("citydivide")+"   \n");
        sb.append("      + M050_CITYSALE - "+paramInfo.getLong("citysale")+" ) CITY_JAN      \n");
        sb.append("   FROM M050_TAXINSTAMP_T                      \n");
        sb.append("  WHERE M050_YEAR = ?                          \n");
        sb.append("    AND M050_DATE = ?                          \n");
        sb.append("    AND M050_GUEONJONG = ?                     \n");   
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
        parameters.setString(i++, paramInfo.getString("create"));
		parameters.setString(i++, paramInfo.getString("ldisuse"));	
        parameters.setString(i++, paramInfo.getString("gumgosale"));	
        parameters.setString(i++, paramInfo.getString("citysale"));	
        parameters.setString(i++, paramInfo.getString("citydivide"));	
        parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("date"));	
        parameters.setString(i++, paramInfo.getString("gueonjong"));	
	
		return template.getData(conn, parameters);
	}


  /* 일자리스트 */
	public static List<CommonEntity> getDayList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT M050_DATE             \n");
    sb.append("       ,MAX(M050_YEAR) M050_YEAR \n");
    sb.append("   FROM M050_TAXINSTAMP_T     \n");
    sb.append("  WHERE M050_DATE >= ?        \n");
    sb.append("  GROUP BY M050_DATE          \n");
    sb.append("  ORDER BY M050_DATE          \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

		parameters.setString(i++, paramInfo.getString("date"));	
	
		return template.getList(conn, parameters);
	}


	/* 수정일 자료 수정(당일과 잔량 필드만 수정) */
	public static int updateFirst(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M050_TAXINSTAMP_T             \n");
		sb.append("   SET M050_CREATE = ?,              \n");	
		sb.append("       M050_LDISUSE = ?, 			\n");	 
		sb.append("       M050_GUMGOSALE = ?, 			\n");	 
		sb.append("	      M050_CITYSALE = ?,  			\n");	
		sb.append("      	M050_CITYDIVIDE = ?,  	    \n");	
        sb.append("	      M050_GUMGOREST = ?,  			\n");	
		sb.append("      	M050_CITYREST =	?			\n");	
		sb.append(" WHERE M050_GUEONJONG = ? 	        \n");
		sb.append("   AND M050_YEAR = ? 	            \n");
		sb.append("   AND M050_DATE = ? 	            \n");
	 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

		parameters.setString(i++, paramInfo.getString("create"));
		parameters.setString(i++, paramInfo.getString("ldisuse"));
		parameters.setString(i++, paramInfo.getString("gumgosale"));
		parameters.setString(i++, paramInfo.getString("citysale"));
		parameters.setString(i++, paramInfo.getString("citydivide"));
		parameters.setString(i++, paramInfo.getString("sigumgo_jan"));
		parameters.setString(i++, paramInfo.getString("city_jan"));
        parameters.setString(i++, paramInfo.getString("gueonjong"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("date"));
	

		return template.update(conn, parameters);
	}


  /* 첫 수정일 아닐 때 */
	public static int updateNugye(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M050_TAXINSTAMP_T           \n");
		sb.append("   SET M050_LASTYEAR = ?           \n");	
		sb.append("      ,M050_TOTALCREATE = ? 			  \n");	 
		sb.append("      ,M050_TOTALDISUSE = ? 			  \n");	 
		sb.append("	     ,M050_TOTALGUMGOSALE = ?  	  \n");	
		sb.append("      ,M050_TOTALCITYSALE =	?		  \n");	
    sb.append("	     ,M050_GUMGOREST = ? + M050_CREATE - M050_LDISUSE - M050_GUMGOSALE - M050_CITYDIVIDE  \n");	
		sb.append("      ,M050_CITYREST =	?	+ M050_CITYDIVIDE - M050_CITYSALE  \n");	
		sb.append(" WHERE M050_GUEONJONG = ? 	        \n");
		sb.append("   AND M050_YEAR = ? 	            \n");
		sb.append("   AND M050_DATE = ? 	            \n");
	 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

    parameters.setString(i++, paramInfo.getString("lastyear_amt"));
		parameters.setString(i++, paramInfo.getString("create_nu"));
		parameters.setString(i++, paramInfo.getString("ldisuse_nu"));
		parameters.setString(i++, paramInfo.getString("gumgosale_nu"));
		parameters.setString(i++, paramInfo.getString("citysale_nu"));
		parameters.setString(i++, paramInfo.getString("sigumgo_amt"));
		parameters.setString(i++, paramInfo.getString("city_amt"));
    parameters.setString(i++, paramInfo.getString("gueonjong"));
		parameters.setString(i++, paramInfo.getString("roop_year"));
		parameters.setString(i++, paramInfo.getString("roop_date"));
	

		return template.update(conn, parameters);
	}


	/*등록 일자 구하기 */
	public static CommonEntity getStampDateView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();


		sb.append("  SELECT  CASE WHEN MAX(M050_DATE) > ?                    \n");
		sb.append("          THEN 'FALSE'                                    \n");
		sb.append("          WHEN MAX(M050_DATE) < GET_AGO_BUSINESSDAY(?)    \n");
		sb.append("          THEN 'SUNSEO'                                   \n");
    sb.append("          WHEN MAX(M050_DATE) = ?                         \n");
		sb.append("          THEN 'JONJAE'                                   \n");
		sb.append("          ELSE 'YES'                                      \n");
		sb.append("          END TT                                          \n");
		sb.append("  FROM  M050_TAXINSTAMP_T                                 \n");   
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
   	parameters.setString(i++, paramInfo.getString("date"));
		parameters.setString(i++, paramInfo.getString("date"));	
    parameters.setString(i++, paramInfo.getString("date"));	
	
		return template.getData(conn, parameters);
	}


  /* 시청, 시금고 잔량 구하기 */
	public static CommonEntity getJanryang(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT CASE WHEN M050_GUMGOREST + NVL(?, 0) - NVL(?, 0) - NVL(?, 0) - NVL(?, 0) >= 0 \n");
    sb.append("             THEN M050_GUMGOREST + NVL(?, 0) - NVL(?, 0) - NVL(?, 0) - NVL(?, 0) || ''\n");
    sb.append("             ELSE 'FALSE'                             \n");
    sb.append("         END SIGUMGO_JAN                              \n");
    sb.append("       ,CASE WHEN M050_CITYREST + NVL(?, 0) - NVL(?, 0) >= 0          \n");
    sb.append("             THEN M050_CITYREST + NVL(?, 0) - NVL(?, 0) || ''         \n");
    sb.append("             ELSE 'FALSE'                             \n");
    sb.append("         END CITY_JAN                                 \n");
    sb.append("   FROM M050_TAXINSTAMP_T                             \n");
    sb.append("  WHERE M050_DATE = ?                                 \n");
    sb.append("    AND M050_GUEONJONG = ?                            \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
        parameters.setString(i++, paramInfo.getString("create"));
		parameters.setString(i++, paramInfo.getString("ldisuse"));	
        parameters.setString(i++, paramInfo.getString("gumgosale"));
		parameters.setString(i++, paramInfo.getString("citydivide"));	
        parameters.setString(i++, paramInfo.getString("create"));
		parameters.setString(i++, paramInfo.getString("ldisuse"));	
        parameters.setString(i++, paramInfo.getString("gumgosale"));
		parameters.setString(i++, paramInfo.getString("citydivide"));	
        parameters.setString(i++, paramInfo.getString("citydivide"));
		parameters.setString(i++, paramInfo.getString("citysale"));	
        parameters.setString(i++, paramInfo.getString("citydivide"));
		parameters.setString(i++, paramInfo.getString("citysale"));	
   	    parameters.setString(i++, paramInfo.getString("max_date"));
		parameters.setString(i++, paramInfo.getString("gueonjong"));	
	
		return template.getData(conn, parameters);
	}


	/*수정 가능 여부 */
	public static CommonEntity Updatable(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT(1) CNT        \n");                
		sb.append("   FROM M050_TAXINSTAMP_T   \n");              
		sb.append("  WHERE M050_YEAR = ?       \n");
        sb.append("    AND M050_DATE = ?       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

	    parameters.setString(i++, paramInfo.getString("year"));
        parameters.setString(i++, paramInfo.getString("date"));
	
		return template.getData(conn, parameters);
	}
	
	/*가장 최근 등록일자 구하기 */
	public static CommonEntity getMaxDateView(Connection conn)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("  SELECT  MAX(M050_DATE) MAX_DATE  \n");
        sb.append("         ,MAX(M050_YEAR) MAX_YEAR  \n");
		sb.append("    FROM  M050_TAXINSTAMP_T        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}


    /*수정시 수정일자의 전영업일 구하기 */
	public static CommonEntity getMaxDate(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("  SELECT MAX(M050_DATE) MAX_DATE    \n");
        sb.append("        ,MAX(M050_YEAR) MAX_YEAR    \n");
		sb.append("    FROM M050_TAXINSTAMP_T          \n");
        sb.append("   WHERE M050_DATE < ?              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("roop_date"));

		return template.getData(conn, parameters);
	}


    /*수정시 직전 영업일의 이월 및 누계치 */
	public static CommonEntity getIwalData(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("  SELECT M050_LASTYEAR                                               \n");      //직전일의 전년이월(연초영업일 아닐 때)
        sb.append("        ,(M050_GUMGOREST + M050_CITYREST)       LASTYEAR_AMT         \n"); //직전일의 전년이월(연초영업일 일때)
        sb.append("        ,(M050_CREATE + M050_TOTALCREATE)       M050_TOTALCREATE     \n"); // -- //
        sb.append("        ,(M050_LDISUSE + M050_TOTALDISUSE)      M050_TOTALDISUSE     \n");
        sb.append("        ,(M050_GUMGOSALE + M050_TOTALGUMGOSALE) M050_TOTALGUMGOSALE  \n"); // 연초영업일 아닐 때 전일누계
        sb.append("        ,(M050_CITYSALE + M050_TOTALCITYSALE)   M050_TOTALCITYSALE   \n");  //--  // 
        sb.append("        ,(M050_GUMGOREST) SIGUMGO_AMT                                \n");  //직전 시금고 잔량
        sb.append("        ,(M050_CITYREST) CITY_AMT                                    \n");  //직전 시청지점 잔량
		sb.append("    FROM M050_TAXINSTAMP_T                                           \n");
        sb.append("   WHERE M050_YEAR = ?                                               \n");
        sb.append("     AND M050_DATE = ?                                               \n");
        sb.append("     AND M050_GUEONJONG = ?                                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("max_year"));
        parameters.setString(idx++, paramInfo.getString("max_date"));
        parameters.setString(idx++, paramInfo.getString("gueonjong"));

		return template.getData(conn, parameters);
	}
	 
	 /*전년이월 = 시금고잔량 + 시청 잔량 */
	public static CommonEntity getLastRestView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();                   
		
		sb.append("SELECT M050_GUMGOREST + M050_CITYREST LASTREST                      \n");  
        sb.append("      ,M050_LASTYEAR                                                \n");  
        sb.append("      ,(M050_CREATE + M050_TOTALCREATE) M050_TOTALCREATE            \n"); 
        sb.append("      ,(M050_LDISUSE + M050_TOTALDISUSE) M050_TOTALDISUSE           \n"); 
        sb.append("      ,(M050_GUMGOSALE + M050_TOTALGUMGOSALE) M050_TOTALGUMGOSALE   \n"); 
        sb.append("      ,(M050_CITYSALE + M050_TOTALCITYSALE) M050_TOTALCITYSALE      \n"); 
        sb.append("  FROM M050_TAXINSTAMP_T                                            \n");   
        sb.append(" WHERE M050_YEAR = ?                                                \n");    
        sb.append("   AND M050_DATE = ?                                                \n");   
        sb.append("   AND M050_GUEONJONG = ?                                           \n");  
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
        
        parameters.setString(i++, paramInfo.getString("max_year"));
	    parameters.setString(i++, paramInfo.getString("max_date"));
		parameters.setString(i++, paramInfo.getString("gueonjong"));

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