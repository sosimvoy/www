/*****************************************************
* 프로젝트명	   : 대전광역시 가상계좌번호납부 관리자시스템
* 프로그램명	   : IR071210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용	 : 지방세 > 가상계좌발췌
******************************************************/
package com.etax.db.mn07;

import java.io.*;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR071210 {

  private static Logger logger = Logger.getLogger(IR071210.class);

  // 입력해야 할 세목코드
  public static CommonEntity getSrtData(Connection conn, CommonEntity paramInfo, String semok) throws SQLException {

    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M440_SEMOKCODE                                     \n");
    sb.append("      , M440_PARTCODE11                                    \n");
    sb.append("      , M440_PARTCODE12                                    \n");
    sb.append("      , M440_PARTCODE13                                    \n");
    sb.append("      , M440_PARTCODE14                                    \n");
    sb.append("      , M440_PARTCODE15                                    \n");
    sb.append("      , M440_PARTCODE21                                    \n");
    sb.append("      , M440_PARTCODE22                                    \n");
    sb.append("      , M440_PARTCODE23                                    \n");
    sb.append("      , M440_PARTCODE24                                    \n");
    sb.append("      , M440_PARTCODE25                                    \n");
    sb.append("      , B.M550_TAXGBN                                      \n");
    sb.append("      , B.M550_MOKGUBUN                                    \n");
    sb.append("      , DECODE(B.M550_TAXGBN,'0','2',                      \n");
    sb.append("                             '1','1',                      \n");
    sb.append("                             '2','3',                      \n");
    sb.append("                             '3','3',                      \n");
    sb.append("                             '4','3','9') AS M550_GTAXGBN  \n");
    sb.append("   FROM M440_NONGHYUPCODE_T A                              \n");
    sb.append("      , M550_INCOMESEMOK_T B                               \n");
    sb.append("  WHERE M440_INTYPE = ?                                    \n");
    sb.append("    AND M440_SEMOKNAME = ?                                 \n");
    sb.append("    AND M550_YEAR = ?                                      \n");
    sb.append("    AND M440_SEMOKCODE = M550_SEMOKCODE                    \n");
    sb.append("    AND M440_YEAR      = M550_YEAR                         \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("data_type1"));
		parameters.setString(idx++, semok);
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.getData(conn, parameters);
  }


  // 일일마감체크
  public static CommonEntity getDailyInfo(Connection conn, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT M210_WORKENDSTATE  \n"); 
    sb.append("   FROM M210_WORKEND_T     \n");
    sb.append("  WHERE M210_YEAR = ?      \n");
    sb.append("    AND M210_DATE = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getData(conn, parameters);
  }


	// 중복 자료 체크
  public static CommonEntity getDataInfo(Connection conn, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT COUNT(1) CNT          \n"); 
    sb.append("   FROM M500_INCOMEDAY_T      \n");
    sb.append("  WHERE M500_YEAR = ?         \n");
    sb.append("    AND M500_DATE = ?         \n");
		sb.append("    AND M500_PROCTYPE = ?     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("proctype"));

    return template.getData(conn, parameters);
  }

	// 임시자료 입력(광역시세일계표)
  public static int insertData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                            \n"); 
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM, \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,        \n");
    sb.append("   M450_USERID)                                                        \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                \n");    
    sb.append("         ?, ?, ?, ?, ?,                                                \n");   
    sb.append("         ? )                                                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
		parameters.setString(idx++, data.getString("USER_ID"));

    return template.insert(conn, parameters);
  }


	// 임시자료 입력(과오납환부일계표)
  public static int insertGwaData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                                         \n");
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM,              \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,                     \n");
    sb.append("   M450_JUNGGU_GWA, M450_NAMGU_GWA, M450_DONGGU_GWA, M450_BUKGU_GWA, M450_ULJU_GWA, \n");
    sb.append("   M450_USERID)                                                                     \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ? )                                                                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
		parameters.setString(idx++, data.getString("JUNGGU_GWA"));
		parameters.setString(idx++, data.getString("NAMGU_GWA"));
		parameters.setString(idx++, data.getString("DONGGU_GWA"));
		parameters.setString(idx++, data.getString("BUKGU_GWA"));
		parameters.setString(idx++, data.getString("ULJUGUN_GWA"));
		parameters.setString(idx++, data.getString("USER_ID"));

    return template.insert(conn, parameters);
  }


  // 임시자료 입력(차량등록사업소시세일계표)
  public static int insertCarData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                                          \n"); 
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM,               \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,                      \n");
    sb.append("   M450_JUNGGU_GWA, M450_NAMGU_GWA, M450_DONGGU_GWA, M450_BUKGU_GWA, M450_ULJU_GWA,  \n");
    sb.append("   M450_USERID)                                                                      \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                              \n");    
    sb.append("         ?, ?, ?, ?, ?,                                                              \n");
    sb.append("         ?, ?, ?, ?, ?,                                                              \n");
    sb.append("         ? )                                                                         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
    parameters.setString(idx++, data.getString("JUNGGU_CNT"));
		parameters.setString(idx++, data.getString("NAMGU_CNT"));
		parameters.setString(idx++, data.getString("DONGGU_CNT"));
		parameters.setString(idx++, data.getString("BUKGU_CNT"));
		parameters.setString(idx++, data.getString("ULJUGUN_CNT"));
		parameters.setString(idx++, data.getString("USER_ID"));

    return template.insert(conn, parameters);
  }

   
	// 임시자료 리스트(광역시세세입일계표)
  public static List<CommonEntity> getNonghyupList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                      \n");
    sb.append("       ,M450_SEMOK_NM                      \n");
    sb.append("       ,M450_JUNGGU                        \n");
    sb.append("       ,M450_NAMGU                         \n");
    sb.append("       ,M450_DONGGU                        \n");
    sb.append("       ,M450_BUKGU                         \n");
    sb.append("       ,M450_ULJU                          \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '1'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '1999999' M450_SEMOK_CD            \n");
    sb.append("       ,'지방세소계' M450_SEMOK_NM         \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '1'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT M450_SEMOK_CD                      \n");
    sb.append("       ,M450_SEMOK_NM                      \n");
    sb.append("       ,M450_JUNGGU                        \n");
    sb.append("       ,M450_NAMGU                         \n");
    sb.append("       ,M450_DONGGU                        \n");
    sb.append("       ,M450_BUKGU                         \n");
    sb.append("       ,M450_ULJU                          \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '2'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '2999999' M450_SEMOK_CD            \n");
    sb.append("       ,'세외수입소계' M450_SEMOK_NM       \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '2'    \n");
		sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '9999999' M450_SEMOK_CD            \n");
    sb.append("       ,'합 계' M450_SEMOK_NM              \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append(" ORDER BY M450_SEMOK_CD                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }


	// 임시자료 리스트(과오납환부일계표)
  public static List<CommonEntity> getNonghyupGwaList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                        \n");
    sb.append("       ,M450_SEMOK_NM                        \n");
    sb.append("       ,M450_JUNGGU                          \n");
    sb.append("       ,M450_NAMGU                           \n");
    sb.append("       ,M450_DONGGU                          \n");
    sb.append("       ,M450_BUKGU                           \n");
    sb.append("       ,M450_ULJU                            \n");
		sb.append("       ,M450_JUNGGU_GWA                      \n");
    sb.append("       ,M450_NAMGU_GWA                       \n");
    sb.append("       ,M450_DONGGU_GWA                      \n");
    sb.append("       ,M450_BUKGU_GWA                       \n");
    sb.append("       ,M450_ULJU_GWA                        \n");
    sb.append("   FROM M450_PREVIEW_T                       \n");
    sb.append("  WHERE M450_YEAR = ?                        \n");
    sb.append("    AND M450_DATE = ?                        \n");
    sb.append("    AND M450_DATA_TYPE = ?                   \n");
    sb.append("    AND M450_USERID = ?                      \n");
    sb.append(" ORDER BY M450_SEMOK_CD                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }

  
  // 임시자료 리스트(차량사업소)
  public static List<CommonEntity> getNonghyupCarList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                        \n");
    sb.append("       ,M450_SEMOK_NM                        \n");
    sb.append("       ,M450_JUNGGU                          \n");
    sb.append("       ,M450_NAMGU                           \n");
    sb.append("       ,M450_DONGGU                          \n");
    sb.append("       ,M450_BUKGU                           \n");
    sb.append("       ,M450_ULJU                            \n");
		sb.append("       ,M450_JUNGGU_GWA                      \n");
    sb.append("       ,M450_NAMGU_GWA                       \n");
    sb.append("       ,M450_DONGGU_GWA                      \n");
    sb.append("       ,M450_BUKGU_GWA                       \n");
    sb.append("       ,M450_ULJU_GWA                        \n");
    sb.append("   FROM M450_PREVIEW_T                       \n");
    sb.append("  WHERE M450_YEAR = ?                        \n");
    sb.append("    AND M450_DATE = ?                        \n");
    sb.append("    AND M450_DATA_TYPE = ?                   \n");
    sb.append("    AND M450_USERID = ?                      \n");
    sb.append(" ORDER BY M450_SEMOK_CD                      \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }
  
	//임시자료 테이블 자료 삭제
  public static int deleteData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" DELETE FROM M450_PREVIEW_T          \n"); 
    sb.append("  WHERE M450_USERID = ?              \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;

    parameters.setString(idx++, data.getString("user_id"));

    return template.delete(conn, parameters);
  }


	//농협자료 등록(북구)
  public static int inserthist(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO M501_INCOMEDAYHST_T                   \n");
		sb.append(" (M501_YEAR, M501_DATE                             \n");
		sb.append(" ,M501_PRTDATE, M501_GROUPTAX         \n");
		sb.append(" ,M501_TAXGBN, M501_PARTCODE                       \n");
		sb.append("	,M501_SEMOKCODE,M501_SEQ                          \n");
		sb.append("	,M501_PROCTYPE , M501_SUNABAMT                    \n");
		sb.append("	,M501_SUNABCNT, M501_HWANBUAMT, M501_HWANBUCNT)   \n");
		sb.append(" VALUES (?, ?				              						    \n");
	  sb.append(" 			 ,?, ?                                      \n");
		sb.append(" 			 ,?, ?  				                            \n");
		sb.append(" 			 ,?, M501_SEQ.NEXTVAL                       \n");
		sb.append(" 			 ,?, ?						                        \n"); 
		sb.append(" 			 ,?,?,?)    						                    \n"); 

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, data.getString("fis_year"));
		parameters.setString(idx++, data.getString("acc_date"));
		parameters.setString(idx++, data.getString("prt_date"));
		parameters.setString(idx++, data.getString("gtaxgbn"));
		parameters.setString(idx++, data.getString("taxgbn"));
		parameters.setString(idx++, data.getString("partCode"));
		parameters.setString(idx++, data.getString("semokCode"));
		parameters.setString(idx++, data.getString("proctype"));
		parameters.setString(idx++, data.getString("sunapamt"));
		parameters.setString(idx++, data.getString("sunapcnt"));
		parameters.setString(idx++, data.getString("hwanbuamt"));
		parameters.setString(idx++, data.getString("hwanbucnt"));

    return template.insert(conn, parameters);
  }

	//농협자료 등록(북구)
  public static int insertmast(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
		sb.append(" INSERT INTO M500_INCOMEDAY_T                      \n");
		sb.append(" (M500_YEAR, M500_DATE                             \n");
		sb.append(" ,M500_PRTDATE, M500_GROUPTAX                      \n");
		sb.append(" ,M500_TAXGBN, M500_PARTCODE                       \n");
		sb.append("	,M500_SEMOKCODE                                   \n");
		sb.append("	,M500_PROCTYPE , M500_SUNABAMT                    \n");
		sb.append("	,M500_SUNABCNT, M500_HWANBUAMT, M500_HWANBUCNT)   \n");
		sb.append(" VALUES (?, ?				              						    \n");
	  sb.append(" 			 ,?, ?                                      \n");
		sb.append(" 			 ,?, ?  				                            \n");
		sb.append(" 			 ,?                                         \n");
		sb.append(" 			 ,?, ?						                          \n"); 
		sb.append(" 			 ,?,?,?)    						                    \n"); 

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, data.getString("fis_year"));
		parameters.setString(idx++, data.getString("acc_date"));
		parameters.setString(idx++, data.getString("prt_date"));
		parameters.setString(idx++, data.getString("gtaxgbn"));
		parameters.setString(idx++, data.getString("taxgbn"));
		parameters.setString(idx++, data.getString("partCode"));
		parameters.setString(idx++, data.getString("semokCode"));
		parameters.setString(idx++, data.getString("proctype"));
		parameters.setString(idx++, data.getString("sunapamt"));
		parameters.setString(idx++, data.getString("sunapcnt"));
		parameters.setString(idx++, data.getString("hwanbuamt"));
		parameters.setString(idx++, data.getString("hwanbucnt"));

    return template.insert(conn, parameters);
  }

	//농협자료 삭제
  public static int deletemastData(Connection conn, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" DELETE FROM M500_INCOMEDAY_T            \n");
		sb.append("  WHERE M500_YEAR = ?                    \n");
		sb.append("    AND M500_DATE = ?                    \n");
		sb.append("    AND M500_PROCTYPE = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("proctype"));

    return template.delete(conn, parameters);
  }
	//농협자료 삭제
  public static int deletehstData(Connection conn, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" DELETE FROM M501_INCOMEDAYHST_T         \n");
		sb.append("  WHERE M501_YEAR = ?                    \n");
		sb.append("    AND M501_DATE = ?                    \n");
		sb.append("    AND M501_PROCTYPE = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("proctype"));

    return template.delete(conn, parameters);
  }
	//농협자료 삭제
  public static int incomemst2hst(Connection conn, CommonEntity paramInfo) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M500_INCOMEDAY_T              \n");
    sb.append(" (                                         \n");
    sb.append("      M500_YEAR                            \n");
    sb.append("    , M500_DATE                            \n");
    sb.append("    , M500_PRTDATE                         \n");
    sb.append("    , M500_GROUPTAX                        \n");
    sb.append("    , M500_TAXGBN                          \n");
    sb.append("    , M500_PARTCODE                        \n");
    sb.append("    , M500_SEMOKCODE                       \n");
    sb.append("    , M500_PROCTYPE                        \n");
    sb.append("    , M500_SUNABAMT                        \n");
    sb.append("    , M500_SUNABCNT                        \n");
    sb.append("    , M500_HWANBUAMT                       \n");
    sb.append("    , M500_HWANBUCNT                       \n");
    sb.append(" )                                         \n");
    sb.append(" SELECT M501_YEAR                          \n");
    sb.append("       , M501_DATE                         \n");
    sb.append("       , M501_PRTDATE                      \n");
    sb.append("       , M501_GROUPTAX                     \n");
    sb.append("       , M501_TAXGBN                       \n");
    sb.append("       , M501_PARTCODE                     \n");
    sb.append("       , M501_SEMOKCODE                    \n");
    sb.append("       , M501_PROCTYPE                     \n");
    sb.append("       , SUM(M501_SUNABAMT)                \n");
    sb.append("       , SUM(M501_SUNABCNT)                \n");
    sb.append("       , SUM(M501_HWANBUAMT)               \n");
    sb.append("       , SUM(M501_HWANBUCNT)               \n");
    sb.append("   FROM M501_INCOMEDAYHST_T                \n");
    sb.append("  WHERE 1 = 1                              \n");
    sb.append("    AND M501_YEAR = ?                      \n");
    sb.append("    AND M501_DATE = ?                      \n");
    sb.append("    AND M501_PROCTYPE = ?                  \n");
    sb.append("  GROUP BY M501_YEAR                       \n");
    sb.append("       , M501_DATE                         \n");
    sb.append("       , M501_PRTDATE                      \n");
    sb.append("       , M501_GROUPTAX                     \n");
    sb.append("       , M501_TAXGBN                       \n");
    sb.append("       , M501_PARTCODE                     \n");
    sb.append("       , M501_SEMOKCODE                    \n");
    sb.append("       , M501_PROCTYPE                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("proctype"));

    return template.delete(conn, parameters);
  }
}
