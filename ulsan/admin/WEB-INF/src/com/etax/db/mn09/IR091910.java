/***************************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	    : IR091910.java
* ���α׷��ۼ���    : (��)�̸�����
* ���α׷��ۼ���    : 2012-02-09
* ���α׷�����	    : �ý��ۿ > �μ���������������
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091910 {

	/* �μ��ڵ� ��ȸ */
	public static List<CommonEntity> getPartList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT M350_YEAR                                 \n");
        sb.append("      , M350_PARTCODE                             \n");
        sb.append("      , M350_PARTNAME                             \n");
        sb.append("   FROM M350_PARTCODE_T                           \n");
        sb.append("  WHERE M350_YEAR     = TO_CHAR(SYSDATE, 'YYYY')  \n");
        sb.append("    AND M350_INSERTYN = ?                         \n");
        sb.append("    AND M350_PARTCODE <> ?                        \n");
        sb.append(" ORDER BY M350_PARTCODE                           \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  "Y");      //����� ���� ��ȸ ���� ����
        parameters.setString(idx++,  "00001");  //��û(������ ����)
  
		return template.getList(conn, parameters);
	}

	/* �������� ��ȸ */
	public static List<CommonEntity> getDocList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT *                                \n");
        sb.append("   FROM M230_REPORTCODE_T                \n");
        sb.append("  WHERE M230_WORKYN = ?                  \n");
        sb.append("    AND M230_REPORTGUBUN = NVL(?, 'D')   \n");
        sb.append("  ORDER BY M230_REPORTCODE               \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  "Y");  //����� ��ȸ������ �͸�
		parameters.setString(idx++,  paramInfo.getString("reportgubun")); //����, ����, �б�
  
		return template.getList(conn, parameters);
	}

    /* ȸ���ڵ� ��ȸ */
	public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT *                                    \n");
        sb.append("   FROM M360_ACCCODE_T                       \n");
        sb.append("  WHERE M360_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");
        sb.append("    AND M360_ACCGUBUN = NVL(?, 'A')          \n");
        sb.append("  ORDER BY M360_ACCCODE                      \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("accgubun")); //����, ����, �б�
  
		return template.getList(conn, parameters);
	}

	/* �������� ��ȸ */
	public static List<CommonEntity> getDocInfoList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT A.*                                                                      \n");
        sb.append("      , DECODE(SUBSTR(A.M630_DOCCODE,1,1), 'D', '���Ϻ���',                    \n");
        sb.append("        'M', '��������', 'Q', '�б⺸��') DOCGUBUNNAME                       \n");
        sb.append("      , D.M230_REPORTNAME                                                        \n");
        sb.append("      , B.M350_PARTNAME                                                          \n");
        sb.append("      , DECODE(A.M630_ACCTYPE, 'A', '�Ϲ�ȸ��', 'B', 'Ư��ȸ��',                 \n");
        sb.append("        'C', '�����Ư��ȸ��', 'D', '���Լ��������', 'E', '���') ACCTYPENAME   \n");
        sb.append("      , C.M360_ACCNAME                                                           \n");
        sb.append("   FROM M630_DOCUMENTINFO_T A                                                    \n");
        sb.append("      , M350_PARTCODE_T B                                                        \n");
        sb.append("      , M360_ACCCODE_T C                                                         \n");
        sb.append("      , M230_REPORTCODE_T D                                                      \n");
        sb.append("  WHERE A.M630_PARTCODE = B.M350_PARTCODE                                        \n");
        sb.append("    AND A.M630_ACCTYPE = C.M360_ACCGUBUN(+)                                      \n");
        sb.append("    AND A.M630_ACCCODE = C.M360_ACCCODE(+)                                       \n");
        sb.append("    AND SUBSTR(A.M630_DOCCODE,1,1) = D.M230_REPORTGUBUN                          \n");
        sb.append("    AND SUBSTR(A.M630_DOCCODE,2) = D.M230_REPORTCODE                             \n");
        sb.append("    AND D.M230_WORKYN = 'Y' --�������ȸ������ �͸�                              \n");
        sb.append("    AND B.M350_YEAR = TO_CHAR(SYSDATE, 'YYYY')                                   \n");
        sb.append("    AND C.M360_YEAR(+) = TO_CHAR(SYSDATE, 'YYYY')                                \n");
        sb.append("    AND A.M630_PARTCODE = ?                                                      \n");
        sb.append("  ORDER BY A.M630_DOCCODE, A.M630_ACCTYPE, A.M630_ACCCODE                        \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("list_part"));
  
		return template.getList(conn, parameters);
	}

    /* �����ϰ��������� */
	public static CommonEntity getIlgwalInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT M630_ILGWALYN                            \n");
        sb.append("   FROM M630_DOCUMENTINFO_T                      \n");
        sb.append("  WHERE M630_PARTCODE = ?                        \n");
        sb.append("    AND M630_DOCCODE	= ? || SUBSTR(?, 2)         \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("partcode"));
        parameters.setString(idx++,  paramInfo.getString("reportgubun"));
        parameters.setString(idx++,  paramInfo.getString("reportcode"));
  
		return template.getData(conn, parameters);
	}


    /* �������� �ڷ� ���� */
	public static CommonEntity getCount(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(*) CNT                             \n");
        sb.append("   FROM M630_DOCUMENTINFO_T                      \n");
        sb.append("  WHERE M630_PARTCODE = ?                        \n");
        sb.append("    AND M630_DOCCODE	= ? || SUBSTR(?, 2)         \n");
        sb.append("    AND NVL(M630_ACCTYPE, 'M')	= NVL(?, 'M')   \n");
        sb.append("    AND NVL(M630_ACCCODE, '00')	= NVL(?, '00')  \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("partcode"));
        parameters.setString(idx++,  paramInfo.getString("reportgubun"));
        parameters.setString(idx++,  paramInfo.getString("reportcode"));
        parameters.setString(idx++,  paramInfo.getString("accgubun"));
        parameters.setString(idx++,  paramInfo.getString("acccode"));
  
		return template.getData(conn, parameters);
	}

	 /* �������� ��� */
	 public static int insertDocInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M630_DOCUMENTINFO_T     \n");
		sb.append(" (  M630_PARTCODE                    \n"); 
		sb.append("	 , M630_DOCCODE                     \n"); 
		sb.append("	 , M630_ACCTYPE                     \n"); 
		sb.append("	 , M630_ACCCODE                     \n"); 
		sb.append("	 , M630_ELECYN                      \n");
        sb.append("	 , M630_ILGWALYN )                  \n");
		sb.append("  VALUES(                            \n");
		sb.append("            ?                        \n");
		sb.append("          , ? || SUBSTR(?, 2)        \n");
		sb.append("          , ?                        \n");
		sb.append("          , ?                        \n");
		sb.append("          , ?                        \n");
        sb.append("          , ?  )                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("partcode"));
        parameters.setString(idx++,  paramInfo.getString("reportgubun"));
        parameters.setString(idx++,  paramInfo.getString("reportcode"));
        parameters.setString(idx++,  paramInfo.getString("accgubun"));
        parameters.setString(idx++,  paramInfo.getString("acccode"));
        parameters.setString(idx++,  paramInfo.getString("elecyn"));
        parameters.setString(idx++,  paramInfo.getString("ilgwalyn"));

		return template.insert(conn, parameters); 
	}


	/* �������� ���� */ 
    public static int deleteDocInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append(" DELETE FROM M630_DOCUMENTINFO_T                 \n");
        sb.append("  WHERE M630_PARTCODE = ?                        \n");
        sb.append("    AND M630_DOCCODE	= ?                         \n");
        sb.append("    AND NVL(M630_ACCTYPE, 'M')	= NVL(?, 'M')   \n");
        sb.append("    AND NVL(M630_ACCCODE, '00')	= NVL(?, '00')  \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++,  paramInfo.getString("list_part"));
        parameters.setString(idx++,  paramInfo.getString("list_reportcode"));
        parameters.setString(idx++,  paramInfo.getString("list_accgubun"));
        parameters.setString(idx++,  paramInfo.getString("list_acccode"));

        return template.delete(conn, parameters);
    }


    /* ��������� ���� */ 
    public static int deleteDocManager(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append(" DELETE FROM M600_DOCUMENTTYPE_T                 \n");
        sb.append("  WHERE M600_DEPT = ?                            \n");
        sb.append("    AND M600_DOCUMENTCODE = ?                    \n");
        sb.append("    AND NVL(M600_ACCTYPE, 'M')	= NVL(?, 'M')   \n");
        sb.append("    AND NVL(M600_ACCCODE, '00')	= NVL(?, '00')  \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++,  paramInfo.getString("list_part"));
        parameters.setString(idx++,  paramInfo.getString("list_reportcode"));
        parameters.setString(idx++,  paramInfo.getString("list_accgubun"));
        parameters.setString(idx++,  paramInfo.getString("list_acccode"));

        return template.delete(conn, parameters);
    }
}