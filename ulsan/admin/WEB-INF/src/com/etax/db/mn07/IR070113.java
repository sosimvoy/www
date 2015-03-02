/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070113.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-18
* 프로그램내용   : 일계보고서 > 보고서 조회 > 세입세출일계표(회계별)
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070113 {

	/* 보고서 조회 */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT LIST.*                                                                                                                \n");
		sb.append("  FROM (                                                                                                                     \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN                                                                                    		\n");
		sb.append("		       FROM (                                                                                                           \n");
	    sb.append("                  SELECT (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1)) AS SUMGUBUN                                                                                      \n");
		sb.append("                        ,A.M220_ACCCODE AS ACCCODE                                                                                                                       \n");
		sb.append("                        ,DECODE(C.M360_ACCNAME,'일반회계','본청(세입)', C.M360_ACCNAME) AS ACCNAME                                                                       \n");
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT,0))  AMTSEIPJEONILTOT                                                                   \n");   // 세입 전일누계        
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIP,0)) AMTSEIP                                                                                      \n");   // 세입 금일수입      
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPGWAONAP,0)) AMTSEIPGWAONAP                                                                        \n");   // 세입 과오납반납    
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPJEONGJEONG                                                                  \n");   // 세입 과목정정액등  
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPTOT	    \n");   // 세입 총계       
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'2',(A.M220_AMTBAJEONGSUJEONILTOT + M220_AMTBAJEONGSURYUNG),0))  AMTBAEJUNG                                         \n");   // 자금배정액
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT                                                                \n");   // 세출 전일누계
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHUL,0)) AMTSECHUL                                                                                  \n");   // 세출 당일지급       
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULBANNAP,0)) AMTSECHULBANNAP                                                                      \n");   // 세출 반납액         
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',A.M220_AMTSECHULJEONGJEONG,0)) AMTSECHULJEONGJEONG                                                              \n");   // 세출 과목정정액등   
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'1',(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG),0)) AMTSECHULTOT	\n");   // 세출 총계 
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'0',A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG,0)				    \n");
		sb.append("                           + DECODE(B.M390_WORKGUBUN,'2',(A.M220_AMTBAJEONGSUJEONILTOT + A.M220_AMTBAJEONGSURYUNG),0)												    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'1',(A.M220_AMTSECHULJEONILTOT + A.M220_AMTSECHUL - A.M220_AMTSECHULBANNAP - A.M220_AMTSECHULJEONGJEONG),0)		    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'2',A.M220_AMTBAJEONGJEONILTOT,0)																                    \n");
		sb.append("                           - DECODE(B.M390_WORKGUBUN,'2',A.M220_AMTBAJEONG,0)) AMTJAN									                                                \n");   // 잔액
		
        sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'3',A.M220_AMTJEONGGI,0)) AMTJEONGGI                                                                                \n");   // 자금운용 정기예금등 
		sb.append("                        ,SUM(DECODE(B.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) AMTGONGGEUM                                                                                \n");   // 자금운용 공금잔액   
		
        sb.append("                    FROM M220_DAY_T A                                \n");
		sb.append("                        ,M390_USESEMOKCODE_T B                       \n");
		sb.append("                        ,M360_ACCCODE_T C                            \n");
		sb.append("                        ,M370_SEMOKCODE_T D                          \n");
		sb.append("                   WHERE A.M220_YEAR = B.M390_YEAR                   \n");
		sb.append("                     AND A.M220_PARTCODE = B.M390_PARTCODE           \n");
		sb.append("                     AND A.M220_ACCTYPE = B.M390_ACCGUBUN            \n");
		sb.append("                     AND A.M220_ACCCODE = B.M390_ACCCODE             \n");
		sb.append("                     AND A.M220_SEMOKCODE = B.M390_SEMOKCODE         \n");
		sb.append("                     AND B.M390_YEAR = C.M360_YEAR					\n");
		sb.append("                     AND B.M390_ACCGUBUN = C.M360_ACCGUBUN			\n");
		sb.append("                     AND B.M390_ACCCODE = C.M360_ACCCODE	            \n");
		sb.append("                     AND B.M390_YEAR = D.M370_YEAR					\n");
		sb.append("                     AND B.M390_ACCGUBUN = D.M370_ACCGUBUN			\n");
		sb.append("                     AND B.M390_ACCCODE = D.M370_ACCCODE				\n");
		sb.append("                     AND B.M390_WORKGUBUN = D.M370_WORKGUBUN			\n");
		sb.append("                     AND B.M390_SEMOKCODE = D.M370_SEMOKCODE         \n");
		sb.append("                     AND D.M370_SEGUMGUBUN <> '2'                    \n");

		sb.append("                     AND A.M220_YEAR = ?								\n");
		sb.append("                     AND A.M220_DATE = ?								\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("                 AND A.M220_ACCTYPE = ?							\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("                 AND A.M220_PARTCODE = ?							\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("                 AND A.M220_ACCCODE = ?							\n");
		}
		sb.append("                  GROUP BY (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1))    \n");
		sb.append("                          ,(A.M220_ACCCODE                                   \n");
		sb.append("                          ,C.M360_ACCNAME)                                   \n");
        sb.append("		            ) ORG                                                       \n");
		sb.append("		      WHERE ROWNUM <= ?                                                 \n");
		sb.append("       ) LIST                                                                \n");
		sb.append(" WHERE LIST.RN > ?                                                           \n");
		

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
		parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

		return template.getList(conn, parameters);
	}

    
	/* 보고서 총 건수 조회 */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT SUM(COUNT(DISTINCT A.M220_ACCCODE)) CNT       \n");
        sb.append("  FROM M220_DAY_T A                                  \n");
		sb.append("      ,M390_USESEMOKCODE_T B                         \n");
		sb.append("      ,M360_ACCCODE_T C                              \n");
		sb.append("      ,M370_SEMOKCODE_T D                            \n");
		sb.append(" WHERE A.M220_YEAR = B.M390_YEAR                     \n");
		sb.append("   AND A.M220_PARTCODE = B.M390_PARTCODE             \n");
		sb.append("   AND A.M220_ACCTYPE = B.M390_ACCGUBUN              \n");
		sb.append("   AND A.M220_ACCCODE = B.M390_ACCCODE               \n");
		sb.append("   AND A.M220_SEMOKCODE = B.M390_SEMOKCODE           \n");
		sb.append("   AND B.M390_YEAR = C.M360_YEAR					    \n");
		sb.append("   AND B.M390_ACCGUBUN = C.M360_ACCGUBUN			    \n");
		sb.append("   AND B.M390_ACCCODE = C.M360_ACCCODE	            \n");
		sb.append("   AND B.M390_YEAR = D.M370_YEAR                     \n");
		sb.append("   AND B.M390_ACCGUBUN = D.M370_ACCGUBUN             \n");
		sb.append("   AND B.M390_ACCCODE = D.M370_ACCCODE				\n");
		sb.append("   AND B.M390_WORKGUBUN = D.M370_WORKGUBUN			\n");
		sb.append("   AND B.M390_SEMOKCODE = D.M370_SEMOKCODE           \n");
		sb.append("   AND D.M370_SEGUMGUBUN <> '2'                      \n");

		sb.append("   AND A.M220_YEAR = ?								\n");
		sb.append("   AND A.M220_DATE = ?								\n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("                 AND A.M220_ACCTYPE = ?				\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("                 AND A.M220_PARTCODE = ?				\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("                 AND A.M220_ACCCODE = ?				\n");
		}
		sb.append(" GROUP BY (A.M220_ACCTYPE || SUBSTR(A.M220_ACCCODE,1,1)) \n");
		sb.append("         ,(A.M220_ACCCODE                                \n");
		sb.append("         ,C.M360_ACCNAME)                                \n");
		

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