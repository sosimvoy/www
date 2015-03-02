/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR061510.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금운용 > 자금운용현황조회
******************************************************/

package com.etax.db.mn06;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR061510 {

	/* 회계코드 조회 */
  public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M360_ACCGUBUN||M360_ACCCODE M360_VALUE \n");
    sb.append("       ,MAX(M360_ACCNAME) M360_ACCNAME         \n");
    sb.append("   FROM M360_ACCCODE_T A                       \n");
    sb.append("       ,M370_SEMOKCODE_T B                     \n");
    sb.append("  WHERE A.M360_YEAR = B.M370_YEAR              \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M370_ACCGUBUN      \n");
    sb.append("    AND A.M360_ACCCODE = B.M370_ACCCODE        \n");
    sb.append("    AND B.M370_WORKGUBUN = '3'                 \n");
		if ("this_year".equals(paramInfo.getString("fis_year")) )	{
			sb.append("    AND A.M360_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");
		} else {
			sb.append("    AND A.M360_YEAR = ?                      \n");
		}		
    sb.append("  GROUP BY A.M360_ACCGUBUN, A.M360_ACCCODE     \n");
    sb.append("  ORDER BY A.M360_ACCGUBUN, A.M360_ACCCODE     \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		if (!"this_year".equals(paramInfo.getString("fis_year")) ) {
			parameters.setString(idx++,  paramInfo.getString("fis_year"));
		}

    return template.getList(conn, parameters);
  }


	/* 잔액명세 조회 */
    public static List<CommonEntity> getHwanList(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT LIST.*												                         \n");
		sb.append("  FROM (														                           \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN						                   \n");
		sb.append("		       FROM (											                         \n");
        sb.append(" SELECT *                                                     \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , '' SINKYU_DATE                                        \n");
        sb.append("      , M140_DEPOSITTYPE DEPOSIT_TYPE                         \n");
    	sb.append("      , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',            \n");
    	sb.append("                    'G2', '환매채',                           \n");
    	sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      , M140_ACCOUNTNO ACCOUNTNO                              \n");
        sb.append("      , '' JWASUNO                                            \n");
        sb.append("      , TO_NUMBER('') DEPOSIT_AMT                             \n");
        sb.append("      , '' MANGI_DATE                                         \n");
        sb.append("      , '' DEPOSIT_DATE                                       \n");
    	sb.append("      , '' RATE                                               \n");
    	sb.append("      , '' CANCEL_DATE                                        \n");
    	sb.append("      , TO_NUMBER('') IJA                                     \n");
        sb.append("      , ( SELECT SUM(CASE WHEN M160_TRANSACTIONTYPE = '1'     \n");
        sb.append("                          THEN M160_TRANSACTIONAMT            \n");
        sb.append("                          WHEN M160_TRANSACTIONTYPE = '2'     \n");
        sb.append("                          THEN -M160_TRANSACTIONAMT           \n");
        sb.append("                          ELSE 0                              \n");
        sb.append("                      END) JAN_AMT                            \n");
    	sb.append("   FROM M160_MMDADETAILS_T                                    \n");
    	sb.append("  WHERE M160_ACCOUNTNO = A.M140_ACCOUNTNO                     \n");
    	sb.append("    AND M160_TRANSACTIONDATE <= ?                             \n");
        sb.append("    AND M140_YEAR = ?                                         \n");
        sb.append("    AND M140_CANCEL_YN = ?                                    \n");
        
        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)                    \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)                    \n");
		}

        sb.append("  ) JAN_AMT                                                   \n");
        sb.append("  FROM M140_MMDAMASTER_T A                                    \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
    	sb.append(" WHERE A.M140_ACCTYPE = B.M360_ACCGUBUN                       \n");
    	sb.append("   AND A.M140_ACCCODE =B.M360_ACCCODE                         \n");
    	sb.append("   AND A.M140_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND A.M140_YEAR = ?                                        \n");
        sb.append("   AND A.M140_CANCEL_YN = ?                                   \n");
        
        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}   
    
        sb.append(" UNION                                                        \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
    	sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
    	sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
    	sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
    	sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
    	sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
    	sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            WHEN M150_CANCELDATE > ?                          \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
    	sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
    	sb.append("     , M360_ACCCODE_T B                                       \n");
    	sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND A.M150_YEAR = B.M360_YEAR                              \n");   // 2013-10-11 추가
        sb.append("   AND M150_YEAR = ?                                          \n");
        
        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}

		sb.append("  AND M150_SINKYUDATE <= ?                        \n");
		sb.append("  AND (M150_CANCELDATE IS NULL                    \n");
		sb.append("       OR M150_CANCELDATE > ? )                   \n");

        sb.append(" )                                                          \n");
		
		if ("".equals(paramInfo.getString("sort_list")) || paramInfo.getString("sort_list") == null ) {
			sb.append(" ORDER BY ACC_CODE, SINKYU_DATE,          \n");
			sb.append("          MANGI_DATE, ACCOUNTNO           \n");
		} else if ("new_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY SINKYU_DATE                         \n");
		} else if ("end_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY MANGI_DATE                         \n");
		} else if ("cancel_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY CANCEL_DATE                         \n");
		}
    
		sb.append("		            ) ORG										             \n");
		sb.append("		      WHERE ROWNUM <= ?									         \n");
		sb.append("       ) LIST												               \n");
		sb.append(" WHERE LIST.RN > ?											             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

  	    parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
        if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	
		}
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	
		}
		parameters.setString(idx++, paramInfo.getString("fixed_date"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

        return template.getList(conn, parameters);
    }



    /* 잔액명세 총카운트 */
    public static CommonEntity getHwanCount(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT COUNT(*) CNT, SUM(JAN_AMT) TOT_AMT                    \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , '' SINKYU_DATE                                        \n");
        sb.append("      , M140_DEPOSITTYPE DEPOSIT_TYPE                         \n");
        sb.append("      , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      , M140_ACCOUNTNO ACCOUNTNO                              \n");
        sb.append("      , '' JWASUNO                                            \n");
        sb.append("      , TO_NUMBER('') DEPOSIT_AMT                             \n");
        sb.append("      , '' MANGI_DATE                                         \n");
        sb.append("      , '' DEPOSIT_DATE                                       \n");
        sb.append("      , '' RATE                                               \n");
        sb.append("      , '' CANCEL_DATE                                        \n");
        sb.append("      , TO_NUMBER('') IJA                                     \n");
        sb.append("      , ( SELECT SUM(CASE WHEN M160_TRANSACTIONTYPE = '1'     \n");
        sb.append("                          THEN M160_TRANSACTIONAMT            \n");
        sb.append("                          WHEN M160_TRANSACTIONTYPE = '2'     \n");
        sb.append("                          THEN -M160_TRANSACTIONAMT           \n");
        sb.append("                          ELSE 0                              \n");
        sb.append("                      END) JAN_AMT                            \n");
        sb.append("   FROM M160_MMDADETAILS_T                                    \n");
        sb.append("  WHERE M160_ACCOUNTNO = A.M140_ACCOUNTNO                     \n");
        sb.append("    AND M160_TRANSACTIONDATE <= ?                             \n");
        sb.append("    AND M140_YEAR = ?                                         \n");
        sb.append("    AND M140_CANCEL_YN = ?                                    \n");
        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}
        sb.append("  ) JAN_AMT                                                   \n");
        sb.append("  FROM M140_MMDAMASTER_T A                                    \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M140_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M140_ACCCODE =B.M360_ACCCODE                         \n");
        sb.append("   AND A.M140_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND A.M140_YEAR = ?                                        \n");
        sb.append("   AND A.M140_CANCEL_YN = ?                                   \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}   
    
        sb.append(" UNION                                                        \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
        sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
        sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
        sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
        sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
        sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            WHEN M150_CANCELDATE > ?                          \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND A.M150_YEAR = B.M360_YEAR                              \n");   // 2013-10-11 추가
        sb.append("   AND M150_YEAR = ?                                          \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}

		sb.append("  AND M150_SINKYUDATE <= ?                                    \n");
		sb.append("  AND (M150_CANCELDATE IS NULL                                \n");
		sb.append("       OR M150_CANCELDATE > ? )                               \n");

        sb.append(" )                                                            \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
        if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
			parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
		parameters.setString(idx++, paramInfo.getString("fixed_date"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
			parameters.setString(idx++, paramInfo.getString("acc_code"));

		}

		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fixed_date"));

        return template.getData(conn, parameters);
    }



    /* 잔액명세(엑셀) */
    public static List<CommonEntity> getHwanExcelList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT *                                                     \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , '' SINKYU_DATE                                        \n");
        sb.append("      , M140_DEPOSITTYPE DEPOSIT_TYPE                         \n");
        sb.append("      , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      , M140_ACCOUNTNO ACCOUNTNO                              \n");
        sb.append("      , '' JWASUNO                                            \n");
        sb.append("      , TO_NUMBER('') DEPOSIT_AMT                             \n");
        sb.append("      , '' MANGI_DATE                                         \n");
        sb.append("      , '' DEPOSIT_DATE                                       \n");
        sb.append("      , '' RATE                                               \n");
        sb.append("      , '' CANCEL_DATE                                        \n");
        sb.append("      , TO_NUMBER('') IJA                                     \n");
        sb.append("      , ( SELECT SUM(CASE WHEN M160_TRANSACTIONTYPE = '1'     \n");
        sb.append("                          THEN M160_TRANSACTIONAMT            \n");
        sb.append("                          WHEN M160_TRANSACTIONTYPE = '2'     \n");
        sb.append("                          THEN -M160_TRANSACTIONAMT           \n");
        sb.append("                          ELSE 0                              \n");
        sb.append("                      END) JAN_AMT                            \n");
        sb.append("   FROM M160_MMDADETAILS_T                                    \n");
        sb.append("  WHERE M160_ACCOUNTNO = A.M140_ACCOUNTNO                     \n");
        sb.append("    AND M160_TRANSACTIONDATE <= ?                             \n");
        sb.append("    AND M140_YEAR = ?                                         \n");
        sb.append("    AND M140_CANCEL_YN = ?                                    \n");
        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}
        sb.append("  ) JAN_AMT                                                   \n");
        sb.append("  FROM M140_MMDAMASTER_T A                                    \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M140_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M140_ACCCODE =B.M360_ACCCODE                         \n");
        sb.append("   AND A.M140_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND A.M140_YEAR = ?                                        \n");
        sb.append("   AND A.M140_CANCEL_YN = ?                                   \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}   
    
        sb.append(" UNION                                                        \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
        sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
        sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
        sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
        sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
        sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND M150_YEAR = ?                                          \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}

		sb.append("  AND M150_SINKYUDATE <= ?                        \n");
		sb.append("  AND (M150_CANCELDATE IS NULL                    \n");
		sb.append("       OR M150_CANCELDATE > ? )                   \n");

        sb.append(" )                                                            \n");
		sb.append(" ORDER BY ACC_CODE, SINKYU_DATE,                              \n");
		sb.append("          MANGI_DATE, ACCOUNTNO                               \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
        if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	
		}
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	
		}

        parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

		parameters.setString(idx++, paramInfo.getString("fixed_date"));
		parameters.setString(idx++, paramInfo.getString("fixed_date"));

        return template.getList(conn, parameters);
    }



    /* 신규해지명세 */
    public static List<CommonEntity> getNewAndCancelList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT LIST.*												 \n");
		sb.append("  FROM (														 \n");
		sb.append("		     SELECT ORG.*, ROWNUM RN						     \n");
		sb.append("		       FROM (											 \n");
        sb.append(" SELECT *                                                     \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");      
        sb.append("       , M360_ACCNAME ACC_NAME                                \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '1'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END SINKYU_DATE                                     \n");         
        sb.append("       , M140_DEPOSITTYPE DEPOSIT_TYPE                        \n");       
        sb.append("       , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',           \n");       
        sb.append("                     'G2', '환매채',                          \n");       
        sb.append("                     'G3', 'MMDA',                            \n");       
        sb.append("                     'G4', '융자금') TYPE_NAME                \n");       
        sb.append("       , M140_ACCOUNTNO ACCOUNTNO                             \n");       
        sb.append("       , '' JWASUNO                                           \n");       
        sb.append("       , M160_TRANSACTIONAMT DEPOSIT_AMT                      \n");       
        sb.append("       , '' MANGI_DATE                                        \n");       
        sb.append("       , '' DEPOSIT_DATE                                      \n");       
        sb.append("       , '' RATE                                              \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '2'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END CANCEL_DATE                                     \n");         
        sb.append("       , TO_NUMBER('') IJA                                    \n");       
        sb.append("       , TO_NUMBER('') JAN_AMT                                \n");       
        sb.append("   FROM M140_MMDAMASTER_T A                                   \n");
        sb.append("      , M160_MMDADETAILS_T B                                  \n");        
        sb.append("      , M360_ACCCODE_T C                                      \n");       
        sb.append("  WHERE A.M140_ACCTYPE = C.M360_ACCGUBUN                      \n");       
        sb.append("    AND A.M140_ACCCODE = C.M360_ACCCODE                       \n");
        sb.append("    AND A.M140_ACCOUNTNO = B.M160_ACCOUNTNO                   \n");            
        sb.append("    AND A.M140_YEAR = C.M360_YEAR                             \n");       
        sb.append("    AND A.M140_YEAR = ?                                       \n");
        sb.append("    AND A.M140_CANCEL_YN = ?                                  \n");
        sb.append("    AND B.M160_TRANSACTIONDATE BETWEEN ? AND ?                \n");
        sb.append("    AND B.M160_TRANSACTIONTYPE = DECODE(?, 'B', '2', 'C', '1')\n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}   
    
        sb.append(" UNION ALL                                                    \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
        sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
        sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
        sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
        sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
        sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND A.M150_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND M150_YEAR = ?                                          \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}

		sb.append("  AND DECODE(?, 'B', M150_CANCELDATE, 'C', M150_SINKYUDATE)   \n");
        sb.append("      BETWEEN ? AND ?                                         \n");
		
        sb.append(" )                                                            \n");
	    if ("".equals(paramInfo.getString("sort_list")) || paramInfo.getString("sort_list") == null ) {
			sb.append(" ORDER BY ACC_CODE, SINKYU_DATE,                            \n");
			sb.append("          MANGI_DATE, ACCOUNTNO                             \n");
		} else if ("new_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY SINKYU_DATE                                       \n");
		} else if ("end_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY MANGI_DATE                                        \n");
		} else if ("cancel_date".equals(paramInfo.getString("sort_list")) )	{
			sb.append(" ORDER BY CANCEL_DATE                                       \n");
		}
    
		sb.append("		            ) ORG										   \n");
		sb.append("		      WHERE ROWNUM <= ?									   \n");
		sb.append("       ) LIST												   \n");
		sb.append(" WHERE LIST.RN > ?											   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
		parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));
        parameters.setString(idx++, paramInfo.getString("src_gubun"));
    

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("src_gubun"));
        parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));

		parameters.setInt(idx++, paramInfo.getInt("toRow"));
		parameters.setInt(idx++, paramInfo.getInt("fromRow"));

        return template.getList(conn, parameters);
    }



    /* 신규해지 총카운트 */
    public static CommonEntity getNewCancelCount(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT COUNT(*) CNT                                          \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");      
        sb.append("       , M360_ACCNAME ACC_NAME                                \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '1'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END SINKYU_DATE                                     \n");         
        sb.append("       , M140_DEPOSITTYPE DEPOSIT_TYPE                        \n");       
        sb.append("       , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',           \n");       
        sb.append("                     'G2', '환매채',                          \n");       
        sb.append("                     'G3', 'MMDA',                            \n");       
        sb.append("                     'G4', '융자금') TYPE_NAME                \n");       
        sb.append("       , M140_ACCOUNTNO ACCOUNTNO                             \n");       
        sb.append("       , '' JWASUNO                                           \n");       
        sb.append("       , TO_NUMBER('') DEPOSIT_AMT                            \n");       
        sb.append("       , '' MANGI_DATE                                        \n");       
        sb.append("       , '' DEPOSIT_DATE                                      \n");       
        sb.append("       , '' RATE                                              \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '2'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END CANCEL_DATE                                     \n");         
        sb.append("       , TO_NUMBER('') IJA                                    \n");       
        sb.append("       , TO_NUMBER('') JAN_AMT                                \n");       
        sb.append("   FROM M140_MMDAMASTER_T A                                   \n");
        sb.append("      , M160_MMDADETAILS_T B                                  \n");        
        sb.append("      , M360_ACCCODE_T C                                      \n");       
        sb.append("  WHERE A.M140_ACCTYPE = C.M360_ACCGUBUN                      \n");       
        sb.append("    AND A.M140_ACCCODE = C.M360_ACCCODE                       \n");
        sb.append("    AND A.M140_ACCOUNTNO = B.M160_ACCOUNTNO                   \n");            
        sb.append("    AND A.M140_YEAR = C.M360_YEAR                             \n");       
        sb.append("    AND A.M140_YEAR = ?                                       \n");
        sb.append("    AND A.M140_CANCEL_YN = ?                                  \n");
        sb.append("    AND B.M160_TRANSACTIONDATE BETWEEN ? AND ?                \n");
        sb.append("    AND B.M160_TRANSACTIONTYPE = DECODE(?, 'B', '2', 'C', '1')\n");
        
        if (!"".equals(paramInfo.getString("acc_code")) ) {
	    	sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}   
    
        sb.append(" UNION ALL                                                    \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
        sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
        sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
        sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
        sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
        sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND A.M150_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND M150_YEAR = ?                                          \n");
        
        if (!"".equals(paramInfo.getString("acc_code")) ) {
	    	sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
	        sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}

		sb.append("  AND DECODE(?, 'B', M150_CANCELDATE, 'C', M150_SINKYUDATE)   \n");
        sb.append("      BETWEEN ? AND ?                                         \n");
		
        sb.append(" )                                                            \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
		parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));
        parameters.setString(idx++, paramInfo.getString("src_gubun"));
    

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("src_gubun"));
        parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));

        return template.getData(conn, parameters);
    }


    /* 신규해지명세(엑셀) */
    public static List<CommonEntity> getNewAndCancelExcelList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT *                                                     \n");
        sb.append("   FROM (                                                     \n");
        sb.append(" SELECT M140_ACCCODE ACC_CODE                                 \n");      
        sb.append("       , M360_ACCNAME ACC_NAME                                \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '1'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END SINKYU_DATE                                     \n");         
        sb.append("       , M140_DEPOSITTYPE DEPOSIT_TYPE                        \n");       
        sb.append("       , DECODE(M140_DEPOSITTYPE, 'G1', '정기예금',           \n");       
        sb.append("                     'G2', '환매채',                          \n");       
        sb.append("                     'G3', 'MMDA',                            \n");       
        sb.append("                     'G4', '융자금') TYPE_NAME                \n");       
        sb.append("       , M140_ACCOUNTNO ACCOUNTNO                             \n");       
        sb.append("       , '' JWASUNO                                           \n");       
        sb.append("       , M160_TRANSACTIONAMT DEPOSIT_AMT                      \n");       
        sb.append("       , '' MANGI_DATE                                        \n");       
        sb.append("       , '' DEPOSIT_DATE                                      \n");       
        sb.append("       , '' RATE                                              \n");       
        sb.append("       , CASE WHEN M160_TRANSACTIONTYPE = '2'                 \n");
        sb.append("              THEN M160_TRANSACTIONDATE                       \n");
        sb.append("              ELSE ''                                         \n");
        sb.append("          END CANCEL_DATE                                     \n");         
        sb.append("       , TO_NUMBER('') IJA                                    \n");       
        sb.append("       , TO_NUMBER('') JAN_AMT                                \n");       
        sb.append("   FROM M140_MMDAMASTER_T A                                   \n");
        sb.append("      , M160_MMDADETAILS_T B                                  \n");        
        sb.append("      , M360_ACCCODE_T C                                      \n");       
        sb.append("  WHERE A.M140_ACCTYPE = C.M360_ACCGUBUN                      \n");       
        sb.append("    AND A.M140_ACCCODE = C.M360_ACCCODE                       \n");
        sb.append("    AND A.M140_ACCOUNTNO = B.M160_ACCOUNTNO                   \n");            
        sb.append("    AND A.M140_YEAR = C.M360_YEAR                             \n");       
        sb.append("    AND A.M140_YEAR = ?                                       \n");
        sb.append("    AND A.M140_CANCEL_YN = ?                                  \n");
        sb.append("    AND B.M160_TRANSACTIONDATE BETWEEN ? AND ?                \n");
        sb.append("    AND B.M160_TRANSACTIONTYPE = DECODE(?, 'B', '2', 'C', '1')\n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M140_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		    sb.append("    AND M140_ACCCODE = SUBSTR(?, 2, 2)            \n");
		}   
    
        sb.append(" UNION ALL                                                    \n");
        sb.append(" SELECT M150_ACCCODE ACC_CODE                                 \n");
        sb.append("      , M360_ACCNAME ACC_NAME                                 \n");
        sb.append("      , M150_SINKYUDATE SINKYU_DATE                           \n"); 
        sb.append("      , M150_DEPOSITTYPE DEPOSIT_TYPE                         \n");  
        sb.append("      , DECODE(M150_DEPOSITTYPE, 'G1', '정기예금',            \n");
        sb.append("                    'G2', '환매채',                           \n");
        sb.append("                    'G3', 'MMDA',                             \n");
        sb.append("                    'G4', '융자금') TYPE_NAME                 \n");
        sb.append("      ,M150_ACCOUNTNO ACCOUNTNO                               \n");
        sb.append("      ,M150_JWASUNO JWASUNO                                   \n");
        sb.append("      ,M150_DEPOSITAMT DEPOSIT_AMT                            \n");
        sb.append("      ,M150_MANGIDATE  MANGI_DATE                             \n");
        sb.append("      ,M150_DEPOSITDATE  DEPOSIT_DATE                         \n");
        sb.append("      ,M150_INTERESTRATE  RATE                                \n");
        sb.append("      ,M150_CANCELDATE CANCEL_DATE                            \n");
        sb.append("      ,M150_INTEREST IJA                                      \n");
        sb.append("      ,CASE WHEN M150_CANCELDATE IS NULL                      \n");
        sb.append("            THEN M150_DEPOSITAMT                              \n");
        sb.append("            ELSE 0                                            \n");
        sb.append("        END JAN_AMT                                           \n");
        sb.append("  FROM M150_MONEYMANAGEMENT_T A                               \n");
        sb.append("     , M360_ACCCODE_T B                                       \n");
        sb.append(" WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN                       \n");
        sb.append("   AND A.M150_ACCCODE = B.M360_ACCCODE                        \n");
        sb.append("   AND A.M150_YEAR = B.M360_YEAR                              \n");
        sb.append("   AND M150_YEAR = ?                                          \n");

        if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)                      \n");
		    sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)                      \n");
		}

		sb.append("  AND DECODE(?, 'B', M150_CANCELDATE, 'C', M150_SINKYUDATE)   \n");
        sb.append("      BETWEEN ? AND ?                                         \n");
		
        sb.append(" )                                                            \n");
		sb.append(" ORDER BY ACC_CODE, SINKYU_DATE,                            \n");
		sb.append("          MANGI_DATE, ACCOUNTNO                             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;

        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
		parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));
        parameters.setString(idx++, paramInfo.getString("src_gubun"));
    

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}

        parameters.setString(idx++, paramInfo.getString("src_gubun"));
        parameters.setString(idx++, paramInfo.getString("start_date"));
        parameters.setString(idx++, paramInfo.getString("end_date"));

        return template.getList(conn, parameters);
    }



    /* 자금운용내역장 */
    public static List<CommonEntity> getTotalList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT *                                                                  \n");
        sb.append("   FROM (                                                                  \n");
        sb.append(" SELECT M120_YEAR ACC_YEAR                                                 \n");
        sb.append("      , M120_SEQ SEQ                                                       \n");
        sb.append("      , M120_DATE ACC_DATE                                                 \n");
        sb.append("      , M120_ACCGUBUN ACC_TYPE                                             \n");
        sb.append("      , M120_ACCCODE ACC_CODE                                              \n");
        sb.append("      , '신규' JUKYO                                                       \n");
        sb.append("      , DECODE(M120_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채',         \n");
	    sb.append("			         'G3', 'MMDA', 'G4', '융자금') DEPOSIT_TYPE               \n");
        sb.append("      , DECODE(M120_MMDATYPE, 'G1', '공금', 'G2', '정기', 'G3', '환매채',  \n");
	    sb.append("			         'G4', 'MMDA', 'G5', '이월') YETAK_NAME                   \n");
        sb.append("      , '' INCHUL_NAME                                                     \n");
        sb.append("      , M120_DEPOSITAMT YETAK_AMT                                          \n");
        sb.append("      , TO_NUMBER('') INCHUL_AMT                                           \n");
        sb.append("   FROM M120_MONEYDEPOSIT_T                                                \n");
        sb.append(" UNION ALL                                                                 \n");
        sb.append(" SELECT M130_YEAR ACC_YEAR                                                 \n");
        sb.append("      , M130_SEQ SEQ                                                       \n");
        sb.append("      , M130_DATE ACC_DATE                                                 \n");
        sb.append("      , M130_ACCGUBUN ACC_TYPE                                             \n");
        sb.append("      , M130_ACCCODE ACC_CODE                                              \n");
        sb.append("      , '해지' JUKYO                                                       \n");
        sb.append("      , DECODE(M130_DEPOSITTYPE, 'G1', '정기예금', 'G2', '환매채',         \n");
	    sb.append("			         'G3', 'MMDA', 'G4', '융자금') DEPOSIT_TYPE               \n");
        sb.append("      , '' YETAK_NAME                                                      \n");
        sb.append("      , DECODE(M130_MMDATYPE, 'G1', '공금', 'G2', '정기', 'G3', '환매채',  \n");
	    sb.append("			         'G4', 'MMDA', 'G5', '이월') INCHUL_TYPE                  \n");
        sb.append("      , TO_NUMBER('') YETAK_AMT                                            \n");
        sb.append("      , M130_INCHULAMT INCHUL_AMT                                          \n");
        sb.append("   FROM M130_MONEYINCHUL_T                                                 \n");
        sb.append("  WHERE M130_MMDA_CANCEL_YN = ?                                            \n");
        sb.append("  )                                                                        \n");
        sb.append(" WHERE ACC_DATE BETWEEN ? AND ?                                            \n");
        if (!"".equals(paramInfo.getString("acc_code")) ) {
            sb.append("   AND ACC_TYPE = SUBSTR(?, 1, 1)                                      \n");
            sb.append("   AND ACC_CODE = SUBSTR(?, 2, 2)                                      \n");
        }
    
        sb.append(" ORDER BY ACC_DATE, SEQ                                                    \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("mmda_cancel"));
        parameters.setString(idx++, paramInfo.getString("start_date"));
		parameters.setString(idx++, paramInfo.getString("end_date"));
        if (!"".equals(paramInfo.getString("acc_code")) ) {
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
		    parameters.setString(idx++, paramInfo.getString("acc_code"));
		}    

        return template.getList(conn, parameters);
    }




	/* 정기 환매채 운용현황 조회 총카운트 
  public static CommonEntity getHwanCount(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT COUNT(1) CNT                                \n");
    sb.append("   FROM M150_MONEYMANAGEMENT_T A                    \n");
    sb.append("       ,M360_ACCCODE_T B                            \n");
    sb.append("  WHERE A.M150_ACCTYPE = B.M360_ACCGUBUN            \n");
    sb.append("    AND A.M150_ACCCODE = B.M360_ACCCODE             \n");
		sb.append("    AND M150_YEAR = ?                               \n");
		if (!"".equals(paramInfo.getString("acc_code")) ) {
			sb.append("    AND M150_ACCTYPE = SUBSTR(?, 1, 1)            \n");
		  sb.append("    AND M150_ACCCODE = SUBSTR(?, 2, 2)            \n");

		}    
		if ("A".equals(paramInfo.getString("src_gubun")) ) {
			sb.append("  AND M150_SINKYUDATE <= ?                        \n");
			sb.append("  AND (M150_CANCELDATE IS NULL                    \n");
			sb.append("       OR M150_CANCELDATE > ? )                   \n");
		} else if ("B".equals(paramInfo.getString("src_gubun")) ) {
			sb.append("  AND M150_CANCELDATE BETWEEN ? AND ?             \n");
		} else if ("C".equals(paramInfo.getString("src_gubun")) ) {
			sb.append("  AND M150_SINKYUDATE BETWEEN ? AND ?             \n");
		} 

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;

		parameters.setString(idx++, paramInfo.getString("fis_year"));	

		if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));		
			parameters.setString(idx++, paramInfo.getString("acc_code"));	

		}
		if ("A".equals(paramInfo.getString("src_gubun")) ) {
			parameters.setString(idx++, paramInfo.getString("fixed_date"));
			parameters.setString(idx++, paramInfo.getString("fixed_date"));
		} else if ("B".equals(paramInfo.getString("src_gubun")) ) {
			parameters.setString(idx++, paramInfo.getString("start_date"));
			parameters.setString(idx++, paramInfo.getString("end_date"));
		} else if ("C".equals(paramInfo.getString("src_gubun")) ) {
			parameters.setString(idx++, paramInfo.getString("start_date"));
			parameters.setString(idx++, paramInfo.getString("end_date"));
		} 

    return template.getData(conn, parameters);
  }

  
  /* 자금현황조회- MMDA전일 까지 누계액 
  public static CommonEntity getMMDAJan(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT NVL(SUM(CASE WHEN M160_TRANSACTIONTYPE = '1' \n");
		sb.append("             THEN NVL(M160_TRANSACTIONAMT, 0)        \n");
		sb.append("             ELSE -NVL(M160_TRANSACTIONAMT, 0)       \n");
		sb.append("         END),0) JAN_AMT                             \n");
    sb.append("   FROM M140_MMDAMASTER_T A                          \n");
    sb.append("       ,M160_MMDADETAILS_T B                         \n");
    sb.append("  WHERE A.M140_ACCOUNTNO = B.M160_ACCOUNTNO          \n");
    sb.append("    AND A.M140_ACCTYPE = B.M160_ACCTYPE              \n");
		if (!"".equals(paramInfo.getString("acc_code")) ) {
      sb.append("    AND M160_ACCTYPE = SUBSTR(?, 1,1)              \n");
      sb.append("    AND M160_ACCCODE = SUBSTR(?, 2,2)              \n");
		}
    sb.append("    AND A.M140_DEPOSITTYPE = ?                       \n");
    sb.append("    AND B.M160_TRANSACTIONDATE < ?                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
    parameters.setString(idx++, paramInfo.getString("md_gubun"));
    parameters.setString(idx++, paramInfo.getString("start_date"));

    return template.getData(conn, parameters);
  }


	/* 자금현황조회- MMDA 
  public static List<CommonEntity> getMmdaList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT M160_TRANSACTIONDATE                   \n");
    sb.append("       ,M160_TRANSACTIONAMT                    \n");
    sb.append("       ,JIBUL_AMT                              \n");
    sb.append("       ,HWAN_AMT                               \n");
    sb.append("       ,CASE WHEN JIBUL_AMT <> 0               \n");
    sb.append("              AND HWAN_AMT = 0                 \n");
    sb.append("             THEN '신규'                       \n");
    sb.append("             WHEN JIBUL_AMT = 0                \n");
    sb.append("              AND HWAN_AMT <> 0                \n");
    sb.append("             THEN '해지'                       \n");
    sb.append("         END JUKYO                             \n");
		sb.append("       ,DECODE(JIBUL_TYPE, 'G1', '정기예금',   \n");
		sb.append("        'G2', '환매채', 'G3', 'MMDA',          \n");
		sb.append("        '') JIBUL_NAME                         \n");
		sb.append("       ,DECODE(HWAN_TYPE, 'G1', '정기예금',    \n");
		sb.append("        'G2', '환매채', 'G3', 'MMDA',          \n");
		sb.append("        '') HWAN_NAME, M160_SEQ                \n");
    sb.append("   FROM (                                      \n");
    sb.append(" SELECT B.M160_ACCOUNTNO                       \n");
    sb.append("       ,B.M160_ACCTYPE                         \n");
		sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '1'   \n");
		sb.append("             THEN M160_MMDATYPE                \n");
		sb.append("             ELSE ''                           \n");
		sb.append("         END JIBUL_TYPE                        \n");
		sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '2'   \n");
		sb.append("             THEN M160_MMDATYPE                \n");
		sb.append("             ELSE ''                           \n");
		sb.append("         END HWAN_TYPE                         \n");
    sb.append("       ,M160_TRANSACTIONDATE                   \n");
    sb.append("       ,M160_TRANSACTIONAMT                    \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '1'   \n");
    sb.append("             THEN M160_TRANSACTIONAMT          \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END JIBUL_AMT                         \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '2'   \n");
    sb.append("             THEN M160_TRANSACTIONAMT          \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END HWAN_AMT                          \n");
    sb.append("       ,M160_SEQ                               \n");
    sb.append("   FROM M140_MMDAMASTER_T A                    \n");
    sb.append("       ,M160_MMDADETAILS_T B                   \n");
    sb.append("  WHERE A.M140_ACCOUNTNO = B.M160_ACCOUNTNO    \n");
    sb.append("    AND A.M140_ACCTYPE = B.M160_ACCTYPE        \n");
		if (!"".equals(paramInfo.getString("acc_code")) ) {
      sb.append("    AND M160_ACCTYPE = SUBSTR(?, 1,1)        \n");
      sb.append("    AND M160_ACCCODE = SUBSTR(?, 2,2)        \n");
		}
    sb.append("    AND A.M140_DEPOSITTYPE = ?                 \n");
    sb.append("    AND B.M160_TRANSACTIONDATE BETWEEN ? AND ? \n");
    sb.append("       )                                       \n");
    sb.append(" ORDER BY M160_SEQ                             \n");


    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
    parameters.setString(idx++, paramInfo.getString("md_gubun"));
    parameters.setString(idx++, paramInfo.getString("start_date"));
		parameters.setString(idx++, paramInfo.getString("end_date"));

    return template.getList(conn, parameters);
  }


	/* 자금현황조회(엑셀)- MMDA 
  public static List<CommonEntity> getMmdaExcelList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
		sb.append(" SELECT M160_TRANSACTIONDATE                   \n");
    sb.append("       ,M160_TRANSACTIONAMT                    \n");
    sb.append("       ,M140_JANAMT                            \n");
    sb.append("       ,JIBUL_AMT                              \n");
    sb.append("       ,JIBUL_JANAMT                           \n");
    sb.append("       ,HWAN_AMT                               \n");
    sb.append("       ,HWAN_JANAMT                            \n");
    sb.append("       ,CASE WHEN JIBUL_AMT <> 0               \n");
    sb.append("              AND HWAN_AMT = 0                 \n");
    sb.append("             THEN '신규'                       \n");
    sb.append("             WHEN JIBUL_AMT = 0                \n");
    sb.append("              AND HWAN_AMT <> 0                \n");
    sb.append("             THEN '해지'                       \n");
    sb.append("         END JUKYO                             \n");
		sb.append("       ,DECODE(JIBUL_TYPE, 'G1', '정기예금',   \n");
		sb.append("        'G2', '환매채', 'G3', 'MMDA',          \n");
		sb.append("        '') JIBUL_NAME                         \n");
		sb.append("       ,DECODE(HWAN_TYPE, 'G1', '정기예금',    \n");
		sb.append("        'G2', '환매채', 'G3', 'MMDA',          \n");
		sb.append("        '') HWAN_NAME, M160_SEQ                \n");
    sb.append("   FROM (                                      \n");
    sb.append(" SELECT B.M160_ACCOUNTNO                       \n");
    sb.append("       ,A.M140_JANAMT                          \n");
    sb.append("       ,B.M160_ACCTYPE                         \n");
		sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '1'   \n");
		sb.append("             THEN M160_MMDATYPE                \n");
		sb.append("             ELSE ''                           \n");
		sb.append("         END JIBUL_TYPE                        \n");
		sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '2'   \n");
		sb.append("             THEN M160_MMDATYPE                \n");
		sb.append("             ELSE ''                           \n");
		sb.append("         END HWAN_TYPE                         \n");
    sb.append("       ,M160_TRANSACTIONDATE                   \n");
    sb.append("       ,M160_TRANSACTIONAMT                    \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '1'   \n");
    sb.append("             THEN M160_TRANSACTIONAMT          \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END JIBUL_AMT                         \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '1'   \n");
    sb.append("             THEN M140_JANAMT                  \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END JIBUL_JANAMT                      \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '2'   \n");
    sb.append("             THEN M160_TRANSACTIONAMT          \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END HWAN_AMT                          \n");
    sb.append("       ,CASE WHEN M160_TRANSACTIONTYPE = '2'   \n");
    sb.append("             THEN M140_JANAMT                  \n");
    sb.append("             ELSE 0                            \n");
    sb.append("         END HWAN_JANAMT                       \n");
    sb.append("       ,M160_SEQ                               \n");
    sb.append("   FROM M140_MMDAMASTER_T A                    \n");
    sb.append("       ,M160_MMDADETAILS_T B                   \n");
    sb.append("  WHERE A.M140_ACCOUNTNO = B.M160_ACCOUNTNO    \n");
    sb.append("    AND A.M140_ACCTYPE = B.M160_ACCTYPE        \n");
		if (!"".equals(paramInfo.getString("acc_code")) ) {
      sb.append("    AND M160_ACCTYPE = SUBSTR(?, 1,1)        \n");
      sb.append("    AND M160_ACCCODE = SUBSTR(?, 2,2)        \n");
		}
    sb.append("    AND A.M140_DEPOSITTYPE = ?                 \n");
    sb.append("    AND B.M160_TRANSACTIONDATE BETWEEN ? AND ? \n");
    sb.append("       )                                       \n");
    sb.append(" ORDER BY M160_SEQ                             \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    if (!"".equals(paramInfo.getString("acc_code")) ) {
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		  parameters.setString(idx++, paramInfo.getString("acc_code"));
		}
    parameters.setString(idx++, paramInfo.getString("md_gubun"));
    parameters.setString(idx++, paramInfo.getString("start_date"));
		parameters.setString(idx++, paramInfo.getString("end_date"));

    return template.getList(conn, parameters);
  }
  */
}
