/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR070110SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-09-09
* 프로그램내용   : 일계보고서 > 보고서 조회 (코드)
****************************************************************/
 
package com.etax.command.mn07;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn07.IR070420;
import com.etax.db.common.ReportDAO;
import com.etax.entity.CommonEntity;


public class IR070420SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR070420SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();      
		paramInfo.setValue("acc_year",	    request.getParameter("acc_year"));
		paramInfo.setValue("acc_date",		request.getParameter("acc_date"));
        paramInfo.setValue("report_gubun",  request.getParameter("report_gubun"));

		// 일일비고데이터
  		CommonEntity bigoData = IR070420.getBigoData(conn, paramInfo);
		request.setAttribute("page.mn07.bigoData", bigoData);

        CommonEntity ilgyeData = new CommonEntity();  //일계데이터 초기화
        
        if ("1".equals(request.getParameter("report_gubun"))) {
            // 일반회계일계데이터
  		    ilgyeData = IR070420.getIlgyeData(conn, paramInfo);
        } else if ("2".equals(request.getParameter("report_gubun"))) {
            // 차량사업소일계데이터
  		    ilgyeData = IR070420.getCargyeData(conn, paramInfo);
        }  
        request.setAttribute("page.mn07.ilgyeData", ilgyeData);

        //직인데이터
        CommonEntity signData = ReportDAO.getEndState(conn, paramInfo);
		request.setAttribute("page.mn07.signData", signData);
	
	}
}