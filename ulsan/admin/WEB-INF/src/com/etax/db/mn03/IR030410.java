/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030410.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-28
* ���α׷�����	   : ���Լ�������� > ���༼���
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030410 {


 /* ����е��  */
  public static int insertjuheang(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO M060_JUHEANG_T                           \n");
		sb.append("(M060_YEAR, 											\n");	 
		sb.append(" M060_SEQ, 											\n");	
		sb.append(" M060_DATE, 											\n");	 
		sb.append("	M060_JINGSUTYPE,                                    \n");  
		sb.append("	M060_REPAYTYPE, 									\n");	 
		sb.append("	M060_CASHTYPE, 										\n");	 
		sb.append("	M060_AMT, 											\n");	 
		sb.append("	M060_NAPSEJA, 										\n");	 
		sb.append("	M060_LOGNO) 										\n");	 
		sb.append("VALUES	(?,M060_SEQ.NEXTVAL,?,?,?,?,?,?,?)          \n");

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
		parameters.setString(i++, paramInfo.getString("log_no"));

		return template.insert(conn, parameters);
	}


  /* ȸ������ ��ȸ */
  public static CommonEntity getFisDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(M070_DATE) M070_DATE FROM M070_JUHANGDAY_T  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
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
  
   /* ���༼�ϰ����̺� �ڷ� �Ǽ�  */
  public static CommonEntity juhaengCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) CNT FROM M070_JUHANGDAY_T  \n");
		sb.append(" WHERE M070_DATE = ?                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
    int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}

	/* ���༼�ϰ����̺� ���  */
    public static int insertjuheangDay(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO M070_JUHANGDAY_T                             \n");
		sb.append("          (  M070_YEAR  ,M070_DATE	                      \n");
		sb.append("             ," + paramInfo.getString("col_name") + "    \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
            sb.append("             ," + paramInfo.getString("cnt_name") + "  \n");
        }
        sb.append("             ,M070_SPREDAYAMT                            \n");
        sb.append("             ,M070_SPREDAYINTERESTAMT                    \n");
        sb.append("             ,M070_PREDAYAMT                             \n");
        sb.append("             ,M070_PREDAYINTERESTAMT                     \n");
		sb.append(" 						,M070_SPREDAYTOTALCNT                       \n");
		sb.append(" 					  ,M070_SPREDAYTOTALAMT                       \n");
		sb.append(" 						,M070_SPREDAYTOTALINTEREST                  \n");
		sb.append(" 						,M070_PREDAYTOTALCNT                        \n");
		sb.append(" 						,M070_PREDAYTOTALAMT                        \n");
		sb.append(" 						,M070_PREDAYTOTALINTEREST                   \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALCNT                 \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALAMT                 \n");
		sb.append(" 						,M070_SPREDAYDIVIDETOTALINTERES             \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALCNT                  \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALAMT                  \n");
		sb.append(" 						,M070_PREDAYDIVIDETOTALINTEREST             \n");
		sb.append("            )                                            \n");
		sb.append("      VALUES	(  ?,?,                                     \n");
	    sb.append("                ?                                        \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
            sb.append("               ,1                                      \n");
        }
        sb.append("                ,?,?,?,?,?                               \n"); 
		sb.append("                ,?,?,?,?,?                               \n"); 
		sb.append("                ,?,?,?,?,?                               \n");
		sb.append("                ,?                                       \n"); 
        sb.append("            )                                            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
        parameters.setString(i++, paramInfo.getString("amt"));
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

		return template.insert(conn, parameters);
	}


    /* ���༼�ϰ����̺� ����(�Է� ����)  */
    public static int updatejuheangDay(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                                                                      \n");
		sb.append("   SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" + ?         \n");
        if (!"".equals(paramInfo.getString("cnt_name")) ) {
		    sb.append("      ," + paramInfo.getString("cnt_name") + " = " + paramInfo.getString("cnt_name") + " + 1 \n");
        }
        sb.append(" WHERE M070_DATE = ?                                                                         \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
        parameters.setString(i++, paramInfo.getString("amt"));
		parameters.setString(i++, paramInfo.getString("acc_date"));
    
		return template.update(conn, parameters);
	}


    /* ���༼�ϰ����̺� ����(�Է� ���� ����)  */
    public static int updateRoopData(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE M070_JUHANGDAY_T                              \n");
        sb.append("   SET M070_SPREDAYAMT = ?                           \n");
        sb.append("      ,M070_SPREDAYINTERESTAMT = ?                   \n");
        sb.append("      ,M070_PREDAYAMT = ?                            \n");
        sb.append("      ,M070_PREDAYINTERESTAMT = ?                    \n");
		sb.append(" 		 ,M070_SPREDAYTOTALCNT = ?                      \n");
		sb.append(" 		 ,M070_SPREDAYTOTALAMT = ?                      \n");
		sb.append(" 		 ,M070_SPREDAYTOTALINTEREST = ?                 \n");
		sb.append(" 		 ,M070_PREDAYTOTALCNT = ?                       \n");
		sb.append(" 		 ,M070_PREDAYTOTALAMT = ?                       \n");
		sb.append(" 		 ,M070_PREDAYTOTALINTEREST = ?                  \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALCNT = ?                \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALAMT = ?                \n");
		sb.append(" 		 ,M070_SPREDAYDIVIDETOTALINTERES = ?            \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALCNT = ?                 \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALAMT = ?                 \n");
		sb.append(" 		 ,M070_PREDAYDIVIDETOTALINTEREST = ?            \n");
        sb.append(" WHERE M070_DATE = ?                                 \n"); 

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

	
	/* ���༼ �ϰ� MAX ���� ��ȸ */
	public static CommonEntity getMaxDate(Connection conn)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("   SELECT MAX(M070_DATE) M070_DATE   \n");	
        sb.append("    FROM M070_JUHANGDAY_T            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
	
		return template.getData(conn);
	}


    /* ȸ������ ������ ������ �� */
	public static CommonEntity getLastYear(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("  SELECT MAX(M070_DATE) M070_DATE   \n");	
        sb.append("    FROM M070_JUHANGDAY_T           \n");
        sb.append("   WHERE M070_DATE < ?              \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
  
        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}


  /* ���ϱݾ�(�����ڷ�) */
	public static CommonEntity getLastAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
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
        sb.append("   FROM M070_JUHANGDAY_T            \n");
        sb.append("  WHERE M070_DATE = ?               \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;
		parameters.setString(i++, paramInfo.getString("max_date"));
	
		return template.getData(conn, parameters);
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
        sb.append("  WHERE M070_DATE = GET_AGO_BUSINESSDAY(?)                       \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;

		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
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
}
