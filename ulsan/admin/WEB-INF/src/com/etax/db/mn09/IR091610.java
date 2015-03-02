/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091610.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > �μ��ڵ����
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091610 {

	/* �μ��ڵ� ��ȸ */
	public static List getuseSemokList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT M370_SEMOKCODE                                          \n");
    sb.append("      , M370_SEMOKNAME                                          \n");
    sb.append("   FROM M390_USESEMOKCODE_T A                                   \n");
    sb.append("      , M370_SEMOKCODE_T B                                      \n");
    sb.append("  WHERE M390_YEAR = M370_YEAR                                   \n");
    sb.append("    AND M390_ACCGUBUN = M370_ACCGUBUN                           \n");
    sb.append("    AND M390_ACCCODE = M370_ACCCODE                             \n");
    sb.append("    AND M390_WORKGUBUN = M370_WORKGUBUN                         \n");
    sb.append("    AND M390_SEMOKCODE = M370_SEMOKCODE                         \n");
    sb.append("    AND M390_ACCGUBUN = ?                                       \n");
    sb.append("    AND M390_ACCCODE  = DECODE(?,'31','11','31')                \n");
    sb.append("    AND M390_WORKGUBUN = ?                                      \n");
    sb.append("    AND M390_YEAR = NVL(?,TO_CHAR(SYSDATE,'YYYY'))              \n");
    sb.append("    AND CASE                                                    \n");
    sb.append("        WHEN ? = 'A' AND                                        \n");
    sb.append("              SUBSTR(M390_SEMOKCODE,1,1) > '1' AND              \n");
    sb.append("              SUBSTR(M390_SEMOKCODE,1,1) < '9' THEN '1'         \n");
    sb.append("        WHEN ? = 'B' AND                                        \n");
    sb.append("              SUBSTR(M390_SEMOKCODE,1,1) < '9' THEN '1'         \n");
    sb.append("        ELSE '0' END = '1'                                      \n");
    sb.append("  GROUP BY M370_SEMOKCODE                                       \n");
    sb.append("      , M370_SEMOKNAME                                          \n");
    sb.append("  ORDER BY M370_SEMOKCODE                                       \n");
    sb.append("      , M370_SEMOKNAME                                          \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("accgbn"));
		parameters.setString(i++,  paramInfo.getString("questdAcccode"));
		parameters.setString(i++,  paramInfo.getString("workgubun"));
		parameters.setString(i++,  paramInfo.getString("queyear"));
		parameters.setString(i++,  paramInfo.getString("accgbn"));
		parameters.setString(i++,  paramInfo.getString("accgbn"));
  
		return template.getList(conn, parameters);
	}

	/* �μ��ڵ� ��ȸ */
	public static List getnowIncomeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M550_SEMOKCODE                              \n");
   sb.append("      , M550_SEMOKNAME                              \n");
   sb.append("   FROM M550_INCOMESEMOK_T                          \n");
   sb.append("  WHERE M550_YEAR = NVL(?,TO_CHAR(SYSDATE,'YYYY'))  \n");
   sb.append("    AND M550_SEMOKCODE BETWEEN '200000000'          \n");
   sb.append("                           AND '699999999'          \n");
   sb.append("    AND M550_MOKGUBUN = '3'                         \n");
   sb.append("    AND M550_SEMOKNAME NOT LIKE '�����⵵%'         \n");
   sb.append(" ORDER BY M550_SEMOKCODE                            \n");
   sb.append("      , M550_SEMOKNAME                              \n");

    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
  
		return template.getList(conn, parameters);
	}
	/* �μ��ڵ� ��ȸ */
	public static List getoldIncomeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M550_SEMOKCODE                              \n");
   sb.append("      , M550_SEMOKNAME                              \n");
   sb.append("   FROM M550_INCOMESEMOK_T                          \n");
   sb.append("  WHERE M550_YEAR = NVL(?,TO_CHAR(SYSDATE,'YYYY'))  \n");
   sb.append("    AND M550_SEMOKCODE BETWEEN '200000000'          \n");
   sb.append("                           AND '699999999'          \n");
   sb.append("    AND M550_MOKGUBUN = '5'                         \n");
   sb.append("    AND M550_SEMOKNAME NOT LIKE '�����⵵%'         \n");
   sb.append(" ORDER BY M550_SEMOKCODE                            \n");
   sb.append("      , M550_SEMOKNAME                              \n");

    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
  
		return template.getList(conn, parameters);
	}
	/* �μ��ڵ� ��ȸ */
	public static List getstandardsemokkList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   sb.append(" SELECT M420_YEAR                                                            \n");
   sb.append("      , M420_STANDARDACCCODE                                                 \n");
   sb.append("      , M420_STANDARDSEMOKCODE                                               \n");
   sb.append("      , M420_SEMOKNAME                                                       \n");
   sb.append("      , DECODE(M420_ACCGUBUN,'A','�Ϲ�ȸ��','Ư��ȸ��') AS  M420_ACCGUBUN    \n");
   sb.append("      , DECODE(M420_SYSTEMACCCODE,'11','�Ϲ�','Ư��') AS  M420_SYSTEMACCCODE \n");
   sb.append("      , '����' AS M420_WORKGUBUN                                             \n");
   sb.append("      , M370_SEMOKNAME AS M420_SYSTEMSEMOKCODE                               \n");
   sb.append("      , C.M550_SEMOKNAME AS M420_BUDGETSEMOKCODE                             \n");
   sb.append("      , D.M550_SEMOKNAME AS M420_BUDGETSEMOKBYEAR                            \n");
   sb.append("   FROM M420_STANDARDSEMOKCODE_T  A                                          \n");
   sb.append("      , M370_SEMOKCODE_T  B                                                  \n");
   sb.append("      , M550_INCOMESEMOK_T  C                                                \n");
   sb.append("      , M550_INCOMESEMOK_T  D                                                \n");
   sb.append("  WHERE M420_YEAR = ?                                                        \n");
   sb.append("    AND M420_STANDARDACCCODE = ?                                             \n");
   sb.append("    AND M420_ACCGUBUN = ?                                                    \n");
   sb.append("    AND M420_WORKGUBUN = ?                                                   \n");
   sb.append("    AND M420_YEAR             = M370_YEAR(+)                                 \n");
   sb.append("    AND M420_ACCGUBUN         = M370_ACCGUBUN(+)                             \n");
   sb.append("    AND M420_STANDARDACCCODE  = DECODE(M370_ACCCODE(+),'11','31','31','51','99')   \n");
   sb.append("    AND M420_WORKGUBUN        = M370_WORKGUBUN(+)                            \n");
   sb.append("    AND M420_SYSTEMSEMOKCODE  = M370_SEMOKCODE(+)                            \n");
   sb.append("    AND M420_YEAR             = C.M550_YEAR(+)                               \n");
   sb.append("    AND M420_BUDGETSEMOKCODE  = C.M550_SEMOKCODE(+)                          \n");
   sb.append("    AND M420_YEAR             = D.M550_YEAR(+)                               \n");
   sb.append("    AND M420_BUDGETSEMOKCODE  = D.M550_SEMOKCODE(+)                          \n");
   sb.append(" ORDER BY M420_YEAR                                                          \n");
   sb.append("      , M420_STANDARDACCCODE                                                 \n");
   sb.append("      , M420_STANDARDSEMOKCODE                                               \n");

    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
		parameters.setString(i++,  paramInfo.getString("questdAcccode"));
		parameters.setString(i++,  paramInfo.getString("accgbn"));
		parameters.setString(i++,  paramInfo.getString("workgubun"));
  
		return template.getList(conn, parameters);
	}
	 /* �μ� �ڵ� ��� */
	 public static int insertStdsemokCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M420_STANDARDSEMOKCODE_T   \n");
		sb.append("           ( M420_YEAR,                 \n"); 
		sb.append("	            M420_STANDARDACCCODE,      \n"); 
		sb.append("	            M420_STANDARDSEMOKCODE,    \n"); 
		sb.append("	            M420_SEMOKNAME,            \n"); 
		sb.append("	            M420_ACCGUBUN,             \n"); 
		sb.append("	            M420_SYSTEMACCCODE,        \n"); 
		sb.append("	            M420_WORKGUBUN,            \n");
		sb.append("	            M420_SYSTEMSEMOKCODE,      \n");
		sb.append("	            M420_BUDGETSEMOKCODE,      \n");
		sb.append("	            M420_BUDGETSEMOKBYEAR)     \n");
		sb.append("     VALUES(                            \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("								?           )            \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("stdAcccode"));
    parameters.setString(i++, paramInfo.getString("stdSemok"));
		parameters.setString(i++, paramInfo.getString("stdSemokName"));
		parameters.setString(i++, paramInfo.getString("accgbn"));
    parameters.setString(i++, paramInfo.getString("stdAcccode"));
		parameters.setString(i++, paramInfo.getString("workgubun"));
    parameters.setString(i++, paramInfo.getString("sysSemokcode"));
    parameters.setString(i++, paramInfo.getString("nowincomeSemok"));
    parameters.setString(i++, paramInfo.getString("oldincomeSemok"));

		return template.insert(conn, parameters); 
	}
	 /* �μ� �ڵ� ��� */
	 public static int insertStdsemokCpy(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

   sb.append("  INSERT INTO M420_STANDARDSEMOKCODE_T           \n");
   sb.append("            ( M420_YEAR,                         \n");
   sb.append(" 	            M420_STANDARDACCCODE,              \n");
   sb.append(" 	            M420_STANDARDSEMOKCODE,            \n");
   sb.append(" 	            M420_SEMOKNAME,                    \n");
   sb.append(" 	            M420_ACCGUBUN,                     \n");
   sb.append(" 	            M420_SYSTEMACCCODE,                \n");
   sb.append(" 	            M420_WORKGUBUN,                    \n");
   sb.append(" 	            M420_SYSTEMSEMOKCODE,              \n");
   sb.append(" 	            M420_BUDGETSEMOKCODE,              \n");
   sb.append(" 	            M420_BUDGETSEMOKBYEAR)             \n");
   sb.append("  SELECT ?                                       \n");
   sb.append("       , M420_STANDARDACCCODE                    \n");
   sb.append("       , M420_STANDARDSEMOKCODE                  \n");
   sb.append("       , M420_SEMOKNAME                          \n");
   sb.append("       , M420_ACCGUBUN                           \n");
   sb.append("       , M420_SYSTEMACCCODE                      \n");
   sb.append("       , M420_WORKGUBUN                          \n");
   sb.append("       , M420_SYSTEMSEMOKCODE                    \n");
   sb.append("       , M420_BUDGETSEMOKCODE                    \n");
   sb.append("       , M420_BUDGETSEMOKBYEAR                   \n");
   sb.append("    FROM M420_STANDARDSEMOKCODE_T                \n");
   sb.append("   WHERE M420_YEAR = ?                           \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("moveyear"));
		parameters.setString(i++, paramInfo.getString("queyear"));

		return template.insert(conn, parameters); 
	}
		/* �μ� �ڵ� ���� */ 
  public static int deletestandardsemokk(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM m420_standardsemokcode_t    \n");
    sb.append("  WHERE  M420_YEAR = ?                   \n");
    sb.append("    AND  M420_STANDARDACCCODE = ?        \n");
    sb.append("    AND  M420_STANDARDSEMOKCODE = ?      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("delyear"));
		parameters.setString(idx++, paramInfo.getString("delacccode"));
		parameters.setString(idx++, paramInfo.getString("delsemokcode"));

    return template.delete(conn, parameters);
  }
}