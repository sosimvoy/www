/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091810.java
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-19
* ���α׷�����	   : �ý��ۿ > ������µ��
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091810 {

	/* �μ��ڵ� ��ȸ */
	public static List getusePartList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append(" SELECT M350_YEAR                                        \n");
    sb.append("       ,  M350_PARTCODE                                  \n");
    sb.append("        , M350_PARTNAME                                  \n");
    sb.append("   FROM M350_PARTCODE_T A                                \n");
    sb.append("        , M390_USESEMOKCODE_T B                          \n");
    sb.append(" WHERE A.M350_YEAR      = B.M390_YEAR                    \n");
    sb.append("    AND A.M350_PARTCODE = B.M390_PARTCODE                \n");
    sb.append("    AND A.M350_YEAR     = ?                              \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                              \n");
    sb.append(" GROUP BY M350_YEAR                                      \n");
    sb.append("       ,  M350_PARTCODE                                  \n");
    sb.append("        , M350_PARTNAME                                  \n");
    sb.append(" ORDER BY M350_PARTCODE                                  \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
		parameters.setString(i++,  paramInfo.getString("accgbn"));
  
		return template.getList(conn, parameters);
	}

	/* ȸ���ڵ� ��ȸ */
	public static List getuseAcccodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append("   select DISTINCT m360_year                \n");
    sb.append("        , m360_acccode                      \n");
    sb.append("        , m360_accname                      \n");
    sb.append("        , m360_accgubun                     \n");
    sb.append("   from M360_ACCCODE_T A                    \n");
    sb.append("        , M390_USESEMOKCODE_T B             \n");
    sb.append(" where m360_year     = ?                    \n");
    sb.append("   and m360_accgubun = ?                    \n");
    sb.append("   AND M360_YEAR = M390_YEAR                \n");
    sb.append("   AND M360_ACCGUBUN = M390_ACCGUBUN        \n");
    sb.append("   AND M360_ACCCODE = M390_ACCCODE          \n");
    sb.append("   AND M390_PARTCODE = NVL(?,'00000')       \n");
    sb.append("   AND M390_WORKGUBUN IN ('0', '1')         \n");
    sb.append(" order by m360_acccode                      \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
		parameters.setString(i++,  paramInfo.getString("accgbn"));
		parameters.setString(i++,  paramInfo.getString("sysPartcode"));
  
		return template.getList(conn, parameters);
	}
	/* ������� ��ȸ */
	public static List gettefAccountList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
    sb.append(" select fis_year                                                \n");
    sb.append("       , account_no                                             \n");
    sb.append("       , accgubun                                               \n");
    sb.append("       , decode(accgubun,'A','�Ϲ�ȸ��',                        \n");
    sb.append("                                   'B','Ư��ȸ��',              \n");
    sb.append("                                   'D','���Լ��������',        \n");
    sb.append("                                   'E','���') as accgbn_nm     \n");
    sb.append("       , part_code                                              \n");
    sb.append("       ,(select M350_PARTNAME                                   \n");
    sb.append("           from M350_PARTCODE_T                                 \n");
    sb.append("          where m350_year =  fis_year                           \n");
    sb.append("             and M350_PARTCODE = part_code                      \n");
    sb.append("             and rownum = 1 ) as part_name                      \n");
    sb.append("       , acc_code                                               \n");
    sb.append("       ,(select m360_accname                                    \n");
    sb.append("           from M360_ACCCODE_T                                  \n");
    sb.append("          where m360_year =  fis_year                           \n");
    sb.append("             and m360_accgubun = accgubun                       \n");
    sb.append("             and m360_acccode = acc_code                        \n");
    sb.append("             and rownum = 1 ) as acc_name                       \n");
    sb.append("   from tef_account                                             \n");
    sb.append("  where fis_year = ?                                            \n");
    if (!"".equals(paramInfo.getString("accgbn"))){
		   sb.append("    and accgubun = ?                                            \n");
		}
    
    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("queyear"));
    if (!"".equals(paramInfo.getString("accgbn"))){
		   parameters.setString(i++,  paramInfo.getString("accgbn"));
		}
  
		return template.getList(conn, parameters);
	}

	public static CommonEntity getcheckYn(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) AS insertCnt        \n");
		sb.append("  FROM tef_account                  \n");
		sb.append(" WHERE fis_year = ?                 \n");
		sb.append("   AND account_no = ?               \n"); 
    
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		parameters.setString(1, paramInfo.getString("fis_year"));
		parameters.setString(2, paramInfo.getString("account_no"));
	
		return template.getData(conn, parameters);
	}
	 /* ��ݰ��� ��� */
	 public static int inserttefAccount(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO tef_account                \n");
		sb.append("           ( fis_year,                  \n"); 
		sb.append("	            account_no,                \n"); 
		sb.append("	            accgubun,                  \n"); 
		sb.append("	            part_code,                 \n"); 
		sb.append("	            acc_code)                  \n");
		sb.append("     VALUES(                            \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("               ?,                       \n");
		sb.append("								?           )            \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("fis_year"));
		parameters.setString(i++, paramInfo.getString("account_no"));
    parameters.setString(i++, paramInfo.getString("accgubun"));
		parameters.setString(i++, paramInfo.getString("part_code"));
		parameters.setString(i++, paramInfo.getString("acc_code"));

		return template.insert(conn, parameters); 
	}
		/* �μ� �ڵ� ���� */ 
  public static int deleteaccountno(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM tef_account                \n");
    sb.append("  WHERE  fis_year = ?                   \n");
    sb.append("    AND  account_no = ?                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("delyear"));
		parameters.setString(idx++, paramInfo.getString("delacccode"));

    return template.delete(conn, parameters);
  }
}