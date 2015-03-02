/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061910.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금운용회계연도이월
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061910 {

	/* 회계연도 이월마감 여부 확인 */
    public static List<CommonEntity> getCarryYN(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT COUNT(1) CNT, M250_ACCTRANSFERYN  \n");
		sb.append("  FROM M250_ACCTRANSFER_T                \n");
        sb.append(" WHERE M250_YEAR = ?                     \n");
        sb.append("   AND M250_ACCTYPE = ?                  \n");
		sb.append(" GROUP BY M250_ACCTRANSFERYN             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("last_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.getList(conn, parameters);
    }

    /* 회계구분에 따른 회계일자 구하기 */
    public static CommonEntity getAccDate(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT CASE WHEN ? = 'A'                 \n");
        sb.append("            THEN M320_DATEILBAN          \n");
        sb.append("            WHEN ? = 'B'                 \n");
        sb.append("            THEN M320_DATETEKBEYL        \n");
        sb.append("            WHEN ? = 'D'                 \n");
        sb.append("            THEN M320_DATEHYUNGEUM       \n");
        sb.append("            WHEN ? = 'E'                 \n");
        sb.append("            THEN M320_DATEGIGEUM         \n");
        sb.append("            ELSE 'ELSE'                  \n");
        sb.append("        END ACC_DATE                     \n");
		sb.append("  FROM M320_COLSINGDATECONTROL_T         \n");
        sb.append(" WHERE M320_YEAR = ?                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("last_year_list"));

        return template.getData(conn, parameters);
    }


    /* 정기예금, 환매채 이월 */
    public static int insertYetak(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M120_MONEYDEPOSIT_T           \n");
        sb.append(" ( M120_YEAR                               \n");
        sb.append(" , M120_SEQ                                \n");
        sb.append(" , M120_DATE                               \n");
        sb.append(" , M120_ACCGUBUN                           \n");
        sb.append(" , M120_ACCCODE                            \n");
        sb.append(" , M120_PARTCODE                           \n");
        sb.append(" , M120_DEPOSITTYPE                        \n");
        sb.append(" , M120_MMDATYPE                           \n");
        sb.append(" , M120_ACCOUNTNO                          \n");
        sb.append(" , M120_JWASUNO                            \n");
        sb.append(" , M120_DEPOSITAMT                         \n");
        sb.append(" , M120_MANGIDATE                          \n");
        sb.append(" , M120_DEPOSITDATE                        \n");
        sb.append(" , M120_INTERESTRATE                       \n");
        sb.append(" , M120_DEPOSITSTATE )                     \n");
        sb.append(" SELECT ACC_YEAR                           \n");
        sb.append("       ,M120_SEQ.NEXTVAL                   \n");
        sb.append("       ,ACC_DATE                           \n");
        sb.append("       ,ACC_TYPE                           \n");
        sb.append("       ,ACC_CODE                           \n");
        sb.append("       ,PART_CODE                          \n");
        sb.append("       ,DEPOSIT_TYPE                       \n");
        sb.append("       ,MMDA_TYPE                          \n");
        sb.append("       ,ACCOUNTNO                          \n");
        sb.append("       ,JWASUNO                            \n");
        sb.append("       ,DEPOSIT_AMT                        \n");
        sb.append("       ,MANGI_DATE                         \n");
        sb.append("       ,DUE_DATE                           \n");
        sb.append("       ,RATE                               \n");
        sb.append("       ,STATE_CODE                         \n");
        sb.append("   FROM (                                  \n");
        sb.append(" SELECT                                    \n");
        sb.append("   ? ACC_YEAR                              \n");
        sb.append(" , ? ACC_DATE                              \n");
        sb.append(" , M140_ACCTYPE ACC_TYPE                   \n");
        sb.append(" , M140_ACCCODE ACC_CODE                   \n");
        sb.append(" , M140_PARTCODE	PART_CODE                 \n");
        sb.append(" , M140_DEPOSITTYPE DEPOSIT_TYPE           \n");
        sb.append(" , ? MMDA_TYPE                             \n");  //G5 고정(회계연도이월)
        sb.append(" , M140_ACCOUNTNO ACCOUNTNO                \n");
        sb.append(" , '' JWASUNO                              \n");
        sb.append(" , M140_JANAMT DEPOSIT_AMT                 \n");
        sb.append(" , '' MANGI_DATE                           \n");
        sb.append(" , '' DUE_DATE                             \n");
        sb.append(" , '' RATE                                 \n");
        sb.append(" , ? STATE_CODE                            \n");
        sb.append("  FROM M140_MMDAMASTER_T                   \n");
        sb.append(" WHERE M140_YEAR = ?                       \n");
        sb.append("   AND M140_ACCTYPE = ?                    \n");
        sb.append("   AND M140_JANAMT > 0                     \n");
        sb.append(" UNION                                     \n");
        sb.append(" SELECT                                    \n");
        sb.append("   ? ACC_YEAR                              \n");
        sb.append(" , ? ACC_DATE                              \n");
        sb.append(" , M150_ACCTYPE ACC_TYPE                   \n");
        sb.append(" , M150_ACCCODE ACC_CODE                   \n");
        sb.append(" , M150_PARTCODE	PART_CODE                 \n");
        sb.append(" , M150_DEPOSITTYPE DEPOSIT_TYPE           \n");
        sb.append(" , ? MMDA_TYPE                             \n");  //G5 고정(회계연도이월)
        sb.append(" , M150_ACCOUNTNO ACCOUNTNO                \n");
        sb.append(" , M150_JWASUNO JWASUNO                    \n");
        sb.append(" , M150_DEPOSITAMT DEPOSIT_AMT             \n");
        sb.append(" , M150_MANGIDATE MANGI_DATE               \n");
        sb.append(" , M150_DEPOSITDATE DUE_DATE               \n");
        sb.append(" , M150_INTERESTRATE RATE                  \n");
        sb.append(" , ? STATE_CODE                            \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T              \n");
        sb.append(" WHERE M150_YEAR = ?                       \n");
        sb.append("   AND M150_ACCTYPE = ?                    \n");
        sb.append("   AND M150_CANCELDATE IS NULL             \n");
        sb.append("  )                                        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, "G5");
        parameters.setString(idx++, "S3");
        parameters.setString(idx++, paramInfo.getString("last_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

        parameters.setString(idx++, paramInfo.getString("this_year_list"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, "G5");
        parameters.setString(idx++, "S3");
		parameters.setString(idx++, paramInfo.getString("last_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.insert(conn, parameters);
    }


	/* 정기예금, 환매채 이월 */
    public static int insertJeongiHwan(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M150_MONEYMANAGEMENT_T                      \n");
        sb.append(" (                                                       \n");
        sb.append("     M150_YEAR                                           \n");
        sb.append("   , M150_ACCOUNTNO                                      \n");
        sb.append("   , M150_JWASUNO                                        \n");
        sb.append("   , M150_ACCTYPE                                        \n");
        sb.append("   , M150_ACCCODE                                        \n");
        sb.append("   , M150_PARTCODE                                       \n");
        sb.append("   , M150_DEPOSITTYPE                                    \n");
        sb.append("   , M150_DEPOSITAMT                                     \n");
        sb.append("   , M150_SINKYUDATE                                     \n");
        sb.append("   , M150_MANGIDATE                                      \n");
        sb.append("   , M150_DEPOSITDATE                                    \n");
        sb.append("   , M150_INTERESTRATE                                   \n");
        sb.append("   , M150_INTEREST                                       \n");
        sb.append("   , M150_CANCELDATE                                     \n");
        sb.append("   , M150_LOGNO                                          \n");
        sb.append("   , M150_M120SEQ                                        \n");
        sb.append("   , M150_M130SEQ                                        \n");
        sb.append("   , M150_REQ_YN                                         \n");
        sb.append("   )                                                     \n");
        sb.append(" SELECT                                                  \n");
        sb.append("  ?, M150_ACCOUNTNO, M150_JWASUNO, M150_ACCTYPE	        \n");
        sb.append(" ,M150_ACCCODE, M150_PARTCODE, M150_DEPOSITTYPE          \n");
        sb.append(" ,M150_DEPOSITAMT, M150_SINKYUDATE, M150_MANGIDATE	    \n");
        sb.append(" ,M150_DEPOSITDATE, M150_INTERESTRATE, M150_INTEREST     \n");
        sb.append(" ,M150_CANCELDATE, M150_LOGNO, M150_M120SEQ              \n");
        sb.append(" ,M150_M130SEQ, M150_REQ_YN                              \n");
		sb.append("  FROM M150_MONEYMANAGEMENT_T                            \n");
        sb.append(" WHERE M150_YEAR = ?                                     \n");
        sb.append("   AND M150_ACCTYPE = ?                                  \n");
		sb.append("   AND M150_CANCELDATE IS NULL                           \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("last_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.insert(conn, parameters);
    }

    /* MMDA 이월 */
    public static int insertMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO M140_MMDAMASTER_T   \n");
        sb.append("  (                              \n");
        sb.append("     M140_YEAR                   \n");
        sb.append("   , M140_ACCOUNTNO              \n");
        sb.append("   , M140_ACCTYPE                \n");
        sb.append("   , M140_ACCCODE                \n");
        sb.append("   , M140_PARTCODE               \n");
        sb.append("   , M140_DEPOSITTYPE            \n");
        sb.append("   , M140_SEQ                    \n");
        sb.append("   , M140_JANAMT                 \n");
        sb.append("   , M140_REQAMT                 \n");
        sb.append("   , M140_CANCEL_YN              \n");
        sb.append("   )                             \n");
        sb.append(" SELECT                          \n");
		sb.append("   ?		                        \n");
        sb.append("  ,M140_ACCOUNTNO                \n");
        sb.append("  ,M140_ACCTYPE                  \n");
        sb.append("  ,M140_ACCCODE                  \n");
        sb.append("  ,M140_PARTCODE                 \n");
        sb.append("  ,M140_DEPOSITTYPE              \n");
		sb.append("  ,M140_SEQ		                \n");
        sb.append("  ,M140_JANAMT	                \n");
        sb.append("  ,M140_REQAMT	                \n");
        sb.append("  , M140_CANCEL_YN               \n");
        sb.append("   FROM M140_MMDAMASTER_T        \n");
        sb.append("  WHERE M140_YEAR = ?            \n");
		sb.append("    AND M140_ACCTYPE = ?         \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("last_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.insert(conn, parameters);
    }


    public static CommonEntity countYetak(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT SUM(M120_CNT + M130_CNT) CNT                    \n");
        sb.append("   FROM (                                               \n");
        sb.append(" SELECT M120_ACCOUNTNO                                  \n");
        sb.append("       ,M120_JWASUNO                                    \n");
        sb.append("       ,NVL((SELECT COUNT(*) FROM M120_MONEYDEPOSIT_T   \n");
        sb.append("          WHERE M120_YEAR = ?                           \n");
        sb.append("            AND M120_MMDATYPE <> ?                      \n");
        sb.append("            AND M120_ACCGUBUN = ?                       \n");
        sb.append("            AND M120_ACCOUNTNO = A.M120_ACCOUNTNO       \n");
        sb.append("            AND M120_JWASUNO = A.M120_JWASUNO           \n");
        sb.append("        ), 0) M120_CNT                                  \n");
        sb.append("       ,NVL((SELECT COUNT(*) FROM M130_MONEYINCHUL_T    \n");
        sb.append("          WHERE M130_YEAR = ?                           \n");
        sb.append("            AND M130_MMDATYPE <> ?                      \n");
        sb.append("            AND M130_ACCGUBUN = ?                       \n");
        sb.append("            AND M130_ACCOUNTNO = A.M120_ACCOUNTNO       \n");
        sb.append("            AND M130_JWASUNO = A.M120_JWASUNO           \n");
        sb.append("        ), 0) M130_CNT                                  \n");
        sb.append("   FROM M120_MONEYDEPOSIT_T A                           \n");
        sb.append("  WHERE M120_YEAR = ?                                   \n");
        sb.append("    AND M120_MMDATYPE = ?                               \n");
        sb.append("    AND M120_ACCGUBUN = ?                               \n");
        sb.append("  )                                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
        parameters.setString(idx++, "G5");
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
        parameters.setString(idx++, "G5");
        parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
        parameters.setString(idx++, "G5");
        parameters.setString(idx++, paramInfo.getString("acc_type"));

        return template.getData(conn, parameters);
    }



    /* 정기 환매체 이월취소 */
    public static int deleteJeongi(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" DELETE                                               \n");
        sb.append("   FROM M150_MONEYMANAGEMENT_T                        \n");
        sb.append("  WHERE M150_YEAR = ?                                 \n");
        sb.append("    AND M150_ACCTYPE = ?                              \n");
        sb.append("    AND M150_ACCOUNTNO IN (SELECT M120_ACCOUNTNO FROM M120_MONEYDEPOSIT_T   \n");
        sb.append("                 WHERE M120_YEAR = ?                  \n");
        sb.append("                   AND M120_ACCGUBUN = ?              \n");
        sb.append("                   AND M120_MMDATYPE = ?)             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, "G5");
    

        return template.delete(conn, parameters);
    }


    /* mmda 이월취소 */
    public static int deleteMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" DELETE                                              \n");
        sb.append("   FROM M140_MMDAMASTER_T                            \n");
        sb.append("  WHERE M140_YEAR = ?                                \n");
        sb.append("    AND M140_ACCTYPE = ?                             \n");
        sb.append("    AND M140_ACCOUNTNO IN (SELECT M120_ACCOUNTNO FROM M120_MONEYDEPOSIT_T  \n");
        sb.append("                 WHERE M120_YEAR = ?                 \n");
        sb.append("                   AND M120_ACCGUBUN = ?             \n");
        sb.append("                   AND M120_MMDATYPE = ?)            \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, "G5");
    

        return template.delete(conn, parameters);
    }


    /* 예탁 이월취소 */
    public static int deleteYetak(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" DELETE                               \n");
        sb.append("   FROM M120_MONEYDEPOSIT_T           \n");
        sb.append("  WHERE M120_YEAR = ?                 \n");
        sb.append("    AND M120_ACCGUBUN = ?             \n");
        sb.append("    AND M120_MMDATYPE = ?             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, paramInfo.getString("this_year_list"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
        parameters.setString(idx++, "G5");    

        return template.delete(conn, parameters);
    }
}
