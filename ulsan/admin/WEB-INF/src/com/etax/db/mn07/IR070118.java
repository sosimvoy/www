/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070118.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 광역교통시설부담금세입일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070118 {

	/* 보고서 조회 */
	public static CommonEntity getReportData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                               \n");
		sb.append("      ,C.M350_PARTNAME                                                               \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01	\n");   // 전일누계 : 광역교통시설부담금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02	\n");   // 전일누계 : 이자수입              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03	\n");   // 전일누계 : 이월금                
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04	\n");   // 전일누계 : 내부전입금            
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99	\n");   // 전일누계 : 합계                  
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01             \n");  // 금일수입 : 광역교통시설부담금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02             \n");  // 금일수입 : 이자수입             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03             \n");  // 금일수입 : 이월금               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_04             \n");  // 금일수입 : 내부전입금           
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99             \n");  // 금일수입 : 합계                 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01		\n");  // 과오납 : 광역교통시설부담금 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02		\n");  // 과오납 : 이자수입           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03		\n");  // 과오납 : 이월금             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_04		\n");  // 과오납 : 내부전입금         
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99		\n");  // 과오납 : 합계               
		    
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	 \n");   // 과오납누계 : 광역교통시설부담금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	 \n");   // 과오납누계 : 이자수입             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	 \n");   // 과오납누계 : 이월금               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	 \n");   // 과오납누계 : 내부전입금           
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	 \n");   // 과오납누계 : 합계                 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_01 \n");     // 과목갱정 : 광역교통시설부담금
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_02 \n");     // 과목갱정 : 이자수입          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_03 \n");     // 과목갱정 : 이월금            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0)) GWAOMOK_04 \n");     // 과목갱정 : 내부전입금        
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG)		                                 GWAOMOK_99	\n");     // 과목갱정 : 합계              
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");   // 금일누계 : 광역교통시설부담금
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");   // 금일누계 : 이자수입          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");   // 금일누계 : 이월금            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");   // 금일누계 : 내부전입금        
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");   // 금일누계 : 합계              
		
        sb.append("  FROM M220_DAY_T A                                                      \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                            \n");
		sb.append("      ,M350_PARTCODE_T C                                                 \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                 \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                  \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                   \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                               \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                 \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                            \n");    // 업무구분:세입
		sb.append("   AND A.M220_ACCTYPE = 'B'                                              \n");    // 회계구분     
		sb.append("   AND A.M220_ACCCODE = '21'                                             \n");    // 회계코드     
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110400')     \n");    // 세목 전체    
		sb.append("   AND A.M220_PARTCODE  = '00000'                                        \n");    // 본청만 해당  

		// WHERE START
		sb.append("   AND A.M220_YEAR = ?										\n");
		sb.append("   AND A.M220_DATE = ?										\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND A.M220_ACCTYPE = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND A.M220_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND A.M220_ACCCODE = ?								\n");
		}
		// WHERE END

		sb.append(" GROUP BY A.M220_PARTCODE,C.M350_PARTNAME  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		if(!paramInfo.getString("acc_type").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if(!paramInfo.getString("part_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if(!paramInfo.getString("acc_code").equals("")){
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}

		return template.getData(conn, parameters);
	}
}