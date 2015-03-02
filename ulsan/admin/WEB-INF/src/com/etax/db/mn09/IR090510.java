/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090510.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-18
* 프로그램내용	   : 시스템운영 > 거래로그관리
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090510 {

	/* 거래로그 상세 */
	public static List getLogList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
                            
		sb.append(" SELECT M280_TRANSDATE,			     	  	                                      \n"); //거래일시
		sb.append("			   M280_LOGNO,				                                                  \n"); //로그번호
		sb.append("			   M280_USERNAME,				                                                \n"); //사용자명
    sb.append("  DECODE(M280_WORKTYPE,   'A01','세입',                                      \n");
		sb.append("                          'A02','세출',                                      \n");
		sb.append("                          'A03','세입세출외현금',                            \n");
		sb.append("                          'A04','세외수입',                                  \n");
		sb.append("                          'A05','자금배정',                                  \n");
		sb.append("                          'A06','자금운용',                                  \n");
		sb.append("                          'A07','일계/보고서',                               \n");
		sb.append("                          'A08','전자결재',                                  \n");
		sb.append("                          'A09','시스템운영') M280_WORKTYPE,                 \n");
		sb.append("  DECODE(M280_WORKTYPE,   'A01',DECODE(M280_TRANSGUBUN, '111','회계일자 등록',                  \n");                      
    sb.append("                                                        '121','수기분 등록(지방세,의존금)',     \n");
    sb.append("                                                        '131','수기분 등록(지방세,세외수입)',   \n");
    sb.append("                                                        '141','수기분 등록(기금,특별회계)',     \n");    
    sb.append("                                                        '150','수기분 조회',                    \n"); 
    sb.append("                                                        '159','수기분 삭제',                    \n"); 
    sb.append("                                                        '160','EXCEL자료 조회',                 \n"); 
    sb.append("                                                        '161','EXCEL자료 등록',                 \n"); 
    sb.append("                                                        '169','EXCEL자료 삭제',                 \n");
    sb.append("                                                        '177','OCR세입집계',                    \n"); 
    sb.append("                                                        '180','영수필통지서 조회',              \n"); 
    sb.append("                                                        '190','세입금정정요구서 조회',          \n"); 
    sb.append("                                                        '197','세입금정정요구서 통지')          \n");               
    sb.append("                          ,'A02',DECODE(M280_TRANSGUBUN,'111','수기분 등록',             			 \n");
    sb.append("                                                        '120','등록내역 조회')                  \n");  
    sb.append("                          ,'A03',DECODE(M280_TRANSGUBUN,'111','수기분 등록',            				 \n");
    sb.append("                                                        '120','등록내역 조회',                  \n"); 
    sb.append("                                                        '129','등록내역 삭제',          				 \n"); 
    sb.append("                                                        '122','등록내역 수정',          				 \n"); 
    sb.append("                                                        '130','수입증지 불출현황 조회', 				 \n"); 
    sb.append("                                                        '131','수입증지 불출현황 등록', 				 \n"); 
    sb.append("                                                        '132','수입증지 불출현황 수정', 				 \n"); 
    sb.append("                                                        '141','주행세 등록',            				 \n"); 
    sb.append("                                                        '150','주행세 조회',            				 \n"); 
    sb.append("                                                        '159','주행세 삭제',            				 \n"); 
    sb.append("                                                        '152','주행세 수정')										 \n"); 
    sb.append("                          ,'A04',DECODE(M280_TRANSGUBUN,'110','계좌입금내역 조회',              \n");    
    sb.append("                                                        '120','예산서 조회',                    \n");    
    sb.append("                                                        '131','징수결의 등록',                  \n");    
    sb.append("                                                        '140','등록내역 조회',                  \n");    
    sb.append("                                                        '149','등록내역 삭제',                  \n");    
    sb.append("                                                        '142','등록내역 수정',                  \n");    
    sb.append("                                                        '150','과오납금송금반화통지서조회',      \n");   
    sb.append("                                                        '157','과오납금송금반화통지서 결과통지') \n");   
    sb.append("                          ,'A05',DECODE(M280_TRANSGUBUN,'110','자금배정미처리잔 액 조회', 			  \n"); 
    sb.append("                                                        '120','자금배정 승인내역조회',    			  \n"); 
    sb.append("                                                        '123','자금배정 처리',            			  \n"); 
    sb.append("                                                        '130','자금재배정 승인내역조회',  			  \n"); 
    sb.append("                                                        '133','자금재배정 처리',          			  \n"); 
    sb.append("                                                        '140','자금배정통지서 조회',      			  \n"); 
    sb.append("                                                        '150','자금배정수기분 조회',      			  \n"); 
		sb.append("                                                        '151','수기분등록',                      \n"); 
    sb.append("                                                        '159','자금배정수기분 조회',      			  \n"); 
    sb.append("                                                        '171','잉여금이입요구 등록',      			  \n"); 
    sb.append("                                                        '170','잉여금이입요구 조회',      			  \n"); 
    sb.append("                                                        '179','잉여금이입요구 삭제',      			  \n"); 
    sb.append("                                                        '180','잉여금이입승인 조회',      			  \n"); 
    sb.append("                                                        '181','잉여금이입 처리',          			  \n"); 
    sb.append("                                                        '190','잉여금이입통지서 조회',    			  \n"); 
    sb.append("                                                        '211','잉여금이입수기분 등록',           \n"); 
    sb.append("                                                        '210','잉여금이입수기분 조회',    			  \n"); 
    sb.append("                                                        '219','잉여금이입수기분 취소',    			  \n"); 
    sb.append("                                                        '221','계좌등록',                 			  \n"); 
    sb.append("                                                        '230','계좌별 거래내역 조회')     			  \n"); 
    sb.append("                          ,'A06',DECODE(M280_TRANSGUBUN,'111','자금예탁 요구등록',           	  \n"); 
    sb.append("                                                        '120','자금예탁 요구조회/취소',      	  \n"); 
    sb.append("                                                        '130','자금예탁 승인조회',           	  \n"); 
    sb.append("                                                        '131','자금예탁 일계등록',           	  \n"); 
    sb.append("                                                        '140','자금예탁 일계등록 조회',      	  \n"); 
    sb.append("                                                        '149','자금예탁 일계등록 취소',      	  \n"); 
    sb.append("                                                        '150','자금예탁 통지서조회',         	  \n"); 
    sb.append("                                                        '160','자금예탁(기금,특별)조회',     	  \n");
		sb.append("                                                        '169','자금예탁(기금,특별)일계등록',     \n"); 
    sb.append("                                                        '179','자금예탁(기금,특별)취소',     	  \n"); 
    sb.append("                                                        '181','자금인출요구등록',            	  \n"); 
    sb.append("                                                        '190','자금인출요구조회',            	  \n"); 
    sb.append("                                                        '199','자금인출요구취소',                \n");  
    sb.append("                                                        '200','자금인출승인조회',            	  \n"); 
    sb.append("                                                        '201','자금인출일계등록',            	  \n"); 
    sb.append("                                                        '210','자금인출일계등록조회',        	  \n"); 
    sb.append("                                                        '219','자금인출일계등록취소',        	  \n"); 
    sb.append("                                                        '220','자금인출통지서조회',          	  \n"); 
    sb.append("                                                        '230','자금인출(기금,특별)조회',     	  \n"); 
		sb.append("                                                        '239','자금인출(기금,특별)일계등록',     \n"); 
    sb.append("                                                        '249','자금인출(기금,특별)취소',     	  \n"); 
    sb.append("                                                        '250','자금운용현황조회',            	  \n"); 
    sb.append("                                                        '261','잔액장자료입력',              	  \n"); 
    sb.append("                                                        '270','잔액장조회',                  	  \n"); 
    sb.append("                                                        '280','평잔표조회',                  	  \n"); 
    sb.append("                                                        '291','자금운용회계연도이월')					  \n"); 
    sb.append("                          ,'A07',DECODE(M280_TRANSGUBUN,'110','보고서조회',      							  \n"); 
    sb.append("                                                        '121','일일업무마감',    							  \n"); 
    sb.append("                                                        '131','회계연도이월')                    \n"); 
    sb.append("                          ,'A09',DECODE(M280_TRANSGUBUN,'110','변경신청내역조회',       				  \n"); 
    sb.append("                                                        '111','사용자등록',             				  \n"); 
    sb.append("                                                        '115','사용자승인',             				  \n"); 
    sb.append("                                                        '120','사용자내역조회',         				  \n"); 
    sb.append("                                                        '132','개인정보수정',           				  \n"); 
    sb.append("                                                        '141','사용자정보변경신청',     				  \n"); 
    sb.append("                                                        '150','거래로그조회',           				  \n"); 
    sb.append("                                                        '160','부서코드조회',           				  \n"); 
    sb.append("                                                        '161','부서코드등록',           				  \n"); 
    sb.append("                                                        '169','부서코드삭제',           				  \n"); 
    sb.append("                                                        '170','세목코드조회',           				  \n"); 
    sb.append("                                                        '171','세목코드등록',           				  \n"); 
    sb.append("                                                        '179','세목코드삭제',           				  \n"); 
    sb.append("                                                        '180','휴일조회',               				  \n"); 
    sb.append("                                                        '181','휴일등록',               				  \n"); 
    sb.append("                                                        '189','휴일수정',                        \n");
    sb.append("                                                        '191','마감일등록',                      \n");
    sb.append("                                                        '201','회기초코드등록')                  \n");
    sb.append("        ) M280_TRANSGUBUN,                                                                       \n");
		sb.append("			         M280_CONNECTIP				                                                              \n"); //접속IP
		sb.append("			    FROM M280_TRANSACTIONLOG_T                                                              \n");
		sb.append("        WHERE SUBSTR(M280_TRANSDATE,0,8) BETWEEN ? AND ?	                                        \n");
		
		if (!"".equals(paramInfo.getString("user_name"))){
		  sb.append("		     AND M280_USERNAME LIKE ?                                \n");
		}           
    sb.append("        AND SUBSTR(M280_WORKTYPE, 1, 1) = 'A'                     \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("su_sdate"));
		parameters.setString(i++,  paramInfo.getString("su_edate"));

		if (!"".equals(paramInfo.getString("user_name")) ) {
      parameters.setString(i++, "%"+paramInfo.getString("user_name")+"%");
    }
  
		return template.getList(conn, parameters);
	}
}