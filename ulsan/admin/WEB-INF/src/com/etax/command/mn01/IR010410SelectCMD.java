/*****************************************************
* 프로젝트명	   : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명	   : IR010410SelectCMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-01
* 프로그램내용	 : 세입 > 코드조회
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.framework.exception.*;
import com.etax.db.mn01.IR010110;
import com.etax.db.mn01.IR010410;
import com.etax.entity.CommonEntity;
import com.etax.util.TextHandler;

public class IR010410SelectCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR010410SelectCMD.class);	// log4j 설정
	
	public void execute(HttpServletRequest request,	HttpServletResponse response,	Connection conn) throws SQLException {
    
		CommonEntity paramInfo = new CommonEntity();
		
		if (!"".equals(request.getParameter("fis_year")) ) {
			paramInfo.setValue("fis_year", request.getParameter("fis_year"));
		}	else {
			paramInfo.setValue("fis_year", TextHandler.formatDate(TextHandler.getCurrentDate(),"yyyyMMdd","yyyy"));
		}
		if ("".equals(request.getParameter("acc_type")) ) {
			paramInfo.setValue("acc_type", "B");
		}	else {
			paramInfo.setValue("acc_type", request.getParameter("acc_type"));
		}
		if ("".equals(request.getParameter("part_code")) ) {
			paramInfo.setValue("part_code", "00000");
		}	else {
			paramInfo.setValue("part_code", request.getParameter("part_code"));
		}
    if (!"".equals(request.getParameter("acc_code")) ) {
			paramInfo.setValue("acc_code", request.getParameter("acc_code"));
		} else {
			List<CommonEntity> accList = IR010410.getAccCodeList(conn, paramInfo);
			CommonEntity accInfo = (CommonEntity)accList.get(0);
			String acc_code = accInfo.getString("M360_ACCCODE");
			paramInfo.setValue("acc_code", acc_code);
		}

    logger.info("paramInfo : " + paramInfo);		


		/* 리스트 조회 */
    CommonEntity fis_date  = IR010110.getFisDate(conn);
    request.setAttribute("page.mn01.fis_date", fis_date); 
		
		List<CommonEntity> specDeptList = IR010410.getSpecDeptList(conn, paramInfo);
		request.setAttribute("page.mn01.specDeptList", specDeptList);
		
		List<CommonEntity> specNameList = IR010410.getSpecNameList(conn, paramInfo);
		request.setAttribute("page.mn01.specNameList", specNameList);

		List<CommonEntity> specSemokList = IR010410.getSpecSemokList(conn, paramInfo);
		request.setAttribute("page.mn01.specSemokList", specSemokList);

		List<CommonEntity> gigwanList = IR010410.getGigwanList(conn);
    request.setAttribute("page.mn01.gigwanList", gigwanList);
	}
}
