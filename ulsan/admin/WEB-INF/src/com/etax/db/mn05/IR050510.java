/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR050510.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 자금배정 > 계좌등록
******************************************************/

package com.etax.db.mn05;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR050510 {

  /* 부서명조회 */
  public static List<CommonEntity> getDeptList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append("SELECT A.M350_PARTCODE                      \n");
    sb.append("      ,A.M350_PARTNAME                      \n");
    sb.append("  FROM M350_PARTCODE_T A                    \n");
    sb.append("      ,M390_USESEMOKCODE_T B                \n");
    sb.append(" WHERE A.M350_YEAR = B.M390_YEAR            \n");
    sb.append("   AND A.M350_PARTCODE = B.M390_PARTCODE    \n");
		sb.append("   AND A.M350_YEAR = ?                      \n");
    sb.append("   AND B.M390_ACCGUBUN = ?                  \n");
    sb.append(" GROUP BY A.M350_PARTCODE, A.M350_PARTNAME  \n");
		sb.append(" ORDER BY A.M350_PARTCODE                   \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
    parameters.setString(idx++,  paramInfo.getString("acc_type"));

    return template.getList(conn, parameters);
  }


	/* 회계명조회 */
  public static List<CommonEntity> getAccList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT A.M360_ACCCODE                     \n");
    sb.append("       ,A.M360_ACCNAME                     \n");
    sb.append("   FROM M360_ACCCODE_T A                   \n");
    sb.append("       ,M390_USESEMOKCODE_T B              \n");
    sb.append("  WHERE A.M360_YEAR = B.M390_YEAR          \n");
    sb.append("    AND A.M360_ACCGUBUN = B.M390_ACCGUBUN  \n");
    sb.append("    AND A.M360_ACCCODE = B.M390_ACCCODE    \n");
    sb.append("    AND A.M360_YEAR = ?                    \n");
    sb.append("    AND A.M360_ACCGUBUN = ?                \n");
    sb.append("    AND B.M390_PARTCODE = ?                \n");
		sb.append("  GROUP BY A.M360_ACCCODE, A.M360_ACCNAME  \n");
    sb.append("  ORDER BY A.M360_ACCCODE                  \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;

    parameters.setString(idx++,  paramInfo.getString("fis_year"));
		parameters.setString(idx++,  paramInfo.getString("acc_type"));
		parameters.setString(idx++,  paramInfo.getString("dept_cd"));

    return template.getList(conn, parameters);
  }

    /* 계좌정보 조회 */
    public static List<CommonEntity> getAcctInfoList(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT A.M300_YEAR                                                                 \n");
        sb.append("        ,DECODE(A.M300_BANKCODE, '039', '경남은행',                                 \n");
        sb.append("                         '011', '농협') M300_BANKCODE                               \n");
        sb.append("        ,B.M350_PARTNAME                                                            \n");
        sb.append("        ,DECODE(M300_ACCGUBUN, 'A', '일반회계', 'B', '특별회계', 'C', '공기업특별'  \n");
        sb.append("                    , 'D', '세입세출외현금', 'E', '기금') ACC_GUBUN                 \n");
        sb.append("        ,C.M360_ACCNAME                                                             \n");
        sb.append("        ,DECODE(A.M300_ACCOUNTTYPE, '01', '입금'                                    \n");
        sb.append("                                   ,'02', '출금'                                    \n");
        sb.append("                                   ,'03', '조회', '04', '일상'                      \n");
        sb.append("                                   ) TYPE_NAME                                      \n");
        sb.append("        ,A.M300_ACCOUNTTYPE                                                         \n");
        sb.append("        ,A.M300_ACCOUNTNO                                                           \n");
        sb.append("        ,A.M300_ACCNAME                                                             \n");
        sb.append("        ,A.M300_NAME                                                                \n");
        sb.append("        ,A.M300_ACCCODE                                                             \n");
        sb.append("        ,DECODE(A.M300_JUHANGACCYN, 'Y', 'Yes',                                     \n");
        sb.append("                          'N', 'No') M300_JUHANGACCYN                               \n");
        sb.append("    FROM M300_ACCOUNTMANAGE_T A                                                     \n");
        sb.append("        ,M350_PARTCODE_T B                                                          \n");
        sb.append("        ,M360_ACCCODE_T C                                                           \n");
        sb.append("   WHERE A.M300_YEAR = B.M350_YEAR(+)                                               \n");
        sb.append("     AND A.M300_PARTCODE = B.M350_PARTCODE(+)                                       \n");
        sb.append("     AND A.M300_YEAR = C.M360_YEAR(+)                                               \n");
        sb.append("     AND A.M300_ACCGUBUN = C.M360_ACCGUBUN(+)                                       \n");
        sb.append("     AND A.M300_ACCCODE = C.M360_ACCCODE(+)                                         \n");
        if (!"".equals(paramInfo.getString("fis_year")) )	{
            sb.append("  AND A.M300_YEAR = ?                               \n");
		}
        if (!"".equals(paramInfo.getString("acct_gubun")) )	{
			sb.append("  AND A.M300_ACCOUNTTYPE = ?                        \n");
		}
        if (!"03".equals(paramInfo.getString("acct_gubun")) )	{
            if (!"".equals(paramInfo.getString("acc_type")) )	{
                sb.append("     AND A.M300_ACCGUBUN = ?                                                        \n");
            }
        }
        sb.append("   ORDER BY A.M300_PARTCODE                                                         \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		if (!"".equals(paramInfo.getString("fis_year")) )	{
			parameters.setString(idx++, paramInfo.getString("fis_year"));
		}

		if (!"".equals(paramInfo.getString("acct_gubun")) )	{
			parameters.setString(idx++, paramInfo.getString("acct_gubun"));
		}
        if (!"03".equals(paramInfo.getString("acct_gubun")) )	{
            if (!"".equals(paramInfo.getString("acc_type")) )	{
			    parameters.setString(idx++, paramInfo.getString("acc_type"));
		    }
        }

        return template.getList(conn, parameters);
    }


	/* 계좌존재 여부 확인 */
    public static CommonEntity getAcctInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M300_YEAR                                   \n");
        sb.append("       ,M300_BANKCODE                               \n");
        sb.append("       ,DECODE(M300_ACCOUNTTYPE, '01', '입금계좌'   \n");
        sb.append("                                 ,'02', '출금계좌'  \n");
        sb.append("                                 ,'03', '조회계좌'  \n");
        sb.append("               ,'04', '일상경비') TYPE_NAME         \n");
		sb.append("       ,M300_ACCOUNTTYPE                            \n");
		sb.append("       ,M300_ACCOUNTNO                              \n");
        sb.append("   FROM M300_ACCOUNTMANAGE_T                        \n");
        sb.append("  WHERE M300_YEAR = ?                               \n");
		sb.append("    AND M300_BANKCODE = ?                           \n");
        sb.append("    AND M300_ACCOUNTNO = ?                          \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("bank_cd"));
	    parameters.setString(idx++, paramInfo.getString("acct_no"));

        return template.getData(conn, parameters);
    }


	/* 회계코드별 계좌존재여부 확인 */
  public static CommonEntity getAcctCnt(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT COUNT(1) CNT            \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T    \n");
    sb.append("  WHERE M300_YEAR = ?           \n");
		sb.append("    AND M300_ACCGUBUN = ?       \n");
    sb.append("    AND M300_ACCCODE = ?        \n");
		sb.append("    AND M300_ACCOUNTTYPE = ?    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
	  parameters.setString(idx++, paramInfo.getString("acc_cd"));
		parameters.setString(idx++, paramInfo.getString("acct_gubun"));

    return template.getData(conn, parameters);
  }


	/* 계좌명 존재여부 확인 */
  public static CommonEntity getAcctNM(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M300_ACCNAME              \n");
		sb.append("       ,M300_ACCOUNTNO            \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T      \n");
		sb.append("  WHERE M300_YEAR = ?             \n");
    sb.append("    AND M300_ACCNAME = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("acct_nm"));

    return template.getData(conn, parameters);
  }


	/* 단말번호 조회 */
  public static CommonEntity getPcNo(Connection conn, String user_id) throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" SELECT M260_TERMINALNO           \n");
		sb.append("       ,SEND_SEQ.NEXTVAL SEND_NO  \n");
    sb.append("   FROM M260_USERMANAGER_T        \n");
		sb.append("  WHERE M260_USERID = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, user_id);

    return template.getData(conn, parameters);
  }
  

	/* 계좌정보 등록 */
  public static int insertAcctInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append("INSERT INTO M300_ACCOUNTMANAGE_T     \n");
    sb.append("(M300_YEAR, M300_BANKCODE,           \n");
		sb.append(" M300_ACCOUNTNO, M300_NAME,          \n");
		sb.append(" M300_ACCOUNTTYPE, M300_PARTCODE,    \n");
		sb.append(" M300_LOGNO, M300_ACCGUBUN,          \n");
		sb.append(" M300_ACCNAME, M300_JUHANGACCYN,     \n");
		sb.append(" M300_STATECODE, M300_ACCCODE )      \n");
		sb.append("VALUES(?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ?,                         \n");
		sb.append("       ?, ? )                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("fis_year"));
		parameters.setString(idx++, paramInfo.getString("bank_cd"));
		parameters.setString(idx++, paramInfo.getString("acct_no"));
		parameters.setString(idx++, paramInfo.getString("owner_nm"));
		parameters.setString(idx++, paramInfo.getString("acct_gubun"));
		parameters.setString(idx++, paramInfo.getString("dept_cd"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("acc_type"));
		parameters.setString(idx++, paramInfo.getString("acct_nm"));
		parameters.setString(idx++, paramInfo.getString("juhaeng"));
		parameters.setString(idx++, paramInfo.getString("state"));
		parameters.setString(idx++, paramInfo.getString("acc_cd"));

    return template.insert(conn, parameters);
  }


	/* 계좌정보 삭제 */
  public static int deleteAcctNoInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append("DELETE FROM M300_ACCOUNTMANAGE_T  \n");
    sb.append(" WHERE M300_ACCOUNTNO = ?         \n");
    sb.append("   AND M300_YEAR = ?           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
		parameters.setString(idx++, paramInfo.getString("acct_no"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));

    return template.delete(conn, parameters);
  }
}
