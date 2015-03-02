/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR040310.jsp
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-12
* 프로그램내용	   : 세외수입 > 징수결의등록
****************************************************************/

package com.etax.db.mn04;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR040310 {
	 
	 /* 징수결의 등록 */
	 public static int jingsuInsert(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

		sb.append(" INSERT INTO M090_JINGSUKYULUI_T              \n");
		sb.append("           ( M090_YEAR,                       \n");//회계연도 
    sb.append("             M090_SEQ,                        \n");//일련번호
		sb.append("             M090_DATE,                       \n");//회계일자
		sb.append("             M090_KYULUITYPE,                 \n");//징수결의서 종류
		sb.append("             M090_DOCUMENTTYPECODE,           \n");
		sb.append("             M090_BALUIDATE,                  \n");
		sb.append("             M090_GOJISEOPUBLISH,             \n");
		sb.append("             M090_NAPIBDATE,                  \n");
		sb.append("             M090_JINGSUBUWRITE,              \n");
		sb.append("             M090_GWAN,                       \n");
		sb.append("             M090_HANG,                       \n");
		sb.append("             M090_MOK,                        \n");
		sb.append("             M090_SEMOKCODE,                  \n");
		sb.append("             M090_BONAMT,                     \n");
		sb.append("             M090_GASANAMT,                   \n");
		sb.append("             M090_INTERESTAMT,                \n");
		sb.append("             M090_TOTALAMT,                   \n");
    sb.append("             M090_NAPBUJANAME,                \n");
    sb.append("             M090_JUMINNO,                    \n");
		sb.append("							M090_NAPBUJAADDRESS,             \n");
		sb.append("							M090_BUSINESSNAME,               \n");
		sb.append("             M090_USERNAME,                   \n");
		sb.append("             M090_REGISTERPART,               \n");
    sb.append("             M090_M080_YEAR,                  \n");
		sb.append("             M090_M080_SEQ,                   \n");
		sb.append("             M090_LOGNO                       \n");
		sb.append("	           )                                 \n");
		sb.append("     VALUES(                                  \n");
		sb.append("               ?,                             \n");
		sb.append("               M090_SEQ.NEXTVAL,              \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("  (  SELECT M370_SEMOKNAME                    \n");
    sb.append("       FROM M370_SEMOKCODE_T                  \n");
    sb.append("      WHERE M370_WORKGUBUN = '0'              \n");
    sb.append("        AND M370_ACCCODE = '11'               \n");
    sb.append("        AND M370_ACCGUBUN = 'A'               \n");
    sb.append("        AND M370_SEMOKCODE = ?              \n");
    sb.append("        AND M370_YEAR = ? ),                     \n");
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
	  sb.append("               '시금고',                      \n");
    sb.append("               ?,                             \n");
		sb.append("               ?,                             \n");
		sb.append("               ?)                             \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
  	
		int i = 1;

    parameters.setString(i++, paramInfo.getString("fis_year"));
		parameters.setString(i++, paramInfo.getString("baluiDate"));
		parameters.setString(i++, paramInfo.getString("kyuluiType"));
		parameters.setString(i++, paramInfo.getString("doc_code"));
		parameters.setString(i++, paramInfo.getString("baluiDate"));
		parameters.setString(i++, paramInfo.getString("gojiseoPublish"));
    parameters.setString(i++, paramInfo.getString("napibDate"));
    parameters.setString(i++, paramInfo.getString("jingsubuWrite"));
		parameters.setString(i++, paramInfo.getString("gwan"));
    parameters.setString(i++, paramInfo.getString("hang"));
    parameters.setString(i++, paramInfo.getString("mok"));
    parameters.setString(i++, paramInfo.getString("fis_year"));
		parameters.setString(i++, paramInfo.getString("semok"));
		parameters.setString(i++, paramInfo.getString("bonAmt"));
    parameters.setString(i++, paramInfo.getString("gasanAmt"));
    parameters.setString(i++, paramInfo.getString("interestAmt"));
    parameters.setString(i++, paramInfo.getString("totalAmt"));
		parameters.setString(i++, paramInfo.getString("napbujaName"));
		parameters.setString(i++, paramInfo.getString("juminNo"));
		parameters.setString(i++, paramInfo.getString("address"));
		parameters.setString(i++, paramInfo.getString("businessName"));
		parameters.setString(i++, paramInfo.getString("userName"));
    parameters.setString(i++, paramInfo.getString("year"));
		parameters.setString(i++, paramInfo.getString("seq"));
    parameters.setString(i++, paramInfo.getString("log_no"));

		return template.insert(conn, parameters); 
	}
		/*예산서상세 상세*/
		public static CommonEntity getBudgetView(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("   SELECT M080_YEAR                  \n"); 
    sb.append("         ,M080_SEQ                   \n");  
    sb.append("         ,M080_MOK                   \n");
		sb.append("         ,M080_SEMOKCODE             \n");
    sb.append("         ,M080_SOGWANPART            \n");
		sb.append("         ,M080_SILGWA                \n");
		sb.append("         ,M080_GWAMOK                \n");
		sb.append("         ,M080_BUSINESSNAME          \n");
		sb.append("         ,M080_DANGCHOAMT            \n");
		sb.append("         ,(M080_CHUKYNGAMT1           \n");
		sb.append("         +M080_CHUKYNGAMT2           \n");
		sb.append("         +M080_CHUKYNGAMT3           \n");
		sb.append("         +M080_CHUKYNGAMT4           \n");
		sb.append("         +M080_CHUKYNGAMT5           \n");
		sb.append("         +M080_CHUKYNGAMT6           \n");
		sb.append("         +M080_CHUKYNGAMT7           \n");
		sb.append("         +M080_CHUKYNGAMT8           \n");
		sb.append("         +M080_CHUKYNGAMT9) CHUKYUNGAMT  \n");
		sb.append("         ,(M080_MONTHAMT_1             \n");
    sb.append("         +M080_MONTHAMT_2              \n");
    sb.append("         +M080_MONTHAMT_3              \n");
    sb.append("         +M080_MONTHAMT_4              \n");
    sb.append("         +M080_MONTHAMT_5              \n");
    sb.append("         +M080_MONTHAMT_6              \n");
    sb.append("         +M080_MONTHAMT_7              \n");
    sb.append("         +M080_MONTHAMT_8              \n");
    sb.append("         +M080_MONTHAMT_9              \n");
    sb.append("         +M080_MONTHAMT_10             \n");
    sb.append("         +M080_MONTHAMT_11             \n");
    sb.append("         +M080_MONTHAMT_12             \n");
    sb.append("         +M080_MONTHAMT_13             \n");
    sb.append("         +M080_MONTHAMT_14) MONTHAMT  \n");
		sb.append("     	  ,M080_WRITEDATE             \n");
    sb.append("     FROM M080_BUDGETMANAGE_T        \n");
    sb.append("    WHERE M080_SEQ = ?               \n");
		sb.append("      AND M080_YEAR = ?               \n");
 
   
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		parameters.setString(1, paramInfo.getString("seq"));
		parameters.setString(2, paramInfo.getString("year"));
	
		return template.getData(conn, parameters);
	}


    /*시청담당자 리스트*/
	public static List<CommonEntity> getManagerList(Connection conn)throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M260_USERID                   \n");
        sb.append("       ,M260_USERNAME                 \n");
        sb.append("   FROM M260_USERMANAGER_T            \n");
        sb.append("  WHERE M260_CURRENTORGAN = '2'       \n");
        sb.append("    AND (M260_CURRENTWORK1 = 'C2'     \n");
        sb.append("        OR M260_CURRENTWORK2 = 'C2')  \n");
        sb.append("  ORDER BY M260_USERNAME              \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
	
		return template.getList(conn);
	}


    /*시청담당자 리스트*/
	public static CommonEntity getManagerInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M260_USERID                   \n");
        sb.append("       ,M260_USERNAME                 \n");
        sb.append("   FROM M260_USERMANAGER_T            \n");
        sb.append("  WHERE M260_CURRENTORGAN = '2'       \n");
        sb.append("    AND (M260_CURRENTWORK1 = 'C2'     \n");
        sb.append("        OR M260_CURRENTWORK2 = 'C2')  \n");
        sb.append("    AND M260_USERID = ?               \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx =1;

        parameters.setString(idx++, paramInfo.getString("userName"));
	
		return template.getData(conn, parameters);
	}

    /*목 리스트*/
	public static List<CommonEntity> getsemokcodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" -- 목코드 SELECT BOX처리쿼리                  \n");             
        sb.append(" SELECT A.M370_SEMOKCODE                      \n");
        sb.append("      , A.M370_SEMOKNAME                      \n");
        sb.append("   FROM M370_SEMOKCODE_T A                    \n");
        sb.append("      , M390_USESEMOKCODE_T B                 \n");
        sb.append("  WHERE B.M390_WORKGUBUN = '0'                \n");
        sb.append("    AND B.M390_ACCCODE = '11'                 \n");
        sb.append("    AND B.M390_ACCGUBUN = 'A'                 \n");
        sb.append("    AND B.M390_PARTCODE = '00000'             \n");
        sb.append("    AND SUBSTR(B.M390_SEMOKCODE,1,1) > '1'    \n");
        sb.append("    AND B.M390_SEMOKCODE = A.M370_SEMOKCODE   \n");
        sb.append("    AND B.M390_WORKGUBUN = A.M370_WORKGUBUN   \n");
        sb.append("    AND B.M390_ACCCODE   = A.M370_ACCCODE     \n");
        sb.append("    AND B.M390_ACCGUBUN  = A.M370_ACCGUBUN    \n");
        sb.append("    AND B.M390_YEAR  = A.M370_YEAR            \n");
        sb.append("    AND B.M390_YEAR  = ?                      \n");
        sb.append("  ORDER BY A.M370_SEMOKCODE                   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx =1;

        parameters.setString(idx++, paramInfo.getString("fis_year"));
		
		return template.getList(conn, parameters);
    }

    /*세목 리스트*/
	public static List<CommonEntity> getsesemokcodeList(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" -- 세목코드 SELECTBOX처리쿼리          \n");
        sb.append(" SELECT M420_STANDARDSEMOKCODE          \n");
        sb.append("      , M420_SEMOKNAME                  \n");
        sb.append("   FROM M420_STANDARDSEMOKCODE_T        \n");
        sb.append("  WHERE M420_SYSTEMSEMOKCODE = ?        \n");
        sb.append("    AND M420_YEAR = ?                   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx =1;
        parameters.setString(idx++, paramInfo.getString("semokcode"));
        parameters.setString(idx++, paramInfo.getString("fis_year"));
		
		return template.getList(conn, parameters);
    }

  /*목상위 장,관,항 과목코드 조회*/
		public static CommonEntity getsangweesemokList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" -- 목코드 선택시 상위코드정보 조회쿼리                                     \n");
    sb.append(" SELECT BASECODE                                                           \n");
    sb.append("      , MAX(DECODE(POSI,1,M370_SEMOKCODE)) AS JANGCODE                     \n");
    sb.append("      , MAX(DECODE(POSI,1,M370_SEMOKNAME)) AS JANGNAME                     \n");
    sb.append("      , MAX(DECODE(POSI,2,M370_SEMOKCODE)) AS GOANCODE                     \n");
    sb.append("      , MAX(DECODE(POSI,2,M370_SEMOKNAME)) AS GOANNAME                     \n");
    sb.append("      , MAX(DECODE(POSI,3,M370_SEMOKCODE)) AS HANGCODE                     \n");
    sb.append("      , MAX(DECODE(POSI,3,M370_SEMOKNAME)) AS HANGNAME                     \n");
    sb.append("   FROM (                                                                  \n");
    sb.append("       SELECT M370_SEMOKCODE AS BASECODE                                   \n");
    sb.append("            , POSI                                                         \n");
    sb.append("            , RPAD(CASE                                                    \n");
    sb.append("              WHEN POSI = 1 THEN SUBSTR(M370_SEMOKCODE,1,POSI)             \n");
    sb.append("              WHEN POSI = 2 THEN SUBSTR(M370_SEMOKCODE,1,POSI)             \n");
    sb.append("              WHEN POSI = 3 THEN SUBSTR(M370_SEMOKCODE,1,POSI)             \n");
    sb.append("              END,7,'0') AS NEWSEMOKCODE                                   \n");
    sb.append("            , DECODE(POSI,1,SUBSTR(M370_SEMOKCODE,1,POSI)) AS JANG         \n");
    sb.append("            , DECODE(POSI,2,SUBSTR(M370_SEMOKCODE,1,POSI)) AS GWAN         \n");
    sb.append("            , DECODE(POSI,3,SUBSTR(M370_SEMOKCODE,1,POSI)) AS HANG         \n");
    sb.append("         FROM M370_SEMOKCODE_T A                                           \n");
    sb.append("            , (SELECT LEVEL AS POSI                                        \n");
    sb.append("                 FROM DUAL                                                 \n");
    sb.append("                CONNECT BY LEVEL < 4)                                      \n");
    sb.append("        WHERE M370_WORKGUBUN = '0' --고정                                  \n");
    sb.append("          AND M370_ACCCODE = '11'  --고정                                  \n");
    sb.append("          AND M370_ACCGUBUN = 'A'  --고정                                  \n");
    sb.append("          AND M370_SEMOKCODE = ?                                           \n");
    sb.append("          AND M370_YEAR = ?                                                \n");
    sb.append("      ) A, M370_SEMOKCODE_T B                                              \n");
    sb.append("  WHERE A.NEWSEMOKCODE = B.M370_SEMOKCODE                                  \n");
    sb.append("    AND B.M370_WORKGUBUN = '0' --고정                                      \n");
    sb.append("    AND B.M370_ACCCODE = '11'  --고정                                      \n");
    sb.append("    AND B.M370_ACCGUBUN = 'A'  --고정                                      \n");
    sb.append("    AND B.M370_YEAR = ?                                                    \n");
    sb.append("  GROUP BY BASECODE                                                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx =1;
    parameters.setString(idx++, paramInfo.getString("semokcode"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
    parameters.setString(idx++, paramInfo.getString("fis_year"));
		
    return template.getData(conn, parameters);
	}
  
  /*납세자 조회*/
		public static List<CommonEntity> getnapbuList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" -- 납부자 SELECT BOX처리 쿼리     \n");
    sb.append("  SELECT M560_NAPBUJA             \n");
    sb.append("    FROM M560_NAPBUADDRESS_T      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    int idx =1;
		
		return template.getList(conn);
	}
  /*납세자 주소조회*/
		public static CommonEntity getnapbuaddressList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append("  -- 납부자 선택시 주소정보 조회       \n");
    sb.append("  SELECT M560_ADDRESS                 \n");
    sb.append("    FROM M560_NAPBUADDRESS_T          \n");
    sb.append("   WHERE M560_NAPBUJA = TRIM(?)       \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx =1;
    parameters.setString(idx++, paramInfo.getString("napbujaName"));
		
    return template.getData(conn, parameters);
	}
  /*수령액 수정*/
	public static int updateYesanAmt(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

    sb.append(" UPDATE M080_BUDGETMANAGE_T           \n");
    sb.append("    SET "+paramInfo.getString("col_name")+" = "+paramInfo.getString("col_name")+" + ? \n");
    sb.append("  WHERE M080_YEAR = ?                 \n");
    sb.append("    AND M080_SEQ = ?                  \n");
   
		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    parameters.setString(idx++, paramInfo.getString("totalAmt"));
		parameters.setString(idx++, paramInfo.getString("year"));
		parameters.setString(idx++, paramInfo.getString("seq"));
	
		return template.update(conn, parameters);
	}
	 public static CommonEntity getTaskId(Connection conn, CommonEntity paramInfo)throws SQLException {
        StringBuffer sb = new StringBuffer();
	
	    sb.append(" SELECT M200_DOCUMENTCODE                             \n");
        sb.append("   FROM M200_DOCUMENTTYPE_T                           \n");
        sb.append("  WHERE M200_TASKID = ?                               \n");
        sb.append("    AND M200_DOCUMENTCODE IN ('ED03','ED12', 'ED15')  \n");

	    QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx =1;
        parameters.setString(idx++, paramInfo.getString("userName"));
		
        return template.getData(conn, parameters);
	}
}
