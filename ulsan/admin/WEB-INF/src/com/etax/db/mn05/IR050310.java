/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050310.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 자금재배정승인내역조회/재배정처리
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050310 {
    /* 지급명세 조회 */
    public static List<CommonEntity> getReAllotList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT B.M350_PARTNAME                                \n");
        sb.append("       ,A.M100_BUDGETAMT/1000 M100_BUDGETAMT           \n");
        sb.append("       ,A.M100_BUDGETALLOTAMT/1000 M100_BUDGETALLOTAMT \n");
        sb.append("       ,A.M100_ORIALLOTAMT/1000 M100_ORIALLOTAMT       \n");
        sb.append("       ,A.M100_TOTALJICHULAMT/1000 M100_TOTALJICHULAMT \n");
        sb.append("       ,A.M100_JANAMT/1000 M100_JANAMT                 \n");
        sb.append("       ,(A.M100_SOYOAMT                                \n");
        sb.append("        +A.M100_SOYOAMT2                               \n");
        sb.append("        +A.M100_SOYOAMT3                               \n");
        sb.append("        +A.M100_SOYOAMT4                               \n");
        sb.append("        +A.M100_SOYOAMT5                               \n");
        sb.append("        +A.M100_SOYOAMT6                               \n");
        sb.append("        +A.M100_SOYOAMT7                               \n");
        sb.append("        +A.M100_SOYOAMT8                               \n");
        sb.append("        +A.M100_SOYOAMT9                               \n");
        sb.append("        +A.M100_SOYOAMT10                              \n");
        sb.append("        )/1000 M100_SOYOAMT                            \n");
        sb.append("       ,A.M100_ALLOTAMT/1000 M100_ALLOTAMT             \n");
        sb.append("       ,A.M100_ALLOTAMT YOGU_AMT                       \n");
        sb.append("       ,A.M100_ALLOTSTATE                              \n");
        sb.append("       ,DECODE(A.M100_ALLOTSTATE, 'S1', '요구등록',    \n");
        sb.append("                                  'S2', '사업소승인',  \n");
        sb.append("                                  'S3', '세정과승인',  \n");
        sb.append("                                  'S4', '재배정처리',  \n");
		sb.append("                                  'S6', '재배정완료'   \n");
        sb.append("              ) M100_ALLOTSTATE_NM                     \n");
        sb.append("       ,A.M100_ALLOTCODE                               \n");
		sb.append("       ,DECODE(A.M100_ALLOTCODE, '0', '미처리',        \n");
		sb.append("                                 '1', '정상처리',      \n");
        sb.append("                        '3', '오류',                   \n");
		sb.append("                        '9', '별단입금') M100_RST_NM   \n");
		sb.append("       ,A.M100_SEQ                                     \n");
		sb.append("       ,A.M100_YEAR                                    \n");
		sb.append("       ,A.M100_PARTCODE                                \n");
        sb.append("       ,A.M100_EDIT_YN                                 \n");
        sb.append("   FROM M100_MONEYALLOT_T A                            \n");
        sb.append("       ,M350_PARTCODE_T B                              \n");
        sb.append("  WHERE A.M100_PARTCODE = B.M350_PARTCODE              \n");
		sb.append("    AND A.M100_YEAR = B.M350_YEAR                      \n");
		sb.append("    AND A.M100_YEAR = ?                                \n");
        sb.append("    AND A.M100_DATE = ?                                \n");
        sb.append("    AND A.M100_ALLOTGUBUN = '2'                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("allot_date"));

        return template.getList(conn, parameters);
    }


	    /* 자금재배정 재배정처리 */
        public static int updateReAllotStat(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
            StringBuffer sb = new StringBuffer();

            sb.append(" UPDATE M100_MONEYALLOT_T                                                    \n");
            sb.append("    SET M100_ALLOTSTATE = DECODE(M100_EDIT_YN, 'N', 'S4', 'Y', 'S6', 'S4')   \n");
            sb.append("      , M100_ALLOTCODE = DECODE(M100_EDIT_YN, 'N', '0', 'Y', '1', '0')       \n");
            sb.append("  WHERE M100_YEAR = ?                                                        \n");
            sb.append("    AND M100_SEQ = ?                                                         \n");
		    sb.append("    AND M100_ALLOTGUBUN = '2'                                                \n");
            sb.append("    AND M100_ALLOTSTATE = 'S3' \n");

            QueryTemplate template = new QueryTemplate(sb.toString());
            QueryParameters parameters = new QueryParameters();
    
            int idx = 1;
		    parameters.setString(idx++, paramInfo.getString("fis_year"));
		    parameters.setString(idx++, seq);

            return template.update(conn, parameters);
        }


    /* 자금재배정 재배정취소 */
    public static int reAllotDelete(Connection conn, String seq, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T     \n");
        sb.append("    SET M100_ALLOTSTATE = ?   \n");
        sb.append("  WHERE M100_YEAR = ?         \n");
        sb.append("    AND M100_DATE = ?         \n");
        sb.append("    AND M100_SEQ = ?          \n");
	    sb.append("    AND M100_ALLOTGUBUN = '2' \n");
        sb.append("    AND M100_ALLOTSTATE = 'S4' \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, "S3");
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("allot_date"));
		parameters.setString(idx++, seq);

        return template.update(conn, parameters);
    }


	/* 일일마감체크 */
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
   
	
	/* 출금계좌정보 */
    public static CommonEntity getOutAcctInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M300_BANKCODE            \n");
        sb.append("       ,M300_ACCOUNTNO           \n");
        sb.append("   FROM M300_ACCOUNTMANAGE_T     \n");
        sb.append("  WHERE M300_YEAR = ?            \n");
        sb.append("    AND M300_ACCGUBUN = ?        \n");
        sb.append("    AND M300_ACCCODE = ?         \n");
		sb.append("    AND M300_PARTCODE = ?        \n");
		sb.append("    AND M300_ACCOUNTTYPE = ?     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, "A");
		parameters.setString(idx++, "11");
		parameters.setString(idx++, "00000");
        parameters.setString(idx++, "02");

        return template.getData(conn, parameters);
    }


	/* 입금계좌정보 */
    public static CommonEntity getInAcctInfo(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT B.M300_BANKCODE                      \n");
        sb.append("       ,B.M300_ACCOUNTNO                     \n");
        sb.append("   FROM M100_MONEYALLOT_T A                  \n");
	   	sb.append("       ,M300_ACCOUNTMANAGE_T B               \n");
        sb.append("  WHERE A.M100_YEAR = B.M300_YEAR            \n");
		sb.append("    AND A.M100_PARTCODE = B.M300_PARTCODE    \n");
		sb.append("    AND A.M100_ACCCODE = B.M300_ACCCODE      \n");
		sb.append("    AND B.M300_ACCOUNTTYPE = ?               \n");
		sb.append("    AND A.M100_YEAR = ?                      \n");
        sb.append("    AND A.M100_SEQ = ?                       \n");
        sb.append("    AND A.M100_DATE = ?                      \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    	
		parameters.setString(idx++, "01");
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, seq);
		parameters.setString(idx++, paramInfo.getString("allot_date"));

        return template.getData(conn, parameters);
    }


	/* 자금재배정 책임자승인 */
    public static int updateReAllotPms(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M100_MONEYALLOT_T                                        \n");
        sb.append("    SET M100_ALLOTSTATE = ?                                      \n");
        sb.append("       ,M100_LOGNO5 = ?                                          \n");
        sb.append("       ,M100_OUT_BANKCD = ?                                      \n");
        sb.append("       ,M100_OUT_ACCOUNTNO = ?                                   \n");
        sb.append("       ,M100_OUT_REQ_YN = 'Y'                                    \n");
        sb.append("       ,M100_OUT_REQ_TIME = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n");
        sb.append("       ,M100_IN_BANKCD = ?                                       \n");
        sb.append("       ,M100_IN_ACCOUNTNO = ?                                    \n");
        sb.append("       ,M100_MANAGERNAME = ?                                     \n");
        sb.append("       ,M100_BANKERNO = ?                                        \n");
        sb.append("       ,M100_MANAGERNO = ?                                       \n");
        sb.append("       ,M100_TERMINALNO = ?                                      \n");
        sb.append("       ,M100_TRADE_NO = ?                                        \n");
        sb.append("       ,M100_IMP_TRADENO = ?                                     \n");
        sb.append("  WHERE M100_YEAR = ?                                            \n");
        sb.append("    AND M100_DATE = ?                                            \n");
        sb.append("    AND M100_SEQ = ?                                             \n");
	    sb.append("    AND M100_ALLOTGUBUN = '2'                                    \n");
	    sb.append("    AND M100_ALLOTSTATE = 'S4'                                   \n");
        sb.append("    AND NVL(M100_OUT_REQ_YN, 'N') = 'N'                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
	    parameters.setString(idx++, "S6");
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("out_bank_cd"));
		parameters.setString(idx++, paramInfo.getString("out_acct_no"));
		parameters.setString(idx++, paramInfo.getString("in_bank_cd"));
		parameters.setString(idx++, paramInfo.getString("in_acct_no"));
		parameters.setString(idx++, paramInfo.getString("man_name"));
		parameters.setString(idx++, paramInfo.getString("man_bankno"));
		parameters.setString(idx++, paramInfo.getString("man_no"));
        parameters.setString(idx++, paramInfo.getString("tml_no"));
        parameters.setString(idx++, paramInfo.getString("send_no"));
        parameters.setString(idx++, paramInfo.getString("trans_manage_no"));
        //WHERE
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("allot_date"));
		parameters.setString(idx++, seq);

        return template.update(conn, parameters);
    }


	/* 부서코드가 구군인지 확인 */
    public static CommonEntity getPartInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M350_PARTCODE        \n");
        sb.append("   FROM M350_PARTCODE_T      \n");
        sb.append("  WHERE M350_YEAR = ?        \n");
		sb.append("    AND M350_PARTCODE IN ('02660', '02670', '02680', '02690', '02700')  \n");
		sb.append("    AND M350_PARTCODE = ?    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("part_code"));

        return template.getData(conn, parameters);
    }


    /* 세출수기분 등록 */
	public static int insertSugiInfo(Connection conn, CommonEntity paramInfo, String seq) throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M030_TAXOTHER_T        \n");
        sb.append("      ( M030_YEAR	                 \n");
        sb.append("       ,M030_SEQ	                   \n");
        sb.append("       ,M030_DATE	                 \n");
        sb.append("       ,M030_ACCTYPE                \n");
        sb.append("       ,M030_ACCCODE                \n");
        sb.append("       ,M030_SEMOKCODE	             \n");
        sb.append("       ,M030_PARTCODE               \n");
        sb.append("       ,M030_INTYPE                 \n");
        sb.append("       ,M030_AMT                    \n");
        sb.append("       ,M030_LOGNO	                 \n");
        sb.append("       ,M030_WORKTYPE	             \n");
        sb.append("       ,M030_TRANSGUBUN )           \n");
        sb.append(" SELECT M100_YEAR	                 \n");
        sb.append("       ,M030_SEQ.NEXTVAL            \n");
        sb.append("       ,?        	                 \n");
        sb.append("       ,?                           \n");
        sb.append("       ,M100_ACCCODE                \n");
        sb.append("       ,?	                         \n");
        sb.append("       ,M100_PARTCODE               \n");
        sb.append("       ,?                           \n");
        sb.append("       ,M100_ALLOTAMT               \n");
        sb.append("       ,?    	                     \n");
        sb.append("       ,?                           \n");
        sb.append("       ,?                           \n");
        sb.append("  WHERE M100_YEAR = ?               \n");
        sb.append("    AND M100_SEQ = ?                \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("allot_date"));
		parameters.setString(idx++, "A");
		parameters.setString(idx++, "1110100");
		parameters.setString(idx++, "I1");
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, "A05");
		parameters.setString(idx++, "135");
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, seq);

        return template.insert(conn, parameters);

	}

	/* 전문 송신 후 결과 수정 */
    public static int updateRst(Connection conn, CommonEntity paramInfo) throws SQLException {
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
        sb.append("    AND M100_TRADE_NO = ?                                            \n");
		sb.append("    AND M100_ALLOTGUBUN = '2'                                        \n");

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
		parameters.setString(idx++, paramInfo.getString("send_no"));

        return template.update(conn, parameters);
    }
}
