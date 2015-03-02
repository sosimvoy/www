/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090310.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090310 {

	/* 회원 상세 */
	public static CommonEntity getUserInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("   SELECT M260_USERID,                                                     \n");
		sb.append("          M260_USERPW,                                                     \n");
		sb.append("					 M260_USERNAME,                                                   \n");
		sb.append("					 M260_USERSTATE,                                                  \n");
		sb.append("					 DECODE(M260_CURRENTORGAN,'1','울산시금고') M260_CURRENTORGAN,    \n");
		sb.append("					 M260_REQUESTDATE,                                                \n");
		sb.append("    		   M260_MANAGERBANKERNO,                                            \n");
		sb.append("					 M260_MANAGERNO,                                                  \n");
		sb.append("					 M260_TERMINALNO,                                                 \n");
		sb.append("					 M260_MGRYN                                                       \n");
		sb.append("     FROM M260_USERMANAGER_T	                                              \n");
		sb.append("    WHERE M260_USERID = ? 	                                                \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
	  int idx = 1;
		parameters.setString(idx++, paramInfo.getString("user_id"));
         
		return template.getData(conn, parameters);
	}

/* 개인정보 수정 */
  public static int updateUserInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
       
	  sb.append(" UPDATE M260_USERMANAGER_T          \n");
    sb.append("   SET  M260_USERNAME = ? ,         \n"); 
    sb.append("        M260_USERPW   = ? ,         \n");
		sb.append("        M260_MANAGERBANKERNO = ?,   \n");  
		sb.append("        M260_MANAGERNO   = ?,       \n");  
		sb.append("        M260_TERMINALNO  = ?        \n");  
		sb.append("  WHERE M260_USERID = ?             \n"); 

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int i = 1;

		if("".equals(paramInfo.getString("chUsername"))) {
    parameters.setString(i++, paramInfo.getString("username"));
		}else if (!"".equals(paramInfo.getString("chUsername"))){
		 parameters.setString(i++, paramInfo.getString("chUsername"));
		}
    if("".equals(paramInfo.getString("chUserpw"))) {
    parameters.setString(i++, paramInfo.getString("userpw"));
		}else if (!"".equals(paramInfo.getString("chUserpw"))){
		 parameters.setString(i++, paramInfo.getString("chUserpw"));
		}
		if("".equals(paramInfo.getString("chManagerBankerNo"))) {
    parameters.setString(i++, paramInfo.getString("managerBankerNo"));
		}else if (!"".equals(paramInfo.getString("chManagerBankerNo"))){
		 parameters.setString(i++, paramInfo.getString("chManagerBankerNo"));
		}
		if("".equals(paramInfo.getString("chManagerNo"))) {
    parameters.setString(i++, paramInfo.getString("managerNo"));
		}else if (!"".equals(paramInfo.getString("chManagerNo"))){
		 parameters.setString(i++, paramInfo.getString("chManagerNo"));
		}
		if("".equals(paramInfo.getString("chTerminalno"))) {
    parameters.setString(i++, paramInfo.getString("terminalNo"));
		}else if (!"".equals(paramInfo.getString("chTerminalno"))){
		 parameters.setString(i++, paramInfo.getString("chTerminalno"));
		}
    parameters.setString(i++, paramInfo.getString("user_id"));

		return template.update(conn, parameters);
   }
}