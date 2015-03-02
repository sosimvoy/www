/*****************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	    : IR010710.java
* ���α׷��ۼ���	: (��)�̸����� 
* ���α׷��ۼ���	: 2010-09-08
* ���α׷�����		: ���� > OCR��������/��� 
* ���α׷����      : 1. �Է��� ȸ������ ���� ���Լ����&���ܼ���OCR�ڷ� ������ ���� Ȯ��(ȸ���� ���� ���Ͻ� ���� �Ұ�)
                      2. �ش� ȸ������(ȸ������,ȸ���ڵ�,�����ڵ�,�μ��ڵ����) ���Լ���� ���
                      3. ȸ��⵵ �� �����ڵ� ����
                       1) ȸ������ �⵵ = �ΰ��⵵
                         -> ȸ��⵵ = �ΰ��⵵, �����ڵ� = ��ȯǥ�ؼ����ڵ�
                       2) ȸ������ �⵵ > �ΰ��⵵ 
                        2-1) ȸ������ ���� >= '0301'
                         -> ȸ��⵵ = ȸ������ �⵵, �����ڵ� = ���⵵����(2290100)
                        2-2) ȸ������ ���� < '0301'
                         i) (ȸ������ �⵵ - 1) = �ΰ��⵵
                          -> ȸ��⵵ = (ȸ������ �⵵ - 1), �����ڵ� = ��ȯǥ�ؼ����ڵ�
                         ii) (ȸ������ �⵵ - 1) > �ΰ��⵵
                          -> ȸ��⵵ = (ȸ������ �⵵ - 1), �����ڵ� = ���⵵����(2290100)
******************************************************/

