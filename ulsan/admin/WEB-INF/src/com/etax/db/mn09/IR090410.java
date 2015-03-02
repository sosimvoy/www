/***************************************************************
* 프로젝트명	     : 울산광역시 세입 및 자금배정관리시스템
* 프로그램명	     : IR090410.java
* 프로그램작성자   : (주)미르이즈
* 프로그램작성일   : 2010-08-18
* 프로그램내용	   : 시스템운영 > 사용자 변경승인신청
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090410 {

	/* 회원 상세 */
	public static CommonEntity getUpdateUserInfo(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("   SELECT  M260_USERID                                                               \n");
		sb.append("					 ,DECODE(M260_CURRENTPART,'B1','시청지점','B2','OCR센터') CURRENTPART       \n");
		sb.append("					 ,M260_CURRENTPART                                                          \n");
		sb.append("					 ,M260_REQUESTDATE                                                          \n");
	  sb.append("					 ,DECODE(M260_CURRENTWORK1,'B1','세입등',                                   \n");
		sb.append("																		'B2','자금배정') CURRENTWORK1                     \n");
		sb.append("					 ,M260_CURRENTWORK1                                                         \n");
		sb.append("					 ,DECODE(M260_CURRENTSIGNTYPE,'B1','담당자','B2','책임자') CURRENTSIGNTYPE  \n");  
		sb.append("					 ,M260_CURRENTSIGNTYPE                                                      \n");
		sb.append("	         ,M260_CURRENTPART,M260_CURRENTWORK1,M260_CURRENTSIGNTYPE ,M260_MGRYN       \n");
		sb.append("     FROM M260_USERMANAGER_T	                                                        \n");
		sb.append("    WHERE M260_USERID = ? 	                                                          \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		 
	  int idx = 1;
		parameters.setString(idx++, paramInfo.getString("user_id"));
         
		return template.getData(conn, parameters);
	}

/* 유저 정보 신청 */
  public static int appUserInfo(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
       
	  sb.append(" UPDATE M260_USERMANAGER_T         \n");
    sb.append("   SET  M260_CHANGEPART  =  ?,     \n"); 
    sb.append("        M260_PARTSTATE   = ?,      \n");  
		sb.append("        M260_CHANGEWORK1 =  ?,     \n"); 
    sb.append("        M260_WORKSTATE1  = ?,      \n");  
		sb.append("        M260_CHANGESIGNTYPE = ?,   \n"); 
    sb.append("        M260_SIGNSTATE   = ?       \n");  
		sb.append("  WHERE M260_USERID = ?            \n");  

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int i = 1;
		
		parameters.setString(i++, paramInfo.getString("changePart"));
		parameters.setString(i++, paramInfo.getString("part_name"));
		parameters.setString(i++, paramInfo.getString("changeWork1"));
		parameters.setString(i++, paramInfo.getString("work_name"));
		parameters.setString(i++, paramInfo.getString("changeSignType"));    
		parameters.setString(i++, paramInfo.getString("sign_name"));
		parameters.setString(i++, paramInfo.getString("user_id"));

		return template.update(conn, parameters);
   }
}