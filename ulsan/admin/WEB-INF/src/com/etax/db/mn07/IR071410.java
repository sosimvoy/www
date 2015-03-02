/***********************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR071410.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-08-16
* 프로그램내용	   : 일계/보고서 > 세입세출일계정정(소급처리)
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR071410 {
  
	/* 수납기관조회 */ 
	public static List<CommonEntity> getRevCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT M430_SUNAPGIGWANCODE AS REV_CD  \n");
		sb.append("      , M430_SUNAPGIGWANNAME AS REV_NM  \n");	
    sb.append("   FROM M430_SUNAPGIGWANCODE_T 	       \n");   
    if ("T2".equals(paramInfo.getString("tax_type")) ) {
      sb.append(" WHERE M430_SUNAPGIGWANCODE = ?       \n");
    }	
    sb.append("  ORDER BY M430_SUNAPGIGWANCODE 	       \n");   
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
    if ("T2".equals(paramInfo.getString("tax_type")) ) {
		  parameters.setString(idx++, "110000");
    }
        
    return template.getList(conn, parameters);
  }


  /* 부서 조회 */ 
	public static List<CommonEntity> getPartCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M350_PARTCODE AS PART_CD           \n");
    sb.append("      , A.M350_PARTNAME AS PART_NM           \n");
    sb.append("   FROM M350_PARTCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B                \n");
    sb.append("  WHERE A.M350_YEAR = B.M390_YEAR            \n");
    sb.append("    AND A.M350_PARTCODE =  B.M390_PARTCODE   \n");
    sb.append("    AND A.M350_YEAR = ?                      \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                  \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                 \n");
    sb.append("  GROUP BY A.M350_PARTCODE, A.M350_PARTNAME  \n");
    sb.append("  ORDER BY A.M350_PARTCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
        
    return template.getList(conn, parameters);
  }


  /* 회계 조회 */ 
	public static List<CommonEntity> getAccCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M360_ACCCODE AS ACC_CD            \n");
    sb.append("      , A.M360_ACCNAME AS ACC_NM            \n");
    sb.append("   FROM M360_ACCCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B               \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR           \n");
    sb.append("    AND A.M360_ACCCODE =  B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");
    sb.append("    AND A.M360_YEAR = ?                     \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                 \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                 \n");
    sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME   \n");
    sb.append("  ORDER BY A.M360_ACCCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getList(conn, parameters);
  }


  /* 부서에 따른 회계 값 얻기 */ 
	public static CommonEntity getAccCd(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M360_ACCCODE AS ACC_CD            \n");
    sb.append("      , A.M360_ACCNAME AS ACC_NM            \n");
    sb.append("   FROM M360_ACCCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B               \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR           \n");
    sb.append("    AND A.M360_ACCCODE =  B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");
    sb.append("    AND A.M360_YEAR = ?                     \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                 \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                 \n");
    sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME   \n");
    sb.append("  ORDER BY A.M360_ACCCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getData(conn, parameters);
  }



  /* 세목 조회 */ 
	public static List<CommonEntity> getSemokCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M370_SEMOKCODE AS SEMOK_CD           \n");
    sb.append("      , A.M370_SEMOKNAME AS SEMOK_NM           \n");
    sb.append("   FROM M370_SEMOKCODE_T A                     \n");
    sb.append("      , M390_USESEMOKCODE_T B                  \n");
    sb.append("  WHERE A.M370_YEAR = B.M390_YEAR              \n");
    sb.append("    AND A.M370_ACCCODE =  B.M390_ACCCODE       \n");
    sb.append("    AND A.M370_ACCGUBUN = B.M390_ACCGUBUN      \n");
    sb.append("    AND A.M370_SEMOKCODE =  B.M390_SEMOKCODE   \n");
    sb.append("    AND A.M370_YEAR = ?                        \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                    \n");
    sb.append("    AND A.M370_WORKGUBUN = ?                   \n");
    sb.append("    AND B.M390_PARTCODE = ?                    \n");
    sb.append("    AND B.M390_ACCCODE = ?                     \n");
    sb.append("  GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME  \n");
    sb.append("  ORDER BY A.M370_SEMOKCODE                    \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
        
    return template.getList(conn, parameters);
  }

  
  /* 자료 존재 여부(회계일자) */ 
	public static CommonEntity getRecord(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT *                                    \n");
    sb.append("   FROM M220_DAY_T                           \n");
    sb.append("  WHERE M220_YEAR = ?                        \n");
    sb.append("    AND M220_DATE = ?                        \n");
    sb.append("    AND M220_ACCTYPE = ?                     \n");
    sb.append("    AND M220_ACCCODE = ?                     \n");
    sb.append("    AND M220_SEMOKCODE = ?                   \n");
    sb.append("    AND M220_PARTCODE = ?                    \n");
    sb.append("    AND M220_SUNAPGIGWANCODE = ?             \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("semok_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("rev_cd"));
        
    return template.getData(conn, parameters);
  }

  /* 화면입력일자보다 큰 마감MAX일자(회계일자)구하기 */ 
	public static CommonEntity getMaxRecord(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT MAX(M210_DATE) AS M210_MAXDATE       \n");
    sb.append("   FROM M210_WORKEND_T                       \n");
    sb.append("  WHERE M210_YEAR = ?                        \n");
    sb.append("    AND M210_DATE > ?                        \n");
    sb.append("    AND M210_WORKENDSTATE = 'Y'              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
        
    return template.getData(conn, parameters);
  }

	/* 일일비고 등록  */
  public static int dateNoteInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M220_DAY_T            \n");
		sb.append("       ( M220_YEAR   					    \n");	 
		sb.append("        ,M220_DATE 					      \n");	 
		sb.append("        ,M220_ACCTYPE 				      \n");	 
		sb.append("        ,M220_ACCCODE 			        \n");	 
		sb.append("        ,M220_SEMOKCODE 			      \n");	 
		sb.append("        ,M220_PARTCODE 			      \n");	 
		sb.append("        ,M220_SUNAPGIGWANCODE      \n");

		sb.append("        ,M220_AMTSEIP              \n"); //세입액
		sb.append("        ,M220_AMTSEIPGWAONAP       \n"); //과오납
		sb.append("        ,M220_AMTSEIPJEONGJEONG    \n"); //세입정정액
		sb.append("        ,M220_AMTPASTSEIP          \n"); //과년도세입액
		sb.append("        ,M220_AMTSECHUL            \n"); //세출액
		sb.append("        ,M220_AMTSECHULBANNAP      \n"); //세출반납액
		sb.append("        ,M220_AMTSECHULJEONGJEONG  \n"); //세출정정액

		sb.append("        ,M220_AMTSEIPJEONILTOT     \n"); //세입전일누계
		sb.append("        ,M220_AMTGWAONAPJEONILTOT  \n"); //과오납전일누계
		sb.append("        ,M220_AMTPASTSEIPJEONILTOT \n"); //과년도세입전일누계
		sb.append("        ,M220_AMTSECHULJEONILTOT   \n"); //세출전일누계
		sb.append("        ,M220_AMTBAJEONGJEONILTOT  \n"); //배정전일누계
		sb.append("        ,M220_AMTBAJEONG           \n"); //배정액
		sb.append("        ,M220_AMTBAJEONGSUJEONILTOT \n"); //배정수령전일누계
		sb.append("        ,M220_AMTBAJEONGSURYUNG     \n"); //배정수령액
		sb.append("        ,M220_AMTSURPLUSJEONILTOT   \n"); //잉여금이입전일누계
		sb.append("        ,M220_AMTSURPLUS            \n"); //잉여금이입액
		sb.append("        ,M220_AMTJEONGGI            \n"); //정기예금등
		sb.append("        ,M220_AMTGONGGEUM           \n"); //공금예금
		sb.append("        ,M220_AMTLOAN )			      \n");	//융자금
		sb.append(" VALUES( ?		                      \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");	
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");

		sb.append(" ,CASE					                                                             \n");  //세입액
		sb.append("  WHEN ? = 'T1' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //과오납
		sb.append("  WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //세입정정액
		sb.append("  WHEN ? = 'T1' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //과년도세입액
		sb.append("  WHEN ? = 'T1' AND ? = 'Y2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //세출액
		sb.append("  WHEN ? = 'T2' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //세출반납액
		sb.append("  WHEN ? = 'T2' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //세출정정액
		sb.append("  WHEN ? = 'T2' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");

		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0	)				                \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

    //세입액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과오납
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세입정정액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과년도세입액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출반납액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출정정액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
	
		return template.insert(conn, parameters);
	}

	/* 일일비고 삭제 */
	public static int dateNoteInsertProc(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M220_DAY_T            \n");
		sb.append("       ( M220_YEAR   					    \n");	 
		sb.append("        ,M220_DATE 					      \n");	 
		sb.append("        ,M220_ACCTYPE 				      \n");	 
		sb.append("        ,M220_ACCCODE 			        \n");	 
		sb.append("        ,M220_SEMOKCODE 			      \n");	 
		sb.append("        ,M220_PARTCODE 			      \n");	 
		sb.append("        ,M220_SUNAPGIGWANCODE      \n");

		sb.append("        ,M220_AMTSEIPJEONILTOT     \n"); //세입전일누계
		sb.append("        ,M220_AMTGWAONAPJEONILTOT  \n"); //과오납전일누계
		sb.append("        ,M220_AMTPASTSEIPJEONILTOT \n"); //과년도세입전일누계
		sb.append("        ,M220_AMTSECHULJEONILTOT   \n"); //세출전일누계

		sb.append("        ,M220_AMTSEIP              \n"); //세입액
		sb.append("        ,M220_AMTSEIPGWAONAP       \n"); //과오납
		sb.append("        ,M220_AMTSEIPJEONGJEONG    \n"); //세입정정액
		sb.append("        ,M220_AMTPASTSEIP          \n"); //과년도세입액
		sb.append("        ,M220_AMTSECHUL            \n"); //세출액
		sb.append("        ,M220_AMTSECHULBANNAP      \n"); //세출반납액
		sb.append("        ,M220_AMTSECHULJEONGJEONG  \n"); //세출정정액
		sb.append("        ,M220_AMTBAJEONGJEONILTOT  \n"); //배정전일누계
		sb.append("        ,M220_AMTBAJEONG           \n"); //배정액
		sb.append("        ,M220_AMTBAJEONGSUJEONILTOT \n"); //배정수령전일누계
		sb.append("        ,M220_AMTBAJEONGSURYUNG     \n"); //배정수령액
		sb.append("        ,M220_AMTSURPLUSJEONILTOT   \n"); //잉여금이입전일누계
		sb.append("        ,M220_AMTSURPLUS            \n"); //잉여금이입액
		sb.append("        ,M220_AMTJEONGGI            \n"); //정기예금등
		sb.append("        ,M220_AMTGONGGEUM           \n"); //공금예금
		sb.append("        ,M220_AMTLOAN )			      \n");	//융자금             
    sb.append("  SELECT M220_YEAR                                                                      \n");
    sb.append("       , FDATE                                                                          \n");
    sb.append("       , M220_ACCTYPE                                                                   \n");
    sb.append("       , M220_ACCCODE                                                                   \n");
    sb.append("       , M220_SEMOKCODE                                                                 \n");
    sb.append("       , M220_PARTCODE                                                                  \n");
    sb.append("       , M220_SUNAPGIGWANCODE                                                           \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I3' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END -- 세입전일누계                                                   \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END  -- 과오납전일누계                                                \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'Y2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END  -- 과년도세입전일누계                                            \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END -- 세출전일누계                                                   \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("    FROM M220_DAY_T                                                                     \n");
    sb.append("       , ( SELECT M210_DATE AS FDATE                                                    \n");
    sb.append("             FROM M210_WORKEND_T                                                        \n");
    sb.append("            WHERE M210_YEAR = ?                                                         \n");
    sb.append("              AND M210_WORKENDSTATE = 'Y'                                               \n");
    sb.append("              AND M210_DATE > ?  ) B                                                    \n");
    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE = ?                                                                  \n");
    sb.append("     AND CASE                                                                           \n");
    sb.append("         WHEN M220_ACCTYPE = 'E' AND FDATE <= M220_YEAR||'1231' THEN '1'               \n");
    sb.append("         WHEN M220_ACCTYPE != 'E' THEN '1'                                              \n");
    sb.append("         ELSE '0' END = '1'                                                             \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //세입전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과오납전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과년도세입전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}

	/* 일일비고 삭제 */
	public static int dateNoteUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M220_DAY_T                                                        \n");
    sb.append("   SET M220_AMTSEIP = M220_AMTSEIP + TO_NUMBER(                   \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I1'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 세입액                                   \n");
    sb.append("     , M220_AMTSEIPGWAONAP = M220_AMTSEIPGWAONAP + TO_NUMBER(          \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 과오납                                   \n");
    sb.append("     , M220_AMTSEIPJEONGJEONG = M220_AMTSEIPJEONGJEONG + TO_NUMBER(       \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I3'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 세입정정액                               \n");
    sb.append("     , M220_AMTPASTSEIP = M220_AMTPASTSEIP + TO_NUMBER(              \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'Y2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 과년도세입액                             \n");
    sb.append("     , M220_AMTSECHUL = M220_AMTSECHUL +  TO_NUMBER(               \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I1'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 세출액                                   \n");
    sb.append("     , M220_AMTSECHULBANNAP = M220_AMTSECHULBANNAP +  TO_NUMBER(          \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 세출반납액                               \n");
    sb.append("     , M220_AMTSECHULJEONGJEONG = M220_AMTSECHULJEONGJEONG + TO_NUMBER(     \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I3'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- 세출정정액                               \n");
    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE = ?                                                                  \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //세입액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과오납
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세입정정액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과년도세입액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출반납액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출정정액
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));	
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}
	/* 일일비고 삭제 */
	public static int dateNoteUpdateProc(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M220_DAY_T                                                        \n");
    sb.append("   set M220_AMTSEIPJEONILTOT = M220_AMTSEIPJEONILTOT + TO_NUMBER(             \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I1'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I2'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I3'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTGWAONAPJEONILTOT = M220_AMTGWAONAPJEONILTOT + TO_NUMBER(    \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I2'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTPASTSEIPJEONILTOT = M220_AMTPASTSEIPJEONILTOT + TO_NUMBER(    \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'Y2'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTSECHULJEONILTOT = M220_AMTSECHULJEONILTOT +  TO_NUMBER(       \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I1'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I2'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I3'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               ELSE '0' END   )                                   \n");

    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE > ?                                                                  \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //세입전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과오납전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //과년도세입전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //세출전일누계
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}


  /* 자료 존재 여부(회계일자) */ 
	public static CommonEntity getDailyMax(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT MAX(M210_DATE) M210_DATE             \n");
    sb.append("   FROM M210_WORKEND_T                       \n");
    sb.append("  WHERE M210_YEAR = ?                        \n");
    sb.append("    AND M210_WORKENDSTATE = ?                \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, "Y");
        
    return template.getData(conn, parameters);
  }
}