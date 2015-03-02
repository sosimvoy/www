/**************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR010910.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > �����ϰ���ȸ
***************************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010910 {
	

	/* �����ϰ���ȸ */
	public static List getSemokDailyList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("SELECT DECODE(SUBSTR(A.M010_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������') GUBUN                           \n");	//����
		sb.append("      ,A.M010_SEMOKCODE, E.M370_SEMOKNAME                                                                                \n");	//�����
		sb.append("      ,SUM(A.M010_CNT) CNT_HAP                                                                                           \n");	//�Ǽ��� ��
		sb.append("      ,SUM(DECODE(A.M010_INTYPE,'I1',M010_AMT, DECODE(SIGN(A.M010_AMT),1,(A.M010_AMT * -1), A.M010_AMT))) AMT_HAP        \n"); //�ݾ�(���,���� ���ϱ�)	   		
		sb.append("   FROM  M010_TAXIN_T A                                                                                                  \n");	 
		sb.append("        ,M390_USESEMOKCODE_T B                                                                                           \n");
		sb.append("        ,M370_SEMOKCODE_T E                                                                                              \n");			
		sb.append("  WHERE  A.M010_YEAR = B.M390_YEAR                                                                                       \n");
		sb.append(" 	 AND  A.M010_ACCTYPE = B.M390_ACCGUBUN                                                                                \n");
		sb.append(" 	 AND  A.M010_ACCCODE = B.M390_ACCCODE                                                                                 \n");
		sb.append(" 	 AND  A.M010_SEMOKCODE = B.M390_SEMOKCODE                                                                             \n");  
		sb.append(" 	 AND  A.M010_PARTCODE = B.M390_PARTCODE                                                                               \n"); 
		sb.append(" 	 AND  E.M370_YEAR = B.M390_YEAR                                                                                       \n");								  
		sb.append(" 	 AND  E.M370_ACCGUBUN = B.M390_ACCGUBUN                                                                               \n");  
		sb.append(" 	 AND  E.M370_ACCCODE = B.M390_ACCCODE                                                                                 \n");	 
		sb.append(" 	 AND  E.M370_WORKGUBUN = B.M390_WORKGUBUN                                                                             \n");
		sb.append(" 	 AND  E.M370_SEMOKCODE = B.M390_SEMOKCODE                                                                             \n");	 
		sb.append(" 	 AND  A.M010_ACCTYPE= 'A'                                                                                             \n");
		sb.append(" 	 AND  A.M010_ACCCODE = '11'                                                                                           \n");  
		sb.append(" 	 AND  B.M390_WORKGUBUN = '0'                                                                                          \n");	
		sb.append(" 	 AND  SUBSTR(A.M010_SEMOKCODE, 1,1) <> '9'                                                                            \n");
   	sb.append(" 	 AND  A.M010_YEAR= ?                                                                                                  \n");	 
		sb.append(" 	 AND  A.M010_DATE = ?                                                                                                 \n");
		sb.append(" 	 AND  A.M010_PARTCODE IN (                                                                                            \n");
	  sb.append("                            SELECT  A.M350_PARTCODE                                                                      \n");
		sb.append("                              FROM  M350_PARTCODE_T A                                                                    \n");
		sb.append("                                   ,M390_USESEMOKCODE_T B                                                                \n");
    sb.append("                             WHERE  A.M350_YEAR = B.M390_YEAR                                                            \n"); 
    sb.append("                               AND  B.M390_ACCGUBUN = 'A'                                                                \n"); 
    sb.append("                               AND  B.M390_ACCCODE = '11'                                                                \n"); 
    sb.append("                               AND  B.M390_WORKGUBUN = '0'                                                               \n"); 
		sb.append("                               AND  A.M350_PARTCODE = B.M390_PARTCODE                                                    \n");
		sb.append("                               AND  (A.M350_PARTCODE BETWEEN '02100' AND '02275'                                         \n");   //02100~02275
		sb.append("                                OR  A.M350_PARTCODE IN ('00000', '00001', '00002',                                       \n");
    sb.append("                                                        '00110', '00140', '00170', '00200', '00710'))                    \n");   //����û
		sb.append("                               AND  A.M350_YEAR = ?                                                                      \n");
		sb.append("                             GROUP BY  A.M350_PARTCODE                                                                   \n");
		sb.append("                              )                                                                                          \n"); 
		sb.append("    AND  CASE                                                                                                            \n");
    sb.append("         WHEN  NVL(?,'ALL') = 'ALL' AND                                                                                  \n");  //��ü
    sb.append("               A.M010_PARTCODE NOT IN ('00110','00140','00170','00200','00710') THEN '1'                                 \n");
    sb.append("         WHEN  NVL(?, ' ')  = '00000' AND                                                                                \n");
    sb.append("               A.M010_PARTCODE NOT IN ('00140','00170','00710','02130','02140', '02220', '00110', '00200', '02190') AND  \n");  //��û
    sb.append("               A.M010_SUNAPGIGWANCODE != '310001' THEN '1'                                                               \n");
    sb.append("         WHEN  A.M010_SUNAPGIGWANCODE = '310001' AND                                                                     \n");  //������ϻ����
    sb.append("               NVL(?, ' ') = '02240' THEN '1'                                                                            \n");
    sb.append("         WHEN  A.M010_SUNAPGIGWANCODE != '310001' AND                                                                    \n");  //�μ�
    sb.append("               NVL(A.M010_PARTCODE, ' ') = ? THEN '1'                                                                    \n"); 
    sb.append("         WHEN  NVL(?,' ') = '99999' AND                                                                                  \n");  //��ü
    sb.append("               A.M010_PARTCODE IN ('00110','00140','00170','00200','00710') AND                                          \n");
		sb.append("               A.M010_SUNAPGIGWANCODE != '310001' THEN '1'                                                               \n");
		sb.append("         ELSE  '0'                                                                                                       \n");
    sb.append("         END = '1'                                                                                                       \n"); 
	  sb.append("   GROUP BY ROLLUP(DECODE(SUBSTR(A.M010_SEMOKCODE,1,1),'1','���漼','2','���ܼ���','','','��������')                     \n");
		sb.append("           ,(A.M010_SEMOKCODE                                                                                            \n");
		sb.append("           ,E.M370_SEMOKNAME))                                                                                           \n"); 
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;			

		parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_date"));
		parameters.setString(idx++, paramInfo.getString("part_year"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("part_code")); 
    parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
    /*
    if (!"".equals(paramInfo.getString("part_code")) &&  !"00000".equals(paramInfo.getString("part_code"))){
		  parameters.setString(idx++, paramInfo.getString("part_code"));
		}
    */
	
		return template.getList(conn, parameters);
	}	
}