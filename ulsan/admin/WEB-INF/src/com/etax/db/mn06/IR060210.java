/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060210.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > �ڱݿ�Ź�䱸��ȸ/���
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060210 {
    /* �ڱݿ�Ź �䱸��ȸ */
    public static List<CommonEntity> bankDepositList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                            \n");
		sb.append("       ,M120_DEPOSITTYPE                                          \n");
        sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '���⿹��', 'G2', 'ȯ��ä' \n");
		sb.append("                                ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
		sb.append("       ,M120_DEPOSITAMT                                           \n");
		sb.append("       ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
		sb.append("       ,DECODE(M120_MMDATYPE, 'G1', '���ݿ���', 'G2', '���⿹��'  \n");
		sb.append("                ,'G3', 'ȯ��ä', 'G4', 'MMDA'                     \n");
        sb.append("                        ,'G5', 'ȸ�迬���̿�') MMDA_NAME          \n");
		sb.append("       ,M120_DEPOSITSTATE                                         \n");
		sb.append("       ,DECODE(M120_DEPOSITSTATE, 'S1', '���', 'S2', '����'      \n");
		sb.append("                                , 'S3', '�ϰ���') STATE_NAME    \n");
        sb.append("   FROM M120_MONEYDEPOSIT_T                                       \n");
        sb.append("  WHERE M120_DATE = ?                                             \n");
        sb.append("    AND M120_ACCGUBUN = ?                                         \n");
        sb.append("    AND M120_ACCCODE = ?                                          \n");
        sb.append("    AND M120_PARTCODE = ?                                         \n");
        sb.append("    AND M120_MMDATYPE <> 'G5'                                     \n"); //ȸ�迬�� �̿� ���� ����. 2013.03.14 ������ �䱸����.
		sb.append("  ORDER BY M120_DEPOSITSTATE, M120_DEPOSITTYPE                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("reg_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00000");

        return template.getList(conn, parameters);
    }


	/* �ڱݿ�Ź �䱸��� */
    public static int deleteDeposit(Connection conn, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" DELETE                              \n");
		sb.append("   FROM M120_MONEYDEPOSIT_T          \n");
        sb.append("  WHERE M120_SEQ = ?                 \n");
		sb.append("    AND M120_DEPOSITSTATE = 'S1'     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, seq);

        return template.delete(conn, parameters);
    }
}
