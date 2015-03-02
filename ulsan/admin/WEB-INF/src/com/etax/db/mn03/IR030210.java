/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR030210.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세입세출외현금 > 수기분 등록내역
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030210 {
  
	/* 회계명 조회 */ 
	public static List<CommonEntity> getAccNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
		sb.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME     \n");		
    sb.append("   FROM  M360_ACCCODE_T A 						  	   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B              \n");       
		sb.append("  WHERE  B.M390_WORKGUBUN = '4'		  			 \n");
		sb.append("    AND  A.M360_ACCGUBUN ='D'               \n");
    sb.append("    AND  A.M360_YEAR = B.M390_YEAR					 \n"); 
    sb.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN	 \n"); 
    sb.append("    AND  A.M360_ACCCODE =  B.M390_ACCCODE	 \n");
		sb.append("    AND  A.M360_YEAR = ?				             \n");
    if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND B.M390_PARTCODE = ?                  \n");																									 
		}
		sb.append(" GROUP BY A.M360_ACCCODE, A.M360_ACCNAME    \n");							
		sb.append(" ORDER BY A.M360_ACCCODE                    \n");		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }

	/* 부서명 조회 */
	public static List<CommonEntity> getCashDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 																															  
	  sb.append(" SELECT  A.M350_PARTCODE, M350_PARTNAME       \n");  
    sb.append("   FROM  M350_PARTCODE_T A  				   			   \n");   
		sb.append("        ,M390_USESEMOKCODE_T B                \n");       
    sb.append("  WHERE  A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("    AND  A.M350_YEAR = B.M390_YEAR            \n"); 
		sb.append("    AND  B.M390_ACCGUBUN ='D'                 \n");
		sb.append("    AND  B.M390_WORKGUBUN = '4'               \n");
		sb.append("    AND  A.M350_YEAR = ?				               \n");
		sb.append("  GROUP BY A.M350_PARTCODE, M350_PARTNAME     \n");
		sb.append(" ORDER BY A.M350_PARTCODE                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        
    return template.getList(conn, parameters);
  }

	/* 수기분 조회 */
	public static List<CommonEntity> getCashList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT  B.M350_PARTNAME PARTCODE                     \n");
		sb.append("		     ,C.M360_ACCNAME ACCCODE                       \n");  
		sb.append("        ,A.M040_SEQ                                   \n");   //일련번호
		sb.append("        ,A.M040_YEAR                                  \n");	 //회계연도
		sb.append("	       ,A.M040_DATE                                  \n");   //회계일자
	  sb.append("	       ,A.M040_PARTCODE                              \n");   //부서
		sb.append("	       ,A.M040_ACCCODE 								               \n");	 //회계명
		sb.append("        ,A.M040_DWTYPE 								               \n");	 //입금지급구분
    sb.append("        ,DECODE(A.M040_DWTYPE, 'G1', '입금',          \n");
    sb.append("                               'G2', '지급'           \n");
		sb.append("               ) M040_DWTYPE_NM                       \n");	 
		sb.append("        ,A.M040_INTYPE 								               \n");	 //입력구분
    sb.append("        ,DECODE(A.M040_INTYPE, 'I1', '입금지급',      \n");
    sb.append("                               'I2', '반납',          \n");
    sb.append("                               'I3', '정정'           \n");
		sb.append("               ) M040_INTYPE_NM                       \n");	 
		sb.append("        ,A.M040_CASHTYPE 								             \n");	 //현금구분
    sb.append("        ,DECODE(A.M040_CASHTYPE, 'C1', '보증금',      \n");
    sb.append("                                 'C2', '보관금',      \n");
		sb.append("                                 'C3', '잡종금',      \n");
    sb.append("                                 'C4', '부가가치세'   \n"); 
		sb.append("               ) M040_CASHTYPE_NM                     \n");	 
		sb.append("        ,A.M040_DEPOSITTYPE 								           \n");	 //예금종류
    sb.append("        ,DECODE(A.M040_DEPOSITTYPE, 'D1', '정기예금', \n");
    sb.append("                                    'D2', '별단예금', \n");
    sb.append("                                    'D3', '공금예금'  \n");
		sb.append("	              ) M040_DEPOSITTYPE_NM                  \n");	 //채주성명
		sb.append("	       ,A.M040_ORDERNAME 							               \n");	 //비고
		sb.append("	       ,A.M040_NOTE 										             \n");	 //지급번호
		sb.append("	       ,A.M040_ORDERNO									             \n");	 //건수
		sb.append("	       ,A.M040_CNT											             \n");   //금액
		sb.append("        ,A.M040_AMT                                   \n");
		sb.append("   FROM  M040_TAXCASH_T A                             \n");
		sb.append("        ,M350_PARTCODE_T B														 \n");
		sb.append("        ,M360_ACCCODE_T C														 \n");
		sb.append("  WHERE  A.M040_YEAR = B.M350_YEAR                    \n");	 
		sb.append("    AND  A.M040_YEAR = C.M360_YEAR                    \n");	 	
		sb.append("    AND  B.M350_PARTCODE(+) = A.M040_PARTCODE         \n");	 //부서조인
		sb.append("    AND  C.M360_ACCCODE(+) = A.M040_ACCCODE           \n");
		sb.append("    AND  C.M360_ACCGUBUN = 'D'                        \n");
    sb.append("    AND  A.M040_YEAR = ?                              \n");
		sb.append("    AND  A.M040_DATE = ?                              \n");
		if (!"".equals(paramInfo.getString("part_code"))){
		  sb.append("	 AND A.M040_PARTCODE = ?                           \n");																									 
		}
		if (!"".equals(paramInfo.getString("acc_code"))){
		  sb.append("	 AND A.M040_ACCCODE = ?                            \n");																									 
		}
    sb.append("    ORDER BY A.M040_SEQ                               \n");	

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("fis_date"));
		if (!"".equals(paramInfo.getString("part_code")) )	{
			parameters.setString(idx++, paramInfo.getString("part_code"));
		} 
		if (!"".equals(paramInfo.getString("acc_code")) )	{
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		} 

    return template.getList(conn, parameters);
  }

	/* 수기분 상세 */
	public static CommonEntity getCashView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   
		sb.append(" SELECT  B.M350_PARTNAME PARTCODE                     \n");
		sb.append("		     ,C.M360_ACCNAME ACCCODE                       \n");  
		sb.append("        ,A.M040_SEQ                                   \n");   //일련번호
		sb.append("        ,A.M040_YEAR                                  \n");	 //회계연도
		sb.append("	       ,A.M040_DATE                                  \n");   //회계일자
	  sb.append("	       ,A.M040_PARTCODE                              \n");   //부서
		sb.append("	       ,A.M040_ACCCODE 								               \n");	 //회계명
		sb.append("        ,A.M040_DWTYPE 								               \n");	 //입금지급구분
    sb.append("        ,DECODE(A.M040_DWTYPE, 'G1', '입금',          \n");
    sb.append("                               'G2', '지급'           \n");
		sb.append("               ) M040_DWTYPE_NM                       \n");	 
		sb.append("        ,A.M040_INTYPE 								               \n");	 //입력구분
    sb.append("        ,DECODE(A.M040_INTYPE, 'I1', '입금지급',      \n");
    sb.append("                               'I2', '반납',          \n");
    sb.append("                               'I3', '정정'           \n");
		sb.append("               ) M040_INTYPE_NM                       \n");	 
		sb.append("        ,A.M040_CASHTYPE 								             \n");	 //현금구분
    sb.append("        ,DECODE(A.M040_CASHTYPE, 'C1', '보증금',      \n");
    sb.append("                                 'C2', '보관금',      \n");
		sb.append("                                 'C3', '잡종금',      \n");
    sb.append("                                 'C4', '부가가치세'   \n"); 
		sb.append("               ) M040_CASHTYPE_NM                     \n");	 
		sb.append("        ,A.M040_DEPOSITTYPE 								           \n");	 //예금종류
    sb.append("        ,DECODE(A.M040_DEPOSITTYPE, 'D1', '정기예금', \n");
    sb.append("                                    'D2', '별단예금', \n");
    sb.append("                                    'D3', '공금예금'  \n");
		sb.append("	              ) M040_DEPOSITTYPE_NM                  \n");	 
		sb.append("	       ,A.M040_ORDERNAME 							               \n");	//채주성명  
		sb.append("	       ,A.M040_NOTE 										             \n");	//비고      
		sb.append("	       ,A.M040_ORDERNO									             \n");	//지급번호  
		sb.append("	       ,A.M040_CNT											             \n");  //건수      
		sb.append("        ,A.M040_AMT                                   \n");  //금액     
		sb.append("   FROM  M040_TAXCASH_T A                             \n");             
		sb.append("        ,M350_PARTCODE_T B														 \n");
		sb.append("        ,M360_ACCCODE_T C														 \n");
		sb.append("  WHERE  A.M040_YEAR = B.M350_YEAR                    \n");	 
		sb.append("    AND  A.M040_YEAR = C.M360_YEAR                    \n");	 	
		sb.append("    AND  B.M350_PARTCODE(+) = A.M040_PARTCODE         \n");	 //부서조인
		sb.append("    AND  C.M360_ACCCODE(+) = A.M040_ACCCODE           \n");
		sb.append("    AND  C.M360_ACCGUBUN = 'D'                        \n");
		sb.append("    AND  A.M040_YEAR = ?	                             \n");
		sb.append("		 AND  A.M040_SEQ = ?                               \n");
    sb.append("		 AND  A.M040_PARTCODE = ?                          \n");
    sb.append("		 AND  A.M040_ACCCODE = ?                           \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int idx = 1;

	  parameters.setString(idx++, paramInfo.getString("fis_year"));
	  parameters.setString(idx++, paramInfo.getString("m040_seq"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
	
		return template.getData(conn, parameters);
	}
		

	/* 수기분 수정 */
	public static int cashUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M040_TAXCASH_T        \n");
	  sb.append("	  SET M040_PARTCODE = ?     \n");  //부서
		sb.append("	     ,M040_ACCCODE = ? 			\n");	 //회계명
		sb.append("      ,M040_DWTYPE = ? 			\n");	 //입금지급구분
		sb.append("      ,M040_INTYPE = ?				\n");	 //입력구분
		sb.append("      ,M040_CASHTYPE = ? 		\n");	 //현금구분
		sb.append("	     ,M040_DEPOSITTYPE = ?  \n");	 //예금종류
		sb.append("	     ,M040_ORDERNAME = ? 		\n");	 //채주성명
		sb.append("	     ,M040_NOTE = ?					\n");	 //비고
		sb.append("	     ,M040_ORDERNO = ?			\n");	 //지급번호
		sb.append("      ,M040_CNT = ?					\n");	 //건수
		sb.append("      ,M040_AMT = ?          \n");  //금액
		sb.append(" WHERE M040_YEAR = ? 	      \n");
		sb.append("	  AND M040_SEQ = ?          \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("dwtype"));
		parameters.setString(idx++, paramInfo.getString("intype"));
		parameters.setString(idx++, paramInfo.getString("cash_type"));
		parameters.setString(idx++, paramInfo.getString("deposit_type"));
		parameters.setString(idx++, paramInfo.getString("order_name"));
		parameters.setString(idx++, paramInfo.getString("note"));
		parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m040_seq"));

		return template.update(conn, parameters);
	}

  /* 수기분 수정 (일일마감여부 'N') */
	public static int cashUpdate1(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M040_TAXCASH_T        \n");
	  sb.append("	  SET M040_ORDERNAME = ?    \n");  //채주성명
		sb.append("	     ,M040_NOTE = ?					\n");	 //비고
    sb.append("      ,M040_ORDERNO = ?      \n");  //지급번호
		sb.append("      ,M040_CNT = ?					\n");	 //건수
		sb.append(" WHERE M040_YEAR = ? 	      \n");
		sb.append("	  AND M040_SEQ = ?          \n");				

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++, paramInfo.getString("order_name"));
		parameters.setString(idx++, paramInfo.getString("note"));
    parameters.setString(idx++, paramInfo.getString("order_no"));
		parameters.setString(idx++, paramInfo.getString("cnt"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("m040_seq"));

		return template.update(conn, parameters);
	}

	/* 수기분 삭제 (일일마감여부 'Y') */
	public static int cashDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M040_TAXCASH_T           \n");
		sb.append(" WHERE M040_YEAR = ?                 \n");	//회계연도
		sb.append("   AND M040_SEQ = ?                  \n");	//일련번호
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		parameters.setString(1, paramInfo.getString("fis_year"));
		parameters.setString(2, paramInfo.getString("m040_seq"));
		
		return template.delete(conn, parameters);
	}
}