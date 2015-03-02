/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR010510.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입 > 수기분 조회/수정/삭제
******************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010510 {
  
	/* 회계명 조회 */ 
	public static List<CommonEntity> getAccCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT A.M360_ACCCODE, A.M360_ACCNAME       \n");		
    sb.append("   FROM M360_ACCCODE_T A						  	      \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                \n"); 
		sb.append("  WHERE A.M360_YEAR = B.M390_YEAR            \n");  
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN    \n");  
	  sb.append("    AND A.M360_ACCCODE = B. M390_ACCCODE     \n");
		sb.append("    AND B.M390_WORKGUBUN = '0'               \n");
		sb.append("    AND A.M360_YEAR = ?                      \n"); 
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M360_ACCGUBUN = ?                  \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                  \n");																									 
		}

		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME     \n"); 																																												
		sb.append(" ORDER BY A.M360_ACCCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
    if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}	
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}	

    return template.getList(conn, parameters);
  }

	/* 부서명 조회 */
	public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT A.M350_PARTCODE, A.M350_PARTNAME 	    \n");  
    sb.append("   FROM M350_PARTCODE_T A				   			      \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                  \n");       
    sb.append("  WHERE A.M350_YEAR =  B.M390_YEAR             \n");   
		sb.append("    AND A.M350_PARTCODE =  B.M390_PARTCODE     \n");   
		sb.append("    AND B.M390_WORKGUBUN = '0'                 \n");   
		sb.append("    AND A.M350_YEAR = ?                        \n"); 
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND B.M390_ACCGUBUN = ?                    \n");																									 
		}
		sb.append("   GROUP BY A.M350_PARTCODE, A.M350_PARTNAME 	\n");
		sb.append("   ORDER BY A.M350_PARTCODE                    \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}    
    return template.getList(conn, parameters);
  }
	
	 /* 세목명 조회 */ 
	public static List<CommonEntity> getSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																											
		sb.append(" SELECT A.M370_SEMOKCODE, A.M370_SEMOKNAME     \n");		
    sb.append("   FROM M370_SEMOKCODE_T A                     \n");   
		sb.append("       ,M390_USESEMOKCODE_T B                  \n");       
    sb.append("  WHERE A.M370_YEAR = M390_YEAR                \n");
		sb.append("    AND A.M370_ACCGUBUN =  B.M390_ACCGUBUN     \n"); 
		sb.append("    AND A.M370_ACCCODE =  B.M390_ACCCODE       \n"); 
		sb.append("    AND A.M370_WORKGUBUN = B.M390_WORKGUBUN    \n");
		sb.append("    AND A.M370_SEMOKCODE =  B.M390_SEMOKCODE   \n");
		sb.append("    AND A.M370_WORKGUBUN = '0'                 \n");
		sb.append("    AND A.M370_YEAR = ?                        \n");
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M370_ACCGUBUN = ?                    \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                    \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M370_ACCCODE = ?                     \n");																									 
		}
		sb.append(" GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME   \n"); 
		sb.append(" ORDER BY A.M370_SEMOKCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		} 
		if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		} 

    return template.getList(conn, parameters);
  }

  /* 수납기관 조회 */ 
	public static List<CommonEntity> getGigwanList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  M430_SUNAPGIGWANCODE       \n");		
    sb.append(" 			 ,M430_SUNAPGIGWANNAME 	     \n"); 
		sb.append("   FROM  M430_SUNAPGIGWANCODE_T     \n");                    
 
		QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getList(conn);
  }

	/* 수기분조회 */
	public static List<CommonEntity> getExpWriteList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT  B.M350_PARTNAME                                   \n");
		sb.append("        ,C.M360_ACCNAME                                    \n");	
		sb.append("        ,D.M370_SEMOKNAME                                  \n");	
		sb.append("        ,E.M430_SUNAPGIGWANNAME                            \n");	
		sb.append("		     ,A.M010_SEQ                                        \n");   //일련번호    
		sb.append("        ,A.M010_YEAR                                       \n");	  //회계연도
		sb.append("	       ,A.M010_DATE                                       \n");   //회계일자
		sb.append("	       ,A.M010_INTYPE                                     \n");   //입력구분
		sb.append("        ,DECODE(A.M010_INTYPE, 'I1', '세입',               \n");	
    sb.append("                               'I2', '과오납',             \n");
    sb.append("                               'I3', '정정'                \n");
    sb.append("               ) M010_INTYPE_NM                            \n");
		sb.append("	       ,A.M010_YEARTYPE                                   \n");   //년도구분
		sb.append("        ,DECODE(A.M010_YEARTYPE, 'Y1', '현년도',           \n");	
    sb.append("                                 'Y2', '과년도'            \n");
    sb.append("               ) M010_YEARTYPE_NM                          \n");
    sb.append("        ,A.M010_SUNAPGIGWANCODE                            \n");   //수납기관
		sb.append("	       ,A.M010_ACCTYPE                                    \n");   //회계구분
		sb.append("        ,DECODE(A.M010_ACCTYPE, 'A', '일반회계',           \n");	 
    sb.append("                                'B', '특별회계',           \n");
    sb.append("                                'E', '기금'                \n");
    sb.append("               ) M010_ACCTYPE_NM                           \n");
		sb.append("		     ,A.M010_PARTCODE                                   \n");   //부서
		sb.append("		     ,A.M010_ACCCODE                                    \n");   //회계명
		sb.append("		     ,A.M010_SEMOKCODE                                  \n");   //세목명
		sb.append("		     ,A.M010_AMT                                        \n");   //금액
		sb.append("		     ,A.M010_CNT                                        \n");   //건수
		sb.append("   FROM  M010_TAXIN_T A                                    \n");
		sb.append("        ,M350_PARTCODE_T B                                 \n");	 //부서 조인
		sb.append("        ,M360_ACCCODE_T C                                  \n");	 //회계명 조인	
		sb.append("        ,M370_SEMOKCODE_T D                                \n");	 //세목명 조인
		sb.append("        ,M430_SUNAPGIGWANCODE_T E                          \n");	 //수납기관 조인
		sb.append("  WHERE  A.M010_YEAR = B.M350_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = C.M360_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = D.M370_YEAR                         \n");
    sb.append("    AND  A.M010_ACCCODE = C.M360_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = C.M360_ACCGUBUN                  \n");
		sb.append("    AND  A.M010_PARTCODE = B.M350_PARTCODE                 \n");
		sb.append("    AND  A.M010_SEMOKCODE = D.M370_SEMOKCODE               \n");
		sb.append("    AND  A.M010_ACCCODE = D.M370_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = D.M370_ACCGUBUN                  \n");
    sb.append("    AND  D.M370_WORKGUBUN = '0'                            \n");
		sb.append("    AND  A.M010_SUNAPGIGWANCODE = E.M430_SUNAPGIGWANCODE   \n");	
    sb.append("    AND  A.M010_YEAR = ?                                   \n");
		sb.append("    AND  A.M010_DATE = ?                                   \n");
    if (!"".equals(paramInfo.getString("intype"))){
		  sb.append("	 AND A.M010_INTYPE = ?                                  \n");																									 
		}
    if (!"".equals(paramInfo.getString("gigwan"))){
		  sb.append("	 AND A.M010_SUNAPGIGWANCODE = ?                         \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_type"))){
		  sb.append("	 AND A.M010_ACCTYPE = ?                                 \n");																									 
		}
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND A.M010_PARTCODE = ?                                \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M010_ACCCODE = ?                                 \n");																									 
		}
		if (!"".equals(paramInfo.getString("semok_code"))){
		  sb.append("	 AND A.M010_SEMOKCODE = ?                               \n");																									 
		}
		if (!"".equals(paramInfo.getString("amt"))){
		  sb.append("	 AND A.M010_AMT = ?                                     \n");																									 
		}
    sb.append("    ORDER BY A.M010_SEQ                                    \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_date"));
		if (!"".equals(paramInfo.getString("intype")) )	{
			parameters.setString(idx++, paramInfo.getString("intype"));
		}
    if (!"".equals(paramInfo.getString("gigwan")) )	{
			parameters.setString(idx++, paramInfo.getString("gigwan"));
		}
		if (!"".equals(paramInfo.getString("acc_type")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		} 
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		} 
    if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		if (!"".equals(paramInfo.getString("semok_code")) )	{
			parameters.setString(idx++, paramInfo.getString("semok_code"));
		} 
		if (!"".equals(paramInfo.getString("amt")) )	{
			parameters.setString(idx++, paramInfo.getString("amt"));
		} 
   
    return template.getList(conn, parameters);
  }


  /* 수기분 상세 */
  public static CommonEntity getExpWriteView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT  B.M350_PARTNAME                                   \n");
		sb.append("        ,C.M360_ACCNAME                                    \n");	
		sb.append("        ,D.M370_SEMOKNAME                                  \n");	
		sb.append("        ,E.M430_SUNAPGIGWANNAME                            \n");	
		sb.append("		     ,A.M010_SEQ                                        \n");   //일련번호    
		sb.append("        ,A.M010_YEAR                                       \n");	  //회계연도
		sb.append("	       ,A.M010_DATE                                       \n");   //회계일자
		sb.append("	       ,A.M010_INTYPE                                     \n");   //입력구분
		sb.append("        ,DECODE(A.M010_INTYPE, 'I1', '세입',               \n");	
    sb.append("                               'I2', '과오납',             \n");
    sb.append("                               'I3', '정정'                \n");
    sb.append("               ) M010_INTYPE_NM                            \n");
		sb.append("	       ,A.M010_YEARTYPE                                   \n");   //년도구분
		sb.append("        ,DECODE(A.M010_YEARTYPE, 'Y1', '현년도',           \n");	
    sb.append("                                 'Y2', '과년도'            \n");
    sb.append("               ) M010_YEARTYPE_NM                          \n");
    sb.append("        ,A.M010_SUNAPGIGWANCODE                            \n");   //수납기관
		sb.append("	       ,A.M010_ACCTYPE                                    \n");   //회계구분
		sb.append("        ,DECODE(A.M010_ACCTYPE, 'A', '일반회계',           \n");	 
    sb.append("                                'B', '특별회계',           \n");
    sb.append("                                'E', '기금'                \n");
    sb.append("               ) M010_ACCTYPE_NM                           \n");
		sb.append("		     ,A.M010_PARTCODE                                   \n");   //부서
		sb.append("		     ,A.M010_ACCCODE                                    \n");   //회계명
		sb.append("		     ,A.M010_SEMOKCODE                                  \n");   //세목명
		sb.append("		     ,A.M010_AMT                                        \n");   //금액
		sb.append("		     ,A.M010_CNT                                        \n");   //건수
		sb.append("   FROM  M010_TAXIN_T A                                    \n");
		sb.append("        ,M350_PARTCODE_T B                                 \n");	 //부서 조인
		sb.append("        ,M360_ACCCODE_T C                                  \n");	 //회계명 조인	
		sb.append("        ,M370_SEMOKCODE_T D                                \n");	 //세목명 조인
		sb.append("        ,M430_SUNAPGIGWANCODE_T E                          \n");	 //수납기관 조인
		sb.append("  WHERE  A.M010_YEAR = B.M350_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = C.M360_YEAR                         \n");
    sb.append("    AND  A.M010_YEAR = D.M370_YEAR                         \n");
    sb.append("    AND  A.M010_ACCCODE = C.M360_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = C.M360_ACCGUBUN                  \n");
		sb.append("    AND  A.M010_PARTCODE = B.M350_PARTCODE                 \n");
		sb.append("    AND  A.M010_SEMOKCODE = D.M370_SEMOKCODE               \n");
		sb.append("    AND  A.M010_ACCCODE = D.M370_ACCCODE                   \n");
		sb.append("    AND  A.M010_ACCTYPE = D.M370_ACCGUBUN                  \n");
    sb.append("    AND  D.M370_WORKGUBUN = '0'                            \n");
		sb.append("    AND  A.M010_SUNAPGIGWANCODE = E.M430_SUNAPGIGWANCODE   \n");	
		sb.append("    AND  A.M010_YEAR = ?	                                  \n");
		sb.append("		 AND  A.M010_SEQ = ?                                    \n");
    sb.append("    AND  A.M010_ACCTYPE = ?	                              \n");
		sb.append("		 AND  A.M010_PARTCODE = ?                               \n");
    sb.append("    AND  A.M010_ACCCODE = ?	                              \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;

	  parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("m010_seq"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
	
		return template.getData(conn, parameters);
	}


	/* 수기분 수정 (일일마감여부 'N')*/
	public static int expWriteUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M010_TAXIN_T                \n");
	  sb.append("	  SET M010_INTYPE = ?             \n");  //입력구분
		sb.append("      ,M010_YEARTYPE = ? 		      \n");	 //년도구분
    sb.append("      ,M010_SUNAPGIGWANCODE = ? 		\n");	 //수납기관
		sb.append("      ,M010_ACCTYPE = ? 			      \n");	 //회계구분
		sb.append("	     ,M010_PARTCODE = ? 		      \n");	 //부서
		sb.append("      ,M010_ACCCODE = ? 			      \n");	 //회계명
		sb.append("	     ,M010_SEMOKCODE = ? 		      \n");	 //세목명
		sb.append("	     ,M010_AMT = ?					      \n");	 //금액
		sb.append("	     ,M010_CNT = ?					      \n");	 //건수
		sb.append(" WHERE M010_YEAR = ? 	            \n");
		sb.append("	  AND M010_SEQ = ?                \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("year_type"));
    parameters.setString(idx++, paramInfo.getString("gigwan"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));

		return template.update(conn, parameters);
	}

  /* 수기분 수정 (일일마감여부 'Y') */
	public static int expWriteUpdate1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M010_TAXIN_T                \n");
		sb.append("   SET M010_CNT = ? 		            \n");	 //건수
		sb.append(" WHERE M010_YEAR = ? 	            \n");
		sb.append("	  AND M010_SEQ = ?                \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));

		return template.update(conn, parameters);
	}


	/* 수기분 삭제 */
	public static int expWriteDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M010_TAXIN_T             \n");
		sb.append(" WHERE M010_YEAR = ?                 \n");	//회계연도
		sb.append("   AND M010_SEQ = ?                  \n");	//일련번호
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m010_seq"));
		
		return template.delete(conn, parameters);
	}
}	
