/*****************************************************
* 프로젝트명		: 경기도 세출시스템
* 프로그램명		: ReportDAO.java
* 프로그램작성자    : 
* 프로그램작성일    : 2010-09-28
* 프로그램내용	    : 공통(세정과) > 보고서 공통 (시금고,세정과,사업소 별 코드 확인)
******************************************************/

package com.etax.db.common;

import java.sql.*;
import java.util.*;

import com.etax.framework.query.QueryTemplate;
import com.etax.framework.query.QueryParameters;
import com.etax.entity.CommonEntity;

public class ReportDAO {

	/* 마감일자 체크 및 직인 가져오기 */
	public static CommonEntity getEndState(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		    sb.append("SELECT M210_WORKENDSTATE AS END_STATE                                                \n");
        sb.append("      ,(SELECT M340_FNAME FROM M340_USERSEAL_T WHERE M340_CURRENTORGAN = ?) F_NAME   \n");
        sb.append("  FROM M210_WORKEND_T                                                                \n");
        sb.append(" WHERE M210_YEAR = ?                                                                 \n");
        sb.append("   AND M210_DATE = ?                                                                 \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;

        parameters.setString(idx++, "1");       // 1: 시금고
        parameters.setString(idx++, paramInfo.getString("acc_year"));
        parameters.setString(idx++, paramInfo.getString("acc_date"));

		return template.getData(conn, parameters);
	}

    
	/* 보고서 코드 목록 가져오기 */
	public static List<CommonEntity> getReportCodeList(Connection conn, CommonEntity paramInfo) throws SQLException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT M230_REPORTGUBUN  REPORTGUBUN     \n");
        sb.append("      ,M230_REPORTCODE   REPORTCODE      \n");
        sb.append("      ,M230_REPORTNAME   REPORTNAME      \n");   
        sb.append("      ,M230_REPORTURL    REPORTURL       \n");   
        sb.append("      ,M230_WORKPARTCODE WORKPARTCODE    \n");   // 사업소 조회가능부서코드(사업소만 사용)
        sb.append("  FROM M230_REPORTCODE_T                 \n");
        sb.append(" WHERE M230_REPORTGUBUN = ?              \n");
        //sb.append("   AND M230_CITYYN = ?                   \n");   // 세정과 조회가능여부
        sb.append("   AND TRIM(M230_REPORTURL) IS NOT NULL  \n");

		QueryTemplate template = new QueryTemplate(sb.toString());
		QueryParameters parameters = new QueryParameters();
		
		int idx = 1;
        
        parameters.setString(idx++, paramInfo.getString("report_gubun"));   // 보고서구분(D:일일보고서,M:월말보고서,Q:분기보고서)
       // parameters.setString(idx++, "Y");       // 세정과 조회가능여부

		return template.getList(conn, parameters);
	}
}

