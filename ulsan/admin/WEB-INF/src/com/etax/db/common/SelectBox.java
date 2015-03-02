/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : SelectBox.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-14
* 프로그램내용   : common
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class SelectBox {

  private static Logger logger = Logger.getLogger(SelectBox.class);  // log4j 설정

	/**
	 * 회계명 조회
	 *
	 * @param Connection conn : 커넥션
	 * @return List : 회계코드 정보를 담은 List
	 * @throws SQLException
	 */
  
		/* 회계구분(일반회계)에 따른 회계명 */ 
	public static List<CommonEntity> getAccNmList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 		
		sql.append(" SELECT  A.M360_ACCCODE, A.M360_ACCNAME                                     \n");
    sql.append("   FROM  M360_ACCCODE_T A						                                        \n");
    sql.append("        ,M390_USESEMOKCODE_T B                                              \n");   
    sql.append("  WHERE  A.M360_ACCGUBUN = 'A'														                  \n");
    sql.append("    AND  A.M360_YEAR = B.M390_YEAR													                \n");
    sql.append("    AND  A.M360_ACCGUBUN = B.M390_ACCGUBUN										              \n");
    sql.append("    AND  A.M360_ACCCODE = B.M390_ACCCODE											              \n");
    sql.append("    AND  B.M390_WORKGUBUN = '0'															                \n");
    sql.append("    AND  SUBSTR(B.M390_SEMOKCODE,1,1) NOT IN ('2') 				                  \n");																	
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND  B.M390_PARTCODE = ?                                              \n"); 
		}
	  sql.append(" GROUP BY A.M360_ACCCODE, B.M360_ACCNAME                                    \n");							
		sql.append(" ORDER BY A.M360_ACCCODE                                                    \n");										

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }


	/* 회계구분(세외수입)에 따른 회계명 */ 
	public static List<CommonEntity> getImpNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME                                       \n");
    sql.append("   FROM  M390_USESEMOKCODE_T A						                                  \n");
    sql.append("        ,M360_ACCCODE_T B                                                   \n");   
    sql.append("  WHERE  A.M390_ACCGUBUN = 'A'														                  \n");
    sql.append("    AND  A.M390_YEAR = B.M360_YEAR													                \n");
    sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN										              \n");
    sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE											              \n");
    sql.append("    AND  A.M390_WORKGUBUN = '0'															                \n");
    sql.append("    AND  SUBSTR(A.M390_SEMOKCODE,1,1) = 2 				                          \n");																	
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND  A.M390_PARTCODE = ?                                              \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, B.M360_ACCNAME                                    \n");							
		sql.append(" ORDER BY A.M390_ACCCODE                                                    \n");										

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }

	/* 회계구분(기금,특별회계)에 따른 회계명 */ 
	public static List<CommonEntity> getSpecNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME                        \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR = B.M360_YEAR	                         \n");  
    sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN                   \n");  
	  sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE										 \n");
		sql.append("    AND  A.M390_ACCGUBUN IN ('B','E')                        \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                              \n");
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("    AND  A.M390_ACCGUBUN = ?                               \n"); 
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND  A.M390_PARTCODE = ?                               \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, M360_ACCNAME                       \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }
	
	/* 세입조회 회계명 */ 
	public static List<CommonEntity> getAccCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME                        \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR = B.M360_YEAR	                         \n");  
    sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN                   \n");  
	  sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE										 \n");
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("  AND  A.M390_ACCGUBUN = ?                                 \n"); 
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("  AND  A.M390_PARTCODE = ?                                 \n"); 
		}
		sql.append("    AND  A.M390_WORKGUBUN = '0'                              \n");
	  sql.append(" GROUP BY A.M390_ACCCODE, M360_ACCNAME                       \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }


	/* 회계구분(세입세출외 현금)에 따른 회계명 */ 
	public static List<CommonEntity> getAccNameList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCGUBUN                         \n");		
		sql.append("        ,A.M390_ACCCODE                          \n");		
		sql.append("        ,A.M390_ACCCODE                          \n");		
		sql.append("        ,B.M360_ACCGUBUN                         \n");		
		sql.append("        ,B.M360_ACCCODE                          \n");		
		sql.append("        ,B.M360_ACCNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	   \n");   
		sql.append("        ,M360_ACCCODE_T B                        \n");       
    sql.append("  WHERE  A.M390_ACCGUBUN ='D'                    \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN       \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE         \n");   
		if (!"".equals(paramInfo.getString("part_code")) ) {
			sql.append("    AND  A.M390_PARTCODE = ?                   \n"); 
		}
																																														
		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }

	/* 세출에 따른 회계명 */ 
	public static List<CommonEntity> getRevAccNmList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 		
		sql.append("  SELECT  A.M390_ACCCODE,  B.M360_ACCNAME					        \n"); 
    sql.append("    FROM  M390_USESEMOKCODE_T A										        \n"); 
    sql.append("         ,M360_ACCCODE_T B												        \n"); 
    sql.append("   WHERE  A.M390_WORKGUBUN = 1										        \n"); 
    sql.append("     AND  A.M390_YEAR = B.M360_YEAR								        \n"); 
    sql.append("     AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN				        \n"); 
    sql.append("     AND  A.M390_ACCCODE =  B.M360_ACCCODE				        \n");
		if (!"".equals(paramInfo.getString("acc_type")) ) {
    sql.append("     AND  A.M390_ACCGUBUN = ?											        \n");
			}
		if (!"".equals(paramInfo.getString("part_code")) ) {
    sql.append("     AND  A.M390_PARTCODE = ?											        \n");
			}
	  sql.append(" GROUP BY A.M390_ACCCODE, B.M360_ACCNAME                  \n");							
		sql.append(" ORDER BY A.M390_ACCCODE                                  \n");										

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }
	
		
  /**
   * 부서코드 조회
   *
   * @param Connection conn : 커넥션
   * @return List : 부서 정보를 담은 List
	 * @throws SQLException
   */

	/* 회계구분(일반회계)에 따른 부서코드 */
	public static List<CommonEntity> getAccDeptList(Connection conn) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE, M350_PARTNAME                        	\n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");  
		sql.append("    AND  A.M390_ACCGUBUN = 'A'                                  \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                 \n");
		sql.append("    AND  SUBSTR(A.M390_SEMOKCODE,1,1) NOT IN ('2')              \n");
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");

																																																																																																																				
		QueryTemplate template = new QueryTemplate(sql.toString());
        
    return template.getList(conn);
  }

	/* 회계구분(세외수입)에 따른 부서코드 */
	public static List<CommonEntity> getImpDeptList(Connection conn) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE, M350_PARTNAME                       	\n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");  
		sql.append("    AND  A.M390_ACCGUBUN = 'A'                                  \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                 \n");
		sql.append("    AND  SUBSTR(A.M390_SEMOKCODE,1,1) = '2'                     \n");
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																																						  																																																																													
		QueryTemplate template = new QueryTemplate(sql.toString());
        
    return template.getList(conn);
  }


	/* 회계구분(기금,특별회계)에 따른 부서코드 */
	public static List<CommonEntity> getSpecDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	                    \n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");  
		sql.append("    AND  A.M390_ACCGUBUN IN ('B','E')                           \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                 \n");
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("    AND  A.M390_ACCGUBUN = ?                                  \n"); 
		}
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
        
    return template.getList(conn, parameters);
  }

	/* 회계구분(세입세출외 현금)에 따른 부서코드 */
	public static List<CommonEntity> getCashDeptList(Connection conn) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME                      	\n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");  
		sql.append("    AND  A.M390_WORKGUBUN = '4'                                 \n");
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																												 																		
		QueryTemplate template = new QueryTemplate(sql.toString());
        
    return template.getList(conn);
  }

	/* 세입 부서코드 */
	public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	                    \n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");   
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("  AND  A.M390_ACCGUBUN = ?                                    \n"); 
		}
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                 \n");
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
        
    return template.getList(conn, parameters);
  }

  /* 세출 부서코드 */
	public static List<CommonEntity> getRevDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();

	  sql.append(" SELECT  A.M390_PARTCODE,  B.M350_PARTNAME  	 	            \n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			              \n");   
		sql.append("        ,M350_PARTCODE_T B                                  \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                  \n"); 
		sql.append("    AND  A.M390_YEAR = B.M350_YEAR                          \n"); 
		sql.append("   AND   A.M390_WORKGUBUN = '1'                             \n"); 
    if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("    AND  A.M390_ACCGUBUN = ?                              \n"); 
		}
		sql.append("	GROUP BY A.M390_PARTCODE , B.M350_PARTNAME 				        \n");
    sql.append("  ORDER BY A.M390_PARTCODE														      \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("acc_type")) ) {
			parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
        
    return template.getList(conn, parameters);
  }

	/**
   * 세목명 조회
   *
   * @param Connection conn : 커넥션
   * @return List : 부서 정보를 담은 List
	 * @throws SQLException
   */


	 /* 회계구분(일반회계)에 따른 세목명 */ 
	public static List<CommonEntity> getAccSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																											
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		sql.append("    AND  A.M390_ACCGUBUN = 'A'                                       \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                      \n"); 
		sql.append("    AND  SUBSTR(A.M390_SEMOKCODE,1,1) NOT IN ('2')                   \n"); 
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND A.M390_PARTCODE = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        
    return template.getList(conn, parameters);
  }


	/* 회계구분(세외수입)에 따른 세목명 */ 
	public static List<CommonEntity> getImpSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		sql.append("    AND  A.M390_ACCGUBUN = 'A'                                       \n"); 
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                      \n"); 
		sql.append("    AND  SUBSTR(A.M390_SEMOKCODE,1,1) = 2                            \n"); 
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND A.M390_PARTCODE = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}	
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        
    return template.getList(conn, parameters);
  }

	/* 회계구분(기금,특별회계)에 따른 세목명 */ 
	public static List<CommonEntity> getSpecSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT A.M370_SEMOKCODE,  B.M370_SEMOKNAME                    \n");		
    sql.append("   FROM M390_USESEMOKCODE_T A                                  \n");   
		sql.append("       ,M370_SEMOKCODE_T B                                     \n");       
    sql.append("  WHERE A.M390_ACCGUBUN IN ('B','E')                           \n");
		sql.append("    AND A.M390_WORKGUBUN = '0'                                 \n"); 
		sql.append("    AND A.M390_SEMOKCODE = B.M370_SEMOKCODE                    \n"); 
    sql.append("    AND A.M390_YEAR = B.M370_YEAR                              \n"); 
    sql.append("    AND A.M390_ACCGUBUN = B.M370_ACCGUBUN                      \n"); 
    sql.append("    AND A.M390_ACCCODE  = M370_ACCCODE                         \n"); 
		if (!"".equals(paramInfo.getString("fis_year")) ) {
		sql.append("    AND A.M390_YEAR = ?                                        \n");   
		}
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		sql.append("    AND A.M390_ACCGUBUN = ?                                    \n");   
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		sql.append("    AND A.M390_PARTCODE = ?                                    \n"); 
		}																																
		sql.append(" GROUP BY A.M390_SEMOKCODE , B.M370_SEMOKNAME                  \n"); 

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("fis_year")) ) {
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		}
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }

	 /* 세입조회 세목명 */ 
	public static List<CommonEntity> getSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																											
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		sql.append("    AND  A.M390_WORKGUBUN = '0'                                      \n");
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("    AND A.M390_ACCGUBUN = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND A.M390_PARTCODE = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        
    return template.getList(conn, parameters);
  }



	/* 회계구분(세출)에 따른 세목명 */ 
	public static List<CommonEntity> getRevSemokList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 		
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		sql.append("    AND  A.M390_WORKGUBUN = '1'                                      \n"); 
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  sql.append("    AND A.M390_ACCGUBUN = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND A.M390_PARTCODE = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}	
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		if (!"".equals(paramInfo.getString("acc_type")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_type"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        
    return template.getList(conn, parameters);
  }

		
  /* 계좌명과 계좌번호 셀렉트박스 */
	public static List<CommonEntity> getAcctList(Connection conn) throws SQLException {
    StringBuffer sql = new StringBuffer();
 
	  sql.append(" SELECT M300_ACCOUNTNO,       \n");
		sql.append("        M300_ACCNAME          \n");
    sql.append("   FROM M300_ACCOUNTMANAGE_T  \n");       
    sql.append("  ORDER BY M300_ACCOUNTNO     \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
        
    return template.getList(conn);
  }
 
   /* 부서코드 부서명 */
	public static List<CommonEntity> getAllotPartCode(Connection conn) throws SQLException {
    StringBuffer sql = new StringBuffer();
 											
		sql.append("  SELECT M350_PARTCODE,M350_PARTNAME  	\n"); 
    sql.append("    FROM M350_PARTCODE_T                \n"); 
    sql.append("   WHERE M350_REALLOTPARTYN = 'N'       \n"); 
    
                             
  	QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();
        
    return template.getList(conn, parameters);
  }

 /* 회계구분에 따른 부서코드(시스템운영) */
	public static List<CommonEntity> getAccGubunDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	                    \n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");   
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("    AND  A.M390_ACCGUBUN = ?                                  \n"); 
		}
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("accGubun")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun"));
		}
        
    return template.getList(conn, parameters);
  }
	 /* 회계구분에 따른 부서코드1(시스템운영) */
	public static List<CommonEntity> getAccGubunDeptList1(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
	  sql.append(" SELECT  A.M390_PARTCODE,  M350_PARTNAME  	                    \n");  
    sql.append("   FROM  M390_USESEMOKCODE_T A 				   			                  \n");   
		sql.append("        ,M350_PARTCODE_T B                                      \n");       
    sql.append("  WHERE  A.M390_PARTCODE = B.M350_PARTCODE                      \n");   
		if (!"".equals(paramInfo.getString("accGubun1")) ) {
		  sql.append("    AND  A.M390_ACCGUBUN = ?                                  \n"); 
		}
		sql.append("   GROUP BY A.M390_PARTCODE, M350_PARTNAME                      \n");
		sql.append("   ORDER BY A.M390_PARTCODE                                     \n");
																																																																																																																				
		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("accGubun1")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun1"));
		}
        
    return template.getList(conn, parameters);
  }
	 /* 회계구분에 따른 세목명(시스템운영) */ 
	public static List<CommonEntity> getSemokList1(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																											
		sql.append(" SELECT  A.M390_SEMOKCODE, B.M370_SEMOKNAME                          \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A                                       \n");   
		sql.append("        ,M370_SEMOKCODE_T B                                          \n");       
    sql.append("  WHERE  A.M390_YEAR = B.M370_YEAR                                   \n");
		sql.append("    AND  A.M390_ACCGUBUN = B.M370_ACCGUBUN                           \n"); 
		sql.append("    AND  A.M390_SEMOKCODE = B.M370_SEMOKCODE                         \n"); 
		sql.append("    AND  A.M390_ACCCODE = B.M370_ACCCODE                             \n");
		sql.append("    AND  A.M390_WORKGUBUN = B.M370_WORKGUBUN                         \n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("    AND A.M390_ACCGUBUN = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("    AND A.M390_PARTCODE = ?                                        \n"); 
		}	
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  sql.append("    AND A.M390_ACCCODE = ?                                         \n"); 
		}	
		sql.append(" GROUP BY A.M390_SEMOKCODE, B.M370_SEMOKNAME                         \n"); 
		sql.append(" ORDER BY A.M390_SEMOKCODE                                           \n");

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  parameters.setString(idx++, paramInfo.getString("accGubun"));
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        
    return template.getList(conn, parameters);
  }
