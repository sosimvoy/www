/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091010.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-16
* ���α׷�����	   : �ý��ۿ > �����ڵ���
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091210 {

	/* ȸ���� �ڵ� �� */
	public static List<CommonEntity> getEndYearCode(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("			SELECT '�μ��ڵ�' CD_NAME                   \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("			  FROM  M350_PARTCODE_T                     \n");
        sb.append("			 WHERE  M350_YEAR = ?                       \n");
		sb.append("	     UNION ALL                                      \n");
		sb.append("			SELECT 'ȸ���ڵ�' CD_NAME                   \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("			  FROM  M360_ACCCODE_T                      \n");
        sb.append("			 WHERE  M360_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '�����ڵ�' CD_NAME                   \n");  
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M370_SEMOKCODE_T                  \n");
		sb.append("			 WHERE  M370_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '��뼼���ڵ�' CD_NAME               \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M390_USESEMOKCODE_T               \n");
		sb.append("			 WHERE  M390_YEAR = ?                       \n");
	    sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT 'ȸ�迬���̿�' CD_NAME               \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M250_ACCTRANSFER_T                \n");
		sb.append("			 WHERE  M250_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '������ ���'  CD_NAME               \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M320_COLSINGDATECONTROL_T         \n");
		sb.append("			 WHERE  M320_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("     SELECT '���뿹�ݰ��� ��� ��� ���'  CD_NAME   \n");
		sb.append("	           ,COUNT(1) CNT                            \n");
	    sb.append("       FROM  M300_ACCOUNTMANAGE_T                    \n");
        sb.append("      WHERE  M300_YEAR = ?                           \n");
        sb.append("        AND  SUBSTR(M300_ACCOUNTNO, 4, 2) = '07'     \n");
        sb.append("        AND  M300_BANKCODE = '039'                   \n");
        //ǥ���ڵ� �߰�
        //�ѿ��� 2012.01.04
        sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT 'ǥ�ؼ����ڵ�'  CD_NAME              \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M420_STANDARDSEMOKCODE_T          \n");
		sb.append("			 WHERE  M420_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT 'ǥ�غμ��ڵ�'  CD_NAME              \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M410_STANDARDPARTCODE_T           \n");
		sb.append("			 WHERE  M410_YEAR = ?                       \n");
        //�����ڵ� �׸� �߰��� ���� ������
        //������ 2010.11.10
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '���꼼���ڵ�'  CD_NAME              \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M550_INCOMESEMOK_T                \n");
		sb.append("			 WHERE  M550_YEAR = ?                       \n");
		sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '����μ��ڵ�'  CD_NAME              \n");
		sb.append("						 ,COUNT(1) CNT                  \n");
		sb.append("				FROM  M551_INCOMEPART_T                 \n");
		sb.append("			 WHERE  M551_YEAR = ?                       \n");
        //�����ڵ����̺� ȸ�迬�� �߰��� ���� ������                    
        //�ѿ��� 2010.12.30                                             
        sb.append("			 UNION ALL                                  \n");
		sb.append("			SELECT '���������ڵ�'  CD_NAME              \n");
		sb.append("				 , COUNT(1) CNT                         \n");
		sb.append("		      FROM  M440_NONGHYUPCODE_T                 \n");
		sb.append("			 WHERE  M440_YEAR = ?                       \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
         
    int i = 1;
    parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
        //ǥ���ڵ� �׸� �߰��� ���� ������
        //�ѿ��� 2012.01.04
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
    //�����ڵ� �׸� �߰��� ���� ������
    //������ 2010.11.10
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
        //�����ڵ����̺� ȸ�迬�� �߰��� ���� ������
        //�ѿ��� 2010.12.30
        parameters.setString(i++, paramInfo.getString("year"));

		return template.getList(conn, parameters);
	}
	 /* ȸ���� �μ� �ڵ� ��� - �μ��ڵ� */
	 public static int insertEndYearCode(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
    		                                     
		sb.append(" INSERT  INTO M350_PARTCODE_T                                                                                                          \n");
		sb.append(" SELECT  ?, M350_PARTCODE,  M350_PARTNAME, M350_INSERTYN, M350_REALLOTPARTYN ,M350_REALLOTPARTCODE,M350_RECEIVENAME,M350_RECEIVECODE   \n");
		sb.append("   FROM M350_PARTCODE_T                                                                                                                \n"); 
		sb.append("  WHERE  M350_YEAR = ? - 1                                                                                                             \n");
	                        
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
	 /* ȸ���� ȸ�� �ڵ� ��� - ȸ���ڵ�*/
	 public static int insertEndYearCode2(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
    		                                     
		sb.append(" INSERT  INTO M360_ACCCODE_T                                                         \n");
		sb.append(" SELECT  ?, M360_ACCGUBUN,  M360_ACCCODE , M360_ACCNAME FROM M360_ACCCODE_T          \n"); 
		sb.append("  WHERE  M360_YEAR = ? - 1                                                           \n");
	                        
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
	 /* ȸ���� ���� �ڵ� ��� - �����ڵ� */
	 public static int insertEndYearCode3(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
    		                                     
		sb.append(" INSERT  INTO M370_SEMOKCODE_T                                                                                                            \n");
    sb.append(" SELECT  ?, M370_ACCGUBUN,M370_ACCCODE,M370_WORKGUBUN,M370_SEMOKCODE,M370_SEMOKNAME,M370_SEGUMGUBUN,M370_MOKGUBUN FROM M370_SEMOKCODE_T   \n");                
    sb.append("  WHERE  M370_YEAR = ? - 1                                                                                                                \n");
	                        
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
	 /* ȸ���� �ڵ� ��� - ��뼼���ڵ� */
	 public static int insertEndYearCode4(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
    		                                      
		sb.append("  INSERT  INTO M390_USESEMOKCODE_T                                                                             \n");
		sb.append("  SELECT  ?, M390_PARTCODE, M390_ACCGUBUN,M390_ACCCODE,M390_WORKGUBUN,M390_SEMOKCODE FROM  M390_USESEMOKCODE_T \n");
		sb.append("   WHERE  M390_YEAR = ? - 1                                                                                    \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
	 /* ȸ���� �ڵ� ��� - ȸ�迬���̿�*/
	 public static int insertEndYearCode5(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M250_ACCTRANSFER_T                                                                            \n");
		sb.append("  SELECT  ?, M250_ACCTYPE, M250_ACCCODE,M250_PARTCODE,M250_SEMOKCODE, M250_ACCTRANSFERYN,M250_AMTSURPLUSTOT  \n");
		sb.append("           , M250_AMTSURPLUSBEFORE,M250_AMTSURPLUSTODAY,M250_M010SEQ FROM  M250_ACCTRANSFER_T                \n");
		sb.append("   WHERE  M250_YEAR = ? - 1                                                                                  \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}

   /* ȸ���� �ڵ� ��� -�����ϵ�� */
	 public static int insertEndYearCode6(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M320_COLSINGDATECONTROL_T                                              \n");
		sb.append("  SELECT  ?, TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATEILBAN,'YYYYMMDD'),12),'YYYYMMDD')    \n");
		sb.append("       ,  TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATETEKBEYL,'YYYYMMDD'),12),'YYYYMMDD')     \n");
		sb.append("       ,  TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATEGIGEUM,'YYYYMMDD'),12),'YYYYMMDD')      \n");
		sb.append("       ,  TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATEHYUNGEUM,'YYYYMMDD'),12),'YYYYMMDD')    \n");
		sb.append("       ,  TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATEJEUNGJI,'YYYYMMDD'),12),'YYYYMMDD')     \n");
		sb.append("       ,  TO_CHAR(ADD_MONTHS(TO_DATE(M320_DATEJUHAENGSE,'YYYYMMDD'),12),'YYYYMMDD')   \n");
	  sb.append("    FROM  M320_COLSINGDATECONTROL_T                                                   \n"); 
		sb.append("   WHERE  M320_YEAR = ? - 1                                                           \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}

	 /* ȸ���� �ڵ� ��� -���뿹�ݰ��� ��ӻ�� ��� */
	 
	 public static int insertEndYearCode7(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M300_ACCOUNTMANAGE_T                                          \n");
		sb.append("  SELECT  ?, M300_BANKCODE, M300_ACCOUNTNO, M300_NAME, M300_ACCGUBUN         \n");
	  sb.append("           , M300_ACCCODE, M300_PARTCODE, M300_ACCOUNTTYPE, M300_ACCNAME     \n"); 
	  sb.append("           , M300_JUHANGACCYN, M300_STATECODE, M300_LOGNO                    \n"); 
	  sb.append("    FROM  M300_ACCOUNTMANAGE_T                                               \n"); 
		sb.append("   WHERE  M300_YEAR = ? - 1                                                  \n");        
		sb.append("							 AND SUBSTR(M300_ACCOUNTNO, 4, 2) = '07'                        \n");
		sb.append("							 AND M300_BANKCODE = '039'                                      \n");

		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.update(conn, parameters); 
	}

    //ǥ���ڵ� �׸� �߰��� ���� ������
    //�ѿ��� 2012.01.04
    /* ȸ���� �ڵ� ��� -ǥ�ؼ����ڵ� */
	 public static int insertEndYearCode11(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M420_STANDARDSEMOKCODE_T (                                                   \n");
		sb.append("    M420_YEAR,M420_STANDARDACCCODE,M420_STANDARDSEMOKCODE,M420_SEMOKNAME,M420_ACCGUBUN,M420_SYSTEMACCCODE,M420_WORKGUBUN,M420_SYSTEMSEMOKCODE,M420_BUDGETSEMOKCODE,M420_BUDGETSEMOKBYEAR \n");
		sb.append("  )                                                                                   \n");
		sb.append("  SELECT  ?, M420_STANDARDACCCODE,M420_STANDARDSEMOKCODE,M420_SEMOKNAME,M420_ACCGUBUN,M420_SYSTEMACCCODE,M420_WORKGUBUN,M420_SYSTEMSEMOKCODE,M420_BUDGETSEMOKCODE,M420_BUDGETSEMOKBYEAR   \n");
	  sb.append("    FROM  M420_STANDARDSEMOKCODE_T                                                          \n"); 
		sb.append("   WHERE  M420_YEAR = ? - 1                                                           \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
    /* ȸ���� �ڵ� ��� -ǥ�غμ��ڵ� */
	public static int insertEndYearCode12(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M410_STANDARDPARTCODE_T (                                                \n");
		sb.append("    M410_YEAR,M410_STANDARDPARTCODE,M410_PARTNAME,M410_SYSTEMPARTCODE,M410_BUDGETPARTCODE  \n");
		sb.append("  )                                                                               \n");
		sb.append("  SELECT  ?, M410_STANDARDPARTCODE,M410_PARTNAME,M410_SYSTEMPARTCODE,M410_BUDGETPARTCODE   \n");
	    sb.append("    FROM  M410_STANDARDPARTCODE_T                                                      \n"); 
		sb.append("   WHERE  M410_YEAR = ? - 1                                                       \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}

    //�����ڵ� �׸� �߰��� ���� ������
    //������ 2010.11.10
    /* ȸ���� �ڵ� ��� -���꼼���ڵ� */
	 public static int insertEndYearCode8(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M550_INCOMESEMOK_T (                                                   \n");
		sb.append("    M550_YEAR, M550_TAXGBN, M550_MOKGUBUN,M550_SEMOKCODE,M550_SEMOKNAME, M550_PARTGBN \n");
		sb.append("  )                                                                                   \n");
		sb.append("  SELECT  ?, M550_TAXGBN, M550_MOKGUBUN,M550_SEMOKCODE,M550_SEMOKNAME, M550_PARTGBN   \n");
	  sb.append("    FROM  M550_INCOMESEMOK_T                                                          \n"); 
		sb.append("   WHERE  M550_YEAR = ? - 1                                                           \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}
   /* ȸ���� �ڵ� ��� -����μ��ڵ� */
	 public static int insertEndYearCode9(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M551_INCOMEPART_T (                                                \n");
		sb.append("    M551_YEAR, M551_PARTCODE, M551_PARTNAME,M551_GBN                              \n");
		sb.append("  )                                                                               \n");
		sb.append("  SELECT  ?, M551_PARTCODE, M551_PARTNAME,M551_GBN                                \n");
	  sb.append("    FROM  M551_INCOMEPART_T                                                      \n"); 
		sb.append("   WHERE  M551_YEAR = ? - 1                                                       \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}


    public static int insertEndYearCode10(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("  INSERT  INTO M440_NONGHYUPCODE_T                                                \n");
		sb.append("  SELECT  ?, M440_INTYPE, M440_SEMOKNAME, M440_SEMOKCODE,                         \n");
        sb.append("          M440_PARTCODE11, M440_PARTCODE12, M440_PARTCODE13, M440_PARTCODE14,     \n");
        sb.append("          M440_PARTCODE15, M440_PARTCODE21, M440_PARTCODE22, M440_PARTCODE23,     \n");
        sb.append("          M440_PARTCODE24, M440_PARTCODE25                                        \n");
	    sb.append("    FROM  M440_NONGHYUPCODE_T                                                       \n"); 
		sb.append("   WHERE  M440_YEAR = ? - 1                                                       \n");        
	
		QueryTemplate template = new QueryTemplate(sb.toString());                                                                                   
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
        parameters.setString(i++, paramInfo.getString("year"));

		return template.insert(conn, parameters); 
	}

	/* ȸ���� �ڵ� ���� - �μ��ڵ�*/ 
  public static int deleteEndYearCode(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M350_PARTCODE_T    \n");
    sb.append("  WHERE M350_YEAR = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
   /* ȸ���� �ڵ� ���� - ȸ���ڵ�*/ 
	 public static int deleteEndYearCode2(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M360_ACCCODE_T     \n");
    sb.append("  WHERE M360_YEAR = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - �����ڵ�*/ 
	 public static int deleteEndYearCode3(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M370_SEMOKCODE_T   \n");
    sb.append("  WHERE M370_YEAR = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - ��뼼���ڵ�*/ 
	 public static int deleteEndYearCode4(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M390_USESEMOKCODE_T    \n");
    sb.append("  WHERE M390_YEAR = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - ȸ�迬���̿�*/ 
   public static int deleteEndYearCode5(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M250_ACCTRANSFER_T     \n");
    sb.append("  WHERE M250_YEAR = ?               \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - �������ڵ��*/ 
   public static int deleteEndYearCode6(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M320_COLSINGDATECONTROL_T     \n");
    sb.append("  WHERE M320_YEAR = ?                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - ���뿹�ݰ��� ��ӻ�� ���*/ 
   public static int deleteEndYearCode7(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append("  DELETE FROM M300_ACCOUNTMANAGE_T                \n");
    sb.append("   WHERE M300_YEAR = ?                            \n");
    sb.append("     AND SUBSTR(M300_ACCOUNTNO, 4, 2) = '07'      \n");
    sb.append("     AND M300_BANKCODE = '039'                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.update(conn, parameters);
  }

    //ǥ���ڵ� �׸� �߰��� ���� ������
    //������ 2010.11.10
	 /* ȸ���� �ڵ� ���� - ǥ�ؼ����ڵ�*/ 
   public static int deleteEndYearCode11(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M420_STANDARDSEMOKCODE_T       \n");
    sb.append("  WHERE M420_YEAR = ?                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - ǥ�غμ��ڵ�*/ 
   public static int deleteEndYearCode12(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M410_STANDARDPARTCODE_T     \n");
    sb.append("  WHERE M410_YEAR = ?                     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }

    //ǥ���ڵ� �׸� �߰��� ���� ������
    //������ 2010.11.10
	 /* ȸ���� �ڵ� ���� - ǥ�ؼ����ڵ�*/ 
   public static int deleteEndYearCode8(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M550_INCOMESEMOK_T     \n");
    sb.append("  WHERE M550_YEAR = ?                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }
	 /* ȸ���� �ڵ� ���� - ǥ�ؼ����ڵ�*/ 
   public static int deleteEndYearCode9(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M551_INCOMEPART_T     \n");
    sb.append("  WHERE M551_YEAR = ?                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

    return template.delete(conn, parameters);
  }

    public static int deleteEndYearCode10(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append(" DELETE FROM M440_NONGHYUPCODE_T     \n");
        sb.append("  WHERE M440_YEAR = ?                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("year"));

        return template.delete(conn, parameters);
    }

	/* ȸ���� �ڵ� �� */
	public static CommonEntity getYearCode(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

  	    sb.append("  SELECT * FROM (                                 \n");
		sb.append("  SELECT '�μ��ڵ�' CD_NAME                       \n");
		sb.append("         ,COUNT(1) CNT_1                          \n");
		sb.append("    FROM  M350_PARTCODE_T                         \n");
		sb.append("   WHERE  M350_YEAR = ?  ),                       \n");
		sb.append(" (SELECT 'ȸ���ڵ�' CD_NAME                       \n");
		sb.append("         ,COUNT(1) CNT_2                          \n");
		sb.append("    FROM  M360_ACCCODE_T                          \n");
		sb.append("   WHERE  M360_YEAR = ?   ),                      \n");
		sb.append(" (SELECT '�����ڵ�' CD_NAME                       \n");
		sb.append("         ,COUNT(1) CNT_3                          \n");
		sb.append("    FROM  M370_SEMOKCODE_T                  	     \n");
		sb.append("   WHERE  M370_YEAR = ?  ),			                 \n");
		sb.append(" (SELECT '��뼼���ڵ�' CD_NAME             	     \n");
		sb.append("          ,COUNT(1) CNT_4                         \n");
		sb.append("    FROM  M390_USESEMOKCODE_T               	     \n");
		sb.append("   WHERE  M390_YEAR = ?  ),			                 \n");
		sb.append(" (SELECT 'ȸ�迬���̿�' CD_NAME             	     \n");
		sb.append("          ,COUNT(1) CNT_5                         \n");
		sb.append("    FROM  M250_ACCTRANSFER_T                	     \n");
		sb.append("   WHERE  M250_YEAR = ?  ),			                 \n");
		sb.append(" (SELECT '������ ���'  CD_NAME             	     \n");
		sb.append("          ,COUNT(1) CNT_6                         \n");
		sb.append("    FROM  M320_COLSINGDATECONTROL_T         	     \n");
		sb.append("   WHERE  M320_YEAR = ? ), 			                 \n");
		sb.append(" (SELECT '���뿹�ݰ��� ��� ��� ���'  CD_NAME   \n");
		sb.append("         ,COUNT(1) CNT_7                          \n"); 
		sb.append("   FROM  M300_ACCOUNTMANAGE_T                     \n");
		sb.append("  WHERE  M300_YEAR = ?                            \n");
		sb.append("    AND  SUBSTR(M300_ACCOUNTNO, 4, 2) = '07'      \n");
		sb.append("    AND  M300_BANKCODE = '039'),  		             \n");
        //ǥ���ڵ� �׸� �߰��� ���� ������
        //�ѿ��� 2012.01.04
		sb.append(" (SELECT 'ǥ�ؼ����ڵ�'  CD_NAME            	     \n");
		sb.append("          ,COUNT(1) CNT_11                        \n");
		sb.append("    FROM  M420_STANDARDSEMOKCODE_T          	     \n");
		sb.append("   WHERE  M420_YEAR = ? ), 		                 \n");
		sb.append(" (SELECT 'ǥ�غμ��ڵ�'  CD_NAME            	     \n");
		sb.append("          ,COUNT(1) CNT_12                        \n");
		sb.append("    FROM  M410_STANDARDPARTCODE_T          	     \n");
        sb.append("   WHERE  M410_YEAR = ? ), 		                 \n");
        //�����ڵ� �׸� �߰��� ���� ������
        //������ 2010.11.10
		sb.append(" (SELECT 'ǥ�ؼ����ڵ�'  CD_NAME            	     \n");
		sb.append("          ,COUNT(1) CNT_8                         \n");
		sb.append("    FROM  M550_INCOMESEMOK_T               	     \n");
		sb.append("   WHERE  M550_YEAR = ? ), 			                 \n");
		sb.append(" (SELECT 'ǥ�غμ��ڵ�'  CD_NAME            	     \n");
		sb.append("          ,COUNT(1) CNT_9                         \n");
		sb.append("    FROM  M551_INCOMEPART_T                	     \n");
		sb.append("   WHERE  M551_YEAR = ? ), 			                 \n");
        //�����ڵ����̺� ȸ�迬�� �߰��� ���� ������
        //�ѿ��� 2010.12.30
        sb.append(" (SELECT '���������ڵ�'  CD_NAME            	     \n");
		sb.append("          ,COUNT(1) CNT_10                        \n");
		sb.append("    FROM  M440_NONGHYUPCODE_T                	 \n");
		sb.append("   WHERE  M440_YEAR = ? )  			             \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
         
        int i = 1;
        parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
        //ǥ���ڵ� �׸� �߰��� ���� ������
        //�ѿ��� 2012.01.04
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));

        //�����ڵ� �׸� �߰��� ���� ������
        //������ 2010.11.10
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("year"));
        parameters.setString(i++, paramInfo.getString("year"));

		return template.getData(conn, parameters);
	}
}