/***************************************************************
* ������Ʈ��     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���     : IR090110SelectCMD.java
* ���α׷��ۼ��� : (��)�̸�����
* ���α׷��ۼ��� : 2010-07-19
* ���α׷�����   : �ý��ۿ > ����ڵ��/�����û������ȸ/����
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090110 {

	/* ����� ���� ��ȸ*/
	public static List getUserSinList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT M260_USERNAME, M260_REQUESTDATE, M260_USERID,M260_PARTSTATE,       \n");
		sb.append("        DECODE(M260_CURRENTORGAN,'1','���ñݰ�') M260_CURRENTORGAN,      \n");
		
		sb.append("        DECODE(M260_CURRENTPART,'B1','��û����',                           \n");
		sb.append("                                'B2','OCR����') CURRENTPART,               \n");
		sb.append("        DECODE(M260_CHANGEPART,'B1','��û����',                            \n");
		sb.append("                                'B2','OCR����') CHANGEPART,                \n");
		sb.append("	       DECODE(M260_CURRENTWORK1,'B1','���Ե�',                            \n");
		sb.append("	                                'B2','�ڱݹ���') CURRENTWORK1,            \n");
		sb.append("	       DECODE(M260_CHANGEWORK1,'B1','���Ե�',                             \n");
		sb.append("	                                'B2','�ڱݹ���') CHANGEWORK1,             \n");
		sb.append("	       DECODE(M260_CURRENTSIGNTYPE,'B1','�����',                         \n");
		sb.append("                                    'B2','å����' )CURRENTSIGNTYPE,        \n");
		sb.append("	       DECODE(M260_CHANGESIGNTYPE,'B1','�����',                          \n");       
		sb.append("                                    'B2','å����' )CHANGESIGNTYPE,         \n");
		sb.append("    		 M260_MANAGERBANKERNO, M260_MANAGERNO, M260_TERMINALNO,             \n");
		sb.append("    		 M260_USERSTATE, M260_SIGNSTATE,M260_WORKSTATE1,                    \n");
	  sb.append("    		 M260_CURRENTPART, M260_CHANGEPART,M260_CURRENTWORK1,               \n");
		sb.append("    		 M260_CHANGEWORK1, M260_CURRENTSIGNTYPE,M260_CHANGESIGNTYPE         \n");
		sb.append("   FROM M260_USERMANAGER_T			                                            \n");
		sb.append("  WHERE M260_USERSTATE = 'N'           	                                  \n");
    sb.append("    AND M260_CURRENTORGAN = '1'           	                                \n");
	  sb.append("    OR M260_PARTSTATE = 'Y'           	                                    \n");
		sb.append("    OR M260_WORKSTATE1 = 'Y'           	                                  \n");
		sb.append("    OR M260_SIGNSTATE = 'Y'           	                                    \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();

		return template.getList(conn, parameters);
	}
 
	/* ��û ���� */ 
  public static int updateUser(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer(); 
		
    sb.append(" UPDATE M260_USERMANAGER_T                      \n");
    sb.append("    SET M260_CURRENTPART     = ?                \n");
		sb.append("       ,M260_CURRENTWORK1    = ?                \n");
		sb.append("				,M260_CURRENTSIGNTYPE = ?                \n");
		sb.append("       ,M260_USERSTATE =  'Y'                   \n");
    sb.append("       ,M260_PARTSTATE =  'N'                   \n");
    sb.append("       ,M260_WORKSTATE1 = 'N'                   \n");
		sb.append("				,M260_SIGNSTATE =  'N'                   \n");
	  sb.append("	      ,M260_LOGNO = ?             						 \n");	
		sb.append("  WHERE M260_USERID = ?                         \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		if("".equals(paramInfo.getString("change_part"))) {
    parameters.setString(idx++, paramInfo.getString("current_part"));
		}else if (!"".equals(paramInfo.getString("change_part"))){
		 parameters.setString(idx++, paramInfo.getString("change_part"));
		}
    if("".equals(paramInfo.getString("change_work1"))) {
    parameters.setString(idx++, paramInfo.getString("current_work1"));
		}else if (!"".equals(paramInfo.getString("change_work1"))){
		 parameters.setString(idx++, paramInfo.getString("change_work1"));
		}
    if("".equals(paramInfo.getString("change_signtype"))) {
    parameters.setString(idx++, paramInfo.getString("current_signtype"));
		}else if (!"".equals(paramInfo.getString("change_signtype"))){
		 parameters.setString(idx++, paramInfo.getString("change_signtype"));
		}

		parameters.setString(idx++, paramInfo.getString("log_no"));
		parameters.setString(idx++, paramInfo.getString("userid"));

    return template.update(conn, parameters);
  }

/* ��û ���� */ 
	 public static CommonEntity getUserState(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();

	  sb.append(" SELECT M260_USERSTATE        \n");
    sb.append("   FROM M260_USERMANAGER_T    \n");
    sb.append("  WHERE M260_USERID = ?       \n");
    
		QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
  
		parameters.setString(idx++, paramInfo.getString("userid"));

    return template.getData(conn, parameters);
  }
	
	/* ��û ��� */ 
	  public static int deleteUser(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
	  sb.append("  DELETE FROM M260_USERMANAGER_T  \n");
    sb.append("   WHERE M260_USERID = ?          \n");  
    sb.append("     AND M260_USERSTATE = 'N'     \n");
	
	  QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
  
		parameters.setString(idx++, paramInfo.getString("userid"));

    return template.delete(conn, parameters);
  }
	
	
	/* ���� ��� */ 
  public static int cancelUser(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		
    sb.append(" UPDATE M260_USERMANAGER_T                      \n");
    sb.append("    SET M260_PARTSTATE =  'N'                   \n");
    sb.append("       ,M260_WORKSTATE1 = 'N'                   \n");
		sb.append("				,M260_SIGNSTATE =  'N'                   \n");
		sb.append("  WHERE M260_USERID = ?                         \n");
   
    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
  
		parameters.setString(idx++, paramInfo.getString("userid"));

    return template.update(conn, parameters);
  }
}