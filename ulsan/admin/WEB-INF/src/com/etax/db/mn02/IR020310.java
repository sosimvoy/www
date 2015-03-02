/*****************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	     : IR020310.java
* 프로그램작성자   : (주)미르이즈 
* 프로그램작성일   : 2010-07-01
* 프로그램내용	   : 세출 > 수기분등록
******************************************************/
package com.etax.db.mn02;

import java.sql.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR020310 {


    /* 수기분등록  */
    public static int batchInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
		
        sb.append(" INSERT INTO M030_TAXOTHER_T                                    \n");
        sb.append(" SELECT A.FIS_YEAR                                              \n");
        sb.append("         ,M030_SEQ.NEXTVAL                                      \n");
        sb.append("         ,TRNX_YMD                                              \n");
        sb.append("         ,B.ACCGUBUN                                            \n");  //회계구분
        sb.append("         ,B.ACC_CODE                                            \n");  // 회계코드
        sb.append("         ,'1110100'                                             \n"); // 세목코드 1110100 고정
        sb.append("         ,B.PART_CODE                                           \n");  //부서코드
        sb.append("         ,'I1'                                                  \n");  // 입력구분
        sb.append("         ,OUT_ACCT_DETL_ITEM                                    \n");  // 채주성명
        sb.append("         ,TO_NUMBER(PAY_CMD_REGI_NO) AS  PAY_CMD_REGI_NO        \n");  // 지급번호
        sb.append("         ,TRNX_AMT                                              \n");
        sb.append("         ,?                                                     \n");  //로그번호
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