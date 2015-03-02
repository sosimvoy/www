/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명     : IR090110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-19
* 프로그램내용   : 시스템운영 > 사용자등록/변경신청내역조회/승인
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090110 {

	/* 사용자 내역 조회*/
	public static List getUserSinList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append(" SELECT M260_USERNAME, M260_REQUESTDATE, M260_USERID,M260_PARTSTATE,       \n");
		sb.append("        DECODE(M260_CURRENTORGAN,'1','울산시금고') M260_CURRENTORGAN,      \n");
		
		sb.append("        DECODE(M260_CURRENTPART,'B1','시청지점',                           \n");
		sb.append("                                'B2','OCR센터') CURRENTPART,               \n");
		sb.append("        DECODE(M260_CHANGEPART,'B1','시청지점',                            \n");
		sb.append("                                'B2','OCR센터') CHANGEPART,                \n");
		sb.append("	       DECODE(M260_CURRENTWORK1,'B1','세입등',                            \n");
		sb.append("	                                'B2','자금배정') CURRENTWORK1,            \n");
		sb.append("	       DECODE(M260_CHANGEWORK1,'B1','세입등',                             \n");
		sb.append("	                                'B2','자금배정') CHANGEWORK1,             \n");
		sb.append("	       DECODE(M260_CURRENTSIGNTYPE,'B1','담당자',                         \n");
		sb.append("                                    'B2','책임자' )CURRENTSIGNTYPE,        \n");
		sb.append("	       DECODE(M260_CHANGESIGNTYPE,'B1','담당자',                          \n");       
		sb.append("                                    'B2','책임자' )CHANGESIGNTYPE,         \n");
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
 
	/* 신청 승인 */ 
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

/* 신청 상태 */ 
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
	
	/* 신청 취소 */ 
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
	
	
	/* 변경 취소 */ 
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