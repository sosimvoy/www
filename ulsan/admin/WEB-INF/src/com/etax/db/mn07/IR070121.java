/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070122.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 사회복지기금및 의료급여기금특별회계 세입,세출 일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070121 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		             
		sb.append("	SELECT SUM(A.M220_AMTSEIPJEONILTOT) M220_AMTSEIPJEONILTOT	                                                                   \n");
    sb.append("			  ,SUM(A.M220_AMTSEIP) M220_AMTSEIP                                                                                      \n");            
    sb.append("			  ,SUM(A.M220_AMTGWAONAPJEONILTOT) M220_AMTGWAONAPJEONILTOT		                                                           \n");
    sb.append("			  ,SUM(A.M220_AMTSEIPGWAONAP) M220_AMTSEIPGWAONAP				                                                                 \n");
    sb.append("			  ,SUM(A.M220_AMTSEIPJEONGJEONG) M220_AMTSEIPJEONGJEONG			                                                             \n");                                  
    sb.append("			  ,SUM(A.M220_AMTPASTSEIPJEONILTOT) M220_AMTPASTSEIPJEONILTOT				                                                     \n");
    sb.append("			  ,SUM(A.M220_AMTPASTSEIP) M220_AMTPASTSEIP						                                                                   \n");
    sb.append("			  ,SUM(A.M220_AMTSECHULJEONILTOT)	M220_AMTSECHULJEONILTOT				                                                         \n");
    sb.append("			  ,SUM(A.M220_AMTSECHUL) M220_AMTSECHUL							                                                                     \n");
    sb.append("			  ,SUM(A.M220_AMTSECHULBANNAP) M220_AMTSECHULBANNAP						                                                           \n");
    sb.append("			  ,SUM(A.M220_AMTSECHULJEONGJEONG)	M220_AMTSECHULJEONGJEONG						                                                 \n");                         
    sb.append("			  ,SUM(A.M220_AMTSURPLUSJEONILTOT) M220_AMTSURPLUSJEONILTOT                                                              \n");
    sb.append("			  ,SUM(A.M220_AMTSURPLUS)	M220_AMTSURPLUS								                                                                 \n");
    sb.append("			  ,SUM(A.M220_AMTJEONGGI)	M220_AMTJEONGGI							                                                                   \n");
    sb.append("			  ,SUM(A.M220_AMTGONGGEUM) M220_AMTGONGGEUM								                                                               \n");
		sb.append("			  ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) SEIPTOTAMT           \n");
		sb.append("			  ,SUM(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG) SECHULTOTAMT  \n");
		sb.append("			  ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)-                    \n");
		sb.append("				(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)) JANAMT           \n");
		sb.append("			  ,B.M360_ACCNAME                                                                                                        \n");
		sb.append("       ,SUM(M220_AMTSURPLUSJEONILTOT + M220_AMTSURPLUS) SUMSURPLUS                                                            \n");
    sb.append("       ,SUM(((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)-                   \n");
    sb.append("       (A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG))- M220_AMTJEONGGI -  M220_AMTGONGGEUM) SURPLUSJAN  \n");
		sb.append("	FROM   M220_DAY_T A                                                                                                       \n");
		sb.append("			  ,M360_ACCCODE_T B                                                                                                   \n");
		sb.append(" WHERE  A.M220_YEAR = B.M360_YEAR                                                                                          \n");
		sb.append("	  AND  A.M220_ACCTYPE = B.M360_ACCGUBUN                                                                                   \n");
		sb.append("	  AND  A.M220_ACCCODE = B.M360_ACCCODE                                                                                    \n");
		sb.append("	  AND (A.M220_ACCTYPE = 'E' AND A.M220_ACCCODE IN('16','17','18')                                                         \n");
		sb.append("				OR A.M220_ACCTYPE = 'B' AND A.M220_ACCCODE= '04')                                                                   \n");              
		sb.append("	  AND  A.M220_YEAR = ?                                                                                                    \n");   
		sb.append("	  AND  A.M220_DATE = ?                                                                                                    \n");
		sb.append("	 GROUP BY B.M360_ACCNAME                                                                                                  \n");
    sb.append("  ORDER BY CASE                                                                                                            \n");
    sb.append("           WHEN M360_ACCNAME = '기초생활보호계정' THEN '00000'                                                             \n");
    sb.append("           WHEN M360_ACCNAME = '노인복지계정' THEN '00001'																																	\n");
    sb.append("           WHEN M360_ACCNAME = '저출산대책계정' THEN '00002'																																\n");
    sb.append("           WHEN M360_ACCNAME = '의료급여기금특별' THEN '00003'																															\n");
    sb.append("           ELSE '99999' END																																																\n");
																																																																				
		QueryTemplate template = new QueryTemplate(sb.toString());																																						
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getList(conn, parameters);
	}
  
	/* 총계 조회 */
	public static CommonEntity getTotReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
	StringBuffer sb = new StringBuffer();

	sb.append("SELECT SUM(M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT                                                                         \n");                
  sb.append("      ,SUM(M220_AMTSEIP) AMTSEIP                                                                                           \n");
  sb.append("      ,SUM(M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP                                                                             \n");
  sb.append("      ,SUM(M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG                                                                       \n");
  sb.append("      ,SUM(M220_AMTSEIPJEONILTOT + M220_AMTSEIP - M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) ALLSEIPTOTAMT              \n");                                      
  sb.append("      ,SUM(M220_AMTSECHULJEONILTOT) AMTSECHULJEONILTOT                                                                     \n");
  sb.append("      ,SUM(M220_AMTSECHUL) AMTSECHUL                                                                                       \n");
  sb.append("      ,SUM(M220_AMTSECHULBANNAP) AMTSECHULBANNAP                                                                           \n");
  sb.append("      ,SUM(M220_AMTSECHULJEONGJEONG) AMTSECHULJEONGJEONG                                                                   \n");
  sb.append("      ,SUM(M220_AMTSURPLUSJEONILTOT) AMTSURPLUSJEONILTOT                                                                   \n");
  sb.append("      ,SUM(M220_AMTSECHULJEONILTOT + M220_AMTSECHUL - M220_AMTSECHULBANNAP - M220_AMTSECHULJEONGJEONG) ALLSECHULTOTAMT     \n");  
  sb.append("      ,SUM(M220_AMTSURPLUS) AMTSURPLUS                                                                                     \n");
  sb.append("      ,SUM(M220_AMTJEONGGI) AMTJEONGGI                                                                                     \n");
  sb.append("      ,SUM(M220_AMTGONGGEUM) AMTGONGGEUM                                                                                   \n");
  sb.append("      ,(SUM(M220_AMTSEIPJEONILTOT + M220_AMTSEIP - M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) -                         \n");
  sb.append("        SUM(M220_AMTSECHULJEONILTOT + M220_AMTSECHUL - M220_AMTSECHULBANNAP - M220_AMTSECHULJEONGJEONG))ALLJANAMT          \n");                                 
  sb.append(" FROM  M220_DAY_T                                                                                                          \n");                                                                                                                                                                          
  sb.append("WHERE (M220_ACCTYPE = 'E' AND M220_ACCCODE IN('16','17','18')                                                              \n");
  sb.append("      OR M220_ACCTYPE = 'B' AND M220_ACCCODE= '04')                                                                        \n");
  sb.append("  AND  M220_YEAR = ?                                                                                                       \n");                                                                                              
  sb.append("  AND  M220_DATE = ?                                                                                                       \n");

                                                                                                    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}
}