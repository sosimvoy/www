/*****************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ������� �ý���
* ���α׷���	     : IR020310.java
* ���α׷��ۼ���   : (��)�̸����� 
* ���α׷��ۼ���   : 2010-07-01
* ���α׷�����	   : ���� > ����е��
******************************************************/
package com.etax.db.mn02;

import java.sql.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020310 {


    /* ����е��  */
    public static int batchInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
        sb.append(" INSERT INTO M030_TAXOTHER_T                                    \n");
        sb.append(" SELECT A.FIS_YEAR                                              \n");
        sb.append("         ,M030_SEQ.NEXTVAL                                      \n");
        sb.append("         ,TRNX_YMD                                              \n");
        sb.append("         ,B.ACCGUBUN                                            \n");  //ȸ�豸��
        sb.append("         ,B.ACC_CODE                                            \n");  // ȸ���ڵ�
        sb.append("         ,'1110100'                                             \n"); // �����ڵ� 1110100 ����
        sb.append("         ,B.PART_CODE                                           \n");  //�μ��ڵ�
        sb.append("         ,'I1'                                                  \n");  // �Է±���
        sb.append("         ,OUT_ACCT_DETL_ITEM                                    \n");  // ä�ּ���
        sb.append("         ,TO_NUMBER(PAY_CMD_REGI_NO) AS  PAY_CMD_REGI_NO        \n");  // ���޹�ȣ
        sb.append("         ,TRNX_AMT                                              \n");
        sb.append("         ,?                                                     \n");  //�α׹�ȣ
        sb.append("         ,'A02'                                                 \n");
        sb.append("         ,'111'                                                 \n");
        sb.append("   FROM TRANS_TEF_EFAM026 A                                     \n");
        sb.append("        , TEF_ACCOUNT B                                         \n");
        sb.append("  WHERE A.FIS_YEAR = B.FIS_YEAR                                 \n");
        sb.append("    AND A.OUT_ACCT_ACCT_NO = B.ACCOUNT_NO                       \n");
        sb.append("    AND A.FIS_YEAR = ?                                          \n");
        sb.append("    AND A.BANK_SEND_DATE = ?                                    \n");
        sb.append("     AND BANK_PROCESS_CODE IN ('0000', '0001')                  \n");
        sb.append("     AND DATA_FG IN ('10', '80', '90')                          \n");
        sb.append("     AND NOT EXISTS (                                           \n");
        sb.append("               SELECT 1                                         \n");
        sb.append("                 FROM M030_TAXOTHER_T STD                       \n");
        sb.append("                WHERE STD.M030_YEAR        =  A.FIS_YEAR        \n");
        sb.append("                   AND  STD.M030_DATE        =  A.TRNX_YMD      \n");
        sb.append("                   AND  STD.M030_ACCTYPE   = B.ACCGUBUN         \n");
        sb.append("                   AND  STD.M030_ACCCODE  = B.ACC_CODE          \n");
        sb.append("                   AND  STD.M030_PARTCODE = B.PART_CODE         \n");
        sb.append("                   AND  STD.M030_ORDERNO   = TO_NUMBER(A.PAY_CMd_REGI_NO  )  \n");
        sb.append("           )                                                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("log_no"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("fis_date"));
		
        return template.insert(conn, parameters);
    }
}