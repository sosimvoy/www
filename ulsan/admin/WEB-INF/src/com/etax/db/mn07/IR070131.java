/**********************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070131.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > �������ǥ(ȸ�躰)
***********************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070131 {
/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT LIST.*                                                                                                                              \n");
		sb.append("  FROM (                                                                                                                                   \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN                                                                                    			                  \n");
		sb.append("		       FROM (                                                                                                                           \n");
		sb.append("									SELECT  A.M390_ACCCODE                                                                                                    \n");
		sb.append("									       ,B.M360_ACCNAME                                                                                                    \n");
		sb.append("									       ,A.M390_ACCGUBUN                                                                                                   \n");
    sb.append("									       ,SUM(DECODE(C.M220_DATE, E.MIN_DATE, C.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT                              \n");
    sb.append("									       ,SUM(C.M220_AMTSECHUL) AMTSECHUL                                                                                   \n");
    sb.append("									       ,SUM(C.M220_AMTSECHULBANNAP) AMTSECHULBANNAP                                                                       \n");
    sb.append("									       ,SUM(C.M220_AMTSECHULJEONGJEONG) AMTSECHULJEONGJEONG                                                               \n"); 
		sb.append("									       ,SUM(C.M220_AMTSECHUL - C.M220_AMTSECHULBANNAP -  C.M220_AMTSECHULJEONGJEONG) DIFFERENCE                           \n"); 
		sb.append("									       ,SUM(DECODE(C.M220_DATE, E.MAX_DATE,                                                                               \n");
    sb.append("	                                   C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG,0)  \n"); 
    sb.append("	                           )TOT_AMT                                                                                                       \n"); 
    sb.append("									  FROM  M390_USESEMOKCODE_T A                                                                                             \n");	 
		sb.append("									       ,M360_ACCCODE_T B                                                                                                  \n");		 
		sb.append("									       ,M220_DAY_T C                                                                                                      \n");	
		sb.append("									       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE																		             				  \n"); 
    sb.append("									            FROM M220_DAY_T																																									              \n");
    sb.append("									           WHERE M220_YEAR = ?                                                                                            \n");           
    sb.append("									             AND M220_DATE BETWEEN ? AND ? ) E       															                    			              \n");  
		sb.append("									  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                                                                            \n");								  
		sb.append("									    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                                                                         \n");  
		sb.append("									    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                                                                         \n");	 
		sb.append("									    AND  A.M390_YEAR       =  C.M220_YEAR(+)                                                                              \n");  
		sb.append("									    AND  A.M390_ACCGUBUN   =  C.M220_ACCTYPE(+)                                                                           \n"); 
		sb.append("									    AND  A.M390_ACCCODE    =  C.M220_ACCCODE(+)                                                                           \n");								  
		sb.append("									    AND  A.M390_SEMOKCODE  =  C.M220_SEMOKCODE(+)                                                                         \n");  
		sb.append("									    AND  A.M390_PARTCODE   =  C.M220_PARTCODE(+)                                                                          \n");	 
		sb.append("									    AND  A.M390_WORKGUBUN  = '1'                    		                                                                  \n");
    sb.append("									    AND  A.M390_YEAR = ?                                                                                                  \n");
		sb.append("									    AND  C.M220_DATE(+) BETWEEN ? AND ?	                                                                                  \n");
    if(!paramInfo.getString("acc_type").equals("")){                                                                  
			sb.append("										AND C.M220_ACCTYPE = ?																			                                                          \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("										AND C.M220_PARTCODE = ?																				                                                        \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("										AND C.M220_ACCCODE = ?																			                                                          \n");
		}																																																																		   
		sb.append("                     GROUP BY A.M390_ACCGUBUN, A.M390_ACCCODE, B.M360_ACCNAME                                                              \n"); 	
		sb.append("                     ORDER BY M390_ACCGUBUN, M390_ACCCODE                                                                                  \n"); 
    sb.append("		            ) ORG                                                                                     						                      \n");
		sb.append("		      WHERE ROWNUM <= ?                                                                               								                  \n");
		sb.append("       ) LIST                                                                                            					                        \n");
		sb.append(" WHERE LIST.RN > ?                                                                                       						                      \n");
																																																																			   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
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

    /* ��ī��Ʈ */
	public static CommonEntity getTotalCount(Connection conn, CommonEntity paramInfo)throws SQLException{
		StringBuffer sb = new StringBuffer();

    sb.append("	SELECT  SUM(COUNT(DISTINCT A.M390_ACCCODE)) CNT                         \n");
    sb.append("	  FROM  M390_USESEMOKCODE_T A                                           \n");	 
		sb.append("	       ,M360_ACCCODE_T B                                                \n");
		sb.append("	       ,M220_DAY_T C                                                    \n");		 
		sb.append("	       ,( SELECT MIN(M220_DATE) MIN_DATE, MAX(M220_DATE) MAX_DATE				\n");	
		sb.append("	            FROM M220_DAY_T																							\n"); //����ִ� ������ ���ʳ�¥��, ������¥ ���ϴ� ����
    sb.append("	           WHERE M220_YEAR = ?                                          \n");
    sb.append("	             AND M220_DATE BETWEEN ? AND ? ) E       										\n");         
    sb.append("	  WHERE  A.M390_YEAR       =  B.M360_YEAR  (+)                          \n");  
		sb.append("	    AND  A.M390_ACCGUBUN   =  B.M360_ACCGUBUN (+)                       \n");								  
		sb.append("	    AND  A.M390_ACCCODE    =  B.M360_ACCCODE  (+)                       \n");  
		sb.append("	    AND  A.M390_YEAR       =  C.M220_YEAR(+)                            \n");	 
		sb.append("	    AND  A.M390_ACCGUBUN   =  C.M220_ACCTYPE(+)                         \n");
		sb.append("	    AND  A.M390_ACCCODE    =  C.M220_ACCCODE(+)                         \n");	
		sb.append("	    AND  A.M390_SEMOKCODE  =  C.M220_SEMOKCODE(+)                       \n");
		sb.append("	    AND  A.M390_PARTCODE   =  C.M220_PARTCODE(+)                        \n");
		sb.append("	    AND  A.M390_WORKGUBUN  = '1'                    		                \n");
		sb.append("	    AND  A.M390_YEAR = ?                                                \n");  
		sb.append("	    AND  C.M220_DATE(+) BETWEEN ? AND ?	                                \n"); 
	
    if(!paramInfo.getString("acc_type").equals("")){                                         
			sb.append("		AND C.M220_ACCTYPE = ?                                              \n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("		AND C.M220_PARTCODE = ?                                             \n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("		AND C.M220_ACCCODE = ?                                              \n");
		}                                                                                   
		sb.append("  GROUP BY A.M390_ACCCODE,B.M360_ACCNAME                                 \n"); 
   
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		
    parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("first_business_date"));
		parameters.setString(idx++, paramInfo.getString("last_business_date"));
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