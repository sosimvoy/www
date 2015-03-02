/*****************************************************
* ������Ʈ��	   : ���������� ������¹�ȣ���� �����ڽý���
* ���α׷���	   : IR010610.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����	 : ���漼 > ������¹���
******************************************************/
package com.etax.db.mn01;

import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR010610 {

    private static Logger logger = Logger.getLogger(IR010610.class);

    // �Է��ؾ� �� �����ڵ�
    public static CommonEntity getSrtData(Connection conn, CommonEntity paramInfo, String semok) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT M440_SEMOKCODE       \n"); 
        sb.append("   FROM M440_NONGHYUPCODE_T  \n");
        sb.append("  WHERE M440_INTYPE = ?      \n");
        sb.append("    AND M440_SEMOKNAME = ?   \n");
        sb.append("    AND M440_YEAR = ?        \n");
    
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("data_type1"));
        parameters.setString(idx++, semok);
        parameters.setString(idx++, paramInfo.getString("fis_year"));

        return template.getData(conn, parameters);
    }


  // ���ϸ���üũ
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
		parameters.setString(idx++, paramInfo.getString("acc_date"));

    return template.getData(conn, parameters);
  }


    // �ߺ� �ڷ� üũ
    public static CommonEntity getDataInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
    
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT COUNT(1) CNT          \n"); 
        sb.append("   FROM M010_TAXIN_T          \n");
        sb.append("  WHERE M010_YEAR = ?         \n");
        sb.append("    AND M010_DATE = ?         \n");
        sb.append("    AND M010_ACCTYPE = ?      \n");
        sb.append("    AND M010_ACCCODE = ?      \n");
        sb.append("    AND M010_INTYPE = ?       \n");
        sb.append("    AND M010_WORKTYPE = ?     \n");
        sb.append("    AND M010_TRANSGUBUN = ?   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("fis_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, paramInfo.getString("in_type"));
        parameters.setString(idx++, paramInfo.getString("work_log"));
        parameters.setString(idx++, paramInfo.getString("trans_gubun"));

        return template.getData(conn, parameters);
    }

	// �ӽ��ڷ� �Է�(�����ü��ϰ�ǥ)
  public static int insertData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                            \n"); 
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM, \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,        \n");
    sb.append("   M450_USERID)                                                        \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                \n");    
    sb.append("         ?, ?, ?, ?, ?,                                                \n");   
    sb.append("         ? )                                                           \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
		parameters.setString(idx++, data.getString("USER_ID"));

    return template.insert(conn, parameters);
  }


	// �ӽ��ڷ� �Է�(������ȯ���ϰ�ǥ)
  public static int insertGwaData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                                         \n");
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM,              \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,                     \n");
    sb.append("   M450_JUNGGU_GWA, M450_NAMGU_GWA, M450_DONGGU_GWA, M450_BUKGU_GWA, M450_ULJU_GWA, \n");
    sb.append("   M450_USERID)                                                                     \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ?, ?, ?, ?, ?,                                                             \n");
    sb.append("         ? )                                                                        \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
		parameters.setString(idx++, data.getString("JUNGGU_GWA"));
		parameters.setString(idx++, data.getString("NAMGU_GWA"));
		parameters.setString(idx++, data.getString("DONGGU_GWA"));
		parameters.setString(idx++, data.getString("BUKGU_GWA"));
		parameters.setString(idx++, data.getString("ULJUGUN_GWA"));
		parameters.setString(idx++, data.getString("USER_ID"));

    return template.insert(conn, parameters);
  }


  // �ӽ��ڷ� �Է�(������ϻ���ҽü��ϰ�ǥ)
  public static int insertCarData(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();
    sb.append(" INSERT INTO M450_PREVIEW_T                                                          \n"); 
    sb.append("  (M450_YEAR, M450_DATE, M450_DATA_TYPE, M450_SEMOK_CD, M450_SEMOK_NM,               \n");
    sb.append("   M450_JUNGGU, M450_NAMGU, M450_DONGGU, M450_BUKGU, M450_ULJU,                      \n");
    sb.append("   M450_JUNGGU_GWA, M450_NAMGU_GWA, M450_DONGGU_GWA, M450_BUKGU_GWA, M450_ULJU_GWA,  \n");
    sb.append("   M450_USERID, M450_ROWNUM)                                                         \n");
    sb.append(" VALUES( ?, ?, ?, ?, ?,                                                              \n");    
    sb.append("         ?, ?, ?, ?, ?,                                                              \n");
    sb.append("         ?, ?, ?, ?, ?,                                                              \n");
    sb.append("         ?, ? )                                                                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("FIS_YEAR"));
		parameters.setString(idx++, data.getString("ACC_DATE"));
		parameters.setString(idx++, data.getString("DATA_TYPE"));
		parameters.setString(idx++, data.getString("SEMOK_CD"));
		parameters.setString(idx++, data.getString("SEMOK_NM"));
		parameters.setString(idx++, data.getString("JUNGGU"));
		parameters.setString(idx++, data.getString("NAMGU"));
		parameters.setString(idx++, data.getString("DONGGU"));
		parameters.setString(idx++, data.getString("BUKGU"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
    parameters.setString(idx++, data.getString("JUNGGU_CNT"));
		parameters.setString(idx++, data.getString("NAMGU_CNT"));
		parameters.setString(idx++, data.getString("DONGGU_CNT"));
		parameters.setString(idx++, data.getString("BUKGU_CNT"));
		parameters.setString(idx++, data.getString("ULJUGUN_CNT"));
		parameters.setString(idx++, data.getString("USER_ID"));
                parameters.setString(idx++, data.getString("ROW_NUM"));

    return template.insert(conn, parameters);
  }

   
	// �ӽ��ڷ� ����Ʈ(�����ü������ϰ�ǥ)
  public static List<CommonEntity> getNonghyupList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                      \n");
    sb.append("       ,M450_SEMOK_NM                      \n");
    sb.append("       ,M450_JUNGGU                        \n");
    sb.append("       ,M450_NAMGU                         \n");
    sb.append("       ,M450_DONGGU                        \n");
    sb.append("       ,M450_BUKGU                         \n");
    sb.append("       ,M450_ULJU                          \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '1'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '1999999' M450_SEMOK_CD            \n");
    sb.append("       ,'���漼�Ұ�' M450_SEMOK_NM         \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '1'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT M450_SEMOK_CD                      \n");
    sb.append("       ,M450_SEMOK_NM                      \n");
    sb.append("       ,M450_JUNGGU                        \n");
    sb.append("       ,M450_NAMGU                         \n");
    sb.append("       ,M450_DONGGU                        \n");
    sb.append("       ,M450_BUKGU                         \n");
    sb.append("       ,M450_ULJU                          \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '2'    \n");
    sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '2999999' M450_SEMOK_CD            \n");
    sb.append("       ,'���ܼ��ԼҰ�' M450_SEMOK_NM       \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append("    AND SUBSTR(M450_SEMOK_CD,1,1) = '2'    \n");
		sb.append(" UNION ALL                                 \n");
    sb.append(" SELECT '9999999' M450_SEMOK_CD            \n");
    sb.append("       ,'�� ��' M450_SEMOK_NM              \n");
    sb.append("       ,SUM(M450_JUNGGU) M450_JUNGGU       \n");
    sb.append("       ,SUM(M450_NAMGU) M450_NAMGU         \n");
    sb.append("       ,SUM(M450_DONGGU) M450_DONGGU       \n");
    sb.append("       ,SUM(M450_BUKGU) M450_BUKGU         \n");
    sb.append("       ,SUM(M450_ULJU) M450_ULJU           \n");
    sb.append("   FROM M450_PREVIEW_T                     \n");
    sb.append("  WHERE M450_YEAR = ?                      \n");
    sb.append("    AND M450_DATE = ?                      \n");
    sb.append("    AND M450_DATA_TYPE = ?                 \n");
    sb.append("    AND M450_USERID = ?                    \n");
    sb.append(" ORDER BY M450_SEMOK_CD                    \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));
		parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }


	// �ӽ��ڷ� ����Ʈ(������ȯ���ϰ�ǥ)
  public static List<CommonEntity> getNonghyupGwaList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                        \n");
    sb.append("       ,M450_SEMOK_NM                        \n");
    sb.append("       ,M450_JUNGGU                          \n");
    sb.append("       ,M450_NAMGU                           \n");
    sb.append("       ,M450_DONGGU                          \n");
    sb.append("       ,M450_BUKGU                           \n");
    sb.append("       ,M450_ULJU                            \n");
		sb.append("       ,M450_JUNGGU_GWA                      \n");
    sb.append("       ,M450_NAMGU_GWA                       \n");
    sb.append("       ,M450_DONGGU_GWA                      \n");
    sb.append("       ,M450_BUKGU_GWA                       \n");
    sb.append("       ,M450_ULJU_GWA                        \n");
    sb.append("   FROM M450_PREVIEW_T                       \n");
    sb.append("  WHERE M450_YEAR = ?                        \n");
    sb.append("    AND M450_DATE = ?                        \n");
    sb.append("    AND M450_DATA_TYPE = ?                   \n");
    sb.append("    AND M450_USERID = ?                      \n");
    sb.append(" ORDER BY M450_SEMOK_CD                      \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }

  
  // �ӽ��ڷ� ����Ʈ(���������)
  public static List<CommonEntity> getNonghyupCarList(Connection conn, CommonEntity data) throws SQLException {

    StringBuffer sb = new StringBuffer();  
    sb.append(" SELECT M450_SEMOK_CD                        \n");
    sb.append("       ,M450_SEMOK_NM                        \n");
    sb.append("       ,M450_JUNGGU                          \n");
    sb.append("       ,M450_NAMGU                           \n");
    sb.append("       ,M450_DONGGU                          \n");
    sb.append("       ,M450_BUKGU                           \n");
    sb.append("       ,M450_ULJU                            \n");
		sb.append("       ,M450_JUNGGU_GWA                      \n");
    sb.append("       ,M450_NAMGU_GWA                       \n");
    sb.append("       ,M450_DONGGU_GWA                      \n");
    sb.append("       ,M450_BUKGU_GWA                       \n");
    sb.append("       ,M450_ULJU_GWA                        \n");
    sb.append("   FROM M450_PREVIEW_T                       \n");
    sb.append("  WHERE M450_YEAR = ?                        \n");
    sb.append("    AND M450_DATE = ?                        \n");
    sb.append("    AND M450_DATA_TYPE = ?                   \n");
    sb.append("    AND M450_USERID = ?                      \n");
    sb.append(" ORDER BY M450_ROWNUM                        \n");
    
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int idx = 1;
    parameters.setString(idx++, data.getString("fis_year"));
    parameters.setString(idx++, data.getString("acc_date"));
    parameters.setString(idx++, data.getString("data_type"));
    parameters.setString(idx++, data.getString("user_id"));

    return template.getList(conn, parameters);
  }
  
    //�ӽ��ڷ� ���̺� �ڷ� ����
    public static int deleteData(Connection conn, CommonEntity data) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM M450_PREVIEW_T          \n"); 
        sb.append("  WHERE M450_USERID = ?              \n");
        sb.append("    AND M450_YEAR = ?                \n");
        sb.append("    AND M450_DATE = ?                \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;

        parameters.setString(idx++, data.getString("user_id"));
        parameters.setString(idx++, data.getString("fis_year"));
        parameters.setString(idx++, data.getString("acc_date"));

        return template.delete(conn, parameters);
    }


    //�����ڷ� ���(�߱�)
    public static int insertJunggu(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M010_TAXIN_T                              \n");
		sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE,                     \n");
		sb.append("   M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,         \n");
		sb.append("   M010_PARTCODE, M010_SUNAPGIGWANCODE, M010_INTYPE,   \n");
		sb.append("   M010_AMT, M010_CNT, M010_LOGNO,	                  \n");
		sb.append("   M010_WORKTYPE, M010_TRANSGUBUN, M010_YEARTYPE )     \n");
        sb.append(" VALUES( ?, M010_SEQ.NEXTVAL, ?,                       \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
		sb.append("         ?, ?, ? )                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, data.getString("FIS_YEAR"));
        parameters.setString(idx++, data.getString("ACC_DATE"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
		parameters.setString(idx++, data.getString("SEMOK_CD"));
        parameters.setString(idx++, "00110");
		parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, data.getString("JUNGGU"));
        parameters.setString(idx++, data.getString("JUNGGU_CNT"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));
        if ("1130100".equals(data.getString("SEMOK_CD"))||"2290100".equals(data.getString("SEMOK_CD")) ) {
            parameters.setString(idx++, "Y2");
        } else {
            parameters.setString(idx++, "Y1");
        }

        return template.insert(conn, parameters);
    }


	//�����ڷ� ���(����)
    public static int insertNamgu(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M010_TAXIN_T                              \n");
		sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE,                     \n");
		sb.append("   M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,         \n");
		sb.append("   M010_PARTCODE, M010_SUNAPGIGWANCODE, M010_INTYPE,   \n");
		sb.append("   M010_AMT, M010_CNT, M010_LOGNO,	                  \n");
		sb.append("   M010_WORKTYPE, M010_TRANSGUBUN, M010_YEARTYPE )     \n");
        sb.append(" VALUES( ?, M010_SEQ.NEXTVAL, ?,                       \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
		sb.append("         ?, ?, ? )                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        int idx = 1;
        parameters.setString(idx++, data.getString("FIS_YEAR"));
        parameters.setString(idx++, data.getString("ACC_DATE"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
		parameters.setString(idx++, data.getString("SEMOK_CD"));
        parameters.setString(idx++, "00140");
		parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, data.getString("NAMGU"));
        parameters.setString(idx++, data.getString("NAMGU_CNT"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));
        if ("1130100".equals(data.getString("SEMOK_CD"))||"2290100".equals(data.getString("SEMOK_CD")) ) {
            parameters.setString(idx++, "Y2");
        } else {
            parameters.setString(idx++, "Y1");
        }

        return template.insert(conn, parameters);
    }


	//�����ڷ� ���(����)
    public static int insertDonggu(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M010_TAXIN_T                              \n");
		sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE,                     \n");
		sb.append("   M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,         \n");
		sb.append("   M010_PARTCODE, M010_SUNAPGIGWANCODE, M010_INTYPE,   \n");
		sb.append("   M010_AMT, M010_CNT, M010_LOGNO,	                  \n");
		sb.append("   M010_WORKTYPE, M010_TRANSGUBUN, M010_YEARTYPE )     \n");
        sb.append(" VALUES( ?, M010_SEQ.NEXTVAL, ?,                       \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
		sb.append("         ?, ?, ? )                                     \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

    	int idx = 1;
        parameters.setString(idx++, data.getString("FIS_YEAR"));
        parameters.setString(idx++, data.getString("ACC_DATE"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
		parameters.setString(idx++, data.getString("SEMOK_CD"));
        parameters.setString(idx++, "00170");
	    parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, data.getString("DONGGU"));
        parameters.setString(idx++, data.getString("DONGGU_CNT"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));
        if ("1130100".equals(data.getString("SEMOK_CD"))||"2290100".equals(data.getString("SEMOK_CD")) ) {
            parameters.setString(idx++, "Y2");
        } else {
            parameters.setString(idx++, "Y1");
        }

        return template.insert(conn, parameters);
    }


	//�����ڷ� ���(�ϱ�)
    public static int insertBukgu(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M010_TAXIN_T                              \n");
		sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE,                     \n");
		sb.append("   M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,         \n");
		sb.append("   M010_PARTCODE, M010_SUNAPGIGWANCODE, M010_INTYPE,   \n");
		sb.append("   M010_AMT, M010_CNT, M010_LOGNO,	                  \n");
		sb.append("   M010_WORKTYPE, M010_TRANSGUBUN, M010_YEARTYPE )     \n");
        sb.append(" VALUES( ?, M010_SEQ.NEXTVAL, ?,                       \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
		sb.append("         ?, ?, ? )                                     \n");
    	
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
    	int idx = 1;
        parameters.setString(idx++, data.getString("FIS_YEAR"));
        parameters.setString(idx++, data.getString("ACC_DATE"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
		parameters.setString(idx++, data.getString("SEMOK_CD"));
        parameters.setString(idx++, "00200");
	    parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, data.getString("BUKGU"));
        parameters.setString(idx++, data.getString("BUKGU_CNT"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));
        if ("1130100".equals(data.getString("SEMOK_CD"))||"2290100".equals(data.getString("SEMOK_CD")) ) {
            parameters.setString(idx++, "Y2");
        } else {
            parameters.setString(idx++, "Y1");
        }

        return template.insert(conn, parameters);
    }


	//�����ڷ� ���(���ֱ�)
    public static int insertUlju(Connection conn, CommonEntity data, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO M010_TAXIN_T                              \n");
		sb.append(" ( M010_YEAR, M010_SEQ, M010_DATE,                     \n");
		sb.append("   M010_ACCTYPE, M010_ACCCODE, M010_SEMOKCODE,         \n");
		sb.append("   M010_PARTCODE, M010_SUNAPGIGWANCODE, M010_INTYPE,   \n");
		sb.append("   M010_AMT, M010_CNT, M010_LOGNO,	                  \n");
		sb.append("   M010_WORKTYPE, M010_TRANSGUBUN, M010_YEARTYPE )     \n");
        sb.append(" VALUES( ?, M010_SEQ.NEXTVAL, ?,                       \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
        sb.append("         ?, ?, ?,                                      \n");
		sb.append("         ?, ?, ? )                                     \n");
    	
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
    	int idx = 1;
        parameters.setString(idx++, data.getString("FIS_YEAR"));
        parameters.setString(idx++, data.getString("ACC_DATE"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
		parameters.setString(idx++, data.getString("SEMOK_CD"));
        parameters.setString(idx++, "00710");
	    parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, data.getString("ULJUGUN"));
        parameters.setString(idx++, data.getString("ULJUGUN_CNT"));
		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));
        if ("1130100".equals(data.getString("SEMOK_CD"))||"2290100".equals(data.getString("SEMOK_CD")) ) {
            parameters.setString(idx++, "Y2");
        } else {
            parameters.setString(idx++, "Y1");
        }

        return template.insert(conn, parameters);
    }


	//�����ڷ� ����
    public static int deleteNongHyupData(Connection conn, CommonEntity paramInfo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE FROM M010_TAXIN_T                \n");
		sb.append("  WHERE M010_YEAR = ?                    \n");
		sb.append("    AND M010_DATE = ?                    \n");
		sb.append("    AND M010_ACCTYPE = ?                 \n");
		sb.append("    AND M010_ACCCODE = ?                 \n");
		sb.append("    AND M010_PARTCODE IN (?, ?, ?, ?, ?) \n");
        sb.append("    AND M010_SUNAPGIGWANCODE = ?         \n");
        sb.append("    AND M010_INTYPE = ?                  \n");
        sb.append("    AND M010_WORKTYPE = ?                \n");
		sb.append("    AND M010_TRANSGUBUN = ?              \n");

    	QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
        
        int idx = 1;
        parameters.setString(idx++, paramInfo.getString("fis_year"));
    	parameters.setString(idx++, paramInfo.getString("acc_date"));
        parameters.setString(idx++, "A");
        parameters.setString(idx++, "11");
        parameters.setString(idx++, "00110");
		parameters.setString(idx++, "00140");
		parameters.setString(idx++, "00170");
		parameters.setString(idx++, "00200");
		parameters.setString(idx++, "00710");
		parameters.setString(idx++, paramInfo.getString("sunap_gigwan"));
        parameters.setString(idx++, paramInfo.getString("in_type"));
		parameters.setString(idx++, paramInfo.getString("work_log"));
		parameters.setString(idx++, paramInfo.getString("trans_gubun"));

        return template.delete(conn, parameters);
    }
}
