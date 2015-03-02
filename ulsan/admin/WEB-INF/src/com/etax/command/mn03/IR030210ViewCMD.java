/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030210ViewCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입세출외현금 > 등록내역 조회
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030210;
import com.etax.db.mn03.IR030000;
import com.etax.db.mn01.IR010110;
import com.etax.entity.CommonEntity;


public class IR030210ViewCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030210ViewCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",          request.getParameter("org_year"));
    paramInfo.setValue("fis_date",          request.getParameter("fis_date"));
		paramInfo.setValue("m040_seq",          request.getParameter("m040_seq"));
		paramInfo.setValue("part_code",         request.getParameter("pop_part_code"));
    paramInfo.setValue("acc_code",          request.getParameter("pop_acc_code"));
		
    String[] chk_list = request.getParameterValues("chk_yn");
		String[] year_list = request.getParameterValues("year_list");
		String[] seq_list = request.getParameterValues("seq_list");
    
			int chk_val = Integer.parseInt(chk_list[0]);
			paramInfo.setValue("fis_year",       year_list[chk_val]);
			paramInfo.setValue("m040_seq",       seq_list[chk_val]);

      logger.info("paramInfo : " + paramInfo);		

  	/* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 

		CommonEntity cashView = IR030210.getCashView(conn, paramInfo);
		request.setAttribute("page.mn03.cashView", cashView);

		List<CommonEntity> cashDeptList = IR030210.getCashDeptList(conn, paramInfo);
		request.setAttribute("page.mn03.cashDeptList", cashDeptList);

		List<CommonEntity> accNameList = IR030210.getAccNameList(conn,paramInfo);
    request.setAttribute("page.mn03.accNameList", accNameList); 

    CommonEntity endChk = IR030000.getDailyEndWork(conn, paramInfo); //일일마감체크
    request.setAttribute("page.mn03.endChk", endChk);
	}
}