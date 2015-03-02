/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090110.jsp
* 프로그램작성자   :
* 프로그램작성일   : 2010-07-20
* 프로그램내용	   : 시스템운영 > 
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090210 {

	/* 관리자공지사항 상세 */
	public static List getUserList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("   SELECT M260_USERID,                                                     \n");
		sb.append("          M260_USERPW,                                                     \n");
		sb.append("					 M260_USERNAME,                                                   \n");
		sb.append("				 DECODE(M260_CURRENTORGAN,'1','울산시금고') M260_CURRENTORGAN,      \n");
		sb.append("        DECODE(M260_CURRENTPART,'B1','시청지점',                           \n");
		sb.append("                                'B2','OCR센터') M260_CURRENTPART,          \n");
		sb.append("	       DECODE(M260_CURRENTWORK1,'B1','세입등',                            \n");
		sb.append("	                                'B2','자금배정') M260_CURRENTWORK1,       \n");
		sb.append("	       DECODE(M260_CURRENTSIGNTYPE,'B1','담당자',                         \n");
		sb.append("                                    'B2','책임자' ) M260_CURRENTSIGNTYPE,  \n");
	  sb.append("    		   M260_MANAGERBANKERNO,                                            \n");
	  sb.append("          M260_MANAGERNO,                                                  \n");
		sb.append("				   M260_TERMINALNO,                                                 \n");
		sb.append("				   M260_MGRYN                                                       \n");
		sb.append("     FROM M260_USERMANAGER_T	                                              \n");
		sb.append("    WHERE M260_USERSTATE = 'Y'	                                            \n");
		sb.append("      AND M260_CURRENTORGAN = '1'                                          \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		return template.getList(conn, parameters);
	}
	
	/* 유저 권한 */
	public static CommonEntity getMgrYn(Connection conn , CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("   SELECT M260_MGRYN                          \n");
		sb.append("     FROM M260_USERMANAGER_T	                 \n");
		sb.append("    WHERE M260_USERSTATE = 'Y'	               \n");
		sb.append("      AND M260_CURRENTORGAN = '1'             \n");
	  sb.append("      AND M260_USERID = ?                     \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

	  parameters.setString(idx++, paramInfo.getString("user_id"));
		
		return template.getData(conn, parameters);
	}

	/* 유저 삭제 */ 
  public static int deleteUserID(Connection conn, CommonEntity paramInfo) throws SQLException {
    StringBuffer sb = new StringBuffer();
		 
    sb.append(" DELETE FROM M260_USERMANAGER_T   \n");
    sb.append("  WHERE  M260_USERID = ?          \n");

    QueryTemplate template = new QueryTemplate(sb.toString());
    QueryParameters parameters = new QueryParameters();
    
    int idx = 1;
    
		parameters.setString(idx++, paramInfo.getString("userId"));

    return template.delete(conn, parameters);
  }
}