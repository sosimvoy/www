/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR051110.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 잉여금이입승인조회/이입처리
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR051110 {

	/* 잉여금이입조회 */
    public static List<CommonEntity> getSrpCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append("SELECT M310_YEARINTO	                            \n");
        sb.append("      ,M310_SEQ	                                \n");
        sb.append("      ,M310_M010SEQ                              \n");
		sb.append("      ,M310_M240SEQ                              \n");
        sb.append("      ,M310_DATE	                                \n");
        sb.append("      ,M310_YEAROVER	                            \n");
        sb.append("      ,M310_ACCOUNTNOOVER                        \n");
        sb.append("      ,M310_ACCOUNTNOINTO                        \n");
        sb.append("      ,M310_INTOTAMT	                            \n");
        sb.append("      ,M310_INTOSTATE                            \n");
        sb.append("      ,M310_INTOCODE                             \n");
        sb.append("      ,DECODE(M310_INTOSTATE, 'S1', '요구등록'   \n");
        sb.append("      ,'S2', '세정과승인', 'S3', '이입처리'      \n");
        sb.append("      ,'S4', '책임자승인', 'S5', '이입완료'      \n");
        sb.append("       ) M310_INTOSTATE_NAME                     \n");
		sb.append("      ,DECODE(M310_INTOCODE, '0', '미처리'       \n");
        sb.append("      ,'1', '정상처리', '9', '별단예금'          \n");
        sb.append("       ) M310_INTOCODE_NAME                      \n");
        sb.append("  FROM M310_SURPLUSINTO_T	                    \n");
        sb.append(" WHERE M310_DATE = ?                             \n");
		sb.append(" ORDER BY M310_SEQ                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++,  paramInfo.getString("reg_date"));

        return template.getList(conn, parameters);
    }

	/* 잉여금이입처리 */
    public static int updateSrpProcess(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M310_SURPLUSINTO_T   \n");
        sb.append("    SET M310_INTOSTATE = ?   \n");
		sb.append("       ,M310_LOGNO3 = ?      \n");
        sb.append("  WHERE M310_DATE = ?        \n");
        sb.append("    AND M310_SEQ = ?         \n");
		sb.append("    AND M310_INTOSTATE = ?   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "S3");
		parameters.setString(idx++,   paramInfo.getString("log_no"));
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);
		parameters.setString(idx++,   "S2");

        return template.update(conn, parameters);
    }


    /* 잉여금이입취소 */
    public static int srpDelete(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M310_SURPLUSINTO_T   \n");
        sb.append("    SET M310_INTOSTATE = ?   \n");
        sb.append("  WHERE M310_DATE = ?        \n");
        sb.append("    AND M310_SEQ = ?         \n");
		sb.append("    AND M310_INTOSTATE = ?   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "S2");
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);
		parameters.setString(idx++,   "S5");

        return template.update(conn, parameters);
    }


	/* 일계등록 상태로 변경 */
    public static int updateSrpPms(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M310_SURPLUSINTO_T                                        \n");
        sb.append("    SET M310_INTOSTATE = ?                                        \n");
		sb.append("       ,M310_M010SEQ = M010_SEQ.NEXTVAL                           \n");
		sb.append("       ,M310_M240SEQ = M240_SEQ.NEXTVAL                           \n");
	    sb.append("       ,M310_LOGNO4 = ?                                           \n");
        sb.append("       ,M310_OUT_BANKCD = ?                                       \n");
        sb.append("       ,M310_OUT_ACCOUNTNO = M310_ACCOUNTNOOVER                   \n");
        sb.append("       ,M310_OUT_REQ_YN = 'Y'                                     \n");
        sb.append("       ,M310_OUT_REQ_TIME = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n");
        sb.append("       ,M310_IN_BANKCD = ?                                        \n");
        sb.append("       ,M310_IN_ACCOUNTNO = M310_ACCOUNTNOINTO                    \n");
        sb.append("       ,M310_INTOCODE = '1'                                       \n");
        sb.append("  WHERE M310_DATE = ?                                             \n");
        sb.append("    AND M310_SEQ = ?                                              \n");
	    sb.append("    AND M310_INTOSTATE = ?                                        \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "S5");
		parameters.setString(idx++,   paramInfo.getString("log_no"));
        parameters.setString(idx++,   "039");
        parameters.setString(idx++,   "039");
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);
		parameters.setString(idx++,   "S2");  //세정과승인건

        return template.update(conn, parameters);
    }


    /* 통신장애 및 에러로 인한 이입처리 상태로 복원 */
    public static int resetSrp(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M310_SURPLUSINTO_T              \n");
        sb.append("    SET M310_INTOSTATE = ?              \n");
		sb.append("       ,M310_LOGNO4 = ''                \n");
        sb.append("       ,M310_MANAGERNAME = ''           \n");
        sb.append("       ,M310_BANKERNO = ''              \n");
        sb.append("       ,M310_MANAGERNO = ''             \n");
        sb.append("       ,M310_TERMINALNO = ''            \n");
        sb.append("       ,M310_OUT_BANKCD = ''            \n");
        sb.append("       ,M310_OUT_ACCOUNTNO = ''         \n");
        sb.append("       ,M310_OUT_REQ_YN = 'N'           \n");
        sb.append("       ,M310_OUT_REQ_TIME = ''          \n");
        sb.append("       ,M310_OUT_PRC_YN = 'N'           \n");
        sb.append("       ,M310_OUT_PRC_TIME = ''          \n");
        sb.append("       ,M310_IN_BANKCD = ''             \n");
        sb.append("       ,M310_IN_ACCOUNTNO = ''          \n");
        sb.append("       ,M310_OUT_REC_CD = ?             \n");
        sb.append("       ,M310_OUT_REC_CONT = ?           \n");
        sb.append("  WHERE SUBSTR(M310_OUT_REQ_TIME,1,8) = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");
        sb.append("    AND M310_TRADE_NO = ?               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "S3");
        parameters.setString(idx++,   paramInfo.getString("err_code"));
		parameters.setString(idx++,   paramInfo.getString("err_cont"));
		parameters.setString(idx++,   paramInfo.getString("send_no"));

        return template.update(conn, parameters);
    }
    



    /* 전문 송신 후 결과 수정 */
    public static int updateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M310_SURPLUSINTO_T               \n");
        sb.append("    SET M310_OUT_REQ_YN = ?              \n");
        sb.append("       ,M310_INTOSTATE = ?               \n");
        sb.append("       ,M310_OUT_PRC_YN = 'Y'            \n");
		sb.append("       ,M310_OUT_PRC_TIME = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n");
		sb.append("       ,M310_OUT_REC_CD = ?              \n");
		sb.append("       ,M310_OUT_REC_CONT = ?            \n");
        sb.append("       ,M310_TRADE_NO = TO_NUMBER(?)     \n");
		sb.append("       ,M310_IMP_TRADENO = TO_NUMBER(?)  \n");
        sb.append("  WHERE SUBSTR(M310_OUT_REQ_TIME,1,8) = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");
        sb.append("    AND M310_TRADE_NO = ?                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        String M310_OUT_REQ_YN = "";
        String M310_INTOSTATE = "";
        if ("0000".equals(paramInfo.getString("err_code")) || "9999".equals(paramInfo.getString("err_code"))) {
            M310_OUT_REQ_YN = "Y";
            M310_INTOSTATE = "S5";
        } else {
            M310_OUT_REQ_YN = "N";
            M310_INTOSTATE = "S3";
        }
        parameters.setString(idx++, M310_OUT_REQ_YN);
        parameters.setString(idx++, M310_INTOSTATE);
		parameters.setString(idx++, paramInfo.getString("err_code"));
		parameters.setString(idx++, paramInfo.getString("err_cont"));
        parameters.setString(idx++, paramInfo.getString("reply_send_no"));
		parameters.setString(idx++, paramInfo.getString("reply_manage_no"));
		parameters.setString(idx++, paramInfo.getString("send_no"));

        return template.update(conn, parameters);
    }


	/* 잉여금명세로 넣기 */
    public static int insertSrpList1(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" INSERT INTO M240_SURPLUSINTODETAILS_T                             \n");
        sb.append(" ( M240_YEARINTO, M240_SEQ, M240_DATE, M240_YEAROVER               \n");
		sb.append("  ,M240_ACCTYPE, M240_ACCCODE, M240_SEMOKCODE, M240_PARTCODE       \n");
		sb.append("  ,M240_AMT, M240_M010SEQ, M240_LOGNO )                            \n");
        sb.append(" SELECT M310_YEARINTO, M310_M240SEQ, M310_DATE, M310_YEAROVER      \n");
		sb.append("       ,?, ?, ?, ?                                                 \n");
		sb.append("       ,M310_INTOTAMT, M310_M010SEQ, ?                             \n");
		sb.append("   FROM M310_SURPLUSINTO_T                                         \n");
		sb.append("  WHERE M310_DATE = ?                                              \n");
        sb.append("    AND M310_SEQ = ?                                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "A");
		parameters.setString(idx++,   "11");
		parameters.setString(idx++,   "2230300");
		parameters.setString(idx++,   "00000");
		parameters.setString(idx++,   paramInfo.getString("log_no"));
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);

        return template.insert(conn, parameters);
    }


	/* 세입수기분으로 넣기 */
    public static int insertSrpList2(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" INSERT INTO M010_TAXIN_T                                          \n");
        sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE                                  \n");
		sb.append("  ,M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE, M010_PARTCODE       \n");
		sb.append("  ,M010_INTYPE, M010_AMT, M010_CNT, M010_LOGNO, M010_SUNAPGIGWANCODE \n");
        sb.append("  ,M010_WORKTYPE, M010_TRANSGUBUN )                                \n");
        sb.append(" SELECT M310_YEARINTO, M310_M010SEQ, M310_DATE                     \n");
		sb.append("       ,?, ?, ?, ?                                                 \n");
		sb.append("       ,?, M310_INTOTAMT, ?, ?, ?                                  \n");
        sb.append("       ,?, ?                                                       \n");
		sb.append("   FROM M310_SURPLUSINTO_T                                         \n");
		sb.append("  WHERE M310_DATE = ?                                              \n");
        sb.append("    AND M310_SEQ = ?                                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   "A");
		parameters.setString(idx++,   "11");
		parameters.setString(idx++,   "2230300");
		parameters.setString(idx++,   "00000");
		parameters.setString(idx++,   "I1");
		parameters.setString(idx++,   "1");
		parameters.setString(idx++,   paramInfo.getString("log_no"));
        parameters.setString(idx++,   "110000");
        parameters.setString(idx++,   paramInfo.getString("work_log"));
		parameters.setString(idx++,   paramInfo.getString("trans_gubun"));
		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   seq);

        return template.insert(conn, parameters);
    }


    /* 잉여금명세 삭제 */
    public static int srp240Delete(Connection conn, CommonEntity paramInfo, String m240seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" DELETE FROM M240_SURPLUSINTODETAILS_T  \n");
        sb.append("  WHERE M240_DATE = ?                   \n");
        sb.append("    AND M240_SEQ = ?                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   m240seq);

        return template.insert(conn, parameters);
    }


	/* 세입수기분 삭제 */
    public static int srp010Delete(Connection conn, CommonEntity paramInfo, String m010seq) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" DELETE FROM M010_TAXIN_T      \n");
        sb.append("  WHERE M010_DATE = ?          \n"); 
        sb.append("    AND M010_SEQ = ?           \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

		parameters.setString(idx++,   paramInfo.getString("reg_date"));
		parameters.setString(idx++,   m010seq);

        return template.insert(conn, parameters);
    }

    //자금배정 담당자여부
    public static CommonEntity getUserWorkInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT * FROM M260_USERMANAGER_T    \n");
        sb.append("  WHERE M260_USERID = ?              \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,   paramInfo.getString("user_id"));

        return template.getData(conn, parameters);
    }
}