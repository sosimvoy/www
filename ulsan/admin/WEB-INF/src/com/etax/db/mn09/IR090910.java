/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090910.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-07-25
* 프로그램내용	   : 시스템운영 > 결재권변경승인신청
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090910 {

  /* 회계구분에 따른 부서코드(시스템운영) */
	public static List<CommonEntity> getAccGubunDeptList09(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	             \n");  
    sb.append("   FROM  M390_USESEMOKCODE_T A 				   			         \n");   
		sb.append("        ,M350_PARTCODE_T B                              \n");       
    sb.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE              \n");   
    sb.append("    AND  A.M390_YEAR = B.M350_YEAR                      \n");   
		sb.append("    AND  A.M390_ACCGUBUN = NVL(?, 'A')                  \n"); 
		sb.append("    AND  A.M390_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY')) \n"); 
		sb.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME              \n");
		sb.append("   ORDER BY A.M390_PARTCODE                             \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("accGubun"));
		parameters.setString(idx++, paramInfo.getString("year"));
        
    return template.getList(conn, parameters);
  }


  /* 회계구분에 따른 회계명 (시스템운영-세목사용 관리)*/ 
	public static List<CommonEntity> getAccCdList092(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  M360_ACCCODE, M360_ACCNAME                  \n");		
        sb.append("   FROM  M360_ACCCODE_T                               \n"); 
		sb.append("  WHERE  M360_ACCGUBUN = NVL(?, 'A')                   \n"); 
		sb.append("  AND  M360_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY'))  \n"); 																																												
		sb.append(" ORDER BY M360_ACCCODE                             \n"); 		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("accGubun"));
	    parameters.setString(idx++, paramInfo.getString("year"));
        
    return template.getList(conn, parameters);
  }

   /* 회계구분에 따른 세목명(시스템운영) */ 
	public static List<CommonEntity> getSemokList091(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																											
		sb.append(" SELECT  M370_SEMOKCODE, M370_SEMOKNAME              \n");		
    sb.append("   FROM  M370_SEMOKCODE_T                            \n");       
    sb.append("  WHERE M370_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY')) \n");
		sb.append("    AND M370_ACCGUBUN = NVL(?, 'A')                  \n"); 
		sb.append("    AND M370_ACCCODE = NVL(?, '11')                  \n"); 
    sb.append("    AND M370_WORKGUBUN = NVL(?, '0')                 \n"); 
    sb.append("    AND M370_MOKGUBUN = '3'                          \n"); 
		sb.append(" ORDER BY M370_ACCGUBUN, M370_ACCCODE, M370_SEMOKCODE  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("year"));
    parameters.setString(idx++, paramInfo.getString("accGubun"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("workGubun"));
        
    return template.getList(conn, parameters);
  }


  /* 회계구분에 따른 회계명 (시스템운영-세목사용 관리)*/ 
	public static List<CommonEntity> getAccCdList091(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME          \n");		
    sb.append("   FROM  M390_USESEMOKCODE_T A 						  	\n");   
		sb.append("        ,M360_ACCCODE_T B                      \n"); 
		sb.append("  WHERE  A.M390_YEAR = B.M360_YEAR	            \n");  
    sb.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN     \n");  
	  sb.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE				\n");
    sb.append("    AND  A.M390_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY')) \n");
		sb.append("    AND  A.M390_PARTCODE = NVL(?, '00000')     \n"); 
    sb.append("    AND  A.M390_ACCGUBUN = NVL(?, 'A')         \n"); 
	  sb.append(" GROUP BY A.M390_ACCCODE, M360_ACCNAME         \n"); 																																												
		sb.append(" ORDER BY A.M390_ACCCODE                       \n"); 		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
    parameters.setString(idx++, paramInfo.getString("year1"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
    parameters.setString(idx++, paramInfo.getString("accGubun1"));
        
    return template.getList(conn, parameters);
  }


  /* 회계구분에 따른 부서코드1(시스템운영) */
	public static List<CommonEntity> getAccGubunDeptList091(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	   \n");  
    sb.append("   FROM  M390_USESEMOKCODE_T A 				   		 \n");   
		sb.append("        ,M350_PARTCODE_T B                    \n");       
    sb.append("  WHERE  A.M390_YEAR = B.M350_YEAR            \n"); 
    sb.append("    AND  A.M390_PARTCODE = B.M350_PARTCODE    \n");  
    sb.append("    AND  A.M390_YEAR = NVL(?, TO_CHAR(SYSDATE, 'YYYY')) \n"); 
		sb.append("    AND  A.M390_ACCGUBUN = NVL(?, 'A')        \n"); 
		sb.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME    \n");
		sb.append("   ORDER BY A.M390_PARTCODE                   \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    parameters.setString(idx++, paramInfo.getString("year1"));
		parameters.setString(idx++, paramInfo.getString("accGubun1"));
        
    return template.getList(conn, parameters);
  }


	/* 사용자 세목코드 조회 */
	public static List<CommonEntity> getUserCodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    
		
	  sb.append("  SELECT  A.M390_YEAR,                                                                                                                   \n");
  	sb.append("          A.M390_PARTCODE,                                                                                                               \n");
   	sb.append("          DECODE(A.M390_ACCGUBUN,'A','일반회계','B','특별회계','C','공기업특별회계','D','세입세출외현금','E','기금') M390_ACCGUBUN_NAME, \n");
    sb.append("          A.M390_ACCGUBUN,                                                                                                                 \n");
    sb.append("          A.M390_ACCCODE,                                                                                                                \n");
   	sb.append("          A.M390_SEMOKCODE,                                                                                                              \n");
   	sb.append("          DECODE(A.M390_WORKGUBUN,'0','세입','1','세출','2','자금배정','3','자금운용','4','세입세출외현금') M390_WORKGUBUN_NAME,              \n");
    sb.append("          A.M390_WORKGUBUN,                                                                                                              \n");
   	sb.append("          B.M370_SEMOKNAME,                                                                                                              \n");
   	sb.append("          C.M360_ACCNAME,                                                                                                                \n");
   	sb.append("          DECODE(B.M370_SEGUMGUBUN,'1','지방세','2','국세') M370_SEGUMGUBUN,                                                             \n");
  	sb.append("          DECODE(B.M370_MOKGUBUN,'0','장','1','관','2','항','3','목') M370_MOKGUBUN                                                      \n"); 
   	sb.append("    FROM  M390_USESEMOKCODE_T A                                                                                                          \n");
  	sb.append("         ,M370_SEMOKCODE_T B                                                                                                             \n");
  	sb.append("         ,M360_ACCCODE_T C                                                                                                               \n");
	  sb.append("   WHERE  A.M390_YEAR = B.M370_YEAR                                                                                                      \n");
  	sb.append("     AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                                                                                              \n");
 	  sb.append("     AND  A.M390_ACCCODE = B.M370_ACCCODE                                                                                                \n");
	  sb.append("     AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                                                                                            \n");
   	sb.append("     AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                                                                                            \n");
   	sb.append("     AND  A.M390_YEAR = C.M360_YEAR                                                                                                      \n");
  	sb.append("     AND  A.M390_ACCGUBUN = C.M360_ACCGUBUN                                                                                              \n");
 	  sb.append("     AND  A.M390_ACCCODE = C.M360_ACCCODE                                                                                                \n");
		sb.append("     AND  A.M390_YEAR = ?                                                                                                                \n");
		sb.append("     AND  A.M390_ACCGUBUN = ?                                                                                                            \n");
		sb.append("     AND  A.M390_PARTCODE = ?                                                                                                            \n");
	  sb.append("     AND  A.M390_ACCCODE = ?                                                                                                            \n");
		

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		     
		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year1"));
		parameters.setString(idx++, paramInfo.getString("accGubun1"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
	  parameters.setString(idx++, paramInfo.getString("accCode"));

		return template.getList(conn, parameters);
	}
	 /* 사용 코드 세목 등록 */
	 public static int insertUserCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M390_USESEMOKCODE_T   \n");
		sb.append("           ( M390_YEAR,            \n"); 
		sb.append("	            M390_PARTCODE,        \n"); 
		sb.append("	            M390_ACCGUBUN,        \n"); 
		sb.append("	            M390_ACCCODE,         \n");
		sb.append("             M390_SEMOKCODE,       \n");
		sb.append("	            M390_WORKGUBUN        \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("								?           )       \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("part_code"));
    parameters.setString(i++, paramInfo.getString("accGubun"));
		parameters.setString(i++, paramInfo.getString("acc_code"));
		parameters.setString(i++, paramInfo.getString("semok_code"));
		parameters.setString(i++, paramInfo.getString("workGubun"));

		return template.insert(conn, parameters); 
	}
		
		/* 사용코드 세목 삭제 */ 
  public static int deleteUserCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M390_USESEMOKCODE_T   \n");
    sb.append("       WHERE M390_YEAR       = ?   \n");
		sb.append("         AND M390_PARTCODE   = ?   \n");
		sb.append("         AND M390_ACCGUBUN   = ?   \n");
		sb.append("         AND M390_ACCCODE    = ?   \n");
		sb.append("         AND M390_SEMOKCODE  = ?   \n");
		sb.append("         AND M390_WORKGUBUN  = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("partCode"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
    parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
	
    return template.delete(conn, parameters);
  }
}

