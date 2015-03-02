/*****************************************************
* 프로젝트명     : 울산광역시 세입및 자금배정시스템
* 프로그램명     : IR011210.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 세입 > GIRO대사등록
******************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.etax.framework.query.*;
import com.etax.trans.data.GiroBody;
import com.etax.util.TextHandler;
import com.etax.entity.CommonEntity;

public class IR011210 {

    private static Logger logger = Logger.getLogger(IR011210.class);

    // 일일마감체크
    public static CommonEntity getDailyInfo(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT M210_WORKENDSTATE  \n"); 
        sb.append("   FROM M210_WORKEND_T     \n");
        sb.append("  WHERE M210_YEAR = ?      \n");
        sb.append("    AND M210_DATE = ?      \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("job_dt"));

		return template.getData(conn, parameters);
    }


	// 중복 자료 체크
    public static CommonEntity getDataInfo(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(1) CNT        \n"); 
        sb.append("   FROM GIRO_DAESA_TAB      \n");
        sb.append("  WHERE AC_DATE = ?         \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++, paramInfo.getString("job_dt"));

		return template.getData(conn, parameters);
    }

	// GIRO자료 입력
    public static int insertData(Connection conn, CommonEntity paramInfo, GiroBody txtBody) throws SQLException {

        StringBuffer sb = new StringBuffer();

        sb.append(" INSERT INTO GIRO_DAESA_TAB                         \n");
        sb.append(" (  AC_DATE                                         \n");
        sb.append("  , WORK_TYPE                                       \n");
        sb.append("  , DATA_TYPE                                       \n");
        sb.append("  , SERIAL_NO                                       \n");
        sb.append("  , PUBLISHING_OFFICE                               \n");
        sb.append("  , GIRO_NO                                         \n");
        sb.append("  , BANK_CD                                         \n");
        sb.append("  , TAX_JUMIN                                       \n");
        sb.append("  , TAX_NO                                          \n");
        sb.append("  , ELEC_PAY_NO                                     \n");
        sb.append("  , RECP_AMT                                        \n");
        sb.append("  , PAY_BANK_OFFICE                                 \n");
        sb.append("  , PAYER_JUMIN                                     \n");
        sb.append("  , PAY_SYSTEM                                      \n");
        sb.append("  , PAY_TYPE                                        \n");
        sb.append("  , CONFIRM_NO                                      \n");
        sb.append("  , INSTALL_CNT                                     \n");
        sb.append("  , PAY_IN_AMT                                      \n");
        sb.append("  , PAY_OUT_AMT                                     \n");
        sb.append("  , PAY_IN_DATE                                     \n");
        sb.append("  , ITEM_AMT1                                       \n");
        sb.append("  , ITEM_AMT2                                       \n");
        sb.append("  , ITEM_AMT3                                       \n");
        sb.append("  , ITEM_AMT4                                       \n");
        sb.append("  , ITEM_AMT5                                       \n");
        sb.append("  , CHECK_YN                                        \n");
        sb.append("  , PAY_SERIAL_NO                                   \n");
        sb.append("  , DEAL_BANK_OFFICE                                \n");
        sb.append("  , TML_NO                                          \n");
        sb.append("  , FILLER                                          \n");
        sb.append("  , USER_ID                                         \n");
        sb.append("  , REG_DT )                                        \n");
        sb.append("  VALUES                                            \n");
        sb.append(" (                                                  \n");
        sb.append("    ? --AC_DATE                                     \n");
        sb.append("  , ? --WORK_TYPE                                   \n");
        sb.append("  , ? --DATA_TYPE                                   \n");
        sb.append("  , ? --SERIAL_NO                                   \n");
        sb.append("  , ? --PUBLISHING_OFFICE                           \n");
        sb.append("  , ? --GIRO_NO                                     \n");
        sb.append("  , ? --BANK_CD                                     \n");
        sb.append("  , ? --TAX_JUMIN                                   \n");
        sb.append("  , ? --TAX_NO                                      \n");
        sb.append("  , ? --ELEC_PAY_NO                                 \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --RECP_AMT                 \n");
        sb.append("  , ? --PAY_BANK_OFFICE                             \n");
        sb.append("  , ? --PAYER_JUMIN                                 \n");
        sb.append("  , ? --PAY_SYSTEM                                  \n");
        sb.append("  , ? --PAY_TYPE                                    \n");
        sb.append("  , ? --CONFIRM_NO                                  \n");
        sb.append("  , ? --INSTALL_CNT                                 \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --PAY_IN_AMT               \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --PAY_OUT_AMT              \n");
        sb.append("  , ? --PAY_IN_DATE                                 \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --ITEM_AMT1                \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --ITEM_AMT2                \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --ITEM_AMT3                \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --ITEM_AMT4                \n");
        sb.append("  , TO_NUMBER(NVL(?, 0)) --ITEM_AMT5                \n");
        sb.append("  , ? --CHECK_YN                                    \n");
        sb.append("  , ? --PAY_SERIAL_NO                               \n");
        sb.append("  , ? --DEAL_BANK_OFFICE                            \n");
        sb.append("  , ? --TML_NO                                      \n");
        sb.append("  , ? --FILLER                                      \n");
        sb.append("  , ? --USER_ID                                     \n");
        sb.append("  , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') --REG_DT   \n");
        sb.append(" )                                                  \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("job_dt"));
		parameters.setString(idx++, txtBody.getMsgField("data01"));
		parameters.setString(idx++, txtBody.getMsgField("data02"));
		parameters.setString(idx++, txtBody.getMsgField("data03"));
		parameters.setString(idx++, txtBody.getMsgField("data04"));
		parameters.setString(idx++, txtBody.getMsgField("data05"));
		parameters.setString(idx++, txtBody.getMsgField("data06"));
		parameters.setString(idx++, txtBody.getMsgField("data07"));
		parameters.setString(idx++, txtBody.getMsgField("data08"));
		parameters.setString(idx++, txtBody.getMsgField("data09"));
		parameters.setString(idx++, txtBody.getMsgField("data10"));
		parameters.setString(idx++, txtBody.getMsgField("data11"));
		parameters.setString(idx++, txtBody.getMsgField("data12"));
		parameters.setString(idx++, txtBody.getMsgField("data13"));
		parameters.setString(idx++, txtBody.getMsgField("data14"));
		parameters.setString(idx++, txtBody.getMsgField("data15"));
		parameters.setString(idx++, txtBody.getMsgField("data16"));
		parameters.setString(idx++, txtBody.getMsgField("data17"));
		parameters.setString(idx++, txtBody.getMsgField("data18"));
		parameters.setString(idx++, TextHandler.formatDate(txtBody.getMsgField("data19"), "yyMMdd", "yyyyMMdd"));
		parameters.setString(idx++, txtBody.getMsgField("data20"));
		parameters.setString(idx++, txtBody.getMsgField("data21"));
		parameters.setString(idx++, txtBody.getMsgField("data22"));
		parameters.setString(idx++, txtBody.getMsgField("data23"));
		parameters.setString(idx++, txtBody.getMsgField("data24"));
		parameters.setString(idx++, txtBody.getMsgField("data25"));
		parameters.setString(idx++, txtBody.getMsgField("data26"));
		parameters.setString(idx++, txtBody.getMsgField("data27"));
		parameters.setString(idx++, txtBody.getMsgField("data28"));
		parameters.setString(idx++, txtBody.getMsgField("data29"));
		parameters.setString(idx++, paramInfo.getString("user_id"));

		return template.insert(conn, parameters);
    }

    
    
    
    
    
    // GIRO대사 조회
    public static CommonEntity getGiroData(Connection conn, CommonEntity paramInfo) throws SQLException {
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT AC_DATE                                                                                                           \n");
        sb.append("     , GET_BUSINESSDAY(AC_DATE, '+', 7) AS ILBAN_DATE                                                                    \n");//일반이체일(7영업일)
        sb.append("     , GET_BUSINESSDAY(SUBSTR(TO_CHAR(ADD_MONTHS(TO_DATE(AC_DATE),  1), 'YYYYMMDD'), 1, 6)||'13', '+', 0) AS CARD_DATE   \n");//카드이체일(익월 13일 영업일체크)
        sb.append("     , CAR_BON_ILCNT                                                                                                     \n");//일반이체차량본세건수
        sb.append("     , CAR_BON_ILAMT                                                                                                     \n");//일반이체차량본세금액
        sb.append("     , CAR_NONG_ILCNT                                                                                                    \n");
        sb.append("     , CAR_NONG_ILAMT                                                                                                    \n");
        sb.append("     , MAC_BON_ILCNT                                                                                                     \n");
        sb.append("     , MAC_BON_ILAMT                                                                                                     \n");
        sb.append("     , MAC_EDU_ILCNT                                                                                                     \n");
        sb.append("     , MAC_EDU_ILAMT                                                                                                     \n");
        sb.append("     , MAC_NONG_ILCNT                                                                                                    \n");
        sb.append("     , MAC_NONG_ILAMT                                                                                                    \n");
        sb.append("     , CAR_BON_CACNT                                                                                                     \n");
        sb.append("     , CAR_BON_CAAMT                                                                                                     \n");
        sb.append("     , CAR_NONG_CACNT                                                                                                    \n");
        sb.append("     , CAR_NONG_CAAMT                                                                                                    \n");
        sb.append("     , MAC_BON_CACNT                                                                                                     \n");
        sb.append("     , MAC_BON_CAAMT                                                                                                     \n");
        sb.append("     , MAC_EDU_CACNT                                                                                                     \n");
        sb.append("     , MAC_EDU_CAAMT                                                                                                     \n");
        sb.append("     , MAC_NONG_CACNT                                                                                                    \n");
        sb.append("     , MAC_NONG_CAAMT                                                                                                    \n");
        sb.append("     , (CAR_BON_ILCNT + MAC_BON_ILCNT) AS ILCNT --일반이체 합계건수                                                      \n");
        sb.append("     , (CAR_BON_ILAMT + CAR_NONG_ILAMT + MAC_BON_ILAMT + MAC_EDU_ILAMT + MAC_NONG_ILAMT) AS ILAMT  --일반이체 합계금액   \n");
        sb.append("     , (CAR_BON_CACNT + MAC_BON_CACNT) AS CACNT --신용카드 합계건수                                                      \n");
        sb.append("     , (CAR_BON_CAAMT + CAR_NONG_CAAMT + MAC_BON_CAAMT + MAC_EDU_CAAMT + MAC_NONG_CAAMT) AS CAAMT  --신용카드 합계금액   \n");
        sb.append("     , (CAR_BON_ILCNT + CAR_BON_CACNT) AS CAR_CNT -- 차량합계건수                                                        \n");
        sb.append("     , (CAR_BON_ILAMT + CAR_BON_CAAMT) AS CAR_BON_AMT  --차량본세금액                                                    \n");
        sb.append("     , (CAR_NONG_ILCNT + CAR_NONG_CACNT) AS CAR_NONG_CNT -- 차량농특세건수                                               \n");
        sb.append("     , (CAR_NONG_ILAMT + CAR_NONG_CAAMT) AS CAR_NONG_AMT  --차량농특세금액                                               \n");
        sb.append("     , (CAR_BON_ILAMT + CAR_NONG_ILAMT + CAR_BON_CAAMT + CAR_NONG_CAAMT) AS CAR_AMT  --차량합계금액                      \n");
        sb.append("     , (MAC_BON_ILCNT + MAC_BON_CACNT) AS MAC_CNT -- 기계장비합계건수                                                    \n");
        sb.append("     , (MAC_BON_ILAMT + MAC_BON_CAAMT) AS MAC_BON_AMT  --기계장비본세금액                                                \n");
        sb.append("     , (MAC_EDU_ILCNT + MAC_EDU_CACNT) AS MAC_EDU_CNT  --기계장비교육세건수                                              \n");
        sb.append("     , (MAC_EDU_ILAMT + MAC_EDU_CAAMT) AS MAC_EDU_AMT  --기계장비교육세금액                                              \n");
        sb.append("     , (MAC_NONG_ILCNT + MAC_NONG_CACNT) AS MAC_NONG_CNT  --기계장비농특세건수                                           \n");
        sb.append("     , (MAC_NONG_ILAMT + MAC_NONG_CAAMT) AS MAC_NONG_AMT  --기계장비농특세금액                                           \n");
        sb.append("     , (MAC_BON_ILAMT + MAC_EDU_ILAMT + MAC_NONG_ILAMT                                                                   \n");
        sb.append("      + MAC_BON_CAAMT + MAC_EDU_CAAMT + MAC_NONG_CAAMT) AS MAC_AMT  --기계장비합계금액                                   \n");
        sb.append("     , CAR_REGI_BON_ILCNT                                                                                                \n");//일반이체등록세 차량본세건수
        sb.append("     , CAR_REGI_BON_ILAMT                                                                                                \n");//일반이체등록세 차량본세금액
        sb.append("     , CAR_REGI_EDU_ILCNT                                                                                                \n");//일반이체등록세 차량교육세건수
        sb.append("     , CAR_REGI_EDU_ILAMT                                                                                                \n");//일반이체등록세 차량교육세금액
        sb.append("     , CAR_REGI_NONG_ILCNT                                                                                               \n");//일반이체등록세 차량농특세건수
        sb.append("     , CAR_REGI_NONG_ILAMT                                                                                               \n");//일반이체등록세 차량농특세금액
        sb.append("     , MAC_REGI_BON_ILCNT                                                                                                \n");//일반이체등록세 기계장비본세건수
        sb.append("     , MAC_REGI_BON_ILAMT                                                                                                \n");//일반이체등록세 기계장비본세금액
        sb.append("     , MAC_REGI_EDU_ILCNT                                                                                                \n");//일반이체등록세 기계장비교육세건수
        sb.append("     , MAC_REGI_EDU_ILAMT                                                                                                \n");//일반이체등록세 기계장비교육세건수
        sb.append("     , MAC_REGI_NONG_ILCNT                                                                                               \n");//일반이체등록세 기계장비농특세건수
        sb.append("     , MAC_REGI_NONG_ILAMT                                                                                               \n");//일반이체등록세 기계장비농특세건수
        sb.append("     , CAR_REGI_BON_CACNT                                                                                                \n");//신용카드등록세 차량본세건수
        sb.append("     , CAR_REGI_BON_CAAMT                                                                                                \n");//신용카드등록세 차량본세금액
        sb.append("     , CAR_REGI_EDU_CACNT                                                                                                \n");//신용카드등록세 차량교육세건수
        sb.append("     , CAR_REGI_EDU_CAAMT                                                                                                \n");//신용카드등록세 차량교육세금액
        sb.append("     , CAR_REGI_NONG_CACNT                                                                                               \n");//신용카드등록세 차량농특세건수
        sb.append("     , CAR_REGI_NONG_CAAMT                                                                                               \n");//신용카드등록세 차량농특세금액
        sb.append("     , MAC_REGI_BON_CACNT                                                                                                \n");//신용카드등록세 기계장비본세건수
        sb.append("     , MAC_REGI_BON_CAAMT                                                                                                \n");//신용카드등록세 기계장비본세금액
        sb.append("     , MAC_REGI_EDU_CACNT                                                                                                \n");//신용카드등록세 기계장비교육세건수
        sb.append("     , MAC_REGI_EDU_CAAMT                                                                                                \n");//신용카드등록세 기계장비교육세금액
        sb.append("     , MAC_REGI_NONG_CACNT                                                                                               \n");//신용카드등록세 기계장비농특세건수
        sb.append("     , MAC_REGI_NONG_CAAMT                                                                                               \n");//신용카드등록세 기계장비농특세금액
        sb.append("     , (CAR_REGI_BON_ILCNT + MAC_REGI_BON_ILCNT) AS REGI_ILCNT                                                           \n");//일반이체등록세합계건수
        sb.append("     , (CAR_REGI_BON_ILAMT + CAR_REGI_EDU_ILAMT + CAR_REGI_NONG_ILAMT                                                    \n");
        sb.append("      + MAC_REGI_BON_ILAMT + MAC_REGI_EDU_ILAMT + MAC_REGI_NONG_ILAMT) AS REGI_ILAMT                                     \n");//일반이체등록세 합계금액
        sb.append("     , (CAR_REGI_BON_CACNT + MAC_REGI_BON_CACNT) AS REGI_CACNT                                                           \n");//신용카드등록세 합계건수
        sb.append("     , (CAR_REGI_BON_CAAMT + CAR_REGI_EDU_CAAMT + CAR_REGI_NONG_CAAMT                                                    \n");
        sb.append("      + MAC_REGI_BON_CAAMT + MAC_REGI_EDU_CAAMT + MAC_REGI_NONG_CAAMT) AS REGI_CAAMT                                     \n");//신용카드등록세 합계금액
        sb.append("     , (CAR_REGI_BON_ILCNT + CAR_REGI_BON_CACNT) AS CAR_REGI_CNT                                                         \n");//등록세 차량합계건수
        sb.append("     , (CAR_REGI_BON_ILAMT + CAR_REGI_BON_CAAMT) AS CAR_REGI_BON_AMT                                                     \n");//등록세 차량본세금액
        sb.append("     , (CAR_REGI_EDU_ILCNT + CAR_REGI_EDU_CACNT) AS CAR_REGI_EDU_CNT                                                     \n");//등록세 차량교육세건수
        sb.append("     , (CAR_REGI_EDU_ILAMT + CAR_REGI_EDU_CAAMT) AS CAR_REGI_EDU_AMT                                                     \n");//등록세 차량교육세금액
        sb.append("     , (CAR_REGI_NONG_ILCNT + CAR_REGI_NONG_CACNT) AS CAR_REGI_NONG_CNT                                                  \n");//등록세 차량농특세건수
        sb.append("     , (CAR_REGI_NONG_ILAMT + CAR_REGI_NONG_CAAMT) AS CAR_REGI_NONG_AMT                                                  \n");//등록세 차량농특세금액
        sb.append("     , (CAR_REGI_BON_ILAMT + CAR_REGI_EDU_ILAMT + CAR_REGI_NONG_ILAMT                                                    \n");
        sb.append("      + CAR_REGI_BON_CAAMT + CAR_REGI_EDU_CAAMT + CAR_REGI_NONG_CAAMT) AS CAR_REGI_AMT                                   \n");//등록세 차량합계금액
        sb.append("     , (MAC_REGI_BON_ILCNT + MAC_REGI_BON_CACNT) AS MAC_REGI_CNT                                                         \n");//등록세 기계장비합계건수
        sb.append("     , (MAC_REGI_BON_ILAMT + MAC_REGI_BON_CAAMT) AS MAC_REGI_BON_AMT                                                     \n");//등록세 기계장비본세금액
        sb.append("     , (MAC_REGI_EDU_ILCNT + MAC_REGI_EDU_CACNT) AS MAC_REGI_EDU_CNT                                                     \n");//등록세 기계장비교육세건수
        sb.append("     , (MAC_REGI_EDU_ILAMT + MAC_REGI_EDU_CAAMT) AS MAC_REGI_EDU_AMT                                                     \n");//등록세 기계장비교육세금액
        sb.append("     , (MAC_REGI_NONG_ILCNT + MAC_REGI_NONG_CACNT) AS MAC_REGI_NONG_CNT                                                  \n");//등록세 기계장비농특세건수
        sb.append("     , (MAC_REGI_NONG_ILAMT + MAC_REGI_NONG_CAAMT) AS MAC_REGI_NONG_AMT                                                  \n");//등록세 기계장비농특세금액
        sb.append("     , (MAC_REGI_BON_ILAMT + MAC_REGI_EDU_ILAMT + MAC_REGI_NONG_ILAMT                                                    \n");
        sb.append("      + MAC_REGI_BON_CAAMT + MAC_REGI_EDU_CAAMT + MAC_REGI_NONG_CAAMT) AS MAC_REGI_AMT                                   \n");//등록세 기계장비합계금액
        sb.append("  FROM (                                                                                                                 \n");
        sb.append("       SELECT NVL(MAX(AC_DATE), ?) AS AC_DATE             --회계일자                                                     \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반 차량본세건수                                                   \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_BON_ILCNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반 차량본세금액                                                   \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_BON_ILAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 차량농특세건수                                                  \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_NONG_ILCNT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 차량농특세금액                                                  \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_NONG_ILAMT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 기계장비본세건수                                                \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_BON_ILCNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 기계장비본세금액                                                \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_BON_ILAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 기계장비교육세건수                                              \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_EDU_ILCNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반 기계장비교육세금액                                              \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_EDU_ILAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1       --일반 기계장비농특세건수                                               \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_NONG_ILCNT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1      --일반 기계장비농특세금액                                                \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_NONG_ILAMT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2      --신용카드 차량본세건수                                                  \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_BON_CACNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 차량본세금액                                                   \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_BON_CAAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 차량농특세건수                                                 \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_NONG_CACNT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 차량농특세금액                                                 \n");
        sb.append("                        AND TAX_ITEM = '101502'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_NONG_CAAMT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비본세건수                                               \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_BON_CACNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비본세금액                                               \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_BON_CAAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비교육세건수                                             \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_EDU_CACNT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비교육세금액                                             \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_EDU_CAAMT                                                                                    \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비농특세건수                                             \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_NONG_CACNT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 기계장비농특세금액                                             \n");
        sb.append("                        AND TAX_ITEM = '101504'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_NONG_CAAMT                                                                                   \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량본세건수                                         \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_BON_ILCNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량본세금액                                         \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_BON_ILAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량교육세건수                                       \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_EDU_ILCNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량교육세금액                                       \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_EDU_ILAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량농특세건수                                       \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_NONG_ILCNT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1         --일반이체 등록세차량농특세금액                                       \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_NONG_ILAMT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반이체 등록세기계장비본세건수                                      \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_BON_ILCNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반이체 등록세기계장비본세금액                                      \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_BON_ILAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반이체 등록세기계장비교육세건수                                    \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_EDU_ILCNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1        --일반이체 등록세기계장비교육세금액                                    \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_EDU_ILAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1       --일반이체 등록세기계장비농특세건수                                     \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_NONG_ILCNT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 1      --일반이체 등록세기계장비농특세금액                                      \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_NONG_ILAMT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2      --신용카드 등록세차량본세건수                                            \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_BON_CACNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세차량본세금액                                             \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_BON_CAAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세차량교육세건수                                           \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_EDU_CACNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세차량교육세금액                                           \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_EDU_CAAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세차량농특세건수                                           \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_NONG_CACNT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세차량농특세금액                                           \n");
        sb.append("                        AND TAX_ITEM = '102002'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) CAR_REGI_NONG_CAAMT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비본세건수                                         \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND BON_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_BON_CACNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비본세금액                                         \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN BON_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_BON_CAAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비교육세건수                                       \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND EDU_AMT > 0                                                                                  \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_EDU_CACNT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비교육세금액                                       \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN EDU_AMT                                                                                      \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_EDU_CAAMT                                                                               \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비농특세건수                                       \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                        AND NONG_AMT > 0                                                                                 \n");
        sb.append("                       THEN 1                                                                                            \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_NONG_CACNT                                                                              \n");
        sb.append("            , SUM(CASE WHEN SUNAP_TYPE = 2     --신용카드 등록세기계장비농특세금액                                       \n");
        sb.append("                        AND TAX_ITEM = '102004'                                                                          \n");
        sb.append("                       THEN NONG_AMT                                                                                     \n");
        sb.append("                       ELSE 0                                                                                            \n");
        sb.append("                   END) MAC_REGI_NONG_CAAMT                                                                              \n");
        sb.append("            , SUM(X.CITY_AMT) CITY_AMT                                                                                   \n");
        sb.append("            , SUM(X.NONG_AMT) NONG_AMT                                                                                   \n");
        sb.append("            , SUM(X.EDU_AMT) EDU_AMT                                                                                     \n");
        sb.append("            , SUM(CASE WHEN X.BON_AMT > 0 THEN 1 ELSE 0                                                                  \n");
        sb.append("                   END) BON_CNT                                                                                          \n");
        sb.append("            , SUM(CASE WHEN X.CITY_AMT > 0 THEN 1 ELSE 0                                                                 \n");
        sb.append("                   END) CITY_CNT                                                                                         \n");
        sb.append("            , SUM(CASE WHEN X.NONG_AMT > 0 THEN 1 ELSE 0                                                                 \n");
        sb.append("                   END) NONG_CNT                                                                                         \n");
        sb.append("            , SUM(CASE WHEN X.EDU_AMT > 0 THEN 1 ELSE 0                                                                  \n");
        sb.append("                   END) EDU_CNT                                                                                          \n");
        sb.append("         FROM (                                                                                                          \n");
        sb.append("              SELECT AC_DATE                                                                                             \n");
        sb.append("                   , SUBSTR(TAX_NO,  9, 6) AS TAX_ITEM                                                                   \n");
        sb.append("                   , CASE WHEN SUBSTR(PAY_BANK_OFFICE, 4, 4) = '0000'                                                    \n");
        sb.append("                          THEN 2                                                                                         \n");
        sb.append("                          ELSE 1                                                                                         \n");
        sb.append("                      END SUNAP_TYPE                                                                                     \n");
        sb.append("                   , CASE WHEN PAY_TYPE = 'D'                                                                            \n");
        sb.append("                          THEN PAY_IN_AMT                                                                                \n");
        sb.append("                          ELSE ITEM_AMT1                                                                                 \n");
        sb.append("                      END BON_AMT                                                                                        \n");
        sb.append("                   , ITEM_AMT2 CITY_AMT                                                                                  \n");
        sb.append("                   , ITEM_AMT3 + ITEM_AMT5 NONG_AMT                                                                      \n");
        sb.append("                   , ITEM_AMT4 EDU_AMT                                                                                   \n");
        sb.append("                   , RECP_AMT                                                                                            \n");
        sb.append("                   , 1 CNT                                                                                               \n");
        sb.append("                FROM GIRO_DAESA_TAB                                                                                      \n");
        sb.append("               WHERE AC_DATE = ?                                                                                         \n");
        sb.append("             ) X                                                                                                         \n");
        sb.append("      ) Y                                                                                                                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("job_dt"));
        parameters.setString(idx++, paramInfo.getString("job_dt"));

        return template.getData(conn, parameters);
    }
    


	//GIRO대사 삭제
    public static int deleteGiroDaesa(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM GIRO_DAESA_TAB            \n");
		sb.append("  WHERE AC_DATE = ?                    \n");

    	QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
    	parameters.setString(idx++, paramInfo.getString("job_dt"));

        return template.delete(conn, parameters);
    }
}
