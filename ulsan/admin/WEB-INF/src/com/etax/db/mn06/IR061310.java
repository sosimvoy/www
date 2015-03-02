/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061310.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출요구등록
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061310 {

	/* 자금인출요구등록조회 */
    public static List<CommonEntity> getInchulSpList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT M140_YEAR FIS_YEAR                              \n");
        sb.append("        ,M140_ACCTYPE ACC_TYPE                          \n");
        sb.append("        ,M140_ACCCODE ACC_CODE                          \n");
        sb.append("        ,M140_PARTCODE PART_CODE                        \n");
        sb.append("        ,DECODE(M140_DEPOSITTYPE, 'G3', 'MMDA',         \n");
        sb.append("                       'G4', '융자금') DEPOSIT_NAME     \n"); 
        sb.append("        ,M140_DEPOSITTYPE DEPOSIT_TYPE                  \n");
        sb.append("        ,M140_ACCOUNTNO ACCT_NO                         \n");
        sb.append("        ,'' JWASU_NO                                    \n");
        sb.append("        ,NVL(M140_JANAMT,0) DEP_AMT                     \n");
        sb.append("        ,'' DEP_PERIOD                                  \n");
        sb.append("        ,'' DEP_RATE                                    \n");
        sb.append("        ,'' NEW_DATE                                    \n");
        sb.append("        ,'' END_DATE                                    \n");
        sb.append("    FROM M140_MMDAMASTER_T                              \n");
        sb.append("   WHERE M140_YEAR = ?                                  \n");
        sb.append("     AND M140_ACCTYPE = ?                               \n");
        sb.append("     AND M140_ACCCODE = ?                               \n");
        sb.append("     AND M140_PARTCODE = ?                              \n");
        sb.append("     AND M140_JANAMT >= 0                               \n");
        sb.append("     AND M140_CANCEL_YN = 'N'                           \n");
        sb.append("   UNION ALL                                            \n");
        sb.append("  SELECT M150_YEAR FIS_YEAR                             \n");
        sb.append("        ,M150_ACCTYPE ACC_TYPE                          \n");
        sb.append("        ,M150_ACCCODE ACC_CODE                          \n");
        sb.append("        ,M150_PARTCODE PART_CODE                        \n");
        sb.append("        ,DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',     \n");
        sb.append("                        'G2', '환매채' ) DEPOSIT_NAME   \n"); 
        sb.append("        ,M150_DEPOSITTYPE DEPOSIT_TYPE                  \n"); 
        sb.append("        ,M150_ACCOUNTNO ACCT_NO                         \n");
        sb.append("        ,M150_JWASUNO JWASU_NO                          \n");
        sb.append("        ,NVL(M150_DEPOSITAMT,0) DEP_AMT                 \n");
        sb.append("        ,M150_DEPOSITDATE DEP_PERIOD                    \n");
        sb.append("        ,M150_INTERESTRATE DEP_RATE                     \n");
        sb.append("        ,M150_SINKYUDATE NEW_DATE                       \n");
        sb.append("        ,M150_MANGIDATE END_DATE                        \n");
        sb.append("    FROM M150_MONEYMANAGEMENT_T                         \n");
        sb.append("   WHERE M150_YEAR = ?                                  \n");
        sb.append("     AND M150_ACCTYPE = ?                               \n");
        sb.append("     AND M150_ACCCODE = ?                               \n");
        sb.append("     AND M150_PARTCODE = ?                              \n"); 
        sb.append("     AND M150_CANCELDATE IS NULL                        \n");
        sb.append("   ORDER BY ACCT_NO, END_DATE, NEW_DATE                 \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("acct_kind"));
        parameters.setString(idx++, paramInfo.getString("acct_list"));
        parameters.setString(idx++, paramInfo.getString("part_code"));

        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("acct_kind"));
        parameters.setString(idx++, paramInfo.getString("acct_list"));
        parameters.setString(idx++, paramInfo.getString("part_code"));

        return template.getList(conn, parameters);
    }


  /* 인출테이블 시퀀스 */
  public static CommonEntity getInchulSeq(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT M130_SEQ.NEXTVAL SEQNO FROM DUAL       \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }
  
  
    /* 인출 테이블 등록 */
    public static int insertInchul(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" INSERT INTO M130_MONEYINCHUL_T                              \n");
		sb.append(" (M130_YEAR, M130_SEQ, M130_DATE                             \n");
		sb.append(" ,M130_ACCGUBUN, M130_ACCCODE, M130_PARTCODE                 \n");
		sb.append(" ,M130_DEPOSITTYPE, M130_MMDATYPE, M130_ACCOUNTNO            \n");
		sb.append(" ,M130_JWASUNO, M130_JANAMT, M130_INCHULAMT                  \n");
        sb.append(" ,M130_INCHULDATE, M130_SINKYUDATE, M130_MANGIDATE           \n");
        sb.append(" ,M130_DEPOSITDATE, M130_INTERESTRATE, M130_CANCELINTEREST   \n");
        sb.append(" ,M130_STATECODE, M130_LOGNO3, M130_MMDA_CANCEL_YN )         \n");
        if ("G1".equals(paramInfo.getString("deposit_type")) || "G2".equals(paramInfo.getString("deposit_type")) ) {  //정기, 환매채
            sb.append(" SELECT M150_YEAR, ?, ?                                      \n");
		    sb.append("       ,?, ?, ?                                              \n"); 
		    sb.append("       ,?, ?, ?                                              \n");
		    sb.append("       ,?, ?, ?                                              \n");
            sb.append("       ,?, M150_SINKYUDATE, M150_MANGIDATE                   \n");
            sb.append("       ,M150_DEPOSITDATE, M150_INTERESTRATE, ?               \n");
            sb.append("       ,?, ?, 'N'                                            \n");
		    sb.append("   FROM M150_MONEYMANAGEMENT_T                               \n");
            sb.append("  WHERE M150_YEAR = ?                                        \n");
		    sb.append("    AND M150_ACCOUNTNO = ?                                   \n");
            sb.append("    AND M150_JWASUNO = ?                                     \n");

        } else if ("G3".equals(paramInfo.getString("deposit_type")) || "G4".equals(paramInfo.getString("deposit_type")) ) {   //MMDA, 융자금

            sb.append(" SELECT M140_YEAR, ?, ?                                      \n");
		    sb.append("       ,?, ?, ?                                              \n"); 
		    sb.append("       ,?, ?, ?                                              \n");
		    sb.append("       ,?, ?, ?                                              \n");
            sb.append("       ,?, '', ''                                            \n");
            sb.append("       ,?, '', ?                                             \n");
            sb.append("       ,?, ?, ?                                              \n");
		    sb.append("   FROM M140_MMDAMASTER_T                                    \n");
            sb.append("  WHERE M140_YEAR = ?                                        \n");
		    sb.append("    AND M140_ACCOUNTNO = ?                                   \n");
        }
		

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        if ("G1".equals(paramInfo.getString("deposit_type")) || "G2".equals(paramInfo.getString("deposit_type")) ) {  //정기, 환매채

			parameters.setString(idx++, paramInfo.getString("m130_seq"));
			parameters.setString(idx++, paramInfo.getString("cancel_date"));
            parameters.setString(idx++, paramInfo.getString("acct_kind"));
            parameters.setString(idx++, paramInfo.getString("acct_list"));
            parameters.setString(idx++, paramInfo.getString("part_code"));
            parameters.setString(idx++, paramInfo.getString("deposit_type"));
            parameters.setString(idx++, paramInfo.getString("mmda_type"));
            parameters.setString(idx++, paramInfo.getString("acct_no"));
            parameters.setString(idx++, paramInfo.getString("jwasu_no"));
            parameters.setString(idx++, paramInfo.getString("dep_amt"));
            parameters.setString(idx++, paramInfo.getString("input_amt"));
            parameters.setString(idx++, paramInfo.getString("cancel_date"));
            parameters.setString(idx++, paramInfo.getString("cancel_ija"));
            parameters.setString(idx++, "S3");
			parameters.setString(idx++, paramInfo.getString("log_no"));
            parameters.setString(idx++, paramInfo.getString("fis_year"));
            parameters.setString(idx++, paramInfo.getString("acct_no"));
            parameters.setString(idx++, paramInfo.getString("jwasu_no"));

        } else if ("G3".equals(paramInfo.getString("deposit_type")) || "G4".equals(paramInfo.getString("deposit_type")) ) {

            parameters.setString(idx++, paramInfo.getString("m130_seq"));
			parameters.setString(idx++, paramInfo.getString("cancel_date"));
            parameters.setString(idx++, paramInfo.getString("acct_kind"));
            parameters.setString(idx++, paramInfo.getString("acct_list"));
            parameters.setString(idx++, paramInfo.getString("part_code"));
            parameters.setString(idx++, paramInfo.getString("deposit_type"));
            parameters.setString(idx++, paramInfo.getString("mmda_type"));
            parameters.setString(idx++, paramInfo.getString("acct_no"));
            parameters.setString(idx++, paramInfo.getString("jwasu_no"));
            parameters.setString(idx++, paramInfo.getString("dep_amt"));
            parameters.setString(idx++, paramInfo.getString("input_amt"));
            parameters.setString(idx++, paramInfo.getString("cancel_date"));
            parameters.setString(idx++, paramInfo.getString("due_day"));
            parameters.setString(idx++, paramInfo.getString("cancel_ija"));
            parameters.setString(idx++, "S3");
			parameters.setString(idx++, paramInfo.getString("log_no"));
            parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
            parameters.setString(idx++, paramInfo.getString("fis_year"));
            parameters.setString(idx++, paramInfo.getString("acct_no"));

        }

        return template.insert(conn, parameters);
    }
  


    /* MMDA 마스터 수정 */
    public static int updateMMDA(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M140_MMDAMASTER_T                      \n");
		sb.append("    SET M140_JANAMT = M140_JANAMT - "+paramInfo.getLong("input_amt")+"  \n");
		sb.append("       ,M140_SEQ = M140_SEQ + 1                \n");
        sb.append("       ,M140_CANCEL_YN = ?                     \n");
        sb.append("  WHERE M140_ACCOUNTNO = ?                     \n");
        sb.append("    AND M140_YEAR = ?                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));

        return template.update(conn, parameters);
    }


  /* MMDA 명세 등록 */
  public static int insertMMDADetail(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" INSERT INTO M160_MMDADETAILS_T                              \n");
		sb.append(" (M160_ACCOUNTNO, M160_SEQ, M160_ACCTYPE                     \n");
		sb.append(" ,M160_ACCCODE, M160_TRANSACTIONDATE, M160_TRANSACTIONTYPE   \n");
		sb.append(" ,M160_MMDATYPE, M160_TRANSACTIONAMT                         \n");
		sb.append(" ,M160_INTEREST, M160_LOGNO, M160_M130SEQ )                  \n");
		sb.append(" VALUES( ?, M160_SEQ.NEXTVAL, ?                              \n");
		sb.append("       ,?, ?, ?                                              \n"); 
		sb.append("       ,?, ?                                                 \n");
		sb.append("       ,?, ?, ?                                              \n");
		sb.append("   )                                                         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acc_code"));
    parameters.setString(idx++, paramInfo.getString("cancel_date"));
    parameters.setString(idx++, "2");
    parameters.setString(idx++, paramInfo.getString("mmda_type"));
    parameters.setString(idx++, paramInfo.getString("input_amt"));
    parameters.setString(idx++, paramInfo.getString("cancel_ija"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
    parameters.setString(idx++, paramInfo.getString("m130_seq"));

    return template.insert(conn, parameters);
  }


    /* 정기예금, 환매채 수정 - 해지일, 해지이자 추가 */
    public static int updateHwan(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" UPDATE M150_MONEYMANAGEMENT_T  \n");
		sb.append("    SET M150_CANCELDATE = ?     \n");
        sb.append("       ,M150_INTEREST = ?       \n");
        sb.append("       ,M150_M130SEQ = ?        \n");
        sb.append("  WHERE M150_YEAR = ?           \n");
		sb.append("    AND M150_ACCOUNTNO = ?      \n");
        sb.append("    AND M150_JWASUNO = ?        \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("cancel_date"));
        parameters.setString(idx++, paramInfo.getString("cancel_ija"));
        parameters.setString(idx++, paramInfo.getString("m130_seq"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("jwasu_no"));

        return template.update(conn, parameters);
    }

}
