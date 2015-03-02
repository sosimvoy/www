/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���     : IR070112.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-09-10
* ���α׷�����   : �ϰ躸�� > ���� ��ȸ > ���Լ����ϰ�ǥ(��û)
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070112 {

	public static CommonEntity getReportPrtyn(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT MAX(PRT_STATE) AS PRT_STATE FROM (                                                                  \n");
		sb.append("SELECT CASE WHEN DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS,0) > 0         \n");
		sb.append("                THEN 'Y'                                                                                   \n");
		sb.append("                ELSE 'N' END  PRT_STATE                                                                    \n");   // �׿������Ծ�		                                                                                                                                                                                              
		sb.append("  FROM M390_USESEMOKCODE_T A                                                                               \n");
		sb.append("      ,M360_ACCCODE_T B		                                                                                \n");
		sb.append("      ,M220_DAY_T C	                                                                                      \n");
		sb.append("      ,M370_SEMOKCODE_T D                                                                                  \n");
		sb.append(" WHERE A.M390_YEAR           = B.M360_YEAR								                                                  \n");
		sb.append("   AND A.M390_ACCGUBUN       = B.M360_ACCGUBUN						                                                  \n");
		sb.append("   AND A.M390_ACCCODE        = B.M360_ACCCODE	                                                            \n");
		sb.append("   AND A.M390_YEAR        	= C.M220_YEAR(+)		                                                            \n");
		sb.append("   AND A.M390_PARTCODE    	= C.M220_PARTCODE(+)	                                                          \n");
		sb.append("   AND A.M390_ACCGUBUN       = C.M220_ACCTYPE(+)                                                           \n");
		sb.append("   AND A.M390_ACCCODE        = C.M220_ACCCODE(+)                                                           \n");
		sb.append("   AND A.M390_SEMOKCODE      = C.M220_SEMOKCODE(+)                                                         \n");
		sb.append("   AND D.M370_SEGUMGUBUN <> '2'                                                                            \n");   //  ��������
    sb.append("   AND A.M390_ACCGUBUN <> 'D'                                                                              \n");
    sb.append("   AND A.M390_YEAR           = D.M370_YEAR							                                                    \n");
		sb.append("   AND A.M390_ACCGUBUN       = D.M370_ACCGUBUN						                                                  \n");
		sb.append("   AND A.M390_ACCCODE        = D.M370_ACCCODE							                                                \n");
		sb.append("   AND A.M390_WORKGUBUN		= D.M370_WORKGUBUN						                                                  \n");
		sb.append("   AND A.M390_SEMOKCODE		= D.M370_SEMOKCODE                                                              \n");
		sb.append("   AND A.M390_ACCGUBUN = 'A'                                                                               \n");
    sb.append("   AND A.M390_YEAR = ?										                                                                  \n");
		sb.append("   AND C.M220_DATE(+) = ?									                                                                \n");
		
		if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND A.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND A.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND A.M390_ACCCODE = ?								\n");
		}
		sb.append("GROUP BY CASE WHEN DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS,0) > 0    \n");
		sb.append("                THEN 'Y'                                                                                \n");
		sb.append("                ELSE 'N' END )                                                               \n");   // �׿������Ծ�		                                                                                                                                                                                              

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
	/* ���� ��ȸ */
	public static List<CommonEntity> getReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ACCTYPE, ACCTYPENM, SUMGUBUN, ACCCODE, ACCNAME                                                                  \n");              
        sb.append("     ,  AMTSEIPJEONILTOT, AMTSEIP, AMTSEIPGWAONAP, AMTSEIPJEONGJEONG                                                    \n");
        sb.append("     ,  AMTSEIPTOT, AMTBAEJUNG, AMTSECHULJEONILTOT, AMTSECHUL                                                           \n");
        sb.append("     ,  AMTSECHULBANNAP, AMTSECHULJEONGJEONG,  AMTSECHULTOT, AMTJAN                                                     \n");
        sb.append("     ,  AMTJEONGGI, AMTGONGGEUM, AMTSURPLUS                                                                             \n");
        sb.append("     ,  LAST_AMT, M220_AMTJAN, (NVL(AMTJAN,0)-NVL(AMTJEONGGI,0)-ROUND(NVL(SEGYE_AMT,0),0)) GONGGEUMJANAMT               \n");
        sb.append("     ,  ROUND(NVL(SEGYE_AMT, 0), 0) SEGYE_AMT                                                                           \n");
        sb.append("   FROM (                                                                                                               \n");
        sb.append("  SELECT M390_ACCGUBUN  ACCTYPE                                                                                         \n");                               
        sb.append("        ,CASE WHEN M390_ACCGUBUN = 'E'                                                                                  \n");                               
        sb.append("          AND M390_ACCCODE = '40'                                                                                       \n");                                  
        sb.append("         THEN '��Ÿ'                                                                                                    \n");                               
        sb.append("         WHEN M390_ACCGUBUN = 'A'                                                                                       \n");                                
        sb.append("         THEN '�Ϲ�ȸ��'                                                                                                \n");                                                                                                                                                                 
        sb.append("         WHEN M390_ACCGUBUN = 'B'                                                                                       \n");                                                                                                                                                      
        sb.append("         THEN 'Ư��ȸ��'                                                                                                \n");                                                                                                                                                                 
        sb.append("         WHEN M390_ACCGUBUN = 'D'                                                                                       \n");                                
        sb.append("         THEN '���Լ��������'                                                                                          \n");                                                                                                                                                           
        sb.append("         WHEN M390_ACCGUBUN = 'E'                                                                                       \n");                                                                                                                                                      
        sb.append("         AND M390_ACCCODE <> '40'                                                                                       \n");                                                                                                                                                      
        sb.append("         THEN '���'                                                                                                    \n");                                   
        sb.append("          END ACCTYPENM                                                                                                 \n");                                 
        sb.append("        ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) AS SUMGUBUN                                                    \n");                                
        sb.append("        ,A.M390_ACCCODE AS ACCCODE                                                                                      \n");                               
        sb.append("        ,DECODE(B.M360_ACCNAME,'�Ϲ�ȸ��','��û(����)', B.M360_ACCNAME) AS ACCNAME                                      \n");                                                                                                                                                                                                                                 
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT,0))  AMTSEIPJEONILTOT     -- ���� ���ϴ���             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIP,0)) AMTSEIP                        -- ���� ���ϼ���             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPGWAONAP,0)) AMTSEIPGWAONAP          -- ���� �������ݳ�           \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPJEONGJEONG    -- ���� ���������׵�         \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP                                       \n");
        sb.append("                   - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPTOT       -- ���� �Ѱ�                 \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT                                                  \n");
        sb.append("                                       + C.M220_AMTBAJEONGSURYUNG),0))  AMTBAEJUNG         -- �ڱݹ�����                \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT  -- ���� ���ϴ���             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHUL,0)) AMTSECHUL                    -- ���� ��������             \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULBANNAP,0)) AMTSECHULBANNAP        -- ���� �ݳ���               \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONGJEONG,0)) AMTSECHULJEONGJEONG  -- ���� ���������׵�       \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL - C.M220_AMTSECHULBANNAP         \n");
        sb.append("                       - C.M220_AMTSECHULJEONGJEONG),0)) AMTSECHULTOT                        -- ���� �Ѱ�               \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT                                                        \n");
        sb.append("                     + C.M220_AMTSEIP - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)                             \n");
        sb.append("         + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                     \n");                               
        sb.append("         - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                                    \n");
        sb.append("                                   - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                            \n");
        sb.append("         - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                                    \n");
        sb.append("         - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0)) AMTJAN                      -- �ܾ�                        \n");                                                                                                                                                                              
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'3',C.M220_AMTJEONGGI,0)) AMTJEONGGI               -- �ڱݿ�� ���⿹�ݵ�          \n");
        sb.append("        ,SUM(DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) AMTGONGGEUM               -- �ڱݿ�� �����ܾ�            \n");                                                                                                                                                                                             
        sb.append("        ,SUM(CASE WHEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0) > 0              \n");
        sb.append("                  THEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0)                  \n");
        sb.append("                  ELSE 0 END) AMTSURPLUS                                                -- �׿������Ծ�                 \n");                                                                                                                                                                                     
        sb.append("        ,SUM((DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP                                      \n");
        sb.append("                     - C.M220_AMTSEIPGWAONAP - C.M220_AMTSEIPJEONGJEONG,0)                                              \n");
        sb.append("              + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                \n");                                                        
        sb.append("              - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                               \n");
        sb.append("              - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                                                 \n");
        sb.append("              - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                               \n");                                 
        sb.append("              - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0))                                                       \n");                               
        sb.append("            - DECODE(A.M390_WORKGUBUN,'3',M220_AMTJEONGGI,0)                                                            \n");                               
        sb.append("            - DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) LAST_AMT  -- (�ܾ���ġ����: �ܾ� - ���⿹�� - �������)  \n");
        sb.append("        ,SUM(NVL(C.M220_AMTGONGGEUM,0))  M220_AMTJAN  -- ���� ���ϴ���                                                  \n");
        sb.append("        ,SUM(CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                        \n");
        sb.append("                 AND TRIM(M390_ACCCODE) IS NOT NULL                                                                     \n");
        sb.append("                THEN CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'A1'                                      \n");
        sb.append("                                    AND M390_ACCCODE = '11'                                                             \n");
        sb.append("                                  THEN NVL(M580_AMT,0)/237                                                              \n");
        sb.append("                          WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'A1'                                     \n");
        sb.append("                                    AND M390_ACCCODE IN ('15', '16')                                                    \n");
        sb.append("                                  THEN NVL(M580_AMT,0)                                                                  \n");
        sb.append("                                 ELSE NVL(M580_AMT,0)/3                                                                 \n");
        sb.append("                           END                                                                                          \n");
        sb.append("              WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                            \n");
        sb.append("                 AND TRIM(M390_ACCCODE) IS NULL                                                                         \n");
        sb.append("              THEN NVL(M580_AMT,0)                                                                                      \n");
        sb.append("               ELSE 0                                                                                                   \n");
        sb.append("       END) SEGYE_AMT    -- ������������                                                                                \n");
        sb.append("   FROM M390_USESEMOKCODE_T A                                                                                           \n");                              
        sb.append("       ,M360_ACCCODE_T B                                                                                                \n");                                  
        sb.append("       ,M220_DAY_T C                                                                                                    \n");                                
        sb.append("       ,M370_SEMOKCODE_T D                                                                                              \n");
        sb.append("       ,(SELECT SUM(M580_AMT) M580_AMT \n");
        sb.append("              , M580_ACCTYPE, M580_ACCCODE \n");
        sb.append("           FROM M580_SEGYE_T \n");
        sb.append("          WHERE M580_YEAR = ? \n");
        sb.append("            AND M580_DATE <= ? \n");
        sb.append("          GROUP BY M580_ACCTYPE, M580_ACCCODE) E                                                                                 \n");                            
        sb.append("  WHERE A.M390_YEAR           = B.M360_YEAR                                                                             \n");                                               
        sb.append("    AND A.M390_ACCGUBUN       = B.M360_ACCGUBUN                                                                         \n");                                           
        sb.append("    AND A.M390_ACCCODE        = B.M360_ACCCODE                                                                          \n");                                
        sb.append("    AND A.M390_YEAR            = C.M220_YEAR(+)                                                                         \n");                                     
        sb.append("    AND A.M390_PARTCODE        = C.M220_PARTCODE(+)                                                                     \n");                                   
        sb.append("    AND A.M390_ACCGUBUN       = C.M220_ACCTYPE(+)                                                                       \n");                              
        sb.append("    AND A.M390_ACCCODE        = C.M220_ACCCODE(+)                                                                       \n");                              
        sb.append("    AND A.M390_SEMOKCODE      = C.M220_SEMOKCODE(+)                                                                     \n");                              
        sb.append("    AND D.M370_SEGUMGUBUN <> '2'         --  ��������                                                                   \n");                                
        sb.append("    AND A.M390_ACCGUBUN <> 'D'                                                                                          \n");                              
        sb.append("    AND A.M390_YEAR           = D.M370_YEAR                                                                             \n");                                             
        sb.append("    AND A.M390_ACCGUBUN       = D.M370_ACCGUBUN                                                                         \n");                                           
        sb.append("    AND A.M390_ACCCODE        = D.M370_ACCCODE                                                                          \n");                                            
        sb.append("    AND A.M390_WORKGUBUN        = D.M370_WORKGUBUN                                                                      \n");                                              
        sb.append("    AND A.M390_SEMOKCODE        = D.M370_SEMOKCODE                                                                      \n");
        sb.append("    AND A.M390_ACCGUBUN       = E.M580_ACCTYPE(+)                                                                           \n");                                           
        sb.append("    AND A.M390_ACCCODE        = E.M580_ACCCODE(+)                                                                             \n");                                            
        sb.append("    AND A.M390_ACCGUBUN = 'A'                                                                                           \n");                               
        sb.append("    AND A.M390_YEAR = ?                                                                                                 \n");                                                        
        sb.append("    AND C.M220_DATE(+) = ?                                                                                              \n");
        
        if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND A.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND A.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND A.M390_ACCCODE = ?								\n");
		}
        sb.append(" GROUP BY ROLLUP(A.M390_ACCGUBUN                                                                                        \n");
        sb.append("         ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1))                                                               \n");
        sb.append("         ,(A.M390_ACCCODE                                                                                               \n");
        sb.append("         ,B.M360_ACCNAME))                                                                                              \n");
        sb.append(" HAVING ((A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) = 'A1'                                                         \n");
        sb.append("       OR (A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1)) IS NULL                                                       \n");
        sb.append("       OR A.M390_ACCCODE IS NOT NULL) -- �Ϲ�ȸ��-��û, Ư��ȸ��-�������� �߰��Ұ踦 �����ش�                         \n");
        sb.append(" ORDER BY ACCTYPE                                                                                                       \n");
        sb.append("         ,(A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1))                                                               \n");
        sb.append("         ,A.M390_ACCCODE                                                                                                \n");
        sb.append("         )                                                                                                              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
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
	/* ���� ��ȸ2 */
	public static List<CommonEntity> getReportList2(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ACCTYPE, ACCTYPENM, SUMGUBUN, ACCCODE, ACCNAME                                                                          \n");
        sb.append("         ,  AMTSEIPJEONILTOT, AMTSEIP, AMTSEIPGWAONAP, AMTSEIPJEONGJEONG                                                        \n");
        sb.append("         ,  AMTSEIPTOT, AMTBAEJUNG, AMTSECHULJEONILTOT, AMTSECHUL                                                               \n");
        sb.append("         ,  AMTSECHULBANNAP, AMTSECHULJEONGJEONG,  AMTSECHULTOT, AMTJAN                                                         \n");
        sb.append("         ,  AMTJEONGGI, AMTGONGGEUM, AMTSURPLUS                                                                                 \n");
        sb.append("         ,  LAST_AMT, M220_AMTJAN, (NVL(AMTJAN,0)-NVL(AMTJEONGGI,0)-ROUND(NVL(SEGYE_AMT,0),0)) GONGGEUMJANAMT                   \n");
        sb.append("         ,  ROUND(NVL(SEGYE_AMT, 0), 0) SEGYE_AMT                                                                               \n");
        sb.append("    FROM (                                                                                                                      \n");
        sb.append("         SELECT DECODE(M390_ACCGUBUN, 'E', DECODE(A.M390_ACCCODE, '40', 'F', M390_ACCGUBUN), M390_ACCGUBUN) ACCTYPE             \n");                            
        sb.append("               ,CASE WHEN M390_ACCGUBUN = 'E'                                                                                   \n");                            
        sb.append("                 AND M390_ACCCODE = '40'                                                                                        \n");                               
        sb.append("                THEN '��Ÿ'                                                                                                     \n");                            
        sb.append("                WHEN M390_ACCGUBUN = 'A'                                                                                        \n");                             
        sb.append("                THEN '�Ϲ�ȸ��'                                                                                                 \n");                                                                                                                                                              
        sb.append("                WHEN M390_ACCGUBUN = 'B'                                                                                        \n");                                                                                                                                                   
        sb.append("                THEN 'Ư��ȸ��'                                                                                                 \n");                                                                                                                                                              
        sb.append("                WHEN M390_ACCGUBUN = 'D'                                                                                        \n");                             
        sb.append("                THEN '���Լ��������'                                                                                           \n");                                                                                                                                                        
        sb.append("                WHEN M390_ACCGUBUN = 'E'                                                                                        \n");                                                                                                                                                   
        sb.append("            AND M390_ACCCODE <> '40'                                                                                            \n");                                                                                                                                               
        sb.append("            THEN '���'                                                                                                         \n");                            
        sb.append("            END ACCTYPENM                                                                                                       \n");                           
        sb.append("               ,DECODE(M390_ACCGUBUN, 'E', DECODE(M390_ACCCODE, '16', 'E9','17', 'E9','18', 'E9',                               \n");
        sb.append("               M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)),M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)) SUMGUBUN                     \n");
        sb.append("               ,A.M390_ACCCODE AS ACCCODE                                                                                       \n");                            
        sb.append("               ,DECODE(B.M360_ACCNAME,'�Ϲ�ȸ��','��û(����)', B.M360_ACCNAME) AS ACCNAME                                       \n");                                                                                                                                                                                                                              
        sb.append("           ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT,0))  AMTSEIPJEONILTOT  -- ���� ���ϴ���                     \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIP,0)) AMTSEIP                 -- ���� ���ϼ���                     \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPGWAONAP,0)) AMTSEIPGWAONAP   -- ���� �������ݳ�                   \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPJEONGJEONG  -- ���� ���������׵�            \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP - C.M220_AMTSEIPGWAONAP                \n");
        sb.append("               - C.M220_AMTSEIPJEONGJEONG,0)) AMTSEIPTOT       -- ���� �Ѱ�                                                     \n");                                                                                                                                                  
        sb.append("           ,SUM(DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT                                                       \n");
        sb.append("           + C.M220_AMTBAJEONGSURYUNG),0))  AMTBAEJUNG -- �ڱݹ�����                                                            \n");
        sb.append("           ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONILTOT,0)) AMTSECHULJEONILTOT        -- ���� ���ϴ���            \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHUL,0)) AMTSECHUL                      -- ���� ��������            \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULBANNAP,0)) AMTSECHULBANNAP          -- ���� �ݳ���              \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'1',C.M220_AMTSECHULJEONGJEONG,0)) AMTSECHULJEONGJEONG  -- ���� ���������׵�        \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                                   \n");
        sb.append("               - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)) AMTSECHULTOT    -- ���� �Ѱ�                          \n");                                                                                                                                                                       
        sb.append("           ,SUM(DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP - C.M220_AMTSEIPGWAONAP                    \n");
        sb.append("           - C.M220_AMTSEIPJEONGJEONG,0)                                                                                        \n");
        sb.append("                + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                      \n");                                                      
        sb.append("                - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                                     \n");
        sb.append("                - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                                                       \n");
        sb.append("                - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                                     \n");                      
        sb.append("                - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0)) AMTJAN             -- �ܾ�                                  \n");                                                                                                                                                                  
        sb.append("           ,SUM(DECODE(A.M390_WORKGUBUN,'3',C.M220_AMTJEONGGI,0)) AMTJEONGGI           -- �ڱݿ�� ���⿹�ݵ�                   \n");
        sb.append("               ,SUM(DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) AMTGONGGEUM       -- �ڱݿ�� �����ܾ�                     \n");                                                                                                                                                                                  
        sb.append("           ,SUM(CASE WHEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0) > 0                   \n");
        sb.append("                         THEN DECODE(A.M390_WORKGUBUN,'0',(C.M220_AMTSURPLUSJEONILTOT + C.M220_AMTSURPLUS),0)                   \n");
        sb.append("                         ELSE 0 END) AMTSURPLUS                                                 -- �׿������Ծ�                 \n");                                                                                                                                                                                   
        sb.append("           ,SUM((DECODE(A.M390_WORKGUBUN,'0',C.M220_AMTSEIPJEONILTOT + C.M220_AMTSEIP - C.M220_AMTSEIPGWAONAP                   \n");
        sb.append("           - C.M220_AMTSEIPJEONGJEONG,0)                                                                                        \n");
        sb.append("                     + DECODE(A.M390_WORKGUBUN,'2',(C.M220_AMTBAJEONGSUJEONILTOT + C.M220_AMTBAJEONGSURYUNG),0)                 \n");                                                     
        sb.append("                     - DECODE(A.M390_WORKGUBUN,'1',(C.M220_AMTSECHULJEONILTOT + C.M220_AMTSECHUL                                \n");
        sb.append("                     - C.M220_AMTSECHULBANNAP - C.M220_AMTSECHULJEONGJEONG),0)                                                  \n");
        sb.append("                     - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONGJEONILTOT,0)                                                \n");                              
        sb.append("                     - DECODE(A.M390_WORKGUBUN,'2',C.M220_AMTBAJEONG,0))                                                        \n");                            
        sb.append("                   - DECODE(A.M390_WORKGUBUN,'3',M220_AMTJEONGGI,0)                                                             \n");                            
        sb.append("                   - DECODE(A.M390_WORKGUBUN,'3',M220_AMTGONGGEUM,0)) LAST_AMT  -- (�ܾ���ġ����: �ܾ� - ���⿹�� - �������)   \n");
        sb.append("           ,SUM(NVL(C.M220_AMTGONGGEUM,0))  M220_AMTJAN                                          -- ���� ���ϴ���               \n");
        sb.append("           ,SUM(CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                             \n");
        sb.append("                        AND TRIM(M390_ACCCODE) IS NOT NULL                                                                      \n");
        sb.append("                     THEN  CASE WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'B2'                                       \n");
        sb.append("                                           AND M390_ACCCODE = '21'                                                              \n");
        sb.append("                                         THEN NVL(M580_AMT,0)/6                                                                 \n");
        sb.append("                                         WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'B3'                              \n");
        sb.append("                                           AND M390_ACCCODE = '31'                                                              \n");
        sb.append("                                         THEN NVL(M580_AMT,0)/32                                                                \n");
        sb.append("                                         WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'E9'                              \n");
        sb.append("                                           AND M390_ACCCODE IN ('16', '17', '18')                                               \n");
        sb.append("                                         THEN NVL(M580_AMT,0)                                                                   \n");
        sb.append("                                          WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'E1'                             \n");
        sb.append("                                           AND M390_ACCCODE IN ('14')                                                           \n");
        sb.append("                                         THEN NVL(M580_AMT,0)/4                                                                 \n");
        sb.append("                                         WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) = 'E4'                              \n");
        sb.append("                                           AND M390_ACCCODE IN ('40')                                                           \n");
        sb.append("                                         THEN NVL(M580_AMT,0)/5                                                                 \n");
        sb.append("                                        ELSE NVL(M580_AMT,0)/3                                                                  \n");
        sb.append("                                  END                                                                                           \n");
        sb.append("                     WHEN A.M390_ACCGUBUN || SUBSTR(A.M390_ACCCODE,1,1) IS NOT NULL                                             \n");
        sb.append("                        AND TRIM(M390_ACCCODE) IS NULL                                                                          \n");
        sb.append("                     THEN NVL(M580_AMT,0)                                                                                       \n");
        sb.append("                      ELSE 0                                                                                                    \n");
        sb.append("              END) SEGYE_AMT                                                                                                    \n");
        sb.append("           FROM M390_USESEMOKCODE_T A                                                                                           \n");                            
        sb.append("               ,M360_ACCCODE_T B                                                                                                \n");                                
        sb.append("               ,M220_DAY_T C                                                                                                    \n");                              
        sb.append("               ,M370_SEMOKCODE_T D                                                                                              \n");
        sb.append("               ,(SELECT SUM(M580_AMT) M580_AMT \n");
        sb.append("                      , M580_ACCTYPE, M580_ACCCODE \n");
        sb.append("                   FROM M580_SEGYE_T \n");
        sb.append("                  WHERE M580_YEAR = ? \n");
        sb.append("                    AND M580_DATE <= ? \n");
        sb.append("                  GROUP BY M580_ACCTYPE, M580_ACCCODE) E                                                                        \n");
        sb.append("          WHERE A.M390_YEAR           = B.M360_YEAR                                                                             \n");                                             
        sb.append("            AND A.M390_ACCGUBUN       = B.M360_ACCGUBUN                                                                         \n");                                         
        sb.append("            AND A.M390_ACCCODE        = B.M360_ACCCODE                                                                          \n");                              
        sb.append("            AND A.M390_YEAR            = C.M220_YEAR(+)                                                                         \n");                                   
        sb.append("            AND A.M390_PARTCODE        = C.M220_PARTCODE(+)                                                                     \n");                                 
        sb.append("            AND A.M390_ACCGUBUN       = C.M220_ACCTYPE(+)                                                                       \n");                            
        sb.append("            AND A.M390_ACCCODE        = C.M220_ACCCODE(+)                                                                       \n");                            
        sb.append("            AND A.M390_SEMOKCODE      = C.M220_SEMOKCODE(+)                                                                     \n");                            
        sb.append("            AND D.M370_SEGUMGUBUN <> '2'                      --  ��������                                                      \n");
        sb.append("        AND A.M390_ACCGUBUN <> 'D'                                                                                              \n");                        
        sb.append("        AND A.M390_YEAR           = D.M370_YEAR                                                                                 \n");                                       
        sb.append("            AND A.M390_ACCGUBUN       = D.M370_ACCGUBUN                                                                         \n");                                         
        sb.append("            AND A.M390_ACCCODE        = D.M370_ACCCODE                                                                          \n");                                          
        sb.append("            AND A.M390_WORKGUBUN        = D.M370_WORKGUBUN                                                                      \n");                                            
        sb.append("            AND A.M390_SEMOKCODE        = D.M370_SEMOKCODE                                                                      \n");
        sb.append("    AND A.M390_ACCGUBUN       = E.M580_ACCTYPE(+)                                                                           \n");                                           
        sb.append("    AND A.M390_ACCCODE        = E.M580_ACCCODE(+)                                                                             \n");                                            
        sb.append("            AND A.M390_ACCGUBUN IN ('B','E')                                                                                    \n");                             
        sb.append("        AND A.M390_YEAR = ?                                                                                                     \n");
        sb.append("        AND C.M220_DATE(+) = ?                                                                                                  \n");
        if(!paramInfo.getString("acc_type").equals("")){
			sb.append("   AND A.M390_ACCGUBUN = ?								\n");
		}
		if(!paramInfo.getString("part_code").equals("")){
			sb.append("   AND A.M390_PARTCODE = ?								\n");
		}
		if(!paramInfo.getString("acc_code").equals("")){
			sb.append("   AND A.M390_ACCCODE = ?								\n");
		}
        sb.append("         GROUP BY ROLLUP(A.M390_ACCGUBUN ,DECODE(A.M390_ACCGUBUN, 'E',                                                          \n");
        sb.append("         DECODE(A.M390_ACCCODE, '40', 'F', A.M390_ACCGUBUN),A.M390_ACCGUBUN)                                                    \n");
        sb.append("                 ,DECODE(M390_ACCGUBUN, 'E', DECODE(M390_ACCCODE, '16', 'E9','17', 'E9','18', 'E9',                             \n");
        sb.append("                 M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)),M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1))                            \n");
        sb.append("                 ,(A.M390_ACCCODE                                                                                               \n");
        sb.append("                 ,B.M360_ACCNAME))                                                                                              \n");
        sb.append("         HAVING (DECODE(M390_ACCGUBUN, 'E', DECODE(M390_ACCCODE, '16', 'E9','17', 'E9','18', 'E9',                              \n");
        sb.append("         M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)),M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)) IN ('A1','B3','E9')                \n");
        sb.append("                 OR (DECODE(M390_ACCGUBUN, 'E', DECODE(M390_ACCCODE, '16', 'E9','17', 'E9','18', 'E9',                          \n");
        sb.append("                 M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)),M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1))) IS NULL                   \n");
        sb.append("                 OR A.M390_ACCCODE IS NOT NULL)    -- �Ϲ�ȸ��-��û, Ư��ȸ��-�������� �߰��Ұ踦 �����ش�                    \n");
        sb.append("         ORDER BY ACCTYPE                                                                                                       \n");
        sb.append("                 ,DECODE(M390_ACCGUBUN, 'E', DECODE(M390_ACCCODE, '16', 'E9','17', 'E9','18', 'E9',                             \n");
        sb.append("                  M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1)),M390_ACCGUBUN|| SUBSTR(M390_ACCCODE,1,1))                           \n");
        sb.append("                 ,A.M390_ACCCODE                                                                                                \n");
        sb.append("         )                                                                                                                      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
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
}
