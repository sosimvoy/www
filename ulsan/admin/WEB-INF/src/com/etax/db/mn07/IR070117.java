/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070117.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-13
* 프로그램내용   : 일계보고서 > 보고서 조회 > 교통사업특별회계세입일계표
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070117 {

	/* 보고서 조회(본청) */
	public static List<CommonEntity> getReportList1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                                   \n");
		sb.append("      ,C.M350_PARTNAME                                                                   \n");
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		\n");   // 전일누계 : 과태료                 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		\n");   // 전일누계 : 교통유발               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		\n");   // 전일누계 : 이자수입               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		\n");   // 전일누계 : 기타사용료             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		\n");   // 전일누계 : 기타잡수입             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		\n");   // 전일누계 : 시도비반               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		\n");   // 전일누계 : 체납처분               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		\n");   // 전일누계 : 이월                   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		\n");   // 전일누계 : 주차요금               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		\n");   // 전일누계 : 내부전입금             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		\n");   // 전일누계 : 국고보조금             
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		\n");   // 전일누계 : 합계                   
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01                 \n");        // 금일수입 : 과태료             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02                 \n");        // 금일수입 : 교통유발           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03                 \n");        // 금일수입 : 이자수입           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIP,0)) TODAY_04                 \n");        // 금일수입 : 기타사용료         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_05                 \n");        // 금일수입 : 기타잡수입         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIP,0)) TODAY_06                 \n");        // 금일수입 : 시도비반           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIP,0)) TODAY_07                 \n");        // 금일수입 : 체납처분           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIP,0)) TODAY_08                 \n");        // 금일수입 : 이월               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIP,0)) TODAY_09                 \n");        // 금일수입 : 주차요금           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIP,0)) TODAY_10                 \n");        // 금일수입 : 내부전입금         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIP,0)) TODAY_11                 \n");        // 금일수입 : 국고보조금         
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99                 \n");        // 금일수입 : 합계               
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01           \n");        // 과오납 : 과태료            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02           \n");        // 과오납 : 교통유발          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03           \n");        // 과오납 : 이자수입          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04           \n");        // 과오납 : 기타사용료        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05           \n");        // 과오납 : 기타잡수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06           \n");        // 과오납 : 시도비반          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07           \n");        // 과오납 : 체납처분          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08           \n");        // 과오납 : 이월              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09           \n");        // 과오납 : 주차요금          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10           \n");        // 과오납 : 내부전입금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11           \n");        // 과오납 : 국고보조금        
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99           \n");        // 과오납 : 합계              
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	   \n");   // 과오납누계 : 과태료       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	   \n");   // 과오납누계 : 교통유발     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	   \n");   // 과오납누계 : 이자수입     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	   \n");   // 과오납누계 : 기타사용료   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05	   \n");   // 과오납누계 : 기타잡수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06	   \n");   // 과오납누계 : 시도비반     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07	   \n");   // 과오납누계 : 체납처분     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08	   \n");   // 과오납누계 : 이월         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09	   \n");   // 과오납누계 : 주차요금     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10	   \n");   // 과오납누계 : 내부전입금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11	   \n");   // 과오납누계 : 국고보조금   
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	   \n");   // 과오납누계 : 합계         
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_01                             \n");   // 과목갱정 : 과태료          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_02                             \n");   // 과목갱정 : 교통유발        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_03                             \n");   // 과목갱정 : 이자수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_04                             \n");   // 과목갱정 : 기타사용료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_05                             \n");   // 과목갱정 : 기타잡수입      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_06                             \n");   // 과목갱정 : 시도비반        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_07                             \n");   // 과목갱정 : 체납처분        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_08                             \n");   // 과목갱정 : 이월            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_09                             \n");   // 과목갱정 : 주차요금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_10                             \n");   // 과목갱정 : 내부전입금      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_11                             \n");   // 과목갱정 : 국고보조금      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) 		                                GWAOMOK_99		                       \n");   // 과목갱정 : 합계            
		sb.append("                                                                                                                    \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");      // 금일누계 : 과태료     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");      // 금일누계 : 교통유발   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");      // 금일누계 : 이자수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");      // 금일누계 : 기타사용료 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");      // 금일누계 : 기타잡수입 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");      // 금일누계 : 시도비반   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");      // 금일누계 : 체납처분   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");      // 금일누계 : 이월       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");      // 금일누계 : 주차요금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");      // 금일누계 : 내부전입금 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");      // 금일누계 : 국고보조금 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");      // 금일누계 : 합계       
		
    sb.append("  FROM M220_DAY_T A                                                   \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                         \n");
		sb.append("      ,M350_PARTCODE_T C                                              \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                              \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                               \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                            \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                              \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'				                         \n");	// 업무구분:세입    
		sb.append("   AND A.M220_ACCTYPE = 'B'					                         \n");  // 회계구분             
		sb.append("   AND A.M220_ACCCODE = '31'					                         \n");  // 회계코드         
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'   \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                  \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                         \n");  // 세목 전체
		sb.append("   AND A.M220_PARTCODE IN ('00000')		                           \n");  // 부서 전체
    sb.append("   AND A.M220_SUNAPGIGWANCODE <> '310001'                         \n");  // 차량제외

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

		return template.getList(conn, parameters);
	}

	/* 보고서 조회(차량등록) */
	public static List<CommonEntity> getReportList3(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                                   \n");
		sb.append("      ,'울산차량등록' M350_PARTNAME                                                                   \n");
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		\n");   // 전일누계 : 과태료                 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		\n");   // 전일누계 : 교통유발               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		\n");   // 전일누계 : 이자수입               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		\n");   // 전일누계 : 기타사용료             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		\n");   // 전일누계 : 기타잡수입             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		\n");   // 전일누계 : 시도비반               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		\n");   // 전일누계 : 체납처분               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		\n");   // 전일누계 : 이월                   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		\n");   // 전일누계 : 주차요금               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		\n");   // 전일누계 : 내부전입금             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		\n");   // 전일누계 : 국고보조금             
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		\n");   // 전일누계 : 합계                   
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIP,0)) TODAY_01                 \n");        // 금일수입 : 과태료             
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIP,0)) TODAY_02                 \n");        // 금일수입 : 교통유발           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIP,0)) TODAY_03                 \n");        // 금일수입 : 이자수입           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIP,0)) TODAY_04                 \n");        // 금일수입 : 기타사용료         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIP,0)) TODAY_05                 \n");        // 금일수입 : 기타잡수입         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIP,0)) TODAY_06                 \n");        // 금일수입 : 시도비반           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIP,0)) TODAY_07                 \n");        // 금일수입 : 체납처분           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIP,0)) TODAY_08                 \n");        // 금일수입 : 이월               
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIP,0)) TODAY_09                 \n");        // 금일수입 : 주차요금           
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIP,0)) TODAY_10                 \n");        // 금일수입 : 내부전입금         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIP,0)) TODAY_11                 \n");        // 금일수입 : 국고보조금         
		sb.append("      ,SUM(A.M220_AMTSEIP)					                   TODAY_99                 \n");        // 금일수입 : 합계               
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01           \n");        // 과오납 : 과태료            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02           \n");        // 과오납 : 교통유발          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03           \n");        // 과오납 : 이자수입          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04           \n");        // 과오납 : 기타사용료        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05           \n");        // 과오납 : 기타잡수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06           \n");        // 과오납 : 시도비반          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07           \n");        // 과오납 : 체납처분          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08           \n");        // 과오납 : 이월              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09           \n");        // 과오납 : 주차요금          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10           \n");        // 과오납 : 내부전입금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11           \n");        // 과오납 : 국고보조금        
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99           \n");        // 과오납 : 합계              
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01	   \n");   // 과오납누계 : 과태료       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02	   \n");   // 과오납누계 : 교통유발     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03	   \n");   // 과오납누계 : 이자수입     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04	   \n");   // 과오납누계 : 기타사용료   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05	   \n");   // 과오납누계 : 기타잡수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06	   \n");   // 과오납누계 : 시도비반     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07	   \n");   // 과오납누계 : 체납처분     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08	   \n");   // 과오납누계 : 이월         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09	   \n");   // 과오납누계 : 주차요금     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10	   \n");   // 과오납누계 : 내부전입금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11	   \n");   // 과오납누계 : 국고보조금   
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	   \n");   // 과오납누계 : 합계         
		
    sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_01                             \n");   // 과목갱정 : 과태료          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_02                             \n");   // 과목갱정 : 교통유발        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_03                             \n");   // 과목갱정 : 이자수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_04                             \n");   // 과목갱정 : 기타사용료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_05                             \n");   // 과목갱정 : 기타잡수입      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_06                             \n");   // 과목갱정 : 시도비반        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_07                             \n");   // 과목갱정 : 체납처분        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_08                             \n");   // 과목갱정 : 이월            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_09                             \n");   // 과목갱정 : 주차요금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_10                             \n");   // 과목갱정 : 내부전입금      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONGJEONG,0))    GWAOMOK_11                             \n");   // 과목갱정 : 국고보조금      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONGJEONG) 		                                GWAOMOK_99		                       \n");   // 과목갱정 : 합계            
		sb.append("                                                                                                                    \n");
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");      // 금일누계 : 과태료     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");      // 금일누계 : 교통유발   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");      // 금일누계 : 이자수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");      // 금일누계 : 기타사용료 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");      // 금일누계 : 기타잡수입 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");      // 금일누계 : 시도비반   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");      // 금일누계 : 체납처분   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");      // 금일누계 : 이월       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");      // 금일누계 : 주차요금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");      // 금일누계 : 내부전입금 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");      // 금일누계 : 국고보조금 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");      // 금일누계 : 합계       
		
    sb.append("  FROM M220_DAY_T A                                                   \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                                         \n");
		sb.append("      ,M350_PARTCODE_T C                                              \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                              \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                               \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                            \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                      \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                              \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'				                         \n");	// 업무구분:세입    
		sb.append("   AND A.M220_ACCTYPE = 'B'					                         \n");  // 회계구분             
		sb.append("   AND A.M220_ACCCODE = '31'					                         \n");  // 회계코드         
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'   \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                  \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                         \n");  // 세목 전체
		sb.append("   AND A.M220_SUNAPGIGWANCODE = '310001'		                       \n");  // 차량

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

		return template.getList(conn, parameters);
	}

    /* 보고서 조회(각 구청) */
	public static List<CommonEntity> getReportList2(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_PARTCODE                                                               \n");
		sb.append("      ,C.M350_PARTNAME                                                               \n");
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01	\n");   // 전일누계 : 과태료                
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02	\n");   // 전일누계 : 교통유발              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03	\n");   // 전일누계 : 이자수입              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04	\n");   // 전일누계 : 기타사용료            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05	\n");   // 전일누계 : 기타잡수입            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06	\n");   // 전일누계 : 시도비반              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07	\n");   // 전일누계 : 체납처분              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08	\n");   // 전일누계 : 이월                  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09	\n");   // 전일누계 : 주차요금              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10	\n");   // 전일누계 : 내부전입금            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11	\n");   // 전일누계 : 국고보조금            
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99	\n");   // 전일누계 : 합계                  
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_01    \n");    // 현년도수입 : 과태료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_02    \n");    // 현년도수입 : 교통유발    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_03    \n");    // 현년도수입 : 이자수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_04    \n");    // 현년도수입 : 기타사용료  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_05    \n");    // 현년도수입 : 기타잡수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_06    \n");    // 현년도수입 : 시도비반    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_07    \n");    // 현년도수입 : 체납처분    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_08    \n");    // 현년도수입 : 이월        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_09    \n");    // 현년도수입 : 주차요금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_10    \n");    // 현년도수입 : 내부전입금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_11    \n");    // 현년도수입 : 국고보조금  
		sb.append("      ,SUM((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP)                                         NOWAMT_99    \n");    // 현년도수입 : 합계        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)) ,0)) NOWTOT_01    \n");    // 현년도누계 : 과태료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_02    \n");    // 현년도누계 : 교통유발    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_03    \n");    // 현년도누계 : 이자수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_04    \n");    // 현년도누계 : 기타사용료  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_05    \n");    // 현년도누계 : 기타잡수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_06    \n");    // 현년도누계 : 시도비반    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_07    \n");    // 현년도누계 : 체납처분    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_08    \n");    // 현년도누계 : 이월        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_09    \n");    // 현년도누계 : 주차요금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_10    \n");    // 현년도누계 : 내부전입금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_11    \n");    // 현년도누계 : 국고보조금  
		sb.append("      ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP))                                         NOWTOT_99    \n");    // 현년도누계 : 합계        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTPASTSEIP,0))  PASTAMT_01            \n"); // 과년도수입 : 과태료       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTPASTSEIP,0))  PASTAMT_02            \n"); // 과년도수입 : 교통유발     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTPASTSEIP,0))  PASTAMT_03            \n"); // 과년도수입 : 이자수입     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTPASTSEIP,0))  PASTAMT_04            \n"); // 과년도수입 : 기타사용료   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTPASTSEIP,0))  PASTAMT_05            \n"); // 과년도수입 : 기타잡수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTPASTSEIP,0))  PASTAMT_06            \n"); // 과년도수입 : 시도비반     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTPASTSEIP,0))  PASTAMT_07            \n"); // 과년도수입 : 체납처분     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTPASTSEIP,0))  PASTAMT_08            \n"); // 과년도수입 : 이월         
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTPASTSEIP,0))  PASTAMT_09            \n"); // 과년도수입 : 주차요금     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTPASTSEIP,0))  PASTAMT_10            \n"); // 과년도수입 : 내부전입금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTPASTSEIP,0))  PASTAMT_11            \n"); // 과년도수입 : 국고보조금   
		sb.append("      ,SUM(A.M220_AMTPASTSEIP)                                       PASTAMT_99            \n"); // 과년도수입 : 합계         
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_01   \n");  // 과년도누계 : 과태료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_02   \n");  // 과년도누계 : 교통유발    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_03   \n");  // 과년도누계 : 이자수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_04   \n");  // 과년도누계 : 기타사용료  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_05   \n");  // 과년도누계 : 기타잡수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_06   \n");  // 과년도누계 : 시도비반    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_07   \n");  // 과년도누계 : 체납처분    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_08   \n");  // 과년도누계 : 이월        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_09   \n");  // 과년도누계 : 주차요금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_10   \n");  // 과년도누계 : 내부전입금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_11   \n");  // 과년도누계 : 국고보조금  
		sb.append("      ,SUM(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)                                            PASTTOT_99   \n");  // 과년도누계 : 합계        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01   \n");  // 금일누계 : 과태료          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02   \n");  // 금일누계 : 교통유발        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03   \n");  // 금일누계 : 이자수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04   \n");  // 금일누계 : 기타사용료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05   \n");  // 금일누계 : 기타잡수입      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06   \n");  // 금일누계 : 시도비반        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07   \n");  // 금일누계 : 체납처분        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08   \n");  // 금일누계 : 이월            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09   \n");  // 금일누계 : 주차요금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10   \n");  // 금일누계 : 내부전입금      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11   \n");  // 금일누계 : 국고보조금      
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99   \n");  // 금일누계 : 합계            
		
        sb.append("  FROM M220_DAY_T A                                                      \n");
		sb.append("      ,M390_USESEMOKCODE_T B                                             \n");
		sb.append("      ,M350_PARTCODE_T C                                                 \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE                                 \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN                                  \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE                                   \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE                               \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                                         \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE                                 \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'                                            \n");   // 업무구분:세입
		sb.append("   AND A.M220_ACCTYPE = 'B'                                              \n");   // 회계구분
		sb.append("   AND A.M220_ACCCODE = '31'                                             \n");   // 회계코드
        sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'      \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                     \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')                             \n");   // 세목 전체
		sb.append("   AND A.M220_PARTCODE IN ('00110','00140','00170','00200','00710')	    \n");   // 부서 전체 


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
		sb.append(" ORDER BY A.M220_PARTCODE  \n");

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
	public static CommonEntity getReportData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		
        sb.append("SELECT SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_01		 \n");  // 전일누계 : 과태료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_02		 \n");  // 전일누계 : 교통유발    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_03		 \n");  // 전일누계 : 이자수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_04		 \n");  // 전일누계 : 기타사용료  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_05		 \n");  // 전일누계 : 기타잡수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_06		 \n");  // 전일누계 : 시도비반    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_07		 \n");  // 전일누계 : 체납처분    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_08		 \n");  // 전일누계 : 이월        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_09		 \n");  // 전일누계 : 주차요금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_10		 \n");  // 전일누계 : 내부전입금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPJEONILTOT,0)) JEONIL_11		 \n");  // 전일누계 : 국고보조금  
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT)                                      JEONIL_99		 \n");  // 전일누계 : 합계        
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_01    \n");  // 현년도수입 : 과태료        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_02    \n");  // 현년도수입 : 교통유발      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_03    \n");  // 현년도수입 : 이자수입      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_04    \n");  // 현년도수입 : 기타사용료    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_05    \n");  // 현년도수입 : 기타잡수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_06    \n");  // 현년도수입 : 시도비반      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_07    \n");  // 현년도수입 : 체납처분      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_08    \n");  // 현년도수입 : 이월          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_09    \n");  // 현년도수입 : 주차요금      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_10    \n");  // 현년도수입 : 내부전입금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP),0))  NOWAMT_11    \n");  // 현년도수입 : 국고보조금    
		sb.append("      ,SUM((A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - A.M220_AMTPASTSEIP)                                         NOWAMT_99    \n");  // 현년도수입 : 합계          
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_01 \n");        // 현년도누계 : 과태료    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_02 \n");        // 현년도누계 : 교통유발  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_03 \n");        // 현년도누계 : 이자수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_04 \n");        // 현년도누계 : 기타사용료
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_05 \n");        // 현년도누계 : 기타잡수입
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_06 \n");        // 현년도누계 : 시도비반  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_07 \n");        // 현년도누계 : 체납처분  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_08 \n");        // 현년도누계 : 이월      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_09 \n");        // 현년도누계 : 주차요금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_10 \n");        // 현년도누계 : 내부전입금
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)),0))  NOWTOT_11 \n");        // 현년도누계 : 국고보조금
		sb.append("      ,SUM((A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG) - (A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP))                                         NOWTOT_99 \n");        // 현년도누계 : 합계      
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTPASTSEIP,0))  PASTAMT_01          \n");    // 과년도수입 : 과태료     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTPASTSEIP,0))  PASTAMT_02          \n");    // 과년도수입 : 교통유발   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTPASTSEIP,0))  PASTAMT_03          \n");    // 과년도수입 : 이자수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTPASTSEIP,0))  PASTAMT_04          \n");    // 과년도수입 : 기타사용료 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTPASTSEIP,0))  PASTAMT_05          \n");    // 과년도수입 : 기타잡수입 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTPASTSEIP,0))  PASTAMT_06          \n");    // 과년도수입 : 시도비반   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTPASTSEIP,0))  PASTAMT_07          \n");    // 과년도수입 : 체납처분   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTPASTSEIP,0))  PASTAMT_08          \n");    // 과년도수입 : 이월       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTPASTSEIP,0))  PASTAMT_09          \n");    // 과년도수입 : 주차요금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTPASTSEIP,0))  PASTAMT_10          \n");    // 과년도수입 : 내부전입금 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTPASTSEIP,0))  PASTAMT_11          \n");    // 과년도수입 : 국고보조금 
		sb.append("      ,SUM(A.M220_AMTPASTSEIP)                                       PASTAMT_99          \n");    // 과년도수입 : 합계       
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_01  \n");    // 과년도누계 : 과태료            
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_02  \n");    // 과년도누계 : 교통유발          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_03  \n");    // 과년도누계 : 이자수입          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_04  \n");    // 과년도누계 : 기타사용료        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_05  \n");    // 과년도누계 : 기타잡수입        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_06  \n");    // 과년도누계 : 시도비반          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_07  \n");    // 과년도누계 : 체납처분          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_08  \n");    // 과년도누계 : 이월              
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_09  \n");    // 과년도누계 : 주차요금          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_10  \n");    // 과년도누계 : 내부전입금        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP),0))     PASTTOT_11  \n");    // 과년도누계 : 국고보조금        
		sb.append("      ,SUM(A.M220_AMTPASTSEIPJEONILTOT + A.M220_AMTPASTSEIP)                                            PASTTOT_99  \n");    // 과년도누계 : 합계              
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',A.M220_AMTSEIPGWAONAP,0)) GWAO_01		    \n"); // 과오납 : 과태료        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',A.M220_AMTSEIPGWAONAP,0)) GWAO_02		    \n"); // 과오납 : 교통유발      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',A.M220_AMTSEIPGWAONAP,0)) GWAO_03		    \n"); // 과오납 : 이자수입      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',A.M220_AMTSEIPGWAONAP,0)) GWAO_04		    \n"); // 과오납 : 기타사용료    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',A.M220_AMTSEIPGWAONAP,0)) GWAO_05		    \n"); // 과오납 : 기타잡수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',A.M220_AMTSEIPGWAONAP,0)) GWAO_06		    \n"); // 과오납 : 시도비반      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',A.M220_AMTSEIPGWAONAP,0)) GWAO_07		    \n"); // 과오납 : 체납처분      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',A.M220_AMTSEIPGWAONAP,0)) GWAO_08		    \n"); // 과오납 : 이월          
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',A.M220_AMTSEIPGWAONAP,0)) GWAO_09		    \n"); // 과오납 : 주차요금      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',A.M220_AMTSEIPGWAONAP,0)) GWAO_10		    \n"); // 과오납 : 내부전입금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',A.M220_AMTSEIPGWAONAP,0)) GWAO_11		    \n"); // 과오납 : 국고보조금    
		sb.append("      ,SUM(A.M220_AMTSEIPGWAONAP)				                      GWAO_99		    \n"); // 과오납 : 합계          
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_01  \n");  	// 과오납누계 : 과태료      
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_02  \n");  	// 과오납누계 : 교통유발    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_03  \n");  	// 과오납누계 : 이자수입    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_04  \n");  	// 과오납누계 : 기타사용료  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_05  \n");  	// 과오납누계 : 기타잡수입  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_06  \n");  	// 과오납누계 : 시도비반    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_07  \n");  	// 과오납누계 : 체납처분    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_08  \n");  	// 과오납누계 : 이월        
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_09  \n");  	// 과오납누계 : 주차요금    
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_10  \n");  	// 과오납누계 : 내부전입금  
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP),0)) GWAOTOT_11  \n");  	// 과오납누계 : 국고보조금  
		sb.append("      ,SUM(A.M220_AMTGWAONAPJEONILTOT + A.M220_AMTSEIPGWAONAP)					                     GWAOTOT_99	 \n");      // 과오납누계 : 합계 
		
        sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110100',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_01    \n");  // 금일누계 : 과태료     
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110200',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_02    \n");  // 금일누계 : 교통유발   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110300',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_03    \n");  // 금일누계 : 이자수입   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110350',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_04    \n");  // 금일누계 : 기타사용료 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110400',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_05    \n");  // 금일누계 : 기타잡수입 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110450',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_06    \n");  // 금일누계 : 시도비반   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110500',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_07    \n");  // 금일누계 : 체납처분   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110600',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_08    \n");  // 금일누계 : 이월       
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110700',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_09    \n");  // 금일누계 : 주차요금   
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110800',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_10    \n");  // 금일누계 : 내부전입금 
		sb.append("      ,SUM(DECODE(A.M220_SEMOKCODE,'1110900',(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG),0))  TODAYTOT_11    \n");  // 금일누계 : 국고보조금 
		sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)		                                   TODAYTOT_99    \n");  // 금일누계 : 합계       
		
        sb.append("  FROM M220_DAY_T A                              \n");
		sb.append("      ,M390_USESEMOKCODE_T B	                    \n");
		sb.append("      ,M350_PARTCODE_T C                         \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                 \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE         \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN          \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE           \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE       \n");
		sb.append("   AND A.M220_YEAR = C.M350_YEAR                 \n");
		sb.append("   AND A.M220_PARTCODE = C.M350_PARTCODE         \n");
		sb.append("   AND B.M390_WORKGUBUN = '0'					                                   \n");    // 업무구분:세입
		sb.append("   AND A.M220_ACCTYPE = 'B'					                                       \n");    // 회계구분
		sb.append("   AND A.M220_ACCCODE = '31'						                                   \n");    // 회계코드
		sb.append("   AND A.M220_SEMOKCODE IN ('1110100','1110200','1110300','1110350'                 \n");
		sb.append("			   ,'1110400','1110450','1110500','1110600'                                \n");
		sb.append("			   ,'1110700' ,'1110800','1110900')	                                       \n");    // 세목 전체
		sb.append("   AND A.M220_PARTCODE IN ('00000','02240','00110','00140','00170','00200','00710') \n");    // 부서 전체

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