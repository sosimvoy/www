/***********************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR071410.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-08-16
* ���α׷�����	   : �ϰ�/���� > ���Լ����ϰ�����(�ұ�ó��)
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR071410 {
  
	/* ���������ȸ */ 
	public static List<CommonEntity> getRevCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT M430_SUNAPGIGWANCODE AS REV_CD  \n");
		sb.append("      , M430_SUNAPGIGWANNAME AS REV_NM  \n");	
    sb.append("   FROM M430_SUNAPGIGWANCODE_T 	       \n");   
    if ("T2".equals(paramInfo.getString("tax_type")) ) {
      sb.append(" WHERE M430_SUNAPGIGWANCODE = ?       \n");
    }	
    sb.append("  ORDER BY M430_SUNAPGIGWANCODE 	       \n");   
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
    if ("T2".equals(paramInfo.getString("tax_type")) ) {
		  parameters.setString(idx++, "110000");
    }
        
    return template.getList(conn, parameters);
  }


  /* �μ� ��ȸ */ 
	public static List<CommonEntity> getPartCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M350_PARTCODE AS PART_CD           \n");
    sb.append("      , A.M350_PARTNAME AS PART_NM           \n");
    sb.append("   FROM M350_PARTCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B                \n");
    sb.append("  WHERE A.M350_YEAR = B.M390_YEAR            \n");
    sb.append("    AND A.M350_PARTCODE =  B.M390_PARTCODE   \n");
    sb.append("    AND A.M350_YEAR = ?                      \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                  \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                 \n");
    sb.append("  GROUP BY A.M350_PARTCODE, A.M350_PARTNAME  \n");
    sb.append("  ORDER BY A.M350_PARTCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
        
    return template.getList(conn, parameters);
  }


  /* ȸ�� ��ȸ */ 
	public static List<CommonEntity> getAccCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M360_ACCCODE AS ACC_CD            \n");
    sb.append("      , A.M360_ACCNAME AS ACC_NM            \n");
    sb.append("   FROM M360_ACCCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B               \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR           \n");
    sb.append("    AND A.M360_ACCCODE =  B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");
    sb.append("    AND A.M360_YEAR = ?                     \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                 \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                 \n");
    sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME   \n");
    sb.append("  ORDER BY A.M360_ACCCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getList(conn, parameters);
  }


  /* �μ��� ���� ȸ�� �� ��� */ 
	public static CommonEntity getAccCd(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M360_ACCCODE AS ACC_CD            \n");
    sb.append("      , A.M360_ACCNAME AS ACC_NM            \n");
    sb.append("   FROM M360_ACCCODE_T A                    \n");
    sb.append("      , M390_USESEMOKCODE_T B               \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR           \n");
    sb.append("    AND A.M360_ACCCODE =  B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN   \n");
    sb.append("    AND A.M360_YEAR = ?                     \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                 \n");
    sb.append("    AND B.M390_WORKGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                 \n");
    sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME   \n");
    sb.append("  ORDER BY A.M360_ACCCODE                   \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
        
    return template.getData(conn, parameters);
  }



  /* ���� ��ȸ */ 
	public static List<CommonEntity> getSemokCdList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT A.M370_SEMOKCODE AS SEMOK_CD           \n");
    sb.append("      , A.M370_SEMOKNAME AS SEMOK_NM           \n");
    sb.append("   FROM M370_SEMOKCODE_T A                     \n");
    sb.append("      , M390_USESEMOKCODE_T B                  \n");
    sb.append("  WHERE A.M370_YEAR = B.M390_YEAR              \n");
    sb.append("    AND A.M370_ACCCODE =  B.M390_ACCCODE       \n");
    sb.append("    AND A.M370_ACCGUBUN = B.M390_ACCGUBUN      \n");
    sb.append("    AND A.M370_SEMOKCODE =  B.M390_SEMOKCODE   \n");
    sb.append("    AND A.M370_YEAR = ?                        \n");
    sb.append("    AND B.M390_ACCGUBUN = ?                    \n");
    sb.append("    AND A.M370_WORKGUBUN = ?                   \n");
    sb.append("    AND B.M390_PARTCODE = ?                    \n");
    sb.append("    AND B.M390_ACCCODE = ?                     \n");
    sb.append("  GROUP BY A.M370_SEMOKCODE, A.M370_SEMOKNAME  \n");
    sb.append("  ORDER BY A.M370_SEMOKCODE                    \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("work_gubun"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
        
    return template.getList(conn, parameters);
  }

  
  /* �ڷ� ���� ����(ȸ������) */ 
	public static CommonEntity getRecord(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT *                                    \n");
    sb.append("   FROM M220_DAY_T                           \n");
    sb.append("  WHERE M220_YEAR = ?                        \n");
    sb.append("    AND M220_DATE = ?                        \n");
    sb.append("    AND M220_ACCTYPE = ?                     \n");
    sb.append("    AND M220_ACCCODE = ?                     \n");
    sb.append("    AND M220_SEMOKCODE = ?                   \n");
    sb.append("    AND M220_PARTCODE = ?                    \n");
    sb.append("    AND M220_SUNAPGIGWANCODE = ?             \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("semok_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("rev_cd"));
        
    return template.getData(conn, parameters);
  }

  /* ȭ���Է����ں��� ū ����MAX����(ȸ������)���ϱ� */ 
	public static CommonEntity getMaxRecord(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT MAX(M210_DATE) AS M210_MAXDATE       \n");
    sb.append("   FROM M210_WORKEND_T                       \n");
    sb.append("  WHERE M210_YEAR = ?                        \n");
    sb.append("    AND M210_DATE > ?                        \n");
    sb.append("    AND M210_WORKENDSTATE = 'Y'              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));
        
    return template.getData(conn, parameters);
  }

	/* ���Ϻ�� ���  */
  public static int dateNoteInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M220_DAY_T            \n");
		sb.append("       ( M220_YEAR   					    \n");	 
		sb.append("        ,M220_DATE 					      \n");	 
		sb.append("        ,M220_ACCTYPE 				      \n");	 
		sb.append("        ,M220_ACCCODE 			        \n");	 
		sb.append("        ,M220_SEMOKCODE 			      \n");	 
		sb.append("        ,M220_PARTCODE 			      \n");	 
		sb.append("        ,M220_SUNAPGIGWANCODE      \n");

		sb.append("        ,M220_AMTSEIP              \n"); //���Ծ�
		sb.append("        ,M220_AMTSEIPGWAONAP       \n"); //������
		sb.append("        ,M220_AMTSEIPJEONGJEONG    \n"); //����������
		sb.append("        ,M220_AMTPASTSEIP          \n"); //���⵵���Ծ�
		sb.append("        ,M220_AMTSECHUL            \n"); //�����
		sb.append("        ,M220_AMTSECHULBANNAP      \n"); //����ݳ���
		sb.append("        ,M220_AMTSECHULJEONGJEONG  \n"); //����������

		sb.append("        ,M220_AMTSEIPJEONILTOT     \n"); //�������ϴ���
		sb.append("        ,M220_AMTGWAONAPJEONILTOT  \n"); //���������ϴ���
		sb.append("        ,M220_AMTPASTSEIPJEONILTOT \n"); //���⵵�������ϴ���
		sb.append("        ,M220_AMTSECHULJEONILTOT   \n"); //�������ϴ���
		sb.append("        ,M220_AMTBAJEONGJEONILTOT  \n"); //�������ϴ���
		sb.append("        ,M220_AMTBAJEONG           \n"); //������
		sb.append("        ,M220_AMTBAJEONGSUJEONILTOT \n"); //�����������ϴ���
		sb.append("        ,M220_AMTBAJEONGSURYUNG     \n"); //�������ɾ�
		sb.append("        ,M220_AMTSURPLUSJEONILTOT   \n"); //�׿����������ϴ���
		sb.append("        ,M220_AMTSURPLUS            \n"); //�׿������Ծ�
		sb.append("        ,M220_AMTJEONGGI            \n"); //���⿹�ݵ�
		sb.append("        ,M220_AMTGONGGEUM           \n"); //���ݿ���
		sb.append("        ,M220_AMTLOAN )			      \n");	//���ڱ�
		sb.append(" VALUES( ?		                      \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");	
		sb.append("        ,?					                \n");
		sb.append("        ,?					                \n");

		sb.append(" ,CASE					                                                             \n");  //���Ծ�
		sb.append("  WHEN ? = 'T1' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //������
		sb.append("  WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //����������
		sb.append("  WHEN ? = 'T1' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //���⵵���Ծ�
		sb.append("  WHEN ? = 'T1' AND ? = 'Y2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //�����
		sb.append("  WHEN ? = 'T2' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //����ݳ���
		sb.append("  WHEN ? = 'T2' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");
		sb.append(" ,CASE					                                                             \n");  //����������
		sb.append("  WHEN ? = 'T2' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )  \n");
		sb.append("  ELSE '0' END                       \n");

		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0					                \n");
		sb.append("        ,0	)				                \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

    //���Ծ�
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���⵵���Ծ�
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //�����
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����ݳ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
	
		return template.insert(conn, parameters);
	}

	/* ���Ϻ�� ���� */
	public static int dateNoteInsertProc(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M220_DAY_T            \n");
		sb.append("       ( M220_YEAR   					    \n");	 
		sb.append("        ,M220_DATE 					      \n");	 
		sb.append("        ,M220_ACCTYPE 				      \n");	 
		sb.append("        ,M220_ACCCODE 			        \n");	 
		sb.append("        ,M220_SEMOKCODE 			      \n");	 
		sb.append("        ,M220_PARTCODE 			      \n");	 
		sb.append("        ,M220_SUNAPGIGWANCODE      \n");

		sb.append("        ,M220_AMTSEIPJEONILTOT     \n"); //�������ϴ���
		sb.append("        ,M220_AMTGWAONAPJEONILTOT  \n"); //���������ϴ���
		sb.append("        ,M220_AMTPASTSEIPJEONILTOT \n"); //���⵵�������ϴ���
		sb.append("        ,M220_AMTSECHULJEONILTOT   \n"); //�������ϴ���

		sb.append("        ,M220_AMTSEIP              \n"); //���Ծ�
		sb.append("        ,M220_AMTSEIPGWAONAP       \n"); //������
		sb.append("        ,M220_AMTSEIPJEONGJEONG    \n"); //����������
		sb.append("        ,M220_AMTPASTSEIP          \n"); //���⵵���Ծ�
		sb.append("        ,M220_AMTSECHUL            \n"); //�����
		sb.append("        ,M220_AMTSECHULBANNAP      \n"); //����ݳ���
		sb.append("        ,M220_AMTSECHULJEONGJEONG  \n"); //����������
		sb.append("        ,M220_AMTBAJEONGJEONILTOT  \n"); //�������ϴ���
		sb.append("        ,M220_AMTBAJEONG           \n"); //������
		sb.append("        ,M220_AMTBAJEONGSUJEONILTOT \n"); //�����������ϴ���
		sb.append("        ,M220_AMTBAJEONGSURYUNG     \n"); //�������ɾ�
		sb.append("        ,M220_AMTSURPLUSJEONILTOT   \n"); //�׿����������ϴ���
		sb.append("        ,M220_AMTSURPLUS            \n"); //�׿������Ծ�
		sb.append("        ,M220_AMTJEONGGI            \n"); //���⿹�ݵ�
		sb.append("        ,M220_AMTGONGGEUM           \n"); //���ݿ���
		sb.append("        ,M220_AMTLOAN )			      \n");	//���ڱ�             
    sb.append("  SELECT M220_YEAR                                                                      \n");
    sb.append("       , FDATE                                                                          \n");
    sb.append("       , M220_ACCTYPE                                                                   \n");
    sb.append("       , M220_ACCCODE                                                                   \n");
    sb.append("       , M220_SEMOKCODE                                                                 \n");
    sb.append("       , M220_PARTCODE                                                                  \n");
    sb.append("       , M220_SUNAPGIGWANCODE                                                           \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I3' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END -- �������ϴ���                                                   \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'I2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END  -- ���������ϴ���                                                \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T1' AND ? = 'Y2' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END  -- ���⵵�������ϴ���                                            \n");
    sb.append("       , CASE                                                                           \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I1' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I2' THEN DECODE( ? , 'P2', ? , 'P1', ? * -1 , '0' )       \n");
    sb.append("         WHEN ? = 'T2' AND ? = 'I3' THEN DECODE( ? , 'P1', ? , 'P2', ? * -1 , '0' )       \n");
    sb.append("         ELSE '0' END -- �������ϴ���                                                   \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("       , 0                                                                              \n");
    sb.append("    FROM M220_DAY_T                                                                     \n");
    sb.append("       , ( SELECT M210_DATE AS FDATE                                                    \n");
    sb.append("             FROM M210_WORKEND_T                                                        \n");
    sb.append("            WHERE M210_YEAR = ?                                                         \n");
    sb.append("              AND M210_WORKENDSTATE = 'Y'                                               \n");
    sb.append("              AND M210_DATE > ?  ) B                                                    \n");
    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE = ?                                                                  \n");
    sb.append("     AND CASE                                                                           \n");
    sb.append("         WHEN M220_ACCTYPE = 'E' AND FDATE <= M220_YEAR||'1231' THEN '1'               \n");
    sb.append("         WHEN M220_ACCTYPE != 'E' THEN '1'                                              \n");
    sb.append("         ELSE '0' END = '1'                                                             \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���⵵�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}

	/* ���Ϻ�� ���� */
	public static int dateNoteUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M220_DAY_T                                                        \n");
    sb.append("   SET M220_AMTSEIP = M220_AMTSEIP + TO_NUMBER(                   \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I1'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ���Ծ�                                   \n");
    sb.append("     , M220_AMTSEIPGWAONAP = M220_AMTSEIPGWAONAP + TO_NUMBER(          \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ������                                   \n");
    sb.append("     , M220_AMTSEIPJEONGJEONG = M220_AMTSEIPJEONGJEONG + TO_NUMBER(       \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'I3'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ����������                               \n");
    sb.append("     , M220_AMTPASTSEIP = M220_AMTPASTSEIP + TO_NUMBER(              \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T1' AND ? = 'Y2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ���⵵���Ծ�                             \n");
    sb.append("     , M220_AMTSECHUL = M220_AMTSECHUL +  TO_NUMBER(               \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I1'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- �����                                   \n");
    sb.append("     , M220_AMTSECHULBANNAP = M220_AMTSECHULBANNAP +  TO_NUMBER(          \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I2'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ����ݳ���                               \n");
    sb.append("     , M220_AMTSECHULJEONGJEONG = M220_AMTSECHULJEONGJEONG + TO_NUMBER(     \n");
    sb.append("                      CASE					                                           \n");
    sb.append("                      WHEN ? = 'T2' AND ? = 'I3'                              \n");
    sb.append("                            THEN DECODE( ? , 'P1', ? ,                        \n");
    sb.append("                                             'P2', ? * -1 , '0' )               \n");
    sb.append("                      ELSE '0' END ) -- ����������                               \n");
    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE = ?                                                                  \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //���Ծ�
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���⵵���Ծ�
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //�����
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����ݳ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //����������
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));	
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}
	/* ���Ϻ�� ���� */
	public static int dateNoteUpdateProc(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M220_DAY_T                                                        \n");
    sb.append("   set M220_AMTSEIPJEONILTOT = M220_AMTSEIPJEONILTOT + TO_NUMBER(             \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I1'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I2'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I3'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTGWAONAPJEONILTOT = M220_AMTGWAONAPJEONILTOT + TO_NUMBER(    \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'I2'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTPASTSEIPJEONILTOT = M220_AMTPASTSEIPJEONILTOT + TO_NUMBER(    \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T1' AND ? = 'Y2'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               ELSE '0' END )                                     \n");
    sb.append("     , M220_AMTSECHULJEONILTOT = M220_AMTSECHULJEONILTOT +  TO_NUMBER(       \n");
    sb.append("                               CASE					                                  \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I1'                      \n");
    sb.append("                                    THEN DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' )        \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I2'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               WHEN ? = 'T2' AND ? = 'I3'                      \n");
    sb.append("                                    THEN TO_CHAR(DECODE( ? , 'P1', ? ,                 \n");
    sb.append("                                                     'P2', ? * -1 , '0' ) * -1)   \n");
    sb.append("                               ELSE '0' END   )                                   \n");

    sb.append("   WHERE 1= 1                                                                           \n");
    sb.append("     AND M220_YEAR = ?                                                                  \n");
    sb.append("     AND M220_DATE > ?                                                                  \n");
    sb.append("     AND M220_ACCTYPE = ?                                                               \n");
    sb.append("     AND M220_ACCCODE = ?                                                               \n");
    sb.append("     AND M220_SEMOKCODE = ?                                                             \n");
    sb.append("     AND M220_PARTCODE = ?                                                              \n");
    sb.append("     AND M220_SUNAPGIGWANCODE = ?                                                       \n");		
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

    //�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //���⵵�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("year_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
    //�������ϴ���
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case1
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case2
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("tax_type"));  //case3
		parameters.setString(idx++, paramInfo.getString("input_type"));
		parameters.setString(idx++, paramInfo.getString("add_type"));
		parameters.setString(idx++, paramInfo.getString("amt"));
		parameters.setString(idx++, paramInfo.getString("amt"));

		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
		parameters.setString(idx++, paramInfo.getString("semok_code"));
		parameters.setString(idx++, paramInfo.getString("part_code"));
		parameters.setString(idx++, paramInfo.getString("rev_cd"));

		return template.delete(conn, parameters);
	}


  /* �ڷ� ���� ����(ȸ������) */ 
	public static CommonEntity getDailyMax(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 		
    sb.append(" SELECT MAX(M210_DATE) M210_DATE             \n");
    sb.append("   FROM M210_WORKEND_T                       \n");
    sb.append("  WHERE M210_YEAR = ?                        \n");
    sb.append("    AND M210_WORKENDSTATE = ?                \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, "Y");
        
    return template.getData(conn, parameters);
  }
}