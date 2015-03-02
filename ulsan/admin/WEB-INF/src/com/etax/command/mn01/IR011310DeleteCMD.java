/*****************************************************
* 프로젝트명		: 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		: IR011310DeleteCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2014-11-19
* 프로그램내용		: 세입 > 차량일계삭제
******************************************************/

package com.etax.command.mn01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.List;

import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn01.IR011310;
import com.etax.entity.CommonEntity;

public class IR011310DeleteCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011310DeleteCMD.class);// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("ac_year",           request.getParameter("ac_year"));
		paramInfo.setValue("ac_date",           request.getParameter("ac_date").replace("-", ""));
		
		logger.debug("paramInfo : " + paramInfo);
		
		IR011310.deleteCarDailyData(conn, paramInfo);
        List<CommonEntity> carDailyList = IR011310.getCarDailyList(conn, paramInfo);
        request.setAttribute("page.mn01.carDailyList", carDailyList);
        
        request.setAttribute("page.mn01.SucMsg",  "삭제처리되었습니다.");
	}
}