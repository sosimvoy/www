/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020410.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2015-02-04
* ���α׷�����	   : ���� > �ϰ���Ϲ�ó����
******************************************************/
package com.etax.db.mn02;

import java.sql.*;
import java.util.List;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020410 {


    /* �ϰ���Ϲ�ó����  */
    public static List<CommonEntity> getMiSechulList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
        sb.append(" SELECT GCC_DEPT_NM                               \n");
        sb.append("      , SUM(TRNX_AMT) AS TRNX_AMT                 \n");
        sb.append("      , COUNT(1) AS CNT                           \n");
        sb.append("   FROM (                                         \n");
        sb.append("   SELECT *                                       \n");
        sb.append("     FROM TRANS_TEF_EFAM026                       \n");
        sb.append("    WHERE FIS_YEAR = ?                            \n");
        sb.append("      AND BANK_SEND_DATE = ?                      \n");
        sb.append("      AND BANK_PROCESS_CODE IN ('0000', '0001')   \n");
        sb.append("      AND DATA_FG IN ('10', '80', '90')           \n");
        sb.append("    MINUS                                         \n");
        sb.append("   SELECT A.*                                     \n");
        sb.append("     FROM TRANS_TEF_EFAM026 A                     \n");
        sb.append("        , TEF_ACCOUNT B                           \n");
        sb.append("    WHERE A.FIS_YEAR = B.FIS_YEAR                 \n");
        sb.append("      AND A.OUT_ACCT_ACCT_NO = B.ACCOUNT_NO       \n");
        sb.append("      AND A.FIS_YEAR = ?                          \n");
        sb.append("      AND A.BANK_SEND_DATE = ?                    \n");
        sb.append("      AND BANK_PROCESS_CODE IN ('0000', '0001')   \n");
        sb.append("      AND DATA_FG IN ('10', '80', '90')           \n");
        sb.append("   ) X                                            \n");
        sb.append("  GROUP BY GCC_DEPT_NM                            \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        
        return template.getList(conn, parameters);
    }
}