/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR040510.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-09
* ���α׷�����	   : ���ܼ��� > ������¡������ ��ȸ
****************************************************************/

package com.etax.db.mn04;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR040510 {

  /* ������ ��ȸ */
  public static List<CommonEntity> getGwaonapList(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("  SELECT  M090_YEAR                     \n");
    sb.append("         ,M090_SEQ                      \n");  
    sb.append("         ,M090_DATE                     \n");
		sb.append("         ,M090_KYULUITYPE               \n");
    sb.append("         ,M090_BUGWANO                  \n");
		sb.append("         ,M090_BALUIDATE                \n");
		sb.append("         ,M090_RETURNORDERDATE          \n");
		sb.append("         ,M090_GOJISEOPUBLISH           \n");
		sb.append("         ,M090_NAPIBDATE                \n");
		sb.append("         ,M090_JINGSUBUWRITE            \n");
		sb.append("         ,M090_GWAONAPWRITE             \n");
		sb.append("     	  ,M090_GWAONAPDATE              \n");
		sb.append("     	  ,M090_GWAN                     \n");
		sb.append("     	  ,M090_HANG                     \n");
		sb.append("     	  ,M090_MOK                      \n");
		sb.append("     	  ,M090_SEMOKCODE                \n");
		sb.append("     	  ,M090_BONAMT                   \n");
		sb.append("     	  ,M090_KINAPBUAMT               \n");
		sb.append("     	  ,M090_GASANAMT                 \n");
		sb.append("     	  ,M090_INTERESTAMT              \n");
		sb.append("     	  ,M090_INTERESTDAY              \n");
		sb.append("     	  ,M090_NAPBUJANAME              \n");
		sb.append("     	  ,M090_JUMINNO                  \n");
		sb.append("     	  ,M090_NAPBUJAADDRESS           \n");
		sb.append("     	  ,M090_BUSINESSNAME             \n");
		sb.append("     	  ,M090_GWAONAPREASON            \n");
		sb.append("     	  ,M090_BANKCODE                 \n");
		sb.append("     	  ,M090_ACCOUNTNO                \n");
		sb.append("     	  ,M090_ACCOUNTHOLDER            \n");
		sb.append("     	  ,M090_FILE                     \n");
		sb.append("     	  ,M090_REGISTERPART             \n");
		sb.append("     	  ,M090_DOCUMENTTYPECODE         \n");
		sb.append("     	  ,M090_GWAONAPSTATECODE         \n");
		sb.append("     	  ,M090_DOCUMENTNO               \n");
		sb.append("     	  ,M090_USERNAME                 \n");
		sb.append("     	  ,NVL(M090_BONAMT,0) +          \n");
		sb.append("     	   NVL(M090_INTERESTAMT,0) +     \n");
		sb.append("     	   NVL(M090_GASANAMT,0) AS TOT_AMT \n");
    sb.append("  FROM M090_JINGSUKYULUI_T              \n");
    sb.append(" WHERE M090_GWAONAPDATE BETWEEN ? AND ? \n");
    sb.append("   AND M090_DOCUMENTNO IS NOT NULL      \n");
   
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
		
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("su_sdate"));
		parameters.setString(i++,  paramInfo.getString("su_edate"));

		return template.getList(conn, parameters);
	}

	/*������ ����*/
	 public static int gwaonapDelete(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M090_JINGSUKYULUI_T    \n");
    sb.append("  WHERE M090_SEQ = ?                \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("seq")); 

    return template.delete(conn, parameters); 
  }
	 /* ������ �� */
  public static CommonEntity getGwaonapView(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append("  SELECT  M090_YEAR                     \n");
    sb.append("         ,M090_SEQ                      \n");  
    sb.append("         ,M090_DATE                     \n");
		sb.append("         ,M090_KYULUITYPE               \n");
    sb.append("         ,M090_BUGWANO                  \n");
		sb.append("         ,M090_BALUIDATE                \n");
		sb.append("         ,M090_RETURNORDERDATE          \n");
		sb.append("         ,M090_GOJISEOPUBLISH           \n");
		sb.append("         ,M090_NAPIBDATE                \n");
		sb.append("         ,M090_JINGSUBUWRITE            \n");
		sb.append("         ,M090_GWAONAPWRITE             \n");
		sb.append("     	  ,M090_GWAONAPDATE              \n");
		sb.append("     	  ,M090_GWAN                     \n");
		sb.append("     	  ,M090_HANG                     \n");
		sb.append("     	  ,M090_MOK                      \n");
		sb.append("     	  ,M090_SEMOKCODE                \n");
		sb.append("     	  ,M090_BONAMT                   \n");
		sb.append("     	  ,M090_KINAPBUAMT               \n");
		sb.append("     	  ,M090_GASANAMT                 \n");
		sb.append("     	  ,M090_INTERESTAMT              \n");
		sb.append("     	  ,M090_INTERESTDAY              \n");
		sb.append("     	  ,M090_NAPBUJANAME              \n");
		sb.append("     	  ,M090_JUMINNO                  \n");
		sb.append("     	  ,M090_NAPBUJAADDRESS           \n");
		sb.append("     	  ,M090_BUSINESSNAME             \n");
		sb.append("     	  ,M090_GWAONAPREASON            \n");
		sb.append("     	  ,M090_BANKCODE                 \n");
		sb.append("     	  ,M090_ACCOUNTNO                \n");
		sb.append("     	  ,M090_ACCOUNTHOLDER            \n");
		sb.append("     	  ,M090_FILE                     \n");
		sb.append("     	  ,M090_REGISTERPART             \n");
		sb.append("     	  ,M090_DOCUMENTTYPECODE         \n");
		sb.append("     	  ,M090_GWAONAPSTATECODE         \n");
		sb.append("     	  ,M090_DOCUMENTNO               \n");
		sb.append("     	  ,M090_USERNAME                 \n");
    sb.append("  FROM M090_JINGSUKYULUI_T              \n");
    sb.append(" WHERE M090_SEQ = ?                     \n");
   
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
		
		int i = 1;
		parameters.setString(i++,  paramInfo.getString("seq"));

		return template.getData(conn, parameters);
	}


  public static CommonEntity getSealInfo(Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" SELECT *                      \n");
    sb.append("   FROM M340_USERSEAL_T        \n");
    sb.append(" WHERE M340_CURRENTORGAN = '2' \n");

    QueryTemplate template = new QueryTemplate(sb.toString());

    return template.getData(conn);
  }


  public static int upateRstState(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
    
    sb.append(" UPDATE M090_JINGSUKYULUI_T        \n");
    sb.append("    SET M090_GWAONAPSTATECODE = ?  \n");
    sb.append(" WHERE M090_SEQ = ?              \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();

    int i = 1;
    parameters.setString(i++,  "R2");
		parameters.setString(i++,  paramInfo.getString("seq"));

    return template.update(conn, parameters);
  }
}
