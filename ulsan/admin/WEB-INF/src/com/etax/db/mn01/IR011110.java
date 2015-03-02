/**************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR011110.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ���񺰴�����ȸ
***************************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR011110 {
	
	/* ���񺰴�����ȸ */
	public static List getSemokTotalList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   
		sb.append(" SELECT DECODE(SUBSTR(A.M220_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������') GUBUN                                  \n");  //����                                                       
		sb.append("				,A.M220_SEMOKCODE, C.M370_SEMOKNAME                                                                                       \n"); //����� 
		sb.append("				,SUM(DECODE(A.M220_DATE, ?, A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG))	\n");	//TO��
    sb.append("	        - SUM(DECODE(A.M220_DATE, ?, A.M220_AMTSEIPJEONILTOT,0)) TOT_AMT                                                        \n"); //FROM��
		sb.append("				,SUM(DECODE(A.M220_DATE, ?, A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP - A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG)) \n");
		sb.append("				 TO_AMT                                                                                                                   \n");
		sb.append("   FROM  M220_DAY_T A                                                                                                            \n");	 
		sb.append("        ,M390_USESEMOKCODE_T B                                                                                                   \n");
		sb.append("        ,M370_SEMOKCODE_T C                                                                                                      \n");	
		sb.append("  WHERE  A.M220_YEAR = B.M390_YEAR                                                                                               \n");
		sb.append(" 	 AND  A.M220_ACCTYPE = B.M390_ACCGUBUN                                                                                        \n");
		sb.append(" 	 AND  A.M220_ACCCODE = B.M390_ACCCODE                                                                                         \n");
		sb.append(" 	 AND  A.M220_SEMOKCODE = B.M390_SEMOKCODE                                                                                     \n");  
		sb.append(" 	 AND  A.M220_PARTCODE = B.M390_PARTCODE                                                                                       \n");  
		sb.append(" 	 AND  B.M390_YEAR  = C.M370_YEAR                                                                                              \n");								  
		sb.append(" 	 AND  B.M390_ACCGUBUN = C.M370_ACCGUBUN                                                                                       \n");  
		sb.append(" 	 AND  B.M390_ACCCODE = C.M370_ACCCODE                                                                                         \n");	 
		sb.append(" 	 AND  B.M390_WORKGUBUN = C.M370_WORKGUBUN                 		                                                                \n");
		sb.append(" 	 AND  B.M390_SEMOKCODE = C.M370_SEMOKCODE                                                                                     \n");	
		sb.append(" 	 AND  A.M220_ACCTYPE= 'A'				                                                                                              \n");
		sb.append(" 	 AND  A.M220_ACCCODE= '11'				                                                                                            \n");  
		sb.append(" 	 AND  B.M390_WORKGUBUN = '0'                                                                                                  \n");	 																	 
   	sb.append(" 	 AND  A.M220_YEAR= ?				                                                                                                  \n");
		sb.append(" 	 AND  A.M220_DATE IN (?,?)				                                                                                            \n");
		sb.append(" 	 AND  A.M220_PARTCODE IN (											                                                                              \n");
	  sb.append("                             SELECT  A.M350_PARTCODE                    					                                               	\n");
		sb.append("                               FROM  M350_PARTCODE_T A    											                                               	  \n");
		sb.append("                                    ,M390_USESEMOKCODE_T B     										                                              \n");
    sb.append("                              WHERE  A.M350_YEAR = B.M390_YEAR                                                                   \n"); 
		sb.append("                                AND  A.M350_PARTCODE = B.M390_PARTCODE                                                           \n");
		sb.append("                                AND  B.M390_ACCGUBUN = 'A'                                                                       \n"); 
    sb.append("                                AND  B.M390_ACCCODE = '11'                                                                       \n"); 
    sb.append("                                AND (A.M350_PARTCODE BETWEEN '02100' AND '02275'                                                 \n");
		sb.append("                                 OR  A.M350_PARTCODE IN ('00000', '00001', '00002',                                              \n");
    sb.append("                                                        '00110', '00140', '00170', '00200', '00710'))                            \n");   //����û
		sb.append("                               AND  A.M350_YEAR = ?                                                                              \n");
		sb.append("                             GROUP BY  A.M350_PARTCODE                                                                           \n");
		sb.append("                            )                                                                                                    \n");
		sb.append("    AND  CASE                                                                                                                    \n");
    sb.append("         WHEN  NVL(?,'ALL') = 'ALL' AND                                                                                          \n");  //��ü
    sb.append("               A.M220_PARTCODE NOT IN ('00110','00140','00170','00200','00710') THEN '1'                                         \n");
    sb.append("         WHEN  NVL(?, ' ')  = '00000' AND                                                                                        \n");
    sb.append("               A.M220_PARTCODE NOT IN ('00140','00170','00710','02130','02140', '02220', '00110', '00200', '02190') AND          \n");  //��û
    sb.append("               A.M220_SUNAPGIGWANCODE != '310001' THEN '1'                                                                       \n");
    sb.append("         WHEN  A.M220_SUNAPGIGWANCODE = '310001' AND                                                                             \n");  //������ϻ����
    sb.append("               NVL(?, ' ') = '02240' THEN '1'                                                                                    \n");
    sb.append("         WHEN  A.M220_SUNAPGIGWANCODE != '310001' AND                                                                            \n");  //�μ�
    sb.append("               NVL(A.M220_PARTCODE, ' ') = ? THEN '1'                                                                            \n"); 
    sb.append("         WHEN  NVL(?,' ') = '99999' AND                                                                                          \n");   
    sb.append("               A.M220_PARTCODE IN ('00110','00140','00170','00200','00710') AND                                                  \n");
	  sb.append("               A.M220_SUNAPGIGWANCODE != '310001' THEN '1'                                                                       \n");
		sb.append("         ELSE  '0'                                                                                                               \n");
    sb.append("         END = '1'                                                                                                               \n");		
		sb.append(" GROUP BY ROLLUP(SUBSTR(A.M220_SEMOKCODE,1,1),(A.M220_SEMOKCODE, C.M370_SEMOKNAME))                                              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;			
		
		parameters.setString(idx++, paramInfo.getString("to_date"));
		parameters.setString(idx++, paramInfo.getString("from_date"));
		parameters.setString(idx++, paramInfo.getString("to_date"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("from_date"));
		parameters.setString(idx++, paramInfo.getString("to_date"));
		parameters.setString(idx++, paramInfo.getString("part_year"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
	
		return template.getList(conn, parameters);
	}	
}
