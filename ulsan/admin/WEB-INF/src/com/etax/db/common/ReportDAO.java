/*****************************************************
* ������Ʈ��		: ��⵵ ����ý���
* ���α׷���		: ReportDAO.java
* ���α׷��ۼ���    : 
* ���α׷��ۼ���    : 2010-09-28
* ���α׷�����	    : ����(������) > ���� ���� (�ñݰ�,������,����� �� �ڵ� Ȯ��)
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;

public class ReportDAO {

	/* �������� üũ �� ���� �������� */
	public static CommonEntity getEndState(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		    sb.append("SELECT M210_WORKENDSTATE AS END_STATE                                                \n");
        sb.append("      ,(SELECT M340_FNAME FROM M340_USERSEAL_T WHERE M340_CURRENTORGAN = ?) F_NAME   \n");
        sb.append("  FROM M210_WORKEND_T                                                                \n");
        sb.append(" WHERE M210_YEAR = ?                                                                 \n");
        sb.append("   AND M210_DATE = ?                                                                 \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

        parameters.setString(idx++, "1");       // 1: �ñݰ�
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}

    
	/* ���� �ڵ� ��� �������� */
	public static List<CommonEntity> getReportCodeList(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT M230_REPORTGUBUN  REPORTGUBUN     \n");
        sb.append("      ,M230_REPORTCODE   REPORTCODE      \n");
        sb.append("      ,M230_REPORTNAME   REPORTNAME      \n");   
        sb.append("      ,M230_REPORTURL    REPORTURL       \n");   
        sb.append("      ,M230_WORKPARTCODE WORKPARTCODE    \n");   // ����� ��ȸ���ɺμ��ڵ�(����Ҹ� ���)
        sb.append("  FROM M230_REPORTCODE_T                 \n");
        sb.append(" WHERE M230_REPORTGUBUN = ?              \n");
        //sb.append("   AND M230_CITYYN = ?                   \n");   // ������ ��ȸ���ɿ���
        sb.append("   AND TRIM(M230_REPORTURL) IS NOT NULL  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("report_gubun"));   // ��������(D:���Ϻ���,M:��������,Q:�б⺸��)
       // parameters.setString(idx++, "Y");       // ������ ��ȸ���ɿ���

		return template.getList(conn, parameters);
	}
}

