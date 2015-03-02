/*****************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR091310.java
* ���α׷��ۼ��� :
* ���α׷��ۼ��� :
* ���α׷�����   : �ý��ۿ > ��Ÿ���������
******************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091310 {

    /* �������� ��ȸ */
    public static CommonEntity getTransOpen(Connection conn) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" SELECT M570_WORK_GUBUN         \n");
        sb.append("      , TO_CHAR(M570_SEND_TIME,'YYYY-MM-DD HH24:MI:SS') M570_SEND_TIME \n");
        sb.append("      , M570_ERR_CODE           \n");
        sb.append("      , M570_ERR_MSG            \n");
        sb.append("   FROM M570_TRANSOPEN_T        \n");
		sb.append("  WHERE M570_WORK_GUBUN = '100' \n");
        sb.append("    AND TO_CHAR(M570_SEND_TIME, 'YYYYMMDD')  = TO_CHAR(SYSDATE, 'YYYYMMDD') \n");

        QueryTemplate template = new QueryTemplate(sb.toString());

        return template.getData(conn);
    }


    /* ������ȣ ������ ���� */
    public static int dropSeq(Connection conn, String seqName) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append("DROP SEQUENCE " + seqName + "  \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        return template.delete(conn, parameters);
    }



    /* ������ȣ ������ ���� */
    public static int createSeq(Connection conn, String seqName, String maxVal) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" CREATE SEQUENCE "+ seqName +"    \n");
        sb.append("        START WITH 1              \n");
        sb.append("        MAXVALUE "+ maxVal + "    \n");
        sb.append("        MINVALUE 1                \n");
        sb.append("        NOCYCLE                   \n");
        sb.append("        NOCACHE                   \n");
        sb.append("        NOORDER                   \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

        return template.insert(conn, parameters);
    }



    /* ���� ���ÿ� ���� ���� ��ȸ */
	public static CommonEntity getLastInfo(Connection conn) throws SQLException {
		StringBuffer sb = new StringBuffer();

        sb.append(" SELECT  M570_WORK_GUBUN, TO_CHAR(M570_SEND_TIME,'YYYY-MM-DD HH24:MI:SS') M570_SEND_TIME, M570_ERR_CODE, M570_ERR_MSG FROM M570_TRANSOPEN_T    ");
	
        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();

	    return template.getData(conn, parameters);
	} 


    /* �������� ���� ��� */
    public static int updateRevData(Connection conn, CommonEntity data) throws SQLException {
        StringBuffer sb = new StringBuffer();

        sb.append(" UPDATE M570_TRANSOPEN_T                                \n");
		sb.append("    SET M570_WORK_GUBUN = ?                             \n");
        sb.append("       ,M570_SEND_TIME  = TO_DATE(?,'YYYYMMDDHH24MISS') \n");
		sb.append("       ,M570_ERR_CODE   = ?                             \n");
        sb.append("       ,M570_ERR_MSG    = ?                             \n");

        QueryTemplate template = new QueryTemplate(sb.toString());
        QueryParameters parameters = new QueryParameters();
    
        int idx = 1;
		parameters.setString(idx++, data.getString("common05"));
        parameters.setString(idx++, data.getString("common08")+ data.getString("common09"));
        parameters.setString(idx++, data.getString("common10"));
        parameters.setString(idx++, data.getString("common11"));

        return template.update(conn, parameters);
    }
}