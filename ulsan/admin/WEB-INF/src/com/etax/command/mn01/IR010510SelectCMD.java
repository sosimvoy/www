/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR010510SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-05
* 프로그램내용   : 세입 > 등록내역 조회
****************************************************************/
package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR010510;	
import com.etax.db.mn01.IR010110;			
import com.etax.framework.exception.*;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010510SelectCMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR010510SelectCMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();

		paramInfo.setValue("fis_year",         request.getParameter("fis_year"));
		paramInfo.setValue("fis_date",         request.getParameter("fis_date"));
	  paramInfo.setValue("intype",           request.getParameter("intype"));
    paramInfo.setValue("gigwan",           request.getParameter("gigwan"));
		paramInfo.setValue("acc_type",         request.getParameter("acc_type"));	
	  paramInfo.setValue("part_code",        request.getParameter("part_code"));
		paramInfo.setValue("acc_code",         request.getParameter("acc_code"));
	  paramInfo.setValue("semok_code",       request.getParameter("semok_code"));
	  paramInfo.setValue("amt",              request.getParameter("amt"));

		logger.info("paramInfo : " + paramInfo);		

		/* 리스트 조회 */
		List<CommonEntity> expWriteList = IR010510.getExpWriteList(conn, paramInfo);
		request.setAttribute("page.mn01.expWriteList", expWriteList);
		
		List<CommonEntity> deptList = IR010510.getDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.deptList", deptList);	

		List<CommonEntity> accCdList = IR010510.getAccCdList(conn, paramInfo);
    request.setAttribute("page.mn01.accCdList", accCdList); 	

		List<CommonEntity> semokList = IR010510.getSemokList(conn, paramInfo);
    request.setAttribute("page.mn01.semokList", semokList);

    List<CommonEntity> gigwanList = IR010510.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);

    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 
	}
}