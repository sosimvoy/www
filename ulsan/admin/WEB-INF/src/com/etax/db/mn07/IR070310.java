/*****************************************************
* ������Ʈ��	    : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	    : IR070310.java
* ���α׷��ۼ���    : (��)�̸����� 
* ���α׷��ۼ���    : 2010-10-01
* ���α׷�����      : �ϰ�/���� > ȸ�迬���̿�
* ���α׷����      : 2010-10-12 �ϰ� ���̺��� �μ� ������� ��� ó��
                      2010-10-21 �׿����Ѿ� ��� ���� �� �������� (�Ϲ�,Ư��,��� �ش�)
******************************************************/
package com.etax.db.mn07;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070310 {

	/* ������ ��ȸ */
	public static CommonEntity getCloseDate(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M320_YEAR                     \n");
		sb.append("      ,M320_DATEILBAN                \n");   // �Ϲ�ȸ��
		sb.append("      ,M320_DATETEKBEYL              \n");   // Ư��ȸ��
		sb.append("      ,M320_DATEGIGEUM               \n");   // ���ȸ��
		sb.append("      ,M320_DATEHYUNGEUM             \n");   // ���Լ��������
		sb.append("      ,M320_DATEJEUNGJI              \n");   // ��������
		sb.append("      ,M320_DATEJUHAENGSE            \n");   // ���༼
		sb.append("  FROM M320_COLSINGDATECONTROL_T     \n");
		sb.append(" WHERE M320_YEAR = ?                 \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("acc_year"));

		return template.getData(conn, parameters);
	}


    /*  �̿������ȸ (ȸ�豸��:A,B,E)  - ���� ���� */
	public static List<CommonEntity> getTransList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M220_YEAR                  ACCYEAR                              \n");
		sb.append("      ,A.M220_DATE                  ACCDATE                              \n");
		sb.append("      ,A.M220_ACCTYPE               ACCTYPE                              \n");
		sb.append("      ,A.M220_ACCCODE               ACCCODE                              \n");
		sb.append("      ,D.M360_ACCNAME               ACCNAME                              \n");
		sb.append("      ,B.M210_WORKENDSTATE          WORKENDSTATE                         \n");   // ��������
		sb.append("      ,NVL(C.M250_ACCTRANSFERYN, 'N') ACCTRANSFERYN                      \n");   // ȸ��⵵�̿�����
		
        sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                    \n");
		sb.append("           A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -            \n");
		sb.append("           A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -              \n");
        sb.append("           A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                \n");
        sb.append("           A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) ING_AMT  \n");   // �׿����Ѿ� 2014.01.03 �ڱݹ������ɾ� �߰�
		
        sb.append("      ,SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) PRE_SUR_AMT   \n");   // �����Ծ�
		
        sb.append("      ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                    \n");
		sb.append("           A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -            \n");
		sb.append("           A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -              \n");
        sb.append("           A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                \n");
        sb.append("           A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -        \n");
		sb.append("       SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) TODAY_SUR_AMT \n");   // �������Ծ�(�׿����Ѿ�-�����Ծ�)
		
        sb.append("FROM   M220_DAY_T A                                                      \n");
		sb.append("      ,M210_WORKEND_T B                                                  \n");
		sb.append("      ,M250_ACCTRANSFER_T C                                              \n");
		sb.append("      ,M360_ACCCODE_T D                                                  \n");
		sb.append("      ,M370_SEMOKCODE_T E                                                \n");

		sb.append("WHERE  A.M220_YEAR          = B.M210_YEAR                                \n");
		sb.append("AND    A.M220_DATE          = B.M210_DATE                                \n");
		sb.append("AND    A.M220_YEAR          = C.M250_YEAR(+)                                \n");
		sb.append("AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                             \n");
		sb.append("AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                             \n");
		sb.append("AND    A.M220_YEAR          = D.M360_YEAR                                \n");
		sb.append("AND    A.M220_ACCTYPE       = D.M360_ACCGUBUN                            \n");
		sb.append("AND    A.M220_ACCCODE       = D.M360_ACCCODE                             \n");
        
        sb.append("AND    A.M220_YEAR           = E.M370_YEAR                               \n");
		sb.append("AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                           \n");
		sb.append("AND    A.M220_ACCCODE        = E.M370_ACCCODE                            \n");
		sb.append("AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                          \n");
        sb.append("AND    E.M370_SEGUMGUBUN <> '2'                                          \n");   //  ��������
                 sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n"); 
		sb.append("AND    A.M220_YEAR          = ?                                          \n");
		sb.append("AND    A.M220_DATE          = ?                                          \n");   // ������
		sb.append("AND    A.M220_ACCTYPE       = ?                                          \n");
		sb.append("GROUP BY A.M220_YEAR                                                     \n");
		sb.append("        ,A.M220_DATE                                                     \n");
		sb.append("        ,A.M220_ACCTYPE                                                  \n");
		sb.append("        ,A.M220_ACCCODE                                                  \n");
		sb.append("        ,B.M210_WORKENDSTATE                                             \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                            \n");
		sb.append("        ,D.M360_ACCNAME                                                  \n");
		sb.append("ORDER BY A.M220_ACCTYPE                                                  \n");
		sb.append("        ,A.M220_ACCCODE                                                  \n");
		sb.append("        ,B.M210_WORKENDSTATE                                             \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                            \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
        
        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_year"));
		parameters.setString(i++, paramInfo.getString("end_date"));
		parameters.setString(i++, paramInfo.getString("acc_type"));

		return template.getList(conn, parameters);
	}

    
    /* �̿������ȸ (ȸ�豸��:D ���Լ��������) */
	public static List<CommonEntity> getTransCashList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT A.M270_YEAR                   ACCYEAR                 \n");
		sb.append("      ,A.M270_DATE                   ACCDATE                 \n");
		sb.append("      ,C.M250_ACCTYPE                ACCTYPE                 \n");
		sb.append("      ,A.M270_ACCCODE                ACCCODE                 \n");
		sb.append("      ,D.M360_ACCNAME                ACCNAME                 \n");
		sb.append("      ,B.M210_WORKENDSTATE           WORKENDSTATE            \n");   // ��������
		sb.append("      ,NVL(C.M250_ACCTRANSFERYN, 'N')  ACCTRANSFERYN           \n");   // ȸ��⵵�̿�����

		sb.append("      ,(SUM(M270_BOJEUNGJEONGGIIBJEON  +                     \n");
		sb.append("            M270_BOJEUNGJEONGGIIBGEUM  -                     \n");
    sb.append("            M270_BOJEUNGJEONGGIIBJEONG -                     \n");
    sb.append("            M270_BOJEUNGJEONGGIJIJEON  -                     \n");
		sb.append("            M270_BOJEUNGJEONGGIJIGEUB  +                     \n");
    sb.append("            M270_BOJEUNGJEONGGIJIJEONG)                      \n");
    sb.append("       +SUM(M270_BOJEUNGBYULDANIBJEON  +                     \n");
		sb.append("            M270_BOJEUNGBYULDANIBGEUM  -                     \n");
		sb.append("            M270_BOJEUNGBYULDANIBJEONG -                     \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEON  -                     \n");
    sb.append("            M270_BOJEUNGBYULDANJIGEUB  +                     \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEONG)                      \n");
		sb.append("       +SUM(M270_BOJEUNGGONGGEUMIBJEON  +                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBGEUM  -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBJEONG -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEON  -                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIGEUB  +                    \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEONG)                     \n");
    sb.append("       +SUM(M270_BOGWANJEONGGIIBJEON  +                      \n");
    sb.append("            M270_BOGWANJEONGGIIBGEUM  -                      \n");
		sb.append("            M270_BOGWANJEONGGIIBJEONG -                      \n");
		sb.append("            M270_BOGWANJEONGGIJIJEON  -                      \n");
		sb.append("            M270_BOGWANJEONGGIJIGEUB  +                      \n");
    sb.append("            M270_BOGWANJEONGGIJIJEONG)                       \n");
		sb.append("       +SUM(M270_BOGWANBYULDANIBJEON  +                      \n");
		sb.append("            M270_BOGWANBYULDANIBGEUM  -                      \n");
		sb.append("            M270_BOGWANBYULDANIBJEONG -                      \n");
		sb.append("            M270_BOGWANBYULDANJIJEON  -                      \n");
		sb.append("            M270_BOGWANBYULDANJIGEUB  +                      \n");
		sb.append("            M270_BOGWANBYULDANJIJEONG)                       \n");
		sb.append("       +SUM(M270_BOGWANGONGGEUMIBJEON  +                     \n");
    sb.append("            M270_BOGWANGONGGEUMIBGEUM  -                     \n");
    sb.append("            M270_BOGWANGONGGEUMIBJEONG -                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEON  -                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIGEUB  +                     \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEONG)                      \n");
    sb.append("       +SUM(M270_JABJONGJEONGGIIBJEON  +                     \n");
		sb.append("            M270_JABJONGJEONGGIIBGEUM  -                     \n");
		sb.append("            M270_JABJONGJEONGGIIBJEONG -                     \n");
		sb.append("            M270_JABJONGJEONGGIJIJEON  -                     \n");
		sb.append("            M270_JABJONGJEONGGIJIGEUB  +                     \n");
		sb.append("            M270_JABJONGJEONGGIJIJEONG)                      \n");
		sb.append("       +SUM(M270_JABJONGBYULDANIBJEON  +                     \n");
		sb.append("            M270_JABJONGBYULDANIBGEUM  -                     \n");
    sb.append("            M270_JABJONGBYULDANIBJEONG -                     \n");
    sb.append("            M270_JABJONGBYULDANJIJEON  -                     \n");
		sb.append("            M270_JABJONGBYULDANJIGEUB  +                     \n");
		sb.append("            M270_JABJONGBYULDANJIJEONG)                      \n");
		sb.append("       +SUM(M270_JABJONGGONGGEUMIBJEON  +                    \n");
    sb.append("            M270_JABJONGGONGGEUMIBGEUM  -                    \n");
		sb.append("            M270_JABJONGGONGGEUMIBJEONG -                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEON  -                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIGEUB  +                    \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEONG)                     \n");
		sb.append("       +SUM(M270_BUGASEJEONGGIIBJEON  +                      \n");
		sb.append("            M270_BUGASEJEONGGIIBGEUM  -                      \n");
		sb.append("            M270_BUGASEJEONGGIIBJEONG -                      \n");
    sb.append("            M270_BUGASEJEONGGIJIJEON  -                      \n");
    sb.append("            M270_BUGASEJEONGGIJIGEUB  +                      \n");
		sb.append("            M270_BUGASEJEONGGIJIJEONG)                       \n");
		sb.append("       +SUM(M270_BUGASEBYULDANIBJEON  +                      \n");
		sb.append("            M270_BUGASEBYULDANIBGEUM  -                      \n");
    sb.append("            M270_BUGASEBYULDANIBJEONG -                      \n");
		sb.append("            M270_BUGASEBYULDANJIJEON  -                      \n");
		sb.append("            M270_BUGASEBYULDANJIGEUB  +                      \n");
		sb.append("            M270_BUGASEBYULDANJIJEONG)                       \n");
		sb.append("       +SUM(M270_BUGASEGONGGEUMIBJEON  +                     \n");
		sb.append("            M270_BUGASEGONGGEUMIBGEUM  -                     \n");
		sb.append("            M270_BUGASEGONGGEUMIBJEONG -                     \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEON  -                     \n");
    sb.append("            M270_BUGASEGONGGEUMJIGEUB  +                     \n");
    sb.append("            M270_BUGASEGONGGEUMJIJEONG)) ING_AMT             \n");

		sb.append("FROM   M270_TAXCASHDAY_T A                                   \n");
		sb.append("      ,M210_WORKEND_T B                                      \n");
    sb.append("      ,M250_ACCTRANSFER_T C                                  \n");
		sb.append("      ,M360_ACCCODE_T D                                      \n");
		sb.append("WHERE  A.M270_YEAR          = B.M210_YEAR                    \n");
		sb.append("AND    A.M270_DATE          = B.M210_DATE                    \n");
		sb.append("AND    A.M270_YEAR          = C.M250_YEAR                    \n");
		sb.append("AND    A.M270_ACCCODE       = C.M250_ACCCODE                 \n");
	//sb.append("--AND    A.M270_PARTCODE      = C.M250_PARTCODE              \n"); //�ϰ� �μ� ����X (2010-10-12)   
		sb.append("AND    A.M270_YEAR          = D.M360_YEAR                    \n");
    sb.append("AND    A.M270_ACCCODE       = D.M360_ACCCODE                 \n");
    sb.append("AND    C.M250_ACCTYPE       = D.M360_ACCGUBUN                \n");
		sb.append("AND    A.M270_YEAR          = ?                              \n");
		sb.append("AND    A.M270_DATE          = ?                              \n");    // ������
		sb.append("AND    C.M250_ACCTYPE       = ?                              \n");
		sb.append("GROUP BY A.M270_YEAR                                         \n");
		sb.append("        ,A.M270_DATE                                         \n");
		sb.append("        ,C.M250_ACCTYPE                                      \n");
		sb.append("        ,A.M270_ACCCODE                                      \n");
		sb.append("        ,B.M210_WORKENDSTATE                                 \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                \n");
		sb.append("        ,D.M360_ACCNAME                                      \n");
		sb.append("ORDER BY C.M250_ACCTYPE                                      \n");
		sb.append("        ,A.M270_ACCCODE                                      \n");
		sb.append("        ,B.M210_WORKENDSTATE                                 \n");
		sb.append("        ,C.M250_ACCTRANSFERYN                                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
        
        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_year"));
		parameters.setString(i++, paramInfo.getString("end_date"));
		parameters.setString(i++, paramInfo.getString("acc_type"));

		return template.getList(conn, parameters);
	}


    /* ���Լ���� ���̺� �Ϸù�ȣ ��ȸ (������) */
	public static CommonEntity getTaxInSeq(Connection conn)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M010_SEQ.NEXTVAL AS MAX_SEQ   \n");
		sb.append("  FROM DUAL                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		return template.getData(conn);
	}


    /* ���Լ�������ݼ���� ���̺� �Ϸù�ȣ ��ȸ (������) */
	public static CommonEntity getTaxCashSeq(Connection conn)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT M040_SEQ.NEXTVAL AS MAX_SEQ   \n");
		sb.append("  FROM DUAL                          \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		return template.getData(conn);
	}


    /* ���Լ���� (ȸ�豸��:A,B,E) �⵵�̿� INSERT  - ���� ���� */
	public static int setTaxInInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M010_TAXIN_T                                                         \n"); 
		sb.append("(      M010_SEQ                                                                  \n");
		sb.append("      ,M010_YEAR                                                                 \n");
		sb.append("      ,M010_DATE                                                                 \n");
		sb.append("      ,M010_ACCTYPE                                                              \n");
		sb.append("      ,M010_ACCCODE                                                              \n");
		sb.append("      ,M010_SEMOKCODE                                                            \n");
		sb.append("      ,M010_PARTCODE                                                             \n");
		sb.append("      ,M010_SUNAPGIGWANCODE                                                      \n");
		sb.append("      ,M010_INTYPE                                                               \n");
		sb.append("      ,M010_YEARTYPE                                                             \n");
		sb.append("      ,M010_AMT                                                                  \n");
		sb.append("      ,M010_CNT                                                                  \n");
		sb.append("      ,M010_LOGNO                                                                \n");
		sb.append("      ,M010_WORKTYPE                                                             \n");
		sb.append("      ,M010_TRANSGUBUN                                                           \n");
		sb.append(")                                                                                \n");
		sb.append("SELECT M010_SEQ.NEXTVAL, X.*                                                     \n");
		sb.append("FROM  (                                                                          \n");
		sb.append("       SELECT ? YEAR                                                             \n");
		sb.append("             ,? M220_DATE                                                        \n");
		sb.append("             ,A.M220_ACCTYPE                                                     \n");
		sb.append("             ,A.M220_ACCCODE                                                     \n");
		sb.append("             ,CASE WHEN C.M250_SEMOKCODE IS NULL THEN MAX(A.M220_SEMOKCODE) ELSE C.M250_SEMOKCODE END SEMOK_CODE \n");
		//sb.append("             ,A.M220_PARTCODE                                                    \n"); //�ϰ� �μ� ����X (2010-10-12)   
		sb.append("             ,CASE WHEN C.M250_PARTCODE IS NULL THEN MAX(A.M220_PARTCODE) ELSE C.M250_PARTCODE END PART_CODE  \n");
		sb.append("             ,? SUNAPGIGWANCODE                                                  \n");
		sb.append("             ,? INTYPE                                                           \n");
		sb.append("             ,? YEARTYPE                                                         \n");

        sb.append("             ,SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                     \n");
		sb.append("                  A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -             \n");
		sb.append("                  A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -               \n");
        sb.append("                  A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                 \n");
        sb.append("                  A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -         \n");
		sb.append("              SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS) TODAY_SUR_AMT  \n");   // �������Ծ�(�׿����Ѿ�-�����Ծ�)

		sb.append("             ,? CNT                                                              \n");
		sb.append("             ,? LOGNO                                                            \n");
		sb.append("             ,? WORKTYPE                                                         \n");
		sb.append("             ,? TRANSGUBUN                                                       \n");
		sb.append("                                                                                 \n");
		sb.append("       FROM   M220_DAY_T A                                                       \n");
		sb.append("             ,M210_WORKEND_T B                                                   \n");
		sb.append("             ,M250_ACCTRANSFER_T C                                               \n");
		sb.append("             ,M370_SEMOKCODE_T E                                                 \n");
    
		sb.append("       WHERE  A.M220_YEAR          = B.M210_YEAR                                 \n");
		sb.append("       AND    A.M220_DATE          = B.M210_DATE                                 \n");
		sb.append("       AND    A.M220_YEAR          = C.M250_YEAR(+)                                 \n");
		sb.append("       AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                              \n");
		sb.append("       AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                              \n");
		//sb.append("       AND    A.M220_PARTCODE      = C.M250_PARTCODE                             \n");  //�ϰ� �μ� ����X (2010-10-12)   
        
        sb.append("       AND    A.M220_YEAR           = E.M370_YEAR                               \n");
		sb.append("       AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                           \n");
		sb.append("       AND    A.M220_ACCCODE        = E.M370_ACCCODE                            \n");
		sb.append("       AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                          \n");
        sb.append("       AND    E.M370_SEGUMGUBUN <> '2'                                          \n");   //  ��������
                sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n");
		sb.append("       AND    A.M220_YEAR          = ?                                           \n");
		sb.append("       AND    A.M220_DATE          = ?                                           \n");
		sb.append("       AND    A.M220_ACCTYPE       = ?                                           \n");
		sb.append("       AND    A.M220_ACCCODE       = ?                                           \n");
		sb.append("       AND    B.M210_WORKENDSTATE  = ?                                           \n");
		sb.append("       AND    NVL(C.M250_ACCTRANSFERYN, 'N') = ?                                           \n");
		sb.append("       GROUP BY A.M220_YEAR                                                      \n");
		sb.append("               ,A.M220_DATE                                                      \n");
		sb.append("               ,A.M220_ACCTYPE                                                   \n");
		sb.append("               ,A.M220_ACCCODE                                                   \n");
		sb.append("               ,C.M250_SEMOKCODE                                                 \n");
		//sb.append("               ,A.M220_PARTCODE                                                  \n");  //�ϰ� �μ� ����X (2010-10-12)  
		sb.append("               ,C.M250_PARTCODE                                                  \n"); 
		sb.append("       ) X                                                                       \n");
        sb.append("WHERE  TODAY_SUR_AMT >= 0                                                         \n");  // �ݾ��� 0 �̻��ΰ͸� ��ϰ��� 


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �������� �Ϳ����� (��Ͻ�)
		parameters.setString(idx++, "110000");                              // �������
		parameters.setString(idx++, "I1");                                  // �Է±���
		parameters.setString(idx++, "Y1");                                  // �⵵����
		parameters.setString(idx++, "1");                                   // �Ǽ�
		parameters.setString(idx++, paramInfo.getString("log_no"));         // �α׹�ȣ
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // ȸ��⵵
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // ��������(��������)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // ȸ�豸��
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "Y");                                   // ������������
		parameters.setString(idx++, "N");                                   // �̿�����

		return template.insert(conn, parameters);
	}


    /* ȸ��⵵�̿� (ȸ�豸��:A,B,E) Table Update ������ ����  - ���� ���� */
	public static List<CommonEntity> getAccTransList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT   D.M010_YEAR         M010_YEAR                                                       \n");   // �̿�ȸ��⵵
		sb.append("        ,C.M250_YEAR         M250_YEAR                                                       \n");   // ����ȸ��⵵(�߰� 2010-10-21)
		sb.append("        ,A.M220_ACCTYPE      M250_ACCTYPE                                                    \n");
		sb.append("        ,A.M220_ACCCODE      M250_ACCCODE                                                    \n"); 
		sb.append("        ,C.M250_PARTCODE     M250_PARTCODE                                                   \n");
		sb.append("        ,C.M250_SEMOKCODE    M250_SEMOKCODE                                                  \n");
		sb.append("        ,'Y'                 M250_ACCTRANSFERYN                                              \n");
        sb.append("        ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                                  \n");
		sb.append("                 A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -                          \n");
		sb.append("                 A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -                            \n");
        sb.append("                 A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                              \n");
        sb.append("                 A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT),0) M250_AMTSURPLUSTOT  \n");   // �׿����Ѿ�
		sb.append("        ,NVL(SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS),0) M250_AMTSURPLUSBEFORE    \n");    //�����Ծ�
		sb.append("       ,NVL(SUM(A.M220_AMTSEIPJEONILTOT + A.M220_AMTSEIP -                                   \n");
		sb.append("                A.M220_AMTSEIPGWAONAP - A.M220_AMTSEIPJEONGJEONG -                           \n");
		sb.append("                A.M220_AMTBAJEONGJEONILTOT - A.M220_AMTBAJEONG -                             \n");
        sb.append("                A.M220_AMTSECHULJEONILTOT - A.M220_AMTSECHUL +                               \n");
        sb.append("                A.M220_AMTSECHULBANNAP + A.M220_AMTSECHULJEONGJEONG + A.M220_AMTBAJEONGSUJEONILTOT) -                       \n");
		sb.append("            SUM(A.M220_AMTSURPLUSJEONILTOT + A.M220_AMTSURPLUS),0)  M250_AMTSURPLUSTODAY     \n");   // �������Ծ�(�׿����Ѿ�-�����Ծ�)
		sb.append("        ,D.M010_SEQ          M250_M010SEQ                                                    \n");
		sb.append("  FROM   M220_DAY_T A                                                                        \n");
		sb.append("        ,M210_WORKEND_T B                                                                    \n");
		sb.append("        ,M250_ACCTRANSFER_T C                                                                \n");
		sb.append("        ,M010_TAXIN_T D                                                                      \n");
		sb.append("        ,M370_SEMOKCODE_T E                                                                  \n");
		sb.append("  WHERE  A.M220_YEAR          = B.M210_YEAR                                                  \n");
		sb.append("  AND    A.M220_DATE          = B.M210_DATE                                                  \n");
		sb.append("  AND    A.M220_YEAR          = C.M250_YEAR(+)                                                  \n");
		sb.append("  AND    A.M220_ACCTYPE       = C.M250_ACCTYPE(+)                                               \n");
		sb.append("  AND    A.M220_ACCCODE       = C.M250_ACCCODE(+)                                               \n");
		sb.append("  AND    A.M220_ACCTYPE       = D.M010_ACCTYPE                                               \n");
		sb.append("  AND    A.M220_ACCCODE       = D.M010_ACCCODE                                               \n");
		sb.append("  AND    C.M250_SEMOKCODE     = D.M010_SEMOKCODE                                             \n");
		sb.append("  AND    C.M250_PARTCODE      = D.M010_PARTCODE                                              \n");
        sb.append("  AND    A.M220_YEAR           = E.M370_YEAR                                                 \n");
		sb.append("  AND    A.M220_ACCTYPE        = E.M370_ACCGUBUN                                             \n");
		sb.append("  AND    A.M220_ACCCODE        = E.M370_ACCCODE                                              \n");
		sb.append("  AND    A.M220_SEMOKCODE      = E.M370_SEMOKCODE                                            \n");
        sb.append("  AND    E.M370_SEGUMGUBUN <> '2'                                                            \n");   //  ��������
        sb.append("AND    E.M370_WORKGUBUN = '0'                                            \n");
		sb.append("  AND    A.M220_YEAR          = ?                                                            \n");   // ȸ��⵵
		sb.append("  AND    A.M220_DATE          = ?                                                            \n");   // ��������
		sb.append("  AND    A.M220_ACCTYPE       = ?                                                            \n");
		sb.append("  AND    A.M220_ACCCODE       = ?                                                            \n");
		sb.append("  AND    B.M210_WORKENDSTATE  = ?                                                            \n");
		sb.append("  AND    C.M250_ACCTRANSFERYN = ?                                                            \n");
		sb.append("  AND    D.M010_YEAR          = ?                                                            \n");   // �̿��⵵
		sb.append("  AND    D.M010_DATE          = ?                                                            \n");   
		sb.append("  AND    D.M010_SUNAPGIGWANCODE = ?                                                          \n");
		sb.append("  AND    D.M010_INTYPE       = ?                                                             \n");
		sb.append("  AND    D.M010_YEARTYPE     = ?                                                             \n");
		sb.append("  AND    D.M010_WORKTYPE     = ?                                                             \n");
		sb.append("  AND    D.M010_TRANSGUBUN   = ?                                                             \n");
		sb.append("  GROUP BY D.M010_YEAR                                                                       \n");
		sb.append("          ,C.M250_YEAR                                                                       \n");
		sb.append("          ,A.M220_ACCTYPE                                                                    \n");
		sb.append("          ,A.M220_ACCCODE                                                                    \n");
		sb.append("          ,C.M250_SEMOKCODE                                                                  \n");
		sb.append("          ,C.M250_PARTCODE                                                                   \n");
		sb.append("          ,D.M010_SEQ                                                                        \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // ȸ��⵵
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // ��������(��������)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // ȸ�豸��
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "Y");                                   // ������������
		parameters.setString(idx++, "N");                                   // �̿�����
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �̿����� (�������� �Ϳ����� (����))
		parameters.setString(idx++, "110000");                              // �������
		parameters.setString(idx++, "I1");                                  // �Է±���
		parameters.setString(idx++, "Y1");                                  // �⵵����
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����

		return template.getList(conn, parameters);
	}


    /* ȸ��⵵�̿� (ȸ�豸��:A,B,D,E) ���� UPDATE */
	public static int setAccTransUpdate(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M250_ACCTRANSFER_T               \n");
		sb.append("SET    M250_SEMOKCODE           = ?     \n");
		sb.append("      ,M250_ACCTRANSFERYN       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTOT       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSBEFORE    = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTODAY     = ?     \n");
		sb.append("      ,M250_M010SEQ             = ?     \n");
		sb.append("WHERE  M250_YEAR                = ?     \n");
		sb.append("AND    M250_ACCTYPE             = ?     \n");
		sb.append("AND    M250_ACCCODE             = ?     \n");
		sb.append("AND    M250_PARTCODE            = ?     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("M250_SEMOKCODE"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCTRANSFERYN"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSTOT"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSBEFORE"));
		parameters.setString(idx++, paramInfo.getString("M250_AMTSURPLUSTODAY"));
		parameters.setString(idx++, paramInfo.getString("M250_M010SEQ"));
		parameters.setString(idx++, paramInfo.getString("M250_YEAR"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCTYPE"));
		parameters.setString(idx++, paramInfo.getString("M250_ACCCODE"));
		parameters.setString(idx++, paramInfo.getString("M250_PARTCODE"));

		return template.update(conn, parameters);
	}


    /* ���Լ�������ݼ����(ȸ�豸��:D) �⵵�̿� INSERT */
	public static int setTaxInCashInsert(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO M040_TAXCASH_T                                           \n");                               
        sb.append("(           M040_SEQ                                                 \n");   
        sb.append("           ,M040_YEAR                                                \n");   
        sb.append("           ,M040_DATE                                                \n");   
        sb.append("           ,M040_ACCCODE                                             \n");   
        sb.append("           ,M040_PARTCODE                                            \n");   
        sb.append("           ,M040_DWTYPE                                              \n");   
        sb.append("           ,M040_INTYPE                                              \n");   
        sb.append("           ,M040_CASHTYPE                                            \n");   
        sb.append("           ,M040_DEPOSITTYPE                                         \n");   
        sb.append("           ,M040_CNT                                                 \n");   
        sb.append("           ,M040_AMT                                                 \n");   
        sb.append("           ,M040_LOGNO                                               \n");   
        sb.append("           ,M040_WORKTYPE                                            \n");   
        sb.append("           ,M040_TRANSGUBUN                                          \n");   
        sb.append(")                                                                    \n");   
        sb.append("SELECT M040_SEQ.NEXTVAL, X.*                                         \n");   
        sb.append("FROM  (                                                              \n");   
        sb.append("        SELECT ?                     M040_YEAR                       \n");   
        sb.append("              ,?                     M040_DATE                       \n");   
        sb.append("              ,A.M270_ACCCODE        M040_ACCCODE                    \n");   
        //sb.append("              ,A.M270_PARTCODE       M040_PARTCODE                   \n");        //�ϰ� �μ� ����X (2010-10-12)     
        sb.append("              ,C.M250_PARTCODE       M040_PARTCODE                   \n");   
        sb.append("              ,?                     M040_DWTYPE                     \n");   
        sb.append("              ,?                     M040_INTYPE                     \n");   
        sb.append("              ,E.CASHTYPE            M040_CASHTYPE                   \n");   
        sb.append("              ,F.DEPOSITTYPE         M040_DEPOSITTYPE                \n");   
        sb.append("              ,?                     M040_CNT                        \n");   
        sb.append("              ,SUM(CASE WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BOJEUNGJEONGGIIBJEON  +           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIIBGEUM  -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIIBJEONG -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIJEON  -           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIGEUB  +           \n");   
        sb.append("                              M270_BOJEUNGJEONGGIJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BOJEUNGBYULDANIBJEON  +           \n");   
        sb.append("                              M270_BOJEUNGBYULDANIBGEUM  -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANIBJEONG -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIJEON  -           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIGEUB  +           \n");   
        sb.append("                              M270_BOJEUNGBYULDANJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C1' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BOJEUNGGONGGEUMIBJEON  +          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMIBGEUM  -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMIBJEONG -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIJEON  -          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIGEUB  +          \n");   
        sb.append("                              M270_BOJEUNGGONGGEUMJIJEONG)           \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BOGWANJEONGGIIBJEON  +            \n");   
        sb.append("                              M270_BOGWANJEONGGIIBGEUM  -            \n");   
        sb.append("                              M270_BOGWANJEONGGIIBJEONG -            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIJEON  -            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIGEUB  +            \n");   
        sb.append("                              M270_BOGWANJEONGGIJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BOGWANBYULDANIBJEON  +            \n");   
        sb.append("                              M270_BOGWANBYULDANIBGEUM  -            \n");   
        sb.append("                              M270_BOGWANBYULDANIBJEONG -            \n");   
        sb.append("                              M270_BOGWANBYULDANJIJEON  -            \n");   
        sb.append("                              M270_BOGWANBYULDANJIGEUB  +            \n");   
        sb.append("                              M270_BOGWANBYULDANJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C2' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BOGWANGONGGEUMIBJEON  +           \n");   
        sb.append("                              M270_BOGWANGONGGEUMIBGEUM  -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMIBJEONG -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIJEON  -           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIGEUB  +           \n");   
        sb.append("                              M270_BOGWANGONGGEUMJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_JABJONGJEONGGIIBJEON  +           \n");   
        sb.append("                              M270_JABJONGJEONGGIIBGEUM  -           \n");   
        sb.append("                              M270_JABJONGJEONGGIIBJEONG -           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIJEON  -           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIGEUB  +           \n");   
        sb.append("                              M270_JABJONGJEONGGIJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_JABJONGBYULDANIBJEON  +           \n");   
        sb.append("                              M270_JABJONGBYULDANIBGEUM  -           \n");   
        sb.append("                              M270_JABJONGBYULDANIBJEONG -           \n");   
        sb.append("                              M270_JABJONGBYULDANJIJEON  -           \n");   
        sb.append("                              M270_JABJONGBYULDANJIGEUB  +           \n");   
        sb.append("                              M270_JABJONGBYULDANJIJEONG)            \n");   
        sb.append("                        WHEN CASHTYPE = 'C3' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_JABJONGGONGGEUMIBJEON  +          \n");   
        sb.append("                              M270_JABJONGGONGGEUMIBGEUM  -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMIBJEONG -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIJEON  -          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIGEUB  +          \n");   
        sb.append("                              M270_JABJONGGONGGEUMJIJEONG)           \n");   
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D1'  \n");   
        sb.append("                        THEN (M270_BUGASEJEONGGIIBJEON  +            \n");   
        sb.append("                              M270_BUGASEJEONGGIIBGEUM  -            \n");   
        sb.append("                              M270_BUGASEJEONGGIIBJEONG -            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIJEON  -            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIGEUB  +            \n");   
        sb.append("                              M270_BUGASEJEONGGIJIJEONG)             \n");   
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D2'  \n");   
        sb.append("                        THEN (M270_BUGASEBYULDANIBJEON  +            \n");   
        sb.append("                              M270_BUGASEBYULDANIBGEUM  -            \n");   
        sb.append("                              M270_BUGASEBYULDANIBJEONG -            \n");   
        sb.append("                              M270_BUGASEBYULDANJIJEON  -            \n");   
        sb.append("                              M270_BUGASEBYULDANJIGEUB  +            \n");   
        sb.append("                              M270_BUGASEBYULDANJIJEONG)             \n");    
        sb.append("                        WHEN CASHTYPE = 'C4' AND DEPOSITTYPE = 'D3'  \n");   
        sb.append("                        THEN (M270_BUGASEGONGGEUMIBJEON  +           \n");   
        sb.append("                              M270_BUGASEGONGGEUMIBGEUM  -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMIBJEONG -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIJEON  -           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIGEUB  +           \n");   
        sb.append("                              M270_BUGASEGONGGEUMJIJEONG)            \n");   
        sb.append("                   END) M040_AMT                                     \n");   
        sb.append("              ,?   M040_LOGNO                                        \n");   
        sb.append("              ,? M040_WORKTYPE                                       \n");   
        sb.append("              ,? M040_TRANSGUBUN                                     \n");   
        sb.append("        FROM   M270_TAXCASHDAY_T A                                   \n");   
        sb.append("              ,M210_WORKEND_T B                                      \n");   
        sb.append("              ,M250_ACCTRANSFER_T C                                  \n");   
        sb.append("              ,M360_ACCCODE_T D                                      \n");   
        sb.append("              ,(SELECT 'C'||LEVEL CASHTYPE                           \n");   
        sb.append("                  FROM DUAL                                          \n");   
        sb.append("                 CONNECT BY LEVEL <= 4) E                            \n");   
        sb.append("              ,(SELECT 'D'||LEVEL DEPOSITTYPE                        \n");   
        sb.append("                  FROM DUAL                                          \n");   
        sb.append("                 CONNECT BY LEVEL <= 3) F                            \n");   
        sb.append("        WHERE  A.M270_YEAR          = B.M210_YEAR                    \n");   
        sb.append("        AND    A.M270_DATE          = B.M210_DATE                    \n");   
        sb.append("        AND    A.M270_YEAR          = C.M250_YEAR                    \n");   
        sb.append("        AND    A.M270_ACCCODE       = C.M250_ACCCODE                 \n");   
        //sb.append("        AND    A.M270_PARTCODE      = C.M250_PARTCODE                \n");         //�ϰ� �μ� ����X (2010-10-12)      
        sb.append("        AND    A.M270_YEAR          = D.M360_YEAR                    \n");   
        sb.append("        AND    A.M270_ACCCODE       = D.M360_ACCCODE                 \n");   
        sb.append("        AND    C.M250_ACCTYPE       = D.M360_ACCGUBUN                \n");   
        sb.append("        AND    A.M270_YEAR          = ?                              \n");   
        sb.append("        AND    A.M270_DATE          = ?                              \n");   
        sb.append("        AND    C.M250_ACCTYPE       = ?                              \n");   
        sb.append("        AND    A.M270_ACCCODE       = ?                              \n");   
		sb.append("        AND    B.M210_WORKENDSTATE  = ?                              \n");
		sb.append("        AND    C.M250_ACCTRANSFERYN = ?                              \n");

        sb.append("        GROUP BY A.M270_YEAR                                         \n");   
        sb.append("                ,A.M270_DATE                                         \n");   
        sb.append("                ,A.M270_ACCCODE                                      \n");   
        //sb.append("                ,A.M270_PARTCODE                                     \n");     //�ϰ� �μ� ����X (2010-10-12)      
        sb.append("                ,C.M250_PARTCODE                                     \n");   
        sb.append("                ,E.CASHTYPE                                          \n");   
        sb.append("                ,F.DEPOSITTYPE                                       \n");   
        sb.append("     ) X                                                             \n");   
        sb.append("WHERE  M040_AMT >= 0                                                 \n");  // �ݾ��� 0 �̻��ΰ͸� ��ϰ��� 


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �������� �Ϳ����� (��Ͻ�)
		parameters.setString(idx++, "G1");                                  // �Է����ޱ���
		parameters.setString(idx++, "I1");                                  // �Է±���
		parameters.setString(idx++, "1");                                   // �Ǽ�
		parameters.setString(idx++, paramInfo.getString("log_no"));         // �α׹�ȣ
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����

		parameters.setString(idx++, paramInfo.getString("acc_year"));       // ȸ��⵵
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // ��������(��������)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // ȸ�豸��
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "Y");                                   // ������������
		parameters.setString(idx++, "N");                                   // �̿�����

		return template.insert(conn, parameters);
	}


    /* ���Լ�������ݼ����(ȸ�豸��:D) ȸ��⵵�̿� Table Update ������ ���� */
	public static List<CommonEntity> getAccTransCashList(Connection conn, CommonEntity paramInfo)throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT D.M040_YEAR          M040_YEAR                           \n");    // �̿�ȸ��⵵ 
		sb.append("      ,C.M250_YEAR          M250_YEAR                           \n");    // ����ȸ��⵵(�߰� 2010-10-21)
		sb.append("      ,C.M250_ACCTYPE       M250_ACCTYPE                        \n");
		sb.append("      ,A.M270_ACCCODE       M250_ACCCODE                        \n");
		//sb.append("      ,A.M270_PARTCODE      M250_PARTCODE                       \n");      //�ϰ� �μ� ����X (2010-10-12)      
		sb.append("      ,C.M250_PARTCODE      M250_PARTCODE                       \n");
		sb.append("      ,C.M250_SEMOKCODE     M250_SEMOKCODE                      \n");
		sb.append("      ,'Y'                  M250_ACCTRANSFERYN                  \n");
		sb.append("      ,(SUM(M270_BOJEUNGJEONGGIIBJEON  +                        \n");
		sb.append("            M270_BOJEUNGJEONGGIIBGEUM  -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIIBJEONG -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIJEON  -                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIGEUB  +                        \n");
		sb.append("            M270_BOJEUNGJEONGGIJIJEONG)                         \n");
		sb.append("       +SUM(M270_BOJEUNGBYULDANIBJEON  +                        \n");
		sb.append("            M270_BOJEUNGBYULDANIBGEUM  -                        \n");
		sb.append("            M270_BOJEUNGBYULDANIBJEONG -                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEON  -                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIGEUB  +                        \n");
		sb.append("            M270_BOJEUNGBYULDANJIJEONG)                         \n");
		sb.append("       +SUM(M270_BOJEUNGGONGGEUMIBJEON  +                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBGEUM  -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMIBJEONG -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEON  -                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIGEUB  +                       \n");
		sb.append("            M270_BOJEUNGGONGGEUMJIJEONG)                        \n");
		sb.append("       +SUM(M270_BOGWANJEONGGIIBJEON  +                         \n");
		sb.append("            M270_BOGWANJEONGGIIBGEUM  -                         \n");
		sb.append("            M270_BOGWANJEONGGIIBJEONG -                         \n");
		sb.append("            M270_BOGWANJEONGGIJIJEON  -                         \n");
		sb.append("            M270_BOGWANJEONGGIJIGEUB  +                         \n");
		sb.append("            M270_BOGWANJEONGGIJIJEONG)                          \n");
		sb.append("       +SUM(M270_BOGWANBYULDANIBJEON  +                         \n");
		sb.append("            M270_BOGWANBYULDANIBGEUM  -                         \n");
		sb.append("            M270_BOGWANBYULDANIBJEONG -                         \n");
		sb.append("            M270_BOGWANBYULDANJIJEON  -                         \n");
		sb.append("            M270_BOGWANBYULDANJIGEUB  +                         \n");
		sb.append("            M270_BOGWANBYULDANJIJEONG)                          \n");
		sb.append("       +SUM(M270_BOGWANGONGGEUMIBJEON  +                        \n");
		sb.append("            M270_BOGWANGONGGEUMIBGEUM  -                        \n");
		sb.append("            M270_BOGWANGONGGEUMIBJEONG -                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEON  -                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIGEUB  +                        \n");
		sb.append("            M270_BOGWANGONGGEUMJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGJEONGGIIBJEON  +                        \n");
		sb.append("            M270_JABJONGJEONGGIIBGEUM  -                        \n");
		sb.append("            M270_JABJONGJEONGGIIBJEONG -                        \n");
		sb.append("            M270_JABJONGJEONGGIJIJEON  -                        \n");
		sb.append("            M270_JABJONGJEONGGIJIGEUB  +                        \n");
		sb.append("            M270_JABJONGJEONGGIJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGBYULDANIBJEON  +                        \n");
		sb.append("            M270_JABJONGBYULDANIBGEUM  -                        \n");
		sb.append("            M270_JABJONGBYULDANIBJEONG -                        \n");
		sb.append("            M270_JABJONGBYULDANJIJEON  -                        \n");
		sb.append("            M270_JABJONGBYULDANJIGEUB  +                        \n");
		sb.append("            M270_JABJONGBYULDANJIJEONG)                         \n");
		sb.append("       +SUM(M270_JABJONGGONGGEUMIBJEON  +                       \n");
		sb.append("            M270_JABJONGGONGGEUMIBGEUM  -                       \n");
		sb.append("            M270_JABJONGGONGGEUMIBJEONG -                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEON  -                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIGEUB  +                       \n");
		sb.append("            M270_JABJONGGONGGEUMJIJEONG)                        \n");
		sb.append("       +SUM(M270_BUGASEJEONGGIIBJEON  +                         \n");
		sb.append("            M270_BUGASEJEONGGIIBGEUM  -                         \n");
		sb.append("            M270_BUGASEJEONGGIIBJEONG -                         \n");
		sb.append("            M270_BUGASEJEONGGIJIJEON  -                         \n");
		sb.append("            M270_BUGASEJEONGGIJIGEUB  +                         \n");
		sb.append("            M270_BUGASEJEONGGIJIJEONG)                          \n");
		sb.append("       +SUM(M270_BUGASEBYULDANIBJEON  +                         \n");
		sb.append("            M270_BUGASEBYULDANIBGEUM  -                         \n");
		sb.append("            M270_BUGASEBYULDANIBJEONG -                         \n");
		sb.append("            M270_BUGASEBYULDANJIJEON  -                         \n");
		sb.append("            M270_BUGASEBYULDANJIGEUB  +                         \n");
		sb.append("            M270_BUGASEBYULDANJIJEONG)                          \n");
		sb.append("       +SUM(M270_BUGASEGONGGEUMIBJEON  +                        \n");
		sb.append("            M270_BUGASEGONGGEUMIBGEUM  -                        \n");
		sb.append("            M270_BUGASEGONGGEUMIBJEONG -                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEON  -                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIGEUB  +                        \n");
		sb.append("            M270_BUGASEGONGGEUMJIJEONG)) M250_AMTSURPLUSTOT     \n");
		sb.append("      ,0 M250_AMTSURPLUSBEFORE                                  \n");
		sb.append("      ,0 M250_AMTSURPLUSTODAY                                   \n");
		sb.append("      ,MAX(D.M040_SEQ)   M250_M010SEQ                           \n");
		sb.append("FROM   M270_TAXCASHDAY_T A                                      \n");
		sb.append("      ,M210_WORKEND_T B                                         \n");
		sb.append("      ,M250_ACCTRANSFER_T C                                     \n");
		sb.append("      ,M040_TAXCASH_T D                                         \n");
		sb.append("WHERE  A.M270_YEAR           = B.M210_YEAR                      \n");
		sb.append("AND    A.M270_DATE           = B.M210_DATE                      \n");
		sb.append("AND    A.M270_YEAR           = C.M250_YEAR                      \n");
		sb.append("AND    A.M270_ACCCODE        = C.M250_ACCCODE                   \n");
		//sb.append("AND    A.M270_PARTCODE       = C.M250_PARTCODE                  \n");      //�ϰ� �μ� ����X (2010-10-12)      
		//sb.append("AND    A.M270_PARTCODE       = D.M040_PARTCODE                  \n");      //�ϰ� �μ� ����X (2010-10-12)
		sb.append("AND    C.M250_PARTCODE       = D.M040_PARTCODE                  \n");      
		sb.append("AND    C.M250_ACCCODE        = D.M040_ACCCODE                    \n");
		sb.append("AND    A.M270_YEAR           = ?                                \n");
		sb.append("AND    A.M270_DATE           = ?                                \n");
		sb.append("AND    C.M250_ACCTYPE        = ?                                \n");
		sb.append("AND    A.M270_ACCCODE        = ?                                \n");
		sb.append("AND    B.M210_WORKENDSTATE   = ?                                \n");
		sb.append("AND    C.M250_ACCTRANSFERYN  = ?                                \n");
		sb.append("AND    D.M040_YEAR           = ?                                \n");
		sb.append("AND    D.M040_DATE           = ?                                \n");
		sb.append("AND    D.M040_DWTYPE         = ?                                \n");
		sb.append("AND    D.M040_INTYPE         = ?                                \n");
		sb.append("AND    D.M040_WORKTYPE       = ?                                \n");
		sb.append("AND    D.M040_TRANSGUBUN     = ?                                \n");
		sb.append("GROUP BY D.M040_YEAR                                            \n");
		sb.append("        ,C.M250_YEAR                                            \n");
		sb.append("        ,C.M250_ACCTYPE                                         \n");
		sb.append("        ,A.M270_ACCCODE                                         \n");
		//sb.append("        ,A.M270_PARTCODE                                        \n");      //�ϰ� �μ� ����X (2010-10-12)
		sb.append("        ,C.M250_PARTCODE                                        \n");
		sb.append("        ,C.M250_SEMOKCODE                                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));       // ȸ��⵵
		parameters.setString(idx++, paramInfo.getString("acc_date"));       // ��������(��������)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // ȸ�豸��
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "Y");                                   // ������������
		parameters.setString(idx++, "N");                                   // �̿�����
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �̿�����(�������� �Ϳ����� (����))
		parameters.setString(idx++, "G1");                                  // �Է±���
		parameters.setString(idx++, "I1");                                  // �⵵����
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����

		return template.getList(conn, parameters);
	}


    /* ���Լ���� (ȸ�豸��:A,B,E)  Delete */
	public static int setTaxInDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE                               \n");
		sb.append("FROM   M010_TAXIN_T                  \n");
		sb.append("WHERE  M010_YEAR             = ?     \n");
		sb.append("AND    M010_DATE             = ?     \n");
		sb.append("AND    M010_ACCTYPE          = ?     \n");
		sb.append("AND    M010_ACCCODE          = ?     \n");
		sb.append("AND    M010_SUNAPGIGWANCODE  = ?     \n");
		sb.append("AND    M010_INTYPE           = ?     \n");
		sb.append("AND    M010_YEARTYPE         = ?     \n");
		sb.append("AND    M010_WORKTYPE         = ?     \n");
		sb.append("AND    M010_TRANSGUBUN       = ?     \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �������� �Ϳ����� (��Ͻ�)
		parameters.setString(idx++, paramInfo.getString("acc_type"));       // ȸ�豸��
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "110000");                              // �������
		parameters.setString(idx++, "I1");                                  // �Է±���
		parameters.setString(idx++, "Y1");                                  // �⵵����
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����

		return template.delete(conn, parameters);
	}

    
    /* ���Լ�������ݼ����(ȸ�豸��:D)  Delete */
	public static int setTaxInCashDelete(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE                           \n");
		sb.append("FROM   M040_TAXCASH_T            \n");
		sb.append("WHERE  M040_YEAR         = ?     \n");
		sb.append("AND    M040_DATE         = ?     \n");
		sb.append("AND    M040_ACCCODE      = ?     \n");
		sb.append("AND    M040_DWTYPE       = ?     \n");
		sb.append("AND    M040_INTYPE       = ?     \n");
		sb.append("AND    M040_WORKTYPE     = ?     \n");
		sb.append("AND    M040_TRANSGUBUN   = ?     \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("next_year"));      // �̿��⵵
		parameters.setString(idx++, paramInfo.getString("reg_acc_date"));   // �������� �Ϳ����� (��Ͻ�)
		parameters.setString(idx++, paramInfo.getString("acc_code"));       // ȸ���ڵ�
		parameters.setString(idx++, "G1");                                  // �Ա����ޱ���
		parameters.setString(idx++, "I1");                                  // �Է±���
		parameters.setString(idx++, paramInfo.getString("work_log"));       // ��������
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));    // �ŷ�����

		return template.delete(conn, parameters);
	}

    /* ȸ��⵵�̿� (ȸ�豸��:A,B,D,E) ���� �̿� ��ҽ� RESET UPDATE */
	public static int setAccTransUpdateReset(Connection conn, CommonEntity paramInfo )throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M250_ACCTRANSFER_T               \n"); 
		sb.append("SET    M250_ACCTRANSFERYN       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTOT       = ?     \n");
		sb.append("      ,M250_AMTSURPLUSBEFORE    = ?     \n");
		sb.append("      ,M250_AMTSURPLUSTODAY     = ?     \n");
		sb.append("      ,M250_M010SEQ             = ?     \n");
		sb.append("WHERE  M250_YEAR                = ?     \n");
		sb.append("AND    M250_ACCTYPE             = ?     \n");
		sb.append("AND    M250_ACCCODE             = ?     \n");
		//sb.append("AND    M250_PARTCODE            = ?     \n");  (����: ȸ��⵵, ȸ�豸��, ȸ���ڵ�)

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, "N");        
		parameters.setInt(idx++, 0);      
		parameters.setInt(idx++, 0);   
		parameters.setInt(idx++, 0);      
		parameters.setString(idx++, "");      
		parameters.setString(idx++, paramInfo.getString("acc_year"));      
		parameters.setString(idx++, paramInfo.getString("acc_type"));      
		parameters.setString(idx++, paramInfo.getString("acc_code"));       

		return template.update(conn, parameters);
	}
}


