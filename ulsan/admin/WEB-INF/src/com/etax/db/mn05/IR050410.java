/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR050410.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �ڱ�(��)������ ��ȸ
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050410 {

	/* �ڱ�(��)���������� ��ȸ */
    public static List<CommonEntity> getBankAllotNoticeList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M100_YEAR                                     \n");
		sb.append("       ,M100_BUDGETAMT/1000 M100_BUDGETAMT           \n");
        sb.append("       ,M100_BUDGETALLOTAMT/1000 M100_BUDGETALLOTAMT \n");
        sb.append("       ,M100_ORIALLOTAMT/1000 M100_ORIALLOTAMT       \n");
        sb.append("       ,M100_TOTALJICHULAMT/1000 M100_TOTALJICHULAMT \n");
        sb.append("       ,M100_JANAMT/1000 M100_JANAMT                 \n");
        sb.append("       ,(M100_SOYOAMT                                \n");
        sb.append("        +M100_SOYOAMT2                               \n");
        sb.append("        +M100_SOYOAMT3                               \n");
        sb.append("        +M100_SOYOAMT4                               \n");
        sb.append("        +M100_SOYOAMT5                               \n");
        sb.append("        +M100_SOYOAMT6                               \n");
        sb.append("        +M100_SOYOAMT7                               \n");
        sb.append("        +M100_SOYOAMT8                               \n");
        sb.append("        +M100_SOYOAMT9                               \n");
        sb.append("        +M100_SOYOAMT10                              \n");
        sb.append("        )/1000 M100_SOYOAMT                          \n");
        sb.append("       ,M100_ALLOTAMT/1000 M100_ALLOTAMT             \n");
		sb.append("      ,M100_DOCUMENTNO                               \n");
		sb.append("      ,DECODE(M100_ALLOTSTATE, 'S1', '�䱸���'      \n");
		sb.append("      ,'S2', '����ҽ���', 'S3', '����������'        \n");
		sb.append("      ,'S4', '����ó��', 'S5', 'å���ڽ���'          \n");
		sb.append("      ,'S6', '�����Ϸ�') M100_ALLOTSTATE_NAME        \n");
		sb.append("      ,DECODE(M100_ALLOTCODE, '0', '��ó��'          \n");
		sb.append("      ,'1', '����ó��', '9', '�����Ա�'              \n");
		sb.append("             ) M100_ALLOTCODE_NAME                   \n");
        sb.append("  FROM M100_MONEYALLOT_T                             \n");
        sb.append(" WHERE M100_YEAR = ?                                 \n");
        sb.append("   AND M100_DATE = ?                                 \n");
        if (!"0".equals(paramInfo.getString("report_gubun")) ) {
            sb.append("   AND M100_ALLOTGUBUN = ?                       \n");
        }
		sb.append("   AND M100_DOCUMENTNO IS NOT NULL                   \n");
		sb.append(" ORDER BY M100_DOCUMENTNO                            \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("allot_date"));
        if (!"0".equals(paramInfo.getString("report_gubun")) ) {
            parameters.setString(idx++, paramInfo.getString("report_gubun"));
        }

        return template.getList(conn, parameters);
    } 


	/* �ڱ�(��)����������- ����ڸ� ��� */
    public static CommonEntity getManagerName(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M180_TASKID                                       \n");
		sb.append("      ,M180_TASKNAME                                     \n");
		sb.append("      ,M180_MIDDLEID                                     \n");
		sb.append("      ,M180_MIDDLENAME                                   \n");
		sb.append("      ,M180_UPPERID                                      \n");
		sb.append("      ,M180_UPPERNAME                                    \n");
		sb.append("      ,M180_COOPERATIONID                                \n");
		sb.append("      ,M180_COOPERATIONNAME                              \n");
        sb.append("      ,M180_SIGNFINISHSTATE                              \n");
        sb.append("      ,M180_DOCUMENTCODE                                 \n");
        sb.append("  FROM M180_DOCUMENT_T                                   \n");
		sb.append(" WHERE M180_YEAR = ?                                     \n");
        sb.append("   AND M180_DOCUMENTNO = TO_NUMBER(SUBSTR(?, 5, 4))      \n");
		sb.append("   AND M180_DOCUMENTCODE IN ('ED00', 'ED05', 'ED06')     \n");// �ڱݹ�����������

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("doc_no"));

        return template.getData(conn, parameters);
    }


	/* �ڱ�(��)����������- �ڱ�(��)��������(��������, ������) - 1���� */
    public static List<CommonEntity> getBankReportList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
        sb.append(" WITH TMPTBL AS (                                                                                \n");                                                                                           
        sb.append("SELECT M100_YEAR                                                                                                                                 \n");
        sb.append("         , M100_DATE                                                                                                                             \n");
        sb.append("         , M100_PARTCODE                                                                                                                         \n");
        sb.append("         , M100_DOCUMENTNO                                                                                                                       \n");
        sb.append("         , M110_BUDGETAMT                                                                                                                        \n");
        sb.append("         , M110_BUDGETALLOTAMT                                                                                                                   \n");
        sb.append("         , M100_ORIALLOTAMT                                                                                                                      \n");
        sb.append("         ,SUM_ALLOTAMT1 AS SUM_ALLOTAMT                                                                                                          \n");
        sb.append("        FROM (                                                                                                                                   \n");
        sb.append("         SELECT A.*                                                                                                                              \n");
        sb.append("               , SUM(SUM_ALLOTAMT) OVER(PARTITION BY M100_DATE, M100_PARTCODE ) AS SUM_ALLOTAMT1                                                 \n");
        //2012.01.16 ���� �հ踦 �������� ���ؼ� G1_POSI ASC �κ��� ������
        //sb.append("               , ROW_NUMBER() OVER(PARTITION BY M100_YEAR, M100_PARTCODE ORDER BY M100_DATE DESC, G1_POSI ASC) AS G2_POSI                        \n");
        sb.append("               , ROW_NUMBER() OVER(PARTITION BY M100_YEAR, M100_PARTCODE ORDER BY M100_DATE DESC) AS G2_POSI                                     \n");
        sb.append("           FROM (                                                                                                                                \n");
        sb.append("            SELECT  M100_YEAR                                                                                                                    \n");
        sb.append("                    , M100_DATE                                                                                                                  \n");
        sb.append("                    , M100_PARTCODE                                                                                                              \n");
        sb.append("                    , M100_DOCUMENTNO                                                                                                            \n");
        sb.append("                    , CASE WHEN M100_DOCUMENTNO = ? THEN MIN(M100_ORIALLOTAMT)  OVER(PARTITION BY M100_DATE, M100_PARTCODE, M100_SEQ )           \n");
        sb.append("                      ELSE M100_ORIALLOTAMT + SUM(M100_ALLOTAMT) OVER(PARTITION BY M100_DATE, M100_PARTCODE, M100_DOCUMENTNO )                   \n");
        sb.append("                      END M100_ORIALLOTAMT                                                                                                       \n");
        sb.append("                   , CASE WHEN M100_DOCUMENTNO = ? THEN M100_ALLOTAMT                                                                \n");
        sb.append("                     ELSE 0                                                                                                                      \n");
        sb.append("                     END SUM_ALLOTAMT                                                                                                            \n");
        sb.append("                  , CASE                                                                                                                         \n");
        sb.append("                    WHEN M100_DOCUMENTNO = ? THEN ROW_NUMBER() OVER(PARTITION BY M100_YEAR, M100_DATE, M100_PARTCODE ORDER BY M100_SEQ ASC)            \n");
        sb.append("                    ELSE ROW_NUMBER() OVER(PARTITION BY M100_YEAR, M100_DATE , M100_PARTCODE ORDER BY M100_SEQ DESC)                             \n");
        sb.append("                   END AS G1_POSI                                                                                                                \n");
        sb.append("          FROM M100_MONEYALLOT_T                                                                                                                 \n");
        sb.append("        WHERE 1 = 1                                                                                                                              \n");
        sb.append("            AND  M100_YEAR = ?                                                                                                                   \n");
        sb.append("            AND M100_ALLOTGUBUN = ?                                                                                                              \n");
        sb.append("            AND M100_DATE <= ?                                                                                                                   \n");
		sb.append("            AND M100_ALLOTSTATE IN ('S4', 'S5', 'S6')																				 \n");
        sb.append("       ) A                                                                                                                                       \n");
        sb.append("    ) A                                                                                                                                          \n");
        sb.append("         , M110_MONEYALLOTCONDITION_T B                                                                                                          \n");
        sb.append("     WHERE M100_YEAR = M110_YEAR                                                                                                                 \n");
        sb.append("       AND M100_DATE = M110_DATE                                                                                                                 \n");
        sb.append("       AND M100_PARTCODE = M110_PARTCODE                                                                                                         \n");
        sb.append("    AND  G2_POSI = 1                                                                                                                             \n");                                                                              
        sb.append("     )                                                                                           \n");                                                                                       
        sb.append("    SELECT A.*                                                                                   \n");                                                                                      
        sb.append("        , (M110_BUDGETAMT - TOT_AMT) AS YESAN                                                    \n");                                                                                 
        sb.append("        , (M110_BUDGETALLOTAMT - TOT_AMT) AS BAE                                                 \n");                                                                                 
        sb.append("      FROM (                                                                                     \n");                                                                                     
        sb.append("        SELECT CASE WHEN ? = '1' THEN '�����հ�'                                                 \n");
        sb.append("                    WHEN ? = '2' THEN '������հ�' END M350_PARTNAME                             \n");
        sb.append("               , '' AS M100_PARTCODE                                                             \n");                                                                                         
        sb.append("               , '' AS M100_DATE                                                                 \n");                                                                                         
        sb.append("               , TRUNC(SUM(M110_BUDGETAMT)/1000) AS M110_BUDGETAMT                               \n");                                                                                         
        sb.append("               , TRUNC(SUM(M110_BUDGETALLOTAMT)/1000) AS M110_BUDGETALLOTAMT                     \n");                                                                                         
        sb.append("               , TRUNC(SUM(M100_ORIALLOTAMT)/1000) AS M110_ORIALLOTAMT                           \n");                                                                                         
        sb.append("               , TRUNC(SUM(SUM_ALLOTAMT)/1000) AS ALLOTAMT                                       \n");                                                                                         
        sb.append("               , TRUNC(SUM(M100_ORIALLOTAMT + SUM_ALLOTAMT)/1000) AS TOT_AMT                     \n");                                                                                         
        sb.append("          FROM TMPTBL                                                                            \n");                                                                                           
        sb.append("         UNION ALL                                                                               \n");                                                                                            
        sb.append("        SELECT '��1�����հ�' AS  M350_PARTNAME                                                   \n");                                                                                         
        sb.append("               , '' AS M100_PARTCODE                                                             \n");                                                                                        
        sb.append("               , '' AS M100_DATE                                                                 \n");                                                                                         
        sb.append("               , TRUNC(SUM(M110_BUDGETAMT)/1000) AS M110_BUDGETAMT                               \n");                                                                                         
        sb.append("               , TRUNC(SUM(M110_BUDGETALLOTAMT)/1000) AS M110_BUDGETALLOTAMT                     \n");                                                                                         
        sb.append("               , TRUNC(SUM(M100_ORIALLOTAMT)/1000) AS M110_ORIALLOTAMT                           \n");                                                                                         
        sb.append("               , TRUNC(SUM(SUM_ALLOTAMT)/1000) AS ALLOTAMT                                       \n");                                                                                         
        sb.append("               , TRUNC(SUM(M100_ORIALLOTAMT + SUM_ALLOTAMT)/1000) AS TOT_AMT                     \n");                                                                                         
        sb.append("          FROM TMPTBL                                                                            \n");                                                                                           
        sb.append("         WHERE M100_PARTCODE <> '00000'                                                          \n");                                                                                            
        sb.append("         UNION ALL                                                                               \n");                                                                                              
        sb.append("        SELECT M350_PARTNAME                                                                     \n");                                                                                            
        sb.append("             , M100_PARTCODE                                                                     \n");                                                                                       
        sb.append("             , M100_DATE                                                                         \n");                                                                                       
        sb.append("             , M110_BUDGETAMT                                                                    \n");                                                                                       
        sb.append("             , M110_BUDGETALLOTAMT                                                               \n");                                                                                       
        sb.append("             , M110_ORIALLOTAMT                                                                  \n");                                                                                       
        sb.append("             , ALLOTAMT                                                                          \n");                                                                                       
        sb.append("             , TOT_AMT                                                                           \n");                                                                                       
        sb.append("          FROM (                                                                                 \n");                                                                                           
        sb.append("            SELECT REPLACE(M350_PARTNAME, '(��)', '') M350_PARTNAME                              \n");                                                                                                
        sb.append("                 , M350_PARTCODE AS M100_PARTCODE                                                \n");                                                                                           
        sb.append("                 , M100_DATE                                                                     \n");                                                                                           
        sb.append("                 , TRUNC(M110_BUDGETAMT/1000) AS M110_BUDGETAMT                                  \n");                                                                                           
        sb.append("                 , TRUNC(M110_BUDGETALLOTAMT/1000) AS M110_BUDGETALLOTAMT                        \n");                                                                                           
        sb.append("                 , TRUNC(M100_ORIALLOTAMT/1000) AS M110_ORIALLOTAMT                              \n");                                                                                           
        sb.append("                 , TRUNC(SUM_ALLOTAMT/1000) AS ALLOTAMT                                          \n");                                                                                           
        sb.append("                 , TRUNC((M100_ORIALLOTAMT + SUM_ALLOTAMT)/1000) TOT_AMT                         \n");                                                                                           
        sb.append("              FROM TMPTBL A                                                                      \n");                                                                                               
        sb.append("                 , M350_PARTCODE_T B                                                             \n");                                                                                           
        sb.append("             WHERE M350_YEAR = M100_YEAR(+)                                                      \n");                                                                                                
        sb.append("               AND M350_PARTCODE = M100_PARTCODE(+)                                              \n");                                                                                            
        sb.append("               AND M350_YEAR = ?                                                                 \n");                                                                                       
        sb.append("               AND CASE WHEN ? = '1' AND  M350_REALLOTPARTYN = 'N' THEN '1'                      \n");                                                                                                      
        sb.append("                        WHEN ? = '2' AND  M350_REALLOTPARTYN = 'Y' THEN '1'                      \n");                                                                                                      
        sb.append("                        WHEN ? = '2' AND  M350_REALLOTPARTYN != 'Y'                              \n");
        sb.append("                         AND M350_PARTCODE = '00000' THEN '1'                                    \n");                                                           
        sb.append("                        ELSE '0'                                                                 \n");                                                                                                       
        sb.append("                    END  = '1'                                                                   \n");                                                                                                  
        sb.append("               AND M350_PARTCODE NOT IN ('00110', '00140', '00170', '00200', '00710')            \n"); 
        sb.append("               AND NOT M350_PARTCODE BETWEEN '00001' AND '00099'                                 \n");
        sb.append("               )                                                                                 \n");                                                                                              
        sb.append("           ) A                                                                                   \n");                                                                                              
        sb.append("     ORDER BY CASE WHEN M350_PARTNAME LIKE '%���հ�' THEN '00000'                                     \n");                                                                                                  
        sb.append("                   WHEN M350_PARTNAME = '��1�����հ�' THEN '00002'                               \n");                                                                                                  
        sb.append("                   WHEN M350_PARTNAME = '��û'    THEN '00001'                                   \n");                                                                                                  
        sb.append("                   ELSE M100_PARTCODE END                                                        \n"); 

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("doc_no"));
		parameters.setString(idx++, paramInfo.getString("doc_no"));
        parameters.setString(idx++, paramInfo.getString("doc_no"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));
        parameters.setString(idx++, paramInfo.getString("report_gubun"));

        return template.getList(conn, parameters);
    }
  
  


  /* �ڱ�(��)����������- �ڱ�(��)��������(�������) - 1���� */
  public static CommonEntity getOriAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT NVL(M100_ORIALLOTAMT,0)/1000 M100_ORIALLOTAMT                 \n");
    sb.append("  FROM M100_MONEYALLOT_T                                        \n");
    sb.append(" WHERE M100_YEAR = ?                                            \n");
    sb.append("   AND M100_DATE = ?                                            \n");
    sb.append("   AND M100_ACCCODE = ?                                         \n");
    sb.append("   AND M100_ALLOTGUBUN = ?                                      \n");
    sb.append("   AND M100_SEQ = (SELECT MIN(M100_SEQ) FROM M100_MONEYALLOT_T  \n");
    sb.append("                    WHERE M100_YEAR = ?                         \n");
    sb.append("                      AND M100_DATE = ?                         \n");
    sb.append("                      AND M100_ACCCODE = ?                      \n");
    sb.append("                      AND M100_ALLOTGUBUN = ? )                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("report_gubun"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("report_gubun"));

    return template.getData(conn, parameters);
  }


  /* �ڱ�(��)����������- �ڱ�(��)��������(��ȸ������) - 1���� */
  public static CommonEntity getBoyuAllotAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT NVL(SUM(NVL(M100_ALLOTAMT, 0)),0)/1000 M100_ALLOTAMT  \n");
    sb.append("  FROM M100_MONEYALLOT_T                 \n");
    sb.append(" WHERE M100_YEAR = ?                     \n");
    sb.append("   AND M100_DATE = ?                     \n");
    if ("10".equals(paramInfo.getString("M360_ACCCODE")) ) {
      if ("1".equals(paramInfo.getString("report_gubun")) ) {
        sb.append("   AND M100_ACCCODE BETWEEN '22' AND '41'  \n");
      } else if ("2".equals(paramInfo.getString("report_gubun")) ) {
        sb.append("   AND M100_ACCCODE BETWEEN '62' AND '89'  \n");
      }
    } else if (!"00".equals(paramInfo.getString("M360_ACCCODE")) ) {
      sb.append("   AND M100_ACCCODE = ?                \n");
    }
    sb.append("   AND M100_ALLOTGUBUN = ?               \n");
    sb.append("   AND M100_DOCUMENTNO = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
    if (!"00".equals(paramInfo.getString("M360_ACCCODE")) && !"10".equals(paramInfo.getString("M360_ACCCODE"))) {
      parameters.setString(idx++, paramInfo.getString("M360_ACCCODE"));
    }
    parameters.setString(idx++, paramInfo.getString("report_gubun"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getData(conn, parameters);
  }


  /* �ڱ�(��)����������- �ڱ�(��)��������(��ȸ������) - 1���� */
  public static CommonEntity getAllotAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT NVL(SUM(NVL(M100_ALLOTAMT, 0)),0)/1000 M100_ALLOTAMT  \n");
    sb.append("  FROM M100_MONEYALLOT_T                 \n");
    sb.append(" WHERE M100_YEAR = ?                     \n");
    sb.append("   AND M100_DATE = ?                     \n");
    if (!"00".equals(paramInfo.getString("M360_ACCCODE")) ) {
      sb.append("   AND M100_ACCCODE = ?                \n");
    }
    sb.append("   AND M100_ALLOTGUBUN = ?               \n");
    sb.append("   AND M100_DOCUMENTNO = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
    if (!"00".equals(paramInfo.getString("M360_ACCCODE")) ) {
      parameters.setString(idx++, paramInfo.getString("acc_code"));
    }
    parameters.setString(idx++, paramInfo.getString("report_gubun"));
    parameters.setString(idx++, paramInfo.getString("doc_no"));

    return template.getData(conn, parameters);
  }


  /* �ڱ�(��)����������- �ڱ�(��)��������(��������, ������) - ��û */
  public static CommonEntity getBonData(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append(" SELECT '00000' M350_PARTCODE                                  \n");
    sb.append("       ,'�� û' M350_PARTNAME                                  \n");
    sb.append("       ,MAX(M110_ACCCODE) M110_ACCCCODE                        \n");
    sb.append("       ,NVL(SUM(NVL(M110_BUDGETAMT, 0)),0)/1000 M110_BUDGETAMT             \n");
    sb.append("       ,NVL(SUM(NVL(M110_BUDGETALLOTAMT, 0)),0)/1000 M110_BUDGETALLOTAMT   \n");
    sb.append("   FROM M110_MONEYALLOTCONDITION_T                             \n");
    sb.append("  WHERE M110_YEAR = ?                                          \n");
    sb.append("    AND M110_DATE = ?                                          \n");
    sb.append("    AND M110_ALLOTGUBUN = ?                                    \n");
    sb.append("    AND M110_PARTCODE = '00000'                                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("report_gubun"));

    return template.getData(conn, parameters);
  }


  /* ��ȸ���� �ݾ��� �ѱ۷� */
  public static CommonEntity getHangulAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT NUM_TO_HANGUL(NVL(?, 0)) HANGUL_AMT   \n");
    sb.append("  FROM DUAL                                  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("ALLOTAMT")+"000");

    return template.getData(conn, parameters);
  }



  /* �ڱ�(��)����������Ȳ(���ݺ����ܾ�) �������� �հ踮��Ʈ  */
  public static CommonEntity getGwanBoyuJan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append(" SELECT '10' M360_ACCCODE                                                   \n");
    sb.append("       ,'��1�����հ�' M360_ACCNAME                                          \n");
    sb.append("       ,TRUNC(SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0)/1000)) M170_OFFICIALDEPOSITJANAMT   \n");
    sb.append("   FROM M170_JANECKJANG_T A                                                 \n");
    sb.append("       ,M360_ACCCODE_T B                                                    \n");
    sb.append("  WHERE A.M170_ACCCODE(+) = B.M360_ACCCODE                                  \n");
    sb.append("    AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                 \n");
    sb.append("    AND B.M360_ACCGUBUN = 'A'                                               \n");
    sb.append("    AND A.M170_YEAR(+)  = B.M360_YEAR                                       \n");
	sb.append("    AND A.M170_YEAR(+)  = ?                                       \n");
    sb.append("    AND A.M170_DATE(+) = GET_AGO_BUSINESSDAY(?)                             \n");
    if ("1".equals(paramInfo.getString("report_gubun")) ) {
      sb.append("    AND B.M360_ACCCODE BETWEEN '22' AND '59'                              \n");
    } else if ("2".equals(paramInfo.getString("report_gubun")) ) {
      sb.append("    AND B.M360_ACCCODE BETWEEN '62' AND '89'                              \n");
    }

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getData(conn, parameters);
  }


  /* �ڱ�(��)����������Ȳ(���ݺ����ܾ�) -1���� �հ� */
  public static List<CommonEntity> getBoyuJan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append(" SELECT M360_ACCCODE                                                        \n");
    sb.append("       ,CASE WHEN M360_ACCCODE = '00'                                       \n");
    sb.append("             THEN '�� ��'                                                   \n");
    sb.append("             WHEN M360_ACCCODE = '21'                                       \n");
    sb.append("             THEN '�� û'                                                   \n");
    sb.append("             ELSE M360_ACCNAME                                              \n");
    sb.append("         END M360_ACCNAME                                                   \n");
    sb.append("       ,M170_OFFICIALDEPOSITJANAMT                                          \n");
    sb.append("   FROM (                                                                   \n");
    sb.append(" SELECT NVL(M360_ACCCODE, '00') M360_ACCCODE                                \n");
    sb.append("       ,REPLACE(MAX(M360_ACCNAME), '(��)', '') M360_ACCNAME                 \n");
    sb.append("       ,TRUNC(SUM(NVL(M170_OFFICIALDEPOSITJANAMT,0)/1000)) M170_OFFICIALDEPOSITJANAMT   \n");
    sb.append("   FROM M170_JANECKJANG_T A                                                 \n");
    sb.append("       ,M360_ACCCODE_T B                                                    \n");
    sb.append("  WHERE A.M170_ACCCODE(+) = B.M360_ACCCODE                                  \n");
    sb.append("    AND A.M170_ACCTYPE(+) = B.M360_ACCGUBUN                                 \n");
    sb.append("    AND B.M360_ACCGUBUN = 'A'                                               \n");
    sb.append("    AND A.M170_YEAR(+)  = B.M360_YEAR                                       \n");
	sb.append("    AND A.M170_YEAR(+)  = ?                                       \n");
    sb.append("    AND A.M170_DATE(+) = GET_AGO_BUSINESSDAY(?)                             \n");
    if ("1".equals(paramInfo.getString("report_gubun")) ) {
      sb.append("    AND B.M360_ACCCODE BETWEEN '21' AND '59'                              \n");
    } else if ("2".equals(paramInfo.getString("report_gubun")) ) {
      sb.append("    AND B.M360_ACCCODE BETWEEN '62' AND '89'                              \n");
    } 
    sb.append("  GROUP BY ROLLUP(M360_ACCCODE)                                             \n");
    sb.append("  ORDER BY M360_ACCCODE                                                     \n");
    sb.append("  )                                                                         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

		int idx = 1;
		
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getList(conn, parameters);
  }
}
