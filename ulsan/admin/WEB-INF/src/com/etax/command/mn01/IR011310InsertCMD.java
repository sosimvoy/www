/*****************************************************
* 프로젝트명		: 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명		: IR011310InsertCMD.java
* 프로그램작성자	: (주)미르이즈
* 프로그램작성일	: 2014-11-19
* 프로그램내용		: 세입 > 차량일계등록
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

public class IR011310InsertCMD extends BaseCommand {

	private static Logger logger = Logger.getLogger(IR011310InsertCMD.class);	// log4j 설정
	
	// Connection 이 필요한 경우는 BaseCommand 의 abstract execute() 를 구현해서 사용
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
		paramInfo.setValue("ac_year",           request.getParameter("ac_year"));
		paramInfo.setValue("ac_date",           request.getParameter("ac_date").replace("-", ""));
		
		CommonEntity data = IR011310.getCarDailyData(conn, paramInfo);
		
		if (!"0".equals(data.getString("CNT"))) {
		    request.setAttribute("page.mn01.SucMsg",  "해당일자("+request.getParameter("ac_date")+")는 이미 등록되었습니다.");
		} else {
            String[] ac_name = request.getParameterValues("ac_name");
            String[] order_no = request.getParameterValues("order_no");
            String[] before_nugye = request.getParameterValues("before_nugye");
            String[] today_suip = request.getParameterValues("today_suip");
            String[] hwanbu_amt = request.getParameterValues("hwanbu_amt");
            String[] hwanbu_nugye = request.getParameterValues("hwanbu_nugye");
            String[] correct_amt = request.getParameterValues("correct_amt");
            String[] today_nugye = request.getParameterValues("today_nugye");
            
            for (int i=0; i<ac_name.length; i++) {
                paramInfo.setValue("before_nugye",     before_nugye[i].replace(",", ""));//전일누계
                paramInfo.setValue("today_suip",       today_suip[i].replace(",", ""));//금일수입
                paramInfo.setValue("hwanbu_amt",       hwanbu_amt[i].replace(",", ""));//환부
                paramInfo.setValue("hwanbu_nugye",     hwanbu_nugye[i].replace(",", ""));//환부누계
                paramInfo.setValue("correct_amt",      correct_amt[i].replace(",", ""));//정정
                paramInfo.setValue("today_nugye",      today_nugye[i].replace(",", ""));//금일누계합
                paramInfo.setValue("ac_name",          ac_name[i]);//회계명
                paramInfo.setValue("order_no",         order_no[i]);//순번
                logger.info("paramInfo : " + paramInfo);
                //차량등록사업소 일계 등록
                IR011310.insertCarDailyData(conn, paramInfo);
            }
            request.setAttribute("page.mn01.SucMsg",  "등록처리되었습니다.");
		}

        List<CommonEntity> carDailyList = IR011310.getCarDailyList(conn, paramInfo);
        request.setAttribute("page.mn01.carDailyList", carDailyList);
	}
}