package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010710 {
  
	/* ����е�� */
    public static int ocrInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M010_TAXIN_T													\n");
		sb.append("			(M010_SEQ, M010_YEAR										  		\n"); 		//�Ϸù�ȣ, ȸ��⵵
		sb.append("			,M010_DATE, M010_ACCTYPE											\n"); 		//ȸ������, ȸ�豸��
		sb.append("			,M010_ACCCODE, M010_SEMOKCODE, M010_YEARTYPE			  	        \n"); 		//ȸ���ڵ�, �����ڵ�
		sb.append("			,M010_PARTCODE,M010_SUNAPGIGWANCODE						            \n");		//�μ��ڵ�, ��������ڵ�
		sb.append("			,M010_INTYPE, M010_AMT					                            \n"); 		//�Է±���, �⵵����, �ݾ�
		sb.append("			,M010_CNT, M010_LOGNO											    \n"); 		//�Ǽ�,		�α׹�ȣ
		sb.append("			,M010_WORKTYPE, M010_TRANSGUBUN)							        \n"); 		//��������, �ŷ�����
		sb.append("SELECT M010_SEQ.NEXTVAL AS M010_SEQ                                          \n");
		sb.append("     , X.*                                                                   \n");
		sb.append("   from (                                                                    \n");
		sb.append("SELECT X.M010_YEAR, X.M010_DATE                                              \n");
        sb.append("     , X.M010_ACCTYPE, X.M010_ACCCODE, X.M010_SEMOKCODE                      \n");
        sb.append("     , DECODE(X.M010_SEMOKCODE, '1130100', 'Y2', '2290100', 'Y2', 'Y1') AS M010_YEARTYPE \n");
        sb.append("     , X.M010_PARTCODE, X.M010_SUNAPGIGWANCODE, X.M010_INTYPE                \n");
        sb.append("     , X.M010_AMT, X.M010_CNT                                                \n");
        sb.append("     , X.M010_LOGNO, X.M010_WORKTYPE, X.M010_TRANSGUBUN                      \n");
		sb.append("	 FROM (																	    \n");		//�Ϸù�ȣ
		sb.append("		    WITH TMPOCR AS (SELECT A.M010_YEAR                                                                                                                                           	\n");		// ȸ������        
		sb.append("		          ,A.ETC_ACCDATE AS M010_DATE                                                                                                                               	\n");		// ȸ�豸��(h)     
		sb.append("		          ,? AS M010_ACCTYPE                                                                                                                                      	\n");		// ȸ���ڵ�(��ȯ)  
		sb.append("		          ,B.M420_SYSTEMACCCODE AS M010_ACCCODE                                                                                                                     	\n"); 
		sb.append("		          ,CASE WHEN SUBSTR(A.ETC_ACCDATE,1,4) = A.ETC_YYYY THEN B.M420_SYSTEMSEMOKCODE                                                                             	\n");		 
		sb.append("		                WHEN SUBSTR(A.ETC_ACCDATE,1,4) > A.ETC_YYYY                                                                                                         	\n");		
		sb.append("		                THEN (CASE WHEN SUBSTR(A.ETC_ACCDATE,5,8) >= '0301' THEN '2290100'                                                                                  	\n");		
		sb.append("		                           ELSE (CASE WHEN TO_CHAR(SUBSTR(A.ETC_ACCDATE,1,4) - 1) = A.ETC_YYYY THEN B.M420_SYSTEMSEMOKCODE                                          	\n");		
		sb.append("		                                      ELSE '2290100' END)                                                                                                           	\n");		
		sb.append("		                           END)                                                                                                                                     	\n");		
		sb.append("		            END M010_SEMOKCODE                                                                                                                                      	\n");	    // �����ڵ�(��ȯ - ���)	
		sb.append("		          ,C.M410_SYSTEMPARTCODE AS M010_PARTCODE                                                                                                                   	\n");	// �μ��ڵ�(��ȯ)  	
		sb.append("		          ,?    AS M010_SUNAPGIGWANCODE                                                                                                                      	\n");	// ��������ڵ�(h) 	
		sb.append("		          ,?    AS M010_INTYPE                                                                                                                                   	\n");	// �Է±���(h)     	
		sb.append("		          ,?    AS M010_YEARTYPE                                                                                                                                 	\n");   // �⵵����(h)      
		sb.append("		          ,A.ETC_AMT2 AS M010_AMT                                                                                                                                   	\n");   // �ݾ�             
		sb.append("		          ,1       AS M010_CNT                                                                                                                                      	\n");   // �Ǽ�             
		sb.append("		          ,?    AS M010_LOGNO                                                                                                                                   	\n");   // �α׹�ȣ(req)    
		sb.append("		          ,?    AS M010_WORKTYPE                                                                                                                                	\n");   // ��������(req)    
		sb.append("		          ,?    AS M010_TRANSGUBUN                                                                                                                              	\n");   // �ŷ�����(req)    
		sb.append("		      FROM (                                                                                                                                                  	  \n");
        sb.append("              SELECT CASE                                                                                                                                          \n");
        sb.append("                     WHEN SUBSTR(ETC_ACCDATE,1,4) = ETC_YYYY  THEN ETC_YYYY                                                                                  \n");
        sb.append("                     WHEN SUBSTR(ETC_ACCDATE,1,4) > ETC_YYYY                                                                                                   \n");
        sb.append("                     THEN  (CASE                                                                                                                                   \n");
        sb.append("                            WHEN SUBSTR(ETC_ACCDATE,5,8) >= '0301' THEN SUBSTR(ETC_ACCDATE,1,4)                                                                \n");
        sb.append("                            ELSE  TO_CHAR(SUBSTR(ETC_ACCDATE,1,4) - 1) END)                                                                                      \n");
        sb.append("                     END AS M010_YEAR                                                                                                                              \n");
        sb.append("                   , T.*                                                                                                                                    \n");
        sb.append("                FROM ETC_T  T                                                                                                                                       \n");
        sb.append("               WHERE ETC_JOBDATE = ? ) A                                                                                                                           \n");
		sb.append("		          ,M420_STANDARDSEMOKCODE_T B                                                                                                                               	\n");
		sb.append("		          ,M410_STANDARDPARTCODE_T C                                                                                                                                	\n");
		sb.append("		     WHERE A.ETC_ACC = B.M420_STANDARDACCCODE                                                                                                                       	\n");
		sb.append("		       AND A.ETC_TAX = B.M420_STANDARDSEMOKCODE                                                                                                                     	\n");
        sb.append("		       AND A.M010_YEAR = B.M420_YEAR                                                                                                                     	\n");
		sb.append("		       AND A.ETC_PARTCODE = C.M410_STANDARDPARTCODE                                                                                                                 	\n");
		sb.append("		       AND A.M010_YEAR = C.M410_YEAR                                                                                                                     	\n");
		sb.append("		     )                                                                                                                                                              	\n");
        sb.append("		     SELECT M010_YEAR                                                                                                                                               	\n");
		sb.append("		            , M010_DATE                                                                                                                                             	\n");
		sb.append("		            , M010_ACCTYPE                                                                                                                                          	\n");
		sb.append("		            , M010_ACCCODE                                                                                                                                          	\n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , SUM(M010_AMT) AS M010_AMT                                                                                                                               \n");
		sb.append("               , SUM(M010_CNT) AS M010_CNT                                                                                                                               \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("          FROM (                                                                                                                                                         \n");
		sb.append("          SELECT M010_YEAR                                                                                                                                               \n");
		sb.append("               , M010_DATE                                                                                                                                               \n");
		sb.append("               , M010_ACCTYPE                                                                                                                                            \n");
		sb.append("               , M010_ACCCODE                                                                                                                                            \n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , M010_AMT                                                                                                                                                \n");
		sb.append("               , M010_CNT                                                                                                                                                \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("            FROM TMPOCR A                                                                                                                                                \n");
		sb.append("           WHERE EXISTS (                                                                                                                                                \n");
		sb.append("                 SELECT 1                                                                                                                                                \n");
		sb.append("                   FROM M390_USESEMOKCODE_T SUB_T                                                                                                                        \n");
		sb.append("                  WHERE SUB_T.M390_YEAR = A.M010_YEAR                                                                                                                    \n");
		sb.append("                    AND SUB_T.M390_PARTCODE = A.M010_PARTCODE                                                                                                            \n");
		sb.append("                    AND SUB_T.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_ACCCODE  = A.M010_ACCCODE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_WORKGUBUN = '0'                                                                                                                       \n");
		sb.append("                    AND SUB_T.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                          \n");
		sb.append("           )                                                                                                                                                             \n");
		sb.append("           UNION ALL                                                                                                                                                     \n");
		sb.append("           SELECT M010_YEAR                                                                                                                                              \n");
		sb.append("                , M010_DATE                                                                                                                                              \n");
		sb.append("                , M010_ACCTYPE                                                                                                                                           \n");
		sb.append("                , M010_ACCCODE                                                                                                                                           \n");
		sb.append("                , M010_SEMOKCODE                                                                                                                                         \n");
		sb.append("                , '00000' AS M010_PARTCODE                                                                                                                               \n");
		sb.append("                , M010_SUNAPGIGWANCODE                                                                                                                                   \n");
		sb.append("                , M010_INTYPE                                                                                                                                            \n");
		sb.append("                , M010_YEARTYPE                                                                                                                                          \n");
		sb.append("                , M010_AMT                                                                                                                                               \n");
		sb.append("                , M010_CNT                                                                                                                                               \n");
		sb.append("                , M010_LOGNO                                                                                                                                             \n");
		sb.append("                , M010_WORKTYPE                                                                                                                                          \n");
		sb.append("                , M010_TRANSGUBUN                                                                                                                                        \n");
		sb.append("            FROM TMPOCR A                                                                                                                                                \n");
		sb.append("               , M390_USESEMOKCODE_T B                                                                                                                                   \n");
		sb.append("           WHERE B.M390_YEAR = A.M010_YEAR                                                                                                                               \n");
		sb.append("             AND B.M390_PARTCODE = '00000'                                                                                                                               \n");
		sb.append("             AND B.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                                        \n");
		sb.append("             AND B.M390_ACCCODE  = A.M010_ACCCODE                                                                                                                        \n");
		sb.append("             AND B.M390_WORKGUBUN = '0'                                                                                                                                  \n");
		sb.append("             AND B.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                                     \n");
		sb.append("             AND NOT EXISTS (                                                                                                                                            \n");
		sb.append("                 SELECT 1                                                                                                                                                \n");
		sb.append("                   FROM M390_USESEMOKCODE_T SUB_T                                                                                                                        \n");
		sb.append("                  WHERE SUB_T.M390_YEAR = A.M010_YEAR                                                                                                                    \n");
		sb.append("                    AND SUB_T.M390_PARTCODE = A.M010_PARTCODE                                                                                                            \n");
		sb.append("                    AND SUB_T.M390_ACCGUBUN = A.M010_ACCTYPE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_ACCCODE  = A.M010_ACCCODE                                                                                                             \n");
		sb.append("                    AND SUB_T.M390_WORKGUBUN = '0'                                                                                                                       \n");
		sb.append("                    AND SUB_T.M390_SEMOKCODE = A.M010_SEMOKCODE                                                                                                          \n");
		sb.append("           )                                                                                                                                                             \n");
		sb.append("         )                                                                                                                                                               \n");
		sb.append("         GROUP BY M010_YEAR                                                                                                                                              \n");
		sb.append("               , M010_DATE                                                                                                                                               \n");
		sb.append("               , M010_ACCTYPE                                                                                                                                            \n");
		sb.append("               , M010_ACCCODE                                                                                                                                            \n");
		sb.append("               , M010_SEMOKCODE                                                                                                                                          \n");
		sb.append("               , M010_PARTCODE                                                                                                                                           \n");
		sb.append("               , M010_SUNAPGIGWANCODE                                                                                                                                    \n");
		sb.append("               , M010_INTYPE                                                                                                                                             \n");
		sb.append("               , M010_YEARTYPE                                                                                                                                           \n");
		sb.append("               , M010_LOGNO                                                                                                                                              \n");
		sb.append("               , M010_WORKTYPE                                                                                                                                           \n");
		sb.append("               , M010_TRANSGUBUN                                                                                                                                         \n");
		sb.append("		 ) X											                                                                                                                                            \n");											   
        sb.append("         WHERE NOT EXISTS (                                                 \n");
        sb.append("              SELECT 1                                                      \n");
        sb.append("                FROM M010_TAXIN_T TAR                                       \n");
        sb.append("               WHERE TAR.M010_YEAR = X.M010_YEAR                            \n");
        sb.append("                 AND TAR.M010_DATE = X.M010_DATE                            \n");
        sb.append("                 AND TAR.M010_ACCTYPE = X.M010_ACCTYPE                      \n");
        sb.append("                 AND TAR.M010_ACCCODE = X.M010_ACCCODE                      \n");
        sb.append("                 AND TAR.M010_SEMOKCODE = X.M010_SEMOKCODE                  \n");
        sb.append("                 AND TAR.M010_PARTCODE = X.M010_PARTCODE                    \n");
        sb.append("                 AND TAR.M010_SUNAPGIGWANCODE = X.M010_SUNAPGIGWANCODE      \n");
        sb.append("                 AND TAR.M010_INTYPE = X.M010_INTYPE                        \n");
        sb.append("                 AND TAR.M010_AMT = X.M010_AMT                              \n");
        sb.append("                 AND TAR.M010_CNT = X.M010_CNT                              \n");
        sb.append("                 AND TAR.M010_WORKTYPE = X.M010_WORKTYPE                    \n");
        sb.append("                 AND TAR.M010_TRANSGUBUN = X.M010_TRANSGUBUN                \n");
        sb.append("         )                                                                  \n");
        sb.append("         )  X                                                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int idx = 1;
		parameters.setString(idx++, "A");			// ȸ�豸��(�Ϲ�ȸ��)
		parameters.setString(idx++, "110000");		// ��������ڵ�
		parameters.setString(idx++, "I1");			// �Է±���(����)
		parameters.setString(idx++, "Y1");			// ��������(���⵵)
		parameters.setString(idx++, paramInfo.getString("log_no"));			// �α׹�ȣ
		parameters.setString(idx++, paramInfo.getString("work_log"));		// ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));	// �ŷ�����
		parameters.setString(idx++, paramInfo.getString("input_date"));		// �۾�����
		
		return template.insert(conn, parameters); 
	}

	/* ������ ���� Ȯ�� */
	public static CommonEntity getOcrCount(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT SUM(A.ETC_CNT) ETC_CNT		\n");
		sb.append("	     ,SUM(A.TAXIN_CNT) TAXIN_CNT	\n");
		sb.append("  FROM (								\n");
		sb.append("SELECT COUNT(1)ETC_CNT				\n");
		sb.append("      ,0 TAXIN_CNT					\n");
		sb.append("  FROM ETC_T							\n");
		sb.append(" WHERE ETC_JOBDATE = ?				\n");	// ȸ������
		sb.append(" UNION								\n");
		sb.append("SELECT 0 ETC_CNT						\n");
		sb.append("      ,COUNT(1)TAXIN_CNT				\n");
		sb.append("  FROM M010_TAXIN_T					\n");
		sb.append(" WHERE M010_DATE = ?					\n");	// ȸ������
		sb.append("   AND M010_WORKTYPE = ?				\n");	// ��������(req)
		sb.append("   AND M010_TRANSGUBUN = ?			\n");	// �ŷ�����(req)
		sb.append("		) A								\n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("input_date"));
		parameters.setString(idx++,  paramInfo.getString("input_date"));
		parameters.setString(idx++,  paramInfo.getString("work_log"));		// ��������
		parameters.setString(idx++,  paramInfo.getString("trans_gubun"));	// �ŷ�����

		return template.getData(conn, parameters);
	}

	/* �۾����� ���� ���� Ȯ�� */
	public static CommonEntity getMagamCount(Connection conn, CommonEntity paramInfo) throws SQLException {
	    StringBuffer sb = new StringBuffer();
		
		sb.append("select count(*) as MAGAM_CNT  		\n");
		sb.append("  FROM M210_WORKEND_T				\n");
		sb.append(" WHERE m210_date = ?			    	\n");	// �۾�����
		sb.append("   AND m210_workendstate = 'Y'    	\n");	// ��������

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,  paramInfo.getString("input_date"));

		return template.getData(conn, parameters);
	}
	/* ����� ���� */
	public static int ocrDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M010_TAXIN_T     \n");
		sb.append(" WHERE M010_DATE = ?         \n");	// ȸ������
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("input_date"));
		
		return template.delete(conn, parameters);
	}
}