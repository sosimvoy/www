/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금예탁요구조회/취소
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060210 {
    /* 자금예탁 요구조회 */
    public static List<CommonEntity> bankDepositList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
        sb.append(" SELECT M120_YEAR, M120_SEQ, M120_DATE                            \n");
		sb.append("       ,M120_DEPOSITTYPE                                          \n");
        sb.append("       ,DECODE(M120_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채' \n");
		sb.append("                                ,'G3', 'MMDA') DEPOSITTYPE_NAME   \n");
		sb.append("       ,M120_DEPOSITAMT                                           \n");
		sb.append("       ,M120_MANGIDATE, M120_DEPOSITDATE, M120_INTERESTRATE       \n");
		sb.append("       ,DECODE(M120_MMDATYPE, 'G1', '공금예금', 'G2', '정기예금'  \n");
		sb.append("                ,'G3', '환매채', 'G4', 'MMDA'                     \n");
        sb.append("                        ,'G5', '회계연도이월') MMDA_NAME          \n");
		sb.append("       ,M120_DEPOSITSTATE                                         \n");
		sb.append("       ,DECODE(M120_DEPOSITSTATE, 'S1', '등록', 'S2', '승인'      \n");
		sb.append("                                , 'S3', '일계등록') STATE_NAME    \n");
        sb.append("   FROM M120_MONEYDEPOSIT_T                                       \n");
        sb.append("  WHERE M120_DATE = ?                                             \n");
        sb.append("    AND M120_ACCGUBUN = ?                                         \n");
        sb.append("    AND M120_ACCCODE = ?                                          \n");
        sb.append("    AND M120_PARTCODE = ?                                         \n");
        sb.append("    AND M120_MMDATYPE <> 'G5'                                     \n"); //회계연도 이월 건은 제외. 2013.03.14 세정과 요구사항.
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


	/* 자금예탁 요구취소 */
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
