/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR060910.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금인출요구등록
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR060910 {

	/* 자금인출요구조회 */
    public static List<CommonEntity> getInchulReqList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("  SELECT M130_YEAR                                    \n");
        sb.append("        ,M130_SEQ                                     \n");
        sb.append("        ,M130_DATE                                    \n");
        sb.append("        ,DECODE(M130_DEPOSITTYPE, 'G1', '정기예금',   \n");
        sb.append("                       'G2', '환매채',                \n");
        sb.append("                       'G3', 'MMDA') DEPOSIT_NAME     \n");
        sb.append("        ,M130_DEPOSITTYPE                             \n"); 
        sb.append("        ,DECODE(M130_MMDATYPE, 'G1', '공금예금',      \n");
        sb.append("                       'G2', '정기예금',              \n");
        sb.append("                       'G3', '환매채',                \n");
        sb.append("                       'G4', 'MMDA',                  \n");
        sb.append("                       'G5', '회계연도이월') MMDA_TYPE\n");                                              
        sb.append("        ,M130_MMDATYPE                                \n");            
        sb.append("        ,M130_ACCOUNTNO                               \n");
        sb.append("        ,M130_JWASUNO                                 \n");
        sb.append("        ,NVL(M130_JANAMT,0) M130_JANAMT               \n");
        sb.append("        ,NVL(M130_INCHULAMT,0) M130_INCHULAMT         \n");
        sb.append("        ,M130_SINKYUDATE                              \n");
        sb.append("        ,M130_MANGIDATE                               \n");
        sb.append("        ,M130_DEPOSITDATE                             \n");
        sb.append("        ,M130_INTERESTRATE                            \n");
        sb.append("        ,M130_STATECODE                               \n");
        sb.append("        ,M130_MMDA_CANCEL_YN		                     \n");
        sb.append("    FROM M130_MONEYINCHUL_T                           \n");
        sb.append("   WHERE M130_DATE = ?                                \n");
        sb.append("     AND M130_STATECODE = 'S1'                        \n");
        sb.append("   ORDER BY M130_ACCOUNTNO, M130_MANGIDATE, M130_SINKYUDATE   \n");
        
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_date"));

        return template.getList(conn, parameters);
    }


	/* 인출요구 취소 */
  public static int deleteInchul(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" DELETE FROM M130_MONEYINCHUL_T       \n");
    sb.append("  WHERE M130_DATE = ?                 \n");
    sb.append("    AND M130_SEQ = ?                  \n");
    sb.append("     AND M130_STATECODE = 'S1'        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("acc_date"));
    parameters.setString(idx++, paramInfo.getString("seq"));

    return template.insert(conn, parameters);
  }
  
  
  /* 인출요구 취소 */
  public static int updateMMDAReqAmt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M140_MMDAMASTER_T            \n");
    sb.append("    SET M140_REQAMT = M140_REQAMT - "+paramInfo.getLong("input_amt")+" \n");
    sb.append("      , M140_CANCEL_YN = 'N'         \n");
    sb.append("  WHERE M140_YEAR = ?                \n");
    sb.append("    AND M140_ACCOUNTNO = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));

    return template.update(conn, parameters);
  }
  
  
  /* 정기환매채 요구등록상태 수정 */
  public static int updateJeongi(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M150_MONEYMANAGEMENT_T     \n");
    sb.append("    SET M150_REQ_YN = 'N'          \n");
    sb.append("  WHERE M150_YEAR = ?              \n");
    sb.append("    AND M150_ACCOUNTNO = ?         \n");
    sb.append("    AND M150_JWASUNO = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("jwasu_no"));

    return template.update(conn, parameters);
  }

}
