/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090810.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-20
* 프로그램내용	   : 시스템운영 > 세목코드관리
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090810 {

		public static List getbudgetSemokList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   
		sb.append("  SELECT  A.M370_YEAR,                                                                                                        \n");
   	sb.append("  DECODE (A.M370_ACCGUBUN,'A','일반회계','B','특별회계','C','공기업특별회계','D','세입세출외현금','E','기금') ACCGUBUN,       \n");
    sb.append("          A.M370_ACCGUBUN,                                                                                                    \n");
    sb.append("          A.M370_ACCCODE,                                                                                                     \n");
	  sb.append("  DECODE (A.M370_WORKGUBUN,'0','세입','1','세출','2','자금배정','3','자금운용','4','세입세출외현금') WORKGUBUN,               \n");
		sb.append("          A.M370_WORKGUBUN,                                                                                                   \n");
    sb.append("          A.M370_SEMOKCODE,                                                                                                   \n");
   	sb.append("          A.M370_SEMOKNAME,                                                                                                   \n");
	  sb.append("  DECODE (A.M370_SEGUMGUBUN,'1','지방세','2','국세') M370_SEGUMGUBUN,                                                         \n");
	  sb.append("  DECODE (A.M370_MOKGUBUN,'0','장','1','관','2','항','3','목') MOKGUBUN,                                                      \n");
    sb.append("          A.M370_MOKGUBUN,                                                                                                    \n");
 	  sb.append("          B.M360_ACCNAME                                                                                                      \n");
 	  sb.append("    FROM  M370_SEMOKCODE_T A                                                                                                  \n");
 	  sb.append("         ,M360_ACCCODE_T B                                                                                                    \n");                                                   
	  sb.append("   WHERE  A.M370_YEAR = B.M360_YEAR                                                                                           \n");
	  sb.append("     AND  A.M370_ACCCODE = B.M360_ACCCODE                                                                                     \n");
  	sb.append("     AND  A.M370_ACCGUBUN = B.M360_ACCGUBUN                                                                                   \n");
   	sb.append("     AND  M370_YEAR = ?                                                                                                    \n");                                                
  	sb.append("     AND  M370_ACCGUBUN = ?                                                                                                 \n");                                            
  	sb.append("     AND  M370_ACCCODE = ?                                                                                                   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("under_year"));
		parameters.setString(idx++, paramInfo.getString("accGubun1"));
		parameters.setString(idx++, paramInfo.getString("accCode"));

		return template.getList(conn, parameters);
	}
	/*세목코드 등록 */
	 public static int insertSemokCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M370_SEMOKCODE_T      \n");
		sb.append("           ( M370_YEAR,            \n"); 
		sb.append("	            M370_ACCGUBUN,        \n"); 
		sb.append("	            M370_ACCCODE,         \n");
		sb.append("             M370_SEMOKCODE,       \n");
		sb.append("	            M370_WORKGUBUN,       \n");
		sb.append("             M370_SEMOKNAME,       \n");
		sb.append("	            M370_SEGUMGUBUN,      \n"); 
    sb.append("	            M370_MOKGUBUN         \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
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
		parameters.setString(i++, paramInfo.getString("accGubun"));
    parameters.setString(i++, paramInfo.getString("acc_code"));
		parameters.setString(i++, paramInfo.getString("semokCode"));
		parameters.setString(i++, paramInfo.getString("workGubun"));
    parameters.setString(i++, paramInfo.getString("semokName"));
		parameters.setString(i++, paramInfo.getString("segumGubun"));
		parameters.setString(i++, paramInfo.getString("mokGubun"));

		return template.insert(conn, parameters); 
	}
		
		/* 세목코드 삭제 */ 
  public static int deleteSemokCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" DELETE FROM M370_SEMOKCODE_T      \n");
    sb.append("       WHERE M370_YEAR       = ?   \n");	
		sb.append("         AND M370_ACCGUBUN   = ?   \n");
		sb.append("         AND M370_ACCCODE    = ?   \n");
		sb.append("         AND M370_WORKGUBUN  = ?   \n");
		sb.append("	        AND M370_SEMOKCODE  = ?   \n");
		sb.append("         AND M370_MOKGUBUN  = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
   
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
    parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	  parameters.setString(idx++, paramInfo.getString("mokGubun"));
  
		return template.delete(conn, parameters);
  }

    
    /* 세목코드 상세 */
    public static CommonEntity getSemokData(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append("  SELECT M370_YEAR,                                                            \n");
        sb.append("         M370_ACCGUBUN,                                                        \n");
   	    sb.append("         M370_ACCCODE,                                                         \n");
	    sb.append("         M370_WORKGUBUN,                                                       \n");
        sb.append("         M370_SEMOKCODE,                                                       \n");
   	    sb.append("         M370_SEMOKNAME,                                                       \n");
	    sb.append("         M370_SEGUMGUBUN,                                                      \n");
	    sb.append("         DECODE (M370_MOKGUBUN,'0','장','1','관','2','항','3','목') MOKGUBUN,  \n");
        sb.append("         M370_MOKGUBUN                                                         \n");
 	    sb.append("    FROM  M370_SEMOKCODE_T A                                                   \n");
   	    sb.append("   WHERE  M370_YEAR = ?                                                        \n");                                                
  	    sb.append("     AND  M370_ACCGUBUN = ?                                                    \n");                                            
  	    sb.append("     AND  M370_ACCCODE = ?                                                     \n");
        sb.append("     AND  M370_WORKGUBUN = ?                                                   \n");
        sb.append("     AND  M370_SEMOKCODE = ?                                                   \n");
        sb.append("     AND  M370_MOKGUBUN = ?                                                    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
        parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	    parameters.setString(idx++, paramInfo.getString("mokGubun"));

		return template.getData(conn, parameters);
	}



    /* 세목명 수정 */
    public static int updateSemokCode(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append("  UPDATE M370_SEMOKCODE_T       \n");
        sb.append("     SET M370_SEMOKNAME = ?,    \n");
	    sb.append("         M370_SEGUMGUBUN = ?    \n");
   	    sb.append("   WHERE M370_YEAR = ?          \n");                                                
  	    sb.append("     AND M370_ACCGUBUN = ?      \n");                                            
  	    sb.append("     AND M370_ACCCODE = ?       \n");
        sb.append("     AND M370_WORKGUBUN = ?     \n");
        sb.append("     AND M370_SEMOKCODE = ?     \n");
        sb.append("     AND M370_MOKGUBUN = ?      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
        parameters.setString(idx++, paramInfo.getString("semokName"));
		parameters.setString(idx++, paramInfo.getString("segumGubun")); 
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
        parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	    parameters.setString(idx++, paramInfo.getString("mokGubun"));

		return template.update(conn, parameters);
	}
}
