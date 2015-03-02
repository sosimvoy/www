/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : TransDAO.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 통신문전용DAO
******************************************************/

package com.etax.db.trans;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class TransDAO {
  /* 단말번호, 책임자행번, 책임자 번호 조회 */
  public static CommonEntity getPcNo(Connection conn, String trans_no, String user_id) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M260_TERMINALNO                            \n");
		sb.append("       ,? SEND_NO                                  \n");
		sb.append("       ,M260_MANAGERBANKERNO                       \n");
		sb.append("       ,M260_MANAGERNO                             \n");
		sb.append("       ,SUBSTRB(M260_USERNAME, 1, 8) M260_USERNAME \n");
    sb.append("   FROM M260_USERMANAGER_T                         \n");
		sb.append("  WHERE M260_USERID = ?                            \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, trans_no);
		parameters.setString(idx++, user_id);

    return template.getData(conn, parameters);
  }


  /* 조회용 단말번호 */
  public static CommonEntity getTmlNo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M260_TERMINALNO     \n");
    sb.append("   FROM M260_USERMANAGER_T  \n");
		sb.append("  WHERE M260_TERMINALNO IS NOT NULL  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }

    
    //책임자 승인
    public static CommonEntity getPcNo(Connection conn, String user_id) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT MANAGE_SEQ.NEXTVAL TRANS_MANAGE_NO         \n");
		sb.append("       ,SEND_SEQ.NEXTVAL SEND_NO                   \n");  //출금거래일련번호
        sb.append("       ,M260_MANAGERBANKERNO                       \n");  //책임자행번
		sb.append("       ,M260_MANAGERNO                             \n");  //책임자번호
        sb.append("       ,M260_TERMINALNO                            \n");  //단말번호
		sb.append("       ,SUBSTRB(M260_USERNAME, 1, 8) M260_USERNAME \n");  //책임자명
        sb.append("   FROM M260_USERMANAGER_T                         \n");
		sb.append("  WHERE M260_CURRENTORGAN = '1'                    \n");  //시금고 소속
        sb.append("    AND M260_USERID = ?                            \n");  //아이디
        sb.append("    AND M260_MANAGERBANKERNO IS NOT NULL           \n");
        sb.append("    AND ROWNUM = 1                                 \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, user_id);

        return template.getData(conn, parameters);
    }


    //단순조회 공통부 세팅
    public static CommonEntity getPcNo(Connection conn) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT MANAGE_SEQ.NEXTVAL TRANS_MANAGE_NO         \n");
        sb.append("      , M260_MANAGERBANKERNO                       \n");  //책임자행번
		sb.append("      , M260_MANAGERNO                             \n");  //책임자번호
        sb.append("      , M260_TERMINALNO                            \n");  //단말번호
		sb.append("      , SUBSTRB(M260_USERNAME, 1, 8) M260_USERNAME \n");  //책임자명
        sb.append("   FROM M260_USERMANAGER_T                         \n");
		sb.append("  WHERE M260_CURRENTORGAN = '1'                    \n");  //시금고 소속
        sb.append("    AND M260_MANAGERBANKERNO IS NOT NULL           \n");
        sb.append("    AND M260_TERMINALNO IS NOT NULL                \n");
        sb.append("    AND ROWNUM = 1                                 \n");

        QueryTemplate template = new QueryTemplate(sb.toString());

        return template.getData(conn);
    }


  /* 전문번호 조회 */
  public static CommonEntity getTransNo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M490_TRANSNO                              \n");
    sb.append("   FROM M490_TRANSFER_T                           \n");
		sb.append("  WHERE M490_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }


  /* 전문번호 초기화 */
  public static int updateResetNo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M490_TRANSFER_T                           \n");
    sb.append("    SET M490_TRANSNO = 1                          \n");
		sb.append("       ,M490_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.update(conn);
  }


  /* 전문번호 수정 */
  public static int updatePlusNo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M490_TRANSFER_T                           \n");
    sb.append("    SET M490_TRANSNO = M490_TRANSNO + 1           \n");
		sb.append("  WHERE M490_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD')  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.update(conn);
  }


	/* 계좌 건수 구하기 */
	public static List<CommonEntity> getAcctInfo(Connection conn, CommonEntity data) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M300_ACCOUNTNO            \n");
		sb.append("   FROM M300_ACCOUNTMANAGE_T      \n");
		sb.append("  WHERE M300_YEAR = ?             \n");
		sb.append("    AND M300_ACCGUBUN = ?         \n");
		sb.append("    AND M300_ACCCODE = ?          \n");
		sb.append("    AND M300_PARTCODE = ?         \n");
		sb.append("    AND M300_ACCOUNTTYPE = ?      \n");
		sb.append("    AND M300_STATECODE = ?        \n");
		sb.append("    AND M300_BANKCODE = ?         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, data.getString("fis_year"));
		parameters.setString(idx++, data.getString("acc_type"));
		parameters.setString(idx++, data.getString("acc_code"));
		parameters.setString(idx++, data.getString("part_code"));
		parameters.setString(idx++, "02");
		parameters.setString(idx++, "S2");
		parameters.setString(idx++, "039");

    return template.getList(conn, parameters);
  }


	/* 계좌에 따른 계좌명 조회 */
	public static CommonEntity getAcctNick(Connection conn, CommonEntity data) throws SQLException {
	    StringBuffer sb = new StringBuffer();

	    sb.append(" SELECT M300_ACCNAME            \n");
		sb.append("       ,M300_ACCOUNTNO          \n");
		sb.append("   FROM M300_ACCOUNTMANAGE_T    \n");
		sb.append("  WHERE M300_YEAR = ?           \n");
		sb.append("    AND M300_ACCOUNTNO = TRIM(?)\n");
		sb.append("    AND M300_BANKCODE = ?       \n");
		sb.append("    AND M300_STATECODE = 'S2'   \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int idx = 1;
		parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACCT_NO"));
		parameters.setString(idx++, "039");

		return template.getData(conn, parameters);
    }
  


    /* 통신거래 등록 */
    public static int insertLog(Connection conn, HashMap<String, String> data, String SEND_MSG, String ACCOUNT_NO, String BANK_CD, String TBL_NAME) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO TRANS_LOG_TAB (                      \n");
        sb.append("       TRADE_SEQ                                 \n");
        sb.append("     , TRANS_MANAGE_NO                           \n");
        sb.append("     , TRANS_DATE                                \n");
        sb.append("     , TRANS_GUBUN                               \n");
        sb.append("     , WORK_GUBUN                                \n");
        sb.append("     , USER_OFFICENO                             \n");
        sb.append("     , TML_NO                                    \n");
        sb.append("     , SEND_TIME                                 \n");
        sb.append("     , ACCOUNT_NO                                \n");
        sb.append("     , BANK_CD                                   \n");
        //sb.append("     , TRANS_AMT                                 \n");
        sb.append("     , TBL_NAME                                  \n");
        sb.append("     , SEND_MSG                                  \n");
        sb.append(") VALUES (                                       \n");
        sb.append("       TRADE_SEQ.NEXTVAL                         \n"); //TRADE_SEQ
        sb.append("     , TO_NUMBER(?)                              \n");  //TRANS_MANAGE_NO
        sb.append("     , ?                                         \n");  //TRANS_DATE
        sb.append("     , ?                                         \n");  //TRANS_GUBUN
        sb.append("     , ?                                         \n");  //WORK_GUBUN
        sb.append("     , ?                                         \n");  //USER_OFFICENO
        sb.append("     , ?                                         \n");  //USER_CHULNO
        sb.append("     , ?                                         \n");  //SEND_TIME
        sb.append("     , ?                                         \n");  //ACCOUNT_NO
        sb.append("     , ?                                         \n");  //BANK_CD
        //sb.append("     , TO_NUMBER(?)                              \n");  //TRANS_AMT
        sb.append("     , ?                                         \n");  //TBL_NAME
        sb.append("     , ?                                         \n");  //SEND_MSG
        sb.append(")                                                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, data.get("common07")); //TRANS_MANAGE_NO
        parameters.setString(idx++, data.get("common08")); //TRANS_DATE
        parameters.setString(idx++, data.get("common04")); //TRANS_GUBUN
        parameters.setString(idx++, data.get("common05")); //WORK_GUBUN
        parameters.setString(idx++, data.get("common12")); //USER_OFFICENO
        parameters.setString(idx++, data.get("common13")); //TML_NO
        parameters.setString(idx++, data.get("common09")); //SEND_TIME
        parameters.setString(idx++, ACCOUNT_NO); //ACCOUNT_NO
        parameters.setString(idx++, BANK_CD); //BANK_CD
        parameters.setString(idx++, TBL_NAME); //TBL_NAME
        parameters.setString(idx++, SEND_MSG); //SEND_MSG

        return template.insert(conn, parameters);
    }
    
    
    
    /* 통신거래 응답저장 */
    public static int updateLog(Connection conn, HashMap<String, String> data, String RECEIVE_MSG, String TRANS_AMT) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("UPDATE TRANS_LOG_TAB SET                                 \n");
        sb.append("       RESULT_CODE = ?                                   \n");
        sb.append("     , RESULT_MESSAGE = ?                                \n");
        sb.append("     , RECEIVE_TIME = TO_CHAR(SYSDATE, 'HH24MISS')       \n");
        sb.append("     , RECEIVE_MSG = ?                                   \n");
        sb.append("     , TRANS_AMT = TO_NUMBER(NVL(?, '10'))               \n");
        sb.append(" WHERE TRANS_DATE = TO_CHAR(SYSDATE, 'YYYYMMDD')         \n");
        sb.append("   AND TRANS_MANAGE_NO = TO_NUMBER(?)                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, data.get("common10")); //RESULT_CODE
        parameters.setString(idx++, data.get("common11").trim()); //RESULT_MESSAGE
        parameters.setString(idx++, RECEIVE_MSG); //RECEIVE_MSG
        parameters.setString(idx++, TRANS_AMT); //TRANS_AMT
        //where
        parameters.setString(idx++, data.get("common07")); //TRANS_MANAGE_NO

        return template.insert(conn, parameters);
    }
    
    
}
