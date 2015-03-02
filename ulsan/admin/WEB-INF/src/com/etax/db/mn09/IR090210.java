/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR090110.jsp
* ���α׷��ۼ���   :
* ���α׷��ۼ���   : 2010-07-20
* ���α׷�����	   : �ý��ۿ > 
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR090210 {

	/* �����ڰ������� �� */
	public static List getUserList(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();
 
		sb.append("   SELECT M260_USERID,                                                     \n");
		sb.append("          M260_USERPW,                                                     \n");
		sb.append("					 M260_USERNAME,                                                   \n");
		sb.append("				 DECODE(M260_CURRENTORGAN,'1','���ñݰ�') M260_CURRENTORGAN,      \n");
		sb.append("        DECODE(M260_CURRENTPART,'B1','��û����',                           \n");
		sb.append("                                'B2','OCR����') M260_CURRENTPART,          \n");
		sb.append("	       DECODE(M260_CURRENTWORK1,'B1','���Ե�',                            \n");
		sb.append("	                                'B2','�ڱݹ���') M260_CURRENTWORK1,       \n");
		sb.append("	       DECODE(M260_CURRENTSIGNTYPE,'B1','�����',                         \n");
		sb.append("                                    'B2','å����' ) M260_CURRENTSIGNTYPE,  \n");
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
	
	/* ���� ���� */
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

	/* ���� ���� */ 
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