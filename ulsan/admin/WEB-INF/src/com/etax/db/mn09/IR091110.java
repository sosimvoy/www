/***************************************************************
* ������Ʈ��	     : ��걤���� ���� �� �ڱݹ��������ý���
* ���α׷���	     : IR091010.jsp
* ���α׷��ۼ���   : (��)�̸�����
* ���α׷��ۼ���   : 2010-08-16
* ���α׷�����	   : �ý��ۿ > �����ϵ��
****************************************************************/

package com.etax.db.mn09;

import java.sql.*;
import java.util.*;
import com.etax.framework.query.*;
import com.etax.entity.CommonEntity;

public class IR091110 {

	/* ������ �� */
	public static CommonEntity getEndWorkDate(Connection conn, CommonEntity paramInfo)throws SQLException {
    StringBuffer sb = new StringBuffer();

		sb.append("   SELECT  M320_YEAR, M320_DATEILBAN,M320_DATETEKBEYL,M320_DATEGIGEUM    \n");
		sb.append("          ,M320_DATEHYUNGEUM,M320_DATEJEUNGJI,M320_DATEJUHAENGSE         \n");
		sb.append("     FROM  M320_COLSINGDATECONTROL_T                                     \n");
		sb.append("    WHERE  M320_YEAR = ?                                                 \n");
	
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
         
    int i = 1;
    parameters.setString(i++, paramInfo.getString("year"));

		return template.getData(conn, parameters);
	}

/* ������ ���� */
  public static int updateEndWorkDate(Connection conn, CommonEntity paramInfo) throws SQLException {
		StringBuffer sb = new StringBuffer();
       
	  sb.append(" UPDATE M320_COLSINGDATECONTROL_T             \n");
		sb.append("   SET  M320_DATEILBAN       = ?,             \n"); 	
		sb.append("        M320_DATETEKBEYL     = ?,             \n");
		sb.append("        M320_DATEGIGEUM      = ?,             \n");
		sb.append("        M320_DATEHYUNGEUM    = ?,             \n");
		sb.append("        M320_DATEJEUNGJI     = ?,             \n");
		sb.append("        M320_DATEJUHAENGSE   = ?              \n");
		sb.append("  WHERE M320_DATEILBAN       = ?              \n"); 
    sb.append("    AND M320_DATETEKBEYL     = ?              \n");
    sb.append("    AND M320_DATEGIGEUM      = ?              \n");
    sb.append("    AND M320_DATEHYUNGEUM    = ?              \n");
    sb.append("    AND M320_DATEJEUNGJI     = ?              \n");
    sb.append("    AND M320_DATEJUHAENGSE   = ?              \n");
		
		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
    
		int i = 1;
    if("".equals(paramInfo.getString("chDateIlban"))) {
    parameters.setString(i++, paramInfo.getString("dateIlban"));
		}else if (!"".equals(paramInfo.getString("chDateIlban"))){
		 parameters.setString(i++, paramInfo.getString("chDateIlban"));
		}

		if("".equals(paramInfo.getString("chDateTekbeyl"))) {
		parameters.setString(i++, paramInfo.getString("dateTekbeyl"));
		}else if (!"".equals(paramInfo.getString("chDateTekbeyl"))){
		 parameters.setString(i++, paramInfo.getString("chDateTekbeyl"));
		}
    
		if("".equals(paramInfo.getString("chDateGigeum"))) {
		parameters.setString(i++, paramInfo.getString("dateGigeum"));
		}else if (!"".equals(paramInfo.getString("chDateGigeum"))){
		 parameters.setString(i++, paramInfo.getString("chDateGigeum"));
		}
    
		if("".equals(paramInfo.getString("chDateHyungeum"))) {
		parameters.setString(i++, paramInfo.getString("dateHyungeum"));
		}else if (!"".equals(paramInfo.getString("chDateHyungeum"))){
		 parameters.setString(i++, paramInfo.getString("chDateHyungeum"));
		}
    if("".equals(paramInfo.getString("chDateJeungji"))) {
		parameters.setString(i++, paramInfo.getString("dateJeungji"));
		}else if (!"".equals(paramInfo.getString("chDateJeungji"))){
		 parameters.setString(i++, paramInfo.getString("chDateJeungji"));
		}
    if("".equals(paramInfo.getString("chDateJuhaengse"))) {
		parameters.setString(i++, paramInfo.getString("dateJuhaengse"));
		}else if (!"".equals(paramInfo.getString("chDateJuhaengse"))){
		 parameters.setString(i++, paramInfo.getString("chDateJuhaengse"));
		}
		parameters.setString(i++, paramInfo.getString("dateIlban"));
		parameters.setString(i++, paramInfo.getString("dateTekbeyl"));	
		parameters.setString(i++, paramInfo.getString("dateGigeum"));
		parameters.setString(i++, paramInfo.getString("dateHyungeum"));
		parameters.setString(i++, paramInfo.getString("dateJeungji"));
		parameters.setString(i++, paramInfo.getString("dateJuhaengse"));

		return template.update(conn, parameters);
   }
}