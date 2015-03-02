/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR071510.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2014-12-19
* 프로그램내용   : 일계보고서 > 본청세출조회
****************************************************************/

package com.etax.db.mn07;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR071510 {

    /* 본청세출 조회 */
    public static List<CommonEntity> getSechulList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT M220_YEAR                                                              \n");
        sb.append("      , M220_DATE                                                              \n");
        sb.append("      , M220_AMTBAJEONGJEONILTOT + M220_AMTBAJEONG as M220_AMTBAJEONGJEONILTOT \n");
        sb.append("      , M220_AMTSECHULJEONILTOT + M220_AMTSECHUL as M220_AMTSECHULJEONILTOT    \n");
        sb.append("      , M220_AMTSECHUL                                                         \n");
        sb.append("      , ((SELECT SUM(CASE WHEN DATA_FG = '45'                                  \n");
        sb.append("                         THEN TRNX_AMT                                         \n");
        sb.append("                         ELSE -TRNX_AMT                                        \n");
        sb.append("                     END ) AMT                                                 \n");
        sb.append("           FROM TRANS_TEF_EFAM026                                              \n");
        sb.append("          WHERE FIS_YEAR = M220_YEAR                                           \n");
        sb.append("            AND TRNX_YMD <= M220_DATE                                          \n");
        sb.append("            AND DATA_FG IN ('45', '46')                                        \n");
        sb.append("            AND FIS_FG_CD = '100'                                              \n");
        sb.append("            AND BANK_PROCESS_YN = '0'                                          \n");
        sb.append("          )                                                                    \n");
        sb.append("       + (SELECT SUM(M220_AMTSECHULBANNAP)                                     \n");
        sb.append("            FROM M220_DAY_T                                                    \n");
        sb.append("           WHERE M220_YEAR = A.M220_YEAR                                       \n");
        sb.append("             AND M220_DATE BETWEEN '20141201' AND A.M220_DATE                  \n");
        sb.append("             AND M220_ACCTYPE = 'A'                                            \n");
        sb.append("             AND M220_ACCCODE NOT IN ('11',  '21')                             \n");
        sb.append("          ) ) AS NEW_BAEJEONG                                                  \n");
        sb.append("      , ((SELECT SUM(CASE WHEN DATA_FG = '45'                                  \n");
        sb.append("                         THEN TRNX_AMT                                         \n");
        sb.append("                         ELSE -TRNX_AMT                                        \n");
        sb.append("                     END ) AMT                                                 \n");
        sb.append("           FROM TRANS_TEF_EFAM026                                              \n");
        sb.append("          WHERE FIS_YEAR = M220_YEAR                                           \n");
        sb.append("            AND TRNX_YMD = M220_DATE                                           \n");
        sb.append("            AND DATA_FG IN ('45', '46')                                        \n");
        sb.append("            AND FIS_FG_CD = '100'                                              \n");
        sb.append("            AND BANK_PROCESS_YN = '0'                                          \n");
        sb.append("          )                                                                    \n");
        sb.append("       + (SELECT SUM(M220_AMTSECHULBANNAP)                                     \n");
        sb.append("            FROM M220_DAY_T                                                    \n");
        sb.append("           WHERE M220_YEAR = A.M220_YEAR                                       \n");
        sb.append("             AND M220_DATE = A.M220_DATE                                       \n");
        sb.append("             AND M220_ACCTYPE = 'A'                                            \n");
        sb.append("             AND M220_ACCCODE NOT IN ('11',  '21')                             \n");
        sb.append("          ) ) AS BAE_DANG                                                      \n");
        sb.append("       , (SELECT SUM(M220_AMTSECHULBANNAP)                                     \n");
        sb.append("            FROM M220_DAY_T                                                    \n");
        sb.append("           WHERE M220_YEAR = A.M220_YEAR                                       \n");
        sb.append("             AND M220_DATE BETWEEN '20141201' AND A.M220_DATE                  \n");
        sb.append("             AND M220_ACCTYPE = 'A'                                            \n");
        //sb.append("             AND M220_ACCCODE =  '21'                                        \n");
        //sb.append("             AND M220_SEMOKCODE = '1110100'                                  \n");
        sb.append("          ) BON_BANNAP                                                         \n");
        sb.append("       , (SELECT SUM(M220_AMTSECHULBANNAP)                                     \n");
        sb.append("            FROM M220_DAY_T                                                    \n");
        sb.append("           WHERE M220_YEAR = A.M220_YEAR                                       \n");
        sb.append("             AND M220_DATE = A.M220_DATE                                       \n");
        sb.append("             AND M220_ACCTYPE = 'A'                                            \n");
        sb.append("          ) BAN_DANG                                                           \n");
        sb.append("       , (SELECT SUM(CASE WHEN M220_ACCCODE = '11'                             \n");
        sb.append("                           AND M220_PARTCODE <> '00000'                        \n");
        sb.append("                          THEN 0                                               \n");
        sb.append("                          ELSE M220_AMTSECHULJEONILTOT + M220_AMTSECHUL - M220_AMTSECHULBANNAP - M220_AMTSECHULJEONGJEONG \n");
        sb.append("                      END                                                      \n");
        sb.append("                 ) AS SECHUL_TOTAL                                             \n");
        sb.append("            FROM M220_DAY_T                                                    \n");
        sb.append("           WHERE M220_YEAR = A.M220_YEAR                                       \n");
        sb.append("             AND M220_DATE = A.M220_DATE                                       \n");
        sb.append("             AND M220_ACCTYPE = 'A'                                            \n");
        sb.append("          ) SECHUL_TOTAL                                                       \n");
        sb.append("   FROM M220_DAY_T   A                                                         \n");
        sb.append("  WHERE M220_ACCTYPE = 'A'                                                     \n");
        sb.append("    AND M220_ACCCODE = '11'                                                    \n");
        sb.append("    AND M220_PARTCODE = '00000'                                                \n");
        sb.append("    AND M220_SEMOKCODE= '1110100'                                              \n");
        sb.append("    AND M220_SUNAPGIGWANCODE = '110000'                                        \n");
        sb.append("    AND M220_YEAR = ?                                                          \n");
        sb.append("    AND M220_DATE BETWEEN ? AND ?                                              \n");
        sb.append("  ORDER BY M220_DATE                                                           \n");
		
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date_s"));
        parameters.setString(idx++, paramInfo.getString("acc_date_e"));

        return template.getList(conn, parameters);
	}
}