/* 회계구분에 따른 회계명 (시스템운영-세목사용 관리)*/ 
	public static List<CommonEntity> getAccCdList1(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME                        \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR = B.M360_YEAR	                         \n");  
    sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN                   \n");  
	  sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE										 \n");
		if (!"".equals(paramInfo.getString("partCode")) ) {
		  sql.append("    AND  A.M390_PARTCODE = ?                               \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, M360_ACCNAME                       \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		if (!"".equals(paramInfo.getString("partCode")) ) {
			parameters.setString(idx++, paramInfo.getString("partCode"));
		}
        
    return template.getList(conn, parameters);
  }
	/* 회계구분에 따른 회계명 (시스템운영-세목사용 관리)*/ 
	public static List<CommonEntity> getAccCdList2(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, M360_ACCNAME                        \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR = B.M360_YEAR	                         \n");  
    sql.append("    AND  A.M390_ACCGUBUN = B.M360_ACCGUBUN                   \n");  
	  sql.append("    AND  A.M390_ACCCODE = B.M360_ACCCODE										 \n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("  AND  A.M390_ACCGUBUN = ?                                 \n"); 
		}
		if (!"".equals(paramInfo.getString("part_code")) ) {
		  sql.append("  AND  A.M390_PARTCODE = ?                                 \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, M360_ACCNAME                       \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("accGubun")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun"));
		}
    
		if (!"".equals(paramInfo.getString("part_code")) ) {
			parameters.setString(idx++, paramInfo.getString("part_code"));
		}
        
    return template.getList(conn, parameters);
  }
