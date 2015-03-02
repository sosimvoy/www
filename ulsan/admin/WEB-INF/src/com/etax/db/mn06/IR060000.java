/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR060000.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ڱݿ�� > ����DB
******************************************************/

package com.etax.db.mn06;

import java.sql.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060000 {


  /* ���� ��� */
  public static CommonEntity getSealInfo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT M340_FNAME                \n");
    sb.append("  FROM M340_USERSEAL_T           \n");
		sb.append(" WHERE M340_CURRENTORGAN = '2'   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }

	/* ���󿬵����̺� ��ȸ */
    public static CommonEntity getDaemonData(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT *                                       \n");
		sb.append("  FROM M480_INQUIRYMANAGE_T                    \n");
        sb.append(" WHERE M480_ACCOUNTNO = '99999999999999999999' \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        return template.getData(conn, parameters);
    }

	/* ���Ͼ�������üũ */
    public static CommonEntity dailyCheck(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT *                           \n");
		sb.append("  FROM M210_WORKEND_T              \n");
        sb.append(" WHERE M210_YEAR = SUBSTR(?, 1, 4) \n");
        sb.append("   AND M210_DATE = ?               \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, date);
		parameters.setString(idx++, date);

        return template.getData(conn, parameters);
    }


	/* ���⵵��������üũ */
    public static CommonEntity lastDailyCheck(Connection conn, String date) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT *                           \n");
		sb.append("  FROM M210_WORKEND_T              \n");
        sb.append(" WHERE M210_YEAR = TO_CHAR(SYSDATE, 'YYYY') -1 \n");
        sb.append("   AND M210_DATE = ?               \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, date);

        return template.getData(conn, parameters);
    }


    /* ���� üũ(�Ϲ�ȸ��/Ư��ȸ��) */
    public static CommonEntity ilbanCheck(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                            \n");
        sb.append("   FROM M320_COLSINGDATECONTROL_T    \n");
        sb.append("  WHERE M320_YEAR = ?                \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("last_year"));

        return template.getData(conn, parameters);
    }


    /* ���⿹��, ȯ��ä ���� �Ǽ� */
    public static CommonEntity getAccountCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT COUNT(1) CNT                   \n");
		sb.append("  FROM M150_MONEYMANAGEMENT_T         \n");
        sb.append(" WHERE M150_YEAR = ?                  \n");
        sb.append("   AND M150_ACCOUNTNO = ?             \n");
        sb.append("   AND M150_JWASUNO = ?               \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
        parameters.setString(idx++, paramInfo.getString("jwasu_no"));

        return template.getData(conn, parameters);
    }


    /* ���� ȸ�� ���� */
    public static CommonEntity getAccInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT * FROM (                     \n");
        sb.append("SELECT M140_ACCTYPE AS ACCTYPE      \n");
        sb.append("       , M140_ACCCODE AS ACCCODE    \n");
        sb.append("  FROM M140_MMDAMASTER_T            \n");
        sb.append(" WHERE M140_YEAR = ?                \n");
        sb.append("    AND M140_ACCOUNTNO = ?          \n");
        sb.append("UNION ALL                           \n");
        sb.append("SELECT M150_ACCTYPE AS ACCTYPE      \n");
        sb.append("       ,  M150_ACCCODE AS ACCCODE   \n");
        sb.append(" FROM M150_MONEYMANAGEMENT_T        \n");
        sb.append("WHERE M150_YEAR = ?                 \n");
        sb.append("    AND M150_ACCOUNTNO = ?          \n");
        sb.append("    AND M150_JWASUNO = ?            \n");
        sb.append(")                                   \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("acc_year"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
        parameters.setString(idx++, paramInfo.getString("jwasu_no"));

        return template.getData(conn, parameters);
    }


	/* ���� üũ(���ݿ���) */
  public static CommonEntity closingCheck(Connection conn, String search_date, String before_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT CASE WHEN SUBSTR(?, 5,8) >= 0101 \n");
    sb.append("              AND SUBSTR(?, 5,8) <= 0310 \n");
	sb.append("              AND SUBSTR(?, 1,4) = SUBSTR(?, 1,4) \n");
    sb.append("             THEN 'TRUE'                 \n");
    sb.append("             ELSE 'FALSE'                \n");
    sb.append("         END DATE_CHEK                   \n");
    sb.append("   FROM DUAL                             \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, before_date);
		parameters.setString(idx++, search_date);

    return template.getData(conn, parameters);
  }


	/* ���� üũ(��Ÿ����) */
  public static CommonEntity closingCheck1(Connection conn, String search_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT CASE WHEN SUBSTR(?, 5,8) >= 0101 \n");
    sb.append("              AND SUBSTR(?, 5,8) <= 0310 \n");
    sb.append("             THEN 'TRUE'                 \n");
    sb.append("             ELSE 'FALSE'                \n");
    sb.append("         END DATE_CHECK                  \n");
    sb.append("   FROM DUAL                             \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, search_date);

    return template.getData(conn, parameters);
  }


	/* �ܾ��� üũ(���⵵) */
    public static CommonEntity getLastJanakData(Connection conn, String search_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT *                                 \n");
        sb.append("   FROM M170_JANECKJANG_T                 \n");
		sb.append("  WHERE M170_YEAR = (SUBSTR( ?, 1, 4) -1) \n");
        sb.append("    AND M170_DATE = ?                     \n");
		sb.append("    AND M170_ACCTYPE IN ('A', 'B')        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, search_date);

        return template.getData(conn, parameters);
    }



	/* �ܾ��� üũ(���⵵) */
  public static CommonEntity getJanakData(Connection conn, String search_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT *                             \n");
    sb.append("   FROM M170_JANECKJANG_T             \n");
		sb.append("  WHERE M170_YEAR = SUBSTR( ?, 1, 4)  \n");
    sb.append("    AND M170_DATE = ?                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, search_date);
		parameters.setString(idx++, search_date);

    return template.getData(conn, parameters);
  }


  // ----------- �ܾ��� ���� ���α׷��� --------------------//

    /* �ܾ��� ������ȸ */
    public static CommonEntity getSingleCount(Connection conn, CommonEntity paramInfo, String acc_date) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT(1) CNT                  \n");
        sb.append("   FROM M170_JANECKJANG_T             \n");
		sb.append("  WHERE M170_YEAR = ?                 \n");
        sb.append("    AND M170_DATE = ?                 \n");
        sb.append("    AND M170_ACCTYPE = ?              \n");
        sb.append("    AND M170_ACCCODE = ?              \n");
        sb.append("    AND M170_PARTCODE = ?             \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, acc_date);
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_code"));
        parameters.setString(idx++, paramInfo.getString("part_code"));
        return template.getData(conn, parameters);
    }


    /* �ܾ��� �κ���ȸ(ȸ������, ȸ�迬�� ����) */
    public static CommonEntity getImpCount(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT(1) CNT                  \n");
        sb.append("   FROM M170_JANECKJANG_T             \n");
		sb.append("  WHERE M170_ACCTYPE = ?              \n");
        sb.append("    AND M170_ACCCODE = ?              \n");
        sb.append("    AND M170_PARTCODE = ?             \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_code"));
        parameters.setString(idx++, paramInfo.getString("part_code"));
        return template.getData(conn, parameters);
    }


  /* ���� �ڵ� �ڷ� �ֱ� */
  public static int insertHiddenData(Connection conn, CommonEntity paramInfo, String acc_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" INSERT INTO M170_JANECKJANG_T                                      \n");
    sb.append(" (M170_YEAR, M170_DATE, M170_ACCTYPE, M170_ACCCODE, M170_PARTCODE   \n");
    sb.append(" ,M170_JEUNGGAMAMT                                                  \n");
    sb.append(" ,M170_DEPOSITJEUNGGAMAMT_1                                         \n");
    sb.append(" ,M170_DEPOSITJEUNGGAMAMT_2                                         \n");
    sb.append(" ,M170_DEPOSITJEUNGGAMAMT_3                                         \n");
    sb.append(" ,M170_DEPOSITJEUNGGAMAMT_4                                         \n");
    sb.append(" ,M170_RPJEUNGGAMAMT_1                                              \n");
    sb.append(" ,M170_RPJEUNGGAMAMT_2                                              \n");
    sb.append(" ,M170_RPJEUNGGAMAMT_3                                              \n");
    sb.append(" ,M170_RPJEUNGGAMAMT_4                                              \n");
    sb.append(" ,M170_LOANJEUNGGAMAMT )                                            \n");
    sb.append(" SELECT SUBSTR(?,1,4), ?, ?, ?, ?                                   \n");
    sb.append("       ,CASE WHEN 'G3' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_JEUNGGAMAMT                                       \n");
    sb.append("       ,CASE WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 3 > NVL('"+paramInfo.getString("due_day")+"', 0)  \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_DEPOSITJEUNGGAMAMT_1                              \n");
    sb.append("       ,CASE WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 3 <= NVL('"+paramInfo.getString("due_day")+"', 0) \n");
    sb.append("              AND 6 > NVL('"+paramInfo.getString("due_day")+"', 0)  \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_DEPOSITJEUNGGAMAMT_2                              \n");
    sb.append("       ,CASE WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 6 <= NVL('"+paramInfo.getString("due_day")+"', 0) \n");
    sb.append("              AND 12 > NVL('"+paramInfo.getString("due_day")+"', 0) \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_DEPOSITJEUNGGAMAMT_3                              \n");
    sb.append("       ,CASE WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 12 <= NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_DEPOSITJEUNGGAMAMT_4                              \n");
    sb.append("       ,CASE WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 30 <= NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("              AND 90 > NVL('"+paramInfo.getString("due_day")+"', 0) \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_RPJEUNGGAMAMT_1                                   \n");
    sb.append("       ,CASE WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 90 <= NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("              AND 120 > NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_RPJEUNGGAMAMT_2                                   \n");
    sb.append("       ,CASE WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 120 <= NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("              AND 180 > NVL('"+paramInfo.getString("due_day")+"', 0) \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_RPJEUNGGAMAMT_3                                   \n");
    sb.append("       ,CASE WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("              AND 180 <= NVL('"+paramInfo.getString("due_day")+"', 0)\n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_RPJEUNGGAMAMT_4                                   \n");
    sb.append("       ,CASE WHEN 'G4' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             THEN NVL("+paramInfo.getLong("input_amt")+",0)         \n");
    sb.append("             ELSE 0                                                 \n");
    sb.append("         END M170_LOANJEUNGGAMAMT                                   \n");
    sb.append("   FROM M170_JANECKJANG_T                                           \n");
    sb.append("  WHERE M170_YEAR = ?                                               \n");
    sb.append("    AND M170_DATE = ?                                               \n");
    sb.append("    AND M170_ACCTYPE = ?                                            \n");
    sb.append("    AND M170_ACCCODE = ?                                            \n");
    sb.append("    AND M170_PARTCODE = ?                                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    
    return template.insert(conn, parameters);
  }

  
    /* �ܾ��� ������Ʈ�� �ʵ�� ���� */
    public static CommonEntity getFieldInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append("SELECT CASE WHEN 'G3' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("            THEN 'M170_JEUNGGAMAMT'                                \n");
        sb.append("            WHEN 'G4' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("            THEN 'M170_LOANJEUNGGAMAMT'                            \n");
        sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 3 > NVL('"+paramInfo.getString("due_day")+"', 0)               \n");
        sb.append("            THEN 'M170_DEPOSITJEUNGGAMAMT_1'                       \n");
        sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 3 <= NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
        sb.append("             AND 6 > NVL('"+paramInfo.getString("due_day")+"', 0)               \n");
        sb.append("            THEN 'M170_DEPOSITJEUNGGAMAMT_2'                       \n");
        sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 6 <= NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
        sb.append("             AND 12 > NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
        sb.append("            THEN 'M170_DEPOSITJEUNGGAMAMT_3'                       \n");
        sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 12 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
        sb.append("            THEN 'M170_DEPOSITJEUNGGAMAMT_4'                       \n");
        sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 30 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
        sb.append("             AND 90 > NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
        sb.append("            THEN 'M170_RPJEUNGGAMAMT_1'                            \n");
        sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 90 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
        sb.append("             AND 120 > NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
        sb.append("            THEN 'M170_RPJEUNGGAMAMT_2'                            \n");
        sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 120 <= NVL('"+paramInfo.getString("due_day")+"', 0)            \n");
        sb.append("             AND 180 > NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
        sb.append("            THEN 'M170_RPJEUNGGAMAMT_3'                            \n");
        sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
        sb.append("             AND 180 <= NVL('"+paramInfo.getString("due_day")+"', 0)            \n");
        sb.append("            THEN 'M170_RPJEUNGGAMAMT_4'                            \n");
        sb.append("        END FIELD_NAME                                             \n");
        sb.append("  FROM DUAL                                                        \n");


        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		return template.getData(conn, parameters);
    }


   /* ���ⱸ�п� ���� ���� ���� */
   public static int updatePlusAmt(Connection conn, CommonEntity paramInfo, String acc_date, String field_name) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE M170_JANECKJANG_T             \n");
    sb.append("    SET "+field_name+" =  "+field_name+" + NVL("+paramInfo.getLong("input_amt")+",0)  \n");
		sb.append("  WHERE M170_YEAR = ?                 \n");
    sb.append("    AND M170_DATE = ?                 \n");
    sb.append("    AND M170_ACCTYPE = ?              \n");
    sb.append("    AND M170_ACCCODE = ?              \n");
    sb.append("    AND M170_PARTCODE = ?             \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));

    return template.update(conn, parameters);
  }


    /* ���ⱸ�п� ���� ���� ���� */
    public static int updateMinusAmt(Connection conn, CommonEntity paramInfo, String acc_date, String field_name) throws SQLException {
        StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE M170_JANECKJANG_T             \n");
		sb.append("    SET "+field_name+" =  "+field_name+" - NVL("+paramInfo.getLong("input_amt")+",0)  \n");
		sb.append("  WHERE M170_YEAR = ?                 \n");
		sb.append("    AND M170_DATE = ?                 \n");
		sb.append("    AND M170_ACCTYPE = ?              \n");
		sb.append("    AND M170_ACCCODE = ?              \n");
		sb.append("    AND M170_PARTCODE = ?             \n");


		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;

        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, acc_date);
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_code"));
        parameters.setString(idx++, paramInfo.getString("part_code"));

        return template.update(conn, parameters);
    }



  /* �ܾ��� �����ܾ� ������Ʈ�� �ʵ�� ���� */
  public static CommonEntity getYesterFieldInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("SELECT CASE WHEN 'G3' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("            THEN 'M170_MMDABEFOREDAYJANAMT'                        \n");
    sb.append("            WHEN 'G4' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("            THEN 'M170_LOANBEFOREDAYJANAMT'                        \n");
    sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 3 > NVL('"+paramInfo.getString("due_day")+"', 0)               \n");
    sb.append("            THEN 'M170_DEPOSITBEFOREDAYJANAMT_1'                   \n");
    sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 3 <= NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
    sb.append("             AND 6 > NVL('"+paramInfo.getString("due_day")+"', 0)               \n");
    sb.append("            THEN 'M170_DEPOSITBEFOREDAYJANAMT_2'                   \n");
    sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 6 <= NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
    sb.append("             AND 12 > NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
    sb.append("            THEN 'M170_DEPOSITBEFOREDAYJANAMT_3'                   \n");
    sb.append("            WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 12 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
    sb.append("            THEN 'M170_DEPOSITBEFOREDAYJANAMT_4'                   \n");
    sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 30 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
    sb.append("             AND 90 > NVL('"+paramInfo.getString("due_day")+"', 0)              \n");
    sb.append("            THEN 'M170_RPBEFOREDAYAMT_1'                           \n");
    sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 90 <= NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
    sb.append("             AND 120 > NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
    sb.append("            THEN 'M170_RPBEFOREDAYAMT_1'                           \n");
    sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 120 <= NVL('"+paramInfo.getString("due_day")+"', 0)            \n");
    sb.append("             AND 180 > NVL('"+paramInfo.getString("due_day")+"', 0)             \n");
    sb.append("            THEN 'M170_RPBEFOREDAYAMT_1'                           \n");
    sb.append("            WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'  \n");
    sb.append("             AND 180 <= NVL('"+paramInfo.getString("due_day")+"', 0)            \n");
    sb.append("            THEN 'M170_RPBEFOREDAYAMT_1'                           \n");
    sb.append("        END FIELD_NAME                                             \n");
    sb.append("  FROM DUAL                                                        \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

    return template.getData(conn, parameters);
  }


  /* �����ܾ� ���ϱ� */
  public static CommonEntity getYesterInfo(Connection conn, CommonEntity paramInfo, String acc_date) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT CASE WHEN 'G3' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("             THEN M170_MMDABEFOREDAYJANAMT + M170_JEUNGGAMAMT               \n");
    sb.append("             WHEN 'G4' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("             THEN M170_LOANBEFOREDAYJANAMT + M170_LOANJEUNGGAMAMT           \n");
    sb.append("             WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 3 > NVL('"+paramInfo.getString("due_day")+"', 0)                       \n");
    sb.append("             THEN M170_DEPOSITBEFOREDAYJANAMT_1 + M170_DEPOSITJEUNGGAMAMT_1 \n");
    sb.append("             WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 3 <= NVL('"+paramInfo.getString("due_day")+"', 0)                      \n");
    sb.append("              AND 6 > NVL('"+paramInfo.getString("due_day")+"', 0)                       \n");
    sb.append("             THEN M170_DEPOSITBEFOREDAYJANAMT_2 + M170_DEPOSITJEUNGGAMAMT_2 \n");
    sb.append("             WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 6 <= NVL('"+paramInfo.getString("due_day")+"', 0)                      \n");
    sb.append("              AND 12 > NVL('"+paramInfo.getString("due_day")+"', 0)                      \n");
    sb.append("             THEN M170_DEPOSITBEFOREDAYJANAMT_3 + M170_DEPOSITJEUNGGAMAMT_3 \n");
    sb.append("             WHEN 'G1' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 12 <= NVL('"+paramInfo.getString("due_day")+"', 0)                     \n");
    sb.append("             THEN M170_DEPOSITBEFOREDAYJANAMT_4 + M170_DEPOSITJEUNGGAMAMT_4 \n");
    sb.append("             WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 30 <= NVL('"+paramInfo.getString("due_day")+"', 0)                     \n");
    sb.append("              AND 90 > NVL('"+paramInfo.getString("due_day")+"', 0)                      \n");
    sb.append("             THEN M170_RPBEFOREDAYAMT_1 + M170_RPJEUNGGAMAMT_1              \n");
    sb.append("             WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 90 <= NVL('"+paramInfo.getString("due_day")+"', 0)                     \n");
    sb.append("              AND 120 > NVL('"+paramInfo.getString("due_day")+"', 0)                     \n");
    sb.append("             THEN M170_RPBEFOREDAYAMT_2 + M170_RPJEUNGGAMAMT_2              \n");
    sb.append("             WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 120 <= NVL('"+paramInfo.getString("due_day")+"', 0)                    \n");
    sb.append("              AND 180 > NVL('"+paramInfo.getString("due_day")+"', 0)                     \n");
    sb.append("             THEN M170_RPBEFOREDAYAMT_3 + M170_RPJEUNGGAMAMT_3              \n");
    sb.append("             WHEN 'G2' = '"+paramInfo.getString("deposit_type")+"'          \n");
    sb.append("              AND 180 <= NVL('"+paramInfo.getString("due_day")+"', 0)                    \n");
    sb.append("             THEN M170_RPBEFOREDAYAMT_4 + M170_RPJEUNGGAMAMT_4              \n");
    sb.append("         END YESTER_AMT                                                     \n");
    sb.append("   FROM M170_JANECKJANG_T                                                   \n");
    sb.append("  WHERE M170_YEAR = ?                                                       \n");
    sb.append("    AND M170_DATE = ?                                                       \n");
    sb.append("    AND M170_ACCTYPE = ?                                                    \n");
    sb.append("    AND M170_ACCCODE = ?                                                    \n");
    sb.append("    AND M170_PARTCODE = ?                                                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));
    
    return template.getData(conn, parameters);
  }


  /* ���� �����ܾ� ���� */
  public static int updateYesterAmt(Connection conn, CommonEntity paramInfo, String acc_date, 
    String yester_field, long addAmt) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" UPDATE M170_JANECKJANG_T             \n");
    sb.append("    SET "+yester_field+" = NVL("+addAmt+",0) \n");
		sb.append("  WHERE M170_YEAR = ?                 \n");
    sb.append("    AND M170_DATE = ?                 \n");
    sb.append("    AND M170_ACCTYPE = ?              \n");
    sb.append("    AND M170_ACCCODE = ?              \n");
    sb.append("    AND M170_PARTCODE = ?             \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, acc_date);
    parameters.setString(idx++, paramInfo.getString("acc_type"));
    parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("part_code"));

    return template.update(conn, parameters);
  }


  public static CommonEntity JanaekCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT COUNT(1) CNT                  \n");
    sb.append("   FROM M170_JANECKJANG_T             \n");
		sb.append("  WHERE M170_YEAR = ?                 \n");
    sb.append("    AND M170_DATE = ?                 \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("acc_year"));
    parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getData(conn, parameters);
  }
}


