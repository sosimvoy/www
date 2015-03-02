/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070119.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 기반시설부담금특별회계세입일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070119 {

    /* 보고서 조회(본청) */
	public static CommonEntity getReportData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                                                                              \n");
		sb.append("      ,C.M350_PARTNAME                                                                                                              \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))    TODAY_01    \n");    // 금일수입 : 과태료    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))    TODAY_02    \n");    // 금일수입 : 교통유발  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))    TODAY_03    \n");    // 금일수입 : 이자수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))    TODAY_04    \n");    // 금일수입 : 기타사용료
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))    TODAY_05    \n");    // 금일수입 : 기타잡수입
		sb.append("      ,SUM(A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)					                       TODAY_99    \n");    // 금일수입 : 합계      
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)),0))  TODAYTOT_01   \n");  // 금일누계 : 과태료      (세입전일누계 + 본청금일수입) 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)),0))  TODAYTOT_02   \n");  // 금일누계 : 교통유발                                  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)),0))  TODAYTOT_03   \n");  // 금일누계 : 이자수입                                  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)),0))  TODAYTOT_04   \n");  // 금일누계 : 기타사용료                                
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)),0))  TODAYTOT_05   \n");  // 금일누계 : 기타잡수입                                
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + (A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))		                                 TODAYTOT_99   \n");  // 금일누계 : 합계                                      
		
        sb.append("  FROM M220_DAY_T A                                                                  \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                                        \n");
		sb.append("      ,M350_PARTCODE_T C                                                             \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                                     \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                             \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                              \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                               \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                                           \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                                     \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                             \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'					                                    \n"); // 업무구분:세입
		sb.append("   AND A.M220_ACCTYPE = 'B'					                                        \n"); // 회계구분
		sb.append("   AND A.M220_ACCCODE = '68'						                                    \n"); // 회계코드
		sb.append("   AND A.M220_SEMOKCODE IN ('2110100','2110200','2110300','2110400','2110500')	    \n"); // 세목 전체
		sb.append("   AND A.M220_PARTCODE = '00000'		                                                \n"); // 부서 (본청) 

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

		sb.append(" GROUP BY A.M220_PARTCODE,C.M350_PARTNAME \n");

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

	/* 보고서 조회(구군청) */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		    sb.append("  SELECT  B.M390_PARTCODE                                                                                                                              \n");                                           
			sb.append("         ,C.M350_PARTNAME                                                                                                                                \n"); 
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPJEONILTOT,0)),0) JEONIL_01                                                             \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPJEONILTOT,0)),0) JEONIL_02                                                             \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPJEONILTOT,0)),0) JEONIL_03                                                             \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPJEONILTOT,0)),0) JEONIL_04                                                             \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPJEONILTOT,0)),0) JEONIL_05                                                             \n");
			sb.append("         ,NVL(SUM(A.M220_AMTSEIPJEONILTOT),0)                                      JEONIL_99                                                             \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIP,0)),0) TODAY_01                                                                       \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIP,0)),0) TODAY_02                                                                       \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIP,0)),0) TODAY_03                                                                       \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIP,0)),0) TODAY_04                                                                       \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIP,0)),0) TODAY_05                                                                       \n");
			sb.append("         ,NVL(SUM(A.M220_AMTSEIP),0)                                       TODAY_99                                                                      \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPGWAONAP,0)),0) GWAO_01                                              		                \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPGWAONAP,0)),0) GWAO_02                                                                 \n");           
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPGWAONAP,0)),0) GWAO_03                                                                 \n");           
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPGWAONAP,0)),0) GWAO_04                                                                 \n");              
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPGWAONAP,0)),0) GWAO_05                                                                 \n");              
			sb.append("         ,NVL(SUM(A.M220_AMTSEIPGWAONAP),0)                                      GWAO_99                                                                 \n");                                 
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)),0) GWAOTOT_01            		                \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)),0) GWAOTOT_02           		                \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)),0) GWAOTOT_03            		                \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)),0) GWAOTOT_04            		                \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)),0) GWAOTOT_05            		                \n");
			sb.append("         ,NVL(SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)                                         GWAOTOT_99            		              \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0)),0)    GWAOMOK_01           \n");                                               
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0)),0)    GWAOMOK_02           \n");                                               
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0)),0)    GWAOMOK_03           \n");                     
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0)),0)    GWAOMOK_04           \n");                                               
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0)),0)    GWAOMOK_05           \n");                                                
			sb.append("         ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP),0) GWAOMOK_99                                                        \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)),0)  TODAYTOT_01  \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)),0)  TODAYTOT_02  \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)),0)  TODAYTOT_03  \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)),0)  TODAYTOT_04  \n");
			sb.append("         ,NVL(SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)),0)  TODAYTOT_05  \n");
			sb.append("         ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0)  TODAYTOT_99   \n");
			sb.append("    FROM  M220_DAY_T A                                                                                                                                   \n");      
			sb.append("         ,M390_USESEMOKCODE_T B  															                                                                                          \n");
			sb.append("         ,M350_PARTCODE_T C                                                                                                                              \n");                                                                                         
			sb.append("   WHERE A.M220_YEAR(+) = B.M390_YEAR                                                                                                                    \n"); 
			sb.append("     AND A.M220_PARTCODE(+) = B.M390_PARTCODE                                                                                                            \n");          
			sb.append("     AND A.M220_ACCTYPE(+) = B.M390_ACCGUBUN                                                                                                             \n");   
			sb.append("     AND A.M220_ACCCODE(+) = B.M390_ACCCODE                                                                                                              \n");   
			sb.append("     AND A.M220_SEMOKCODE(+) = B.M390_SEMOKCODE                                                                                                          \n");            
			sb.append("     AND B.M390_YEAR(+) = C.M350_YEAR                                                                                                                    \n");                    
			sb.append("     AND B.M390_PARTCODE(+) = C.M350_PARTCODE                                                                                                            \n");                   
			sb.append("     AND B.M390_WORKGUBUN = '0'                                                                                                                          \n");             
			sb.append("     AND A.M220_ACCTYPE(+) = 'B'                                                                                                                         \n");                
			sb.append("     AND A.M220_ACCCODE(+) = '68'                                                                                                                        \n");             
			sb.append("     AND B.M390_SEMOKCODE IN ('2110100','2110200','2110300','2110400','2110500')                                                                         \n");             
			sb.append("     AND B.M390_PARTCODE IN ('00110','00140','00170','00200','00710')                                                                                    \n");                                                            
			sb.append("     AND A.M220_YEAR(+) = ?                                                                                                                              \n");
			sb.append("     AND A.M220_DATE(+) = ?                                                                                                 			                        \n");
			
			if(!paramInfo.getString("acc_type").equals("")){
			sb.append("      AND A.M220_ACCTYPE = ?								                                                                                                              \n");
											 }
			if(!paramInfo.getString("part_code").equals("")){
			sb.append("      AND A.M220_PARTCODE = ?							                                                                                     	                       \n");
											 }
			if(!paramInfo.getString("acc_code").equals("")){
			sb.append("      AND A.M220_ACCCODE = ?							                                                                                                               \n");
											 }
			sb.append(" GROUP BY  B.M390_PARTCODE,C.M350_PARTNAME                                                                                         \n");
			sb.append(" ORDER BY  B.M390_PARTCODE                                                                                                          \n");
			
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
		 
			return template.getList(conn, parameters);
		}

    /* 보고서 조회(합계) */
	public static CommonEntity getTotalData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01           \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02           \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03           \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04           \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05           \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99           \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIP,0)) TODAY_01                     \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIP,0)) TODAY_02                     \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIP,0)) TODAY_03                     \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIP,0)) TODAY_04                     \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIP,0)) TODAY_05                     \n");
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99                     \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01		        \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02		        \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03		        \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_04		        \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_05		        \n");
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99		        \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	\n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	\n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	\n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	\n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05	\n");
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	\n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0))    GWAOMOK_01      \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0))    GWAOMOK_02      \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0))    GWAOMOK_03      \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0))    GWAOMOK_04      \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP,0))    GWAOMOK_05      \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIP)    GWAOMOK_99      \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01  \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02  \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03  \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04  \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'2110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05  \n");
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99  \n");
		
        sb.append("  FROM M220_DAY_T A                                                                  \n");
		sb.append("      ,M390_USESEMOKCODE_T B                                                         \n");
		sb.append("      ,M350_PARTCODE_T C                                                             \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                                     \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                             \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                              \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                               \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                                           \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                                     \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                             \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                                        \n");
		sb.append("   AND A.M220_ACCTYPE = 'B'                                                          \n");
		sb.append("   AND A.M220_ACCCODE = '68'                                                         \n");
		sb.append("   AND A.M220_SEMOKCODE IN ('2110100','2110200','2110300','2110400','2110500')       \n");
		sb.append("   AND A.M220_PARTCODE IN ('00000','00110','00140','00170','00200','00710')		    \n");
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
