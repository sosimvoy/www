/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR051510.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݹ��� > �ڱݹ������γ�����ȸ/����ó��
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051510 {
    /* �����ֳ�����ȸ */
    public static List<CommonEntity> getAllotList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT CASE                                                      \n");
        sb.append("        WHEN M100_ALLOTGUBUN = '1' THEN '����'                    \n");
        sb.append("        WHEN M100_ALLOTGUBUN = '2' THEN '�����'                  \n");
        sb.append("        ELSE '�׿���' END M100_ALLOTGBNNM                         \n");
        sb.append("      , M100_ALLOTGUBUN                                           \n");
        sb.append("      , M100_OUT_REC_CD                                           \n");
        sb.append("      , M100_OUT_REQ_DT                                           \n");
        sb.append("      , M100_IMP_TRADENO                                          \n");
        sb.append("      , M100_TRADE_NO                                             \n");
        sb.append("      , SUM(NVL(M100_ALLOTAMT,0)) AS M100_ORGALLOTAMT             \n");
        sb.append("      , SUM(M100_ALLOTAMT)/1000 AS M100_ALLOTAMT                  \n");
        sb.append("      , COUNT(*) AS PROCCNT                                       \n");
        sb.append("  FROM (                                                          \n");
        sb.append("     SELECT M100_ALLOTGUBUN                                       \n");
        sb.append("            , M100_OUT_REC_CD                                     \n");
        sb.append("            , SUBSTR(M100_OUT_REQ_TIME,1,8) AS M100_OUT_REQ_DT    \n");
        sb.append("            , M100_IMP_TRADENO                                    \n");
        sb.append("            , M100_TRADE_NO                                       \n");
        sb.append("            , M100_ALLOTAMT                                       \n");
        sb.append("       FROM M100_MONEYALLOT_T                                     \n");
        sb.append("      WHERE M100_OUT_REC_CD = ?                                   \n");
        sb.append("        AND SUBSTR(M100_OUT_REQ_TIME,1,8) = ?                     \n");
        sb.append("      UNION ALL                                                   \n");
        sb.append("      SELECT '3' AS M100_ALLOTGUBUN                               \n");
        sb.append("            , M310_OUT_REC_CD AS M100_OUT_REC_CD                  \n");
        sb.append("            , SUBSTR(M310_OUT_REQ_TIME,1,8) AS M100_OUT_REQ_DT    \n");
        sb.append("            , M310_IMP_TRADENO AS M100_IMP_TRADENO                \n");
        sb.append("            , M310_TRADE_NO AS M100_TRADE_NO                      \n");
        sb.append("            , M310_INTOTAMT AS M100_ALLOTAMT                      \n");
        sb.append("        FROM M310_SURPLUSINTO_T                                   \n");
        sb.append("      WHERE M310_OUT_REC_CD = ?                                   \n");
        sb.append("        AND SUBSTR(M310_OUT_REQ_TIME,1,8) = ?                     \n");
        sb.append("   )                                                              \n");
        sb.append("   GROUP BY M100_ALLOTGUBUN                                       \n");
        sb.append("         , M100_OUT_REC_CD                                        \n");
        sb.append("         , M100_OUT_REQ_DT                                        \n");
        sb.append("         , M100_IMP_TRADENO                                       \n");
        sb.append("         , M100_TRADE_NO                                          \n");
        sb.append("   ORDER BY M100_ALLOTGUBUN, M100_TRADE_NO, M100_IMP_TRADENO      \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
	    parameters.setString(idx++, paramInfo.getString("imp_rec_cd"));
        parameters.setString(idx++, paramInfo.getString("allot_date"));
	    parameters.setString(idx++, paramInfo.getString("imp_rec_cd"));
        parameters.setString(idx++, paramInfo.getString("allot_date"));

        return template.getList(conn, parameters);
    }

    /* ���ϸ���üũ */
	public static CommonEntity getMagamInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT *                 \n");
        sb.append("   FROM M210_WORKEND_T    \n");
        sb.append("  WHERE M210_YEAR = ?     \n");
        sb.append("    AND M210_DATE = ?     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.getData(conn, parameters);
	}
  
    /* �׿����� ����ȸ */
	public static CommonEntity getm310Info(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                                         \n");
        sb.append("   FROM M310_SURPLUSINTO_T                        \n");
        sb.append("  WHERE M310_IMP_TRADENO = ?                      \n");
        sb.append("    AND M310_OUT_REC_CD = '9999'                  \n");
        sb.append("    AND SUBSTR(M310_OUT_REQ_TIME,1,8) = ?         \n");
        sb.append("    AND ROWNUM = 1                                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
	    parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.getData(conn, parameters);
	}
  
    /* ����������� ����ȸ */
	public static CommonEntity getm100Info(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                                         \n");
        sb.append("   FROM M100_MONEYALLOT_T                         \n");
        sb.append("  WHERE M100_OUT_REC_CD = '9999'                  \n");
        sb.append("    AND M100_IMP_TRADENO = ?                      \n");
        sb.append("    AND SUBSTR(M100_OUT_REQ_TIME,1,8) = ?         \n");
        sb.append("    AND ROWNUM = 1                                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.getData(conn, parameters);
	}

	/* �׿��� ����ó�� */
    public static int m310updateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M310_SURPLUSINTO_T                                       \n");
        sb.append("    SET M310_OUT_REQ_TIME  = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
        sb.append("      , M310_OUT_REC_CD = ?                                      \n");
        sb.append("      , M310_OUT_REC_CONT = ?                                    \n");
        sb.append("  WHERE M310_IMP_TRADENO = ?                                     \n");
        sb.append("    AND M310_OUT_REC_CD = '9999'                                 \n");
        sb.append("    AND SUBSTR(M310_OUT_REQ_TIME,1,8) = ?                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++,  paramInfo.getString("err_code"));
        parameters.setString(idx++,  paramInfo.getString("err_cont"));
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.update(conn, parameters);
    }


	/* �ڱݹ��� ����ó�� */
    public static int m100updateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T                                        \n");
        sb.append("    SET M100_OUT_REQ_TIME = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')  \n");
        sb.append("      , M100_OUT_REC_CD = ?                                      \n");
        sb.append("      , M100_OUT_REC_CONT = ?                                    \n");
        sb.append("  WHERE M100_OUT_REC_CD = '9999'                                 \n");
        sb.append("    AND M100_IMP_TRADENO = ?                                     \n");
        sb.append("    AND SUBSTR(M100_OUT_REQ_TIME,1,8) = ?                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++,  paramInfo.getString("err_code"));
        parameters.setString(idx++,  paramInfo.getString("err_cont"));
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.update(conn, parameters);
    }

	/* �׿��� ����ó�� */
    public static int m310updateerrRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M310_SURPLUSINTO_T                                       \n");
        sb.append("    SET M310_OUT_REQ_TIME  = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
        sb.append("      , M310_OUT_REQ_YN = 'N'                                    \n");
        sb.append("      , M310_INTOSTATE  = 'S4'                                   \n");
        sb.append("      , M310_OUT_REC_CD = ?                                      \n");
        sb.append("      , M310_OUT_REC_CONT = ?                                    \n");
        sb.append("  WHERE M310_IMP_TRADENO = ?                                     \n");
        sb.append("    AND M310_OUT_REC_CD = '9999'                                 \n");
        sb.append("    AND SUBSTR(M310_OUT_REQ_TIME,1,8) = ?                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++,  paramInfo.getString("err_code"));
        parameters.setString(idx++,  paramInfo.getString("err_cont"));
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.update(conn, parameters);
    }


	/* �ڱݹ��� ����ó�� */
    public static int m100updateerrRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T                                        \n");
        sb.append("    SET M100_OUT_REQ_TIME = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')  \n");
        sb.append("      , M100_OUT_REQ_YN = 'N'                                    \n");
        sb.append("      , M100_ALLOTSTATE = 'S4'                                   \n");
        sb.append("      , M100_OUT_REC_CD = ?                                      \n");
        sb.append("      , M100_OUT_REC_CONT = ?                                    \n");
        sb.append("  WHERE M100_OUT_REC_CD = '9999'                                 \n");
        sb.append("    AND M100_IMP_TRADENO = ?                                     \n");
        sb.append("    AND SUBSTR(M100_OUT_REQ_TIME,1,8) = ?                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++,  paramInfo.getString("err_code"));
        parameters.setString(idx++,  paramInfo.getString("err_cont"));
        parameters.setString(idx++,  paramInfo.getString("m100_sendno"));
		parameters.setString(idx++,  paramInfo.getString("allot_date"));

        return template.update(conn, parameters);
    }

	/* ���� �۽� �� ��� ���� */
    public static int m100managerupdateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T                                            \n");
        sb.append("    SET M100_OUT_REQ_YN = ?                                          \n");
        sb.append("       ,M100_ALLOTSTATE = ?                                          \n");
        sb.append("       ,M100_OUT_PRC_YN = 'Y'                                        \n");
		sb.append("       ,M100_OUT_PRC_TIME = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')     \n");
		sb.append("       ,M100_OUT_REC_CD = ?                                          \n");
		sb.append("       ,M100_OUT_REC_CONT = ?                                        \n");
        sb.append("       ,M100_TRADE_NO = TO_NUMBER(?)                                 \n");
		sb.append("       ,M100_IMP_TRADENO = TO_NUMBER(?)                              \n");
        sb.append("  WHERE SUBSTR(M100_OUT_REQ_TIME,1,8) = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");
        sb.append("    AND M100_IMP_TRADENO = ?                                         \n");
		sb.append("    AND M100_ALLOTGUBUN = '1'                                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        String M100_OUT_REQ_YN = "";
        String M100_ALLOTSTATE = "";
        if ("0000".equals(paramInfo.getString("err_code")) || "9999".equals(paramInfo.getString("err_code"))) {
            M100_OUT_REQ_YN = "Y";
            M100_ALLOTSTATE = "S6";
        } else {
            M100_OUT_REQ_YN = "N";
            M100_ALLOTSTATE = "S4";
        }
        parameters.setString(idx++, M100_OUT_REQ_YN);
        parameters.setString(idx++, M100_ALLOTSTATE);
		parameters.setString(idx++, paramInfo.getString("err_code"));
		parameters.setString(idx++, paramInfo.getString("err_cont"));
        parameters.setString(idx++, paramInfo.getString("reply_send_no"));
		parameters.setString(idx++, paramInfo.getString("reply_manage_no"));
		parameters.setString(idx++, paramInfo.getString("m100_sendno"));

        return template.update(conn, parameters);
    }
	/* ���� �۽� �� ��� ���� */
    public static int m310managerupdateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M310_SURPLUSINTO_T                                           \n");
        sb.append("    SET M310_OUT_REQ_YN = ?                                          \n");
        sb.append("       ,M310_INTOSTATE = ?                                          \n");
        sb.append("       ,M310_OUT_PRC_YN = 'Y'                                        \n");
		sb.append("       ,M310_OUT_PRC_TIME = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')     \n");
		sb.append("       ,M310_OUT_REC_CD = ?                                          \n");
		sb.append("       ,M310_OUT_REC_CONT = ?                                        \n");
        sb.append("       ,M310_TRADE_NO = TO_NUMBER(?)                                 \n");
		sb.append("       ,M310_IMP_TRADENO = TO_NUMBER(?)                              \n");
        sb.append("  WHERE SUBSTR(M310_OUT_REQ_TIME,1,8) = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");
        sb.append("    AND M310_IMP_TRADENO = ?                                         \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        String M100_OUT_REQ_YN = "";
        String M100_ALLOTSTATE = "";
        if ("0000".equals(paramInfo.getString("err_code")) || "9999".equals(paramInfo.getString("err_code"))) {
            M100_OUT_REQ_YN = "Y";
            M100_ALLOTSTATE = "S6";
        } else {
            M100_OUT_REQ_YN = "N";
            M100_ALLOTSTATE = "S4";
        }
        parameters.setString(idx++, M100_OUT_REQ_YN);
        parameters.setString(idx++, M100_ALLOTSTATE);
		parameters.setString(idx++, paramInfo.getString("err_code"));
		parameters.setString(idx++, paramInfo.getString("err_cont"));
        parameters.setString(idx++, paramInfo.getString("reply_send_no"));
		parameters.setString(idx++, paramInfo.getString("reply_manage_no"));
		parameters.setString(idx++, paramInfo.getString("m100_sendno"));

        return template.update(conn, parameters);
    }
}
