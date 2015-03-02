/*****************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR040110.java
* 프로그램작성자 :
* 프로그램작성일 :
* 프로그램내용   : 세외수입 > 입금내역조회
******************************************************/

package com.etax.db.mn04;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR040110 {
 
	/* 계좌명과 계좌번호 셀렉트박스 */
	public static List<CommonEntity> getAcctList(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
 
	  sb.append(" SELECT M300_ACCOUNTNO,       \n");
		sb.append("        M300_ACCNAME          \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T  \n");
		sb.append("  WHERE M300_YEAR = ?         \n");
		sb.append("    AND M300_BANKCODE = ?     \n");
    sb.append("    AND M300_PARTCODE = '00000' \n");
    sb.append("    AND M300_STATECODE = 'S2' \n");
    sb.append("  ORDER BY M300_ACCOUNTNO     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		int idx = 1;
		parameters.setString(idx++,    paramInfo.getString("fis_year"));
    parameters.setString(idx++,    "039");

    return template.getList(conn, parameters);
  }
  
	 public static int budgetExcelbeforedel(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" DELETE FROM M080_BUDGETMANAGE_T              \n");
		sb.append("       WHERE M080_YEAR = ?                    \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;

    parameters.setString(i++, paramInfo.getString("fis_year"));

		return template.insert(conn, parameters); 
 	}

	 public static int budgetExcelUpload(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M080_BUDGETMANAGE_T              \n");
		sb.append("           ( M080_YEAR,                       \n"); 
    sb.append("             M080_SEQ,                        \n");
		sb.append("             M080_MOK,                        \n");
		sb.append("             M080_SEMOKCODE,                  \n");
		sb.append("             M080_SOGWANPART,                 \n");
		sb.append("             M080_SILGWA,                     \n");
		sb.append("             M080_GWAMOK,                     \n");
		sb.append("             M080_BUSINESSNAME,               \n");
		sb.append("             M080_DANGCHOAMT,                 \n");
		sb.append("             M080_MONTHAMT_1,                 \n");
		sb.append("             M080_MONTHAMT_2,                 \n");
		sb.append("             M080_MONTHAMT_3,                 \n");
		sb.append("             M080_MONTHAMT_4,                 \n");
		sb.append("             M080_MONTHAMT_5,                 \n");
		sb.append("             M080_MONTHAMT_6,                 \n");
		sb.append("             M080_MONTHAMT_7,                 \n");
		sb.append("             M080_MONTHAMT_8,                 \n");
		sb.append("             M080_MONTHAMT_9,                 \n");
		sb.append("             M080_MONTHAMT_10,                \n");
		sb.append("             M080_MONTHAMT_11,                \n");
		sb.append("             M080_MONTHAMT_12,                \n");
		sb.append("             M080_MONTHAMT_13,                \n");
		sb.append("             M080_MONTHAMT_14,                \n");
		sb.append("             M080_WRITEDATE,                  \n");
		sb.append("             M080_CHUKYNGAMT1,                 \n");
		sb.append("             M080_CHUKYNGAMT2,                 \n");
		sb.append("             M080_CHUKYNGAMT3,                 \n");
		sb.append("             M080_CHUKYNGAMT4,                 \n");
		sb.append("             M080_CHUKYNGAMT5,                 \n");
		sb.append("             M080_CHUKYNGAMT6,                 \n");
		sb.append("             M080_CHUKYNGAMT7,                 \n");
		sb.append("             M080_CHUKYNGAMT8,                 \n");
		sb.append("             M080_CHUKYNGAMT9,                 \n");
		sb.append("             M080_USERNAME,                   \n");
		sb.append("             M080_INTELNO                     \n");
		sb.append("	           )                                 \n");
		sb.append("     VALUES(                                  \n");
		sb.append("               NVL(?,?),                      \n");
		sb.append("               M080_SEQ.NEXTVAL,              \n");

		sb.append("   CASE                                                         \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-01' THEN '국고보조금'     \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-02' THEN '광특회계보조금' \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-03' THEN '기금'           \n");
		sb.append("   ELSE ? END ,                                                 \n");

		sb.append("   CASE                                                         \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-01' THEN '국고보조금'     \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-02' THEN '광특회계보조금' \n");
		sb.append("   WHEN NVL(?,' ') = ' ' AND ? = '511-03' THEN '기금보조금'     \n");
		sb.append("   ELSE ? END,                                                  \n");
    
    sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");

		sb.append(" NVL(?, to_char(sysdate,'yyyymmdd')),        \n");

		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ? * 1000,                      \n");
		sb.append("               ?,                             \n");
		sb.append("								?          )                   \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;

    parameters.setString(i++, paramInfo.getString("year"));     //엑셀회계년도에 NULL이 오면 조회조건의 회계년도로 입력
    parameters.setString(i++, paramInfo.getString("fis_year"));

    //목을 입력할때 목에 NULL인가를 체크하여 과목에 의해 목을 강제로 설정하는 로직
		parameters.setString(i++, paramInfo.getString("mok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("mok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("mok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("mok"));
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++여기까지 목처리
    //세목을 입력할때 목에 NULL인가를 체크하여 과목에 의해 목을 강제로 설정하는 로직
		parameters.setString(i++, paramInfo.getString("semok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("semok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("semok"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("semok"));
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++여기까지 세목처리

    parameters.setString(i++, paramInfo.getString("sogwanpart"));
		parameters.setString(i++, paramInfo.getString("silgwa"));
		parameters.setString(i++, paramInfo.getString("gwamok"));
		parameters.setString(i++, paramInfo.getString("businessname"));
		parameters.setString(i++, paramInfo.getString("dangchoamt"));
		parameters.setString(i++, paramInfo.getString("monthamt1"));
		parameters.setString(i++, paramInfo.getString("monthamt2"));
		parameters.setString(i++, paramInfo.getString("monthamt3"));
		parameters.setString(i++, paramInfo.getString("monthamt4"));
		parameters.setString(i++, paramInfo.getString("monthamt5"));
		parameters.setString(i++, paramInfo.getString("monthamt6"));
		parameters.setString(i++, paramInfo.getString("monthamt7"));
		parameters.setString(i++, paramInfo.getString("monthamt8"));
		parameters.setString(i++, paramInfo.getString("monthamt9"));
		parameters.setString(i++, paramInfo.getString("monthamt10"));
		parameters.setString(i++, paramInfo.getString("monthamt11"));
		parameters.setString(i++, paramInfo.getString("monthamt12"));
		parameters.setString(i++, paramInfo.getString("monthamt13"));
		parameters.setString(i++, paramInfo.getString("monthamt14"));
		parameters.setString(i++, paramInfo.getString("writedate"));  //WRITEDATE가 NULL이면 시스템일자로 설정
		parameters.setString(i++, paramInfo.getString("chukyngamt1"));
		parameters.setString(i++, paramInfo.getString("chukyngamt2"));
		parameters.setString(i++, paramInfo.getString("chukyngamt3"));
		parameters.setString(i++, paramInfo.getString("chukyngamt4"));
		parameters.setString(i++, paramInfo.getString("chukyngamt5"));
		parameters.setString(i++, paramInfo.getString("chukyngamt6"));
		parameters.setString(i++, paramInfo.getString("chukyngamt7"));
		parameters.setString(i++, paramInfo.getString("chukyngamt8"));
		parameters.setString(i++, paramInfo.getString("chukyngamt9"));
		parameters.setString(i++, paramInfo.getString("username"));
		parameters.setString(i++, paramInfo.getString("intelno"));

		return template.insert(conn, parameters); 
	}
	/* 계좌명과 계좌번호 셀렉트박스 */
	public static List<CommonEntity> getAcctList(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
 
	  sb.append(" SELECT M300_ACCOUNTNO,       \n");
		sb.append("        M300_ACCNAME          \n");
    sb.append("   FROM M300_ACCOUNTMANAGE_T  \n");
		sb.append("  WHERE M300_YEAR = TO_CHAR(SYSDATE, 'YYYY') \n");
		sb.append("    AND M300_BANKCODE = '039' \n");
    sb.append("    AND M300_PARTCODE = '00000' \n");
    sb.append("    AND M300_STATECODE = 'S2' \n");
    sb.append("  ORDER BY M300_ACCOUNTNO     \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
        
    return template.getList(conn);
  }
}
