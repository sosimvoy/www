/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR030610.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-28
* ���α׷�����	   : ���Լ�������� > ���༼���
******************************************************/
package com.etax.db.mn03;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR030610 {

    /* ȸ������ ��ȸ */
    public static CommonEntity getFisDate(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT MAX(M070_DATE) M070_DATE FROM M070_JUHANGDAY_T  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());

		return template.getData(conn);
	}

    /* �Ϳ����� ��ȸ */
    public static CommonEntity getNextBusinessDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT GET_NEXT_BUSINESSDAY(?) NEXT_DAY FROM DUAL \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}
  

	/* ���༼�ϰ����̺� ���  */
    public static int insertjuheangDay(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO M070_JUHANGDAY_T                                 \n");
		sb.append("          (  M070_YEAR  ,M070_DATE                           \n");
        sb.append("             ,M070_SPREDAYAMT                                \n");
        sb.append("             ,M070_SPREDAYINTERESTAMT                        \n");
        sb.append("             ,M070_PREDAYAMT                                 \n");
        sb.append("             ,M070_PREDAYINTERESTAMT                         \n");
		sb.append(" 			,M070_SPREDAYTOTALCNT               \n");
		sb.append(" 			,M070_SPREDAYTOTALAMT               \n");
		sb.append(" 			,M070_SPREDAYTOTALINTEREST          \n");
		sb.append(" 			,M070_PREDAYTOTALCNT                \n");
		sb.append(" 			,M070_PREDAYTOTALAMT                \n");
		sb.append(" 			,M070_PREDAYTOTALINTEREST           \n");
		sb.append(" 			,M070_SPREDAYDIVIDETOTALCNT         \n");
		sb.append(" 			,M070_SPREDAYDIVIDETOTALAMT         \n");
		sb.append(" 			,M070_SPREDAYDIVIDETOTALINTERES     \n");
		sb.append(" 			,M070_PREDAYDIVIDETOTALCNT          \n");
		sb.append(" 			,M070_PREDAYDIVIDETOTALAMT          \n");
		sb.append(" 			,M070_PREDAYDIVIDETOTALINTEREST     \n");
		sb.append("            )                                                \n");
		sb.append("      VALUES	(  ?,?,                                         \n");
	    sb.append("                ?                                            \n");
        sb.append("                ,?,?,?,?,?                                   \n"); 
		sb.append("                ,?,?,?,?,?                                   \n"); 
		sb.append("                ,?,?,?,?,?                                   \n");
		sb.append("                 )                                           \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  
        int i = 1;
		parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("next_date"));
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

    /* ���ϱݾ�(�����ڷ�) */
	public static CommonEntity getLastAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT (M070_SPREDAYAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT                                                          \n");
        sb.append("       - M070_SDAYJIGUPSUMAMT + M070_SDAYBANNAPSUMAMT) M070_SPREDAYAMT                                                           \n");//Ư�������ܾ�
        sb.append("       ,(M070_SPREDAYINTERESTAMT + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST                                        \n");
        sb.append("       - M070_SDAYJIGUPSUMINTEREST + M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYINTERESTAMT                                         \n");//Ư����������
        sb.append("       ,(M070_PREDAYAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT                                                             \n");
        sb.append("       - M070_DAYJIGUPSUMAMT + M070_DAYBANNAPSUMAMT) M070_PREDAYAMT                                                              \n");//�ֵ������ܾ�
        sb.append("       ,(M070_PREDAYINTERESTAMT + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST                                           \n");
        sb.append("       - M070_DAYJIGUPSUMINTEREST + M070_DAYBANNAPSUMINTEREST) M070_PREDAYINTERESTAMT                                            \n");//�ֵ���������
        sb.append("       ,(M070_SPREDAYTOTALCNT + M070_SDAYSUIPSUMCNT + M070_SDAYGWAONAPSUMCNT) M070_SPREDAYTOTALCNT                               \n");//Ư������¡���Ǽ�����
        sb.append("       ,(M070_SPREDAYTOTALAMT + M070_SDAYSUIPSUMAMT - M070_SDAYGWAONAPSUMAMT) M070_SPREDAYTOTALAMT                               \n");//Ư������¡���ݾ״���
        sb.append("       ,(M070_SPREDAYTOTALINTEREST + M070_SDAYSUIPSUMINTEREST - M070_SDAYGWAONAPSUMINTEREST) M070_SPREDAYTOTALINTEREST           \n");//Ư������¡�����ڴ���
        sb.append("       ,(M070_PREDAYTOTALCNT + M070_DAYSUIPSUMCNT + M070_DAYGWAONAPSUMCNT) M070_PREDAYTOTALCNT                                   \n");//�ֵ�����¡���Ǽ�����
        sb.append("       ,(M070_PREDAYTOTALAMT + M070_DAYSUIPSUMAMT - M070_DAYGWAONAPSUMAMT) M070_PREDAYTOTALAMT                                   \n");//�ֵ�����¡���ݾ״���
        sb.append("       ,(M070_PREDAYTOTALINTEREST + M070_DAYSUIPSUMINTEREST - M070_DAYGWAONAPSUMINTEREST) M070_PREDAYTOTALINTEREST               \n");//�ֵ�����¡�����ڴ���
        sb.append("       ,(M070_SPREDAYDIVIDETOTALCNT + M070_SDAYJIGUPSUMCNT + M070_SDAYBANNAPSUMCNT) M070_SPREDAYDIVIDETOTALCNT                   \n");//Ư�����Ϲ�аǼ�����
        sb.append("       ,(M070_SPREDAYDIVIDETOTALAMT + M070_SDAYJIGUPSUMAMT - M070_SDAYBANNAPSUMAMT) M070_SPREDAYDIVIDETOTALAMT                   \n");//Ư�����Ϲ�бݾ״���
        sb.append("       ,(M070_SPREDAYDIVIDETOTALINTERES + M070_SDAYJIGUPSUMINTEREST - M070_SDAYBANNAPSUMINTEREST) M070_SPREDAYDIVIDETOTALINTERES \n");//Ư�����Ϲ�����ڴ���
        sb.append("       ,(M070_PREDAYDIVIDETOTALCNT + M070_DAYJIGUPSUMCNT + M070_DAYBANNAPSUMCNT) M070_PREDAYDIVIDETOTALCNT                       \n");//�ֵ����Ϲ�аǼ�����
        sb.append("       ,(M070_PREDAYDIVIDETOTALAMT + M070_DAYJIGUPSUMAMT - M070_DAYBANNAPSUMAMT) M070_PREDAYDIVIDETOTALAMT                       \n");//�ֵ����Ϲ�бݾ״���
        sb.append("       ,(M070_PREDAYDIVIDETOTALINTEREST + M070_DAYJIGUPSUMINTEREST - M070_DAYBANNAPSUMINTEREST) M070_PREDAYDIVIDETOTALINTEREST   \n");//�ֵ����Ϲ�����ڴ���
        sb.append("   FROM M070_JUHANGDAY_T                                                                                                         \n");
        sb.append("  WHERE M070_DATE = ?                                                                                                            \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int i = 1;
		parameters.setString(i++, paramInfo.getString("acc_date"));
	
		return template.getData(conn, parameters);
	}
}
