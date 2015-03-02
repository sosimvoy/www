/***********************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR070410.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-08-16
* ���α׷�����	   : �ϰ�/���� > ���Ϻ����
***********************************************************/
package com.etax.db.mn07;

import java.sql.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR070410 {

    /* �������� ��ȸ */ 
	public static CommonEntity getAgoDate(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  COUNT(*) CNT        \n");
		sb.append("   FROM  M470_DAYBIGO_T      \n");   
        sb.append("  WHERE  M470_YEAR = ?       \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }
  
	/* ���Ϻ�� ��ȸ */ 
	public static CommonEntity getDateNote(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT  *					\n");
		sb.append("   FROM  M470_DAYBIGO_T 		\n");   
		sb.append("  WHERE  M470_DATE = ?		\n");
        sb.append("    AND  M470_YEAR = ?       \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }


    /* �� ������ ��� ��ȸ */ 
	public static CommonEntity getAgoNote1(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT ? AS M470_YEAR                                \n");
        sb.append("      , ? AS M470_DATE                                \n");
        sb.append("      , 0 AS M470_CAR_GC01                            \n");
        sb.append("      , 0 AS M470_CAR_ICHE_GC01                       \n");
        sb.append("      , 0 AS M470_CAR_SUM_GC01                        \n");
        sb.append("      , ? AS M470_CAR_AGODATE_GC01                    \n");
        sb.append("      , 0 AS M470_CAR_GC02                            \n");
        sb.append("      , 0 AS M470_CAR_ICHE_GC02                       \n");
        sb.append("      , 0 AS M470_CAR_SUM_GC02                        \n");
        sb.append("      , ? AS M470_CAR_AGODATE_GC02                    \n");
        sb.append("      , 0 AS M470_NONG_GC01                           \n");
        sb.append("      , 0 AS M470_NONG_ICHE_GC01                      \n");
        sb.append("      , 0 AS M470_NONG_SUM_GC01                       \n");
        sb.append("      , ? AS M470_NONG_AGODATE_GC01                   \n");
        sb.append("      , 0 AS M470_NONG_GC02                           \n");
        sb.append("      , 0 AS M470_NONG_ICHE_GC02                      \n");
        sb.append("      , 0 AS M470_NONG_SUM_GC02                       \n");
        sb.append("      , ? AS M470_NONG_AGODATE_GC02                   \n");
        sb.append("      , 0 AS M470_BAL_GP01                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GP01                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GP01                        \n");
        sb.append("      , GET_NEXT_BUSINESSDAY(?) M470_BAL_AGODATE_GP01 \n");
        sb.append("      , 0 AS M470_BAL_GC01                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GC01                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GC01                        \n");
        sb.append("      , ? AS M470_BAL_AGODATE_GC01                    \n");
        sb.append("      , 0 AS M470_BAL_GC02                            \n");
        sb.append("      , 0 AS M470_BAL_ICHE_GC02                       \n");
        sb.append("      , 0 AS M470_BAL_SUM_GC02                        \n");
        sb.append("      , ? AS M470_BAL_AGODATE_GC02                    \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_CD_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_CD_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_NT_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_NT_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_CARD_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_CARD_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_SUNAP                 \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_ICHE                  \n");
        sb.append("      , 0 AS M470_BS_GUM_NOCARD_SUM                   \n");
        sb.append("      , ? AS M470_BS_GUM_NOCARD_DATE                  \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_OARS_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_OARS_DATE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_ICHE                    \n");
        sb.append("      , 0 AS M470_BS_GUM_NARS_SUM                     \n");
        sb.append("      , ? AS M470_BS_GUM_NARS_DATE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_OCARD_SUNAP                  \n"); //���ܼ��� ī��
        sb.append("      , 0 AS M470_SS_GUM_OCARD_ICHE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_OCARD_SUM                    \n");
        sb.append("      , ? AS M470_SS_GUM_OCARD_DATE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_SUNAP                  \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_ICHE                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NCARD_SUM                    \n");
        sb.append("      , ? AS M470_SS_GUM_NCARD_DATE                   \n");        
        sb.append("      , 0 AS M470_SS_GUM_OARS_SUNAP                   \n"); //���ܼ��� ARS
        sb.append("      , 0 AS M470_SS_GUM_OARS_ICHE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_OARS_SUM                     \n");
        sb.append("      , ? AS M470_SS_GUM_OARS_DATE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_SUNAP                   \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_ICHE                    \n");
        sb.append("      , 0 AS M470_SS_GUM_NARS_SUM                     \n");
        sb.append("      , ? AS M470_SS_GUM_NARS_DATE                    \n");
        sb.append("   FROM DUAL                                          \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));//���ܼ���
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        
        return template.getData(conn, parameters);
    }


    /* �� ������ ��� ��ȸ */ 
	public static CommonEntity getAgoNote(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
 		
		sb.append(" SELECT M470_YEAR                                        \n");
        sb.append("      , M470_DATE                                        \n");
        sb.append("      , M470_CAR_GC01                                    \n");
        sb.append("      , M470_CAR_ICHE_GC01                               \n");
        sb.append("      , M470_CAR_SUM_GC01                                \n");
        sb.append("      , M470_CAR_AGODATE_GC01                            \n");
        sb.append("      , M470_CAR_GC02                                    \n");
        sb.append("      , M470_CAR_ICHE_GC02                               \n");
        sb.append("      , M470_CAR_SUM_GC02                                \n");
        sb.append("      , M470_CAR_AGODATE_GC02                            \n");
        sb.append("      , M470_NONG_GC01                                   \n");
        sb.append("      , M470_NONG_ICHE_GC01                              \n");
        sb.append("      , M470_NONG_SUM_GC01                               \n");
        sb.append("      , M470_NONG_AGODATE_GC01                           \n");
        sb.append("      , M470_NONG_GC02                                   \n");
        sb.append("      , M470_NONG_ICHE_GC02                              \n");
        sb.append("      , M470_NONG_SUM_GC02                               \n");
        sb.append("      , M470_NONG_AGODATE_GC02                           \n");
        sb.append("      , M470_BAL_GP01                                    \n");
        sb.append("      , M470_BAL_ICHE_GP01                               \n");
        sb.append("      , M470_BAL_SUM_GP01                                \n");
        sb.append("      , GET_NEXT_BUSINESSDAY(?) M470_BAL_AGODATE_GP01    \n");
        sb.append("      , M470_BAL_GC01                                    \n");
        sb.append("      , M470_BAL_ICHE_GC01                               \n");
        sb.append("      , M470_BAL_SUM_GC01                                \n");
        sb.append("      , M470_BAL_AGODATE_GC01                            \n");
        sb.append("      , M470_BAL_GC02                                    \n");
        sb.append("      , M470_BAL_ICHE_GC02                               \n");
        sb.append("      , M470_BAL_SUM_GC02                                \n");
        sb.append("      , M470_BAL_AGODATE_GC02                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_CD_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUM                             \n");
        sb.append("      , M470_CD_GUM_CARD_DATE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_NT_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUM                             \n");
        sb.append("      , M470_NT_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUM                             \n");
        sb.append("      , M470_BS_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUNAP                         \n");
        sb.append("      , M470_BS_GUM_NOCARD_ICHE                          \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUM                           \n");
        sb.append("      , M470_BS_GUM_NOCARD_DATE                          \n");
        sb.append("      , M470_BS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_NARS_DATE                            \n");        
        sb.append("      , M470_SS_GUM_OCARD_SUNAP                  		\n"); //���ܼ��� ī��
        sb.append("      , M470_SS_GUM_OCARD_ICHE                   		\n");
        sb.append("      , M470_SS_GUM_OCARD_SUM                    		\n");
        sb.append("      , M470_SS_GUM_OCARD_DATE                   		\n");
        sb.append("      , M470_SS_GUM_NCARD_SUNAP                  		\n");
        sb.append("      , M470_SS_GUM_NCARD_ICHE                   		\n");
        sb.append("      , M470_SS_GUM_NCARD_SUM                    		\n");
        sb.append("      , M470_SS_GUM_NCARD_DATE                   		\n");        
        sb.append("      , M470_SS_GUM_OARS_SUNAP                   		\n"); //���ܼ��� ARS
        sb.append("      , M470_SS_GUM_OARS_ICHE                    		\n");
        sb.append("      , M470_SS_GUM_OARS_SUM                     		\n");
        sb.append("      , M470_SS_GUM_OARS_DATE                    		\n");
        sb.append("      , M470_SS_GUM_NARS_SUNAP                   		\n");
        sb.append("      , M470_SS_GUM_NARS_ICHE                    		\n");
        sb.append("      , M470_SS_GUM_NARS_SUM                     		\n");
        sb.append("      , M470_SS_GUM_NARS_DATE                    		\n");
        sb.append("   FROM M470_DAYBIGO_T                                   \n");
        sb.append("  WHERE M470_DATE = GET_AGO_BUSINESSDAY(?)               \n");
        sb.append("    AND M470_YEAR = ?                                    \n");
        
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("acc_date"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        
        return template.getData(conn, parameters);
    }

	/* ���Ϻ�� ���  */
    public static int insertNote(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		
		sb.append(" INSERT INTO M470_DAYBIGO_T                              \n");
        sb.append("      ( M470_YEAR                                        \n");
        sb.append("      , M470_DATE                                        \n");
        sb.append("      , M470_CAR_GC01                                    \n");
        sb.append("      , M470_CAR_ICHE_GC01                               \n");
        sb.append("      , M470_CAR_SUM_GC01                                \n");
        sb.append("      , M470_CAR_AGODATE_GC01                            \n");
        sb.append("      , M470_CAR_GC02                                    \n");
        sb.append("      , M470_CAR_ICHE_GC02                               \n");
        sb.append("      , M470_CAR_SUM_GC02                                \n");
        sb.append("      , M470_CAR_AGODATE_GC02                            \n");
        sb.append("      , M470_NONG_GC01                                   \n");
        sb.append("      , M470_NONG_ICHE_GC01                              \n");
        sb.append("      , M470_NONG_SUM_GC01                               \n");
        sb.append("      , M470_NONG_AGODATE_GC01                           \n");
        sb.append("      , M470_NONG_GC02                                   \n");
        sb.append("      , M470_NONG_ICHE_GC02                              \n");
        sb.append("      , M470_NONG_SUM_GC02                               \n");
        sb.append("      , M470_NONG_AGODATE_GC02                           \n");
        sb.append("      , M470_BAL_GP01                                    \n");
        sb.append("      , M470_BAL_ICHE_GP01                               \n");
        sb.append("      , M470_BAL_SUM_GP01                                \n");
        sb.append("      , M470_BAL_AGODATE_GP01                            \n");
        sb.append("      , M470_BAL_GC01                                    \n");
        sb.append("      , M470_BAL_ICHE_GC01                               \n");
        sb.append("      , M470_BAL_SUM_GC01                                \n");
        sb.append("      , M470_BAL_AGODATE_GC01                            \n");
        sb.append("      , M470_BAL_GC02                                    \n");
        sb.append("      , M470_BAL_ICHE_GC02                               \n");
        sb.append("      , M470_BAL_SUM_GC02                                \n");
        sb.append("      , M470_BAL_AGODATE_GC02                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_CD_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_CD_GUM_CARD_SUM                             \n");
        sb.append("      , M470_CD_GUM_CARD_DATE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_NT_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_NT_GUM_CARD_SUM                             \n");
        sb.append("      , M470_NT_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_CARD_ICHE                            \n");
        sb.append("      , M470_BS_GUM_CARD_SUM                             \n");
        sb.append("      , M470_BS_GUM_CARD_DATE                            \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUNAP                         \n");
        sb.append("      , M470_BS_GUM_NOCARD_ICHE                          \n");
        sb.append("      , M470_BS_GUM_NOCARD_SUM                           \n");
        sb.append("      , M470_BS_GUM_NOCARD_DATE                          \n");
        sb.append("      , M470_BS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_BS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_BS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_BS_GUM_NARS_DATE                            \n");
        sb.append("      , M470_SS_GUM_OCARD_SUNAP                          \n"); //���ܼ���
        sb.append("      , M470_SS_GUM_OCARD_ICHE                           \n");
        sb.append("      , M470_SS_GUM_OCARD_SUM                            \n");
        sb.append("      , M470_SS_GUM_OCARD_DATE                           \n");
        sb.append("      , M470_SS_GUM_NCARD_SUNAP                          \n");
        sb.append("      , M470_SS_GUM_NCARD_ICHE                           \n");
        sb.append("      , M470_SS_GUM_NCARD_SUM                            \n");
        sb.append("      , M470_SS_GUM_NCARD_DATE                           \n");
        sb.append("      , M470_SS_GUM_OARS_SUNAP                           \n");
        sb.append("      , M470_SS_GUM_OARS_ICHE                            \n");
        sb.append("      , M470_SS_GUM_OARS_SUM                             \n");
        sb.append("      , M470_SS_GUM_OARS_DATE                            \n");
        sb.append("      , M470_SS_GUM_NARS_SUNAP                           \n");
        sb.append("      , M470_SS_GUM_NARS_ICHE                            \n");
        sb.append("      , M470_SS_GUM_NARS_SUM                             \n");
        sb.append("      , M470_SS_GUM_NARS_DATE )                          \n");
        sb.append(" VALUES                                                  \n");
        sb.append("      ( ?                                                \n");  //ȸ�迬��
        sb.append("      , ?                                                \n");  //ȸ������
        sb.append("      , ?                                                \n");  //������漼 ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //������漼 ī�� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //������漼 �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //������漼 ī�� ��ü������
        sb.append("      , ?                                                \n");  //������漼 ī��� ���ϼ�����
        sb.append("      , ?                                                \n");  //������漼 ī��� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //������漼 �ݰ�� �� ī�� �հ�
        sb.append("      , ?                                                \n");  //������漼 ī��� ��ü������
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //������Ư�� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ��ü������
        sb.append("      , ?                                                \n");  //������Ư�� ī��� ���ϼ�����
        sb.append("      , ?                                                \n");  //������Ư�� ī��� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //������Ư�� �ݰ�� �� ī�� �հ�
        sb.append("      , ?                                                \n");  //������Ư�� ī��� ��ü������
        sb.append("      , ?                                                \n");  //����û����ü� �Ϲ� ���ϼ�����
        sb.append("      , ?                                                \n");  //����û����ü� �Ϲ� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //����û����ü� �ݰ�� �Ϲ� �հ�
        sb.append("      , ?                                                \n");  //����û����ü� �Ϲ� ��ü������
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //����û����ü� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ��ü������
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ���ϼ�����
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ������ü��
        sb.append("      , ? + ? - ? -- ���ϴ���+����-��ü                  \n");  //����û����ü� �ݰ�� �� ī�� �հ�
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ��ü������
        sb.append("      , ?                                                \n");  //������漼 ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //������漼 ī�� ������ü��
        sb.append("      , ? + ? - ?                                        \n");  //������漼 �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //������漼 ī�� ��ü������
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ���ϼ����� 
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ������ü�� 
        sb.append("      , ? + ? - ?                                        \n");  //������Ư�� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //������Ư�� ī�� ��ü������
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ������ü�� 
        sb.append("      , ? + ? - ?                                        \n");  //����û����ü� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //����û����ü� ī�� ��ü������
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ���ϼ�����  
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ������ü��  
        sb.append("      , ? + ? - ?                                        \n");  //����û����ü� �ݰ�� �� ī�� �հ�
        sb.append("      , ?                                                \n");  //����û����ü� ī��� ��ü������  
        sb.append("      , ?                                                \n");  //����û����ü� OARS ���ϼ�����  
        sb.append("      , ?                                                \n");  //����û����ü� OARS ������ü��  
        sb.append("      , ? + ? - ?                                        \n");  //����û����ü� OARS �հ�
        sb.append("      , ?                                                \n");  //����û����ü� OARS ��ü������  
        sb.append("      , ?                                                \n");  //����û����ü� NARS ���ϼ�����  
        sb.append("      , ?                                                \n");  //����û����ü� NARS ������ü��  
        sb.append("      , ? + ? - ?                                        \n");  //����û����ü� NARS �հ�
        sb.append("      , ?                                                \n");  //����û����ü� NARS ��ü������        
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ������ü�� 
        sb.append("      , ? + ? - ?                                        \n");  //���ܼ��� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ��ü������
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ���ϼ�����
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ������ü�� 
        sb.append("      , ? + ? - ?                                        \n");  //���ܼ��� �ݰ�� ī�� �հ�
        sb.append("      , ?                                                \n");  //���ܼ��� ī�� ��ü������
        sb.append("      , ?                                                \n");  //���ܼ��� OARS ���ϼ�����  
        sb.append("      , ?                                                \n");  //���ܼ��� OARS ������ü��  
        sb.append("      , ? + ? - ?                                        \n");  //���ܼ��� OARS �հ�
        sb.append("      , ?                                                \n");  //���ܼ��� OARS ��ü������  
        sb.append("      , ?                                                \n");  //���ܼ��� NARS ���ϼ�����  
        sb.append("      , ?                                                \n");  //���ܼ��� NARS ������ü��  
        sb.append("      , ? + ? - ?                                        \n");  //���ܼ��� NARS �հ�
        sb.append("      , ? )                                              \n");  //���ܼ��� NARS ��ü������  
		


		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acc_date"));

		parameters.setString(idx++, paramInfo.getString("M470_CAR_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_CAR_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGODATE_GC01"));

		parameters.setString(idx++, paramInfo.getString("M470_CAR_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_CAR_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_CAR_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGODATE_GC01"));

        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_NONG_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_NONG_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GP01"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GP01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GP01"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GC01"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC01"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GC01"));

        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGO_GC02"));
        parameters.setString(idx++, paramInfo.getString("M470_BAL_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_ICHE_GC02"));
		parameters.setString(idx++, paramInfo.getString("M470_BAL_AGODATE_GC02"));

        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_CD_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_NT_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_CARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NOCARD_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_OARS_DATE"));

        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_BS_GUM_NARS_DATE"));
        
        //���ܼ��� ī��
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OCARD_DATE"));
        //���ܼ��� ī��
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NCARD_DATE"));
        //���ܼ��� ars
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_OARS_DATE"));
        //���ܼ��� ars
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUM"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_SUNAP"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_ICHE"));
        parameters.setString(idx++, paramInfo.getString("M470_SS_GUM_NARS_DATE"));
	
		return template.insert(conn, parameters);
	}

	/* ���Ϻ�� ���� */
	public static int deleteNote(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("DELETE FROM M470_DAYBIGO_T      \n");
		sb.append(" WHERE M470_DATE = ?            \n");
        sb.append("   AND M470_YEAR = ?            \n");
		
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		
		return template.delete(conn, parameters);
	}

}