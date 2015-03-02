/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070122.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 공단환경오염이주대책 특별회계세입세출일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070120 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT  A.M220_YEAR ,A.M220_DATE                                                                                              \n");                                                          
		sb.append("       ,SUM(A.M220_AMTSEIPJEONILTOT)M220_AMTSEIPJEONILTOT  ,SUM(A.M220_AMTSEIP)M220_AMTSEIP                                   \n");
		sb.append("       ,SUM(A.M220_AMTSEIPGWAONAP)M220_AMTSEIPGWAONAP ,SUM(A.M220_AMTSEIPJEONGJEONG)M220_AMTSEIPJEONGJEONG                    \n");
		sb.append("       ,SUM(A.M220_AMTSECHULJEONILTOT)M220_AMTSECHULJEONILTOT ,SUM(A.M220_AMTSECHUL) M220_AMTSECHUL                           \n");
		sb.append("       ,SUM(A.M220_AMTSECHULBANNAP)M220_AMTSECHULBANNAP ,SUM(A.M220_AMTSECHULJEONGJEONG) M220_AMTSECHULJEONGJEONG             \n");
		sb.append("       ,SUM(A.M220_AMTJEONGGI) M220_AMTJEONGGI ,SUM(A.M220_AMTGONGGEUM) M220_AMTGONGGEUM                                      \n");
		sb.append("       ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) SEIPTOTAMT           \n");
		sb.append("       ,SUM(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG) SECHULTOTAMT  \n");
		sb.append("       ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)-                    \n");
		sb.append("        (A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)) JANAMT          \n");
		sb.append("       ,B.M360_ACCNAME                                                                                                        \n");
		sb.append("       ,SUM(M220_AMTSURPLUSJEONILTOT + M220_AMTSURPLUS) SUMSURPLUS                                                            \n");
    sb.append("       ,SUM(((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)-                   \n");                  
    sb.append("       (A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG)) - M220_AMTJEONGGI -  M220_AMTGONGGEUM) SURPLUSJAN \n");       
		sb.append("  FROM  M220_DAY_T A                                                                                                          \n");                                                                          
		sb.append("       ,M360_ACCCODE_T B                                                                                                      \n");
		sb.append(" WHERE  M360_YEAR = M220_YEAR                                                                                                 \n");
		sb.append("   AND  M360_ACCGUBUN = M220_ACCTYPE                                                                                          \n");
		sb.append("   AND  M360_ACCCODE = M220_ACCCODE                                                                                           \n");
		sb.append("   AND  M220_YEAR = ?                                                                                                         \n");
		sb.append("   AND  M220_DATE = ?                                                                                                         \n");
		sb.append("   AND  M220_ACCTYPE = 'B'                                                                                                    \n");
		sb.append("   AND  M220_ACCCODE = 02                                                                                                     \n");
		sb.append("  GROUP BY  A.M220_YEAR ,A.M220_DATE,B.M360_ACCNAME                                                                           \n");                                                                                                                                                                                   
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getList(conn, parameters);
	}
		
		public static CommonEntity getTotReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("	SELECT A.M220_YEAR                                          \n");
    sb.append("	      ,A.M220_DATE                                          \n");
    sb.append("	      ,SUM(A.M220_AMTSEIPJEONILTOT) AMTSEIPJEONILTOT        \n");
    sb.append("	      ,SUM(A.M220_AMTSEIP) AMTSEIP                          \n");
    sb.append("	      ,SUM(A.M220_AMTSEIPGWAONAP) AMTSEIPGWAONAP            \n");
    sb.append("	      ,SUM(A.M220_AMTSEIPJEONGJEONG) AMTSEIPJEONGJEONG      \n");
    sb.append("	      ,SUM(A.M220_AMTSECHULJEONILTOT) AMTSECHULJEONILTOT    \n");
    sb.append("	      ,SUM(A.M220_AMTSECHUL) AMTSECHUL                      \n");
    sb.append("	      ,SUM(A.M220_AMTSECHULBANNAP) AMTSECHULBANNAP          \n");
    sb.append("	      ,SUM(A.M220_AMTSECHULJEONGJEONG) AMTSECHULJEONGJEONG  \n");
    sb.append("	      ,SUM(A.M220_AMTJEONGGI) AMTJEONGGI                    \n");
    sb.append("	      ,SUM(A.M220_AMTGONGGEUM) AMTGONGGEUM                  \n");
    sb.append("	      ,B.M360_ACCNAME                                       \n");
		sb.append("       ,SUM(M220_AMTSEIPJEONILTOT + M220_AMTSEIP - M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) ALLSEIPTOTAMT              \n");
    sb.append("       ,SUM(M220_AMTSECHULJEONILTOT + M220_AMTSECHUL - M220_AMTSECHULBANNAP - M220_AMTSECHULJEONGJEONG) ALLSECHULTOTAMT     \n");
    sb.append("       ,(SUM(M220_AMTSEIPJEONILTOT + M220_AMTSEIP - M220_AMTSEIPGWAONAP - M220_AMTSEIPJEONGJEONG) -                         \n");
    sb.append("         SUM(M220_AMTSECHULJEONILTOT + M220_AMTSECHUL - M220_AMTSECHULBANNAP - M220_AMTSECHULJEONGJEONG))ALLJANAMT          \n");
    sb.append("	 FROM M220_DAY_T A                                          \n");
		sb.append("      ,M360_ACCCODE_T B                                      \n");
    sb.append(" WHERE  M360_YEAR = M220_YEAR                                \n");
		sb.append("   AND  M360_ACCGUBUN = M220_ACCTYPE                         \n");
		sb.append("   AND  M360_ACCCODE = M220_ACCCODE                          \n");
		sb.append("	  AND A.M220_YEAR = ?                                       \n");
    sb.append("	  AND A.M220_DATE = ?                                       \n");
    sb.append("	  AND A.M220_ACCTYPE = 'B'                                  \n");
    sb.append("	  AND A.M220_ACCCODE =02                                    \n");
    sb.append("	GROUP BY A.M220_YEAR ,A.M220_DATE,B.M360_ACCNAME            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}
}