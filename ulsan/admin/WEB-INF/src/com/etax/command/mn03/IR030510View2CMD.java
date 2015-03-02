/***************************************************************
* 프로젝트명     : 울산광역시 세입 및 자금배정관리 시스템
* 프로그램명     : IR030510View2CMD.java
* 프로그램작성자 : (주)미르이즈
* 프로그램작성일 : 2010-07-28
* 프로그램내용   : 세입세출외현금 > 주행세일계
****************************************************************/
 
package com.etax.command.mn03;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import org.apache.log4j.Logger;

import com.etax.framework.command.BaseCommand;
import com.etax.db.mn03.IR030510;
import com.etax.entity.CommonEntity;


public class IR030510View2CMD extends BaseCommand {
	private static Logger logger = Logger.getLogger(IR030510View2CMD.class);	// log4j 설정
	public void execute(HttpServletRequest request,	HttpServletResponse response, Connection conn) throws SQLException {
		
		/* (조회할)파라미터 세팅 */
		CommonEntity paramInfo = new CommonEntity();
    
	    paramInfo.setValue("start_date",       request.getParameter("start_date"));
		paramInfo.setValue("end_date",         request.getParameter("end_date"));

        CommonEntity endCnt = IR030510.getEndCnt(conn, paramInfo); //자료 존재여부
        CommonEntity juhangDayView = new CommonEntity();
        if ("0".equals(endCnt.getString("CNT")) ) {  //자료없을 때 직전일 날짜로 조회
            juhangDayView = IR030510.getNothingView(conn, paramInfo);
        } else {  //자료존재할 때
            juhangDayView = IR030510.getJuhangDayView(conn, paramInfo);
        }
        juhangDayView.setValue("CNT",  endCnt.getString("CNT"));
		request.setAttribute("page.mn03.juhangDayView", juhangDayView);

		CommonEntity sealState = IR030510.getSealState(conn);
		request.setAttribute("page.mn03.sealState", sealState);
	}
}