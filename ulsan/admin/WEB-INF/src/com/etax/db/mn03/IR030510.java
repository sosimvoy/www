/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR030510.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���Լ�������� > ���༼ ��ȸ/����/����
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030510 {

	/* ����� ��ȸ */
	public static List<CommonEntity> getJuheangList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT  M060_YEAR,                                                      \n");	 
		sb.append("	        M060_DATE,                                                      \n"); 
		sb.append("	        M060_SEQ,                                                       \n");
		sb.append("	        M060_CASHTYPE,                                                  \n"); 
		sb.append("        DECODE(M060_JINGSUTYPE, 'J1','�ֵ�Ư��¡��',                     \n");
		sb.append("                                'J2','Ư��¡��') M060_JINGSUNAME,        \n");
		sb.append("        DECODE(M060_REPAYTYPE,  'R1','����',                             \n");
		sb.append("                                'R2','����') M060_REPAYNAME,             \n");
        sb.append("        DECODE(M060_CASHTYPE,  'A1','���Ծ�',                            \n");
		sb.append("                               'A2','������',                            \n");
        sb.append("                               'A3','���޾�',                            \n");
        sb.append("                               'A4','�ݳ���') M060_CASHNAME,             \n");
		sb.append("				 CASE WHEN M060_CASHTYPE = 'A1'                             \n");
		sb.append("							THEN M060_AMT                                   \n");
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END SUIP_AMT                                               \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A2'                             \n");
		sb.append("				      THEN M060_AMT                                         \n");
		sb.append("				      ELSE 0                                                \n");
		sb.append("				 END GWAO_AMT                                               \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A3'                             \n");
		sb.append("							THEN M060_AMT                                   \n");
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END JIGUP_AMT                                              \n"); 
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A4'                             \n");
		sb.append("							THEN M060_AMT                                   \n"); 
		sb.append("							ELSE 0                                          \n");
		sb.append("				 END BANNAP_AMT,                                            \n");
		sb.append("         M060_AMT, 								                        \n");	 
		sb.append("       	M060_NAPSEJA, 						                            \n");	
        sb.append("         M060_JINGSUTYPE, 								                \n");	 
		sb.append("       	M060_REPAYTYPE   						                        \n");	 	 
		sb.append("   FROM  M060_JUHEANG_T                                                  \n");
		sb.append("  WHERE  M060_DATE BETWEEN ? AND ?                                       \n");																							 
		if (!"".equals(paramInfo.getString("napseja"))){
		    sb.append("	 AND  M060_NAPSEJA LIKE ?                                           \n");																									 
		}
		sb.append("  ORDER BY M060_DATE, M060_SEQ                                           \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	    int i = 1;

        parameters.setString(i++, paramInfo.getString("start_date"));
	    parameters.setString(i++, paramInfo.getString("end_date"));
		if(!"".equals(paramInfo.getString("napseja")) ) {
            parameters.setString(i++, "%"+paramInfo.getString("napseja")+"%");
        }
	
		return template.getList(conn, parameters);
	}

	/* ���༼ ���� */ 
    public static int deleteJuheang(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append(" DELETE FROM M060_JUHEANG_T   \n");
        sb.append("  WHERE M060_SEQ = ?          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("seq"));

        return template.delete(conn, parameters);
    }
	
	/* ������������ �� */
	public static CommonEntity getYoungsuView(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
   
		sb.append(" SELECT  M060_YEAR,                                    \n");	 
		sb.append("	        M060_DATE,                                    \n"); 
		sb.append("	        M060_SEQ,                                     \n"); 
		sb.append("	        M060_CASHTYPE,                                \n");
		sb.append("	        M060_JINGSUTYPE,                              \n");
		sb.append("				 CASE WHEN M060_CASHTYPE = 'A1'                 \n");
		sb.append("							THEN M060_AMT                             \n");
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END SUIP_AMT                                   \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A2'                 \n");
		sb.append("				      THEN M060_AMT                             \n");
		sb.append("				      ELSE 0                                    \n");
		sb.append("				 END GWAO_AMT                                   \n");
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A3'                 \n");
		sb.append("							THEN M060_AMT                             \n");
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END JIGUP_AMT                                  \n"); 
		sb.append("				,CASE WHEN M060_CASHTYPE = 'A4'                 \n");
		sb.append("							THEN M060_AMT                             \n"); 
		sb.append("							ELSE 0                                    \n");
		sb.append("				 END BANNAP_AMT,                                \n");
		sb.append("         M060_AMT, 								                    \n");	 
		sb.append("       	M060_NAPSEJA  						                    \n");	 
		sb.append("   FROM  M060_JUHEANG_T                                \n");
		sb.append("  WHERE  M060_SEQ = ?	                                \n");
		 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	    int i = 1;

	    parameters.setString(i++, paramInfo.getString("seq"));
	
		return template.getData(conn, parameters);
	}
	
	/* ���༼ ���� */
	public static int juheangUpdate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("UPDATE M060_JUHEANG_T                    \n");
		sb.append("   SET M060_YEAR = ?,                    \n");	
		sb.append("	      M060_DATE = ?,                    \n"); 	 
	    sb.append("	      M060_JINGSUTYPE = ?,              \n");   
		sb.append("	      M060_REPAYTYPE = ?, 				\n");	 
		sb.append("       M060_CASHTYPE = ?, 				\n");	 
		sb.append("       M060_AMT = ?, 					\n");	 
		sb.append("	      M060_NAPSEJA = ?  				\n");	
		sb.append(" WHERE M060_SEQ = ? 	                    \n");
	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;
  	    parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
		parameters.setString(i++, paramInfo.getString("jingsuType"));
		parameters.setString(i++, paramInfo.getString("repayType"));
		parameters.setString(i++, paramInfo.getString("cashType"));
		parameters.setString(i++, paramInfo.getString("amt"));
		parameters.setString(i++, paramInfo.getString("napseja"));
		parameters.setString(i++, paramInfo.getString("seq"));

		return template.update(conn, parameters);
	}


  /* �ִ����� ���ϱ� */
	public static CommonEntity getMaxDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT MAX(M070_DATE) M070_DATE  \n");
		sb.append("  FROM M070_JUHANGDAY_T          \n");		

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}

    /* ���༼�ϰ� ���� ����Ʈ */
	public static List<CommonEntity> getDayList(Connection conn,CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT M070_DATE                   \n");	
        sb.append("   FROM M070_JUHANGDAY_T            \n");
        sb.append("  WHERE M070_DATE > ?               \n");
        sb.append("  ORDER BY M070_DATE                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getList(conn, parameters);
	}


   /* ���ϱݾ�(ȸ�������� ���� ������) */
	public static CommonEntity getLastNu(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                    \n");
    sb.append("       - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT                     \n");	//Ư�������ܾ�
    sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");
    sb.append("       - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT   \n");	//Ư����������
    sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                       \n");
    sb.append("       - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                        \n"); //�ֵ������ܾ�
    sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST     \n");
    sb.append("       - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT      \n");	//�ֵ���������
    sb.append("       ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT  \n"); //Ư������¡���Ǽ�����
    sb.append("       ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT  \n"); //Ư������¡���ݾ״���
    sb.append("       ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST  \n"); //Ư������¡�����ڴ���
    sb.append("       ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT      \n");	//�ֵ�����¡���Ǽ�����
    sb.append("       ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT      \n");	//�ֵ�����¡���ݾ״���
    sb.append("       ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST      \n");	//�ֵ�����¡�����ڴ���
    sb.append("       ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT          \n");	//Ư�����Ϲ�аǼ�����
    sb.append("       ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT          \n");  //Ư�����Ϲ�бݾ״���
    sb.append("       ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES \n");	//Ư�����Ϲ�����ڴ���
    sb.append("       ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT          \n");	//�ֵ����Ϲ�аǼ�����
    sb.append("       ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT          \n");  //�ֵ����Ϲ�бݾ״���
    sb.append("       ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST \n");	//�ֵ����Ϲ�����ڴ���
    sb.append("   FROM M070_JUHANGDAY_T                                         \n");
    sb.append("  WHERE M070_DATE = (SELECT MAX(M070_DATE) M070_DATE             \n");
    sb.append("                       FROM M070_JUHANGDAY_T                     \n");
    sb.append("                      WHERE M070_DATE < ?  )                     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}


    /* ���༼�ϰ����̺� ����(�Է� ���� ����)  */
    public static int updateRoopData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                                  \n");
        sb.append("   SET M070_SPREDAYAMT = ?                               \n");
        sb.append("      ,M070_SPREDAYINTERESTAMT = ?                       \n");
        sb.append("      ,M070_PREDAYAMT = ?                                \n");
        sb.append("      ,M070_PREDAYINTERESTAMT = ?                        \n");
		sb.append("      ,M070_SPREDAYTOTALCNT = ?                          \n");
		sb.append(" 	 ,M070_SPREDAYTOTALAMT = ?                          \n");
		sb.append(" 	 ,M070_SPREDAYTOTALINTEREST = ?                     \n");
		sb.append(" 	 ,M070_PREDAYTOTALCNT = ?                           \n");
		sb.append(" 	 ,M070_PREDAYTOTALAMT = ?                           \n");
		sb.append(" 	 ,M070_PREDAYTOTALINTEREST = ?                      \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALCNT = ?                    \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALAMT = ?                    \n");
		sb.append(" 	 ,M070_SPREDAYDIVIDETOTALINTERES = ?                \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALCNT = ?                     \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALAMT = ?                     \n");
		sb.append(" 	 ,M070_PREDAYDIVIDETOTALINTEREST = ?                \n");
        sb.append(" WHERE M070_DATE = ?                                     \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;

		parameters.setString(i++, paramInfo.getString("M070_SPREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYINTERESTAMT"));	
  	    parameters.setString(i++, paramInfo.getString("M070_PREDAYAMT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYINTERESTAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALCNT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALAMT"));	
		parameters.setString(i++, paramInfo.getString("M070_PREDAYTOTALINTEREST"));	
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_SPREDAYDIVIDETOTALINTERES"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALCNT"));
		parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALAMT"));
        parameters.setString(i++, paramInfo.getString("M070_PREDAYDIVIDETOTALINTEREST"));
        parameters.setString(i++, paramInfo.getString("acc_date"));

		return template.update(conn, parameters);
	}


  /* ���� �Ǽ� �ʵ��� �� */
	public static CommonEntity getTotCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT (M070_DAYSUIPSUMCNT +           \n");
    sb.append("       M070_DAYGWAONAPSUMCNT +         \n");
    sb.append("       M070_SDAYSUIPSUMCNT +           \n");
    sb.append("       M070_SDAYGWAONAPSUMCNT +        \n");
    sb.append("       M070_DAYJIGUPSUMCNT +           \n");
    sb.append("       M070_DAYBANNAPSUMCNT +          \n");
    sb.append("       M070_SDAYJIGUPSUMCNT +          \n");
    sb.append("       M070_SDAYBANNAPSUMCNT) TOT_CNT  \n");
		sb.append("  FROM M070_JUHANGDAY_T                \n");	
		sb.append(" WHERE M070_DATE = ? 	                \n");
	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int i = 1;

    parameters.setString(i++, paramInfo.getString("max_date"));

		return template.getData(conn, parameters);
	}


    /* ���༼ �ϰ� ���� */ 
    public static int updateData(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		 
        sb.append("UPDATE M070_JUHANGDAY_T                                                                        \n");
		sb.append("   SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" - ?           \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
		    sb.append("    ," + paramInfo.getString("cnt_name") + " = " + paramInfo.getString("cnt_name") + " - 1 \n");
        }
        sb.append(" WHERE M070_DATE = ?                                                                           \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("input_amt"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.update(conn, parameters);
    }
  /* Ư��¡�� ���༼ ������ ��ȸ */
	public static List<CommonEntity> getSpSunapList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

   sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','�ֵ�Ư��¡�� ������',           \n");
   sb.append("                            'J2','Ư��¡�� ������') M060_JINGSUNAME, \n");
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','����',                          \n");
   sb.append("                           'R2','����') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT,                                                \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE                                                \n");
   sb.append("  FROM M060_JUHEANG_T                                                \n");
   sb.append(" WHERE SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = ?                                           \n");
   sb.append("   AND M060_REPAYTYPE ='R1'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
    parameters.setString(i++, paramInfo.getString("M060_JINGSUTYPE"));
	
		return template.getList(conn, parameters);
	}
  
	 /* Ư��¡�� �߻����� ������ ��ȸ */
	public static List<CommonEntity> getSpIjaList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

   sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','�ֵ�Ư��¡�� ������',           \n");
   sb.append("                            'J2','Ư��¡�� ������') M060_JINGSUNAME, \n");
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','����',                          \n");
   sb.append("                           'R2','����') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT,                                                \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE                                                \n");
   sb.append("  FROM M060_JUHEANG_T                                                \n");
   sb.append(" WHERE SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = ?                                           \n");
   sb.append("   AND M060_REPAYTYPE ='R2'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
	      
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
    parameters.setString(i++, paramInfo.getString("M060_JINGSUTYPE"));
	
		return template.getList(conn, parameters);
	}

/* �ֵ�Ư��¡�� ������ ��ȸ */
	public static List<CommonEntity> getJuSunapList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
     
	 sb.append("SELECT M060_YEAR,                                                    \n");
   sb.append("       M060_DATE,                                                    \n");
   sb.append("       M060_SEQ,                                                     \n");
   sb.append("       M060_CASHTYPE,                                                \n");
   sb.append("       DECODE(M060_JINGSUTYPE, 'J1','�ֵ�Ư��¡�� ������',           \n");
   sb.append("                            'J2','Ư��¡�� ������') M060_JINGSUNAME, \n"); 
   sb.append("       DECODE(M060_REPAYTYPE,  'R1','����',                          \n");
   sb.append("                           'R2','����') M060_REPAYNAME,              \n");
   sb.append("       CASE WHEN M060_CASHTYPE = 'A1'                                \n");      
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A2'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END SUIP_AMT                                                 \n");            
   sb.append("       CASE WHEN M060_CASHTYPE = 'A3'                                \n");      
   sb.append("            THEN M060_AMT                                            \n");
   sb.append("            WHEN M060_CASHTYPE = 'A4'                                \n");
   sb.append("            THEN -M060_AMT                                           \n");
   sb.append("        END JIGUP_AMT,                                               \n");
   sb.append("       M060_AMT,                                                     \n");
   sb.append("       M060_NAPSEJA,                                                 \n");
   sb.append("       M060_JINGSUTYPE,                                              \n");
   sb.append("       M060_REPAYTYPE,                                               \n");
   sb.append("  FROM M060_JUHEANG_T A                                              \n");
   sb.append(" WHERE M060_YEAR = ?                                                 \n"); 
   sb.append("   AND SUBSTR(M060_DATE, 1, 6) = ?                                   \n");
   sb.append("   AND M060_DATE <= ?                                                \n");
   sb.append("   AND M060_JINGSUTYPE = 'J1'                                        \n");
   sb.append("   AND M060_REPAYTYPE ='R1'                                          \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                        \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
	
		return template.getList(conn, parameters);
	}

/* �ֵ�Ư��¡�� �߻����� ��ȸ */
	public static List<CommonEntity> getJuIjaList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
     
	 sb.append("SELECT A.M060_YEAR,                                                    \n");
   sb.append("        A.M060_DATE,                                                   \n");
   sb.append("        A.M060_SEQ,                                                    \n");
   sb.append("        A.M060_CASHTYPE,                                               \n");
   sb.append(" DECODE(A.M060_JINGSUTYPE, 'J1','�ֵ�Ư��¡�� ������',                 \n");
   sb.append("                            'J2','Ư��¡�� ������') M060_JINGSUNAME,   \n"); 
   sb.append(" DECODE(A.M060_REPAYTYPE,  'R1','����',                                \n");
   sb.append("                           'R2','����') M060_REPAYNAME,                \n");
   sb.append(" CASE  WHEN A.M060_CASHTYPE = 'A1'                                     \n");       
   sb.append("       THEN A.M060_AMT                                                 \n");
   sb.append("       WHEN A.M060_CASHTYPE = 'A2'                                     \n");
   sb.append("       THEN -A.M060_AMT                                                \n");
   sb.append("  END R2SUIP_AMT                                                       \n");
   sb.append("  ,CASE WHEN A.M060_CASHTYPE IN ('A3', 'A4')                           \n");
   sb.append("      THEN A.M060_AMT                                                  \n");
   sb.append("      ELSE 0                                                           \n");
   sb.append("  END R2JIGUP_AMT,                                                     \n");
   sb.append("        A.M060_AMT,                                                    \n");
   sb.append("        A.M060_NAPSEJA,                                                \n");
   sb.append("        A.M060_JINGSUTYPE,                                             \n");
   sb.append("        A.M060_REPAYTYPE,                                              \n");
   sb.append("        B.M070_SPREMONTHAMT,                                           \n");
   sb.append("        B.M070_SPREMONTHINTERESTAMT                                    \n");
   sb.append("  FROM  M060_JUHEANG_T A                                               \n");
   sb.append("       ,M070_JUHANGDAY_T B                                             \n");
   sb.append(" WHERE  M060_YEAR = ?                                                  \n"); 
   sb.append("   AND  M060_YEAR = M070_YEAR                                          \n");
   sb.append("   AND  M060_DATE = M070_DATE                                          \n");
   sb.append("   AND SUBSTR(M060_DATE, 1, 6) = ?                                     \n");
   sb.append("   AND M060_DATE <= ?                                                  \n");
   sb.append("   AND  M060_JINGSUTYPE = 'J1'                                         \n");
   sb.append("   AND  A.M060_REPAYTYPE ='R2'                                         \n");
   sb.append(" ORDER BY M060_DATE, M060_SEQ                                          \n");
 
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
    parameters.setString(i++, paramInfo.getString("search_mon"));
    parameters.setString(i++, paramInfo.getString("end_date"));
	
		return template.getList(conn, parameters);
	}

    /* ȸ������(���� ��������) ��ȸ */
    public static CommonEntity getMinDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MIN(M070_DATE) M070_DATE  \n");
        sb.append("  FROM M070_JUHANGDAY_T          \n");
        sb.append(" WHERE M070_YEAR = ?             \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("max_date").substring(0,4));

		return template.getData(conn, parameters);
	}


  /* �ϰ� �ڷ� ���� ���� */
  public static CommonEntity getEndCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) CNT              \n");
    sb.append("  FROM M070_JUHANGDAY_T          \n");
    sb.append(" WHERE M070_DATE = ?             \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
  
    int i = 1;
		parameters.setString(i++, paramInfo.getString("end_date"));

		return template.getData(conn, parameters);
	}


    /* �ϰ�ǥ */
    public static CommonEntity getJuhangDayView(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT M070_YEAR, M070_DATE                                                      \n");  //ȸ��⵵, ����
        sb.append("     , M070_SPREDAYAMT                                                           \n");  //Ư�����ϱݾ�
        sb.append("     , M070_SPREDAYINTERESTAMT                                                   \n");  //Ư����������
        sb.append("     , (M070_SPREDAYAMT + M070_SPREDAYINTERESTAMT) SP_JAN                        \n");  //Ư�������ܾ�
        sb.append("     , M070_PREDAYAMT                                                            \n");  //�ֵ����ϱݾ�
        sb.append("     , M070_PREDAYINTERESTAMT                                                    \n");  //�ֵ���������
        sb.append("     , (M070_PREDAYAMT + M070_PREDAYINTERESTAMT) BS_JAN                          \n");  //�ֵ������ܾ�
        sb.append("     , (M070_SPREDAYTOTALCNT + M070_SPREDAYDIVIDETOTALCNT) SP_CNT                \n");  //Ư�����ϰǼ�
        sb.append("     , (M070_PREDAYTOTALCNT + M070_PREDAYDIVIDETOTALCNT) BS_CNT                  \n");  //�ֵ����ϰǼ�
        sb.append("     , M070_SPREDAYTOTALCNT                                                      \n");  //Ư������¡���Ǽ�
        sb.append("     , M070_SPREDAYTOTALAMT                                                      \n");  //Ư������¡���ݾ�
        sb.append("     , M070_SPREDAYTOTALINTEREST                                                 \n");  //Ư������¡������
        sb.append("     , (M070_SPREDAYTOTALAMT + M070_SPREDAYTOTALINTEREST) SP_JEON_NU             \n");  //Ư������¡���Ұ�
        sb.append("     , M070_PREDAYTOTALCNT                                                       \n");  //�ֵ�����¡���Ǽ�
        sb.append("     , M070_PREDAYTOTALAMT                                                       \n");  //�ֵ�����¡���ݾ�
        sb.append("     , M070_PREDAYTOTALINTEREST                                                  \n");  //�ֵ�����¡������
        sb.append("     , (M070_PREDAYTOTALAMT + M070_PREDAYTOTALINTEREST) BS_JEON_NU               \n");  //�ֵ�����¡���Ұ�
        sb.append("     , M070_SDAYSUIPSUMCNT                                                       \n");  //Ư������¡���Ǽ�
        sb.append("     , M070_SDAYSUIPSUMAMT                                                       \n");  //Ư������¡���ݾ�
        sb.append("     , M070_SDAYSUIPSUMINTEREST                                                  \n");  //Ư������¡������
        sb.append("     , (M070_SDAYSUIPSUMAMT + M070_SDAYSUIPSUMINTEREST) SP_DANG_NU               \n");  //Ư�����ϼҰ�
        sb.append("     , M070_DAYSUIPSUMCNT                                                        \n");  //�ֵȴ���¡���Ǽ�
        sb.append("     , M070_DAYSUIPSUMAMT                                                        \n");  //�ֵȴ���¡���ݾ�
        sb.append("     , M070_DAYSUIPSUMINTEREST                                                   \n");  //�ֵȴ���¡������
        sb.append("     , (M070_DAYSUIPSUMAMT + M070_DAYSUIPSUMINTEREST) BS_DANG_NU                 \n");  //�ֵȴ��ϼҰ�
        sb.append("     , M070_SDAYGWAONAPSUMCNT                                                    \n");  //Ư���������Ǽ�
        sb.append("     , M070_SDAYGWAONAPSUMAMT                                                    \n");  //Ư���������ݾ�
        sb.append("     , M070_SDAYGWAONAPSUMINTEREST                                               \n");  //Ư������������
        sb.append("     , (M070_SDAYGWAONAPSUMAMT + M070_SDAYGWAONAPSUMINTEREST) SP_GWAO_NU         \n");  //Ư���������Ұ�
        sb.append("     , M070_DAYGWAONAPSUMCNT                                                     \n");  //�ֵȰ������Ǽ�
        sb.append("     , M070_DAYGWAONAPSUMAMT                                                     \n");  //�ֵȰ������ݾ�
        sb.append("     , M070_DAYGWAONAPSUMINTEREST                                                \n");  //�ֵȰ���������
        sb.append("     , (M070_DAYGWAONAPSUMAMT + M070_DAYGWAONAPSUMINTEREST) BS_GWAO_NU           \n");  //�ֵȰ������Ұ�
        sb.append("     , M070_SPREDAYDIVIDETOTALCNT                                                \n");  //Ư�����Ϲ�аǼ�
        sb.append("     , M070_SPREDAYDIVIDETOTALAMT                                                \n");  //Ư�����Ϲ�бݾ�
        sb.append("     , M070_SPREDAYDIVIDETOTALINTERES                                            \n");  //Ư�����Ϲ������
        sb.append("     , (M070_SPREDAYDIVIDETOTALAMT + M070_SPREDAYDIVIDETOTALINTERES) SP_JEON_BAE \n");  //Ư�����Ϲ�мҰ�
        sb.append("     , M070_PREDAYDIVIDETOTALCNT                                                 \n");  //�ֵ����Ϲ�аǼ�
        sb.append("     , M070_PREDAYDIVIDETOTALAMT                                                 \n");  //�ֵ����Ϲ�бݾ�
        sb.append("     , M070_PREDAYDIVIDETOTALINTEREST                                            \n");  //�ֵ����Ϲ������
        sb.append("     , (M070_PREDAYDIVIDETOTALAMT + M070_PREDAYDIVIDETOTALINTEREST) BS_JEON_BAE  \n");  //�ֵ����Ϲ�мҰ�
        sb.append("     , M070_SDAYJIGUPSUMCNT                                                      \n");  //Ư�����Ϲ�аǼ�
        sb.append("     , M070_SDAYJIGUPSUMAMT                                                      \n");  //Ư�����Ϲ�бݾ�
        sb.append("     , M070_SDAYJIGUPSUMINTEREST                                                 \n");  //Ư�����Ϲ������
        sb.append("     , (M070_SDAYJIGUPSUMAMT + M070_SDAYJIGUPSUMINTEREST) SP_DANG_BAE            \n");  //Ư�����Ϲ�мҰ�
        sb.append("     , M070_DAYJIGUPSUMCNT                                                       \n");  //�ֵȴ��Ϲ�аǼ�
        sb.append("     , M070_DAYJIGUPSUMAMT                                                       \n");  //�ֵȴ��Ϲ�бݾ�
        sb.append("     , M070_DAYJIGUPSUMINTEREST                                                  \n");  //�ֵȴ��Ϲ������
        sb.append("     , (M070_DAYJIGUPSUMAMT + M070_DAYJIGUPSUMINTEREST) BS_DANG_BAE              \n");  //�ֵȴ��Ϲ�мҰ�
        sb.append("     , M070_SDAYBANNAPSUMCNT                                                     \n");  //Ư���ݳ���аǼ�
        sb.append("     , M070_SDAYBANNAPSUMAMT                                                     \n");  //Ư���ݳ���бݾ�
        sb.append("     , M070_SDAYBANNAPSUMINTEREST                                                \n");  //Ư���ݳ��������
        sb.append("     , (M070_SDAYBANNAPSUMAMT + M070_SDAYBANNAPSUMINTEREST) SP_BAN_BAE           \n");  //Ư���ݳ���мҰ�
        sb.append("     , M070_DAYBANNAPSUMCNT                                                      \n");  //�ֵȹݳ���аǼ�
        sb.append("     , M070_DAYBANNAPSUMAMT                                                      \n");  //�ֵȹݳ���бݾ�
        sb.append("     , M070_DAYBANNAPSUMINTEREST                                                 \n");  //�ֵȹݳ��������
        sb.append("     , (M070_DAYBANNAPSUMAMT + M070_DAYBANNAPSUMINTEREST) BS_BAN_BAE             \n");  //�ֵȹݳ���мҰ�
        sb.append("  FROM M070_JUHANGDAY_T                                                          \n");
        sb.append(" WHERE M070_DATE = ?                                                             \n");
		
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int i = 1;

        parameters.setString(i++, paramInfo.getString("end_date"));
		
        return template.getData(conn, parameters);
    }


  /* �ϰ�ǥ */
	public static CommonEntity getNothingView(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

    sb.append("SELECT M070_YEAR, M070_DATE                                                      \n");  //ȸ��⵵, ����
    sb.append("      ,(M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT           \n");  
    sb.append("     - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT             \n");  //Ư�������ܾ�(�ݾ�)
    sb.append("      ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");  
    sb.append("     - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT    \n");  //Ư�������ܾ�(����)
    sb.append("      ,((M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT          \n");  
    sb.append("     - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT)                             \n"); 
    sb.append("     + (M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");  
    sb.append("     - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST)) SP_JAN           \n");  //Ư�������ܾ�(�Ұ�)
    sb.append("      ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT              \n");  
    sb.append("     - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                \n");  //�ֵ������ܾ�(�ݾ�)
    sb.append("      ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST \n");  
    sb.append("     - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT   \n");  //�ֵ���������
    sb.append("      ,((M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT             \n");  
    sb.append("     - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT)                               \n");  
    sb.append("     + (M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST \n");  
    sb.append("     - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST)) BS_JAN             \n");  //�ֵ������ܾ�(�Ұ�)
    sb.append("			 ,((M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT                              \n");
    sb.append("     + M070_SDAYGWAONAPSUMCNT)                                                   \n");   
    sb.append("			+ (M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT                        \n");
    sb.append("     + M070_SDAYBANNAPSUMCNT)) SP_CNT                                            \n");  //Ư�������ܾװǼ�
    sb.append("			 ,((M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT                                \n");
    sb.append("     + M070_DAYGWAONAPSUMCNT)                                                    \n");
    sb.append("			+ (M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT                          \n");
    sb.append("     + M070_DAYBANNAPSUMCNT)) BS_CNT                                             \n");  //�ֵ������ܾװǼ�
    sb.append("      ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT                               \n");
    sb.append("     + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT                              \n");  //Ư������¡���Ǽ�
    sb.append("      ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT                               \n");
    sb.append("     - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT                              \n");  //Ư������¡���ݾ�
    sb.append("		   ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST                     \n");
    sb.append("     - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST                    \n");  //Ư������¡������
    sb.append("      ,((M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT                              \n");
    sb.append("     - M070_SDAYGWAONAPSUMAMT)                                                   \n");  
    sb.append("		  + (M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST                     \n");
    sb.append("     - M070_SDAYGWAONAPSUMINTEREST)) SP_JEON_NU                                  \n");  //Ư������¡���Ұ�
    sb.append("      ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT                                 \n");
    sb.append("     + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT                                \n");  //�ֵ�����¡���Ǽ�
    sb.append("      ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT                                 \n");
    sb.append("     - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT                                \n");  //�ֵ�����¡���ݾ�
    sb.append("		   ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST                       \n");
    sb.append("     - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST                      \n");  //�ֵ�����¡������
    sb.append("      ,((M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT                                \n");
    sb.append("     - M070_DAYGWAONAPSUMAMT)                                                    \n");  
    sb.append("		  + (M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST                       \n");
    sb.append("     - M070_DAYGWAONAPSUMINTEREST)) BS_JEON_NU                                   \n");  //�ֵ�����¡���Ұ�
    sb.append("			 ,0 M070_SDAYSUIPSUMCNT                                                     \n");  //Ư������¡���Ǽ�
    sb.append("			 ,0 M070_SDAYSUIPSUMAMT                                                     \n");  //Ư������¡���ݾ�
    sb.append("			 ,0 M070_SDAYSUIPSUMINTEREST                                                \n");  //Ư������¡������
    sb.append("			 ,0 SP_DANG_NU                                                              \n");  //Ư�����ϼҰ�
    sb.append("			 ,0 M070_DAYSUIPSUMCNT                                                      \n");  //�ֵȴ���¡���Ǽ�
    sb.append("			 ,0 M070_DAYSUIPSUMAMT                                                      \n");  //�ֵȴ���¡���ݾ�
    sb.append("			 ,0 M070_DAYSUIPSUMINTEREST                                                 \n");  //�ֵȴ���¡������
    sb.append("			 ,0 BS_DANG_NU                                                              \n");  //�ֵȴ��ϼҰ�
    sb.append("			 ,0 M070_SDAYGWAONAPSUMCNT                                                  \n");  //Ư���������Ǽ�
    sb.append("      ,0 M070_SDAYGWAONAPSUMAMT                                                  \n");  //Ư���������ݾ�
    sb.append("      ,0 M070_SDAYGWAONAPSUMINTEREST                                             \n");  //Ư������������
    sb.append("      ,0 SP_GWAO_NU                                                              \n");  //Ư���������Ұ�
    sb.append("			 ,0 M070_DAYGWAONAPSUMCNT                                                   \n");  //�ֵȰ������Ǽ�
    sb.append("      ,0 M070_DAYGWAONAPSUMAMT                                                   \n");  //�ֵȰ������ݾ�
    sb.append("      ,0 M070_DAYGWAONAPSUMINTEREST                                              \n");  //�ֵȰ���������
    sb.append("			 ,0 BS_GWAO_NU                                                              \n");  //�ֵȰ������Ұ�
    sb.append("      ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT                        \n");
    sb.append("     + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT                         \n");  //Ư�����Ϲ�аǼ�
    sb.append("      ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT                        \n");
    sb.append("     - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT                         \n");  //Ư�����Ϲ�бݾ�
    sb.append("      ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST               \n");
    sb.append("     - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES                \n");  //Ư�����Ϲ������
    sb.append("      ,((M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT                       \n");
    sb.append("     - M070_SDAYBANNAPSUMAMT)                                                    \n");
    sb.append("     + (M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST               \n");
    sb.append("     - M070_SDAYBANNAPSUMINTEREST)) SP_JEON_BAE                                  \n");  //Ư�����Ϲ�мҰ�
    sb.append("      ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT                          \n");
    sb.append("     + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT                           \n");  //�ֵ����Ϲ�аǼ�
    sb.append("      ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT                          \n");
    sb.append("     - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT                           \n");  //�ֵ����Ϲ�бݾ�
    sb.append("      ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST                \n");
    sb.append("     - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST                 \n");  //�ֵ����Ϲ������
    sb.append("      ,((M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT                         \n");
    sb.append("     - M070_DAYBANNAPSUMAMT)                                                     \n");  
    sb.append("     + (M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST                \n");
    sb.append("     - M070_DAYBANNAPSUMINTEREST)) BS_JEON_BAE                                   \n");  //�ֵ����Ϲ�мҰ�
    sb.append("			 ,0 M070_SDAYJIGUPSUMCNT                                                    \n");  //Ư�����Ϲ�аǼ�
    sb.append("      ,0 M070_SDAYJIGUPSUMAMT                                                    \n");  //Ư�����Ϲ�бݾ�
    sb.append("      ,0 M070_SDAYJIGUPSUMINTEREST                                               \n");  //Ư�����Ϲ������
    sb.append("      ,0 SP_DANG_BAE                                                             \n");  //Ư�����Ϲ�мҰ�
    sb.append("			 ,0 M070_DAYJIGUPSUMCNT                                                     \n");  //�ֵȴ��Ϲ�аǼ�
    sb.append("      ,0 M070_DAYJIGUPSUMAMT                                                     \n");  //�ֵȴ��Ϲ�бݾ�
    sb.append("      ,0 M070_DAYJIGUPSUMINTEREST                                                \n");  //�ֵȴ��Ϲ������
    sb.append("			 ,0 BS_DANG_BAE                                                             \n");  //�ֵȴ��Ϲ�мҰ�
    sb.append("			 ,0 M070_SDAYBANNAPSUMCNT                                                   \n");  //Ư���ݳ���аǼ�
    sb.append("      ,0 M070_SDAYBANNAPSUMAMT                                                   \n");  //Ư���ݳ���бݾ�
    sb.append("      ,0 M070_SDAYBANNAPSUMINTEREST                                              \n");  //Ư���ݳ��������
    sb.append("      ,0 SP_BAN_BAE                                                              \n");  //Ư���ݳ���мҰ�
    sb.append("      ,0 M070_DAYBANNAPSUMCNT                                                    \n");  //�ֵȹݳ���аǼ�
    sb.append("      ,0 M070_DAYBANNAPSUMAMT                                                    \n");  //�ֵȹݳ���бݾ�
    sb.append("      ,0 M070_DAYBANNAPSUMINTEREST                                               \n");  //�ֵȹݳ��������
    sb.append("      ,0 BS_BAN_BAE                                                              \n");  //�ֵȹݳ���мҰ�
    sb.append("  FROM M070_JUHANGDAY_T                                                          \n");                          
    sb.append(" WHERE M070_DATE = (SELECT MAX(M070_DATE) FROM M070_JUHANGDAY_T                  \n");
    sb.append("                     WHERE M070_DATE < ?  )                                      \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
	  int i = 1;

	  parameters.setString(i++, paramInfo.getString("end_date"));
		
		return template.getData(conn, parameters);
	}


	/* �����̿��� */
	public static CommonEntity getIwalData(Connection conn, CommonEntity paramInfo)throws SQLException {
	    StringBuffer sb = new StringBuffer();

        sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                    \n");
        sb.append("      - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) LAST_MONTH_AMT                       \n");
        sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST  \n");
        sb.append("      - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) LAST_MONTH_IJA             \n");
        sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                       \n");
        sb.append("      - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) BASE_MONTH_AMT                         \n");
        sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST     \n");
        sb.append("      - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) BASE_MONTH_IJA               \n");
        sb.append("   FROM M070_JUHANGDAY_T                                                                   \n");
        sb.append("  WHERE M070_DATE = (SELECT MAX(M070_DATE) M070_DATE                                       \n");
        sb.append("                       FROM M070_JUHANGDAY_T                                               \n");
        sb.append("                      WHERE M070_DATE < ? )                                                \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
		int i = 1;

		parameters.setString(i++, paramInfo.getString("first_date"));
	
		return template.getData(conn, parameters);
	}


	/* Ư�����༼ MAX ���� ��ȸ */
	public static CommonEntity getMaxSunap(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE                                        \n");	
    sb.append("         ,SUM(M070_PREMONTHAMT) M070_PREMONTHAMT                          \n");	                     	
    sb.append("         ,SUM(M070_PREMONTHINTERESTAMT) M070_PREMONTHINTERESTAMT          \n");	                    
    sb.append("         ,SUM(M070_SPREMONTHAMT) M070_SPREMONTHAMT                        \n");	               	
    sb.append("         ,SUM(M070_SPREMONTHINTERESTAMT) M070_SPREMONTHINTERESTAMT        \n");	                   	                                
    sb.append("    FROM M070_JUHANGDAY_T                                                 \n");	
    sb.append("   WHERE M070_YEAR = ?   	                                               \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}
		/* �ֵ� ���༼ MAX ���� ��ȸ */
	public static CommonEntity getJuMaxSunap(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE                                        \n");	
    sb.append("         ,SUM(M070_PREMONTHAMT) M070_PREMONTHAMT                          \n");	                     	
    sb.append("         ,SUM(M070_PREMONTHINTERESTAMT) M070_PREMONTHINTERESTAMT          \n");	                    
    sb.append("         ,SUM(M070_SPREMONTHAMT) M070_SPREMONTHAMT                        \n");	               	
    sb.append("         ,SUM(M070_SPREMONTHINTERESTAMT) M070_SPREMONTHINTERESTAMT        \n");	                   	                                
    sb.append("    FROM M070_JUHANGDAY_T                                                 \n");	
    sb.append("   WHERE M070_YEAR = ?   	                                               \n");	

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
   
	  int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}


  /* ���� �������� */
	public static CommonEntity getSealState(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
	
    sb.append(" SELECT M340_FNAME FROM M340_USERSEAL_T WHERE M340_CURRENTORGAN = '1'     \n");
      
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		return template.getData(conn);
	}
}