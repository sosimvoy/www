/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090810.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-20
* ���α׷�����	   : �ý��ۿ > �����ڵ����
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090810 {

		public static List getbudgetSemokList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
   
		sb.append("  SELECT  A.M370_YEAR,                                                                                                        \n");
   	sb.append("  DECODE (A.M370_ACCGUBUN,'A','�Ϲ�ȸ��','B','Ư��ȸ��','C','�����Ư��ȸ��','D','���Լ��������','E','���') ACCGUBUN,       \n");
    sb.append("          A.M370_ACCGUBUN,                                                                                                    \n");
    sb.append("          A.M370_ACCCODE,                                                                                                     \n");
	  sb.append("  DECODE (A.M370_WORKGUBUN,'0','����','1','����','2','�ڱݹ���','3','�ڱݿ��','4','���Լ��������') WORKGUBUN,               \n");
		sb.append("          A.M370_WORKGUBUN,                                                                                                   \n");
    sb.append("          A.M370_SEMOKCODE,                                                                                                   \n");
   	sb.append("          A.M370_SEMOKNAME,                                                                                                   \n");
	  sb.append("  DECODE (A.M370_SEGUMGUBUN,'1','���漼','2','����') M370_SEGUMGUBUN,                                                         \n");
	  sb.append("  DECODE (A.M370_MOKGUBUN,'0','��','1','��','2','��','3','��') MOKGUBUN,                                                      \n");
    sb.append("          A.M370_MOKGUBUN,                                                                                                    \n");
 	  sb.append("          B.M360_ACCNAME                                                                                                      \n");
 	  sb.append("    FROM  M370_SEMOKCODE_T A                                                                                                  \n");
 	  sb.append("         ,M360_ACCCODE_T B                                                                                                    \n");                                                   
	  sb.append("   WHERE  A.M370_YEAR = B.M360_YEAR                                                                                           \n");
	  sb.append("     AND  A.M370_ACCCODE = B.M360_ACCCODE                                                                                     \n");
  	sb.append("     AND  A.M370_ACCGUBUN = B.M360_ACCGUBUN                                                                                   \n");
   	sb.append("     AND  M370_YEAR = ?                                                                                                    \n");                                                
  	sb.append("     AND  M370_ACCGUBUN = ?                                                                                                 \n");                                            
  	sb.append("     AND  M370_ACCCODE = ?                                                                                                   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("under_year"));
		parameters.setString(idx++, paramInfo.getString("accGubun1"));
		parameters.setString(idx++, paramInfo.getString("accCode"));

		return template.getList(conn, parameters);
	}
	/*�����ڵ� ��� */
	 public static int insertSemokCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M370_SEMOKCODE_T      \n");
		sb.append("           ( M370_YEAR,            \n"); 
		sb.append("	            M370_ACCGUBUN,        \n"); 
		sb.append("	            M370_ACCCODE,         \n");
		sb.append("             M370_SEMOKCODE,       \n");
		sb.append("	            M370_WORKGUBUN,       \n");
		sb.append("             M370_SEMOKNAME,       \n");
		sb.append("	            M370_SEGUMGUBUN,      \n"); 
    sb.append("	            M370_MOKGUBUN         \n"); 
		sb.append("                          )        \n");
		sb.append("     VALUES(                       \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("               ?,                  \n");
		sb.append("								?           )       \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("accGubun"));
    parameters.setString(i++, paramInfo.getString("acc_code"));
		parameters.setString(i++, paramInfo.getString("semokCode"));
		parameters.setString(i++, paramInfo.getString("workGubun"));
    parameters.setString(i++, paramInfo.getString("semokName"));
		parameters.setString(i++, paramInfo.getString("segumGubun"));
		parameters.setString(i++, paramInfo.getString("mokGubun"));

		return template.insert(conn, parameters); 
	}
		
		/* �����ڵ� ���� */ 
  public static int deleteSemokCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" DELETE FROM M370_SEMOKCODE_T      \n");
    sb.append("       WHERE M370_YEAR       = ?   \n");	
		sb.append("         AND M370_ACCGUBUN   = ?   \n");
		sb.append("         AND M370_ACCCODE    = ?   \n");
		sb.append("         AND M370_WORKGUBUN  = ?   \n");
		sb.append("	        AND M370_SEMOKCODE  = ?   \n");
		sb.append("         AND M370_MOKGUBUN  = ?   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
   
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
    parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	  parameters.setString(idx++, paramInfo.getString("mokGubun"));
  
		return template.delete(conn, parameters);
  }

    
    /* �����ڵ� �� */
    public static CommonEntity getSemokData(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append("  SELECT M370_YEAR,                                                            \n");
        sb.append("         M370_ACCGUBUN,                                                        \n");
   	    sb.append("         M370_ACCCODE,                                                         \n");
	    sb.append("         M370_WORKGUBUN,                                                       \n");
        sb.append("         M370_SEMOKCODE,                                                       \n");
   	    sb.append("         M370_SEMOKNAME,                                                       \n");
	    sb.append("         M370_SEGUMGUBUN,                                                      \n");
	    sb.append("         DECODE (M370_MOKGUBUN,'0','��','1','��','2','��','3','��') MOKGUBUN,  \n");
        sb.append("         M370_MOKGUBUN                                                         \n");
 	    sb.append("    FROM  M370_SEMOKCODE_T A                                                   \n");
   	    sb.append("   WHERE  M370_YEAR = ?                                                        \n");                                                
  	    sb.append("     AND  M370_ACCGUBUN = ?                                                    \n");                                            
  	    sb.append("     AND  M370_ACCCODE = ?                                                     \n");
        sb.append("     AND  M370_WORKGUBUN = ?                                                   \n");
        sb.append("     AND  M370_SEMOKCODE = ?                                                   \n");
        sb.append("     AND  M370_MOKGUBUN = ?                                                    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
        parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	    parameters.setString(idx++, paramInfo.getString("mokGubun"));

		return template.getData(conn, parameters);
	}



    /* ����� ���� */
    public static int updateSemokCode(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append("  UPDATE M370_SEMOKCODE_T       \n");
        sb.append("     SET M370_SEMOKNAME = ?,    \n");
	    sb.append("         M370_SEGUMGUBUN = ?    \n");
   	    sb.append("   WHERE M370_YEAR = ?          \n");                                                
  	    sb.append("     AND M370_ACCGUBUN = ?      \n");                                            
  	    sb.append("     AND M370_ACCCODE = ?       \n");
        sb.append("     AND M370_WORKGUBUN = ?     \n");
        sb.append("     AND M370_SEMOKCODE = ?     \n");
        sb.append("     AND M370_MOKGUBUN = ?      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
        parameters.setString(idx++, paramInfo.getString("semokName"));
		parameters.setString(idx++, paramInfo.getString("segumGubun")); 
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("accGubun"));  
        parameters.setString(idx++, paramInfo.getString("accCode"));
		parameters.setString(idx++, paramInfo.getString("workGubun"));
		parameters.setString(idx++, paramInfo.getString("semokCode"));
	    parameters.setString(idx++, paramInfo.getString("mokGubun"));

		return template.update(conn, parameters);
	}
}