/* 회계구분에 따른 회계명 (시스템운영-세목코드 관리)*/ 
	public static List<CommonEntity> getAccCdList3(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE, B.M360_ACCCODE, B.M360_ACCNAME      \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR(+)= B.M360_YEAR	                         \n");  
    sql.append("    AND  A.M390_ACCGUBUN(+) = B.M360_ACCGUBUN                   \n");  
	  sql.append("    AND  A.M390_ACCCODE(+) = B.M360_ACCCODE										 \n");
		if (!"".equals(paramInfo.getString("accGubun")) ) {
		  sql.append("  AND  B.M360_ACCGUBUN = ?                                 \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, B.M360_ACCNAME, B.M360_ACCCODE     \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("accGubun")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun"));
		}    
    return template.getList(conn, parameters);
  }
/* 회계구분에 따른 회계명 (시스템운영-세목코드 관리)*/ 
	public static List<CommonEntity> getAccCdList4(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sql = new StringBuffer();
 																															  
		sql.append(" SELECT  A.M390_ACCCODE,B.M360_ACCNAME ,B.M360_ACCCODE       \n");		
    sql.append("   FROM  M390_USESEMOKCODE_T A 						  	               \n");   
		sql.append("        ,M360_ACCCODE_T B                                    \n"); 
		sql.append("  WHERE  A.M390_YEAR(+) = B.M360_YEAR	                       \n");  
    sql.append("    AND  A.M390_ACCGUBUN(+) = B.M360_ACCGUBUN                \n");  
	  sql.append("    AND  A.M390_ACCCODE(+) = B.M360_ACCCODE									\n");
		if (!"".equals(paramInfo.getString("accGubun1")) ) {
		  sql.append("  AND  B.M360_ACCGUBUN = ?                                 \n"); 
		}
	  sql.append(" GROUP BY A.M390_ACCCODE, B.M360_ACCNAME, B.M360_ACCCODE     \n"); 																																												
		sql.append(" ORDER BY A.M390_ACCCODE                                     \n"); 		

		QueryTemplate template = new QueryTemplate(sql.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    if (!"".equals(paramInfo.getString("accGubun1")) ) {
			parameters.setString(idx++, paramInfo.getString("accGubun1"));
		}    
    return template.getList(conn, parameters);
  }
}